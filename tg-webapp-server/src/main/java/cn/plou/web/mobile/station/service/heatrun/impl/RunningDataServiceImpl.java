package cn.plou.web.mobile.station.service.heatrun.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import cn.plou.web.common.constant.CKey;
import cn.plou.web.mobile.common.AppConstant;
import cn.plou.web.mobile.common.AppTools;
import cn.plou.web.mobile.station.service.AppBaseService;
import cn.plou.web.mobile.station.service.heatrun.RunningDataService;
import cn.plou.web.mobile.station.vo.LineData;
import cn.plou.web.mobile.station.vo.RunningDataVo;
import cn.plou.web.station.stationDataPoint.dao.StationDataPointMapper;
import cn.plou.web.station.stationDataPoint.vo.StationDataPointVo;
import cn.plou.web.station.stationshowdev.dao.StationShowDevMapper;
import cn.plou.web.station.stationshowdev.entity.StationShowDev;
import cn.plou.web.station.stationshowdevtype.dao.StationShowDevTypeMapper;
import cn.plou.web.station.stationshowdevtype.entity.StationShowDevType;
import cn.plou.web.system.baseMessage.station.dao.StationMapper;
import cn.plou.web.system.baseMessage.system.dao.SystemMapper;
import cn.plou.web.system.commonMessage.heatingTime.dao.HeatingTimeMapper;

/**
 * @Project : tg-webapp-server
 * @File : RunningDataServiceImpl.java
 * @Author : WangJiWei
 * @Date : 2018年10月9日下午12:57:32
 *
 * @Comments :
 * 
 */
@Service
public class RunningDataServiceImpl extends AppBaseService implements RunningDataService {

    private static final Logger log = LoggerFactory.getLogger(RunningDataServiceImpl.class);

    @Autowired
    SystemMapper systemMapper;
    @Autowired
    StationMapper stationMapper;
    @Autowired
    StationDataPointMapper stationDataPointMapper;
    @Autowired
    StationShowDevMapper stationShowDevMapper;
    @Autowired
    HeatingTimeMapper heatingTimeMapper;
    @Autowired
    StationShowDevTypeMapper stationShowDevTypeMapper;

    /**
     * systemId->{供回水数据:[供水温度、回水温度、供水压力、回水压力],流量/功率:[循环流量、瞬时功率、补水流量]...}
     */
    /**
     * 指定系统实时数据
     */
    @Override
    public List<RunningDataVo> findRunningData(String userId, String systemId) {

	/**
	 * 点位数据基于站ID或系统ID
	 */
	/** 当前系统ID下有效点位最新数据 */
	// valided=1,有效点位、showlevel=1,关注,全部
	String stationId = systemId.substring(0, systemId.length() - 2);
	List<String> ids = Arrays.asList(stationId, systemId);
	List<StationDataPointVo> sdps = stationDataPointMapper.findDataBySystemIds(ids,
		AppConstant.POINT_VALIDED, AppConstant.SHOW_LEVEL_1);
	/** 当前系统ID下热泵数据 最新数据 */
	// cn.plou.web.system.baseMessage.system.entity.System system =
	// getSystem(systemId);
	// String companyId = system.getCompanyId();
	List<StationShowDev> hPumpInfos = stationShowDevMapper.findBySourceIds(
		Arrays.asList(systemId, stationId),
		AppConstant.RB);/* devType:热泵类型 TODO */
	if (hPumpInfos != null && hPumpInfos.size() != 0) {
	    Map<String, StationShowDev> devIdMap = AppTools.listToMap(hPumpInfos,
		    StationShowDev::getDevId, __ -> __);
	    List<StationDataPointVo> hPumpsData = stationDataPointMapper.findDataBySystemIds(
		    new ArrayList<>(devIdMap.keySet()), AppConstant.POINT_VALIDED,
		    AppConstant.SHOW_LEVEL_1);// TODO
	    if (hPumpsData != null) {
		hPumpsData.forEach(h -> {
		    h.setPointName(devIdMap.get(h.getSourceid()).getDevName());
		});
		sdps.addAll(hPumpsData);
	    }
	}
	/** 所有点位按显示项分组 */
	Map<String, List<StationDataPointVo>> showGroup = AppTools.groupBy(sdps,
		StationDataPointVo::getShowdevtype);
	List<LineData> lineData = checkCOP(stationId, showGroup);
	return buildViewData(showGroup, lineData);
    }

    /**
     * COP曲线
     */
    private List<LineData> checkCOP(String stationId,
	    Map<String, List<StationDataPointVo>> showGroup) {
	List<LineData> lineData = null;
	if (showGroup.containsKey(AppConstant.COP_POINT_NAME)) { // 如果有cop的点位配置
	    // TODO 今日 、总量
	    /* 今天0点 ~ 至今的cop曲线 */
	    try {
		lineData = queryES(stationId, AppConstant.COP_POINT_NAME,
			AppTools.formatDate(CKey.YMD000).getTime(), System.currentTimeMillis(),
			"asc", null);
	    } catch (Exception e) {
		log.error("Failed to load data from ES, " + e.toString());
	    }
	}
	return lineData;
    }

    /***
     * 采暖季开始时间
     */
    private Date getHeatTimeStart(String stationId, String companyId) {
	Date start = heatingTimeMapper.getStartByStation(stationId);
	/* 如果当前站没有采暖季开始时间,则取公司级 */
	return start == null ? heatingTimeMapper.getStartByCompany(companyId) : start;
    }

    private cn.plou.web.system.baseMessage.system.entity.System getSystem(String systemId) {
	cn.plou.web.system.baseMessage.system.entity.System systemInfo = systemMapper
		.selectByPrimaryKey(systemId);
	return systemInfo;
    }

    /** 构建数据格式 */
    private List<RunningDataVo> buildViewData(Map<String, List<StationDataPointVo>> showGroup,
	    List<LineData> lineData) {

	Map<String, StationShowDevType> sdtMap = showDevTypeMap();
	List<RunningDataVo> rdvs = Lists.newArrayList();
	for (Entry<String, List<StationDataPointVo>> en : showGroup.entrySet()) {
	    String k = en.getKey(); // showDevtype
	    RunningDataVo rdv = new RunningDataVo();
	    StationShowDevType sst = sdtMap.get(k);
	    /**
	     * 2018-10-27 新添加排序<br/>
	     * notes: 关注页-分组排序,分组内点位排序延后
	     */
	    rdv.setOrder(Integer.parseInt(sst.getMemo2()));
	    rdv.setCategory(sst == null ? "默认" : sst.getDevName()); // 类目
	    List<StationDataPointVo> vs = en.getValue();
	    long lastTime = 0;
	    for (StationDataPointVo s : vs) {
		Date time = s.getTime();
		if (time != null && time.getTime() >= lastTime) {
		    lastTime = time.getTime();
		}
	    }
	    rdv.setLastTime(new Date(lastTime));
	    rdv.setMetrics(vs);
	    if (k.contains(AppConstant.COP_POINT_NAME)) {
		rdv.setType("2");// 0.普通点位 1.热泵状态 2.COP
		rdv.setLineData(lineData);
	    } else if (k.contains("1")) {
		rdv.setType("1"); // 热泵状态：正常、停止、故障
	    } else {
		rdv.setType("0");
	    }
	    rdvs.add(rdv);
	}
	/** ============ */
	// RunningDataVo r = new RunningDataVo();
	// r.setCategory("COP");
	// r.setLastTime(new Date());
	// r.setType("2");
	// lineData = new ArrayList<>();
	// Random rr = new Random();
	// for (int i = 1; i <= 1000; i++) {
	// double d = rr.nextDouble();
	// LineData data = new LineData(new Date().getTime(), new Double(d));
	// lineData.add(data);
	// }
	// r.setLineData(lineData);
	// List<StationDataPointVo> metrics2 = new ArrayList<>();
	// StationDataPointVo ss1 = new StationDataPointVo();
	// ss1.setPointid("abc");
	// ss1.setPointName("瞬时COP");
	// ss1.setShowdevtype("COP");
	// ss1.setTime(new Date());
	// ss1.setVal(0.59D);
	// metrics2.add(ss1);
	// r.setMetrics(metrics2);
	// rdvs.add(r);

	/** ============= **/
	return rdvs;
    }

    @Override
    public Map<String, StationShowDevType> showDevTypeMap() {
	List<StationShowDevType> showDevType = stationShowDevTypeMapper.findAll();
	Map<String, StationShowDevType> sdtMap = AppTools.listToMap(showDevType,
		StationShowDevType::getDevType, __ -> __);
	return sdtMap;
    }

}
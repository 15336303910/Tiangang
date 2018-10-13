package cn.plou.web.mobile.station.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.google.common.collect.Maps;

import cn.plou.web.common.utils.Support;
import cn.plou.web.common.utils.Tools;
import cn.plou.web.mobile.common.AppTools;
import cn.plou.web.mobile.station.service.RunningDataService;
import cn.plou.web.mobile.station.vo.RunningDataVo;
import cn.plou.web.station.stationDataPoint.dao.StationDataPointMapper;
import cn.plou.web.station.stationDataPoint.vo.StationDataPointVo;
import cn.plou.web.station.stationshowdev.dao.StationShowDevMapper;
import cn.plou.web.station.stationshowdev.entity.StationShowDev;
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
public class RunningDataServiceImpl implements RunningDataService {

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

    /**
     * TODO app显示数据根据用户,灵活配置展示项
     * systemId->{供回水数据:[供水温度、回水温度、供水压力、回水压力],流量/功率:[循环流量、瞬时功率、补水流量]...}
     */
    @Override
    public List<RunningDataVo> findRunningData(String userId, String systemId) {

	/** 当前系统ID下有效点位最新数据 */
	// valided=1,有效点位、showlevel=1,关注,全部
	List<StationDataPointVo> sdps = stationDataPointMapper.findDataBySystemId(systemId, 0, 1);
	/** 当前系统ID下热泵数据 最新数据 */
	cn.plou.web.system.baseMessage.system.entity.System system = getSystem(systemId);
	String stationId = system.getStationId();
	String companyId = system.getCompanyId();
	List<StationShowDev> hPumpInfos = stationShowDevMapper.findBySourceIds(
		Arrays.asList(systemId, stationId), "1");/* devType:1 */
	List<String> hPumpIds = AppTools.objectExtractIds(hPumpInfos, h -> h.getDevId());
	List<StationDataPointVo> hPumpsData = stationDataPointMapper.findDataBySystemIds(hPumpIds,
		0, 1);// TODO
	sdps.addAll(hPumpsData);

	/** 所有点位按显示项分组 */
	Map<String, List<StationDataPointVo>> showGroup = AppTools.groupBy(sdps,
		StationDataPointVo::getShowdevtype);

	List<Map<String, Object>> lineData = null;
	if (showGroup.containsKey("COP")) { // 如果有cop的点位配置
	    // TODO 今日 、总量
	    /* 采暖季开始 ~ 至今的cop曲线 */
	    Date start = getHeatTimeStart(stationId, companyId);// 当前站采暖季开始时间
	    try {
		// TODO cop小时索引,size = 24 * 120
		String sql = "SELECT val,time FROM station_data_point"//
			+ " where sourceid = '" + systemId + "' and time > '"//
			+ Tools.dateToTimeStr(start, null) + "' and time < '"//
			+ Tools.currentTime() // TODO endTime <= 采暖季结束时间?
			+ "' order by time asc";
		ResultSet rs = Support.querryFromEs(sql);
		lineData = extractRS(rs);
	    } catch (Exception e) {
		log.error("Failed to load data from ES, " + e.toString());
	    }
	}
	return buildViewData(showGroup, lineData);
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
	    List<Map<String, Object>> lineData) {
	List<RunningDataVo> rdvs = Lists.newArrayList();
	for (Entry<String, List<StationDataPointVo>> en : showGroup.entrySet()) {
	    String k = en.getKey(); // showDevtype
	    RunningDataVo rdv = new RunningDataVo();
	    rdv.setCategory(k); // 类目
	    List<StationDataPointVo> vs = en.getValue();
	    Date lastTime = vs.get(vs.size() - 1).getTime();
	    rdv.setLastTime(lastTime);
	    rdv.setMetrics(vs);
	    if (k.contains("COP")) {
		rdv.setType("2");// 0.普通点位 1.热泵状态 2.COP
		rdv.setLineData(lineData);
	    } else if (k.contains("热泵")) {
		rdv.setType("1"); // 正常、停止、故障
	    } else {
		rdv.setType("0");
	    }
	    rdvs.add(rdv);
	}
	return rdvs;
    }

    /**  */
    private List<Map<String, Object>> extractRS(ResultSet rs) throws SQLException {
	List<Map<String, Object>> items = null;
	if (null != rs) {
	    items = Lists.newArrayList();
	    Map<String, Object> item = null;
	    while (rs.next()) {
		Double val = rs.getObject("val") == null ? null : rs.getDouble("val");
		String time = rs.getObject("time") == null ? null : rs.getString("time");
		if (!Tools.isNull(time)) { // 曲线断点
		    item = Maps.newHashMap();
		    item.put("val", val);
		    item.put("time", time);
		    items.add(item);
		}
	    }
	}
	return items;
    }

}

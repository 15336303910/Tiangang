package cn.plou.web.mobile.station.service.heatrun.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.plou.web.common.constant.CKey;
import cn.plou.web.mobile.common.AppConstant;
import cn.plou.web.mobile.common.AppTools;
import cn.plou.web.mobile.station.query.ParamQuery;
import cn.plou.web.mobile.station.service.AppBaseService;
import cn.plou.web.mobile.station.service.heatrun.ParamDetailService;
import cn.plou.web.mobile.station.service.heatrun.RunningDataService;
import cn.plou.web.mobile.station.vo.LineData;
import cn.plou.web.mobile.station.vo.ParamGroupVo;
import cn.plou.web.mobile.station.vo.ShowDevTypeVo;
import cn.plou.web.station.stationDataPoint.dao.StationDataPointMapper;
import cn.plou.web.station.stationDataPoint.vo.StationDataPointVo;
import cn.plou.web.station.stationshowdev.dao.StationShowDevMapper;
import cn.plou.web.station.stationshowdev.entity.StationShowDev;
import cn.plou.web.station.stationshowdevtype.entity.StationShowDevType;
import cn.plou.web.station.systemPoint.dao.SystemPointMapper;
import cn.plou.web.station.systemPoint.entity.SystemPoint;

/**
 * @Project : tg-webapp-server
 * @File : ParamDetailServiceImpl.java
 * @Author : WangJiWei
 * @Date : 2018年10月11日上午10:25:11
 *
 * @Comments :
 * 
 */
@Service
public class ParamDetailServiceImpl extends AppBaseService implements ParamDetailService {

    private static final Logger log = LoggerFactory.getLogger(ParamDetailServiceImpl.class);

    @Autowired
    StationDataPointMapper stationDataPointMapper;
    @Autowired
    StationShowDevMapper stationShowDevMapper;
    @Autowired
    SystemPointMapper systemPointMapper;
    @Autowired
    RunningDataService runningDataService;

    private List<String> systemIdToStationId(List<String> systemIds) {
	Set<String> sets = new HashSet<>();
	systemIds.forEach(s -> {
	    sets.add(s);
	    sets.add(s.substring(0, s.length() - 2));
	});
	return new ArrayList<>(sets);
    }

    /**
     * 列表
     */
    @Override
    public List<ParamGroupVo> findParamList(String systemId) {
	List<String> ids = Arrays.asList(systemId, systemId.substring(0, systemId.length() - 2));

	List<StationShowDev> hpumps = stationShowDevMapper.findBySourceIds(ids, AppConstant.RB);// 热泵
	if (AppTools.isNotEmpty(hpumps)) {
	    List<String> hids = AppTools.objectExtractIds(hpumps, StationShowDev::getDevId);
	    hids.addAll(ids);
	    List<SystemPoint> sps = systemPointMapper.findPoints(hids, AppConstant.POINT_VALIDED,
		    AppConstant.SHOW_LEVEL_2, null);
	    Set<String> showdevtypes = new HashSet<>();
	    sps.forEach(s -> { // 去重
		if (StringUtils.isNotBlank(s.getShowdevtype())) {
		    showdevtypes.add(s.getShowdevtype());
		}
	    });
	    Map<String, StationShowDevType> map = runningDataService.showDevTypeMap();
	    Map<String, ParamGroupVo> hashPGV = Maps.newHashMap();
	    showdevtypes.forEach(p -> {// 分类
		try {
		    StationShowDevType ssdt = map.get(p);
		    ShowDevTypeVo showDevTypeVo = new ShowDevTypeVo(ssdt.getDevType(),
			    ssdt.getDevName());
		    String memo1 = ssdt.getMemo1();
		    /**
		     * 2018-10-27 添加排序 memo2全部页一级分组排序
		     */
		    String memo2 = ssdt.getMemo2();
		    if (hashPGV.containsKey(memo1)) {
			ParamGroupVo lp = hashPGV.get(memo1);
			lp.getShowdevtypes().add(showDevTypeVo);
		    } else {
			ParamGroupVo lp = new ParamGroupVo(memo1);
			lp.setOrder(Integer.parseInt(memo2));
			List<ShowDevTypeVo> sds = new ArrayList<>(Arrays.asList(showDevTypeVo));
			lp.setShowdevtypes(sds);
			hashPGV.put(memo1, lp);
		    }
		} catch (Exception e) {
		    log.error(e.toString());
		    e.printStackTrace();
		}
	    });
	    Collection<ParamGroupVo> values = hashPGV.values();
	    return new ArrayList<>(values);
	}
	return null;
    }

    /**
     * 指定showType下所有点位实时数据
     */
    @Override
    public List<StationDataPointVo> findRunningDataByParam(String systemId, String showdevtype) {

	List<String> ids = Arrays.asList(systemId, systemId.substring(0, systemId.length() - 2));
	/**
	 * 热泵
	 */
	if (StringUtils.equals(showdevtype, AppConstant.RB)) {
	    List<StationShowDev> hPumpInfos = stationShowDevMapper.findBySourceIds(ids,
		    showdevtype);
	    if (AppTools.isNotEmpty(hPumpInfos)) {
		List<String> hPumpIds = AppTools.objectExtractIds(hPumpInfos, h -> h.getDevId());
		List<StationDataPointVo> sdps = stationDataPointMapper.findDataBySystemIds(hPumpIds,
			AppConstant.POINT_VALIDED, AppConstant.SHOW_LEVEL_2);// TODO
		if (AppTools.isNotEmpty(sdps)) {
		    Map<String, StationShowDev> map = AppTools.listToMap(hPumpInfos,
			    StationShowDev::getDevId, __ -> __);
		    for (StationDataPointVo s : sdps) {
			StationShowDev ss = map.get(s.getSourceid());
			s.setPointName(ss == null ? s.getPointName()
				: (ss.getDevName() + "_" + s.getPointName()));
		    }
		    /**
		     * 2017-10-25 同热泵放一起显示
		     */
		    List<StationDataPointVo> collect = sdps.stream()
			    .sorted((u1, u2) -> u1.getPointName().compareTo(u2.getPointName()))
			    .collect(Collectors.toList());
		    return collect;
		}
	    }
	    return null;
	} else {/** 其他点位 */
	    return stationData(ids, showdevtype);
	}
    }

    /**
     * 指定sourceid和pointid查询当前点位信息
     */
    private SystemPoint getSystemPoint(String sourceid, String pointid) {
	return systemPointMapper.findOne(sourceid, pointid);
    }

    private List<StationDataPointVo> stationData(List<String> sourceids, String showdevtype) {
	List<StationDataPointVo> runningData = stationDataPointMapper
		.findDataBySystemIdAndShowdevtype(sourceids, AppConstant.POINT_VALIDED,
			AppConstant.SHOW_LEVEL_2, showdevtype);
	return runningData;
    }

    /**
     * 指定点位历史数据
     */
    @Override
    public List<LineData> findHistory(String stationId, String pointid, ParamQuery paramQuery)
	    throws Exception {
	String showType = paramQuery.getShowType();
	showType = StringUtils.isBlank(showType) ? AppConstant.PARAM_SHOW_TYPE_ALL : showType;
	List<LineData> datas = null;
	if (StringUtils.equals(showType, AppConstant.PARAM_SHOW_TYPE_HOUR)) { // 
	    /** 指定时间范围内的每小时第一条数据 */
	    // datas = queryES(stationId, pointid, start, end);
	    datas = Lists.newArrayList();
	    Date ss = AppTools.formatDate(paramQuery.getStartTime(), CKey.YMDH00);
	    Date ee = AppTools.formatDate(paramQuery.getEndTime(), CKey.YMDH00);
	    List<Date> rangeDate = AppTools.rangeDate(ss, ee);
	    Date temp = ss;
	    for (Date d : rangeDate) {
		List<LineData> data = queryES(stationId, pointid, temp.getTime(), d.getTime(),
			paramQuery.getOrderBy(), AppConstant.LIMIT_1);
		datas.addAll(data);
		temp = d;
	    }
	} else {
	    /** 指定时间范围内的所有数据 */
	    /** TODO 前端协助拼接 */
	    long start = AppTools.formatDate(paramQuery.getStartTime(), CKey.YMDHMS__).getTime();
	    long end = AppTools.formatDate(paramQuery.getEndTime(), CKey.YMDHMS__).getTime();
	    datas = queryES(stationId, pointid, start, end, paramQuery.getOrderBy(), null);
	}
	return datas;
    }

    /**
     * 参数历史数据列表 created by wzd.
     */
    @Override
    public List<LineData> findParamHistoryDataList(String stationId, String pointid, String limit,
	    ParamQuery paramQuery) throws Exception {
	List<LineData> datas = null;
	long start = AppTools.formatDate(paramQuery.getStartTime(), CKey.YMDHMS__).getTime();
	long end = AppTools.formatDate(paramQuery.getEndTime(), CKey.YMDHMS__).getTime();
	datas = queryES(stationId, pointid, start, end, paramQuery.getOrderBy(), limit);

	return datas;
    }

    /**
     * 参数历史数据列表 created by wzd.
     */
    @Override
    public Integer findParamHistoryCount(String stationId, String pointid, ParamQuery paramQuery)
	    throws Exception {
	long start = AppTools.formatDate(paramQuery.getStartTime(), CKey.YMDHMS__).getTime();
	long end = AppTools.formatDate(paramQuery.getEndTime(), CKey.YMDHMS__).getTime();
	Integer count = queryESCount(stationId, pointid, start, end);
	return count;
    }

}

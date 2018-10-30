package cn.plou.web.mobile.station.service.impl;

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
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Maps;
import cn.plou.web.common.constant.CKey;
import cn.plou.web.mobile.common.AppConstant;
import cn.plou.web.mobile.common.AppTools;
import cn.plou.web.mobile.station.query.ParamQuery;
import cn.plou.web.mobile.station.service.AppBaseService;
import cn.plou.web.mobile.station.service.ParamDetailService;
import cn.plou.web.mobile.station.service.RunningDataService;
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

    @Override
    public Collection<ParamGroupVo> findParamList(String systemId) {
	List<String> ids = Arrays.asList(systemId, systemId.substring(0, systemId.length() - 2));

	List<StationShowDev> hpumps = stationShowDevMapper.findBySourceIds(ids, "1");// 热泵
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
	    StationShowDevType ssdt = map.get(p);
	    ShowDevTypeVo showDevTypeVo = new ShowDevTypeVo(ssdt.getDevType(), ssdt.getDevName());
	    String memo1 = ssdt.getMemo1();
	    if (hashPGV.containsKey(memo1)) {
		ParamGroupVo lp = hashPGV.get(memo1);
		lp.getShowdevtypes().add(showDevTypeVo);
	    } else {
		ParamGroupVo lp = new ParamGroupVo(memo1);
		List<ShowDevTypeVo> sds = new ArrayList<>(Arrays.asList(showDevTypeVo));
		lp.setShowdevtypes(sds);
		hashPGV.put(memo1, lp);
	    }
	});
	Collection<ParamGroupVo> values = hashPGV.values();
	return values;
    }

    @Override
    public List<StationDataPointVo> findRunningDataByParam(String systemId, String showdevtype) {

	List<String> ids = Arrays.asList(systemId, systemId.substring(0, systemId.length() - 2));
	if (StringUtils.equals(showdevtype, "1")) {
	    List<StationShowDev> hPumpInfos = stationShowDevMapper
		    .findBySourceIds(ids, showdevtype);
	    List<String> hPumpIds = AppTools.objectExtractIds(hPumpInfos, h -> h.getDevId());
	    return stationDataPointMapper.findDataBySystemIds(hPumpIds, AppConstant.POINT_VALIDED,
		    AppConstant.SHOW_LEVEL_2);// TODO
	} else {
	    return stationData(ids, showdevtype);
	}
    }

    private List<StationDataPointVo> stationData(List<String> sourceids, String showdevtype) {
	List<StationDataPointVo> runningData = stationDataPointMapper
		.findDataBySystemIdAndShowdevtype(sourceids, AppConstant.POINT_VALIDED,
			AppConstant.SHOW_LEVEL_2, showdevtype);
	return runningData;
    }

    @Override
    public List<LineData> findHistory(String stationId, String pointid, ParamQuery paramQuery)
	    throws Exception {
	String showType = paramQuery.getShowType();
	showType = StringUtils.isBlank(showType) ? AppConstant.PARAM_SHOW_TYPE_ALL : showType;
	List<LineData> datas = null;
	if (StringUtils.equals(showType, AppConstant.PARAM_SHOW_TYPE_HOUR)) {
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

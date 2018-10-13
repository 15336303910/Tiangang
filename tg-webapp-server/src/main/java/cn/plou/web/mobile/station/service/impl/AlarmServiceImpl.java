package cn.plou.web.mobile.station.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.plou.web.common.constant.CKey;
import cn.plou.web.mobile.common.AppTools;
import cn.plou.web.mobile.station.query.AlarmsQuery;
import cn.plou.web.mobile.station.service.AlarmService;
import cn.plou.web.station.alarm.dao.AlarmInfoMapper;
import cn.plou.web.station.alarm.entity.AlarmInfo;
import cn.plou.web.station.alarm.vo.AlarmStatistics;

/**
 * @Project : tg-webapp-server
 * @File : AlarmServiceImpl.java
 * @Author : WangJiWei
 * @Date : 2018年10月9日下午12:54:15
 *
 * @Comments :
 * 
 */
@Service
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    AlarmInfoMapper alarmInfoMapper;

    @Override
    public Map<String, Object> findAlarmStatistics(List<String> stationIds) throws Exception {
	/** 最新报警数量 */
	int latestAlarmCount = alarmInfoMapper.findLatestAlarmCount(stationIds, null, 0);
	String now = AppTools.curDateStr(CKey.YMD_);
	Date start = AppTools.formatDate(now + AppTools.TIME_LEFT, CKey.YMDHMS__);
	Date end = AppTools.formatDate(now + AppTools.TIME_RIGHT, CKey.YMDHMS__);
	/** 低值报警、高值报警、低低值报警、高高值报警 */
	List<AlarmStatistics> typeAlarmStat = alarmInfoMapper
		.findAlarmStatisticsGroupByType(stationIds, start, end);
	/** 今日报警数 = 所有类型的报警和 */
	Integer todayStat = typeAlarmStat.stream()
		.collect(Collectors.summingInt(AlarmStatistics::getTotal));
	/** 今日已处理 */
	Integer todayFinish = alarmInfoMapper.getCountByResult(stationIds, 1, start, end);
	/** 今日未处理 */
	Integer todayNoFinish = (todayStat != null && todayFinish != null)
		? (todayStat - todayFinish) : null;
	/** 处理进度 */
	// Double ratio = (todayFinish != null && todayStat != null && todayStat
	// != 0)
	// ? BigDecimalUtil.div(todayFinish, todayStat, 2).doubleValue() : null;
	/** 正常报警、紧急报警 */ // TODO 下钻?
	List<AlarmStatistics> levelAlarmStat = alarmInfoMapper
		.findAlarmStatisticsGroupByLevel(stationIds, start, end);
	return new HashMap<String, Object>(20) {
	    private static final long serialVersionUID = 1L;
	    {
		put("newestAlarm", latestAlarmCount);// 最新报警
		put("todayAlarm", todayStat);// 今日报警
		put("finishAlarm", todayFinish);// 已处理
		put("noFinishAlarm", todayNoFinish);// 未处理
		// put("ratio", ratio);// 处理进度
		put("type", typeAlarmStat);// 报警类型:{低值报警、高值报警、低低值报警、高高值报警}
		put("level", levelAlarmStat);// 报警级别:{正常报警、紧急报警}
	    }
	};
    }

    /**
     * 报警、操作日志
     *
     */
    @Override
    public List<AlarmInfo> findAlarms(AlarmsQuery query) throws Exception {
	String alarmTime = query.getAlarmTime();
	if (StringUtils.isNotBlank(alarmTime)) {
	    query.setStartTime(AppTools.formatDate(alarmTime + AppTools.TIME_LEFT, CKey.YMDHMS__));
	    query.setEndTime(AppTools.formatDate(alarmTime + AppTools.TIME_RIGHT, CKey.YMDHMS__));
	}
	return alarmInfoMapper.findAlarms(query);
    }

    @Override
    public void verifyAlarm(String userId, List<String> alarmIds) {
	alarmInfoMapper.updateAlarms(userId, 1, alarmIds);
    }

}

package cn.plou.web.mobile.station.service.heatrun;

import java.util.List;
import java.util.Map;

import cn.plou.web.mobile.station.query.AlarmsQuery;
import cn.plou.web.mobile.station.query.EventInfoQuery;
import cn.plou.web.station.alarm.vo.AlarmInfoVo;
import cn.plou.web.station.event.entity.EventInfo;

/**
 * @Project : tg-webapp-server
 * @File : AlarmService.java
 * @Author : WangJiWei
 * @Date : 2018年10月9日下午12:53:08
 *
 * @Comments : 报警
 * 
 */
public interface AlarmService {

    /**
     * 报警统计
     */
    public Map<String, Object> findAlarmStatistics(List<String> stationIds) throws Exception;

    /**
     * 报警日志查询
     */
    public List<AlarmInfoVo> findAlarms(AlarmsQuery query) throws Exception;

    public Integer alarmTotal(AlarmsQuery query) throws Exception;

    /**
     * 确认报警
     */
    public void verifyAlarm(String userId, List<String> alarmIds);

    public Integer alarmCount(List<String> systemIds);

    public Integer eventTotal(EventInfoQuery query) throws Exception;

    public List<EventInfo> findEvents(EventInfoQuery query) throws Exception;

}

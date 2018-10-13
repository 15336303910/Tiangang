package cn.plou.web.mobile.station.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.constant.CKey;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.mobile.common.AppTools;
import cn.plou.web.mobile.station.query.AlarmsQuery;
import cn.plou.web.mobile.station.service.AlarmService;
import cn.plou.web.mobile.station.service.RunningDataService;
import cn.plou.web.mobile.station.service.StationAppService;
import cn.plou.web.mobile.station.vo.RunningDataVo;
import cn.plou.web.station.alarm.entity.AlarmInfo;
import cn.plou.web.system.baseMessage.system.vo.SystemInfoVo;

/**
 * @Project : tg-webapp-server
 * @File : StationController.java
 * @Author : WangJiWei
 * @Date : 2018年9月19日上午9:18:30
 *
 * @Comments :
 * 
 */
@RestController
@RequestMapping("/mobile/station")
public class StationAppController extends AppBaseController {

    @Autowired
    StationAppService stationService;
    @Autowired
    AlarmService alarmService;
    @Autowired
    RunningDataService runningDataService;

    /**
     * 获取用户权限内的所有系统
     */
    @RequestMapping("/systems")
    public Root findStationInfoList() throws Exception {

	/** TODO 登录复用web端 */
	// String userId = UserUtils.getUserId();
	String userId = "95277";
	List<SystemInfoVo> systems = stationService.findSystemByUserId(userId);
	Map<String, List<SystemInfoVo>> result = AppTools.groupBy(systems,
		SystemInfoVo::getCompanyName);
	return success(JSON.toJSON(result));
	/**
	 * 数据格式:
	 * 
	 * <pre>
	{
	    "code": 0,
	    "msg": "操作成功",
	    "data": {
	      "王基伟测试公司": [
	        {
	          "companyId": "00240",
	          "systemId": "000261",
	          "systemName": "b-1",
	          "companyName": "王基伟测试公司",
	          "stationName": "bbbbbbbb",
	          "stationId": "000240"
	        },
	        {
	          "companyId": "00240",
	          "systemId": "000262",
	          "systemName": "c-1",
	          "companyName": "王基伟测试公司",
	          "stationName": "ccccccccc",
	          "stationId": "000241"
	        }
	      ]
	    }
	  }
	 * </pre>
	 * 
	 */
    }

    /**
     * 实时数据
     */
    @GetMapping("/system/{systemId}/metrics")
    public Root realTimeData(@PathVariable String systemId) throws Exception {
	String userId = "95277";
	List<RunningDataVo> rdv = runningDataService.findRunningData(userId, systemId);
	Object data = JSONArray.parseArray(JSON.toJSONString(rdv));
	return success(data);
	/**
	 * 
	 * <pre>
	 * {
	"code": 0,
	"msg": "操作成功",
	"data": [
	{
	  "metrics": [
	    {
	      "sourceid": "000002",
	      "val": 1.1033,
	      "unit": "kWh",
	      "pointid": "CT_Tot",
	      "pointName": "测试点",
	      "showdevtype": "",
	      "time": 1532653305000
	    },
	    {
	      "sourceid": "000002",
	      "val": 0,
	      "unit": "A",
	      "pointid": "Conv2_Cur",
	      "showdevtype": "",
	      "time": 1532653279000
	    },
	    {
	      "sourceid": "000002",
	      "unit": "",
	      "pointid": "Stat",
	      "showdevtype": ""
	    }
	      ],
	      "category": "供回水温度",
	      "lineData":[],
	      "lastTime":1532653279000,
	      "type": "0"
	    }
	]
	}
	 * </pre>
	 * 
	 */
    }

    /**
     * 报警统计
     */
    @PostMapping("/alarm/stat")
    public Root alarmStat(@RequestBody List<String> stationIds) throws Exception {
	if (stationIds == null || stationIds.size() == 0) {
	    stationIds = checkStationIds();
	}
	Map<String, Object> alarmStatistics = alarmService.findAlarmStatistics(stationIds);
	Object data = new JSONObject(alarmStatistics);
	return success(data);
	/**
	 * <pre>
	 * {
	      "code": 0,
	      "msg": "操作成功",
	      "data": {
	        "newestAlarm": 3,
	        "finishAlarm": 720,
	        "level": [
	          {
	            "type": "正常报警",
	            "total": 720
	          },
	          {
	            "type": "紧急报警",
	            "total": 480
	          }
	        ],
	        "todayAlarm": 1200,
	        "noFinishAlarm": 480,
	        "type": [
	          {
	            "type": "低值报警",
	            "total": 400
	          },
	          {
	            "type": "高值报警",
	            "total": 400
	          },
	          {
	            "type": "低低值报警",
	            "total": 400
	          },
	          {
	            "type": "高高值报警",
	            "total": 400
	          }
	        ]
	      }
	    }
	 * </pre>
	 * 
	 * 
	 */
    }

    private List<String> checkStationIds() throws Exception {
	Set<String> temp = Sets.newHashSet();
	List<SystemInfoVo> sysInfos = stationService
		.findSystemByUserId(/* UserUtils.getUserId() */"95277");
	if (sysInfos != null && sysInfos.size() != 0) {
	    sysInfos.forEach(s -> {
		temp.add(s.getStationId());
	    });
	}
	// return new ArrayList<>(temp);
	return Arrays.asList("1", "2");
    }

    /**
     * 报警日志列表
     */
    @PostMapping("/alarm/list")
    public Root alarms(@RequestBody AlarmsQuery query) throws Exception {
	if (query.getStationIds() == null || query.getStationIds().size() == 0) {
	    query.setStationIds(checkStationIds());
	}
	/** 默认查询未处理 */
	query.setResult(null == query.getResult() ? 0 : query.getResult());
	query.setResultTime(null);
	query.setAlarmTime(StringUtils.isBlank(query.getAlarmTime())
		? AppTools.curDateStr(CKey.YMD_) : query.getAlarmTime());
	List<AlarmInfo> alarms = alarmService.findAlarms(query);
	Object data = JSONArray.parseArray(JSON.toJSONString(alarms));
	return success(data);
	/**
	 * <pre>
	 * {
	      "code": 0,
	      "msg": "操作成功",
	      "data": [
	        {
	          "devId": "1",
	          "resultTime": 1539049708000,
	          "val": "123",
	          "valset": "123",
	          "level": "1",
	          "sysId": "1",
	          "resultPerson": "王基伟",
	          "primaryId": "1",
	          "type": "1",
	          "result": 1,
	          "companyId": "1",
	          "des": "1",
	          "beginTime": 1543303818000,
	          "endTime": 1543248000000,
	          "commuityId": "1",
	          "stationId": "1"
	        },
	        {
	          "devId": "2",
	          "result": 0,
	          "companyId": "2",
	          "level": "2",
	          "sysId": "2",
	          "beginTime": 1543304261000,
	          "endTime": 1543304269000,
	          "primaryId": "2",
	          "type": "2",
	          "commuityId": "2",
	          "stationId": "2"
	        }
	      ]
	    }
	 * </pre>
	 */
    }

    /**
     * 报警确认
     */
    @PostMapping("/alarm/verify")
    public Root verifyAlarm(@RequestBody List<String> alarmIds) throws Exception {
	if (alarmIds != null && alarmIds.size() != 0) {
	    // String userId = UserUtils.getUserId();
	    String userId = "9527";
	    alarmService.verifyAlarm(userId, alarmIds);
	}
	return success(null);
    }

    /**
     * 操作日志
     */
    @PostMapping("/alarm/operations")
    public Root alarmOperations(@RequestBody AlarmsQuery query) throws Exception {
	if (query.getStationIds() == null || query.getStationIds().size() == 0) {
	    query.setStationIds(checkStationIds());
	}
	/** 默认查询处理过的日志 */
	query.setResult(1);
	query.setAlarmTime(null);
	query.setResultTime(StringUtils.isBlank(query.getResultTime())
		? AppTools.curDateStr(CKey.YMD_) : query.getResultTime());
	List<AlarmInfo> alarms = alarmService.findAlarms(query);
	Object data = JSONArray.parseArray(JSON.toJSONString(alarms));
	return success(data);
    }

}

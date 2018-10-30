package cn.plou.web.heatManage.diagnose.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.plou.web.common.utils.PageUtils;
import cn.plou.web.common.utils.Query;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.heatManage.diagnose.domain.MbusInfoDO;
import cn.plou.web.heatManage.diagnose.domain.MeterInfoDO;
import cn.plou.web.heatManage.diagnose.service.MbusInfoService;
import cn.plou.web.heatManage.diagnose.service.MeterInfoService;
import cn.plou.web.system.baseMessage.company.entity.Company;
import cn.plou.web.system.baseMessage.company.service.CompanyService;
import cn.plou.web.system.permission.role.service.DataRoleService;

/**
 * 
 * 设备诊断
 * 
 * @author liuxiaodong
 * @email yigeyanse@hotmail.com
 * @date 2018-07-03 16:33:45
 */

@RestController
@RequestMapping("${heatManagePath}/diagnose")
public class ChartDataController {
	@Autowired
	private MbusInfoService mbusInfoService;

	@Autowired
	private MeterInfoService meterInfoService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private DataRoleService dataRoleService;
	/**
	 * 获取状态信息
	 * 
	 * @param params
	 * @return
	 */
	@GetMapping("/getDeviceStatusData/{type}/{id}")
	public Object getDeviceStatusData(@PathVariable("type") String type, @PathVariable("id") String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("consumerId", id);
		params.put("type", type);

		 Boolean flag = dataRoleService.getDataRoleItemsType(UserUtils.getUserId(), params);
		JSONObject jsonObject = new JSONObject();
		// 设备总数
//		List<MeterInfoDO> list = meterInfoService.list(params);
		params.put("isvalid", "1");
		int listSize = meterInfoService.count(params);
		jsonObject.put("deviceCnts", listSize);

		// 热表
		params.remove("runningState");
		params.put("meterType", "metertype_01");
//		List<MeterInfoDO> meterList = meterInfoService.list(params);
		int meterListSize = meterInfoService.count(params);
		jsonObject.put("meterCnts", meterListSize);
		// 通讯故障
		Integer meterNetErrorCnts = meterInfoService.netErrorCount(params);
		jsonObject.put("meterNetErrorCnts", meterNetErrorCnts);
		// 设备故障
		Integer meterErrorCnts = meterInfoService.deviceErrorCount(params);
		jsonObject.put("meterErrorCnts", meterErrorCnts);

		params.remove("runningState");
		List<MeterInfoDO> meterErrorList = meterInfoService.groupList(params);
		JSONArray array1 = new JSONArray();
		for (MeterInfoDO meterError : meterErrorList) {
			JSONObject obj1 = new JSONObject();
			obj1.put("name", meterError.getName());
			obj1.put("value", meterError.getCnts());
			array1.add(obj1);
		}
		jsonObject.put("meterErrorList", array1);

		// 集中器--------------------------------------------------------------
		params.remove("runningState");
		params.remove("meterType");
//		List<MbusInfoDO> mbusList = mbusInfoService.list(params);
		int mbusListSize = mbusInfoService.count(params);
		jsonObject.put("mbusCnts", mbusListSize);
		//通讯故障
		params.put("runningState", "mbus_run_status_1");
		Integer mbusNetErrorCnts = mbusInfoService.errorCount(params);
		jsonObject.put("mbusNetErrorCnts", mbusNetErrorCnts);
		//设备故障
//		jsonObject.put("mbusErrorCnts", mbusNetErrorCnts);
		jsonObject.put("mbusErrorCnts", "0");
		params.remove("runningState");
		JSONArray array2 = new JSONArray();
		List<MbusInfoDO> mbusErrorList = mbusInfoService.groupList(params);
		for (MbusInfoDO mbusError : mbusErrorList) {
			JSONObject obj1 = new JSONObject();
			obj1.put("name", mbusError.getTypeName());
			obj1.put("value", mbusError.getCnts());
			array2.add(obj1);
		}
		jsonObject.put("mbusErrorList", array2);

		// 温控阀
		params.remove("runningState");
		params.put("meterType", "metertype_02");
//		List<MeterInfoDO> wkList = meterInfoService.list(params);
		int wkListSize = meterInfoService.count(params);
		jsonObject.put("wkCnts", wkListSize);
		// 通讯故障
//		params.put("runningState", "valve_read_status_ERR");
		Integer wkNetErrorCnts = meterInfoService.netErrorCount(params);
		jsonObject.put("wkNetErrorCnts", wkNetErrorCnts);
		// 设备故障
//		params.put("runningState", "valve_read_status_ERR1,valve_read_status_ERR2");
		Integer wkErrorCnts = meterInfoService.deviceErrorCount(params);
		jsonObject.put("wkErrorCnts", wkErrorCnts);
		
		
		

		JSONArray array3 = new JSONArray();
		params.remove("runningState");
		List<MeterInfoDO> wkErrorList = meterInfoService.groupList(params);
		for (MeterInfoDO wkError : wkErrorList) {
			JSONObject obj1 = new JSONObject();
			obj1.put("name", wkError.getName());
			obj1.put("value", wkError.getCnts());
			array3.add(obj1);
		}
		jsonObject.put("wkErrorList", array3);

		// 室温采集
		params.remove("runningState");
		params.put("meterType", "metertype_0D");
//		List<MeterInfoDO> tmpCollectorList = meterInfoService.list(params);
		int tmpCollectorListSize = meterInfoService.count(params);
		jsonObject.put("tmpCollectorCnts", tmpCollectorListSize);
		// 通讯故障
		params.put("runningState", "room_temperature_read_status_ERR");
		Integer tmpCollectorNetErrorCnts = meterInfoService.netErrorCount(params);
		jsonObject.put("tmpCollectorNetErrorCnts", tmpCollectorNetErrorCnts);
		// 设备故障
		params.put("runningState", "room_temperature_read_status_ERR1");
		Integer tmpCollectorErrorCnts = meterInfoService.deviceErrorCount(params);
		jsonObject.put("tmpCollectorErrorCnts", tmpCollectorErrorCnts);

		JSONArray array4 = new JSONArray();
		params.remove("runningState");
		List<MeterInfoDO> tmpCollectorErrorList = meterInfoService.groupList(params);
		for (MeterInfoDO tmpCollectorError : tmpCollectorErrorList) {
			JSONObject obj1 = new JSONObject();
			obj1.put("name", tmpCollectorError.getName());
			obj1.put("value", tmpCollectorError.getCnts());
			array4.add(obj1);
		}
		jsonObject.put("tmpCollectorErrorList", array4);
		
		
		// 平衡阀
		params.remove("runningState");
		params.put("meterType", "metertype_09");
//		List<MeterInfoDO> tmpCollectorList = meterInfoService.list(params);
		int tmpBalanceListSize = meterInfoService.count(params);
		jsonObject.put("tmpBalanceCnts", tmpBalanceListSize);
		// 通讯故障
//		params.put("runningState", "room_temperature_read_status_ERR");
		Integer tmpBalanceNetErrorCnts = meterInfoService.netErrorCount(params);
		jsonObject.put("tmpBalanceNetErrorCnts", tmpBalanceNetErrorCnts);
		// 设备故障
//		params.put("runningState", "room_temperature_read_status_ERR1");
		Integer tmpBalanceErrorCnts = meterInfoService.deviceErrorCount(params);
		jsonObject.put("tmpBalanceErrorCnts", tmpBalanceErrorCnts);

		JSONArray array5 = new JSONArray();
		params.remove("runningState");
		List<MeterInfoDO> tmpBalanceErrorList = meterInfoService.groupList(params);
		for (MeterInfoDO tmpBalanceError : tmpBalanceErrorList) {
			JSONObject obj1 = new JSONObject();
			obj1.put("name", tmpBalanceError.getName());
			obj1.put("value", tmpBalanceError.getCnts());
			array5.add(obj1);
		}
		jsonObject.put("tmBalanceErrorList", array5);

		// 通讯故障总数
		jsonObject.put("deviceNetErrorCnts",
				meterNetErrorCnts + mbusNetErrorCnts + wkNetErrorCnts + tmpCollectorNetErrorCnts+tmpBalanceNetErrorCnts);
		// 设备故障总数
		jsonObject.put("deviceErrorCnts", meterErrorCnts + mbusNetErrorCnts + wkErrorCnts + tmpCollectorErrorCnts+tmpBalanceErrorCnts);

		return jsonObject;
	}

}

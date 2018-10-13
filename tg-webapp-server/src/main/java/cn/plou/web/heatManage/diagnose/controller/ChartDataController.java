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
import cn.plou.web.heatManage.diagnose.domain.MbusInfoDO;
import cn.plou.web.heatManage.diagnose.domain.MeterInfoDO;
import cn.plou.web.heatManage.diagnose.service.MbusInfoService;
import cn.plou.web.heatManage.diagnose.service.MeterInfoService;
import cn.plou.web.system.baseMessage.company.entity.Company;
import cn.plou.web.system.baseMessage.company.service.CompanyService;

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

		JSONObject jsonObject = new JSONObject();
		// 设备总数
		List<MeterInfoDO> list = meterInfoService.list(params);
		jsonObject.put("deviceCnts", list.size());

		// 热表
		params.remove("runningState");
		params.put("meterType", "metertype_1");
		List<MeterInfoDO> meterList = meterInfoService.list(params);
		jsonObject.put("meterCnts", meterList.size());
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
		List<MbusInfoDO> mbusList = mbusInfoService.list(params);
		jsonObject.put("mbusCnts", mbusList.size());
		//通讯故障
		params.put("runningState", "mbus_run_status_1");
		Integer mbusNetErrorCnts = mbusInfoService.errorCount(params);
		jsonObject.put("mbusNetErrorCnts", mbusNetErrorCnts);
		//设备故障
		jsonObject.put("mbusErrorCnts", mbusNetErrorCnts);
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
		params.put("meterType", "metertype_2");
		List<MeterInfoDO> wkList = meterInfoService.list(params);
		jsonObject.put("wkCnts", wkList.size());
		// 通讯故障
		params.put("runningState", "valve_read_status_ERR");
		Integer wkNetErrorCnts = meterInfoService.netErrorCount(params);
		jsonObject.put("wkNetErrorCnts", wkNetErrorCnts);
		// 设备故障
		params.put("runningState", "valve_read_status_ERR1,valve_read_status_ERR2");
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
		params.put("meterType", "metertype_7");
		List<MeterInfoDO> tmpCollectorList = meterInfoService.list(params);
		jsonObject.put("tmpCollectorCnts", tmpCollectorList.size());
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

		// 通讯故障总数
		jsonObject.put("deviceNetErrorCnts",
				meterNetErrorCnts + mbusNetErrorCnts + wkNetErrorCnts + tmpCollectorNetErrorCnts);
		// 设备故障总数
		jsonObject.put("deviceErrorCnts", meterErrorCnts + mbusNetErrorCnts + wkErrorCnts + tmpCollectorErrorCnts);

		return jsonObject;
	}

}

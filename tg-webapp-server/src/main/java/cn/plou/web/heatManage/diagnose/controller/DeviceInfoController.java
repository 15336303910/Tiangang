package cn.plou.web.heatManage.diagnose.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
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
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.TypeMstService;

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
public class DeviceInfoController {
	@Autowired
	private MbusInfoService mbusInfoService;

	@Autowired
	private MeterInfoService meterInfoService;

	@Autowired
	private TypeMstService typeMstService;

	@GetMapping("/getMeterStatusDetail/{meterType}")
	public List<TypeMst> getDeviceStatusList(@PathVariable("meterType") String meterType) {
		String kbn = "";
		switch(meterType) {
		case "metertype_01":
			kbn = "meter_status";
			break;
		case "metertype_02":
			kbn = "valve_read_status";
			break;
		case "metertype_07":
			kbn = "room_temperature_read_status";
			break;
		case "mbus":
			kbn = "mbus_run_status";
			break;
		}
		
		List<String> listKbn = new ArrayList<String>();
		listKbn.add(kbn);
		List<TypeMst> list = typeMstService.getDownInfoByTypeKbns(listKbn);

		return list;
	}

	/**
	 * 热表、温控阀、采集器
	 * 
	 * @param type
	 * @param id
	 * @param meterType
	 * @param meterId
	 * @param runningState
	 * @return
	 */
	@GetMapping("/getMeterStatusDetail")
	public PageUtils getMeterStatusDetail(@RequestParam Map<String, Object> params) {
		// String type, String consumerId, String meterType, String meterId, String
		// runningState
		// String limit,String offset
		Query query = new Query(params);
		List<MeterInfoDO> meterInfoList = meterInfoService.list(query);

		JSONArray array = new JSONArray();
		for (MeterInfoDO meter : meterInfoList) {
			JSONObject jsonObject = new JSONObject();
			// 一户的信息
			jsonObject.put("meterId", meter.getMeterId());
			jsonObject.put("source", meter.getAddress());
			TypeMst mst2 = typeMstService.getTypeMstById(meter.getRunningState());
			jsonObject.put("deviceStatus", mst2==null?"":mst2.getTypeName());
			if(meter.getMeterErrorTime()!=null) {
				jsonObject.put("errorTime",  DateFormatUtils.format(meter.getMeterErrorTime(), "yyyy-MM-dd HH:mm:ss"));
				jsonObject.put("errorDays",
						Math.abs((new Date().getTime() - meter.getMeterErrorTime().getTime()) / 86400000));
			}
			MbusInfoDO mbus = mbusInfoService.get(meter.getMbusId());
			if(mbus != null){
				jsonObject.put("mbusCode", mbus.getMbusCode());
				TypeMst mst = typeMstService.getTypeMstById(mbus.getRunningState());
				jsonObject.put("mbusNetState", mst.getTypeName());
			}
			jsonObject.put("compName", meter.getFactory());

			array.add(jsonObject);
		}
		// 查询列表数据
		int total = meterInfoService.count(query);
		PageUtils pageUtils = new PageUtils(JSONArray.parseArray(array.toJSONString(), Object.class), total);
		return pageUtils;
	}

	/**
	 * 获取集中器状态信息
	 * 
	 * @param params
	 * @return
	 */
	@GetMapping("/getMbusStatusDetail")
	public PageUtils getMbusStatusDetail(@RequestParam Map<String, Object> params) {
		// String type, String consumerId, String mbusId, String runningState
		// String limit,String offset
		Query query = new Query(params);
		List<MbusInfoDO> mbusInfoList = mbusInfoService.list(query);
		JSONArray array = new JSONArray();
		for (MbusInfoDO mbus : mbusInfoList) {
			JSONObject jsonObject = new JSONObject();
			// 一户的信息
			jsonObject.put("consumerId", mbus.getConsumerId());
			jsonObject.put("mbusCode", mbus.getMbusCode());
			TypeMst mst = typeMstService.getTypeMstById(mbus.getRunningState());
			jsonObject.put("deviceStatus", mst==null?"":mst.getTypeName());
			TypeMst mstBusy = typeMstService.getTypeMstById(mbus.getBusyStatus());
			jsonObject.put("lineStatus", mstBusy==null?"":mstBusy.getTypeName());

			if(mbus.getOnlineTime()!=null)
			jsonObject.put("onlineTime", DateFormatUtils.format(mbus.getOnlineTime(), "yyyy-MM-dd HH:mm:ss"));
			if(mbus.getOfflineTime()!=null)
			jsonObject.put("offlineTime", DateFormatUtils.format(mbus.getOfflineTime(), "yyyy-MM-dd HH:mm:ss"));
			jsonObject.put("sendInterval", mbus.getSendInterval());

			if (mbus.getOfflineTime() != null) {
				jsonObject.put("errorTime",  DateFormatUtils.format(mbus.getOfflineTime(), "yyyy-MM-dd HH:mm:ss"));
				jsonObject.put("errorDays",
						Math.abs((new Date().getTime() - mbus.getOfflineTime().getTime()) / 86400000));
			}
			
			jsonObject.put("cardFlow", mbus.getCardFlow());
			TypeMst mstTans = typeMstService.getTypeMstById(mbus.getTransMode());
			jsonObject.put("transType", mstTans==null?"":mstTans.getTypeName());
			TypeMst mstUp = typeMstService.getTypeMstById(mbus.getUpCommMode());
			jsonObject.put("uploadType", mstUp==null?"":mstUp.getTypeName());
			TypeMst mstChan = typeMstService.getTypeMstById(mbus.getChannlMode());
			jsonObject.put("isVirtual",mstChan==null?"": mstChan.getTypeName());

			jsonObject.put("companyName", mbus.getFactory());
			jsonObject.put("remark", mbus.getNotes());
			jsonObject.put("isFirstSearch", mbus.getIsFirst());
			jsonObject.put("address", mbus.getAddress());
			array.add(jsonObject);
		}
		// 查询列表数据
		int total = mbusInfoService.count(query);
		PageUtils pageUtils = new PageUtils(JSONArray.parseArray(array.toJSONString(), Object.class), total);
		return pageUtils;
	}

}

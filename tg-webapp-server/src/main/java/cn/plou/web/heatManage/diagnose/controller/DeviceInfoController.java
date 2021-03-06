package cn.plou.web.heatManage.diagnose.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.PageUtils;
import cn.plou.web.common.utils.Query;
import cn.plou.web.heatManage.diagnose.domain.MbusInfoDO;
import cn.plou.web.heatManage.diagnose.domain.MeterInfoDO;
import cn.plou.web.heatManage.diagnose.service.MbusInfoService;
import cn.plou.web.heatManage.diagnose.service.MeterInfoService;
import cn.plou.web.heatManage.monitor.domain.RunningDataTotalDO;
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
		case "metertype_0D":
			kbn = "room_temperature_read_status";
			break;
		case "mbus":
			kbn = "mbus_run_status";
			break;
		case "metertype_09":
			kbn = "balance_valve_state";
			break;
		}
		
		List<String> listKbn = new ArrayList<String>();
		listKbn.add(kbn);
		List<TypeMst> list = typeMstService.getDownInfoByTypeKbns(listKbn);
		
		Iterator<TypeMst> iterator = list.iterator();
        while (iterator.hasNext()) {
        	TypeMst tp = iterator.next();
            if ("0".equals(tp.getTypeId())) {
                iterator.remove();//使用迭代器的删除方法删除
            }
        }
		

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
	
		JSONArray array = meterInfoService.getMeterStatusDetail(params);
		// 查询列表数据
		params.put("errorFlay", "1");
		Query query = new Query(params);
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
		
		JSONArray array = mbusInfoService.getMbusStatusDetail(params);
		// 查询列表数据
		params.put("errorFlay", "1");
		Query query = new Query(params);
		int total = mbusInfoService.count(query);
		PageUtils pageUtils = new PageUtils(JSONArray.parseArray(array.toJSONString(), Object.class), total);
		return pageUtils;
	}
	
  @RequestMapping(value="/diagnoseMeterToExcel",method = RequestMethod.POST)
  public Root diagnoseMeterToExcel(@RequestBody Map<String, Object> params, ServletRequest request) {
      Root root = new Root();
      root.setData(meterInfoService.diagnoseMeterToExcel(params,request));
      return root;
  }
  
  @RequestMapping(value="/diagnoseMubsToExcel",method = RequestMethod.POST)
  public Root diagnoseMubsToExcel(@RequestBody Map<String, Object> params, ServletRequest request) {
      Root root = new Root();
      root.setData(mbusInfoService.diagnoseMubsToExcel(params,request));
      return root;
  }
  
  @RequestMapping(value="/diagnoseTmpToExcel",method = RequestMethod.POST)
  public Root diagnoseTmpToExcel(@RequestBody Map<String, Object> params, ServletRequest request) {
      Root root = new Root();
      root.setData(meterInfoService.diagnoseTmpToExcel(params,request));
      return root;
  }
  
  @RequestMapping(value="/diagnoseWkToExcel",method = RequestMethod.POST)
  public Root diagnoseWkToExcel(@RequestBody Map<String, Object> params, ServletRequest request) {
      Root root = new Root();
      root.setData(meterInfoService.diagnoseWkToExcel(params,request));
      return root;
  }
  
  @RequestMapping(value="/diagnoseBalanceToExcel",method = RequestMethod.POST)
  public Root diagnoseBalanceToExcel(@RequestBody Map<String, Object> params, ServletRequest request) {
      Root root = new Root();
      root.setData(meterInfoService.diagnoseWkToExcel(params,request));
      return root;
  }
  
}

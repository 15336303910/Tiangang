package cn.plou.web.heatManage.monitor.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.plou.common.utils.DateUtil;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.CamelCaseUtil;
import cn.plou.web.common.utils.PageUtils;
import cn.plou.web.common.utils.Query;
import cn.plou.web.common.utils.R;
import cn.plou.web.common.utils.Tools;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.heatManage.diagnose.service.MbusInfoService;
import cn.plou.web.heatManage.diagnose.service.MeterInfoService;
import cn.plou.web.heatManage.diagnose.vo.MeterInfoVO;
import cn.plou.web.heatManage.history.service.HistoryDataService;
import cn.plou.web.heatManage.monitor.domain.RunningDataTotalDO;
import cn.plou.web.heatManage.monitor.domain.SystemMarkersDO;
import cn.plou.web.heatManage.monitor.domain.WeatherDataDO;
import cn.plou.web.heatManage.monitor.service.RunningDataTotalService;
import cn.plou.web.heatManage.monitor.service.SystemMarkersService;
import cn.plou.web.heatManage.monitor.service.WeatherDataService;
import cn.plou.web.heatManage.monitor.vo.BuildingRunningDataTotalVO;
import cn.plou.web.heatManage.monitor.vo.HouseDataVO;
import cn.plou.web.system.baseMessage.build.entity.Build;
import cn.plou.web.system.baseMessage.build.service.BuildService;
import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import cn.plou.web.system.baseMessage.commuity.service.CommuityService;
import cn.plou.web.system.baseMessage.company.entity.Company;
import cn.plou.web.system.baseMessage.company.service.CompanyService;
import cn.plou.web.system.baseMessage.house.entity.House;
import cn.plou.web.system.baseMessage.house.service.HouseService;
import cn.plou.web.system.baseMessage.unit.entity.Unit;
import cn.plou.web.system.baseMessage.unit.service.UnitService;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.TypeMstService;
import cn.plou.web.system.permission.role.service.DataRoleService;
import io.netty.util.internal.StringUtil;

/**
 * 用户运行数据汇总
 * 
 * @author liuxiadong
 * @Date 2018年6月29日 上午9:40:34
 */

@RestController
@RequestMapping("${heatManagePath}/monitor/house")
public class HouseRunningDataController {
	@Autowired
	private RunningDataTotalService runningDataTotalService;

	@Autowired
	private WeatherDataService dataService;

	@Autowired
	private HistoryDataService historyDataService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CommuityService commuityService;

	@Autowired
	private BuildService buildService;

	@Autowired
	private UnitService unitService;

	@Autowired
	private HouseService houseService;

	@Autowired
	private SystemMarkersService systemMarkersService;

	@Autowired
	private MbusInfoService mbusInfoService;

	@Autowired
	private MeterInfoService meterInfoService;

	@Autowired
	private TypeMstService typeMstService;

	@Autowired
	private DataRoleService dataRoleService;
	
	/**
	 * 客户报修
	 * 
	 * @param consumerId  户id
	 * @return
	 */
	@GetMapping("/getCustomerServiceData/{consumerId}")
	public Object getCustomerServiceData(@PathVariable("consumerId") String consumerId) {
		JSONArray jsonArray = new JSONArray();
		for(int i=0; i<3;i++){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("categoryType", "表扬投诉");
		jsonObject.put("processTime", "2018-10-01");
		jsonObject.put("desc", i+"：这里是报修内容，我家不暖和。");
		jsonObject.put("overFlag", "处理中");
		jsonObject.put("executRecord", "正在处理，水管堵了。");
		jsonObject.put("executTime", "2018-10-27");
		jsonObject.put("executPerson", "李微微");
		jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	/**
	 * 客户报修
	 * 
	 * @param consumerId  户id
	 * @return
	 */
	@GetMapping("/getCustomerServiceData/{consumerId}/{id}")
	public Object setCustomerServiceStatus(@PathVariable("consumerId") String consumerId,@PathVariable("id") String id) {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", "设置成功");
		return jsonObject;
		
		
	}
	
	/**
	 * 户用图-楼汇总信息
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	@GetMapping("/getBuildingUseHeatData/{buildingId}/{beginTime}/{endTime}")
	public Object getBuildingUseHeatData(@PathVariable("buildingId") String buildingId,
			@PathVariable("beginTime") String beginTime, @PathVariable("endTime") String endTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("consumerId", buildingId);
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);

		BuildingRunningDataTotalVO data = runningDataTotalService.getBuildingVO(params);

		JSONObject jsonBuildingObject = new JSONObject();
		// 楼汇总信息
		Commuity community = commuityService.getCommuityById(buildingId.substring(0, 10));
		jsonBuildingObject.put("communityName", community.getCommuityName());
		Build build = buildService.getBuildById(buildingId);
		jsonBuildingObject.put("buildingName", build.getBuildingName());
		jsonBuildingObject.put("unitCnts", build.getUnitNum());
		jsonBuildingObject.put("totalHouseCnts", data.getHouseCnts());

		// 设备汇总信息
		MeterInfoVO meterVo = meterInfoService.getByBuilding(buildingId);
		if (meterVo == null) {
			jsonBuildingObject.put("totalMeterCnts", 0);
			jsonBuildingObject.put("totalFlowMeterCnts", 0);
			jsonBuildingObject.put("totalWkfCnts", 0);
			jsonBuildingObject.put("totalMbusCnts", 0);
			jsonBuildingObject.put("totalPhfCnts", 0);
			jsonBuildingObject.put("totalTmpCollectorCnts", 0);
		} else {
			jsonBuildingObject.put("totalMeterCnts", meterVo.getTotalMeterCnts());
			jsonBuildingObject.put("totalFlowMeterCnts", meterVo.getTotalFlowMeterCnts());
			jsonBuildingObject.put("totalWkfCnts", meterVo.getTotalWkfCnts());
			jsonBuildingObject.put("totalMbusCnts", meterVo.getTotalMbusCnts());
			jsonBuildingObject.put("totalPhfCnts", meterVo.getTotalPhfCnts());
			jsonBuildingObject.put("totalTmpCollectorCnts", meterVo.getTotalTmpCollectorCnts());
		}
		// 运行参数汇总
		jsonBuildingObject.put("inWaterTmp", Tools.formatBigDecimalTo2(data.getInWaterTmp()));
		jsonBuildingObject.put("outWaterTmp", Tools.formatBigDecimalTo2(data.getOutWaterTmp()));
		jsonBuildingObject.put("totalFlow", Tools.formatBigDecimalTo2(data.getTotalFlow()));
		jsonBuildingObject.put("totalPower",Tools.formatBigDecimalTo2(data.getTotalPower()));

		return jsonBuildingObject;
	}

	/**
	 * 数据列表-统计信息
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	@GetMapping("/getStatisticsData/{type}/{consumerId}/{beginTime}/{endTime}")
	public Object getStatisticsData(@PathVariable("type") String type, @PathVariable("consumerId") String consumerId,
			@PathVariable("beginTime") String beginTime, @PathVariable("endTime") String endTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("consumerId", consumerId);
		// params.put("beginTime", beginTime);
		// params.put("endTime", endTime);

		BuildingRunningDataTotalVO data = runningDataTotalService.statisticsData(params);

		JSONObject jsonBuildingObject = new JSONObject();
		// 楼汇总信息
		jsonBuildingObject.put("unitCnts", data.getUnitCnts());
		jsonBuildingObject.put("totalHouseCnts", data.getHouseCnts());
		jsonBuildingObject.put("heatArea", Tools.formatBigDecimalTo2(data.getTotalHeatArea()));
		jsonBuildingObject.put("area", Tools.formatBigDecimalTo2(data.getTotalArea()));

		jsonBuildingObject.put("avgInWaterTmp", Tools.formatBigDecimalTo2(data.getInWaterTmp()));
		jsonBuildingObject.put("avgOutWaterTmp", Tools.formatBigDecimalTo2(data.getOutWaterTmp()));
		jsonBuildingObject.put("avgHouseTmp", Tools.formatBigDecimalTo2(data.getHouseTmp()));
		jsonBuildingObject.put("outsideTmp", Tools.formatBigDecimalTo2(data.getOutsideTmp()));

		jsonBuildingObject.put("avgFlow", Tools.formatBigDecimalTo2(data.getFlow()));
		jsonBuildingObject.put("avgPower", Tools.formatBigDecimalTo2(data.getPower()));
		jsonBuildingObject.put("heatIndex", Tools.formatBigDecimalTo2(data.getHeatIndex()));
		jsonBuildingObject.put("flowIndex", Tools.formatBigDecimalTo2(data.getFlowIndex()));
		jsonBuildingObject.put("indexCalc", Tools.formatBigDecimalTo2(data.getAdjHeatingIndex()));
		
		return jsonBuildingObject;
	}

	/**
	 * 户图用-用户实时主要数据
	 * 
	 * @param bId
	 * @param interval
	 * @param cId
	 * @return
	 */
	@GetMapping("/getHouseRealTimeData/{buildingId}/{beginTime}/{endTime}")
	public Object getHouseRealTimeData(@PathVariable("buildingId") String buildingId, @PathVariable("beginTime") String beginTime,
			@PathVariable("endTime") String endTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		JSONArray jsonUnitArray = new JSONArray();
		List<Unit> unitList = unitService.getUnitTree(buildingId);

		Map<String, House> houses = houseService.getHouseByUnitIds(unitList);
		List<TypeMst> typeNmes = typeMstService.getTypeMstByTypeKbn("open_status", null, null, null, 1, 10000).getList();

		for (Unit unit : unitList) {
			JSONObject jsonUnitObject = new JSONObject();
			jsonUnitObject.put("unitName", unit.getUnitName());
			params.put("consumerId", unit.getUnitId());
			List<RunningDataTotalDO> runningDataTotalList = runningDataTotalService.list(params);
			

			JSONArray jsonHouseArray = new JSONArray();
			// 每个单元的户数组
			List<HouseDataVO> houseVOs = new ArrayList<>();
			for (RunningDataTotalDO data : runningDataTotalList) {
				JSONObject jsonHouseObject = new JSONObject();
				// 一户的信息
				testData(data);
				House house = houses.remove(data.getConsumerId());
				HouseDataVO houseData = new HouseDataVO();
				houseData.setFromHouse(house, data, typeNmes);
				houseVOs.add(houseData);
				jsonHouseArray.add(jsonHouseObject);
			}
			List<House> lHouse = getHouseListByUnitId(houses, unit.getUnitId());
			for (House house : lHouse) {
				HouseDataVO houseData = new HouseDataVO();
				houseData.setFromHouse(house, null, typeNmes);
				houseVOs.add(houseData);
			}
			Integer floorHouse = 0;	//每层户数
			if (unit.getFloorHouse() != null) {
				floorHouse = unit.getFloorHouse().intValue();
			}
			Integer floorNum = 0;		//楼层数
			if (unit.getFloorNum() != null && !unit.getFloorNum().isEmpty()) {
				floorNum = Integer.parseInt(unit.getFloorNum());
			}
			List<List<HouseDataVO>> lHouseTotal = getHouseVoListByUnitId(houseVOs, floorHouse, floorNum);

			jsonUnitObject.put("houses", lHouseTotal);
			jsonUnitObject.put("unitFloorCnts", lHouseTotal.size());
			jsonUnitArray.add(jsonUnitObject);
		}

		return jsonUnitArray;
	}
	
	/**
	 * 生成房屋结构
	 * @param houseVOs
	 * @param floorHouse
	 * @param floorNum
	 * @return
	 */
	private List<List<HouseDataVO>> getHouseVoListByUnitId(List<HouseDataVO> houseVOs, Integer floorHouse, Integer floorNum) {
		List<List<HouseDataVO>> list = new ArrayList<>();
		Collections.sort(houseVOs, new Comparator<HouseDataVO>() {
      public int compare(HouseDataVO o1, HouseDataVO o2) {
          return o2.getHouseNo().compareTo(o1.getHouseNo());
      }
  });
		int maxfloorhouse = getMaxHouseIndex(houseVOs);
		int minfloorhouse = getMinHouseIndex(houseVOs);	
		if(floorHouse > 0){
			maxfloorhouse = (floorHouse + minfloorhouse -1) > maxfloorhouse?(floorHouse + minfloorhouse -1):maxfloorhouse;
		}
		if(floorHouse <= 0){
			floorHouse = getMaxHouseIndex(houseVOs) -1;
		}
		if(floorHouse <= 0){
			floorHouse = getMaxHouseIndex(houseVOs) -1;
		}
		if(floorNum <= 0){
			floorNum = getMaxHouseFloor(houseVOs) ;
		}
		//从第一层开始拼
		for(int i = 0; i< floorNum; i++){
			List<HouseDataVO> listT = new ArrayList<>();
			for(int j = minfloorhouse ; j <= maxfloorhouse; j++){
				HouseDataVO house = findHousefromListOrder(houseVOs, i+1 , j);
				listT.add(house);
			}
			list.add(listT);
		}
		return list;
	}

	private int getMinHouseIndex(List<HouseDataVO> houseVOs) {
		int min = 9999;
		
		for(HouseDataVO house:houseVOs){
			if(house.getIndex() == null){
				continue;
			}
			if(house.getIndex() < min){
				min = house.getIndex();
			}
		}
		if(min == 0){
			min = 1;
		}
		return min;
	}

	private HouseDataVO findHousefromListOrder(List<HouseDataVO> houseVOs, int i, int j) {
		HouseDataVO house = null;
		for(int k = 0 ; k < houseVOs.size(); k++){
			HouseDataVO house2 = houseVOs.get(k);
			if(house2.getnFloor() == i && house2.getIndex() == j ){
				house = houseVOs.remove(k);
				break;
			}
		}
		if(house == null){
			house = new HouseDataVO();
			house.setFloor(i+"");
			house.setIndex(j);
		}
		return house;
	}

	private int getMaxHouseFloor(List<HouseDataVO> houseVOs) {
		int max = 0;
		for(HouseDataVO house:houseVOs){
			if(house.getnFloor() == null){
				continue;
			}
			if(house.getnFloor() > max){
				max = house.getnFloor();
			}
		}
		if(max == 0){
			max = 1;
		}
		return max;
	}

	private int getMaxHouseIndex(List<HouseDataVO> houseVOs) {
		int max = 0;
		
		for(HouseDataVO house:houseVOs){
			if(house.getIndex() == null){
				continue;
			}
			if(house.getIndex() > max){
				max = house.getIndex();
			}
		}
		if(max == 0){
			max = 1;
		}
		return max;
	}

	/**
	 * 按照生成规则，对这个单元的所有楼进行排序，排序后的楼满足按照一梯户数的排序
	 * @param houses
	 * @param unitId
	 * @return
	 */
	private List<House> getHouseListByUnitId(Map<String, House> houses, String unitId) {
		List<House> lHouse = new ArrayList<>();
		for(String key: houses.keySet()){
			if(key.substring(0, 15).equals(unitId)){
				lHouse.add(houses.get(key));
			}
		}
		Collections.sort(lHouse, new Comparator<House>() {
      public int compare(House o1, House o2) {
          return o2.getName().compareTo(o1.getName());
      }
  });
		return lHouse;
	}

	/**
	 * 数据列表-用户列表
	 * 
	 * @param bId
	 * @param interval
	 * @param cId
	 * @return
	 */
	@GetMapping("/getHouseRealTimeDataList")
	public PageUtils getHouseRealTimeDataList(@RequestParam Map<String, Object> params) {
		//
		// @PathVariable("type") String type, @PathVariable("consumerId") String
		// consumerId,
		// @PathVariable("beginTime") String beginTime, @PathVariable("endTime")
		// String
		// endTime
		// String limit,String offset
    Boolean flag = dataRoleService.getDataRoleItemsType(UserUtils.getUserId(), params);
    params.remove("beginTime");
		convertSortBy(params);
		Query query = new Query(params);

		List<RunningDataTotalDO> runningDataTotalList = runningDataTotalService.houseList(query);
		// 每个单元的户数组
//		JSONArray jsonHouseArray = new JSONArray();
//		for (RunningDataTotalDO data : runningDataTotalList) {
//			JSONObject jsonHouseObject = new JSONObject();
//			// 一户的信息
//			testData(data);
//			jsonHouseObject.put("houseId", data.getConsumerId());
//			if (data.getSystemReadTime() != null){
//				jsonHouseObject.put("updateTime", DateFormatUtils.format(data.getSystemReadTime(), "yyyy-MM-dd HH:mm:ss"));
//			}else{
//				jsonHouseObject.put("updateTime", "2000-01-01 00:00:00");
//			}
//			jsonHouseObject.put("houseAddress", data.getAddress());
//			jsonHouseObject.put("inWaterTmp", Tools.formatBigDecimalTo2(data.getInTemperature()));
//			jsonHouseObject.put("outWaterTmp", Tools.formatBigDecimalTo2(data.getOutTemperature()));
//			if (data.getInTemperature() != null && data.getOutTemperature() != null) {
//				jsonHouseObject.put("tmpDiff",Tools.formatBigDecimalTo2( data.getInTemperature().subtract(data.getOutTemperature())));
//			} else {
//				jsonHouseObject.put("tmpDiff", new BigDecimal(0));
//			}
//			jsonHouseObject.put("flow", Tools.formatBigDecimalTo2(data.getFlowSpeed()));
//			jsonHouseObject.put("power", Tools.formatBigDecimalTo2(data.getPower()));
//			jsonHouseObject.put("houseTmp", Tools.formatBigDecimalTo2(data.getRoomTemperatureRead()));
//			jsonHouseObject.put("totalFlow", Tools.formatBigDecimalTo2(data.getFlow()));
//			jsonHouseObject.put("totalHeat",data.getHeat());
//			jsonHouseObject.put("adjHeatingIndex", data.getAdjHeatingIndex());
//			jsonHouseObject.put("customerServiceFlg", "1");
//			jsonHouseArray.add(jsonHouseObject);
//		}

		// 查询列表数据
		int total = runningDataTotalService.houseListCount(query);
		PageUtils pageUtils = new PageUtils(runningDataTotalList, total);
		return pageUtils;
	}

	private void convertSortBy(Map<String, Object> params) {
		String sortby =(String) params.get("sort");
		if(sortby == null || sortby.isEmpty()){
			return ;
		}
		switch(sortby){
		case "updateTime":
			params.put("sort", "system_read_time");
			break;
		case "houseAddress":
			params.put("sort", "address");
			break;
		case "inWaterTmp":
			params.put("sort", "in_temperature");
			break;
		case "outWaterTmp":
			params.put("sort", "out_temperature");
			break;	
		case "flow":
			params.put("sort", "flow_speed");
			break;	
		case "houseTmp":
			params.put("sort", "room_temperature_read");
			break;	
		case "heatStatusName":
			params.put("sort", "heat_status");
			break;	
		case "openStatusName":
			params.put("sort", "open_status");
			break;	
		case "temperatureDiff":
			params.put("sort", "in_temperature - out_temperature");
			break;
		default:
			//params.remove("sort");
			params.put("sort", CamelCaseUtil.toUnderscoreCase(sortby));
			break;
		}
	}

	private void testData(RunningDataTotalDO model) {

		Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组

		for (int j = 0; j < field.length; j++) { // 遍历所有属性
			String name = field[j].getName(); // 获取属性的名字
			name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
			String type = field[j].getGenericType().toString(); // 获取属性的类型
			if (type.equals("class java.math.BigDecimal")) { // 如果type是类类型，则前面包含"class
																									// ，后面跟类名
				Method mset;
				Method mget;
				try {
					mget = model.getClass().getMethod("get" + name);
					BigDecimal val = (BigDecimal) (mget.invoke(model));
					if (val == null) {
						
						Method method =  model.getClass().getDeclaredMethod("set" + name, new Class[]{BigDecimal.class});
						//mset = model.getClass().getMethod("set" + name);
						method.invoke(model, new Object[]{new BigDecimal(0)}); // 调用getter方法获取属性值
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 户图-弹出曲线-室外温度
	 * 
	 * @param type
	 * @param id
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@GetMapping("/getHouseOutTmp/{type}/{id}/{beginTime}/{endTime}")
	public Object getHouseOutTmp(@PathVariable("type") String type, @PathVariable("id") String id,
			@PathVariable("beginTime") String beginTime, @PathVariable("endTime") String endTime) {
		Company info = companyService.get(id);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("city", info.getCity());
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		List<WeatherDataDO> dataList = dataService.list(params);

		JSONArray jsonHouseArray = new JSONArray();
		for (WeatherDataDO data : dataList) {
			JSONObject jsonHouseObject = new JSONObject();
			// 一户的信息
			jsonHouseObject.put("tmp", data.getTemperature());
			jsonHouseObject.put("time", data.getReadTime());

			jsonHouseArray.add(jsonHouseObject);
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", jsonHouseArray);

		return jsonObject;
	}

	/**
	 * 户图-弹出曲线-单用户运行数据
	 * 
	 * @param type
	 * @param id
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@GetMapping("/getESSingleHouseRunningData/{houseId}/{beginTime}/{endTime}")
	public Object getESSingleHouseRunningData(@PathVariable("houseId") String houseId,
			@PathVariable("beginTime") String beginTime, @PathVariable("endTime") String endTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("consumerId", houseId);
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		params.put("sort", "sysReadTimeLong");
		List<RunningDataTotalDO> runningDataHistoryList = historyDataService.list(params);

		JSONArray jsonHouseArray = new JSONArray();
		for (RunningDataTotalDO data : runningDataHistoryList) {
			JSONObject jsonHouseObject = new JSONObject();
			// 一户的信息
			jsonHouseObject.put("inTmp", data.getRoomTemperatureRead());
			jsonHouseObject.put("outTmp", data.getOutdoorTemperature());
			jsonHouseObject.put("inWaterTmp", data.getInTemperature());
			jsonHouseObject.put("outWaterTmp", data.getOutTemperature());
			jsonHouseObject.put("tmpDiff", data.getInTemperature().subtract(data.getOutTemperature()));
			jsonHouseObject.put("power", data.getPower());
			jsonHouseObject.put("flow", data.getFlowSpeed());
			jsonHouseObject.put("time", DateUtil.toDateTimeString(data.getSystemReadTime().getTime()));
			jsonHouseObject.put("adjHeatingIndex", data.getAdjHeatingIndex());
			jsonHouseObject.put("heatingIndex", data.getHeatingIndex());  
			jsonHouseArray.add(jsonHouseObject);
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", jsonHouseArray);

		return jsonObject;
	}

	/**
	 * 户图-弹出曲线-单用户运行数据es
	 * 
	 * @param type
	 * @param id
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@GetMapping("/getSingleHouseRunningData/{houseId}/{beginTime}/{endTime}")
	public Object getSingleHouseRunningData(@PathVariable("houseId") String houseId, @PathVariable("beginTime") String beginTime,
			@PathVariable("endTime") String endTime) {

		beginTime = DateUtil.toDateTimeString(DateUtil.addHour(DateUtil.getSysdate(), -24));
		endTime = DateUtil.getSysdateTimeString();
		return getESSingleHouseRunningData(houseId, beginTime, endTime);
	}

	/**
	 * 户图、楼图-获取颜色标识数据
	 * 
	 * @param companyId
	 * @return
	 */
	@GetMapping("/markers/{companyId}")
	public Object getMarks(@PathVariable("companyId") String companyId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyId", companyId);
		List<SystemMarkersDO> list = systemMarkersService.list(params);

		return list;
	}

	/**
	 * 保存颜色标识
	 * 
	 * @param params
	 * @return
	 */
	@PostMapping("/markers")
	public R saveMarks(@RequestParam Map<String, Object> params) {
		// {"companyId":"2",field:"power","data":{"field": "power", "content":
		// [{"name":
		// "下下限报警", "color": "blue", "value": "10"}, {}, {}, {}]}}
		SystemMarkersDO marker = new SystemMarkersDO();
		marker.setCompanyId(params.get("companyId").toString());
		marker.setField(params.get("field").toString());
		marker.setData(params.get("data").toString());
		systemMarkersService.save(marker);
		System.out.println(marker.getId());
		return R.ok(marker.getId().toString());
	}

	/**
	 * 
	 * @param params
	 * @return
	 */
	@PutMapping("/markers")
	public R updateMarks(@RequestParam Map<String, Object> params) {
		// {"id":"2","data":{"field": "power", "content": [{"name": "下下限报警",
		// "color":
		// "blue", "value": "10"}, {}, {}, {}]}}
		SystemMarkersDO marker = systemMarkersService.get(Integer.parseInt(params.get("id").toString()));
		marker.setData(params.get("data").toString());

		systemMarkersService.update(marker);
		return R.ok();
	}
	
  @RequestMapping(value="/getHouseRealTimeDataListToExcel",method = RequestMethod.POST)
	public Root getHouseRealTimeDataListToExcel(@RequestBody Map<String, Object> params, ServletRequest request) {
		Root root = new Root();
		Boolean flag = dataRoleService.getDataRoleItemsType(UserUtils.getUserId(), params);
		params.remove("beginTime");
		params.put("offset", "0");
		params.put("limit", "50000");
		convertSortBy(params);
		Query query = new Query(params);

		List<RunningDataTotalDO> runningDataTotalList = runningDataTotalService.houseList(query);

		root.setData(runningDataTotalService.getHouseRealTimeDataListToExcel(runningDataTotalList, request));
		return root;
	}
}

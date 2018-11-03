package cn.plou.web.heatManage.history.service.impl;

import cn.plou.common.utils.DateUtil;
import cn.plou.common.utils.StringUtil;
import cn.plou.component.es.ESTool;
import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.config.common.Cond;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.CommonServiceImp;
import cn.plou.web.common.utils.RedisUtil;
import cn.plou.web.common.utils.Support;
import cn.plou.web.common.utils.Tools;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.common.utils.a1.NumUtil;
import cn.plou.web.heatManage.history.dao.HistoryDataDao;
import cn.plou.web.heatManage.history.domain.BaseHistoryData;
import cn.plou.web.heatManage.history.domain.HeatMeterDataDO;
import cn.plou.web.heatManage.history.domain.WkfDataDO;
import cn.plou.web.heatManage.history.domain.WkqDataDO;
import cn.plou.web.heatManage.history.service.HistoryDataService;
import cn.plou.web.heatManage.monitor.domain.RunningDataTotalDO;
import cn.plou.web.system.baseMessage.build.service.BuildService;
import cn.plou.web.system.baseMessage.build.vo.BuildInfo;
import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import cn.plou.web.system.baseMessage.commuity.service.CommuityService;
import cn.plou.web.system.baseMessage.company.service.CompanyService;
import cn.plou.web.system.baseMessage.house.entity.House;
import cn.plou.web.system.baseMessage.house.service.HouseService;
import cn.plou.web.system.baseMessage.house.vo.HouseInfo;
import cn.plou.web.system.baseMessage.house.vo.HouseVo;
import cn.plou.web.system.baseMessage.unit.entity.Unit;
import cn.plou.web.system.baseMessage.unit.service.UnitService;
import cn.plou.web.system.commonMessage.pageGrid.service.PageGridService;
import cn.plou.web.system.commonMessage.pageGrid.vo.PageGridInfo;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.TypeMstService;
import cn.plou.web.system.meterMessage.meter.service.MeterService;
import cn.plou.web.system.meterMessage.meter.vo.MeterInfo;
import cn.plou.web.system.permission.role.service.DataRoleService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
@Slf4j
public class HistoryDataServiceImpl implements HistoryDataService {

	@Autowired
	private HistoryDataDao historyDataDao;
	@Autowired
	private TypeMstService typeMstService;
	@Autowired
	private PageGridService pageGridService;
	@Autowired
	private DataRoleService dataRoleService;
	@Autowired
	private CommonServiceImp commonService;

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
	private MeterService meterService;
	@Autowired
	private ESTool esTool;

	
	private String esCondi = "meterId";
	private String sqlCondi = "meter_id";
	/**
	 * 根据条件查找房屋信息
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public Map<String, HouseInfo> houseInfo(Map<String, Object> map, List<String> consumerIdList) {
		// String beginTime,String endTime,String heatStatus,String type,String
		// id，String useHeatStatus,String houseType
		String companyId = null;
		String stationId = null;
		String commuityId = null;
		String buildingNo = null;
		String unitId = null;
		String type = "";
		List<String> list = null;
		// 组织相关字段
		type = getMapTypeVal(map);
		HouseVo houseVo = new HouseVo();
		
		
		if (type.equals("company")) {
			companyId = getMapValue(map, "id");
			map.put("companyId", companyId);
		} else if (type.equals("station")) {
			stationId = getMapValue(map, "id");
			map.put("stationId", stationId);
		} else if (type.equals("commuity")) {
			commuityId = getMapValue(map, "id");
			map.put("commuityId", commuityId);
		} else if (type.equals("building")) {
			buildingNo = getMapValue(map, "id");
			map.put("buildingNo", buildingNo);
		} else if (type.equals("unit")) {
			unitId = getMapValue(map, "id");
			map.put("unitId", unitId);
		} else if (type.equals("house")) {
			String strids = getMapValue(map, "id");
			list = Arrays.asList(strids.split(","));
			if (list.size() > 0) {
				houseVo.setConsumerIds(list);
			}
		}
		if (consumerIdList != null && consumerIdList.size() > 0) {
			String str = Tools.genorInByList(consumerIdList);
			map.put("rowno", str);
			houseVo.setRownos(consumerIdList);
		}
		
		String order = null;
		String sortby = null;
		if (map.get("order") != null && map.get("sort") != null) {
			order = map.get("order").toString();
			sortby = map.get("sort").toString();
		}
		
		if (map.get("heatingStatus") != null) {
			houseVo.setHeatingStatus(map.get("heatingStatus").toString());
		}
		if (map.get("houseType") != null) {
			houseVo.setHouseType(map.get("houseType").toString());
		}
		List<String> companyIds = new ArrayList<String>();
		if (companyId != null && !companyId.isEmpty()) {
			companyIds.add(companyId);
		}
		List<String> stationIds = new ArrayList<String>();
		if (stationId != null && !stationId.isEmpty()) {
			stationIds.add(stationId);
		}
		List<String> commuityIds = new ArrayList<String>();
		if (commuityId != null && !commuityId.isEmpty()) {
			commuityIds.add(commuityId);
		}
		// List<HouseInfo> houses = houseMapper.selectAllHouse(companyIds,
		// stationIds, commuityIds, buildingNo, unitId, houseVo, order,
		// sortby);
		List<HouseInfo> houses = historyDataDao.selectAllHouse(companyIds, stationIds, commuityIds, buildingNo, unitId, houseVo,
				order, sortby);
		Map<String, HouseInfo> mapHouses = mapHouses(houses);
		return mapHouses;
	}

	private String getMapTypeVal(Map<String, Object> map) {
		String type = getMapValue(map, "type");
		if (type == null) {
			type = "house";
			map.put("type", type);
			String strid = getMapValue(map, "id");
			if (strid == null) {
				String id = getMapValue(map, "consumerId");
				map.put("id", id);
			}
		}
		return type;
	}

	private void setRootCond(Root root, Map<String, Object> map, Integer total) {
		String page = getMapValue(map, "offset");
		String pageSize = getMapValue(map, "limit");
		String order = getMapValue(map, "order");
		String sortby = getMapValue(map, "sort");
		root.setCond(Cond.getCond(Integer.getInteger(page), Integer.getInteger(pageSize), total, (String) order, (String) sortby));
	}

	/**
	 * 查找列表历史数据
	 */
	@Override
	public Root getMeterHisData(Map<String, Object> map) {
		List<HeatMeterDataDO> datasT = new ArrayList<>();
		return getMeterHisDataList(map, datasT);
	}
	@Override
	public Root getWkfHisData(Map<String, Object> map) {
		List<WkfDataDO> datasT = new ArrayList<>();
		return getWkfHisDataList(map, datasT);
	}
	@Override
	public Root getHouseWkqHisData(Map<String, Object> map) {
		List<WkqDataDO> datasT = new ArrayList<>();
		return getHouseWkqHisDataList(map, datasT);
	}
	
	@Override
	public Root getHouseTotalData(Map<String, Object> map) {
		List<RunningDataTotalDO> datasT = new ArrayList<>();
		return getHouseTotalDataList(map, datasT);
	}


	private Map<String,HouseInfo> mapHouses(List<HouseInfo> houses) {
		Map<String,HouseInfo> map = new HashMap<String,HouseInfo>();
		for(HouseInfo info:houses){
			map.put(info.getConsumerId(), info);
		}
		return map;
	}



	/**
	 * 函数有效性
	 * @param map
	 * @return
	 */
	Root viladHisParam(Map<String, Object> map) {
		Root root = new Root();
		if (map.get("type") == null || map.get("id") == null || map.get("type").toString().isEmpty() || map.get("id").toString().isEmpty()) {
			// || map.get("order")== null || map.get("pageSize")== null ||
			// map.get("sortby") == null ||map.get("page")== null){
			root.setMsg("必要参数缺失");
			root.setCode(1);
			return root;
		}
		String readTime = getMapValue(map, "readTime");
		if(readTime != null){
			String[] times = readTime.split(",");
			if(times.length != 2){
				root.setMsg("必要参数缺失");
				root.setCode(1);
				return root;
			}
		}
//		else
//		{
//			root.setMsg("请选择查询日期！");
//			root.setCode(1);
//			return root;
//		}
		String page = getMapValue(map, "offset");
		String pageSize = getMapValue(map, "limit");
//		if(page !=  null && pageSize != null){		
//			if(Integer.parseInt(page)+Integer.parseInt(pageSize) > 50000){
//				root.setMsg("查询数据量过大，请缩小查询范围！");
//				root.setCode(1);				
//			}
//		}
		return root;
	}

//	private String condiByType(String meterType, Map<String, Object> map) {
//		String condi = null;
//		// 组织相关字段
//		String type = "";
//		String condWord = esCondi; // todo rowno
//		if (meterType.isEmpty()) {
//			condWord = "consumerId";
//		}
//		// String condWord = "meterId"; // todo rowno
//		List<String> list = null;
//		type = getMapTypeVal(map);
//		if (type.equals("house")) {
//			list = Arrays.asList(map.get("id").toString().split(","));
//			String str = Tools.genorInByList(list);
//			if (!str.isEmpty()) {
//				condi = condWord + " in (" + str + ")";
//			}
//		} else if (type.equals("unit") || type.equals("build") || type.equals("building")){
//				condi = condWord + " like '" + meterType + map.get("id").toString() + "%'";
//		}
//		return condi;
//	}
	
	private String condiByType(String meterType, Map<String, Object> map) {
		String condi = "";
		String type = "";

		type = getMapTypeVal(map);
		String id="";
		if(map.get("id") == null){
			id = map.get("consumerId").toString();
		}else{
		  id=map.get("id").toString();
		}
		if(type.equals("build") || type.equals("building"))
		{
			condi=condi+" and buildingNo='"+id+"'" ;
			}
		if(type.equals("unit"))
		{
			condi=condi+" and unitId='"+id+"'" ;
		}
		if(type.equals("house"))
		{
			condi=condi+" and consumerId='"+id+"'" ;
		}
		if (StringUtil.hasValue(meterType))
		{
			condi=condi+" and meterId like '"+meterType+id+"" + "%'";
		}
		return condi;
	}

	private List<String> getAreasByStationId(String string) {
		Map<String, Object> map = new HashMap<>();
		map.put("stationId", string);
		return historyDataDao.getAreasByStationId(map);
	}

	private String getMapValue(Map<String, Object> map, String mapName) {
		return Tools.getMapValue(map, mapName);
	}

	private String filterHeatingStatue(String strsql, Map<String, Object> map) {
		String heatingStatue = getMapValue(map, "heatingStatus");
		if (heatingStatue != null && !heatingStatue.isEmpty()) {
			strsql += " and heatingStatue ='" + heatingStatue + "'";
		}
		return strsql;
	}

	private String filterCondi(String strsql, Map<String, Object> map, String meterType) {
		String condi = condiByType(meterType, map);
		if (condi != null && !condi.isEmpty()) {
//			strsql += " and " + condi;
			strsql += condi;
		}	
		//权限控制
		strsql = filterRoles(strsql, map);
		return strsql;
	}

	private String filterRoles(String sql, Map<String, Object> map) {
		List<String> companyIds = null;
		List<String> commuityIds = null;
		if (map.get("companyIds") != null) {
			companyIds = (ArrayList<String>) (map.get("companyIds"));
			if (companyIds.size() > 0) {
				String str = "and  companyId in (";
				for (int i = 0; i < companyIds.size(); i++) {
					str = str + "'" + companyIds.get(i) + "',";
			}
				str = str.substring(0, str.length() - 1);
				str += ") ";
				sql += str;
		}
			}
		if (map.get("commuityIds") != null) {
			commuityIds = (ArrayList<String>) (map.get("commuityIds"));
			if (commuityIds.size() > 0) {
				String str = "and  commuityId in (";
				for (int i = 0; i < commuityIds.size(); i++) {
					str = str + "'" + commuityIds.get(i) + "',";
		}
				str = str.substring(0, str.length() - 1);
			str += ") ";
			sql += str;
		}
		}
		return sql;
	}
	
//	private String filterRoles(String sql, Map<String, Object> map) {
//		List<String> roles = new ArrayList<>();
//		List<String> companyIds = null;
//		if(map.get("companyIds") != null){
//			companyIds = (ArrayList<String>)(map.get("companyIds"));
//			for(String str: companyIds){
//				roles.add("consumerId like '" + str + "%'");
//			}
//		}
//		List<String> commuityIds = null;
//		if(map.get("commuityIds") != null){
//			commuityIds = (ArrayList<String>)(map.get("commuityIds"));
//			for(String str: commuityIds){
//				roles.add("consumerId like '" + str + "%'");
//			}
//		}
//		if(roles.size() > 0){
//			String str = "and (" + roles.get(0);
//			for(int i = 1; i < roles.size(); i++){
//				str =str + " or " + roles.get(i);
//			}
//			str += ") ";
//			sql += str;
//		}
//		return sql;
//	}

	public String filterMeterVal(String strsql, Map<String, Object> map) {
		String inTemperature = getMapValue(map, "inWaterTmp");
		String outTemperature = getMapValue(map, "outWaterTmp");
		String tmpDiff = getMapValue(map, "tmpDiff");

		String inFlow = getMapValue(map, "flow");
		String flowSpeed = getMapValue(map, "flowSpeed");
		String power = getMapValue(map, "power");
		String heat = getMapValue(map, "heat");

		strsql += SetMysqlRange("inTemperature", inTemperature);
		strsql += SetMysqlRange("outTemperature", outTemperature);
		strsql += SetMysqlRange("temperature", tmpDiff);
		strsql += SetMysqlRange("inFlow", inFlow);
		strsql += SetMysqlRange("flowSpeed", flowSpeed);
		strsql += SetMysqlRange("power", power);
		strsql += SetMysqlRange("heat", heat);
		return strsql;
	}
	
	
	public String filterValveVal(String strsql, Map<String, Object> map) {

		String setTemperature = getMapValue(map, "setTemperature");
		String roomTemperature = getMapValue(map, "roomTemperature");
		String flowingIndex = getMapValue(map, "flowingIndex");

		String flowUpperRatio = getMapValue(map, "flowUpperRatio");
		String flowUpperValue = getMapValue(map, "flowUpperValue");
		String controlCommond = getMapValue(map, "controlCommond");
		String correctCoefficient = getMapValue(map, "correctCoefficient");
		String flowRate = getMapValue(map, "flowRate");
		String flowNow = getMapValue(map, "flowNow");

		strsql += SetMysqlRange("setTemperature", setTemperature);
		strsql += SetMysqlRange("roomTemperature", roomTemperature);
		strsql += SetMysqlRange("flowingIndex", flowingIndex);
		strsql += SetMysqlRange("flowUpperRatio", flowUpperRatio);
		strsql += SetMysqlRange("flowUpperValue", flowUpperValue);
		strsql += SetMysqlEqual("controlCommond", controlCommond);
		strsql += SetMysqlRange("correctCoefficient", correctCoefficient);
		strsql += SetMysqlRange("flowRate", flowRate);
		strsql += SetMysqlRange("flowNow", flowNow);
		return strsql;
		
	}
	public String filterRoomVal(String strsql, Map<String, Object> map) {

		String signal = getMapValue(map, "signal");
		String elec = getMapValue(map, "elec");
		String roomTemperature = getMapValue(map, "roomTemperature");
		
		strsql += SetMysqlRange("signal", signal);
		strsql += SetMysqlRange("elec", elec);
		strsql += SetMysqlRange("roomTemperature", roomTemperature);
		return strsql;
	}
	
	private String filterHouseVal(String strsql, Map<String, Object> map) {
		// TODO 自动生成的方法存根
		String power = getMapValue(map, "power");
		String flowSpeed = getMapValue(map, "flowSpeed");
		String inTemperature = getMapValue(map, "inTemperature");
		String outTemperature = getMapValue(map, "outTemperature");
		String roomTemperature = getMapValue(map, "roomTemperature");
		String heatingIndex = getMapValue(map, "heatingIndex");
		String flowingIndex = getMapValue(map, "flowingIndex");
		String adjHeatingIndex = getMapValue(map, "adjHeatingIndex");
		
		strsql += SetMysqlRange("power", power);
		strsql += SetMysqlRange("flowSpeed", flowSpeed);
		strsql += SetMysqlRange("inTemperature", inTemperature);
		strsql += SetMysqlRange("outTemperature", outTemperature);
		strsql += SetMysqlRange("roomTemperature", roomTemperature);
		strsql += SetMysqlRange("heatingIndex", heatingIndex);
		strsql += SetMysqlRange("flowingIndex", flowingIndex);
		strsql += SetMysqlRange("adjHeatingIndex", adjHeatingIndex);
		return strsql;
	}
	
	
	public List<HeatMeterDataDO> esMeterAll(Map<String, Object> map) {
		return (List)esMeter(map, null, false);
	}
		
	public List<HeatMeterDataDO> esMeterChar(Map<String, Object> map) {
		return esMeterC(map, null, false);
	}
	public List<WkfDataDO> esWkfDataAll(Map<String, Object> map) {
		return esWkfData(map, null, false);
	}
	/**
	 * 从es中获取表数据
	 * 
	 * @param map
	 * @return
	 */
	private List<HeatMeterDataDO> esMeterC(Map<String, Object> map, Root root, Boolean isLimit) {
		List<HeatMeterDataDO> data = new ArrayList<HeatMeterDataDO>();
		if (isLimit) {
			Integer count = esMeterCount(map, root);
			if (count == 0) {
				return data;
			}
		} 

		try {
			String meterId = map.get("id").toString();
			meterId="01"+meterId.substring(2, meterId.length());
			 
			String strsql = "SELECT  flowSpeed, outTemperature,sysReadTime FROM heat_meter_data "
					+  " where rowno='"+meterId+ "' order by sysReadTimeLong desc limit 0,32 "; 
			
			ResultSet res = Support.querryFromEs(strsql);
			if (res == null) {
				return data;
			}
			while (res.next()) {
				HeatMeterDataDO dat = orderHeatMeterData(res);
				if (dat != null) {
					data.add(dat);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace() ;
			System.out.println("esmeter error");
		}
		return data;
	}
/**
	 * 从es中获取表数据
	 * 
	 * @param map
	 * @return
	 */
	private List<BaseHistoryData> esMeter(Map<String, Object> map, Root root, Boolean isLimit) {
		List<BaseHistoryData> data = new ArrayList<BaseHistoryData>();
		if (isLimit) {
			Integer count = esMeterCount(map, root);
			if (count == 0) {
				return data;
			}
		}

		String meterType = "";
		String strsql = "select * from heat_meter_data where  sysReadTimeLong >= %s and sysReadTimeLong <=%s ";

		strsql = filterTime(strsql, map);
		strsql = filterHeatingStatue(strsql, map);
		strsql = filterCondi(strsql, map, meterType);
		strsql = filterMeterVal(strsql, map);
		strsql = filterSortAndLimit(strsql, map);
		System.out.println(strsql);
		try {
			ResultSet res = Support.querryFromEs(strsql);
			System.out.println("-----------------------------res:"+res);
			if (res == null) {
				return data;
			}
			while (res.next()) {
				HeatMeterDataDO dat = orderHeatMeterData(res);
        System.out.println("-----------------------------dat:"+dat);
				if (dat != null) {
					data.add(dat);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("esmeter error");
		}
		return data;
	}
	
	private List<BaseHistoryData> esWkfDataList(Map<String, Object> map, Root dataCount,Boolean isLimit) {
		List<BaseHistoryData> data = new ArrayList<BaseHistoryData>();
		if (isLimit) {
			Integer count = esWkfCount(map, dataCount);
			if (count == 0) {
				return data;
			}
		}
		
		String meterType = "";
		String strsql = "select * from valve_data where  sysReadTimeLong >= %s and sysReadTimeLong <=%s ";
		strsql = filterTime(strsql, map);
		strsql = filterCondi(strsql, map, meterType);
		strsql = filterValveVal(strsql, map);
		strsql = filterSortAndLimit(strsql, map);
		System.out.println(strsql);
		try {
		
			ResultSet res = Support.querryFromEs(strsql);
			if (res == null) {
				return data;
			}
			while (res.next()) {
				WkfDataDO dat = orderWkfData(res);
				if (dat != null) {
					data.add(dat);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace() ;
			System.out.println("esmeter error");
		}
		return data;
	}
	
	private List<BaseHistoryData> esWkqData(Map<String, Object> map, Root dataCount) {
		List<BaseHistoryData> datas = new ArrayList<BaseHistoryData>();
		Integer count = esWkqCount(map, dataCount);
		if (count == 0) {
			return datas;
		}
		String meterType = "";
		String tableName = "room_temperature_data";
		String strsql = "select * from " + tableName + " where  sysReadTimeLong >= %s and sysReadTimeLong <=%s ";

		strsql = filterTime(strsql, map);
		strsql = filterCondi(strsql, map, meterType);
		strsql = filterRoomVal(strsql, map);
		strsql = filterSortAndLimit(strsql, map);

		try {
			ResultSet res = Support.querryFromEs(strsql);
			if (res == null) {
				return datas;
			}
			while (res.next()) {
				WkqDataDO dat = orderWkqData(res);
				if (dat != null) {
					datas.add(dat);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return datas;
	}
	
	
	/**
	 * @param map
	 * @param dataCount
	 * @return
	 */
	private List<BaseHistoryData> esHouseTotalData(Map<String, Object> map, Root dataCount, Boolean isLimit) {
		List<BaseHistoryData> datas = new ArrayList<BaseHistoryData>();

		if (isLimit) {
			Integer count = esHouseTotalDataCount(map, dataCount);
			if (count == 0) {
				return datas;
			}
		}

		String tableName = "user_running_data_history";
		String strsql = "select * from " + tableName + " where  sysReadTimeLong >= %s and sysReadTimeLong <=%s ";

		strsql = filterTime(strsql, map);
		strsql = filterCondi(strsql, map, "");
		strsql = filterHouseVal(strsql, map);
		strsql = filterSortAndLimit(strsql, map);

		try {
			ResultSet res = Support.querryFromEs(strsql);
			if (res == null) {
				return datas;
			}
			while (res.next()) {
				try {
					RunningDataTotalDO dat = orderHouseTotalData(res);
					if (dat != null) {
						datas.add(dat);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return datas;
	}
	
	
	/**
	 * @param map
	 * @param dataCount
	 * @return
	 */
	private List<RunningDataTotalDO> esHouseTotalData2(Map<String, Object> map, Root dataCount, Boolean isLimit) {
		List<RunningDataTotalDO> datas = new ArrayList<RunningDataTotalDO>();
		
		if (isLimit) {
			Integer count = esHouseTotalDataCount(map, dataCount);
			if (count == 0) {
				return datas;
			}
		}

		String tableName = "user_running_data_history";
		String strsql = "select * from " + tableName + " where  sysReadTimeLong >= %s and sysReadTimeLong <=%s ";

		strsql = filterTime(strsql, map);
		strsql = filterCondi(strsql, map, "");
		strsql = filterHouseVal(strsql, map);
		strsql = filterSortAndLimit(strsql, map);

    try {
			ResultSet res = Support.querryFromEs(strsql);
			if (res == null) {
				return datas;
			}
			while (res.next()) {
				try {
					RunningDataTotalDO dat = orderHouseTotalData(res);
					if (dat != null) {
						datas.add(dat);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return datas;
	}

	
	private String filterTime(String strsql, Map<String, Object> map) {
		String readTime = getMapValue(map, "readTime");
		String beginTime = getMapValue(map, "beginTime");
		String endTime = getMapValue(map, "endTime");
		if (StringUtil.isNullOrEmpty(beginTime) || StringUtil.isNullOrEmpty(endTime)) {
			if (readTime == null) {
//				Date date1 = new Date(((System.currentTimeMillis() / 1000) - 86400) * 1000);
//				Date date2 = new Date(((System.currentTimeMillis() / 1000)) * 1000);
//				endTime = DateUtil.toDateTimeString(date2);
//				beginTime = DateUtil.toDateTimeString(date1);
				Date date = new Date();
				beginTime = DateUtil.toDateString(date)+" 00:00:00";
				endTime=DateUtil.toDateString(date)+" 23:59:59";
			} else {
				String[] times = readTime.split(",");
				beginTime = times[0];
				endTime = times[1];
			}
		}
		strsql = String.format(strsql, Long.toString(DateUtil.fromString(beginTime,true).getTime()),
				Long.toString(DateUtil.fromString(endTime,true).getTime()));
		return strsql;
	}

	private String filterSortAndLimit(String strsql, Map<String, Object> map) {
		String strSortby = getMapValue(map, "sort");
		String page = getMapValue(map, "offset");
		String pageSize = getMapValue(map, "limit");
		String order = getMapValue(map, "order");
		if (strSortby != null && !strSortby.isEmpty()) { 
			strsql += " order by " + strSortby ;
			if(StringUtil.hasValue(order)){
				strsql += " " + order;
			}
//			strsql +=",consumerId";
		}
		else
		{
//			strsql += " order by consumerId,sysReadTime" ;
			strsql += " order by consumerId" ;
		}

		if (page != null && !page.isEmpty()) {
			//Integer bPage = (Integer.parseInt(page))*Integer.parseInt(pageSize);
			strsql += " limit " + page + "," + pageSize;
		}
		return strsql;
	}




	
	private List<TypeMst> typeMstByType(String string) {
		return typeMstService.getTypeMstByTypeKbn(string, null, null, null, 1, 10000).getList();
	}


	private List<WkfDataDO> esWkfData(Map<String, Object> map, Root dataCount,Boolean isLimit) {
		List<WkfDataDO> data = new ArrayList<WkfDataDO>();
		if (isLimit) {
			Integer count = esWkfCount(map, dataCount);
			if (count == 0) {
				return data;
			}
		}
		try {
			String meterId = map.get("id").toString();
			String  table= "valve_data";
			String meterType = "02";
			MeterInfo mtif= meterService.getMeterById(meterId) ;  
			if(null!=mtif)
			{
			if(null!=mtif.getProtocol())
			{
				if("02511".equals(mtif.getProtocol()))
				{
					table= "room_temperature_data";
					 meterType = "0D";
				}
			}
			}   
			meterId=meterType+meterId.substring(2, meterId.length()); 
			String strsql = "SELECT   roomTemperature,sysReadTime FROM "+table 
					+  " where rowno='"+meterId+ "' order by sysReadTimeLong desc limit 0,32 "; 
			
			ResultSet res = Support.querryFromEs(strsql);
			if (res == null) {
				return data;
			}
			while (res.next()) {
				WkfDataDO dat = orderWkfData(res);
				if (dat != null) {
					data.add(dat);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace() ;
			System.out.println("esmeter error");
		}
		return data;
	}

	

	
	@Override
	public List<RunningDataTotalDO> esHouseTotalDataAll(Map<String, Object> map) {
		return esHouseTotalData2(map, null, false);
	}
	


	private List<RunningDataTotalDO> esHouseTotalDataTest(Map<String, Object> map, Root dataCount) {
		dataCount.setData(100);
		List<RunningDataTotalDO> data = new ArrayList<RunningDataTotalDO>();
		String pageSize = getMapValue(map, "limit");
		if (pageSize == null) {
			return data;
		}
		Integer nPage = Integer.parseInt(pageSize);
		for (int i = 0; i < nPage; i++) {
			RunningDataTotalDO dat = new RunningDataTotalDO();
			dat.setAddress("测试假数据10" + i);
			dat.setCompanyId("00101");
			dat.setFlow(new BigDecimal((1.6 + i / 10.0) + ""));
			dat.setHeat((1232.6 + i / 10.0) + "");
			data.add(dat);
		}
		return data;
	}



	/**
	 * 计算es中的count -通用函数
	 * 
	 * @param strsql
	 * @param dataCount
	 * @return count值
	 */
	private Integer esCount(String strsql, Root dataCount) {
		Integer count = 0;
		try {
				ResultSet resCount = Support.querryFromEs(strsql);
				if (resCount != null) {
					if (resCount.next()) {
						Double contd = resCount.getDouble("count");
						count = (int) (contd.intValue());
					}
				}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("esCount error");
		}
		dataCount.setData(count);
		return count;
	}


	/**
	 * 热表count
	 * 
	 * @param map
	 * @param dataCount
	 * @return
	 */
	private Integer esMeterCount(Map<String, Object> map, Root dataCount) {
		String meterType = "";
		String strsql = "select count(*) as count from heat_meter_data where  sysReadTimeLong >= %s and sysReadTimeLong <=%s ";

		strsql = filterTime(strsql, map);
		strsql = filterHeatingStatue(strsql, map);
		strsql = filterCondi(strsql, map, meterType);
		strsql = filterMeterVal(strsql, map);
		return esCount(strsql, dataCount);
	}

	/**
	 * 温控阀count
	 * 
	 * @param map
	 * @param dataCount
	 * @return
	 */
	private Integer esWkfCount(Map<String, Object> map, Root dataCount) {
		String meterType = "";
		String tableName = "valve_data";
		String strsql = "select count(*) as count from " + tableName + " where  sysReadTimeLong >= %s and sysReadTimeLong <=%s ";

		strsql = filterTime(strsql, map);
		strsql = filterCondi(strsql, map, meterType);
		strsql = filterValveVal(strsql, map);
		return esCount(strsql, dataCount);
	}
	/**
	 * 温控器count
	 * 
	 * @param map
	 * @param dataCount
	 * @return
	 */
	private Integer esWkqCount(Map<String, Object> map, Root dataCount) {
		String meterType = "";
		String tableName = "room_temperature_data";
		String strsql = "select count(*) as count from " + tableName + " where  sysReadTimeLong >= %s and sysReadTimeLong <=%s ";

		strsql = filterTime(strsql, map);
		strsql = filterCondi(strsql, map, meterType);
		strsql = filterRoomVal(strsql, map);
		return esCount(strsql, dataCount);
	}
	

	/**
	 * es用户汇总数据数量
	 * 
	 * @param map
	 * @param dataCount
	 * @return
	 */
	private Integer esHouseTotalDataCount(Map<String, Object> map, Root dataCount) {
		String tableName = "user_running_data_history";
		String strsqlCount = "select count(*) as count from " + tableName
				+ " where  sysReadTimeLong >= %s and sysReadTimeLong <=%s ";

		strsqlCount = filterTime(strsqlCount, map);
		strsqlCount = filterCondi(strsqlCount, map, "");
		strsqlCount = filterHouseVal(strsqlCount, map);
		return esCount(strsqlCount, dataCount);
	}



	/**
	 * 热表数据
	 * 
	 * @param res
	 * @return
	 */
	private HeatMeterDataDO orderHeatMeterData(ResultSet res) {
		HeatMeterDataDO dat = null;
		try {
			dat = new HeatMeterDataDO();
			dat.setAllTime(getResultString(res, "allTime"));
			dat.setRowno(getResultString(res, "rowno"));
			dat.setAllWorkTime(getResultString(res, "allWorkTime"));
			// dat.setAreas(getResultString(res,"area")); //没这个字段
			dat.setCompanyId(getResultString(res, "companyId"));
			dat.setCool(getResultBigDecimal(res, "cool"));
			dat.setFlowSpeed(getResultBigDecimal(res, "flowSpeed"));
			dat.setHeat(getResultBigDecimal(res, "heat"));
			dat.setInFlow(getResultBigDecimal(res, "inFlow"));
			dat.setInTemperature(getResultBigDecimal(res, "inTemperature"));
			dat.setAddress2nd(getResultString(res, "address2nd"));
			dat.setMeterId(getResultString(res, "meterId"));
			dat.setMeterTime(getResultString(res, "meterTime"));
			dat.setCompanyId(getResultString(res, "companyId"));
			dat.setOutFlow(getResultBigDecimal(res, "outFlow"));
			dat.setOutTemperature(getResultBigDecimal(res, "outTemperature"));
			dat.setPower(getResultBigDecimal(res, "power"));
			dat.setRunningState(getResultString(res, "runningState"));
			dat.setSysReadTime(getResultString(res, "sysReadTime"));
			dat.setConsumerId(getResultString(res, "consumerId"));
			dat.setTmpDiff(getResultBigDecimal(res, "temperature"));
//			if (dat.getInTemperature() != null && dat.getOutTemperature() != null)
//				dat.setTmpDiff(dat.getInTemperature().subtract(dat.getOutTemperature()));
		} catch (Exception e) {
			log.error(e.getMessage());
			System.out.println("esdata meter change error");
		}
		return dat;
	}

	private String getResultString(ResultSet res, String index) {
		try {
			if (res.getObject(index) != null) {
				return res.getString(index);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			System.out.println("没找到" + index);
			//e.printStackTrace();

		}
		return "";
	}
	private Long getResultLong(ResultSet res, String index) {
		try {
			if (res.getObject(index) != null) {
				return res.getLong(index);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			System.out.println("没找到" + index);
			//e.printStackTrace();

		}
		return 0L;
	}
	private Double getResultDouble(ResultSet res, String index) {
		try {
			if (res.getObject(index) != null) {
				Double val = res.getDouble(index);
				BigDecimal bg = new BigDecimal(val);
				Double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				return f1;
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			//e.printStackTrace();
			System.out.println("没找到" + index);
		}
		return 0d;
	}

	private BigDecimal getResultBigDecimal(ResultSet res, String index) {
		return new BigDecimal(getResultDouble(res, index) + "");
	}

	/**
	 * 用户用热数据
	 * 
	 * @param res
	 * @return
	 */
	private RunningDataTotalDO orderHouseTotalData(ResultSet res) {
		RunningDataTotalDO dat = null;
		try {
			dat = new RunningDataTotalDO();
			// dat.setMeterId(res.getString("meterId"));
			dat.setConsumerId(getResultString(res, "consumerId"));
			//dat.setActionLimit(getResultString(res, "actionLimit"));
			dat.setAddress(getResultString(res, "address"));
			dat.setCompanyId(getResultString(res, "companyId"));
			dat.setControlRole(getResultString(res, "controlRole"));
			dat.setFlow(getResultBigDecimal(res, "flow"));
			dat.setFlowingIndex(getResultBigDecimal(res, "flowingIndex"));

			dat.setFlowSpeed(getResultBigDecimal(res, "flowSpeed"));
			dat.setFlowUpperLimit(getResultBigDecimal(res, "flowUpperLimit"));
			dat.setHeat(getResultDouble(res, "heat") + "");
			dat.setHeatingArea(getResultBigDecimal(res, "heatingArea"));
			dat.setHeatingIndex(getResultBigDecimal(res, "heatingIndex"));
			dat.setHeatStatus(getResultString(res, "heatStatus"));
			dat.setInTemperature(getResultBigDecimal(res, "inTemperature"));
			dat.setOutTemperature(getResultBigDecimal(res, "outTemperature"));
			dat.setOpenness(getResultBigDecimal(res, "openness"));
			dat.setOpenStatus(getResultString(res, "openStatus"));
			dat.setOutdoorTemperature(getResultBigDecimal(res, "outdoorTemperature"));
			//dat.setOutTemperature(getResultBigDecimal(res, "outTemperature"));
			dat.setPower(getResultBigDecimal(res, "power"));
			dat.setRegulationMode(getResultString(res, "regulationMode"));
			dat.setRoomTemperatureRead(getResultBigDecimal(res, "roomTemperatureRead"));
			dat.setRoomTemperatureSet(getResultBigDecimal(res, "roomTemperatureSet"));
			Long longTime = getResultLong(res, "sysReadTimeLong");
			dat.setSystemReadTime(new Date(longTime));
			dat.setStepTotal(getResultBigDecimal(res, "stepTotal"));
			dat.setExecutionStep(getResultBigDecimal(res, "executionStep"));
		} catch (Exception e) {
			System.out.println(cn.plou.common.utils.Tools.getExceptionAllinformation(e));
		}
		return dat;
	}

	/**
	 * 温控阀数据
	 * 
	 * @param res
	 * @return
	 */
	private WkfDataDO orderWkfData(ResultSet res) {
		WkfDataDO dat = null;
		try {
			dat = new WkfDataDO();
			dat.setPrimaryId(getResultString(res, "primaryId"));
			dat.setMeterId(getResultString(res, "meterId"));
			dat.setRowno(getResultString(res, "rowno"));
			dat.setConsumerId(getResultString(res, "consumerId"));
			dat.setRunningState(getResultString(res, "runningState"));
			dat.setLimitStatus(getResultString(res, "limitStatus"));
			dat.setExecutionStep(getResultBigDecimal(res, "executionStep"));
			dat.setFixedStep(getResultBigDecimal(res, "fixedStep"));
			dat.setPowerProtection(getResultString(res, "powerProtection"));
			dat.setSetTemperature(getResultBigDecimal(res, "setTemperature"));
			dat.setRoomTemperature(getResultBigDecimal(res, "roomTemperature"));
			dat.setSysReadTime(getResultString(res, "sysReadTime"));
			dat.setCompanyId(getResultString(res, "companyId"));
			dat.setStepTotal(getResultBigDecimal(res, "stepTotal"));
			dat.setBaseStep(getResultBigDecimal(res, "baseStep"));
			dat.setFurStep(getResultBigDecimal(res, "furStep"));
			dat.setOpenTime(getResultString(res, "openTime"));
			dat.setIntervals(getResultBigDecimal(res, "intervals"));
			dat.setWireless(getResultString(res, "wireless"));
			dat.setCurrentTimes(getResultString(res, "currentTimes"));
			dat.setLocks(getResultString(res, "locks"));
			dat.setIndoorColTime(getResultString(res, "indoorColTime"));
			dat.setReadTime(getResultString(res, "readTime"));
			dat.setHeatingArea(getResultString(res, "heatingArea"));
			dat.setFlowingIndex(getResultBigDecimal(res, "flowingIndex"));
			dat.setFlowUpperRatio(getResultBigDecimal(res, "flowUpperRatio"));
			dat.setFlowUpperValue(getResultBigDecimal(res, "flowUpperValue"));
			dat.setMaxFlowThreshold(getResultBigDecimal(res, "maxFlowThreshold"));
			dat.setFlowGiveMode(getResultString(res, "flowGiveMode"));
			dat.setCalKind(getResultString(res, "calKind"));
			dat.setControlCommond(getResultString(res, "controlCommond"));
			dat.setCorrectCoefficient(getResultBigDecimal(res, "correctCoefficient"));
			dat.setFlowRate(getResultBigDecimal(res, "flowRate"));
			dat.setFlowLast(getResultBigDecimal(res, "flowLast"));
			dat.setFlowNow(getResultBigDecimal(res, "flowNow"));
			dat.setActionNow(getResultString(res, "actionNow"));
			dat.setControlAddr(getResultString(res, "controlAddr"));
			dat.setMbusId(getResultString(res, "mbusId"));
			dat.setNotes(getResultString(res, "notes"));
//			dat.setCreateDate(getResultString(res, "createDate"));
//			dat.setCreateUser(getResultString(res, "createUser"));
//			dat.setUpdateDate(getResultString(res, "updateDate"));
//			dat.setUpdateUser(getResultString(res, "updateUser"));
			dat.setAddress2nd(getResultString(res, "address2nd"));
			dat.setProtocol(getResultString(res, "protocol"));
			dat.setValveReadStatus(getResultString(res, "valveReadStatus"));
			dat.setMbusId2(getResultString(res, "mbusId2"));
			dat.setActionSet(getResultString(res, "actionSet"));
			dat.setActionStep(getResultString(res, "actionStep"));
			dat.setMaxTempSet(getResultBigDecimal(res, "maxTempSet"));

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return dat;
	}

	/**
	 * 温控器数据
	 * 
	 * @param res
	 * @return
	 */
	private WkqDataDO orderWkqData(ResultSet res) {
		WkqDataDO dat = null;
		try {
			dat = new WkqDataDO();
			dat.setConsumerId(res.getString("consumerId"));
			dat.setCompanyId(res.getString("companyId"));
			dat.setMeterId(res.getString("meterId"));
			dat.setRowno(res.getString("rowno"));
			dat.setReadTime(DateUtil.fromString(res.getString("readTime")));
			dat.setRoomTemperature(res.getDouble("roomTemperature"));
			dat.setRunningState(res.getString("runningState"));
			dat.setSysReadTime(res.getString("sysReadTime"));
			dat.setElec(res.getDouble("elec"));
			dat.setMeterTime(DateUtil.fromString(res.getString("meterTime")));
			dat.setSignal((int) res.getDouble("signal"));
			dat.setSysReadTime(res.getString("sysReadTime"));
			dat.setAddress2nd(getResultString(res, "address2nd"));
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return dat;
	}

	@Override
	public List<RunningDataTotalDO> list(Map<String, Object> map) {
		try {
			//List<HouseInfo> houses = houseInfo(map);
			Root dataCount = new Root();
			List<RunningDataTotalDO> datas = esHouseTotalData2(map, dataCount , true);
			if (datas.size() == 0) {
				return datas;
			}
			List<TypeMst> runStatetypes = typeMstByType(null);

//			List<RunningDataTotalDO> datasT = new ArrayList<>();
//			for (HouseInfo house : houses) {
				for (RunningDataTotalDO dat : datas) {
					// System.out.println("------------------" + dat.getCompanyId() +
					// house.getRowno());
					//if (dat.getConsumerId().equals(house.getRowno())) {
						dat.setControlRole(typeNameById(runStatetypes, "control_role", dat.getControlRole()));
						//dat.setAddress(house.getAddress());
						dat.setOpenStatus(typeNameById(runStatetypes, "open_status", dat.getOpenStatus()));
						dat.setHeatStatus(typeNameById(runStatetypes, "heat_status", dat.getHeatStatus()));
						dat.setRegulationMode(typeNameById(runStatetypes, "regulation_mode", dat.getRegulationMode()));
						//datasT.add(dat);
					//}
				//}
			}
			return datas;
		} catch (Exception ex) {
			List<RunningDataTotalDO> datas = new ArrayList<RunningDataTotalDO>();
			return datas;
		}
	}





	private Boolean getPageHisHead(XSSFCell cell, XSSFRow row, String name) {
		List<PageGridInfo> infos = pageGridService.getAllPageGrid(0, 200, "asc", "colsort", null, name);
		if (infos.size() == 0) {
			return false;
		}
		int index = 0;
		Boolean flag = false;

		for (int i = 0; i < infos.size(); i++) {
			PageGridInfo info = infos.get(i);
			if ("false".equals(info.getHide())) {
				if (!flag) {
					cell.setCellValue(infos.get(index).getDisplay());// 设置单元格内容
					flag = true;
				} else {
					row.createCell(index).setCellValue(info.getDisplay());
				}
				index++;
			}
		}
		return index > 0;
	}

	public Object getValue(Object dto,String name){  
    Method[]  m = dto.getClass().getMethods();  
    for(int i=0;i<m.length;i++){  
    if(("get"+name).toLowerCase().equals(m[i].getName().toLowerCase())){  
        try {
					return  m[i].invoke(dto);
				} catch (IllegalAccessException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}  
     }  
    }
    return null;
	}
	
	private <T> Boolean setObjectToCell(XSSFRow row, XSSFCell cell, T DataDO, List<PageGridInfo> infos) {
		if (infos.size() == 0) {
			return false;
		}
		int index = 0;
		for (int i = 0; i < infos.size(); i++) {
			PageGridInfo info  = infos.get(i);
			if ("false".equals(info.getHide())) {
				String gridName = info.getName();
				Object objt = null;
				objt = getValue(DataDO, gridName);
				if (objt != null) {
					cell.setCellValue(objt.toString());
				}else{
					cell.setCellValue("");
				}
				index++;
				break;
			}
		}

		for (int i = index; i < infos.size(); i++) {
			PageGridInfo info  = infos.get(i);
			if ("false".equals(info.getHide())) {
				String gridName = info.getName();
				Object objt = null;
				objt = getValue(DataDO, gridName);
				if (objt != null) {
					row.createCell(index).setCellValue(objt.toString());
				}else{
					row.createCell(index).setCellValue("");
				}
				index++;
			}
		}
		return index > 0;
	}
	

	@Override
	public Object getMeterHisDataToExcel(Map<String, Object> params, ServletRequest request) {
		
		Root root = viladHisParam(params);
		List<HeatMeterDataDO> datasT= heatList(params,root);
//		return getHisDataToExcel(request, "houseMeter", "热表历史",datasT);
		if(datasT==null)
		{
			return root;
		}
		Object obj;
		Long time1 = DateUtil.getSysdateLong();
//		obj=commonService.getHisJSONArrayToExcel(request, "houseMeter", "main","热表历史",JSONArray.parseArray(JSON.toJSONString(datasT)), true);
		Long time2 = DateUtil.getSysdateLong();
		System.out.println("------------exceknornal:" + (time2 - time1));
		
		Long time3 = DateUtil.getSysdateLong();
		obj=commonService.getHisJSONArrayToBigExcel(request, "houseMeter", "main","热表历史",JSONArray.parseArray(JSON.toJSONString(datasT)), true);
		Long time4 = DateUtil.getSysdateLong();
		System.out.println("------------excekbin:" + (time4 - time3));
		return obj;
//		return commonService.getHisJSONArrayToExcel(request, "houseMeter", "main","热表历史",JSONArray.parseArray(JSON.toJSONString(datasT)), true);
		
		
// 		List<HeatMeterDataDO> datasT = new ArrayList<>();
//		params.put("offset", "0");
//		params.put("limit", "50000");
// 		getMeterHisDataList(params, datasT);
// 		return getHisDataToExcel(request, "heatmeterhistory", "热表历史",datasT);
	}

	@Override
	public Object getWkfHisDataToExcel(Map<String, Object> params, ServletRequest request) {
		Root root = viladHisParam(params);
		List<WkfDataDO> datasT = valveList(params, root);
		if (datasT == null) {
			return root;
		}
		Object obj;
		Long time3 = DateUtil.getSysdateLong();
		obj = commonService.getHisJSONArrayToBigExcel(request, "tempValve", "main", "阀门历史",
				JSONArray.parseArray(JSON.toJSONString(datasT)), true);
		Long time4 = DateUtil.getSysdateLong();
		System.out.println("------------excekbin:" + (time4 - time3));
		return obj;
		
// 		List<WkfDataDO> datasT = new ArrayList<>();
//		params.put("offset", "0");
//		params.put("limit", "50000");
//		getWkfHisDataList(params, datasT);
// 		return getHisDataToExcel(request, "valvehistory", "阀门历史",datasT);
	}
	
	@Override
	public Object getHouseWkqHisDataToExcel(Map<String, Object> params, ServletRequest request) {
		
		Root root = viladHisParam(params);
		List<WkqDataDO> datasT = roomList(params, root);
		if (datasT == null) {
			return root;
		}
		Object obj;
		Long time3 = DateUtil.getSysdateLong();
		obj = commonService.getHisJSONArrayToBigExcel(request, "roomhistory", "main", "室内温度采集历史",
				JSONArray.parseArray(JSON.toJSONString(datasT)), true);
		Long time4 = DateUtil.getSysdateLong();
		System.out.println("------------excekbin:" + (time4 - time3));
		return obj;
// 		List<WkqDataDO> datasT = new ArrayList<>();
//		params.put("offset", "0");
//		params.put("limit", "50000");
//		getHouseWkqHisDataList(params, datasT);
// 		return getHisDataToExcel(request, "roomhistory", "室内温度采集历史",datasT);
	}
	
	@Override
	public Object getHouseTotalDataToExcel(Map<String, Object> params, ServletRequest request) {
		
		Root root = viladHisParam(params);
		List<RunningDataTotalDO> datasT = roomRunningList(params, root);
		if (datasT == null) {
			return root;
		}
		Object obj;
		Long time3 = DateUtil.getSysdateLong();
		obj = commonService.getHisJSONArrayToBigExcel(request, "userDataStatistics", "main", "用户汇总历史",
				JSONArray.parseArray(JSON.toJSONString(datasT)), true);
		Long time4 = DateUtil.getSysdateLong();
		System.out.println("------------excekbin:" + (time4 - time3));
		return obj;
// 		List<RunningDataTotalDO> datasT = new ArrayList<>();
//		params.put("offset", "0");
//		params.put("limit", "50000");
//		getHouseTotalDataList(params, datasT);
// 		return getHisDataToExcel(request, "userrunningdatahistroy", "用户汇总历史",datasT);
	}
	
	
	public<T> Object getHisDataToExcel(ServletRequest request, String tableName,String desc, List<T> datasT) {
		
		Long time1 = DateUtil.getSysdateLong();
		String path = desc + DateUtil.getSysdateTimeString().replace(" ", "").replace(":", "").replace("-", "") + ".xlsx";
		String filePath = request.getServletContext().getRealPath("/") + path;// 文件路径
		System.out.println("路径：" + filePath);
		XSSFWorkbook workbook = new XSSFWorkbook();// 创建Excel文件(Workbook)
		XSSFSheet sheet = workbook.createSheet(desc);// 创建工作表(Sheet)
		XSSFRow row = sheet.createRow(0);// 创建行,从0开始
		XSSFCell cell = row.createCell(0);// 创建行的单元格,也是从0开始
		if (!getPageHisHead(cell, row, tableName)) {
			throw new BusinessException("未找到要到出的列名，请检查配置！");
		}

		List<PageGridInfo> infos = pageGridService.getAllPageGrid(0, 200, "asc", "colsort", null, tableName);
		for (int i = 0; i < datasT.size(); i++) {
			row = sheet.createRow(i + 1);// 创建行,从0开始
			cell = row.createCell(0);// 创建行的单元格,也是从0开始
			setObjectToCell(row, cell, datasT.get(i), infos);
		}
		try {
			FileOutputStream out = new FileOutputStream(filePath);
			workbook.write(out);// 保存Excel文件
			out.close();// 关闭文件流
			System.out.println("导出成功!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Long time2 = DateUtil.getSysdateLong();
		System.out.println("------------excek:" + (time2 - time1));
		return path;
	}


	
	
	public Root getMeterHisDataList(Map<String, Object> map, List<HeatMeterDataDO> datasT) {
		// String beginTime,String endTime,String heatStatus,String type,String
		// id，String useHeatStatus,String houseType
		Root root = viladHisParam(map);
		if (root.getCode() == 1) {
			System.out.println("------------开始-------------------");
			return root;
		}
		Boolean flag = dataRoleService.getDataRoleItemsType(UserUtils.getUserId(), map);
		Root datCount = new Root();
		Long time1 = DateUtil.getSysdateLong();
		System.out.println("------------开始");
		List<BaseHistoryData> datas = esMeter(map, datCount, true);
		Long time2 = DateUtil.getSysdateLong();
		System.out.println("------------esMeter:" + (time2 - time1));

		// 组合数据，将ID替换成名字，并补充对应字段
		if (datas.size() == 0) {

			setRootCond(root, map, 0);
			root.setData(JSON.toJSON(datas));
			return root;
		}

		SetMeterInfo(datas, "meter_status");
		Integer total = Integer.parseInt(datCount.getData().toString());
		setRootCond(root, map, total);
		// root.setData(JSON.toJSON(datasT));
		root.setData(JSON.toJSON(datas));

		Long time4 = DateUtil.getSysdateLong();
		// System.out.println("------------完成:" + (time4 - time3)+ "总计" + (time4
		// - time1));
		return root;
	}
	
	
	public Root getWkfHisDataList(Map<String, Object> map, List<WkfDataDO> datasT) {
		Root root = viladHisParam(map);
		if (root.getCode() == 1) {
			return root;
		}
		Boolean flag = dataRoleService.getDataRoleItemsType(UserUtils.getUserId(), map);
		Root dataCount = new Root();
		List<BaseHistoryData> datas = esWkfDataList(map, dataCount, true);

		if (datas.size() == 0) {
			setRootCond(root, map, 0);
			root.setData(JSON.toJSON(datas));
			return root;
		}
		SetMeterInfo(datas,"open_status");
		List<TypeMst> runStatetypes = typeMstByType(null);
		
		for (BaseHistoryData dat : datas)
		{
			((WkfDataDO) dat).setControlCommond(typeNameById(runStatetypes, "control_commond", ((WkfDataDO) dat).getControlCommond()));
			((WkfDataDO) dat).setLocks(typeNameById(runStatetypes, "locks", ((WkfDataDO) dat).getLocks()));
			((WkfDataDO) dat).setLimitStatus(typeNameById(runStatetypes, "limit_status", ((WkfDataDO) dat).getLimitStatus()));
			((WkfDataDO) dat).setPowerProtection(typeNameById(runStatetypes, "power_protection", ((WkfDataDO) dat).getPowerProtection()));
			((WkfDataDO) dat).setWireless(typeNameById(runStatetypes, "wire_wireless", ((WkfDataDO) dat).getWireless()));
			((WkfDataDO) dat).setFlowGiveMode(typeNameById(runStatetypes, "flow_give_mode", ((WkfDataDO) dat).getFlowGiveMode()));
			((WkfDataDO) dat).setCalKind(typeNameById(runStatetypes, "cal_kind", ((WkfDataDO) dat).getCalKind()));

			((WkfDataDO) dat).setActionNow(typeNameById(runStatetypes, "action_now", ((WkfDataDO) dat).getActionNow()));
			((WkfDataDO) dat).setProtocol(typeNameById(runStatetypes, "protocol", ((WkfDataDO) dat).getProtocol()));
			((WkfDataDO) dat).setValveReadStatus(typeNameById(runStatetypes, "valve_read_status", ((WkfDataDO) dat).getValveReadStatus()));
			((WkfDataDO) dat).setActionSet(typeNameById(runStatetypes, "valve_control", ((WkfDataDO) dat).getActionSet()));
		}

		Integer total = Integer.parseInt(dataCount.getData().toString());
		setRootCond(root, map, total);
		root.setData(JSON.toJSON(datas)); 

		Long time4 = DateUtil.getSysdateLong();
		return root;
	}
	
	
	public Root getHouseWkqHisDataList(Map<String, Object> map, List<WkqDataDO> datasT) {
		Root root = viladHisParam(map);
		if (root.getCode() == 1) {
			return root;
		}
		Boolean flag = dataRoleService.getDataRoleItemsType(UserUtils.getUserId(), map);
		Root dataCount = new Root();
		List<BaseHistoryData> datas = esWkqData(map, dataCount);
		if (datas.size() == 0) {
			setRootCond(root, map, 0);
			root.setData(JSON.toJSON(datas));
			return root;
		}
		// 组合数据，将ID替换成名字，并补充对应字段
		if (datas.size() == 0) {
			setRootCond(root, map, 0);
			root.setData(JSON.toJSON(datas));
			return root;
		}
		SetMeterInfo(datas, "valve_read_status");
		Integer total = Integer.parseInt(dataCount.getData().toString());
		setRootCond(root, map, total);
		root.setData(JSON.toJSON(datas));
		Long time4 = DateUtil.getSysdateLong();
		return root;
	}
	
	public Root getHouseTotalDataList(Map<String, Object> map,List<RunningDataTotalDO> datasT) {		
		Root root = viladHisParam(map);
		if (root.getCode() == 1) {
			return root;
		}
		Boolean flag = dataRoleService.getDataRoleItemsType(UserUtils.getUserId(), map);
		Root dataCount = new Root();
		List<BaseHistoryData> datas = esHouseTotalData(map, dataCount, true);

		if (datas.size() == 0) {
			setRootCond(root, map, 0);
			root.setData(JSON.toJSON(datas));
			return root;
		}
		SetMeterInfo(datas,"");
		List<TypeMst> runStatetypes = typeMstByType(null);
		
		for (BaseHistoryData dat : datas)
		{
			((RunningDataTotalDO) dat).setControlRole(typeNameById(runStatetypes, "control_role", ((RunningDataTotalDO) dat).getControlRole()));
			((RunningDataTotalDO) dat).setOpenStatus(typeNameById(runStatetypes, "open_status", ((RunningDataTotalDO) dat).getOpenStatus()));
			((RunningDataTotalDO) dat).setHeatStatus(typeNameById(runStatetypes, "heat_status", ((RunningDataTotalDO) dat).getHeatStatus()));
			((RunningDataTotalDO) dat).setRegulationMode(typeNameById(runStatetypes, "regulation_mode", ((RunningDataTotalDO) dat).getRegulationMode()));
		}

		Integer total = Integer.parseInt(dataCount.getData().toString());
		setRootCond(root, map, total);
		root.setData(JSON.toJSON(datas)); 

		Long time4 = DateUtil.getSysdateLong();
		return root;
		
//		Root root = viladHisParam(map);
//		if (root.getCode() == 1) {
//			return root;
//		}
//    Boolean flag = dataRoleService.getDataRoleItemsType(UserUtils.getUserId(), map);
//    
//		// List<HouseInfo> houses = houseInfo(map); address在汇总里面搞了
//		Root dataCount = new Root();
//		List<BaseHistoryData> datas = esHouseTotalData(map, dataCount, true);
//		if (datas.size() == 0) {
//			root.setData(JSON.toJSON(datas));
//			setRootCond(root, map, 0);
//			return root;
//		}
//		
//		List<TypeMst> runStatetypes = typeMstByType(null);
//
//		// for (HouseInfo house : houses) {
//		for (RunningDataTotalDO dat : datas) {
//			// System.out.println("------------------" + dat.getCompanyId() +
//			// house.getRowno());
//			// if (dat.getConsumerId().equals(house.getConsumerId())) {
//			dat.setControlRole(typeNameById(runStatetypes, "control_role", dat.getControlRole()));
//			// dat.setAddress(house.getAddress());
//			dat.setOpenStatus(typeNameById(runStatetypes, "open_status", dat.getOpenStatus()));
//			dat.setHeatStatus(typeNameById(runStatetypes, "heat_status", dat.getHeatStatus()));
//			dat.setRegulationMode(typeNameById(runStatetypes, "regulation_mode", dat.getRegulationMode()));
//			//dat.setActionLimit(typeNameById(runStatetypes, "action_limit", dat.getActionLimit()));
//			datasT.add(dat);
//			// }
//		}
//		// }
//
//		// 组合数据，将ID替换成名字，并补充对应字段
//		if (datasT.size() == 0) {
//			setRootCond(root, map, 0);
//			root.setData(JSON.toJSON(datasT));
//			return root;
//		}
//		Integer total = Integer.parseInt(dataCount.getData().toString());
//		setRootCond(root, map, total);
//		root.setData(JSON.toJSON(datasT));
//		return root;
	}
	

	
	

	@Override
	public List<RunningDataTotalDO> listTotal(Map<String, Object> map) {
		List<RunningDataTotalDO> datas = new ArrayList<>();
		String tableName = "user_running_data_day";
		String strsql = "select avg(inTemperature) as inTemperature,avg(outTemperature) as outTemperature,"
				+ "avg(outdoorTemperature) as outdoorTemperature,avg(roomTemperatureRead) as roomTemperatureRead"
				+ "avg(power) as power,avg(flowSpeed) as flowSpeed,last(openStatus) as openStatus,avg(executionStep) as executionStep,"
				+ "avg(stepTotal) as stepTotal  from " + tableName + " where  sysReadTimeLong >= %s and sysReadTimeLong <=%s ";

		strsql = filterTime(strsql, map);
		strsql = filterCondi(strsql, map, "");
		strsql = filterHouseVal(strsql, map);
		strsql = filtergroup(strsql);	
		strsql = filterSortAndLimit(strsql, map);

		try {
			ResultSet res = Support.querryFromEs(strsql);
			if (res == null) {
				return datas;
			}
			while (res.next()) {
				try {
					RunningDataTotalDO dat = orderHouseTotalData(res);
					if (dat != null) {
						datas.add(dat);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return datas;
	}

	private String filtergroup(String strsql) {
		strsql = strsql + " group by sysReadTime,consumerId ";
		return strsql;
	}
	
	public List<HeatMeterDataDO> heatList(Map<String, Object> map,Root root)
	{

		List<BaseHistoryData> datasT = new ArrayList<>();
	    TransportClient esClient = (TransportClient) esTool.getClient();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
	    
		String readTime = getMapValue(map, "readTime");
		String inTemperature = getMapValue(map, "inWaterTmp");
		String outTemperature = getMapValue(map, "outWaterTmp");
		String tmpDiff = getMapValue(map, "tmpDiff");

		String inFlow = getMapValue(map, "flow");
		String flowSpeed = getMapValue(map, "flowSpeed");
		String power = getMapValue(map, "power");
		String heat = getMapValue(map, "heat");
		String meterStatus = getMapValue(map, "meterStatus");
		
		SetESReadTime(boolQueryBuilder, "sysReadTimeLong", readTime);
		SetESRange(boolQueryBuilder, "inTemperature", inTemperature);
		SetESRange(boolQueryBuilder, "outTemperature", outTemperature);
		SetESRange(boolQueryBuilder, "temperature", tmpDiff);
		SetESRange(boolQueryBuilder, "inFlow", inFlow);
		SetESRange(boolQueryBuilder, "flowSpeed", flowSpeed);
		SetESRange(boolQueryBuilder, "power", power);
		SetESRange(boolQueryBuilder, "heat", heat);
		SetESEqual(boolQueryBuilder, "runningState", meterStatus);
	  
	   String type = getMapValue(map, "type");
	   String id = getMapValue(map, "id");
	   SetESRole(boolQueryBuilder,type,id);
		Long time1 = DateUtil.getSysdateLong();
	    List<String> ids=new ArrayList<>();
	    SearchResponse searchResponse = esClient.prepareSearch("heat_meter_data")
	            .setTypes("data")
	            .setFetchSource(new String[] { "sysReadTime", "consumerId", "heat", "cool", "inFlow", "outFlow", "inTemperature", "outTemperature", "temperature", "power", "allWorkTime", "runningState", "allTime", "address2nd", "meterTime" }, null)
	            .setSize(5000)
	            .setQuery(boolQueryBuilder)
	            //这个游标维持多长时间
	            .setScroll(TimeValue.timeValueMinutes(8))
	            .execute().actionGet();
	    
	    System.out.println(searchResponse.getHits().getTotalHits());
	    if(searchResponse.getHits().getTotalHits()>1000000)
		{
			root.setMsg("请选择100万条以内的数据！");
			root.setCode(1);
//		    esTool.clearScroll(esClient, ids);
		    esTool.releaseClient(esClient);
	    	return null;
		}
	    StringBuffer buffer = new StringBuffer();
	    while(true){
	        for (SearchHit hit : searchResponse.getHits()) {
	            System.out.println(hit.getSourceAsString());
	            JSONObject jsonObject = JSON.parseObject(hit.getSourceAsString());
	            HeatMeterDataDO dt=  jsonObject.toJavaObject(HeatMeterDataDO.class);
	            datasT.add(dt);
	            buffer.append(hit.getSourceAsString().replace("\n"," ")+"\n");
	        }
	        ids.add(searchResponse.getScrollId());
	        searchResponse = esClient.prepareSearchScroll(searchResponse.getScrollId())
	                .setScroll(TimeValue.timeValueMinutes(8))
	                .execute().actionGet();
	        if (searchResponse.getHits().getHits().length == 0) {
	            break;
	        }
	    }
		Long time2 = DateUtil.getSysdateLong();
		System.out.println("------------esheat:" + (time2 - time1));

	    esTool.clearScroll(esClient, ids);
	    esTool.releaseClient(esClient);
	    Long time3 = DateUtil.getSysdateLong();
	    SetMeterInfo(datasT,"meter_status");
	    Long time4 = DateUtil.getSysdateLong();
	    System.out.println("------------redis总:" + (time4 - time3));
	    return (List)datasT;
	    
		
	}
	
	
	public List<WkfDataDO> valveList(Map<String, Object> map,Root root)
	{

		List<BaseHistoryData> datasT = new ArrayList<>();
	    TransportClient esClient = (TransportClient) esTool.getClient();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
	    
		String setTemperature = getMapValue(map, "setTemperature");
		String roomTemperature = getMapValue(map, "roomTemperature");
		String flowingIndex = getMapValue(map, "flowingIndex");

		String flowUpperRatio = getMapValue(map, "flowUpperRatio");
		String flowUpperValue = getMapValue(map, "flowUpperValue");
		String controlCommond = getMapValue(map, "controlCommond");
		String correctCoefficient = getMapValue(map, "correctCoefficient");
		String flowRate = getMapValue(map, "flowRate");
		String flowNow = getMapValue(map, "flowNow");
		
		SetESRange(boolQueryBuilder, "setTemperature", setTemperature);
		SetESRange(boolQueryBuilder, "roomTemperature", roomTemperature);
		SetESRange(boolQueryBuilder, "flowingIndex", flowingIndex);
		SetESRange(boolQueryBuilder, "flowUpperRatio", flowUpperRatio);
		SetESRange(boolQueryBuilder, "flowUpperRatio", flowUpperRatio);
		SetESEqual(boolQueryBuilder, "controlCommond", controlCommond);
		SetESRange(boolQueryBuilder, "correctCoefficient", correctCoefficient);
		SetESRange(boolQueryBuilder, "flowRate", flowRate);
		SetESRange(boolQueryBuilder, "flowNow", flowNow);
		

		String readTime = getMapValue(map, "readTime");
		String meterStatus = getMapValue(map, "meterStatus");
		
		SetESReadTime(boolQueryBuilder, "sysReadTimeLong", readTime);
		SetESEqual(boolQueryBuilder, "runningState", meterStatus);
	  
	   String type = getMapValue(map, "type");
	   String id = getMapValue(map, "id");
	   SetESRole(boolQueryBuilder,type,id);
		Long time1 = DateUtil.getSysdateLong();
	    List<String> ids=new ArrayList<>();
	    SearchResponse searchResponse = esClient.prepareSearch("valve_data")
	            .setTypes("data")
//	            .setFetchSource(new String[] { "sysReadTime", "consumerId", "heat", "cool", "inFlow", "outFlow", "inTemperature", "outTemperature", "temperature", "power", "allWorkTime", "runningState", "allTime", "address2nd", "meterTime" }, null)
	            .setSize(5000)
	            .setQuery(boolQueryBuilder)
	            //这个游标维持多长时间
	            .setScroll(TimeValue.timeValueMinutes(8))
	            .execute().actionGet();
	    
	    System.out.println(searchResponse.getHits().getTotalHits());
	    if(searchResponse.getHits().getTotalHits()>1000000)
		{
			root.setMsg("请选择100万条以内的数据！");
			root.setCode(1);
//		    esTool.clearScroll(esClient, ids);
		    esTool.releaseClient(esClient);
	    	return null;
		}
	    StringBuffer buffer = new StringBuffer();
	    while(true){
	        for (SearchHit hit : searchResponse.getHits()) {
	            System.out.println(hit.getSourceAsString());
	            JSONObject jsonObject = JSON.parseObject(hit.getSourceAsString());
	            WkfDataDO dt=  jsonObject.toJavaObject(WkfDataDO.class);
	            datasT.add(dt);
	            buffer.append(hit.getSourceAsString().replace("\n"," ")+"\n");
	        }
	        ids.add(searchResponse.getScrollId());
	        searchResponse = esClient.prepareSearchScroll(searchResponse.getScrollId())
	                .setScroll(TimeValue.timeValueMinutes(8))
	                .execute().actionGet();
	        if (searchResponse.getHits().getHits().length == 0) {
	            break;
	        }
	    }
		Long time2 = DateUtil.getSysdateLong();
		System.out.println("------------esheat:" + (time2 - time1));

	    esTool.clearScroll(esClient, ids);
	    esTool.releaseClient(esClient);
	    Long time3 = DateUtil.getSysdateLong();
	    SetMeterInfo(datasT,"open_status");
	    Long time4 = DateUtil.getSysdateLong();
	    System.out.println("------------redis总:" + (time4 - time3));
	    return (List)datasT;
	}
	
	
	public List<WkqDataDO> roomList(Map<String, Object> map,Root root)
	{
		List<BaseHistoryData> datasT = new ArrayList<>();
	    TransportClient esClient = (TransportClient) esTool.getClient();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
	    
		String signal = getMapValue(map, "signal");
		String elec = getMapValue(map, "elec");
		String roomTemperature = getMapValue(map, "roomTemperature");
		
		SetESRange(boolQueryBuilder, "signal", signal);
		SetESRange(boolQueryBuilder, "elec", elec);
		SetESRange(boolQueryBuilder, "roomTemperature", roomTemperature);
		
		String readTime = getMapValue(map, "readTime");
		String meterStatus = getMapValue(map, "meterStatus");
		
		SetESReadTime(boolQueryBuilder, "sysReadTimeLong", readTime);
		SetESEqual(boolQueryBuilder, "runningState", meterStatus);
	  
	   String type = getMapValue(map, "type");
	   String id = getMapValue(map, "id");
	   SetESRole(boolQueryBuilder,type,id);
		Long time1 = DateUtil.getSysdateLong();
	    List<String> ids=new ArrayList<>();
	    SearchResponse searchResponse = esClient.prepareSearch("room_temperature_data")
	            .setTypes("data")
//	            .setFetchSource(new String[] { "sysReadTime", "consumerId", "heat", "cool", "inFlow", "outFlow", "inTemperature", "outTemperature", "temperature", "power", "allWorkTime", "runningState", "allTime", "address2nd", "meterTime" }, null)
	            .setSize(5000)
	            .setQuery(boolQueryBuilder)
	            //这个游标维持多长时间
	            .setScroll(TimeValue.timeValueMinutes(8))
	            .execute().actionGet();
	    
	    System.out.println(searchResponse.getHits().getTotalHits());
	    if(searchResponse.getHits().getTotalHits()>1000000)
		{
			root.setMsg("请选择100万条以内的数据！");
			root.setCode(1);
//		    esTool.clearScroll(esClient, ids);
		    esTool.releaseClient(esClient);
	    	return null;
		}
	    StringBuffer buffer = new StringBuffer();
	    while(true){
	        for (SearchHit hit : searchResponse.getHits()) {
	            System.out.println(hit.getSourceAsString());
	            JSONObject jsonObject = JSON.parseObject(hit.getSourceAsString());
	            WkqDataDO dt=  jsonObject.toJavaObject(WkqDataDO.class);
	            datasT.add(dt);
	            buffer.append(hit.getSourceAsString().replace("\n"," ")+"\n");
	        }
	        ids.add(searchResponse.getScrollId());
	        searchResponse = esClient.prepareSearchScroll(searchResponse.getScrollId())
	                .setScroll(TimeValue.timeValueMinutes(8))
	                .execute().actionGet();
	        if (searchResponse.getHits().getHits().length == 0) {
	            break;
	        }
	    }
		Long time2 = DateUtil.getSysdateLong();
		System.out.println("------------esheat:" + (time2 - time1));

	    esTool.clearScroll(esClient, ids);
	    esTool.releaseClient(esClient);
	    Long time3 = DateUtil.getSysdateLong();
	    SetMeterInfo(datasT,"open_status");
	    Long time4 = DateUtil.getSysdateLong();
	    System.out.println("------------redis总:" + (time4 - time3));
	    return (List)datasT;
	    
		
	}
	
	
	public List<RunningDataTotalDO> roomRunningList(Map<String, Object> map,Root root)
	{
		List<BaseHistoryData> datasT = new ArrayList<>();
	    TransportClient esClient = (TransportClient) esTool.getClient();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
	    
		String power = getMapValue(map, "power");
		String flowSpeed = getMapValue(map, "flowSpeed");
		String inTemperature = getMapValue(map, "inTemperature");
		String outTemperature = getMapValue(map, "outTemperature");
		String roomTemperature = getMapValue(map, "roomTemperature");
		String heatingIndex = getMapValue(map, "heatingIndex");
		String flowingIndex = getMapValue(map, "flowingIndex");
		String adjHeatingIndex = getMapValue(map, "adjHeatingIndex");
		
		
		
		SetESRange(boolQueryBuilder, "power", power);
		SetESRange(boolQueryBuilder, "flowSpeed", flowSpeed);
		SetESRange(boolQueryBuilder, "inTemperature", inTemperature);
		SetESRange(boolQueryBuilder, "outTemperature", outTemperature);
		SetESRange(boolQueryBuilder, "roomTemperature", roomTemperature);
		SetESRange(boolQueryBuilder, "heatingIndex", heatingIndex);
		SetESRange(boolQueryBuilder, "flowingIndex", flowingIndex);
		SetESRange(boolQueryBuilder, "adjHeatingIndex", adjHeatingIndex);
		
		
		
		
		String readTime = getMapValue(map, "readTime");
//		String meterStatus = getMapValue(map, "meterStatus");
		
		SetESReadTime(boolQueryBuilder, "sysReadTimeLong", readTime);
//		SetESEqual(boolQueryBuilder, "runningState", meterStatus);
	  
	   String type = getMapValue(map, "type");
	   String id = getMapValue(map, "id");
	   SetESRole(boolQueryBuilder,type,id);
		Long time1 = DateUtil.getSysdateLong();
	    List<String> ids=new ArrayList<>();
	    SearchResponse searchResponse = esClient.prepareSearch("user_running_data_history")
	            .setTypes("data")
//	            .setFetchSource(new String[] { "sysReadTime", "consumerId", "heat", "cool", "inFlow", "outFlow", "inTemperature", "outTemperature", "temperature", "power", "allWorkTime", "runningState", "allTime", "address2nd", "meterTime" }, null)
	            .setSize(5000)
	            .setQuery(boolQueryBuilder)
	            //这个游标维持多长时间
	            .setScroll(TimeValue.timeValueMinutes(8))
	            .execute().actionGet();
	    
	    System.out.println(searchResponse.getHits().getTotalHits());
	    if(searchResponse.getHits().getTotalHits()>1000000)
		{
			root.setMsg("请选择100万条以内的数据！");
			root.setCode(1);
//		    esTool.clearScroll(esClient, ids);
		    esTool.releaseClient(esClient);
	    	return null;
		}
	    StringBuffer buffer = new StringBuffer();
	    while(true){
	        for (SearchHit hit : searchResponse.getHits()) {
	            System.out.println(hit.getSourceAsString());
	            JSONObject jsonObject = JSON.parseObject(hit.getSourceAsString());
	            RunningDataTotalDO dt=  jsonObject.toJavaObject(RunningDataTotalDO.class);
	            datasT.add(dt);
	            buffer.append(hit.getSourceAsString().replace("\n"," ")+"\n");
	        }
	        ids.add(searchResponse.getScrollId());
	        searchResponse = esClient.prepareSearchScroll(searchResponse.getScrollId())
	                .setScroll(TimeValue.timeValueMinutes(8))
	                .execute().actionGet();
	        if (searchResponse.getHits().getHits().length == 0) {
	            break;
	        }
	    }
		Long time2 = DateUtil.getSysdateLong();
		System.out.println("------------esheat:" + (time2 - time1));

	    esTool.clearScroll(esClient, ids);
	    esTool.releaseClient(esClient);
	    Long time3 = DateUtil.getSysdateLong();
	    SetMeterInfo(datasT,"open_status");
	    Long time4 = DateUtil.getSysdateLong();
	    System.out.println("------------redis总:" + (time4 - time3));
	    return (List)datasT;
	    
		
	}
	
	private void SetMeterInfo(List<BaseHistoryData> datas, String statusType) {
//		List<BaseHistoryData> list = null;
//		BaseHistoryData=datas;
		List<TypeMst> runStatetypes = typeMstByType(statusType);
		RedisUtil redisUtil = RedisUtil.getSingleton();
		Jedis jedis = null;
		try {
			jedis = redisUtil.getJedis();
			House house=new House();
			Unit unit=new Unit();
			BuildInfo buildInfo=new BuildInfo();
			Commuity commuity=new Commuity();
			int i=1;

			Map<String, House> map = new HashMap<String, House>();

	
			for (BaseHistoryData data : datas) {
				if(StringUtil.hasValue(statusType))
				{
					data.setRunningState(typeNameById(runStatetypes, statusType, data.getRunningState()));
				}
				Long time3 = DateUtil.getSysdateLong();
				int consumerLen = data.getConsumerId().length();
				String key = "";
				if (consumerLen == 19) {
					key = "houseinfo_" + data.getConsumerId();
				}
				if (consumerLen == 15) {
					key = "unitinfo_" + data.getConsumerId();
				}
				if (consumerLen == 13) {
					key = "buildinfo_" + data.getConsumerId();
				}
				if (consumerLen == 10) {
					key = "commuityinfo_" + data.getConsumerId();
				}
				if (consumerLen == 19)
				{
					house=map.get(data.getConsumerId());
					if(house!=null)
					{
						 System.out.println("------------map:"+i);
						data.setSource(house.getAddress());
						continue;
					}
				}

				String keys = jedis.get(key);
				if (StringUtil.hasValue(keys)) {
					JSONObject jsonObject = JSON.parseObject(keys);
					if (consumerLen == 19) {
//						house=map.get(data.getConsumerId());
						
						house = jsonObject.toJavaObject(House.class);
							data.setSource(house.getAddress());
							map.put(data.getConsumerId(), house);
//						}
					}
					if (consumerLen == 15) {
						unit = jsonObject.toJavaObject(Unit.class);
						data.setSource(unit.getAddress());
					}
					if (consumerLen == 13) {
						buildInfo = jsonObject.toJavaObject(BuildInfo.class);
						data.setSource(buildInfo.getAddress());
					}
					if (consumerLen == 10) {
						commuity = jsonObject.toJavaObject(Commuity.class);
						data.setSource(commuity.getAddress());
					}

				}
			
				 Long time4 = DateUtil.getSysdateLong();
				 i=i+1;
				    System.out.println("------------redis:"+i+"---" + (time4 - time3));
			}
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			RedisUtil.returnSource(jedis);
		}
	}
	

	private void SetESReadTime(BoolQueryBuilder boolQueryBuilder, String field, String data) {
		String strB = "";
		String strE = "";
		Date date = new Date();
		if (StringUtil.hasValue(data)) {
			String[] str = data.split(",");
			if (str.length == 2) {
				strB = str[0];
				strE = str[1];
			}
			if (str.length == 1) {
				strB = str[0];
			}
		}
		if (StringUtil.hasValue(strB) == false) {
			strB = DateUtil.toDateString(date) + " 00:00:00";
		}
		if (StringUtil.hasValue(strE) == false){
			strE = DateUtil.toDateString(date) + " 23:59:59";
		}


//	RangeQueryBuilder publishDateBuilder = QueryBuilders.rangeQuery(
//			"sysReadTimeLong");
		RangeQueryBuilder publishDateBuilder = QueryBuilders.rangeQuery(
				field);
	publishDateBuilder.from(NumUtil.toLong(Long.toString(DateUtil.fromString(strB,true).getTime())));// 格式需要同你创建索引时的格式匹配
	publishDateBuilder.includeLower(true);boolQueryBuilder.filter(publishDateBuilder);
	RangeQueryBuilder publishDateBuildert = QueryBuilders.rangeQuery(
			field);publishDateBuildert.to(NumUtil.toLong(Long.toString(DateUtil.fromString(strE,true).getTime())));// 格式需要同你创建索引时的格式匹配
	publishDateBuildert.includeUpper(true);
	boolQueryBuilder.filter(publishDateBuildert);
	}
	
	private void SetESRange(BoolQueryBuilder boolQueryBuilder, String field, String data) {

		if (StringUtil.hasValue(data)) {
			String strB = "";
			String strE = "";
			String[] str = data.split(",");
			if (str.length == 2) {
				strB = str[0];
				strE = str[1];
			}
			if (str.length == 1) {
				strB = str[0];
			}
			if (StringUtil.hasValue(strB)) {
				RangeQueryBuilder publishDateBuilder = QueryBuilders.rangeQuery(field);
				publishDateBuilder.from(NumUtil.toFloat(strB));// 格式需要同你创建索引时的格式匹配
				publishDateBuilder.includeLower(true);
				boolQueryBuilder.filter(publishDateBuilder);
			}
			if (StringUtil.hasValue(strE)) {
				RangeQueryBuilder publishDateBuildert = QueryBuilders.rangeQuery(field);
				publishDateBuildert.to(NumUtil.toFloat(strE));// 格式需要同你创建索引时的格式匹配
				publishDateBuildert.includeUpper(true);
				boolQueryBuilder.filter(publishDateBuildert);
			}
		}
	}

	private String SetMysqlRange(String field, String data) {
		String strsql = "";
		if (StringUtil.hasValue(data) == false) {
			return strsql;
		}
		String strB = "";
		String strE = "";
		String[] str = data.split(",");
		if (str.length == 2) {
			strB = str[0];
			strE = str[1];
		}
		if (str.length == 1) {
			strB = str[0];
		}
		if (StringUtil.hasValue(strB)) {
			strsql += " and " + field + ">= " + strB;
		}
		if (StringUtil.hasValue(strE)) {
			strsql += " and " + field + "<= " + strE;
		}
		return strsql;
	}
	
	private String SetMysqlEqual(String field, String data) {
		String strsql = "";
		if (StringUtil.hasValue(data) == false) {
			return strsql;
		}
		else {
			strsql += " and " + field + "= " + data;
		}
		return strsql;
	}
	
	
	private void SetESEqual(BoolQueryBuilder boolQueryBuilder, String field, String data) {
		if (StringUtil.hasValue(data)) {
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(data);
			TermsQueryBuilder termsQuery = QueryBuilders.termsQuery(field, arrayList);
			boolQueryBuilder.filter(termsQuery);
		}
	}

	private void SetESEqualIn(BoolQueryBuilder boolQueryBuilder, String field, List<String> data) {
		String intArray[] = new String[data.size()];
		for (int i = 0; i < data.size(); i++) {
			intArray[i] = data.get(i);
		}
		MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(field, intArray);
		boolQueryBuilder.filter(queryBuilder);
	}

	private void SetESRole(BoolQueryBuilder boolQueryBuilder, String type, String id) {
		String companyId = null;
		String stationId = null;
		String commuityId = null;
		// Boolean flag =
		// dataRoleService.getDataRoleItemsType(UserUtils.getUserId(), map);
		List<String> companyIds = new ArrayList<String>();
		List<String> stationIds = new ArrayList<String>();
		List<String> commuityIds = new ArrayList<String>();
		if (id.equals("company")) {
			companyId = id;
		}
		if (id.equals("station")) {
			stationId = id;
		}
		Boolean flag = dataRoleService.getDataRoleItems(companyIds, stationIds, commuityIds, UserUtils.getUserId(),
				companyId, stationId, commuityId);

		switch (type) {
		case "company":
			if (commuityIds.size() == 0) {
				SetESEqual(boolQueryBuilder, "companyId", id);
			} else {
				SetESEqualIn(boolQueryBuilder, "commuityId", commuityIds);
			}

			break;
		case "station":
			if (commuityIds.size() > 0) {
				SetESEqualIn(boolQueryBuilder, "commuityId", commuityIds);
			}
			break;
		case "commuity":
			SetESEqual(boolQueryBuilder, "commuityId", id);
			break;
		case "building":
			SetESEqual(boolQueryBuilder, "building", id);
			break;
		case "unit":
			SetESEqual(boolQueryBuilder, "unitId", id);
			break;
		case "house":
			SetESEqual(boolQueryBuilder, "consumerId", id);
			break;
		}
	}
	
	private String typeNameById(List<TypeMst> types, String typeed, String id) {
		String idname = "";
		if(id.isEmpty()){
			return idname;
		}
		if(id.contains("_")){
			idname = typeMstService.getTypeNameById(types,  id);
		}else{
			idname = typeMstService.getTypeNameById(types, typeed + "_" + id);
		}
		if(idname.isEmpty()){
			idname = id;
		}
		return idname;
	}
	
}

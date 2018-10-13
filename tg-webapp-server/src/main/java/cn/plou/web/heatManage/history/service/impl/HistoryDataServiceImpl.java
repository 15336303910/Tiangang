package cn.plou.web.heatManage.history.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import cn.plou.web.common.utils.Tools;
import cn.plou.common.utils.DateUtil;

import cn.plou.web.heatManage.history.service.HistoryDataService;
import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.config.common.Cond;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.Support;

import cn.plou.web.heatManage.history.dao.HistoryDataDao;
import cn.plou.web.heatManage.history.domain.HeatMeterDataDO;
import cn.plou.web.heatManage.history.domain.WkfDataDO;
import cn.plou.web.heatManage.history.domain.WkqDataDO;

import cn.plou.web.heatManage.monitor.domain.RunningDataTotalDO;
import cn.plou.web.system.baseMessage.house.vo.HouseInfo;
import cn.plou.web.system.baseMessage.house.vo.HouseVo;
import cn.plou.web.system.commonMessage.pageGrid.entity.PageGrid;
import cn.plou.web.system.commonMessage.pageGrid.service.PageGridService;
import cn.plou.web.system.commonMessage.pageGrid.vo.PageGridInfo;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.TypeMstService;
import cn.plou.web.TGWebApplication;

@Component
@Slf4j
public class HistoryDataServiceImpl implements HistoryDataService {

	@Autowired
	private HistoryDataDao historyDataDao;
	@Autowired
	private TypeMstService typeMstService;
	@Autowired
	private PageGridService pageGridService;
	
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
		if (map.get("order") != null && map.get("sortby") != null) {
			order = map.get("order").toString();
			sortby = map.get("sortby").toString();
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
		String page = getMapValue(map, "page");
		String pageSize = getMapValue(map, "pageSize");
		String order = getMapValue(map, "order");
		String sortby = getMapValue(map, "sortby");
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

	private Map<String,HouseInfo> mapHouses(List<HouseInfo> houses) {
		Map<String,HouseInfo> map = new HashMap<String,HouseInfo>();
		for(HouseInfo info:houses){
			map.put(info.getConsumerId(), info);
		}
		return map;
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

	Root viladHisParam(Map<String, Object> map) {
		Root root = new Root();
		if (map.get("type") == null) {
			// || map.get("order")== null || map.get("pageSize")== null ||
			// map.get("sortby") == null ||map.get("page")== null){
			root.setMsg("必要参数缺失");
			root.setCode(1);
			return root;
		}
		return root;
	}

	private String condiByType(String meterType, Map<String, Object> map) {
		String condi = null;
		// 组织相关字段
		String type = "";
		String condWord = esCondi; // todo rowno
		if (meterType.isEmpty()) {
			condWord = "consumerId";
		}
		// String condWord = "meterId"; // todo rowno
		List<String> list = null;
		type = getMapTypeVal(map);
		if (type.equals("house")) {
			list = Arrays.asList(map.get("id").toString().split(","));
			String str = Tools.genorInByList(list);
			if (!str.isEmpty()) {
				condi = condWord + " in (" + str + ")";
			}
		} else {
			if (type.equals("station")) {
				List<String> lstrs = getAreasByStationId(map.get("id").toString());
				System.out.println(lstrs.toString());
				if (lstrs.size() == 0) {
					return "";
				}
				condi = "(" + condWord + " like '" + meterType + lstrs.get(0) + "%'";
				for (int i = 1; i < lstrs.size(); i++) {
					condi += "or " + condWord + " like '" + meterType + lstrs.get(i) + "%'";
				}
				condi += ")";
				// String str = genorInByList(list);
				// select *where substr(attri,0,3) in ('123','132');

			} else {
				condi = condWord + " like '" + meterType + map.get("id").toString() + "%'";
			}
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
			strsql += " and " + condi;
		}
		return strsql;
	}

	public String filterMeterVal(String strsql, Map<String, Object> map) {
		String inTemperature = getMapValue(map, "inTemperature");
		String outTemperature = getMapValue(map, "outTemperature");
		String tmpDiff = getMapValue(map, "tmpDiff");

		String inFlow = getMapValue(map, "inFlow");
		String flowSpeed = getMapValue(map, "flowSpeed");
		String power = getMapValue(map, "power");
		String heat = getMapValue(map, "heat");
		if (inTemperature != null) {
			String[] str = inTemperature.split(",");
			strsql += " and inTemperature between " + str[0] + " and " + str[1];
		}
		if (outTemperature != null) {
			String[] str = outTemperature.split(",");
			strsql += " and outTemperature between " + str[0] + " and " + str[1];
		}
		if (tmpDiff != null) {
			String[] str = tmpDiff.split(",");
			strsql += " and inTemperature - outTemperature between " + str[0] + " and " + str[1];
		}
		if (inFlow != null) {
			String[] str = inFlow.split(",");
			strsql += " and inFlow between " + str[0] + " and " + str[1];
		}
		if (flowSpeed != null) {
			String[] str = flowSpeed.split(",");
			strsql += " and flowSpeed between " + str[0] + " and " + str[1];
		}
		if (power != null) {
			String[] str = power.split(",");
			strsql += " and power between " + str[0] + " and " + str[1];
		}
		if (heat != null) {
			String[] str = heat.split(",");
			strsql += " and heat between " + str[0] + " and " + str[1];
		}
		return strsql;
	}
	
	public List<HeatMeterDataDO> esMeterAll(Map<String, Object> map) {
		return esMeter(map, null, false);
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
	private List<HeatMeterDataDO> esMeter(Map<String, Object> map, Root root, Boolean isLimit) {
		List<HeatMeterDataDO> data = new ArrayList<HeatMeterDataDO>();
		if (isLimit) {
			Integer count = esMeterCount(map, root);
			if (count == 0) {
				return data;
			}
		}

		String meterType = "01";
		String strsql = "select * from heat_meter_data where  sysReadTimeLong >= %s and sysReadTimeLong <=%s ";

		strsql = filterTime(strsql, map);
		strsql = filterHeatingStatue(strsql, map);
		strsql = filterCondi(strsql, map, meterType);
		strsql = filterMeterVal(strsql, map);
		strsql = filterSortAndLimit(strsql, map);

		try {
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
			ex.printStackTrace();
			System.out.println("esmeter error");
		}
		return data;
	}

	private String filterTime(String strsql, Map<String, Object> map) {
		String readTime = getMapValue(map, "readTime");
		String beginTime = "";
		String endTime = "";
		if (readTime == null) {
			Date date1 = new Date(((System.currentTimeMillis() / 1000) - 3*86400) * 1000);
			Date date2 = new Date(((System.currentTimeMillis() / 1000)) * 1000);
			endTime = DateUtil.toDateTimeString(date2);
			beginTime = DateUtil.toDateTimeString(date1);
		} else {
			String[] times = readTime.split(",");
			beginTime = times[0];
			endTime = times[1];
		}
		strsql = String.format(strsql, Long.toString(DateUtil.fromString(beginTime,true).getTime()),
				Long.toString(DateUtil.fromString(endTime,true).getTime()));
		return strsql;
	}

	private String filterSortAndLimit(String strsql, Map<String, Object> map) {
		String strSortby = getMapValue(map, "sortby");
		String page = getMapValue(map, "offset");
		String pageSize = getMapValue(map, "limit");
		String order = getMapValue(map, "order");
		if (strSortby != null && !strSortby.isEmpty()) {
			strsql += " order by " + strSortby + " " + order;
		}

		if (page != null && !page.isEmpty()) {
			//Integer bPage = (Integer.parseInt(page))*Integer.parseInt(pageSize);
			strsql += " limit " + page + "," + pageSize;
		}
		return strsql;
	}

	@Override
	public Root getWkfHisData(Map<String, Object> map) {
		List<WkfDataDO> datasT = new ArrayList<>();
		return getWkfHisDataList(map, datasT);
	}

	public Root getWkfHisDataList(Map<String, Object> map, List<WkfDataDO> datasT) {
		Root root = viladHisParam(map);
		if (root.getCode() == 1) {
			return root;
		}
		
		Root dataCount = new Root();
		List<WkfDataDO> datas = esWkfData(map, dataCount, true);
		
		if (datas.size() == 0) {

			setRootCond(root, map, 0);
			root.setData(JSON.toJSON(datas));
			return root;
		}
		
		List<String> consumerIdList = new ArrayList<>();
		for(WkfDataDO data:datas){
			consumerIdList.add(data.getRowno());
		}
		Map<String, HouseInfo> mapHouses = houseInfo(map, consumerIdList);
		
		List<TypeMst> runStatetypes = typeMstByType(null);

		for (WkfDataDO dat : datas) {
			// System.out.println("------------------" + dat.getCompanyId() +
			// house.getRowno());
			HouseInfo house = mapHouses.get(dat.getConsumerId());
			dat.setRunningState(typeNameById(runStatetypes, "open_status", dat.getRunningState()));
			dat.setControlCommond(typeNameById(runStatetypes, "control_commond", dat.getControlCommond()));
			dat.setLocks(typeNameById(runStatetypes, "locks", dat.getLocks()));
			dat.setLimitStatus(typeNameById(runStatetypes, "limit_status", dat.getLimitStatus()));
			dat.setPowerProtection(typeNameById(runStatetypes, "power_protection", dat.getPowerProtection()));
			dat.setWireless(typeNameById(runStatetypes, "wire_wireless", dat.getWireless()));
			dat.setFlowGiveMode(typeNameById(runStatetypes, "flow_give_mode", dat.getFlowGiveMode()));
			dat.setCalKind(typeNameById(runStatetypes, "cal_kind", dat.getCalKind()));

			dat.setActionNow(typeNameById(runStatetypes, "action_now", dat.getActionNow()));
			dat.setProtocol(typeNameById(runStatetypes, "protocol", dat.getProtocol()));
			dat.setValveReadStatus(typeNameById(runStatetypes, "valve_read_status", dat.getValveReadStatus()));
			dat.setActionSet(typeNameById(runStatetypes, "valve_control", dat.getActionSet()));

			if (house != null) {
				dat.setSource(house.getAddress());
			}
			datasT.add(dat);
		}
		// 组合数据，将ID替换成名字，并补充对应字段
		if (datasT.size() == 0) {
			setRootCond(root, map, 0);
			root.setData(JSON.toJSON(datasT));
			return root;
		}
		if (dataCount.getData() != null) {
			try {
				Integer total = Integer.parseInt(dataCount.getData().toString());
				setRootCond(root, map, total);
				root.setData(JSON.toJSON(datasT));// dataT
			} catch (Exception ex) {

			}
		}
		return root;
	}
	
	
	private List<TypeMst> typeMstByType(String string) {
		return typeMstService.getTypeMstByTypeKbn(string, null, null, null, 1, 10000).getList();
	}

//	private List<WkfDataDO> esWkfDataTest(Map<String, Object> map, Root dataCount) {
//		dataCount.setData(100);
//		List<WkfDataDO> data = new ArrayList<WkfDataDO>();
//		String pageSize = getMapValue(map, "limit");
//		if (pageSize == null) {
//			return data;
//		}
//		Integer nPage = Integer.parseInt(pageSize);
//		for (int i = 0; i < nPage; i++) {
//			WkfDataDO dat = new WkfDataDO();
//			dat.setMeterId("0010101015421542" + i);
//			dat.setCompanyId("00101");
//			dat.setCurrntWork("正常");
//			data.add(dat);
//		}
//		return data;
//	}

	private List<WkfDataDO> esWkfData(Map<String, Object> map, Root dataCount,Boolean isLimit) {
		List<WkfDataDO> data = new ArrayList<WkfDataDO>();
		if (isLimit) {
			Integer count = esWkfCount(map, dataCount);
			if (count == 0) {
				return data;
			}
		}
		String meterType = "02";
		String tableName = "valve_data";
		String strsql = "select * from " + tableName + " where  sysReadTimeLong >= %s and sysReadTimeLong <=%s ";

		strsql = filterTime(strsql, map);
		strsql = filterCondi(strsql, map, meterType);
		strsql = filterValveVal(strsql, map);
		strsql = filterSortAndLimit(strsql, map);

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
			ex.printStackTrace();
		}
		return data;
	}

	public String filterValveVal(String strsql, Map<String, Object> map) {

		return strsql;
	}

	public String filterRoomVal(String strsql, Map<String, Object> map) {

		return strsql;
	}

	@Override
	public Root getHouseWkqHisData(Map<String, Object> map) {
		List<WkqDataDO> datasT = new ArrayList<>();
		return getHouseWkqHisDataList(map, datasT);
	}

	public Root getHouseWkqHisDataList(Map<String, Object> map, List<WkqDataDO> datasT) {
		Root root = viladHisParam(map);
		if (root.getCode() == 1) {
			return root;
		}

		Root dataCount = new Root();
		List<WkqDataDO> datas = esWkqData(map, dataCount);
		
		if (datas.size() == 0) {

			setRootCond(root, map, 0);
			root.setData(JSON.toJSON(datas));
			return root;
		}
		
		List<TypeMst> runStatetypes = typeMstByType(null);
		
		List<String> consumerIdList = new ArrayList<>();
		for(WkqDataDO data:datas){
			consumerIdList.add(data.getRowno());
		}
		Map<String, HouseInfo> mapHouses = houseInfo(map, consumerIdList);
		
		for (WkqDataDO dat : datas) {
			HouseInfo house =  mapHouses.get(dat.getConsumerId());
			System.out.println("------------------" + dat.getCompanyId() + house.getRowno());
			if (dat.getConsumerId().equals(house.getRowno())) {
				dat.setRunningState(typeNameById(runStatetypes, "meter_status", dat.getRunningState()));
				if (house != null) {
					dat.setSource(house.getAddress());
				}
				datasT.add(dat);
			}
		}

		// 组合数据，将ID替换成名字，并补充对应字段
		if (datasT.size() == 0) {
			setRootCond(root, map, 0);
			root.setData(JSON.toJSON(datasT));
			return root;
		}
		Integer total = Integer.parseInt(dataCount.getData().toString());
		setRootCond(root, map, total);
		root.setData(JSON.toJSON(datasT));
		System.out.println("-------------tiaojian + "+map.toString());
		return root;
	}
	
	private List<WkqDataDO> esWkqData(Map<String, Object> map, Root dataCount) {
		List<WkqDataDO> datas = new ArrayList<WkqDataDO>();
		Integer count = esWkqCount(map, dataCount);
		if (count == 0) {
			return datas;
		}
		String meterType = "0D";
		String tableName = "room_temperature_data";
		String strsql = "select * from " + tableName + " where  sysReadTimeLong >= %s and sysReadTimeLong <=%s ";

		strsql = filterTime(strsql, map);
		strsql = filterCondi(strsql, map, meterType);
		strsql = filterValveVal(strsql, map);
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

	private List<WkqDataDO> esWkqDataTest(Map<String, Object> map, Root dataCount) {
		dataCount.setData(100);
		List<WkqDataDO> data = new ArrayList<WkqDataDO>();
		String pageSize = getMapValue(map, "limit");
		if (pageSize == null) {
			return data;
		}
		Integer nPage = Integer.parseInt(pageSize);
		for (int i = 0; i < nPage; i++) {
			WkqDataDO dat = new WkqDataDO();
			dat.setMeterId("0010101015421542" + i);
			dat.setCompanyId("00101");
			dat.setRoomTemperature((21.6 + i / 10.0));
			dat.setSource("测试假数据");
			data.add(dat);
		}
		return data;
	}

	public Root getHouseTotalDataList(Map<String, Object> map,List<RunningDataTotalDO> datasT) {
		Root root = viladHisParam(map);
		if (root.getCode() == 1) {
			return root;
		}
		
		// List<HouseInfo> houses = houseInfo(map); address在汇总里面搞了
		Root dataCount = new Root();
		List<RunningDataTotalDO> datas = esHouseTotalData(map, dataCount, true);
		if (datas.size() == 0) {
			root.setData(JSON.toJSON(datas));
			setRootCond(root, map, 0);
			return root;
		}
		
		List<TypeMst> runStatetypes = typeMstByType(null);

		// for (HouseInfo house : houses) {
		for (RunningDataTotalDO dat : datas) {
			// System.out.println("------------------" + dat.getCompanyId() +
			// house.getRowno());
			// if (dat.getConsumerId().equals(house.getConsumerId())) {
			dat.setControlRole(typeNameById(runStatetypes, "control_role", dat.getControlRole()));
			// dat.setAddress(house.getAddress());
			dat.setOpenStatus(typeNameById(runStatetypes, "open_status", dat.getOpenStatus()));
			dat.setHeatStatus(typeNameById(runStatetypes, "heat_status", dat.getHeatStatus()));
			dat.setRegulationMode(typeNameById(runStatetypes, "regulation_mode", dat.getRegulationMode()));
			dat.setActionLimit(typeNameById(runStatetypes, "action_limit", dat.getActionLimit()));
			datasT.add(dat);
			// }
		}
		// }

		// 组合数据，将ID替换成名字，并补充对应字段
		if (datasT.size() == 0) {
			setRootCond(root, map, 0);
			root.setData(JSON.toJSON(datasT));
			return root;
		}
		Integer total = Integer.parseInt(dataCount.getData().toString());
		setRootCond(root, map, total);
		root.setData(JSON.toJSON(datasT));
		return root;
	}
	
	@Override
	public Root getHouseTotalData(Map<String, Object> map) {
		List<RunningDataTotalDO> datasT = new ArrayList<>();
		return getHouseTotalDataList(map, datasT);
	}
	
	@Override
	public List<RunningDataTotalDO> esHouseTotalDataAll(Map<String, Object> map) {
		return esHouseTotalData(map, null, false);
	}
	
	/**
	 * @param map
	 * @param dataCount
	 * @return
	 */
	private List<RunningDataTotalDO> esHouseTotalData(Map<String, Object> map, Root dataCount, Boolean isLimit) {
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

	private String filterHouseVal(String strsql, Map<String, Object> map) {
		// TODO 自动生成的方法存根
		return strsql;
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
					dataCount.setData(count);
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
	 * 温控器count
	 * 
	 * @param map
	 * @param dataCount
	 * @return
	 */
	private Integer esWkqCount(Map<String, Object> map, Root dataCount) {
		String meterType = "0D";
		String tableName = "room_temperature_data";
		String strsql = "select count(*) as count from " + tableName + " where  sysReadTimeLong >= %s and sysReadTimeLong <=%s ";

		strsql = filterTime(strsql, map);
		strsql = filterCondi(strsql, map, meterType);
		strsql = filterRoomVal(strsql, map);
		return esCount(strsql, dataCount);
	}

	/**
	 * 热表count
	 * 
	 * @param map
	 * @param dataCount
	 * @return
	 */
	private Integer esMeterCount(Map<String, Object> map, Root dataCount) {
		String meterType = "01";
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
		String meterType = "02";
		String tableName = "valve_data";
		String strsql = "select count(*) as count from " + tableName + " where  sysReadTimeLong >= %s and sysReadTimeLong <=%s ";

		strsql = filterTime(strsql, map);
		strsql = filterCondi(strsql, map, meterType);
		strsql = filterValveVal(strsql, map);
		return esCount(strsql, dataCount);
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
			if (dat.getInTemperature() != null && dat.getOutTemperature() != null)
				dat.setTmpDiff(dat.getInTemperature().subtract(dat.getOutTemperature()));
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
			dat.setActionLimit(getResultString(res, "actionLimit"));
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
			dat.setOutTemperature(getResultBigDecimal(res, "outTemperature"));
			dat.setPower(getResultBigDecimal(res, "power"));
			dat.setRegulationMode(getResultString(res, "regulationMode"));
			dat.setRoomTemperatureRead(getResultBigDecimal(res, "roomTemperatureRead"));
			dat.setRoomTemperatureSet(getResultBigDecimal(res, "roomTemperatureSet"));
			dat.setSystemReadTime(DateUtil.fromString(getResultString(res, "systemReadTime")));
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
			List<RunningDataTotalDO> datas = esHouseTotalData(map, dataCount , true);
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
						dat.setActionLimit(typeNameById(runStatetypes, "action_limit", dat.getActionLimit()));
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

	@Override
	public Object getHouseTotalDataToExcel(Map<String, Object> params, ServletRequest request) {
		
 		List<RunningDataTotalDO> datasT = new ArrayList<>();
		params.put("offset", "0");
		params.put("limit", "50000");
		getHouseTotalDataList(params, datasT);
 		return getHisDataToExcel(request, "userrunningdatahistroy", "用户汇总历史",datasT);
	}

	@Override
	public Object getWkfHisDataToExcel(Map<String, Object> params, ServletRequest request) {
 		List<WkfDataDO> datasT = new ArrayList<>();
		params.put("offset", "0");
		params.put("limit", "50000");
		getWkfHisDataList(params, datasT);
 		return getHisDataToExcel(request, "valvehistory", "阀门历史",datasT);
	}

	private Boolean getPageHisHead(HSSFCell cell, HSSFRow row, String name) {
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
	
	private <T> Boolean setObjectToCell(HSSFRow row, HSSFCell cell, T DataDO, List<PageGridInfo> infos) {
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
 		List<HeatMeterDataDO> datasT = new ArrayList<>();
		params.put("offset", "0");
		params.put("limit", "50000");
 		getMeterHisDataList(params, datasT);
 		return getHisDataToExcel(request, "heatmeterhistory", "热表历史",datasT);
	}
	
	public<T> Object getHisDataToExcel(ServletRequest request, String tableName,String desc, List<T> datasT) {
		String path = desc + DateUtil.getSysdateTimeString().replace(" ", "").replace(":", "").replace("-", "") + ".xls";
		String filePath = request.getServletContext().getRealPath("/") + path;// 文件路径
		System.out.println("路径：" + filePath);
		HSSFWorkbook workbook = new HSSFWorkbook();// 创建Excel文件(Workbook)
		HSSFSheet sheet = workbook.createSheet(desc);// 创建工作表(Sheet)
		HSSFRow row = sheet.createRow(0);// 创建行,从0开始
		HSSFCell cell = row.createCell(0);// 创建行的单元格,也是从0开始
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
		return path;
	}

	@Override
	public Object getHouseWkqHisDataToExcel(Map<String, Object> params, ServletRequest request) {
 		List<WkqDataDO> datasT = new ArrayList<>();
		params.put("offset", "0");
		params.put("limit", "50000");
		getHouseWkqHisDataList(params, datasT);
 		return getHisDataToExcel(request, "roomhistory", "室内温度采集历史",datasT);
	}
	
	
	public Root getMeterHisDataList(Map<String, Object> map, List<HeatMeterDataDO> datasT) {
		// String beginTime,String endTime,String heatStatus,String type,String
		// id，String useHeatStatus,String houseType
		Root root = viladHisParam(map);
		if (root.getCode() == 1) {
			return root;
		}

		Root datCount = new Root();
		Long time1 = DateUtil.getSysdateLong();
		System.out.println("------------开始");
		List<HeatMeterDataDO> datas = esMeter(map, datCount, true);
		Long time2 = DateUtil.getSysdateLong();
		System.out.println("------------esMeter:" + (time2 - time1));

		// 组合数据，将ID替换成名字，并补充对应字段
		if (datas.size() == 0) {

			setRootCond(root, map, 0);
			root.setData(JSON.toJSON(datas));
			return root;
		}
		
		List<TypeMst> runStatetypes = typeMstByType("meter_status");
		List<String> consumerIdList = new ArrayList<>();
		for(HeatMeterDataDO data:datas){
			if(data.getMeterId() != null)
			consumerIdList.add(data.getMeterId());	//todo rowno;
		}
		 
		Map<String, HouseInfo> mapHouses = houseInfo(map, consumerIdList);
		Long time3 = DateUtil.getSysdateLong();
		System.out.println("------------houseInfo:" + (time3 - time2));
		for (HeatMeterDataDO dat : datas) {
			HouseInfo house = null;
			if (dat.getUsingId() != null) {
				house =  mapHouses.get(dat.getUsingId());
				dat.setAreas(house.getHeatingArea().toString());
				dat.setSource(house.getAddress());
				dat.setTmpDiff((dat.getInTemperature().subtract(dat.getOutTemperature())));
				datasT.add(dat);
				break;
			} else {
				// String data1 = house.getRowno();
				// String data2 = dat.getMeterId().substring(2,21);
				// System.out.println(data1 +" "+data2);
				house = mapHouses.get(dat.getMeterId().substring(2, 21));
				if(house == null){
					continue; 
				}

				System.out.println("------------------" + house.getRowno() + "-------------" + house.getAddress());
				if(house.getHeatingArea() != null){
					dat.setAreas(house.getHeatingArea().toString());
				}
				
				dat.setSource(house.getAddress());
				if (dat.getOutTemperature() != null) {
					dat.setTmpDiff((dat.getInTemperature().subtract(dat.getOutTemperature())));
				} else {
					dat.setTmpDiff(dat.getInTemperature());
				}
				dat.setRunningState(typeNameById(runStatetypes, "meter_status", dat.getRunningState()));
				datasT.add(dat);
			}
		}
		Integer total = Integer.parseInt(datCount.getData().toString());
		setRootCond(root, map, total);
		root.setData(JSON.toJSON(datasT)); 
		if(total > 50000){
			root.setMsg("查询数据量过大，请缩小查询范围！");
			root.setCode(1);
		}
		Long time4 = DateUtil.getSysdateLong();
		System.out.println("------------完成:" + (time4 - time3)+ "总计" + (time4 - time1));
		return root;
	}
}

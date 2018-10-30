package cn.plou.web.heatManage.dataAnalysis.service.impl;

import cn.plou.common.utils.DateUtil;
import cn.plou.web.common.config.common.Cond;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.Support;
import cn.plou.web.common.utils.Tools;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.heatManage.dataAnalysis.domain.SummaryCountDataDO;
import cn.plou.web.heatManage.dataAnalysis.domain.WaterConditionDO;
import cn.plou.web.heatManage.dataAnalysis.service.SummaryCountService;
import cn.plou.web.heatManage.dataAnalysis.service.WaterConditionService;
import cn.plou.web.heatManage.dataAnalysis.vo.WaterConditionVO;
import cn.plou.web.heatManage.history.dao.HistoryDataDao;
import cn.plou.web.heatManage.dataAnalysis.domain.SummaryCountDataDO;
import cn.plou.web.heatManage.monitor.domain.RunningDataTotalDO;
import cn.plou.web.system.baseMessage.house.dao.HouseMapper;
import cn.plou.web.system.baseMessage.house.service.HouseService;
import cn.plou.web.system.baseMessage.house.vo.HouseInfo;
import cn.plou.web.system.baseMessage.house.vo.HouseVo;
import cn.plou.web.system.baseMessage.system.service.SystemService;
import cn.plou.web.system.baseMessage.system.vo.SystemInfo;
import cn.plou.web.system.baseMessage.unit.service.UnitService;
import cn.plou.web.system.commonMessage.pageGrid.service.PageGridService;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.TypeMstService;
import cn.plou.web.system.permission.role.service.DataRoleService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SummaryCountServiceImpl implements SummaryCountService {

	@Autowired
	HouseService houseService;
	@Autowired
	SystemService systemService;

	@Autowired
	private HistoryDataDao historyDataDao;

	@Autowired
	private HouseMapper houseMapper;

	@Autowired
	private TypeMstService typeMstService;
	@Autowired
	private PageGridService pageGridService;
	@Autowired
	private DataRoleService dataRoleService;
	@Autowired
	private UnitService unitService;

	private String esCondi = "meterId";
	private String sqlCondi = "meter_id";

	/**
	 * 根据条件查找房屋信息
	 *
	 * @param map
	 * @return
	 */
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
		List<HouseInfo> houses = houseMapper.selectAllHouse(companyIds, stationIds, commuityIds, buildingNo, unitId, houseVo,
				order, sortby,1,50000);
		Map<String, HouseInfo> mapHouses = mapHouses(houses);
		return mapHouses;
	}

	@Override
	public Root getSummaryCountData(Map<String, Object> map) {
		List<SummaryCountDataDO> datasT = new ArrayList<>();
		return getSummaryCountDataList(map, datasT);
	}


	public Root getSummaryCountDataList(Map<String, Object> map, List<SummaryCountDataDO> datasT) {
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
		List<SummaryCountDataDO> datas = esSummaryCountDataList(map, datCount, true);
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
		for(SummaryCountDataDO data:datas){
			if(data.getConsumerId() != null)
				consumerIdList.add(data.getConsumerId());	//todo rowno;
		}
		//List<Unit>  unitlist = unitService.get
		Map<String, HouseInfo> mapHouses = houseInfo(map, consumerIdList);
		Long time3 = DateUtil.getSysdateLong();
		System.out.println("------------houseInfo:" + (time3 - time2));
		for (SummaryCountDataDO dat : datas) {
			HouseInfo house = null;
			String type = "";
			// 组织相关字段
			type = getMapTypeVal(map);

			house = mapHouses.get(dat.getConsumerId());
			if(house == null){
				continue;
			}
			if (type.equals("company")) {
				dat.setAddress(house.getCommuityName());//其实应该统计到站，但是我统计到小区了
			} else if (type.equals("station")) {
				dat.setAddress(house.getCommuityName());//统计到小区
			} else if (type.equals("commuity")) {
				dat.setAddress(house.getBuildingName());//统计到楼
			} else if (type.equals("building")) {
				dat.setAddress(house.getAddress());//统计到户
			}else {
				dat.setAddress(house.getAddress());//统计到户
			}


				System.out.println("------------------" + house.getRowno() + "-------------" + house.getAddress());

				datasT.add(dat);
		}
		Integer total = Integer.parseInt(datCount.getData().toString());
		setRootCond(root, map, total);
		root.setData(JSON.toJSON(datasT));

		Long time4 = DateUtil.getSysdateLong();
		System.out.println("------------完成:" + (time4 - time3)+ "总计" + (time4 - time1));
		return root;
	}

	private List<TypeMst> typeMstByType(String string) {
		return typeMstService.getTypeMstByTypeKbn(string, null, null, null, 1, 10000).getList();
	}


	/**
	 * 热表count
	 *
	 * @param map
	 * @param dataCount
	 * @return
	 */
	private Integer esSummaryCountDataListCount(Map<String, Object> map, Root dataCount) {
		String meterType = "01";
		String strsql = "select count(*) as count from user_running_data_histroy ";


		strsql = filterGroup(strsql, map);
		strsql+="where  sysReadTimeLong >= %s and sysReadTimeLong <=%s ";//"select count(*) as count from heat_meter_data where  sysReadTimeLong >= %s and sysReadTimeLong <=%s ";

		strsql = filterTime(strsql, map);
		strsql = filterHeatingStatue(strsql, map);
		strsql = filterCondi(strsql, map, meterType);
		strsql = filterMeterVal(strsql, map);

		return esCount(strsql, dataCount);
	}

	private List<SummaryCountDataDO> esSummaryCountDataList(Map<String, Object> map, Root root, Boolean isLimit) {
		List<SummaryCountDataDO> data = new ArrayList<SummaryCountDataDO>();
		if (isLimit) {
			Integer count = esSummaryCountDataListCount(map, root);
			if (count == 0) {
				return data;
			}
		}
		String type = "";
		// 组织相关字段
		type = getMapTypeVal(map);

		String consumerId = "";
		if (type.equals("company")) {
			consumerId= "  substring(consumer_id,1,10) as consumerId, ";//其实应该统计到站，但是我统计到小区了
		} else if (type.equals("station")) {
			consumerId= "  substring(consumer_id,1,10) as consumerId, ";//统计到小区
		} else if (type.equals("commuity")) {
			consumerId= "  substring(consumer_id,1,13) as consumerId, ";//统计到楼
		} else if (type.equals("building")) {
			consumerId= " consumer_id as consumerId ";//统计到户
		} else {
			consumerId= " consumer_id as consumerId ";//统计到户
		}


		String meterType = "01";
		String strsql = "select "+consumerId;

		strsql+=" CONCAT(min(systemReadTime), ' - ',max(systemReadTime)) as systemReadTime,"+
				" sum(heat) as heat, sum(heatingArea) as heatingArea,avg(flowSpeed) as flowSpeed "+
				" avg(roomTemperature) as roomTemperature, avg(outdoorTemperature) as outdoorTemperature,sum(heatingIndex) as heatingIndex "+
				" avg(adjHeatingIndex) as adjHeatingIndex, avg(flowingIndex) as flowingIndex "+
				" from user_running_data_histroy ";

		strsql = filterGroup(strsql, map);

		strsql+="where  sysReadTimeLong >= %s and sysReadTimeLong <=%s ";

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
				SummaryCountDataDO dat = orderHeatMeterData(res);
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


	/**
	 * 热表数据
	 *
	 * @param res
	 * @return
	 */
	private SummaryCountDataDO orderHeatMeterData(ResultSet res) {
		SummaryCountDataDO dat = null;
		try {
			dat = new SummaryCountDataDO();
			dat.setConsumerId(getResultString(res, "consumerId"));
			dat.setSystemReadTime(getResultString(res, "systemReadTime"));
			dat.setHeat(getResultBigDecimal(res, "heat"));
			dat.setHeatingArea(getResultBigDecimal(res, "heatingArea"));
			dat.setFlowSpeed(getResultBigDecimal(res, "flowSpeed"));
			dat.setRoomTemperature(getResultBigDecimal(res, "roomTemperature"));
			dat.setOutdoorTemperature(getResultBigDecimal(res, "outdoorTemperature"));
			dat.setHeatingIndex(getResultBigDecimal(res, "heatingIndex"));
			dat.setAdjHeatingIndex(getResultBigDecimal(res, "adjHeatingIndex"));
			dat.setFlowingIndex(getResultBigDecimal(res, "flowingIndex"));

		} catch (Exception e) {
			//log.error(e.getMessage());
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

	private String filterTime(String strsql, Map<String, Object> map) {
		String readTime = getMapValue(map, "readTime");
		String beginTime = "";
		String endTime = "";
		if (readTime == null) {
			Date date1 = new Date(((System.currentTimeMillis() / 1000) - 3*24*60*60) * 1000);
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
		//权限控制
		strsql = filterRoles(strsql, map);
		return strsql;
	}

	private String filterRoles(String sql, Map<String, Object> map) {
		List<String> roles = new ArrayList<>();
		List<String> companyIds = null;
		if(map.get("companyIds") != null){
			companyIds = (ArrayList<String>)(map.get("companyIds"));
			for(String str: companyIds){
				roles.add("consumerId like '" + str + "%'");
			}
		}
		List<String> commuityIds = null;
		if(map.get("commuityIds") != null){
			commuityIds = (ArrayList<String>)(map.get("commuityIds"));
			for(String str: commuityIds){
				roles.add("consumerId like '" + str + "%'");
			}
		}
		if(roles.size() > 0){
			String str = "and (" + roles.get(0);
			for(int i = 1; i < roles.size(); i++){
				str =str + " or " + roles.get(i);
			}
			str += ") ";
			sql += str;
		}
		return sql;
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


	public String filterGroup(String strsql, Map<String, Object> map) {

		String type = "";
		// 组织相关字段
		type = getMapTypeVal(map);

		if (type.equals("company")) {
			strsql += " group by substring(consumer_id,1,10) ";//其实应该统计到站，但是我统计到小区了
		} else if (type.equals("station")) {
			strsql += " group by substring(consumer_id,1,10) ";//统计到小区
		} else if (type.equals("commuity")) {
			strsql += " group by substring(consumer_id,1,13) ";//统计到楼
		} else if (type.equals("building")) {
			strsql += " group by consumer_id ";//统计到户
		}else {
			strsql += " group by consumer_id ";//统计到户
		}
 		return strsql;
	}

	private String filterSortAndLimit(String strsql, Map<String, Object> map) {
		String strSortby = getMapValue(map, "sort");
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
		} else if (type.equals("unit") || type.equals("build") || type.equals("building")){
			condi = condWord + " like '" + meterType + map.get("id").toString() + "%'";
		}
		return condi;
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
		String page = getMapValue(map, "offset");
		String pageSize = getMapValue(map, "limit");
		if(page !=  null && pageSize != null){
			if(Integer.parseInt(page)+Integer.parseInt(pageSize) > 50000){
				root.setMsg("查询数据量过大，请缩小查询范围！");
				root.setCode(1);
			}
		}
		return root;
	}


	private String getUnitAddr(String address) {
		String[] addrs = address.split(" ");
		String addr = "";
		for(int i = 0 ; i < addrs.length-1; i++){
			addr = addrs[i]+ " ";
		}
		return addr;
	}

	private void setRootCond(Root root, Map<String, Object> map, Integer total) {
		String page = getMapValue(map, "page");
		String pageSize = getMapValue(map, "pageSize");
		String order = getMapValue(map, "order");
		String sortby = getMapValue(map, "sortby");

		root.setCond(Cond.getCond(Integer.getInteger(page), Integer.getInteger(pageSize), total, (String) order, (String) sortby));
	}
	
	private String getMapValue(Map<String, Object> map, String mapName) {
		return Tools.getMapValue(map, mapName);
	}

	
	private String getUnitidbyConsumerAndSystem(String consumerId, String systemId){
		return consumerId.substring(0, 15) + "_"+systemId;
	}


}

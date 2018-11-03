package cn.plou.web.heatManage.dataAnalysis.service.impl;

import cn.plou.common.utils.DateUtil;
import cn.plou.common.utils.StringUtil;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.RedisUtil;
import cn.plou.web.common.utils.Support;
import cn.plou.web.common.utils.Tools;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.heatManage.dataAnalysis.dao.HouseRunningDataAnalysisTotalDao;
import cn.plou.web.heatManage.dataAnalysis.domain.*;
import cn.plou.web.heatManage.dataAnalysis.service.HouseRunningDataAnalysisService;
import cn.plou.web.heatManage.dataAnalysis.vo.HouseTotalDataAnalysisParamRangeInfo;
import cn.plou.web.heatManage.monitor.dao.RunningDataTotalDao;
import cn.plou.web.system.baseMessage.build.vo.BuildInfo;
import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import cn.plou.web.system.baseMessage.house.entity.House;
import cn.plou.web.system.baseMessage.unit.entity.Unit;
import cn.plou.web.system.permission.role.service.DataRoleService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class HouseRunningDataAnalysisServiceImpl implements HouseRunningDataAnalysisService {
	@Autowired
	private HouseRunningDataAnalysisTotalDao houseRunningDataAnalysisTotalDao;

	@Autowired
	private DataRoleService dataRoleService;

	private String esCondi = "meterId";
	
	@Override
	public List<HouseRunningDataAnalysisTotalGatherDO> getHouseTotalData(Map<String, Object> map) {
		// TODO Auto-generated method stub


		Boolean flag = dataRoleService.getDataRoleItemsType(UserUtils.getUserId(), map);

		Root datCount = new Root();
		return esHouseTotalDataList(map, datCount);
		//return houseRunningDataAnalysisTotalDao.getHouseTotalData(map);
	}


	private List<HouseRunningDataAnalysisTotalGatherDO> esHouseTotalDataList(Map<String, Object> map, Root root) {
		List<HouseRunningDataAnalysisTotalGatherDO> data = new ArrayList<HouseRunningDataAnalysisTotalGatherDO>();

		List<HouseTotalDataAnalysisParamRangeInfo> rangeLst = (List<HouseTotalDataAnalysisParamRangeInfo>)map.get("range");

		String type = "";
		// 组织相关字段
		type = getMapTypeVal(map);



		String meterType = "01";
		String strsql = "";

		List<String> companyIds = (List<String>)map.get("companyIds");
		List<String> commuityIds = (List<String>)map.get("commuityIds");

		// 组织相关字段
		type = getMapTypeVal(map);

		String standard = getMapValue(map, "standard");


		String startTime = getMapValue(map, "startTime");
		String endTime = getMapValue(map, "endTime");
		startTime = Long.toString(DateUtil.fromString(startTime,true).getTime());
		endTime = Long.toString(DateUtil.fromString(endTime,true).getTime());



		int i = 0;
		for (HouseTotalDataAnalysisParamRangeInfo houseTotalDataAnalysisParamRangeInfo : rangeLst) {

			Integer min = houseTotalDataAnalysisParamRangeInfo.getMin();
			Integer max = houseTotalDataAnalysisParamRangeInfo.getMax();

			strsql="select ";
			//strsql+="select substr(consumerId,1,5) as c2 ,";
			//不支持这种语法
//			if(min == null){
//				strsql+=" concat('<','"+max+"')  as paramtitle,";
//			}else if(max == null){
//				strsql+=" concat('>','"+min+"')  as paramtitle,";
//			}else{
//				strsql+=" concat('"+min+"',' - ','"+max+"')  as paramtitle,";
//			}

			strsql+=" count(consumerId) as countConsumer,avg(heatingIndex) as heatingIndex,avg(flowingIndex) as flowingIndex, avg(inTemperature) as inTemperature," +
					//" avg(outTemperature) as outTemperature ,(max(roomTemperature)-min(roomTemperature)) as temperatureDiff,"+
					" avg(outTemperature) as outTemperature ,"+
					" avg(roomTemperature) as roomTemperature " +
					" from user_running_data_history " +
					" where 1=1 ";

			switch (standard) {
				case "temperature"://室温指标
					if (min == null) {
						strsql += " and inTemperature < " + max + " ";
					} else if (max == null) {
						strsql += " and inTemperature > " + min + " ";
					} else {
						strsql += " and inTemperature > " + min + " " + " and inTemperature < " + max + " ";
					}

					break;
				case "heating"://耗热指标

					if (min == null) {
						strsql += " and heatingIndex < " + max + " ";
					} else if (max == null) {
						strsql += " and heatingIndex > " + min + " ";
					} else {
						strsql += " and heatingIndex > " + min + " " + " and heatingIndex < " + max + " ";
					}

					break;
				case "flowing"://流量指标

					if (min == null) {
						strsql += " and flowingIndex < " + max + " ";
					} else if (max == null) {
						strsql += " and flowingIndex > " + min + " ";
					} else {
						strsql += " and flowingIndex > " + min + " " + " and flowingIndex < " + max + " ";
					}

					break;
			}

			strsql+=" and sysReadTimeLong  > "+startTime+" and  sysReadTimeLong < "+endTime+" ";

			if(map.get("companyIds") != null){
				companyIds = (ArrayList<String>)(map.get("companyIds"));
				for(String str: companyIds){
					strsql+=" and consumerId like '" + str + "%' ";
				}
			}

			if(map.get("commuityIds") != null){
				commuityIds = (ArrayList<String>)(map.get("commuityIds"));
				for(String str: commuityIds){
					strsql+=" and consumerId like '" + str + "%' ";
				}
			}


			try {
				ResultSet res = Support.querryFromEs(strsql);
				if (res == null) {
					return data;
				}
				while (res.next()) {
					HouseRunningDataAnalysisTotalGatherDO dat = orderSummaryCountData(res);
					if (dat != null) {
						data.add(dat);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("esmeter error");
			}


		}

		return data;
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


	private String getMapValue(Map<String, Object> map, String mapName) {
		return Tools.getMapValue(map, mapName);
	}


	private HouseRunningDataAnalysisTotalGatherDO orderSummaryCountData(ResultSet res) {
		HouseRunningDataAnalysisTotalGatherDO dat = null;
		try {
			dat = new HouseRunningDataAnalysisTotalGatherDO();
			dat.setParamTitle(getResultString(res, "paramtitle"));
			dat.setConsumerCount(getResultLong(res, "countConsumer"));
			dat.setHeatingIndex(getResultBigDecimal(res, "heatingIndex"));
			dat.setFlowingIndex(getResultBigDecimal(res, "flowingIndex"));
			dat.setInTemperature(getResultBigDecimal(res, "inTemperature"));
			dat.setOutTemperature(getResultBigDecimal(res, "outTemperature"));
			dat.setRoomTemperatureRead(getResultBigDecimal(res, "roomTemperature"));
			dat.setRoomTemperatureDifference(getResultBigDecimal(res, "temperatureDiff"));
		} catch (Exception e) {
			//log.error(e.getMessage());
			System.out.println("esdata read error");
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



	@Override
	public PageInfo<HouseTotalDataAnalysisStatisticsIndexDO> getHouseTotalDataAnalysisStatisticsIndex(Map<String, Object> map) {
		// TODO Auto-generated method stub
		int page = (Integer) map.get("page");
		int pageSize = (Integer) map.get("pageSize");
		PageHelper.startPage(page,pageSize);
		List<HouseTotalDataAnalysisStatisticsIndexDO> houseTotalDataAnalysisStatisticsIndexLst = houseRunningDataAnalysisTotalDao.getHouseTotalDataAnalysisStatisticsIndex(map);
		PageInfo<HouseTotalDataAnalysisStatisticsIndexDO> pageInfo=new PageInfo<>(houseTotalDataAnalysisStatisticsIndexLst);
		return pageInfo;
	}


	@Override
	public List<HouseTotalDataAnalysisHotDistributeDO> getHouseTotalDataAnalysisHotDistribute(Map<String, Object> map) {
		// TODO Auto-generated method stub

		Boolean flag = dataRoleService.getDataRoleItemsType(UserUtils.getUserId(), map);

		Root datCount = new Root();
		return houseTotalDataAnalysisHotDistributeList(map, datCount);
		//return houseRunningDataAnalysisTotalDao.getHouseTotalDataAnalysisHotDistribute(map);
	}


	private List<HouseTotalDataAnalysisHotDistributeDO> houseTotalDataAnalysisHotDistributeList(Map<String, Object> map, Root root) {
		List<HouseTotalDataAnalysisHotDistributeDO> data = new ArrayList<HouseTotalDataAnalysisHotDistributeDO>();




//		if (isLimit) {
//			Integer count = esSummaryCountDataListCount(map, root);
//			if (count == 0) {
//				return data;
//			}
//		}





		String meterType = "01";
		String strsql = "";

		strsql = filterHotDistributeUnion(strsql, map);

//		strsql = filterTime(strsql, map);
//		strsql = filterHeatingStatue(strsql, map);
//		strsql = filterCondi(strsql, map, meterType);




		try {
			ResultSet res = Support.querryFromEs(strsql);
			if (res == null) {
				return data;
			}
			while (res.next()) {
				HouseTotalDataAnalysisHotDistributeDO dat = orderHotDistributeData(res);
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




	private HouseTotalDataAnalysisHotDistributeDO orderHotDistributeData(ResultSet res) {
		HouseTotalDataAnalysisHotDistributeDO dat = null;
		Jedis jedis = null;
		RedisUtil redisUtil = RedisUtil.getSingleton();
		try {

				jedis = redisUtil.getJedis();

			dat = new HouseTotalDataAnalysisHotDistributeDO();


			dat.setConsumerId(getResultString(res, "consumerId"));
			dat.setDataRead(getResultBigDecimal(res, "dataRead"));


			int consumerLen=dat.getConsumerId().length();
			String key="";
//			if (consumerLen == 19) {
//				key = "houseinfo_" + dat.getConsumerId();
//			}
//			if (consumerLen == 15) {
//				key = "unitinfo_" + dat.getConsumerId();
//			}
			if (consumerLen == 13) {
				key = "buildinfo_" + dat.getConsumerId();
			}
			if (consumerLen == 10) {
				key = "commuityinfo_" + dat.getConsumerId();
			}

			String keys = jedis.get(key);
			if (StringUtil.hasValue(keys)) {
				JSONObject jsonObject = JSON.parseObject(keys);
//				if (consumerLen == 19){
//					House house = jsonObject.toJavaObject(House.class);
//					dat.setLatitude(house.getLatitude());
//					dat.setLongitude(house.getLongitude());
//				}
//				if (consumerLen == 15) {
//					Unit unit = jsonObject.toJavaObject(Unit.class);
//					data.setSource(unit.getAddress());
//				}
				if (consumerLen == 13) {
					BuildInfo buildInfo = jsonObject.toJavaObject(BuildInfo.class);
					dat.setLatitude(buildInfo.getLatitude());
					dat.setLongitude(buildInfo.getLongitude());
				}
				if (consumerLen == 10) {
					Commuity commuity = jsonObject.toJavaObject(Commuity.class);
					dat.setLatitude(commuity.getLatitude());
					dat.setLongitude(commuity.getLongitude());
				}
			}

		} catch (Exception e) {
			//log.error(e.getMessage());
			System.out.println("esdata read error");
		}
		return dat;
	}

	public String filterHotDistributeUnion(String strsql, Map<String, Object> map) {

		List<HouseTotalDataAnalysisParamRangeInfo> rangeLst = (List<HouseTotalDataAnalysisParamRangeInfo>)map.get("range");

		List<String> companyIds = (List<String>)map.get("companyIds");
		List<String> commuityIds = (List<String>)map.get("commuityIds");

		String type = "";
		// 组织相关字段
		type = getMapTypeVal(map);

		String consumerId = "";
		if (type.equals("company")) {
			consumerId= "  commuityId as consumerId, ";//其实应该统计到站，但是我统计到小区了
		} else if (type.equals("station")) {
			consumerId= "  commuityId as consumerId, ";//统计到小区
		} else if (type.equals("commuity")) {
			consumerId= "  buildId as consumerId, ";//统计到楼
		}
//		else if (type.equals("building")) {
//			consumerId= " consumerId as consumerId ";//统计到户
//		} else {
//			consumerId= " consumerId as consumerId ";//统计到户
//		}

		String standard = getMapValue(map, "standard");


		String startTime = getMapValue(map, "startTime");
		String endTime = getMapValue(map, "endTime");
//		String consumerId = "";
//		if (type.equals("company")) {
//			consumerId= "  substring(consumerId,1,10) as consumerId, ";//其实应该统计到站，但是我统计到小区了
//		} else if (type.equals("station")) {
//			consumerId= "  substring(consumerId,1,10) as consumerId, ";//统计到小区
//		} else if (type.equals("commuity")) {
//			consumerId= "  substring(consumerId,1,13) as consumerId, ";//统计到楼
//		} else if (type.equals("building")) {
//			consumerId= " consumerId as consumerId ";//统计到户
//		} else {
//			consumerId= " consumerId as consumerId ";//统计到户
//		}


			strsql+="select "+consumerId;


		switch (standard) {
			case "temperature"://室温指标

				strsql+=" avg(roomTemperature) AS dataRead ";
				break;
			case "heating"://耗热指标
				strsql+=" avg(heatingIndex) AS dataRead ";
				break;
			case "flowing"://流量指标
				strsql+=" avg(flowingIndex) AS dataRead ";
				break;
			case "devError"://设备故障
				//这个不好统计
				strsql+=" avg(roomTemperature) AS dataRead ";
				break;
			case "communicError"://通讯故障
				//这个不好统计
				strsql+=" avg(roomTemperature) AS dataRead ";
				break;
		}

		strsql+=" FROM  user_running_data_history WHERE 1 = 1 ";
			strsql+=" and systemReadTime  > '"+startTime+"' and  systemReadTime < '"+endTime+"' ";

			if(map.get("companyIds") != null){
				companyIds = (ArrayList<String>)(map.get("companyIds"));
				for(String str: companyIds){
					strsql+=" and consumerId like '" + str + "%' ";
				}
			}

			if(map.get("commuityIds") != null){
				commuityIds = (ArrayList<String>)(map.get("commuityIds"));
				for(String str: commuityIds){
					strsql+=" and consumerId like '" + str + "%' ";
				}
			}

		strsql+=" group by  ";

		if (type.equals("company")) {
			strsql+=" commuityId  ";//其实应该统计到站，但是我统计到小区了
		} else if (type.equals("station")) {
			strsql+=" commuityId  ";//统计到小区
		} else if (type.equals("commuity")) {
			strsql+=" buildId  ";//统计到楼
		}

//		else if (type.equals("building")) {
//			strsql+= " consumerId ";//统计到户
//		} else {
//			strsql+= " consumerId ";//统计到户
//		}

		return strsql;
	}

	@Override
	public HeatingStartAndEndTimeDO getHeatingStartAndEndTime(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return houseRunningDataAnalysisTotalDao.getHeatingStartAndEndTime(map);
	}

}

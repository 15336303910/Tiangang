package cn.plou.web.heatManage.monitor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.plou.web.common.utils.PageUtils;
import cn.plou.web.common.utils.Query;
import cn.plou.web.common.utils.R;
import cn.plou.web.heatManage.monitor.domain.RunningDataTotalDO;
import cn.plou.web.heatManage.monitor.service.RunningDataTotalService;
import cn.plou.web.heatManage.monitor.vo.BuildingRunningDataTotalVO;
import cn.plou.web.system.baseMessage.build.entity.Build;
import cn.plou.web.system.baseMessage.build.service.BuildService;
import cn.plou.web.system.baseMessage.build.vo.BuildInfo;
import cn.plou.web.system.baseMessage.commuity.dao.CommuityMapper;
import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import cn.plou.web.system.baseMessage.commuity.service.CommuityService;
import cn.plou.web.system.baseMessage.commuity.vo.CommuityInfo;
import cn.plou.web.system.baseMessage.company.entity.Company;
import cn.plou.web.system.baseMessage.company.service.CompanyService;
import cn.plou.web.system.baseMessage.station.entity.Station;
import cn.plou.web.system.baseMessage.station.service.StationService;

/**
 * 用户运行数据汇总
 * 
 * @author liuxiadong
 * @Date 2018年6月29日 上午9:40:34
 */

@RestController
@RequestMapping("${heatManagePath}/monitor/building")
public class BuildingRunningDataController {
	@Autowired
	private RunningDataTotalService runningDataTotalService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private StationService stationService;

	@Autowired
	private CommuityService commuityService;

	@Autowired
	private BuildService buildService;

	@Autowired
	private CommuityMapper commuityMapper;

	/**
	 * 楼图-小区和楼信息
	 * 
	 * @param companyId
	 * @param stationId
	 * @param communityId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@GetMapping("/getBuildingRealTimeData/{type}/{id}/{beginTime}/{endTime}")
	public Object getBuildingRealTimeData(@PathVariable("type") String type, @PathVariable("id") String id,
			@PathVariable("beginTime") String beginTime, @PathVariable("endTime") String endTime) {
		String companyId = "";
		String stationId = "";
		String communityId = "";
		switch (type) {
		case "company":
			companyId = id;
			break;
		case "station":
			stationId = id;
			break;
		case "commuity":
			communityId = id;
			break;
		}

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("type", type);
		params.put("consumerId", id);
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);

		JSONObject jsonObject = new JSONObject();

		Company company = new Company();
		if (StringUtils.isNotBlank(companyId)) {
			company = companyService.get(companyId);
			jsonObject.put("companyName", company.getCompanyName());
		}
		Station station = new Station();
		if (StringUtils.isNotBlank(stationId)) {
			station = stationService.getStationById(stationId);
			jsonObject.put("stationName", station.getStationName());
		}
		
		Commuity comuity = new Commuity();
		if (StringUtils.isNotBlank(communityId)) {
			comuity = commuityService.getCommuityById(communityId);
			jsonObject.put("communityName", comuity.getCommuityName());
		}

		int totalCommunityCnts = runningDataTotalService.getCommunityCount(params);
		int totalBuildingCnts = runningDataTotalService.getBuildingCount(params);

		jsonObject.put("totalCommunityCnts", totalCommunityCnts);
		jsonObject.put("totalBuildingCnts", totalBuildingCnts);

		List<BuildingRunningDataTotalVO> communityList = runningDataTotalService.getAllCommunity(params);
		
		JSONArray commArray = new JSONArray();
		for (BuildingRunningDataTotalVO comm : communityList) {
			List<BuildingRunningDataTotalVO> buildList = runningDataTotalService.getAllBuilding(comm.getCommunityId());
			JSONObject jsonCommunityObject = new JSONObject();
			jsonCommunityObject.put("communityName", comm.getCommunityName());
			jsonCommunityObject.put("buildingCnts", buildList.size());
			JSONArray buildingArray = new JSONArray();
			for (BuildingRunningDataTotalVO binfo : buildList) {
				params.put("consumerId", binfo.getBuildingId());
				BuildingRunningDataTotalVO vo = runningDataTotalService.buildingDataVo(params);
				JSONObject buildingObject = new JSONObject();
				buildingObject.put("buildingId", binfo.getBuildingId());
				buildingObject.put("buildingName", binfo == null ? "" : binfo.getBuildingName());
				buildingObject.put("inWaterTmp", vo==null?"":vo.getInWaterTmp());
				buildingObject.put("outWaterTmp", vo==null?"":vo.getOutWaterTmp());
				buildingObject.put("flow", vo==null?"":vo.getFlow());
				buildingObject.put("power", vo==null?"":vo.getPower());
				
				buildingArray.add(buildingObject);
			}
			jsonCommunityObject.put("buildingDataList", buildingArray);
			commArray.add(jsonCommunityObject);
		}
		jsonObject.put("communityDataList", commArray);
		
		/*List<BuildingRunningDataTotalVO> runningDataTotalList = runningDataTotalService.listByBuilding(params);

		// 户数组
		JSONArray jsonArray = new JSONArray();
		for (BuildingRunningDataTotalVO data : runningDataTotalList) {
			JSONObject jsonBuildingObject = new JSONObject();
			// 一户的信息
			Commuity community = commuityService.getCommuityById(data.getCommunityId());
			jsonBuildingObject.put("communityName", community.getCommuityName());

			params.put("type", "commuity");
			params.put("consumerId", data.getCommunityId());
			int buildingCnts = runningDataTotalService.getBuildingCount(params);
			jsonBuildingObject.put("buildingCnts", buildingCnts);

			Build build = buildService.getBuildById(data.getBuildingId());
			jsonBuildingObject.put("buildingName", build == null ? "" : build.getBuildingName());
			jsonBuildingObject.put("inWaterTmp", data.getInWaterTmp());
			jsonBuildingObject.put("outWaterTmp", data.getOutWaterTmp());
			jsonBuildingObject.put("flow", data.getFlow());
			jsonBuildingObject.put("power", data.getPower());

			jsonArray.add(jsonBuildingObject);
		}

		jsonObject.put("data", jsonArray);*/

		return jsonObject;
	}

}

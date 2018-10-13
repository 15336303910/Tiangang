package cn.plou.web.heatManage.dataAnalysis.controller;

import cn.plou.web.common.config.common.Cond;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.heatManage.dataAnalysis.domain.HeatingStartAndEndTimeDO;
import cn.plou.web.heatManage.dataAnalysis.domain.HouseRunningDataAnalysisTotalGatherDO;
import cn.plou.web.heatManage.dataAnalysis.domain.HouseTotalDataAnalysisHotDistributeDO;
import cn.plou.web.heatManage.dataAnalysis.domain.HouseTotalDataAnalysisStatisticsIndexDO;
import cn.plou.web.heatManage.dataAnalysis.service.HouseRunningDataAnalysisService;
import cn.plou.web.heatManage.dataAnalysis.vo.HouseTotalDataAnalysisParamInfo;
import cn.plou.web.heatManage.dataAnalysis.vo.HouseTotalDataAnalysisParamRangeInfo;
import cn.plou.web.heatManage.dataAnalysis.vo.HouseTotalDataAnalysisStatisticsIndexParamInfo;
import cn.plou.web.heatManage.diagnose.service.MbusInfoService;
import cn.plou.web.heatManage.diagnose.service.MeterInfoService;
import cn.plou.web.heatManage.monitor.service.SystemMarkersService;
import cn.plou.web.heatManage.monitor.service.WeatherDataService;
import cn.plou.web.system.baseMessage.build.service.BuildService;
import cn.plou.web.system.baseMessage.commuity.service.CommuityService;
import cn.plou.web.system.baseMessage.company.service.CompanyService;
import cn.plou.web.system.baseMessage.house.service.HouseService;
import cn.plou.web.system.baseMessage.unit.service.UnitService;
import cn.plou.web.system.commonMessage.typeMst.service.TypeMstService;
import cn.plou.web.system.meterMessage.meter.vo.MeterInfo;
import cn.plou.web.system.meterMessage.meterKey.entity.MeterKey;
import cn.plou.web.system.meterMessage.meterKey.vo.MeterKeySelectInfo;
import cn.plou.web.system.permission.userDataHistory.vo.UserDataHistoryInfo;
import cn.plou.web.system.permission.userDataHistory.vo.UserDataHistorySelectInfo;
import cn.plou.web.system.permission.userPageHistory.entity.UserPageHistory;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.plou.web.common.config.common.Cond.getCond;

/**
 * 用户运行数据汇总
 * 
 * @author liuxiadong
 * @Date 2018年6月29日 上午9:40:34
 */

@RestController
@RequestMapping("${heatManagePath}/dataanalysis/house")
public class HouseRunningDataAnalysisController {
	@Autowired
	private HouseRunningDataAnalysisService houseRunningDataAnalysisService;

	@Autowired
	private WeatherDataService dataService;

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


	/**
	 * 获取数据分析实时信息:用热管理-数据分析-室温分析、耗热指标、流量指标
	 *
	 * @param houseTotalDataAnalysisParamInfo
	 * @return
	 */

	@PostMapping("/getHouseRunningDataAnalysis")
	public Object getHouseRunningDataAnalysis(@RequestBody HouseTotalDataAnalysisParamInfo houseTotalDataAnalysisParamInfo ) {

		String companyId = "";
		String stationId = "";
		String communityId = "";

		String type = houseTotalDataAnalysisParamInfo.getType();
        String standard = houseTotalDataAnalysisParamInfo.getStandard();
		String id = houseTotalDataAnalysisParamInfo.getId();
		String startTime = houseTotalDataAnalysisParamInfo.getStartTime();
		String endTime = houseTotalDataAnalysisParamInfo.getEndTime();
		List<HouseTotalDataAnalysisParamRangeInfo> rangeLst = houseTotalDataAnalysisParamInfo.getRanges();
		JSONObject jsonObject = new JSONObject();
		if(rangeLst==null || rangeLst.size() == 0)
		{
			jsonObject.put("houseRunningDataAnalysisList", null);
			return jsonObject;
		}
		//HouseTotalDataParamRangeInfo[] range = (HouseTotalDataParamRangeInfo[])rangeLst.toArray();
		HouseTotalDataAnalysisParamRangeInfo[] range = new HouseTotalDataAnalysisParamRangeInfo[rangeLst.size()];
		rangeLst.toArray(range);

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
			default:
				jsonObject.put("houseRunningDataAnalysisList", null);
				return jsonObject;
		}

        switch (standard) {
            case "temperature"://室温指标
                break;
            case "heating"://耗热指标
                break;
            case "flowing"://流量指标
                break;
            default:
                jsonObject.put("houseRunningDataAnalysisList", null);
                return jsonObject;
        }

		if(range==null || range.length==0 || range.length>9){
			jsonObject.put("houseRunningDataAnalysisList", null);
			return jsonObject;
		}


		Map<String, Object> params = new HashMap<String, Object>();

		params.put("type", type);
		params.put("consumerId", id);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
        params.put("standard", standard);

		for(int i=0;i<range.length;i++){
			if(range[i]==null){
				jsonObject.put("communityDataList", null);
				return jsonObject;
			}
			if(i==0){
				params.put("range0_0", null);
				params.put("range0_1", range[i].getMax());
			}
			else if(i==range.length-1){
				params.put("range"+i+"_0", range[i].getMin());
				params.put("range"+i+"_1", null);
			}
			else{
				params.put("range"+i+"_0", range[i].getMin());
				params.put("range"+i+"_1", range[i].getMax());
			}
		}

		List<HouseRunningDataAnalysisTotalGatherDO> houseRunningDataAnalysisList = houseRunningDataAnalysisService.getHouseTotalData(params);

		jsonObject.put("houseRunningDataAnalysisList", houseRunningDataAnalysisList);

		return jsonObject;
	}




	/**
	 * 采暖至今
	 *
	 * @param params
	 * @return
	 */

	@GetMapping("/getHeatingStartAndEndTime")
	public Object getHeatingStartAndEndTime(@RequestParam Map<String, Object> params) {

		String year = params.get("year").toString();
		String companyId = params.get("companyId").toString();

		JSONObject jsonObject = new JSONObject();
		if(year == null || year.equals("")){
			jsonObject.put("heatingStartAndEndTime", null);
			return jsonObject;
		}
		if(companyId == null || companyId.equals("")){
			jsonObject.put("heatingStartAndEndTime", null);
			return jsonObject;
		}


		Map<String, Object> paramsEx = new HashMap<String, Object>();

		paramsEx.put("year", year);
		paramsEx.put("companyId", companyId);


		HeatingStartAndEndTimeDO heatingBeginAndEndTime = houseRunningDataAnalysisService.getHeatingStartAndEndTime(paramsEx);

		jsonObject.put("heatingStartAndEndTime", heatingBeginAndEndTime);

		return jsonObject;


	}



	/**
	 * 用热管理-数据分析-统计指标
	 *
	 * @param houseTotalDataAnalysisStatisticsIndexParamInfo
	 * @return
	 */

	@GetMapping("/getHouseTotalDataAnalysisStatisticsIndex")
	public Root getHouseTotalDataAnalysisStatisticsIndex(HouseTotalDataAnalysisStatisticsIndexParamInfo houseTotalDataAnalysisStatisticsIndexParamInfo, String sortby, Integer page, Integer
			pageSize) {

		Root root = new Root();
        JSONObject jsonObject = new JSONObject();

        String type = houseTotalDataAnalysisStatisticsIndexParamInfo.getType();
		String id = houseTotalDataAnalysisStatisticsIndexParamInfo.getId();

        String readTime = houseTotalDataAnalysisStatisticsIndexParamInfo.getReadTime();
        if(readTime == null || readTime.equals("")){
            jsonObject.put("houseTotalDataAnalysisStatisticsIndexList", null);
            root.setCode(500);
            root.setMsg("readTime param incorrect");
            return root;
        }
        String[] splitReadTime = readTime.split(",");
        if(splitReadTime==null || splitReadTime.length > 2){
            jsonObject.put("houseTotalDataAnalysisStatisticsIndexList", null);
            root.setCode(500);
            root.setMsg("readTime format incorrect");
            return root;
        }

        String startTime = splitReadTime[0];
		String endTime = splitReadTime[1];

		BigDecimal heatMin = houseTotalDataAnalysisStatisticsIndexParamInfo.getHeatMin();
		BigDecimal heatMax = houseTotalDataAnalysisStatisticsIndexParamInfo.getHeatMax();

		BigDecimal heatingAreaMin = houseTotalDataAnalysisStatisticsIndexParamInfo.getHeatingAreaMin();
		BigDecimal heatingAreaMax = houseTotalDataAnalysisStatisticsIndexParamInfo.getHeatingAreaMax();

		BigDecimal avgFlowingMin = houseTotalDataAnalysisStatisticsIndexParamInfo.getAvgFlowingMin();
		BigDecimal avgFlowingMax = houseTotalDataAnalysisStatisticsIndexParamInfo.getAvgFlowingMax();

		BigDecimal avgOutdoorTemperatureMin = houseTotalDataAnalysisStatisticsIndexParamInfo.getAvgOutdoorTemperatureMin();
		BigDecimal avgOutdoorTemperatureMax = houseTotalDataAnalysisStatisticsIndexParamInfo.getAvgOutdoorTemperatureMax();

		BigDecimal avgRoomTemperatureMin = houseTotalDataAnalysisStatisticsIndexParamInfo.getAvgRoomTemperatureMin();
		BigDecimal avgRoomTemperatureMax = houseTotalDataAnalysisStatisticsIndexParamInfo.getAvgRoomTemperatureMax();

		BigDecimal heatingIndexMin = houseTotalDataAnalysisStatisticsIndexParamInfo.getHeatingIndexMin();
		BigDecimal heatingIndexMax = houseTotalDataAnalysisStatisticsIndexParamInfo.getHeatingIndexMax();

		BigDecimal adjHeatingIndexMin = houseTotalDataAnalysisStatisticsIndexParamInfo.getAdjHeatingIndexMin();
		BigDecimal adjHeatingIndexMax = houseTotalDataAnalysisStatisticsIndexParamInfo.getAdjHeatingIndexMax();

		BigDecimal heatTargeMin = houseTotalDataAnalysisStatisticsIndexParamInfo.getHeatTargeMin();
		BigDecimal heatTargetMax = houseTotalDataAnalysisStatisticsIndexParamInfo.getHeatTargetMax();

		BigDecimal flowingIndexMin = houseTotalDataAnalysisStatisticsIndexParamInfo.getFlowingIndexMin();
		BigDecimal flowingIndexMax = houseTotalDataAnalysisStatisticsIndexParamInfo.getFlowingIndexMax();

		BigDecimal avgInTemperatureMin = houseTotalDataAnalysisStatisticsIndexParamInfo.getAvgInTemperatureMin();
		BigDecimal avgInTemperatureMax = houseTotalDataAnalysisStatisticsIndexParamInfo.getAvgInTemperatureMax();

		BigDecimal avgOutTemperatureMin = houseTotalDataAnalysisStatisticsIndexParamInfo.getAvgOutTemperatureMin();
		BigDecimal avgOutTemperatureMax = houseTotalDataAnalysisStatisticsIndexParamInfo.getAvgOutTemperatureMax();

		BigDecimal avgRoomTemperatureDifferenceMin = houseTotalDataAnalysisStatisticsIndexParamInfo.getAvgRoomTemperatureDifferenceMin();
		BigDecimal avgRoomTemperatureDifferenceMax = houseTotalDataAnalysisStatisticsIndexParamInfo.getAvgRoomTemperatureDifferenceMax();

		switch (type) {
			case "company":
				break;
			case "station":
				break;
			case "commuity":
				break;
			default:
				jsonObject.put("houseTotalDataAnalysisStatisticsIndexList", null);

				root.setCode(500);
				root.setMsg("type param incorrect");
				return root;
		}
		if(sortby != null && !sortby.equals(""))
		{

			if(sortby.indexOf(" ") != -1 ){
				final String[] sortbys = sortby.split(" ");
//参数合法性判断
				switch (sortbys[0].trim()) {
					case "systemReadTime":
						break;
					case "heatTarge":
						break;
					case "heat":
						break;
					case "address":
						break;
					case "heatingArea":
						break;
					case "avgFlowing":
						break;
					case "avgOutdoorTemperature":
						break;
					case "avgRoomTemperature":
						break;
					case "heatingIndex":
						break;
					case "adjHeatingIndex":
						break;
					case "flowingIndex":
						break;
					case "avgInTemperature":
						break;
					case "avgOutTemperature":
						break;
					case "avgRoomTemperatureDifference":
						break;
					default:
						jsonObject.put("houseTotalDataAnalysisStatisticsIndexList", null);

						root.setCode(500);
						root.setMsg("sortby param incorrect");
						return root;
				}
				switch (sortbys[1].toLowerCase().trim()) {
					case "asc":
						break;
					case "desc":
						break;
					default:
						jsonObject.put("houseTotalDataAnalysisStatisticsIndexList", null);

						root.setCode(500);
						root.setMsg("sortby param incorrect");
						return root;
				}
			}else{

				switch (sortby.trim()) {
					case "systemReadTime":
						break;
					case "heatTarge":
						break;
					case "heat":
						break;
					case "address":
						break;
					case "heatingArea":
						break;
					case "avgFlowing":
						break;
					case "avgOutdoorTemperature":
						break;
					case "avgRoomTemperature":
						break;
					case "heatingIndex":
						break;
					case "adjHeatingIndex":
						break;
					case "flowingIndex":
						break;
					case "avgInTemperature":
						break;
					case "avgOutTemperature":
						break;
					case "avgRoomTemperatureDifference":
						break;
					default:
						jsonObject.put("houseTotalDataAnalysisStatisticsIndexList", null);
						root.setCode(500);
						root.setMsg("sortby param incorrect");
						return root;
				}
			}

		}


		Map<String, Object> params = new HashMap<String, Object>();

		params.put("type", type);
		params.put("consumerId", id);

		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("heatMin", heatMin);
		params.put("heatMax", heatMax);
		params.put("heatingAreaMin", heatingAreaMin);
		params.put("heatingAreaMax", heatingAreaMax);
		params.put("avgFlowingMin", avgFlowingMin);
		params.put("avgFlowingMax", avgFlowingMax);
		params.put("avgOutdoorTemperatureMin", avgOutdoorTemperatureMin);
		params.put("avgOutdoorTemperatureMax", avgOutdoorTemperatureMax);
		params.put("avgRoomTemperatureMin", avgRoomTemperatureMin);
		params.put("avgRoomTemperatureMax", avgRoomTemperatureMax);
		params.put("heatingIndexMin", heatingIndexMin);
		params.put("heatingIndexMax", heatingIndexMax);
		params.put("adjHeatingIndexMin", adjHeatingIndexMin);
		params.put("adjHeatingIndexMax", adjHeatingIndexMax);
		params.put("heatTargeMin", heatTargeMin);
		params.put("heatTargetMax", heatTargetMax);
		params.put("flowingIndexMin", flowingIndexMin);
		params.put("flowingIndexMax", flowingIndexMax);
		params.put("avgInTemperatureMin", avgInTemperatureMin);
		params.put("avgInTemperatureMax", avgInTemperatureMax);
		params.put("avgOutTemperatureMin", avgOutTemperatureMin);
		params.put("avgOutTemperatureMax", avgOutTemperatureMax);
		params.put("avgRoomTemperatureDifferenceMin", avgRoomTemperatureDifferenceMin);
		params.put("avgRoomTemperatureDifferenceMax", avgRoomTemperatureDifferenceMax);

		params.put("sortby", sortby);
		params.put("page", page);
		params.put("pageSize", pageSize);

		PageInfo<HouseTotalDataAnalysisStatisticsIndexDO> houseTotalDataAnalysisStatisticsIndexList = houseRunningDataAnalysisService.getHouseTotalDataAnalysisStatisticsIndex(params);

		root.setData(houseTotalDataAnalysisStatisticsIndexList.getList());
		root.setCond(getCond(page,pageSize,(int)houseTotalDataAnalysisStatisticsIndexList.getTotal(),
				null,sortby));
		return root;
	}

    /**
     * 用热管理-数据分析-热点分布
     *
     * @param params
     * @return
     */

    @RequestMapping("/getHouseTotalDataAnalysisHotDistribute")
    public Root getHouseTotalDataAnalysisHotDistribute(@RequestParam Map<String, Object> params) {
        Root root = new Root();
        JSONObject jsonObject = new JSONObject();
        String type = params.get("type").toString();
        String id = params.get("id").toString();
        String readTime = params.get("readTime").toString();
        String standard = params.get("standard").toString();
        switch (type) {
            case "company":
                break;
            case "station":
                break;
            case "commuity":
                break;
            default:
                jsonObject.put("houseTotalDataAnalysisHotDistribute", null);
                root.setCode(500);
                root.setMsg("type param incorrect");
                return root;
        }


        switch (standard) {
            case "temperature"://室温指标
                break;
            case "heating"://耗热指标
                break;
            case "flowing"://流量指标
                break;
            case "devError"://设备故障
                break;
            case "communicError"://通讯故障
                break;
            default:
                jsonObject.put("houseTotalDataAnalysisHotDistribute", null);
                root.setCode(500);
                root.setMsg("standard param incorrect");
                return root;
        }

        if(readTime == null || readTime.equals("")){
            jsonObject.put("houseTotalDataAnalysisHotDistribute", null);
            root.setCode(500);
            root.setMsg("readTime param incorrect");
            return root;
        }
        String[] splitReadTime = readTime.split(",");
        if(splitReadTime==null || splitReadTime.length > 2){
            jsonObject.put("houseTotalDataAnalysisHotDistribute", null);
            root.setCode(500);
            root.setMsg("readTime format incorrect");
            return root;
        }

        String startTime = splitReadTime[0];
        String endTime = splitReadTime[1];



        Map<String, Object> paramsEx = new HashMap<String, Object>();

        paramsEx.put("type", type);
        paramsEx.put("consumerId", id);
        paramsEx.put("startTime", startTime);
        paramsEx.put("endTime", endTime);
        paramsEx.put("standard", standard);

        List<HouseTotalDataAnalysisHotDistributeDO> houseTotalDataAnalysisHotDistributes = houseRunningDataAnalysisService.getHouseTotalDataAnalysisHotDistribute(paramsEx);
        root.setData(houseTotalDataAnalysisHotDistributes);
        return root;
    }


}

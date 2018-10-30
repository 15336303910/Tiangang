package cn.plou.web.heatManage.dataAnalysis.controller;

import cn.plou.web.common.config.common.Root;
import cn.plou.web.heatManage.dataAnalysis.domain.HeatingStartAndEndTimeDO;
import cn.plou.web.heatManage.dataAnalysis.domain.HouseRunningDataAnalysisTotalGatherDO;
import cn.plou.web.heatManage.dataAnalysis.domain.HouseTotalDataAnalysisHotDistributeDO;
import cn.plou.web.heatManage.dataAnalysis.domain.HouseTotalDataAnalysisStatisticsIndexDO;
import cn.plou.web.heatManage.dataAnalysis.service.HouseRunningDataAnalysisService;
import cn.plou.web.heatManage.dataAnalysis.service.SummaryCountService;
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
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.plou.web.common.config.common.Cond.getCond;

/**
 * 用热管理——数据分析——汇总统计
 *
 */

@RestController
@RequestMapping("${heatManagePath}/dataanalysis/summarycount")
public class SummaryCountController {
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

	@Autowired
	private SummaryCountService summaryCountService;


	/**
	 * 获取汇总统计数据
	 * @param params
	 * @return
	 */
	//@ApiOperation(value = "获取汇总统计数据")
	@RequestMapping("/getSummaryCountData")
	public Object getMeterHisData(@RequestParam Map<String, Object> params) {
		Root root = new Root();
		try {
			root = summaryCountService.getSummaryCountData(params);
			return root;
		} catch (Exception ex) {
			ex.printStackTrace();
			root.setCode(1);
			root.setMsg(ex.getMessage());
		}
		return root;
	}




}

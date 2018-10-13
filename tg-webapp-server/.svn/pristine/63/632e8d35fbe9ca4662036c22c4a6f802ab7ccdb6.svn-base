package cn.plou.web.heatManage.dataAnalysis.service;

import cn.plou.web.heatManage.dataAnalysis.domain.HeatingStartAndEndTimeDO;
import cn.plou.web.heatManage.dataAnalysis.domain.HouseRunningDataAnalysisTotalGatherDO;
import cn.plou.web.heatManage.dataAnalysis.domain.HouseTotalDataAnalysisHotDistributeDO;
import cn.plou.web.heatManage.dataAnalysis.domain.HouseTotalDataAnalysisStatisticsIndexDO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-06-29 09:26:54
 */
public interface HouseRunningDataAnalysisService {

	List<HouseRunningDataAnalysisTotalGatherDO> getHouseTotalData(Map<String, Object> map);

	PageInfo<HouseTotalDataAnalysisStatisticsIndexDO> getHouseTotalDataAnalysisStatisticsIndex(Map<String, Object> map);

	List<HouseTotalDataAnalysisHotDistributeDO> getHouseTotalDataAnalysisHotDistribute(Map<String, Object> map);

	HeatingStartAndEndTimeDO getHeatingStartAndEndTime(Map<String, Object> map);

}

package cn.plou.web.heatManage.dataAnalysis.dao;

import cn.plou.web.heatManage.dataAnalysis.domain.HeatingStartAndEndTimeDO;
import cn.plou.web.heatManage.dataAnalysis.domain.HouseRunningDataAnalysisTotalGatherDO;
import cn.plou.web.heatManage.dataAnalysis.domain.HouseTotalDataAnalysisHotDistributeDO;
import cn.plou.web.heatManage.dataAnalysis.domain.HouseTotalDataAnalysisStatisticsIndexDO;
import cn.plou.web.heatManage.monitor.domain.RunningDataTotalDO;
import cn.plou.web.heatManage.monitor.vo.BuildingRunningDataTotalVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author liuxiadong
 * @Date 2018年6月30日 上午10:14:09
 */
@Mapper
public interface HouseRunningDataAnalysisTotalDao {

	List<HouseRunningDataAnalysisTotalGatherDO> getHouseTotalData(Map<String, Object> map);
	List<HouseTotalDataAnalysisStatisticsIndexDO> getHouseTotalDataAnalysisStatisticsIndex(Map<String, Object> map);
	List<HouseTotalDataAnalysisHotDistributeDO> getHouseTotalDataAnalysisHotDistribute(Map<String, Object> map);
	HeatingStartAndEndTimeDO getHeatingStartAndEndTime(Map<String, Object> map);
}

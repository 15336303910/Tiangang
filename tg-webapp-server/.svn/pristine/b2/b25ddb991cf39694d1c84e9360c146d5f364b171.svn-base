package cn.plou.web.heatManage.dataAnalysis.service.impl;

import cn.plou.web.heatManage.dataAnalysis.dao.HouseRunningDataAnalysisTotalDao;
import cn.plou.web.heatManage.dataAnalysis.domain.HeatingStartAndEndTimeDO;
import cn.plou.web.heatManage.dataAnalysis.domain.HouseRunningDataAnalysisTotalGatherDO;
import cn.plou.web.heatManage.dataAnalysis.domain.HouseTotalDataAnalysisHotDistributeDO;
import cn.plou.web.heatManage.dataAnalysis.domain.HouseTotalDataAnalysisStatisticsIndexDO;
import cn.plou.web.heatManage.dataAnalysis.service.HouseRunningDataAnalysisService;
import cn.plou.web.heatManage.monitor.dao.RunningDataTotalDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HouseRunningDataAnalysisServiceImpl implements HouseRunningDataAnalysisService {
	@Autowired
	private HouseRunningDataAnalysisTotalDao houseRunningDataAnalysisTotalDao;


	@Override
	public List<HouseRunningDataAnalysisTotalGatherDO> getHouseTotalData(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return houseRunningDataAnalysisTotalDao.getHouseTotalData(map);
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
		return houseRunningDataAnalysisTotalDao.getHouseTotalDataAnalysisHotDistribute(map);
	}


	@Override
	public HeatingStartAndEndTimeDO getHeatingStartAndEndTime(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return houseRunningDataAnalysisTotalDao.getHeatingStartAndEndTime(map);
	}

}

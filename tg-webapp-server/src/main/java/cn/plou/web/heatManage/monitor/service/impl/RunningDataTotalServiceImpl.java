package cn.plou.web.heatManage.monitor.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.plou.web.heatManage.monitor.dao.RunningDataTotalDao;
import cn.plou.web.heatManage.monitor.domain.RunningDataTotalDO;
import cn.plou.web.heatManage.monitor.service.RunningDataTotalService;
import cn.plou.web.heatManage.monitor.vo.BuildingRunningDataTotalVO;

@Service
public class RunningDataTotalServiceImpl implements RunningDataTotalService {
	@Autowired
	private RunningDataTotalDao runningDataTotalDao;
	
	@Override
	public RunningDataTotalDO get(String consumerId){
		return runningDataTotalDao.get(consumerId);
	}
	
	@Override
	public List<RunningDataTotalDO> list(Map<String, Object> map){
		return runningDataTotalDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return runningDataTotalDao.count(map);
	}

	@Override
	public List<BuildingRunningDataTotalVO> listByBuilding(Map<String, Object> map) {
		return runningDataTotalDao.listByBuilding(map);
	}

	@Override
	public BuildingRunningDataTotalVO getBuildingVO(Map<String, Object> map) {
		return runningDataTotalDao.getBuildingVO(map);
	}

	@Override
	public int getCommunityCount(Map<String, Object> map) {
		return runningDataTotalDao.getCommunityCount(map);
	}

	@Override
	public int getBuildingCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return runningDataTotalDao.getBuildingCount(map);
	}

	@Override
	public List<RunningDataTotalDO> houseList(Map<String, Object> map) {
		return runningDataTotalDao.houseList(map);
	}

	@Override
	public int houseListCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return runningDataTotalDao.houseListCount(map);
	}

	@Override
	public BuildingRunningDataTotalVO statisticsData(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return runningDataTotalDao.statisticsData(map);
	}

	@Override
	public BuildingRunningDataTotalVO buildingDataVo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return runningDataTotalDao.buildingDataVo(map);
	}

	@Override
	public List<BuildingRunningDataTotalVO> getAllCommunity(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return runningDataTotalDao.getAllCommunity(map);
	}


	@Override
	public List<BuildingRunningDataTotalVO> getAllBuilding(String consumerId) {
		// TODO Auto-generated method stub
		return runningDataTotalDao.getAllBuilding(consumerId);
	}
	
}
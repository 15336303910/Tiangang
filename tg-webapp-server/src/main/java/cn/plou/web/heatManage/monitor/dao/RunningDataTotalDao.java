package cn.plou.web.heatManage.monitor.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.PathVariable;

import cn.plou.web.heatManage.monitor.domain.RunningDataTotalDO;
import cn.plou.web.heatManage.monitor.vo.BuildingRunningDataTotalVO;

/**
 * 
 * @author liuxiadong
 * @Date 2018年6月30日 上午10:14:09
 */
@Mapper
public interface RunningDataTotalDao {

	RunningDataTotalDO get(String consumerId);
	
	BuildingRunningDataTotalVO getBuildingVO(Map<String, Object> map);
	
	BuildingRunningDataTotalVO statisticsData(Map<String, Object> map);
	
	List<RunningDataTotalDO> list(Map<String,Object> map);
	
	List<RunningDataTotalDO> houseList(Map<String, Object> map);
	
	List<BuildingRunningDataTotalVO> listByBuilding(Map<String, Object> map);
	
	List<BuildingRunningDataTotalVO> getAllCommunity(Map<String, Object> map);

	List<BuildingRunningDataTotalVO> getAllBuilding(@PathVariable("consumerId") String consumerId);
	
	BuildingRunningDataTotalVO buildingDataVo(Map<String, Object> map);
	
	int count(Map<String,Object> map);
	
	int houseListCount(Map<String, Object> map);
	
	int getCommunityCount(Map<String, Object> map);
	
	int getBuildingCount(Map<String, Object> map);
}

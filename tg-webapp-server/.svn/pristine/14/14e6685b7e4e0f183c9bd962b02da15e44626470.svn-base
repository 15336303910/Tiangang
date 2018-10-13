package cn.plou.web.heatManage.history.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import cn.plou.web.heatManage.history.domain.HeatMeterDataDO;
import cn.plou.web.heatManage.monitor.domain.RunningDataTotalDO;
import cn.plou.web.heatManage.monitor.vo.BuildingRunningDataTotalVO;
import cn.plou.web.system.baseMessage.house.vo.HouseInfo;
import cn.plou.web.system.baseMessage.house.vo.HouseVo;

/**
 * 
 * @author 吕琨
 * @Date 2018年6月30日 上午10:14:09
 */
@Mapper
public interface HistoryDataDao {

	int getMeterHisDataCount(Map<String,Object> map);
	List<HeatMeterDataDO> getMeterHisData(Map<String, Object> map);
	List<HeatMeterDataDO> getMeterNowData(Map<String, Object> map);	
  List<HouseInfo> selectAllHouse(@Param("companyIds") List<String> companyIds, @Param("stationIds") List<String> stationIds,
      @Param("commuityIds") List<String> commuityIds,
  @Param("buildingNo") String buildingNo, @Param("unitId") String unitId, @Param("houseVo") HouseVo houseVo,
  @Param("order") String order, @Param("sortby") String sortby);
	List<String> getAreasByStationId(Map<String, Object> map);
	
}

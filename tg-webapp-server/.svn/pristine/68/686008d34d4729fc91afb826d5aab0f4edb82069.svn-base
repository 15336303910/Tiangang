package cn.plou.web.heatManage.history.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import cn.plou.web.common.config.common.Root;
import cn.plou.web.heatManage.history.domain.HeatMeterDataDO;
import cn.plou.web.heatManage.history.domain.WkfDataDO;
import cn.plou.web.heatManage.monitor.domain.RunningDataTotalDO;
import cn.plou.web.system.baseMessage.house.vo.HouseInfo;

public interface HistoryDataService {

		Root getMeterHisData(Map<String,Object> map);
		Root getWkfHisData(Map<String,Object> map);
		Root getHouseWkqHisData(Map<String, Object> params);
		Root getHouseTotalData(Map<String, Object> params);
		List<RunningDataTotalDO> list(Map<String, Object> map);
		List<RunningDataTotalDO> esHouseTotalDataAll(Map<String, Object> map);
		Map<String, HouseInfo> houseInfo(Map<String, Object> map, List<String> consumerIdList);
		List<WkfDataDO> esWkfDataAll(Map<String, Object> map);
		List<HeatMeterDataDO> esMeterAll(Map<String, Object> map);
		Object getHouseTotalDataToExcel(Map<String, Object> params, ServletRequest request);
		Object getWkfHisDataToExcel(Map<String, Object> params, ServletRequest request);
		Object getMeterHisDataToExcel(Map<String, Object> params, ServletRequest request);
		Object getHouseWkqHisDataToExcel(Map<String, Object> params, ServletRequest request);
}

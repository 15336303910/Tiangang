package cn.plou.web.heatManage.dataAnalysis.service;

import java.util.List;
import java.util.Map;

import cn.plou.web.common.config.common.Root;
import cn.plou.web.heatManage.dataAnalysis.domain.WaterConditionDO;
import cn.plou.web.heatManage.dataAnalysis.vo.WaterConditionVO;
import cn.plou.web.heatManage.monitor.domain.RunningDataTotalDO;
import cn.plou.web.system.baseMessage.house.vo.HouseInfo;

public interface WaterConditionService {

	Root CalBuildWaterConditionVo(Map<String, Object> map,  Map<String, List<RunningDataTotalDO>> mapData, Map<String,HouseInfo> houses);
	Root CalHouseWaterConditionVo(Map<String, Object> map,  Map<String, List<RunningDataTotalDO>> mapData, Map<String,HouseInfo> houses);
}

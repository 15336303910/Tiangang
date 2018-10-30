package cn.plou.web.heatManage.dataAnalysis.service;

import cn.plou.web.common.config.common.Root;
import cn.plou.web.heatManage.monitor.domain.RunningDataTotalDO;
import cn.plou.web.system.baseMessage.house.vo.HouseInfo;

import java.util.List;
import java.util.Map;

public interface SummaryCountService {

	Root getSummaryCountData(Map<String,Object> map);
}

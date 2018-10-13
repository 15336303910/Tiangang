package cn.plou.web.heatManage.monitor.service;

import java.util.List;
import java.util.Map;

import cn.plou.web.heatManage.monitor.domain.WeatherDataDO;

/**
 * 
 * 
 * @author liuxiaodong
 * @email yigeyanse@hotmail.com
 * @date 2018-06-29 15:24:01
 */
public interface WeatherDataService {
	
	WeatherDataDO get(String rowno);
	
	List<WeatherDataDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(WeatherDataDO data);
	
	int update(WeatherDataDO data);
	
	int remove(String rowno);
	
	int batchRemove(String[] rownos);
}

package cn.plou.web.heatManage.monitor.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.plou.web.heatManage.monitor.domain.WeatherDataDO;

/**
 * 
 * @author liuxiaodong
 * @email yigeyanse@hotmail.com
 * @date 2018-06-29 15:24:01
 */
@Mapper
public interface WeatherDataDao {

	WeatherDataDO get(String rowno);
	
	List<WeatherDataDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(WeatherDataDO data);
	
	int update(WeatherDataDO data);
	
	int remove(String rowno);
	
	int batchRemove(String[] rownos);
}

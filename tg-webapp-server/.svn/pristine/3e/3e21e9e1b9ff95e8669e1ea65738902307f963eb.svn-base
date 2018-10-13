package cn.plou.web.heatManage.monitor.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.plou.web.heatManage.monitor.domain.SystemMarkersDO;

/**
 * 
 * @author liuxiaodong
 * @email yigeyanse@hotmail.com
 * @date 2018-07-06 09:59:43
 */
@Mapper
public interface SystemMarkersDao {

	SystemMarkersDO get(Integer id);
	
	List<SystemMarkersDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(SystemMarkersDO systemMarkers);
	
	int update(SystemMarkersDO systemMarkers);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}

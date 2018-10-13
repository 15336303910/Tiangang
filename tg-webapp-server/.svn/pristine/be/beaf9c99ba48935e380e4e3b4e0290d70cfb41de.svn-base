package cn.plou.web.heatManage.monitor.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.plou.web.heatManage.monitor.dao.SystemMarkersDao;
import cn.plou.web.heatManage.monitor.domain.SystemMarkersDO;
import cn.plou.web.heatManage.monitor.service.SystemMarkersService;



@Service
public class SystemMarkersServiceImpl implements SystemMarkersService {
	@Autowired
	private SystemMarkersDao systemMarkersDao;
	
	@Override
	public SystemMarkersDO get(Integer id){
		return systemMarkersDao.get(id);
	}
	
	@Override
	public List<SystemMarkersDO> list(Map<String, Object> map){
		return systemMarkersDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return systemMarkersDao.count(map);
	}
	
	@Override
	public int save(SystemMarkersDO systemMarkers){
		return systemMarkersDao.save(systemMarkers);
	}
	
	@Override
	public int update(SystemMarkersDO systemMarkers){
		return systemMarkersDao.update(systemMarkers);
	}
	
	@Override
	public int remove(Integer id){
		return systemMarkersDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return systemMarkersDao.batchRemove(ids);
	}
	
}

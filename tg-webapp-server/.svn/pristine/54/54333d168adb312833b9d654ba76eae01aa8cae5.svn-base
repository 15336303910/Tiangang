package cn.plou.web.heatManage.monitor.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.plou.web.heatManage.monitor.dao.WeatherDataDao;
import cn.plou.web.heatManage.monitor.domain.WeatherDataDO;
import cn.plou.web.heatManage.monitor.service.WeatherDataService;



@Service
public class WeatherDataServiceImpl implements WeatherDataService {
	@Autowired
	private WeatherDataDao dataDao;
	
	@Override
	public WeatherDataDO get(String rowno){
		return dataDao.get(rowno);
	}
	
	@Override
	public List<WeatherDataDO> list(Map<String, Object> map){
		return dataDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return dataDao.count(map);
	}
	
	@Override
	public int save(WeatherDataDO data){
		return dataDao.save(data);
	}
	
	@Override
	public int update(WeatherDataDO data){
		return dataDao.update(data);
	}
	
	@Override
	public int remove(String rowno){
		return dataDao.remove(rowno);
	}
	
	@Override
	public int batchRemove(String[] rownos){
		return dataDao.batchRemove(rownos);
	}
	
}

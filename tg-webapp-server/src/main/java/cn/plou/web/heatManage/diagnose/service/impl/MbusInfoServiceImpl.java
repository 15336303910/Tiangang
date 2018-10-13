package cn.plou.web.heatManage.diagnose.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.plou.web.heatManage.diagnose.dao.MbusInfoDao;
import cn.plou.web.heatManage.diagnose.domain.MbusInfoDO;
import cn.plou.web.heatManage.diagnose.service.MbusInfoService;



@Service
public class MbusInfoServiceImpl implements MbusInfoService {
	@Autowired
	private MbusInfoDao mbusInfoDao;
	
	@Override
	public MbusInfoDO get(String mbusId){
		return mbusInfoDao.get(mbusId);
	}
	
	@Override
	public List<MbusInfoDO> list(Map<String, Object> map){
		return mbusInfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return mbusInfoDao.count(map);
	}
	
	@Override
	public int save(MbusInfoDO mbusInfo){
		return mbusInfoDao.save(mbusInfo);
	}
	
	@Override
	public int update(MbusInfoDO mbusInfo){
		return mbusInfoDao.update(mbusInfo);
	}
	
	@Override
	public int remove(String mbusId){
		return mbusInfoDao.remove(mbusId);
	}
	
	@Override
	public int batchRemove(String[] mbusIds){
		return mbusInfoDao.batchRemove(mbusIds);
	}

	@Override
	public int errorCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mbusInfoDao.errorCount(map);
	}

	@Override
	public List<MbusInfoDO> groupList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mbusInfoDao.groupList(map);
	}
	
}

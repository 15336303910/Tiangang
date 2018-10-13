package cn.plou.web.heatManage.diagnose.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.plou.web.heatManage.diagnose.dao.MeterInfoDao;
import cn.plou.web.heatManage.diagnose.domain.MeterInfoDO;
import cn.plou.web.heatManage.diagnose.service.MeterInfoService;
import cn.plou.web.heatManage.diagnose.vo.MeterInfoVO;



@Service
public class MeterInfoServiceImpl implements MeterInfoService {
	@Autowired
	private MeterInfoDao meterInfoDao;
	
	@Override
	public MeterInfoDO get(String meterId){
		return meterInfoDao.get(meterId);
	}
	
	@Override
	public List<MeterInfoDO> list(Map<String, Object> map){
		return meterInfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return meterInfoDao.count(map);
	}
	
	@Override
	public int save(MeterInfoDO meterInfo){
		return meterInfoDao.save(meterInfo);
	}
	
	@Override
	public int update(MeterInfoDO meterInfo){
		return meterInfoDao.update(meterInfo);
	}
	
	@Override
	public int remove(String meterId){
		return meterInfoDao.remove(meterId);
	}
	
	@Override
	public int batchRemove(String[] meterIds){
		return meterInfoDao.batchRemove(meterIds);
	}

	@Override
	public List<MeterInfoDO> groupList(Map<String, Object> map) {
		return meterInfoDao.groupList(map);
	}

	@Override
	public MeterInfoVO getByBuilding(String consumerId) {
		return meterInfoDao.getByBuilding(consumerId);
	}

	@Override
	public int netErrorCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return meterInfoDao.netErrorCount(map);
	}

	@Override
	public int deviceErrorCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return meterInfoDao.deviceErrorCount(map);
	}
	
}

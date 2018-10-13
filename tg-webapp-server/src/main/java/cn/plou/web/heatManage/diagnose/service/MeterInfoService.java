package cn.plou.web.heatManage.diagnose.service;

import java.util.List;
import java.util.Map;

import cn.plou.web.heatManage.diagnose.domain.MeterInfoDO;
import cn.plou.web.heatManage.diagnose.vo.MeterInfoVO;

/**
 * 
 * 
 * @author liuxiaodong
 * @email yigeyanse@hotmail.com
 * @date 2018-07-03 16:33:45
 */
public interface MeterInfoService {
	
	MeterInfoDO get(String meterId);
	
	MeterInfoVO getByBuilding(String consumerId);
	
	List<MeterInfoDO> list(Map<String, Object> map);
	
	List<MeterInfoDO> groupList(Map<String,Object> map);
	
	int count(Map<String, Object> map);
	
	int netErrorCount(Map<String, Object> map);
	
	int deviceErrorCount(Map<String, Object> map);
	
	int save(MeterInfoDO meterInfo);
	
	int update(MeterInfoDO meterInfo);
	
	int remove(String meterId);
	
	int batchRemove(String[] meterIds);
}
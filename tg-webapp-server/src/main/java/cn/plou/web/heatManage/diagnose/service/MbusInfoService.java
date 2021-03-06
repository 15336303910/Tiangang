package cn.plou.web.heatManage.diagnose.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import com.alibaba.fastjson.JSONArray;

import cn.plou.web.heatManage.diagnose.domain.MbusInfoDO;

/**
 * 
 * 
 * @author liuxiaodong
 * @email yigeyanse@hotmail.com
 * @date 2018-07-03 16:33:45
 */
public interface MbusInfoService {
	
	MbusInfoDO get(String mbusId);
	
	List<MbusInfoDO> list(Map<String, Object> map);
	
	List<MbusInfoDO> groupList(Map<String,Object> map);
	
	int errorCount(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(MbusInfoDO mbusInfo);
	
	int update(MbusInfoDO mbusInfo);
	
	int remove(String mbusId);
	
	int batchRemove(String[] mbusIds);

	String diagnoseMubsToExcel(Map<String, Object> params, ServletRequest request);

	JSONArray getMbusStatusDetail(Map<String, Object> params);
}

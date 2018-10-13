package cn.plou.web.heatManage.diagnose.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.plou.web.heatManage.diagnose.domain.MbusInfoDO;

/**
 * 
 * @author liuxiaodong
 * @email yigeyanse@hotmail.com
 * @date 2018-07-03 16:33:45
 */
@Mapper
public interface MbusInfoDao {

	MbusInfoDO get(String mbusId);
	
	List<MbusInfoDO> list(Map<String,Object> map);
	
	List<MbusInfoDO> groupList(Map<String,Object> map);
	
	int errorCount(Map<String, Object> map);
	
	int count(Map<String,Object> map);
	
	int save(MbusInfoDO mbusInfo);
	
	int update(MbusInfoDO mbusInfo);
	
	int remove(String mbus_id);
	
	int batchRemove(String[] mbusIds);
}

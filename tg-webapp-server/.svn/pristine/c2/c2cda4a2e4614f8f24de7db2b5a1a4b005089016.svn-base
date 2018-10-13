package cn.plou.web.heatManage.housecontrol.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.plou.web.common.utils.Query;
import cn.plou.web.heatManage.housecontrol.domain.HouseControlDO;
import cn.plou.web.heatManage.housecontrol.domain.HouseControlDetail;
import cn.plou.web.heatManage.housecontrol.domain.HouseControlInfoDO;

/**
 * 
 * @author lvkun
 * @email yigeyanse@hotmail.com
 * @date 2018-07-03 16:33:45
 */
@Mapper
public interface HouseControlDao {

	void insertControl(HouseControlDO houseControlDO);

	List<HouseControlInfoDO> controlInfoList(Map<String,Object> map);
	int controlInfoListCount(Map<String,Object> map);

	void operControlInfo(Map<String,Object> map);

	HouseControlDetail getControlHisInfo(Map<String, Object> map);

	void operDevInfo(Map<String, Object> params);
}

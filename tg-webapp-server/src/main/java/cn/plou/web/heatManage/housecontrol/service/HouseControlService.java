package cn.plou.web.heatManage.housecontrol.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.ibatis.annotations.Param;

import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.Query;
import cn.plou.web.heatManage.housecontrol.domain.HouseControlHisDO;
import cn.plou.web.heatManage.housecontrol.domain.HouseControlInfoDO;

/**
 * 
 * 
 * @author liuxiaodong
 * @email yigeyanse@hotmail.com
 * @date 2018-07-03 16:33:45
 */
public interface HouseControlService {

	/**
	 * 参数为 id，location，type（company，station，commuity，building，unit，house），
	 * valveControl，meterControl，para1，companyId， 
	 * @param map
	 * @return
	 */
	public String houseControl(Map<String, Object> map);

	public Root controlInfoList(Map<String, Object> map);

	public int controlInfoListCount(Map<String, Object> map);

	public Root operControlInfo(Map<String, Object> params);

	public Root getControlDetailInfo(Map<String, Object> params);

	public Root getControlHisInfo(Map<String, Object> params);

	public Root operDevInfo(Map<String, Object> params);

	public void getUserDeviceIdToParams(Map<String, Object> params);

	public String getControlInfoToExcel(Map<String, Object> params, ServletRequest request);
}

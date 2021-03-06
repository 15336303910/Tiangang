package cn.plou.web.system.permission.role.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.plou.web.system.permission.role.entity.DataRole;

@Service
public interface DataRoleService {
	DataRole getDataRole(String roleId);
	public Boolean showCompanys(List<String> companyIds, String companyId, List<String> companyIdshow);
	public List<String> findStringByList(List<String> compsUnCheck, List<String> compsHave);
	public void getCommuitysByStation(List<String> stationIds, List<String> commuityIds);
	Boolean getDataRoleItems(List<String> companyIds, List<String> stationIds, List<String> commuityIds, String userId,
			String companyId, String stationId, String commuityId);
	public Boolean getDataRoleItemsType(String userId,Map<String, Object> map);
}

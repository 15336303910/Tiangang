package cn.plou.web.system.permission.role.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.plou.common.utils.DateUtil;
import cn.plou.web.common.utils.Tools;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import cn.plou.web.system.baseMessage.commuity.service.CommuityService;
import cn.plou.web.system.baseMessage.company.entity.Company;
import cn.plou.web.system.baseMessage.company.service.CompanyService;
import cn.plou.web.system.baseMessage.company.vo.CompanyInfo;
import cn.plou.web.system.baseMessage.station.entity.Station;
import cn.plou.web.system.baseMessage.station.service.StationService;
import cn.plou.web.system.permission.rlRoleData.entity.RlRoleData;
import cn.plou.web.system.permission.rlRoleData.service.RlRoleDataService;
import cn.plou.web.system.permission.rlUserRole.service.RlUserRoleService;
import cn.plou.web.system.permission.role.entity.DataRole;
import cn.plou.web.system.permission.role.service.DataRoleService;

@Component
public class DateRoleServiceImpl implements DataRoleService {
	private static Map<String, DataRole> dataRoleMap = new ConcurrentHashMap<>();
	@Autowired
	CompanyService companyService;
	@Autowired
	RlUserRoleService userRoleService;
	@Autowired
	RlRoleDataService rlRoleDataService;
	@Autowired
	StationService stationService;
	@Autowired
	CommuityService commuityService;

	public DataRole getDataRole(String roleId) {
			DataRole role = getNewDataRole(roleId);
			return role;
	}

	private DataRole getNewDataRole(String roleId) {
		DataRole role = new DataRole();
		role.setUserCode(roleId);
		role.setTimeLastAct(DateUtil.getSysdateLong());
		List<String> companyIds = new ArrayList<>();
		List<String> stationIds = new ArrayList<>();
		List<String> commuityIds = new ArrayList<>();

		if(roleId.equals("1")){
			role.setUserCodeType("G");
			List<CompanyInfo> list = companyService.selectAllCompany();
			List<String> idList = new ArrayList<>();
			for(CompanyInfo comp:list){
				idList.add(comp.getCompanyId());
			}
			role.setCompanyIds(idList);
			return role;
		}
		List<RlRoleData> rlRoleDataList = rlRoleDataService.getRlRoleDataByRoleId(roleId);
		//直接权限的获取.
		for (RlRoleData rlRoleData : rlRoleDataList) {
			if (rlRoleData.getColumnType().equals("A")) {
				companyIds.add(rlRoleData.getColumnValue());
			} else if (rlRoleData.getColumnType().equals("E")) {
				stationIds.add(rlRoleData.getColumnValue());
			} else if (rlRoleData.getColumnType().equals("F")) {
				String commuityId = rlRoleData.getColumnValue();
				commuityIds.add(commuityId);
			}
		}
		//间接权限，获取所有有权限的子公司
		if (companyIds.size() > 0) {
			List<String> comps = getCompanyIdsChild(companyIds);
			if (comps != null) {
				companyIds.addAll(comps);
			}
		}
		//获得所有权限的所有小区
//		if(stationIds.size() > 0){
//			getCommuitysByStation(stationIds,commuityIds);
//		}
		role.setCompanyIds(companyIds);
		role.setStationIds(stationIds);
		role.setCommuityIds(commuityIds);
		return role;
	}
	public void getCommuitysByStation(List<String> stationIds, List<String> commuityIds) {
		List<Commuity> list = commuityService.selectCommuityByStationIds(stationIds);
		for(Commuity com: list){
			commuityIds.add(com.getCommuityId());
		}
	}

	private List<String> getCompanyIdsChild(List<String> companyIds) {
		List<Company> companys = companyService.selectChildrenCompanyAll(companyIds);
		//获取所有公司有权限的站
		if(companys == null || companys.size() == 0){
			return null;
		}
		List<String> comps = new ArrayList<>();
		for(Company comp:companys){
			comps.add(comp.getCompanyId());
		}
		return comps;
	}
	@Override
	public List<String> findStringByList(List<String> compsUnCheck, List<String> compsHave) {
		List<String> compsChecked = new ArrayList<>();
		if(compsUnCheck == null){
			return compsChecked;
		}
		for(String uncheck:compsUnCheck){
			for(String have: compsHave){
				if(uncheck.equals(have)){
					compsChecked.add(have);
					break;
				}
			}
		}
		return compsChecked;
	}
	
	/**
	 * 根据公司查询所有要显示的公司权限，包括这个公司及其子公司
	 * @param role
	 * @param companyId
	 * @param companyIds
	 * @return
	 */
	public Boolean showCompanys(List<String> companyIds, String companyId, List<String> companyIdshow){
		if(companyId== null){
			return false;
		}
		Boolean flag = false;
		for (String company : companyIds) {
			if (company.equals(companyId)) {
				companyIdshow.add(company);
				List<String> list = new ArrayList<>();
				list.add(companyId);
				// 间接权限，获取所有有权限的子公司
				List<String> comps = getCompanyIdsChild(list);
				if(comps != null){
					companyIdshow.addAll(comps);
				}
				
				flag = true;
				break;
			}
		}
		if (!flag) {
			List<String> list = new ArrayList<>();
			list.add(companyId);
			// 间接权限，获取所有有权限的子公司
			List<String> comps = getCompanyIdsChild(list);
			List<String> showCompanys = findStringByList(comps, companyIds);
			companyIdshow.addAll(showCompanys);
			if(showCompanys.size() > 0){
				return true;
			}
		}
		return flag;
	}

	@Override
	public Boolean getDataRoleItems(List<String> companyIds, List<String> stationIds, List<String> commuityIds, String userId,
			String companyId, String stationId, String commuityId) {
		String roleId = userRoleService.getRoleByUserId(UserUtils.getUserId());
		DataRole dataRole = getDataRole(roleId);
		if(dataRole.getCompanyIds().size() == 0 && dataRole.getStationIds().size() == 0 && dataRole.getCommuityIds().size() == 0){
			return false;
		}
		// 前台需要返给我选择的条件,公司,公司下的站,站下的小区(返回选中节点和他的所有子节点即可,我可以再根据本地权限细化修改)
		if (companyId != null) {
			showCompanys(dataRole.getCompanyIds(), companyId, companyIds);
			if (companyIds.size() == 0) {
				stationIds.addAll(dataRole.getStationIds());
				commuityIds.addAll(dataRole.getCommuityIds());
			}
		}
		if (stationId != null) {
			stationIds.add(stationId);
			getCommuitysByStation(stationIds, commuityIds);
		}
		if (commuityId != null) {
			commuityIds.add(commuityId);
		}
		return true;
	}
}

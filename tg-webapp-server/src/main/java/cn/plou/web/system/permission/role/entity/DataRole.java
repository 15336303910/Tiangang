package cn.plou.web.system.permission.role.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import lombok.Data;
/**
 * 权限使用顺序 输入公司时，判断直接权限，如果没有，查找数据库中该公司下的所有子公司，并通过直接权限进行过滤
 * 输入站时，直接判断是否是这个站的权限即可（所有有权限的站都被加载了）
 * 输入小区时，截取小区ID为公司ID，判断直接权限即可
 * @author Administrator
 *
 */
@Data
public class DataRole {
		String userCode;
		String userCodeType = "A";	//G为管理员 其他为非管理员
		Long timeLastAct;
		String currentCompanyId = "";
		Map<String,List<String>> companyIdAndStationIds = new HashMap<>();	
		Map<String, Map<String,List<String>>> companyIdAndCommuityIds = new HashMap<>();		
		List<String> companyIds = new ArrayList<>(); //权限是公司时拥有的公司及子公司权限（直接权限）。
		List<String> stationIds = new ArrayList<>();
		List<String> commuityIds = new ArrayList<>();
}

package cn.plou.web.mobile.station.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.plou.web.common.utils.Tools;
import cn.plou.web.mobile.common.AppTools;
import cn.plou.web.mobile.station.service.StationAppService;
import cn.plou.web.system.baseMessage.system.dao.SystemMapper;
import cn.plou.web.system.baseMessage.system.vo.SystemInfoVo;
import cn.plou.web.system.permission.rlRoleData.dao.RlRoleDataMapper;
import cn.plou.web.system.permission.rlRoleData.entity.RlRoleData;
import cn.plou.web.system.permission.rlUserRole.dao.RlUserRoleMapper;

/**
 * @Project : tg-webapp-server
 * @File : StationAppServiceImpl.java
 * @Author : WangJiWei
 * @Date : 2018年9月19日下午1:40:33
 *
 * @Comments :
 * 
 */
@Service
public class StationAppServiceImpl implements StationAppService {

    private static final Logger log = LoggerFactory.getLogger(StationAppServiceImpl.class);

    @Autowired
    SystemMapper systemMapper;
    @Autowired
    RlRoleDataMapper rlRoleDataMapper;
    @Autowired
    RlUserRoleMapper userRoleMapper;

    @Override
    public List<RlRoleData> findRoleDataByUserId(String userId) {
	String roleId = userRoleMapper.selectRoleByUserId(userId);
	return rlRoleDataMapper.selectRlRoleDataByRoleId(roleId);
    }

    @Override
    public List<SystemInfoVo> findSystemByUserId(String userId) {
	List<SystemInfoVo> systemInfos = null;
	List<RlRoleData> ds = findRoleDataByUserId(userId);
	if (!Tools.isNotEmpty(ds)) {
	    return systemInfos;
	}
	List<String> ids = AppTools.objectExtractIds(ds, c -> c.getColumnValue());
	/** 站级权限与公司级权限对于同一角色保持一致 */
	RlRoleData rd = ds.stream().findAny().get();
	if (Tools.equals(rd.getColumnType(), "E")) { // 站级权限
	    systemInfos = systemMapper.getSystemByStationIds(ids);
	} else if (Tools.equals(rd.getColumnType(), "A")) { // 公司级权限
	    systemInfos = systemMapper.getSystemByCompanyIds(ids);
	}
	return systemInfos;
    }

}
package cn.plou.web.system.permission.role.service.impl;

import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.utils.CamelCaseUtil;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.baseMessage.commuity.service.CommuityService;
import cn.plou.web.system.baseMessage.company.service.CompanyService;
import cn.plou.web.system.baseMessage.station.service.StationService;
import cn.plou.web.system.commonMessage.typeMst.service.impl.TypeMstServiceImpl;
import cn.plou.web.system.permission.rlMenuRole.dao.RlMenuRoleMapper;
import cn.plou.web.system.permission.rlMenuRole.service.RlMenuRoleService;
import cn.plou.web.system.permission.rlRoleData.dao.RlRoleDataMapper;
import cn.plou.web.system.permission.rlRoleData.entity.RlRoleData;
import cn.plou.web.system.permission.rlRoleData.service.RlRoleDataService;
import cn.plou.web.system.permission.rlRoleData.vo.RlRoleDataVo;
import cn.plou.web.system.permission.rlRoleData.vo.RoleData;
import cn.plou.web.system.permission.rlRoleValve.dao.RlRoleValveMapper;
import cn.plou.web.system.permission.rlUserRole.entity.RlUserRole;
import cn.plou.web.system.permission.rlUserRole.service.impl.RlUserRoleServiceImpl;
import cn.plou.web.system.permission.role.dao.RoleMapper;
import cn.plou.web.system.permission.role.entity.Role;
import cn.plou.web.system.permission.role.service.RoleService;
import cn.plou.web.system.permission.role.vo.RoleInfo;
import cn.plou.web.system.permission.role.vo.RoleVo;
import cn.plou.web.system.permission.userPageHistory.entity.UserPageHistory;
import cn.plou.web.system.permission.userPageHistory.service.impl.UserPageHistoryServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RlUserRoleServiceImpl rlUserRoleServiceImpl;
    @Autowired
    RlRoleDataMapper rlRoleDataMapper;
    @Autowired
    RlMenuRoleMapper rlMenuRoleMapper;
    @Autowired
    RlRoleDataService rlRoleDataService;
    @Autowired
    RlRoleValveMapper rlRoleValveMapper;
    @Autowired
    private UserPageHistoryServiceImpl userPageHistoryServiceImpl;
    @Autowired
    TypeMstServiceImpl typeMstServiceImpl;
    @Autowired
    CompanyService companyService;
    @Autowired
    StationService stationService;
    @Autowired
    CommuityService commuityService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addRole(Role role) {
        /*List<RoleInfo> roleInfoList = roleMapper.selectAllRole(null, null, null,null,null,null);
        List<Integer> list = new ArrayList<Integer>();
        String roleId="";
        if (roleInfoList.size() == 0) {
            role.setRoleId("1");
        } else {
            for (RoleInfo roleInfo : roleInfoList) {
                list.add(Integer.valueOf(roleInfo.getRoleId()));
            }
            Collections.sort(list);
            Collections.reverse(list);
             roleId=""+(list.get(0) + 1);
            role.setRoleId(roleId);
        }*/
//        role.setRoleState(true);
        //String roleId = rlUserRoleServiceImpl.getRoleByUserId(UserUtils.getUserId());
        String roleId = role.getpRoleId();
        List<String> roleIds = roleMapper.selectAllRoleIdsByRoleId(roleId);
        List<String> childrenRoleIds = new ArrayList<>();
        if(roleIds.size()==1){
            role.setRoleId(roleId + "01");
        }else {
            for (String eachRoleId : roleIds) {
                if ((roleId.length() + 2 == eachRoleId.length())) {
                    childrenRoleIds.add(eachRoleId);
                }
            }
            Collections.sort(childrenRoleIds);
            Collections.reverse(childrenRoleIds);
        }
        //if(!roleId.equals("1")){
            role.setRoleId(String.format("%0"+(roleId.length()+2)+"d",Integer.parseInt(childrenRoleIds.get(0))+1));
        //}else{
            //role.setRoleId("1"+Integer.parseInt(childrenRoleIds.get()));
        //}
        int count=roleMapper.insertSelective(role);
        if(!roleId.equals("1")){
            List<RlRoleData> rlRoleDataList = rlRoleDataMapper.selectRlRoleDataByUserId(UserUtils.getUserId());
            List<RoleData> roleDataList=new ArrayList<>();
            for(RlRoleData rlRoleData:rlRoleDataList){
                RoleData roleData=new RoleData();
                BeanUtils.copyProperties(rlRoleData,roleData);
                roleDataList.add(roleData);
            }
            RlRoleDataVo rlRoleDataVo=new RlRoleDataVo();
            rlRoleDataVo.setRoleId(roleId);
            rlRoleDataVo.setColumnType("A");
            rlRoleDataVo.setRoleDataList(roleDataList);
            rlRoleDataService.modifyRlRoleDataBatch(rlRoleDataVo);
        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("role");
        userPageHistory.setAct("addRole");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyRoleBatch(RoleVo roleVo) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("role");
        userPageHistory.setAct("modifyRoleBatch");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return roleMapper.updateRoleBatch(roleVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyRole(Role role) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("role");
        userPageHistory.setAct("modifyRole");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleBatchByIds(List<String> roleIds) {
        List<RlUserRole> rlUserRoleList = rlUserRoleServiceImpl.getRlUserRoleByRoleId(roleIds);
        if (rlUserRoleList.size() > 0) {
            throw new BusinessException("角色已被用户关联，不能删除");
        }
        for (String roleId : roleIds) {
            rlMenuRoleMapper.deleteRlMenuRoleByRoleId(roleId);
            rlRoleDataMapper.deleteRlRoleData(roleId);
            rlRoleValveMapper.deleteRlRoleValve(roleId);
        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("role");
        userPageHistory.setAct("deleteRoleBatchByIds");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return roleMapper.deleteRoleBatchByIds(roleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageInfo<RoleInfo> getAllRole(String order, String sortby, RoleVo roleVo, Integer page, Integer pageSize) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("role");
        userPageHistory.setAct("getAllRole");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
//        if(page!=null) {
//            page = (page - 1) * pageSize;
//        }
        sortby = CamelCaseUtil.toUnderscoreCase(sortby);
        if (sortby != null) {
            if (sortby.equals("role_state_name")) {
                sortby = "role_state";
            }
        }
        List<String> companyIds = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        /*if (!rlUserRoleServiceImpl.getRoleByUserId(UserUtils.getUserId()).equals("1")) {
            List<RlRoleData> rlRoleDataList = rlRoleDataMapper.selectRlRoleDataByUserId(UserUtils.getUserId());
            for (RlRoleData rlRoleData : rlRoleDataList) {
                if (rlRoleData.getColumnType().equals("A"))
                    companyIds.add(rlRoleData.getColumnValue());
            }
            if (companyIds.size() == 0) {
                return new PageInfo<>();
            } else {
                for (String companyId : companyIds) {
                    ids.addAll(companyService.getCompanyIdsByPid(companyId));
                }
            }
        }
        PageHelper.startPage(page, pageSize);
        List<RoleInfo> roleInfoList = roleMapper.selectAllRole(ids, order, sortby, roleVo, page, pageSize);
				if (ids.size() > 0) {
					List<RoleInfo> roleInfoList2 = roleMapper.selectAllRoleOnlyStation(ids, order, sortby, roleVo, page, pageSize);
	        roleInfoList.addAll(roleInfoList2);
				}*/
       
        String roleId = rlUserRoleServiceImpl.getRoleByUserId(UserUtils.getUserId());
        List<RoleInfo> roleInfoList = new ArrayList<>();
        PageHelper.startPage(page,pageSize);
        if(!roleId.equals("1")){
            roleInfoList = roleMapper.selectAllRole(roleId,order,sortby,roleVo,page,pageSize);
        }else{
            roleInfoList = roleMapper.selectAllRole(null,order,sortby,roleVo,page,pageSize);
        }
        PageInfo<RoleInfo> pageInfo = new PageInfo<>(roleInfoList);
        for (RoleInfo roleInfo : pageInfo.getList()) {
            if (roleInfo.getRoleState().equals("true")) {
                roleInfo.setRoleStateName("有效");
            }
            if (roleInfo.getRoleState().equals("false")) {
                roleInfo.setRoleStateName("无效");
            }
        }
        return pageInfo;

    }

    @Override
    public List<Role> getRoleDownInfo() {
        /*List<String> companyIds = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        List<Role> roleList=new ArrayList<>();
        if (!rlUserRoleServiceImpl.getRoleByUserId(UserUtils.getUserId()).equals("1")) {
            List<RlRoleData> rlRoleDataList = rlRoleDataMapper.selectRlRoleDataByUserId(UserUtils.getUserId());
            for (RlRoleData rlRoleData : rlRoleDataList) {
                if (rlRoleData.getColumnType().equals("A"))
                    companyIds.add(rlRoleData.getColumnValue());
            }
            if (companyIds.size() == 0) {
                roleList.add(roleMapper.selectByPrimaryKey(rlUserRoleServiceImpl.getRoleByUserId(UserUtils.getUserId())));
                return roleList;
            } else {
                for (String companyId : companyIds) {
                    ids.addAll(companyService.getCompanyIdsByPid(companyId));
                }
            }
        }
        return roleMapper.selectRoleDownInfo(ids);*/
        String roleId = rlUserRoleServiceImpl.getRoleByUserId(UserUtils.getUserId());
        return roleMapper.selectRoleDownInfo(roleId);
    }
}
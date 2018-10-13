package cn.plou.web.system.permission.userLogin.service.impl;

import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.utils.*;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.impl.TypeMstServiceImpl;
import cn.plou.web.system.permission.rlUserRole.dao.RlUserRoleMapper;
import cn.plou.web.system.permission.rlUserRole.entity.RlUserRole;
import cn.plou.web.system.permission.rlUserRole.service.impl.RlUserRoleServiceImpl;
import cn.plou.web.system.permission.rlUserRole.vo.RlUserRoleVo;
import cn.plou.web.system.permission.role.dao.RoleMapper;
import cn.plou.web.system.permission.role.entity.Role;
import cn.plou.web.system.permission.role.service.RoleService;
import cn.plou.web.system.permission.role.vo.RoleInfo;
import cn.plou.web.system.permission.userDataHistory.entity.UserDataHistory;
import cn.plou.web.system.permission.userDataHistory.service.impl.UserDataHistoryServiceImpl;
import cn.plou.web.system.permission.userLogin.dao.UserLoginMapper;
import cn.plou.web.system.permission.userLogin.entity.UserLogin;
import cn.plou.web.system.permission.userLogin.service.UserLoginService;
import cn.plou.web.system.permission.userLogin.vo.UserLoginInfo;
import cn.plou.web.system.permission.userLogin.vo.UserLoginVo;
import cn.plou.web.system.permission.userPageHistory.entity.UserPageHistory;
import cn.plou.web.system.permission.userPageHistory.service.impl.UserPageHistoryServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    UserLoginMapper userLoginMapper;
    @Autowired
    RlUserRoleServiceImpl rlUserRoleServiceImpl;
    @Autowired
    private UserPageHistoryServiceImpl userPageHistoryServiceImpl;
    @Autowired
    private UserDataHistoryServiceImpl userDataHistoryServiceImpl;
    @Autowired
    private TypeMstServiceImpl typeMstServiceImpl;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleService roleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addUserLogin(UserLoginInfo userLoginInfo) {
        List<UserLoginInfo> userLoginInfoList = userLoginMapper.selectAllUserLogin(null,null, null, null,null,null,null);
//        List<Integer>ids=new ArrayList<Integer>();
        for(UserLoginInfo userLoginInfo1:userLoginInfoList) {
            if (userLoginInfo.getUserCode().equals(userLoginInfo1.getUserCode())) {
                throw new BusinessException("账号已存在");
            }
//            if (userLoginInfo.getUsername() != null) {
//                if (userLoginInfo.getUsername().equals(userLoginInfo1.getUsername())) {
//                    throw new BusinessException("用户名已存在");
//                }
//            }
//            if (userLoginInfo.getEmail() != null) {
//                if (userLoginInfo1.getEmail().equals(userLoginInfo1.getEmail())) {
//                    throw new BusinessException("邮箱已存在");
//                }
//            }
            if (userLoginInfo.getIdentityCard() != null) {
                if (userLoginInfo.getIdentityCard().equals(userLoginInfo1.getIdentityCard())) {
                    throw new BusinessException("身份证号已存在");
                }
            }
            if (userLoginInfo.getPhone() != null) {
                if (userLoginInfo.getPhone().equals(userLoginInfo1.getPhone())) {
                    throw new BusinessException("手机号已存在");
                }
            }
        }
        userLoginInfo.setUserPsw(SecureUtils.md5(userLoginInfo.getUserPsw()));
        userLoginInfo.setStatus("1");
        RlUserRole rlUserRole=new RlUserRole();
        rlUserRole.setRoleId(userLoginInfo.getRoleId());
        rlUserRole.setUserid(userLoginInfo.getUserCode());
        rlUserRoleServiceImpl.addRlUserRole(rlUserRole);
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("userLogin");
        userPageHistory.setAct("addUserLogin");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return userLoginMapper.insertSelective(userLoginInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyUserLoginBatch(UserLoginVo userLoginVo) {
        int count=0;
        if(userLoginVo.getRoleId()!=null){
            RlUserRoleVo rlUserRoleVo=new RlUserRoleVo();
            rlUserRoleVo.setRoleId(userLoginVo.getRoleId());
            List<String> userCodes=userLoginVo.getUserCodes();
            rlUserRoleVo.setUserCodes(userCodes);
            count= rlUserRoleServiceImpl.modifyRlUserRoleBatch(rlUserRoleVo);
        }

        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("userLogin");
        userPageHistory.setAct("modifyUserLoginBatch");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);

        for(String userCode : userLoginVo.getUserCodes()){
            UserDataHistory userDataHistory = new UserDataHistory();
            userDataHistory.setConsumerId(userCode);
            userDataHistory.setPage("user");
            userDataHistory.setInfo("修改用户信息，用户ID:" + (userCode));
            userDataHistoryServiceImpl.addUserDataHistory(userDataHistory);
        }
        if(userLoginVo.getNotes()!=null||userLoginVo.getStatus()!=null) {
            return userLoginMapper.updateUserLoginBatch(userLoginVo);
        }else{
            return  count;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserLoginBatchByIds(List<String> userCodes) {
        rlUserRoleServiceImpl.deleteRlUserRoleByUserId(userCodes);
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("userLogin");
        userPageHistory.setAct("deleteUserLoginBatchByIds");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);

        for(String userCode : userCodes){
            UserDataHistory userDataHistory = new UserDataHistory();
            userDataHistory.setConsumerId(userCode);
            userDataHistory.setPage("user");
            userDataHistory.setInfo("删除用户信息，用户ID:" + (userCode));
            userDataHistoryServiceImpl.addUserDataHistory(userDataHistory);
        }
        return userLoginMapper.deleteUserLoginBatchByIds(userCodes);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyUserLogin(UserLoginInfo userLoginInfo) {
        if(userLoginInfo.getRoleId()!=null){
            RlUserRoleVo rlUserRoleVo=new RlUserRoleVo();
            rlUserRoleVo.setRoleId(userLoginInfo.getRoleId());
            List<String> userCodes=new ArrayList<String>();
            userCodes.add(userLoginInfo.getUserCode());
            rlUserRoleVo.setUserCodes(userCodes);
            rlUserRoleServiceImpl.modifyRlUserRoleBatch(rlUserRoleVo);
        }

        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("userLogin");
        userPageHistory.setAct("modifyUserLogin");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);

        UserDataHistory userDataHistory = new UserDataHistory();
        userDataHistory.setConsumerId(userLoginInfo.getUserCode());
        userDataHistory.setPage("user");
        userDataHistory.setInfo("修改用户信息，用户ID:" + (userLoginInfo.getUserCode()));
        userDataHistoryServiceImpl.addUserDataHistory(userDataHistory);
        return userLoginMapper.updateByPrimaryKeySelective(userLoginInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyUserPsw(UserLogin userLogin) {
        userLogin.setUserPsw(SecureUtils.md5(userLogin.getUserPsw()));
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("userLogin");
        userPageHistory.setAct("modifyUserPsw");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);

        UserDataHistory userDataHistory = new UserDataHistory();
        userDataHistory.setConsumerId(userLogin.getUserCode());
        userDataHistory.setPage("user");
        userDataHistory.setInfo("修改用户密码，用户ID:" + (userLogin.getUserCode()));
        userDataHistoryServiceImpl.addUserDataHistory(userDataHistory);
        return userLoginMapper.updateByPrimaryKeySelective(userLogin);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageInfo<UserLoginInfo> getAllUserLogin( UserLoginVo userLoginVo, String order, String sortby, Integer page, Integer pageSize,String roleId) {
        sortby=CamelCaseUtil.toUnderscoreCase(sortby);
        if(sortby!=null){
            if(sortby.equals("status_name")){
                sortby="status";
            }
        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("userLogin");
        userPageHistory.setAct("getAllUserLogin");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
//        if(page!=null) {
//            page = (page - 1) * pageSize;
//        }
        //List<RoleInfo> roleInfoList = roleMapper.selectAllRole(null, null, null, null, null, null);
        //List<RoleInfo> roleInfoList = roleService.getAllRole(null,null,null,null,null).getList();
        List<Role> roleInfoList = roleService.getRoleDownInfo();
        List<String> roleIds=new ArrayList<>();
        if(roleInfoList.size()==0){
            roleInfoList.add((RoleInfo) roleMapper.selectByPrimaryKey(rlUserRoleServiceImpl.getRoleByUserId(UserUtils.getUserId())));
        }
        for(Role roleInfo:roleInfoList){
            roleIds.add(roleInfo.getRoleId());
        }
        PageHelper.startPage(page,pageSize);
        List<UserLoginInfo> userLoginInfoList = userLoginMapper.selectAllUserLogin(roleIds, userLoginVo, order, sortby, page, pageSize,roleId);
        PageInfo<UserLoginInfo> pageInfo=new PageInfo<>(userLoginInfoList);
        List<TypeMst> typeMstList = typeMstServiceImpl.getAllTypeMstByRedis();
        for(UserLoginInfo userLoginInfo:pageInfo.getList()){
            userLoginInfo.setStatusName(typeMstServiceImpl.getTypeNameById(typeMstList,userLoginInfo.getStatus()));
//            if(userLoginInfo.getStatus()!=null) {
//                if(typeMstServiceImpl.getTypeMstById(userLoginInfo.getStatus())!=null) {
//                    userLoginInfo.setStatusName(typeMstServiceImpl.getTypeMstById(userLoginInfo.getStatus()).getTypeName());
//                }
//            }
        }
        return pageInfo;
    }

    @Override
    public UserLogin getUserLoginById(String userId) {
        return userLoginMapper.selectByPrimaryKey(userId);
    }

    @Override
    public UserLogin getByUsername(String username) {
        return userLoginMapper.selectByUsername(username);
    }
}

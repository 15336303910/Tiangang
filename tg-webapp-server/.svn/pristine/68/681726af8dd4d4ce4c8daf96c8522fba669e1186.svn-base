package cn.plou.web.system.permission.rlUserRole.service.impl;

import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.system.permission.rlUserRole.dao.RlUserRoleMapper;
import cn.plou.web.system.permission.rlUserRole.entity.RlUserRole;
import cn.plou.web.system.permission.rlUserRole.service.RlUserRoleService;
import cn.plou.web.system.permission.rlUserRole.vo.RlUserRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class RlUserRoleServiceImpl implements RlUserRoleService {
    @Autowired
    private RlUserRoleMapper rlUserRoleMapper;
    @Override
    public String getRoleByUserId(String userid) {
        String roleId = rlUserRoleMapper.selectRoleByUserId(userid);
        if(roleId!=null){
        return roleId;
        }else {
            throw new BusinessException("该用户没有有效角色");
        }
    }

    @Override
    public List<RlUserRole> getRlUserRoleByRoleId(List<String> roleIds) {
        return rlUserRoleMapper.selectRlUserRoleByRoleId(roleIds);
    }

    @Override
    public List<RlUserRole> getAllRlUserRole() {
        return rlUserRoleMapper.selectAllRlUserRole();
    }

    @Override
    public int addRlUserRole(RlUserRole rlUserRole) {
        List<RlUserRole> rlUserRoleList = rlUserRoleMapper.selectAllRlUserRole();
        if(rlUserRoleList.size()==0){
            rlUserRole.setId("1");
        }else {
            List<Integer> ids = new ArrayList<Integer>();
            for (RlUserRole rlUserRole1 : rlUserRoleList) {
                ids.add(Integer.valueOf(rlUserRole1.getId()));
            }
            Collections.sort(ids);
            Collections.reverse(ids);
            rlUserRole.setId(""+(ids.get(0)+1));
        }
        return rlUserRoleMapper.insertSelective(rlUserRole);
    }

    @Override
    public int modifyRlUserRoleBatch(RlUserRoleVo rlUserRoleVo) {
        int count=0;
        deleteRlUserRoleByUserId(rlUserRoleVo.getUserCodes());
        for(String userCode:rlUserRoleVo.getUserCodes()) {
            RlUserRole rlUserRole=new RlUserRole();
            rlUserRole.setUserid(userCode);
            rlUserRole.setRoleId(rlUserRoleVo.getRoleId());
            addRlUserRole(rlUserRole);
            count++;
        }
        return count;
    }

    public int deleteRlUserRoleByUserId(List<String> userIds){
        return rlUserRoleMapper.deleteByUserId(userIds);
    }

    public int deleteRlUserRoleByRoleId(List<String> roleIds){
        return rlUserRoleMapper.deleteByRoleId(roleIds);
    }
}

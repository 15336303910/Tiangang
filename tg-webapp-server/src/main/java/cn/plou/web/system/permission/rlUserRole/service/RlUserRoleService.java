package cn.plou.web.system.permission.rlUserRole.service;

import cn.plou.web.system.permission.rlUserRole.entity.RlUserRole;
import cn.plou.web.system.permission.rlUserRole.vo.RlUserRoleVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RlUserRoleService {
     String getRoleByUserId(String userid);
     List<RlUserRole> getRlUserRoleByRoleId(List<String> roleIds);
     List<RlUserRole>getAllRlUserRole();
     int addRlUserRole(RlUserRole rlUserRole);
     int modifyRlUserRoleBatch(RlUserRoleVo rlUserRoleVo);
}

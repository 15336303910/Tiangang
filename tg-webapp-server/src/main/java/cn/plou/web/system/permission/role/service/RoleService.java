package cn.plou.web.system.permission.role.service;

import cn.plou.web.system.permission.role.entity.Role;
import cn.plou.web.system.permission.role.vo.RoleInfo;
import cn.plou.web.system.permission.role.vo.RoleVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RoleService {
    int addRole(Role role);
    int modifyRoleBatch(RoleVo roleVo);
    int modifyRole(Role role);
    int deleteRoleBatchByIds(List<String> roleIds);
    PageInfo<RoleInfo> getAllRole(String order, String sortby, RoleVo roleVo, Integer page, Integer pageSize);
    List<Role>getRoleDownInfo();
}

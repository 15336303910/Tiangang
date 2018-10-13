package cn.plou.web.system.permission.rlMenuRole.service;

import cn.plou.web.system.permission.rlMenuRole.entity.RlMenuRole;
import cn.plou.web.system.permission.rlMenuRole.entity.RlMenuRoleKey;
import cn.plou.web.system.permission.rlMenuRole.vo.RlMenuRoleInfo;
import cn.plou.web.system.permission.rlMenuRole.vo.RlMenuRoleVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RlMenuRoleService {

    //添加用户功能权限
//    int addRlMenuRole(RlMenuRole rlMenuRole);

    //修改单个用户功能权限
//    int modifyRlMenuRole(RlMenuRole rlMenuRole);

    //批量修改用户功能权限
    int modifyRlMenuRoleBatch( RlMenuRoleVo rlMenuRoleVo);


    //按roleId查权限
    List<RlMenuRole> getRlMenuRoleByRoleId(String roleId);

    List<String> getAllMenuByRoleId(String roleId);
    public  List<String> getNewIds(int num);

    //查全部
//    List<RlMenuRole> getAllRlMenuRole();
}

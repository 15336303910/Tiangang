package cn.plou.web.system.permission.rlMenuRole.dao;

import cn.plou.web.system.permission.rlMenuRole.entity.RlMenuRole;
import cn.plou.web.system.permission.rlMenuRole.entity.RlMenuRoleKey;
import cn.plou.web.system.permission.rlMenuRole.vo.RlMenuRoleInfo;
import cn.plou.web.system.permission.rlMenuRole.vo.RlMenuRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RlMenuRoleMapper {

    //添加用户功能权限
    int insertRlMenuRole(RlMenuRole rlMenuRole);

    //批量添加用户功能权限
    int insertRlMenuRoleBatch(List<RlMenuRole> rlMenuRoles);
    //删除
    int deleteRlMenuRoleByRoleId(String roleId);

    List<String> selectCMenuNotInPMenu(String roleId);
    int deleteCMenuNotInPMenu(@Param("roleIds") List<String> roleIds,
                              @Param("pageNames")List<String> pageNames);

    int selectMaxId();
    //单改
//    int updateRlMenuRole(RlMenuRole rlMenuRole);

    //批量修改用户功能权限
    int insertNewRlMenuRoleBatch(List<RlMenuRole> rlMenuRoles);

    List<String> selectAllIds();

    List<String> selectAllMenuByRoleId(String roleId);
    //按roleId查
    List<RlMenuRole> selectRlMenuRoleByRoleId(String roleId);

    //查全部
//    List<RlMenuRole> selectAllRlMenuRole();

//    int insert(RlMenuRole record);
}
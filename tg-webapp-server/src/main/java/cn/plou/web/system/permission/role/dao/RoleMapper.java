package cn.plou.web.system.permission.role.dao;


import cn.plou.web.system.permission.role.entity.Role;
import cn.plou.web.system.permission.role.vo.RoleInfo;
import cn.plou.web.system.permission.role.vo.RoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(String roleId);

    int insert(Role record);

    int insertSelective(Role record);

    int deleteRoleBatchByIds(List<String> roleIds);

    int updateRoleBatch(RoleVo roleVo);

    List<String> selectAllRoleIdsByRoleId(String roleId);

    //List<Role> selectRoleDownInfo(@Param("ids")List<String> ids);

    List<Role> selectRoleDownInfo(@Param("roleId")String roleId);

    /*List<RoleInfo>selectAllRole(@Param("ids") List<String> ids,@Param("order") String order, @Param("sortby")String sortby, @Param("roleVo")RoleVo roleVo,
                                @Param("page")Integer page, @Param("pageSize")Integer pageSize);*/

    List<RoleInfo>selectAllRole(@Param("roleId") String roleId,@Param("order") String order, @Param("sortby")String sortby, @Param("roleVo")RoleVo roleVo,
                                @Param("page")Integer page, @Param("pageSize")Integer pageSize);
    Role selectByPrimaryKey(String roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

		List<RoleInfo> selectAllRoleOnlyStation(@Param("ids") List<String> ids,@Param("order") String order, @Param("sortby")String sortby, @Param("roleVo")RoleVo roleVo,
        @Param("page")Integer page, @Param("pageSize")Integer pageSize);
}
package cn.plou.web.system.permission.rlUserRole.dao;

import cn.plou.web.system.permission.rlUserRole.entity.RlUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RlUserRoleMapper {
    int deleteByPrimaryKey(String id);

    int deleteByUserId(List<String> userIds);

    int deleteByRoleId(List<String> roleIds);

    int insert(RlUserRole record);

    int insertSelective(RlUserRole record);

    RlUserRole selectByPrimaryKey(String id);

    List<RlUserRole> selectRlUserRoleByRoleId(List<String> roleIds);

    int updateByPrimaryKeySelective(RlUserRole record);

    int updateByPrimaryKey(RlUserRole record);

    String selectRoleByUserId(String userid);

    List<RlUserRole> selectAllRlUserRole();
}
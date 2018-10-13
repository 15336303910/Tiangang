package cn.plou.web.system.permission.rlRoleValve.dao;

import cn.plou.web.system.permission.rlRoleValve.entity.RlRoleValve;
import cn.plou.web.system.permission.rlRoleValve.vo.RlRoleValveVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RlRoleValveMapper {

    int insertRlRoleValve(RlRoleValve rlRoleValve);

    int updateRlRoleValve(RlRoleValveVo rlRoleValveVo);

//    int updateRlRoleValveBatch(RlRoleValveVo rlRoleValveVo);

    List<RlRoleValve> selectRlRoleValveByRoleId(String roleId);

    List<String> selectAllIds();

//    List<RlRoleValve> selectAllRlRoleValve(@Param("rlRoleValveVo") RlRoleValveVo rlRoleValveVo, @Param("order")String order, @Param("sortby")String sortby);

    int deleteRlRoleValve(String roleId);

    List<RlRoleValve> selectRlRoleValveByUserId(String userId);

//    int insert(RlRoleValve record);//    int updateByPrimaryKey(RlRoleValve record);
//
////
}
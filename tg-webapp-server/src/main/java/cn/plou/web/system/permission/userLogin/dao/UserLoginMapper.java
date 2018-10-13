package cn.plou.web.system.permission.userLogin.dao;


import cn.plou.web.system.permission.userLogin.entity.UserLogin;
import cn.plou.web.system.permission.userLogin.vo.UserLoginInfo;
import cn.plou.web.system.permission.userLogin.vo.UserLoginVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserLoginMapper {
    int deleteByPrimaryKey(String userCode);

    int insert(UserLogin record);

    int selectMaxId();

    int insertSelective(UserLogin record);

    UserLogin selectByPrimaryKey(String userCode);

    UserLogin selectByUsername(String username);

    int updateByPrimaryKeySelective(UserLogin record);

    int updateByPrimaryKey(UserLogin record);

    int updateUserLoginBatch(UserLoginVo userLoginVo);

    int deleteUserLoginBatchByIds(List<String> userCodes);

    List<UserLoginInfo> selectAllUserLogin( @Param("roleIds")List<String> roleIds,@Param("userLoginVo") UserLoginVo userLoginVo, @Param("order") String order,
                                           @Param("sortby")String sortby, @Param("page")Integer page, @Param("pageSize")Integer pageSize,
                                            @Param("roleId")String roleId);
}
package cn.plou.web.system.permission.userLogin.service;

import cn.plou.web.system.permission.userLogin.entity.UserLogin;
import cn.plou.web.system.permission.userLogin.vo.UserLoginInfo;
import cn.plou.web.system.permission.userLogin.vo.UserLoginVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserLoginService {
    int addUserLogin(UserLoginInfo userLoginInfo);
    int modifyUserLoginBatch(UserLoginVo userLoginVo);
    int deleteUserLoginBatchByIds(List<String> userCodes);
    int modifyUserLogin(UserLoginInfo userLoginInfo);
    int modifyUserPsw(UserLogin userLogin);
    PageInfo<UserLoginInfo> getAllUserLogin( UserLoginVo userLoginVo, String order, String sortby, Integer page, Integer pageSize,String roleId);
    UserLogin getUserLoginById(String userId);
    UserLogin getByUsername(String username);
}

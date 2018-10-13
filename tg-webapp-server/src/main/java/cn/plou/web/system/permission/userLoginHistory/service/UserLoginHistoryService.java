package cn.plou.web.system.permission.userLoginHistory.service;

import cn.plou.web.system.permission.userLoginHistory.entity.UserLoginHistory;
import cn.plou.web.system.permission.userLoginHistory.vo.UserLoginHistoryVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserLoginHistoryService {

    String getNewId();

    int addUserLoginHistory(UserLoginHistory userLoginHistory);

    PageInfo<UserLoginHistory> getAllUserLoginHistory(UserLoginHistoryVo userLoginHistoryVo, String order, String sortby, Integer page, Integer pageSize);
}

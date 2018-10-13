package cn.plou.web.system.permission.userPageHistory.service;

import cn.plou.web.system.permission.userPageHistory.entity.UserPageHistory;
import cn.plou.web.system.permission.userPageHistory.vo.UserPageHistoryVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserPageHistoryService {

    int addUserPageHistory(UserPageHistory userPageHistory);

    String getNewId();

    PageInfo<UserPageHistory> getAllUserPageHistory(String companyId, String pageName, UserPageHistoryVo userPageHistoryVo, String order, String sortby, Integer page, Integer pageSize);
}

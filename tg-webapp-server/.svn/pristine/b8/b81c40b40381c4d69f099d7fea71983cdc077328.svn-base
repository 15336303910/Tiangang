package cn.plou.web.system.permission.userDataHistory.service;

import cn.plou.web.system.permission.userDataHistory.entity.UserDataHistory;
import cn.plou.web.system.permission.userDataHistory.vo.UserDataHistoryInfo;
import cn.plou.web.system.permission.userDataHistory.vo.UserDataHistoryVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserDataHistoryService {

    //添加数据操作记录
    int addUserDataHistory(UserDataHistory userDataHistory);

    //查询数据操作记录
    PageInfo<UserDataHistoryInfo> getAllUserDataHistory(UserDataHistoryVo userDataHistoryVo, String pageName, String order, String sortby, Integer page, Integer pageSize);

    String getNewId();
}

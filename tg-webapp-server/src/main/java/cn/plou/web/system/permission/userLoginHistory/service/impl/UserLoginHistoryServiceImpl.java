package cn.plou.web.system.permission.userLoginHistory.service.impl;

import cn.plou.web.common.utils.Tools;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.permission.userLogin.service.UserLoginService;
import cn.plou.web.system.permission.userLoginHistory.dao.UserLoginHistoryMapper;
import cn.plou.web.system.permission.userLoginHistory.entity.UserLoginHistory;
import cn.plou.web.system.permission.userLoginHistory.service.UserLoginHistoryService;
import cn.plou.web.system.permission.userLoginHistory.vo.UserLoginHistoryVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static cn.plou.web.common.utils.Tools.getMaxIdNoSize;

@Component
public class UserLoginHistoryServiceImpl implements UserLoginHistoryService {
    @Autowired
    private UserLoginHistoryMapper userLoginHistoryMapper;
    @Autowired
    private UserLoginService userLoginService;

    @Override
    public String getNewId() {
        if (userLoginHistoryMapper.selectAllIds().size() == 0){
           return "1";
        }else {
            return getMaxIdNoSize(userLoginHistoryMapper.selectAllIds());
        }
    }

    @Override
    public int addUserLoginHistory(UserLoginHistory userLoginHistory){
        userLoginHistory.setUserCode(UserUtils.getUserId());
        userLoginHistory.setUsername(userLoginService.getUserLoginById(UserUtils.getUserId()).getUsername());
        userLoginHistory.setIntime(Tools.timeStrToDate(Tools.curTime(null),"yyyy-MM-dd HH:mm:ss"));
        userLoginHistory.setPrimaryId(UserUtils.getUserId()+"-"+UUID.randomUUID().toString().replace("-",""));
        return userLoginHistoryMapper.insertUserLoginHistory(userLoginHistory);
    }

    @Override
    public PageInfo<UserLoginHistory> getAllUserLoginHistory(UserLoginHistoryVo userLoginHistoryVo, String order, String sortby, Integer page, Integer pageSize){
//        if(page!=null) {
//            page = (page - 1) * pageSize;
//        }
        List<String> userCodes = new ArrayList<>();
        userCodes.add(UserUtils.getUserId());
        PageHelper.startPage(page,pageSize);
        List<UserLoginHistory> userLoginHistoryList = userLoginHistoryMapper.selectAllUserLoginHistory(userLoginHistoryVo, order, sortby, page, pageSize,userCodes);
        PageInfo<UserLoginHistory> pageInfo=new PageInfo<>(userLoginHistoryList);
        return pageInfo;
    }
}
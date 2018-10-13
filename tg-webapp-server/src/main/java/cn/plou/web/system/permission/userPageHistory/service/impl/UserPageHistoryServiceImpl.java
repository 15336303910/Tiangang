package cn.plou.web.system.permission.userPageHistory.service.impl;

import cn.plou.web.common.utils.CamelCaseUtil;
import cn.plou.web.common.utils.Tools;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.permission.rlMenuRole.entity.RlMenuRole;
import cn.plou.web.system.permission.rlMenuRole.service.RlMenuRoleService;
import cn.plou.web.system.permission.rlRoleData.entity.RlRoleData;
import cn.plou.web.system.permission.userLogin.dao.UserLoginMapper;
import cn.plou.web.system.permission.userPageHistory.dao.UserPageHistoryMapper;
import cn.plou.web.system.permission.userPageHistory.entity.UserPageHistory;
import cn.plou.web.system.permission.userPageHistory.service.UserPageHistoryService;
import cn.plou.web.system.permission.userPageHistory.vo.UserPageHistoryVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static cn.plou.web.common.utils.Tools.getMaxIdNoSize;

@Component
public class UserPageHistoryServiceImpl implements UserPageHistoryService {

    @Autowired
    private UserPageHistoryMapper userPageHistoryMapper;
    @Autowired
    private UserLoginMapper userLoginMapper;
    @Autowired
    private RlMenuRoleService rlMenuRoleService;

    @Override
    public int addUserPageHistory(UserPageHistory userPageHistory) {
        userPageHistory.setUserCode(UserUtils.getUserId());
        userPageHistory.setUsername(userLoginMapper.selectByPrimaryKey(UserUtils.getUserId()).getUsername());
        //userPageHistory.setIntime(Tools.timeStrToDate(Tools.curTime(null), "yyyy-MM-dd HH:mm:ss"));
        userPageHistory.setIntime(new Date());
        userPageHistory.setPrimaryId(UserUtils.getUserId()+"-"+UUID.randomUUID().toString().replace("-",""));
        return userPageHistoryMapper.insertUserPageHistory(userPageHistory);
    }

    @Override
    public String getNewId() {
        if (userPageHistoryMapper.selectAllIds().size() == 0) {
            return "1";
        } else {
            return getMaxIdNoSize(userPageHistoryMapper.selectAllIds());
        }
    }

    @Override
    public PageInfo<UserPageHistory> getAllUserPageHistory(String companyId, String pageName, UserPageHistoryVo userPageHistoryVo, String order, String sortby, Integer page, Integer pageSize) {
//        if(page!=null) {
//            page = (page - 1) * pageSize;
//        }
        List<String> userCodes = new ArrayList<>();
        userCodes.add(UserUtils.getUserId());
        sortby = CamelCaseUtil.toUnderscoreCase(sortby);
        PageHelper.startPage(page,pageSize);
        List<UserPageHistory> userPageHistoryList = userPageHistoryMapper.selectAllUserPageHistory(companyId, pageName, userPageHistoryVo, order, sortby, page, pageSize,userCodes);
        PageInfo<UserPageHistory> pageInfo=new PageInfo<>(userPageHistoryList);
        return pageInfo;
    }
}

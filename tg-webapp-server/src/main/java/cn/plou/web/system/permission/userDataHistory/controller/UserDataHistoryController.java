package cn.plou.web.system.permission.userDataHistory.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.permission.userDataHistory.service.UserDataHistoryService;
import cn.plou.web.system.permission.userDataHistory.entity.UserDataHistory;

import cn.plou.web.system.permission.userDataHistory.vo.UserDataHistoryInfo;
import cn.plou.web.system.permission.userDataHistory.vo.UserDataHistorySelectInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static cn.plou.web.common.config.common.Cond.getCond;
import static cn.plou.web.common.utils.PageInfo.getPageInfo;

@RestController
@RequestMapping("${systemPath}/userDataHistory")
@EnableSwagger2
@Api(description = "数据操作记录")
public class UserDataHistoryController  {

    @Autowired
    private UserDataHistoryService userDataHistoryService;

    @ApiOperation(value = "添加数据操作记录")
    @PostMapping("/addUserDataHistory")
    public Boolean addUserDataHistory(@RequestBody UserDataHistory userDataHistory){
        return userDataHistoryService.addUserDataHistory(userDataHistory) == 1;
    }

    @ApiOperation(value = "根据条件查询全部数据操作记录")
    @PostMapping("/getUserDataHistory")
    @RequiresPermissions("40804")
    public Root getAllUserDataHistory(@RequestBody UserDataHistorySelectInfo userDataHistorySelectInfo){
        Root root = new Root();
//        PageHelper.startPage(userDataHistorySelectInfo.getPage(),userDataHistorySelectInfo.getPageSize());
        PageInfo<UserDataHistoryInfo> pageInfo = userDataHistoryService.getAllUserDataHistory(userDataHistorySelectInfo.getSearchCondition(),
                userDataHistorySelectInfo.getPageName(),userDataHistorySelectInfo.getOrder(),userDataHistorySelectInfo.getSortby(),userDataHistorySelectInfo.getPage(),userDataHistorySelectInfo.getPageSize());
//        PageInfo<UserDataHistoryInfo> pageInfo=new PageInfo(userDataHistoryInfoList);
        root.setData(pageInfo.getList());
        root.setCond(getCond(userDataHistorySelectInfo.getPage(),userDataHistorySelectInfo.getPageSize(),(int)pageInfo.getTotal(),
                userDataHistorySelectInfo.getOrder(),userDataHistorySelectInfo.getSortby()));
        return root;
    }


}

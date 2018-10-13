package cn.plou.web.system.permission.userPageHistory.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Cond;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.permission.userLoginHistory.vo.UserLoginHistorySelectInfo;
import cn.plou.web.system.permission.userPageHistory.entity.UserPageHistory;
import cn.plou.web.system.permission.userPageHistory.service.UserPageHistoryService;
import cn.plou.web.system.permission.userPageHistory.vo.UserPageHistorySelectInfo;
import cn.plou.web.system.permission.userPageHistory.vo.UserPageHistoryVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static cn.plou.web.common.config.common.Cond.getCond;
import static cn.plou.web.common.utils.PageInfo.getPageInfo;

@RestController
@RequestMapping("${systemPath}/userPageHistory")
@EnableSwagger2
@Api(description = "功能操作记录")
public class UserPageHistoryController  {

    @Autowired
    private UserPageHistoryService userPageHistoryService;

    @ApiOperation(value = "增加一条功能操作记录")
    @PostMapping("/addUserPageHistory")
    public Boolean addUserPageHistory(@RequestBody UserPageHistory userPageHistory){

        return  userPageHistoryService.addUserPageHistory(userPageHistory) == 1;
    }

//    @ApiOperation(value = "根据id批量删功能操作记录")
//    @DeleteMapping("/deleteUserPageHistoryBatchByIds")
//    public Root deleteUserPageHistoryBatchByIds(@RequestBody List<String> ids){
//        Root root = new Root();
//        userPageHistoryService.deleteBatchByIds(ids);
//        return root;
//    }

//    @ApiOperation(value="批量修改功能操作记录")
//    @PutMapping("/modifyUserPageHistoryBatch")
//    public Root modifyUserPageHistoryServiceBatch(@RequestBody Staff staff){
//        Root root = new Root();
//        root.setData(userPageHistoryService.modifyserPageHistoryBatch(rlRoleData));
//        return root;
//    }

//    @ApiOperation(value = "根据id获取对应用户功能操作记录")
//    @GetMapping("/getUserPageHistoryById")
//    public Root getserPageHistoryById(@RequestParam("id") String id){
//        Root root = new Root();
//        root.setData(userPageHistoryService.getStaffById(id));
//        return root;
//    }

    @ApiOperation(value = "根据查询条件获取全部用户功能操作记录")
    @PostMapping("/getAllUserPageHistory")
    @RequiresPermissions("40704")
    public Root getAllUserPageHistory(@RequestBody UserPageHistorySelectInfo UserPageHistorySelectInfo){
        Root root = new Root();
//        PageHelper.startPage(UserPageHistorySelectInfo.getPage(), UserPageHistorySelectInfo.getPageSize());
        PageInfo<UserPageHistory> pageInfo = userPageHistoryService.getAllUserPageHistory(UserPageHistorySelectInfo.getCompanyId(),
                UserPageHistorySelectInfo.getPageName(),UserPageHistorySelectInfo.getSearchCondition(),UserPageHistorySelectInfo.getOrder(),
                UserPageHistorySelectInfo.getSortby(),UserPageHistorySelectInfo.getPage(),UserPageHistorySelectInfo.getPageSize());
//        PageInfo<UserPageHistory> pageInfo=new PageInfo<>(userPageHistoryList);
        root.setData(pageInfo.getList());
        root.setCond(Cond.getCond(pageInfo.getPageNum(),pageInfo.getPageSize(),(int)pageInfo.getTotal(),UserPageHistorySelectInfo.getOrder(),UserPageHistorySelectInfo.getSortby()));
        return root;
    }
}

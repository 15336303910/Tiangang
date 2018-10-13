package cn.plou.web.system.permission.userLoginHistory.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.permission.userLoginHistory.entity.UserLoginHistory;
import cn.plou.web.system.permission.userLoginHistory.service.UserLoginHistoryService;
import cn.plou.web.system.permission.userLoginHistory.vo.UserLoginHistorySelectInfo;
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
@RequestMapping("${systemPath}/userLoginHistory")
@EnableSwagger2
@Api(description = "访问记录")
public class UserLoginHistoryController {

    @Autowired
    private UserLoginHistoryService userLoginHistoryService;

    @ApiOperation(value = "增加一个访问记录")
    @PostMapping("/addUserLoginHistory")
    public Boolean addUserLoginHistory(@RequestBody UserLoginHistory userLoginHistory){

        return userLoginHistoryService.addUserLoginHistory(userLoginHistory) == 1;
    }

//    @ApiOperation(value = "根据id批量删除访问记录")
//    @DeleteMapping("/deleteUserLoginHistoryBatchByIds")
//    public Root deleteUserLoginHistoryBatchByIds(@RequestBody List<String> ids){
//        Root root = new Root();
//        userLoginHistoryService.deleteBatchByIds(ids);
//        return root;
//    }

//    @ApiOperation(value="批量修改访问记录")
//    @PutMapping("/modifyUserLoginHistoryBatch")
//    public Root modifyUserLoginHistoryBatch(@RequestBody UserLoginHistory userLoginHistory){
//        Root root = new Root();
//        root.setData(userLoginHistoryService.modifyStaffBatch(userLoginHistory));
//        return root;
//    }

//    @ApiOperation(value = "根据id获取对应访问记录")
//    @GetMapping("/getUserLoginHistoryById")
//    public Root getUserLoginHistoryById(@RequestParam("id") String id){
//        Root root = new Root();
//        root.setData(userLoginHistoryService.getStaffById(id));
//        return root;
//    }

    @ApiOperation(value = "根据查询条件获取全部用户访问记录")
    @PostMapping("/getAllUserLoginHistory")
    @RequiresPermissions("40604")
    public Root getAlluserLoginHistory(@RequestBody UserLoginHistorySelectInfo userLoginHistorySelectInfo){
        Root root = new Root();
//        PageHelper.startPage(userLoginHistorySelectInfo.getPage(),userLoginHistorySelectInfo.getPageSize());
        PageInfo<UserLoginHistory> pageInfo = userLoginHistoryService.getAllUserLoginHistory(userLoginHistorySelectInfo.getSearchCondition(),userLoginHistorySelectInfo.getOrder(),
                userLoginHistorySelectInfo.getSortby(),userLoginHistorySelectInfo.getPage(),userLoginHistorySelectInfo.getPageSize());
//        PageInfo<UserLoginHistory> pageInfo=new PageInfo(userLoginHistoryList);
        root.setData(pageInfo.getList());
        root.setCond(getCond(pageInfo.getPageNum(),pageInfo.getPageSize(),
                (int)pageInfo.getTotal(),userLoginHistorySelectInfo.getOrder(),userLoginHistorySelectInfo.getSortby()));
        return root;
    }
}

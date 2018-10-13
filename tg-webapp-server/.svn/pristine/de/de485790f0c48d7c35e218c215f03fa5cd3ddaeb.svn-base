package cn.plou.web.system.permission.userLogin.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Cond;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.PageInfo;
import cn.plou.web.system.permission.userLogin.entity.UserLogin;
import cn.plou.web.system.permission.userLogin.service.UserLoginService;
import cn.plou.web.system.permission.userLogin.vo.UserLoginInfo;
import cn.plou.web.system.permission.userLogin.vo.UserLoginSelectInfo;
import cn.plou.web.system.permission.userLogin.vo.UserLoginVo;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@EnableSwagger2
@RequestMapping("${systemPath}/userLogin")
@Api(description = "登陆用户信息")
public class UserLoginController {

    @Autowired
    UserLoginService userLoginService;

//    @ApiOperation(value = "根据id获取登陆用户信息")
//    @GetMapping("/getUserLoginById")
//    public Root getUserLoginById(@RequestParam String userCode){
//        Root root = new Root();
//
//        return root;
//    }

    @ApiOperation(value = "增加一条登陆用户信息")
    @PostMapping("/addUserLogin")
    @RequiresPermissions("40201")
    public Boolean addUserLogin(@RequestBody UserLoginInfo userLoginInfo){
        return userLoginService.addUserLogin(userLoginInfo)==1;
    }

    @ApiOperation(value = "根据查询条件获取全部登陆用户信息")
    @PostMapping ("/getAllUserLogin")
    @RequiresPermissions("40204")
    public Root getAllUserLogin(@RequestBody(required=false) UserLoginSelectInfo userLoginSelectInfo){
        Root root = new Root();
//        PageHelper.startPage(userLoginSelectInfo.getPage(),userLoginSelectInfo.getPageSize());
        com.github.pagehelper.PageInfo<UserLoginInfo> pageInfo = userLoginService.getAllUserLogin(userLoginSelectInfo.getSearchCondition(), userLoginSelectInfo.getOrder(), userLoginSelectInfo.getSortby(),
                userLoginSelectInfo.getPage(),userLoginSelectInfo.getPageSize(),userLoginSelectInfo.getRoleId());
//        com.github.pagehelper.PageInfo<UserLoginInfo> pageInfo=new com.github.pagehelper.PageInfo(userLoginInfoList);
        root.setData(pageInfo.getList());
        root.setCond(Cond.getCond(pageInfo.getPageNum(),pageInfo.getPageSize(),(int)pageInfo.getTotal(),userLoginSelectInfo.getOrder(),userLoginSelectInfo.getSortby()));
        return root;
    }

    @ApiOperation(value = "批量修改登陆用户信息")
    @PutMapping("/modifyUserLoginBatch")
    @RequiresPermissions("40203")
    public Boolean modifyUserLoginBatch(@RequestBody UserLoginVo userLoginVo){
        return userLoginService.modifyUserLoginBatch(userLoginVo)==userLoginVo.getUserCodes().size();
    }

    @ApiOperation(value = "修改登陆用户信息")
    @PutMapping("/modifyUserLogin")
    @RequiresPermissions("40203")
    public Boolean modifyUserLogin(@RequestBody UserLoginInfo userLoginInfo){
        return userLoginService.modifyUserLogin(userLoginInfo)==1;
    }

    @ApiOperation(value = "批量删除登陆用户信息")
    @DeleteMapping("/deleteUserLoginBatchByIds")
    @RequiresPermissions("40202")
    public Boolean deleteUserLoginBatchByIds(@RequestBody List<String> userCodes){
        return userLoginService.deleteUserLoginBatchByIds(userCodes)==userCodes.size();
    }

    @ApiOperation(value = "修改密码")
    @PutMapping("/modifyUserPsw")
    @RequiresPermissions("40205")
    public Boolean modifyUserPsw(@RequestBody UserLogin userLogin){
        return userLoginService.modifyUserPsw(userLogin)==1;
    }
}

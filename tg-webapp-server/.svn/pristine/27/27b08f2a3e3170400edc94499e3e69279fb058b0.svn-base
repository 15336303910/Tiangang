package cn.plou.web.system.permission.rlMenuRole.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.baseMessage.system.entity.System;
import cn.plou.web.system.permission.rlMenuRole.entity.RlMenuRole;
import cn.plou.web.system.permission.rlMenuRole.entity.RlMenuRoleKey;
import cn.plou.web.system.permission.rlMenuRole.service.RlMenuRoleService;
import cn.plou.web.system.permission.rlMenuRole.vo.RlMenuRoleInfo;
import cn.plou.web.system.permission.rlMenuRole.vo.RlMenuRoleSelectInfo;
import cn.plou.web.system.permission.rlMenuRole.vo.RlMenuRoleVo;
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
@RequestMapping("${systemPath}/rlMenuRole")
@EnableSwagger2
@Api(description = "用户功能权限")
public class RlMenuRoleController {

    @Autowired
    RlMenuRoleService rlMenuRoleService;

//    @ApiOperation(value = "增加一个用户功能权限信息")
//    @PostMapping("/addRlMenuRole")
//    public Boolean addRlMenuRole(@RequestBody RlMenuRole rlMenuRole){
//        rlMenuRole.setId(rlMenuRoleService.getNewId());
//        return rlMenuRoleService.addRlMenuRole(rlMenuRole) == 1;
//    }

//    @ApiOperation(value = "根据id批量删除用户功能权限信息")
//    @DeleteMapping("/deleteRlMenuRoleBatchByIds")
//    public Boolean deleteRlMenuRoleBatchByIds(@RequestBody List<String> ids){
//        return rlMenuRoleService.deleteBatchByIds(ids) == ids.size();
//    }

//    @ApiOperation(value = "修改用户功能权限信息")
//    @PutMapping("/modifyRlMenuRole")
//    public Boolean modifyRlMenuRole(@RequestBody RlMenuRole rlMenuRole){
//        return rlMenuRoleService.modifyRlMenuRole(rlMenuRole) == 1;
//    }

    @ApiOperation(value="批量修改一个用户的功能权限信息")
    @PutMapping("/modifyRlMenuRoleBatch")
    @RequiresPermissions("40303")
    public Boolean modifyRlMenuRoleBatch(@RequestBody RlMenuRoleVo rlMenuRoleVo){
        return rlMenuRoleService.modifyRlMenuRoleBatch(rlMenuRoleVo) == rlMenuRoleVo.getPageNames().size();
    }


    @ApiOperation(value = "根据roleId获取对应用户所有权限")
    @GetMapping("/getRlMenuRoleByRoleId")
    @RequiresPermissions("40304")
    public Root getRlMenuRoleByRoleId(@RequestParam String roleId){
        Root root = new Root();
        root.setData(rlMenuRoleService.getRlMenuRoleByRoleId(roleId));
        return root;
    }

//    @ApiOperation(value = "获取全部用户功能权限信息")
//    @PostMapping("/getAllRlMenuRole")
//    public List<RlMenuRole> getAllRlMenuRole(){
//        return rlMenuRoleService.getAllRlMenuRole();
//    }
}

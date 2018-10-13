package cn.plou.web.system.permission.role.controller;

import cn.plou.web.common.config.common.Cond;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.PageInfo;
import cn.plou.web.system.permission.role.entity.Role;
import cn.plou.web.system.permission.role.service.RoleService;
import cn.plou.web.system.permission.role.vo.RoleInfo;
import cn.plou.web.system.permission.role.vo.RoleSelectInfo;
import cn.plou.web.system.permission.role.vo.RoleVo;
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
@RequestMapping("${systemPath}/role")
@Api(description = "角色信息")
public class RoleController {
    @Autowired
    RoleService roleService;

//    @ApiOperation(value = "根据id获取角色信息")
//    @GetMapping("/getRoleById")
//    public Root getRoleById(@RequestParam String roleId){
//        Root root = new Root();
//
//        return root;
//    }

    @ApiOperation(value = "增加一条角色信息")
    @PostMapping("/addRole")
    @RequiresPermissions("40101")
    public Boolean addRole(@RequestBody Role role){
        return roleService.addRole(role)==1;
    }

    @ApiOperation(value = "根据查询条件获取全部角色信息")
    @PostMapping ("/getAllRole")
    @RequiresPermissions("40104")
    public Root getAllRole(@RequestBody RoleSelectInfo roleSelectInfo){
        Root root = new Root();
//        PageHelper.startPage(roleSelectInfo.getPage(),roleSelectInfo.getPageSize());
        com.github.pagehelper.PageInfo<RoleInfo> pageInfo = roleService.getAllRole(roleSelectInfo.getOrder(), roleSelectInfo.getSortby(), roleSelectInfo.getSearchCondition(),
                roleSelectInfo.getPage(),roleSelectInfo.getPageSize());
//        com.github.pagehelper.PageInfo<RoleInfo> pageInfo=new com.github.pagehelper.PageInfo<>(roleInfoList);
        root.setData(pageInfo.getList());
        root.setCond(Cond.getCond(pageInfo.getPageNum(),pageInfo.getPageSize(),(int)pageInfo.getTotal(),roleSelectInfo.getOrder(),roleSelectInfo.getSortby()));
        return root;
    }

    @ApiOperation(value = "批量修改角色信息")
    @PutMapping("/modifyRoleBatch")
    @RequiresPermissions("40103")
    public Boolean modifyRoleBatch(@RequestBody RoleVo roleVo){
        return roleService.modifyRoleBatch(roleVo)==roleVo.getRoleIds().size();
    }

    @ApiOperation(value = "修改角色信息")
    @PutMapping("/modifyRole")
    @RequiresPermissions("40103")
    public Boolean modifyRole(@RequestBody Role role){
        return roleService.modifyRole(role)==1;
    }


    @ApiOperation(value = "批量删除角色信息")
    @DeleteMapping("/deleteRoleBatchByIds")
    @RequiresPermissions("40102")
    public Boolean deleteRoleBatchByIds(@RequestBody List<String> roleIds){
        return roleService.deleteRoleBatchByIds(roleIds)==roleIds.size();
    }

    @ApiOperation(value = "获取角色下拉框")
    @PostMapping ("/getRoleDownInfo")
    public Root getRoleDownInfo(){
        Root root = new Root();
        root.setData(roleService.getRoleDownInfo());
        return root;
    }
}

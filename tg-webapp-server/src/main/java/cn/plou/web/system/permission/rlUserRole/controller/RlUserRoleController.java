package cn.plou.web.system.permission.rlUserRole.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.system.permission.rlUserRole.service.RlUserRoleService;
import cn.plou.web.system.permission.rlUserRole.vo.RlUserRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@RequestMapping("${systemPath}/rlRoleData")
@EnableSwagger2
@Api(description = "用户角色")
public class RlUserRoleController  {

    @Autowired
    RlUserRoleService rlUserRoleService;

    @ApiOperation(value="批量修改用户角色")
    @PutMapping("/modifyRlUserRoleBatch")
    public Boolean modifyRlUserRoleBatch(@RequestBody RlUserRoleVo rlUserRoleVo){
        return rlUserRoleService.modifyRlUserRoleBatch(rlUserRoleVo) == rlUserRoleVo.getUserCodes().size();
    }
}

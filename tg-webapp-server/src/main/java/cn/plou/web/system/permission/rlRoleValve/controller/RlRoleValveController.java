package cn.plou.web.system.permission.rlRoleValve.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Root;


import cn.plou.web.system.permission.rlRoleValve.entity.RlRoleValve;
import cn.plou.web.system.permission.rlRoleValve.service.RlRoleValveService;
import cn.plou.web.system.permission.rlRoleValve.vo.RlRoleValveInfo;
import cn.plou.web.system.permission.rlRoleValve.vo.RlRoleValveSelectInfo;
import cn.plou.web.system.permission.rlRoleValve.vo.RlRoleValveVo;
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
@RequestMapping("${systemPath}/rlRoleValve")
@EnableSwagger2
@Api(description = "用户指令权限信息")
public class RlRoleValveController  {

    @Autowired
    private RlRoleValveService rlRoleValveService;

//    @ApiOperation(value = "添加一个用户指令权限信息")
//    @PostMapping("/addRlRoleValve")
//    public Boolean addRlRoleValve(@RequestBody RlRoleValve rlRoleValve){
//       rlRoleValve.setId(rlRoleValveService.getNewId());
//        return  rlRoleValveService.addRlRoleValve(rlRoleValve) == 1;
//    }

//    @ApiOperation(value = "根据id批量删除地图点信息")
//    @DeleteMapping("/deleteRlRoleValveBatchByIds")
//    public Root deleteRlRoleValveBatchByIds(@RequestBody List<String> ids){
//        Root root = new Root();
//        rlRoleValveService.deleteBatchByIds(ids);
//        return root;
//    }

    @ApiOperation(value="修改用户控制指令权限信息")
    @PutMapping("/modifyRlRoleValve")
    @RequiresPermissions("40503")
    public Boolean modifyrlRoleValve(@RequestBody RlRoleValveVo rlRoleValveVo){
        return rlRoleValveService.modifyRlRoleValve(rlRoleValveVo) == rlRoleValveVo.getRoleValveList().size();
    }


//    @ApiOperation(value="批量修改用户控制指令权限信息")
//    @PutMapping("/modifyRlRoleValveBatch")
//    public Boolean modifyrlRoleValveBatch(@RequestBody RlRoleValveVo rlRoleValveVo){
//        return rlRoleValveService.modifyRlRoleValveBatch(rlRoleValveVo) == rlRoleValveVo.getIds().size() ;
//    }

    @ApiOperation(value = "根据roleId获取对应用户控制指令权限信息")
    @GetMapping("/getRlRoleValveById")
    @RequiresPermissions("40504")
    public Root getRlRoleValveByroleId(@RequestParam("roleId") String roleId){
        Root root = new Root();
        root.setData(rlRoleValveService.getRlRoleValveByRoleId(roleId));
        return root;
    }

//    @ApiOperation(value = "根据查询条件获取全部用户控制指令权限信息")
//    @PostMapping("/getAllRlRoleValve")
//    public Root getAllRlRoleValve( @RequestBody RlRoleValveSelectInfo rlRoleValveSelectInfo){
//        Root root = new Root();
//        List<RlRoleValve> rlRoleValveList = rlRoleValveService.getAllRlRoleValve(rlRoleValveSelectInfo.getSearchCondition(), rlRoleValveSelectInfo.getOrder(), rlRoleValveSelectInfo.getSortby());
//        root.setData(rlRoleValveList);
//        return root;
//    }
}

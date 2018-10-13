package cn.plou.web.system.permission.rlRoleData.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.permission.rlRoleData.entity.RlRoleData;
import cn.plou.web.system.permission.rlRoleData.service.RlRoleDataService;
import cn.plou.web.system.permission.rlRoleData.vo.RlRoleDataInfo;
import cn.plou.web.system.permission.rlRoleData.vo.RlRoleDataSelectInfo;
import cn.plou.web.system.permission.rlRoleData.vo.RlRoleDataVo;
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
@RequestMapping("${systemPath}/rlRoleData")
@EnableSwagger2
@Api(description = "用户数据权限")
public class RlRoleDataController {

    @Autowired
    RlRoleDataService rlRoleDataService;

//    @ApiOperation(value = "增加一个用户数据权限信息")
//    @PostMapping("/addRlRoleData")
//    public Boolean addRlRoleData(@RequestBody RlRoleData rlRoleData){
//        rlRoleData.setId(rlRoleDataService.getNewId());
//        return rlRoleDataService.addRlRoleData(rlRoleData)==1;
//    }

//    @ApiOperation(value = "根据id批量删除用户数据权限信息")
//    @DeleteMapping("/deleteRlRoleDataBatchByIds")
//    public Root deleteRlRoleDataBatchByIds(@RequestBody List<String> ids){
//        Root root = new Root();
////        rlRoleDataService.deleteBatchByIds(ids);
//        return root;
//    }

    @ApiOperation(value="批量修改用户数据权限信息")
    @PutMapping("/modifyRlRoleDataBatch")
    @RequiresPermissions("40403")
    public Boolean modifyRlRoleDataBatch(@RequestBody RlRoleDataVo rlRoleDataVo){
        return rlRoleDataService.modifyRlRoleDataBatch(rlRoleDataVo) == rlRoleDataVo.getRoleDataList().size();
    }

    @ApiOperation(value = "根据roleId获取对应用户数据权限信息")
    @GetMapping("/getRlRoleDataById")
    @RequiresPermissions("40404")
    public Root getRlRoleDataById(@RequestParam("roleId") String roleId){
        Root root = new Root();
        root.setData(rlRoleDataService.getRlRoleDataByRoleId(roleId));
        return root;
    }

//    @ApiOperation(value = "根据查询条件获取全部用户数据权限信息")
//    @PostMapping("/getAllRlRoleData")
//    public Root getAllRlRoleData(@RequestBody RlRoleDataSelectInfo rlRoleDataSelectInfo){
//        Root root = new Root();
//        List<RlRoleDataInfo> rlRoleDataInfoList = rlRoleDataService.getAllRlRoleData(rlRoleDataSelectInfo.getSearchCondition(),
//                rlRoleDataSelectInfo.getOrder(),rlRoleDataSelectInfo.getSortby());
//        root.setData(getPageInfo(rlRoleDataInfoList,rlRoleDataSelectInfo.getPage(),rlRoleDataSelectInfo.getPageSize()));
//        root.setCond(getCond(rlRoleDataSelectInfo.getPage(),rlRoleDataSelectInfo.getPageSize(),rlRoleDataInfoList.size(),
//                rlRoleDataSelectInfo.getOrder(),rlRoleDataSelectInfo.getSortby()));
//        return root;
//    }
}

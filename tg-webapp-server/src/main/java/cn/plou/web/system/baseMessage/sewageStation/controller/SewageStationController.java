package cn.plou.web.system.baseMessage.sewageStation.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Cond;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.PageInfo;
import cn.plou.web.system.baseMessage.sewageStation.entity.SewageStation;
import cn.plou.web.system.baseMessage.sewageStation.service.SewageStationService;
import cn.plou.web.system.baseMessage.sewageStation.vo.SewageStationInfo;
import cn.plou.web.system.baseMessage.sewageStation.vo.SewageStationSelectInfo;
import cn.plou.web.system.baseMessage.sewageStation.vo.SewageStationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@EnableSwagger2
@RequestMapping("${systemPath}/sewageStation")
@Api(description = "污水站信息")
public class SewageStationController  {
    @Autowired
    SewageStationService sewageStationService;

    @ApiOperation(value = "根据id获取污水站信息")
    @GetMapping("/getSewageStationById")
    @RequiresPermissions("10504")
    public Root getSewageStationById(@RequestParam String sewageStationId){
        Root root = new Root();
        root.setData(sewageStationService.getSewageStationById(sewageStationId));
        return root;
    }

    @ApiOperation(value = "增加一条污水站信息")
    @PostMapping("/addSewageStation")
    @RequiresPermissions("10501")
    public Boolean addSewageStation(@RequestBody SewageStation sewageStation){
        return sewageStationService.addSewageStation(sewageStation)==1;
    }

    @ApiOperation(value = "根据查询条件获取全部污水站信息")
    @PostMapping ("/getAllSewageStation")
    @RequiresPermissions("10501")
    public Root getAllSewageStation(@RequestBody SewageStationSelectInfo sewageStationSelectInfo){
        Root root = new Root();
        List<SewageStationInfo> sewageStationList=sewageStationService.getAllSewageStation(sewageStationSelectInfo.getCompanyId(),
                sewageStationSelectInfo.getSearchCondition(),sewageStationSelectInfo.getOrder(),sewageStationSelectInfo.getSortby());
        root.setData(PageInfo.getPageInfo(sewageStationList,sewageStationSelectInfo.getPage(),sewageStationSelectInfo.getPageSize()));
        root.setCond(Cond.getCond(sewageStationSelectInfo.getPage(),sewageStationSelectInfo.getPageSize(),sewageStationList.size(),
                sewageStationSelectInfo.getOrder(),sewageStationSelectInfo.getSortby()));
        return root;
    }

    @ApiOperation(value = "批量修改污水站信息")
    @PutMapping("/modifySewageStationBatch")
    @RequiresPermissions("10503")
    public Boolean modifySewageStationBatch(@RequestBody SewageStationVo sewageStationVo){
        return sewageStationService.modifySewageStationBatch(sewageStationVo)==sewageStationVo.getSewageStationIds().size();
    }

    @ApiOperation(value = "修改污水站信息")
    @PutMapping("/modifySewageStation")
    @RequiresPermissions("10503")
    public Boolean modifySewageStation(@RequestBody SewageStation sewageStation){
        return sewageStationService.modifySewageStation(sewageStation)==1;
    }

    @ApiOperation(value = "批量删除污水站信息")
    @DeleteMapping("/deleteSewageStationBatchByIds")
    @RequiresPermissions("10502")
    public Boolean deleteSewageStationBatchByIds(@RequestBody List<String> sewageStationIds){
        return sewageStationService.deleteSewageStationBatchByIds(sewageStationIds)==sewageStationIds.size();
    }

   /* @ApiOperation(value = "下拉框")
    @GetMapping("/getSewageStationDownInfo")
    public Root getSewageStationDownInfo(@RequestParam String companyId){
        Root root = new Root();

        return root;
    }*/
}

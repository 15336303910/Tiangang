package cn.plou.web.system.baseMessage.unit.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.common.utils.RedisUtil;
import cn.plou.web.common.utils.SerializeUtil;
import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import cn.plou.web.system.baseMessage.unit.entity.Unit;
import cn.plou.web.system.baseMessage.unit.service.UnitService;
import cn.plou.web.system.baseMessage.unit.vo.UnitInfo;
import cn.plou.web.system.baseMessage.unit.vo.UnitListInfo;
import cn.plou.web.system.baseMessage.unit.vo.UnitSelectInfo;
import cn.plou.web.system.baseMessage.unit.vo.UnitVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static cn.plou.web.common.config.common.Cond.getCond;
import static cn.plou.web.common.utils.PageInfo.getPageInfo;

@RestController
@EnableSwagger2
@RequestMapping("${systemPath}/unit")
@Api(description = "单元管理")
public class UnitController {
    @Autowired
    private UnitService unitService;
    @ApiOperation(value = "获取全部单元信息")
    @PostMapping("/getAllUnit")
    @RequiresPermissions("10804")
    public Root getAllUnit(@RequestBody UnitSelectInfo unitSelectInfo){
        Root root = new Root();
        if(unitSelectInfo.getSortby()!=null){
            if(unitSelectInfo.getSortby().equals("controlTypeName")){
                unitSelectInfo.setSortby("controlType");
            }else if(unitSelectInfo.getSortby().equals("heatingFormName")){
                unitSelectInfo.setSortby("heatingForm");
            }else if(unitSelectInfo.getSortby().equals("hasBuildMeterName")){
                unitSelectInfo.setSortby("hasBuildMeter");
            }else if(unitSelectInfo.getSortby().equals("hasBalanceValveName")){
                unitSelectInfo.setSortby("hasBalanceValve");
            }else if(unitSelectInfo.getSortby().equals("waterControlTypeName")){
                unitSelectInfo.setSortby("waterControlType");
            }else if(unitSelectInfo.getSortby().equals("waterHasBuildMeterName")){
                unitSelectInfo.setSortby("waterHasBuildMeter");
            }else if(unitSelectInfo.getSortby().equals("heatingFormName")){
                unitSelectInfo.setSortby("heatingForm");
            }else if(unitSelectInfo.getSortby().equals("diameterName")){
                unitSelectInfo.setSortby("diameter");
            }
        }
       // PageHelper.startPage(unitSelectInfo.getPage(),unitSelectInfo.getPageSize());
        UnitListInfo unitListInfo = unitService.getAllUnit(unitSelectInfo.getPage(),unitSelectInfo.getPageSize(),unitSelectInfo.getOrder(),unitSelectInfo.getSortby(),unitSelectInfo.getCompanyId(),
                unitSelectInfo.getStationId(),unitSelectInfo.getCommuityId(),unitSelectInfo.getBuildingNo(),unitSelectInfo.getUnitId(), unitSelectInfo.getSearchCondition());
       // PageInfo pageInfo = new PageInfo(unitList);
//        root.setCond(getCond(pageInfo.getPageNum(),pageInfo.getPageSize(),(int)pageInfo.getTotal(),unitSelectInfo.getOrder(),unitSelectInfo.getSortby()));
        root.setCond(getCond(unitSelectInfo.getPage(),unitSelectInfo.getPageSize(),unitListInfo.getCount(),unitSelectInfo.getOrder(),unitSelectInfo.getSortby()));
        root.setData(unitListInfo.getUnitInfoList());
        return root;
    }
    @ApiOperation(value = "根据id获取对应单元信息")
    @GetMapping("/getUnitById")
    @RequiresPermissions("10804")
    public Root getUnitById(@RequestParam String unitId){
        Root root = new Root();
        root.setData(unitService.getUnitById(unitId));
        return root;
    }
    @ApiOperation(value = "根据id批量删除单元信息")
    @DeleteMapping("/deleteBatch")
    @RequiresPermissions("10802")
    public Boolean dropUnitBatchByIds(@RequestBody List<String> unitIds){
        return unitService.deleteBatchByIds(unitIds)==unitIds.size();
    }

    @ApiOperation(value = "添加一个单元信息")
    @PostMapping("/addUnit")
    @RequiresPermissions("10801")
    public Boolean addUnit(@RequestBody Unit unit){
        return unitService.addUnit(unit)==1;
    }

    @ApiOperation(value = "批量修改单元信息")
    @PutMapping("/modifyUnitBatch")
    @RequiresPermissions("10803")
    public Boolean modifyUnitBatch(@RequestBody UnitVo unitVo){
        if(unitVo.getBuildingId()!=null){
            unitVo.setCompanyId(unitVo.getBuildingId().substring(0,5));
        }
        return unitService.modifyBatch(unitVo)==unitVo.getUnitIds().size();
    }

    @ApiOperation(value = "修改一条单元信息")
    @PutMapping("/modifyUnit")
    @RequiresPermissions("10803")
    public Boolean modifyUnit(@RequestBody Unit unit){
        if(unit.getBuildingId()!=null){
            unit.setCompanyId(unit.getBuildingId().substring(0,5));
        }
        return unitService.modifyUnit(unit)==1;
    }

    @ApiOperation("根据建筑物id获取全部单元名")
    @GetMapping("/getUnitTree")
    // @RequiresPermissions("10804")
    public List<Unit> getUnitNameByBuildId(@RequestParam String buildingNo){
        return unitService.getUnitTree(buildingNo);
    }

    @ApiOperation("根据excel表格批量获取单元信息")
    @PostMapping(value="/importExcel")
    public ImportResult importUnitExcel(@RequestParam("file") MultipartFile file, ServletRequest request) {
    	 
        ImportResult importResult =  unitService.importExcel(file,request);
        return importResult;
    }

    @ApiOperation("批量导出单元信息")
    @PostMapping("/exportExcel")
    public Root exportExcel(ServletRequest request,@RequestBody UnitSelectInfo unitSelectInfo) {
        Root root = new Root();
        root.setCode(0);
        root.setMsg("导出成功");
        root.setData(unitService.exportExcel(request,unitSelectInfo));
        return root;
    }
}

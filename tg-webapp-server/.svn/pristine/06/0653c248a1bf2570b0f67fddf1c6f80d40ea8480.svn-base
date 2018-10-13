package cn.plou.web.system.baseMessage.station.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.PageInfo;
import cn.plou.web.system.baseMessage.station.entity.Station;
import cn.plou.web.system.baseMessage.station.entity.StationKey;
import cn.plou.web.system.baseMessage.station.service.StationService;
import cn.plou.web.system.baseMessage.station.vo.StationInfo;
import cn.plou.web.system.baseMessage.station.vo.StationSelectInfo;
import cn.plou.web.system.baseMessage.station.vo.StationVo;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

import static cn.plou.web.common.config.common.Cond.getCond;

@RestController
@EnableSwagger2
@RequestMapping("${systemPath}/station")
@Api(description = "站信息")
public class StationController  {
    @Autowired
    private StationService stationService;

    @ApiOperation(value = "根据id获取站信息")
    @GetMapping("/getStationById")
    @RequiresPermissions("10304")
    public Root getStationById(@RequestParam String stationId){
        Root root = new Root();
        root.setData(stationService.getStationById(stationId));
        return root;
    }

    @ApiOperation(value = "增加一条站信息")
    @PostMapping("/addStation")
    @RequiresPermissions("10301")
    public Boolean addStation(@RequestBody Station station){

       return stationService.addStation(station)==1;
    }

    @ApiOperation(value = "根据查询条件获取全部站信息")
    @PostMapping ("/getAllStation")
    @RequiresPermissions("10304")
    public Root getAllStation(@RequestBody StationSelectInfo stationSelectInfo){
        Root root = new Root();
//        PageHelper.startPage(stationSelectInfo.getPage(), stationSelectInfo.getPageSize());
        com.github.pagehelper.PageInfo<StationInfo> pageInfo = stationService.getAllStation(stationSelectInfo.getCompanyId(), stationSelectInfo.getProducerId(),
                stationSelectInfo.getSearchCondition(), stationSelectInfo.getOrder(), stationSelectInfo.getSortby(), stationSelectInfo.getPage(), stationSelectInfo.getPageSize());
//        com.github.pagehelper.PageInfo<StationInfo> pageInfo = new com.github.pagehelper.PageInfo<>(stationList);
        root.setData(pageInfo.getList());
        root.setCond(getCond(stationSelectInfo.getPage(), stationSelectInfo.getPageSize(), (int) pageInfo.getTotal(), stationSelectInfo.getOrder(), stationSelectInfo.getSortby()));
        return root;
    }

    @ApiOperation(value = "批量修改站信息")
    @PutMapping("/modifyStationBatch")
    @RequiresPermissions("10303")
    public Boolean modifyStationBatch(@RequestBody StationVo stationVo){
        return stationService.modifyStationBatch(stationVo)==stationVo.getStationIds().size();
    }

    @ApiOperation(value = "修改站信息")
    @PutMapping("/modifyStation")
    @RequiresPermissions("10303")
    public Boolean modifyStation(@RequestBody Station station){
        return stationService.modifyStation(station)==1;
    }

    @ApiOperation(value = "批量删除站信息")
    @DeleteMapping("/deleteStationBatchByIds")
    @RequiresPermissions("10302")
    public Boolean deleteStationBatchByIds(@RequestBody List<String> stationIds){
        return stationService.deleteStationBatchByIds(stationIds)==stationIds.size();
    }

    @ApiOperation(value = "下拉框")
    @GetMapping("/getStationDownInfo")
    // @RequiresPermissions("10304")
    public Root getStationDownInfo(@RequestParam String companyId){
        Root root = new Root();
        root.setData(stationService.getStationDownInfo(companyId));
        return root;
    }
    @ApiOperation(value = "根据树获取全部站的id")
    @GetMapping("/getStationByCompanyTree")
    //@RequiresPermissions("10304")
    public List<String> getStationByCompanyTree(@RequestParam(required = false) String companyId,
                                                @RequestParam(required = false) String stationId ){
        return stationService.getStationIdList(companyId,stationId);
    }



    @ApiOperation(value = "根据树获取全部站的id")
    @GetMapping("/getCompanyIdByStationId")
    public Object  getCompanyIdByStationId(@RequestParam(required = false) String stationId ){
        Map <String,String> param= new HashMap();
        param.put("companyId" ,stationService.getStationById(stationId).getCompanyId());
        return   param;

    }
}

package cn.plou.web.system.commonMessage.mapPoint.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.commonMessage.mapPoint.entity.MapPoint;
import cn.plou.web.system.commonMessage.mapPoint.service.MapPointService;
import cn.plou.web.system.commonMessage.mapPoint.vo.MapPointInfo;
import cn.plou.web.system.commonMessage.mapPoint.vo.MapPointListInfo;
import cn.plou.web.system.commonMessage.mapPoint.vo.MapPointSelectInfo;
import cn.plou.web.system.commonMessage.mapPoint.vo.MapPointVo;
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
@RequestMapping("${systemPath}/mapPoint")
@EnableSwagger2
@Api(description = "地图点信息")
public class MapPointController  {

    @Autowired
    MapPointService mapPointService;

    @ApiOperation(value = "增加一条地图点信息")
    @PostMapping("/addMapPoint")
    @RequiresPermissions("30801")
    public Boolean addMapPoint(@RequestBody MapPoint mapPoint){
        mapPoint.setId(mapPointService.getNewId());
        return mapPointService.addMapPoint(mapPoint) == 1;
    }

    @ApiOperation(value = "根据id批量删除地图点信息")
    @DeleteMapping("/deleteMapPointBatchByIds")
    @RequiresPermissions("30802")
    public Boolean deleteMapPointBatchByIds(@RequestBody List<String> ids){
        return mapPointService.deleteBatchByIds(ids) == ids.size();
    }

    @ApiOperation(value="修改地图点信息")
    @PutMapping("/modifyMapPoint")
    @RequiresPermissions("30803")
    public Boolean modifyMapPoint(@RequestBody MapPoint mapPoint){
        return mapPointService.modifyMapPoint(mapPoint) == 1;
    }

    @ApiOperation(value="批量修改地图点信息")
    @PutMapping("/modifyMapPointBatch")
    @RequiresPermissions("30803")
    public Boolean modifyMapPointBatch(@RequestBody MapPointVo mapPointVo){
        return  mapPointService.modifyMapPointBatch(mapPointVo) == mapPointVo.getIds().size();
    }

    @ApiOperation(value = "根据id获取对应地图点信息")
    @GetMapping("/getMapPointById")
    @RequiresPermissions("30804")
    public Root getMapPointById(@RequestParam("id") String id){
        Root root = new Root();
        root.setData(mapPointService.getMapPointById(id));
        return root;
    }

    @ApiOperation(value = "根据查询条件获取全部地图点信息")
    @PostMapping("/getAllMapPoint")
    @RequiresPermissions("30804")
    public Root getAllMapPoint(@RequestBody MapPointSelectInfo mapPointSelectInfo){
        Root root = new Root();
        //PageHelper.startPage(mapPointSelectInfo.getPage(),mapPointSelectInfo.getPageSize());
        MapPointListInfo mapPointListInfo = mapPointService.getAllMapPoint(mapPointSelectInfo.getCompanyId(),
                mapPointSelectInfo.getStationId(), mapPointSelectInfo.getCommuityId(), mapPointSelectInfo.getSearchCondition(),
                mapPointSelectInfo.getOrder(), mapPointSelectInfo.getSortby(),null,null);
        root.setData(mapPointListInfo.getMapPointInfoList());
        //PageInfo pageInfo = new PageInfo(mapPointInfoList);
        root.setCond(getCond(mapPointSelectInfo.getPage(),mapPointSelectInfo.getPageSize(),mapPointListInfo.getCount(),
                mapPointSelectInfo.getOrder(),mapPointSelectInfo.getSortby()));
        return root;
    }
}

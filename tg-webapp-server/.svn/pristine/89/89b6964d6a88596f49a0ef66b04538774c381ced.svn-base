package cn.plou.web.system.commonMessage.mapLine.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.commonMessage.mapLine.service.MapLineService;
import cn.plou.web.system.commonMessage.mapLine.entity.MapLine;
import cn.plou.web.system.commonMessage.mapLine.vo.MapLineInfo;
import cn.plou.web.system.commonMessage.mapLine.vo.MapLineListInfo;
import cn.plou.web.system.commonMessage.mapLine.vo.MapLineSelelctInfo;
import cn.plou.web.system.commonMessage.mapLine.vo.MapLineVo;
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
@RequestMapping("${systemPath}/mapLine")
@EnableSwagger2
@Api(description = "地图线信息")
public class MapLineController  {

    @Autowired
    private MapLineService mapLineService;

    @ApiOperation(value = "增加一条地图线信息")
    @PostMapping("/addMapLine")
    @RequiresPermissions("30701")
    public Boolean addMapLine(@RequestBody MapLine mapLine){
        mapLine.setId(mapLineService.getNewId());
        return mapLineService.addMapLine(mapLine) == 1;
    }

    @ApiOperation(value = "根据id批量删除地图线信息")
    @DeleteMapping("/deleteMapLineBatchByIds")
    @RequiresPermissions("30702")
    public Boolean deleteMapLineBatchByIds(@RequestBody List<String> ids){
        return mapLineService.deleteBatchByIds(ids)==ids.size();
    }

    @ApiOperation(value="修改地图线信息")
    @PutMapping("/modifyMapLine")
    @RequiresPermissions("30703")
    public Boolean modifyMapLine(@RequestBody MapLine mapLine){
        return mapLineService.modifyMapLine(mapLine) == 1;
    }

    @ApiOperation(value="批量修改地图线信息")
    @PutMapping("/modifyMapLineBatch")
    @RequiresPermissions("30703")
    public Boolean modifyMapLineBatch(@RequestBody MapLineVo mapLineVo){
        return mapLineService.modifyMapLineBatch(mapLineVo) == mapLineVo.getIds().size();
    }

    @ApiOperation(value = "根据id获取对应地图线信息")
    @GetMapping("/getMapLineById")
    @RequiresPermissions("30704")
    public Root getMapLineById(@RequestParam("id") String id){
        Root root = new Root();
        root.setData(mapLineService.getMapLineById(id));
        return root;
    }

    @ApiOperation(value = "根据查询条件获取全部地图线信息")
    @PostMapping("/getAllMapLine")
    @RequiresPermissions("30704")
    public Root getAllMapLine(@RequestBody MapLineSelelctInfo mapLineSelelctInfo){
        Root root = new Root();
       // PageHelper.startPage(mapLineSelelctInfo.getPage(),mapLineSelelctInfo.getPageSize());
        MapLineListInfo mapLineListInfo = mapLineService.getAllMapLine(mapLineSelelctInfo.getCompanyId(),
                mapLineSelelctInfo.getCommuityId(),mapLineSelelctInfo.getStationId(),mapLineSelelctInfo.getSearchCondition(),
                mapLineSelelctInfo.getOrder(),mapLineSelelctInfo.getSortby(),null,null);
        root.setData(mapLineListInfo.getMapLineInfoList());
        //PageInfo pageInfo = new PageInfo(mapLineInfoList);
        root.setCond(getCond(mapLineSelelctInfo.getPage(),mapLineSelelctInfo.getPageSize(),mapLineListInfo.getCount(),
                mapLineSelelctInfo.getOrder(),mapLineSelelctInfo.getSortby()));
        return root;
    }

    @ApiOperation(value = "查询公司的地图信息")
    @GetMapping("/getMapInfo")
    public Root getMapInfo(@RequestParam(required = false) String companyId ,@RequestParam (required = false)String stationId,@RequestParam (required = false)String commuityId) {
        Root root = new Root();
        root.setData(mapLineService.getMapInfo(companyId,stationId,commuityId));
        return root;
    }
}

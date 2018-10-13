package cn.plou.web.balance.distribution.controller;

import cn.plou.web.balance.distribution.entity.BuildRunningData;
import cn.plou.web.balance.distribution.service.DistributionService;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.baseMessage.build.service.BuildService;
import cn.plou.web.system.baseMessage.build.vo.BuildInfo;
import cn.plou.web.system.commonMessage.mapLine.service.MapLineService;
import cn.plou.web.system.commonMessage.mapLine.vo.MapLineListInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@EnableSwagger2
//@RequestMapping("${balancePath}/distribution")
@RequestMapping("balance/distribution")
@Api(description = "楼宇平衡")
public class DistributionController {
    @Autowired
    private MapLineService mapLineService;
    @Autowired
    private BuildService buildService;
    @Autowired
    private DistributionService distributionService;

    @ApiOperation(value = "获取站下平衡阀平均值")
    @GetMapping("/getStationAvg")
    //@RequiresPermissions("")
    public Root getStationAvg(@RequestParam String stationId){
        Root root = new Root();
        return root;
    }

    @ApiOperation(value = "根据id获取建筑物平衡阀运行值")
    @GetMapping("/getDataNowByBuildingId")
    //@RequiresPermissions("")
    public BuildRunningData getDataNowByBuildingId(@RequestParam String buildingId){
        return distributionService.getBuildRunningDataByBuildId(buildingId);
    }

    @ApiOperation(value="获取地图线的列表")
    @GetMapping("/getMapLineList")
    public MapLineListInfo getMapLineList(@RequestParam("stationId") String stationId){
        return mapLineService.getAllMapLine(null,null,stationId,null,null,null,null,null);
    }

    @ApiOperation(value="根据站id获取建筑物列表")
    @GetMapping("getBuildListByStationId")
    public List<BuildInfo> getBuildListByStationId(@RequestParam("stationId")String stationId){
        return distributionService.getBuildListByStationId(stationId);
    }
}

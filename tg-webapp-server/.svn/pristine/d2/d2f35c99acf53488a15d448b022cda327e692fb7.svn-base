package cn.plou.web.system.baseMessage.zoneArea.controller;

import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.baseMessage.zoneArea.service.ZoneAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@EnableSwagger2
@RequestMapping("${systemPath}/zoneArea")
@Api(description = "区")
public class ZoneAreaController {
    @Autowired
    ZoneAreaService zoneAreaService;

    @ApiOperation(value = "根据市Id查询区信息")
    @GetMapping("/getZoneAreaByZACityid")
    public Root getZoneAreaByZACityid(@RequestParam String zACityid){
        Root root=new Root();
        root.setData(zoneAreaService.getZoneAreaByZACityid(zACityid));
        return root;
    }


    @ApiOperation(value = "省市区三级联动")
    @GetMapping("/getCityStructureInfoById")
    public Root getCityStructureInfoById(@RequestParam String idName, @RequestParam String id){
        Root root=new Root();
        root.setData(zoneAreaService.getCityStructureInfoById(idName,id));
        return root;
    }
}

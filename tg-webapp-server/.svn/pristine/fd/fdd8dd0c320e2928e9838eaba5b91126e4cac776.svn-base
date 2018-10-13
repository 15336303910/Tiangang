package cn.plou.web.system.baseMessage.zoneProvince.controller;

import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.baseMessage.zoneProvince.entity.ZoneProvince;
import cn.plou.web.system.baseMessage.zoneProvince.service.ZoneProvinceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@EnableSwagger2
@RequestMapping("${systemPath}/zoneProvince")
@Api(description = "省")
public class ZoneProvinceController {
    @Autowired
    ZoneProvinceService zoneProvinceService;


    @ApiOperation(value = "查询所有省信息")
    @PostMapping("/getAllZoneProvince")
    public Root getAllZoneProvince(){
        Root root=new Root();
        root.setData(zoneProvinceService.getAllZoneProvince());
        return root;
    }

    @ApiOperation(value = "根据区Id获取市信息")
    @GetMapping("/getZoneProvinceByZCCityid")
    public Root getZoneProvinceByZCCityid(@RequestParam String zCCityid){
        Root root=new Root();
        root.setData(zoneProvinceService.getZoneProvinceByZCCityid(zCCityid));
        return root;
    }
}

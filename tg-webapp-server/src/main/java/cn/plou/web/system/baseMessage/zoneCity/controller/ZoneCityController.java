package cn.plou.web.system.baseMessage.zoneCity.controller;

import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.baseMessage.zoneCity.service.ZoneCityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@EnableSwagger2
@RequestMapping("${systemPath}/zoneCity")
@Api(description = "市")
public class                                                                ZoneCityController {
    @Autowired
    ZoneCityService zoneCityService;


    @ApiOperation(value = "根据省Id查询市信息")
    @GetMapping("/getZoneCityByZCProvinceid")
    public Root getZoneCityByZCProvinceid(@RequestParam String zCProvinceid){
        Root root=new Root();
        root.setData(zoneCityService.getZoneCityByZCProvinceid(zCProvinceid));
        return root;
    }

    @ApiOperation(value = "根据区Id获取市信息")
    @GetMapping("/getZoneCityByZAAreaid")
    public Root getZoneCityByZAAreaid(@RequestParam String zAAreaid){
        Root root=new Root();
        root.setData(zoneCityService.getZoneCityByZAAreaid(zAAreaid));
        return root;
    }
}

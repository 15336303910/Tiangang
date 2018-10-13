package cn.plou.web.system.commonMessage.weather.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.commonMessage.weather.service.WeatherFurService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@RequestMapping("${systemPath}/weatherFur")
@EnableSwagger2
@Api(description = "天气预测")
public class WeatherFurController  {
    @Autowired
    WeatherFurService weatherFurService;

    @ApiOperation(value = "获取未来三天天气城市信息")
    @GetMapping("/getWeatherFurByCity")
    public Root getWeatherFurByCity(@RequestParam String city){
        Root root = new Root();
        root.setData(weatherFurService.getWeatherFurByCity(city));
        return root;
    }
}

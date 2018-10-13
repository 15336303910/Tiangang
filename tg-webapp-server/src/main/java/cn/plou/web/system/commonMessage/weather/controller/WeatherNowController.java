package cn.plou.web.system.commonMessage.weather.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.system.commonMessage.weather.entity.WeatherNow;
import cn.plou.web.system.commonMessage.weather.service.WeatherNowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;

@RestController
@RequestMapping("${systemPath}/weatherNow")
@EnableSwagger2
@Api(description = "实时天气")
public class WeatherNowController  {
    @Autowired
    private WeatherNowService weatherNowService;
    @ApiOperation("获取当天实时天气")
    @GetMapping("/getWeatherNow")
    public WeatherNow getWeatherNow(@RequestParam String city){
        Date readTime = new Date();
        return weatherNowService.getWeatherNow(city);
    }
}

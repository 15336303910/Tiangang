package cn.plou.web.system.commonMessage.weather.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.commonMessage.weather.entity.Weather;
import cn.plou.web.system.commonMessage.weather.service.WeathrtService;
import cn.plou.web.system.commonMessage.weather.vo.WeatherInfo;
import cn.plou.web.system.commonMessage.weather.vo.WeatherListInfo;
import cn.plou.web.system.commonMessage.weather.vo.WeatherSelectInfo;
import cn.plou.web.system.commonMessage.weather.vo.WeatherVo;
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
@RequestMapping("${systemPath}/weather")
@EnableSwagger2
@Api(description = "采集天气城市")
public class WeatherController  {

    @Autowired
    WeathrtService weatherService;

    @ApiOperation(value = "添加一个采集天气城市信息")
    @PostMapping("/addWeather")
    @RequiresPermissions("30901")
    public Boolean addWeather(@RequestBody Weather weather){
        return weatherService.addWeather(weather) == 1;
    }

    @ApiOperation(value = "根据id批量删除采集天气城市信息")
    @DeleteMapping("/deleteWeatherBatchByIds")
    @RequiresPermissions("30902")
    public Boolean deleteWeatherBatchByIds(@RequestBody List<String> ids){
        return weatherService.deleteBatchByIds(ids) == ids.size();
    }

    @ApiOperation(value = "修改采集天气城市信息")
    @PutMapping("/modifyWeather")
    @RequiresPermissions("30903")
    public Boolean getWeatherById(@RequestBody Weather weather){
        return weatherService.modifyWeather(weather) == 1;
    }

    @ApiOperation(value="批量修改采集天气城市信息")
    @PutMapping("/modifyWeatherBatch")
    @RequiresPermissions("30903")
    public Boolean modifyWeatherBatch(@RequestBody WeatherVo weatherVo){
        return  weatherService.modifyWeatherBatch(weatherVo) == weatherVo.getWeatherIds().size();
}

    @ApiOperation(value = "根据查询条件获取全部采集天气城市信息")
    @PostMapping("/getAllWeather")
    @RequiresPermissions("30904")
    public Root getAllWeather(@RequestBody WeatherSelectInfo weatherSelectInfo){
        Root root = new Root();
        WeatherListInfo weatherListInfo = weatherService.getAllWeather(weatherSelectInfo.getSearchCondition(),weatherSelectInfo.getOrder(),
                weatherSelectInfo.getSortby(),weatherSelectInfo.getzPProvinceid(),weatherSelectInfo.getzCCityid(),weatherSelectInfo.getzAAreaid(),weatherSelectInfo.getPage(),weatherSelectInfo.getPageSize());
        root.setData(weatherListInfo.getWeatherInfoList());
        root.setCond(getCond(weatherSelectInfo.getPage(),weatherSelectInfo.getPageSize(),weatherListInfo.getCount(),weatherSelectInfo.getOrder(),weatherSelectInfo.getSortby()));
        return root;
    }
}

package cn.plou.web.system.commonMessage.weather.service.impl;

import cn.plou.web.system.baseMessage.zoneCity.dao.ZoneCityMapper;
import cn.plou.web.system.baseMessage.zoneCity.entity.ZoneCity;
import cn.plou.web.system.commonMessage.weather.dao.WeatherFurMapper;
import cn.plou.web.system.commonMessage.weather.service.WeatherFurService;
import cn.plou.web.system.commonMessage.weather.vo.WeatherFurInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeatherFurServiceImpl implements WeatherFurService {
    @Autowired
    WeatherFurMapper weatherFurMapper;
    @Autowired
    ZoneCityMapper zoneCityMapper;

    @Override
    public WeatherFurInfo getWeatherFurByCity(String city) {
//        ZoneCity zoneCity = zoneCityMapper.selectByCityName(city);
        return weatherFurMapper.selectWeatherFurByCity(city);
    }
}

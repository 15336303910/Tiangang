package cn.plou.web.system.commonMessage.weather.service.impl;

import cn.plou.web.common.config.common.Constant;
import cn.plou.web.system.baseMessage.company.service.CompanyService;
import cn.plou.web.system.commonMessage.weather.dao.WeatherNowMapper;
import cn.plou.web.system.commonMessage.weather.entity.WeatherNow;
import cn.plou.web.system.commonMessage.weather.service.WeatherNowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WeatherNowServiceImpl implements WeatherNowService {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private WeatherNowMapper weatherNowMapper;
    @Override
    public WeatherNow getWeatherNow(String city) {
        return weatherNowMapper.selectWeatherNow(city);
    }
}

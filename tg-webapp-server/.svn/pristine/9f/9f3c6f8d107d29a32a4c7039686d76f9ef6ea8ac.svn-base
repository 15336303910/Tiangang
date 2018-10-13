package cn.plou.web.system.commonMessage.weather.service;

import cn.plou.web.system.commonMessage.weather.entity.Weather;
import cn.plou.web.system.commonMessage.weather.vo.WeatherInfo;
import cn.plou.web.system.commonMessage.weather.vo.WeatherListInfo;
import cn.plou.web.system.commonMessage.weather.vo.WeatherVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeathrtService {
    //增加采集天气城市信息
    int addWeather(Weather weather);

    int deleteBatchByIds(List<String> ids);

    int modifyWeather(Weather weather);

    int modifyWeatherBatch(WeatherVo weatherVo);

    //按条件查询所有采集天气城市信息
    WeatherListInfo getAllWeather(@Param("weatherVo") WeatherVo weatherVo,
                                  @Param("order") String order,
                                  @Param("sortby") String sortby,
                                  @Param("provianceId")String provianceId,
                                  @Param("cityId")String cityId,
                                  @Param("areaId")String areaId,
                                  @Param("page")Integer page,
                                  @Param("pageSize")Integer pageSize);

}

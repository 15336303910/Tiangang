package cn.plou.web.system.commonMessage.weather.dao;

import cn.plou.web.system.commonMessage.weather.entity.Weather;
import cn.plou.web.system.commonMessage.weather.vo.WeatherInfo;
import cn.plou.web.system.commonMessage.weather.vo.WeatherVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WeatherMapper {

    //增加采集天气城市信息
    int insertWeather(Weather weather);

    int deleteBatchByIds(List<String> weatherIds);

    int updateWeather(Weather weather);

    int updateWeatherBathch(WeatherVo weatherVo);

    //按条件查询所有采集天气城市信息
    List<WeatherInfo> selectAllWeather(@Param("weatherVo") WeatherVo weatherVo,
                                   @Param("order")String order,
                                   @Param("sortby")String sortby,
                                   @Param("zProvianceId")String zProvianceId,
                                   @Param("cityId")String cityId,
                                   @Param("areaId")String areaId,
                                    @Param("page")Integer page,
                                       @Param("pageSize")Integer pageSize);

    //按主键查询采集天气信息
//    Weather selectWeatherById(String id);
    Integer selectWeatherCount(@Param("weatherVo") WeatherVo weatherVo,
                               @Param("zProvianceId")String zProvianceId,
                               @Param("cityId")String cityId,
                               @Param("areaId")String areaId);

}
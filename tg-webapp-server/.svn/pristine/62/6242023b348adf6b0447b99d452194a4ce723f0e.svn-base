package cn.plou.web.system.commonMessage.weather.dao;

import cn.plou.web.system.commonMessage.weather.entity.WeatherNow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface WeatherNowMapper {
    int deleteByPrimaryKey(String city);

    int insert(WeatherNow record);

    int insertSelective(WeatherNow record);

    WeatherNow selectByPrimaryKey(String city);

    int updateByPrimaryKeySelective(WeatherNow record);

    int updateByPrimaryKey(WeatherNow record);

    WeatherNow selectWeatherNow(@Param("city") String city);
}
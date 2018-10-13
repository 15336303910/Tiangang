package cn.plou.web.system.commonMessage.weather.dao;


import cn.plou.web.system.commonMessage.weather.entity.WeatherFur;
import cn.plou.web.system.commonMessage.weather.vo.WeatherFurInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WeatherFurMapper {
    int deleteByPrimaryKey(String rowno);

    int insert(WeatherFur record);

    int insertSelective(WeatherFur record);

    WeatherFur selectByPrimaryKey(String rowno);

    WeatherFurInfo selectWeatherFurByCity(String city);

    int updateByPrimaryKeySelective(WeatherFur record);

    int updateByPrimaryKey(WeatherFur record);
}
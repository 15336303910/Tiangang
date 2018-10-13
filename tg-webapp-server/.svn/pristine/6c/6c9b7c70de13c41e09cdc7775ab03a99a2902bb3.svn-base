package cn.plou.web.system.baseMessage.zoneCity.dao;


import cn.plou.web.system.baseMessage.zoneCity.entity.ZoneCity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ZoneCityMapper {
    int deleteByPrimaryKey(String zPId);

    int insert(ZoneCity record);

    ZoneCity selectByCityName(String cityName);

    int insertSelective(ZoneCity record);

    ZoneCity selectByPrimaryKey(String zPId);

    int updateByPrimaryKeySelective(ZoneCity record);

    int updateByPrimaryKey(ZoneCity record);

    List<ZoneCity> selectZoneCityByZCProvinceid(String zCProvinceid);

    ZoneCity selectZoneCityByZAAreaid(String zAAreaid);
}
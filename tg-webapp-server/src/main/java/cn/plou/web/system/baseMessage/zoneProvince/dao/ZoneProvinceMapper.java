package cn.plou.web.system.baseMessage.zoneProvince.dao;


import cn.plou.web.system.baseMessage.zoneProvince.entity.ZoneProvince;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ZoneProvinceMapper {
    int deleteByPrimaryKey(String zPId);

    int insert(ZoneProvince record);

    int insertSelective(ZoneProvince record);

    ZoneProvince selectByPrimaryKey(String zPId);

    int updateByPrimaryKeySelective(ZoneProvince record);

    int updateByPrimaryKey(ZoneProvince record);

    List<ZoneProvince> selectAllZoneProvince();

    ZoneProvince selectZoneProvinceByZCCityid(String zCCityid);
}
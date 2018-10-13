package cn.plou.web.system.baseMessage.zoneArea.dao;


import cn.plou.web.system.baseMessage.zoneArea.entity.ZoneArea;
import cn.plou.web.system.baseMessage.zoneCity.entity.ZoneCity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ZoneAreaMapper {
    int deleteByPrimaryKey(String zPId);

    int insert(ZoneArea record);

    int insertSelective(ZoneArea record);

    ZoneArea selectByPrimaryKey(String zPId);

    int updateByPrimaryKeySelective(ZoneArea record);

    int updateByPrimaryKey(ZoneArea record);

    List<ZoneArea> selectZoneAreaByZACityid(String zACityid);


}
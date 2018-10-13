package cn.plou.web.station.stationDevRelative.dao;

import java.util.List;

import cn.plou.web.station.stationDevRelative.entity.StationDevRelative;

public interface StationDevRelativeMapper {
    int deleteByPrimaryKey(String primaryId);

    int insert(StationDevRelative record);

    int insertSelective(StationDevRelative record);

    StationDevRelative selectByPrimaryKey(String primaryId);

    int updateByPrimaryKeySelective(StationDevRelative record);

    int updateByPrimaryKey(StationDevRelative record);

    List<String> findHPumpIdsBySystemId(String systemId);

}
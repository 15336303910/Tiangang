package cn.plou.web.station.stationshowdev.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.plou.web.station.stationshowdev.entity.StationShowDev;

public interface StationShowDevMapper {
    int deleteByPrimaryKey(String primaryId);

    int insert(StationShowDev record);

    int insertSelective(StationShowDev record);

    StationShowDev selectByPrimaryKey(String primaryId);

    int updateByPrimaryKeySelective(StationShowDev record);

    int updateByPrimaryKey(StationShowDev record);

    List<StationShowDev> findBySourceId(String sourceId);

    List<StationShowDev> findBySourceIds(@Param("sourceids") List<String> sourceids,
	    @Param("devType") String devType);
}
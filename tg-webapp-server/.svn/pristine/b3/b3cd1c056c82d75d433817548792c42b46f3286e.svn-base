package cn.plou.web.station.event.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.plou.web.mobile.station.query.EventInfoQuery;
import cn.plou.web.station.event.entity.EventInfo;

public interface EventInfoMapper {
    int deleteByPrimaryKey(String primaryId);

    int insert(EventInfo record);

    int insertSelective(EventInfo record);

    EventInfo selectByPrimaryKey(String primaryId);

    int updateByPrimaryKeySelective(EventInfo record);

    int updateByPrimaryKey(EventInfo record);

    Integer eventCount(@Param("query") EventInfoQuery query);

    List<EventInfo> findEventInfos(@Param("query") EventInfoQuery query);
}
package cn.plou.web.station.systemPoint.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.plou.web.station.systemPoint.entity.SystemPoint;

@Mapper
public interface SystemPointMapper {
    int deleteByPrimaryKey(String primaryId);

    int insert(SystemPoint record);

    int insertSelective(SystemPoint record);

    SystemPoint selectByPrimaryKey(String primaryId);

    int updateByPrimaryKeySelective(SystemPoint record);

    int updateByPrimaryKey(SystemPoint record);

    /**
     * 当前系统有效点位
     */
    List<SystemPoint> findPointsBySystemId(@Param("systemId") String systemId,
	    @Param("valided") Integer valided);
}
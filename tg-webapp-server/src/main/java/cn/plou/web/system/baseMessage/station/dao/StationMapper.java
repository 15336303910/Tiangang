package cn.plou.web.system.baseMessage.station.dao;


import cn.plou.web.system.baseMessage.station.entity.Station;
import cn.plou.web.system.baseMessage.station.entity.StationKey;
import cn.plou.web.system.baseMessage.station.vo.StationInfo;
import cn.plou.web.system.baseMessage.station.vo.StationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StationMapper {
    int deleteByPrimaryKey(StationKey key);

    int deleteStationBatchByIds(List<String> stationIds);

    int insert(Station record);

    int insertSelective(Station record);

    Station selectByPrimaryKey(String stationId);

    Station selectByName(String stationName);

    int updateByPrimaryKeySelective(Station record);

    int updateByPrimaryKey(Station record);

    int updateStationBatch(StationVo stationVo);

    List<StationInfo> selectAllStation(@Param("companyIds") List<String> companyIds, @Param("producerId") String producerId,
                                       @Param("stationVo") StationVo stationVo, @Param("order") String oder, @Param("sortby") String sortby);

    List<StationInfo> selectStationByStationIds(@Param("stationIds") List<String> stationIds, @Param("producerId") String producerId,
                                                @Param("stationVo") StationVo stationVo, @Param("order") String oder, @Param("sortby") String sortby,
                                                @Param("page") Integer page, @Param("pageSize") Integer pageSize);

    List<Station> selectStationByCompanyId(String companyId);

		String getMaxStationId();

		List<Station> selectStationByCompanyIds(List<String> companyIds);
}
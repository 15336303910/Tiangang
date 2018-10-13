package cn.plou.web.system.baseMessage.sewageStation.dao;


import cn.plou.web.system.baseMessage.sewageStation.entity.SewageStation;
import cn.plou.web.system.baseMessage.sewageStation.vo.SewageStationInfo;
import cn.plou.web.system.baseMessage.sewageStation.vo.SewageStationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SewageStationMapper {
    int deleteByPrimaryKey(String sewageStationId);

    int insert(SewageStation record);

    int insertSelective(SewageStation record);

    SewageStation selectByPrimaryKey(String sewageStationId);

    int updateByPrimaryKeySelective(SewageStation record);

    int updateByPrimaryKey(SewageStation record);

    int updateSewageStationBatch(SewageStationVo sewageStationVo);

    int deleteSewageStationBatchByIds(List<String> sewageStationIds);

    List<SewageStationInfo> selectAllSewageStation(@Param("companyIds") List<String> companyIds, @Param("sewageStationVo") SewageStationVo sewageStationVo,
                                                   @Param("order") String order, @Param("sortby") String sortby);
}
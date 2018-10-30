package cn.plou.web.balance.trendCurve.dao;
import cn.plou.web.balance.trendCurve.entity.UnitRunningData;
import cn.plou.web.balance.trendCurve.vo.RunningData;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UnitRunningDataMapper {
    int deleteByPrimaryKey(String primaryId);

    int insert(UnitRunningData record);

    int insertSelective(UnitRunningData record);

    UnitRunningData selectByPrimaryKey(String primaryId);

    int updateByPrimaryKeySelective(UnitRunningData record);

    int updateByPrimaryKey(UnitRunningData record);

    List<RunningData> selectUnitRuuningDataList(@Param("unitId")String unitId,
                                                @Param("startDate")Date startDate,
                                                @Param("endDate")Date endDate);
}
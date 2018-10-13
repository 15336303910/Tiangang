package cn.plou.web.system.meterMessage.mbus.dao;

import cn.plou.web.system.meterMessage.mbus.entity.MeterTimingDefine;
import cn.plou.web.system.meterMessage.mbus.vo.MeterTimingDefineVo;
import cn.plou.web.system.meterMessage.mbus.vo.TimingTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface MeterTimingDefineMapper {
    int deleteByPrimaryKey(String primaryId);

    int deleteTimingDefineBatch(List<String> primaryIds);

    int insert(MeterTimingDefine record);

    int insertSelective(MeterTimingDefine record);

    int insertSelectiveBatch(List<MeterTimingDefine> meterTimingDefines);
    int insertSelectiveBatch3(List<MeterTimingDefine> meterTimingDefines);

    MeterTimingDefine selectByPrimaryKey(String primaryId);

    List<String> selectAllMbusCodes();

    int updateByPrimaryKeySelective(MeterTimingDefine record);

    int updateByPrimaryKey(MeterTimingDefine record);

    List<MeterTimingDefine> selectByMbusCodeAndUpCommMode(@Param("mbusCode") String mbusCode,
                                             @Param("upCommMode")String upCommMode);

    /*List<MeterTimingDefine> selectAllByDefineVo(@Param("mbusCodes") List<String> mbusCodes,
                                                @Param("timingTasks") List<TimingTask> timingTasks,
                                                @Param("upCommMode") String upCommonMode,
                                                @Param("intervals") Integer intervals);*/
    List<MeterTimingDefine> selectAllByDefineVo(@Param("mbusCodes") List<String> mbusCodes);

    List<MeterTimingDefine> selectAllByMbusCodesAnd(List<String> mbusCodes);
    //List<MeterTimingDefine> selectAllTimingsByMbusCodes(List<String> mbusCodes);
    List<MeterTimingDefine> selectAllTimigByMbusCode(String mbusCode);
    MeterTimingDefine selectIntervalsByMbusCode(String mbusCode);
    MeterTimingDefine selectPrimaryIdByData(@Param("mbusCode")String mbusCode,
                                 @Param("upCommMode")String upCommMode,
                                 @Param("intervals")Integer intervals,
                                 @Param("timing")String timing,
                                 @Param("orderType") String orderType);

		void insertSelectiveBatch2(List<MeterTimingDefine> needInsertList);
}
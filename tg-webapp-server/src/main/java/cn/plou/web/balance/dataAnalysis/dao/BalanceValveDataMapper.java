package cn.plou.web.balance.dataAnalysis.dao;

import cn.plou.web.balance.dataAnalysis.entity.BalanceValveData;

import java.util.List;

public interface BalanceValveDataMapper {
    int deleteByPrimaryKey(String primaryId);

    int insert(BalanceValveData record);

    int insertSelective(BalanceValveData record);

    BalanceValveData selectByPrimaryKey(String primaryId);

    List<BalanceValveData> selectAllByBuildId(String buildId);

    int updateByPrimaryKeySelective(BalanceValveData record);

    int updateByPrimaryKey(BalanceValveData record);
}
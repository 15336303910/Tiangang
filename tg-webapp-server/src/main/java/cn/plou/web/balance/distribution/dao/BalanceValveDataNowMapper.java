package cn.plou.web.balance.distribution.dao;

import cn.plou.web.balance.distribution.entity.BalanceValveDataNow;

public interface BalanceValveDataNowMapper {
    int deleteByPrimaryKey(String meterId);

    int insert(BalanceValveDataNow record);

    int insertSelective(BalanceValveDataNow record);

    BalanceValveDataNow selectByPrimaryKey(String meterId);

    int updateByPrimaryKeySelective(BalanceValveDataNow record);

    int updateByPrimaryKey(BalanceValveDataNow record);

    BalanceValveDataNow selectDataNowByBuildId(String buildId);
}
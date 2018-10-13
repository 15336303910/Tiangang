package cn.plou.web.charge.chargeconfig.dao;

import cn.plou.web.charge.chargeconfig.entity.MoneyStateInfo;

public interface MoneyStateInfoMapper {
    int deleteByPrimaryKey(String primaryId);

    int insert(MoneyStateInfo record);

    int insertSelective(MoneyStateInfo record);

    MoneyStateInfo selectByPrimaryKey(String primaryId);

    int updateByPrimaryKeySelective(MoneyStateInfo record);

    int updateByPrimaryKey(MoneyStateInfo record);
}
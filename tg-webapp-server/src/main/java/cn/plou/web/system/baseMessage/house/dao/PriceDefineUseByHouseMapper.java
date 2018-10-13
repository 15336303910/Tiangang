package cn.plou.web.system.baseMessage.house.dao;

import cn.plou.web.system.baseMessage.house.entity.PriceDefineUseInHouse;

import java.util.List;

public interface PriceDefineUseByHouseMapper {
    int deleteByPrimaryKey(String primaryId);

    int insert(PriceDefineUseInHouse record);

    int insertSelective(PriceDefineUseInHouse record);

    PriceDefineUseInHouse selectByPrimaryKey(String primaryId);

    int updateByPrimaryKeySelective(PriceDefineUseInHouse record);

    int updateByPrimaryKey(PriceDefineUseInHouse record);

    List<PriceDefineUseInHouse> selectByYearAndCompany(String companyId);
}
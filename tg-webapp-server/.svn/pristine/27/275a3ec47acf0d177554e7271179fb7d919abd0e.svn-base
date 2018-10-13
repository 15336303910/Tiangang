package cn.plou.web.charge.chargeconfig.dao;
import org.apache.ibatis.annotations.Param;

import cn.plou.web.charge.chargeconfig.entity.PriceAccuracyInfo;

import java.util.List;

public interface PriceAccuracyInfoMapper {

    int deleteByPrimaryKey(String primaryId);


    int insert(PriceAccuracyInfo record);


    int insertSelective(PriceAccuracyInfo record);


    PriceAccuracyInfo selectByPrimaryKey(String primaryId);


    int updateByPrimaryKeySelective(PriceAccuracyInfo record);


    int updateByPrimaryKey(PriceAccuracyInfo record);


    PriceAccuracyInfo    findPriceAccuracyInfo(PriceAccuracyInfo record);

    PriceAccuracyInfo findByCompanyId(@Param("companyId")String companyId);


}
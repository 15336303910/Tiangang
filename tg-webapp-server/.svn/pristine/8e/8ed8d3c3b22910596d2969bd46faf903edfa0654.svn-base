package cn.plou.web.charge.heatingmanage.dao;

import cn.plou.web.charge.heatingmanage.domain.ContractHeatMoneyDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContractHeatMoneyDetailMapper {

    int deleteByPrimaryKey(String primaryId);

    int insert(ContractHeatMoneyDetail record);

    ContractHeatMoneyDetail selectByPrimaryKey(String primaryId);

    int updateByPrimaryKey(ContractHeatMoneyDetail record);

    ContractHeatMoneyDetail getTodayLast(String companyId);
}
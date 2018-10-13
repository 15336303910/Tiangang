package cn.plou.web.charge.heatingmanage.dao;

import cn.plou.web.charge.heatingmanage.domain.ContractHeatInfoTmp;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContractHeatInfoTmpMapper {

    int deleteByPrimaryKey(String approveSerial);

    int insert(ContractHeatInfoTmp record);

    ContractHeatInfoTmp selectByPrimaryKey(String approveSerial);

    int updateByPrimaryKey(ContractHeatInfoTmp record);

    ContractHeatInfoTmp getTodayLast(String companyId);
}
package cn.plou.web.charge.chargeconfig.dao;

import cn.plou.web.charge.chargeconfig.dto.GeneralLedgerSearchDTO;
import cn.plou.web.charge.chargeconfig.entity.BankReconciliationsHead;
import cn.plou.web.charge.chargeconfig.vo.GeneralLedgerListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Mybatis Generator 2018/09/11
 */
@Mapper
public interface BankReconciliationsHeadMapper {

    int deleteByPrimaryKey(String primaryId);

    int insert(BankReconciliationsHead record);

    int insertSelective(BankReconciliationsHead record);

    BankReconciliationsHead selectByPrimaryKey(String primaryId);

    BankReconciliationsHead findByPlatformCodeAndTransactionDate(@Param("platformCode") String platformCode, @Param("transactionDate") String transactionDate);

    int updateByPrimaryKeySelective(BankReconciliationsHead record);

    int updateByPrimaryKey(BankReconciliationsHead record);

    BankReconciliationsHead findIfCharge(@Param("companyId") String companyId, @Param("annual") String annual, @Param("bankId") String bankId, @Param("platformCode") String platformCode);

    List<GeneralLedgerListVO> generalLedgerList(GeneralLedgerSearchDTO generalLedgerSearchDTO);
}
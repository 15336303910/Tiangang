package cn.plou.web.charge.chargeconfig.dao;

import cn.plou.web.charge.chargeconfig.dto.DoChargeDTO;
import cn.plou.web.charge.chargeconfig.dto.LedgerDetailSearchDTO;
import cn.plou.web.charge.chargeconfig.entity.BankReconciliationsDetail;
import cn.plou.web.charge.chargeconfig.vo.LedgerDetailListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Mybatis Generator 2018/09/11
 */
@Mapper
public interface BankReconciliationsDetailMapper {

    int deleteByPrimaryKey(String primaryId);

    int insert(BankReconciliationsDetail record);

    int insertSelective(BankReconciliationsDetail record);

    BankReconciliationsDetail selectByPrimaryKey(String primaryId);

    int updateByPrimaryKeySelective(BankReconciliationsDetail record);

    int updateByPrimaryKey(BankReconciliationsDetail record);

    int insertBatch(List<BankReconciliationsDetail> saveList);

    int updateReconciliationsFlag(@Param("list") List<BankReconciliationsDetail> bankReconciliationsDetails, @Param("flag") String flag);

    List<BankReconciliationsDetail> getBankOnly(DoChargeDTO doChargeDTO);

    List<BankReconciliationsDetail> getChargeNotEqual(DoChargeDTO doChargeDTO);

    List<BankReconciliationsDetail> getChargeEqual(DoChargeDTO doChargeDTO);

    List<LedgerDetailListVO> generalLedgerList(LedgerDetailSearchDTO ledgerDetailSearchDTO);
}
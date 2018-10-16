package cn.plou.web.charge.chargeconfig.service;

import cn.plou.web.charge.chargeconfig.dto.GeneralLedgerSearchDTO;
import cn.plou.web.charge.chargeconfig.vo.GeneralLedgerListVO;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: BankReconciliationsHeadService
 * @Description:
 * @Author: youbc
 * @Date 2018-09-27 15:55
 */
public interface BankReconciliationsHeadService {

    String getDailyReconciliation(String platformCode, String transactionDate);

    PageInfo<GeneralLedgerListVO> generalLedgerList(GeneralLedgerSearchDTO generalLedgerSearchDTO);
}

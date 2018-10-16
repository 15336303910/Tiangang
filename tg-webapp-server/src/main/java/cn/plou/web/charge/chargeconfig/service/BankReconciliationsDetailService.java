package cn.plou.web.charge.chargeconfig.service;

import cn.plou.web.charge.chargeconfig.dto.LedgerDetailSearchDTO;
import cn.plou.web.charge.chargeconfig.vo.LedgerDetailListVO;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: BankReconciliationsHeadService
 * @Description:
 * @Author: youbc
 * @Date 2018-09-27 15:55
 */
public interface BankReconciliationsDetailService {

    PageInfo<LedgerDetailListVO> ledgerDetailList(LedgerDetailSearchDTO ledgerDetailSearchDTO);
}

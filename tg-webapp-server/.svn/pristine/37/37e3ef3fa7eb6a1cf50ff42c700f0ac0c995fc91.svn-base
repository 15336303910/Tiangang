package cn.plou.web.charge.chargeconfig.service.impl;

import cn.plou.web.charge.chargeconfig.dao.BankReconciliationsDetailMapper;
import cn.plou.web.charge.chargeconfig.dto.LedgerDetailSearchDTO;
import cn.plou.web.charge.chargeconfig.service.BankReconciliationsDetailService;
import cn.plou.web.charge.chargeconfig.vo.LedgerDetailListVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: BankReconciliationsDetailServiceImpl
 * @Description:
 * @Author: youbc
 * @Date 2018-10-15 10:56
 */
@Service
public class BankReconciliationsDetailServiceImpl implements BankReconciliationsDetailService {

    @Autowired
    private BankReconciliationsDetailMapper bankReconciliationsDetailMapper;

    @Override
    public PageInfo<LedgerDetailListVO> ledgerDetailList(LedgerDetailSearchDTO ledgerDetailSearchDTO) {
        PageHelper.startPage(ledgerDetailSearchDTO.getPage(), ledgerDetailSearchDTO.getPageSize());
        List<LedgerDetailListVO> list = bankReconciliationsDetailMapper.generalLedgerList(ledgerDetailSearchDTO);
        return new PageInfo<>(list);
    }
}

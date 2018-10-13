package cn.plou.web.charge.chargeconfig.service.impl;

import cn.plou.web.charge.chargeconfig.dao.BankReconciliationsHeadMapper;
import cn.plou.web.charge.chargeconfig.entity.BankReconciliationsHead;
import cn.plou.web.charge.chargeconfig.service.BankReconciliationsHeadService;
import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.system.commonMessage.typeMst.dao.TypeMstMapper;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: BankReconciliationsHeadServiceImpl
 * @Description:
 * @Author: youbc
 * @Date 2018-09-27 15:55
 */
@Service
public class BankReconciliationsHeadServiceImpl implements BankReconciliationsHeadService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BankReconciliationsHeadMapper bankReconciliationsHeadMapper;

    @Autowired
    private TypeMstMapper typeMstMapper;

    /**
     * @Description: 获取银行对账文件与系统内数据的对账状态
     * @Param: [platformCode, transactionDate]
     * @Return: java.lang.String
     * @Author: youbc
     * @Date: 2018/9/27 16下午04
     */
    @Override
    public String getDailyReconciliation(String platformCode, String transactionDate) {
        BankReconciliationsHead head = bankReconciliationsHeadMapper.findByPlatformCodeAndTransactionDate(platformCode, transactionDate);
        if (head == null) {
            logger.error("未找到对账记录！");
            throw new BusinessException("未找到对账记录！");
        } else {
            TypeMst typeMst = typeMstMapper.selectByPrimaryKey(head.getReconciliationsFlag());
            if (typeMst == null) {
                logger.error("未找到对账状态！");
                throw new BusinessException("未找到对账状态！");
            } else {
                return typeMst.getTypeName();
            }
        }
    }
}

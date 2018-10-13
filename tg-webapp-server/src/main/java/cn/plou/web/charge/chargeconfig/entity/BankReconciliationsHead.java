package cn.plou.web.charge.chargeconfig.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Mybatis Generator 2018/09/11
 */
@Data
public class BankReconciliationsHead {

    /**
     * 记录号ＩＤ
     */
    private String primaryId;

    /**
     * 公司ＩＤ
     */
    private String companyId;

    /**
     * 采暖年度
     */
    private String annual;

    /**
     * 银行ＩＤ
     */
    private String bankId;

    /**
     * 平台码
     */
    private String platformCode;

    /**
     * 交易总笔数
     */
    private Integer transactionTotal;

    /**
     * 交易总金额
     */
    private BigDecimal transactionMoney;

    /**
     * 交易日期
     */
    private Date transactionDate;

    /**
     * 对账标志：账平、账不平
     */
    private String reconciliationsFlag;

    /**
     * 备注
     */
    private String notes;

    /**
     * 保留１
     */
    private String memo1;

    /**
     * 保留２
     */
    private String memo2;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 更新者
     */
    private String updateUser;
}
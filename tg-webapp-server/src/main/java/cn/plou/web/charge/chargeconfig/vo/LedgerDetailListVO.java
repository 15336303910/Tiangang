package cn.plou.web.charge.chargeconfig.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Mybatis Generator 2018/09/11
 */
@Data
public class LedgerDetailListVO {

    /**
     * 记录号ＩＤ
     */
    private String primaryId;

    /**
     * 公司ＩＤ
     */
    private String companyId;
    private String companyName;

    /**
     * 采暖年度
     */
    private String annual;

    /**
     * 银行ＩＤ
     */
    private String bankId;
    private String bankName;

    /**
     * 平台码
     */
    private String platformCode;

    /**
     * 用户ＩＤ
     */
    private String consumerId;

    /**
     * 交易流水号
     */
    private String thirdPartyFlowCode;

    /**
     * 交易日期
     */
    private Date payDate;

    /**
     * 交易金额
     */
    private BigDecimal payMoney;

    /**
     * 入账柜员
     */
    private String operatorCode;

    /**
     * 入账网点
     */
    private String operatorArea;

    /**
     * 代收方式
     */
    private String payMode;

    /**
     * 推票手机号
     */
    private String pushTel;

    /**
     * 推票邮箱
     */
    private String pushMail;

    /**
     * 对账标志：正常、收费系统无、金额不匹配。。。
     */
    private String reconciliationsFlag;
    private String reconciliationsFlagName;

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
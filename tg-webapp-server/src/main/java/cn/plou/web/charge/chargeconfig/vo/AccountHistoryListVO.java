package cn.plou.web.charge.chargeconfig.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 对账历史
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018/8/31 14下午23
 */
@Data
public class AccountHistoryListVO {

    /**
     * 对账流水号：用户ＩＤ＋缴费日期
     */
    private String primaryId;

    /**
     * 公司ＩＤ
     */
    private String companyId;

    /**
     * 对账日期
     */
    private Date reconciliationsDate;

    /**
     * 对账员工
     */
    private String reconciliationsUser;
    private String reconciliationsUserName;

    /**
     * 对账状态：平、不平
     */
    private String reconciliationsStatus;
    private String reconciliationsStatusName;

    /**
     * 采暖年度
     */
    private String annual;

    /**
     * 缴费方式：现金、刷卡、银行、微信、支付宝、网银等
     */
    private String accountType;
    private String accountTypeName;

    /**
     * 缴费通道：大厅收费、移动支付、银行代收等
     */
    private String accountChannel;
    private String accountChannelName;

    /**
     * 缴费金额
     */
    private BigDecimal payMoney;

    /**
     * 扣点金额
     */
    private BigDecimal pointMoney;

    private BigDecimal accountCostTrue; // 实际到账=缴费金额-扣点金额

    /**
     * 账不平说明
     */
    private String accountExplain;

    /**
     * 备注
     */
    private String notes;

    /**
     * 创建者
     */
    private Date createDate;

    /**
     * 创建时间
     */
    private String createUser;

    /**
     * 更新者
     */
    private Date updateDate;

    /**
     * 更新时间
     */
    private String updateUser;
}
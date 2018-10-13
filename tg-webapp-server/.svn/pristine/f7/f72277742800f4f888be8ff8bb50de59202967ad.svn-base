package cn.plou.web.charge.chargeconfig.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName: 对账收费弹框-收款通道详细信息-列表
 * @Description:
 * @Author: youbc
 * @Date 2018-09-03 15:34
 */
@Data
public class ChargeAccountDetailListVO {

    private String accountChannel; // 缴费通道
    private String accountChannelName;

    /**
     * 缴费方式：现金、刷卡、银行、微信、支付宝、网银等
     */
    private String accountType;
    private String accountTypeName;


    private Integer num; // 交易笔数

    /**
     * 交易金额
     */
    private BigDecimal payMoney;

    /**
     * 扣点费用
     */
    private BigDecimal pointMoney;

    private BigDecimal moneyTrue; // 实际到账（元）
}

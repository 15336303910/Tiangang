package cn.plou.web.charge.chargeconfig.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 营业分析
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018/10/9 16下午11
 */
@Data
public class BusinessAnalysisVO {

    private String dateRange; // 日期区间

    private String accountChannel; // 缴费方式（即缴费通道）

    private String accountChannelName;

    private BigDecimal accountCostTotal; // 缴费总额 TODO youbc 是否要减去扣点金额

    private BigDecimal accountCostPercent; // 缴费额占比

    private Integer numTotal; // 交易笔数

    private BigDecimal numPercent; // 交易数占比

}
package cn.plou.web.charge.chargeconfig.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yinxiaochen
 * 2018/8/31 8:57
 */
@Data
public class ClearTaskDTO {


    private String consumerId;
    /*供热面积：可配置等于用户信息表中的建筑面积、套内建筑面积、使用面积及修正公式，自动生成*/
    private BigDecimal heatingArea;
    /*收费面积：可配置等于用户信息表中的建筑面积、套内建筑面积、使用面积及修正公式，自动生成*/
    private BigDecimal chargeArea;




    /*===================================================*/



    /*供暖年度*/
    private String annual;


    /*收费面积：可配置等于用户信息表中的建筑面积、套内建筑面积、使用面积及修正公式，自动生成*/
    private BigDecimal payArea;

    /*预收热费金额：自动计算＝收费面积＊面积单价*/
    private BigDecimal advHeatCost;

    /*热费应收合计：自动从《用户年度采暖费用明细表》中取合计数*/
    private BigDecimal sumReceivable;

    /*热费实收合计：自动从《用户年度采暖缴费明细表》中取合计数*/
    private BigDecimal sumAccount;


    /*缴费状态：已缴费、未交费、余额不足*/
    private String payOver;


    /*heating_status*/
    private String heatingStatus;

}

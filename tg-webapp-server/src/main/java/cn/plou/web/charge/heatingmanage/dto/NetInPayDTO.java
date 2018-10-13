package cn.plou.web.charge.heatingmanage.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Description: 入网管理-入网缴费
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018-8-27 17:02:44
 */
@Data
public class NetInPayDTO {

    private String primaryId; // 合同记录号 uuid

    private String contractCode; // 合同编号

    @NotNull(message = "请填写本次实缴金额")
    private BigDecimal accountCost; // 缴费金额

    private String payType; // 缴费方式：现金，刷卡，支付宝，微信，支票2*

    @Length(max = 200, message = "顶账说明不能超过 200 字")
    private String notes; // 备注（顶账说明）

    private String isbill; // 发票开具：是、否

    @Length(max = 32, message = "发票号不能超过 32 字")
    private String billno; // 发票号

    @NotNull(message = "请填写收据号")
    @Length(max = 32, message = "收据号不能超过 32 字")
    private String receiptno; // 收据号

    private String companyId; // 公司ｉｄ
}
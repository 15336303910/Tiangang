package cn.plou.web.charge.heatingmanage.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @ClassName: ServicePaymentDTO
 * @Description: 供热服务-服务缴费
 * @Author: youbc
 * @Date 2018-08-16 10:45
 */
@Data
public class ServicePaymentDTO {

    private String primaryId; // 主键：用户ＩＤ＋申请时间

    private String costType; // 费用类型：工程费、材料费、手续费等

    @NotNull(message = "请填写服务单价")
    private BigDecimal singleCost; // 单价

    @NotNull(message = "请填写数量")
    private BigDecimal nums; // 数量

    @NotNull(message = "请填写实际缴费金额")
    private BigDecimal actCost; // 实收金额

    private String payChannel; // 缴费方式：现金、刷卡、微信、支付宝等

    @Length(max = 32, message = "发票号不能超过 32 字")
    private String billno; // 发票号

    private String printFlag; // 打印标志

    @NotNull(message = "请填写收据号")
    @Length(max = 32, message = "收据号不能超过 32 字")
    private String receiptno; // 收据号
}

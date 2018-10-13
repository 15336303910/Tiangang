package cn.plou.web.charge.chargeconfig.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Description: 银行对账请求数据包
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018/9/11 11上午03
 */
@Data
public class BankChargeDTO {

    @NotEmpty(message = "请传入对账日期")
    private String reconciliationsDate;

    @NotEmpty(message = "请传入银行 IP")
    private String bankIp;

    @NotEmpty(message = "请传入平台码")
    private String platformCode;

    private String bankId;
}
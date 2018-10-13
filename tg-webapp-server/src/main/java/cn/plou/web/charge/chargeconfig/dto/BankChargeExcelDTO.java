package cn.plou.web.charge.chargeconfig.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description: 银行对账请求 Excel 内容
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018/9/11 11上午03
 */
@Data
public class BankChargeExcelDTO {

    private Integer transactionTotal;

    private BigDecimal transactionMoney;

    private Date transactionDate;

    List<BankChargeListDTO> list;
}
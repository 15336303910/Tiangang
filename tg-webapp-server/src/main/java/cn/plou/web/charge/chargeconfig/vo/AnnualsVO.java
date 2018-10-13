package cn.plou.web.charge.chargeconfig.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description:
 * @Param:
 * @Return:
 * @Author:
 * @Date:
 */
@Data
public class AnnualsVO {
    private String annual;
    private BigDecimal needTotal;
    private BigDecimal realNeedTotal;
}
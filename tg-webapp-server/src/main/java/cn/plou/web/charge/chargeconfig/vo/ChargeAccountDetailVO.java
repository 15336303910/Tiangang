package cn.plou.web.charge.chargeconfig.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: 对账收费弹框-收款通道详细信息
 * @Description:
 * @Author: youbc
 * @Date 2018-09-03 15:34
 */
@Data
public class ChargeAccountDetailVO {

    private BigDecimal payMoney; // 缴费金额

    private BigDecimal pointMoney; // 扣点金额

    private BigDecimal totalMoney; // 实际到账（元）

    private Integer num; // 收费笔数

    /**
     * 对账状态：平、不平
     */
    private String reconciliationsStatus;

    /**
     * 账不平说明
     */
    private String accountExplain;

    /**
     * 备注
     */
    private String notes;

    private List<ChargeAccountDetailListVO> list;

}

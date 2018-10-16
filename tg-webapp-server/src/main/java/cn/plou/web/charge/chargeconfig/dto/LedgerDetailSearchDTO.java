package cn.plou.web.charge.chargeconfig.dto;

import cn.plou.web.common.config.common.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @ClassName: ChargeAccountSearchDTO
 * @Description: 对接设置查询
 * @Author: youbc
 * @Date 2018年9月18日16:39:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LedgerDetailSearchDTO extends BasePageEntity {

    @NotEmpty(message = "请选择范围（公司、站、小区等）")
    private String rangeId; // 右侧列表，支持小区、楼和单元

    private Date payDateStart; // 交易日期

    private Date payDateEnd; // 交易日期

    /**
     * 交易流水号
     */
    private String thirdPartyFlowCode;

    /**
     * 银行ＩＤ
     */
    @NotNull(message = "请选择银行")
    private String bankId;

    /**
     * 采暖年度
     */
    @NotNull(message = "请选择采暖季")
    private String annual;

    /**
     * 对账标志：账平、账不平
     */
    @NotNull(message = "请选择对账状态")
    private String reconciliationsFlag;
}

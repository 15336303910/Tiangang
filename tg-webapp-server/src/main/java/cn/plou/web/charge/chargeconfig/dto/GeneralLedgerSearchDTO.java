package cn.plou.web.charge.chargeconfig.dto;

import cn.plou.web.common.config.common.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @ClassName: GeneralLedgerSearchDTO
 * @Description: 通道管理-银行对账-总账数据查询
 * @Author: youbc
 * @Date 2018年9月18日16:39:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GeneralLedgerSearchDTO extends BasePageEntity {

    @NotEmpty(message = "请选择公司")
    @Length(min = 5, max = 5, message = "只能按公司查询")
    private String companyId; // 公司ID

    private Date transactionDateStart; // 交易日期

    private Date transactionDateEnd; // 交易日期

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

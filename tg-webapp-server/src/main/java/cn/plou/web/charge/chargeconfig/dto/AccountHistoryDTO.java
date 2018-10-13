package cn.plou.web.charge.chargeconfig.dto;

import cn.plou.web.common.config.common.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @Description: 对账历史
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018/8/31 14下午23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountHistoryDTO extends BasePageEntity {

    /**
     * 公司ＩＤ
     */
    @NotEmpty(message = "请选择公司")
    @Length(min = 5, max = 5, message = "只能按公司查询")
    private String companyId;

    /**
     * 对账日期
     */
    private Date reconciliationsDateStart;
    private Date reconciliationsDateEnd;

    /**
     * 对账员工
     */
    private String reconciliationsUser;

    /**
     * 对账状态：平、不平
     */
    private String reconciliationsStatus;

    /**
     * 缴费通道：大厅收费、移动支付、银行代收等
     */
    private String accountChannel;

}
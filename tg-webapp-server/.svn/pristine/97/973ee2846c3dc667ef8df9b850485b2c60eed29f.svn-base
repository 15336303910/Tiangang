package cn.plou.web.charge.chargeconfig.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 往年缴费时传的参数
 * @Param:
 * @Return:
 * @Author:
 * @Date:
 */
@Data
public class UserYearAccountDetailForOldYearVO {
    @NotEmpty(message = "公司id不能为空")
    private String companyId;
    @NotEmpty(message = "consumerId不能为空")
    private String consumerId;
    @NotNull(message = "缴费金额不能为空")
    private BigDecimal accountCost;
    private String accountType;
    private String notes;
    private String idcard;
    private String name;
    private String tel;
    private String userCompany;
    @NotNull(message = "精度处理后的需交金额不能为空")
    private BigDecimal needTotal;
    @NotNull(message = "需交金额不能为空")
    private BigDecimal realNeedTotal;
    private boolean wantChange;//是否找零
    private List<AnnualsVO> selectedAnnualParams;


}
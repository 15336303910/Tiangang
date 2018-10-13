package cn.plou.web.charge.chargeconfig.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Description: 对账收费弹框
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018/9/3 14下午50
 */
@Data
public class ChargeAccountDTO {

    /**
     * 公司ＩＤ
     */
    @NotEmpty(message = "请选择公司")
    @Length(min = 5, max = 5, message = "只能按公司对账")
    private String companyId;

    /**
     * 缴费日期
     */
    @NotNull(message = "请选择缴费日期")
    private Date accountTime;

    private String payChannel; // 缴费通道

}
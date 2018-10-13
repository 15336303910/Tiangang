package cn.plou.web.charge.chargeconfig.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Description: 对账收费弹框
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018/9/3 14下午50
 */
@Data
public class SaveChargeAccountDTO {

    /**
     * 公司ＩＤ
     */
    @NotEmpty(message = "请传入公司")
    @Length(min = 5, max = 5, message = "只能按公司对账")
    private String companyId;

    /**
     * 缴费日期
     */
    @NotNull(message = "请传入缴费日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date accountTime;

    @Valid
    List<SaveChargeAccountListDTO> list;
}
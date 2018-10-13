package cn.plou.web.charge.chargeconfig.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @Description: 对账收费弹框
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018/9/3 14下午50
 */
@Data
public class SaveChargeAccountListDTO {

    private String accountChannel;

    private String reconciliationsStatus;

    @Length(max = 200, message = "对账不平说明不能超过 200 字")
    private String accountExplain;

    @Length(max = 200, message = "备注不能超过 200 字")
    private String notes;
}
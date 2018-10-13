package cn.plou.web.charge.chargeconfig.dto;

import cn.plou.web.common.config.common.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @ClassName: ChargeAccountSearchDTO
 * @Description: 对接设置查询
 * @Author: youbc
 * @Date 2018年9月18日16:39:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DockingSetSearchDTO extends BasePageEntity {

    @NotEmpty(message = "请选择公司")
    @Length(min = 5, max = 5, message = "只能按公司查询")
    private String companyId; // 公司ID

    private String bankId; // 银行名称
}

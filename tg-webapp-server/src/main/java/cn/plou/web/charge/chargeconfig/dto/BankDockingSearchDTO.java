package cn.plou.web.charge.chargeconfig.dto;

import cn.plou.web.common.config.common.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @ClassName: ChargeAccountSearchDTO
 * @Description: 银行对接查询
 * @Author: youbc
 * @Date 2018年9月18日16:39:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BankDockingSearchDTO extends BasePageEntity {

    @NotEmpty(message = "请选择范围（公司、站、小区等）")
    private String companyId; // 公司ID

    private String content; // 查询内容

    private Date requestDataDateStart; // 请求数据时间

    private Date requestDataDateEnd; // 请求数据时间

    private String bankId; // 缴费通道

    private String businessCode; // 业务编码
}

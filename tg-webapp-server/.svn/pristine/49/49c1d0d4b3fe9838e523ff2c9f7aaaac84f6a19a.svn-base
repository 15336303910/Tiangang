package cn.plou.web.charge.chargeconfig.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Description: 营业分析查询
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018/10/9 15下午56
 */
@Data
public class BusinessAnalysisSearchDTO {

    @NotEmpty(message = "请选择公司")
    private String companyId; // 公司ID

    @NotNull(message = "请选择时间段")
    private Date dateStart;

    @NotNull(message = "请选择时间段")
    private Date dateEnd;

    @NotNull(message = "请选择缴费方式")
    private String payChannel; // 缴费方式（选择全部，则传空字符串）

}

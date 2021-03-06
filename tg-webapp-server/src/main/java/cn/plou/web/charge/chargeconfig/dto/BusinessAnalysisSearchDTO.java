package cn.plou.web.charge.chargeconfig.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = 5, max = 5, message = "只能按公司查询")
    private String companyId; // 公司ID

    private Date dateStart;

    private Date dateEnd;

    private String currentAnnual; // 本采暖季

    @NotNull(message = "请选择缴费方式")
    private String payChannel; // 缴费方式（选择全部，则传空字符串）

}

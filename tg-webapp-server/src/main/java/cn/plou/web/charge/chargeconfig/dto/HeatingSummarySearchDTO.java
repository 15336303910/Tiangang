package cn.plou.web.charge.chargeconfig.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @Description: 供热汇总查询
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018/10/9 15下午56
 */
@Data
public class HeatingSummarySearchDTO {

    @NotEmpty(message = "请选择范围（公司、站、小区等）")
    private String rangeId; // 右侧列表，支持小区、楼和单元

    @NotEmpty(message = "请选择采暖年度")
    private String annual;

    @NotEmpty(message = "请选择统计对象")
    private String summaryUnit; // 统计对象

}

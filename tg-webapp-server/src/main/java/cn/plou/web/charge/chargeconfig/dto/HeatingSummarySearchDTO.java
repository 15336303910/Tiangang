package cn.plou.web.charge.chargeconfig.dto;

import cn.plou.web.common.config.common.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @Description: 供热汇总查询
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018/10/9 15下午56
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HeatingSummarySearchDTO extends BasePageEntity {

    @NotEmpty(message = "请选择范围（公司、站、小区等）")
    private String rangeId; // 右侧列表，支持小区、楼和单元

    @NotEmpty(message = "请选择采暖年度")
    private String annual;

    @NotEmpty(message = "请选择统计对象")
    private String statisticalObject; // 统计对象

}

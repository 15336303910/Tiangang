package cn.plou.web.charge.chargeconfig.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description: 统计分析-供热汇总
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018/10/9 16下午11
 */
@Data
public class HeatingSummaryVO {

    private HeatingSummaryCountVO count;

    private List<HeatingSummaryListVO> list;
}
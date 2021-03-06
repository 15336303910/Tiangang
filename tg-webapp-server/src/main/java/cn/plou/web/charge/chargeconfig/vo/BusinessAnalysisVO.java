package cn.plou.web.charge.chargeconfig.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description: 营业分析
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018/10/9 16下午11
 */
@Data
public class BusinessAnalysisVO {

    private List<BusinessAnalysisChartVO> chart;

    private List<BusinessAnalysisListVO> list;
}

package cn.plou.web.heatManage.dataAnalysis.service;

        import cn.plou.web.heatManage.dataAnalysis.vo.HeatDistributionData;

        import java.util.List;

/**
 *
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-06-29 09:26:54
 */
public interface HeatDistributionService {

    List<HeatDistributionData> getHeatDistributionData(String selectId, String selectType, String selectDateType, String heatParamType, String start, String end);

   // List<HeatDistributionByBuildNo> getHeatDistributionByBuildNo(Map<String, Object> map);
}

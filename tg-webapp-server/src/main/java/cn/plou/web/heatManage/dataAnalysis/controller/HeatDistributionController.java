package cn.plou.web.heatManage.dataAnalysis.controller;

import cn.plou.web.charge.chargeconfig.vo.HeatInfoVO;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.heatManage.dataAnalysis.service.HeatDistributionService;
import cn.plou.web.heatManage.dataAnalysis.vo.HeatDistributionData;
import cn.plou.web.heatManage.history.service.HistoryDataService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static cn.plou.web.common.config.common.Cond.getCond;
@RequestMapping("${heatManagePath}/history")
public class HeatDistributionController {
    @Autowired
    private HeatDistributionService heatDistributionService;

    @ApiOperation(value = "热力分布")
    @RequestMapping("getHeatDistribution")
    public Root getHeatInfoList(@RequestParam(value = "selectId", required = false) String selectId,
                                @RequestParam(value = "selectType", required = false) String selectType,
                                @RequestParam(value = "selectDateType", required = false) String selectDateType,
                                @RequestParam(value = "heatParamType", required = false) String heatParamType,
                                @RequestParam(value = "startDate", required = false) String startDate,
                                @RequestParam(value = "endDate", required = false) String endDate) {
        Root root = new Root();
        try {
            List<HeatDistributionData> datas = heatDistributionService.getHeatDistributionData(selectId,selectType,selectDateType,heatParamType,startDate,endDate);
            if (datas.size() == 0) {
                root.setData(datas);
                return root;
            }
            return root;
        } catch (Exception ex) {
            ex.printStackTrace();
            root.setCode(1);
            root.setMsg(ex.getMessage());
        }
        return root;
    }

}





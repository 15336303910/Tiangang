package cn.plou.web.heatManage.dataAnalysis.controller;

import cn.plou.web.charge.chargeconfig.vo.HeatInfoVO;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.heatManage.dataAnalysis.domain.HouseTotalDataAnalysisHotDistributeDO;
import cn.plou.web.heatManage.dataAnalysis.service.HeatDistributionService;
import cn.plou.web.heatManage.dataAnalysis.service.HouseRunningDataAnalysisService;
import cn.plou.web.heatManage.dataAnalysis.vo.HeatDistributionData;
import cn.plou.web.heatManage.history.service.HistoryDataService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.plou.web.common.config.common.Cond.getCond;

@RestController
@RequestMapping("${heatManagePath}/history")
public class HeatDistributionController {
    @Autowired
    private HeatDistributionService heatDistributionService;

    @Autowired
    private HouseRunningDataAnalysisService houseRunningDataAnalysisService;

//    @ApiOperation(value = "热力分布")
//    @RequestMapping("getHeatDistribution")
//    public Root getHeatInfoList(@RequestParam(value = "selectId", required = false) String selectId,
//                                @RequestParam(value = "selectType", required = false) String selectType,
//                                @RequestParam(value = "selectDateType", required = false) String selectDateType,
//                                @RequestParam(value = "heatParamType", required = false) String heatParamType,
//                                @RequestParam(value = "startDate", required = false) String startDate,
//                                @RequestParam(value = "endDate", required = false) String endDate) {
//        Root root = new Root();
//        try {
//            List<HeatDistributionData> datas = heatDistributionService.getHeatDistributionData(selectId,selectType,selectDateType,heatParamType,startDate,endDate);
//            if (datas.size() == 0) {
//                root.setData(datas);
//                return root;
//            }
//            return root;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            root.setCode(1);
//            root.setMsg(ex.getMessage());
//        }
//        return root;
//    }


    /**
     * 用热管理-数据分析-热点分布
     *
     * @param params
     * @return
     */

    @RequestMapping("/getHouseTotalDataAnalysisHotDistribute")
    public Root getHouseTotalDataAnalysisHotDistribute(@RequestParam Map<String, Object> params) {
        Root root = new Root();
        JSONObject jsonObject = new JSONObject();
        String type = params.get("type").toString();
        String id = params.get("id").toString();
        String readTime = params.get("readTime").toString();
        String standard = params.get("standard").toString();
        switch (type) {
            case "company":
                break;
            case "station":
                break;
            case "commuity":
                break;
            default:
                jsonObject.put("houseTotalDataAnalysisHotDistribute", null);
                root.setCode(500);
                root.setMsg("type param incorrect");
                return root;
        }


        switch (standard) {
            case "temperature"://室温指标
                break;
            case "heating"://耗热指标
                break;
            case "flowing"://流量指标
                break;
            case "devError"://设备故障
                break;
            case "communicError"://通讯故障
                break;
            default:
                jsonObject.put("houseTotalDataAnalysisHotDistribute", null);
                root.setCode(500);
                root.setMsg("standard param incorrect");
                return root;
        }

        if(readTime == null || readTime.equals("")){
            jsonObject.put("houseTotalDataAnalysisHotDistribute", null);
            root.setCode(500);
            root.setMsg("readTime param incorrect");
            return root;
        }
        String[] splitReadTime = readTime.split(",");
        if(splitReadTime==null || splitReadTime.length > 2){
            jsonObject.put("houseTotalDataAnalysisHotDistribute", null);
            root.setCode(500);
            root.setMsg("readTime format incorrect");
            return root;
        }

        String startTime = splitReadTime[0];
        String endTime = splitReadTime[1];



        Map<String, Object> paramsEx = new HashMap<String, Object>();

        paramsEx.put("type", type);
        paramsEx.put("consumerId", id);
        paramsEx.put("startTime", startTime);
        paramsEx.put("endTime", endTime);
        paramsEx.put("standard", standard);

        List<HouseTotalDataAnalysisHotDistributeDO> houseTotalDataAnalysisHotDistributes = houseRunningDataAnalysisService.getHouseTotalDataAnalysisHotDistribute(paramsEx);
        root.setData(houseTotalDataAnalysisHotDistributes);
        return root;
    }

}





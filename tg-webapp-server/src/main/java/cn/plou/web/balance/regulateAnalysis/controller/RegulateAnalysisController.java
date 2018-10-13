package cn.plou.web.balance.regulateAnalysis.controller;

import cn.plou.web.balance.dataAnalysis.service.BalanceValveDataService;
import cn.plou.web.balance.dataAnalysis.vo.BalanceValveDataInfo;
import cn.plou.web.balance.dataAnalysis.vo.BalanceValveDataSelectInfo;
import cn.plou.web.common.config.common.Root;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@RestController
@EnableSwagger2
//@RequestMapping("${balancePath}/regulate")
@RequestMapping("balance/regulateAnalysis")
@Api(description = "控制分析")
public class RegulateAnalysisController {
    @Autowired
    BalanceValveDataService balanceValveDataService;
    @ApiOperation(value = "根据控制模式获取平衡阀历史数据")
    @PostMapping("/getHistoryDataListByControlMode")
    //@RequiresPermissions("")
    public List<BalanceValveDataInfo> getBalanceDataListByControlMode(@RequestBody BalanceValveDataSelectInfo balanceValveDataSelectInfo){
        Root root = new Root();
        //root.setData(balanceValveDataService.getBalanceDataList(balanceValveDataSelectInfo.getStartDate(),balanceValveDataSelectInfo.getEndDate(),balanceValveDataSelectInfo.getConsumerId()));
        List<BalanceValveDataInfo> balanceValveDataInfos = new ArrayList<>();
        root.setData(balanceValveDataInfos);
        return balanceValveDataInfos;
    }
}

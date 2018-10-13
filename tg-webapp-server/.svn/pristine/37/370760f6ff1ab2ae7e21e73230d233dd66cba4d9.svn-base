package cn.plou.web.balance.dataAnalysis.controller;

import cn.plou.web.balance.dataAnalysis.service.BalanceValveDataService;
import cn.plou.web.balance.dataAnalysis.vo.BalanceValveDataInfo;
import cn.plou.web.balance.dataAnalysis.vo.BalanceValveDataSelectInfo;
import cn.plou.web.balance.distribution.vo.BalanceValveDataNowSelectInfo;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.baseMessage.build.vo.BuildSelectInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@EnableSwagger2
//@RequestMapping("${balancePath}/regulate")
@RequestMapping("balance/dataAnalysis")
@Api(description = "数据分析")
public class DataAnalysisController {
    @Autowired
    BalanceValveDataService balanceValveDataService;
    @ApiOperation(value = "获取平衡阀时间段内运行数据")
    @PostMapping("/getHistoryDataList")
    //@RequiresPermissions("")
    public List<BalanceValveDataInfo> getBalanceDataList(@RequestBody BalanceValveDataSelectInfo balanceValveDataSelectInfo){
        Root root = new Root();
        //root.setData(balanceValveDataService.getBalanceDataList(balanceValveDataSelectInfo.getStartDate(),balanceValveDataSelectInfo.getEndDate(),balanceValveDataSelectInfo.getConsumerId()));
        List<BalanceValveDataInfo> balanceValveDataInfos = new ArrayList<>();
        root.setData(balanceValveDataInfos);
        return balanceValveDataInfos;
    }

    @ApiOperation(value = "获取平衡阀时间段内运行平均数据")
    @PostMapping("/getAvgHistoryData")
    //@RequiresPermissions("")
    public List<BalanceValveDataInfo> getAvgHistoryData(@RequestBody BalanceValveDataSelectInfo balanceValveDataSelectInfo){
        Root root = new Root();
        //root.setData(balanceValveDataService.getAvgHistoryData(balanceValveDataSelectInfo.getStartDate(),balanceValveDataSelectInfo.getEndDate(),balanceValveDataSelectInfo.getConsumerId()));
        List<BalanceValveDataInfo> balanceValveDataInfos = new ArrayList<>();
        return balanceValveDataInfos;
    }

    @ApiOperation(value = "批量导出平衡阀历史数据")
    @PostMapping("/exportExcelData")
    public String exportExcelData(@RequestBody BalanceValveDataSelectInfo balanceValveDataSelectInfo, ServletRequest request) {
        Root root=new Root();
        root.setData(balanceValveDataService.
                exportExcelData(balanceValveDataSelectInfo.getStartDate(),balanceValveDataSelectInfo.getEndDate(),balanceValveDataSelectInfo.getConsumerId(),request));
        //return root;
        return "";
    }
    @ApiOperation(value = "批量导出平衡阀历史数据均值")
    @PostMapping("/exportExcelAvgData")
    public String exportExcelAvgData(@RequestBody BalanceValveDataSelectInfo balanceValveDataSelectInfo, ServletRequest request) {
        Root root=new Root();
        root.setData(balanceValveDataService.
                exportExcelAvgData(balanceValveDataSelectInfo.getStartDate(),balanceValveDataSelectInfo.getEndDate(),balanceValveDataSelectInfo.getConsumerId(),request));
        return "";
    }

    @ApiOperation(value = "批量导出平衡阀压差控制数据")
    @PostMapping("/exportExcelPressureData")
    public String exportExcelPressureData(@RequestBody BalanceValveDataSelectInfo balanceValveDataSelectInfo, ServletRequest request) {
        Root root=new Root();
        root.setData(balanceValveDataService.
                exportExcelPressureData(balanceValveDataSelectInfo.getStartDate(),balanceValveDataSelectInfo.getEndDate(),balanceValveDataSelectInfo.getConsumerId(),request));
        return "";
    }

    @ApiOperation(value = "批量导出平衡阀温度控制数据")
    @PostMapping("/exportExcelTemperatureData")
    public String exportExcelTemperatureData(@RequestBody BalanceValveDataSelectInfo balanceValveDataSelectInfo, ServletRequest request) {
        Root root=new Root();
        root.setData(balanceValveDataService.
                exportExcelTemperatureData(balanceValveDataSelectInfo.getStartDate(),balanceValveDataSelectInfo.getEndDate(),balanceValveDataSelectInfo.getConsumerId(),request));
        return "";
    }

    @ApiOperation(value = "批量导出平衡阀流量控制数据")
    @PostMapping("/exportExcelFlowData")
    public String exportExcelFlowData(@RequestBody BalanceValveDataSelectInfo balanceValveDataSelectInfo, ServletRequest request) {
        Root root=new Root();
        root.setData(balanceValveDataService.
                exportExcelFlowData(balanceValveDataSelectInfo.getStartDate(),balanceValveDataSelectInfo.getEndDate(),balanceValveDataSelectInfo.getConsumerId(),request));
        return "";
    }

    @ApiOperation(value = "根据时间获取平衡阀历史数据")
    @PostMapping("/getHistoryDataByDate")
    public List<BalanceValveDataInfo> getHistoryDataByDate(@RequestBody BalanceValveDataSelectInfo balanceValveDataSelectInfo){
        Root root = new Root();
        root.setData(balanceValveDataService.getHistoryDataByDate(balanceValveDataSelectInfo.getStartDate(),balanceValveDataSelectInfo.getEndDate(),balanceValveDataSelectInfo.getConsumerId(),balanceValveDataSelectInfo.getSystemReadTime()));
        List<BalanceValveDataInfo> balanceValveDataInfos = new ArrayList<>();
        //return root;
        return balanceValveDataInfos;
    }
}

package cn.plou.web.balance.trendCurve.controller;

import cn.plou.web.balance.dataAnalysis.service.BalanceValveDataService;
import cn.plou.web.balance.dataAnalysis.vo.BalanceValveDataInfo;
import cn.plou.web.balance.dataAnalysis.vo.BalanceValveDataSelectInfo;
import cn.plou.web.balance.trendCurve.service.TrendCurveService;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.permission.userPageHistory.entity.UserPageHistory;
import cn.plou.web.system.permission.userPageHistory.service.UserPageHistoryService;
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
@RequestMapping("balance/trendCurve")
@Api(description = "趋势曲线")
public class TrendCurveController {
    @Autowired
    TrendCurveService trendCurveService;
    @Autowired
    UserPageHistoryService userPageHistoryService;
    @ApiOperation(value = "获取趋势曲线上数据值")
    @PostMapping("/getTrendCurveDatas")
    //@RequiresPermissions("")
    public Root getTrendCurveDatas(@RequestBody BalanceValveDataSelectInfo balanceValveDataSelectInfo){
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("balance-trendCurve");
        userPageHistory.setAct("getTrendCurveDatas");
        userPageHistoryService.addUserPageHistory(userPageHistory);
        return trendCurveService.getTrendCurveDataService(balanceValveDataSelectInfo.getUnitId(),balanceValveDataSelectInfo.getBuildingNo(),balanceValveDataSelectInfo.getStartDate(),balanceValveDataSelectInfo.getEndDate());
    }
}

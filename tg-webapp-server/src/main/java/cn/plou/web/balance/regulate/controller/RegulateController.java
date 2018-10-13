package cn.plou.web.balance.regulate.controller;

import cn.plou.web.balance.distribution.entity.BalanceValveDataNow;
import cn.plou.web.balance.distribution.vo.BalanceValveDataNowInfo;
import cn.plou.web.balance.distribution.vo.BalanceValveDataNowSelectInfo;
import cn.plou.web.balance.distribution.vo.SearchCondition;
import cn.plou.web.common.config.common.Root;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@RestController
@EnableSwagger2
//@RequestMapping("${balancePath}/regulate")
@RequestMapping("balance/regulate")
@Api(description = "平衡控制")
public class RegulateController {
    @ApiOperation(value = "获取平衡阀值列表")
    @PostMapping("/getBalanceDataList")
    //@RequiresPermissions("")
    public List<BalanceValveDataNowInfo> getBalanceDataList(@RequestBody BalanceValveDataNowSelectInfo balanceValveDataNowSelectInfo){
        Root root = new Root();
        List<BalanceValveDataNowInfo> balanceValveDataNowInfos = new ArrayList<>();
        //return root;
        return balanceValveDataNowInfos;
    }

    @ApiOperation(value = "控制操作")
    @PostMapping("/operation")
    //@RequiresPermissions("")
    public Root regulateOperation(@RequestBody BalanceValveDataNow balanceValveDataNow){
        Root root = new Root();
        return root;
    }

    @ApiOperation(value="获取阀的偏差值")
    @GetMapping("/deviation")
    public SearchCondition getDeviationsByBalanceValve(@RequestParam("meterId")String meterId){
        return null;
    }
}

package cn.plou.web.charge.chargeconfig.controller;

import cn.plou.web.charge.chargeconfig.entity.PriceAccuracyInfo;
import cn.plou.web.charge.chargeconfig.service.PriceAccuracyInfoService;
import cn.plou.web.common.exception.BindingResultHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author yinxiaochen
 * 2018/8/17 14:27
 */

@RequestMapping("${chargePath}/priceaccuracyinfo")
@RestController
@EnableSwagger2
@Api(description = "金额精度管理")
public class PriceAccuracyInfoController {


    @Resource
    private PriceAccuracyInfoService   priceAccuracyInfoService;


    @ApiOperation(value = "保存或者更新某一条金额精度设置")
    @RequestMapping("/addOrUpdatePriceAccuracyInfo")
    public Boolean addOrUpdatePriceAccuracyInfo(@Valid PriceAccuracyInfo    priceAccuracyInfo, BindingResult  bindingResult) {
        BindingResultHandler.handle(bindingResult);
        if (priceAccuracyInfo.getPrimaryId() == null || priceAccuracyInfo.getPrimaryId().equals("")) {
            return priceAccuracyInfoService.add(priceAccuracyInfo)==1;
        }
        int  result=priceAccuracyInfoService.update(priceAccuracyInfo);
        return result==1||result==0;
    }


    @ApiOperation(value = "获取某条金额精度设置详细信息")
    @RequestMapping("/getPriceAccuracyInfo")
    public Object getPriceAccuracyInfo(PriceAccuracyInfo    priceAccuracyInfo) {
        return priceAccuracyInfoService.findPriceAccuracyInfo(priceAccuracyInfo);
    }




    @ApiOperation(value = "测试事物")
    @Transactional
    @RequestMapping("/testTransactional")
    public Object testTransactional(PriceAccuracyInfo    priceAccuracyInfo) {
        priceAccuracyInfo.setPrimaryId("1111111");
        priceAccuracyInfo.setCompanyId("aaaaaaa");
         priceAccuracyInfoService.add(priceAccuracyInfo);

        priceAccuracyInfo.setPrimaryId("222222");
        priceAccuracyInfo.setCompanyId("bbbbbbb");
         priceAccuracyInfoService.add(priceAccuracyInfo);


        return  null;
    }


}

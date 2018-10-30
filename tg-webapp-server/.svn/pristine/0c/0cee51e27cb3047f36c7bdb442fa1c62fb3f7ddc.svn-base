package cn.plou.web.charge.chargeconfig.controller;


import cn.plou.web.charge.chargeconfig.service.PriceDefineService;
import cn.plou.web.charge.chargeconfig.service.UserYearHeatService;
import cn.plou.web.charge.chargeconfig.vo.*;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.baseMessage.build.service.BuildService;
import cn.plou.web.system.baseMessage.commuity.service.CommuityService;
import cn.plou.web.system.baseMessage.house.service.HouseService;
import cn.plou.web.system.baseMessage.station.service.StationService;
import cn.plou.web.system.baseMessage.unit.service.UnitService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("${chargePath}/home")
public class ChargeHomePageController {

    @Resource
    private UserYearHeatService userYearHeatService;
    @Resource
    private PriceDefineService priceDefineService;

    @Resource
    private CommuityService commuityService;
    @Resource
    private UnitService unitService;
    @Resource
    private BuildService buildService;


    @Resource
    private HouseService houseService;

    @Resource
    private StationService stationService;



    @ApiOperation(value = "收费首页 左上 图表")
    @RequestMapping("/getHomePageTopLeftChart")
    public Root getHomePageTopLeftChart(@RequestParam String companyId){
        Root root = new Root();
        List<HomePageTopLeftVO> HomePageTopLeftVOs = userYearHeatService.getHomePageTopLeftChart(companyId);
        root.setData(HomePageTopLeftVOs);
        root.setCode(0);
        return root;
    }

    @ApiOperation(value = "收费首页 中上 图表")
    @RequestMapping("/getHomePageTopMiddleChart")
    public Root getHomePageTopMiddleChart(@RequestParam String companyId){
        Root root = new Root();
        HomePageTopMiddleVO HomePageTopMiddleVOs = userYearHeatService.getHomePageTopMiddleChart(companyId);
        root.setData(HomePageTopMiddleVOs);
        root.setCode(0);
        return root;
    }


    @ApiOperation(value = "收费首页 右上 图表")
    @RequestMapping("/getHomePageTopRightChart")
    public Root getHomePageTopRightChart(@RequestParam String companyId){
        Root root = new Root();
        List<HomePageTopRightVO> HomePageTopRightVOs = userYearHeatService.getHomePageTopRightChart(companyId);
        root.setData(HomePageTopRightVOs);
        root.setCode(0);
        return root;
    }



    @ApiOperation(value = "收费首页 中左 图表")
    @RequestMapping("/getHomePageMiddleLeftChart")
    public Root getHomePageMiddleLeftChart(@RequestParam String companyId,@RequestParam String startAnnual,@RequestParam String endAnnual){
        Root root = new Root();
        List<HomePageMiddleLeftVO> HomePageMiddleLeftVOs = userYearHeatService.getHomePageMiddleLeftChart(companyId,startAnnual,endAnnual);
        root.setData(HomePageMiddleLeftVOs);
        root.setCode(0);
        return root;
    }

    @ApiOperation(value = "收费首页 中中 图表")
    @RequestMapping("/getHomePageMiddleMiddleChart")
    public Root getHomePageMiddleMiddleChart(@RequestParam String companyId){
        Root root = new Root();
        List<HomePageMiddleMiddleVO> HomePageMiddleMiddleVOs = userYearHeatService.getHomePageMiddleMiddleChart(companyId);
        root.setData(HomePageMiddleMiddleVOs);
        root.setCode(0);
        return root;
    }

    @ApiOperation(value = "收费首页 中右  图表")
    @RequestMapping("/getHomePageMiddleRightChart")
    public Root getHomePageMiddleRightChart(@RequestParam String companyId){
        Root root = new Root();
        List<HomePageMiddleRightVO> HomePageMiddleRightInnerVOs = userYearHeatService.getHomePageMiddleRightChart(companyId);
        root.setData(HomePageMiddleRightInnerVOs);
        root.setCode(0);
        return root;
    }



    @ApiOperation(value = "收费首页 下左 图表")
    @RequestMapping("/getHomePageBottomLeftChart")
    public Root getHomePageBottomLeftChart(@RequestParam String companyId,@RequestParam String startAnnual,@RequestParam String endAnnual){
        Root root = new Root();
        List<HomePageBottomLeftVO> HomePageBottomLeftVOs = userYearHeatService.getHomePageBottomLeftChart(companyId,startAnnual,endAnnual);
        root.setData(HomePageBottomLeftVOs);
        root.setCode(0);
        return root;
    }


    @ApiOperation(value = "收费首页 下中 图表")
    @RequestMapping("/getHomePageBottomMiddleChart")
    public Root getHomePageBottomMiddleChart(@RequestParam String companyId){
        Root root = new Root();
        List<HomePageBottomMiddleVO> HomePageBottomMiddleVOs = userYearHeatService.getHomePageBottomMiddleChart(companyId);
        root.setData(HomePageBottomMiddleVOs);
        root.setCode(0);
        return root;
    }


    @ApiOperation(value = "收费首页 下右 图表")
    @RequestMapping("/getHomePageBottomRightChart")
    public Root getHomePageBottomRightChart(@RequestParam String companyId,@RequestParam Date startTime,@RequestParam Date endTime){
        Root root = new Root();
        List<HomePageBottomRightVO> HomePageBottomRightVOs = userYearHeatService.getHomePageBottomRightChart(companyId,startTime,endTime);
        root.setData(HomePageBottomRightVOs);
        root.setCode(0);
        return root;
    }


}

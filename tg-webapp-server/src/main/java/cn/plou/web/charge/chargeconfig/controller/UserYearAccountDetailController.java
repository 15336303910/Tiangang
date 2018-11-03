package cn.plou.web.charge.chargeconfig.controller;

import cn.plou.web.charge.chargeconfig.dto.BusinessAnalysisSearchDTO;
import cn.plou.web.charge.chargeconfig.dto.HeatingSummarySearchDTO;
import cn.plou.web.charge.chargeconfig.service.PriceDefineService;
import cn.plou.web.charge.chargeconfig.service.UserYearAccountDetailService;
import cn.plou.web.charge.chargeconfig.service.UserYearHeatService;
import cn.plou.web.charge.chargeconfig.vo.*;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.exception.BindingResultHandler;
import cn.plou.web.common.utils.CommonServiceImp;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static cn.plou.web.common.config.common.Cond.getCond;

/**
 * @author yinxiaochen
 * 2018/9/6 9:26
 */
@RestController
@RequestMapping("${chargePath}/userYearAccountDetail")
public class UserYearAccountDetailController {

    @Autowired
    private UserYearHeatService userYearHeatService;


    @Resource
    private UserYearAccountDetailService userYearAccountDetailService;

    @Resource
    private PriceDefineService priceDefineService;

    @Autowired
    private CommonServiceImp commonServiceImp;

    /**
     * @param id 右侧树公司或者站的id
     * @return 统计分析 —— 统计分析-收费明细
     */
    @GetMapping("/chargeHistory")
    public Object chargeQuery(@RequestParam("id") String id) {


        return null;

    }

    /**
     * @param id
     * @return统计分析 —— 统计分析-收费汇总
     */
    @GetMapping("/chargeTotal")
    public Object chargeTotal(@RequestParam("id") String id) {


        return null;

    }


    /**
     * @Description: 统计分析-营业分析
     * @Param: [businessAnalysisSearchDTO, bindingResult]
     * @Return: java.util.List<cn.plou.web.charge.chargeconfig.vo.BusinessAnalysisListVO>
     * @Author: youbc
     * @Date: 2018/10/10 9上午10
     */
    @PostMapping("business-analysis")
    public BusinessAnalysisVO businessAnalysis(@Valid BusinessAnalysisSearchDTO businessAnalysisSearchDTO, BindingResult bindingResult) {
        BindingResultHandler.handle(bindingResult);
        return userYearAccountDetailService.businessAnalysis(businessAnalysisSearchDTO);
    }

    /**
     * @Description: 统计分析-营业分析导出 Excel
     * @Param: [request, businessAnalysisSearchDTO, bindingResult]
     * @Return: cn.plou.web.common.config.common.Root
     * @Author: youbc
     * @Date: 18/10/25 14下午07
     */
    @PostMapping("export-business-analysis")
    public Root exportBusinessAnalysis(ServletRequest request, @Valid BusinessAnalysisSearchDTO businessAnalysisSearchDTO, BindingResult bindingResult) {
        BindingResultHandler.handle(bindingResult);
        BusinessAnalysisVO vo = userYearAccountDetailService.businessAnalysis(businessAnalysisSearchDTO);
        Root root = new Root();
        root.setCode(0);
        root.setMsg("导出成功");
        root.setData(commonServiceImp.getHisDataByGridNameToExcel(request, "statistic", "channel", "统计分析-营业分析", vo.getList(), true));
        return root;
    }

    /**
     * @Description: 统计分析-供热汇总
     * @Param: [heatingSummarySearchDTO, bindingResult]
     * @Return: java.util.List<cn.plou.web.charge.chargeconfig.vo.BusinessAnalysisListVO>
     * @Author: youbc
     * @Date: 2018/10/10 11上午10
     */
    @PostMapping("heating-summary")
    public Root heatingSummary(@Valid HeatingSummarySearchDTO heatingSummarySearchDTO, BindingResult bindingResult) {
        BindingResultHandler.handle(bindingResult);
        HeatingSummaryVO vo = userYearHeatService.heatingSummary(heatingSummarySearchDTO);
        PageInfo<HeatingSummaryListVO> pageInfo = new PageInfo<>(vo.getList());

        Root root = new Root();
        root.setData(vo);
        root.setCond(getCond(heatingSummarySearchDTO.getPage(), heatingSummarySearchDTO.getPageSize(), (int) pageInfo.getTotal(), heatingSummarySearchDTO.getOrder(), heatingSummarySearchDTO.getSortby()));
        return root;
    }

    /**
     * @Description: 统计分析-供热汇总导出 Excel
     * @Param: [request, heatingSummarySearchDTO, bindingResult]
     * @Return: cn.plou.web.common.config.common.Root
     * @Author: youbc
     * @Date: 18/10/25 14下午10
     */
    @PostMapping("export-heating-summary")
    public Root exportHeatingSummary(ServletRequest request, @Valid HeatingSummarySearchDTO heatingSummarySearchDTO, BindingResult bindingResult) {
        BindingResultHandler.handle(bindingResult);
        HeatingSummaryVO vo = userYearHeatService.heatingSummary(heatingSummarySearchDTO);
        Root root = new Root();
        root.setCode(0);
        root.setMsg("导出成功");
        root.setData(commonServiceImp.getHisDataByGridNameToExcel(request, "statistic", "heating", "统计分析-供热汇总", vo.getList(), true));
        return root;
    }

    /**
     * @param standard 统计标准
     * @return统计分析 —— 统计分析-收费汇总
     */
    @ApiOperation(value = "统计分析 收费汇总 列表数据")
    @RequestMapping("/accountSummaryList")
    public Root chargeTotal(@RequestParam String companyId
            , @RequestParam(value = "stationId", required = false) String stationId
            , @RequestParam(value = "commuityId", required = false) String commuityId
            , @RequestParam(value = "buildingNo", required = false) String buildingNo
            , @RequestParam(value = "unitId", required = false) String unitId
            , @RequestParam(value = "consumerId", required = false) String consumerId
            , @RequestParam String currentAnnual
            , @RequestParam String standard
            , @RequestParam Date dateStart
            , @RequestParam Date dateEnd
            , @RequestParam(value = "order", required = false) String order
            , @RequestParam(value = "sortby", required = false) String sortby
            , @RequestParam Integer page
            , @RequestParam Integer pageSize) {


        if(currentAnnual == null || currentAnnual.isEmpty()){
            dateStart = dateEnd = null;
        }
        String annual = priceDefineService.findCurrentHeatAnnual(companyId);

        Root root = new Root();
        switch (standard) {
            case "consumer"://以用户为统计对象
                if (!StringUtils.hasText(companyId)
                        && !StringUtils.hasText(stationId)
                        && !StringUtils.hasText(commuityId)
                        && !StringUtils.hasText(buildingNo)
                        && !StringUtils.hasText(unitId)
                        && !StringUtils.hasText(consumerId)) {
                    root.setCode(500);
                    root.setMsg("companyId、stationId、commuityId、buildingNo、unitId和consumerId不能同时为空");
                    return root;
                }
                PageInfo<AccountSummaryListVO> accountSummaryByConsumer = userYearAccountDetailService.getAccountSummaryListByConsumer(companyId,
                        stationId,
                        commuityId,
                        buildingNo,
                        unitId,
                        consumerId,
                        annual,
                        currentAnnual,
                        dateStart,
                        dateEnd,
                        order,
                        sortby,
                        page,
                        pageSize);




                for (AccountSummaryListVO accountSummaryListVO : accountSummaryByConsumer.getList()) {
                    if (accountSummaryListVO.getAdvHeatCost() == null) {
                        accountSummaryListVO.setAdvHeatCost(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getSumAccount() == null) {
                        accountSummaryListVO.setSumAccount(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getOweAccount() == null) {
                        accountSummaryListVO.setOweAccount(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getAccountRate() == null) {
                        accountSummaryListVO.setAccountRate(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getSumReceivable() == null) {
                        accountSummaryListVO.setSumReceivable(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getMarginNow() == null) {
                        accountSummaryListVO.setMarginNow(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getBillAccount() == null) {
                        accountSummaryListVO.setBillAccount(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getNotBillAccount() == null) {
                        accountSummaryListVO.setNotBillAccount(BigDecimal.ZERO);
                    }

                    if (accountSummaryListVO.getAccountTimes() == null) {
                        accountSummaryListVO.setAccountTimes(0);
                    }
                    if (accountSummaryListVO.getBillTimes() == null) {
                        accountSummaryListVO.setBillTimes(0);
                    }
                    if (accountSummaryListVO.getNotBillTimes() == null) {
                        accountSummaryListVO.setNotBillTimes(0);
                    }
                }

                root.setData(accountSummaryByConsumer.getList());
                root.setCond(getCond(page, pageSize, (int) accountSummaryByConsumer.getTotal(),
                        order, sortby));
                return root;
            case "commuity"://以小区为统计对象
                if (!StringUtils.hasText(companyId) && !StringUtils.hasText(stationId) && !StringUtils.hasText(commuityId)) {
                    root.setCode(500);
                    root.setMsg("companyId、stationId和commuityId不能同时为空");
                    return root;
                }

                PageInfo<AccountSummaryListVO> accountSummaryByCommuity = userYearAccountDetailService.getAccountSummaryListByCommuity(companyId,
                        stationId,
                        commuityId,
                        buildingNo,
                        unitId,
                        consumerId,
                        annual,
                        currentAnnual,
                        dateStart,
                        dateEnd,
                        order,
                        sortby,
                        page,
                        pageSize);


                for (AccountSummaryListVO accountSummaryListVO : accountSummaryByCommuity.getList()) {
                    if (accountSummaryListVO.getAdvHeatCost() == null) {
                        accountSummaryListVO.setAdvHeatCost(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getSumAccount() == null) {
                        accountSummaryListVO.setSumAccount(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getOweAccount() == null) {
                        accountSummaryListVO.setOweAccount(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getAccountRate() == null) {
                        accountSummaryListVO.setAccountRate(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getSumReceivable() == null) {
                        accountSummaryListVO.setSumReceivable(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getMarginNow() == null) {
                        accountSummaryListVO.setMarginNow(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getBillAccount() == null) {
                        accountSummaryListVO.setBillAccount(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getNotBillAccount() == null) {
                        accountSummaryListVO.setNotBillAccount(BigDecimal.ZERO);
                    }

                    if (accountSummaryListVO.getAccountTimes() == null) {
                        accountSummaryListVO.setAccountTimes(0);
                    }
                    if (accountSummaryListVO.getBillTimes() == null) {
                        accountSummaryListVO.setBillTimes(0);
                    }
                    if (accountSummaryListVO.getNotBillTimes() == null) {
                        accountSummaryListVO.setNotBillTimes(0);
                    }
                }
                root.setData(accountSummaryByCommuity.getList());
                root.setCond(getCond(page, pageSize, (int) accountSummaryByCommuity.getTotal(),
                        order, sortby));
                return root;

            case "building"://以楼房为统计对象
                if (!StringUtils.hasText(companyId) && !StringUtils.hasText(stationId) && !StringUtils.hasText(commuityId) && !StringUtils.hasText(buildingNo)) {
                    root.setCode(500);
                    root.setMsg("companyId、stationId、commuityId和buildingNo不能同时为空");
                    return root;
                }

                PageInfo<AccountSummaryListVO> accountSummaryByBuilding = userYearAccountDetailService.getAccountSummaryListByBuilding(companyId,
                        stationId,
                        commuityId,
                        buildingNo,
                        unitId,
                        consumerId,
                        annual,
                        currentAnnual,
                        dateStart,
                        dateEnd,
                        order,
                        sortby,
                        page,
                        pageSize);


                for (AccountSummaryListVO accountSummaryListVO : accountSummaryByBuilding.getList()) {
                    if (accountSummaryListVO.getAdvHeatCost() == null) {
                        accountSummaryListVO.setAdvHeatCost(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getSumAccount() == null) {
                        accountSummaryListVO.setSumAccount(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getOweAccount() == null) {
                        accountSummaryListVO.setOweAccount(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getAccountRate() == null) {
                        accountSummaryListVO.setAccountRate(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getSumReceivable() == null) {
                        accountSummaryListVO.setSumReceivable(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getMarginNow() == null) {
                        accountSummaryListVO.setMarginNow(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getBillAccount() == null) {
                        accountSummaryListVO.setBillAccount(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getNotBillAccount() == null) {
                        accountSummaryListVO.setNotBillAccount(BigDecimal.ZERO);
                    }

                    if (accountSummaryListVO.getAccountTimes() == null) {
                        accountSummaryListVO.setAccountTimes(0);
                    }
                    if (accountSummaryListVO.getBillTimes() == null) {
                        accountSummaryListVO.setBillTimes(0);
                    }
                    if (accountSummaryListVO.getNotBillTimes() == null) {
                        accountSummaryListVO.setNotBillTimes(0);
                    }
                }
                root.setData(accountSummaryByBuilding.getList());
                root.setCond(getCond(page, pageSize, (int) accountSummaryByBuilding.getTotal(),
                        order, sortby));
                return root;


            case "station"://以换热站为统计对象
                if (!StringUtils.hasText(companyId) && !StringUtils.hasText(stationId)) {
                    root.setCode(500);
                    root.setMsg("companyId和stationId不能同时为空");
                    return root;
                }
                PageInfo<AccountSummaryListVO> accountSummaryByStation = userYearAccountDetailService.getAccountSummaryListByStation(companyId,
                        stationId,
                        commuityId,
                        buildingNo,
                        unitId,
                        consumerId,
                        annual,
                        currentAnnual,
                        dateStart,
                        dateEnd,
                        order,
                        sortby,
                        page,
                        pageSize);


                for (AccountSummaryListVO accountSummaryListVO : accountSummaryByStation.getList()) {
                    if (accountSummaryListVO.getAdvHeatCost() == null) {
                        accountSummaryListVO.setAdvHeatCost(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getSumAccount() == null) {
                        accountSummaryListVO.setSumAccount(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getOweAccount() == null) {
                        accountSummaryListVO.setOweAccount(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getAccountRate() == null) {
                        accountSummaryListVO.setAccountRate(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getSumReceivable() == null) {
                        accountSummaryListVO.setSumReceivable(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getMarginNow() == null) {
                        accountSummaryListVO.setMarginNow(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getBillAccount() == null) {
                        accountSummaryListVO.setBillAccount(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getNotBillAccount() == null) {
                        accountSummaryListVO.setNotBillAccount(BigDecimal.ZERO);
                    }

                    if (accountSummaryListVO.getAccountTimes() == null) {
                        accountSummaryListVO.setAccountTimes(0);
                    }
                    if (accountSummaryListVO.getBillTimes() == null) {
                        accountSummaryListVO.setBillTimes(0);
                    }
                    if (accountSummaryListVO.getNotBillTimes() == null) {
                        accountSummaryListVO.setNotBillTimes(0);
                    }
                }
                root.setData(accountSummaryByStation.getList());
                root.setCond(getCond(page, pageSize, (int) accountSummaryByStation.getTotal(),
                        order, sortby));
                return root;
            case "company"://以公司为统计对象
                if (!StringUtils.hasText(companyId)) {
                    root.setCode(500);
                    root.setMsg("companyId不能为空");
                    return root;
                }
                //Assert.hasText(companyId, "companyId不能为空");

                PageInfo<AccountSummaryListVO> accountSummaryByCompany = userYearAccountDetailService.getAccountSummaryListByCompany(companyId,
                        stationId,
                        commuityId,
                        buildingNo,
                        unitId,
                        consumerId,
                        annual,
                        currentAnnual,
                        dateStart,
                        dateEnd,
                        order,
                        sortby,
                        page,
                        pageSize);


                for (AccountSummaryListVO accountSummaryListVO : accountSummaryByCompany.getList()) {
                    if (accountSummaryListVO.getAdvHeatCost() == null) {
                        accountSummaryListVO.setAdvHeatCost(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getSumAccount() == null) {
                        accountSummaryListVO.setSumAccount(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getOweAccount() == null) {
                        accountSummaryListVO.setOweAccount(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getAccountRate() == null) {
                        accountSummaryListVO.setAccountRate(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getSumReceivable() == null) {
                        accountSummaryListVO.setSumReceivable(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getMarginNow() == null) {
                        accountSummaryListVO.setMarginNow(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getBillAccount() == null) {
                        accountSummaryListVO.setBillAccount(BigDecimal.ZERO);
                    }
                    if (accountSummaryListVO.getNotBillAccount() == null) {
                        accountSummaryListVO.setNotBillAccount(BigDecimal.ZERO);
                    }

                    if (accountSummaryListVO.getAccountTimes() == null) {
                        accountSummaryListVO.setAccountTimes(0);
                    }
                    if (accountSummaryListVO.getBillTimes() == null) {
                        accountSummaryListVO.setBillTimes(0);
                    }
                    if (accountSummaryListVO.getNotBillTimes() == null) {
                        accountSummaryListVO.setNotBillTimes(0);
                    }
                }
                root.setData(accountSummaryByCompany.getList());
                root.setCond(getCond(page, pageSize, (int) accountSummaryByCompany.getTotal(),
                        order, sortby));
                return root;
            default:
                root.setCode(500);
                root.setMsg("companyId不能为空");
                return root;
        }


    }


    /**
     * @param standard 统计标准
     * @return统计分析 —— 统计分析-收费汇总 导出Excel
     */
    @ApiOperation(value = "统计分析 收费汇总 列表数据 导出Excel")
    @RequestMapping("/accountSummaryListToExcel")
    public Root chargeTotalToExcel(ServletRequest request, @RequestParam(value = "companyId", required = false) String companyId
            , @RequestParam(value = "stationId", required = false) String stationId
            , @RequestParam(value = "commuityId", required = false) String commuityId
            , @RequestParam(value = "buildingNo", required = false) String buildingNo
            , @RequestParam(value = "unitId", required = false) String unitId
            , @RequestParam(value = "consumerId", required = false) String consumerId
            , @RequestParam String currentAnnual
            , @RequestParam String standard
            , @RequestParam Date dateStart
            , @RequestParam Date dateEnd
            , @RequestParam(value = "order", required = false) String order
            , @RequestParam(value = "sortby", required = false) String sortby) {

        //限制导出文件的大小，防止爆内存
        Root root1 = chargeTotal(companyId
                , stationId
                , commuityId
                , buildingNo
                , unitId
                , consumerId
                ,currentAnnual
                , standard
                , dateStart
                , dateEnd
                , order
                , sortby
                , 1
                , 50000);

        List<AccountSummaryListVO> AccountSummaryListVOs = (List<AccountSummaryListVO>) root1.getData();

        Root root = new Root();
        root.setCode(0);
        root.setMsg("导出成功");
        root.setData(commonServiceImp.getHisDataByGridNameToExcel(request, "statistic", "count", "统计分析-收费明细", AccountSummaryListVOs, true));
        return root;
    }


    /**
     * @param standard 统计标准
     * @return统计分析 —— 统计分析-收费汇总
     */
    @ApiOperation(value = "统计分析 收费汇总 数字数据")
    @RequestMapping("/accountSummaryData")
    public Root chargeTotal(@RequestParam(value = "companyId", required = false) String companyId
            , @RequestParam(value = "stationId", required = false) String stationId
            , @RequestParam(value = "commuityId", required = false) String commuityId
            , @RequestParam(value = "buildingNo", required = false) String buildingNo
            , @RequestParam(value = "unitId", required = false) String unitId
            , @RequestParam(value = "consumerId", required = false) String consumerId
            , @RequestParam String currentAnnual
            , @RequestParam String standard
            , @RequestParam Date dateStart
            , @RequestParam Date dateEnd) {

        if(currentAnnual == null || currentAnnual.isEmpty()){
            dateStart = dateEnd = null;
        }

        String annual = priceDefineService.findCurrentHeatAnnual(companyId);
        Root root = new Root();
        switch (standard) {
            case "consumer"://以用户为统计对象
                if (!StringUtils.hasText(companyId)
                        && !StringUtils.hasText(stationId)
                        && !StringUtils.hasText(commuityId)
                        && !StringUtils.hasText(buildingNo)
                        && !StringUtils.hasText(unitId)
                        && !StringUtils.hasText(consumerId)) {
                    root.setCode(500);
                    root.setMsg("companyId、stationId、commuityId、buildingNo、unitId和consumerId不能同时为空");
                    return root;
                }
                AccountSummaryListVO accountSummaryByConsumer = userYearAccountDetailService.getAccountSummaryDataByConsumer(companyId,
                        stationId,
                        commuityId,
                        buildingNo,
                        unitId,
                        consumerId,
                        annual,
                        currentAnnual,
                        dateStart,
                        dateEnd);
                root.setData(accountSummaryByConsumer);
                return root;
            case "commuity"://以小区为统计对象
                if (!StringUtils.hasText(companyId) && !StringUtils.hasText(stationId) && !StringUtils.hasText(commuityId)) {
                    root.setCode(500);
                    root.setMsg("companyId、stationId和commuityId不能同时为空");
                    return root;
                }

                AccountSummaryListVO accountSummaryByCommuity = userYearAccountDetailService.getAccountSummaryDataByCommuity(companyId,
                        stationId,
                        commuityId,
                        buildingNo,
                        unitId,
                        consumerId,
                        annual,
                        currentAnnual,
                        dateStart,
                        dateEnd);
                root.setData(accountSummaryByCommuity);
                return root;

            case "building"://以楼房为统计对象
                if (!StringUtils.hasText(companyId) && !StringUtils.hasText(stationId) && !StringUtils.hasText(commuityId) && !StringUtils.hasText(buildingNo)) {
                    root.setCode(500);
                    root.setMsg("companyId、stationId、commuityId和buildingNo不能同时为空");
                    return root;
                }

                AccountSummaryListVO accountSummaryByBuilding = userYearAccountDetailService.getAccountSummaryDataByBuilding(companyId,
                        stationId,
                        commuityId,
                        buildingNo,
                        unitId,
                        consumerId,
                        annual,
                        currentAnnual,
                        dateStart,
                        dateEnd);
                root.setData(accountSummaryByBuilding);
                return root;


            case "station"://以换热站为统计对象
                if (!StringUtils.hasText(companyId) && !StringUtils.hasText(stationId)) {
                    root.setCode(500);
                    root.setMsg("companyId和stationId不能同时为空");
                    return root;
                }
                AccountSummaryListVO accountSummaryByStation = userYearAccountDetailService.getAccountSummaryDataByStation(companyId,
                        stationId,
                        commuityId,
                        buildingNo,
                        unitId,
                        consumerId,
                        annual,
                        currentAnnual,
                        dateStart,
                        dateEnd);
                root.setData(accountSummaryByStation);
                return root;
            case "company"://以公司为统计对象
                if (!StringUtils.hasText(companyId)) {
                    root.setCode(500);
                    root.setMsg("companyId不能为空");
                    return root;
                }
                //Assert.hasText(companyId, "companyId不能为空");

                AccountSummaryListVO accountSummaryByCompany = userYearAccountDetailService.getAccountSummaryDataByCompany(companyId,
                        stationId,
                        commuityId,
                        buildingNo,
                        unitId,
                        consumerId,
                        annual,
                        currentAnnual,
                        dateStart,
                        dateEnd);
                root.setData(accountSummaryByCompany);
                return root;
            default:
                root.setCode(500);
                root.setMsg("companyId不能为空");
                return root;
        }


    }


    @ApiOperation(value = "统计分析 收费明细 列表数据和饼图数据")
    @RequestMapping("/accountDetailList")
    public Root accountDetailList(@RequestParam(value = "companyId", required = false) String companyId
            , @RequestParam(value = "stationId", required = false) String stationId
            , @RequestParam(value = "commuityId", required = false) String commuityId
            , @RequestParam(value = "buildingNo", required = false) String buildingNo
            , @RequestParam(value = "unitId", required = false) String unitId
            , @RequestParam(value = "consumerId", required = false) String consumerId
            , @RequestParam String currentAnnual
            , @RequestParam Date dateStart
            , @RequestParam Date dateEnd) {

        //currentAnnual 这个参数，先保留一下吧

        if(currentAnnual == null || currentAnnual.isEmpty()){
            dateStart = dateEnd = null;
        }

        String annual = priceDefineService.findCurrentHeatAnnual(companyId);
        Root root = new Root();
        if (!StringUtils.hasText(companyId)
                && !StringUtils.hasText(stationId)
                && !StringUtils.hasText(commuityId)
                && !StringUtils.hasText(buildingNo)
                && !StringUtils.hasText(unitId)
                && !StringUtils.hasText(consumerId)) {
            root.setCode(500);
            root.setMsg("companyId、stationId、commuityId、buildingNo、unitId和consumerId不能同时为空");
            return root;
        }
        List<AccountDetailListVO> accountDetailListByAccountType = userYearAccountDetailService.getAccountDetailListByAccountChannel(companyId,
                stationId,
                commuityId,
                buildingNo,
                unitId,
                consumerId,
                annual,
                dateStart,
                dateEnd);
        for (AccountDetailListVO accountDetailListVO : accountDetailListByAccountType) {
            if (accountDetailListVO.getAccountCostTotal() == null) {
                accountDetailListVO.setAccountCostTotal(BigDecimal.ZERO);
            }
            if (accountDetailListVO.getAccountTimesTotal() == null) {
                accountDetailListVO.setAccountTimesTotal(0);
            }
        }


        root.setData(accountDetailListByAccountType);
        root.setCode(0);
        return root;


    }


    @ApiOperation(value = "统计分析 收费明细 列表数据和饼图数据 点开每一列缴费通道的弹出层")
    @RequestMapping("/getAccountDetailListByAccountType")
    public Root getAccountDetailListByAccountType(@RequestParam(value = "companyId", required = false) String companyId
            , @RequestParam(value = "stationId", required = false) String stationId
            , @RequestParam(value = "commuityId", required = false) String commuityId
            , @RequestParam(value = "buildingNo", required = false) String buildingNo
            , @RequestParam(value = "unitId", required = false) String unitId
            , @RequestParam(value = "consumerId", required = false) String consumerId
            , @RequestParam String accountChannel
            , @RequestParam String currentAnnual
            , @RequestParam Date dateStart
            , @RequestParam Date dateEnd) {

        if(currentAnnual == null || currentAnnual.isEmpty()){
            dateStart = dateEnd = null;
        }

        String annual = priceDefineService.findCurrentHeatAnnual(companyId);
        Root root = new Root();
        if (!StringUtils.hasText(companyId)
                && !StringUtils.hasText(stationId)
                && !StringUtils.hasText(commuityId)
                && !StringUtils.hasText(buildingNo)
                && !StringUtils.hasText(unitId)
                && !StringUtils.hasText(consumerId)) {
            root.setCode(500);
            root.setMsg("companyId、stationId、commuityId、buildingNo、unitId和consumerId不能同时为空");
            return root;
        }
        List<AccountDetailListVO> accountDetailListByAccountType = userYearAccountDetailService.getAccountDetailListByAccountType(companyId,
                stationId,
                commuityId,
                buildingNo,
                unitId,
                consumerId,
                accountChannel,
                annual,
                dateStart,
                dateEnd);
        for (AccountDetailListVO accountDetailListVO : accountDetailListByAccountType) {
            if (accountDetailListVO.getAccountCostTotal() == null) {
                accountDetailListVO.setAccountCostTotal(BigDecimal.ZERO);
            }
            if (accountDetailListVO.getAccountTimesTotal() == null) {
                accountDetailListVO.setAccountTimesTotal(0);
            }
        }


        root.setData(accountDetailListByAccountType);
        root.setCode(0);
        return root;


    }


}

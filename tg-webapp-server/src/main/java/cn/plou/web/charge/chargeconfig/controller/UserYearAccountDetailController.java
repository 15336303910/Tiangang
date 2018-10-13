package cn.plou.web.charge.chargeconfig.controller;

import cn.plou.web.charge.chargeconfig.dto.BusinessAnalysisSearchDTO;
import cn.plou.web.charge.chargeconfig.dto.HeatingSummarySearchDTO;
import cn.plou.web.charge.chargeconfig.service.UserYearAccountDetailService;
import cn.plou.web.charge.chargeconfig.vo.AccountDetailListVO;
import cn.plou.web.charge.chargeconfig.vo.AccountSummaryListVO;
import cn.plou.web.charge.chargeconfig.vo.BusinessAnalysisListVO;
import cn.plou.web.charge.chargeconfig.vo.BusinessAnalysisVO;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.exception.BindingResultHandler;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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


    @Resource
    private UserYearAccountDetailService  userYearAccountDetailService;
    /**
     * @param id 右侧树公司或者站的id
     * @return 统计分析 —— 统计分析-收费明细
     */
    @GetMapping("/chargeHistory")
    public    Object  chargeQuery(@RequestParam("id") String  id){





        return  null;

    }

    /**
     * @param id
     * @return统计分析 —— 统计分析-收费汇总
     */
    @GetMapping("/chargeTotal")
    public    Object  chargeTotal(@RequestParam("id") String  id){




        return  null;

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
     * @Description: 统计分析-供热汇总 youbctodo 需求跟wl讨论，总面积？
     * @Param: [heatingSummarySearchDTO, bindingResult]
     * @Return: java.util.List<cn.plou.web.charge.chargeconfig.vo.BusinessAnalysisListVO>
     * @Author: youbc
     * @Date: 2018/10/10 11上午10
     */
    @PostMapping("heating-summary")
    public List<BusinessAnalysisListVO> heatingSummary(@Valid HeatingSummarySearchDTO heatingSummarySearchDTO, BindingResult bindingResult) {
        BindingResultHandler.handle(bindingResult);
        return userYearAccountDetailService.heatingSummary(heatingSummarySearchDTO);
    }

    /**
     * @param standard 统计标准，“以用户为统计对象”
     * @return统计分析 —— 统计分析-收费汇总
     */
    @ApiOperation(value = "统计分析 收费汇总 列表数据")
    @RequestMapping("/accountSummaryList")
    public Root chargeTotal(@RequestParam(value = "companyId", required = false) String companyId
            , @RequestParam(value = "stationId", required = false) String stationId
            , @RequestParam(value = "commuityId", required = false) String commuityId
            , @RequestParam(value = "buildingNo", required = false) String buildingNo
            , @RequestParam(value = "unitId", required = false) String unitId
            , @RequestParam(value = "consumerId", required = false) String consumerId
            , @RequestParam String standard
            ,@RequestParam Date dateStart
            ,@RequestParam  Date dateEnd
            , @RequestParam(value = "order", required = false) String order
            , @RequestParam(value = "sortby", required = false) String sortby
            , @RequestParam Integer page
            , @RequestParam Integer pageSize){

        Root root = new Root();
        switch (standard) {
            case "consumer"://以用户为统计对象
                if(!StringUtils.hasText(companyId)
                        && !StringUtils.hasText(stationId)
                        && !StringUtils.hasText(commuityId)
                        && !StringUtils.hasText(buildingNo)
                        && !StringUtils.hasText(unitId)
                        && !StringUtils.hasText(consumerId)){
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
                        dateStart,
                        dateEnd,
                        order,
                        sortby,
                        page,
                        pageSize);
                root.setData(accountSummaryByConsumer.getList());
                root.setCond(getCond(page, pageSize, (int) accountSummaryByConsumer.getTotal(),
                        order, sortby));
                return root;
            case "commuity"://以小区为统计对象
                if(!StringUtils.hasText(companyId) && !StringUtils.hasText(stationId) && !StringUtils.hasText(commuityId)){
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
                        dateStart,
                        dateEnd,
                        order,
                        sortby,
                        page,
                        pageSize);
                root.setData(accountSummaryByCommuity.getList());
                root.setCond(getCond(page, pageSize, (int) accountSummaryByCommuity.getTotal(),
                        order, sortby));
                return root;

            case "building"://以楼房为统计对象
                if(!StringUtils.hasText(companyId) && !StringUtils.hasText(stationId) && !StringUtils.hasText(commuityId) && !StringUtils.hasText(buildingNo)){
                    root.setCode(500);
                    root.setMsg("companyId、stationId、commuityId和buildingNo不能同时为空");
                    return root;
                }

                PageInfo<AccountSummaryListVO> accountSummaryByBuilding= userYearAccountDetailService.getAccountSummaryListByBuilding(companyId,
                        stationId,
                        commuityId,
                        buildingNo,
                        unitId,
                        consumerId,
                        dateStart,
                        dateEnd,
                        order,
                        sortby,
                        page,
                        pageSize);
                root.setData(accountSummaryByBuilding.getList());
                root.setCond(getCond(page, pageSize, (int) accountSummaryByBuilding.getTotal(),
                        order, sortby));
                return root;


            case "station"://以换热站为统计对象
                if(!StringUtils.hasText(companyId) && !StringUtils.hasText(stationId)){
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
                        dateStart,
                        dateEnd,
                        order,
                        sortby,
                        page,
                        pageSize);
                root.setData(accountSummaryByStation.getList());
                root.setCond(getCond(page, pageSize, (int) accountSummaryByStation.getTotal(),
                        order, sortby));
                return root;
            case "company"://以公司为统计对象
                if(!StringUtils.hasText(companyId)){
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
                        dateStart,
                        dateEnd,
                        order,
                        sortby,
                        page,
                        pageSize);
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
     * @param standard 统计标准，“以用户为统计对象”
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
            , @RequestParam String standard
            ,@RequestParam Date dateStart
            ,@RequestParam  Date dateEnd){

        Root root = new Root();
        switch (standard) {
            case "consumer"://以用户为统计对象
                if(!StringUtils.hasText(companyId)
                        && !StringUtils.hasText(stationId)
                        && !StringUtils.hasText(commuityId)
                        && !StringUtils.hasText(buildingNo)
                        && !StringUtils.hasText(unitId)
                        && !StringUtils.hasText(consumerId)){
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
                        dateStart,
                        dateEnd);
                root.setData(accountSummaryByConsumer);
                return root;
            case "commuity"://以小区为统计对象
                if(!StringUtils.hasText(companyId) && !StringUtils.hasText(stationId) && !StringUtils.hasText(commuityId)){
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
                        dateStart,
                        dateEnd);
                root.setData(accountSummaryByCommuity);
                return root;

            case "building"://以楼房为统计对象
                if(!StringUtils.hasText(companyId) && !StringUtils.hasText(stationId) && !StringUtils.hasText(commuityId) && !StringUtils.hasText(buildingNo)){
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
                        dateStart,
                        dateEnd);
                root.setData(accountSummaryByBuilding);
                return root;


            case "station"://以换热站为统计对象
                if(!StringUtils.hasText(companyId) && !StringUtils.hasText(stationId)){
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
                        dateStart,
                        dateEnd);
                root.setData(accountSummaryByStation);
                return root;
            case "company"://以公司为统计对象
                if(!StringUtils.hasText(companyId)){
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
            ,@RequestParam Date dateStart
            ,@RequestParam  Date dateEnd){

        Root root = new Root();
                if(!StringUtils.hasText(companyId)
                        && !StringUtils.hasText(stationId)
                        && !StringUtils.hasText(commuityId)
                        && !StringUtils.hasText(buildingNo)
                        && !StringUtils.hasText(unitId)
                        && !StringUtils.hasText(consumerId)){
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
                        dateStart,
                        dateEnd);
        for (AccountDetailListVO accountDetailListVO : accountDetailListByAccountType) {
            if(accountDetailListVO.getAccountCostTotal() == null){
                accountDetailListVO.setAccountCostTotal(BigDecimal.ZERO);
            }
            if(accountDetailListVO.getAccountTimesTotal() == null){
                accountDetailListVO.setAccountTimesTotal(0);
            }
        }


        root.setData(accountDetailListByAccountType);
                root.setCode(0);
                return root;


    }



}

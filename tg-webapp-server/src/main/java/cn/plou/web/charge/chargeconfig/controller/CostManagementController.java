package cn.plou.web.charge.chargeconfig.controller;

import cn.plou.web.charge.chargeconfig.entity.PriceDefine;
import cn.plou.web.charge.chargeconfig.entity.UserYearHeat;
import cn.plou.web.charge.chargeconfig.entity.UserYearReceivableDetail;
import cn.plou.web.charge.chargeconfig.service.PriceDefineService;
import cn.plou.web.charge.chargeconfig.service.UserYearAccountDetailService;
import cn.plou.web.charge.chargeconfig.service.UserYearHeatService;
import cn.plou.web.charge.chargeconfig.service.UserYearReceivableDetailService;
import cn.plou.web.charge.chargeconfig.vo.UserYearHeatDetailInfo;
import cn.plou.web.charge.chargeconfig.vo.UserYearReceivableDetailInfo;
import cn.plou.web.charge.heatingmanage.service.HouseHeatDetailService;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.IDWorker;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.baseMessage.house.entity.House;
import cn.plou.web.system.baseMessage.house.service.HouseService;
import cn.plou.web.system.baseMessage.house.vo.HouseInfo;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.TypeMstService;
import cn.plou.web.system.organizationMessage.staff.entity.Staff;
import cn.plou.web.system.organizationMessage.staff.service.StaffService;
import cn.plou.web.system.permission.userLogin.entity.UserLogin;
import cn.plou.web.system.permission.userLogin.service.UserLoginService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.util.Assert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import static cn.plou.web.common.config.common.Cond.getCond;

/**
 * @author panziqiang
 * 2018/8/17 14:27
 */

@RequestMapping("${chargePath}/costmanagement")
@RestController
@Api(description = "收费管理——费用管理")
@EnableSwagger2
public class CostManagementController {

    @Resource
    private TypeMstService typeMstService;
    @Resource
    private UserYearReceivableDetailService userYearReceivableDetailService;
    @Resource
    private UserYearAccountDetailService userYearAccountDetailService;

    @Resource
    private UserYearHeatService userYearHeatService;
    @Resource
    private HouseService houseService;
    @Resource
    private HouseHeatDetailService houseHeatDetailService;
    @Resource
    private UserLoginService userLoginService;
    @Resource
    private StaffService staffService;


    @Resource
    private PriceDefineService priceDefineService;

    @ApiOperation(value = "获取 费用管理 用户明细数据")
    @RequestMapping("/getUserYearReceivableDetail")
    public Root getUserYearReceivableDetail(@RequestParam String consumerId, @RequestParam(value = "annual", required = false) String annual, @RequestParam(value = "order", required = false) String order, @RequestParam(value = "sortby", required = false) String sortby, @RequestParam Integer page, @RequestParam Integer
            pageSize) {

        Root root = new Root();

        PageInfo<UserYearReceivableDetailInfo> userYearReceivableDetailsByConsumerId = userYearReceivableDetailService.getUserYearReceivableDetailsByConsumerId(consumerId, null, annual, order, sortby, page, pageSize);

        root.setData(userYearReceivableDetailsByConsumerId.getList());
        root.setCond(getCond(page, pageSize, (int) userYearReceivableDetailsByConsumerId.getTotal(),
                null, sortby));
        return root;

    }


    /**
     * 参数形式 [{consumerId:xxx,annual:xxx,order:xxxx,sortby:xxx,page:1,pageSize:10}]
     *
     * @return
     */

    @ApiOperation(value = "批量获取 用户历年费用明细")
    @RequestMapping("/getBatchUserYearReceivableDetail")
    public Object getBatchUserYearReceivableDetail(@RequestBody List<Map> paramList) {

        List<Root> rootList = new ArrayList<>();
        Root root = null;
        String consumerId;
        String annual;
        String order;
        String sortby;
        Integer page;
        Integer pageSize;
        for (Map map : paramList) {
            root = new Root();
            consumerId = (String) map.get("consumerId");
            annual = (String) map.get("annual");
            order = (String) map.get("order");
            sortby = (String) map.get("sortby");
            page = (Integer) map.get("page");
            pageSize = (Integer) map.get("pageSize");


            PageInfo<UserYearReceivableDetailInfo> userYearReceivableDetailsByConsumerId = userYearReceivableDetailService.getUserYearReceivableDetailsByConsumerId(consumerId, null, annual, order, sortby, page, pageSize);
            House house = houseService.getHouseById(consumerId);
            root.setMsg(house.getAddress() + (house.getName() == null ? "null" : house.getName()));
            root.setData(userYearReceivableDetailsByConsumerId.getList());
            root.setCond(getCond(page, pageSize, (int) userYearReceivableDetailsByConsumerId.getTotal(),
                    null, sortby));

            rootList.add(root);
        }
        return rootList;


    }

    @ApiOperation(value = "获取 暖费收取 用户采暖汇总 历年采暖汇总")
    @RequestMapping("/getUserYearHeatDetail")
    public Root getUserYearHeatDetail(@RequestParam String consumerId, @RequestParam(value = "annual", required = false) String annual, @RequestParam(value = "order", required = false) String order, @RequestParam(value = "sortby", required = false) String sortby, @RequestParam Integer page, @RequestParam Integer
            pageSize) {

        Root root = new Root();

        PageInfo<UserYearHeatDetailInfo> userYearHeatByUser = userYearHeatService.findUserYearHeatDetailInfo(consumerId, annual, order, sortby, page, pageSize);
        root.setData(userYearHeatByUser.getList());
        root.setCond(getCond(page, pageSize, (int) userYearHeatByUser.getTotal(),
                null, sortby));
        return root;

    }


    /**
     * 参数形式 [{consumerId:xxx,annual:xxx,order:xxxx,sortby:xxx,page:1,pageSize:10}]
     *
     * @return
     */
    @ApiOperation(value = "批量获取 用户采暖汇总")
    @RequestMapping("/getBatchUserYearHeatDetail")
    public Object getBatchUserYearHeatDetail(@RequestBody List<Map> paramList) {

        List<Root> rootList = new ArrayList<>();
        Root root = null;
        String consumerId;
        String annual;
        String order;
        String sortby;
        Integer page;
        Integer pageSize;
        for (Map map : paramList) {
            root = new Root();
            consumerId = (String) map.get("consumerId");
            annual = (String) map.get("annual");
            order = (String) map.get("order");
            sortby = (String) map.get("sortby");
            page = (Integer) map.get("page");
            pageSize = (Integer) map.get("pageSize");

            PageInfo<UserYearHeat> userYearHeatByUser = userYearHeatService.findByUser(consumerId, annual, order, sortby, page, pageSize);
            House house = houseService.getHouseById(consumerId);
            root.setMsg(house.getAddress() + (house.getName() == null ? "null" : house.getName()));
            root.setData(userYearHeatByUser.getList());
            root.setCond(getCond(page, pageSize, (int) userYearHeatByUser.getTotal(),
                    null, sortby));
            rootList.add(root);

        }
        return rootList;

    }

    @ApiOperation(value = "获取 暖费收取 用户信息(供第三方调用)")
    @RequestMapping("/getUserAnnualHeatInfoEx")
    public Root getHouseByChargeIdOrTelOrIdcard(@RequestParam Map<String, Object> params) throws Exception {
        Root root = new Root();
        String consumerId = params.get("consumerId") == null ? null : params.get("consumerId").toString();
        String annual = params.get("annual") == null ? null : params.get("annual").toString();

        if (consumerId == null || annual == null) {
            throw new Exception("consumerId或者annual参数缺失");
        }
        root =  getUserAnnualHeatInfo(consumerId,annual,true);
        if(root.getCode()!=0){
            throw new Exception(root.getMsg());
        }
        return root;
    }

    @ApiOperation(value = "获取 暖费收取 用户信息 ")
    @RequestMapping("/getUserAnnualHeatInfo")
    public Root getUserAnnualHeatInfo(@RequestParam String consumerId, @RequestParam String annual, @RequestParam(value = "displaySumReceivable", required = false) Boolean displaySumReceivable) throws Exception {
        Root root = new Root();
        if (consumerId.equals("")) {
            root.setCode(500);
            root.setMsg("用户id不能传空字符串");
            return root;
        }
        if (annual.equals("")) {
            root.setCode(500);
            root.setMsg("采暖年度不能传空字符串");
            return root;
        }
        House houseById = houseService.getHouseById(consumerId);
        if (houseById == null) {
            root.setCode(500);
            root.setMsg("获取不到用户信息");
            return root;
        }
        String unitPriceType = houseById.getUnitPriceType();
        if (unitPriceType == null || unitPriceType.equals("")) {
            root.setCode(500);
            root.setMsg("获取不到暖费信息");
            return root;
        }

        PriceDefine priceDefine = priceDefineService.selectByPrimaryKey(unitPriceType);
        if (priceDefine == null) {
            root.setCode(500);
            root.setMsg("获取不到暖费信息");
            return root;
        }


        Map<String, Object> map = new HashMap<>();
        map.put("companyId", houseById.getCompanyId());
        map.put("consumerId", houseById.getConsumerId());
        map.put("name", houseById.getName());
        map.put("address", houseById.getAddress());
        String heatingStatus = houseById.getHeatingStatus();
        String netStatus = houseById.getNetStatus();


        if (heatingStatus == null || heatingStatus.equals("")) {
            map.put("heatingStatus", "");//其实都应该这样处理
        } else {
            TypeMst typeMst = typeMstService.getTypeMstById(heatingStatus);
            if (typeMst != null) {

                map.put("heatingStatus", typeMst.getTypeName());
            } else {

                map.put("heatingStatus", "");
            }
        }

        if (netStatus == null || netStatus.equals("")) {
            map.put("netStatus", "");//其实都应该这样处理
        } else {
            TypeMst typeMst = typeMstService.getTypeMstById(netStatus);
            if (typeMst != null) {

                map.put("netStatus", typeMst.getTypeName());
            } else {

                map.put("netStatus", "");
            }
        }


        map.put("tel", houseById.getTel());
        map.put("idcard", houseById.getIdcard());
        map.put("userCompany", houseById.getUserCompany());

        if (houseById.getChargeType() == null) {
            map.put("chargeType", "");
        } else if (houseById.getChargeType().equals("charge_type_1")) {
            map.put("chargeType", "面积收费");
        } else if (houseById.getChargeType().equals("charge_type_2")) {
            map.put("chargeType", "二部制收费");
        } else if (houseById.getChargeType().equals("charge_type_3")) {
            map.put("chargeType", "热计量收费");
        }

        if (houseById.getHeatUserType() == null) {
            map.put("heatUserType", "");
        } else if (houseById.getHeatUserType().equals("heat_user_type_1")) {
            map.put("heatUserType", "低保");
        } else if (houseById.getHeatUserType().equals("heat_user_type_2")) {
            map.put("heatUserType", "事业");
        } else if (houseById.getHeatUserType().equals("heat_user_type_3")) {
            map.put("heatUserType", "政府");
        } else if (houseById.getHeatUserType().equals("heat_user_type_4")) {
            map.put("heatUserType", "工厂");
        } else if (houseById.getHeatUserType().equals("heat_user_type_5")) {
            map.put("heatUserType", "门市");
        } else if (houseById.getHeatUserType().equals("heat_user_type_6")) {
            map.put("heatUserType", "写字楼");
        } else if (houseById.getHeatUserType().equals("heat_user_type_7")) {
            map.put("heatUserType", "学校");
        } else if (houseById.getHeatUserType().equals("heat_user_type_8")) {
            map.put("heatUserType", "居民");
        } else {
            map.put("heatUserType", "");
        }

        if (houseById.getHeatExchangeStatus() == null) {
            map.put("heatExchangeNum", "0");
        } else if (houseById.getHeatExchangeStatus().equals("heat_exchange_status_0")) {
            map.put("heatExchangeNum", "0");
        } else if (houseById.getHeatExchangeStatus().equals("heat_exchange_status_1")) {
            map.put("heatExchangeNum", "1");
        } else if (houseById.getHeatExchangeStatus().equals("heat_exchange_status_2")) {
            map.put("heatExchangeNum", "2");
        } else if (houseById.getHeatExchangeStatus().equals("heat_exchange_status_3")) {
            map.put("heatExchangeNum", "3");
        } else if (houseById.getHeatExchangeStatus().equals("heat_exchange_status_4")) {
            map.put("heatExchangeNum", "4");
        } else if (houseById.getHeatExchangeStatus().equals("heat_exchange_status_5")) {
            map.put("heatExchangeNum", "5");
        } else if (houseById.getHeatExchangeStatus().equals("heat_exchange_status_6")) {
            map.put("heatExchangeNum", "6");
        } else if (houseById.getHeatExchangeStatus().equals("heat_exchange_status_7")) {
            map.put("heatExchangeNum", "7");
        } else if (houseById.getHeatExchangeStatus().equals("heat_exchange_status_8")) {
            map.put("heatExchangeNum", "8");
        } else {
            map.put("heatExchangeNum", "0");
        }

        map.put("chargeArea",houseById.getChargeArea());
        map.put("areaPrice",priceDefine.getAreaPrice());
        UserYearHeat byUserAndAnnual = userYearHeatService.findByUserAndAnnual(consumerId, annual);
        if(byUserAndAnnual == null){
            map.put("hasYearHeatInfo",false);
        }else{
            map.put("hasYearHeatInfo",true);
        }
        if(displaySumReceivable != null && displaySumReceivable == true){
            if(byUserAndAnnual == null){
                root.setCode(500);
                root.setMsg("获取不到用户"+consumerId+"在采暖年度"+annual+"的费用合计信息");
                return root;
            }
            BigDecimal sumReceivable = byUserAndAnnual.getSumReceivable();
            BigDecimal sumAccount = byUserAndAnnual.getSumAccount();
            if(sumReceivable == null){
                sumReceivable=BigDecimal.ZERO;
            }
            map.put("sumReceivable",sumReceivable);//费用合计
            if(sumAccount == null){
                sumAccount=BigDecimal.ZERO;
            }
            map.put("sumAccount",sumAccount);//缴费合计
        }

        BigDecimal sumHeatAdj = houseHeatDetailService.getSumHeatAdj(consumerId, annual);
        BigDecimal sumMeterRead = houseHeatDetailService.getSumMeterRead(consumerId, annual);
        map.put("sumHeatAdj", sumHeatAdj);//热量调整
        map.put("sumMeterRead", sumMeterRead);//计量码数
        root.setData(map);
        return root;


    }


    /**
     * 参数形式[{consumerId:xxx},{consumerId:xxx}]
     *
     * @return [批量收费]批量获取 暖费收取 用户信息
     */
    @ApiOperation(value = "[批量收费]批量获取 暖费收取 用户信息 ")
    @RequestMapping("/getBatchUserAnnualHeatInfo")
    public Root getBatchUserAnnualHeatInfo(@RequestBody List<Map> paramList) throws  Exception {

        String consumerId = "";
        Root root = new Root();
        List<Map> result = new ArrayList<>();
        for (Map mapParam : paramList) {
            consumerId = (String) mapParam.get("consumerId");
            String companyId=consumerId.substring(0,5);
            String annual = priceDefineService.findCurrentHeatAnnual(companyId);
            if (consumerId==null) {
                root.setCode(500);
                root.setMsg("用户id不能传空字符串");
                return root;
            }
            if (annual.equals("")) {
                root.setCode(500);
                root.setMsg("采暖年度不能传空字符串");
                return root;
            }
            House houseById = houseService.getHouseById(consumerId);
            if (houseById == null) {
                root.setCode(500);
                root.setMsg("获取不到用户信息");
                return root;
            }
            String unitPriceType = houseById.getUnitPriceType();
            if (unitPriceType == null || unitPriceType.equals("")) {
                root.setCode(500);
                root.setMsg("获取不到暖费信息");
                return root;
            }

            PriceDefine priceDefine = priceDefineService.selectByPrimaryKey(unitPriceType);
            if (priceDefine == null) {
                root.setCode(500);
                root.setMsg("获取不到暖费信息");
                return root;
            }

            UserYearHeat byUserAndAnnual = userYearHeatService.findByUserAndAnnual(consumerId, annual);
            if (byUserAndAnnual == null) {
                root.setCode(500);
                root.setMsg("获取不到用户" + consumerId + "在采暖年度" + annual + "的费用合计信息");
                return root;
            }

            Map<String, Object> map = new HashMap<>();
            map.put("companyId", houseById.getCompanyId());
            map.put("consumerId", houseById.getConsumerId());
            map.put("name", houseById.getName());
            map.put("address", houseById.getAddress());
            String heatingStatus = houseById.getHeatingStatus();
            String netStatus = houseById.getNetStatus();


            if (heatingStatus == null || heatingStatus.equals("")) {
                map.put("heatingStatus", "");//其实都应该这样处理
            } else {
                TypeMst typeMst = typeMstService.getTypeMstById(heatingStatus);
                if (typeMst != null) {

                    map.put("heatingStatus", typeMst.getTypeName());
                } else {

                    map.put("heatingStatus", "");
                }
            }

            if (netStatus == null || netStatus.equals("")) {
                map.put("netStatus", "");//其实都应该这样处理
            } else {
                TypeMst typeMst = typeMstService.getTypeMstById(netStatus);
                if (typeMst != null) {

                    map.put("netStatus", typeMst.getTypeName());
                } else {

                    map.put("netStatus", "");
                }
            }


            map.put("tel", houseById.getTel());
            map.put("idcard", houseById.getIdcard());
            map.put("userCompany", houseById.getUserCompany());

            if (houseById.getChargeType() == null) {
                map.put("chargeType", "");
            } else if (houseById.getChargeType().equals("charge_type_1")) {
                map.put("chargeType", "面积收费");
            } else if (houseById.getChargeType().equals("charge_type_2")) {
                map.put("chargeType", "二部制收费");
            } else if (houseById.getChargeType().equals("charge_type_3")) {
                map.put("chargeType", "热计量收费");
            }

            if (houseById.getHeatUserType() == null) {
                map.put("heatUserType", "");
            } else if (houseById.getHeatUserType().equals("heat_user_type_1")) {
                map.put("heatUserType", "低保");
            } else if (houseById.getHeatUserType().equals("heat_user_type_2")) {
                map.put("heatUserType", "事业");
            } else if (houseById.getHeatUserType().equals("heat_user_type_3")) {
                map.put("heatUserType", "政府");
            } else if (houseById.getHeatUserType().equals("heat_user_type_4")) {
                map.put("heatUserType", "工厂");
            } else if (houseById.getHeatUserType().equals("heat_user_type_5")) {
                map.put("heatUserType", "门市");
            } else if (houseById.getHeatUserType().equals("heat_user_type_6")) {
                map.put("heatUserType", "写字楼");
            } else if (houseById.getHeatUserType().equals("heat_user_type_7")) {
                map.put("heatUserType", "学校");
            } else if (houseById.getHeatUserType().equals("heat_user_type_8")) {
                map.put("heatUserType", "居民");
            } else {
                map.put("heatUserType", "");
            }

            if (houseById.getHeatExchangeStatus() == null) {
                map.put("heatExchangeNum", "0");
            } else if (houseById.getHeatExchangeStatus().equals("heat_exchange_status_0")) {
                map.put("heatExchangeNum", "0");
            } else if (houseById.getHeatExchangeStatus().equals("heat_exchange_status_1")) {
                map.put("heatExchangeNum", "1");
            } else if (houseById.getHeatExchangeStatus().equals("heat_exchange_status_2")) {
                map.put("heatExchangeNum", "2");
            } else if (houseById.getHeatExchangeStatus().equals("heat_exchange_status_3")) {
                map.put("heatExchangeNum", "3");
            } else if (houseById.getHeatExchangeStatus().equals("heat_exchange_status_4")) {
                map.put("heatExchangeNum", "4");
            } else if (houseById.getHeatExchangeStatus().equals("heat_exchange_status_5")) {
                map.put("heatExchangeNum", "5");
            } else if (houseById.getHeatExchangeStatus().equals("heat_exchange_status_6")) {
                map.put("heatExchangeNum", "6");
            } else if (houseById.getHeatExchangeStatus().equals("heat_exchange_status_7")) {
                map.put("heatExchangeNum", "7");
            } else if (houseById.getHeatExchangeStatus().equals("heat_exchange_status_8")) {
                map.put("heatExchangeNum", "8");
            } else {
                map.put("heatExchangeNum", "0");
            }

            map.put("chargeArea", houseById.getChargeArea());
            map.put("areaPrice", priceDefine.getAreaPrice());
            BigDecimal sumReceivable = byUserAndAnnual.getSumReceivable();
            map.put("sumReceivable", sumReceivable);//费用合计
            BigDecimal sumHeatAdj = houseHeatDetailService.getSumHeatAdj(consumerId, annual);
            BigDecimal sumMeterRead = houseHeatDetailService.getSumMeterRead(consumerId, annual);
            map.put("sumHeatAdj", sumHeatAdj);//热量调整
            map.put("sumMeterRead", sumMeterRead);//计量码数
            result.add(map);
        }
        root.setData(result);
        return root;


    }

    @ApiOperation(value = "删除 费用管理 用户明细数据(实际处理是加上另外一条等额负数的记录)")
    @RequestMapping("/deleteUserYearReceivableDetail")
    @Transactional
    public Root deleteUserYearReceivableDetail(@RequestParam String primaryId,@RequestParam String note,@RequestParam String chargeItem) {

        //此处需要考虑，是否需要判断这条记录是否有其他业务使用过。

        Root root = new Root();

        UserYearReceivableDetail userYearReceivableDetail = userYearReceivableDetailService.selectByPrimaryKey(primaryId);
        if (userYearReceivableDetail == null) {
            root.setCode(500);
            root.setMsg("费用管理用户明细数据记录不存在：" + primaryId + "，请传入正常值");
            return root;
        }

//        int i1 = userYearReceivableDetailService.deleteByPrimaryKey(primaryId);
//        if(i1 != 1){
//            root.setCode(500);
//            root.setMsg("删除费用管理用户明细数据记录："+primaryId+"，失败");
//            return root;
//        }
        UserYearReceivableDetail newUserYearReceivableDetail = new UserYearReceivableDetail();
        newUserYearReceivableDetail.setCompanyId(userYearReceivableDetail.getCompanyId());
        newUserYearReceivableDetail.setConsumerId(userYearReceivableDetail.getConsumerId());
        newUserYearReceivableDetail.setAnnual(userYearReceivableDetail.getAnnual());
        newUserYearReceivableDetail.setChargingItem(userYearReceivableDetail.getChargingItem());

        newUserYearReceivableDetail.setSum(BigDecimal.ZERO.subtract(userYearReceivableDetail.getSum()));

        newUserYearReceivableDetail.setUnitPrice(userYearReceivableDetail.getUnitPrice());
        newUserYearReceivableDetail.setDiscount(userYearReceivableDetail.getDiscount());
        newUserYearReceivableDetail.setTotal(BigDecimal.ZERO.subtract(userYearReceivableDetail.getTotal()));
        //newUserYearReceivableDetail.setCertufucate(userYearReceivableDetail.getCertufucate()); 不用凭证
        //newUserYearReceivableDetail.setApproveRes(userYearReceivableDetail.getApproveRes()); 不用审批
        newUserYearReceivableDetail.setNotes("删除id为："+userYearReceivableDetail.getPrimaryId()+"的记录，删除原因："+note+"，费用项目为："+chargeItem);

        Root add = postUserYearReceivableDetail(newUserYearReceivableDetail, "add");
        if (add.getCode() == 0) {

            root.setCode(0);
            root.setMsg("删除费用管理用户明细数据记录：" + primaryId + "，成功");
            return root;
        } else {
            return add;
        }

    }


    @ApiOperation(value = "增加 费用管理 用户明细数据")
    @RequestMapping("/addUserYearReceivableDetail")
    @Transactional
    public Root addUserYearReceivableDetail(UserYearReceivableDetail userYearReceivableDetail) {
        return postUserYearReceivableDetail(userYearReceivableDetail, "add");
    }

    @ApiOperation(value = "修改 费用管理 用户明细数据")
    @RequestMapping("/updateUserYearReceivableDetail")
    @Transactional
    public Root updateUserYearReceivableDetail(UserYearReceivableDetail userYearReceivableDetail) {
        return postUserYearReceivableDetail(userYearReceivableDetail, "update");
    }

    public Root postUserYearReceivableDetail(UserYearReceivableDetail userYearReceivableDetail, String method) {

        Root root = new Root();

        //参数合法性判断
        //修改
        if (method.equals("update")) {
            if (userYearReceivableDetail.getPrimaryId() == null || userYearReceivableDetail.getPrimaryId().equals("")) {
                root.setCode(500);
                root.setMsg("id：" + userYearReceivableDetail.getPrimaryId() + "，请传入正常值");
                return root;
            }
        }


        if (userYearReceivableDetail.getConsumerId() == null || userYearReceivableDetail.getConsumerId().equals("")) {
            root.setCode(500);
            root.setMsg("用户id：" + userYearReceivableDetail.getConsumerId() + "，请传入正常值");
            return root;
        }

        if (userYearReceivableDetail.getCompanyId() == null || userYearReceivableDetail.getCompanyId().equals("")) {
            root.setCode(500);
            root.setMsg("公司id：" + userYearReceivableDetail.getCompanyId() + "，请传入正常值");
            return root;
        }


        if (userYearReceivableDetail.getAnnual() == null || userYearReceivableDetail.getAnnual().equals("")) {
            root.setCode(500);
            root.setMsg("采暖年度：" + userYearReceivableDetail.getAnnual() + "，请传入正常值");
            return root;
        }

        if (userYearReceivableDetail.getSum() == null) {
            root.setCode(500);
            root.setMsg("数量：" + userYearReceivableDetail.getSum() + "，请传入正常值");
            return root;
        }

        if (userYearReceivableDetail.getUnitPrice() == null) {
            root.setCode(500);
            root.setMsg("单价：" + userYearReceivableDetail.getUnitPrice() + "，请传入正常值");
            return root;
        }

        String chargingItem = userYearReceivableDetail.getChargingItem();

        if (chargingItem == null || chargingItem.equals("")) {
            root.setCode(500);
            root.setMsg("费用项目：" + chargingItem + "，请传入正常值");
            return root;
        }


        List<String> typeKbns = new ArrayList<>();
        String typeKbn = "charging_item";
        typeKbns.add(typeKbn);
        List<TypeMst> downInfoByTypeKbns = typeMstService.getDownInfoByTypeKbns(typeKbns);

        List<String> ids = new ArrayList<String>();
        List<String> name_ids = new ArrayList<>();
        if (downInfoByTypeKbns != null && downInfoByTypeKbns.size() > 0) {
            for (int i = 0; i < downInfoByTypeKbns.size(); i++) {
                TypeMst typeMst = downInfoByTypeKbns.get(i);
                ids.add(typeMst.getId());
                name_ids.add(typeMst.getTypeName());
            }
        }

        if (!ids.contains(chargingItem)) {

            if (!name_ids.contains(chargingItem)) {//id找不到，那么就通过name找,name找不到，那么就报错
                root.setCode(500);
                root.setMsg("费用项目枚举值不存在：" + chargingItem + "，请传入正常值");
                return root;
            }else {

                if (downInfoByTypeKbns != null && downInfoByTypeKbns.size() > 0) {
                    for (int i = 0; i < downInfoByTypeKbns.size(); i++) {
                        TypeMst typeMst = downInfoByTypeKbns.get(i);
                        if (chargingItem.equals(typeMst.getTypeName())) {
                            chargingItem = typeMst.getId();//name找到了，那么就赋值给id                        }
                        }
                    }
                }
            }
        }


        //先判断是否有重复添加 费用项目
        Integer standardPageSize = 20;
        PageInfo<UserYearReceivableDetailInfo> userYearReceivableDetailsByConsumerId = userYearReceivableDetailService.getUserYearReceivableDetailsByConsumerId(userYearReceivableDetail.getConsumerId(), null, userYearReceivableDetail.getAnnual(), null, null, 1, standardPageSize);
        long total = userYearReceivableDetailsByConsumerId.getTotal();
        if (total > standardPageSize) {
            long length = total / standardPageSize;
            long yushu = total % standardPageSize > 0 ? 1 : 0;
            length += yushu;
            Integer page = 1;
            for (int i = 0; i < length; i++, page++) {
                PageInfo<UserYearReceivableDetailInfo> userYearReceivableDetailsByConsumerIdItem = userYearReceivableDetailService.getUserYearReceivableDetailsByConsumerId(userYearReceivableDetail.getConsumerId(), null, userYearReceivableDetail.getAnnual(), null, null, page, standardPageSize);
                List<UserYearReceivableDetailInfo> list = userYearReceivableDetailsByConsumerIdItem.getList();
                for (UserYearReceivableDetailInfo yearReceivableDetail : list) {
                    String chargingItem1 = yearReceivableDetail.getChargingItem();
                    if (chargingItem1 != null) {

                        if (method.equals("update")) {

                            if (chargingItem1.equals(chargingItem) && !chargingItem.equals("charging_item_4") && !yearReceivableDetail.getPrimaryId().equals(userYearReceivableDetail.getPrimaryId()) && !yearReceivableDetail.getTotal().equals(BigDecimal.ZERO.subtract(userYearReceivableDetail.getTotal()))) {
                                //后面那句有可能是对冲
                                root.setCode(500);
                                root.setMsg("修改的费用项目和已有数据重复");
                                return root;
                            }

                        }else{

                            if (chargingItem1.equals(chargingItem) && !chargingItem.equals("charging_item_4") && !yearReceivableDetail.getTotal().equals(BigDecimal.ZERO.subtract(userYearReceivableDetail.getTotal()))) {
                                //后面那句有可能是对冲
                                root.setCode(500);
                                root.setMsg("增加的费用项目和已有数据重复");
                                return root;
                            }
                        }

                    }


                }
            }
        } else {
            List<UserYearReceivableDetailInfo> list = userYearReceivableDetailsByConsumerId.getList();
            for (UserYearReceivableDetailInfo yearReceivableDetail : list) {
                String chargingItem1 = yearReceivableDetail.getChargingItem();
                if (chargingItem1 != null) {

                    if (method.equals("update")) {

                        if (chargingItem1.equals(chargingItem)  && !chargingItem.equals("charging_item_4") && !yearReceivableDetail.getPrimaryId().equals(userYearReceivableDetail.getPrimaryId()) && !yearReceivableDetail.getTotal().equals(BigDecimal.ZERO.subtract(userYearReceivableDetail.getTotal()))) {
                            //后面那句有可能是对冲
                            root.setCode(500);
                            root.setMsg("修改的费用项目和已有数据重复");
                            return root;
                        }

                    }else{

                        if (chargingItem1.equals(chargingItem)  && !chargingItem.equals("charging_item_4") && !yearReceivableDetail.getTotal().equals(BigDecimal.ZERO.subtract(userYearReceivableDetail.getTotal()))) {
                            //后面那句有可能是对冲
                            root.setCode(500);
                            root.setMsg("增加的费用项目和已有数据重复");
                            return root;
                        }
                    }


                }
            }

        }

        //增加
        if (method.equals("add")) {
            //生成主键_这里需要统一在底层弄一下主键，防止并发操作导致的id冲突
            //DecimalFormat mFormat = new DecimalFormat("0000000000");
            String PrimaryId = null;
            //每次读取最后那条id，然后加1
//            UserYearReceivableDetail lastUserYearReceivableDetails = userYearReceivableDetailService.findLastUserYearReceivableDetails();
//            Integer newPrimaryId = null;
//            if (lastUserYearReceivableDetails == null) {
//                newPrimaryId = 0;
//            } else {
//                newPrimaryId = Integer.parseInt(lastUserYearReceivableDetails.getPrimaryId());
//            }
//            newPrimaryId = newPrimaryId + 1;
            if(userYearReceivableDetail.getPrimaryId()==null){
                PrimaryId = IDWorker.uuid();
                userYearReceivableDetail.setPrimaryId(PrimaryId);
            }

        }


        //折扣 默认为1
        if (userYearReceivableDetail.getDiscount() == null) {
            userYearReceivableDetail.setDiscount(new BigDecimal(1));
        }


        //总价=数量*单价*折扣
        userYearReceivableDetail.setTotal(userYearReceivableDetail.getUnitPrice().multiply(userYearReceivableDetail.getSum().multiply(userYearReceivableDetail.getDiscount())));

        //增加
        if (method.equals("add")) {
            //创建人
            userYearReceivableDetail.setCreateUser(UserUtils.getUserId());
            //创建时间
            userYearReceivableDetail.setCreateDate(new Date());
        } else if (method.equals("update")) {

            //修改人
            userYearReceivableDetail.setUpdateUser(UserUtils.getUserId());
            //修改时间
            userYearReceivableDetail.setUpdateDate(new Date());
        }

        //增加
        if (method.equals("add")) {
            if (userYearReceivableDetailService.insertSelective(userYearReceivableDetail) == 1) {

                Boolean aBoolean = updateUserYearHeat(userYearReceivableDetail.getConsumerId(), userYearReceivableDetail.getAnnual());//触发器
                if(aBoolean == false){

                    root.setCode(500);
                    root.setMsg("该用户未生成用户采暖年度信息表中的记录");
                    return root;
                }

                root.setCode(0);
                root.setMsg("添加成功");
                return root;
            } else {

                root.setCode(500);
                root.setMsg("添加失败");
                return root;
            }
        } else if (method.equals("update")) {
            if (userYearReceivableDetailService.updateByPrimaryKey(userYearReceivableDetail) == 1) {

                Boolean aBoolean = updateUserYearHeat(userYearReceivableDetail.getConsumerId(), userYearReceivableDetail.getAnnual());//触发器
                if(aBoolean == false){

                    root.setCode(500);
                    root.setMsg("该用户未生成用户采暖年度信息表中的记录");
                    return root;
                }
                root.setCode(0);
                root.setMsg("修改成功");
                return root;
            } else {

                root.setCode(500);
                root.setMsg("修改失败");
                return root;
            }
        }


        root.setCode(500);
        root.setMsg("这个方法不支持");
        return root;

    }

    @ApiOperation(value = "获取当前登录用户的审核人")
    @RequestMapping("/getApprovePerson")
    @Transactional
    public Root getApprovePerson() {

        Root root = new Root();
        UserLogin userLoginById = userLoginService.getUserLoginById(UserUtils.getUserId());
        if (userLoginById == null) {
            root.setCode(500);
            root.setMsg("获取不到当前登录用户");
            return root;
        }
        Staff staff = staffService.selectByPrimaryKey(userLoginById.getStaffId());
        if (staff == null) {
            root.setCode(500);
            root.setMsg("当前用户没有审核人");
            return root;
        }

        root.setCode(0);
        root.setData(staff);
        return root;
    }

// 老逻辑没有处理pay_over
//    private Boolean updateUserYearHeat(String consumerId, String annual) {
//        BigDecimal totalAccountByConsumerId = userYearAccountDetailService.getTotalByConsumerId(consumerId, annual);
//        if (totalAccountByConsumerId == null) totalAccountByConsumerId = BigDecimal.ZERO;
//
//        BigDecimal totalReceivableByConsumerId = userYearReceivableDetailService.getTotalByConsumerId(consumerId,annual);
//        if(totalReceivableByConsumerId == null) totalReceivableByConsumerId = BigDecimal.ZERO;
//
//
//        UserYearHeat byUserAndAnnual = userYearHeatService.findByUserAndAnnual(consumerId, annual);
//        if(byUserAndAnnual == null){
//            return false;
//        }
//
//        byUserAndAnnual.setSumReceivable(totalReceivableByConsumerId);
//        byUserAndAnnual.setSumAccount(totalAccountByConsumerId);//从user_year_receivable_detail 用户年度采暖费用明细表 中取数
//
//        byUserAndAnnual.setMarginNow(totalAccountByConsumerId.subtract(totalReceivableByConsumerId));
//        userYearHeatService.updateByPrimaryKey(byUserAndAnnual);
//        return true;
//    }


    private Boolean updateUserYearHeat(String consumerId, String annual) {
        BigDecimal totalAccountByConsumerId = userYearAccountDetailService.getTotalByConsumerId(consumerId, annual);
        if (totalAccountByConsumerId == null) totalAccountByConsumerId = BigDecimal.ZERO;
        BigDecimal totalReceivableByConsumerId = userYearReceivableDetailService.getTotalByConsumerId(consumerId,annual);
        if(totalReceivableByConsumerId == null) totalReceivableByConsumerId = BigDecimal.ZERO;
        UserYearHeat byUserAndAnnual = userYearHeatService.findByUserAndAnnual(consumerId, annual);
        if (byUserAndAnnual == null) {
            return false;
        }
        byUserAndAnnual.setSumAccount(totalAccountByConsumerId);
        byUserAndAnnual.setSumReceivable(totalReceivableByConsumerId);
        byUserAndAnnual.setMarginNow(totalAccountByConsumerId.subtract(totalReceivableByConsumerId));

        String payOver = byUserAndAnnual.getPayOver();


        //改变缴费状态的逻辑
        House houseInfo = houseService.getHouseById(consumerId);
        if (houseInfo == null) {
            return false;
        }
        //逻辑为：当供暖状态为停暖时，无论缴费为何种情况，缴费状态为停暖
        //当供暖状态为供暖时  1、热费余额大于等于0，为已缴费  2：未有缴费记录且热费余额小于0  3、有缴费记录且热费余额小于0
        //1缴费状态为已缴费  2  缴费状态为未缴费  3  缴费状态为余额不足
        if (houseInfo.getHeatingStatus() != null && !houseInfo.getHeatingStatus().equals("")) {
            switch (houseInfo.getHeatingStatus()) {
                case "heating_status_1"://供暖
                    if (byUserAndAnnual.getMarginNow().compareTo(BigDecimal.ZERO) != -1) {
                        byUserAndAnnual.setPayOver("pay_over_1");//已缴费
                    } else if (totalAccountByConsumerId.compareTo(BigDecimal.ZERO) == 0 && byUserAndAnnual.getMarginNow().compareTo(BigDecimal.ZERO) == -1) {
                        byUserAndAnnual.setPayOver("pay_over_2");//未缴费
                    } else if (totalAccountByConsumerId.compareTo(BigDecimal.ZERO) == 1 && byUserAndAnnual.getMarginNow().compareTo(BigDecimal.ZERO) == -1) {
                        byUserAndAnnual.setPayOver("pay_over_3");//余额不足
                    }
                    break;
                case "heating_status_2"://停供
                    byUserAndAnnual.setPayOver("pay_over_4");//停暖
                    break;
                default:
                    break;
            }

        } else {

            byUserAndAnnual.setPayOver("pay_over_2");//未缴费
        }


        userYearHeatService.updateByPrimaryKey(byUserAndAnnual);
        return true;
    }

}

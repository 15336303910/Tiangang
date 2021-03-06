package cn.plou.web.charge.chargeconfig.controller;

import java.util.Date;

import cn.plou.web.charge.chargeconfig.dto.BankChargeDTO;
import cn.plou.web.charge.chargeconfig.dto.ReceiptListDTO;
import cn.plou.web.charge.chargeconfig.entity.*;
import cn.plou.web.charge.chargeconfig.service.*;
import cn.plou.web.charge.chargeconfig.util.MoneyConverter;
import cn.plou.web.charge.chargeconfig.vo.AnnualsVO;
import cn.plou.web.charge.chargeconfig.vo.UserYearAccountDetailForOldYearVO;
import cn.plou.web.charge.chargeconfig.vo.UserYearHeatLateFee;
import cn.plou.web.charge.chargeconfig.vo.UserYearReceivableDetailInfo;
import cn.plou.web.charge.heatingmanage.dto.ApproveContractApprovesDTO;
import cn.plou.web.common.annotation.VisitRecord;
import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.exception.BindingResultHandler;
import cn.plou.web.common.utils.CurrencyUtil;
import cn.plou.web.common.utils.FlowIdTool;
import cn.plou.web.common.utils.IDWorker;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import cn.plou.web.system.baseMessage.commuity.service.CommuityService;
import cn.plou.web.system.baseMessage.house.entity.House;
import cn.plou.web.system.baseMessage.house.service.HouseService;
import cn.plou.web.system.baseMessage.house.vo.HouseInfo;
import cn.plou.web.system.commonMessage.typeMst.dao.TypeMstMapper;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.TypeMstService;
import cn.plou.web.system.permission.userLogin.service.UserLoginService;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.plou.web.common.config.common.Cond.getCond;

/**
 * @author panziqiang
 * 2018/8/17 14:27
 */

@RequestMapping("${chargePath}/costgather")
@RestController
@Api(description = "收费管理——费用收取")
@Slf4j
public class CostGatherController {


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ChargeAccountService chargeAccountService;
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
    private CommuityService commuityService;

    @Resource
    private BankInterfaceHistoryService bankInterfaceHistoryService;
    @Resource
    private PriceDefineService priceDefineService;

    @Resource
    private TypeMstMapper typeMstMapper;

    @Resource
    UserLoginService userLoginService;

    @Resource
    CostManagementController costManagementController;
    @Resource
    BankReconciliationsHeadService bankReconciliationsHeadService;


    @ApiOperation(value = "根据关键字和用户id获取住户信息")
    @RequestMapping("/getHouseBySearchTextOrCompanyId")
    public Root getHouseBySearchTextOrCompanyId(@RequestParam(value = "searchText", required = false) String searchText,
                                                @RequestParam(value = "companyId", required = false) String companyId,
                                                @RequestParam(value = "order", required = false) String order,
                                                @RequestParam(value = "sortby", required = false) String sortby,
                                                @RequestParam Integer page,
                                                @RequestParam Integer pageSize) {

        Root root = new Root();
        PageInfo<House> pageInfo = houseService.getHouseBySearchTextOrId(searchText, companyId, order, sortby, page, pageSize);
        root.setData(pageInfo.getList());
        root.setCond(getCond(page, pageSize, (int) pageInfo.getTotal(),
                null, sortby));
        return root;

    }

    @ApiOperation(value = "根据采暖卡id获取住户信息")
    @RequestMapping("/getHouseByChargeIdOrTelOrIdcard")
    public Root getHouseByChargeIdOrTelOrIdcard(@RequestParam Map<String, Object> params) {
        Root root = new Root();
        //Assert.notNull(params.get("chargeId"), "chargeId can not be empty");
        String chargeId = params.get("chargeId") == null ? null : params.get("chargeId").toString();
        String tel = params.get("tel") == null ? null : params.get("tel").toString();
        String idcard = params.get("idcard") == null ? null : params.get("idcard").toString();
        String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();

        if (chargeId == null && tel == null && idcard == null) {
            root.setCode(500);
            root.setMsg("请在charge_id、tel、idcard中，至少传递一个参数");
            return root;
        }
        root.setData(houseService.getHouseByChargeIdOrTelOrIdcardOrCompanyId(chargeId, tel, idcard, companyId));

        return root;
    }


    @ApiOperation(value = "获取 暖费收取 预收暖费信息")
    @RequestMapping("/getPreReceiveHeatInfo")
    public Root getPreReceiveHeatInfo(@RequestParam String consumerId,
                                      @RequestParam String annual,
                                      @RequestParam(value = "isClearChecked") boolean isClearChecked,
                                      @RequestParam(value = "paymentMethod") String paymentMethod,
                                      @RequestParam(value = "wantChange") boolean wantChange) {

        Root root = new Root();
//        Integer page = 1;
//        Integer pageSize = Integer.MAX_VALUE;


//        PageInfo<UserYearReceivableDetailInfo> userYearReceivableDetailsByConsumerId = userYearReceivableDetailService.getUserYearReceivableDetailsByConsumerId(consumerId, null, annual, null, null, page, pageSize);
//        if (userYearReceivableDetailsByConsumerId.getTotal() == 0) {
//            root.setCode(500);
//            root.setMsg("获取预收热费信息失败：该用户"+consumerId+"在"+annual+"采暖年度，没有费用明细记录，请为该用户重新生成采暖季数据，或者手动添加费用明细");
//            return root;
//        }

        House houseById = houseService.getHouseById(consumerId);
        String chargeType = houseById.getChargeType();//供热收费类型

        UserYearHeat byUserAndAnnual = userYearHeatService.findByUserAndAnnual(consumerId, annual);
        if (byUserAndAnnual == null) {
            root.setCode(500);
            root.setMsg("获取预收热费信息失败：用户年度采暖信息表没有记录");
            return root;
        }

        if (byUserAndAnnual.getSumAccount() == null) {
            byUserAndAnnual.setSumAccount(BigDecimal.ZERO);
        }
        if (byUserAndAnnual.getSumReceivable() == null) {
            byUserAndAnnual.setSumReceivable(BigDecimal.ZERO);
        }
        /*  PageInfo<UserYearHeat> userYearHeats = userYearHeatService.findByUserForOldAnnual(consumerId, currentHeatAnnual, "desc", "annual", page, pageSize);

        TreeMap<String, Object> mapGlobal = new TreeMap<String, Object>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        BigDecimal needTotal = BigDecimal.ZERO;
        BigDecimal realNeedTotal = BigDecimal.ZERO;
        List<Map<String, Object>> lst = new ArrayList<>();
        for (UserYearHeat userYearHeat : userYearHeats.getList()) {
            Map<String, Object> map = getUserHeatFeeMapInfo(userYearHeat, paymentMethod, wantChange);
            lst.add(map);
            if (selectedAnnuals != null) {
                for (String annual : selectedAnnuals) {
                    if (map.get("annual").equals(annual)) {
                        needTotal = needTotal.add(new BigDecimal((String) map.get("needTotal")));
                        realNeedTotal = needTotal.add((BigDecimal) map.get("realNeedTotal"));
                    }
                    break;
                }
            }

        }
        mapGlobal.put("detail", lst);
        mapGlobal.put("OweTotal", oldYearOweTotal);//各年度总的欠费金额
        mapGlobal.put("realNeedTotal", realNeedTotal);
        mapGlobal.put("needTotal", needTotal.toPlainString());*/

        BigDecimal oldYearOweTotal = userYearHeatService.getOldYearOwe(consumerId, annual);//获取往年欠费
        oldYearOweTotal = oldYearOweTotal == null ? BigDecimal.ZERO : oldYearOweTotal;

        PriceDefine priceDefine = priceDefineService.selectByPrimaryKey(houseById.getUnitPriceType());
        if (priceDefine == null) {
            root.setCode(500);
            root.setMsg("获取热费单价信息失败：热费单价类型表没有记录");
            return root;
        }
        //List<UserYearReceivableDetailInfo> list = userYearReceivableDetailsByConsumerId.getList();

        BigDecimal areaTotal = BigDecimal.ZERO;//面积收费
        BigDecimal heatTotal = BigDecimal.ZERO;//热量收费 王磊说，对于预收的情况，热量收费和面积收费都是这么计算的
        String heatingStatus = houseById.getHeatingStatus();
        if (heatingStatus == null) {

        } else if (heatingStatus.equals("heating_status_1")) {//供暖
            areaTotal = new BigDecimal(priceDefine.getPrePrice()).multiply(houseById.getChargeArea());
            heatTotal = areaTotal;
        } else if (heatingStatus.equals("heating_status_2")) {//停供

        } else {

        }
//        for (UserYearReceivableDetailInfo userYearReceivableDetail : list) {
////            if (userYearReceivableDetail.getChargingItem() != null && userYearReceivableDetail.getChargingItem().equals("charging_item_1")) {
////                //面积费用
////
////                areaTotal = userYearReceivableDetail.getTotal();
////            }
//            if (userYearReceivableDetail.getChargingItem() != null && userYearReceivableDetail.getChargingItem().equals("charging_item_2")) {
//                //热量费用
//                heatTotal = userYearReceivableDetail.getTotal();
//            }
//            total = total.add(userYearReceivableDetail.getTotal());
//        }

//        BigDecimal total = userYearReceivableDetailService.getTotalByConsumerId(consumerId, annual);//预收合计
//        if(total == null){
//            total = BigDecimal.ZERO;
//        }
        BigDecimal otherTotal = userYearReceivableDetailService.getOtherTotalByConsumerId(consumerId, annual);//其他费用
        if (otherTotal == null) {
            otherTotal = BigDecimal.ZERO;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("otherTotal", otherTotal);
        map.put("oldYearOweTotal", oldYearOweTotal);

        BigDecimal needTotal = areaTotal.add(otherTotal); //应缴金额

        if (isClearChecked) {//如果勾选了清欠按钮需要返回更详细的信息
            needTotal = needTotal.add(oldYearOweTotal);
        }

        map.put("total", needTotal);

        BigDecimal sumAccount = byUserAndAnnual.getSumAccount();
        needTotal = needTotal.subtract(sumAccount);
        map.put("realNeedTotal", needTotal);//实际需要交的钱，有可能包含欠款。传回这个只是为了平账的时候用


        if (wantChange) { //若为找零模式则需要处理收费精度
            needTotal = MoneyConverter.Convert(consumerId.substring(0, 5), paymentMethod, needTotal);
        }
        map.put("needTotal", needTotal.toPlainString());

//王磊说，前端不用区分
//        if (chargeType.equals("charge_type_1")) {
//            //面积收费
//            map.put("areaTotal", areaTotal);
//        } else if (chargeType.equals("charge_type_2")) {
//            //二部制收费
//
//            map.put("areaTotal", areaTotal);
//            map.put("heatTotal", heatTotal);
//        } else if (chargeType.equals("charge_type_3")) {
//            //热计量收费
//            map.put("heatTotal", heatTotal);
//        }

        map.put("areaTotal", areaTotal);
        map.put("heatTotal", heatTotal);

        root.setCode(0);
        root.setData(map);
        return root;

    }


    @ApiOperation(value = "获取 日常收费 本季暖费信息，该接口主要供第三方调用")
    @RequestMapping("/getThisAnnualHeatFeeInfoEx")
    @Transactional
    public Map<String, Object> getThisAnnualHeatFeeInfoEx(@RequestParam Map<String, List<Map<String, Object>>> params) throws ClientProtocolException {

        Map<String, Object> mapRestlt = new HashMap<>();
        Map<String, Object> mapUser = new HashMap<>();
        List<Map<String, Object>> lstUser = new ArrayList<>();
        Root root = new Root();

        if (params == null) {
            return null;
        }
        Object objlst = params.get("lst");
        if (objlst == null) {
            return null;
        }
        String strlst = objlst.toString();

        Gson gson = new Gson();
//        GsonBuilder gb = new GsonBuilder();
//        gb.setLongSerializationPolicy(LongSerializationPolicy.STRING);
//        Gson gson = gb.create();
        //String gsonString =  gson.toJson(strlst);

        //Map<String, Collection<LinkedHashMap<String, String>>>
        //Map<String, Collection<LinkedHashMap<String, String>>> advDataMap = new Gson().fromJson(strlst, new TypeToken<Map<String, Collection<LinkedHashMap<String, String>>>>() {}.getType());


        List<Map<String, Object>> lst = gson.fromJson(strlst, List.class);

//        List<Map<String, String>> lst  = gson.fromJson(strlst,
//                new TypeToken< List<Map<String, String>>>() {
//                }.getType());

        if (lst == null || lst.size() == 0) {
            return null;
        }


        int i = 0;
        //List<String> consumerIdlst = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : lst) {
            if (stringObjectMap.get("consumerId") == null) {
                throw new ClientProtocolException("获取参数consumerId失败" + i + "：");
            }
            if (stringObjectMap.get("annual") == null) {
                throw new ClientProtocolException("获取参数annual失败" + i + "：");
            }
            if (stringObjectMap.get("platform_code") == null) {
                throw new ClientProtocolException("获取参数platform_code失败" + i + "：");
            }
            if (stringObjectMap.get("business_code") == null) {
                throw new ClientProtocolException("获取参数business_code失败" + i + "：");
            }
            if (stringObjectMap.get("bankIp") == null) {
                throw new ClientProtocolException("获取参数bankIp失败" + i + "：");
            }
            if (stringObjectMap.get("requestData") == null) {
                throw new ClientProtocolException("获取参数requestData失败" + i + "：");
            }
            if (stringObjectMap.get("requestDataDate") == null) {
                throw new ClientProtocolException("获取参数requestDataDate失败" + i + "：");
            }
            //consumerIdlst.add(stringObjectMap.get("consumerId").toString());

            String consumerId = stringObjectMap.get("consumerId").toString();
            String annual = stringObjectMap.get("annual").toString();
            String business_code = stringObjectMap.get("business_code").toString();
            String platformCode = stringObjectMap.get("platform_code").toString();
            if (platformCode == null || platformCode.length() <= 5) {
                throw new ClientProtocolException("请传入正常的平台码");
            }
            String companyId = platformCode.substring(0, 5);
            String bankId = platformCode.substring(5, platformCode.length());
            String bankIp = stringObjectMap.get("bankIp").toString();


            //Map<String, Object> map1 = gson.fromJson(stringObjectMap.get("requestData"), Map.class);


            String requestData = stringObjectMap.get("requestData").toString();
            String requestDataDate = stringObjectMap.get("requestDataDate").toString();


            String chargeId = "";
            String address = "";
            String name = "";


            House houseById = houseService.getHouseById(consumerId);
            if (houseById != null) {
                chargeId = houseById.getChargeId();
                address = houseById.getAddress();
                name = houseById.getName();
            } else {

//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String ymd = sdf.format(new Date());
//                Map<String, Object> params2 = new HashMap<>();
//                params2.put("companyId", companyId);
//                //params2.put("companyId", houseById.getCompanyId());
//                params2.put("annual", annual);
//                params2.put("consumerId", consumerId);
//                params2.put("bankId", bankId);
//                params2.put("businessCode", business_code);
//                params2.put("requestData", requestData);
//                params2.put("requestDataDate", requestDataDate);
//                params2.put("returnData", "没有相关用户记录" + i + "：");
//                params2.put("returnDataDate", ymd);
//                params2.put("notes", "查询热费");
//
//                Root root1 = null;
//                try {
//                    root1 = addBankInterfaceHistory(params2);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    throw new ClientProtocolException("没有相关用户记录" + i + "：" + "添加银行履历失败1：" + e.getMessage());
//                }
//
//                if (root1.getCode() == 0) {
//                    throw new ClientProtocolException("没有相关用户记录" + i + "：");
//                } else {
//                    //如果没有插入成功，那么就需要回滚事务。
//                    throw new ClientProtocolException("没有相关用户记录" + i + "：" + "添加银行履历失败2：" + root1.getMsg());
//                }

            }


            UserYearHeat byUserAndAnnual = userYearHeatService.findByUserAndAnnual(consumerId, annual);
            if (byUserAndAnnual == null) {
                root.setCode(500);
                root.setMsg("用户年度采暖信息表没有记录");


//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String ymd = sdf.format(new Date());
//                Map<String, Object> params2 = new HashMap<>();
//                params2.put("companyId", companyId);
//                //params2.put("companyId", houseById.getCompanyId());
//                params2.put("annual", annual);
//                params2.put("consumerId", consumerId);
//                params2.put("bankId", bankId);
//                params2.put("businessCode", business_code);
//                params2.put("requestData", requestData);
//                params2.put("requestDataDate", requestDataDate);
//                params2.put("returnData", "用户" + consumerId + "年度采暖信息表没有记录" + i + "：");
//                params2.put("returnDataDate", ymd);
//                params2.put("notes", "查询热费");
//
//                Root root1 = null;
//                try {
//                    root1 = addBankInterfaceHistory(params2);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    throw new ClientProtocolException("用户" + consumerId + "年度采暖信息表没有记录" + i + "：" + "添加银行履历失败3：" + e.getMessage());
//                }
//                if (root1.getCode() == 0) {
//                    throw new ClientProtocolException("用户" + consumerId + "年度采暖信息表没有记录" + i + "：");
//                } else {
//                    //如果没有插入成功，那么就需要回滚事务。
//                    throw new ClientProtocolException("用户" + consumerId + "年度采暖信息表没有记录" + i + "：" + "添加银行履历失败4：" + root1.getMsg());
//                }


            }


//            BigDecimal oldYearOweTotal = BigDecimal.ZERO;//往年欠费
//            oldYearOweTotal = userYearHeatService.getOldYearOwe(consumerId, annual);//获取往年欠费
//            if (oldYearOweTotal == null) {
//                oldYearOweTotal = BigDecimal.ZERO;
//            }

//            Root preReceiveHeatInfo = getPreReceiveHeatInfo(consumerId, annual, true, null, false);
//            if(preReceiveHeatInfo.getCode() != 0){
//                throw new ClientProtocolException("获取热费信息失败失败" + "："+preReceiveHeatInfo.getMsg());
//            }
//
//            Map<String, Object> map = (Map<String, Object>)preReceiveHeatInfo.getData();//这里应该走的是预收热费的路子。而不是receivable表的简单相加getUserHeatFeeMapInfo(byUserAndAnnual, oldYearOweTotal);
            String payOver = byUserAndAnnual.getPayOver();
            String pay_state = "1";
            switch (payOver) {
                case "pay_over_1":
                    pay_state = "0";
                    break;
                default:
                    pay_state = "1";
                    break;
            }


            String heatingStatus = houseById.getHeatingStatus();
            if (heatingStatus == null || heatingStatus.equals("") || heatingStatus.equals("heating_status_2")) {//停止供暖的用户，应该是未缴费
                pay_state = "1";
            }
            BigDecimal sumAccount = byUserAndAnnual.getSumAccount();
            if (sumAccount == null) {
                byUserAndAnnual.setSumAccount(BigDecimal.ZERO);
            }

            mapUser = new HashMap<>();
            mapUser.put("consumer_id", consumerId == null ? "" : consumerId);
            mapUser.put("annual", annual == null ? "" : annual);
            mapUser.put("charge_id", chargeId == null ? "" : chargeId);
            mapUser.put("address", address == null ? "" : address);
            mapUser.put("name", name == null ? "" : name);
            // mapUser.put("sum_receivable", map.get("realNeedTotal") == null ? "0" : map.get("realNeedTotal"));
            mapUser.put("sum_receivable", byUserAndAnnual.getAdvHeatCost().subtract(byUserAndAnnual.getSumAccount()));
            mapUser.put("pay_state", pay_state == null ? "" : pay_state);
            mapUser.put("notes", "");
            lstUser.add(mapUser);

//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String ymd = sdf.format(new Date());
//            Map<String, Object> params2 = new HashMap<>();
//            //params2.put("companyId", houseById.getCompanyId());
//            params2.put("companyId", companyId);
//            params2.put("annual", annual);
//            params2.put("consumerId", consumerId);
//            params2.put("bankId", bankId);
//            params2.put("businessCode", business_code);
//            params2.put("requestData", requestData);
//            params2.put("requestDataDate", requestDataDate);
//
//
//            Map<String, Object> eachMapResult = new HashMap<>();
//            eachMapResult.put("return_code", "0000");
//            eachMapResult.put("business_code", business_code);
//            eachMapResult.put("tips", "查询成功");
//            eachMapResult.put("user", lstUser);
//
//            params2.put("returnData", "第" + (i + 1) + "次循环（共" + lst.size() + "次循环）的返回结果是：" + eachMapResult.toString());
//            params2.put("returnDataDate", ymd);
//            params2.put("notes", "查询热费");
//
//            Root root1 = null;
//            try {
//                root1 = addBankInterfaceHistory(params2);
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new ClientProtocolException("查询顺序" + i + "：" + "添加银行履历失败5：" + e.getMessage());
//            }
//            if (root1.getCode() != 0) {
//                throw new ClientProtocolException("查询顺序" + i + "：" + "添加银行履历失败6：" + root1.getMsg());
//            }


            i++;
        }


        String business_code = lst.get(0).get("business_code").toString();
        mapRestlt.put("return_code", "0000");
        mapRestlt.put("business_code", business_code);
        mapRestlt.put("tips", "查询成功");

        mapRestlt.put("user", lstUser);

        return mapRestlt;


    }


    @ApiOperation(value = "增加 第三方接入 记录履历信息，该接口主要供第三方调用")
    @RequestMapping("/addBankInterfaceHistory")
    @Transactional
    public Root addBankInterfaceHistory(@RequestParam Map<String, Object> params) {
        //加上锁，为了保持id的连贯性

        Map<String, Object> mapRestlt = new HashMap<>();
        Root root = new Root();

        String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
        String annual = params.get("annual") == null ? null : params.get("annual").toString();
        String consumerId = params.get("consumerId") == null ? null : params.get("consumerId").toString();
        String bankId = params.get("bankId") == null ? null : params.get("bankId").toString();
        String businessCode = params.get("businessCode") == null ? null : params.get("businessCode").toString();
        String requestData = params.get("requestData") == null ? null : params.get("requestData").toString();
        String requestDataDate = params.get("requestDataDate") == null ? null : params.get("requestDataDate").toString();
        String returnData = params.get("returnData") == null ? null : params.get("returnData").toString();
        String returnDataDate = params.get("returnDataDate") == null ? null : params.get("returnDataDate").toString();
        String notes = params.get("notes") == null ? null : params.get("notes").toString();

        //String PrimaryId = null;
        //synchronized (this) {

        String PrimaryId = UserUtils.getUserId() + "-" + UUID.randomUUID().toString().replace("-", "");
//            DecimalFormat mFormat = new DecimalFormat("000000000000000000000000000000");
//            BankInterfaceHistory lastBankInterfaceHistory = bankInterfaceHistoryService.findLastBankInterfaceHistory();
//            Integer newPrimaryId = null;
//            if (lastBankInterfaceHistory == null) {
//                newPrimaryId = 0;
//            } else {
//                newPrimaryId = Integer.parseInt(lastBankInterfaceHistory.getPrimaryId());
//            }
//            newPrimaryId = newPrimaryId + 1;
//            PrimaryId = mFormat.format(newPrimaryId);
        BankInterfaceHistory bankInterfaceHistory = new BankInterfaceHistory();
        bankInterfaceHistory.setPrimaryId(PrimaryId);
        bankInterfaceHistory.setCompanyId(companyId);
        bankInterfaceHistory.setAnnual(annual);
        bankInterfaceHistory.setConsumerId(consumerId);
        bankInterfaceHistory.setBankId(bankId);
        bankInterfaceHistory.setBusinessCode(businessCode);
        bankInterfaceHistory.setRequestData(requestData);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(requestDataDate);
            date2 = sdf.parse(returnDataDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bankInterfaceHistory.setRequestDataDate(date1);
        bankInterfaceHistory.setReturnData(returnData);
        bankInterfaceHistory.setReturnDataDate(date2);
        bankInterfaceHistory.setNotes(notes);

        int insert = bankInterfaceHistoryService.insert(bankInterfaceHistory);

        if (insert != 1) {
            root.setCode(500);
            root.setMsg("添加失败：" + bankInterfaceHistory.toString());
            return root;
        } else {
            root.setCode(0);
            root.setMsg("添加成功：" + bankInterfaceHistory.toString());
            return root;
        }

        //}

    }

    @ApiOperation(value = "日常收费 获取本季暖费信息")
    @RequestMapping("/getThisAnnualHeatFeeInfo")
    public Root getThisAnnualHeatFeeInfo(@RequestParam String consumerId,
                                         @RequestParam String annual,
                                         @RequestParam(value = "isClearChecked") boolean isClearChecked,
                                         @RequestParam(value = "paymentMethod") String paymentMethod,
                                         @RequestParam(value = "wantChange") boolean wantChange) {

        Root root = new Root();
        UserYearHeat byUserAndAnnual = userYearHeatService.findByUserAndAnnual(consumerId, annual);
        if (byUserAndAnnual == null) {
            root.setCode(500);
            root.setMsg("用户年度采暖信息表没有记录");
            return root;
        }
        BigDecimal oldYearOweTotal = BigDecimal.ZERO;//往年欠费
        oldYearOweTotal = userYearHeatService.getOldYearOwe(consumerId, annual);//获取往年欠费
        if (oldYearOweTotal == null) {
            oldYearOweTotal = BigDecimal.ZERO;
        }

        Map<String, Object> map = getUserHeatFeeMapInfo(byUserAndAnnual, oldYearOweTotal);
        BigDecimal needTotal = (BigDecimal) (map.get("needTotal"));
        if (isClearChecked) {//如果勾选了清欠按钮
            needTotal = needTotal.add(oldYearOweTotal);//往年欠费
        }

        map.put("realNeedTotal", needTotal);//实际需要交的钱，有可能包含欠款。传回这个只是为了平账的时候用
        //处理收费精度
        if (wantChange) { //若为找零模式则需要处理收费精度
            needTotal = MoneyConverter.Convert(consumerId.substring(0, 5), paymentMethod, needTotal);
        }

        map.put("needTotal", needTotal.toPlainString());

        root.setCode(0);
        root.setMsg("操作成功");
        root.setData(map);
        return root;

    }


    private Map<String, Object> getUserHeatFeeMapInfo(UserYearHeat userYearHeat, BigDecimal oldYearOweTotal) {
        if (userYearHeat.getSumAccount() == null) {
            userYearHeat.setSumAccount(BigDecimal.ZERO);
        }
        if (userYearHeat.getSumReceivable() == null) {
            userYearHeat.setSumReceivable(BigDecimal.ZERO);
        }

        BigDecimal receivableTotal = BigDecimal.ZERO;//费用合计
        BigDecimal accountTotal = BigDecimal.ZERO;//缴费合计
        BigDecimal needTotal = BigDecimal.ZERO;//应缴金额
        BigDecimal balanceTotal = BigDecimal.ZERO;//账户余额，这个应该是应缴金额的相反数。如果应缴金额为负数，那么应缴金额应该为0，而账户余额这时候应该是正数

        receivableTotal = userYearHeat.getSumReceivable();//费用合计
        accountTotal = userYearHeat.getSumAccount();//缴费合计
        String annual = userYearHeat.getAnnual();
        String companyId = userYearHeat.getCompanyId();


        //应缴金额
        //费用合计 大于 缴费合计
        needTotal = receivableTotal.subtract(accountTotal);
        balanceTotal = accountTotal.subtract(receivableTotal);//账户余额

        Map<String, Object> map = new HashMap<>();
        map.put("receivableTotal", receivableTotal);//费用合计
        map.put("accountTotal", accountTotal);//缴费合计
        map.put("oldYearOweTotal", oldYearOweTotal);//往年欠费
        map.put("needTotal", needTotal);//应缴金额——这个可以作为当年欠费（往年收费界面中的“陈欠合计”）
        map.put("balanceTotal", balanceTotal);//账户余额
        map.put("annual", annual);
        map.put("companyId", companyId);

        return map;
    }


    private Map<String, Object> getUserHeatFeeMapInfo(UserYearHeat userYearHeat, String paymentMethod, boolean wantChange) {
        if (userYearHeat.getSumAccount() == null) {
            userYearHeat.setSumAccount(BigDecimal.ZERO);
        }
        if (userYearHeat.getSumReceivable() == null) {
            userYearHeat.setSumReceivable(BigDecimal.ZERO);
        }

        BigDecimal receivableTotal = BigDecimal.ZERO;//费用合计
        BigDecimal accountTotal = BigDecimal.ZERO;//缴费合计
        BigDecimal needTotal = BigDecimal.ZERO;//应缴金额
        BigDecimal balanceTotal = BigDecimal.ZERO;//账户余额，这个应该是应缴金额的相反数。如果应缴金额为负数，那么应缴金额应该为0，而账户余额这时候应该是正数

        receivableTotal = userYearHeat.getSumReceivable();//费用合计
        accountTotal = userYearHeat.getSumAccount();//缴费合计
        String annual = userYearHeat.getAnnual();
        String companyId = userYearHeat.getCompanyId();


        //应缴金额
        //费用合计 大于 缴费合计
        if (receivableTotal.compareTo(accountTotal) == 1) {
            needTotal = receivableTotal.subtract(accountTotal);
        }
        balanceTotal = accountTotal.subtract(receivableTotal);//账户余额
        Map<String, Object> map = new HashMap<>();
        map.put("receivableTotal", receivableTotal);//费用合计
        map.put("accountTotal", accountTotal);//缴费合计
        map.put("realNeedTotal", needTotal);//应缴金额——这个可以作为当年欠费（往年收费界面中的“陈欠合计”）
        if (wantChange) { //若为找零模式则需要处理收费精度
            needTotal = MoneyConverter.Convert(userYearHeat.getCompanyId(), paymentMethod, needTotal);
        }
        map.put("needTotal", needTotal.toPlainString());//应缴金额——这个可以作为当年欠费（往年收费界面中的“陈欠合计”）
        map.put("balanceTotal", balanceTotal);//账户余额
        map.put("annual", annual);
        map.put("companyId", companyId);

        return map;
    }


    @ApiOperation(value = "获取 当前采暖季（为第三方调用）")
    @RequestMapping("/getCurrentHeatAnnualByConsumerIdEx")
    public Root getCurrentHeatAnnualByConsumerIdEx(@RequestParam Map<String, Object> params) {
        String consumerId = params.get("consumerId") == null ? null : params.get("consumerId").toString();
        consumerId = new Gson().fromJson(consumerId, String.class);
        return getCurrentHeatAnnualByConsumerId(consumerId);
    }


    @ApiOperation(value = "获取 当前采暖季")
    @RequestMapping("/getCurrentHeatAnnualByConsumerId")
    public Root getCurrentHeatAnnualByConsumerId(@RequestParam String consumerId) {
        return getCurrentHeatAnnualByCompanyId(consumerId.substring(0, 5));
    }

    @ApiOperation(value = "获取 当前采暖季")
    @RequestMapping("/getCurrentHeatAnnualByCompanyId")
    public Root getCurrentHeatAnnualByCompanyId(@RequestParam String companyId) {

        Root root = new Root();
        String currentHeatAnnual = priceDefineService.findCurrentHeatAnnual(companyId);
        root.setCode(0);
        root.setMsg("获取成功");
        root.setData(currentHeatAnnual);
        return root;
    }


    @ApiOperation(value = "往年收费 获取往年暖费陈欠信息")
    @RequestMapping("/getAllAnnualHeatFeeInfo")
    public Root getAllAnnualHeatFeeInfo(@RequestParam String consumerId,
                                        @RequestParam(value = "paymentMethod") String paymentMethod,
                                        @RequestParam(value = "wantChange") boolean wantChange,
                                        @RequestParam(value = "selectedAnnuals[]", required = false) List<String> selectedAnnuals,
                                        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        Root root = new Root();
        String currentHeatAnnual = priceDefineService.findCurrentHeatAnnual(consumerId.substring(0, 5));
        BigDecimal oldYearOweTotal = userYearHeatService.getOldYearOwe(consumerId, currentHeatAnnual);
        if (oldYearOweTotal == null) {
            oldYearOweTotal = BigDecimal.ZERO;
        }

        PageInfo<UserYearHeat> userYearHeats = userYearHeatService.findByUserForOldAnnual(consumerId, currentHeatAnnual, "desc", "annual", page, pageSize);

        TreeMap<String, Object> mapGlobal = new TreeMap<String, Object>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        BigDecimal needTotal = BigDecimal.ZERO;
        BigDecimal realNeedTotal = BigDecimal.ZERO;
        List<Map<String, Object>> lst = new ArrayList<>();
        for (UserYearHeat userYearHeat : userYearHeats.getList()) {
            Map<String, Object> map = getUserHeatFeeMapInfo(userYearHeat, paymentMethod, wantChange);
            lst.add(map);
            if (selectedAnnuals != null) {
                for (String annual : selectedAnnuals) {
                    if (userYearHeat.getAnnual().equals(annual)) {
                        needTotal = needTotal.add(new BigDecimal((String) map.get("needTotal")));
                        realNeedTotal = realNeedTotal.add((BigDecimal) map.get("realNeedTotal"));
                        break;
                    }
                }
            }
        }
        mapGlobal.put("detail", lst);
        mapGlobal.put("OweTotal", oldYearOweTotal);//各年度总的欠费金额
        mapGlobal.put("realNeedTotal", realNeedTotal);
        mapGlobal.put("needTotal", needTotal.toPlainString());
        root.setCode(0);
        root.setData(mapGlobal);
        root.setCond(getCond(page, pageSize, (int) userYearHeats.getTotal(),
                null, "annual"));
        return root;

    }

    @ApiOperation(value = "获取 暖费收取 历年缴费记录")
    @RequestMapping("/getUserYearAccountDetail")
    public Root getUserYearAccountDetail(@RequestParam String consumerId,
                                         @RequestParam(value = "annual", required = false) String annual,
                                         @RequestParam(value = "order", required = false) String order,
                                         @RequestParam(value = "sortby", required = false) String sortby,
                                         @RequestParam Integer page,
                                         @RequestParam Integer pageSize) {

        Root root = new Root();

        PageInfo<UserYearAccountDetail> userYearAccountDetails = userYearAccountDetailService.findByUserAndAnnual(consumerId, annual, order, sortby, page, pageSize);

        root.setData(userYearAccountDetails.getList());
        root.setCond(getCond(page, pageSize, (int) userYearAccountDetails.getTotal(),
                null, sortby));
        return root;

    }


    @ApiOperation(value = "暖费管理 补打发票 点击打印按钮（会生成收据号）")
    @RequestMapping("/clickPrintButton")
    public Root clickPrintButton(@RequestBody List<String> primaryIds) {
        Root root = new Root();
        List<Map<String, Object>> result = new ArrayList<>();

        for (String primaryId : primaryIds) {
            UserYearAccountDetail userYearAccountDetail = userYearAccountDetailService.selectByPrimaryKey(primaryId);
            if (userYearAccountDetail == null) {
                root.setCode(500);
                root.setMsg("获取不到缴费记录");
                return root;
            }

            String receiptno = userYearAccountDetail.getReceiptno();

            if (receiptno == null || receiptno.equals("")) {
                //如果收据号为空，会生成收据号
                String flowId = FlowIdTool.GetReceiptNo(userYearAccountDetail.getCompanyId());//收据号
                userYearAccountDetail.setReceiptno(flowId);

                userYearAccountDetailService.updateByPrimaryKey(userYearAccountDetail);
            }

            result.add(makeReceipt(userYearAccountDetail));
        }

        root.setCode(0);
        root.setMsg("生成成功");
        root.setData(result);
        return root;

    }

    /**
     * @Description: 生成收据内容
     * @Param: [userYearAccountDetail]
     * @Return: java.util.Map<java.lang.String                                                                                                                               ,                                                                                                                               java.lang.Object>
     * @Author: youbc
     * @Date: 2018/9/26 10上午52
     */
    private Map<String, Object> makeReceipt(UserYearAccountDetail userYearAccountDetail) {
        String consumerId = userYearAccountDetail.getConsumerId();

        HouseInfo houseInfo = houseService.selectHouseInfoById(consumerId);
        if (houseInfo == null) {
            throw new BusinessException("获取不到对应的用户记录");
        }

        Commuity community = commuityService.getCommuityById(consumerId.substring(0, 10));
        if (community == null) {
            throw new BusinessException("获取不到对应的小区");
        }

        Map<String, Object> eachMapResult = new HashMap<>();

        String accountType = userYearAccountDetail.getAccountType();
        if (accountType == null) {
            throw new BusinessException("获取不到对应的交款方式");
        }

        TypeMst typeMstById = typeMstService.getTypeMstById(accountType);

        if (typeMstById == null) {
            throw new BusinessException("获取不到" + accountType + "对应的交款方式枚举值");
        }
        if (userYearAccountDetail.getAccountCost() == null) {
            throw new BusinessException("获取不到对应的交款金额");
        }
        Date accountTime = userYearAccountDetail.getAccountTime();

        if (accountTime == null) {
            throw new BusinessException("获取不到对应的交款日期");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String strAccountTime = sdf.format(accountTime);

        String accountCostChinaUpper = userYearAccountDetail.getAccountCost().toString();

        eachMapResult.put("companyName", houseInfo.getCompanyName());
        eachMapResult.put("userName", houseInfo.getName());
        eachMapResult.put("address", houseInfo.getAddress());

        eachMapResult.put("communityName", community.getCommuityName());
        eachMapResult.put("archivesId", community.getArchivesId());
        eachMapResult.put("accountType", typeMstById.getTypeName());
        eachMapResult.put("accountCost", userYearAccountDetail.getAccountCost());
        eachMapResult.put("notes", userYearAccountDetail.getNotes());
        eachMapResult.put("accountTime", strAccountTime);
        eachMapResult.put("annual", userYearAccountDetail.getAnnual());
        eachMapResult.put("billNo", userYearAccountDetail.getBillno());
        eachMapResult.put("isbill", userYearAccountDetail.getIsbill());
        eachMapResult.put("receiptno", userYearAccountDetail.getReceiptno());
        eachMapResult.put("primaryId", userYearAccountDetail.getPrimaryId());
        try {
            eachMapResult.put("accountCostChinaUpper", CurrencyUtil.toChinaUpper(accountCostChinaUpper));
        } catch (Exception e) {
            throw new BusinessException("对应的交款金额转换人民币大写失败");
        }
        return eachMapResult;
    }


    @ApiOperation(value = "暖费管理 补打发票 修改发票号")
    @RequestMapping("/updateBillNo")
    public Root updateBillNo(@RequestParam String primaryId, @RequestParam String billNo) {

        Root root = new Root();
        UserYearAccountDetail userYearAccountDetail = userYearAccountDetailService.selectByPrimaryKey(primaryId);
        if (userYearAccountDetail == null) {
            root.setCode(500);
            root.setMsg("获取不到缴费记录");
            return root;
        }
        userYearAccountDetail.setBillno(billNo);
        if (billNo != null && billNo != "") {
            userYearAccountDetail.setIsbill("isbill_1");
        } else {
            userYearAccountDetail.setIsbill("isbill_0");
        }

        userYearAccountDetailService.updateByPrimaryKey(userYearAccountDetail);
        root.setCode(0);
        root.setMsg("修改收据号成功");
        return root;
    }


    @ApiOperation(value = "暖费管理 补打发票 修改收据号")
    @RequestMapping("/changeReceiptno")
    public Root changeReceiptno(@RequestBody List<ReceiptListDTO> list) {

        Root root = new Root();
        List<String> result = Lists.newArrayList();

        for (ReceiptListDTO dto : list) {
            String primaryId = dto.getPrimaryId();
            String receiptno = dto.getReceiptno();
            if (receiptno.equals("")) {
                root.setCode(500);
                root.setMsg("收据号不能为空");
                return root;
            }
            UserYearAccountDetail userYearAccountDetail = userYearAccountDetailService.selectByPrimaryKey(primaryId);
            if (userYearAccountDetail == null) {
                root.setCode(500);
                root.setMsg("获取不到缴费记录");
                return root;
            }

            userYearAccountDetail.setReceiptno(receiptno);
            userYearAccountDetailService.updateByPrimaryKey(userYearAccountDetail);
            result.add(receiptno);
        }

        root.setCode(0);
        root.setMsg("修改收据号成功");
        root.setData(result);
        return root;

    }

    @ApiOperation(value = "获取 暖费管理 补打发票 信息")
    @RequestMapping("/getUserYearAccountDetailPrintInfo")
    public Root getUserYearAccountDetailPrintInfo(@RequestBody List<String> primaryIds) {
        Root root = new Root();
        List<Map<String, Object>> eachMapResults = new ArrayList<>();
        for (String primaryId : primaryIds) {
            UserYearAccountDetail userYearAccountDetail = userYearAccountDetailService.selectByPrimaryKey(primaryId);
            if (userYearAccountDetail == null) {
                throw new BusinessException("获取不到缴费记录");
            }
            eachMapResults.add(makeReceipt(userYearAccountDetail));
        }

        root.setCode(0);
        root.setMsg("转换成功");
        root.setData(eachMapResults);
        return root;
    }


    @ApiOperation(value = "往年收费 点击缴费按钮进行暖费收取 ")
    @RequestMapping("/addUserYearAccountDetailForOldYear")
    @Transactional
    public Root addUserYearAccountDetailForOldYear(@RequestBody @Valid UserYearAccountDetailForOldYearVO userYearAccountDetailForOldYearVO, BindingResult bindingResult) {
        BindingResultHandler.handle(bindingResult);
        Root root = new Root();
        List<String> primaryids = new ArrayList<>();//打印需传的id
        BigDecimal accountCost = userYearAccountDetailForOldYearVO.getAccountCost();//实缴金额。
        BigDecimal needTotal = userYearAccountDetailForOldYearVO.getNeedTotal();//总的应缴金额，金额精度处理后的
        //BigDecimal realNeedTotal = userYearAccountDetailForOldYearVO.getRealNeedTotal();//总的应缴金额，暂时没什么用
        List<AnnualsVO> selectedAnnualParams = userYearAccountDetailForOldYearVO.getSelectedAnnualParams();
        String companyId = userYearAccountDetailForOldYearVO.getCompanyId();
        String consumerId = userYearAccountDetailForOldYearVO.getConsumerId();
        String accountType = userYearAccountDetailForOldYearVO.getAccountType();
        boolean isWantChange = userYearAccountDetailForOldYearVO.isWantChange();
        if (accountCost.compareTo(needTotal) < 0) {
            throw new BusinessException("缴费失败！实缴金额必须大于应缴金额！");
        }
        if (accountCost.compareTo(needTotal) < 0) {//实缴金额 < 应缴金额
            throw new BusinessException("实缴金额 小于 应缴金额");
        }

        if (selectedAnnualParams == null || selectedAnnualParams.size() == 0) {
            throw new BusinessException("请输入每年的具体缴费金额");
        }

        for (AnnualsVO annualsVO : selectedAnnualParams) {//开始逐年交往年的欠费
            BigDecimal needTotalForOneyear = annualsVO.getNeedTotal();
            BigDecimal realNeedTotalForOneyear = annualsVO.getRealNeedTotal();
            String annual = annualsVO.getAnnual();
            String primaryId = IDWorker.uuid();
            //只有1找零模式，并且2实际交的钱大于等于经过精度处理后的应缴金额，才需要处理金额精度做做平账处理，同时用户当年应缴费用也要另算（即useryearheat要更新）
            if (isWantChange) {
                BigDecimal difference = needTotalForOneyear.subtract(realNeedTotalForOneyear);
                if (difference.compareTo(BigDecimal.ZERO) != 0) {
                    UserYearReceivableDetail differenceDetail = new UserYearReceivableDetail();
                    differenceDetail.setPrimaryId(primaryId);
                    differenceDetail.setCompanyId(companyId);
                    differenceDetail.setConsumerId(consumerId);
                    differenceDetail.setAnnual(annual);
                    differenceDetail.setChargingItem("charging_item_4");//费用调整
                    differenceDetail.setSum(new BigDecimal("1"));//数量
                    differenceDetail.setUnitPrice(difference);
                    differenceDetail.setDiscount(new BigDecimal("1"));
                    differenceDetail.setTotal(difference);
                    differenceDetail.setCertufucate(null);
                    differenceDetail.setApproveRes("");
                    differenceDetail.setNotes("现金精度调整");
                    differenceDetail.setCreateDate(new Date());
                    String currentUserName = userLoginService.getUserLoginById(UserUtils.getUserId()).getUsername();
                    differenceDetail.setCreateUser(currentUserName);
                    differenceDetail.setUpdateDate(null);
                    differenceDetail.setUpdateUser(null);
                    differenceDetail.setApproveSerial(null);//暂时留空
                    Root tempRoot = costManagementController.postUserYearReceivableDetail(differenceDetail, "add");
                    if (0 != tempRoot.getCode()) {
                        throw new BusinessException(tempRoot.getMsg());
                    }
                }
            }


            UserYearAccountDetail userYearAccountDetail = new UserYearAccountDetail();
            userYearAccountDetail.setCorrespondingId(primaryId);//缴费作废时用到
            userYearAccountDetail.setThirdConsumerId(null);//先传null吧
            userYearAccountDetail.setCompanyId(companyId);
            userYearAccountDetail.setConsumerId(consumerId);
            userYearAccountDetail.setAnnual(annual);
            userYearAccountDetail.setAccountCost(needTotalForOneyear);
            userYearAccountDetail.setAccountPointCost(null);//扣点 先传null吧
            userYearAccountDetail.setAccountType(accountType);//现金、刷卡、银行、微信、支付宝、网银等
            userYearAccountDetail.setAccountChannel("pay_channel_1");//大厅收费、移动支付、银行代收等
            userYearAccountDetail.setIsbill(null);
            userYearAccountDetail.setBillno(null);
            userYearAccountDetail.setBankName(null);
            userYearAccountDetail.setBankDept(null);
            userYearAccountDetail.setTeller(null);
            userYearAccountDetail.setAccountUser(null);
            userYearAccountDetail.setBankDept(null);
            userYearAccountDetail.setIsreconciliations(null);
            userYearAccountDetail.setNotes(null);//note 也需要前台传给我
            handleSingleToBeAddeDetail(userYearAccountDetail, new Date());//生成primaryid等

            //增加缴费记录
            if (userYearAccountDetailService.insertSelective(userYearAccountDetail) == 1) {
                primaryids.add(userYearAccountDetail.getPrimaryId());
                Boolean aBoolean = updateUserYearHeat(userYearAccountDetail.getConsumerId(), userYearAccountDetail.getAnnual());//触发器
                if (aBoolean == false) {
                    throw new RuntimeException("该用户未生成用户采暖年度信息表中的记录,添加缴费记录失败");
                }

            } else {
                throw new RuntimeException("添加" + annual + "年度缴费失败，触发回滚");
            }

        }

        String currentHeatAnnual = priceDefineService.findCurrentHeatAnnual(companyId);
        BigDecimal subtract = accountCost.subtract(needTotal);//实缴金额 减去 应缴金额 余下的那部分，插入当年缴费记录
        if (subtract.compareTo(BigDecimal.ZERO) > 0) {
            UserYearAccountDetail userYearAccountDetail = new UserYearAccountDetail();
            userYearAccountDetail.setThirdConsumerId(null);//先传null吧
            userYearAccountDetail.setCompanyId(companyId);
            userYearAccountDetail.setConsumerId(consumerId);
            userYearAccountDetail.setAnnual(currentHeatAnnual);
            userYearAccountDetail.setAccountCost(subtract);
            userYearAccountDetail.setAccountPointCost(null);//扣点 先传null吧
            userYearAccountDetail.setAccountType(accountType);//现金、刷卡、银行、微信、支付宝、网银等
            userYearAccountDetail.setAccountChannel("pay_channel_1");//大厅收费、移动支付、银行代收等
            userYearAccountDetail.setIsbill(null);
            userYearAccountDetail.setBillno(null);
            userYearAccountDetail.setBankName(null);
            userYearAccountDetail.setBankDept(null);
            userYearAccountDetail.setTeller(null);
            userYearAccountDetail.setAccountUser(null);
            userYearAccountDetail.setBankDept(null);
            userYearAccountDetail.setIsreconciliations(null);
            userYearAccountDetail.setNotes(null);//note 也需要前台传给我
            handleSingleToBeAddeDetail(userYearAccountDetail, new Date());
            //增加
            if (userYearAccountDetailService.insertSelective(userYearAccountDetail) == 1) {
                primaryids.add(userYearAccountDetail.getPrimaryId());
                Boolean aBoolean = updateUserYearHeat(userYearAccountDetail.getConsumerId(), userYearAccountDetail.getAnnual());//触发器
                if (!aBoolean) {
                    throw new RuntimeException("该用户未生成用户采暖年度信息表中的记录,添加缴费记录失败");
                }
            } else {
                throw new RuntimeException("添加" + currentHeatAnnual + "年度缴费失败，触发回滚");
            }
        }
        root.setCode(0);
        root.setData(primaryids);
        root.setMsg("添加成功");
        return root;


    }


    @ApiOperation(value = "暖费收取 预收暖费+日常收费")
    @RequestMapping("/addUserYearAccountDetail")
    @VisitRecord(operationName = "缴费")
    @Transactional
    public Root addUserYearAccountDetail(UserYearAccountDetail userYearAccountDetail) {
        Root root = new Root();
        root.setMsg("交费成功");
        List<String> primaryids = new ArrayList<>();//返回给前端用作打印收据、发票
        if (userYearAccountDetail.getIsClearChecked()
                && userYearAccountDetail.getRealNeedTotal().compareTo(userYearAccountDetail.getOldYearOweTotal()) != 0) {//若勾选往年欠费，判断实缴金额是否大于等于欠费金额(只交欠费的情况除外)，否则不予交费
            if (userYearAccountDetail.getAccountCost().compareTo(userYearAccountDetail.getOldYearOweTotal()) < 0) {
                throw new BusinessException("缴费失败！缴费金额必须大于清欠金额");
            }
        }

        BigDecimal needTotal0 = userYearAccountDetail.getNeedTotal();
        if (needTotal0 == null) {
            userYearAccountDetail.setNeedTotal(BigDecimal.ZERO);//防止后面BigDecimal比较null值报异常
        }

        //只有1找零模式，并且2实际交的钱大于等于经过精度处理后的应缴金额，才需要处理金额精度做做平账处理，同时用户当年应缴费用也要另算（即useryearheat要更新）
        if (userYearAccountDetail.isWantChange() && userYearAccountDetail.getAccountCost().compareTo(userYearAccountDetail.getNeedTotal()) >= 0) {
            BigDecimal difference = userYearAccountDetail.getNeedTotal().subtract(userYearAccountDetail.getRealNeedTotal());
            if (difference.compareTo(BigDecimal.ZERO) != 0) {
                UserYearReceivableDetail differenceDetail = new UserYearReceivableDetail();
                String primaryId = IDWorker.uuid();
                differenceDetail.setPrimaryId(primaryId);
                userYearAccountDetail.setCorrespondingId(primaryId);
                differenceDetail.setCompanyId(userYearAccountDetail.getCompanyId());
                differenceDetail.setConsumerId(userYearAccountDetail.getConsumerId());
                differenceDetail.setAnnual(userYearAccountDetail.getAnnual());
                differenceDetail.setChargingItem("charging_item_4");//费用调整
                differenceDetail.setSum(new BigDecimal("1"));//数量
                differenceDetail.setUnitPrice(difference);
                differenceDetail.setDiscount(new BigDecimal("1"));
                differenceDetail.setTotal(difference);
                differenceDetail.setCertufucate(null);
                differenceDetail.setApproveRes("");
                differenceDetail.setNotes("现金精度调整");
                differenceDetail.setCreateDate(new Date());
                String currentUserName = userLoginService.getUserLoginById(UserUtils.getUserId()).getUsername();
                differenceDetail.setCreateUser(currentUserName);
                differenceDetail.setUpdateDate(null);
                differenceDetail.setUpdateUser(null);
                differenceDetail.setApproveSerial(null);//暂时留空
                Root tempRoot = costManagementController.postUserYearReceivableDetail(differenceDetail, "add");
                if (0 != tempRoot.getCode()) {
                    throw new BusinessException(tempRoot.getMsg());
                }
            }
        }
        //若勾选了往年清欠，并传了往年陈欠的值，则先把往年的欠费补收
        if (userYearAccountDetail.getIsClearChecked()) {
            String currentHeatAnnual = priceDefineService.findCurrentHeatAnnual(userYearAccountDetail.getConsumerId().substring(0, 5));
            BigDecimal oldYearOweTotal = userYearHeatService.getOldYearOwe(userYearAccountDetail.getConsumerId(), currentHeatAnnual);
            if (oldYearOweTotal == null) {
                throw new BusinessException("该用户不存在往年欠费数据");
            }

            PageInfo<UserYearHeat> userYearHeats = userYearHeatService.findByUserForOldAnnual(userYearAccountDetail.getConsumerId(), currentHeatAnnual, "desc", "annual", 1, Integer.MAX_VALUE);
            BigDecimal totalOwe = BigDecimal.ZERO;
            for (UserYearHeat userYearHeat : userYearHeats.getList()) {
                Map<String, Object> map = getUserHeatFeeMapInfo(userYearHeat, null);
                UserYearAccountDetail oweUserYearAccountDetail = new UserYearAccountDetail();

                BigDecimal needTotal = (BigDecimal) map.get("needTotal");
                oweUserYearAccountDetail.setAccountCost(needTotal == null ? BigDecimal.ZERO : needTotal);
                oweUserYearAccountDetail.setThirdConsumerId(null);//先传null吧
                oweUserYearAccountDetail.setCompanyId(userYearAccountDetail.getCompanyId());
                oweUserYearAccountDetail.setConsumerId(userYearAccountDetail.getConsumerId());
                oweUserYearAccountDetail.setAnnual((String) map.get("annual"));
                oweUserYearAccountDetail.setAccountPointCost(null);//扣点 先传null吧
                oweUserYearAccountDetail.setAccountType(userYearAccountDetail.getAccountType());//现金、刷卡、银行、微信、支付宝、网银等
                oweUserYearAccountDetail.setAccountChannel("pay_channel_1");//大厅收费、移动支付、银行代收等
                oweUserYearAccountDetail.setIsbill(null);
                oweUserYearAccountDetail.setBillno(null);
                oweUserYearAccountDetail.setBankName(null);
                oweUserYearAccountDetail.setBankDept(null);
                oweUserYearAccountDetail.setTeller(null);
                oweUserYearAccountDetail.setAccountUser(null);
                oweUserYearAccountDetail.setBankDept(null);
                oweUserYearAccountDetail.setIsreconciliations(null);
                oweUserYearAccountDetail.setNotes(null);//note 也需要前台传给我
                handleSingleToBeAddeDetail(oweUserYearAccountDetail, new Date());
                primaryids.add(oweUserYearAccountDetail.getPrimaryId());
                totalOwe = totalOwe.add(oweUserYearAccountDetail.getAccountCost());
                //增加
                if (userYearAccountDetailService.insertSelective(oweUserYearAccountDetail) == 1) {
                    Boolean aBoolean = updateUserYearHeat(oweUserYearAccountDetail.getConsumerId(), oweUserYearAccountDetail.getAnnual());//触发器
                    if (!aBoolean) {
                        throw new BusinessException("该用户未生成用户采暖年度信息表中的记录,添加缴费记录失败");
                    }
                } else {
                    throw new BusinessException("添加" + map.get("annual") + "年度缴费失败，触发回滚");
                }
            }
            if (userYearAccountDetail.getOldYearOweTotal().compareTo(totalOwe) != 0) {
                throw new BusinessException("数据一致性出现错误，缴费失败！");
            }
        }

        if (userYearAccountDetail.getIsClearChecked()) {//若勾选往年欠费
            userYearAccountDetail.setAccountCost(userYearAccountDetail.getAccountCost().subtract(userYearAccountDetail.getOldYearOweTotal()));
        }
        if (userYearAccountDetail.getAccountCost().compareTo(BigDecimal.ZERO) > 0) {//看看还了往年清欠还剩多少钱，如果没钱了也没必要再交今年的了
            //ok,还剩钱，开始收取当年暖费(交的钱得减去清欠的。)
            handleSingleToBeAddeDetail(userYearAccountDetail, new Date());
            //增加
            if (userYearAccountDetailService.insertSelective(userYearAccountDetail) == 1) {

                Boolean aBoolean = updateUserYearHeat(userYearAccountDetail.getConsumerId(), userYearAccountDetail.getAnnual());//触发器
                if (!aBoolean) {
                    throw new RuntimeException("该用户未生成用户采暖年度信息表中的记录,添加缴费记录失败");
                }
                primaryids.add(userYearAccountDetail.getPrimaryId());
            } else {
                throw new BusinessException("交费失败！");
            }
        }
        root.setData(primaryids);
        return root;
    }


    @ApiOperation(value = "根据流水号 冲正(供第三方调用)")
    @RequestMapping("/reverseUserYearAccountDetailByThirdConsumerId")
    public Root reverseUserYearAccountDetailByThirdConsumerId(@RequestParam Map<String, Object> params) throws ClientProtocolException {
        Root root = new Root();

        Assert.notNull(params.get("thirdPartyFlowCode"), "thirdPartyFlowCode can not be empt");
        Assert.notNull(params.get("payMoney"), "payMoney can not be empt");
        Assert.notNull(params.get("operatorCode"), "operatorCode can not be empt");
        Assert.notNull(params.get("platformCode"), "platformCode can not be empt");

        String thirdPartyFlowCode = params.get("thirdPartyFlowCode") == null ? null : params.get("thirdPartyFlowCode").toString();
        String payMoney = params.get("payMoney") == null ? null : params.get("payMoney").toString();
        String operatorCode = params.get("operatorCode") == null ? null : params.get("operatorCode").toString();
        String platformCode = params.get("platformCode") == null ? null : params.get("platformCode").toString();
        if (platformCode == null || platformCode.length() <= 5) {
            root.setCode(500);
            root.setMsg("请传入正常的平台码");
            return root;
        }

        String companyId = platformCode.substring(0, 5);
        String bankId = platformCode.substring(5, platformCode.length());

        String businessCode = params.get("businessCode") == null ? null : params.get("businessCode").toString();
        String bankIp = params.get("bankIp") == null ? null : params.get("bankIp").toString();
        String requestData = params.get("requestData") == null ? null : params.get("requestData").toString();
        String requestDataDate = params.get("requestDataDate") == null ? null : params.get("requestDataDate").toString();

        BigDecimal payMoneyEx = new BigDecimal(payMoney);

        List<UserYearAccountDetail> byThirdConsumerId = userYearAccountDetailService.findByThirdConsumerId(thirdPartyFlowCode);

        if (byThirdConsumerId == null || byThirdConsumerId.size() == 0) {
            root.setCode(500);
            root.setMsg("根据流水号查询不到对应的缴费记录");
            return root;
        } else if (byThirdConsumerId.size() == 2) {

            BigDecimal accountCost1 = byThirdConsumerId.get(0).getAccountCost();
            BigDecimal accountCost2 = byThirdConsumerId.get(1).getAccountCost();
            BigDecimal accountCostResult = accountCost1.add(accountCost2);
            if (accountCostResult.compareTo(BigDecimal.ZERO) == 0) {
                root.setCode(500);
                root.setMsg("该流水号已冲正");
                return root;
            } else {
                root.setCode(500);
                root.setMsg("业务出错，这笔流水号两次缴费额度分别为：" + accountCost1 + "和" + accountCost2 + "并没有冲正");
                return root;
            }

        } else if (byThirdConsumerId.size() > 2) {
            root.setCode(500);
            root.setMsg("根据流水号查询到多条缴费记录：" + byThirdConsumerId.toString());
            return root;
        }
        UserYearAccountDetail userYearAccountDetail1 = byThirdConsumerId.get(0);

        if (userYearAccountDetail1.getAccountCost().compareTo(payMoneyEx) != 0) {
            root.setCode(500);
            root.setMsg("冲正金额：" + payMoney + "不等于查询到的金额" + userYearAccountDetail1.getAccountCost());
            return root;
        }


        Root root1 = cancelUserYearAccountDetail(userYearAccountDetail1.getPrimaryId(), "第三方冲正", operatorCode);

        return root1;

//        if (root1.getCode() == 0) {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String ymd = sdf.format(new Date());
//            Map<String, Object> params2 = new HashMap<>();
//
//            //params2.put("companyId", userYearAccountDetail1.getCompanyId());
//            params2.put("companyId", companyId);
//            params2.put("annual", userYearAccountDetail1.getAnnual());
//            params2.put("consumerId", userYearAccountDetail1.getConsumerId());
//            params2.put("bankId", bankId);
//            params2.put("businessCode", businessCode);
//            params2.put("requestData", requestData);
//            params2.put("requestDataDate", requestDataDate);
//
//            Map<String, Object> eachMapResult = new HashMap<>();
//            eachMapResult.put("return_code", "0000");
//            eachMapResult.put("business_code", businessCode);
//            eachMapResult.put("tips", "操作成功");
//
//            params2.put("returnData", eachMapResult.toString());
//            params2.put("returnDataDate", ymd);
//            params2.put("notes", "冲正请求");
//
//            Root root2 = null;
//            try {
//                root2 = addBankInterfaceHistory(params2);
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new ClientProtocolException("添加失败" + "：" + "添加银行履历失败1：" + e.getMessage());
//            }
//
//            if (root2.getCode() == 0) {
//                return root1;
//            } else {
//                //如果没有插入成功，那么就需要回滚事务。
//                throw new ClientProtocolException("添加失败" + "：" + "添加银行履历失败2：" + root1.getMsg());
//            }

//        } else {
//            return root1;
//        }

    }


    /**
     * @Description: 检查银行对账参数
     * @Param: [param, msg]
     * @Return: void
     * @Author: youbc
     * @Date: 2018/9/13 9上午04
     */
    private void checkParam(String param, String msg) {
        if (StringUtils.isEmpty(param)) {
            String message = "请传入" + msg;
            log.error(message);
            throw new BusinessException(message);
        }
    }

    /**
     * @Description: 银行对账
     * @Param: [bankChargeDTO, bindingResult]
     * @Return: cn.plou.web.common.config.common.Root
     * @Author: youbc
     * @Date: 2018/9/11 15下午48
     */
    @PostMapping("bank-charge")
    @Transactional
    public Root bankCharge(@RequestParam Map<String, String> params) throws ClientProtocolException {
        String reconciliationsDate = params.get("reconciliationsDate");
        checkParam(reconciliationsDate, "对账日期");
        String bankIp = params.get("bankIp");
        checkParam(bankIp, "银行 IP");
        String platformCode = params.get("platformCode");
        checkParam(platformCode, "平台码");
        if (platformCode == null || platformCode.length() <= 5) {
            throw new ClientProtocolException("对账失败" + "：" + "请传入正常的平台码。");
        }


        String businessCode = params.get("businessCode") == null ? null : params.get("businessCode").toString();
        //String bankIp = params.get("bankIp") == null ? null : params.get("bankIp").toString();
        String requestData = params.get("requestData") == null ? null : params.get("requestData").toString();
        String requestDataDate = params.get("requestDataDate") == null ? null : params.get("requestDataDate").toString();

        BankChargeDTO bankChargeDTO = new BankChargeDTO();
        bankChargeDTO.setReconciliationsDate(reconciliationsDate);
        bankChargeDTO.setBankIp(bankIp);
        bankChargeDTO.setPlatformCode(platformCode);
        String dailyReconciliation = "";
        try {
            dailyReconciliation = bankReconciliationsHeadService.getDailyReconciliation(platformCode, reconciliationsDate);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClientProtocolException("对账失败" + "：" + e.getMessage());
        }

        String companyId = platformCode.substring(0, 5);
        String bankId = platformCode.substring(5, platformCode.length());


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ymd = sdf.format(new Date());


        String annual = "";
        String consumerId = "";
        if (companyId == null || companyId.equals("")) {
            throw new ClientProtocolException("对账失败" + "：" + "对账记录对应的公司id为空。");
        }
//            if (annual == null || annual.equals("")) {
//                throw new ClientProtocolException("对账失败" + "：" + "对账记录对应的采暖季为空。");
//            }
//            if (consumerId == null || consumerId.equals("")) {
//                throw new ClientProtocolException("对账失败" + "：" + "对账记录对应的用户id为空。");
//            }

//            Map<String, Object> params2 = new HashMap<>();
//            params2.put("companyId", companyId);
//            params2.put("annual", annual);
//            params2.put("consumerId", consumerId);
//            //上面先传的null，这里谋划一下该怎么传入这三个值，因为可能是多个
////        params2.put("companyId", userYearAccountDetail1.getCompanyId());
////        params2.put("annual", userYearAccountDetail1.getAnnual());
////        params2.put("consumerId", userYearAccountDetail1.getConsumerId());
//            params2.put("bankId", bankId);
//            params2.put("businessCode", businessCode);
//            params2.put("requestData", requestData);
//            params2.put("requestDataDate", requestDataDate);
//
//            Map<String, Object> eachMapResult = new HashMap<>();
//            eachMapResult.put("return_code", "0000");
//            eachMapResult.put("business_code", businessCode);
//            eachMapResult.put("tips", dailyReconciliation);
//
//            params2.put("returnData", eachMapResult.toString());
//            params2.put("returnDataDate", ymd);
//            params2.put("notes", "银行对账");
//
//            Root root2 = null;
//            try {
//                root2 = addBankInterfaceHistory(params2);
//                if (root2.getCode() != 0) {
//                    //如果没有插入成功，那么就需要回滚事务。
//                    throw new ClientProtocolException("添加失败" + "：" + "添加银行履历失败1：" + root2.getMsg());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new ClientProtocolException("添加失败" + "：" + "添加银行履历失败2：" + e.getMessage());
//            }


        Root root = new Root();
        root.setCode(0);
        root.setMsg(dailyReconciliation);
        return root;


    }


    @ApiOperation(value = "增加 暖费收取 预收暖费(供第三方调用)")
    @RequestMapping("/addUserYearAccountDetailEx")
    public Root addUserYearAccountDetailEx(@RequestParam Map<String, Object> params) throws ClientProtocolException {
        Root root = new Root();


//        验证码	verification_code
//        业务码	business_code
//        平台码	platform_code
//        代收方式	pay_mode
//        第三方流水号	third_party_flow_code
//        客户ID	consumer_id
//        采暖季	annual
//        交费金额	pay_money
//        交费时间	pay_date
//        操作员编号	operator_code
//        推票手机号	push_tel
//        推票邮箱	push_mail


        String consumerId = params.get("consumerId") == null ? null : params.get("consumerId").toString();
        String annual = params.get("annual") == null ? null : params.get("annual").toString();

        String payMode = params.get("payMode") == null ? null : params.get("payMode").toString();//1-柜台，2-网上银行，3-手机银行，4-微信，0-其他  注：如果不区分以上方式，此项传空。
        String thirdPartyFlowCode = params.get("thirdPartyFlowCode") == null ? null : params.get("thirdPartyFlowCode").toString();
        String payMoney = params.get("payMoney") == null ? null : params.get("payMoney").toString();
        String payDate = params.get("payDate") == null ? null : params.get("payDate").toString();
        String operatorCode = params.get("operatorCode") == null ? null : params.get("operatorCode").toString();
        String pushTel = params.get("pushTel") == null ? null : params.get("pushTel").toString();
        String pushMail = params.get("pushMail") == null ? null : params.get("pushMail").toString();


        String businessCode = params.get("businessCode") == null ? null : params.get("businessCode").toString();
        String bankIp = params.get("bankIp") == null ? null : params.get("bankIp").toString();
        String requestData = params.get("requestData") == null ? null : params.get("requestData").toString();
        String requestDataDate = params.get("requestDataDate") == null ? null : params.get("requestDataDate").toString();
        String platformCode = params.get("platformCode") == null ? null : params.get("platformCode").toString();

        if (platformCode == null || platformCode.length() <= 5) {
            root.setCode(500);
            root.setMsg("请传入正常的平台码");
            return root;
        }


        if (consumerId == null || consumerId.equals("")) {
            root.setCode(500);
            root.setMsg("consumerId参数缺失");
            return root;
        }
        if (annual == null || annual.equals("")) {
            root.setCode(500);
            root.setMsg("annual参数缺失");
            return root;
        }
        if (payMode == null || payMode.equals("")) {
            root.setCode(500);
            root.setMsg("payMode参数缺失");
            return root;
        }
        if (thirdPartyFlowCode == null || thirdPartyFlowCode.equals("")) {
            root.setCode(500);
            root.setMsg("thirdPartyFlowCode参数缺失");
            return root;
        }

        if (payMoney == null || payMoney.equals("")) {
            root.setCode(500);
            root.setMsg("payMoney参数缺失");
            return root;
        }
        if (payDate == null || payDate.equals("")) {
            root.setCode(500);
            root.setMsg("payDate参数缺失");
            return root;
        }
        if (operatorCode == null || operatorCode.equals("")) {
            root.setCode(500);
            root.setMsg("operatorCode参数缺失");
            return root;
        }

        //这里得加上必选参数判断
        Date date = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = format.parse(payDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        UserYearAccountDetail userYearAccountDetail = new UserYearAccountDetail();

        userYearAccountDetail.setThirdConsumerId(thirdPartyFlowCode);
        userYearAccountDetail.setCompanyId(consumerId.substring(0, 5));
        userYearAccountDetail.setConsumerId(consumerId);
        userYearAccountDetail.setAnnual(annual);
        userYearAccountDetail.setAccountCost(new BigDecimal(payMoney));
        userYearAccountDetail.setAccountPointCost(null);
        userYearAccountDetail.setAccountTime(date);


        if (payMode != null) {
            switch (payMode) {
                case "0":
                    userYearAccountDetail.setAccountType(null);//其他？传null吧
                    break;
                case "1":
                    userYearAccountDetail.setAccountType("payment_method_1");//现金
                    break;
                case "2":
                    userYearAccountDetail.setAccountType("payment_method_2");//网上银行、手机银行，暂定为刷卡吧
                    break;
                case "3":
                    userYearAccountDetail.setAccountType("payment_method_2");//网上银行、手机银行，暂定为刷卡吧
                    break;
                case "4":
                    userYearAccountDetail.setAccountType("payment_method_3");
                    break;
                default:
                    root.setCode(500);
                    root.setMsg("payMode参数非法");
                    return root;
            }
        } else {
            userYearAccountDetail.setAccountType(null);
        }

        userYearAccountDetail.setAccountChannel("pay_channel_3");//这个应该是银行代收吧
        userYearAccountDetail.setIsbill(null);
        userYearAccountDetail.setBillno(null);
        userYearAccountDetail.setBankName(null);
        userYearAccountDetail.setBankDept(null);
        userYearAccountDetail.setTeller(operatorCode);
        userYearAccountDetail.setAccountUser(null);
        userYearAccountDetail.setIsreconciliations(null);
        userYearAccountDetail.setNotes("第三方接入平台入账");
        handleSingleToBeAddeDetail(userYearAccountDetail, date);

        //增加
        if (userYearAccountDetailService.insertSelective(userYearAccountDetail) == 1) {

            Boolean aBoolean = updateUserYearHeat(userYearAccountDetail.getConsumerId(), userYearAccountDetail.getAnnual());//触发器

            if (aBoolean == false) {
                throw new RuntimeException("该用户未生成用户采暖年度信息表中的记录,添加缴费记录失败");
            }

            String companyId = platformCode.substring(0, 5);
            String bankId = platformCode.substring(5, platformCode.length());
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String ymd = sdf.format(new Date());
//            Map<String, Object> params2 = new HashMap<>();
//            //params2.put("companyId", consumerId.substring(0, 5));
//            params2.put("companyId", companyId);
//            params2.put("annual", annual);
//            params2.put("consumerId", consumerId);
//            params2.put("bankId", bankId);
//            params2.put("businessCode", businessCode);
//            params2.put("requestData", requestData);
//            params2.put("requestDataDate", requestDataDate);
//
//            Map<String, Object> eachMapResult = new HashMap<>();
//            eachMapResult.put("return_code", "0000");
//            eachMapResult.put("business_code", businessCode);
//            eachMapResult.put("tips", "操作成功");
//
//            params2.put("returnData", eachMapResult.toString());
//            params2.put("returnDataDate", ymd);
//            params2.put("notes", "入账请求");
//
//            Root root1 = null;
//            try {
//                root1 = addBankInterfaceHistory(params2);
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new ClientProtocolException("添加失败" + "：" + "添加银行履历失败1：" + e.getMessage());
//            }
//
//            if (root1.getCode() == 0) {
//
//            } else {
//                //如果没有插入成功，那么就需要回滚事务。
//                throw new ClientProtocolException("添加失败" + "：" + "添加银行履历失败2：" + root1.getMsg());
//            }


            root.setCode(0);
            root.setMsg("添加成功");
            return root;
        } else {
            throw new ClientProtocolException("添加失败");
        }

    }


    private Boolean updateUserYearHeat(String consumerId, String annual) {
        BigDecimal totalAccountByConsumerId = userYearAccountDetailService.getTotalByConsumerId(consumerId, annual);
        if (totalAccountByConsumerId == null) totalAccountByConsumerId = BigDecimal.ZERO;
        BigDecimal totalReceivableByConsumerId = userYearReceivableDetailService.getTotalByConsumerId(consumerId, annual);
        if (totalReceivableByConsumerId == null) totalReceivableByConsumerId = BigDecimal.ZERO;
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


    //这个表其实不涉及删除和修改，但是我还是把删除接口加上了。

    @ApiOperation(value = "删除 暖费收取 预收暖费")
    @RequestMapping("/deleteUserYearReceivableDetail")
    @Transactional
    public Root deleteUserYearReceivableDetail(@RequestParam String primaryId) {

        //此处需要考虑，是否需要判断这条记录是否有其他业务使用过。

        Root root = new Root();

        UserYearAccountDetail userYearAccountDetail = userYearAccountDetailService.selectByPrimaryKey(primaryId);
        if (userYearAccountDetail == null) {
            root.setCode(500);
            root.setMsg("预收暖费记录不存在：" + primaryId + "，请传入正常值");
            return root;
        }
        int i1 = userYearAccountDetailService.deleteByPrimaryKey(primaryId);
        if (i1 != 1) {
            root.setCode(500);
            root.setMsg("删除预收暖费数据记录：" + primaryId + "，失败");
            return root;
        }

        Boolean aBoolean = updateUserYearHeat(userYearAccountDetail.getConsumerId(), userYearAccountDetail.getAnnual());//触发器
        if (aBoolean == false) {
            throw new RuntimeException("该用户未生成用户采暖年度信息表中的记录,删除失败");
        }

        root.setCode(0);
        root.setMsg("删除预收暖费数据记录：" + primaryId + "，成功");
        return root;
    }


    //滞纳金计算公式：
    //从user_year_heat表（用户粘度采暖信息表）中取，因为这里全是入网用户
    //每个用户对应的热费单价类型，然后找到“热费单价类型表”price_define，然后用“用户年度采暖费用明细表”user_year_receivable_detail中，费用项目charging_item中的“金额”
    // （金额指的是除了面积费用、热量费用、滞纳金剩下的那些）,去在“热费单价类型表”price_define中滞纳金缴纳时间段内，乘以“热费单价类型表”price_define中的滞纳金率。每天计算一个滞纳金。
    //比如说，金额为100，滞纳金率为5%，那么每天会产生5元钱的滞纳金，每天五元钱。
    //所以这个要扫描所有的用户表，计算所有的情况，会比较消耗系统资源。

    @ApiOperation(value = "计算滞纳金，供任务调度")
    @RequestMapping("/calcLateFee")
    @Transactional
    public Root calcLateFee() {

        Root root = new Root();
        Integer page = 1;
        Integer pageSize = 200;

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);


        PageInfo<UserYearHeatLateFee> userYearHeatLatesFeeForTotal = userYearHeatService.calcLateFee(dateString, null, null, page, pageSize);
        PageInfo<UserYearHeatLateFee> userYearHeatsLateFee = null;

        int pages = userYearHeatLatesFeeForTotal.getPages();
        for (int i = 0; i < pages; i++) {

            currentTime = new Date();

            page = page + i;
            userYearHeatsLateFee = userYearHeatService.calcLateFee(dateString, null, null, page, pageSize);
            List<UserYearHeatLateFee> list = userYearHeatsLateFee.getList();
            //开始批量修改
            List<UserYearReceivableDetail> userYearReceivableDetails = new ArrayList<>();
            for (UserYearHeatLateFee userYearHeatLateFee : list) {
                UserYearReceivableDetail userYearReceivableDetail = new UserYearReceivableDetail();
                //String annual = userYearHeatLateFee.getAnnual();
                String consumerId = userYearHeatLateFee.getConsumerId();
                String companyId = userYearHeatLateFee.getCompanyId();
                String lateFee = userYearHeatLateFee.getLateFee();
                String annual = userYearHeatLateFee.getAnnual();

                userYearReceivableDetail.setCompanyId(companyId);
                userYearReceivableDetail.setConsumerId(consumerId);
                userYearReceivableDetail.setAnnual(annual);
                userYearReceivableDetail.setChargingItem("charging_item_7");
                userYearReceivableDetail.setSum(BigDecimal.ZERO);
                userYearReceivableDetail.setUnitPrice(BigDecimal.ZERO);
                userYearReceivableDetail.setDiscount(BigDecimal.ONE);
                userYearReceivableDetail.setTotal(new BigDecimal(lateFee));
                userYearReceivableDetail.setCreateDate(currentTime);
                userYearReceivableDetail.setCreateUser(UserUtils.getUserId());
                userYearReceivableDetail.setUpdateDate(currentTime);
                userYearReceivableDetail.setUpdateUser(UserUtils.getUserId());

                userYearReceivableDetails.add(userYearReceivableDetail);


            }

            userYearReceivableDetailService.batchUpdate(userYearReceivableDetails);

        }

        root.setCode(0);
        root.setData("生成滞纳金数据成功");
        return root;

    }


    @ApiOperation(value = "余额提现,传入的提现额度为正值，后台会转为负值")
    @RequestMapping("/balanceWithdrawCash")
    @Transactional
    public Root balanceWithdrawCash(UserYearAccountDetail userYearAccountDetail) {

        Root root = new Root();

        Assert.notNull(userYearAccountDetail.getConsumerId(), "传入的用户id不能为空");
        Assert.notNull(userYearAccountDetail.getAnnual(), "传入的采暖季不能为空");

        UserYearHeat userYearHeat = userYearHeatService.findByUserAndAnnual(userYearAccountDetail.getConsumerId(), userYearAccountDetail.getAnnual());
        if (userYearHeat == null) {
            //理论上不会走这里
            root.setCode(500);
            root.setMsg("获取不到用户采暖季数据");
            return root;
        }
        BigDecimal marginNow = userYearHeat.getMarginNow();//当前可以提现的余额

        BigDecimal withdrawCashValue = userYearAccountDetail.getAccountCost();// BigDecimal.ZERO.subtract(userYearAccountDetail.getAccountCost());

        userYearAccountDetail.setAccountType("payment_method_1");//提现 用现金吧


        if (withdrawCashValue.compareTo(marginNow) == 1) {
            root.setCode(500);
            root.setData("提现额度：" + withdrawCashValue + "大于余额：" + marginNow);
            return root;
        }


        String notesOld = userYearAccountDetail.getNotes();
        if (notesOld == null) {
            notesOld = "";
        }
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(date);
        String notesNew = "余额提现，提现额度：" + withdrawCashValue + " 时间：" + strDate + ";" + notesOld;
        if (notesNew.length() >= 255) {
            //理论上不会走这里
            root.setCode(500);
            root.setMsg("余额提现备注过长");
            return root;
        }
        userYearAccountDetail.setNotes(notesNew);
        withdrawCashValue = BigDecimal.ZERO.subtract(userYearAccountDetail.getAccountCost());
        userYearAccountDetail.setAccountCost(withdrawCashValue);
        userYearAccountDetail.setAccountChannel("pay_channel_1");//提现设置缴费通道为大厅收费，以便对账

        handleSingleToBeAddeDetail(userYearAccountDetail, new Date());
        if (userYearAccountDetailService.insertSelective(userYearAccountDetail) == 1) {

            Boolean aBoolean = updateUserYearHeat(userYearAccountDetail.getConsumerId(), userYearAccountDetail.getAnnual());//触发器
            if (!aBoolean) {
                throw new RuntimeException("该用户未生成用户采暖年度信息表中的记录,添加提现记录失败");
            }
        } else {
            throw new BusinessException("提现失败！");
        }

        root.setData(userYearAccountDetail.getPrimaryId());
        return root;
    }


    @ApiOperation(value = "作废缴费记录")
    @RequestMapping("/cancelUserYearAccountDetail")
    @Transactional
    public Root cancelUserYearAccountDetail(@RequestParam String primaryId, @RequestParam String notes, @RequestParam(value = "teller", required = false) String teller) {

        Root root = new Root();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        UserYearAccountDetail userYearAccountDetail = userYearAccountDetailService.selectByPrimaryKey(primaryId);
        if (userYearAccountDetail == null) {
            root.setCode(500);
            root.setMsg("预收暖费记录不存在：" + primaryId + "，请传入正常值");
            return root;
        }
        userYearAccountDetail.setThirdConsumerId(userYearAccountDetail.getThirdConsumerId());

        userYearAccountDetail.setAccountPointCost(null);//扣点，还未完善，先设置为null

        String notesOld = userYearAccountDetail.getNotes();
        if (notesOld == null) {
            notesOld = "";
        }
        Date date = new Date();
        String strDate = sdf.format(date);
        String notesNew = "缴费作废 作废原因：" + notes + " 缴费时间：" + userYearAccountDetail.getAccountTime() + " 流水号:" + userYearAccountDetail.getThirdConsumerId() + " 金额:" + userYearAccountDetail.getAccountCost();//"缴费作废,id：" + primaryId + " 时间：" + strDate + " 金额:" + userYearAccountDetail.getAccountCost() + " 作废原因：" + notes + ";" + notesOld;
        if (notesNew.length() >= 255) {
            root.setCode(500);
            root.setMsg("作废原因备注过长");
            return root;
        }
        userYearAccountDetail.setNotes(notesNew);

        userYearAccountDetail.setAccountCost(BigDecimal.ZERO.subtract(userYearAccountDetail.getAccountCost()));

        userYearAccountDetail.setTeller(teller);

        String userId = UserUtils.getUserId();
        //创建人
        userYearAccountDetail.setCreateUser(userId);
        userYearAccountDetail.setUpdateUser(userId);
        //创建时间
        userYearAccountDetail.setCreateDate(date);
        userYearAccountDetail.setUpdateDate(date);

        userYearAccountDetail.setAccountTime(date);


//        String PrimaryId = userYearAccountDetail.getConsumerId() + strDate;
//        userYearAccountDetail.setPrimaryId(PrimaryId);


        //开始收取当年暖费
        handleSingleToBeAddeDetail(userYearAccountDetail, date);
        //增加
        if (userYearAccountDetailService.insertSelective(userYearAccountDetail) == 1) {

            Boolean aBoolean = updateUserYearHeat(userYearAccountDetail.getConsumerId(), userYearAccountDetail.getAnnual());//触发器
            if (aBoolean == false) {
                throw new RuntimeException("该用户未生成用户采暖年度信息表中的记录,作废缴费记录失败");
            }
            //若交这笔费用的时候有在费用表进行进行冲正，还需要再做一笔相反的冲正费用矫正回来

            String correspondingId = userYearAccountDetail.getCorrespondingId();
            if (correspondingId != null) {
                UserYearReceivableDetail correspondDetail = userYearReceivableDetailService.selectByPrimaryKey(correspondingId);
                correspondDetail.setUnitPrice(BigDecimal.ZERO.subtract(correspondDetail.getUnitPrice()));
                correspondDetail.setTotal(BigDecimal.ZERO.subtract(correspondDetail.getTotal()));
                correspondDetail.setPrimaryId(IDWorker.uuid());
                Root tempRoot = costManagementController.postUserYearReceivableDetail(correspondDetail, "add");
                if (0 != tempRoot.getCode()) {
                    throw new BusinessException(tempRoot.getMsg());
                }
            }
            root.setCode(0);
            root.setMsg("操作成功");
            return root;
        } else {

            root.setCode(500);
            root.setMsg("操作失败");
            return root;
        }
    }


    @ApiOperation(value = "批量收费—— 预收暖费（按勾选模式,多户一起收）")
    @RequestMapping("/addBatchUserYearAccountDetail")
    @Transactional
    public Root addBatchUserYearAccountDetail(@RequestBody List<UserYearAccountDetail> userYearAccountDetails) {
        //要注意的点：1每个用户都要有缴费记录，2打多张发票 3：分开算精度，每户都要有费用调整4不存在清欠和找零
        Root root = new Root();
        root.setMsg("交费成功");
        List<String> primaryids = new ArrayList<>();//返回给前端用作打印收据、发票
        for (UserYearAccountDetail userYearAccountDetail : userYearAccountDetails) {
            if (userYearAccountDetail.getNeedTotal() == null) {
                userYearAccountDetail.setNeedTotal(BigDecimal.ZERO);//防止后面BigDecimal比较null值报异常
            }
            if (userYearAccountDetail.getAccountCost() == null || userYearAccountDetail.getAccountCost().compareTo(userYearAccountDetail.getNeedTotal()) != 0) {
                throw new BusinessException("批量缴费必须足额缴！");
            }
            //批量收费中，必须交够，且必须做金额精度处理
            BigDecimal difference = userYearAccountDetail.getNeedTotal().subtract(userYearAccountDetail.getRealNeedTotal());
            if (difference.compareTo(BigDecimal.ZERO) != 0) {
                UserYearReceivableDetail differenceDetail = new UserYearReceivableDetail();
                String primaryId = IDWorker.uuid();
                differenceDetail.setPrimaryId(primaryId);
                userYearAccountDetail.setCorrespondingId(primaryId);
                differenceDetail.setCompanyId(userYearAccountDetail.getCompanyId());
                differenceDetail.setConsumerId(userYearAccountDetail.getConsumerId());
                differenceDetail.setAnnual(userYearAccountDetail.getAnnual());
                differenceDetail.setChargingItem("charging_item_4");//费用调整
                differenceDetail.setSum(new BigDecimal("1"));//数量
                differenceDetail.setUnitPrice(difference);
                differenceDetail.setDiscount(new BigDecimal("1"));
                differenceDetail.setTotal(difference);
                differenceDetail.setCertufucate(null);
                differenceDetail.setApproveRes("");
                differenceDetail.setNotes("现金精度调整");
                differenceDetail.setCreateDate(new Date());
                String currentUserName = userLoginService.getUserLoginById(UserUtils.getUserId()).getUsername();
                differenceDetail.setCreateUser(currentUserName);
                differenceDetail.setUpdateDate(null);
                differenceDetail.setUpdateUser(null);
                differenceDetail.setApproveSerial(null);//暂时留空
                Root tempRoot = costManagementController.postUserYearReceivableDetail(differenceDetail, "add");
                if (0 != tempRoot.getCode()) {
                    throw new BusinessException(tempRoot.getMsg());
                }
            }
            //开始缴费
            handleSingleToBeAddeDetail(userYearAccountDetail, new Date());

            if (userYearAccountDetailService.insertSelective(userYearAccountDetail) == 1) {

                Boolean aBoolean = updateUserYearHeat(userYearAccountDetail.getConsumerId(), userYearAccountDetail.getAnnual());//触发器
                if (!aBoolean) {
                    throw new RuntimeException("存在用户未生成用户采暖年度信息表中的记录,缴费失败");
                }
                primaryids.add(userYearAccountDetail.getPrimaryId());
            } else {
                throw new BusinessException("交费失败！");
            }
        }
        root.setData(primaryids);
        return root;
    }


    /**
     * @param userYearAccountDetail（注意！！！：其中的consumerId当做单元id和楼Id来处理）
     * @return
     */
    @ApiOperation(value = "批量收费 —— 预收暖费（按楼收费或者单元收费）")
    @RequestMapping("/addBatchUserYearAccountDetailForBuildOrCommunity")
    @Transactional
    public Root addBatchUserYearAccountDetailForBuildOrCommunity(UserYearAccountDetail userYearAccountDetail) {
        //要注意的点：1每个用户都要有缴费记录，2统一打一张发票 3：统一算精度，多收或者少收的钱算在第一户上，4不存在清欠和找零
        Root root = new Root();
        root.setMsg("交费成功");
        List<String> primaryids = new ArrayList<>();//返回给前端用作打印收据、发票
        //先缴费
        List<UserYearHeat> userYearHeatList = userYearHeatService.findByIdAndAnnual(userYearAccountDetail.getConsumerId(), userYearAccountDetail.getAnnual());
        for (int i = 0; i < userYearHeatList.size(); i++) {
            BigDecimal receivable=userYearHeatList.get(i).getSumReceivable()==null?BigDecimal.ZERO:userYearHeatList.get(i).getSumReceivable();
            BigDecimal   account=userYearHeatList.get(i).getSumAccount()==null?BigDecimal.ZERO:userYearHeatList.get(i).getSumAccount();
            BigDecimal cost = receivable.subtract(account);
            if(cost.doubleValue()<=0){
                //如果cost是负值，在批量收费中不好处理
                throw new BusinessException("暂不支持缴费超额的用户批量缴费");
            }
            UserYearAccountDetail yearAccountDetailForOne = new UserYearAccountDetail();
            yearAccountDetailForOne.setCompanyId(userYearHeatList.get(i).getCompanyId());//
            yearAccountDetailForOne.setConsumerId(userYearHeatList.get(i).getConsumerId());//
            yearAccountDetailForOne.setAnnual(userYearHeatList.get(i).getAnnual());//
            yearAccountDetailForOne.setAccountCost(cost);//
            yearAccountDetailForOne.setAccountType(userYearAccountDetail.getAccountType());//
            yearAccountDetailForOne.setAccountChannel(userYearAccountDetail.getAccountChannel());//
            yearAccountDetailForOne.setNotes(userYearAccountDetail.getNotes());//
            if (i == 0) {
                //做金额精度处理，多收或者少收的钱算在第一户上
                BigDecimal difference = userYearAccountDetail.getNeedTotal().subtract(userYearAccountDetail.getRealNeedTotal());
                if (difference.compareTo(BigDecimal.ZERO) != 0) {
                    UserYearReceivableDetail differenceDetail = new UserYearReceivableDetail();
                    String primaryId = IDWorker.uuid();
                    differenceDetail.setPrimaryId(primaryId);
                    userYearAccountDetail.setCorrespondingId(primaryId);
                    differenceDetail.setCompanyId(userYearAccountDetail.getCompanyId());
                    differenceDetail.setConsumerId(userYearHeatList.get(i).getConsumerId());
                    differenceDetail.setAnnual(userYearAccountDetail.getAnnual());
                    differenceDetail.setChargingItem("charging_item_4");//费用调整
                    differenceDetail.setSum(new BigDecimal("1"));//数量
                    differenceDetail.setUnitPrice(difference);
                    differenceDetail.setDiscount(new BigDecimal("1"));
                    differenceDetail.setTotal(difference);
                    differenceDetail.setCertufucate(null);
                    differenceDetail.setApproveRes("");
                    differenceDetail.setNotes("现金精度调整");
                    differenceDetail.setCreateDate(new Date());
                    String currentUserName = userLoginService.getUserLoginById(UserUtils.getUserId()).getUsername();
                    differenceDetail.setCreateUser(currentUserName);
                    differenceDetail.setUpdateDate(null);
                    differenceDetail.setUpdateUser(null);
                    differenceDetail.setApproveSerial(null);//暂时留空
                    Root tempRoot = costManagementController.postUserYearReceivableDetail(differenceDetail, "add");
                    if (0 != tempRoot.getCode()) {
                        throw new BusinessException(tempRoot.getMsg());
                    }
                    yearAccountDetailForOne.setAccountCost(yearAccountDetailForOne.getAccountPointCost().add(difference));
                }
            }
            //开始缴费
            handleSingleToBeAddeDetail(yearAccountDetailForOne, new Date());
            //这里要加一个循环 缴费
            if (userYearAccountDetailService.insertSelective(yearAccountDetailForOne) == 1) {
                Boolean aBoolean = updateUserYearHeat(yearAccountDetailForOne.getConsumerId(), yearAccountDetailForOne.getAnnual());//触发器
                if (!aBoolean) {
                    throw new RuntimeException("存在用户未生成用户采暖年度信息表中的记录,缴费失败");
                }
                primaryids.add(yearAccountDetailForOne.getPrimaryId());
            } else {
                throw new BusinessException("交费失败！");
            }
        }
        root.setData(primaryids);
        return root;
    }


    /**
     * @param mapList 格式：[{consumerId:xxxx,annual:xxxx}]
     * @return getBatchThisAnnualHeatFeeInfo?paymentMethod=xxxxx
     * 无需考虑清欠部分 ，只需要处理金额精度;也不用考虑找零，必须要按找零处理（不然剩下的钱入到谁的账户上呢）
     */
    @ApiOperation(value = "批量收费——获取预收暖费信息（勾选模式）")
    @RequestMapping("/getBatchPreReceiveHeatInfo")
    public Root getBatchPreReceiveHeatInfo(@RequestBody List<Map> mapList) {
        Root root = new Root();
        String paymentMethod = request.getParameter("paymentMethod");
        String consumerId = "";
        String annual = "";
        List<Map> resultList = new ArrayList();
        for (Map paramMap : mapList) {
            consumerId = (String) paramMap.get("consumerId");
            annual = (String) paramMap.get("annual");

            House houseById = houseService.getHouseById(consumerId);
            String chargeType = houseById.getChargeType();//供热收费类型

            UserYearHeat byUserAndAnnual = userYearHeatService.findByUserAndAnnual(consumerId, annual);
            if (byUserAndAnnual == null) {
                root.setCode(500);
                root.setMsg("获取预收热费信息失败：用户" + houseById.getAddress() + "年度采暖信息表没有记录");
                return root;
            }

            if (byUserAndAnnual.getSumAccount() == null) {
                byUserAndAnnual.setSumAccount(BigDecimal.ZERO);
            }
            if (byUserAndAnnual.getSumReceivable() == null) {
                byUserAndAnnual.setSumReceivable(BigDecimal.ZERO);
            }


            BigDecimal oldYearOweTotal = userYearHeatService.getOldYearOwe(consumerId, annual);//获取往年欠费

            if (oldYearOweTotal!=null&&oldYearOweTotal.compareTo(BigDecimal.ZERO) != 0) {
                throw new BusinessException("用户" + houseById.getAddress() + "存在往年陈欠部分，不能一并勾选缴费");
            }


            PriceDefine priceDefine = priceDefineService.selectByPrimaryKey(houseById.getUnitPriceType());
            if (priceDefine == null) {
                throw new BusinessException("用户" + houseById.getAddress() + "热费单价信息失败：热费单价类型表没有记录");
            }

            BigDecimal areaTotal = BigDecimal.ZERO;//面积收费
            BigDecimal heatTotal = BigDecimal.ZERO;//热量收费 王磊说，对于预收的情况，热量收费和面积收费都是这么计算的
            String heatingStatus = houseById.getHeatingStatus();
            if (heatingStatus == null) {

            } else if (heatingStatus.equals("heating_status_1")) {//供暖
                areaTotal = new BigDecimal(priceDefine.getPrePrice()).multiply(houseById.getChargeArea());
                heatTotal = areaTotal;
            } else if (heatingStatus.equals("heating_status_2")) {//停供

            } else {

            }

            BigDecimal otherTotal = userYearReceivableDetailService.getOtherTotalByConsumerId(consumerId, annual);//其他费用
            if (otherTotal == null) {
                otherTotal = BigDecimal.ZERO;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("otherTotal", otherTotal);
            BigDecimal needTotal = areaTotal.add(otherTotal); //应缴金额
            map.put("total", needTotal);
            needTotal = needTotal.subtract(byUserAndAnnual.getSumAccount());
            map.put("realNeedTotal", needTotal);
            needTotal = MoneyConverter.Convert(consumerId.substring(0, 5), paymentMethod, needTotal);

            map.put("needTotal", needTotal.toPlainString());
            map.put("areaTotal", areaTotal);
            map.put("heatTotal", heatTotal);
            map.put("consumerId", consumerId);
            map.put("annual", annual);
            resultList.add(map);
        }
        root.setCode(0);
        root.setData(resultList);
        return root;
    }


    /**
     * @param id     楼或者单元的id
     * @param annual 当前供暖年度
     * @return
     */
    @ApiOperation(value = "批量收费——获取预收暖费信息（按楼收费或按单元收费模式）")
    @RequestMapping("/getBatchPreReceiveHeatInfoForBuildOrCommunity")
    public Root getBatchPreReceiveHeatInfoForBuildOrCommunity(@RequestParam("id") String id,
                                                              @RequestParam("annual") String annual,
                                                              @RequestParam("paymentMethod") String paymentMethod) {

        Root root = new Root();
      /*  if(id.length()==13){//楼

        }else if(id.length()==15){//单元
getBatchUserYearAccountDetail
        }*/
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal areaTotal = BigDecimal.ZERO;
        BigDecimal otherTotal = BigDecimal.ZERO;
        BigDecimal needTotal = BigDecimal.ZERO;
        BigDecimal realNeedTotal = BigDecimal.ZERO;

        List<UserYearHeat> userYearHeatList = userYearHeatService.findByIdAndAnnual(id, annual);
        for (UserYearHeat userYearHeat : userYearHeatList) {
            House houseById = houseService.getHouseById(userYearHeat.getConsumerId());
            String chargeType = houseById.getChargeType();//供热收费类型
            if (userYearHeat.getSumAccount() == null) {
                userYearHeat.setSumAccount(BigDecimal.ZERO);
            }
            if (userYearHeat.getSumReceivable() == null) {
                userYearHeat.setSumReceivable(BigDecimal.ZERO);
            }


            BigDecimal oldYearOweTotal = userYearHeatService.getOldYearOwe(userYearHeat.getConsumerId(), annual);//获取往年欠费

            if (oldYearOweTotal!=null&&oldYearOweTotal.compareTo(BigDecimal.ZERO) != 0) {
                throw new BusinessException("用户" + houseById.getAddress() + "存在往年陈欠部分，不能一并勾选缴费");
            }

            PriceDefine priceDefine = priceDefineService.selectByPrimaryKey(houseById.getUnitPriceType());
            if (priceDefine == null) {
                throw new BusinessException("用户" + houseById.getAddress() + "热费单价信息失败：热费单价类型表没有记录");
            }

            BigDecimal areaTotalForOne = BigDecimal.ZERO;//面积收费
            BigDecimal heatTotalForOne = BigDecimal.ZERO;//热量收费 王磊说，对于预收的情况，热量收费和面积收费都是这么计算的
            String heatingStatus = houseById.getHeatingStatus();
            if (heatingStatus == null) {

            } else if (heatingStatus.equals("heating_status_1")) {//供暖
                areaTotalForOne = new BigDecimal(priceDefine.getPrePrice()).multiply(houseById.getChargeArea());
                heatTotalForOne = areaTotalForOne;
            } else if (heatingStatus.equals("heating_status_2")) {//停供

            } else {

            }

            BigDecimal otherTotalForOne = userYearReceivableDetailService.getOtherTotalByConsumerId(userYearHeat.getConsumerId(), annual);//其他费用
            if (otherTotalForOne == null) {
                otherTotalForOne = BigDecimal.ZERO;
            }
            Map<String, Object> map = new HashMap<>();
            areaTotal = areaTotal.add(areaTotalForOne);
            otherTotal = otherTotal.add(otherTotalForOne);
            BigDecimal needTotalForOne = areaTotalForOne.add(otherTotalForOne); //应缴金额
            total = total.add(needTotalForOne);
            needTotalForOne = needTotalForOne.subtract(userYearHeat.getSumAccount());
            realNeedTotal = realNeedTotal.add(needTotalForOne);
        }
        Map result = new HashMap();
        needTotal = MoneyConverter.Convert(id.substring(0, 5), paymentMethod, realNeedTotal);
        result.put("total", total);
        result.put("areaTotal", areaTotal);
        result.put("otherTotal", otherTotal);
        result.put("needTotal", needTotal.toPlainString());
        result.put("realNeedTotal", realNeedTotal);
        result.put("consumerId", id);
        result.put("annual", annual);
        root.setCode(0);
        root.setData(result);
        return root;
    }


    private void handleSingleToBeAddeDetail(UserYearAccountDetail userYearAccountDetail, Date accountTime) {


        if (userYearAccountDetail.getConsumerId() == null || userYearAccountDetail.getConsumerId().equals("")) {
            throw new BusinessException("用户id：" + userYearAccountDetail.getConsumerId() + "，请传入正常值");
        }

        if (userYearAccountDetail.getCompanyId() == null || userYearAccountDetail.getCompanyId().equals("")) {
            throw new BusinessException("公司id：" + userYearAccountDetail.getCompanyId() + "，请传入正常值");
        }


        if (userYearAccountDetail.getAnnual() == null || userYearAccountDetail.getAnnual().equals("")) {
            throw new BusinessException("采暖年度：" + userYearAccountDetail.getAnnual() + "，请传入正常值");
        }


        List<String> typeKbns = new ArrayList<>();
        String typeKbn = "payment_method";
        typeKbns.add(typeKbn);
        List<TypeMst> downInfoByTypeKbns = typeMstService.getDownInfoByTypeKbns(typeKbns);

        List<String> ids = new ArrayList<String>();
        if (downInfoByTypeKbns != null && downInfoByTypeKbns.size() > 0) {
            for (int i = 0; i < downInfoByTypeKbns.size(); i++) {
                TypeMst typeMst = downInfoByTypeKbns.get(i);
                ids.add(typeMst.getId());
            }
        }

        if (!ids.contains(userYearAccountDetail.getAccountType())) {

            throw new BusinessException("缴费方式枚举值不存在：" + userYearAccountDetail.getAccountType() + "，请传入正常值");
        }


        //初始化其他字段

        //创建人
        userYearAccountDetail.setCreateUser(UserUtils.getUserId());
        //创建时间
        userYearAccountDetail.setCreateDate(new Date());

        userYearAccountDetail.setAccountTime(accountTime);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmszs");

        String strDate = sdf.format(accountTime);
        String thirdConsumerId = userYearAccountDetail.getThirdConsumerId();
        String PrimaryId = "";
        if (thirdConsumerId != null && thirdConsumerId.length() >= 5) {
            PrimaryId = userYearAccountDetail.getConsumerId() + strDate + thirdConsumerId.substring(thirdConsumerId.length() - 5) + userYearAccountDetail.getAnnual();//再加上流水号（应该加五位流水号）
        } else {
            PrimaryId = userYearAccountDetail.getConsumerId() + strDate + userYearAccountDetail.getAnnual();
        }
        userYearAccountDetail.setPrimaryId(PrimaryId);
    }


}

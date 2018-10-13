package cn.plou.web.charge.chargeconfig.controller;

import cn.plou.web.charge.chargeconfig.entity.PriceDefine;
import cn.plou.web.charge.chargeconfig.entity.UserYearAccountDetail;
import cn.plou.web.charge.chargeconfig.service.PriceDefineService;
import cn.plou.web.charge.chargeconfig.service.UserYearAccountDetailService;
import cn.plou.web.common.exception.BindingResultHandler;
import cn.plou.web.common.utils.IDWorker;
import cn.plou.web.system.baseMessage.house.entity.House;
import cn.plou.web.system.baseMessage.house.service.HouseService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yinxiaochen
 * 2018/8/17 14:27
 */

@RequestMapping("${chargePath}/pricedefine")
@RestController
@Validated
public class PriceDefineController {

    @Resource
    private PriceDefineService priceDefineService;

    @Resource
    private HouseService houseService;

    @Resource
    private UserYearAccountDetailService userYearAccountDetailService;

    @RequestMapping("/addPriceDefine")
    public Boolean addPriceDefine(@Valid PriceDefine priceDefine, BindingResult bindingResult) throws Exception {
        BindingResultHandler.handle(bindingResult);
        int result = priceDefineService.addNewPriceDefine(priceDefine);
        return result == 1;
    }

    /**
     * @param primaryId 热价主键id
     * @return 检验是否有已收费用户使用了此条热价
     * @throws Exception
     */
    @RequestMapping("/checkIfPriceDefineBeUsed")
    public Object checkIfPriceDefineBeUsed(@NotEmpty(message = "primaryId不能为空") @RequestParam(value = "primaryId", defaultValue = "") String primaryId) throws Exception {

        Map<String, Boolean> map = new HashMap();
        map.put("isUsed", false);
        List<House> houseList = houseService.findByChargeType(primaryId);
        PriceDefine priceDefine = priceDefineService.selectByPrimaryKey(primaryId);
        if (houseList.size() > 0) {
            for (House house : houseList) {
                PageInfo pageInfo = userYearAccountDetailService.findByUserAndAnnual(house.getConsumerId(), priceDefine.getAnnual(), null, null, 1, Integer.MAX_VALUE);
                if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
                    map.put("isUsed", true);
                    return map;
                }
            }
            map.put("isUsed", false);
        }

        return map;
    }

    @RequestMapping("/delPriceDefine")
    public Boolean delPriceDefine(@NotEmpty(message = "primaryId不能为空") @RequestParam(value = "primaryId", defaultValue = "") String primaryId) {
        return priceDefineService.delete(primaryId) == 1;
    }


    @RequestMapping("/updatePriceDefine")
    public Boolean updatePriceDefine(@Valid PriceDefine priceDefine, BindingResult bindingResult) throws Exception {
        BindingResultHandler.handle(bindingResult);
        int result1 = priceDefineService.update(priceDefine);
        return result1 == 1;
    }


    @RequestMapping("/getPriceDefineList")
    public Object getPriceDefineList(@NotEmpty(message = "公司Id不能为空") @RequestParam(value = "companyId", defaultValue = "") String companyId) throws Exception {
        List<PriceDefine> priceDefineList = priceDefineService.findBycompanyId(companyId);
        Map<String, List<PriceDefine>> groupBy = priceDefineList.stream().collect(Collectors.groupingBy(PriceDefine::getAnnual));

        Map<String, Map> resultMap = new TreeMap<String, Map>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 降序排序
                        return obj2.compareTo(obj1);
                    }
                });
        for (String key : groupBy.keySet()) {
            int num = 0;
            Map<String, Object> tempMap = new HashMap();
            Collections.sort(groupBy.get(key), new Comparator<PriceDefine>() {
                public int compare(PriceDefine o1, PriceDefine o2) {
                    return o2.getCreateDate().compareTo(o1.getCreateDate());
                }
            });
            for (PriceDefine priceDefine : groupBy.get(key)) {
                if (priceDefine.getApproveRes().equals("未审核-内容新增") ||
                        priceDefine.getApproveRes().equals("未审核-内容更新") ||
                        priceDefine.getApproveRes().equals("未审核-热价删除")) {
                    num++;
                }
            }
            tempMap.put("unApprovedNum", num);
            tempMap.put("priceDefineList", groupBy.get(key));
            resultMap.put(key, tempMap);
        }
        return resultMap;

    }


    //@RequiresPermissions("xxxxxx")  //后续加上权限许可
    @RequestMapping("/approvalPriceDefineToPassed")
    public Boolean approvalPriceDefine(@RequestBody List<Map<String, String>> mapList) throws Exception {
        for (Map<String, String> map : mapList) {
            String primaryId = map.get("primaryId");
            String approveRes = map.get("approveRes");
            priceDefineService.approvalSinglePriceDefine(primaryId, approveRes);
        }
        return true;
    }

}

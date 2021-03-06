package cn.plou.web.charge.chargeconfig.controller;

import cn.plou.web.charge.chargeconfig.entity.PriceDefine;
import cn.plou.web.charge.chargeconfig.entity.UserYearAccountDetail;
import cn.plou.web.charge.chargeconfig.entity.UserYearHeat;
import cn.plou.web.charge.chargeconfig.entity.UserYearReceivableDetail;
import cn.plou.web.charge.chargeconfig.service.PriceDefineService;
import cn.plou.web.charge.chargeconfig.service.UserYearAccountDetailService;
import cn.plou.web.charge.chargeconfig.service.UserYearHeatService;
import cn.plou.web.charge.chargeconfig.service.UserYearReceivableDetailService;
import cn.plou.web.charge.chargeconfig.vo.ChargeHouseDataVO;
import cn.plou.web.charge.chargeconfig.vo.ChargeSumVO;
import cn.plou.web.charge.chargeconfig.vo.UserYearReceivableDetailInfo;
import cn.plou.web.common.config.common.Cond;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.CommonServiceImp;
import cn.plou.web.common.utils.ExportExcelUtil;
import cn.plou.web.common.utils.SecureUtils;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import cn.plou.web.system.baseMessage.commuity.service.CommuityService;
import cn.plou.web.system.baseMessage.house.entity.House;
import cn.plou.web.system.baseMessage.house.service.HouseService;
import cn.plou.web.system.baseMessage.house.vo.HouseInfo;
import cn.plou.web.system.baseMessage.house.vo.HouseVo;
import cn.plou.web.system.commonMessage.heatingTime.service.HeatingTimeService;
import cn.plou.web.system.commonMessage.heatingTime.vo.HeatingTimeInfo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author panziqiang
 * 2018/8/17 14:27
 */

@RequestMapping("${chargePath}/datainit")
@RestController
@Api(description = "采暖季开始数据生成")
public class DataInitController {

    @Resource
    private UserYearHeatService userYearHeatService;
    @Resource
    private HouseService houseService;
    @Resource
    private PriceDefineService priceDefineService;
    @Resource
    private UserYearReceivableDetailService userYearReceivableDetailService;
    @Resource
    private UserYearAccountDetailService userYearAccountDetailService;
    @Resource
    private HeatingTimeService heatingTimeService;
    @Resource
    private CommuityService commuityService;
    @Autowired
    private CommonServiceImp commonServiceImp;


    private Root verificationData( String annual,String communityOrHouseIds) {


        Root root = new Root();
        HouseVo houseVo = new HouseVo();
        houseVo.setNetStatus("net_status_1");//入网

        Integer page = 1;
        Integer pageSize = 200;
        long communityIdLength = 10;//小区长度是10
        long houseIdLength =19;//用户长度是19

        if(communityOrHouseIds != null || !communityOrHouseIds.equals("")){

            String[] split = communityOrHouseIds.split(",");
            if(split == null || split.length==1){
                //表示只传了单个用户号或者小区号

                String companyId = communityOrHouseIds.substring(0,5);
//                PriceDefineUseInHouse priceDefine = priceDefineService.selectByAnnualAndCompanyId(annual,companyId);
//                if(priceDefine == null){
//                    root.setCode(500);
//                    root.setMsg("热量单价类型表缺少相关数据");
//                    root.setData(true);
//                    return root;
//                }
//                List<HeatingTimeInfo> heatingTimeInfos = heatingTimeService.selectHeatingTimes(companyId, null, annual);
//                Set<String> stationIds = new HashSet<>();
//                for (HeatingTimeInfo heatingTimeInfo : heatingTimeInfos) {
//                    stationIds.add(heatingTimeInfo.getCompanyId()+","+heatingTimeInfo.getStationId());
//                }
//
//                List<String> companyIdAndStationIdsFromHeatingTimeInfo = new ArrayList<>(stationIds);

                if(communityOrHouseIds.length() == communityIdLength){//如果这是小区

                    //验证数据的合法性！
//                    Set<String> has=new HashSet<String> ();
//                    PageInfo<HouseInfo> allHouseForTotal = houseService.getAllHouse(communityOrHouseIds,  houseVo, "asc", "consumer_id", 1, 10);
//                    long total = allHouseForTotal.getTotal();
//
//                    long length = total/pageSize;
//                    long yushu = total%pageSize > 0 ?1:0;
//                    length+=yushu;
//
//                    for(int i=0;i<length;i++,page++){
//                        PageInfo<HouseInfo> allHouses = houseService.getAllHouse(communityOrHouseIds,  houseVo, "asc", "consumer_id", page, pageSize);
//                        List<HouseInfo> list = allHouses.getList();
//                        for (HouseInfo houseInfo : list) {
//                            if(houseInfo.getPrePrice() == null){
//                                root.setCode(500);
//                                root.setMsg(houseInfo.getAddress()+" 热量单价类型表缺少相关数据");
//                                root.setData(true);
//                                return root;
//                            }
//
//                            has.add(houseInfo.getCompanyId()+","+houseInfo.getStationId());//这个set里装的是字符串，前面是companyid后面是stationid
//                        }
//                    }


                    Commuity commuityById = commuityService.getCommuityById(communityOrHouseIds);
                    if(commuityById == null){
                        root.setCode(500);
                        root.setMsg("根据小区id："+communityOrHouseIds+"查找不到对应的小区");
                        root.setData(true);
                        return root;
                    }
                    String stationId = commuityById.getStationId();
                    if(stationId == null || stationId.equals("")){
                        root.setCode(500);
                        root.setMsg("根据小区id："+communityOrHouseIds+"查找不到对应的换热站id");
                        root.setData(true);
                        return root;
                    }
                    List<HeatingTimeInfo> heatingTimeInfos = heatingTimeService.selectHeatingTimes(companyId, stationId, annual);
                    if(heatingTimeInfos == null || heatingTimeInfos.size()==0){
                        root.setCode(500);
                        root.setMsg("当前采暖年度"+annual+"下，根据小区id："+communityOrHouseIds+"查找到的公司id是"+companyId+"换热站id是"+stationId+"但是查不到对应的供暖信息表记录");
                        root.setData(true);
                        return root;
                    }else if(heatingTimeInfos.size() > 1){
                        root.setCode(500);
                        root.setMsg("当前采暖年度"+annual+"下，根据小区id："+communityOrHouseIds+"查找到的公司id是"+companyId+"换热站id是"+stationId+"但是查到了多条供暖信息表记录。不确定匹配哪一条供暖信息表记录");
                        root.setData(true);
                        return root;
                    }





                    root.setCode(0);
                    root.setMsg("验证成功");
                    root.setData(true);
                    return root;
                }
                else if(communityOrHouseIds.length() == houseIdLength){
                    //如果这是用户

//                    HouseInfo houseInfo = houseService.selectHouseInfoById(communityOrHouseIds);
//                    if(houseInfo == null){
//
//                        root.setCode(500);
//                        root.setMsg("参数communityOrHouseIds"+communityOrHouseIds+"错误，请传入正确的用户id");
//                        root.setData(true);
//                        return root;
//                    }
//                    PriceDefineUseInHouse priceDefine = priceDefineService.selectByPrimaryKey(houseInfo.getUnitPriceType());
//                    if(priceDefine == null){
//                        root.setCode(500);
//                        root.setMsg(houseInfo.getAddress()+":热量单价类型表缺少相关数据");
//                        root.setData(true);
//                        return root;
//                    }


                    HouseInfo houseInfo = houseService.selectHouseInfoById(communityOrHouseIds);
                    if(houseInfo == null){
                        root.setCode(500);
                        root.setMsg("根据用户id："+communityOrHouseIds+"查找不到对应的用户");
                        root.setData(true);
                        return root;
                    }
                    String stationId = houseInfo.getStationId();
                    if(stationId == null || stationId.equals("")){
                        root.setCode(500);
                        root.setMsg("根据用户id："+communityOrHouseIds+"查找不到对应的换热站id");
                        root.setData(true);
                        return root;
                    }
                    List<HeatingTimeInfo> heatingTimeInfos = heatingTimeService.selectHeatingTimes(companyId, stationId, annual);
                    if(heatingTimeInfos == null || heatingTimeInfos.size()==0){
                        root.setCode(500);
                        root.setMsg("当前采暖年度"+annual+"下，根据用户id："+communityOrHouseIds+"查找到的公司id是"+companyId+"换热站id是"+stationId+"但是查不到对应的供暖信息表记录");
                        root.setData(true);
                        return root;
                    }else if(heatingTimeInfos.size() > 1){
                        root.setCode(500);
                        root.setMsg("当前采暖年度"+annual+"下，根据用户id："+communityOrHouseIds+"查找到的公司id是"+companyId+"换热站id是"+stationId+"但是查到了多条供暖信息表记录。不确定匹配哪一条供暖信息表记录");
                        root.setData(true);
                        return root;
                    }


                    //单个用户id
                    root.setCode(0);
                    root.setMsg("验证成功");
                    root.setData(true);
                    return root;
                }
                else{

                    root.setCode(500);
                    root.setMsg("参数communityOrHouseIds错误，请传入正确的小区id或者用户id");
                    root.setData(true);
                    return root;
                }
            }
            else{


                //这种情况，是传了一组小区ids
                if(split[0].length() == communityIdLength){
                    //如果第一个是小区长度

                    Set<String> has=new HashSet<String> ();
                    String companyIds="";
                    for (String s : split) {
                        if(s.length() != communityIdLength){

                            root.setCode(500);
                            root.setMsg("参数communityOrHouseIds错误，传入的参数不都是小区id");
                            root.setData(true);
                            return root;
                        }
                        String companyId = s.substring(0,5);
                        has.add(companyId);
                    }

                    for (String str : has) {
                        companyIds+=str+",";
                    }
                    companyIds = companyIds.substring(0,companyIds.length() - 1);
//                    List<PriceDefineUseInHouse> priceDefines = priceDefineService.selectByAnnualAndCompanyIds(annual, companyIds);
//
//                    if(priceDefines.size() != has.size()){
//                        root.setCode(500);
//                        root.setMsg("热量单价类型表中的数据个数有问题，公司id:"+companyIds+"在"+annual+"年度的记录数应该唯一");
//                        root.setData(true);
//                        return root;
//                    }




                    //验证数据的合法性！
                    Set<String> has2=new HashSet<String> ();
                    PageInfo<HouseInfo> allHouseForTotal = houseService.getAllHouse(communityOrHouseIds,  houseVo, "asc", "consumer_id", 1, 10);
                    long total = allHouseForTotal.getTotal();

                    long length = total/pageSize;
                    long yushu = total%pageSize > 0 ?1:0;
                    length+=yushu;

                    for(int i=0;i<length;i++,page++){
                        //其实system_info表中可以没有stationid
                        PageInfo<HouseInfo> allHouses = houseService.getAllHouse(communityOrHouseIds,  houseVo, "asc", "consumer_id", page, pageSize);
                        List<HouseInfo> list = allHouses.getList();
                        for (HouseInfo houseInfo : list) {
                            if(houseInfo.getStationId()==null || houseInfo.getStationId().equals("")){
                                root.setCode(500);
                                root.setMsg("根据用户id："+houseInfo.getConsumerId()+"查找不到对应的换热站id");
                                root.setData(true);
                                return root;
                            }

                            List<HeatingTimeInfo> heatingTimeInfos = heatingTimeService.selectHeatingTimes(houseInfo.getCompanyId(), houseInfo.getStationId(), annual);
                            if(heatingTimeInfos == null || heatingTimeInfos.size()==0){
                                root.setCode(500);
                                root.setMsg("当前采暖年度"+annual+"下，根据用户id："+houseInfo.getConsumerId()+"查找到的公司id是"+houseInfo.getCompanyId()+"换热站id是"+houseInfo.getStationId()+"但是查不到对应的供暖信息表记录");
                                root.setData(true);
                                return root;
                            }else if(heatingTimeInfos.size() > 1){
                                root.setCode(500);
                                root.setMsg("当前采暖年度"+annual+"下，根据用户id："+houseInfo.getConsumerId()+"查找到的公司id是"+houseInfo.getCompanyId()+"换热站id是"+houseInfo.getStationId()+"但是查到了多条供暖信息表记录。不确定匹配哪一条供暖信息表记录");
                                root.setData(true);
                                return root;
                            }

                        }
                    }


                    root.setCode(0);
                    root.setMsg("验证成功");
                    root.setData(true);
                    return root;

                }
                else if(split[0].length() == houseIdLength){//如果第一个是用户长度

                    for (String s : split) {
                        if(s.length() != houseIdLength){

                            root.setCode(500);
                            root.setMsg("参数communityOrHouseIds错误，传入的参数不都是用户id");
                            root.setData(true);
                            return root;
                        }
                    }

                    PageInfo<HouseInfo> allHouseForTotal = houseService.getAllHouse(communityOrHouseIds,  houseVo, "asc", "consumer_id", 1, 10);
                    long total = allHouseForTotal.getTotal();

                    long length = total/pageSize;
                    long yushu = total%pageSize > 0 ?1:0;
                    length+=yushu;

                    for(int i=0;i<length;i++,page++){
                        //其实system_info表中可以没有stationid
                        PageInfo<HouseInfo> allHouses = houseService.getAllHouse(communityOrHouseIds,  houseVo, "asc", "consumer_id", page, pageSize);
                        List<HouseInfo> list = allHouses.getList();
                        for (HouseInfo houseInfo : list) {
                            if(houseInfo.getStationId()==null || houseInfo.getStationId().equals("")){
                                root.setCode(500);
                                root.setMsg("根据用户id："+houseInfo.getConsumerId()+"查找不到对应的换热站id");
                                root.setData(true);
                                return root;
                            }

                            List<HeatingTimeInfo> heatingTimeInfos = heatingTimeService.selectHeatingTimes(houseInfo.getCompanyId(), houseInfo.getStationId(), annual);
                            if(heatingTimeInfos == null || heatingTimeInfos.size()==0){
                                root.setCode(500);
                                root.setMsg("当前采暖年度"+annual+"下，根据用户id："+houseInfo.getConsumerId()+"查找到的公司id是"+houseInfo.getCompanyId()+"换热站id是"+houseInfo.getStationId()+"但是查不到对应的供暖信息表记录");
                                root.setData(true);
                                return root;
                            }else if(heatingTimeInfos.size() > 1){
                                root.setCode(500);
                                root.setMsg("当前采暖年度"+annual+"下，根据用户id："+houseInfo.getConsumerId()+"查找到的公司id是"+houseInfo.getCompanyId()+"换热站id是"+houseInfo.getStationId()+"但是查到了多条供暖信息表记录。不确定匹配哪一条供暖信息表记录");
                                root.setData(true);
                                return root;
                            }

                        }
                    }


                }
                else{
                    root.setCode(500);
                    root.setMsg("参数communityOrHouseIds错误，传入的参数不都是用户id或者小区id");
                    root.setData(true);
                    return root;
                }

            }
        }else{
            //这种情况下，是生成所有数据

            //获取所有houses的companyid

            List<String> companyIdLst = houseService.getAllCompanyId();

            String companyIds="";
            for (String str : companyIdLst) {
                companyIds+=str+",";
            }
            companyIds = companyIds.substring(0,companyIds.length() - 1);
//            List<PriceDefineUseInHouse> priceDefines = priceDefineService.selectByAnnualAndCompanyIds(annual, companyIds);
//
//            if(priceDefines.size() != companyIdLst.size()){
//                root.setCode(500);
//                root.setMsg("热量单价类型表缺少相关数据");
//                root.setData(true);
//                return root;
//            }



            //验证数据的合法性！
//            Set<String> has2=new HashSet<String> ();
//            PageInfo<HouseInfo> allHouseForTotal = houseService.getAllHouse(null, null, null, null, null, houseVo, null, null, 1, 10);
//            long total = allHouseForTotal.getTotal();
//
//            long length = total/pageSize;
//            long yushu = total%pageSize > 0 ?1:0;
//            length+=yushu;
//
//            for(int i=0;i<length;i++,page++){
//                PageInfo<HouseInfo> allHouses = houseService.getAllHouse(null, null, null, null, null, houseVo, null, null, page, pageSize);
//                List<HouseInfo> list = allHouses.getList();
//                for (HouseInfo houseInfo : list) {
//                    has2.add(houseInfo.getCompanyId()+","+houseInfo.getStationId());//这个set里装的是字符串，前面是companyid后面是stationid
//                }
//            }
//
//
//            List<HeatingTimeInfo> heatingTimeInfos = heatingTimeService.selectHeatingTimes(companyIds, null, annual);
//            Set<String> stationIds = new HashSet<>();
//            for (HeatingTimeInfo heatingTimeInfo : heatingTimeInfos) {
//                stationIds.add(heatingTimeInfo.getCompanyId()+","+heatingTimeInfo.getStationId());
//            }
//
//            List<String> companyIdAndStationIdsFromHeatingTimeInfo = new ArrayList<>(stationIds);
//
//
//            //这是从用户表中取到的所有的
//            List<String> companyIdAndStationIdsFromHouseInfo = new ArrayList<>(has2);
//            //交集
//            companyIdAndStationIdsFromHeatingTimeInfo.retainAll(companyIdAndStationIdsFromHouseInfo);
//            if(companyIdAndStationIdsFromHeatingTimeInfo.size() != companyIdAndStationIdsFromHouseInfo.size()){
//                root.setCode(500);
//                root.setMsg("供暖时间信息表缺少相关数据3，供热信息表中登记个数和小区为："+stationIds.toString()+"。用户表中登记个数和小区为："+has2.toString()+"。交集不匹配");
//
//                root.setData(true);
//                return root;
//            }

            root.setCode(0);
            root.setMsg("验证成功");
            root.setData(true);
            return root;
        }

        root.setCode(0);
        root.setMsg("验证成功");
        root.setData(true);
        return root;


    }


    @ApiOperation(value = "生成 用户年度采暖信息表（user_year_heat） 数据,第二个参数采用逗号分隔小区id或者用逗号分隔的用户id，如果为null则表示全部生成,第三个参数，如果是采暖季数据生成，就不要传了（或者传入true），如果是其他处调用，就传false，这个是为了能够不删除原本记录。")
//    @RequestMapping("/initUserYearHeatData")
    @Transactional
    public Root initUserYearHeatData(@RequestParam String annual,@RequestParam String communityOrHouseIds,@RequestParam(value = "needDelete", required = false) Boolean needDelete) {

//第一步判断了，这一步不需要判断了
//        Root verificationRoot = verificationData(  annual, communityOrHouseIds);
//        if(verificationRoot.getCode() != 0){
//            return verificationRoot;
//        }
        Root root = new Root();
//        HouseVo houseVo = new HouseVo();
//        houseVo.setNetStatus("net_status_1");//入网

        long communityIdLength = 10;//小区长度是10
        long houseIdLength =19;//用户长度是19

        if(communityOrHouseIds != null || !communityOrHouseIds.equals("")){

            String[] split = communityOrHouseIds.split(",");
            if(split == null || split.length==1){
                if(communityOrHouseIds.length() == communityIdLength){//如果这是小区
                    //单个小区id
                    return initUserYearHeatData(annual,communityOrHouseIds,"community","singular",needDelete);
                }
                else if(communityOrHouseIds.length() == houseIdLength){
                //单个用户id
                    return initUserYearHeatData(annual,communityOrHouseIds,"house","singular",needDelete);
                }
                else{

                    root.setCode(500);
                    root.setMsg("参数communityOrHouseIds错误，请传入正确的小区id或者用户id");
                    root.setData(false);
                    return root;
                }
            }
            else{
                //这种情况，是传了一组小区ids
                if(split[0].length() == communityIdLength){
                    return initUserYearHeatData(annual,communityOrHouseIds,"community","complex",needDelete);
                }
//目前还不接受这种参数
                else if(split[0].length() == houseIdLength){//如果第一个是用户长度

                    for (String s : split) {
                        if(s.length() != houseIdLength){

                            root.setCode(0);
                            root.setMsg("参数communityOrHouseIds错误，传入的参数不都是用户id");
                            root.setData(true);
                            return root;
                        }
                    }

                    return initUserYearHeatData(annual,communityOrHouseIds,"house","complex",needDelete);

                }
                else{
                    root.setCode(500);
                    root.setMsg("参数communityOrHouseIds错误，传入的参数不都是用户id或者小区id");
                    root.setData(false);
                    return root;
                }

            }
        }
        else{
            //这里进行全部生成的逻辑处理，处理原则，为防止出现各种更改的复杂逻辑，先删除，再添加。
            return initUserYearHeatData(annual,null,null,null,needDelete);

        }

    }


    @ApiOperation(value = "第三个参数是小区还是房屋，第四个参数是单数还是复数")
    private Root initUserYearHeatData(String annual, String communityOrHouseIds,String communityOrHouseflag,String singularOrComplexflag, Boolean needDelete) {

        HouseVo houseVo = new HouseVo();
        houseVo.setNetStatus("net_status_1");//入网

        Root root = new Root();

        //加上锁，为了保持id的连贯性
        //synchronized(this) {
            //DecimalFormat mFormat = new DecimalFormat("000000000000000000000000000000");
            //读取houses_info中的内容。获取每户的id。然后插入表中。用户id和采暖年度，会组成一组唯一的一组数据
            PageInfo<HouseInfo> allHousesNeedGenerateForTotal = null;
            PageInfo<HouseInfo> allHousesNeedGenerate = null;
            PageInfo<HouseInfo> allHouses = null;
            Integer page = 1;
            Integer pageSize = 200;
            long housesInNetTotal = 0;//入网用户数量
            long allHousesTotal = 0;//所有用户数量
            long generatedHousesTotal = 0;//最终生成的用户数量
            //这种情况下所有的都要判断
        if(communityOrHouseIds == null && communityOrHouseflag == null && singularOrComplexflag == null){

            //先判断user_year_receivable_detail表中,对应的数据是否存在了，应该根据参数来判断
            PageInfo<UserYearReceivableDetailInfo> allUserYearReceivableDetailsByAnnual = userYearReceivableDetailService.getUserYearReceivableDetailsByConsumerId(null, null,annual,null, null, 1, 2);
            if(allUserYearReceivableDetailsByAnnual == null || allUserYearReceivableDetailsByAnnual.getTotal() == 0){
                root.setCode(500);
                root.setMsg("请先生成用户年度采暖费用明细表中数据");
                root.setData(false);
                return root;
            }

            //不必判断user_year_receivable_detail表了，逻辑过于复杂。直接生成即可，因为生成之前是先删除的操作。

            if(needDelete == null || needDelete== true){
                userYearHeatService.deleteByConsumerId(null,null,annual);
            }
            allHousesNeedGenerateForTotal = houseService.getAllHouse(null, null, null, null, null, houseVo, "asc", "consumer_id", page, pageSize);
            allHouses = houseService.getAllHouse(null, null, null, null, null, null, "asc", "consumer_id", page, pageSize);
            housesInNetTotal = allHousesNeedGenerateForTotal.getTotal();
            allHousesTotal =  allHouses.getTotal();
        }

        if(communityOrHouseflag.equals("community") && singularOrComplexflag.equals("singular")){
            if(needDelete == null || needDelete== true){
                userYearHeatService.deleteByConsumerId(null,communityOrHouseIds,annual);
            }
            allHousesNeedGenerateForTotal = houseService.getAllHouse(null, null, communityOrHouseIds, null, null, houseVo, "asc", "consumer_id", page, pageSize);
            allHouses = houseService.getAllHouse(null, null, communityOrHouseIds, null, null, null, "asc", "consumer_id", page, pageSize);
            housesInNetTotal = allHousesNeedGenerateForTotal.getTotal();
            allHousesTotal =  allHouses.getTotal();
        }
        else if(communityOrHouseflag.equals("house") && singularOrComplexflag.equals("singular")){
            if(needDelete == null || needDelete== true){
                userYearHeatService.deleteByConsumerId(communityOrHouseIds,null,annual);
            }
            House houseById = houseService.getHouseById(communityOrHouseIds);
            if(houseById != null){
                if(StringUtils.equalsIgnoreCase(houseById.getNetStatus(), "net_status_1")){
                    housesInNetTotal = 1;
                    allHousesTotal = 1;
                }else{
                    housesInNetTotal = 0;
                    allHousesTotal = 1;
                }
            }
            else{
                housesInNetTotal = 0;
                allHousesTotal = 0;
            }

        }
        else if(communityOrHouseflag.equals("community") && singularOrComplexflag.equals("complex")){
            if(needDelete == null || needDelete== true){
                userYearHeatService.deleteByConsumerIds(null,communityOrHouseIds,annual);
            }
            allHousesNeedGenerateForTotal = houseService.getAllHouse(communityOrHouseIds,  houseVo, "asc", "consumer_id", page, pageSize);
            allHouses = houseService.getAllHouse(communityOrHouseIds,  null, "asc", "consumer_id", page, pageSize);
            housesInNetTotal = allHousesNeedGenerateForTotal.getTotal();
            allHousesTotal =  allHouses.getTotal();
        }
        else if(communityOrHouseflag.equals("house") && singularOrComplexflag.equals("complex")){
            if(needDelete == null || needDelete== true){
                userYearHeatService.deleteByConsumerIds(communityOrHouseIds,null,annual);
            }

            allHousesNeedGenerateForTotal = houseService.getAllHouseByIds(communityOrHouseIds,  houseVo, "asc", "consumer_id", page, pageSize);
            allHouses = houseService.getAllHouseByIds(communityOrHouseIds,  null, "asc", "consumer_id", page, pageSize);
            housesInNetTotal = allHousesNeedGenerateForTotal.getTotal();
            allHousesTotal =  allHouses.getTotal();
        }
        else{
            root.setCode(500);
            root.setMsg("参数无效");
            root.setData(false);
            return root;
        }

            long length = housesInNetTotal/pageSize;
            long yushu = housesInNetTotal%pageSize > 0 ?1:0;
            length+=yushu;

            for(int i=0;i<length;i++){

                int len = 0;
                List<HouseInfo> allHouseLst =  null;

                if(communityOrHouseIds == null && communityOrHouseflag == null && singularOrComplexflag == null) {
                    allHousesNeedGenerate = houseService.getAllHouse(null, null, null, null, null, houseVo, "asc", "consumer_id", page, pageSize);
                    allHouseLst = allHousesNeedGenerate.getList();
                    len = allHouseLst.size();

                }
                if(communityOrHouseflag.equals("community") && singularOrComplexflag.equals("singular")){

                    allHousesNeedGenerate = houseService.getAllHouse(null, null, communityOrHouseIds, null, null, houseVo, "asc", "consumer_id", page, pageSize);
                    allHouseLst = allHousesNeedGenerate.getList();
                    len = allHouseLst.size();
                }
                else if(communityOrHouseflag.equals("house") && singularOrComplexflag.equals("singular")){
                    HouseInfo houseById = houseService.selectHouseInfoById(communityOrHouseIds);//子类 父类
                    if(houseById != null){
                        allHouseLst =  new ArrayList<HouseInfo>();
                        allHouseLst.add(houseById);
                        len = allHouseLst.size();
                    }
                }
                else if(communityOrHouseflag.equals("community") && singularOrComplexflag.equals("complex")){
                    allHousesNeedGenerate = houseService.getAllHouse(communityOrHouseIds,  houseVo, "asc", "consumer_id", page, pageSize);
                    allHouseLst = allHousesNeedGenerate.getList();
                    len = allHouseLst.size();
                }
                else if(communityOrHouseflag.equals("house") && singularOrComplexflag.equals("complex")){
                    allHousesNeedGenerate = houseService.getAllHouseByIds(communityOrHouseIds,  houseVo, "asc", "consumer_id", page, pageSize);
                    allHouseLst = allHousesNeedGenerate.getList();
                    len = allHouseLst.size();
                }


                List<UserYearHeat> userYearHeats = new ArrayList<UserYearHeat>();

//                UserYearHeat lastUserYearHeats = userYearHeatService.findLastUserYearHeats();
//                Integer newPrimaryId = null;
//                if(lastUserYearHeats == null){
//                    newPrimaryId=0;
//                }
//                else{
//                    newPrimaryId = Integer.parseInt(lastUserYearHeats.getPrimaryId());
//                }
//                newPrimaryId = newPrimaryId + 1;

                Date now = new Date();
                for(int j=0;j<len;j++){
                    HouseInfo houseInfo = allHouseLst.get(j);
                    String PrimaryId = houseInfo.getConsumerId()+annual;//mFormat.format(newPrimaryId);

                    UserYearHeat  userYearHeat = new UserYearHeat();
                    userYearHeat.setPrimaryId(PrimaryId);
                    userYearHeat.setConsumerId(houseInfo.getConsumerId());
                    userYearHeat.setAnnual(annual);
                    userYearHeat.setHeatingArea(houseInfo.getHeatingArea());
                    userYearHeat.setPayArea(houseInfo.getChargeArea());
                    userYearHeat.setSealArea(null);
                    userYearHeat.setAreaPriceType(houseInfo.getUnitPriceType());
                    userYearHeat.setHeatUserType(houseInfo.getChargeType());//这个设计好容易混淆

                    String unitPriceType = houseInfo.getUnitPriceType();
                    if(unitPriceType!=null && !unitPriceType.equals("")){
                        PriceDefine priceDefine = priceDefineService.selectByPrimaryKey(unitPriceType);
                        if(priceDefine != null){
                            String prePrice = priceDefine.getPrePrice();//解密
                            if(houseInfo.getHeatingStatus() == null || !houseInfo.getHeatingStatus().equals("heating_status_1") || prePrice == null){//供暖状态不为供暖的，就生成一些0值
                                userYearHeat.setPrePrice(BigDecimal.ZERO);
                            }else{
                                userYearHeat.setPrePrice(new BigDecimal(prePrice));
                            }
                        }
                    }else{

                        userYearHeat.setPrePrice(null);
                    }

                    if(userYearHeat.getPrePrice() != null && houseInfo.getChargeArea() != null){
                        if(houseInfo.getHeatingStatus() == null || !houseInfo.getHeatingStatus().equals("heating_status_1")){//供暖状态不为供暖的，就生成一些0值
                            userYearHeat.setAdvHeatCost(BigDecimal.ZERO);
                        }else{
                            userYearHeat.setAdvHeatCost(userYearHeat.getPrePrice().multiply(houseInfo.getChargeArea()) );
                        }
                    }else{
                        userYearHeat.setAdvHeatCost(null );
                    }

                    BigDecimal totalByConsumerId = userYearReceivableDetailService.getTotalByConsumerId(houseInfo.getConsumerId(),annual);

                    if(houseInfo.getHeatingStatus() == null || !houseInfo.getHeatingStatus().equals("heating_status_1")){//供暖状态不为供暖的，就生成一些0值
                        userYearHeat.setSumReceivable(BigDecimal.ZERO);
                    }else{
                        userYearHeat.setSumReceivable(totalByConsumerId);//从user_year_receivable_detail 用户年度采暖费用明细表 中取数
                    }
                    userYearHeat.setSumAccount(BigDecimal.ZERO);//暂无数据，我先置为0

                    if(userYearHeat.getSumAccount() != null && userYearHeat.getSumReceivable() != null){
                        if(houseInfo.getHeatingStatus() == null || !houseInfo.getHeatingStatus().equals("heating_status_1")){//供暖状态不为供暖的，就生成一些0值
                            userYearHeat.setMarginNow(BigDecimal.ZERO);
                        }else{
                            userYearHeat.setMarginNow(userYearHeat.getSumAccount().subtract(userYearHeat.getSumReceivable()) );
                        }
                    }else{
                        userYearHeat.setMarginNow(userYearHeat.getSumAccount().subtract(userYearHeat.getSumReceivable()) );
                    }


                    userYearHeat.setHeatingStatus(houseInfo.getHeatingStatus());
                    int lastAnnual = Integer.parseInt(annual) - 1;

                    UserYearHeat lastAnnualUserYearHeat = userYearHeatService.findByUserAndAnnual(houseInfo.getConsumerId(), lastAnnual+"");
                    PageInfo<UserYearAccountDetail> lastAnnualUserYearAccountDetail = userYearAccountDetailService.findByUserAndAnnual(houseInfo.getConsumerId(), lastAnnual + "", null, null, 1, 200);
                    //逻辑为：当供暖状态为停暖时，无论缴费为何种情况，缴费状态为停暖
                    //当供暖状态为供暖时  1、热费余额大于等于0，为已缴费  2：未有缴费记录且热费余额小于0  3、有缴费记录且热费余额小于0
                    //1缴费状态为已缴费  2  缴费状态为未缴费  3  缴费状态为余额不足
                    if(houseInfo.getHeatingStatus()!=null && !houseInfo.getHeatingStatus().equals("")){
                        switch (houseInfo.getHeatingStatus()){
                            case "heating_status_1"://供暖
                                if(lastAnnualUserYearHeat == null){

                                    userYearHeat.setPayOver("pay_over_4");//未缴费
                                }
                                else if(lastAnnualUserYearHeat.getMarginNow().compareTo(BigDecimal.ZERO) != -1){
                                    userYearHeat.setPayOver("pay_over_1");//已缴费
                                }
                                else if(lastAnnualUserYearAccountDetail.getTotal() == 0L  && lastAnnualUserYearHeat.getMarginNow().compareTo(BigDecimal.ZERO) == -1){
                                    userYearHeat.setPayOver("pay_over_4");//未缴费
                                }
                                else if(lastAnnualUserYearAccountDetail.getTotal() > 0L  && lastAnnualUserYearHeat.getMarginNow().compareTo(BigDecimal.ZERO) == -1){
                                    userYearHeat.setPayOver("pay_over_3");//余额不足
                                }
                                break;
                            case "heating_status_2"://停供
                                userYearHeat.setPayOver("pay_over_4");//未缴费
                                break;
//                            case "heating_status_3"://部分供暖
//                                userYearHeat.setPayOver("pay_over_3");//余额不足
//                                break;
                            default:
                                break;
                        }

                    }else{

                        userYearHeat.setPayOver("pay_over_2");//未缴费
                    }

                    userYearHeat.setSysautoHeatingStatus(null);
                    userYearHeat.setAroundHeating("around_heating_0");//默认值：正常
                    userYearHeat.setTotalValue(new BigDecimal("0"));
                    userYearHeat.setCompanyId(houseInfo.getCompanyId());
                    List<HeatingTimeInfo> heatingTimeInfos = heatingTimeService.selectHeatingTimes(houseInfo.getCompanyId(), houseInfo.getStationId(), annual);

                    if(heatingTimeInfos == null || heatingTimeInfos.size() == 0 || heatingTimeInfos.size()>1){
                        userYearHeat.setActbeginTime(null);
                        userYearHeat.setActendTime(null);
                    }else{
                        userYearHeat.setActbeginTime(heatingTimeInfos.get(0).getStartTime());
                        userYearHeat.setActendTime(heatingTimeInfos.get(0).getEndTime());
                    }

                    userYearHeat.setHeatTarget(null);
                    userYearHeat.setRowno(houseInfo.getRowno());

                    userYearHeat.setNotes(null);
                    userYearHeat.setMemo1(null);
                    userYearHeat.setMemo2(null);
                    userYearHeat.setCreateDate(now);
                    userYearHeat.setCreateUser(UserUtils.getUserId());
                    userYearHeat.setUpdateDate(null);
                    userYearHeat.setUpdateUser(null);

                    userYearHeats.add(userYearHeat);
                }
                if(userYearHeats.size()>0){
                    userYearHeatService.insertByBatch(userYearHeats);
                    generatedHousesTotal += userYearHeats.size();
                }
                if(length ==1 && userYearHeats.size()==0){

                    root.setCode(0);
                    root.setMsg("没有数据被生成");
                    root.setData(true);
                    return root;
                }
                page++;
            }
        //}

        root.setCode(0);
        root.setMsg("生成成功：用户总数量："+allHousesTotal+"，生成"+generatedHousesTotal+"，未入网用户"+(allHousesTotal-housesInNetTotal));


        root.setData(true);
        return root;
    }


    @ApiOperation(value = "生成 用户年度采暖费用明细表（user_year_receivable_detail） 数据 数据,第二个参数采用逗号分隔小区id或者用逗号分隔的用户id，如果为null则表示全部生成,第三个参数，如果是采暖季数据生成，就不要传了（或者传入true），如果是其他处调用，就传false，这个是为了能够不删除原本记录。")
    @RequestMapping("/initUserYearReceivableDetailData")
    @Transactional
    public Root initUserYearReceivableDetailData(@RequestParam String annual,@RequestParam String communityOrHouseIds,@RequestParam(value = "needDelete", required = false) Boolean needDelete) {

        Root verificationRoot = verificationData(  annual, communityOrHouseIds);
        if(verificationRoot.getCode() != 0){
            return verificationRoot;
        }

        Root root = new Root();
        long communityIdLength = 10;//小区长度是10
        long houseIdLength =19;//用户长度是19

        if(communityOrHouseIds != null || !communityOrHouseIds.equals("")){

            String[] split = communityOrHouseIds.split(",");
            if(split == null || split.length==1){
                if(communityOrHouseIds.length() == communityIdLength){
                    //单个小区id
                    return initUserYearReceivableDetailData(annual,communityOrHouseIds,"community","singular", needDelete);
                }else if(communityOrHouseIds.length() == houseIdLength){
                    //单个用户id
                    return initUserYearReceivableDetailData(annual,communityOrHouseIds,"house","singular", needDelete);
                }
                else{

                    root.setCode(500);
                    root.setMsg("参数communityOrHouseIds错误，请传入正确的小区id或者用户id");
                    root.setData(true);
                    return root;
                }
            }
            else{

                if(split[0].length() == communityIdLength){//如果第一个是小区长度

                    for (String s : split) {
                        if(s.length() != communityIdLength){

                            root.setCode(500);
                            root.setMsg("参数communityOrHouseIds错误，传入的参数不都是小区id");
                            root.setData(false);
                            return root;
                        }
                    }


                    return initUserYearReceivableDetailData(annual,communityOrHouseIds,"community","complex", needDelete);

                }
//目前还不接受这种参数
                else if(split[0].length() == houseIdLength){//如果第一个是用户长度

                    for (String s : split) {
                        if(s.length() != houseIdLength){

                            root.setCode(0);
                            root.setMsg("参数communityOrHouseIds错误，传入的参数不都是用户id");
                            root.setData(true);
                            return root;
                        }
                    }

                    return initUserYearReceivableDetailData(annual,communityOrHouseIds,"house","complex", needDelete);

                }
                else{

                    for (String s : split) {
                        if(s.length() != houseIdLength){

                            root.setCode(500);
                            root.setMsg("参数communityOrHouseIds错误，传入的参数不都是小区id或者用户id");
                            root.setData(false);
                            return root;
                        }
                    }
                }

            }
        }


        //这里进行全部生成的逻辑处理，处理原则，为防止出现各种更改的复杂逻辑，先删除，再添加。
        return initUserYearReceivableDetailData(annual,null,null,null, needDelete);



    }


    @ApiOperation(value = "第三个参数是小区还是房屋，第四个参数是单数还是复数")
    private Root initUserYearReceivableDetailData(String annual, String communityOrHouseIds,String communityOrHouseflag,String singularOrComplexflag,Boolean needDelete) {

        HouseVo houseVo = new HouseVo();
        houseVo.setNetStatus("net_status_1");//入网

        Root root = new Root();
        //加上锁，为了保持id的连贯性
        //synchronized(this) {
            //DecimalFormat mFormat = new DecimalFormat("000000000000000000000000000000");
            //读取houses_info中的内容。获取每户的id。然后插入表中。用户id、采暖年度、费用项目，会组成一组唯一的一组数据


            Integer page = 1;
            Integer pageSize = 100;
            PageInfo<HouseInfo> allHousesForTotal = null;
            PageInfo<HouseInfo> allHouses = null;
            long housesTotal = 0;


            //这种情况下所有的都要判断
            if(communityOrHouseIds == null && communityOrHouseflag == null && singularOrComplexflag == null){
                //不必判断user_year_receivable_detail表了，逻辑过于复杂。直接生成即可，因为生成之前是先删除的操作。
                if(needDelete == null || needDelete== true){
                    userYearReceivableDetailService.deleteByConsumerId(null,null,annual);
                }
                allHousesForTotal = houseService.getAllHouse(null, null, null, null, null, houseVo, "asc", "consumer_id", page, pageSize);
                housesTotal = allHousesForTotal.getTotal();
            }

            if(communityOrHouseflag.equals("community") && singularOrComplexflag.equals("singular")){
                if(needDelete == null || needDelete== true){
                    userYearReceivableDetailService.deleteByConsumerId(null,communityOrHouseIds,annual);
                }
                allHousesForTotal = houseService.getAllHouse(null, null, communityOrHouseIds, null, null, houseVo, "asc", "consumer_id", page, pageSize);
                housesTotal = allHousesForTotal.getTotal();
            }
            else if(communityOrHouseflag.equals("house") && singularOrComplexflag.equals("singular")){
                if(needDelete == null || needDelete== true){
                    userYearReceivableDetailService.deleteByConsumerId(communityOrHouseIds,null,annual);
                }
                House houseById = houseService.getHouseById(communityOrHouseIds);
                if(houseById != null){
                    housesTotal = 1;
                }
                else{
                    housesTotal = 0;
                }

            }
            else if(communityOrHouseflag.equals("community") && singularOrComplexflag.equals("complex")){
                if(needDelete == null || needDelete== true){
                    userYearReceivableDetailService.deleteByConsumerIds(null,communityOrHouseIds,annual);
                }
                allHousesForTotal = houseService.getAllHouse(communityOrHouseIds,  houseVo, "asc", "consumer_id", page, pageSize);
                housesTotal = allHousesForTotal.getTotal();
            }
            else if(communityOrHouseflag.equals("house") && singularOrComplexflag.equals("complex")){
                if(needDelete == null || needDelete== true){
                    userYearReceivableDetailService.deleteByConsumerIds(communityOrHouseIds,null,annual);
                }
                allHousesForTotal = houseService.getAllHouseByIds(communityOrHouseIds,  houseVo, "asc", "consumer_id", page, pageSize);
                housesTotal = allHousesForTotal.getTotal();
            }
            else{
                root.setCode(500);
                root.setMsg("参数无效");
                root.setData(false);
                return root;
            }

            long length = housesTotal/pageSize;
            long yushu = housesTotal%pageSize > 0 ?1:0;
            length+=yushu;

            for(int i=0;i<length;i++){


                int len = 0;
                List<HouseInfo> allHouseLst =  null;

                if(communityOrHouseIds == null && communityOrHouseflag == null && singularOrComplexflag == null) {
                    allHouses = houseService.getAllHouse(null, null, null, null, null, houseVo, "asc", "consumer_id", page, pageSize);
                    allHouseLst = allHouses.getList();
                    len = allHouseLst.size();

                }
                if(communityOrHouseflag.equals("community") && singularOrComplexflag.equals("singular")){

                    allHouses = houseService.getAllHouse(null, null, communityOrHouseIds, null, null, houseVo, "asc", "consumer_id", page, pageSize);
                    allHouseLst = allHouses.getList();
                    len = allHouseLst.size();
                }
                else if(communityOrHouseflag.equals("house") && singularOrComplexflag.equals("singular")){
                    HouseInfo houseById = houseService.selectHouseInfoById(communityOrHouseIds);//子类 父类
                    if(houseById != null){
                        allHouseLst =  new ArrayList<HouseInfo>();
                        allHouseLst.add(houseById);
                        len = allHouseLst.size();
                    }
                }
                else if(communityOrHouseflag.equals("community") && singularOrComplexflag.equals("complex")){
                    allHouses = houseService.getAllHouse(communityOrHouseIds,  houseVo, "asc", "consumer_id", page, pageSize);
                    allHouseLst = allHouses.getList();
                    len = allHouseLst.size();
                }
                else if(communityOrHouseflag.equals("house") && singularOrComplexflag.equals("complex")){
                    allHouses = houseService.getAllHouseByIds(communityOrHouseIds,  houseVo, "asc", "consumer_id", page, pageSize);
                    allHouseLst = allHouses.getList();
                    len = allHouseLst.size();
                }

                List<UserYearReceivableDetail> userYearReceivableDetails = new ArrayList<UserYearReceivableDetail>();

                //每次读取最后那条id，然后加1
//                UserYearReceivableDetail lastUserYearReceivableDetails = userYearReceivableDetailService.findLastUserYearReceivableDetails();
//                Integer newPrimaryId = null;
//                if(lastUserYearReceivableDetails == null){
//                    newPrimaryId=0;
//                }
//                else{
//                    newPrimaryId = Integer.parseInt(lastUserYearReceivableDetails.getPrimaryId());
//                }
//                newPrimaryId = newPrimaryId + 1;
                Date now = new Date();
                for(int j=0;j<len;j++){
                    HouseInfo houseInfo = allHouseLst.get(j);//一个house可能对应多个记录
                    String PrimaryId = null;
                    String chargeType = houseInfo.getChargeType();
                    String unitPriceType = houseInfo.getUnitPriceType();
                    if(unitPriceType == null || unitPriceType.equals("")){
                        //这个逻辑是根据费用项目划分的
                        continue;
                    }

                    if(needDelete == null || needDelete== true){
                        if(houseInfo.getHeatingStatus() == null || !houseInfo.getHeatingStatus().equals("heating_status_1")){//供暖状态不为供暖的，就不生成
                        continue;
                        }
                    }

                    PriceDefine priceDefine = priceDefineService.selectByPrimaryKey(unitPriceType);

                    if(priceDefine != null){

                        if(chargeType.equals("charge_type_1")){
                            //面积收费：面积费用、换热器费用、滞纳金
                            if(needDelete == null || needDelete== true){
                                PrimaryId = houseInfo.getConsumerId()+annual+"charging_item_1";//mFormat.format(newPrimaryId);
                            }else{
                                PrimaryId = UserUtils.getUserId()+"-"+UUID.randomUUID().toString().replace("-","");
                            }
                            //面积费用
                            UserYearReceivableDetail  userYearReceivableDetail_mianji = new UserYearReceivableDetail();
                            userYearReceivableDetail_mianji.setPrimaryId(PrimaryId);
                            userYearReceivableDetail_mianji.setCompanyId(houseInfo.getCompanyId());
                            userYearReceivableDetail_mianji.setConsumerId(houseInfo.getConsumerId());
                            userYearReceivableDetail_mianji.setAnnual(annual);
                            userYearReceivableDetail_mianji.setChargingItem("charging_item_1");//字典表中费用项目  面积费用
                            userYearReceivableDetail_mianji.setUnitPrice(new BigDecimal(priceDefine.getAreaPrice()));//面积按照面积单价
                            userYearReceivableDetail_mianji.setSum(houseInfo.getChargeArea());//面积总价，按照 收费面积 setSum(houseInfo.getHeatingArea().multiply(userYearReceivableDetail_mianji.getUnitPrice()))
                            userYearReceivableDetail_mianji.setDiscount(new BigDecimal(1));
                            userYearReceivableDetail_mianji.setTotal(userYearReceivableDetail_mianji.getSum().multiply(userYearReceivableDetail_mianji.getUnitPrice().multiply(userYearReceivableDetail_mianji.getDiscount())));
                            userYearReceivableDetail_mianji.setBeginDataFlag("begin_data_flag_1");
                            userYearReceivableDetail_mianji.setCertufucate(null);//原始凭证
                            userYearReceivableDetail_mianji.setApproveRes(null);//审批结果
                            userYearReceivableDetail_mianji.setNotes(null);//备注
                            userYearReceivableDetail_mianji.setCreateDate(now);
                            userYearReceivableDetail_mianji.setCreateUser(UserUtils.getUserId());
                            userYearReceivableDetail_mianji.setUpdateDate(null);
                            userYearReceivableDetail_mianji.setUpdateUser(null);
                            userYearReceivableDetail_mianji.setApproveSerial(null);


                            userYearReceivableDetails.add(userYearReceivableDetail_mianji);

                            //newPrimaryId++;

                        }
                        else if(chargeType.equals("charge_type_2")){
                            //二部制收费：面积费用、热量费用、换热器费用、滞纳金

                            if(needDelete == null || needDelete== true){
                                PrimaryId = houseInfo.getConsumerId()+annual+"charging_item_1";//mFormat.format(newPrimaryId);
                            }else{
                                PrimaryId = UserUtils.getUserId()+"-"+UUID.randomUUID().toString().replace("-","");
                            }
                            //面积费用
                            UserYearReceivableDetail  userYearReceivableDetail_mianji = new UserYearReceivableDetail();
                            userYearReceivableDetail_mianji.setPrimaryId(PrimaryId);
                            userYearReceivableDetail_mianji.setCompanyId(houseInfo.getCompanyId());
                            userYearReceivableDetail_mianji.setConsumerId(houseInfo.getConsumerId());
                            userYearReceivableDetail_mianji.setAnnual(annual);
                            userYearReceivableDetail_mianji.setChargingItem("charging_item_1");//字典表中费用项目  面积费用
                            userYearReceivableDetail_mianji.setUnitPrice(new BigDecimal(priceDefine.getAreaPrice()));//面积按照面积单价
                            userYearReceivableDetail_mianji.setSum(houseInfo.getChargeArea());//面积总价，按照 收费面积.setSum(houseInfo.getHeatingArea().multiply(userYearReceivableDetail_mianji.getUnitPrice()))
                            userYearReceivableDetail_mianji.setDiscount(new BigDecimal(1));
                            userYearReceivableDetail_mianji.setTotal(userYearReceivableDetail_mianji.getSum().multiply(userYearReceivableDetail_mianji.getUnitPrice().multiply(userYearReceivableDetail_mianji.getDiscount())));
                            userYearReceivableDetail_mianji.setBeginDataFlag("begin_data_flag_1");
                            userYearReceivableDetail_mianji.setCertufucate(null);//原始凭证
                            userYearReceivableDetail_mianji.setApproveRes(null);//审批结果
                            userYearReceivableDetail_mianji.setNotes(null);//备注
                            userYearReceivableDetail_mianji.setCreateDate(now);
                            userYearReceivableDetail_mianji.setCreateUser(UserUtils.getUserId());
                            userYearReceivableDetail_mianji.setUpdateDate(null);
                            userYearReceivableDetail_mianji.setUpdateUser(null);
                            userYearReceivableDetail_mianji.setApproveSerial(null);

                            userYearReceivableDetails.add(userYearReceivableDetail_mianji);

                            //newPrimaryId++;

                            if(needDelete == null || needDelete== true){
                                PrimaryId = houseInfo.getConsumerId()+annual+"charging_item_2";//mFormat.format(newPrimaryId);
                            }else{
                                PrimaryId = UserUtils.getUserId()+"-"+UUID.randomUUID().toString().replace("-","");
                            }
                            //热量费用
                            UserYearReceivableDetail  userYearReceivableDetail_reliang = new UserYearReceivableDetail();
                            userYearReceivableDetail_reliang.setPrimaryId(PrimaryId);
                            userYearReceivableDetail_reliang.setCompanyId(houseInfo.getCompanyId());
                            userYearReceivableDetail_reliang.setConsumerId(houseInfo.getConsumerId());
                            userYearReceivableDetail_reliang.setAnnual(annual);
                            userYearReceivableDetail_reliang.setChargingItem("charging_item_2");//字典表中费用项目  热量费用
                            userYearReceivableDetail_reliang.setUnitPrice(new BigDecimal(0));//热量单价 0
                            userYearReceivableDetail_reliang.setSum(new BigDecimal(0));//热量总价，0
                            userYearReceivableDetail_reliang.setDiscount(new BigDecimal(1));
                            userYearReceivableDetail_reliang.setTotal(userYearReceivableDetail_reliang.getSum().multiply(userYearReceivableDetail_reliang.getUnitPrice().multiply(userYearReceivableDetail_reliang.getDiscount())));
                            userYearReceivableDetail_reliang.setBeginDataFlag("begin_data_flag_1");
                            userYearReceivableDetail_reliang.setCertufucate(null);//原始凭证
                            userYearReceivableDetail_reliang.setApproveRes(null);//审批结果
                            userYearReceivableDetail_reliang.setNotes(null);//备注
                            userYearReceivableDetail_reliang.setCreateDate(now);
                            userYearReceivableDetail_reliang.setCreateUser(UserUtils.getUserId());
                            userYearReceivableDetail_reliang.setUpdateDate(null);
                            userYearReceivableDetail_reliang.setUpdateUser(null);
                            userYearReceivableDetail_reliang.setApproveSerial(null);
                            userYearReceivableDetails.add(userYearReceivableDetail_reliang);

                            //newPrimaryId++;

                        }
                        else if(chargeType.equals("charge_type_3")){
                            //热计量收费：热量费用、换热器费用、滞纳金
                            if(needDelete == null || needDelete== true){
                                PrimaryId = houseInfo.getConsumerId()+annual+"charging_item_2";//mFormat.format(newPrimaryId);
                            }else{
                                PrimaryId = UserUtils.getUserId()+"-"+UUID.randomUUID().toString().replace("-","");
                            }
                            //热量费用
                            UserYearReceivableDetail  userYearReceivableDetail_reliang = new UserYearReceivableDetail();
                            userYearReceivableDetail_reliang.setPrimaryId(PrimaryId);
                            userYearReceivableDetail_reliang.setCompanyId(houseInfo.getCompanyId());
                            userYearReceivableDetail_reliang.setConsumerId(houseInfo.getConsumerId());
                            userYearReceivableDetail_reliang.setAnnual(annual);
                            userYearReceivableDetail_reliang.setChargingItem("charging_item_2");//字典表中费用项目  热量费用
                            userYearReceivableDetail_reliang.setUnitPrice(new BigDecimal(0));//热量单价 0
                            userYearReceivableDetail_reliang.setSum(new BigDecimal(0));//热量总价，0
                            userYearReceivableDetail_reliang.setDiscount(new BigDecimal(1));
                            userYearReceivableDetail_reliang.setTotal(userYearReceivableDetail_reliang.getSum().multiply(userYearReceivableDetail_reliang.getUnitPrice().multiply(userYearReceivableDetail_reliang.getDiscount())));
                            userYearReceivableDetail_reliang.setBeginDataFlag("begin_data_flag_1");
                            userYearReceivableDetail_reliang.setCertufucate(null);//原始凭证
                            userYearReceivableDetail_reliang.setApproveRes(null);//审批结果
                            userYearReceivableDetail_reliang.setNotes(null);//备注
                            userYearReceivableDetail_reliang.setCreateDate(now);
                            userYearReceivableDetail_reliang.setCreateUser(UserUtils.getUserId());
                            userYearReceivableDetail_reliang.setUpdateDate(null);
                            userYearReceivableDetail_reliang.setUpdateUser(null);
                            userYearReceivableDetail_reliang.setApproveSerial(null);
                            userYearReceivableDetails.add(userYearReceivableDetail_reliang);
                            //newPrimaryId++;

                        }
//                        else{
//                            newPrimaryId++;
//                        }

                        //每个都有 换热器费用、滞纳金 单独提出来：

                        if(needDelete == null || needDelete== true){
                            PrimaryId = houseInfo.getConsumerId()+annual+"charging_item_3";//mFormat.format(newPrimaryId);
                        }else{
                            PrimaryId = UserUtils.getUserId()+"-"+UUID.randomUUID().toString().replace("-","");
                        }
                        //换热器费用
                        UserYearReceivableDetail  userYearReceivableDetail_huanreqi = new UserYearReceivableDetail();
                        userYearReceivableDetail_huanreqi.setPrimaryId(PrimaryId);
                        userYearReceivableDetail_huanreqi.setCompanyId(houseInfo.getCompanyId());
                        userYearReceivableDetail_huanreqi.setConsumerId(houseInfo.getConsumerId());
                        userYearReceivableDetail_huanreqi.setAnnual(annual);
                        userYearReceivableDetail_huanreqi.setChargingItem("charging_item_3");//字典表中费用项目  换热器费用
                        String heatExchangeStatus = houseInfo.getHeatExchangeStatus();
                        if(heatExchangeStatus == null){
                            userYearReceivableDetail_huanreqi.setUnitPrice(BigDecimal.ZERO);//换热器按照换热器单价
                            userYearReceivableDetail_huanreqi.setSum(BigDecimal.ZERO);//换热器总价，按照 供热换热器
                        }
                        else if(heatExchangeStatus.equals("heat_exchange_status_0")){//没有
                            userYearReceivableDetail_huanreqi.setUnitPrice(BigDecimal.ZERO);//换热器按照换热器单价
                            userYearReceivableDetail_huanreqi.setSum(BigDecimal.ZERO);//换热器总价，按照 供热换热器
                        }
                        else if(heatExchangeStatus.equals("heat_exchange_status_1")){//有1换热器
                            userYearReceivableDetail_huanreqi.setUnitPrice(new BigDecimal(priceDefine.getExchangerPrice()));//换热器按照换热器单价
                            userYearReceivableDetail_huanreqi.setSum(new BigDecimal(1));//换热器总价，按照 供热换热器
                        }
                        else if(heatExchangeStatus.equals("heat_exchange_status_2")){//有2换热器
                            userYearReceivableDetail_huanreqi.setUnitPrice(new BigDecimal(priceDefine.getExchangerPrice()));//换热器按照换热器单价
                            userYearReceivableDetail_huanreqi.setSum(new BigDecimal(2));//换热器总价，按照 供热换热器
                        }
                        else if(heatExchangeStatus.equals("heat_exchange_status_3")){//有3换热器
                            userYearReceivableDetail_huanreqi.setUnitPrice(new BigDecimal(priceDefine.getExchangerPrice()));//换热器按照换热器单价
                            userYearReceivableDetail_huanreqi.setSum(new BigDecimal(3));//换热器总价，按照 供热换热器
                        }
                        else if(heatExchangeStatus.equals("heat_exchange_status_4")){//有4换热器
                            userYearReceivableDetail_huanreqi.setUnitPrice(new BigDecimal(priceDefine.getExchangerPrice()));//换热器按照换热器单价
                            userYearReceivableDetail_huanreqi.setSum(new BigDecimal(4));//换热器总价，按照 供热换热器
                        }
                        else if(heatExchangeStatus.equals("heat_exchange_status_5")){//有5换热器
                            userYearReceivableDetail_huanreqi.setUnitPrice(new BigDecimal(priceDefine.getExchangerPrice()));//换热器按照换热器单价
                            userYearReceivableDetail_huanreqi.setSum(new BigDecimal(5));//换热器总价，按照 供热换热器
                        }
                        else if(heatExchangeStatus.equals("heat_exchange_status_6")){//有6换热器
                            userYearReceivableDetail_huanreqi.setUnitPrice(new BigDecimal(priceDefine.getExchangerPrice()));//换热器按照换热器单价
                            userYearReceivableDetail_huanreqi.setSum(new BigDecimal(6));//换热器总价，按照 供热换热器
                        }
                        else if(heatExchangeStatus.equals("heat_exchange_status_7")){//有7换热器
                            userYearReceivableDetail_huanreqi.setUnitPrice(new BigDecimal(priceDefine.getExchangerPrice()));//换热器按照换热器单价
                            userYearReceivableDetail_huanreqi.setSum(new BigDecimal(7));//换热器总价，按照 供热换热器
                        }
                        else if(heatExchangeStatus.equals("heat_exchange_status_8")){//有8换热器
                            userYearReceivableDetail_huanreqi.setUnitPrice(new BigDecimal(priceDefine.getExchangerPrice()));//换热器按照换热器单价
                            userYearReceivableDetail_huanreqi.setSum(new BigDecimal(8));//换热器总价，按照 供热换热器
                        }else{
                            userYearReceivableDetail_huanreqi.setUnitPrice(BigDecimal.ZERO);//换热器按照换热器单价
                            userYearReceivableDetail_huanreqi.setSum(BigDecimal.ZERO);//换热器总价，按照 供热换热器
                        }




                        userYearReceivableDetail_huanreqi.setDiscount(new BigDecimal(1));
                        userYearReceivableDetail_huanreqi.setTotal(userYearReceivableDetail_huanreqi.getSum().multiply(userYearReceivableDetail_huanreqi.getUnitPrice().multiply(userYearReceivableDetail_huanreqi.getDiscount())));
                        userYearReceivableDetail_huanreqi.setBeginDataFlag("begin_data_flag_1");
                        userYearReceivableDetail_huanreqi.setCertufucate(null);//原始凭证
                        userYearReceivableDetail_huanreqi.setApproveRes(null);//审批结果
                        userYearReceivableDetail_huanreqi.setNotes(null);//备注
                        userYearReceivableDetail_huanreqi.setCreateDate(now);
                        userYearReceivableDetail_huanreqi.setCreateUser(UserUtils.getUserId());
                        userYearReceivableDetail_huanreqi.setUpdateDate(null);
                        userYearReceivableDetail_huanreqi.setUpdateUser(null);
                        userYearReceivableDetail_huanreqi.setApproveSerial(null);
                        userYearReceivableDetails.add(userYearReceivableDetail_huanreqi);


                        //newPrimaryId++;

                        if(needDelete == null || needDelete== true){
                            PrimaryId = houseInfo.getConsumerId()+annual+"charging_item_7";//mFormat.format(newPrimaryId);
                        }else{
                            PrimaryId = UserUtils.getUserId()+"-"+UUID.randomUUID().toString().replace("-","");
                        }
                        //滞纳金费用
                        UserYearReceivableDetail  userYearReceivableDetail_zhinajin = new UserYearReceivableDetail();
                        userYearReceivableDetail_zhinajin.setPrimaryId(PrimaryId);
                        userYearReceivableDetail_zhinajin.setCompanyId(houseInfo.getCompanyId());
                        userYearReceivableDetail_zhinajin.setConsumerId(houseInfo.getConsumerId());
                        userYearReceivableDetail_zhinajin.setAnnual(annual);
                        userYearReceivableDetail_zhinajin.setChargingItem("charging_item_7");//字典表中费用项目  滞纳金费用
                        userYearReceivableDetail_zhinajin.setUnitPrice(new BigDecimal(0));//滞纳金单价 0
                        userYearReceivableDetail_zhinajin.setSum(new BigDecimal(0));//滞纳金总价，0
                        userYearReceivableDetail_zhinajin.setDiscount(new BigDecimal(1));
                        userYearReceivableDetail_zhinajin.setTotal(userYearReceivableDetail_zhinajin.getSum().multiply(userYearReceivableDetail_zhinajin.getUnitPrice().multiply(userYearReceivableDetail_zhinajin.getDiscount())));
                        userYearReceivableDetail_zhinajin.setBeginDataFlag("begin_data_flag_1");
                        userYearReceivableDetail_zhinajin.setCertufucate(null);//原始凭证
                        userYearReceivableDetail_zhinajin.setApproveRes(null);//审批结果
                        userYearReceivableDetail_zhinajin.setNotes(null);//备注
                        userYearReceivableDetail_zhinajin.setCreateDate(now);
                        userYearReceivableDetail_zhinajin.setCreateUser(UserUtils.getUserId());
                        userYearReceivableDetail_zhinajin.setUpdateDate(null);
                        userYearReceivableDetail_zhinajin.setUpdateUser(null);
                        userYearReceivableDetail_zhinajin.setApproveSerial(null);
                        userYearReceivableDetails.add(userYearReceivableDetail_zhinajin);

                        //newPrimaryId++;


                    }


                }//每次循环100个，实际存入list的可能300——400个

                if(userYearReceivableDetails.size()>0){
                    userYearReceivableDetailService.insertByBatch(userYearReceivableDetails);
                }
                page++;
            }//9次循环，每次批量插入N个

        //}//上锁


        root.setCode(0);
        root.setMsg("生成成功");
        root.setData(true);
        return root;
    }


    @ApiOperation(value = "获取采暖季用户一些数字信息")
    @RequestMapping("/getChargeCount")
    @Transactional
    public Root getChargeCount(@RequestParam String companyId,@RequestParam String annual) {
        Root root = new Root();
        Integer inNetHousesCount = houseService.selectCountByNetStatus(companyId,"net_status_1");
        //Integer stopHeatingHousesCount = houseService.selectCountByHeatingStatus(companyId,"heating_status_2");
        Integer stopHeatingHousesCount = userYearHeatService.selectCountNotHeating(companyId,annual);//不供暖用户

        //Integer stopHeatingHousesCount = houseService.selectCountNotHeating(companyId);

        Integer allHousesCount = houseService.getAllCount(companyId);
        Integer generatedHousesCount = userYearHeatService.getCountByAnnual(companyId,annual);


        Integer byAreaHousesCount = userYearHeatService.selectCountByChargeType(companyId,annual,"charge_type_1");//面积收费
        Integer byHeatAndAreaHousesCount = userYearHeatService.selectCountByChargeType(companyId,annual,"charge_type_2");//二部制收费
        Integer byHeatHousesCount = userYearHeatService.selectCountByChargeType(companyId,annual,"charge_type_3");//热计量收费

        Map<String,Object> map = new HashMap<>();
        map.put("inNetHousesCount",inNetHousesCount);
        map.put("allHousesCount",allHousesCount);
        map.put("generatedHousesCount",generatedHousesCount);
        map.put("byAreaHousesCount",byAreaHousesCount);
        map.put("byHeatAndAreaHousesCount",byHeatAndAreaHousesCount);
        map.put("byHeatHousesCount",byHeatHousesCount);
        map.put("stopHeatingHousesCount",stopHeatingHousesCount);

        root.setCode(0);
        root.setMsg("获取成功");
        root.setData(map);
        return root;
    }

    @ApiOperation(value = "获取采暖季用户一些数字信息")
    @RequestMapping("/getChargeSumList")
    @Transactional
    public Root getChargeSumList(@RequestParam String companyId,@RequestParam String annual,@RequestParam(value = "order", required = false)  String order,@RequestParam(value = "sortby", required = false)  String sortby,@RequestParam Integer page,@RequestParam Integer pageSize) {
        Root root = new Root();

        PageInfo<ChargeSumVO>  chargeSumVOs = userYearHeatService.getChargeSumByAnnual(companyId,annual,order,sortby,page, pageSize);
        List<ChargeSumVO> list = chargeSumVOs.getList();

        for (ChargeSumVO chargeSumVO : list) {
            if(chargeSumVO.getHeatUserType().equals("charge_type_1")){
                chargeSumVO.setHeatUserType("面积收费");
            }else if(chargeSumVO.getHeatUserType().equals("charge_type_2")){
                chargeSumVO.setHeatUserType("二部制收费");
            }else if(chargeSumVO.getHeatUserType().equals("charge_type_3")){
                chargeSumVO.setHeatUserType("热计量收费");
            }

            String prePrice = SecureUtils.AESDncode(Constant.AES_RULES, chargeSumVO.getPrePrice());//解密
            chargeSumVO.setPrePrice(prePrice);
        }

        root.setData(list);
        root.setCond(Cond.getCond(page, chargeSumVOs.getPageSize(), (int)chargeSumVOs.getTotal(),
                order,sortby));
        root.setMsg("获取成功");
        return root;
    }




    @ApiOperation(value = "获取采暖季用户一些数字信息 导出Excel")
    @RequestMapping("/getChargeSumListToExcel")
    @Transactional
    public Root getChargeSumListToExcel(ServletRequest request,@RequestParam String companyId
            ,@RequestParam String annual
            ,@RequestParam(value = "order", required = false)  String order
            ,@RequestParam(value = "sortby", required = false)  String sortby) {

        //限制导出文件的大小，防止爆内存
        Root root1 =  getChargeSumList(companyId
                ,annual
                ,order
                ,sortby
                ,1
                ,50000);

        List<ChargeSumVO> chargeSumVOs =  (List<ChargeSumVO>)root1.getData();

        Root root = new Root();
        root.setCode(0);
        root.setMsg("导出成功");
        root.setData(ExportExcelUtil.ExportList(request, chargeSumVOs, "收费设置-采暖季数据"));        ;
        return root;
    }



    @ApiOperation(value = "获取采暖季用户管理数据 stationIds和commuityIds传入逗号分隔字符串，hasGenerated传入0或1")
    @RequestMapping("/getChargeHouseDataList")
    @Transactional
    public Root getChargeHouseDataList(@RequestParam(value = "stationIds", required = false)  String stationIds,@RequestParam(value = "commuityIds", required = false)  String commuityIds,@RequestParam(value = "hasGenerated", required = false)  String hasGenerated,@RequestParam String annual
            ,@RequestParam(value = "order", required = false)  String order,@RequestParam(value = "sortby", required = false)  String sortby,@RequestParam Integer page,@RequestParam Integer pageSize) {
        Root root = new Root();

        PageInfo<ChargeHouseDataVO>  chargeHouseDataVOs = userYearHeatService.getChargeHouseDataList(stationIds, commuityIds, hasGenerated, annual, order, sortby, page, pageSize);

        //先这样，排序肯定不准
        for (ChargeHouseDataVO chargeHouseDataVO : chargeHouseDataVOs.getList()) {

            if(chargeHouseDataVO.getChargeType() == null){
                chargeHouseDataVO.setChargeType("");
            }
            else if(chargeHouseDataVO.getChargeType().equals("charge_type_1")){
                chargeHouseDataVO.setChargeType("面积收费");
            }else if(chargeHouseDataVO.getChargeType().equals("charge_type_2")){
                chargeHouseDataVO.setChargeType("二部制收费");
            }else if(chargeHouseDataVO.getChargeType().equals("charge_type_3")){
                chargeHouseDataVO.setChargeType("热计量收费");
            }


            if(chargeHouseDataVO.getHeatUserType() == null){
                chargeHouseDataVO.setHeatUserType("");
            }
            else if(chargeHouseDataVO.getHeatUserType().equals("heat_user_type_1")){
                chargeHouseDataVO.setHeatUserType("低保");
            }else if(chargeHouseDataVO.getHeatUserType().equals("heat_user_type_2")){
                chargeHouseDataVO.setHeatUserType("事业");
            }else if(chargeHouseDataVO.getHeatUserType().equals("heat_user_type_3")){
                chargeHouseDataVO.setHeatUserType("政府");
            }else if(chargeHouseDataVO.getHeatUserType().equals("heat_user_type_4")){
                chargeHouseDataVO.setHeatUserType("工厂");
            }else if(chargeHouseDataVO.getHeatUserType().equals("heat_user_type_5")){
                chargeHouseDataVO.setHeatUserType("门市");
            }else if(chargeHouseDataVO.getHeatUserType().equals("heat_user_type_6")){
                chargeHouseDataVO.setHeatUserType("写字楼");
            }else if(chargeHouseDataVO.getHeatUserType().equals("heat_user_type_7")){
                chargeHouseDataVO.setHeatUserType("学校");
            }else if(chargeHouseDataVO.getHeatUserType().equals("heat_user_type_8")){
                chargeHouseDataVO.setHeatUserType("居民");
            }



            if(chargeHouseDataVO.getHouseType() == null){
                chargeHouseDataVO.setHouseType("");
            }
            else if(chargeHouseDataVO.getHouseType().equals("house_type_1")){
                chargeHouseDataVO.setHouseType("边户");
            }else if(chargeHouseDataVO.getHouseType().equals("house_type_2")){
                chargeHouseDataVO.setHouseType("顶户");
            }else if(chargeHouseDataVO.getHouseType().equals("house_type_3")){
                chargeHouseDataVO.setHouseType("顶边户");
            }else if(chargeHouseDataVO.getHouseType().equals("house_type_4")){
                chargeHouseDataVO.setHouseType("中间户");
            }else if(chargeHouseDataVO.getHouseType().equals("house_type_5")){
                chargeHouseDataVO.setHouseType("底户");
            }else if(chargeHouseDataVO.getHouseType().equals("house_type_6")){
                chargeHouseDataVO.setHouseType("底边户");
            }



            if(chargeHouseDataVO.getContryStat() == null){
                chargeHouseDataVO.setContryStat("");
            }
            else if(chargeHouseDataVO.getContryStat().equals("contry_stat_1")){
                chargeHouseDataVO.setContryStat("正常");
            }else if(chargeHouseDataVO.getContryStat().equals("contry_stat_2")){
                chargeHouseDataVO.setContryStat("抄表不收费");
            }else if(chargeHouseDataVO.getContryStat().equals("contry_stat_3")){
                chargeHouseDataVO.setContryStat("注销");
            }else if(chargeHouseDataVO.getContryStat().equals("contry_stat_4")){
                chargeHouseDataVO.setContryStat("不抄表");
            }else if(chargeHouseDataVO.getContryStat().equals("contry_stat_5")){
                chargeHouseDataVO.setContryStat("销户");
            }

            if(chargeHouseDataVO.getNetStatus() == null){
                chargeHouseDataVO.setNetStatus("null");
            }
            else if(chargeHouseDataVO.getNetStatus().equals("net_status_0")){
                chargeHouseDataVO.setNetStatus("未入网");
            }else if(chargeHouseDataVO.getNetStatus().equals("net_status_1")){
                chargeHouseDataVO.setNetStatus("入网");
            }else if(chargeHouseDataVO.getNetStatus().equals("net_status_2")){
                chargeHouseDataVO.setNetStatus("退网");
            }
        }

        root.setCode(0);
        root.setMsg("获取成功");
        root.setData(chargeHouseDataVOs.getList());
        root.setCond(Cond.getCond(page, chargeHouseDataVOs.getPageSize(), (int)chargeHouseDataVOs.getTotal(),
                order,sortby));
        return root;
    }

//    @ApiOperation(value = "重置层级生成状态 第一个参数传入逗号分隔字符串")
//    @RequestMapping("/resetLevelGenerationState")
//    @Transactional
//   public Root resetLevelGenerationState(@RequestParam(value = "stationIds", required = false)   String stationIds,@RequestParam(value = "commuityIds", required = false)   String commuityIds,@RequestParam(value = "hasGenerated", required = false)  String hasGenerated,@RequestParam String annual) {
//        Root root = new Root();
//        //1111111111111 這個有bug！！！！！
//        Integer page = 1;
//        Integer pageSize = 200;
//        String consumerIds="";
//        PageInfo<ChargeHouseDataVO>  chargeHouseDataVOs = userYearHeatService.getChargeHouseDataList(stationIds, commuityIds, hasGenerated, annual, null, null, page, pageSize);
//        long total = chargeHouseDataVOs.getTotal();
//        int pages = chargeHouseDataVOs.getPages();
//        if(total <= pageSize){
//            List<ChargeHouseDataVO> list = chargeHouseDataVOs.getList();
//            for (ChargeHouseDataVO chargeHouseDataVO : list) {
//                String consumerId = chargeHouseDataVO.getConsumerId();
//                consumerIds+=consumerId+",";
//            }
//            if(consumerIds.length()>0){
//                consumerIds = consumerIds.substring(0,consumerIds.length()-1);
//            }
//
//            int k = userYearHeatService.deleteByConsumerIds(consumerIds, null, annual);
//            int j = userYearReceivableDetailService.deleteByConsumerIds(consumerIds, null, annual);
////            if(k != total){//j我实在无法判断
////                throw new RuntimeException("重置出错，请重新重置");
////            }
//
//        }else{
//            //为了少查一次
//
//            for(int i =1 ;i<pages+1;i++){
//
//                PageInfo<ChargeHouseDataVO>  chargeHouseDataVOs1 = userYearHeatService.getChargeHouseDataList(stationIds, commuityIds, hasGenerated, annual, null, null, i, pageSize);
//                List<ChargeHouseDataVO> list = chargeHouseDataVOs1.getList();
//                for (ChargeHouseDataVO chargeHouseDataVO : list) {
//                    String consumerId = chargeHouseDataVO.getConsumerId();
//                    consumerIds+=consumerId+",";
//                }
//                if(consumerIds.length()>0){
//                    consumerIds = consumerIds.substring(0,consumerIds.length()-1);
//                }
//
//                int k = userYearHeatService.deleteByConsumerIds(consumerIds, null, annual);
//                int j = userYearReceivableDetailService.deleteByConsumerIds(consumerIds, null, annual);
////                if(k != total){//j我实在无法判断
////                    throw new RuntimeException("重置出错，请重新重置");
////                }
//            }
//        }
//
//        root.setCode(0);
//        root.setMsg("重置成功");
//        //root.setData(split.length);
//        return root;
//    }

    @ApiOperation(value = "重置层级生成状态 stationIds和commuityIds,要么传入单个值，要么传入null")
    @RequestMapping("/resetLevelGenerationState")
    @Transactional
    public Root resetLevelGenerationState(@RequestParam String companyId,
                @RequestParam(value = "stationIds", required = false)  String stationIds,
                @RequestParam(value = "commuityIds", required = false)  String commuityIds,
                @RequestParam String annual){


        Root root = new Root();

        if((stationIds==null || stationIds.equals("")) && (commuityIds==null || commuityIds.equals(""))){

            int netStatusNullHousesTotal = houseService.selectCountNetStatusNull(companyId);
            if(netStatusNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前公司"+companyId+"的用户中，有用户的入网状态为空，请修改用户的入网状态后再重置");
                root.setData(true);
                return root;
            }

            int heatingStatusNullHousesTotal = houseService.selectCountHeatingStatusNull(companyId);
            if(heatingStatusNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前公司"+companyId+"的用户中，有用户的供暖状态为空，请修改用户的供暖状态后再重置");
                root.setData(true);
                return root;
            }

            int priceDefineNullHousesTotal = houseService.selectCountPriceDefineNull(companyId);
            if(priceDefineNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前公司"+companyId+"的用户中，有用户的热费单价类型为空，请修改用户的热费单价类型后再重置");
                root.setData(true);
                return root;
            }

            int chargeTypeNullHousesTotal = houseService.selectCountChargeTypeNull(companyId);
            if(chargeTypeNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前公司"+companyId+"的用户中，有用户的供热收费类型为空，请修改用户的供热收费类型后再重置");
                root.setData(true);
                return root;
            }

            //先修改PriceDefine表，把加密的数据解密开，插入到tmp字段
            List<PriceDefine> priceDefines = priceDefineService.selectByAnnualAndCompanyIds(annual, companyId);
            if(priceDefines == null || priceDefines.size() == 0){
                root.setCode(500);
                root.setMsg("当前公司"+companyId+"在采暖季"+annual+"下，热量单价类型表没有数据");
                root.setData(true);
                return root;
            }
            List<HeatingTimeInfo> heatingTimeInfos = heatingTimeService.selectHeatingTimes(companyId, companyId, annual);
            if(heatingTimeInfos == null || heatingTimeInfos.size() == 0){
                root.setCode(500);
                root.setMsg("当前公司"+companyId+"在采暖季"+annual+"下，热费时间表没有数据");
                root.setData(true);
                return root;
            }else if( heatingTimeInfos.size() > 1){
                root.setCode(500);
                root.setMsg("当前公司"+companyId+"在采暖季"+annual+"下，热费时间表多于一条记录");
                root.setData(true);
                return root;
            }

            HeatingTimeInfo heatingTimeInfo = heatingTimeInfos.get(0);
            if(!heatingTimeInfo.getStationId().equals(heatingTimeInfo.getCompanyId())){
                root.setCode(500);
                root.setMsg("当前公司id:"+companyId+"不等于站id:"+heatingTimeInfo.getStationId());
                root.setData(true);
                return root;
            }


            List<String> priceDefinesIds = new ArrayList<>();
            for (PriceDefine priceDefine : priceDefines) {
                priceDefinesIds.add(priceDefine.getPrimaryId()+":_:"+priceDefine.getPriceName());
            }

            List<PriceDefine> priceDefinesFormHouses = houseService.selectPriceDefineByCompanyId(companyId);
            List<String> priceDefinesFormHousesIds = new ArrayList<>();
            for (PriceDefine priceDefinesFormHouse : priceDefinesFormHouses) {
                priceDefinesFormHousesIds.add(priceDefinesFormHouse.getPrimaryId()+":_:"+priceDefinesFormHouse.getPriceName());
            }

            priceDefinesFormHousesIds.removeAll(priceDefinesIds);
            if(priceDefinesFormHousesIds != null && priceDefinesFormHousesIds.size() > 0){
                String errorPriceDefines = "";
                for (String priceDefinesFormHousesId : priceDefinesFormHousesIds) {
                    String[] split = priceDefinesFormHousesId.split(":_:");
                    errorPriceDefines+=split[1]+",";
                }
                root.setCode(500);
                root.setMsg("当前公司id:"+companyId+"的用户中，存在某些用户使用了非法的热费单价类型:"+errorPriceDefines);
                root.setData(true);
                return root;
            }



            userYearReceivableDetailService.deleteByCompanyId(companyId,annual);//先全部刪掉！
            userYearHeatService.deleteByCompanyId(companyId,annual);//先全部刪掉！
            root.setCode(0);
            root.setMsg("重置成功");
            root.setData(true);
            return root;

        }else if(stationIds!=null && !stationIds.equals("") && commuityIds!=null && !commuityIds.equals("")){

            List<String> commuityIdlst = new ArrayList<>();
            if(commuityIds == null || commuityIds.equals("") ){
                commuityIdlst =  null;
            }else{
                String[] split = commuityIds.split(",");
                for (String s : split) {
                    commuityIdlst.add(s);
                }
            }


            int netStatusNullHousesTotal = houseService.selectCountNetStatusNullForCommunityIds(commuityIdlst);
            if(netStatusNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前所选小区"+commuityIds+"的用户中，有用户的入网状态为空，请修改用户的入网状态后再重置");
                root.setData(true);
                return root;
            }


            int heatingStatusNullHousesTotal = houseService.selectCountHeatingStatusNullForCommunityIds(commuityIdlst);
            if(heatingStatusNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前所选小区"+commuityIds+"的用户中，有用户的供暖状态为空，请修改用户的供暖状态后再重置");
                root.setData(true);
                return root;
            }


            int priceDefineNullHousesTotal = houseService.selectCountPriceDefineNullForCommunityIds(commuityIdlst);
            if(priceDefineNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前所选小区"+commuityIds+"的用户中，有用户的热费单价类型为空，请修改用户的热费单价类型后再重置");
                root.setData(true);
                return root;
            }


            int chargeTypeNullHousesTotal = houseService.selectCountChargeTypeNullForCommunityIds(commuityIdlst);
            if(chargeTypeNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前所选小区"+commuityIds+"的用户中，有用户的供热收费类型为空，请修改用户的供热收费类型后再重置");
                root.setData(true);
                return root;
            }

            Date a=   new Date();
            System.out.println("开始时间："+a);
            //先修改PriceDefine表，把加密的数据解密开，插入到tmp字段
            List<PriceDefine> priceDefines = priceDefineService.selectByAnnualAndCommunityIds(annual, commuityIds);
            if(priceDefines == null || priceDefines.size() == 0){
                root.setCode(500);
                root.setMsg("当前小区"+commuityIds+"在采暖季"+annual+"下，热量单价类型表没有数据");
                root.setData(true);
                return root;
            }
            List<HeatingTimeInfo> heatingTimeInfos = heatingTimeService.selectHeatingTimes(companyId, companyId, annual);
            if(heatingTimeInfos == null || heatingTimeInfos.size() == 0){
                root.setCode(500);
                root.setMsg("当前公司"+companyId+"在采暖季"+annual+"下，热费时间表没有数据");
                root.setData(true);
                return root;
            }else if( heatingTimeInfos.size() > 1){
                root.setCode(500);
                root.setMsg("当前公司"+companyId+"在采暖季"+annual+"下，热费时间表多于一条记录");
                root.setData(true);
                return root;
            }

            HeatingTimeInfo heatingTimeInfo = heatingTimeInfos.get(0);
            if(!heatingTimeInfo.getStationId().equals(heatingTimeInfo.getCompanyId())){
                root.setCode(500);
                root.setMsg("当前公司id:"+companyId+"不等于站id:"+heatingTimeInfo.getStationId());
                root.setData(true);
                return root;
            }


            List<String> priceDefinesIds = new ArrayList<>();
            for (PriceDefine priceDefine : priceDefines) {
                priceDefinesIds.add(priceDefine.getPrimaryId()+":_:"+priceDefine.getPriceName());
            }

            List<PriceDefine> priceDefinesFormHouses = houseService. selectPriceDefineByCommunityIds(commuityIdlst);
            List<String> priceDefinesFormHousesIds = new ArrayList<>();
            for (PriceDefine priceDefinesFormHouse : priceDefinesFormHouses) {
                priceDefinesFormHousesIds.add(priceDefinesFormHouse.getPrimaryId()+":_:"+priceDefinesFormHouse.getPriceName());
            }

            priceDefinesFormHousesIds.removeAll(priceDefinesIds);
            if(priceDefinesFormHousesIds != null && priceDefinesFormHousesIds.size() > 0){
                String errorPriceDefines = "";
                for (String priceDefinesFormHousesId : priceDefinesFormHousesIds) {
                    String[] split = priceDefinesFormHousesId.split(":_:");
                    errorPriceDefines+=split[1]+",";
                }
                root.setCode(500);
                root.setMsg("当前所选小区"+commuityIds+"的用户中，存在某些用户使用了非法的热费单价类型:"+errorPriceDefines);
                root.setData(true);
                return root;
            }




            userYearReceivableDetailService.deleteByCommuityIds(commuityIdlst,annual);//先全部刪掉！
            userYearHeatService.deleteByCommuityIds(commuityIdlst,annual);//先全部刪掉！

            root.setCode(0);
            root.setMsg("重置成功");
            root.setData(true);
            return root;
        }else if((stationIds!=null && !stationIds.equals("")) && (commuityIds==null || commuityIds.equals(""))){

            List<String> stationIdlst = new ArrayList<>();
            if(stationIds == null || stationIds.equals("") ){
                stationIdlst =  null;
            }else{
                String[] split = stationIds.split(",");
                for (String s : split) {
                    stationIdlst.add(s);
                }
            }


            int netStatusNullHousesTotal = houseService.selectCountNetStatusNullForStationIds(stationIdlst);
            if(netStatusNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前所选换热站"+stationIds+"的用户中，有用户的入网状态为空，请修改用户的入网状态后再重置");
                root.setData(true);
                return root;
            }



            int heatingStatusNullHousesTotal = houseService.selectCountHeatingStatusNullForStationIds(stationIdlst);
            if(heatingStatusNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前所选换热站"+stationIds+"的用户中，有用户的供暖状态为空，请修改用户的供暖状态后再重置");
                root.setData(true);
                return root;
            }

            int priceDefineNullHousesTotal = houseService.selectCountPriceDefineNullForStationIds(stationIdlst);
            if(priceDefineNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前所选换热站"+stationIds+"的用户中，有用户的热费单价类型为空，请修改用户的热费单价类型后再重置");
                root.setData(true);
                return root;
            }

            int chargeTypeNullHousesTotal = houseService.selectCountChargeTypeNullForStationIds(stationIdlst);
            if(chargeTypeNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前所选换热站"+stationIds+"的用户中，有用户的供热收费类型为空，请修改用户的供热收费类型后再重置");
                root.setData(true);
                return root;
            }

            Date a=   new Date();
            System.out.println("开始时间："+a);
            //先修改PriceDefine表，把加密的数据解密开，插入到tmp字段
            List<PriceDefine> priceDefines = priceDefineService.selectByAnnualAndStationIds(annual, stationIds);
            if(priceDefines == null || priceDefines.size() == 0){
                root.setCode(500);
                root.setMsg("当前站"+stationIds+"在采暖季"+annual+"下，热量单价类型表没有数据");
                root.setData(true);
                return root;
            }
            List<HeatingTimeInfo> heatingTimeInfos = heatingTimeService.selectHeatingTimes(companyId, companyId, annual);
            if(heatingTimeInfos == null || heatingTimeInfos.size() == 0){
                root.setCode(500);
                root.setMsg("当前站"+stationIds+"在采暖季"+annual+"下，热费时间表没有数据");
                root.setData(true);
                return root;
            }else if( heatingTimeInfos.size() > 1){
                root.setCode(500);
                root.setMsg("当前站"+stationIds+"在采暖季"+annual+"下，热费时间表多于一条记录");
                root.setData(true);
                return root;
            }

            HeatingTimeInfo heatingTimeInfo = heatingTimeInfos.get(0);
            if(!heatingTimeInfo.getStationId().equals(heatingTimeInfo.getCompanyId())){
                root.setCode(500);
                root.setMsg("当前公司id:"+companyId+"不等于站id:"+heatingTimeInfo.getStationId());
                root.setData(true);
                return root;
            }


            List<String> priceDefinesIds = new ArrayList<>();
            for (PriceDefine priceDefine : priceDefines) {
                priceDefinesIds.add(priceDefine.getPrimaryId()+":_:"+priceDefine.getPriceName());
            }

            List<PriceDefine> priceDefinesFormHouses = houseService. selectPriceDefineByStationIds(stationIdlst);
            List<String> priceDefinesFormHousesIds = new ArrayList<>();
            for (PriceDefine priceDefinesFormHouse : priceDefinesFormHouses) {
                priceDefinesFormHousesIds.add(priceDefinesFormHouse.getPrimaryId()+":_:"+priceDefinesFormHouse.getPriceName());
            }

            priceDefinesFormHousesIds.removeAll(priceDefinesIds);
            if(priceDefinesFormHousesIds != null && priceDefinesFormHousesIds.size() > 0){
                String errorPriceDefines = "";
                for (String priceDefinesFormHousesId : priceDefinesFormHousesIds) {
                    String[] split = priceDefinesFormHousesId.split(":_:");
                    errorPriceDefines+=split[1]+",";
                }
                root.setCode(500);
                root.setMsg("当前所选换热站"+stationIds+"的用户中，存在某些用户使用了非法的热费单价类型:"+errorPriceDefines);
                root.setData(true);
                return root;
            }





            userYearReceivableDetailService.deleteByStationIds(stationIdlst,annual);//先全部刪掉！
            userYearHeatService.deleteByStationIds(stationIdlst,annual);//先全部刪掉！

            root.setCode(0);
            root.setMsg("重置成功");
            root.setData(true);
            return root;
        }
        root.setCode(500);
        root.setMsg("参数不正常");
        root.setData(false);
        return root;
    }

    @ApiOperation(value = "整个层级批量生成  stationIds和commuityIds,要么传入单个值，要么传入null")
    @RequestMapping("/wholeLevelBatchProduct")
    @Transactional
    public Root wholeLevelBatchProduct(@RequestParam String companyId,
                                       @RequestParam(value = "stationIds", required = false)  String stationIds,
                                       @RequestParam(value = "commuityIds", required = false)  String commuityIds,
                                       @RequestParam String annual) {

        if((stationIds==null || stationIds.equals("")) && (commuityIds==null || commuityIds.equals(""))){
            return resetAll(companyId,annual);
        }else if(stationIds!=null && !stationIds.equals("") && commuityIds!=null && !commuityIds.equals("")){

            List<String> commuityIdlst = new ArrayList<>();
            if(commuityIds == null || commuityIds.equals("") ){
                commuityIdlst =  null;
            }else{
                String[] split = commuityIds.split(",");
                for (String s : split) {
                    commuityIdlst.add(s);
                }
            }

            Root root = new Root();


            int netStatusNullHousesTotal = houseService.selectCountNetStatusNullForCommunityIds(commuityIdlst);
            if(netStatusNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前所选小区"+commuityIds+"的用户中，有用户的入网状态为空，请修改用户的入网状态后再生成数据");
                root.setData(true);
                return root;
            }


            int heatingStatusNullHousesTotal = houseService.selectCountHeatingStatusNullForCommunityIds(commuityIdlst);
            if(heatingStatusNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前所选小区"+commuityIds+"的用户中，有用户的供暖状态为空，请修改用户的供暖状态后再生成数据");
                root.setData(true);
                return root;
            }


            int priceDefineNullHousesTotal = houseService.selectCountPriceDefineNullForCommunityIds(commuityIdlst);
            if(priceDefineNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前所选小区"+commuityIds+"的用户中，有用户的热费单价类型为空，请修改用户的热费单价类型后再生成数据");
                root.setData(true);
                return root;
            }


            int chargeTypeNullHousesTotal = houseService.selectCountChargeTypeNullForCommunityIds(commuityIdlst);
            if(chargeTypeNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前所选小区"+commuityIds+"的用户中，有用户的供热收费类型为空，请修改用户的供热收费类型后再生成数据");
                root.setData(true);
                return root;
            }

            Date a=   new Date();
            System.out.println("开始时间："+a);
            //先修改PriceDefine表，把加密的数据解密开，插入到tmp字段
            List<PriceDefine> priceDefines = priceDefineService.selectByAnnualAndCommunityIds(annual, commuityIds);
            if(priceDefines == null || priceDefines.size() == 0){
                root.setCode(500);
                root.setMsg("当前小区"+commuityIds+"在采暖季"+annual+"下，热量单价类型表没有数据");
                root.setData(true);
                return root;
            }
            List<HeatingTimeInfo> heatingTimeInfos = heatingTimeService.selectHeatingTimes(companyId, companyId, annual);
            if(heatingTimeInfos == null || heatingTimeInfos.size() == 0){
                root.setCode(500);
                root.setMsg("当前公司"+companyId+"在采暖季"+annual+"下，热费时间表没有数据");
                root.setData(true);
                return root;
            }else if( heatingTimeInfos.size() > 1){
                root.setCode(500);
                root.setMsg("当前公司"+companyId+"在采暖季"+annual+"下，热费时间表多于一条记录");
                root.setData(true);
                return root;
            }

            HeatingTimeInfo heatingTimeInfo = heatingTimeInfos.get(0);
            if(!heatingTimeInfo.getStationId().equals(heatingTimeInfo.getCompanyId())){
                root.setCode(500);
                root.setMsg("当前公司id:"+companyId+"不等于站id:"+heatingTimeInfo.getStationId());
                root.setData(true);
                return root;
            }


            List<String> priceDefinesIds = new ArrayList<>();
            for (PriceDefine priceDefine : priceDefines) {
                priceDefinesIds.add(priceDefine.getPrimaryId()+":_:"+priceDefine.getPriceName());
            }

            List<PriceDefine> priceDefinesFormHouses = houseService. selectPriceDefineByCommunityIds(commuityIdlst);
            List<String> priceDefinesFormHousesIds = new ArrayList<>();
            for (PriceDefine priceDefinesFormHouse : priceDefinesFormHouses) {
                priceDefinesFormHousesIds.add(priceDefinesFormHouse.getPrimaryId()+":_:"+priceDefinesFormHouse.getPriceName());
            }

            priceDefinesFormHousesIds.removeAll(priceDefinesIds);
            if(priceDefinesFormHousesIds != null && priceDefinesFormHousesIds.size() > 0){
                String errorPriceDefines = "";
                for (String priceDefinesFormHousesId : priceDefinesFormHousesIds) {
                    String[] split = priceDefinesFormHousesId.split(":_:");
                    errorPriceDefines+=split[1]+",";
                }
                root.setCode(500);
                root.setMsg("当前所选小区"+commuityIds+"的用户中，存在某些用户使用了非法的热费单价类型:"+errorPriceDefines);
                root.setData(true);
                return root;
            }


            for (PriceDefine priceDefine : priceDefines) {

                priceDefine.setAreaPriceTmp(new BigDecimal(priceDefine.getAreaPrice()));
                priceDefine.setExchangerPriceTmp(new BigDecimal(priceDefine.getExchangerPrice()));
                priceDefine.setHeatPriceTmp(new BigDecimal(priceDefine.getHeatPrice()));
                priceDefine.setPrePriceTmp(new BigDecimal(priceDefine.getPrePrice()));

                priceDefineService.updateTmpValueByPrimaryKey(priceDefine.getPrePriceTmp(),priceDefine.getAreaPriceTmp(),priceDefine.getHeatPriceTmp(),priceDefine.getExchangerPriceTmp(),priceDefine.getPrimaryId());

            }

            int lastAnnual = Integer.parseInt(annual) - 1;
            String lastannual = lastAnnual+"";
            int i0 = 0;
            int i1 = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int j0 = 0;
            int j1 = 0;
            int j2_1 = 0;
            int j2_2 = 0;
            int j2_3 = 0;
            int j2_4 = 0;
            int j3 = 0;
            int j4 = 0;
            try {
                i0 = userYearReceivableDetailService.deleteByCommuityIds(commuityIdlst,annual);//先全部刪掉！
                i1 = userYearReceivableDetailService.insertChargingItem1ForGeneratedForSelectedCommunity(commuityIdlst,annual,UserUtils.getUserId());
                i2 = userYearReceivableDetailService.insertChargingItem2ForGeneratedForSelectedCommunity(commuityIdlst,annual,UserUtils.getUserId());
                i3 = userYearReceivableDetailService.insertChargingItem3ForGeneratedForSelectedCommunity(commuityIdlst,annual,UserUtils.getUserId());
                i4 = userYearReceivableDetailService.insertChargingItem7ForGeneratedForSelectedCommunity(commuityIdlst,annual,UserUtils.getUserId());
            } catch (Exception e) {
                throw new RuntimeException("生成UserYearReceivableDeta过程出错，执行回滚："+e.getMessage());
            }

            int allHousesTotal = houseService.getCountByCommunityIds(commuityIdlst);
            int generatedHousesTotal = 0;
            int housesInNetTotal = houseService.selectCountByNetStatusForCommunityIds(commuityIdlst,"net_status_1");
            try {
                j0 = userYearHeatService.deleteByCommuityIds(commuityIdlst,annual);//先全部刪掉！
                j2_1 = userYearHeatService.insertForGenerated1ForSelectedCommunity(commuityIdlst,annual,UserUtils.getUserId());
                j2_2 = userYearHeatService.insertForGenerated2ForSelectedCommunity(commuityIdlst,annual,UserUtils.getUserId());
                j2_3 = userYearHeatService.insertForGenerated3ForSelectedCommunity(commuityIdlst,annual,UserUtils.getUserId());


                userYearHeatService.updateForGenerated2ForSelectedCommunity(commuityIdlst,annual);
                userYearHeatService.updateForGenerated3ForSelectedCommunity(commuityIdlst,annual,heatingTimeInfo.getStartTime());
                userYearHeatService.updateForGenerated4ForSelectedCommunity(commuityIdlst,annual,heatingTimeInfo.getEndTime());




                generatedHousesTotal = j1+j2_1+j2_2+j2_3+j2_4+j3+j4;

            } catch (Exception e) {
                throw new RuntimeException("生成UserYearReceivableDeta过程出错，执行回滚："+e.getMessage());
            }
            for (PriceDefine priceDefine : priceDefines) {

                priceDefine.setAreaPriceTmp(null);
                priceDefine.setExchangerPriceTmp(null);
                priceDefine.setHeatPriceTmp(null);
                priceDefine.setPrePriceTmp(null);


                priceDefineService.updateTmpValueByPrimaryKey(null,null,null,null,priceDefine.getPrimaryId());
            }

            String generateSuccessMsg = "生成成功：用户总数量："+allHousesTotal+"，生成"+generatedHousesTotal+"，未入网用户"+(allHousesTotal-housesInNetTotal);
            Date b=   new Date();
            long interval = (b.getTime() - a.getTime())/1000;
            System.out.println("结束时间："+new Date());
            System.out.println("差多少秒："+interval);

            root.setCode(0);
            root.setMsg(generateSuccessMsg);
            root.setData(true);
            return root;
        }else if((stationIds!=null && !stationIds.equals("")) && (commuityIds==null || commuityIds.equals(""))){

            List<String> stationIdlst = new ArrayList<>();
            if(stationIds == null || stationIds.equals("") ){
                stationIdlst =  null;
            }else{
                String[] split = stationIds.split(",");
                for (String s : split) {
                    stationIdlst.add(s);
                }
            }
            Root root = new Root();

            int netStatusNullHousesTotal = houseService.selectCountNetStatusNullForStationIds(stationIdlst);
            if(netStatusNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前所选换热站"+stationIds+"的用户中，有用户的入网状态为空，请修改用户的入网状态后再生成数据");
                root.setData(true);
                return root;
            }



            int heatingStatusNullHousesTotal = houseService.selectCountHeatingStatusNullForStationIds(stationIdlst);
            if(heatingStatusNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前所选换热站"+stationIds+"的用户中，有用户的供暖状态为空，请修改用户的供暖状态后再生成数据");
                root.setData(true);
                return root;
            }

            int priceDefineNullHousesTotal = houseService.selectCountPriceDefineNullForStationIds(stationIdlst);
            if(priceDefineNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前所选换热站"+stationIds+"的用户中，有用户的热费单价类型为空，请修改用户的热费单价类型后再生成数据");
                root.setData(true);
                return root;
            }

            int chargeTypeNullHousesTotal = houseService.selectCountChargeTypeNullForStationIds(stationIdlst);
            if(chargeTypeNullHousesTotal > 0){
                root.setCode(500);
                root.setMsg("当前所选换热站"+stationIds+"的用户中，有用户的供热收费类型为空，请修改用户的供热收费类型后再生成数据");
                root.setData(true);
                return root;
            }

            Date a=   new Date();
            System.out.println("开始时间："+a);
            //先修改PriceDefine表，把加密的数据解密开，插入到tmp字段
            List<PriceDefine> priceDefines = priceDefineService.selectByAnnualAndStationIds(annual, stationIds);
            if(priceDefines == null || priceDefines.size() == 0){
                root.setCode(500);
                root.setMsg("当前站"+stationIds+"在采暖季"+annual+"下，热量单价类型表没有数据");
                root.setData(true);
                return root;
            }
            List<HeatingTimeInfo> heatingTimeInfos = heatingTimeService.selectHeatingTimes(companyId, companyId, annual);
            if(heatingTimeInfos == null || heatingTimeInfos.size() == 0){
                root.setCode(500);
                root.setMsg("当前站"+stationIds+"在采暖季"+annual+"下，热费时间表没有数据");
                root.setData(true);
                return root;
            }else if( heatingTimeInfos.size() > 1){
                root.setCode(500);
                root.setMsg("当前站"+stationIds+"在采暖季"+annual+"下，热费时间表多于一条记录");
                root.setData(true);
                return root;
            }

            HeatingTimeInfo heatingTimeInfo = heatingTimeInfos.get(0);
            if(!heatingTimeInfo.getStationId().equals(heatingTimeInfo.getCompanyId())){
                root.setCode(500);
                root.setMsg("当前公司id:"+companyId+"不等于站id:"+heatingTimeInfo.getStationId());
                root.setData(true);
                return root;
            }


            List<String> priceDefinesIds = new ArrayList<>();
            for (PriceDefine priceDefine : priceDefines) {
                priceDefinesIds.add(priceDefine.getPrimaryId()+":_:"+priceDefine.getPriceName());
            }

            List<PriceDefine> priceDefinesFormHouses = houseService. selectPriceDefineByStationIds(stationIdlst);
            List<String> priceDefinesFormHousesIds = new ArrayList<>();
            for (PriceDefine priceDefinesFormHouse : priceDefinesFormHouses) {
                priceDefinesFormHousesIds.add(priceDefinesFormHouse.getPrimaryId()+":_:"+priceDefinesFormHouse.getPriceName());
            }

            priceDefinesFormHousesIds.removeAll(priceDefinesIds);
            if(priceDefinesFormHousesIds != null && priceDefinesFormHousesIds.size() > 0){
                String errorPriceDefines = "";
                for (String priceDefinesFormHousesId : priceDefinesFormHousesIds) {
                    String[] split = priceDefinesFormHousesId.split(":_:");
                    errorPriceDefines+=split[1]+",";
                }
                root.setCode(500);
                root.setMsg("当前所选换热站"+stationIds+"的用户中，存在某些用户使用了非法的热费单价类型:"+errorPriceDefines);
                root.setData(true);
                return root;
            }


            for (PriceDefine priceDefine : priceDefines) {

                priceDefine.setAreaPriceTmp(new BigDecimal(priceDefine.getAreaPrice()));
                priceDefine.setExchangerPriceTmp(new BigDecimal(priceDefine.getExchangerPrice()));
                priceDefine.setHeatPriceTmp(new BigDecimal(priceDefine.getHeatPrice()));
                priceDefine.setPrePriceTmp(new BigDecimal(priceDefine.getPrePrice()));

                priceDefineService.updateTmpValueByPrimaryKey(priceDefine.getPrePriceTmp(),priceDefine.getAreaPriceTmp(),priceDefine.getHeatPriceTmp(),priceDefine.getExchangerPriceTmp(),priceDefine.getPrimaryId());

            }

            int lastAnnual = Integer.parseInt(annual) - 1;
            String lastannual = lastAnnual+"";
            int i0 = 0;
            int i1 = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int j0 = 0;
            int j1 = 0;
            int j2_1 = 0;
            int j2_2 = 0;
            int j2_3 = 0;
            int j2_4 = 0;
            int j3 = 0;
            int j4 = 0;
            try {

                i0 = userYearReceivableDetailService.deleteByStationIds(stationIdlst,annual);//先全部刪掉！



                i1 = userYearReceivableDetailService.insertChargingItem1ForGeneratedForOneStationAllCommunity(stationIdlst.get(0),annual,UserUtils.getUserId());
                i2 = userYearReceivableDetailService.insertChargingItem2ForGeneratedForOneStationAllCommunity(stationIdlst.get(0),annual,UserUtils.getUserId());
                i3 = userYearReceivableDetailService.insertChargingItem3ForGeneratedForOneStationAllCommunity(stationIdlst.get(0),annual,UserUtils.getUserId());
                i4 = userYearReceivableDetailService.insertChargingItem7ForGeneratedForOneStationAllCommunity(stationIdlst.get(0),annual,UserUtils.getUserId());
            } catch (Exception e) {
                throw new RuntimeException("生成UserYearReceivableDeta过程出错，执行回滚："+e.getMessage());
            }

            int allHousesTotal = houseService.getCountByStationIds(stationIdlst);
            int generatedHousesTotal = 0;
            int housesInNetTotal = houseService.selectCountByNetStatusForStationIds(stationIdlst,"net_status_1");
            try {
                j0 = userYearHeatService.deleteByStationIds(stationIdlst,annual);//先全部刪掉！
                j2_1 = userYearHeatService.insertForGenerated1ForOneStationAllCommunity(stationIdlst.get(0),annual,UserUtils.getUserId());
                j2_2 = userYearHeatService.insertForGenerated2ForOneStationAllCommunity(stationIdlst.get(0),annual,UserUtils.getUserId());
                j2_3 = userYearHeatService.insertForGenerated3ForOneStationAllCommunity(stationIdlst.get(0),annual,UserUtils.getUserId());


                userYearHeatService.updateForGenerated2ForOneStationAllCommunity(stationIdlst.get(0),annual);
                userYearHeatService.updateForGenerated3ForOneStationAllCommunity(stationIdlst.get(0),annual,heatingTimeInfo.getStartTime());
                userYearHeatService.updateForGenerated4ForOneStationAllCommunity(stationIdlst.get(0),annual,heatingTimeInfo.getEndTime());




                generatedHousesTotal = j1+j2_1+j2_2+j2_3+j2_4+j3+j4;

            } catch (Exception e) {
                throw new RuntimeException("生成UserYearReceivableDeta过程出错，执行回滚："+e.getMessage());
            }
            for (PriceDefine priceDefine : priceDefines) {

                priceDefine.setAreaPriceTmp(null);
                priceDefine.setExchangerPriceTmp(null);
                priceDefine.setHeatPriceTmp(null);
                priceDefine.setPrePriceTmp(null);


                priceDefineService.updateTmpValueByPrimaryKey(null,null,null,null,priceDefine.getPrimaryId());
            }

            String generateSuccessMsg = "生成成功：用户总数量："+allHousesTotal+"，生成"+generatedHousesTotal+"，未入网用户"+(allHousesTotal-housesInNetTotal);
            Date b=   new Date();
            long interval = (b.getTime() - a.getTime())/1000;
            System.out.println("结束时间："+new Date());
            System.out.println("差多少秒："+interval);

            root.setCode(0);
            root.setMsg(generateSuccessMsg);
            root.setData(true);
            return root;
        }


        Root root = new Root();
        root.setCode(500);
        root.setMsg("参数不正常");
        root.setData(false);
        return root;
    }


    @ApiOperation(value = "确定按钮，打钩的会重新生成，不打钩的会重置")
    @RequestMapping("/resetAndGenerateByCheck")
    @Transactional
    public Root resetAndGenerateByCheck(@RequestParam String companyId,@RequestParam(value = "resetConsumerIds", required = false) String resetConsumerIds,@RequestParam String generateConsumerIds,@RequestParam String annual) {
        List<String> consumerIdlst = new ArrayList<>();
        if(generateConsumerIds == null || generateConsumerIds.equals("") ){
            consumerIdlst =  null;
        }else{
            String[] split = generateConsumerIds.split(",");
            for (String s : split) {
                consumerIdlst.add(s);
            }
        }

        Root root = new Root();

        if(resetConsumerIds != null && !resetConsumerIds.equals("")){

            Root root1 = resetLevelGenerationState(companyId,resetConsumerIds,annual);
            if(root1.getCode() != 0){
                throw new RuntimeException("重置过程出错，执行回滚："+root1.getMsg());
            }

        }


        int netStatusNullHousesTotal = houseService.selectCountNetStatusNullForConsumerIds(consumerIdlst);
        if(netStatusNullHousesTotal > 0){
            root.setCode(500);
            root.setMsg("当前所选用户"+generateConsumerIds+"的用户中，有用户的入网状态为空，请修改用户的入网状态后再生成数据");
            root.setData(true);
            return root;
        }

        int heatingStatusNullHousesTotal = houseService.selectCountHeatingStatusNullForConsumerIds(consumerIdlst);
        if(heatingStatusNullHousesTotal > 0){
            root.setCode(500);
            root.setMsg("当前所选用户"+generateConsumerIds+"的用户中，有用户的供暖状态为空，请修改用户的供暖状态后再生成数据");
            root.setData(true);
            return root;
        }

        int priceDefineNullHousesTotal = houseService.selectCountPriceDefineNullForConsumerIds(consumerIdlst);
        if(priceDefineNullHousesTotal > 0){
            root.setCode(500);
            root.setMsg("当前所选用户"+generateConsumerIds+"的用户中，有用户的热费单价类型为空，请修改用户的热费单价类型后再生成数据");
            root.setData(true);
            return root;
        }

        int chargeTypeNullHousesTotal = houseService.selectCountChargeTypeNullForConsumerIds(consumerIdlst);
        if(chargeTypeNullHousesTotal > 0){
            root.setCode(500);
            root.setMsg("当前所选用户"+generateConsumerIds+"的用户中，有用户的供热收费类型为空，请修改用户的供热收费类型后再生成数据");
            root.setData(true);
            return root;
        }




        Date a=   new Date();
        System.out.println("开始时间："+a);
        //先修改PriceDefine表，把加密的数据解密开，插入到tmp字段
        List<PriceDefine> priceDefines = priceDefineService.selectByAnnualAndConsumerIds(annual, generateConsumerIds);
        if(priceDefines == null || priceDefines.size() == 0){
            root.setCode(500);
            root.setMsg("当前所选用户"+generateConsumerIds+"在采暖季"+annual+"下，热量单价类型表没有数据");
            root.setData(true);
            return root;
        }
        List<HeatingTimeInfo> heatingTimeInfos = heatingTimeService.selectHeatingTimes(companyId, companyId, annual);
        if(heatingTimeInfos == null || heatingTimeInfos.size() == 0){
            root.setCode(500);
            root.setMsg("当前公司"+companyId+"在采暖季"+annual+"下，热费时间表没有数据");
            root.setData(true);
            return root;
        }else if( heatingTimeInfos.size() > 1){
            root.setCode(500);
            root.setMsg("当前公司"+companyId+"在采暖季"+annual+"下，热费时间表多于一条记录");
            root.setData(true);
            return root;
        }

        HeatingTimeInfo heatingTimeInfo = heatingTimeInfos.get(0);
        if(!heatingTimeInfo.getStationId().equals(heatingTimeInfo.getCompanyId())){
            root.setCode(500);
            root.setMsg("当前公司id:"+companyId+"不等于站id:"+heatingTimeInfo.getStationId());
            root.setData(true);
            return root;
        }

        List<String> priceDefinesIds = new ArrayList<>();
        for (PriceDefine priceDefine : priceDefines) {
            priceDefinesIds.add(priceDefine.getPrimaryId()+":_:"+priceDefine.getPriceName());
        }

        List<PriceDefine> priceDefinesFormHouses = houseService. selectPriceDefineByStationIds(consumerIdlst);
        List<String> priceDefinesFormHousesIds = new ArrayList<>();
        for (PriceDefine priceDefinesFormHouse : priceDefinesFormHouses) {
            priceDefinesFormHousesIds.add(priceDefinesFormHouse.getPrimaryId()+":_:"+priceDefinesFormHouse.getPriceName());
        }

        priceDefinesFormHousesIds.removeAll(priceDefinesIds);
        if(priceDefinesFormHousesIds != null && priceDefinesFormHousesIds.size() > 0){
            String errorPriceDefines = "";
            for (String priceDefinesFormHousesId : priceDefinesFormHousesIds) {
                String[] split = priceDefinesFormHousesId.split(":_:");
                errorPriceDefines+=split[1]+",";
            }
            root.setCode(500);
            root.setMsg("当前所选用户"+generateConsumerIds+"的用户中，存在某些用户使用了非法的热费单价类型:"+errorPriceDefines);
            root.setData(true);
            return root;
        }



        for (PriceDefine priceDefine : priceDefines) {

            priceDefine.setAreaPriceTmp(new BigDecimal(priceDefine.getAreaPrice()));
            priceDefine.setExchangerPriceTmp(new BigDecimal(priceDefine.getExchangerPrice()));
            priceDefine.setHeatPriceTmp(new BigDecimal(priceDefine.getHeatPrice()));
            priceDefine.setPrePriceTmp(new BigDecimal(priceDefine.getPrePrice()));

            priceDefineService.updateTmpValueByPrimaryKey(priceDefine.getPrePriceTmp(),priceDefine.getAreaPriceTmp(),priceDefine.getHeatPriceTmp(),priceDefine.getExchangerPriceTmp(),priceDefine.getPrimaryId());

        }

        int lastAnnual = Integer.parseInt(annual) - 1;
        String lastannual = lastAnnual+"";
        int i0 = 0;
        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int j0 = 0;
        int j1 = 0;
        int j2_1 = 0;
        int j2_2 = 0;
        int j2_3 = 0;
        int j2_4 = 0;
        int j3 = 0;
        int j4 = 0;
        try {

            i0 = userYearReceivableDetailService.deleteByConsumerIds(consumerIdlst,annual);//先全部刪掉！
            i1 = userYearReceivableDetailService.insertChargingItem1ForGeneratedForSelectedConsumer(consumerIdlst,annual,UserUtils.getUserId());
            i2 = userYearReceivableDetailService.insertChargingItem2ForGeneratedForSelectedConsumer(consumerIdlst,annual,UserUtils.getUserId());
            i3 = userYearReceivableDetailService.insertChargingItem3ForGeneratedForSelectedConsumer(consumerIdlst,annual,UserUtils.getUserId());
            i4 = userYearReceivableDetailService.insertChargingItem7ForGeneratedForSelectedConsumer(consumerIdlst,annual,UserUtils.getUserId());
        } catch (Exception e) {
            throw new RuntimeException("生成UserYearReceivableDeta过程出错，执行回滚："+e.getMessage());
        }



        int allHousesTotal = houseService.getCountByConsumerIds(consumerIdlst);
        int generatedHousesTotal = 0;
        int housesInNetTotal = houseService.selectCountByNetStatusForConsumerIds(consumerIdlst,"net_status_1");
        try {
            j0 = userYearHeatService.deleteByConsumerIds(consumerIdlst,annual);//先全部刪掉！


            j2_1 = userYearHeatService.insertForGenerated1ForSelectedConsumer(consumerIdlst,annual,UserUtils.getUserId());
            j2_2 = userYearHeatService.insertForGenerated2ForSelectedConsumer(consumerIdlst,annual,UserUtils.getUserId());
            j2_3 = userYearHeatService.insertForGenerated3ForSelectedConsumer(consumerIdlst,annual,UserUtils.getUserId());


            userYearHeatService.updateForGenerated2ForSelectedConsumer(consumerIdlst,annual);
            userYearHeatService.updateForGenerated3ForSelectedConsumer(consumerIdlst,annual,heatingTimeInfo.getStartTime());
            userYearHeatService.updateForGenerated4ForSelectedConsumer(consumerIdlst,annual,heatingTimeInfo.getEndTime());




            generatedHousesTotal = j1+j2_1+j2_2+j2_3+j2_4+j3+j4;

        } catch (Exception e) {
            throw new RuntimeException("生成UserYearReceivableDeta过程出错，执行回滚："+e.getMessage());
        }
        for (PriceDefine priceDefine : priceDefines) {

            priceDefine.setAreaPriceTmp(null);
            priceDefine.setExchangerPriceTmp(null);
            priceDefine.setHeatPriceTmp(null);
            priceDefine.setPrePriceTmp(null);


            priceDefineService.updateTmpValueByPrimaryKey(null,null,null,null,priceDefine.getPrimaryId());
        }

        String generateSuccessMsg = "生成成功：用户总数量："+allHousesTotal+"，生成"+generatedHousesTotal+"，未入网用户"+(allHousesTotal-housesInNetTotal);
        Date b=   new Date();
        long interval = (b.getTime() - a.getTime())/1000;
        System.out.println("结束时间："+new Date());
        System.out.println("差多少秒："+interval);

        root.setCode(0);
        root.setMsg(generateSuccessMsg);
        root.setData(true);
        return root;


    }


    @ApiOperation(value = "这个方法是用于用户重置receivable表的，但是原本的记录不删除")
    @RequestMapping("/resetUserYearReceivableDetail")
    @Transactional
    public Root resetUserYearReceivableDetail(@RequestParam String consumerId,@RequestParam String annual) {
        Root root = new Root();

        Root root1 = initUserYearReceivableDetailData(annual,consumerId,false);
        if(root1.getCode() != 0){
            throw new RuntimeException("生成UserYearReceivableDeta过程出错，执行回滚："+root1.getMsg());
        }
        Boolean bResult = updateUserYearHeat(consumerId,annual);
        if(bResult != true){
            throw new RuntimeException("生成UserYearHeat过程出错，执行回滚："+"修改用户年度供暖信息表出错");
        }

        root.setCode(0);
        root.setMsg("生成成功");
        root.setData(true);
        return root;
    }

    @ApiOperation(value = "申请停暖")
    @RequestMapping("/stopHeat")
    @Transactional
    public Root stopHeat(@RequestParam String consumerId,@RequestParam String annual) {
        Root root = new Root();

        userYearReceivableDetailService.deleteGenerated(consumerId,annual);

        Boolean bResult = updateUserYearHeat(consumerId,annual);
        if(bResult != true){
            throw new RuntimeException("申请停暖过程出错1，执行回滚："+"修改用户年度供暖信息表出错");
        }

        UserYearHeat byUserAndAnnual = userYearHeatService.findByUserAndAnnual(consumerId, annual);
        if(byUserAndAnnual == null){
            throw new RuntimeException("申请停暖过程出错2，执行回滚："+"找不到用户年度供暖信息表");
        }

        byUserAndAnnual.setHeatingStatus("heating_status_2");
        byUserAndAnnual.setPayOver("pay_over_4");

        userYearHeatService.updateByPrimaryKey(byUserAndAnnual);

        House houseById = houseService.getHouseById(consumerId);
        if(byUserAndAnnual == null){
            throw new RuntimeException("申请停暖过程出错2，执行回滚："+"找不到用户");
        }

        houseById.setHeatingStatus("heating_status_2");
        houseService.modifyHouse(houseById);

        root.setCode(0);
        root.setMsg("申请停暖成功");
        root.setData(true);
        return root;
    }

    public Root resetLevelGenerationState(@RequestParam String companyId,@RequestParam String consumerIds,@RequestParam String annual) {
        Root root = new Root();
        String[] split = consumerIds.split(",");


        List<String> consumerIdlst = new ArrayList<>();
        if(consumerIds == null || consumerIds.equals("") ){
            consumerIdlst =  null;
        }else{
            for (String s : split) {
                consumerIdlst.add(s);
            }
        }


        int netStatusNullHousesTotal = houseService.selectCountNetStatusNullForConsumerIds(consumerIdlst);
        if(netStatusNullHousesTotal > 0){
            root.setCode(500);
            root.setMsg("当前所选用户"+consumerIds+"的用户中，有用户的入网状态为空，请修改用户的入网状态后再重置");
            root.setData(true);
            return root;
        }

        int heatingStatusNullHousesTotal = houseService.selectCountHeatingStatusNullForConsumerIds(consumerIdlst);
        if(heatingStatusNullHousesTotal > 0){
            root.setCode(500);
            root.setMsg("当前所选用户"+consumerIds+"的用户中，有用户的供暖状态为空，请修改用户的供暖状态后再重置");
            root.setData(true);
            return root;
        }

        int priceDefineNullHousesTotal = houseService.selectCountPriceDefineNullForConsumerIds(consumerIdlst);
        if(priceDefineNullHousesTotal > 0){
            root.setCode(500);
            root.setMsg("当前所选用户"+consumerIds+"的用户中，有用户的热费单价类型为空，请修改用户的热费单价类型后再重置");
            root.setData(true);
            return root;
        }

        int chargeTypeNullHousesTotal = houseService.selectCountChargeTypeNullForConsumerIds(consumerIdlst);
        if(chargeTypeNullHousesTotal > 0){
            root.setCode(500);
            root.setMsg("当前所选用户"+consumerIds+"的用户中，有用户的供热收费类型为空，请修改用户的供热收费类型后再重置");
            root.setData(true);
            return root;
        }




        Date a=   new Date();
        System.out.println("开始时间："+a);
        //先修改PriceDefine表，把加密的数据解密开，插入到tmp字段
        List<PriceDefine> priceDefines = priceDefineService.selectByAnnualAndConsumerIds(annual, consumerIds);
        if(priceDefines == null || priceDefines.size() == 0){
            root.setCode(500);
            root.setMsg("当前所选用户"+consumerIds+"在采暖季"+annual+"下，热量单价类型表没有数据");
            root.setData(true);
            return root;
        }
        List<HeatingTimeInfo> heatingTimeInfos = heatingTimeService.selectHeatingTimes(companyId, companyId, annual);
        if(heatingTimeInfos == null || heatingTimeInfos.size() == 0){
            root.setCode(500);
            root.setMsg("当前公司"+companyId+"在采暖季"+annual+"下，热费时间表没有数据");
            root.setData(true);
            return root;
        }else if( heatingTimeInfos.size() > 1){
            root.setCode(500);
            root.setMsg("当前公司"+companyId+"在采暖季"+annual+"下，热费时间表多于一条记录");
            root.setData(true);
            return root;
        }

        HeatingTimeInfo heatingTimeInfo = heatingTimeInfos.get(0);
        if(!heatingTimeInfo.getStationId().equals(heatingTimeInfo.getCompanyId())){
            root.setCode(500);
            root.setMsg("当前公司id:"+companyId+"不等于站id:"+heatingTimeInfo.getStationId());
            root.setData(true);
            return root;
        }

        List<String> priceDefinesIds = new ArrayList<>();
        for (PriceDefine priceDefine : priceDefines) {
            priceDefinesIds.add(priceDefine.getPrimaryId()+":_:"+priceDefine.getPriceName());
        }

        List<PriceDefine> priceDefinesFormHouses = houseService. selectPriceDefineByStationIds(consumerIdlst);
        List<String> priceDefinesFormHousesIds = new ArrayList<>();
        for (PriceDefine priceDefinesFormHouse : priceDefinesFormHouses) {
            priceDefinesFormHousesIds.add(priceDefinesFormHouse.getPrimaryId()+":_:"+priceDefinesFormHouse.getPriceName());
        }

        priceDefinesFormHousesIds.removeAll(priceDefinesIds);
        if(priceDefinesFormHousesIds != null && priceDefinesFormHousesIds.size() > 0){
            String errorPriceDefines = "";
            for (String priceDefinesFormHousesId : priceDefinesFormHousesIds) {
                String[] split1 = priceDefinesFormHousesId.split(":_:");
                errorPriceDefines+=split1[1]+",";
            }
            root.setCode(500);
            root.setMsg("当前所选用户"+consumerIds+"的用户中，存在某些用户使用了非法的热费单价类型，导致无法重置:"+errorPriceDefines);
            root.setData(true);
            return root;
        }



        int i = userYearHeatService.deleteByConsumerIds(consumerIds, null, annual);
        int j = userYearReceivableDetailService.deleteByConsumerIds(consumerIds, null, annual);

        root.setCode(0);
        root.setMsg("重置成功");
        root.setData(split.length);
        return root;
    }

    @ApiOperation(value = "重置数据。这个方法，需要执行非常长的时间，因为数据量太大")
    @RequestMapping("/resetAll")
    @Transactional
    public Root resetAll(@RequestParam String companyId,@RequestParam String annual) {
        Root root = new Root();
        Date a=   new Date();
        System.out.println("开始时间："+a);

        int netStatusNullHousesTotal = houseService.selectCountNetStatusNull(companyId);
        if(netStatusNullHousesTotal > 0){
            root.setCode(500);
            root.setMsg("当前公司"+companyId+"的用户中，有用户的入网状态为空，请修改用户的入网状态后再生成数据");
            root.setData(true);
            return root;
        }

        int heatingStatusNullHousesTotal = houseService.selectCountHeatingStatusNull(companyId);
        if(heatingStatusNullHousesTotal > 0){
            root.setCode(500);
            root.setMsg("当前公司"+companyId+"的用户中，有用户的供暖状态为空，请修改用户的供暖状态后再生成数据");
            root.setData(true);
            return root;
        }

        int priceDefineNullHousesTotal = houseService.selectCountPriceDefineNull(companyId);
        if(priceDefineNullHousesTotal > 0){
            root.setCode(500);
            root.setMsg("当前公司"+companyId+"的用户中，有用户的热费单价类型为空，请修改用户的热费单价类型后再生成数据");
            root.setData(true);
            return root;
        }

        int chargeTypeNullHousesTotal = houseService.selectCountChargeTypeNull(companyId);
        if(chargeTypeNullHousesTotal > 0){
            root.setCode(500);
            root.setMsg("当前公司"+companyId+"的用户中，有用户的供热收费类型为空，请修改用户的供热收费类型后再生成数据");
            root.setData(true);
            return root;
        }

        //先修改PriceDefine表，把加密的数据解密开，插入到tmp字段
        List<PriceDefine> priceDefines = priceDefineService.selectByAnnualAndCompanyIds(annual, companyId);
        if(priceDefines == null || priceDefines.size() == 0){
            root.setCode(500);
            root.setMsg("当前公司"+companyId+"在采暖季"+annual+"下，热量单价类型表没有数据");
            root.setData(true);
            return root;
        }
        List<HeatingTimeInfo> heatingTimeInfos = heatingTimeService.selectHeatingTimes(companyId, companyId, annual);
        if(heatingTimeInfos == null || heatingTimeInfos.size() == 0){
            root.setCode(500);
            root.setMsg("当前公司"+companyId+"在采暖季"+annual+"下，热费时间表没有数据");
            root.setData(true);
            return root;
        }else if( heatingTimeInfos.size() > 1){
            root.setCode(500);
            root.setMsg("当前公司"+companyId+"在采暖季"+annual+"下，热费时间表多于一条记录");
            root.setData(true);
            return root;
        }

        HeatingTimeInfo heatingTimeInfo = heatingTimeInfos.get(0);
        if(!heatingTimeInfo.getStationId().equals(heatingTimeInfo.getCompanyId())){
            root.setCode(500);
            root.setMsg("当前公司id:"+companyId+"不等于站id:"+heatingTimeInfo.getStationId());
            root.setData(true);
            return root;
        }


        List<String> priceDefinesIds = new ArrayList<>();
        for (PriceDefine priceDefine : priceDefines) {
            priceDefinesIds.add(priceDefine.getPrimaryId()+":_:"+priceDefine.getPriceName());
        }

        List<PriceDefine> priceDefinesFormHouses = houseService.selectPriceDefineByCompanyId(companyId);
        List<String> priceDefinesFormHousesIds = new ArrayList<>();
        for (PriceDefine priceDefinesFormHouse : priceDefinesFormHouses) {
            priceDefinesFormHousesIds.add(priceDefinesFormHouse.getPrimaryId()+":_:"+priceDefinesFormHouse.getPriceName());
        }

        priceDefinesFormHousesIds.removeAll(priceDefinesIds);
        if(priceDefinesFormHousesIds != null && priceDefinesFormHousesIds.size() > 0){
            String errorPriceDefines = "";
            for (String priceDefinesFormHousesId : priceDefinesFormHousesIds) {
                String[] split1 = priceDefinesFormHousesId.split(":_:");
                errorPriceDefines+=split1[1]+",";
            }
            root.setCode(500);
            root.setMsg("当前公司id:"+companyId+"的用户中，存在某些用户使用了非法的热费单价类型:"+errorPriceDefines);
            root.setData(true);
            return root;
        }


        for (PriceDefine priceDefine : priceDefines) {

            priceDefine.setAreaPriceTmp(new BigDecimal(priceDefine.getAreaPrice()));
            priceDefine.setExchangerPriceTmp(new BigDecimal(priceDefine.getExchangerPrice()));
            priceDefine.setHeatPriceTmp(new BigDecimal(priceDefine.getHeatPrice()));
            priceDefine.setPrePriceTmp(new BigDecimal(priceDefine.getPrePrice()));

            priceDefineService.updateTmpValueByPrimaryKey(priceDefine.getPrePriceTmp(),priceDefine.getAreaPriceTmp(),priceDefine.getHeatPriceTmp(),priceDefine.getExchangerPriceTmp(),priceDefine.getPrimaryId());

        }

        int lastAnnual = Integer.parseInt(annual) - 1;
        String lastannual = lastAnnual+"";
        int i0 = 0;
        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int j0 = 0;
        int j1 = 0;
        int j2_1 = 0;
        int j2_2 = 0;
        int j2_3 = 0;
        int j2_4 = 0;
        int j3 = 0;
        int j4 = 0;
         try {
            i0 = userYearReceivableDetailService.deleteByCompanyId(companyId,annual);//先全部刪掉！
            i1 = userYearReceivableDetailService.insertChargingItem1ForGenerated(companyId,annual,UserUtils.getUserId());
            i2 = userYearReceivableDetailService.insertChargingItem2ForGenerated(companyId,annual,UserUtils.getUserId());
            i3 = userYearReceivableDetailService.insertChargingItem3ForGenerated(companyId,annual,UserUtils.getUserId());
            i4 = userYearReceivableDetailService.insertChargingItem7ForGenerated(companyId,annual,UserUtils.getUserId());
        } catch (Exception e) {
            throw new RuntimeException("生成UserYearReceivableDeta过程出错，执行回滚："+e.getMessage());
        }



        int allHousesTotal = houseService.getAllCount(companyId);
        int generatedHousesTotal = 0;
        int housesInNetTotal = houseService.selectCountByNetStatus(companyId,"net_status_1");
        try {
            j0 = userYearHeatService.deleteByCompanyId(companyId,annual);//先全部刪掉！
          //   j1 = generatedHousesTotal =  userYearHeatService.insertForGenerated(companyId,annual,lastannual,UserUtils.getUserId());


            j2_1 = userYearHeatService.insertForGenerated1(companyId,annual,lastannual,UserUtils.getUserId());
            j2_2 = userYearHeatService.insertForGenerated2(companyId,annual,lastannual,UserUtils.getUserId());
            j2_3 = userYearHeatService.insertForGenerated3(companyId,annual,lastannual,UserUtils.getUserId());


//            userYearHeatService.updateForGenerated0(companyId,annual);
//            userYearHeatService.updateForGenerated1(companyId,annual);
            userYearHeatService.updateForGenerated2(companyId,annual);
            userYearHeatService.updateForGenerated3(companyId,annual,heatingTimeInfo.getStartTime());
            userYearHeatService.updateForGenerated4(companyId,annual,heatingTimeInfo.getEndTime());

//            j3 = userYearHeatService.insertForGeneratedHeatingStatusNull(companyId,annual,lastannual,UserUtils.getUserId());
//            j4 = userYearHeatService.insertForGeneratedNotHeatingStatus1(companyId,annual,lastannual,UserUtils.getUserId());
//
//
//            j2_1 = userYearHeatService.insertForGeneratedHeatingStatus1_1(companyId,annual,lastannual,UserUtils.getUserId());
//            j2_2 = userYearHeatService.insertForGeneratedHeatingStatus1_2(companyId,annual,lastannual,UserUtils.getUserId());
//            j2_3 = userYearHeatService.insertForGeneratedHeatingStatus1_3(companyId,annual,lastannual,UserUtils.getUserId());
//            j2_4 = userYearHeatService.insertForGeneratedHeatingStatus1_4(companyId,annual,lastannual,UserUtils.getUserId());



            generatedHousesTotal = j1+j2_1+j2_2+j2_3+j2_4+j3+j4;

        } catch (Exception e) {
            throw new RuntimeException("生成UserYearReceivableDeta过程出错，执行回滚："+e.getMessage());
        }
        for (PriceDefine priceDefine : priceDefines) {

            priceDefine.setAreaPriceTmp(null);
            priceDefine.setExchangerPriceTmp(null);
            priceDefine.setHeatPriceTmp(null);
            priceDefine.setPrePriceTmp(null);


            priceDefineService.updateTmpValueByPrimaryKey(null,null,null,null,priceDefine.getPrimaryId());
        }

       String generateSuccessMsg = "生成成功：用户总数量："+allHousesTotal+"，生成"+generatedHousesTotal+"，未入网用户"+(allHousesTotal-housesInNetTotal);
        Date b=   new Date();
        long interval = (b.getTime() - a.getTime())/1000;
        System.out.println("结束时间："+new Date());
        System.out.println("差多少秒："+interval);

        root.setCode(0);
        root.setMsg(generateSuccessMsg);
        root.setData(true);
        return root;
    }


    private Boolean updateUserYearHeat(String consumerId, String annual){
        BigDecimal totalAccountByConsumerId = userYearAccountDetailService.getTotalByConsumerId(consumerId, annual);
        if (totalAccountByConsumerId == null) totalAccountByConsumerId = BigDecimal.ZERO;

        BigDecimal totalReceivableByConsumerId = userYearReceivableDetailService.getTotalByConsumerId(consumerId,annual);
        if(totalReceivableByConsumerId == null) totalReceivableByConsumerId = BigDecimal.ZERO;


        UserYearHeat byUserAndAnnual = userYearHeatService.findByUserAndAnnual(consumerId, annual);
        if(byUserAndAnnual == null){
            return false;
        }

        byUserAndAnnual.setSumReceivable(totalReceivableByConsumerId);
        byUserAndAnnual.setSumAccount(totalAccountByConsumerId);//从user_year_receivable_detail 用户年度采暖费用明细表 中取数

        byUserAndAnnual.setMarginNow(totalAccountByConsumerId.subtract(totalReceivableByConsumerId));
        userYearHeatService.updateByPrimaryKey(byUserAndAnnual);
        return true;


    }



}

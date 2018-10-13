package cn.plou.web.charge.chargeconfig.service.impl;

import cn.plou.web.charge.heatingmanage.domain.HouseYearHeatstateDetail;
import cn.plou.web.charge.heatingmanage.event.PriceDefineChangeEvent;
import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.eventhandle.EventManager;
import cn.plou.web.common.utils.FlowIdTool;
import cn.plou.web.common.utils.IDWorker;
import cn.plou.web.common.utils.SecureUtils;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.commonMessage.typeMst.dao.TypeMstMapper;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.permission.userLogin.dao.UserLoginMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cn.plou.web.charge.chargeconfig.entity.PriceDefine;
import cn.plou.web.charge.chargeconfig.dao.PriceDefineMapper;
import cn.plou.web.charge.chargeconfig.service.PriceDefineService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class PriceDefineServiceImpl implements PriceDefineService {

    @Resource
    private PriceDefineMapper priceDefineMapper;

    @Resource
    private
    UserLoginMapper userLoginMapper;


    @Resource
    private TypeMstMapper typeMstMapper;


    @Resource
    private EventManager eventManager;

    /**
     * @param priceDefine
     * @return 增加新的一条热价   只需要先在缓存表里加，正式表暂不加，以后加权限判断， 若为高权限账户则直接可以进行增加无需审核
     */
    @Override
    public int addNewPriceDefine(PriceDefine priceDefine) {
        PriceDefine  existDefine=  priceDefineMapper.findBypriceNameAndcompanyId(priceDefine.getPriceName(),priceDefine.getCompanyId());
        if(existDefine!=null){
            throw  new BusinessException("操作失败！该公司已有名称相同的热价！");
        }
        priceDefine.setPrimaryId(IDWorker.uuid());//主键uuid
        priceDefine.setApproveRes("未审核-内容新增");//审核状态
        priceDefine.setCreateDate(new Date());
        priceDefine.setCreateUser(userLoginMapper.selectByPrimaryKey(UserUtils.getUserId()).getUsername());
        TypeMst typeMst = typeMstMapper.getByTypeKbnAndTypeName(Constant.GLOBAL_BUSINESS_TYPE, Constant.GBT_PRICE_DEFINE);
        if (typeMst == null) {
            log.error("业务类型获取失败！");
            throw new BusinessException("业务类型获取失败！");
        }
        String flowId = FlowIdTool.GetFlowId(priceDefine.getCompanyId(), typeMst.getTypeId());//审核流水号
        priceDefine.setApproveSerial(flowId);
        priceDefine.setApproveType("新增");

        //加密
        priceDefine.setPrePrice(SecureUtils.AESEncode(Constant.AES_RULES, priceDefine.getPrePrice()));
        priceDefine.setAreaPrice(SecureUtils.AESEncode(Constant.AES_RULES, priceDefine.getAreaPrice()));
        priceDefine.setHeatPrice(SecureUtils.AESEncode(Constant.AES_RULES, priceDefine.getHeatPrice()));
        priceDefine.setExchangerPrice(SecureUtils.AESEncode(Constant.AES_RULES, priceDefine.getExchangerPrice()));

        return priceDefineMapper.insertToTemp(priceDefine);

    }


    @Override
    public int update(PriceDefine priceDefine) {

        PriceDefine  existDefine=  priceDefineMapper.findBypriceNameAndcompanyId(priceDefine.getPriceName(),priceDefine.getCompanyId());
        if(existDefine!=null && !existDefine.getPrimaryId().equals(priceDefine.getPrimaryId())){
            throw  new BusinessException("操作失败！该公司已有名称相同的热价！");
        }

        //取出主表内容暂存
        PriceDefine oldPriceDefine = priceDefineMapper.selectByPrimaryKey(priceDefine.getPrimaryId());
        if(oldPriceDefine==null){
            throw  new BusinessException("操作失败！该热价未审核完成前不能进行其他操作！");
        }

        //先向temp表里插
        priceDefine.setPrimaryId(IDWorker.uuid());
        priceDefine.setApproveRes("未审核-内容更新");
        priceDefine.setCreateDate(new Date());
        priceDefine.setCreateUser(userLoginMapper.selectByPrimaryKey(UserUtils.getUserId()).getUsername());

        TypeMst typeMst = typeMstMapper.getByTypeKbnAndTypeName(Constant.GLOBAL_BUSINESS_TYPE, Constant.GBT_PRICE_DEFINE);
        if (typeMst == null) {
            log.error("业务类型获取失败！");
            throw new BusinessException("业务类型获取失败！");
        }
        String flowId = FlowIdTool.GetFlowId(priceDefine.getCompanyId(), typeMst.getTypeId());//审核流水号

        priceDefine.setUpdateDate(new Date());
        priceDefine.setUpdateUser(userLoginMapper.selectByPrimaryKey(UserUtils.getUserId()).getUsername());
        priceDefine.setApproveSerial(flowId);
        priceDefine.setApproveType("更新");
        //加密
        priceDefine.setPrePrice(SecureUtils.AESEncode(Constant.AES_RULES, priceDefine.getPrePrice()));
        priceDefine.setAreaPrice(SecureUtils.AESEncode(Constant.AES_RULES, priceDefine.getAreaPrice()));
        priceDefine.setHeatPrice(SecureUtils.AESEncode(Constant.AES_RULES, priceDefine.getHeatPrice()));
        priceDefine.setExchangerPrice(SecureUtils.AESEncode(Constant.AES_RULES, priceDefine.getExchangerPrice()));
        int result1 = priceDefineMapper.insertToTemp(priceDefine);


        /*再更新主表*/
        oldPriceDefine.setApproveRes("未审核-内容更新");
        oldPriceDefine.setApproveSerial(flowId);//设置当前流水号
        int result2 = priceDefineMapper.update(oldPriceDefine);
        if (result1 == 1 && result2 >= 0) {
            return 1;
        }
        return -1;
    }

    @Override
    public int delete(String primaryId) {

        //取出主表内容暂存

        PriceDefine priceDefine = priceDefineMapper.selectByPrimaryKey(primaryId);
        if(priceDefine==null){
            throw  new BusinessException("操作失败！该热价未审核完成前不能进行其他操作！");
        }
        String tempprimaryId = priceDefine.getPrimaryId();

        //先向temp表里插
        priceDefine.setPrimaryId(IDWorker.uuid());
        priceDefine.setApproveRes("未审核-热价删除");
        priceDefine.setCreateDate(new Date());
        priceDefine.setCreateUser(userLoginMapper.selectByPrimaryKey(UserUtils.getUserId()).getUsername());

        TypeMst typeMst = typeMstMapper.getByTypeKbnAndTypeName(Constant.GLOBAL_BUSINESS_TYPE, Constant.GBT_PRICE_DEFINE);
        if (typeMst == null) {
            log.error("业务类型获取失败！");
            throw new BusinessException("业务类型获取失败！");
        }
        String flowId = FlowIdTool.GetFlowId(priceDefine.getCompanyId(), typeMst.getTypeId());//审核流水号
        priceDefine.setApproveSerial(flowId);
        priceDefine.setUpdateDate(new Date());
        priceDefine.setUpdateUser(userLoginMapper.selectByPrimaryKey(UserUtils.getUserId()).getUsername());
        priceDefine.setApproveType("删除");
        //加密
        int result1 = priceDefineMapper.insertToTemp(priceDefine);



        /*再更新主表*/
        priceDefine.setPrimaryId(tempprimaryId);
        int result2 = priceDefineMapper.update(priceDefine);
        if (result1 == 1 && result2 >= 0) {
            return 1;
        }
        return -1;
    }


    @Override
    public List<PriceDefine> findBycompanyId(String companyId) {
        //一、正在审核中的：
        // 1新增类：只从temp中取
        List<PriceDefine> priceDefineListNew = priceDefineMapper.findBycompanyIdFromTemp(companyId, "未审核-内容新增");

        // 2更新类：从主表和tmp表中取
        List<PriceDefine> priceDefineListUpdate = priceDefineMapper.findBycompanyIdFromTemp(companyId, "未审核-内容更新");
        priceDefineListUpdate.stream().map(priceDefineTmp -> {
            PriceDefine priceDefine = priceDefineMapper.findOriginData(priceDefineTmp.getApproveSerial());
            priceDefineTmp.setOriginData(priceDefine);
            return priceDefineTmp;
        }).collect(Collectors.toList());

        //3删除类：只从tmp表中取（因为不需要对比数据）
        List<PriceDefine> priceDefineListDel = priceDefineMapper.findBycompanyIdFromTemp(companyId, "未审核-热价删除");


        //二、已审核完成或者已驳回：从主表取
        List<PriceDefine> priceDefineListAlreadyDone = priceDefineMapper.findBycompanyId(companyId);


        List<PriceDefine> allPriceDefineList = new ArrayList<PriceDefine>();
        allPriceDefineList.addAll(priceDefineListNew);
        allPriceDefineList.addAll(priceDefineListUpdate);
        allPriceDefineList.addAll(priceDefineListDel);
        allPriceDefineList.addAll(priceDefineListAlreadyDone);
        //统一解码,顺便记录待审核
        return allPriceDefineList.stream().map(priceDefine -> {
            priceDefine.setPrePrice(SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getPrePrice()));
            priceDefine.setAreaPrice(SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getAreaPrice()));
            priceDefine.setHeatPrice(SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getHeatPrice()));
            priceDefine.setExchangerPrice(SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getExchangerPrice()));
            if (priceDefine.getOriginData() != null) {
                PriceDefine originData = priceDefine.getOriginData();
                originData.setPrePrice(SecureUtils.AESDncode(Constant.AES_RULES, originData.getPrePrice()));
                originData.setAreaPrice(SecureUtils.AESDncode(Constant.AES_RULES, originData.getAreaPrice()));
                originData.setHeatPrice(SecureUtils.AESDncode(Constant.AES_RULES, originData.getHeatPrice()));
                originData.setExchangerPrice(SecureUtils.AESDncode(Constant.AES_RULES, originData.getExchangerPrice()));
                priceDefine.setOriginData(originData);
            }
            return priceDefine;
        }).collect(Collectors.toList());


    }


    //获取某一条热价当前的状态 ，用来更新用
    @Override
    public PriceDefine selectByPrimaryKey(String primaryId) {
        PriceDefine priceDefine = priceDefineMapper.selectByPrimaryKey(primaryId);
//        System.out.println("####################################");
//        System.out.println(priceDefine);
//        System.out.println("####################################");
        if(priceDefine != null){
            String prePrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getPrePrice());//解密
            String areaPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getAreaPrice());//解密
            String heatPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getHeatPrice());//解密
            String exchangerPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getExchangerPrice());//解密

            if(prePrice != null){
                priceDefine.setPrePrice(prePrice);
            }
            if(areaPrice != null){
                priceDefine.setAreaPrice(areaPrice);
            }
            if(heatPrice != null){
                priceDefine.setHeatPrice(heatPrice);
            }
            if(exchangerPrice != null){
                priceDefine.setExchangerPrice(exchangerPrice);
            }
        }
        return priceDefine;

    }



//    public PriceDefineUseInHouse selectByAnnualAndConsumerId(String annual, String consumerId) {
//
//        PriceDefineUseInHouse priceDefine = priceDefineMapper.selectByAnnualAndConsumerId(annual, consumerId);
//        String prePrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getPrePrice());//解密
//        String areaPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getAreaPrice());//解密
//        String heatPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getHeatPrice());//解密
//        String exchangerPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getExchangerPrice());//解密
//        priceDefine.setPrePrice(prePrice);
//        priceDefine.setAreaPrice(areaPrice);
//        priceDefine.setHeatPrice(heatPrice);
//        priceDefine.setExchangerPrice(exchangerPrice);
//
//        return priceDefine;
//    }

//    @Override
//    public PriceDefineUseInHouse selectByAnnualAndCompanyId(String annual, String companyId) {
//
//        PriceDefineUseInHouse priceDefine = priceDefineMapper.selectByAnnualAndCompanyId(annual, companyId);
//        String prePrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getPrePrice());//解密
//        String areaPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getAreaPrice());//解密
//        String heatPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getHeatPrice());//解密
//        String exchangerPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getExchangerPrice());//解密
//        priceDefine.setPrePrice(prePrice);
//        priceDefine.setAreaPrice(areaPrice);
//        priceDefine.setHeatPrice(heatPrice);
//        priceDefine.setExchangerPrice(exchangerPrice);
//
//        return priceDefine;
//    }

    @Override
    public List<PriceDefine> selectByAnnualAndCompanyIds(String annual, String companyIds) {

        String[] split = companyIds.split(",");
        List<String> companyIdlst = new ArrayList<>();
        for (String s : split) {
            companyIdlst.add(s);
        }

        List<PriceDefine> priceDefines = priceDefineMapper.selectByAnnualAndCompanyIds(annual, companyIdlst);
        for (PriceDefine priceDefine : priceDefines) {
            if(priceDefine != null){
                String prePrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getPrePrice());//解密
                String areaPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getAreaPrice());//解密
                String heatPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getHeatPrice());//解密
                String exchangerPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getExchangerPrice());//解密
                priceDefine.setPrePrice(prePrice);
                priceDefine.setAreaPrice(areaPrice);
                priceDefine.setHeatPrice(heatPrice);
                priceDefine.setExchangerPrice(exchangerPrice);
            }
        }

        return priceDefines;
    }


    @Override
    public List<PriceDefine> selectByAnnualAndCommunityIds(String annual, String commuityIds) {

        String[] split = commuityIds.split(",");
        List<String> commuityIdlst = new ArrayList<>();
        for (String s : split) {
            commuityIdlst.add(s);
        }

        List<PriceDefine> priceDefines = priceDefineMapper.selectByAnnualAndCommunityIds(annual, commuityIdlst);
        for (PriceDefine priceDefine : priceDefines) {
            if(priceDefine != null){
                String prePrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getPrePrice());//解密
                String areaPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getAreaPrice());//解密
                String heatPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getHeatPrice());//解密
                String exchangerPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getExchangerPrice());//解密
                priceDefine.setPrePrice(prePrice);
                priceDefine.setAreaPrice(areaPrice);
                priceDefine.setHeatPrice(heatPrice);
                priceDefine.setExchangerPrice(exchangerPrice);
            }
        }

        return priceDefines;
    }


    @Override
    public List<PriceDefine> selectByAnnualAndStationIds(String annual, String stationIds) {

        String[] split = stationIds.split(",");
        List<String> stationIdlst = new ArrayList<>();
        for (String s : split) {
            stationIdlst.add(s);
        }

        List<PriceDefine> priceDefines = priceDefineMapper.selectByAnnualAndStationIds(annual, stationIdlst);
        for (PriceDefine priceDefine : priceDefines) {
            if(priceDefine != null){
                String prePrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getPrePrice());//解密
                String areaPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getAreaPrice());//解密
                String heatPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getHeatPrice());//解密
                String exchangerPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getExchangerPrice());//解密
                priceDefine.setPrePrice(prePrice);
                priceDefine.setAreaPrice(areaPrice);
                priceDefine.setHeatPrice(heatPrice);
                priceDefine.setExchangerPrice(exchangerPrice);
            }
        }

        return priceDefines;
    }



    @Override
    public List<PriceDefine> selectByAnnualAndConsumerIds(String annual, String consumerIds) {

        String[] split = consumerIds.split(",");
        List<String> consumerIdlst = new ArrayList<>();
        for (String s : split) {
            consumerIdlst.add(s);
        }

        List<PriceDefine> priceDefines = priceDefineMapper.selectByAnnualAndConsumerIds(annual, consumerIdlst);
        for (PriceDefine priceDefine : priceDefines) {
            if(priceDefine != null){
                String prePrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getPrePrice());//解密
                String areaPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getAreaPrice());//解密
                String heatPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getHeatPrice());//解密
                String exchangerPrice = SecureUtils.AESDncode(Constant.AES_RULES, priceDefine.getExchangerPrice());//解密
                priceDefine.setPrePrice(prePrice);
                priceDefine.setAreaPrice(areaPrice);
                priceDefine.setHeatPrice(heatPrice);
                priceDefine.setExchangerPrice(exchangerPrice);
            }
        }

        return priceDefines;
    }

    /**
     * .* @param primaryId
     *
     * @param primaryId
     * @param approveRes
     */
    @Override
    public void approvalSinglePriceDefine(String primaryId, String approveRes) {


        PriceDefine oldPriceDefineOrigin = priceDefineMapper.selectByPrimaryKey(primaryId);
        if(oldPriceDefineOrigin!=null){
            throw  new BusinessException("操作失败！存在热价已经审核，不能再次审核！");
        }

        String currentUserName = userLoginMapper.selectByPrimaryKey(UserUtils.getUserId()).getUsername();
        Date date = new Date();

        //审核逻辑
        // 新增类：通过    驳回：
        // 更新：通过    驳回：
        // 删除：通过    驳回：
        //若为未审核无需进行任何操作  若为已通过和已驳回则进行如下操作
        //priceDefine.setApproveName(userLoginMapper.selectByPrimaryKey(UserUtils.getUserId()).getUsername());
        if (approveRes.equals("已通过-内容新增")) {
            PriceDefine priceDefine = priceDefineMapper.selectByPrimaryKeyFromTemp(primaryId);
            PriceDefine  existDefine=  priceDefineMapper.findBypriceNameAndcompanyId(priceDefine.getPriceName(),priceDefine.getCompanyId());
            if(existDefine!=null){
                throw  new BusinessException("操作失败！该公司已有名称相同的热价！");
            }
            priceDefine.setApproveRes(approveRes);
            //更新tmp表
            priceDefine.setApproveName(currentUserName);
            priceDefine.setApproveDate(date);
            priceDefineMapper.updateTemp(priceDefine);

            //插入主表
            priceDefine.setPrimaryId(IDWorker.uuid());
            priceDefine.setApproveSerial(null);//已完成审核  当前流水号清空

            priceDefineMapper.insert(priceDefine);

            priceDefineMapper.changeOtherToNotCurrent(priceDefine.getAnnual(),priceDefine.getCompanyId());
        } else if (approveRes.equals("已通过-内容更新")) {
            PriceDefine priceDefine = priceDefineMapper.selectByPrimaryKeyFromTemp(primaryId);
            //更新tmp表
            priceDefine.setApproveName(currentUserName);
            priceDefine.setApproveRes(approveRes);
            priceDefine.setApproveDate(date);
            priceDefineMapper.updateTemp(priceDefine);
            PriceDefine oldPriceDefine = priceDefineMapper.findOriginData(priceDefine.getApproveSerial());

            //更新主表
            priceDefine.setPrimaryId(oldPriceDefine.getPrimaryId());
            priceDefine.setCreateDate(oldPriceDefine.getCreateDate());
            priceDefine.setApproveSerial(null);//已完成审核  当前流水号清空

            PriceDefine  existDefine=  priceDefineMapper.findBypriceNameAndcompanyId(priceDefine.getPriceName(),priceDefine.getCompanyId());
            if(existDefine!=null && !existDefine.getPrimaryId().equals(priceDefine.getPrimaryId())){
                throw  new BusinessException("操作失败！该公司已有名称相同的热价！");
            }

            priceDefineMapper.update(priceDefine);
            //发送事件，热价更新，使用此热价的用户按需要更新采暖季价格
            PriceDefineChangeEvent  priceDefineChangeEvent=new PriceDefineChangeEvent(priceDefine,"update");
            eventManager.publish(priceDefineChangeEvent);

        } else if (approveRes.equals("已通过-热价删除") ||
                approveRes.equals("已驳回-内容新增") ||
                approveRes.equals("已驳回-内容更新") ||
                approveRes.equals("已驳回-热价删除")) {

            PriceDefine priceDefine = priceDefineMapper.selectByPrimaryKeyFromTemp(primaryId);
            //更新tmp表
            priceDefine.setApproveName(currentUserName);
            priceDefine.setApproveRes(approveRes);
            priceDefine.setApproveDate(date);
            priceDefineMapper.updateTemp(priceDefine);
            PriceDefine oldPriceDefine = priceDefineMapper.findOriginData(priceDefine.getApproveSerial());
            if (oldPriceDefine != null) {
                //更新主表
                oldPriceDefine.setApproveRes(approveRes);
                oldPriceDefine.setApproveSerial(null);//已完成审核  当前流水号清空
                priceDefineMapper.update(oldPriceDefine);
            }else{//新增驳回
                priceDefine.setPrimaryId(IDWorker.uuid());
                priceDefine.setApproveSerial(null);//已完成审核  当前流水号清空
                priceDefineMapper.insert(priceDefine);
            }
        }
    }

    @Override
    public String findCurrentHeatAnnual(String companyId) {
        return priceDefineMapper.findCurrentHeatAnnual(companyId);
    }

    @Override
    public int updateByPrimaryKey(PriceDefine record) {
        return priceDefineMapper.updateByPrimaryKey(record);
    }
    ;

    public
    int updateTmpValueByPrimaryKey(BigDecimal prePriceTmp,BigDecimal areaPriceTmp,BigDecimal heatPriceTmp,BigDecimal exchangerPriceTmp,String  primaryId) {
        return priceDefineMapper.updateTmpValueByPrimaryKey( prePriceTmp, areaPriceTmp, heatPriceTmp, exchangerPriceTmp,  primaryId);
    }

}

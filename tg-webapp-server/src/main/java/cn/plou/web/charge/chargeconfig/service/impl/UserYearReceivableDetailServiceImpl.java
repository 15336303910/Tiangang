package cn.plou.web.charge.chargeconfig.service.impl;

import cn.plou.web.charge.chargeconfig.dao.UserYearReceivableDetailMapper;
import cn.plou.web.charge.chargeconfig.entity.UserYearReceivableDetail;
import cn.plou.web.charge.chargeconfig.service.UserYearReceivableDetailService;
import cn.plou.web.charge.chargeconfig.vo.UserYearReceivableDetailInfo;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.system.baseMessage.commuity.dao.CommuityMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 2018/8/15 15:38
 */


@Service
@Transactional(rollbackFor = Exception.class)
public class UserYearReceivableDetailServiceImpl implements UserYearReceivableDetailService {


    @Resource
    private UserYearReceivableDetailMapper userYearReceivableDetailMapper;

    @Resource
    private CommuityMapper commuityMapper;
    @Override
    public int deleteByPrimaryKey(String primaryId) {
        return userYearReceivableDetailMapper.deleteByPrimaryKey(primaryId);
    }

    @Override
    public int insert(UserYearReceivableDetail record) {
        return userYearReceivableDetailMapper.insert(record);
    }

    @Override
    public int insertSelective(UserYearReceivableDetail record) {
        return userYearReceivableDetailMapper.insertSelective(record);
    }


    @Override
    public void batchUpdate(List<UserYearReceivableDetail> userYearReceivableDetails) {
        userYearReceivableDetailMapper.batchUpdate(userYearReceivableDetails);
    }

    @Override
    public void insertByBatch(List<UserYearReceivableDetail> userYearReceivableDetails) {
        userYearReceivableDetailMapper.insertByBatch(userYearReceivableDetails);
    }

    @Override
    public UserYearReceivableDetail selectByPrimaryKey(String primaryId) {
        return userYearReceivableDetailMapper.selectByPrimaryKey(primaryId);
    }

    @Override
    public int updateByPrimaryKeySelective(UserYearReceivableDetail record) {
        return userYearReceivableDetailMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserYearReceivableDetail record) {
        return userYearReceivableDetailMapper.updateByPrimaryKey(record);
    }


    @Override
    public List<UserYearReceivableDetail> findAllUserYearReceivableDetails(){
        return userYearReceivableDetailMapper.findAllUserYearReceivableDetails();
    }

    @Override
    public UserYearReceivableDetail findLastUserYearReceivableDetails(){
        return userYearReceivableDetailMapper.findLastUserYearReceivableDetails();
    }

    @Override
    public List<UserYearReceivableDetail> findAllUserYearReceivableDetailsByAnnual(String annual){
        return userYearReceivableDetailMapper.findAllUserYearReceivableDetailsByAnnual(annual);
    }

    @Override
    public UserYearReceivableDetail findByUserAndAnnual(String consumerId, String annual) {
        return userYearReceivableDetailMapper.findByUserAndAnnual(consumerId, annual);
    }


    @Override
    public BigDecimal getTotalByConsumerId(String consumerId, String annual) {
        return userYearReceivableDetailMapper.getTotalByConsumerId(consumerId, annual);
    }

    @Override
    public BigDecimal getOtherTotalByConsumerId(String consumerId, String annual) {
        return userYearReceivableDetailMapper.getOtherTotalByConsumerId(consumerId, annual);
    }


    @Override
    public PageInfo<UserYearReceivableDetailInfo> getUserYearReceivableDetailsByConsumerId(String consumerId, String communityId, String annual, String order, String sortby, Integer page, Integer pageSize){
        List<UserYearReceivableDetailInfo> list = new ArrayList<>();
        if (consumerId.length() == Constant.STATION_ID_LENGTH) {//说明是站
            List<String> commuityIds = commuityMapper.selectCommuityIdsByStationId(consumerId);
            if (commuityIds.size() > 0) {
                PageHelper.startPage(page, pageSize);
                list = userYearReceivableDetailMapper.getUserYearReceivableDetailsByConsumerIdByCommuityIds(commuityIds, communityId,annual, order, sortby, page, pageSize);
            }
        } else {
            PageHelper.startPage(page, pageSize);
            list = userYearReceivableDetailMapper.getUserYearReceivableDetailsByConsumerId(consumerId, communityId,annual, order, sortby, page, pageSize);
        }
        return new PageInfo<>(list);

    }

    @Override
    public PageInfo<UserYearReceivableDetail> getUserYearReceivableDetailsByConsumerIds(String consumerIds,String communityIds,String annual,String order, String sortby, Integer page, Integer pageSize){
        PageHelper.startPage(page,pageSize);
        List<UserYearReceivableDetail> userYearReceivableDetailsByConsumerId = userYearReceivableDetailMapper.getUserYearReceivableDetailsByConsumerIds(consumerIds,consumerIds,annual, order,sortby,page,pageSize);
        PageInfo<UserYearReceivableDetail> pageInfo=new PageInfo<>(userYearReceivableDetailsByConsumerId);
        return pageInfo;

    }

    ;
    @Override
    public int deleteByConsumerIds(String consumerIds, String commuityIds, String annual){

        List<String> consumerIdlst = new ArrayList<>();
        List<String> commuityIdlst = new ArrayList<>();
        if(consumerIds == null ){
            consumerIdlst =  null;
        }else{
            String[] split = consumerIds.split(",");
            for (String s : split) {
                consumerIdlst.add(s);
            }
        }

        if(commuityIds == null ){
            commuityIdlst =  null;
        }else{
            String[] split = commuityIds.split(",");
            for (String s : split) {
                commuityIdlst.add(s);
            }
        }
        return userYearReceivableDetailMapper.deleteByConsumerIds(consumerIdlst,commuityIdlst, annual);
    }


    @Override
    public int deleteByConsumerId(String consumerId, String communityId, String annual) {
        return userYearReceivableDetailMapper.deleteByConsumerId(consumerId,communityId, annual);
    }


    @Override
    public int deleteByCompanyId(String companyId, String annual) {
        return userYearReceivableDetailMapper.deleteByCompanyId(companyId, annual);
    }


    @Override
    public int deleteByCommuityIds(List<String> commuityIds, String annual) {
        return userYearReceivableDetailMapper.deleteByCommuityIds(commuityIds, annual);
    }


    @Override
    public int deleteByStationIds(List<String> stationIds, String annual) {
        return userYearReceivableDetailMapper.deleteByStationIds(stationIds, annual);
    }


    @Override
    public int deleteByConsumerIds(List<String> consumerIds, String annual) {
        return userYearReceivableDetailMapper.deleteByConsumerIds2(consumerIds, annual);
    }

    @Override
    public int deleteGenerated(String consumerId, String annual) {
        return userYearReceivableDetailMapper.deleteGenerated(consumerId, annual);
    }

    @Override
    public int insertChargingItem1ForGenerated(String companyId, String annual, String createUser) {
        return userYearReceivableDetailMapper.insertChargingItem1ForGenerated(companyId, annual, createUser);
    }


    @Override
    public int insertChargingItem2ForGenerated(String companyId, String annual, String createUser) {
        return userYearReceivableDetailMapper.insertChargingItem2ForGenerated(companyId, annual, createUser);
    }
    @Override
    public int insertChargingItem3ForGenerated(String companyId, String annual, String createUser) {
        return userYearReceivableDetailMapper.insertChargingItem3ForGenerated(companyId, annual, createUser);
    }
    @Override
    public int insertChargingItem7ForGenerated(String companyId, String annual, String createUser) {
        return userYearReceivableDetailMapper.insertChargingItem7ForGenerated(companyId, annual, createUser);
    }


    @Override
    public int insertChargingItem1ForGeneratedForOneStationAllCommunity(String stationId, String annual, String createUser) {
        return userYearReceivableDetailMapper.insertChargingItem1ForGeneratedForOneStationAllCommunity(stationId, annual, createUser);
    }

    @Override
    public int insertChargingItem2ForGeneratedForOneStationAllCommunity(String stationId, String annual, String createUser) {
        return userYearReceivableDetailMapper.insertChargingItem2ForGeneratedForOneStationAllCommunity(stationId, annual, createUser);
    }
    @Override
    public int insertChargingItem3ForGeneratedForOneStationAllCommunity(String stationId, String annual, String createUser) {
        return userYearReceivableDetailMapper.insertChargingItem3ForGeneratedForOneStationAllCommunity(stationId, annual, createUser);
    }
    @Override
    public int insertChargingItem7ForGeneratedForOneStationAllCommunity(String stationId, String annual, String createUser) {
        return userYearReceivableDetailMapper.insertChargingItem7ForGeneratedForOneStationAllCommunity(stationId, annual, createUser);
    }


    @Override
    public int insertChargingItem1ForGeneratedForSelectedCommunity(List<String> commuityIds, String annual, String createUser) {
        return userYearReceivableDetailMapper.insertChargingItem1ForGeneratedForSelectedCommunity(commuityIds, annual, createUser);
    }

    @Override
    public int insertChargingItem2ForGeneratedForSelectedCommunity(List<String> commuityIds, String annual, String createUser) {
        return userYearReceivableDetailMapper.insertChargingItem2ForGeneratedForSelectedCommunity(commuityIds, annual, createUser);
    }

    @Override
    public int insertChargingItem3ForGeneratedForSelectedCommunity(List<String> commuityIds, String annual, String createUser) {
        return userYearReceivableDetailMapper.insertChargingItem3ForGeneratedForSelectedCommunity(commuityIds, annual, createUser);
    }

    @Override
    public int insertChargingItem7ForGeneratedForSelectedCommunity(List<String> commuityIds, String annual, String createUser) {
        return userYearReceivableDetailMapper.insertChargingItem7ForGeneratedForSelectedCommunity(commuityIds, annual, createUser);
    }


    @Override
    public int insertChargingItem1ForGeneratedForSelectedConsumer(List<String> consumerIds, String annual, String createUser) {
        return userYearReceivableDetailMapper.insertChargingItem1ForGeneratedForSelectedConsumer(consumerIds, annual, createUser);
    }

    @Override
    public int insertChargingItem2ForGeneratedForSelectedConsumer(List<String> consumerIds, String annual, String createUser) {
        return userYearReceivableDetailMapper.insertChargingItem2ForGeneratedForSelectedConsumer(consumerIds, annual, createUser);
    }


    @Override
    public int insertChargingItem3ForGeneratedForSelectedConsumer(List<String> consumerIds, String annual, String createUser) {
        return userYearReceivableDetailMapper.insertChargingItem3ForGeneratedForSelectedConsumer(consumerIds, annual, createUser);
    }

    @Override
    public int insertChargingItem7ForGeneratedForSelectedConsumer(List<String> consumerIds, String annual, String createUser) {
        return userYearReceivableDetailMapper.insertChargingItem7ForGeneratedForSelectedConsumer(consumerIds, annual, createUser);
    }



}

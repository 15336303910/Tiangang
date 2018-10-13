package cn.plou.web.charge.chargeconfig.service;

import cn.plou.web.charge.chargeconfig.entity.UserYearReceivableDetail;
import cn.plou.web.charge.chargeconfig.vo.UserYearReceivableDetailInfo;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface UserYearReceivableDetailService {
    int deleteByPrimaryKey(String primaryId);

    int insert(UserYearReceivableDetail record);

    int insertSelective(UserYearReceivableDetail record);

    @Transactional(rollbackFor = Exception.class)
    void batchUpdate(List<UserYearReceivableDetail> userYearReceivableDetails);

    void insertByBatch(List<UserYearReceivableDetail> userYearHeats);

    UserYearReceivableDetail selectByPrimaryKey(String primaryId);

    int updateByPrimaryKeySelective(UserYearReceivableDetail record);

    int updateByPrimaryKey(UserYearReceivableDetail record);

    List<UserYearReceivableDetail> findAllUserYearReceivableDetails();

    UserYearReceivableDetail findLastUserYearReceivableDetails();

    List<UserYearReceivableDetail> findAllUserYearReceivableDetailsByAnnual(String annual);

    UserYearReceivableDetail findByUserAndAnnual(String consumerId, String annual);

    BigDecimal getTotalByConsumerId(String consumerId,String annual);

    BigDecimal getOtherTotalByConsumerId(String consumerId,String annual);

    PageInfo<UserYearReceivableDetailInfo> getUserYearReceivableDetailsByConsumerId(String consumerId, String communityId, String annual, String order, String sortby, Integer page, Integer pageSize);

    PageInfo<UserYearReceivableDetail> getUserYearReceivableDetailsByConsumerIds(String consumerIds,String communityIds,String annual,String order, String sortby, Integer page, Integer pageSize);

    int deleteByConsumerIds(String consumerIds, String commuityIds, String annual);

    int deleteByConsumerId(String consumerId, String communityId, String annual);

    int deleteByCompanyId(String companyId, String annual);

    int deleteByCommuityIds(List<String> commuityIds, String annual);
    int deleteByStationIds(List<String> stationIds, String annual);
    int deleteByConsumerIds(List<String> consumerIds, String annual);


    int deleteGenerated(String consumerId, String annual);

    int insertChargingItem1ForGenerated(String companyId, String annual, String createUser) ;

    int insertChargingItem2ForGenerated(String companyId, String annual, String createUser) ;

    int insertChargingItem3ForGenerated(String companyId, String annual, String createUser) ;

    int insertChargingItem7ForGenerated(String companyId, String annual, String createUser) ;




    int insertChargingItem1ForGeneratedForOneStationAllCommunity(String stationId, String annual, String createUser) ;
    int insertChargingItem2ForGeneratedForOneStationAllCommunity(String stationId, String annual, String createUser) ;
    int insertChargingItem3ForGeneratedForOneStationAllCommunity(String stationId, String annual, String createUser) ;
    int insertChargingItem7ForGeneratedForOneStationAllCommunity(String stationId, String annual, String createUser) ;


    int insertChargingItem1ForGeneratedForSelectedCommunity(List<String> commuityIds, String annual, String createUser) ;
    int insertChargingItem2ForGeneratedForSelectedCommunity(List<String> commuityIds, String annual, String createUser) ;

    int insertChargingItem3ForGeneratedForSelectedCommunity(List<String> commuityIds, String annual, String createUser) ;

    int insertChargingItem7ForGeneratedForSelectedCommunity(List<String> commuityIds, String annual, String createUser) ;



    int insertChargingItem1ForGeneratedForSelectedConsumer(List<String> consumerIds, String annual, String createUser) ;
    int insertChargingItem2ForGeneratedForSelectedConsumer(List<String> consumerIds, String annual, String createUser) ;
    int insertChargingItem3ForGeneratedForSelectedConsumer(List<String> consumerIds, String annual, String createUser) ;
    int insertChargingItem7ForGeneratedForSelectedConsumer(List<String> consumerIds, String annual, String createUser) ;






}
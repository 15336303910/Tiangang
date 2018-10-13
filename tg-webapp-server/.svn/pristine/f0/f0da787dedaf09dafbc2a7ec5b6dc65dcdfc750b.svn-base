package cn.plou.web.charge.chargeconfig.dao;

import cn.plou.web.charge.chargeconfig.entity.UserYearReceivableDetail;
import cn.plou.web.charge.chargeconfig.vo.UserYearReceivableDetailInfo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UserYearReceivableDetailMapper {
    int deleteByPrimaryKey(String primaryId);

    int insert(UserYearReceivableDetail record);

    int insertSelective(UserYearReceivableDetail record);

    UserYearReceivableDetail selectByPrimaryKey(String primaryId);

    int updateByPrimaryKeySelective(UserYearReceivableDetail record);

    int updateByPrimaryKey(UserYearReceivableDetail record);

    void batchUpdate(List<UserYearReceivableDetail> userYearReceivableDetails);

    //下面这些可能能用到
    void insertByBatch(List<UserYearReceivableDetail> userYearReceivableDetails);

    List<UserYearReceivableDetail> findAllUserYearReceivableDetails();

    UserYearReceivableDetail findLastUserYearReceivableDetails();

    List<UserYearReceivableDetail> findAllUserYearReceivableDetailsByAnnual(String annual);

    UserYearReceivableDetail findByUserAndAnnual(String consumerId, String annual);

    BigDecimal getTotalByConsumerId(@Param("consumerId")String consumerId,@Param("annual")String annual);



    BigDecimal getOtherTotalByConsumerId(@Param("consumerId")String consumerId,@Param("annual")String annual);





    List<UserYearReceivableDetailInfo> getUserYearReceivableDetailsByConsumerId(@Param("consumerId")String consumerId, @Param("communityId")String communityId, @Param("annual")String annual, @Param("order")String order, @Param("sortby")String sortby, @Param("page")Integer page, @Param("pageSize")Integer pageSize);

    List<UserYearReceivableDetail> getUserYearReceivableDetailsByConsumerIds(@Param("consumerIds")String consumerIds, @Param("communityIds")String communityIds, @Param("annual")String annual,@Param("order")String order, @Param("sortby")String sortby, @Param("page")Integer page, @Param("pageSize")Integer pageSize);

    int deleteByConsumerIds(@Param("consumerIds")List<String> consumerIds, @Param("commuityIds")List<String> commuityIds, @Param("annual")String annual);

    int deleteByConsumerId(@Param("consumerId")String consumerId, @Param("communityId")String communityId, @Param("annual")String annual);

    int deleteByCompanyId(@Param("companyId")String companyId, @Param("annual")String annual);

    int deleteByCommuityIds(@Param("commuityIds")List<String> commuityIds, @Param("annual")String annual);

    int deleteByStationIds(@Param("stationIds")List<String> stationIds, @Param("annual")String annual);

    int deleteByConsumerIds2(@Param("consumerIds")List<String> consumerIds, @Param("annual")String annual);



    int deleteGenerated(@Param("consumerId")String consumerId, @Param("annual")String annual);

    List<UserYearReceivableDetail> findByConsumerIdAndCompanyIdAndAnnual(@Param("consumerId")String consumerId,@Param("companyId")String companyId,@Param("annual")String annual);

    int insertChargingItem1ForGenerated(@Param("companyId")String companyId, @Param("annual")String annual, @Param("createUser")String createUser);
    int insertChargingItem2ForGenerated(@Param("companyId")String companyId, @Param("annual")String annual, @Param("createUser")String createUser);
    int insertChargingItem3ForGenerated(@Param("companyId")String companyId, @Param("annual")String annual, @Param("createUser")String createUser);
    int insertChargingItem7ForGenerated(@Param("companyId")String companyId, @Param("annual")String annual, @Param("createUser")String createUser);



    int insertChargingItem1ForGeneratedForOneStationAllCommunity(@Param("stationId")String stationId, @Param("annual")String annual, @Param("createUser")String createUser) ;
    int insertChargingItem2ForGeneratedForOneStationAllCommunity(@Param("stationId")String stationId, @Param("annual")String annual, @Param("createUser")String createUser) ;
    int insertChargingItem3ForGeneratedForOneStationAllCommunity(@Param("stationId")String stationId, @Param("annual")String annual, @Param("createUser")String createUser) ;
    int insertChargingItem7ForGeneratedForOneStationAllCommunity(@Param("stationId")String stationId, @Param("annual")String annual, @Param("createUser")String createUser) ;


    int insertChargingItem1ForGeneratedForSelectedCommunity(@Param("commuityIds")List<String> commuityIds, @Param("annual")String annual, @Param("createUser")String createUser) ;
    int insertChargingItem2ForGeneratedForSelectedCommunity(@Param("commuityIds")List<String> commuityIds, @Param("annual")String annual, @Param("createUser")String createUser) ;

    int insertChargingItem3ForGeneratedForSelectedCommunity(@Param("commuityIds")List<String> commuityIds, @Param("annual")String annual, @Param("createUser")String createUser) ;

    int insertChargingItem7ForGeneratedForSelectedCommunity(@Param("commuityIds")List<String> commuityIds, @Param("annual")String annual, @Param("createUser")String createUser) ;



    int insertChargingItem1ForGeneratedForSelectedConsumer(@Param("consumerIds")List<String> consumerIds, @Param("annual")String annual, @Param("createUser")String createUser) ;
    int insertChargingItem2ForGeneratedForSelectedConsumer(@Param("consumerIds")List<String> consumerIds, @Param("annual")String annual, @Param("createUser")String createUser) ;
    int insertChargingItem3ForGeneratedForSelectedConsumer(@Param("consumerIds")List<String> consumerIds, @Param("annual")String annual, @Param("createUser")String createUser) ;
    int insertChargingItem7ForGeneratedForSelectedConsumer(@Param("consumerIds")List<String> consumerIds, @Param("annual")String annual, @Param("createUser")String createUser) ;




}
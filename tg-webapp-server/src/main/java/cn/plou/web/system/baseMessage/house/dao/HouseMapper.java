package cn.plou.web.system.baseMessage.house.dao;


import cn.plou.web.charge.chargeconfig.entity.PriceDefine;
import cn.plou.web.charge.heatingmanage.dto.HeatingServeUserSearchDTO;
import cn.plou.web.charge.heatingmanage.vo.HeatingServeUserListVO;
import cn.plou.web.system.baseMessage.house.entity.House;
import cn.plou.web.system.baseMessage.house.entity.HouseKey;
import cn.plou.web.system.baseMessage.house.vo.HouseInfo;
import cn.plou.web.system.baseMessage.house.vo.HouseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface HouseMapper {
    int deleteByPrimaryKey(HouseKey key);

    int deleteHouseBatchByIds(List<String> consumerIds);

    int setDelHouseBatch(@Param("consumerIds") List<String> consumerIds,
                         @Param("updateUser")String updateUser,
                         @Param("updateDate")Date updateDate);

    int insert(House record);

    int insertSelective(HouseInfo record);

    House selectByPrimaryKey(String consumerId);

    List<House> getHouseByIds(@Param("consumerIds")List<String> consumerIds);

    List<House> getHouseByChargeIdOrTelOrIdcardOrCompanyId(@Param("chargeId") String chargeId,@Param("tel") String tel,@Param("idcard") String idcard,@Param("companyId") String companyId);

    List<House> getHouseBySearchTextOrId(@Param("searchText") String searchText,@Param("id") String id, @Param("order")String order, @Param("sortby")String sortby, @Param("page")Integer page, @Param("pageSize")Integer pageSize);

    House selectByName(@Param("roomName")String houseName,@Param("unitId")String unitId);

    int updateByPrimaryKeySelective(House record);

    int updateByPrimaryKey(House record);

    int updateHouseBatch(HouseVo houseVo);

    List<House> selectHouseByUnitId(String unitId);

    Integer selectCountByNetStatus(@Param("companyId")String companyId,@Param("netStatus")String netStatus);

    Integer selectCountNetStatusNull(@Param("companyId")String companyId);




    Integer selectCountByNetStatusForCommunityIds(@Param("communityIds")List<String> communityIds,@Param("netStatus")String netStatus);


    Integer selectCountNetStatusNullForCommunityIds(@Param("communityIds")List<String> communityIds);


    Integer selectCountByNetStatusForStationIds(@Param("stationIds")List<String> stationIds,@Param("netStatus")String netStatus);

    Integer selectCountNetStatusNullForStationIds(@Param("stationIds")List<String> stationIds);



    Integer selectCountByNetStatusForConsumerIds(@Param("consumerIds")List<String> consumerIds,@Param("netStatus")String netStatus);

    Integer selectCountNetStatusNullForConsumerIds(@Param("consumerIds")List<String> consumerIds);



    Integer selectCountHeatingStatusNullForCommunityIds(@Param("communityIds")List<String> communityIds);



    Integer selectCountHeatingStatusNullForStationIds(@Param("stationIds")List<String> stationIds);



    Integer selectCountHeatingStatusNullForConsumerIds(@Param("consumerIds")List<String> consumerIds);




    Integer selectCountHeatingStatusNull(@Param("companyId")String companyId);




    Integer selectCountPriceDefineNullForCommunityIds(@Param("communityIds")List<String> communityIds);



    Integer selectCountPriceDefineNullForStationIds(@Param("stationIds")List<String> stationIds);



    Integer selectCountPriceDefineNullForConsumerIds(@Param("consumerIds")List<String> consumerIds);




    Integer selectCountPriceDefineNull(@Param("companyId")String companyId);




    Integer selectCountChargeTypeNullForCommunityIds(@Param("communityIds")List<String> communityIds);



    Integer selectCountChargeTypeNullForStationIds(@Param("stationIds")List<String> stationIds);



    Integer selectCountChargeTypeNullForConsumerIds(@Param("consumerIds")List<String> consumerIds);




    Integer selectCountChargeTypeNull(@Param("companyId")String companyId);











    Integer selectCountByHeatingStatus(@Param("companyId")String companyId,@Param("heatingStatus")String heatingStatus);


    Integer selectCountNotHeating(@Param("companyId")String companyId);

    Integer selectCountByChargeType(@Param("companyId")String companyId,@Param("chargeType")String chargeType);


    Integer getAllCount(@Param("companyId")String companyId);

    Integer getCountByCommunityIds(@Param("communityIds")List<String> communityIds);

    Integer getCountByStationIds(@Param("stationIds")List<String> stationIds);

    Integer getCountByConsumerIds(@Param("consumerIds")List<String> consumerIds);


    List<PriceDefine> selectPriceDefineByCompanyId(@Param("companyId")String companyId);


    List<PriceDefine> selectPriceDefineByCommunityIds(@Param("communityIds")List<String> communityIds);

    List<PriceDefine>  selectPriceDefineByConsumerIds(@Param("consumerIds")List<String> consumerIds);

    List<PriceDefine> selectPriceDefineByStationIds(@Param("stationIds")List<String> stationIds);











    List<HeatingServeUserListVO> selectByRangeId(HeatingServeUserSearchDTO heatingServeUserSearchDTO);

    Integer selectCountByRangeId(HeatingServeUserSearchDTO heatingServeUserSearchDTO);

    List<String> getAllCompanyId();

    List<HouseInfo> selectAllHouse(@Param("companyIds") List<String> companyIds, @Param("stationIds") List<String> stationIds,
                                   @Param("commuityIds") List<String> commuityIds,
                               @Param("buildingNo") String buildingNo, @Param("unitId") String unitId, @Param("houseVo") HouseVo houseVo,
                               @Param("order") String order, @Param("sortby") String sortby,@Param("page")Integer page,@Param("pageSize")Integer pageSize);

    List<HouseInfo> selectAllHouseByIds(@Param("companyIds") List<String> companyIds, @Param("stationIds") List<String> stationIds,
                                   @Param("consumerIds") List<String> consumerIds,
                                   @Param("buildingNo") String buildingNo, @Param("unitId") String unitId, @Param("houseVo") HouseVo houseVo,
                                   @Param("order") String order, @Param("sortby") String sortby,@Param("page")Integer page,@Param("pageSize")Integer pageSize);



    List<House> selectHouseByUnitIds(@Param("ids") String ids);

    HouseInfo selectHouseInfoById(@Param("consumerId") String consumerId);

    List<House> findByChargeType(@Param("chargeType")String chargeType);


    List<House> findByConsumerIdlike(@Param("likeConsumerId")String likeConsumerId);

    Integer findCountByConsumerIdlike(@Param("likeConsumerId")String likeConsumerId);

    int updateHouseBatchAll(HouseVo houseVo);

    int updateHouseBatchAllStation(HouseVo houseVo);

		void batchInsertOrUpdate(List<HouseInfo> houseInfoList);

		String selectMaxHouseId(String unitId);


    List<House> findHouseListByCommuitys(@Param("searchText")String searchText, @Param("list")List<String> commuityIds, @Param("order")String order, @Param("sortby")String sortby);


    List<HeatingServeUserListVO> selectByRangeIdOfStation(@Param("dto") HeatingServeUserSearchDTO heatingServeUserSearchDTO, @Param("list") List<String> ids);

    Integer selectCountByRangeIdOfStation(@Param("dto") HeatingServeUserSearchDTO heatingServeUserSearchDTO, @Param("list") List<String> ids);

		void modifyHouseName(@Param("changedAdress")String changedAdress, @Param("nkey")String nkey, @Param("consumerId")String consumerId);

		void modifyHouseNameByAddress(@Param("orgAddress")String orgAddress, @Param("changedAddress")String changedAddress, 
				@Param("nkey")String nkey, @Param("consumerId")String consumerId);
}
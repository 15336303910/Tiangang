package cn.plou.web.system.baseMessage.house.service;

/*import cn.plou.web.charge.heatingmanage.dto.HeatingServeUserSearchDTO;
import cn.plou.web.charge.heatingmanage.vo.HeatingServeUserListVO;*/

import cn.plou.web.charge.chargeconfig.entity.PriceDefine;
import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import cn.plou.web.system.baseMessage.house.entity.House;
import cn.plou.web.system.baseMessage.house.entity.PriceDefineUseInHouse;
import cn.plou.web.system.baseMessage.house.entity.Subord;
import cn.plou.web.system.baseMessage.house.vo.HouseInfo;
import cn.plou.web.system.baseMessage.house.vo.HouseSelectInfo;
import cn.plou.web.system.baseMessage.house.vo.HouseVo;
import cn.plou.web.system.baseMessage.house.vo.StructureInfo;
import cn.plou.web.system.baseMessage.unit.entity.Unit;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

@Service
public interface HouseService {
    int addHouse(HouseInfo houseInfo);

    House getHouseById(String consumerId);

    List<House> getHouseByIds(List<String> consumerIds);

    List<House> getHouseByChargeIdOrTelOrIdcardOrCompanyId(String chargeId, String tel, String idcard, String companyId);

    /**
     * @param searchText
     * @param Id
     * @param order
     * @param sortby
     * @param page
     * @param pageSize
     * @return 根据传的id查找所有的house
     * 尹晓晨
     */
    PageInfo<House> getHouseBySearchTextOrId(String searchText, String Id, String order, String sortby, Integer page, Integer pageSize);

    Map<String, House> getHouseByUnitIds(List<Unit> units);

    List<House> getHouseByUnitId(String unitId);

    Integer selectCountByNetStatus(String companyId, String netStatus);

    Integer selectCountNetStatusNull(String companyId);

    Integer selectCountByNetStatusForCommunityIds(List<String> communityIds, String netStatus);

    Integer selectCountNetStatusNullForCommunityIds(List<String> communityIds);

    Integer selectCountByNetStatusForStationIds(List<String> stationIds, String netStatus);

    Integer selectCountNetStatusNullForStationIds(List<String> stationIds);

    Integer selectCountByNetStatusForConsumerIds(List<String> consumerIds, String netStatus);

    Integer selectCountNetStatusNullForConsumerIds(List<String> consumerIds);

    Integer selectCountHeatingStatusNull(String companyId);

    Integer selectCountHeatingStatusNullForCommunityIds(List<String> communityIds);

    Integer selectCountHeatingStatusNullForConsumerIds(List<String> consumerIds);

    Integer selectCountHeatingStatusNullForStationIds(List<String> stationIds);


    Integer selectCountPriceDefineNull(String companyId);

    Integer selectCountPriceDefineNullForCommunityIds(List<String> communityIds);

    Integer selectCountPriceDefineNullForConsumerIds(List<String> consumerIds);

    Integer selectCountPriceDefineNullForStationIds(List<String> stationIds);


    Integer selectCountChargeTypeNull(String companyId);

    Integer selectCountChargeTypeNullForCommunityIds(List<String> communityIds);

    Integer selectCountChargeTypeNullForConsumerIds(List<String> consumerIds);

    Integer selectCountChargeTypeNullForStationIds(List<String> stationIds);


    Integer selectCountByHeatingStatus(String companyId, String heatingStatus);

    Integer selectCountNotHeating(String companyId);


    Integer selectCountByChargeType(String companyId, String chargeType);

    Integer getAllCount(String companyId);

    Integer getCountByCommunityIds(List<String> communityIds);

    Integer getCountByStationIds(List<String> stationIdls);

    Integer getCountByConsumerIds(List<String> consumerIds);


    List<PriceDefine> selectPriceDefineByCompanyId(String companyId);


    List<PriceDefine> selectPriceDefineByCommunityIds(List<String> communityIds);

    List<PriceDefine> selectPriceDefineByConsumerIds(List<String> consumerIds);

    List<PriceDefine> selectPriceDefineByStationIds(List<String> stationIds);

    List<PriceDefineUseInHouse> getAllByYearAndCompany(String companyId);

    /*List<HeatingServeUserListVO> selectByRangeId(HeatingServeUserSearchDTO heatingServeUserSearchDTO);
    Integer selectCountByRangeId(HeatingServeUserSearchDTO heatingServeUserSearchDTO);*/
    StructureInfo getStructureById(String id);

    int modifyHouseBatch(HouseVo houseVo);

    int deleteHouseBatchByIds(List<String> consumerIds);

    int setDelHouseBatch(List<String> consumerIds);

    int modifyHouse(House house);

    List<String> getAllCompanyId();

    PageInfo<HouseInfo> getAllHouse(String companyId, String stationId, String commuityId, String buildingNo, String unitId, HouseVo houseVo,
                                    String order, String sortby, Integer page, Integer pageSize);

    PageInfo<HouseInfo> getAllHouse(String commuityIds, HouseVo houseVo,
                                    String order, String sortby, Integer page, Integer pageSize);

    PageInfo<HouseInfo> getAllHouseByIds(String consumerIds, HouseVo houseVo,
                                         String order, String sortby, Integer page, Integer pageSize);

    House getHouseByName(String houseName, String commuityId);

    ImportResult importExcel(MultipartFile multipartFile, ServletRequest request);

    HouseInfo selectHouseInfoById(String consumerId);

    int addBatch(List<HouseInfo> houseInfoList);

    String exportExcel(HouseSelectInfo houseSelectInfo, ServletRequest request);


    List<House> findByChargeType(String chargeType);


    List<House> findByConsumerIdlike(String likeConsumerId);

    int findCountByConsumerIdlike(String likeConsumerId);

    Integer getMaxHouseId(String unitId);

    Map<String, House> getHouseByIdAndToMap(Commuity commuity, Map<String, Map<String, House>> mapHouse);

    Boolean getSubord(Subord sub, String companyName, String commmuityName, String buildingName,
                      String unitName, String houseName, String stationName, String systemName);

    Boolean getSubordAndUpdate(Subord sub, String companyName, String commmuityName, String buildingName,
        String unitName, String houseName, String stationName, String systemName, Boolean isUpdate);

   List<House> getHouseListById(String   id);
	void modifyHouseName(String changedAdress, String consumerId);
	void modifyHouseNameByAddress(String changedAdress, String orgAdress, String consumerId);
}

package cn.plou.web.charge.chargeconfig.dao;

import cn.plou.web.charge.chargeconfig.dto.BusinessAnalysisSearchDTO;
import cn.plou.web.charge.chargeconfig.dto.ChargeAccountDTO;
import cn.plou.web.charge.chargeconfig.dto.ChargeAccountSearchDTO;
import cn.plou.web.charge.chargeconfig.dto.SaveChargeAccountDTO;
import cn.plou.web.charge.chargeconfig.entity.UserYearAccountDetail;
import cn.plou.web.charge.chargeconfig.vo.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface UserYearAccountDetailMapper {
    int deleteByPrimaryKey(String primaryId);

    int insert(UserYearAccountDetail record);

    int insertSelective(UserYearAccountDetail record);

    UserYearAccountDetail selectByPrimaryKey(String primaryId);

    int updateByPrimaryKeySelective(UserYearAccountDetail record);

    int updateByPrimaryKey(UserYearAccountDetail record);


    BigDecimal getTotalByConsumerId(@Param("consumerId")String consumerId, @Param("annual")String annual);

    List<UserYearAccountDetail> findByUserAndAnnual(@Param("consumerId")String consumerId, @Param("annual")String annual, @Param("order")String order, @Param("sortby")String sortby, @Param("page")Integer page, @Param("pageSize")Integer pageSize);

    List<CheckAccountListVO> getListBySearch(ChargeAccountSearchDTO chargeAccountSearchDTO);

    Integer getListCountBySearch(ChargeAccountSearchDTO chargeAccountSearchDTO);

    List<ChargeAccountDetailListVO> getChargeAccount(ChargeAccountDTO chargeAccountDTO);

    int updateIsreconciliations(SaveChargeAccountDTO saveChargeAccountDTO);

    List<UserYearAccountDetail> findByThirdConsumerId(@Param("thirdConsumerId")String thirdConsumerId);

    List<CheckAccountListVO> getListBySearchOfStation(@Param("dto") ChargeAccountSearchDTO chargeAccountSearchDTO, @Param("list") List<String> ids);

    Integer getListCountBySearchOfStation(@Param("dto") ChargeAccountSearchDTO chargeAccountSearchDTO, @Param("list") List<String> ids);

    List<BusinessAnalysisListVO> businessAnalysis(BusinessAnalysisSearchDTO businessAnalysisSearchDTO);

    List<BusinessAnalysisChartVO> businessAnalysisChart(BusinessAnalysisSearchDTO businessAnalysisSearchDTO);

    List<AccountSummaryListVO> getAccountSummaryListByConsumer(@Param("companyId")String companyId,
                                                               @Param("stationId")String stationId,
                                                               @Param("commuityId")String commuityId,
                                                               @Param("buildingNo")String buildingNo,
                                                               @Param("unitId")String unitId,
                                                               @Param("consumerId")String consumerId,
                                                               @Param("dateStart")Date dateStart,
                                                               @Param("dateEnd")Date dateEnd,
                                                               @Param("order")String order,
                                                               @Param("sortby")String sortby,
                                                               @Param("page")Integer page,
                                                               @Param("pageSize")Integer pageSize);

    List<AccountSummaryListVO> getAccountSummaryListByCommuity(@Param("companyId")String companyId,
                                                               @Param("stationId")String stationId,
                                                               @Param("commuityId")String commuityId,
                                                               @Param("buildingNo")String buildingNo,
                                                               @Param("unitId")String unitId,
                                                               @Param("consumerId")String consumerId,
                                                               @Param("dateStart")Date dateStart,
                                                               @Param("dateEnd")Date dateEnd,
                                                               @Param("order")String order,
                                                               @Param("sortby")String sortby,
                                                               @Param("page")Integer page,
                                                               @Param("pageSize")Integer pageSize);


    List<AccountSummaryListVO> getAccountSummaryListByBuilding(@Param("companyId")String companyId,
                                                               @Param("stationId")String stationId,
                                                               @Param("commuityId")String commuityId,
                                                               @Param("buildingNo")String buildingNo,
                                                               @Param("unitId")String unitId,
                                                               @Param("consumerId")String consumerId,
                                                               @Param("dateStart")Date dateStart,
                                                               @Param("dateEnd")Date dateEnd,
                                                               @Param("order")String order,
                                                               @Param("sortby")String sortby,
                                                               @Param("page")Integer page,
                                                               @Param("pageSize")Integer pageSize);

    List<AccountSummaryListVO> getAccountSummaryListByStation(@Param("companyId")String companyId,
                                                              @Param("stationId")String stationId,
                                                              @Param("commuityId")String commuityId,
                                                              @Param("buildingNo")String buildingNo,
                                                              @Param("unitId")String unitId,
                                                              @Param("consumerId")String consumerId,
                                                              @Param("dateStart")Date dateStart,
                                                              @Param("dateEnd")Date dateEnd,
                                                              @Param("order")String order,
                                                              @Param("sortby")String sortby,
                                                              @Param("page")Integer page,
                                                              @Param("pageSize")Integer pageSize);

    List<AccountSummaryListVO> getAccountSummaryListByCompany(@Param("companyId")String companyId,
                                                              @Param("stationId")String stationId,
                                                              @Param("commuityId")String commuityId,
                                                              @Param("buildingNo")String buildingNo,
                                                              @Param("unitId")String unitId,
                                                              @Param("consumerId")String consumerId,
                                                              @Param("dateStart")Date dateStart,
                                                              @Param("dateEnd")Date dateEnd,
                                                              @Param("order")String order,
                                                              @Param("sortby")String sortby,
                                                              @Param("page")Integer page,
                                                              @Param("pageSize")Integer pageSize);




    AccountSummaryListVO getAccountSummaryDataByConsumer(@Param("companyId")String companyId,
                                                         @Param("stationId")String stationId,
                                                         @Param("commuityId")String commuityId,
                                                         @Param("buildingNo")String buildingNo,
                                                         @Param("unitId")String unitId,
                                                         @Param("consumerId")String consumerId,
                                                         @Param("dateStart")Date dateStart,
                                                         @Param("dateEnd")Date dateEnd);

    AccountSummaryListVO getAccountSummaryDataByCommuity(@Param("companyId")String companyId,
                                                         @Param("stationId")String stationId,
                                                         @Param("commuityId")String commuityId,
                                                         @Param("buildingNo")String buildingNo,
                                                         @Param("unitId")String unitId,
                                                         @Param("consumerId")String consumerId,
                                                         @Param("dateStart")Date dateStart,
                                                         @Param("dateEnd")Date dateEnd);

    AccountSummaryListVO getAccountSummaryDataByBuilding(@Param("companyId")String companyId,
                                                         @Param("stationId")String stationId,
                                                         @Param("commuityId")String commuityId,
                                                         @Param("buildingNo")String buildingNo,
                                                         @Param("unitId")String unitId,
                                                         @Param("consumerId")String consumerId,
                                                         @Param("dateStart")Date dateStart,
                                                         @Param("dateEnd")Date dateEnd);


    AccountSummaryListVO getAccountSummaryDataByStation(@Param("companyId")String companyId,
                                                        @Param("stationId")String stationId,
                                                        @Param("commuityId")String commuityId,
                                                        @Param("buildingNo")String buildingNo,
                                                        @Param("unitId")String unitId,
                                                        @Param("consumerId")String consumerId,
                                                        @Param("dateStart")Date dateStart,
                                                        @Param("dateEnd")Date dateEnd);

    AccountSummaryListVO getAccountSummaryDataByCompany(@Param("companyId")String companyId,
                                                        @Param("stationId")String stationId,
                                                        @Param("commuityId")String commuityId,
                                                        @Param("buildingNo")String buildingNo,
                                                        @Param("unitId")String unitId,
                                                        @Param("consumerId")String consumerId,
                                                        @Param("dateStart")Date dateStart,
                                                        @Param("dateEnd")Date dateEnd);

    List<AccountDetailListVO> getAccountDetailListByAccountType(@Param("companyId")String companyId,
                                                              @Param("stationId")String stationId,
                                                              @Param("commuityId")String commuityId,
                                                              @Param("buildingNo")String buildingNo,
                                                              @Param("unitId")String unitId,
                                                              @Param("consumerId")String consumerId,
                                                              @Param("dateStart")Date dateStart,
                                                              @Param("dateEnd")Date dateEnd);
}
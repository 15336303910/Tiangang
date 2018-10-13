package cn.plou.web.charge.chargeconfig.service;

import cn.plou.web.charge.chargeconfig.dto.BusinessAnalysisSearchDTO;
import cn.plou.web.charge.chargeconfig.dto.HeatingSummarySearchDTO;
import cn.plou.web.charge.chargeconfig.entity.UserYearAccountDetail;
import cn.plou.web.charge.chargeconfig.vo.BusinessAnalysisListVO;
import cn.plou.web.charge.chargeconfig.vo.AccountDetailListVO;
import cn.plou.web.charge.chargeconfig.vo.AccountSummaryListVO;
import cn.plou.web.charge.chargeconfig.vo.BusinessAnalysisVO;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface UserYearAccountDetailService {
    int deleteByPrimaryKey(String primaryId);

    int insert(UserYearAccountDetail record);

    int insertSelective(UserYearAccountDetail record);

    UserYearAccountDetail selectByPrimaryKey(String primaryId);

    int updateByPrimaryKeySelective(UserYearAccountDetail record);

    int updateByPrimaryKey(UserYearAccountDetail record);

    BigDecimal getTotalByConsumerId(String consumerId, String annual);

    PageInfo<UserYearAccountDetail> findByUserAndAnnual(String consumerId,String annual, String order, String sortby, Integer page, Integer pageSize);


    List<UserYearAccountDetail> findByThirdConsumerId(String thirdConsumerId);

    BusinessAnalysisVO businessAnalysis(BusinessAnalysisSearchDTO businessAnalysisSearchDTO);

    List<BusinessAnalysisListVO> heatingSummary(HeatingSummarySearchDTO heatingSummarySearchDTO);

    PageInfo<AccountSummaryListVO> getAccountSummaryListByConsumer(String companyId,
                                                                   String stationId,
                                                                   String commuityId,
                                                                   String buildingNo,
                                                                   String unitId,
                                                                   String consumerId,
                                                                   Date dateStart,
                                                                   Date dateEnd,
                                                                   String order,
                                                                   String sortby,
                                                                   Integer page,
                                                                   Integer pageSize);


    PageInfo<AccountSummaryListVO> getAccountSummaryListByCommuity(String companyId,
                                                                   String stationId,
                                                                   String commuityId,
                                                                   String buildingNo,
                                                                   String unitId,
                                                                   String consumerId,
                                                                   Date dateStart,
                                                                   Date dateEnd,
                                                                   String order,
                                                                   String sortby,
                                                                   Integer page,
                                                                   Integer pageSize);


    PageInfo<AccountSummaryListVO> getAccountSummaryListByBuilding(String companyId,
                                                                   String stationId,
                                                                   String commuityId,
                                                                   String buildingNo,
                                                                   String unitId,
                                                                   String consumerId,
                                                                   Date dateStart,
                                                                   Date dateEnd,
                                                                   String order,
                                                                   String sortby,
                                                                   Integer page,
                                                                   Integer pageSize);


    PageInfo<AccountSummaryListVO> getAccountSummaryListByStation(String companyId,
                                                                  String stationId,
                                                                  String commuityId,
                                                                  String buildingNo,
                                                                  String unitId,
                                                                  String consumerId,
                                                                  Date dateStart,
                                                                  Date dateEnd,
                                                                  String order,
                                                                  String sortby,
                                                                  Integer page,
                                                                  Integer pageSize);

    PageInfo<AccountSummaryListVO> getAccountSummaryListByCompany(String companyId,
                                                                  String stationId,
                                                                  String commuityId,
                                                                  String buildingNo,
                                                                  String unitId,
                                                                  String consumerId,
                                                                  Date dateStart,
                                                                  Date dateEnd,
                                                                  String order,
                                                                  String sortby,
                                                                  Integer page,
                                                                  Integer pageSize);


    AccountSummaryListVO getAccountSummaryDataByConsumer(String companyId,
                                                         String stationId,
                                                         String commuityId,
                                                         String buildingNo,
                                                         String unitId,
                                                         String consumerId,
                                                         Date dateStart,
                                                         Date dateEnd);


    AccountSummaryListVO getAccountSummaryDataByCommuity(String companyId,
                                                         String stationId,
                                                         String commuityId,
                                                         String buildingNo,
                                                         String unitId,
                                                         String consumerId,
                                                         Date dateStart,
                                                         Date dateEnd);

    AccountSummaryListVO getAccountSummaryDataByBuilding(String companyId,
                                                         String stationId,
                                                         String commuityId,
                                                         String buildingNo,
                                                         String unitId,
                                                         String consumerId,
                                                         Date dateStart,
                                                         Date dateEnd);

    AccountSummaryListVO getAccountSummaryDataByStation(String companyId,
                                                        String stationId,
                                                        String commuityId,
                                                        String buildingNo,
                                                        String unitId,
                                                        String consumerId,
                                                        Date dateStart,
                                                        Date dateEnd);

    AccountSummaryListVO getAccountSummaryDataByCompany(String companyId,
                                                        String stationId,
                                                        String commuityId,
                                                        String buildingNo,
                                                        String unitId,
                                                        String consumerId,
                                                        Date dateStart,
                                                        Date dateEnd);



    List<AccountDetailListVO> getAccountDetailListByAccountType(String companyId,
                                                        String stationId,
                                                        String commuityId,
                                                        String buildingNo,
                                                        String unitId,
                                                        String consumerId,
                                                        Date dateStart,
                                                        Date dateEnd);

}
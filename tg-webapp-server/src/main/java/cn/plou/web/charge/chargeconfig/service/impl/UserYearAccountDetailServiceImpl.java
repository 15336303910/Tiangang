package cn.plou.web.charge.chargeconfig.service.impl;

import cn.plou.web.charge.chargeconfig.dao.UserYearAccountDetailMapper;
import cn.plou.web.charge.chargeconfig.dto.BusinessAnalysisSearchDTO;
import cn.plou.web.charge.chargeconfig.entity.UserYearAccountDetail;
import cn.plou.web.charge.chargeconfig.service.UserYearAccountDetailService;
import cn.plou.web.charge.chargeconfig.vo.AccountDetailListVO;
import cn.plou.web.charge.chargeconfig.vo.AccountSummaryListVO;
import cn.plou.web.charge.chargeconfig.vo.BusinessAnalysisListVO;
import cn.plou.web.charge.chargeconfig.vo.BusinessAnalysisVO;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.system.baseMessage.commuity.dao.CommuityMapper;
import cn.plou.web.system.commonMessage.typeMst.dao.TypeMstMapper;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static cn.plou.web.common.utils.a1.DateUtil.BANK_DEFAULT_DATE_FORMAT;
import static cn.plou.web.common.utils.a1.DateUtil.toDateTimeString;

/**
 * @author yinxiaochen
 * 2018/8/15 15:38
 */


@Service
@Transactional(rollbackFor = Exception.class)
public class UserYearAccountDetailServiceImpl implements UserYearAccountDetailService {

    @Autowired
    private TypeMstMapper typeMstMapper;


    @Resource
    private CommuityMapper commuityMapper;

    @Resource
    private UserYearAccountDetailMapper userYearAccountDetailMapper;

    @Override
    public int deleteByPrimaryKey(String primaryId) {
        return userYearAccountDetailMapper.deleteByPrimaryKey(primaryId);
    }

    @Override
    public int insert(UserYearAccountDetail record) {
        return userYearAccountDetailMapper.insert(record);
    }

    @Override
    public int insertSelective(UserYearAccountDetail record) {
        return userYearAccountDetailMapper.insertSelective(record);
    }

    @Override
    public UserYearAccountDetail selectByPrimaryKey(String primaryId) {
        return userYearAccountDetailMapper.selectByPrimaryKey(primaryId);
    }


    @Override
    public int updateByPrimaryKeySelective(UserYearAccountDetail record) {
        return userYearAccountDetailMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserYearAccountDetail record) {
        return userYearAccountDetailMapper.updateByPrimaryKey(record);
    }


    @Override
    public BigDecimal getTotalByConsumerId(String consumerId, String annual) {
        return userYearAccountDetailMapper.getTotalByConsumerId(consumerId, annual);
    }


    @Override
    public PageInfo<UserYearAccountDetail> findByUserAndAnnual(String consumerId, String annual, String order, String sortby, Integer page, Integer pageSize) {

        List<UserYearAccountDetail> list = new ArrayList<>();
        if (consumerId.length() == Constant.STATION_ID_LENGTH) {//说明是站
            List<String> commuityIds = commuityMapper.selectCommuityIdsByStationId(consumerId);
            if (commuityIds.size() > 0) {
                PageHelper.startPage(page, pageSize);
                list = userYearAccountDetailMapper.findByUserAndAnnualByCommuityIds(commuityIds, annual, order, sortby, page, pageSize);
            }
        } else {
            PageHelper.startPage(page, pageSize);
            list = userYearAccountDetailMapper.findByUserAndAnnual(consumerId, annual, order, sortby, page, pageSize);
        }
        return new PageInfo<>(list);


    }

    @Override
    public List<UserYearAccountDetail> findByThirdConsumerId(String thirdConsumerId) {
        return userYearAccountDetailMapper.findByThirdConsumerId(thirdConsumerId);
    }

    /**
     * @Description: 统计分析-营业分析
     * @Param: [businessAnalysisSearchDTO]
     * @Return: java.util.List<cn.plou.web.charge.chargeconfig.vo.BusinessAnalysisListVO>
     * @Author: youbc
     * @Date: 2018/10/10 9上午10
     */
    @Override
    public BusinessAnalysisVO businessAnalysis(BusinessAnalysisSearchDTO businessAnalysisSearchDTO) {
        BusinessAnalysisVO result = new BusinessAnalysisVO();
        result.setChart(userYearAccountDetailMapper.businessAnalysisChart(businessAnalysisSearchDTO));
        result.setList(getBusinessAnalysisList(businessAnalysisSearchDTO));
        return result;
    }

    /**
     * @Description: 营业分析列表
     * @Param: [businessAnalysisSearchDTO]
     * @Return: java.util.List<cn.plou.web.charge.chargeconfig.vo.BusinessAnalysisListVO>
     * @Author: youbc
     * @Date: 18/10/12 17下午27
     */
    private List<BusinessAnalysisListVO> getBusinessAnalysisList(BusinessAnalysisSearchDTO businessAnalysisSearchDTO) {
        List<BusinessAnalysisListVO> list = userYearAccountDetailMapper.businessAnalysis(businessAnalysisSearchDTO);
        List<TypeMst> pay_channel = typeMstMapper.selectDownInfoByTypeKbn("pay_channel");
        int numAll = 0;
        BigDecimal accountCostAll = BigDecimal.ZERO;
        for (BusinessAnalysisListVO vo : list) {
            if (vo.getNumTotal() == null) {
                vo.setNumTotal(0);
            }
            numAll = numAll + vo.getNumTotal();
            if (vo.getAccountCostTotal() == null) {
                vo.setAccountCostTotal(BigDecimal.ZERO);
            }
            accountCostAll = accountCostAll.add(vo.getAccountCostTotal());
            for (TypeMst typeMst : pay_channel) {
                if (StringUtils.equalsIgnoreCase(typeMst.getId(), vo.getAccountChannel())) {
                    vo.setAccountChannelName(typeMst.getTypeName());
                    break;
                }
            }

            String currentAnnual = businessAnalysisSearchDTO.getCurrentAnnual();
            if (StringUtils.isEmpty(currentAnnual)) {
                String dateStartStr = toDateTimeString(businessAnalysisSearchDTO.getDateStart(), BANK_DEFAULT_DATE_FORMAT);
                String dateEndStr = toDateTimeString(businessAnalysisSearchDTO.getDateEnd(), BANK_DEFAULT_DATE_FORMAT);
                if (StringUtils.equalsIgnoreCase(dateStartStr, dateEndStr)) {
                    vo.setDateRange(dateStartStr);
                } else {
                    vo.setDateRange(dateStartStr + "-" + dateEndStr);
                }
            } else {
                vo.setDateRange(currentAnnual);
            }
        }

        for (BusinessAnalysisListVO vo : list) {
            vo.setAccountCostPercent(vo.getAccountCostTotal().multiply(new BigDecimal(100)).divide(accountCostAll, 2, BigDecimal.ROUND_HALF_UP));
            vo.setNumPercent(new BigDecimal(vo.getNumTotal()).multiply(new BigDecimal(100)).divide(new BigDecimal(numAll), 2, BigDecimal.ROUND_HALF_UP));
        }
        return filterList(list, businessAnalysisSearchDTO);
    }

    /**
     * @Description: 过滤营业分析列表
     * @Param: [list, businessAnalysisSearchDTO]
     * @Return: java.util.List<cn.plou.web.charge.chargeconfig.vo.BusinessAnalysisListVO>
     * @Author: youbc
     * @Date: 18/10/12 16下午50
     */
    private List<BusinessAnalysisListVO> filterList(List<BusinessAnalysisListVO> list, BusinessAnalysisSearchDTO businessAnalysisSearchDTO) {
        String payChannel = businessAnalysisSearchDTO.getPayChannel();
        if (StringUtils.isNotEmpty(payChannel)) {
            List<BusinessAnalysisListVO> filterList = Lists.newArrayList();
            for (BusinessAnalysisListVO vo : list) {
                if (StringUtils.equalsIgnoreCase(payChannel, vo.getAccountChannel())) {
                    filterList.add(vo);
                    break;
                }
            }
            return filterList;
        } else {
            return list;
        }
    }

    @Override
    public PageInfo<AccountSummaryListVO> getAccountSummaryListByConsumer(String companyId,
                                                                          String stationId,
                                                                          String commuityId,
                                                                          String buildingNo,
                                                                          String unitId,
                                                                          String consumerId,
                                                                          String annual,
                                                                          String currentAnnual,
                                                                          Date dateStart,
                                                                          Date dateEnd,
                                                                          String order,
                                                                          String sortby,
                                                                          Integer page,
                                                                          Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<AccountSummaryListVO> accountSummaryByConsumer = userYearAccountDetailMapper.getAccountSummaryListByConsumer(companyId,
                stationId,
                commuityId,
                buildingNo,
                unitId,
                consumerId,
                annual,
                currentAnnual,
                dateStart,
                dateEnd,
                order,
                sortby,
                page,
                pageSize);
        PageInfo<AccountSummaryListVO> pageInfo = new PageInfo<>(accountSummaryByConsumer);
        return pageInfo;

    }


    @Override
    public PageInfo<AccountSummaryListVO> getAccountSummaryListByCommuity(String companyId,
                                                                          String stationId,
                                                                          String commuityId,
                                                                          String buildingNo,
                                                                          String unitId,
                                                                          String consumerId,
                                                                          String annual,
                                                                          String currentAnnual,
                                                                          Date dateStart,
                                                                          Date dateEnd,
                                                                          String order,
                                                                          String sortby,
                                                                          Integer page,
                                                                          Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<AccountSummaryListVO> accountSummaryByCommuity = userYearAccountDetailMapper.getAccountSummaryListByCommuity(companyId,
                stationId,
                commuityId,
                buildingNo,
                unitId,
                consumerId,
                annual,
                currentAnnual,
                dateStart,
                dateEnd,
                order,
                sortby,
                page,
                pageSize);
        PageInfo<AccountSummaryListVO> pageInfo = new PageInfo<>(accountSummaryByCommuity);
        return pageInfo;

    }


    @Override
    public PageInfo<AccountSummaryListVO> getAccountSummaryListByBuilding(String companyId,
                                                                          String stationId,
                                                                          String commuityId,
                                                                          String buildingNo,
                                                                          String unitId,
                                                                          String consumerId,
                                                                          String annual,
                                                                          String currentAnnual,
                                                                          Date dateStart,
                                                                          Date dateEnd,
                                                                          String order,
                                                                          String sortby,
                                                                          Integer page,
                                                                          Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<AccountSummaryListVO> accountSummaryByBuilding = userYearAccountDetailMapper.getAccountSummaryListByBuilding(companyId,
                stationId,
                commuityId,
                buildingNo,
                unitId,
                consumerId,
                annual,
                currentAnnual,
                dateStart,
                dateEnd,
                order,
                sortby,
                page,
                pageSize);
        PageInfo<AccountSummaryListVO> pageInfo = new PageInfo<>(accountSummaryByBuilding);
        return pageInfo;

    }

    @Override
    public PageInfo<AccountSummaryListVO> getAccountSummaryListByStation(String companyId,
                                                                         String stationId,
                                                                         String commuityId,
                                                                         String buildingNo,
                                                                         String unitId,
                                                                         String consumerId,
                                                                         String annual,
                                                                         String currentAnnual,
                                                                         Date dateStart,
                                                                         Date dateEnd,
                                                                         String order,
                                                                         String sortby,
                                                                         Integer page,
                                                                         Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<AccountSummaryListVO> accountSummaryByStation = userYearAccountDetailMapper.getAccountSummaryListByStation(companyId,
                stationId,
                commuityId,
                buildingNo,
                unitId,
                consumerId,
                annual,
                currentAnnual,
                dateStart,
                dateEnd,
                order,
                sortby,
                page,
                pageSize);
        PageInfo<AccountSummaryListVO> pageInfo = new PageInfo<>(accountSummaryByStation);
        return pageInfo;

    }

    @Override
    public PageInfo<AccountSummaryListVO> getAccountSummaryListByCompany(String companyId,
                                                                         String stationId,
                                                                         String commuityId,
                                                                         String buildingNo,
                                                                         String unitId,
                                                                         String consumerId,
                                                                         String annual,
                                                                         String currentAnnual,
                                                                         Date dateStart,
                                                                         Date dateEnd,
                                                                         String order,
                                                                         String sortby,
                                                                         Integer page,
                                                                         Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<AccountSummaryListVO> accountSummaryByCompany = userYearAccountDetailMapper.getAccountSummaryListByCompany(companyId,
                stationId,
                commuityId,
                buildingNo,
                unitId,
                consumerId,
                annual,
                currentAnnual,
                dateStart,
                dateEnd,
                order,
                sortby,
                page,
                pageSize);
        PageInfo<AccountSummaryListVO> pageInfo = new PageInfo<>(accountSummaryByCompany);
        return pageInfo;

    }


    @Override
    public AccountSummaryListVO getAccountSummaryDataByConsumer(String companyId,
                                                                String stationId,
                                                                String commuityId,
                                                                String buildingNo,
                                                                String unitId,
                                                                String consumerId,
                                                                String annual,
                                                                String currentAnnual,
                                                                Date dateStart,
                                                                Date dateEnd) {

        AccountSummaryListVO accountSummaryByConsumer = userYearAccountDetailMapper.getAccountSummaryDataByConsumer(companyId,
                stationId,
                commuityId,
                buildingNo,
                unitId,
                consumerId,
                annual,
                currentAnnual,
                dateStart,
                dateEnd);
        return accountSummaryByConsumer;

    }


    @Override
    public AccountSummaryListVO getAccountSummaryDataByCommuity(String companyId,
                                                                String stationId,
                                                                String commuityId,
                                                                String buildingNo,
                                                                String unitId,
                                                                String consumerId,
                                                                String annual,
                                                                String currentAnnual,
                                                                Date dateStart,
                                                                Date dateEnd) {

        AccountSummaryListVO accountSummaryByCommuity = userYearAccountDetailMapper.getAccountSummaryDataByCommuity(companyId,
                stationId,
                commuityId,
                buildingNo,
                unitId,
                consumerId,
                annual,
                currentAnnual,
                dateStart,
                dateEnd);
        return accountSummaryByCommuity;

    }


    @Override
    public AccountSummaryListVO getAccountSummaryDataByBuilding(String companyId,
                                                                String stationId,
                                                                String commuityId,
                                                                String buildingNo,
                                                                String unitId,
                                                                String consumerId,
                                                                String annual,
                                                                String currentAnnual,
                                                                Date dateStart,
                                                                Date dateEnd) {

        AccountSummaryListVO accountSummaryByBuilding = userYearAccountDetailMapper.getAccountSummaryDataByBuilding(companyId,
                stationId,
                commuityId,
                buildingNo,
                unitId,
                consumerId,
                annual,
                currentAnnual,
                dateStart,
                dateEnd);
        return accountSummaryByBuilding;

    }


    @Override
    public AccountSummaryListVO getAccountSummaryDataByStation(String companyId,
                                                               String stationId,
                                                               String commuityId,
                                                               String buildingNo,
                                                               String unitId,
                                                               String consumerId,
                                                               String annual,
                                                               String currentAnnual,
                                                               Date dateStart,
                                                               Date dateEnd) {

        AccountSummaryListVO accountSummaryByStation = userYearAccountDetailMapper.getAccountSummaryDataByStation(companyId,
                stationId,
                commuityId,
                buildingNo,
                unitId,
                consumerId,
                annual,
                currentAnnual,
                dateStart,
                dateEnd);
        return accountSummaryByStation;

    }

    @Override
    public AccountSummaryListVO getAccountSummaryDataByCompany(String companyId,
                                                               String stationId,
                                                               String commuityId,
                                                               String buildingNo,
                                                               String unitId,
                                                               String consumerId,
                                                               String annual,
                                                               String currentAnnual,
                                                               Date dateStart,
                                                               Date dateEnd) {

        AccountSummaryListVO accountSummaryByCompany = userYearAccountDetailMapper.getAccountSummaryDataByCompany(companyId,
                stationId,
                commuityId,
                buildingNo,
                unitId,
                consumerId,
                annual,
                currentAnnual,
                dateStart,
                dateEnd);
        return accountSummaryByCompany;

    }

    @Override
    public List<AccountDetailListVO> getAccountDetailListByAccountChannel(String companyId,
                                                                          String stationId,
                                                                          String commuityId,
                                                                          String buildingNo,
                                                                          String unitId,
                                                                          String consumerId,
                                                                          String annual,
                                                                          Date dateStart,
                                                                          Date dateEnd) {
        List<AccountDetailListVO> accountDetailListByAccountType = userYearAccountDetailMapper.getAccountDetailListByAccountChannel(companyId,
                stationId,
                commuityId,
                buildingNo,
                unitId,
                consumerId,
                annual,
                dateStart,
                dateEnd);
        return accountDetailListByAccountType;
    }


    @Override
    public List<AccountDetailListVO> getAccountDetailListByAccountType(String companyId,
                                                                       String stationId,
                                                                       String commuityId,
                                                                       String buildingNo,
                                                                       String unitId,
                                                                       String consumerId,
                                                                       String accountChannel,
                                                                       String annual,
                                                                       Date dateStart,
                                                                       Date dateEnd) {
        List<AccountDetailListVO> accountDetailListByAccountType = userYearAccountDetailMapper.getAccountDetailListByAccountType(companyId,
                stationId,
                commuityId,
                buildingNo,
                unitId,
                consumerId,
                accountChannel,
                annual,
                dateStart,
                dateEnd);
        return accountDetailListByAccountType;
    }


}

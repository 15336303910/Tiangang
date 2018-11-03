package cn.plou.web.charge.chargeconfig.service.impl;

import cn.plou.web.charge.chargeconfig.dao.UserYearHeatMapper;
import cn.plou.web.charge.chargeconfig.dto.ClearTaskDTO;
import cn.plou.web.charge.chargeconfig.dto.HeatingSummarySearchDTO;
import cn.plou.web.charge.chargeconfig.dto.InfoObject;
import cn.plou.web.charge.chargeconfig.entity.UserYearHeat;
import cn.plou.web.charge.chargeconfig.service.UserYearHeatService;
import cn.plou.web.charge.chargeconfig.vo.*;
import cn.plou.web.common.config.common.BasePageEntity;
import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.system.baseMessage.commuity.dao.CommuityMapper;
import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import cn.plou.web.system.commonMessage.typeMst.dao.TypeMstMapper;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 2018/8/15 15:38
 */


@Service
@Transactional(rollbackFor = Exception.class)
public class UserYearHeatServiceImpl implements UserYearHeatService {


    @Resource
    private UserYearHeatMapper userYearHeatMapper;

    @Resource
    private CommuityMapper commuityMapper;

    @Resource
    private TypeMstMapper typeMstMapper;

    @Override
    public int deleteByPrimaryKey(String primaryId) {
        return userYearHeatMapper.deleteByPrimaryKey(primaryId);
    }

    @Override
    public int insert(UserYearHeat record) {
        return userYearHeatMapper.insert(record);
    }

    @Override
    public int insertSelective(UserYearHeat record) {
        return userYearHeatMapper.insertSelective(record);
    }

    @Override
    public void insertByBatch(List<UserYearHeat> userYearHeats) {
        userYearHeatMapper.insertByBatch(userYearHeats);
    }


    @Override
    public UserYearHeat selectByPrimaryKey(String primaryId) {
        return userYearHeatMapper.selectByPrimaryKey(primaryId);
    }


    @Override
    public int updateByPrimaryKeySelective(UserYearHeat record) {
        return userYearHeatMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserYearHeat record) {
        return userYearHeatMapper.updateByPrimaryKey(record);
    }


    @Override
    public List<UserYearHeat> findAllUserYearHeats() {
        return userYearHeatMapper.findAllUserYearHeats();
    }

    @Override
    public UserYearHeat findLastUserYearHeats() {
        return userYearHeatMapper.findLastUserYearHeats();
    }

    @Override
    public List<UserYearHeat> findAllUserYearHeatsByAnnual(String annual) {
        return userYearHeatMapper.findAllUserYearHeatsByAnnual(annual);
    }

    @Override
    public UserYearHeat findByUserAndAnnual(String consumerId, String annual) {
        return userYearHeatMapper.findByUserAndAnnual(consumerId, annual);
    }

    /**
     * @param id     楼 或者单元的Id
     * @param annual 供暖年度
     * @return
     */
    @Override
    public List<UserYearHeat> findByIdAndAnnual(String id, String annual) {
         return userYearHeatMapper.findByIdAndAnnual(id, annual);
    }

    @Override
    public PageInfo<UserYearHeat> findByUser(String consumerId, String annual, String order, String sortby, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<UserYearHeat> userYearReceivableDetailsByConsumerId = userYearHeatMapper.findByUser(consumerId, annual, order, sortby, page, pageSize);
        PageInfo<UserYearHeat> pageInfo = new PageInfo<>(userYearReceivableDetailsByConsumerId);
        return pageInfo;

    }


    @Override
    public PageInfo<UserYearHeatDetailInfo> findUserYearHeatDetailInfo(String consumerId, String annual, String order, String sortby, Integer page, Integer pageSize) {
        List<UserYearHeatDetailInfo> list = new ArrayList<>();
          if (consumerId.length() == Constant.STATION_ID_LENGTH) {//说明是站
            List<String> commuityIds = commuityMapper.selectCommuityIdsByStationId(consumerId);
            if (commuityIds.size() > 0) {
                PageHelper.startPage(page, pageSize);
                list = userYearHeatMapper.findUserYearHeatDetailInfoByCommuityIds(commuityIds, annual, order, sortby, page, pageSize);
            }
        } else {
            PageHelper.startPage(page, pageSize);
              list = userYearHeatMapper.findUserYearHeatDetailInfo(consumerId, annual, order, sortby, page, pageSize);
        }
        return new PageInfo<>(list);
    }


    @Override
    public PageInfo<UserYearHeat> findByUserForOldAnnual(String consumerId, String annual, String order, String sortby, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<UserYearHeat> userYearReceivableDetailsByConsumerId = userYearHeatMapper.findByUserForOldAnnual(consumerId, annual, order, sortby, page, pageSize);
        PageInfo<UserYearHeat> pageInfo = new PageInfo<>(userYearReceivableDetailsByConsumerId);
        return pageInfo;

    }


    @Override
    public int deleteByConsumerIds(String consumerIds, String commuityIds, String annual) {
        List<String> consumerIdlst = new ArrayList<>();
        List<String> commuityIdlst = new ArrayList<>();
        if (consumerIds == null) {
            consumerIdlst = null;
        } else {
            String[] split = consumerIds.split(",");
            for (String s : split) {
                consumerIdlst.add(s);
            }
        }

        if (commuityIds == null) {
            commuityIdlst = null;
        } else {
            String[] split = commuityIds.split(",");
            for (String s : split) {
                commuityIdlst.add(s);
            }
        }
        return userYearHeatMapper.deleteByConsumerIds(consumerIdlst, commuityIdlst, annual);
    }


    @Override
    public int deleteByConsumerId(String consumerId, String communityId, String annual) {
        return userYearHeatMapper.deleteByConsumerId(consumerId, communityId, annual);
    }


    @Override
    public BigDecimal getOldYearOwe(String consumerId, String annual) {
        return userYearHeatMapper.getOldYearOwe(consumerId, annual);
    }

    /**
     * @param consumerId
     * @param annuals
     * @return 特定年度的欠费费用
     */
    @Override
    public BigDecimal getchoosedYearOwe(String consumerId, List<String> annuals) {
        return userYearHeatMapper.getchoosedYearOwe(consumerId, annuals);
    }

    @Override
    public PageInfo<UserYearHeatLateFee> calcLateFee(String dateNow, String order, String sortby, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<UserYearHeatLateFee> userYearHeatLateFees = userYearHeatMapper.calcLateFee(dateNow, order, sortby, page, pageSize);
        PageInfo<UserYearHeatLateFee> pageInfo = new PageInfo<>(userYearHeatLateFees);
        return pageInfo;

    }

    @Override
    public List<ClearTaskDTO> getStatisticalData(String id) {
        List<ClearTaskDTO> clearTaskDTOS = new ArrayList<>();
        if (id.length() != 6) {
            clearTaskDTOS = userYearHeatMapper.getStatisticalData(id);
        } else {
            //通过站id查出所有的小区
            List<Commuity> commuityList = commuityMapper.selectTreeList(id, null);
            if (commuityList != null && commuityList.size() > 0) {
                List<String> ids = new ArrayList<>();
                for (Commuity commuity : commuityList) {
                    ids.add(commuity.getCommuityId());
                }
                clearTaskDTOS = userYearHeatMapper.getStatisticalDataBycommuityIds(ids);
            }
        }
        return clearTaskDTOS;
    }


    @Override
    public InfoObject handleListData(List<ClearTaskDTO> clearTaskDTOS, String annual) {
        BigDecimal totalSumReceivable = new BigDecimal("0");//总应收金额
        BigDecimal totalSumAccount = new BigDecimal("0");//总实收金额
        BigDecimal totalArrearage = new BigDecimal("0");//总欠费金额
        BigDecimal chargeRate = new BigDecimal("0");//收费率
        InfoObject infoObject = new InfoObject();
        for (ClearTaskDTO clearTaskDTO : clearTaskDTOS) {
            if (clearTaskDTO.getSumReceivable() != null) {
                totalSumReceivable = totalSumReceivable.add(clearTaskDTO.getSumReceivable());//应收金额
            }
            if (clearTaskDTO.getSumAccount() != null) {
                totalSumAccount = totalSumAccount.add(clearTaskDTO.getSumAccount());//实收金额
            }
        }
        if (totalSumReceivable.doubleValue() == 0) {
            chargeRate = new BigDecimal("0.0");
        } else {
            chargeRate = totalSumAccount.divide(totalSumReceivable, 2, RoundingMode.FLOOR);
        }

        totalArrearage = totalSumReceivable.subtract(totalSumAccount);
        infoObject.setTotalArrearage(totalArrearage);
        infoObject.setChargeRate(chargeRate);
        return infoObject;
    }

    @Override
    public List<UserYearHeat> findAllUserYearHeatsByConsumerIds(List<String> consumerIds) {
        return userYearHeatMapper.findAllUserYearHeatsByConsumerIds(consumerIds);
    }


    public PageInfo<UserYearHeat> findFeeLessById(String id,BigDecimal minPriceValue ,String annual,String userType,
                                                  boolean ignoreHasTask,BasePageEntity basePageEntity) {

        // 这里一会判断一下 站的例外情况
        List<UserYearHeat> list = new ArrayList<>();
        if (id.length() == 6) {
            List<String> commuityIdList = commuityMapper.selectCommuityIdsByStationId(id);
            PageHelper.startPage(basePageEntity.getPage(), basePageEntity.getPageSize());
            list = userYearHeatMapper.findFeeLessBycommuityIdList(commuityIdList,minPriceValue , annual, userType,ignoreHasTask, basePageEntity);
        } else {
            PageHelper.startPage(basePageEntity.getPage(), basePageEntity.getPageSize());
            list = userYearHeatMapper.findFeeLessByBuildingNo(id, minPriceValue , annual, userType,ignoreHasTask,basePageEntity);
        }


        return new PageInfo<>(list);
    }


    @Override
    public int batchUpdateHeatPriceByCommunityId(BatchAdjustHeatInfoVO batchAdjustHeatInfoVO) {
        return userYearHeatMapper.batchUpdateHeatPriceByCommunityId(batchAdjustHeatInfoVO);
    }

    @Override
    public int batchUpdateHeatTimeByCommunityId(BatchAdjustHeatInfoVO batchAdjustHeatInfoVO) {
        return userYearHeatMapper.batchUpdateHeatTimeByCommunityId(batchAdjustHeatInfoVO);
    }

    @Override
    public Integer getCountByAnnual(String companyId, String annual) {
        return userYearHeatMapper.getCountByAnnual(companyId, annual);
    }


    @Override
    public PageInfo<ChargeSumVO> getChargeSumByAnnual(String companyId, String annual, String order, String sortby, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<ChargeSumVO> chargeHouseDataVOs = null;
        chargeHouseDataVOs = userYearHeatMapper.getChargeSumByAnnual(companyId, annual, order, sortby, page, pageSize);
        PageInfo<ChargeSumVO> pageInfo = new PageInfo<>(chargeHouseDataVOs);
        return pageInfo;
    }


    @Override
    public PageInfo<ChargeHouseDataVO> getChargeHouseDataList(String stationIds, String commuityIds, String hasGenerated, String annual, String order, String sortby, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<String> stationIdlst = new ArrayList<>();
        List<String> commuityIdlst = new ArrayList<>();
        if (stationIds == null || stationIds.equals("")) {
            stationIdlst = null;
        } else {
            String[] split = stationIds.split(",");
            for (String s : split) {
                stationIdlst.add(s);
            }
        }
        if (commuityIds == null || commuityIds.equals("")) {
            commuityIdlst = null;
        } else {
            String[] split = commuityIds.split(",");
            for (String s : split) {
                commuityIdlst.add(s);
            }
        }

        List<ChargeHouseDataVO> chargeHouseDataVOs = null;
        if (hasGenerated == null || hasGenerated.equals("")) {

            chargeHouseDataVOs = userYearHeatMapper.getChargeHouseDataAllList(stationIdlst, commuityIdlst, hasGenerated, annual, order, sortby, page, pageSize);
        } else {
            chargeHouseDataVOs = userYearHeatMapper.getChargeHouseDataList(stationIdlst, commuityIdlst, hasGenerated, annual, order, sortby, page, pageSize);
        }
        PageInfo<ChargeHouseDataVO> pageInfo = new PageInfo<>(chargeHouseDataVOs);
        return pageInfo;
    }


    @Override
    public Integer selectCountByChargeType(String companyId, String annual, String chargeType) {
        return userYearHeatMapper.selectCountByChargeType(companyId, annual, chargeType);
    }

    @Override
    public Integer selectCountNotHeating(String companyId, String annual) {
        return userYearHeatMapper.selectCountNotHeating(companyId, annual);
    }


    @Override
    public int deleteByCompanyId(String companyId, String annual) {
        return userYearHeatMapper.deleteByCompanyId(companyId, annual);
    }

    @Override
    public int deleteByCommuityIds(List<String> commuityIds, String annual) {
        return userYearHeatMapper.deleteByCommuityIds(commuityIds, annual);
    }

    @Override
    public int deleteByStationIds(List<String> stationIds, String annual) {
        return userYearHeatMapper.deleteByStationIds(stationIds, annual);
    }

    @Override
    public int deleteByConsumerIds(List<String> consumerIds, String annual) {
        return userYearHeatMapper.deleteByConsumerIds(consumerIds, null, annual);
    }


//    @Override
//    public int insertForGenerated(String companyId, String annual,String lastannual, String createUser) {
//        return userYearHeatMapper.insertForGenerated(companyId, annual ,lastannual ,createUser);
//    }


    @Override
    public int insertForGenerated1(String companyId, String annual, String lastannual, String createUser) {
        return userYearHeatMapper.insertForGenerated1(companyId, annual, lastannual, createUser);
    }

    @Override
    public int insertForGenerated2(String companyId, String annual, String lastannual, String createUser) {
        return userYearHeatMapper.insertForGenerated2(companyId, annual, lastannual, createUser);
    }

    @Override
    public int insertForGenerated3(String companyId, String annual, String lastannual, String createUser) {
        return userYearHeatMapper.insertForGenerated3(companyId, annual, lastannual, createUser);
    }

    @Override
    public int insertForGenerated1ForOneStationAllCommunity(String stationId, String annual, String createUser) {
        return userYearHeatMapper.insertForGenerated1ForOneStationAllCommunity(stationId, annual, createUser);
    }


    @Override
    public int insertForGenerated2ForOneStationAllCommunity(String stationId, String annual, String createUser) {
        return userYearHeatMapper.insertForGenerated2ForOneStationAllCommunity(stationId, annual, createUser);
    }

    @Override
    public int insertForGenerated3ForOneStationAllCommunity(String stationId, String annual, String createUser) {
        return userYearHeatMapper.insertForGenerated3ForOneStationAllCommunity(stationId, annual, createUser);
    }

    @Override
    public int insertForGenerated1ForSelectedCommunity(List<String> commuityIds, String annual, String createUser) {
        return userYearHeatMapper.insertForGenerated1ForSelectedCommunity(commuityIds, annual, createUser);
    }

    @Override
    public int insertForGenerated2ForSelectedCommunity(List<String> commuityIds, String annual, String createUser) {
        return userYearHeatMapper.insertForGenerated2ForSelectedCommunity(commuityIds, annual, createUser);
    }


    @Override
    public int insertForGenerated3ForSelectedCommunity(List<String> commuityIds, String annual, String createUser) {
        return userYearHeatMapper.insertForGenerated3ForSelectedCommunity(commuityIds, annual, createUser);
    }


    @Override
    public int insertForGenerated1ForSelectedConsumer(List<String> consumerIds, String annual, String createUser) {
        return userYearHeatMapper.insertForGenerated1ForSelectedConsumer(consumerIds, annual, createUser);
    }


    @Override
    public int insertForGenerated2ForSelectedConsumer(List<String> consumerIds, String annual, String createUser) {
        return userYearHeatMapper.insertForGenerated2ForSelectedConsumer(consumerIds, annual, createUser);
    }

    @Override
    public int insertForGenerated3ForSelectedConsumer(List<String> consumerIds, String annual, String createUser) {
        return userYearHeatMapper.insertForGenerated3ForSelectedConsumer(consumerIds, annual, createUser);
    }


    @Override
    public int updateForGenerated2(String companyId, String annual) {
        return userYearHeatMapper.updateForGenerated2(companyId, annual);
    }

    @Override
    public int updateForGenerated3(String companyId, String annual, Date startTime) {
        return userYearHeatMapper.updateForGenerated3(companyId, annual, startTime);
    }

    @Override
    public int updateForGenerated4(String companyId, String annual, Date endTime) {
        return userYearHeatMapper.updateForGenerated4(companyId, annual, endTime);
    }


    @Override
    public int updateForGenerated2ForOneStationAllCommunity(String stationId, String annual) {
        return userYearHeatMapper.updateForGenerated2ForOneStationAllCommunity(stationId, annual);
    }

    @Override
    public int updateForGenerated3ForOneStationAllCommunity(String stationId, String annual, Date startTime) {
        return userYearHeatMapper.updateForGenerated3ForOneStationAllCommunity(stationId, annual, startTime);
    }

    @Override
    public int updateForGenerated4ForOneStationAllCommunity(String stationId, String annual, Date endTime) {
        return userYearHeatMapper.updateForGenerated4ForOneStationAllCommunity(stationId, annual, endTime);
    }


    @Override
    public int updateForGenerated2ForSelectedCommunity(List<String> commuityIds, String annual) {
        return userYearHeatMapper.updateForGenerated2ForSelectedCommunity(commuityIds, annual);
    }


    @Override
    public int updateForGenerated3ForSelectedCommunity(List<String> commuityIds, String annual, Date startTime) {
        return userYearHeatMapper.updateForGenerated3ForSelectedCommunity(commuityIds, annual, startTime);
    }

    @Override
    public int updateForGenerated4ForSelectedCommunity(List<String> commuityIds, String annual, Date endTime) {
        return userYearHeatMapper.updateForGenerated4ForSelectedCommunity(commuityIds, annual, endTime);
    }


    @Override
    public int updateForGenerated2ForSelectedConsumer(List<String> consumerIds, String annual) {
        return userYearHeatMapper.updateForGenerated2ForSelectedConsumer(consumerIds, annual);
    }

    @Override
    public int updateForGenerated3ForSelectedConsumer(List<String> consumerIds, String annual, Date startTime) {
        return userYearHeatMapper.updateForGenerated3ForSelectedConsumer(consumerIds, annual, startTime);
    }

    @Override
    public int updateForGenerated4ForSelectedConsumer(List<String> consumerIds, String annual, Date endTime) {
        return userYearHeatMapper.updateForGenerated4ForSelectedConsumer(consumerIds, annual, endTime);
    }

    @Override
    public PageInfo<HeatInfoVO> getHeatInfoList(String companyId, String areaPriceType, String chargeType, String heatUserType, String heatingStatus, String floorHigh, String order, String sortby, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<HeatInfoVO> heatInfos = userYearHeatMapper.getHeatInfoByCompanyId(companyId, areaPriceType, chargeType, heatUserType, heatingStatus, floorHigh, order, sortby, page, pageSize);
        PageInfo<HeatInfoVO> pageInfo = new PageInfo<>(heatInfos);
        return pageInfo;
    }


    @Override
    public List<HomePageTopLeftVO> getHomePageTopLeftChart(String companyId) {
        List<HomePageTopLeftVO> homePageTopLeftVOs = userYearHeatMapper.getHomePageTopLeftChart(companyId);
        return homePageTopLeftVOs;
    }


    @Override
    public HomePageTopMiddleVO getHomePageTopMiddleChart(String companyId) {
        HomePageTopMiddleVO homePageTopMiddleVOs = userYearHeatMapper.getHomePageTopMiddleChart(companyId);
        return homePageTopMiddleVOs;
    }

    @Override
    public List<HomePageTopRightVO> getHomePageTopRightChart(String companyId) {
        List<HomePageTopRightVO> homePageTopRightVOs = userYearHeatMapper.getHomePageTopRightChart(companyId);
        return homePageTopRightVOs;
    }


    @Override
    public List<HomePageMiddleLeftVO> getHomePageMiddleLeftChart(String companyId, String startAnnual, String endAnnual) {
        List<HomePageMiddleLeftVO> homePageMiddleLeftVOs = userYearHeatMapper.getHomePageMiddleLeftChart(companyId, startAnnual, endAnnual);
        return homePageMiddleLeftVOs;
    }

    @Override
    public List<HomePageMiddleMiddleVO> getHomePageMiddleMiddleChart(String companyId) {
        List<HomePageMiddleMiddleVO> homePageMiddleMiddleVOs = userYearHeatMapper.getHomePageMiddleMiddleChart(companyId);
        return homePageMiddleMiddleVOs;
    }

    @Override
    public List<HomePageMiddleRightVO> getHomePageMiddleRightChart(String companyId) {
        List<HomePageMiddleRightVO> homePageMiddleRightInnerVOs = userYearHeatMapper.getHomePageMiddleRightChart(companyId);
        return homePageMiddleRightInnerVOs;
    }


    @Override
    public List<HomePageBottomLeftVO> getHomePageBottomLeftChart(String companyId, String startAnnual, String endAnnual) {
        List<HomePageBottomLeftVO> homePageBottomLeftVOs = userYearHeatMapper.getHomePageBottomLeftChart(companyId, startAnnual, endAnnual);
        return homePageBottomLeftVOs;
    }


    @Override
    public List<HomePageBottomMiddleVO> getHomePageBottomMiddleChart(String companyId) {
        List<HomePageBottomMiddleVO> homePageBottomMiddleVOs = userYearHeatMapper.getHomePageBottomMiddleChart(companyId);
        return homePageBottomMiddleVOs;
    }

    @Override
    public List<HomePageBottomRightVO> getHomePageBottomRightChart(String companyId, Date startTime, Date endTime) {
        List<HomePageBottomRightVO> homePageBottomRightVOs = userYearHeatMapper.getHomePageBottomRightChart(companyId, startTime, endTime);
        return homePageBottomRightVOs;
    }

    /**
     * @Description: 统计分析-供热汇总
     * @Param: [heatingSummarySearchDTO]
     * @Return: cn.plou.web.charge.chargeconfig.vo.HeatingSummaryVO
     * @Author: youbc
     * @Date: 18/10/18 15下午08
     */
    @Override
    public HeatingSummaryVO heatingSummary(HeatingSummarySearchDTO heatingSummarySearchDTO) {
        HeatingSummaryCountVO vo;
        List<HeatingSummaryListVO> list;
        int i = checkStatisticalObject(heatingSummarySearchDTO);
        if (i == 20) { // 按换热站统计，右侧可选：公司
            vo = sumData(userYearHeatMapper.heatingSummary20(heatingSummarySearchDTO));
            PageHelper.startPage(heatingSummarySearchDTO.getPage(), heatingSummarySearchDTO.getPageSize());
            list = userYearHeatMapper.heatingSummary20(heatingSummarySearchDTO);
        } else if (i == 30) { // 按小区统计，右侧可选：公司、换热站
            vo = sumData(userYearHeatMapper.heatingSummary30(heatingSummarySearchDTO));
            PageHelper.startPage(heatingSummarySearchDTO.getPage(), heatingSummarySearchDTO.getPageSize());
            list = userYearHeatMapper.heatingSummary30(heatingSummarySearchDTO);
        } else if (i == 40) { // 按楼统计，右侧可选：公司、换热站、小区
            vo = sumData(userYearHeatMapper.heatingSummary40(heatingSummarySearchDTO));
            PageHelper.startPage(heatingSummarySearchDTO.getPage(), heatingSummarySearchDTO.getPageSize());
            list = userYearHeatMapper.heatingSummary40(heatingSummarySearchDTO);
        } else if (i == 50) { // 按用户统计，右侧可选：公司、换热站、小区、楼
            vo = sumData(userYearHeatMapper.heatingSummary50(heatingSummarySearchDTO));
            PageHelper.startPage(heatingSummarySearchDTO.getPage(), heatingSummarySearchDTO.getPageSize());
            list = userYearHeatMapper.heatingSummary50(heatingSummarySearchDTO);
        } else {
            throw new BusinessException("请选择正确的统计对象");
        }

        HeatingSummaryVO result = new HeatingSummaryVO();
        result.setCount(vo);
        result.setList(list);
        return result;
    }

    /**
     * @Description: 统计分析-供热汇总（计算合计）
     * @Param: [list]
     * @Return: cn.plou.web.charge.chargeconfig.vo.HeatingSummaryCountVO
     * @Author: youbc
     * @Date: 18/10/19 15下午49
     */
    private HeatingSummaryCountVO sumData(List<HeatingSummaryListVO> list) {
        BigDecimal areaTotal = BigDecimal.ZERO;
        int numTotal = 0;
        BigDecimal areaHeat = BigDecimal.ZERO;
        int numHeat = 0;
        BigDecimal areaNotHeat = BigDecimal.ZERO;
        int numNotHeat = 0;
        BigDecimal areaApplyHeat = BigDecimal.ZERO;
        int numApplyHeat = 0;
        BigDecimal areaApplyNotHeat = BigDecimal.ZERO;
        int numApplyNotHeat = 0;
        for (HeatingSummaryListVO vo : list) {
            areaTotal = areaTotal.add(vo.getAreaTotal());
            numTotal = numTotal + vo.getNumTotal();
            areaHeat = areaHeat.add(vo.getAreaHeat());
            numHeat = numHeat + vo.getNumHeat();
            areaNotHeat = areaNotHeat.add(vo.getAreaNotHeat());
            numNotHeat = numNotHeat + vo.getNumNotHeat();
            areaApplyHeat = areaApplyHeat.add(vo.getAreaApplyHeat());
            numApplyHeat = numApplyHeat + vo.getNumApplyHeat();
            areaApplyNotHeat = areaApplyNotHeat.add(vo.getAreaApplyNotHeat());
            numApplyNotHeat = numApplyNotHeat + vo.getNumApplyNotHeat();
        }
        HeatingSummaryCountVO result = new HeatingSummaryCountVO();
        result.setAreaTotal(areaTotal);
        result.setNumTotal(numTotal);
        result.setAreaHeat(areaHeat);
        result.setNumHeat(numHeat);
        result.setAreaNotHeat(areaNotHeat);
        result.setNumNotHeat(numNotHeat);
        result.setInRatio(numTotal == 0 ? BigDecimal.ZERO : (new BigDecimal(numHeat).multiply(new BigDecimal(100)).divide(new BigDecimal(numTotal), 2, BigDecimal.ROUND_HALF_UP)));
        result.setAreaApplyHeat(areaApplyHeat);
        result.setNumApplyHeat(numApplyHeat);
        result.setAreaApplyNotHeat(areaApplyNotHeat);
        result.setNumApplyNotHeat(numApplyNotHeat);

        return result;
    }

    /**
     * @Description: 检查选择的统计对象是否正确
     * @Param: [heatingSummarySearchDTO]
     * @Return: void
     * @Author: youbc
     * @Date: 18/10/18 15下午33
     */
    private int checkStatisticalObject(HeatingSummarySearchDTO heatingSummarySearchDTO) {
        String statisticalObject = heatingSummarySearchDTO.getStatisticalObject();
        List<TypeMst> typeMstList = typeMstMapper.selectDownInfoByTypeKbn("statistical_object");
        boolean hasType = false;
        int orderByDb = 0; // 按什么统计
        for (TypeMst typeMst : typeMstList) {
            if (StringUtils.equalsIgnoreCase(typeMst.getTypeId(), statisticalObject)) {
                orderByDb = Integer.parseInt(typeMst.getOrderBy());
                hasType = true;
                break;
            }
        }

        if (!hasType) {
            throw new BusinessException("请选择正确的统计对象");
        } else {
            int length = heatingSummarySearchDTO.getRangeId().length();
            int orderByFront; // 右侧选择的范围
            if (length == 5) { // 公司
                orderByFront = 10;
            } else if (length == 6) { // 换热站
                orderByFront = 20;
            } else if (length == 10) { // 小区
                orderByFront = 30;
            } else if (length == 13) { // 楼
                orderByFront = 40;
            } else {
                throw new BusinessException("请选择正确的范围（公司、站、小区等）");
            }

            if (orderByDb <= orderByFront) {
                throw new BusinessException("只能对所选范围（公司、站、小区等）内部的对象进行数据统计");
            }
            return orderByDb;
        }
    }


}

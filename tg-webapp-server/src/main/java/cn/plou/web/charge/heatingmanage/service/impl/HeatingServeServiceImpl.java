package cn.plou.web.charge.heatingmanage.service.impl;

import cn.plou.web.charge.chargeconfig.controller.CostManagementController;
import cn.plou.web.charge.chargeconfig.controller.DataInitController;
import cn.plou.web.charge.chargeconfig.dao.PriceDefineMapper;
import cn.plou.web.charge.chargeconfig.dao.UserYearHeatMapper;
import cn.plou.web.charge.chargeconfig.dao.UserYearReceivableDetailMapper;
import cn.plou.web.charge.chargeconfig.entity.UserYearHeat;
import cn.plou.web.charge.chargeconfig.util.MoneyConverter;
import cn.plou.web.charge.heatingmanage.dao.HeatCheckDetailMapper;
import cn.plou.web.charge.heatingmanage.dao.HouseYearHeatstateDetailMapper;
import cn.plou.web.charge.heatingmanage.domain.HeatCheckDetail;
import cn.plou.web.charge.heatingmanage.domain.HouseYearHeatstateDetail;
import cn.plou.web.charge.heatingmanage.domain.ServeResultByType;
import cn.plou.web.charge.heatingmanage.dto.*;
import cn.plou.web.charge.heatingmanage.service.HeatingServeService;
import cn.plou.web.charge.heatingmanage.vo.HeatingServeDepartmentVO;
import cn.plou.web.charge.heatingmanage.vo.HeatingServeListVO;
import cn.plou.web.charge.heatingmanage.vo.HeatingServeUserListVO;
import cn.plou.web.charge.heatingmanage.vo.HeatingServeVO;
import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.utils.FlowIdTool;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.common.utils.a1.DateUtil;
import cn.plou.web.system.baseMessage.commuity.dao.CommuityMapper;
import cn.plou.web.system.baseMessage.house.dao.HouseMapper;
import cn.plou.web.system.baseMessage.house.entity.House;
import cn.plou.web.system.commonMessage.typeMst.dao.TypeMstMapper;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.organizationMessage.department.dao.DepartmentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: HeatingServeServiceImpl
 * @Description: 供热服务
 * @Author: youbc
 * @Date 2018-08-15 16:07
 */
@Service
public class HeatingServeServiceImpl implements HeatingServeService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HouseYearHeatstateDetailMapper houseYearHeatstateDetailMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private PriceDefineMapper priceDefineMapper;

    @Autowired
    private UserYearHeatMapper userYearHeatMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private TypeMstMapper typeMstMapper;

    @Autowired
    private HeatCheckDetailMapper heatCheckDetailMapper;


    @Resource
    private CommuityMapper commuityMapper;

    @Autowired
    private UserYearReceivableDetailMapper userYearReceivableDetailMapper;

    @Autowired
    private CostManagementController costManagementController;

    @Autowired
    private DataInitController dataInitController;

    private static final String TASK_TYPE_1 = "task_type_1"; // 申请供暖
    private static final String TASK_TYPE_2 = "task_type_2"; // 申请停暖
    private static final String TASK_TYPE_3 = "task_type_3"; // 强制停暖

    private static final String HEATING_STATUS_1 = "heating_status_1"; // 供暖
    private static final String HEATING_STATUS_2 = "heating_status_2"; // 停暖

    private static final String NET_IN = "net_status_1"; // 入网状态-入网

    /**
     * @Description: 服务申请
     * @Param: [serviceApplicationDTO]
     * @Return: void
     * @Author: youbc
     * @Date: 2018/8/21 8上午31
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void serviceApplication(ServiceApplicationDTO serviceApplicationDTO) {
        String primaryId = serviceApplicationDTO.getPrimaryId();
        String taskTypeFront = serviceApplicationDTO.getTaskType();
        Date date = new Date();
        String userId = UserUtils.getUserId();

        String consumerId = serviceApplicationDTO.getConsumerId();
        House house = houseMapper.selectByPrimaryKey(consumerId);
        if (house == null) {
            logger.error("用户基本信息获取失败！");
            throw new BusinessException("用户基本信息获取失败！");
        }
        boolean netIn = StringUtils.equalsIgnoreCase(NET_IN, house.getNetStatus());
        if (!netIn) {
            logger.error("用户（" + house.getName() + "）尚未入网，不能办理该业务！");
            throw new BusinessException("用户（" + house.getName() + "）尚未入网，不能办理该业务！");
        }

        checkTaskType(serviceApplicationDTO, house);

        if (StringUtils.isEmpty(primaryId)) {
            String apprTimestamp = DateUtil.toDateTimeString(date, DateUtil.TIMESTAMP_FORMAT);
            String companyId = house.getCompanyId();
            String annual = priceDefineMapper.findCurrentHeatAnnual(companyId);
            if (annual == null) {
                logger.error("当前供暖季获取失败！");
                throw new BusinessException("当前供暖季获取失败！");
            }

            TypeMst typeMst = typeMstMapper.getByTypeKbnAndTypeName(Constant.GLOBAL_BUSINESS_TYPE, Constant.GBT_HEATING_SERVE);
            if (typeMst == null) {
                logger.error("业务类型获取失败！");
                throw new BusinessException("业务类型获取失败！");
            }
            String typeId = typeMst.getTypeId();
            String flowId = FlowIdTool.GetFlowId(companyId, typeId);

            HouseYearHeatstateDetail houseYearHeatstateDetail = new HouseYearHeatstateDetail();
            houseYearHeatstateDetail.setPrimaryId(consumerId + apprTimestamp);
            houseYearHeatstateDetail.setConsumerId(consumerId);
            houseYearHeatstateDetail.setFlowId(flowId);
            houseYearHeatstateDetail.setAnnual(annual);
            houseYearHeatstateDetail.setCompanyId(companyId);
            houseYearHeatstateDetail.setApprPerson(serviceApplicationDTO.getApprPerson());
            houseYearHeatstateDetail.setApprTime(date);
            houseYearHeatstateDetail.setApprTelephone(serviceApplicationDTO.getApprTelephone());
            houseYearHeatstateDetail.setTaskType(taskTypeFront);
            houseYearHeatstateDetail.setTaskContect(serviceApplicationDTO.getTaskContect());
            houseYearHeatstateDetail.setHandler(userId);
            houseYearHeatstateDetail.setEmerge(serviceApplicationDTO.getEmerge());
            houseYearHeatstateDetail.setOvertime(serviceApplicationDTO.getOvertime());
            houseYearHeatstateDetail.setCreateDate(date);
            houseYearHeatstateDetail.setCreateUser(userId);
            houseYearHeatstateDetail.setUpdateDate(date);
            houseYearHeatstateDetail.setUpdateUser(userId);
            houseYearHeatstateDetail.setDiameter(house.getDiameter());
            houseYearHeatstateDetail.setControlType(house.getControlType());
            houseYearHeatstateDetail.setAppointTime(serviceApplicationDTO.getAppointTime());

            int insert = houseYearHeatstateDetailMapper.insert(houseYearHeatstateDetail);
            if (insert != 1) {
                logger.error("供热服务申请失败，insert = " + insert);
                throw new BusinessException("供热服务申请失败！");
            }

            changeHeatingStatus(date, userId, houseYearHeatstateDetail);
        } else {
            HouseYearHeatstateDetail houseYearHeatstateDetail = houseYearHeatstateDetailMapper.selectByPrimaryKey(primaryId);
            if (houseYearHeatstateDetail == null) {
                logger.error("用户年度供暖管理明细获取失败！");
                throw new BusinessException("用户年度供暖管理明细获取失败！");
            }
            String taskTypeDb = houseYearHeatstateDetail.getTaskType();

            houseYearHeatstateDetail.setApprPerson(serviceApplicationDTO.getApprPerson());
            houseYearHeatstateDetail.setApprTelephone(serviceApplicationDTO.getApprTelephone());
            houseYearHeatstateDetail.setTaskType(taskTypeFront);
            houseYearHeatstateDetail.setEmerge(serviceApplicationDTO.getEmerge());
            houseYearHeatstateDetail.setAppointTime(serviceApplicationDTO.getAppointTime());
            houseYearHeatstateDetail.setOvertime(serviceApplicationDTO.getOvertime());
            houseYearHeatstateDetail.setTaskContect(serviceApplicationDTO.getTaskContect());
            houseYearHeatstateDetail.setUpdateDate(date);
            houseYearHeatstateDetail.setUpdateUser(UserUtils.getUserId());

            int update = houseYearHeatstateDetailMapper.updateByPrimaryKey(houseYearHeatstateDetail);
            if (update != 1) {
                logger.error("供热服务申请失败，update = " + update);
                throw new BusinessException("供热服务申请失败！");
            }

            if (!StringUtils.equalsIgnoreCase(taskTypeDb, taskTypeFront)) {
                changeHeatingStatus(date, userId, houseYearHeatstateDetail);
            }
        }
    }

    /**
     * @Description: 服务缴费
     * @Param: [servicePaymentDTO]
     * @Return: void
     * @Author: youbc
     * @Date: 2018/8/21 8上午31
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void servicePayment(ServicePaymentDTO servicePaymentDTO) {
        String primaryId = servicePaymentDTO.getPrimaryId();
        HouseYearHeatstateDetail houseYearHeatstateDetail = houseYearHeatstateDetailMapper.selectByPrimaryKey(primaryId);
        if (houseYearHeatstateDetail == null) {
            logger.error("用户年度供暖管理明细获取失败！");
            throw new BusinessException("用户年度供暖管理明细获取失败！");
        }
        BigDecimal singleCost = servicePaymentDTO.getSingleCost();
        BigDecimal nums = servicePaymentDTO.getNums();

        houseYearHeatstateDetail.setCostType(servicePaymentDTO.getCostType());
        houseYearHeatstateDetail.setSingleCost(singleCost);
        houseYearHeatstateDetail.setNums(nums);
        houseYearHeatstateDetail.setTotalCost(singleCost.multiply(nums));
        houseYearHeatstateDetail.setActCost(servicePaymentDTO.getActCost());
        houseYearHeatstateDetail.setPayChannel(servicePaymentDTO.getPayChannel());
        houseYearHeatstateDetail.setBillno(servicePaymentDTO.getBillno());
        houseYearHeatstateDetail.setPrintFlag(servicePaymentDTO.getPrintFlag());
        houseYearHeatstateDetail.setReceiptno(servicePaymentDTO.getReceiptno());
        houseYearHeatstateDetail.setUpdateDate(new Date());
        houseYearHeatstateDetail.setUpdateUser(UserUtils.getUserId());

        int update = houseYearHeatstateDetailMapper.updateByPrimaryKey(houseYearHeatstateDetail);
        if (update != 1) {
            logger.error("供热服务缴费失败，update = " + update);
            throw new BusinessException("供热服务缴费失败！");
        }
    }

    /**
     * @Description: 服务反馈
     * @Param: [serviceFeedbackDTO]
     * @Return: void
     * @Author: youbc
     * @Date: 2018/8/21 9上午24
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void serviceFeedback(ServiceFeedbackDTO serviceFeedbackDTO) {
        String primaryId = serviceFeedbackDTO.getPrimaryId();
        HouseYearHeatstateDetail houseYearHeatstateDetail = houseYearHeatstateDetailMapper.selectByPrimaryKey(primaryId);
        if (houseYearHeatstateDetail == null) {
            logger.error("用户年度供暖管理明细获取失败！");
            throw new BusinessException("用户年度供暖管理明细获取失败！");
        }
        House house = houseMapper.selectByPrimaryKey(houseYearHeatstateDetail.getConsumerId());
        if (house == null) {
            logger.error("用户基本信息获取失败！");
            throw new BusinessException("用户基本信息获取失败！");
        }

        Date date = new Date();
        String userId = UserUtils.getUserId();
        String endState = serviceFeedbackDTO.getEndState();

        houseYearHeatstateDetail.setExecutePerson(serviceFeedbackDTO.getExecutePerson());
        houseYearHeatstateDetail.setExecuteTime(serviceFeedbackDTO.getExecuteTime());
        houseYearHeatstateDetail.setEndState(endState);
        houseYearHeatstateDetail.setFeedbackContect(serviceFeedbackDTO.getFeedbackContect());
        houseYearHeatstateDetail.setHouseView(serviceFeedbackDTO.getHouseView());
        houseYearHeatstateDetail.setHouseSign(house.getName());
        houseYearHeatstateDetail.setVisitPerson(serviceFeedbackDTO.getVisitPerson());
        houseYearHeatstateDetail.setVisitTime(serviceFeedbackDTO.getVisitTime());
        houseYearHeatstateDetail.setSatisfy(serviceFeedbackDTO.getSatisfy());
        houseYearHeatstateDetail.setVisitContect(serviceFeedbackDTO.getVisitContect());
        houseYearHeatstateDetail.setNotes(serviceFeedbackDTO.getNotes());
        houseYearHeatstateDetail.setUpdateDate(date);
        houseYearHeatstateDetail.setUpdateUser(userId);

        int update = houseYearHeatstateDetailMapper.updateByPrimaryKey(houseYearHeatstateDetail);
        if (update != 1) {
            logger.error("供热服务反馈失败，update = " + update);
            throw new BusinessException("供热服务反馈失败！");
        }
    }

    /**
     * @Description: 修改其他表的供暖状态
     * 用户基本信息：更新【供暖状态】
     * <p>
     * 用户年度采暖费用明细表【user_year_receivable_detail】：
     * 1、申请供：重新生成（直接插入 N 条）
     * 2、申请停：做一笔负的进行冲正（汇总后插入 1 条负的）
     * <p>
     * 用户年度采暖信息表【user_year_heat】：由于【用户年度采暖费用明细表】变动，需更新本表中的某些字段（逻辑已有）；更新【供暖状态】
     * <p>
     * 供热稽查明细表【heat_check_detail】：更新【供暖状态】
     * @Param: [date, userId, taskType]
     * @Return: void
     * @Author: youbc
     * @Date: 2018/9/10 15下午00
     */
    private void changeHeatingStatus(Date date, String userId, HouseYearHeatstateDetail houseYearHeatstateDetail) {
        String consumerId = houseYearHeatstateDetail.getConsumerId();
        String taskType = houseYearHeatstateDetail.getTaskType();
        // 用户基本信息
        House house = new House();
        house.setConsumerId(consumerId);
        house.setHeatingStatus(getHeatingStatus(taskType));
        house.setUpdateDate(date);
        house.setUpdateUser(userId);
        int updateHouse = houseMapper.updateByPrimaryKeySelective(house);
        if (updateHouse > 1) {
            logger.error("用户基本信息更新失败，updateHouse = " + updateHouse);
            throw new BusinessException("用户基本信息更新失败！");
        }
        // 用户年度采暖费用明细表
        String annual = houseYearHeatstateDetail.getAnnual();
        String companyId = houseYearHeatstateDetail.getCompanyId();
        if (StringUtils.equalsIgnoreCase(taskType, TASK_TYPE_1)) { // 申请供暖
            dataInitController.resetAndGenerateByCheck(companyId, null, consumerId, annual);
        } else if (StringUtils.equalsIgnoreCase(taskType, TASK_TYPE_2)) { // 申请停暖
            dataInitController.stopHeat(consumerId, annual);
        } else if (StringUtils.equalsIgnoreCase(taskType, TASK_TYPE_3)) { // 强制停暖
            logger.info("强制停暖：" + house.getName());
        } else {
            logger.error("任务类型不正确！taskType：" + taskType);
            throw new BusinessException("任务类型不正确！");
        }
        // 用户年度采暖信息表
        UserYearHeat userYearHeat = new UserYearHeat();
        userYearHeat.setConsumerId(consumerId);
        userYearHeat.setCompanyId(companyId);
        userYearHeat.setAnnual(annual);
        userYearHeat.setHeatingStatus(getHeatingStatus(taskType));
        userYearHeat.setUpdateDate(date);
        userYearHeat.setUpdateUser(userId);
        int updateUserYearHeat = userYearHeatMapper.updateHeatingStatus(userYearHeat);
        if (updateUserYearHeat != 1) {
            logger.error("用户年度采暖信息更新失败，updateUserYearHeat = " + updateUserYearHeat);
            throw new BusinessException("用户年度采暖信息更新失败！");
        }
        // 供热稽查明细表
        List<HeatCheckDetail> heatCheckDetails = heatCheckDetailMapper.findByConsumerIdAndCompanyIdAndAnnual(consumerId, companyId, annual);
        int size = heatCheckDetails.size();
        if (size > 0) {
            HeatCheckDetail heatCheckDetail = new HeatCheckDetail();
            heatCheckDetail.setConsumerId(consumerId);
            heatCheckDetail.setCompanyId(companyId);
            heatCheckDetail.setAnnual(annual);
            heatCheckDetail.setHeatingStatus(getHeatingStatus(taskType));
            heatCheckDetail.setUpdateUser(userId);
            heatCheckDetail.setUpdateDate(date);
            int updateHeatCheckDetail = heatCheckDetailMapper.updateHeatingStatus(heatCheckDetail);
            if (updateHeatCheckDetail != size) {
                logger.error("供热稽查明细更新失败，updateHeatCheckDetail = " + updateHeatCheckDetail + "，list.size = " + size);
                throw new BusinessException("供热稽查明细更新失败！");
            }
        }
    }

    /**
     * @Description: 根据申请类型返回供暖状态
     * @Param: [taskType]
     * @Return: java.lang.String
     * @Author: youbc
     * @Date: 2018/9/27 17下午16
     */
    private String getHeatingStatus(String taskType) {
        if (StringUtils.equalsIgnoreCase(taskType, TASK_TYPE_1)) { // 申请供暖
            return HEATING_STATUS_1;
        } else if (StringUtils.equalsIgnoreCase(taskType, TASK_TYPE_2)) { // 申请停暖
            return HEATING_STATUS_2;
        } else if (StringUtils.equalsIgnoreCase(taskType, TASK_TYPE_3)) { // 强制停暖
            return HEATING_STATUS_2;
        } else {
            logger.error("任务类型不正确！taskType：" + taskType);
            throw new BusinessException("任务类型不正确！");
        }
    }

    /**
     * @Description: 供热服务弹框内容
     * @Param: [primaryId]
     * @Return: cn.plou.web.charge.heatingmanage.vo.HeatingServeVO
     * @Author: youbc
     * @Date: 2018/8/21 16下午38
     */
    @Override
    public HeatingServeVO get(String primaryId) {
        HeatingServeVO vo = houseYearHeatstateDetailMapper.get(primaryId);
        String companyId = vo.getCompanyId();
        BigDecimal totalCost = vo.getTotalCost();
        vo.setTotalCostCash(MoneyConverter.Convert(companyId, totalCost, "1"));
        vo.setTotalCostNotCash(MoneyConverter.Convert(companyId, totalCost, "2"));
        if (vo.getReceiptno() == null) { // null 代表没生成，空字符串代表选择的不开收据
            vo.setReceiptno(FlowIdTool.GetReceiptNo(companyId));
        }
        return vo;
    }

    /**
     * @Description: 供热服务列表
     * @Param: [heatingServeSearchDTO]
     * @Return: java.util.List<cn.plou.web.charge.heatingmanage.vo.HeatingServeListVO>
     * @Author: youbc
     * @Date: 2018/8/22 15下午59
     */
    @Override
    public PageInfo<HeatingServeListVO> list(HeatingServeSearchDTO heatingServeSearchDTO) {
        String rangeId = heatingServeSearchDTO.getRangeId();
        List<HeatingServeListVO> list = new ArrayList<>();
        if (rangeId.length() == Constant.STATION_ID_LENGTH) {//说明是站
            List<String> commuityIds = commuityMapper.selectCommuityIdsByStationId(rangeId);
            if (commuityIds.size() > 0) {
                PageHelper.startPage(heatingServeSearchDTO.getPage(), heatingServeSearchDTO.getPageSize());
                list = houseYearHeatstateDetailMapper.getListBySearchOfStation(heatingServeSearchDTO, commuityIds);
            }
        } else {
            PageHelper.startPage(heatingServeSearchDTO.getPage(), heatingServeSearchDTO.getPageSize());
            list = houseYearHeatstateDetailMapper.getListBySearch(heatingServeSearchDTO);
        }
        return new PageInfo<HeatingServeListVO>(list);
    }


    /**
     * @Description: 供热服务用户列表
     * @Param: [heatingServeUserSearchDTO]
     * @Return: java.util.List<cn.plou.web.charge.heatingmanage.vo.HeatingServeUserListVO>
     * @Author: youbc
     * @Date: 2018/8/22 15下午58
     */
    @Override
    public List<HeatingServeUserListVO> userList(HeatingServeUserSearchDTO heatingServeUserSearchDTO) {
        return houseMapper.selectByRangeId(heatingServeUserSearchDTO);
    }

    /**
     * @Description: 供热服务用户列表-总数
     * @Param: [heatingServeUserSearchDTO]
     * @Return: java.lang.Integer
     * @Author: youbc
     * @Date: 2018/8/28 16下午11
     */
    @Override
    public Integer userListCount(HeatingServeUserSearchDTO heatingServeUserSearchDTO) {
        return houseMapper.selectCountByRangeId(heatingServeUserSearchDTO);
    }

    @Override
    public List<HeatingServeUserListVO> userListOfStation(HeatingServeUserSearchDTO heatingServeUserSearchDTO, List<String> ids) {
        return houseMapper.selectByRangeIdOfStation(heatingServeUserSearchDTO, ids);
    }

    @Override
    public Integer userListCountOfStation(HeatingServeUserSearchDTO heatingServeUserSearchDTO, List<String> ids) {
        return houseMapper.selectCountByRangeIdOfStation(heatingServeUserSearchDTO, ids);
    }

    @Override
    public      List  <ServeResultByType>  getTotalResultByType(HeatingServeSearchDTO heatingServeSearchDTO) {
        String rangeId = heatingServeSearchDTO.getRangeId();
        List <ServeResultByType> list = new ArrayList<>();
        if (rangeId.length() == Constant.STATION_ID_LENGTH) {//说明是站
            List<String> commuityIds = commuityMapper.selectCommuityIdsByStationId(rangeId);
            if (commuityIds.size() > 0) {
                list = houseYearHeatstateDetailMapper.getTotalResultOfStationByType( heatingServeSearchDTO, commuityIds);
            }
        } else {
            list = houseYearHeatstateDetailMapper.getTotalResultOfNormalByType(heatingServeSearchDTO);
        }
        return list;
    }

    @Override
    public Integer getCountByendState(HeatingServeSearchDTO heatingServeSearchDTO) {
        String rangeId = heatingServeSearchDTO.getRangeId();
        Integer  count = 0;
        if (rangeId.length() == Constant.STATION_ID_LENGTH) {//说明是站
            List<String> commuityIds = commuityMapper.selectCommuityIdsByStationId(rangeId);
            if (commuityIds.size() > 0) {
                count = houseYearHeatstateDetailMapper.getCountOfStationByendState( heatingServeSearchDTO, commuityIds);
            }
        } else {
            count = houseYearHeatstateDetailMapper.getCountOfNormalByendState(heatingServeSearchDTO);
        }
        return count;
    }

    /**
     * @Description: 执行人、回访人下拉框
     * @Param: [companyId]
     * @Return: java.util.List<cn.plou.web.charge.heatingmanage.vo.HeatingServeDepartmentVO>
     * @Author: youbc
     * @Date: 2018/8/23 8上午40
     */
    @Override
    public List<HeatingServeDepartmentVO> getUsers(String companyId) {
        return departmentMapper.selectDepartmentStaff(companyId);
    }

    /**
     * @Description: 检测任务类型
     * @Param: [serviceApplicationDTO]
     * @Return: void
     * @Author: youbc
     * @Date: 2018年9月27日17:03:12
     */
    private void checkTaskType(ServiceApplicationDTO serviceApplicationDTO, House house) {
        String heatingStatus = house.getHeatingStatus();
        String taskType = serviceApplicationDTO.getTaskType();
        if (HEATING_STATUS_1.equalsIgnoreCase(heatingStatus) && TASK_TYPE_1.equalsIgnoreCase(taskType)) {
            logger.error("用户已是供暖状态，不能再次申请供暖！");
            throw new BusinessException("用户已是供暖状态，不能再次申请供暖！");
        }
        if (HEATING_STATUS_2.equalsIgnoreCase(heatingStatus) && TASK_TYPE_2.equalsIgnoreCase(taskType)) {
            logger.error("用户已是停暖状态，不能再次申请停暖！");
            throw new BusinessException("用户已是停暖状态，不能再次申请停暖！");
        }
        if (HEATING_STATUS_2.equalsIgnoreCase(heatingStatus) && TASK_TYPE_3.equalsIgnoreCase(taskType)) {
            logger.error("用户已是停暖状态，不能进行强制停暖！");
            throw new BusinessException("用户已是停暖状态，不能进行强制停暖！");
        }
    }
}

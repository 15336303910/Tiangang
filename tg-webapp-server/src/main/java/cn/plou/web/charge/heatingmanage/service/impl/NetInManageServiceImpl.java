package cn.plou.web.charge.heatingmanage.service.impl;

import cn.plou.web.charge.chargeconfig.util.MoneyConverter;
import cn.plou.web.charge.heatingmanage.dao.ContractHeatInfoMapper;
import cn.plou.web.charge.heatingmanage.dao.ContractHeatInfoTmpMapper;
import cn.plou.web.charge.heatingmanage.dao.ContractHeatMoneyDetailMapper;
import cn.plou.web.charge.heatingmanage.domain.ContractHeatInfo;
import cn.plou.web.charge.heatingmanage.domain.ContractHeatInfoTmp;
import cn.plou.web.charge.heatingmanage.domain.ContractHeatMoneyDetail;
import cn.plou.web.charge.heatingmanage.dto.ApproveContractApprovesDTO;
import cn.plou.web.charge.heatingmanage.dto.ContractAddDTO;
import cn.plou.web.charge.heatingmanage.dto.NetInManageSearchDTO;
import cn.plou.web.charge.heatingmanage.dto.NetInPayDTO;
import cn.plou.web.charge.heatingmanage.service.NetInManageService;
import cn.plou.web.charge.heatingmanage.vo.ContractHeatListVO;
import cn.plou.web.charge.heatingmanage.vo.NetInPayVO;
import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.utils.FlowIdTool;
import cn.plou.web.common.utils.IDWorker;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.commonMessage.typeMst.dao.TypeMstMapper;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: HeatingServeServiceImpl
 * @Description: 入网管理
 * @Author: youbc
 * @Date 2018-8-27 09:32:01
 */
@Service
public class NetInManageServiceImpl implements NetInManageService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String APPROVE_TYPE_UPDATE = "1"; // 审核类型：内容更新
    private static final String APPROVE_TYPE_DELETE = "2"; // 审核类型：合同删除

    private static final String APPROVE_RES_YES = "approve_res_1"; // 审批结果：通过
    private static final String APPROVE_RES_NO = "approve_res_2"; // 审批结果：未通过


    @Autowired
    private ContractHeatInfoMapper contractHeatInfoMapper;

    @Autowired
    private ContractHeatInfoTmpMapper contractHeatInfoTmpMapper;

    @Autowired
    private TypeMstMapper typeMstMapper;

    @Autowired
    private ContractHeatMoneyDetailMapper contractHeatMoneyDetailMapper;

    /**
     * @Description: 新增/更新合同
     * @Param: [contractAddDTO]
     * @Return: void
     * @Author: youbc
     * @Date: 2018/8/27 9上午36
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auContract(ContractAddDTO contractAddDTO) {
        String userId = UserUtils.getUserId();
        Date date = new Date();
        BigDecimal area = contractAddDTO.getArea();
        BigDecimal unitPrice = contractAddDTO.getUnitPrice();
        BigDecimal total = (area.multiply(unitPrice)).setScale(3, BigDecimal.ROUND_HALF_UP);
        BigDecimal priceAdj = contractAddDTO.getPriceAdj();
        if (priceAdj == null) {
            priceAdj = BigDecimal.ZERO;
        }

        String primaryId = contractAddDTO.getPrimaryId();
        if (StringUtils.isEmpty(primaryId)) {
            String contractCode = contractAddDTO.getContractCode();
            int count = contractHeatInfoMapper.countByContractCode(contractCode);
            if (count != 0) {
                logger.error("合同编号已存在：" + contractCode);
                throw new BusinessException("合同编号已存在！");
            }

            ContractHeatInfo contractHeatInfo = new ContractHeatInfo();
            contractHeatInfo.setPrimaryId(IDWorker.uuid());
            contractHeatInfo.setCompanyId(contractAddDTO.getCompanyId());
            contractHeatInfo.setContractCode(contractCode);
            contractHeatInfo.setContractName(contractAddDTO.getContractName());
            contractHeatInfo.setaName(contractAddDTO.getaName());
            contractHeatInfo.setbName(contractAddDTO.getbName());
            contractHeatInfo.setScope(contractAddDTO.getScope());
            contractHeatInfo.setArea(area);
            contractHeatInfo.setUnitPrice(unitPrice);
            contractHeatInfo.setTotal(total);
            contractHeatInfo.setPriceAdj(priceAdj);
            contractHeatInfo.setAccountAll(BigDecimal.ZERO);
            contractHeatInfo.setArrearsCost((area.multiply(unitPrice).add(priceAdj)).setScale(3, BigDecimal.ROUND_HALF_UP));
            contractHeatInfo.setContactName(contractAddDTO.getContactName());
            contractHeatInfo.setContactPhone(contractAddDTO.getContactPhone());
            contractHeatInfo.setFilePath(contractAddDTO.getFilePath());
            contractHeatInfo.setNotes(contractAddDTO.getNotes());
            contractHeatInfo.setSignDate(contractAddDTO.getSignDate());
            contractHeatInfo.setCreateDate(date);
            contractHeatInfo.setCreateUser(userId);
            contractHeatInfo.setUpdateDate(date);
            contractHeatInfo.setUpdateUser(userId);

            int insert = contractHeatInfoMapper.insert(contractHeatInfo);
            if (insert != 1) {
                logger.error("合同信息新增失败，insert = " + insert);
                throw new BusinessException("合同信息新增失败！");
            }
        } else {
            ContractHeatInfo contractHeatInfo = checkApprove(primaryId, "修改");

            BigDecimal areaDb = contractHeatInfo.getArea();
            BigDecimal unitPriceDb = contractHeatInfo.getUnitPrice();
            BigDecimal priceAdjDb = contractHeatInfo.getPriceAdj();
            BigDecimal accountAllDb = contractHeatInfo.getAccountAll();

            contractHeatInfo.setContractName(contractAddDTO.getContractName());
            contractHeatInfo.setaName(contractAddDTO.getaName());
            contractHeatInfo.setbName(contractAddDTO.getbName());
            contractHeatInfo.setScope(contractAddDTO.getScope());
            contractHeatInfo.setContactName(contractAddDTO.getContactName());
            contractHeatInfo.setContactPhone(contractAddDTO.getContactPhone());
            contractHeatInfo.setSignDate(contractAddDTO.getSignDate());
            contractHeatInfo.setFilePath(contractAddDTO.getFilePath());
            contractHeatInfo.setNotes(contractAddDTO.getNotes());
            contractHeatInfo.setUpdateDate(date);
            contractHeatInfo.setUpdateUser(userId);

            if (!(areaDb.equals(area) && unitPriceDb.equals(unitPrice) && priceAdjDb.equals(priceAdj))) {
                TypeMst typeMst = typeMstMapper.getByTypeKbnAndTypeName(Constant.GLOBAL_BUSINESS_TYPE, Constant.GBT_CONTRACT_APPROVE);
                if (typeMst == null) {
                    logger.error("业务类型获取失败！");
                    throw new BusinessException("业务类型获取失败！");
                }
                String typeId = typeMst.getTypeId();
                String companyId = contractAddDTO.getCompanyId();
                String flowId = FlowIdTool.GetFlowId(companyId, typeId);
                contractHeatInfo.setApproveSerial(flowId);

                // 插入 tmp 表，走审核
                ContractHeatInfoTmp contractHeatInfoTmp = new ContractHeatInfoTmp();
                contractHeatInfoTmp.setApproveSerial(flowId);
                contractHeatInfoTmp.setPrimaryId(IDWorker.uuid());
                contractHeatInfoTmp.setCompanyId(companyId);
                contractHeatInfoTmp.setContractCode(contractHeatInfo.getContractCode());
                contractHeatInfoTmp.setContractName(contractAddDTO.getContractName());
                contractHeatInfoTmp.setaName(contractAddDTO.getaName());
                contractHeatInfoTmp.setbName(contractAddDTO.getbName());
                contractHeatInfoTmp.setScope(contractAddDTO.getScope());
                contractHeatInfoTmp.setArea(area);
                contractHeatInfoTmp.setUnitPrice(unitPrice);
                contractHeatInfoTmp.setTotal(total);
                contractHeatInfoTmp.setPriceAdj(priceAdj);
                contractHeatInfoTmp.setAccountAll(accountAllDb);
                contractHeatInfoTmp.setArrearsCost((area.multiply(unitPrice).add(priceAdj).subtract(accountAllDb)).setScale(3, BigDecimal.ROUND_HALF_UP));
                contractHeatInfoTmp.setContactName(contractAddDTO.getContactName());
                contractHeatInfoTmp.setContactPhone(contractAddDTO.getContactPhone());
                contractHeatInfoTmp.setSignDate(contractAddDTO.getSignDate());
                contractHeatInfoTmp.setFilePath(contractAddDTO.getFilePath());
                contractHeatInfoTmp.setNotes(contractAddDTO.getNotes());
                contractHeatInfoTmp.setCreateDate(date);
                contractHeatInfoTmp.setCreateUser(userId);
                contractHeatInfoTmp.setUpdateDate(date);
                contractHeatInfoTmp.setUpdateUser(userId);
                contractHeatInfoTmp.setApproveType(APPROVE_TYPE_UPDATE);

                int insert = contractHeatInfoTmpMapper.insert(contractHeatInfoTmp);
                if (insert != 1) {
                    logger.error("合同审核信息保存失败，insert = " + insert);
                    throw new BusinessException("合同审核信息保存失败！");
                }
            }

            int update = contractHeatInfoMapper.updateByPrimaryKey(contractHeatInfo);
            if (update != 1) {
                logger.error("合同信息更新失败，update = " + update);
                throw new BusinessException("合同信息更新失败！");
            }
        }
    }

    /**
     * @Description: 入网缴费弹框内容
     * @Param: [primaryId]
     * @Return: cn.plou.web.charge.heatingmanage.vo.NetInPayVO
     * @Author: youbc
     * @Date: 2018/8/27 15下午02
     */
    @Override
    public NetInPayVO getNetInPay(String primaryId) {
        ContractHeatInfo contractHeatInfo = checkApprove(primaryId, "缴费");

        NetInPayVO netInPay = contractHeatInfoMapper.getNetInPay(primaryId);
        if (netInPay == null) {
            logger.error("入网缴费信息获取失败！");
            throw new BusinessException("入网缴费信息获取失败！");
        }
        BigDecimal arrearsCost = netInPay.getArrearsCost();
        String companyId = contractHeatInfo.getCompanyId();
        netInPay.setArrearsCostCash(MoneyConverter.Convert(companyId, arrearsCost, "1"));
        netInPay.setArrearsCostNotCash(MoneyConverter.Convert(companyId, arrearsCost, "2"));
        netInPay.setReceiptno(FlowIdTool.GetReceiptNo(companyId));
        return netInPay;
    }

    /**
     * @Description: 检查是否在审核
     * @Param: [primaryId]
     * @Return: void
     * @Author: youbc
     * @Date: 2018/8/30 14下午22
     */
    @Override
    public void checkApprove(String primaryId) {
        checkApprove(primaryId, "操作");
    }

    /**
     * @Description: 检查合同是否正在审核中
     * @Param: [primaryId, msg]
     * @Return: cn.plou.web.charge.heatingmanage.domain.ContractHeatInfo
     * @Author: youbc
     * @Date: 2018/8/29 15下午09
     */
    private ContractHeatInfo checkApprove(String primaryId, String msg) {
        ContractHeatInfo contractHeatInfo = contractHeatInfoMapper.selectByPrimaryKey(primaryId);
        if (contractHeatInfo == null) {
            logger.error("合同信息获取失败！");
            throw new BusinessException("合同信息获取失败！");
        }
        String approveSerial = contractHeatInfo.getApproveSerial();
        if (approveSerial != null) {
            ContractHeatInfoTmp contractHeatInfoTmp = contractHeatInfoTmpMapper.selectByPrimaryKey(approveSerial);
            if (contractHeatInfoTmp.getApproveRes() == null) {
                logger.error("合同审核中，暂不能" + msg + "！");
                throw new BusinessException("合同审核中，暂不能" + msg + "！");
            }
        }
        return contractHeatInfo;
    }

    /**
     * @Description: 入网缴费
     * @Param: [netInPayDTO]
     * @Return: void
     * @Author: youbc
     * @Date: 2018/8/27 17下午32
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void netInPay(NetInPayDTO netInPayDTO) {
        String primaryId = netInPayDTO.getPrimaryId();
        ContractHeatInfo contractHeatInfo = checkApprove(primaryId, "缴费");
        String userId = UserUtils.getUserId();
        Date date = new Date();
        BigDecimal accountCost = netInPayDTO.getAccountCost();

        contractHeatInfo.setAccountAll(contractHeatInfo.getAccountAll().add(accountCost));
        contractHeatInfo.setArrearsCost(contractHeatInfo.getArrearsCost().subtract(accountCost));
        contractHeatInfo.setUpdateDate(date);
        contractHeatInfo.setUpdateUser(userId);

        int update = contractHeatInfoMapper.updateByPrimaryKey(contractHeatInfo);
        if (update != 1) {
            logger.error("入网缴费失败，update = " + update);
            throw new BusinessException("入网缴费失败！");
        }

        TypeMst typeMst = typeMstMapper.getByTypeKbnAndTypeName(Constant.GLOBAL_BUSINESS_TYPE, Constant.GBT_CONTRACT_PAY);
        if (typeMst == null) {
            logger.error("业务类型获取失败！");
            throw new BusinessException("业务类型获取失败！");
        }
        String typeId = typeMst.getTypeId();
        String companyId = netInPayDTO.getCompanyId();
        String flowId = FlowIdTool.GetFlowId(companyId, typeId);
        ContractHeatMoneyDetail contractHeatMoneyDetail = new ContractHeatMoneyDetail();
        contractHeatMoneyDetail.setPrimaryId(flowId);
        contractHeatMoneyDetail.setCompanyId(companyId);
        contractHeatMoneyDetail.setContractCode(netInPayDTO.getContractCode());
        contractHeatMoneyDetail.setAccountCost(accountCost);
        contractHeatMoneyDetail.setAccountTime(date);
        contractHeatMoneyDetail.setPayType(netInPayDTO.getPayType());
        contractHeatMoneyDetail.setIsbill(netInPayDTO.getIsbill());
        contractHeatMoneyDetail.setBillno(netInPayDTO.getBillno());
        contractHeatMoneyDetail.setReceiptno(netInPayDTO.getReceiptno());
        contractHeatMoneyDetail.setContactName(userId);
        contractHeatMoneyDetail.setNotes(netInPayDTO.getNotes());
        contractHeatMoneyDetail.setCreateDate(date);
        contractHeatMoneyDetail.setCreateUser(userId);
        contractHeatMoneyDetail.setUpdateDate(date);
        contractHeatMoneyDetail.setUpdateUser(userId);

        int insert = contractHeatMoneyDetailMapper.insert(contractHeatMoneyDetail);
        if (insert != 1) {
            logger.error("入网缴费明细保存失败，insert = " + insert);
            throw new BusinessException("入网缴费明细保存失败！");
        }
    }

    /**
     * @Description: 合同列表
     * @Param: [netInManageSearchDTO]
     * @Return: java.util.List<cn.plou.web.charge.heatingmanage.vo.ContractHeatListVO>
     * @Author: youbc
     * @Date: 2018/8/28 16下午12
     */
    @Override
    public List<ContractHeatListVO> list(NetInManageSearchDTO netInManageSearchDTO) {
        return contractHeatInfoMapper.getListBySearch(netInManageSearchDTO);
    }

    /**
     * @Description: 合同列表-总数
     * @Param: [netInManageSearchDTO]
     * @Return: java.lang.Integer
     * @Author: youbc
     * @Date: 2018/8/28 16下午12
     */
    @Override
    public Integer listCount(NetInManageSearchDTO netInManageSearchDTO) {
        return contractHeatInfoMapper.getListCountBySearch(netInManageSearchDTO);
    }

    /**
     * @Description: 删除合同
     * @Param: [primaryId]
     * @Return: void
     * @Author: youbc
     * @Date: 2018/8/29 15下午18
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteContract(String primaryId, String explanation) {
        ContractHeatInfo contractHeatInfo = checkApprove(primaryId, "删除");

        TypeMst typeMst = typeMstMapper.getByTypeKbnAndTypeName(Constant.GLOBAL_BUSINESS_TYPE, Constant.GBT_CONTRACT_APPROVE);
        if (typeMst == null) {
            logger.error("业务类型获取失败！");
            throw new BusinessException("业务类型获取失败！");
        }
        String typeId = typeMst.getTypeId();
        String companyId = contractHeatInfo.getCompanyId();
        String flowId = FlowIdTool.GetFlowId(companyId, typeId);
        contractHeatInfo.setApproveSerial(flowId);

        String userId = UserUtils.getUserId();
        Date date = new Date();
        contractHeatInfo.setMemo1(explanation);
        contractHeatInfo.setUpdateDate(date);
        contractHeatInfo.setUpdateUser(userId);

        int update = contractHeatInfoMapper.updateByPrimaryKey(contractHeatInfo);
        if (update != 1) {
            logger.error("合同信息删除失败，update = " + update);
            throw new BusinessException("合同信息删除失败！");
        }

        // 插入 tmp 表，走审核
        ContractHeatInfoTmp contractHeatInfoTmp = new ContractHeatInfoTmp();
        contractHeatInfoTmp.setApproveSerial(flowId);
        contractHeatInfoTmp.setPrimaryId(IDWorker.uuid());
        contractHeatInfoTmp.setCompanyId(companyId);
        contractHeatInfoTmp.setContractCode(contractHeatInfo.getContractCode());
        contractHeatInfoTmp.setContractName(contractHeatInfo.getContractName());
        contractHeatInfoTmp.setaName(contractHeatInfo.getaName());
        contractHeatInfoTmp.setbName(contractHeatInfo.getbName());
        contractHeatInfoTmp.setScope(contractHeatInfo.getScope());
        contractHeatInfoTmp.setArea(contractHeatInfo.getArea());
        contractHeatInfoTmp.setUnitPrice(contractHeatInfo.getUnitPrice());
        contractHeatInfoTmp.setTotal(contractHeatInfo.getTotal());
        contractHeatInfoTmp.setPriceAdj(contractHeatInfo.getPriceAdj());
        contractHeatInfoTmp.setAccountAll(contractHeatInfo.getAccountAll());
        contractHeatInfoTmp.setArrearsCost(contractHeatInfo.getArrearsCost());
        contractHeatInfoTmp.setContactName(contractHeatInfo.getContactName());
        contractHeatInfoTmp.setContactPhone(contractHeatInfo.getContactPhone());
        contractHeatInfoTmp.setSignDate(contractHeatInfo.getSignDate());
        contractHeatInfoTmp.setFilePath(contractHeatInfo.getFilePath());
        contractHeatInfoTmp.setNotes(contractHeatInfo.getNotes());
        contractHeatInfoTmp.setMemo1(explanation);
        contractHeatInfoTmp.setCreateDate(date);
        contractHeatInfoTmp.setCreateUser(userId);
        contractHeatInfoTmp.setUpdateDate(date);
        contractHeatInfoTmp.setUpdateUser(userId);
        contractHeatInfoTmp.setApproveType(APPROVE_TYPE_DELETE);

        int insert = contractHeatInfoTmpMapper.insert(contractHeatInfoTmp);
        if (insert != 1) {
            logger.error("合同审核信息保存失败，insert = " + insert);
            throw new BusinessException("合同审核信息保存失败！");
        }

    }

    /**
     * @Description: 审核合同（批量）
     * @Param: [list]
     * @Return: void
     * @Author: youbc
     * @Date: 2018/8/31 10上午48
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveContract(List<ApproveContractApprovesDTO> list) {
        for (ApproveContractApprovesDTO approve : list) {
            doApprove(approve.getPrimaryId(), approve.getApproveRes());
        }
    }

    /**
     * @Description: 审核合同（单个）
     * @Param: [primaryId, approveRes]
     * @Return: void
     * @Author: youbc
     * @Date: 2018/8/30 10上午55
     */
    private void doApprove(String primaryId, String approveRes) {
        ContractHeatInfo contractHeatInfo = contractHeatInfoMapper.selectByPrimaryKey(primaryId);
        if (contractHeatInfo == null) {
            logger.error("合同信息获取失败，primaryId：" + primaryId);
            throw new BusinessException("合同信息获取失败！");
        }

        String contractName = contractHeatInfo.getContractName();
        String approveSerial = contractHeatInfo.getApproveSerial();
        if (StringUtils.isEmpty(approveSerial)) {
            logger.error("【" + contractName + "】合同无需审核！");
            throw new BusinessException("【" + contractName + "】合同无需审核！");
        }
        ContractHeatInfoTmp contractHeatInfoTmp = contractHeatInfoTmpMapper.selectByPrimaryKey(approveSerial);
        if (StringUtils.isNotEmpty(contractHeatInfoTmp.getApproveRes())) {
            logger.error("【" + contractName + "】合同无需审核！");
            throw new BusinessException("【" + contractName + "】合同无需审核！");
        }

        String userId = UserUtils.getUserId();
        Date date = new Date();
        contractHeatInfoTmp.setApproveRes(approveRes);
        contractHeatInfoTmp.setApproveName(userId);
        contractHeatInfoTmp.setApproveDate(date);
        int update = contractHeatInfoTmpMapper.updateByPrimaryKey(contractHeatInfoTmp);
        if (update != 1) {
            logger.error("【" + contractName + "】合同审核信息更新失败，update = " + update);
            throw new BusinessException("【" + contractName + "】合同审核信息更新失败！");
        }

        if (StringUtils.equalsIgnoreCase(APPROVE_RES_YES, approveRes)) {
            String approveType = contractHeatInfoTmp.getApproveType();
            if (StringUtils.equals(APPROVE_TYPE_UPDATE, approveType)) {
                contractHeatInfo.setArea(contractHeatInfoTmp.getArea());
                contractHeatInfo.setUnitPrice(contractHeatInfoTmp.getUnitPrice());
                contractHeatInfo.setTotal(contractHeatInfoTmp.getTotal());
                contractHeatInfo.setPriceAdj(contractHeatInfoTmp.getPriceAdj());
                contractHeatInfo.setArrearsCost(contractHeatInfoTmp.getArrearsCost());
                int update1 = contractHeatInfoMapper.updateByPrimaryKey(contractHeatInfo);
                if (update1 != 1) {
                    logger.error("【" + contractName + "】合同信息更新失败，update1 = " + update1);
                    throw new BusinessException("【" + contractName + "】合同信息更新失败！");
                }
            } else if (StringUtils.equals(APPROVE_TYPE_DELETE, approveType)) {
                int delete = contractHeatInfoMapper.deleteByPrimaryKey(primaryId);
                if (delete != 1) {
                    logger.error("【" + contractName + "】合同信息删除失败，delete = " + delete);
                    throw new BusinessException("【" + contractName + "】合同信息删除失败！");
                }
            } else {
                logger.error("【" + contractName + "】合同审核类型不正确！");
                throw new BusinessException("【" + contractName + "】合同审核类型不正确！");
            }
        }
    }
}

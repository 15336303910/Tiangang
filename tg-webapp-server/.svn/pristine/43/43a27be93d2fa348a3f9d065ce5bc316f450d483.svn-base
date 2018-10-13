package cn.plou.web.charge.chargeconfig.service.impl;

import cn.plou.web.charge.chargeconfig.dao.BankInterfaceInfoMapper;
import cn.plou.web.charge.chargeconfig.dto.DockingSetSaveDTO;
import cn.plou.web.charge.chargeconfig.dto.DockingSetSearchDTO;
import cn.plou.web.charge.chargeconfig.entity.BankInterfaceInfo;
import cn.plou.web.charge.chargeconfig.service.BankInterfaceInfoService;
import cn.plou.web.charge.chargeconfig.vo.DockingSetInfoVO;
import cn.plou.web.charge.chargeconfig.vo.DockingSetListVO;
import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.utils.IDWorker;
import cn.plou.web.common.utils.UserUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 2018/8/15 15:38
 */


@Service
@Transactional(rollbackFor = Exception.class)
public class BankInterfaceInfoServiceImpl implements BankInterfaceInfoService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private BankInterfaceInfoMapper bankInterfaceInfoMapper;

    @Override
    public int deleteByPrimaryKey(String primaryId) {
        return bankInterfaceInfoMapper.deleteByPrimaryKey(primaryId);
    }

    @Override
    public int insert(BankInterfaceInfo record) {
        return bankInterfaceInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(BankInterfaceInfo record) {
        return bankInterfaceInfoMapper.insertSelective(record);
    }

    @Override
    public BankInterfaceInfo selectByPrimaryKey(String primaryId) {
        return bankInterfaceInfoMapper.selectByPrimaryKey(primaryId);
    }

    @Override
    public BankInterfaceInfo selectByBankIp(String bankIp,String companyId) {
        return bankInterfaceInfoMapper.selectByBankIp(bankIp,companyId);
    }


    @Override
    public int updateByPrimaryKeySelective(BankInterfaceInfo record) {
        return bankInterfaceInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BankInterfaceInfo record) {
        return bankInterfaceInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<DockingSetListVO> list(DockingSetSearchDTO bankDockingSearchDTO) {
        return bankInterfaceInfoMapper.getListBySearch(bankDockingSearchDTO);
    }

    @Override
    public Integer listCount(DockingSetSearchDTO bankDockingSearchDTO) {
        return bankInterfaceInfoMapper.getListCountBySearch(bankDockingSearchDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(DockingSetSaveDTO dockingSetSaveDTO) {
        String userId = UserUtils.getUserId();
        Date date = new Date();
        String primaryId = dockingSetSaveDTO.getPrimaryId();
        if (StringUtils.isEmpty(primaryId)) {
            BankInterfaceInfo bankInterfaceInfo = new BankInterfaceInfo();
            BeanUtils.copyProperties(dockingSetSaveDTO, bankInterfaceInfo);
            bankInterfaceInfo.setPrimaryId(IDWorker.uuid());
            bankInterfaceInfo.setCreateDate(date);
            bankInterfaceInfo.setCreateUser(userId);
            bankInterfaceInfo.setUpdateDate(date);
            bankInterfaceInfo.setUpdateUser(userId);

            int insert = bankInterfaceInfoMapper.insert(bankInterfaceInfo);
            if (insert != 1) {
                logger.error("对接设置信息新增失败，insert = " + insert);
                throw new BusinessException("对接设置信息新增失败！");
            }
        } else {
            BankInterfaceInfo bankInterfaceInfo = bankInterfaceInfoMapper.selectByPrimaryKey(primaryId);
            if (bankInterfaceInfo == null) {
                logger.error("对接设置信息获取失败！");
                throw new BusinessException("对接设置信息获取失败！");
            }
            BeanUtils.copyProperties(dockingSetSaveDTO, bankInterfaceInfo);
            bankInterfaceInfo.setUpdateDate(date);
            bankInterfaceInfo.setUpdateUser(userId);

            int update = bankInterfaceInfoMapper.updateByPrimaryKey(bankInterfaceInfo);
            if (update != 1) {
                logger.error("对接设置信息更新失败，update = " + update);
                throw new BusinessException("对接设置信息更新失败！");
            }
        }
    }

    @Override
    public DockingSetInfoVO getSet(String primaryId) {
        BankInterfaceInfo bankInterfaceInfo = bankInterfaceInfoMapper.selectByPrimaryKey(primaryId);
        if (bankInterfaceInfo == null) {
            logger.error("对接设置信息获取失败！");
            throw new BusinessException("对接设置信息获取失败！");
        }

        DockingSetInfoVO dockingSetInfoVO = new DockingSetInfoVO();
        BeanUtils.copyProperties(bankInterfaceInfo, dockingSetInfoVO);
        return dockingSetInfoVO;
    }

    @Override
    public void deleteSet(String primaryId) {
        int delete = bankInterfaceInfoMapper.deleteByPrimaryKey(primaryId);
        if (delete != 1) {
            logger.error("对接设置信息删除失败，delete = " + delete);
            throw new BusinessException("对接设置信息删除失败！");
        }
    }


}

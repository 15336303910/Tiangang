package cn.plou.web.charge.chargeconfig.service.impl;

import cn.plou.web.charge.chargeconfig.dao.BankInterfaceHistoryMapper;
import cn.plou.web.charge.chargeconfig.dto.BankDockingSearchDTO;
import cn.plou.web.charge.chargeconfig.entity.BankInterfaceHistory;
import cn.plou.web.charge.chargeconfig.service.BankInterfaceHistoryService;
import cn.plou.web.charge.chargeconfig.vo.BankDockingListVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 2018/8/15 15:38
 */


@Service
@Transactional(rollbackFor = Exception.class)
public class BankInterfaceHistoryServiceImpl implements BankInterfaceHistoryService {

    @Resource
    private BankInterfaceHistoryMapper bankInterfaceHistoryMapper;

    @Override
    public int deleteByPrimaryKey(String primaryId) {
        return bankInterfaceHistoryMapper.deleteByPrimaryKey(primaryId);
    }

    @Override
    public int insert(BankInterfaceHistory record) {
        return bankInterfaceHistoryMapper.insert(record);
    }

    @Override
    public int insertSelective(BankInterfaceHistory record) {
        return bankInterfaceHistoryMapper.insertSelective(record);
    }

    @Override
    public BankInterfaceHistory selectByPrimaryKey(String primaryId) {
        return bankInterfaceHistoryMapper.selectByPrimaryKey(primaryId);
    }

    @Override
    public int updateByPrimaryKeySelective(BankInterfaceHistory record) {
        return bankInterfaceHistoryMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BankInterfaceHistory record) {
        return bankInterfaceHistoryMapper.updateByPrimaryKey(record);
    }

    @Override
    public BankInterfaceHistory findLastBankInterfaceHistory() {
        return bankInterfaceHistoryMapper.findLastBankInterfaceHistory();
    }

    @Override
    public PageInfo<BankDockingListVO> list(BankDockingSearchDTO bankDockingSearchDTO) {
        PageHelper.startPage(bankDockingSearchDTO.getPage(), bankDockingSearchDTO.getPageSize());
        List<BankDockingListVO> list = bankInterfaceHistoryMapper.getListBySearch(bankDockingSearchDTO);
        return new PageInfo<>(list);
    }

    @Override
    public Integer listCount(BankDockingSearchDTO bankDockingSearchDTO) {
        return bankInterfaceHistoryMapper.getListCountBySearch(bankDockingSearchDTO);
    }
}

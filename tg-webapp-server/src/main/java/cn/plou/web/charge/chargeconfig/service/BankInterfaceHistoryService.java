package cn.plou.web.charge.chargeconfig.service;

import cn.plou.web.charge.chargeconfig.dto.BankDockingSearchDTO;
import cn.plou.web.charge.chargeconfig.entity.BankInterfaceHistory;
import cn.plou.web.charge.chargeconfig.entity.BankInterfaceInfo;
import cn.plou.web.charge.chargeconfig.vo.BankDockingListVO;

import java.util.List;

public interface BankInterfaceHistoryService {
    int deleteByPrimaryKey(String primaryId);

    int insert(BankInterfaceHistory record);

    int insertSelective(BankInterfaceHistory record);

    BankInterfaceHistory selectByPrimaryKey(String primaryId);

    int updateByPrimaryKeySelective(BankInterfaceHistory record);

    int updateByPrimaryKey(BankInterfaceHistory record);

    BankInterfaceHistory findLastBankInterfaceHistory();

    List<BankDockingListVO> list(BankDockingSearchDTO bankDockingSearchDTO);

    Integer listCount(BankDockingSearchDTO bankDockingSearchDTO);

    List<BankDockingListVO> listOfStation(BankDockingSearchDTO bankDockingSearchDTO, List<String> ids);

    Integer listCountOfStation(BankDockingSearchDTO bankDockingSearchDTO, List<String> ids);
}
package cn.plou.web.charge.chargeconfig.dao;

import cn.plou.web.charge.chargeconfig.dto.BankDockingSearchDTO;
import cn.plou.web.charge.chargeconfig.entity.BankInterfaceHistory;
import cn.plou.web.charge.chargeconfig.vo.BankDockingListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BankInterfaceHistoryMapper {
    int deleteByPrimaryKey(String primaryId);

    int insert(BankInterfaceHistory record);

    int insertSelective(BankInterfaceHistory record);

    BankInterfaceHistory selectByPrimaryKey(String primaryId);

    int updateByPrimaryKeySelective(BankInterfaceHistory record);

    int updateByPrimaryKey(BankInterfaceHistory record);

    BankInterfaceHistory findLastBankInterfaceHistory();

    List<BankDockingListVO> getListBySearch(BankDockingSearchDTO bankDockingSearchDTO);

    Integer getListCountBySearch(BankDockingSearchDTO bankDockingSearchDTO);

    List<BankDockingListVO> getListBySearchOfStation(@Param("dto") BankDockingSearchDTO bankDockingSearchDTO, @Param("list") List<String> ids);

    Integer getListCountBySearchOfStation(@Param("dto") BankDockingSearchDTO bankDockingSearchDTO, @Param("list") List<String> ids);
}
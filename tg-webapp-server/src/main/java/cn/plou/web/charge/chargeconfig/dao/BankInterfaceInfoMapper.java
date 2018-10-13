package cn.plou.web.charge.chargeconfig.dao;

import cn.plou.web.charge.chargeconfig.dto.DockingSetSearchDTO;
import cn.plou.web.charge.chargeconfig.entity.BankInterfaceInfo;
import cn.plou.web.charge.chargeconfig.vo.DockingSetListVO;

import java.util.List;

public interface BankInterfaceInfoMapper {
    int deleteByPrimaryKey(String primaryId);

    int insert(BankInterfaceInfo record);

    int insertSelective(BankInterfaceInfo record);

    BankInterfaceInfo selectByPrimaryKey(String primaryId);

    BankInterfaceInfo selectByBankIp(String bankIp,String companyId);

    BankInterfaceInfo selectByPlatformCode(String platformCode);

    int updateByPrimaryKeySelective(BankInterfaceInfo record);

    int updateByPrimaryKey(BankInterfaceInfo record);

    List<DockingSetListVO> getListBySearch(DockingSetSearchDTO bankDockingSearchDTO);

    Integer getListCountBySearch(DockingSetSearchDTO bankDockingSearchDTO);
}
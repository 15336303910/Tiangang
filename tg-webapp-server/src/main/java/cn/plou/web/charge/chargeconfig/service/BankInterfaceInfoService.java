package cn.plou.web.charge.chargeconfig.service;

import cn.plou.web.charge.chargeconfig.dto.DockingSetSaveDTO;
import cn.plou.web.charge.chargeconfig.dto.DockingSetSearchDTO;
import cn.plou.web.charge.chargeconfig.entity.BankInterfaceInfo;
import cn.plou.web.charge.chargeconfig.vo.DockingSetInfoVO;
import cn.plou.web.charge.chargeconfig.vo.DockingSetListVO;
import com.github.pagehelper.PageInfo;
import java.math.BigDecimal;
import java.util.List;

public interface BankInterfaceInfoService {
    int deleteByPrimaryKey(String primaryId);

    int insert(BankInterfaceInfo record);

    int insertSelective(BankInterfaceInfo record);

    BankInterfaceInfo selectByPrimaryKey(String primaryId);

    BankInterfaceInfo selectByBankIp(String bankIp,String companyId);

    int updateByPrimaryKeySelective(BankInterfaceInfo record);

    int updateByPrimaryKey(BankInterfaceInfo record);

    List<DockingSetListVO> list(DockingSetSearchDTO bankDockingSearchDTO);

    Integer listCount(DockingSetSearchDTO bankDockingSearchDTO);

    void save(DockingSetSaveDTO dockingSetSaveDTO);

    DockingSetInfoVO getSet(String primaryId);

    void deleteSet(String primaryId);
}
package cn.plou.web.charge.heatingmanage.dao;

import cn.plou.web.charge.heatingmanage.domain.ContractHeatInfo;
import cn.plou.web.charge.heatingmanage.dto.NetInManageSearchDTO;
import cn.plou.web.charge.heatingmanage.vo.ContractHeatListVO;
import cn.plou.web.charge.heatingmanage.vo.NetInPayVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContractHeatInfoMapper {

    int deleteByPrimaryKey(String primaryId);

    int insert(ContractHeatInfo record);

    ContractHeatInfo selectByPrimaryKey(String primaryId);

    int updateByPrimaryKey(ContractHeatInfo record);

    NetInPayVO getNetInPay(String primaryId);

    List<ContractHeatListVO> getListBySearch(NetInManageSearchDTO netInManageSearchDTO);

    Integer getListCountBySearch(NetInManageSearchDTO netInManageSearchDTO);

    int countByContractCode(String contractCode);
}
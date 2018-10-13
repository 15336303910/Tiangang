package cn.plou.web.charge.heatingmanage.service;

import cn.plou.web.charge.heatingmanage.dto.ApproveContractApprovesDTO;
import cn.plou.web.charge.heatingmanage.dto.ContractAddDTO;
import cn.plou.web.charge.heatingmanage.dto.NetInManageSearchDTO;
import cn.plou.web.charge.heatingmanage.dto.NetInPayDTO;
import cn.plou.web.charge.heatingmanage.vo.ContractHeatListVO;
import cn.plou.web.charge.heatingmanage.vo.NetInPayVO;

import java.util.List;

/**
 * @ClassName: HeatingServeService
 * @Description: 入网管理
 * @Author: youbc
 * @Date 2018-8-27 09:31:16
 */
public interface NetInManageService {

    void auContract(ContractAddDTO contractAddDTO);

    NetInPayVO getNetInPay(String primaryId);

    void netInPay(NetInPayDTO netInPayDTO);

    List<ContractHeatListVO> list(NetInManageSearchDTO netInManageSearchDTO);

    Integer listCount(NetInManageSearchDTO netInManageSearchDTO);

    void deleteContract(String primaryId, String explanation);

    void approveContract(List<ApproveContractApprovesDTO> list);

    void checkApprove(String primaryId);
}

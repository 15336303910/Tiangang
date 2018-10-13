package cn.plou.web.charge.heatingmanage.controller;

import cn.plou.web.charge.heatingmanage.dto.ApproveContractApprovesDTO;
import cn.plou.web.charge.heatingmanage.dto.ContractAddDTO;
import cn.plou.web.charge.heatingmanage.dto.NetInManageSearchDTO;
import cn.plou.web.charge.heatingmanage.dto.NetInPayDTO;
import cn.plou.web.charge.heatingmanage.service.NetInManageService;
import cn.plou.web.charge.heatingmanage.vo.ContractHeatListVO;
import cn.plou.web.charge.heatingmanage.vo.NetInPayVO;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.exception.BindingResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static cn.plou.web.common.config.common.Cond.getCond;

/**
 * @ClassName: NetInManageController
 * @Description: 入网管理
 * @Author: youbc
 * @Date 2018-08-24 16:55
 */
@RestController
@RequestMapping("${chargePath}/heating-manage/net-in-manage/")
public class NetInManageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NetInManageService netInManageService;

    /**
     * @Description: 新增/更新合同
     * @Param: [contractAddDTO]
     * @Return: cn.plou.web.common.config.common.Root
     * @Author: youbc
     * @Date: 2018/8/27 9上午36
     */
    @PostMapping("au-contract")
    public Root auContract(@Valid ContractAddDTO contractAddDTO, BindingResult bindingResult) {
        BindingResultHandler.handle(bindingResult);
        netInManageService.auContract(contractAddDTO);
        return new Root();
    }

    /**
     * @Description: 入网缴费弹框内容
     * @Param: [primaryId]
     * @Return: cn.plou.web.charge.heatingmanage.vo.NetInPayVO
     * @Author: youbc
     * @Date: 2018/8/27 15下午01
     */
    @GetMapping("get-net-in-pay")
    public NetInPayVO getNetInPay(@RequestParam String primaryId) {
        return netInManageService.getNetInPay(primaryId);
    }

    /**
     * @Description: 入网缴费
     * @Param: [contractAddDTO]
     * @Return: cn.plou.web.common.config.common.Root
     * @Author: youbc
     * @Date: 2018/8/27 17下午31
     */
    @PostMapping("net-in-pay")
    public Root netInPay(@Valid NetInPayDTO netInPayDTO, BindingResult bindingResult) {
        BindingResultHandler.handle(bindingResult);
        netInManageService.netInPay(netInPayDTO);
        return new Root();
    }

    /**
     * @Description: 合同列表
     * @Param: [netInManageSearchDTO]
     * @Return: cn.plou.web.common.config.common.Root
     * @Author: youbc
     * @Date: 2018/8/28 15下午51
     */
    @PostMapping("list")
    public Root list(@Valid NetInManageSearchDTO netInManageSearchDTO, BindingResult bindingResult) {
        BindingResultHandler.handle(bindingResult);
        List<ContractHeatListVO> list = netInManageService.list(netInManageSearchDTO);
        Root root = new Root();
        root.setData(list);
        root.setCond(getCond(netInManageSearchDTO.getPage(), netInManageSearchDTO.getPageSize(), netInManageService.listCount(netInManageSearchDTO), netInManageSearchDTO.getOrder(), netInManageSearchDTO.getSortby()));
        return root;
    }

    /**
     * @Description: 删除合同
     * @Param: [primaryId, explanation]
     * @Return: cn.plou.web.common.config.common.Root
     * @Author: youbc
     * @Date: 2018/8/29 17下午08
     */
    @PostMapping("delete-contract")
    public Root deleteContract(@RequestParam String primaryId, @RequestParam String explanation) {
        netInManageService.deleteContract(primaryId, explanation);
        return new Root();
    }

    /**
     * @Description: 审核合同（批量）
     * @Param: [list]
     * @Return: cn.plou.web.common.config.common.Root
     * @Author: youbc
     * @Date: 2018/8/31 10上午48
     */
    @PostMapping("approve-contract")
    public Root approveContract(@RequestBody List<ApproveContractApprovesDTO> list) {
        netInManageService.approveContract(list);
        return new Root();
    }

    /**
     * @Description: 检查是否在审核
     * @Param: [primaryId]
     * @Return: void
     * @Author: youbc
     * @Date: 2018/8/30 14下午21
     */
    @GetMapping("check-approve")
    public Root checkApprove(@RequestParam String primaryId) {
        netInManageService.checkApprove(primaryId);
        return new Root();
    }
}

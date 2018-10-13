package cn.plou.web.charge.chargeconfig.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @Description: 通道管理-新增/编辑对接设置信息【bank_interface_info】
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018年9月18日16:52:36
 */
@Data
public class DockingSetSaveDTO {

    private String primaryId;

    @NotEmpty(message = "请选择供热公司")
    @Length(min = 5, max = 5, message = "只能按公司对接")
    private String companyId;

    @NotEmpty(message = "请选择对应用户")
    private String userCode;

    @NotEmpty(message = "请选择银行信息")
    private String bankId;

    @NotEmpty(message = "请输入平台码")
    @Length(max = 32, message = "平台码不能超过 32 字")
    private String platformCode;

    @NotEmpty(message = "请输入银行 IP")
    @Length(max = 32, message = "银行 IP 不能超过 32 字")
    private String bankIp;

    @NotEmpty(message = "请输入服务端口")
    @Length(max = 32, message = "服务端口不能超过 32 字")
    private String bankPort;

    @NotEmpty(message = "请输入加密密钥")
    @Length(max = 64, message = "加密密钥不能超过 64 字")
    private String secretKey;

    @Length(max = 200, message = "备注不能超过 200 字")
    private String notes;

    private String memo1;

    private String memo2;
}
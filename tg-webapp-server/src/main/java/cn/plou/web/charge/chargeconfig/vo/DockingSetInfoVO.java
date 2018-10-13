package cn.plou.web.charge.chargeconfig.vo;

import lombok.Data;

/**
 * @Description: 通道管理-对接设置弹框【bank_interface_info】
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018年9月18日16:52:36
 */
@Data
public class DockingSetInfoVO {

    private String primaryId;

    private String companyId;

    private String bankId;

    private String bankIp;

    private String platformCode;

    private String bankPort;

    private String secretKey;

    private String userCode;

    private String notes;

    private String memo1;

    private String memo2;
}
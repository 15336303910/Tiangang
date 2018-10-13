package cn.plou.web.charge.chargeconfig.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Description: 通道管理-对接设置列表【bank_interface_info】
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018年9月18日16:52:36
 */
@Data
public class DockingSetListVO {

    private String primaryId;

    private String companyId;
    private String companyName;

    private String bankId;
    private String bankName;

    private String bankIp;

    private String platformCode;

    private String bankPort;

    private String secretKey;

    private String userCode;
    private String userName;

    private Date lasttime;

    private String notes;

    private String memo1;

    private String memo2;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;
}
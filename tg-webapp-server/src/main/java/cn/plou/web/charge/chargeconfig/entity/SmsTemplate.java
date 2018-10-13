package cn.plou.web.charge.chargeconfig.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *短信模版
 *2018.8.15  yxc
 */

@Data
public class SmsTemplate  implements Serializable {


    private static final long serialVersionUID = 1L;
    /** 短信模板ｉｄ*/
    private String primaryId;

    /** 公司ｉｄ*/
    private String companyId;


    /** 短信模板主题*/
    private String title;


    /** 短信模板阿里大于id*/
    private String templateCode;

    /** 短信模板内容*/
    private String contents;

    /** 保留１*/
    private String memo1;

    /** 保留２*/
    private String memo2;

    /** 创建时间*/
    private Date createDate;

    /** 创建者*/
    private String createUser;

    /** 更新时间*/
    private Date updateDate;

    /** 更新者*/
    private String updateUser;


}
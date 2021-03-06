package cn.plou.web.charge.heatingmanage.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table money_clear_task
 *
 * @mbg.generated do_not_delete_during_merge
 */
@Data
public class    MoneyClearTask {
    /** 清欠流水号ＩＤ：用户ＩＤ＋任务安排日期*/
    private String primaryId;

    /** 采暖年度*/
    private String annual;

    /** 用户ＩＤ*/
    private String consumerId;

    /** 公司ＩＤ*/
    private String companyId;

    /** 欠费年度*/
    private String arrearsYear;
    /** 应缴热费*/
    private BigDecimal feePayable;

    /** 欠费金额*/
    private BigDecimal feeArrearage;

    /** 催缴措施：短信催缴、电话催缴、上门催缴、网络催缴、情况调查*/
    private String reminders;

    /** 任务安排日期*/
    private Date taskDate;

    /** 执行部门*/
    private String executDept;

    /** 执行人*/
    private String executPerson;

    /** 任务内容*/
    private String executContent;

    /** 用户姓名*/
    private String userName;

    /** 用户电话*/
    private String userTelephone;

    /** 执行人签名*/
    private String executPersonName;



    /** 供热用户类型ID*/
    private String  heatUserId;
    /** 网络ＡＰＰ*/
    private String netApp;

    /** 网络标识*/
    private String netIdentification;

    /** 执行情况*/
    private String executRecord;

    /** 执行日期*/
    private Date executTime;

    /** 执行标志*/
    private String executFlag;



    /** 维修负责人：取自用户信息*/
    private String repairPerson;

    /** 备注：情况说明*/
    private String notes;

    /** 创建者*/
    private Date createDate;

    /** 创建时间*/
    private String createUser;

    /** 更新时间*/
    private Date updateDate;

    /** 更新者*/
    private String updateUser;




    /** 催缴措施名称*/
    private String remindersName;


    private String   address;



}
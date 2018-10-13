package cn.plou.web.charge.heatingmanage.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.joda.time.format.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 *用户稽查
 */

@Data
public class HeatCheckDetail {
    /** 供热稽查流水号ＩＤ：用户ＩＤ＋任务安排日期*/
    private String primaryId;

    /** 采暖年度*/
    private String annual;

    /** 用户ＩＤ*/
    private String consumerId;

    /** 公司ＩＤ*/
    private String companyId;

    /** 供暖状态：供暖、停暖*/
    private String heatingStatus;

    /** 封堵面积*/
    private BigDecimal sealArea;

    /** 任务类型*/
    private String taskType;

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

    /** 执行情况*/
    private String executRecord;

    /** 执行日期*/
    private Date executTime;

    /** 稽查结论：正常、不正常、不确定*/
    private String inspectionConclusion;

    /** 维修负责人：取自用户信息*/
    private String repairPerson;

    /** 备注*/
    private String notes;

    /** 创建者*/
    private String createUser  ;
    /** 创建时间*/

    private Date createDate;

    /** 更新者*/
    private String   updateUser ;

    /** 更新时间*/
    private Date   updateDate;



    /** 用户地址*/
    private String   address;




}
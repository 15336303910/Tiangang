package cn.plou.web.charge.heatingmanage.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: HeatingServeVO
 * @Description: 供热服务弹框
 * @Author: youbc
 * @Date 2018-08-16 09:54
 */
@Data
public class HeatingServeListVO {

    private String primaryId; // 主键：用户ＩＤ＋申请时间

    private String consumerId; // 用户ＩＤ

    private String flowId; // 任务流水号：流程管理应尽可能和收费的审批使用一个模式

    private String address; // 地址

    private String annual; // 采暖年度

    private String apprPerson; // 申请人

    private Date apprTime; // 申请时间

    private String taskType; // 任务类型：申请供暖、申请停暖

    private String taskTypeName;

    private String taskContect; // 任务内容

    private String emerge; // 紧急程度

    private String emergeName; // 紧急程度

    private String overtime; // 处理时限

    private String endState; // 完成情况

    private String endStateName; // 完成情况

    private String handler; // 受理人

    private String handlerName; // 受理人

    private String executePerson; // 执行人

    private String executePersonName; // 执行人

    private Date executeTime; // 执行日期

    private String feedbackContect; // 反馈内容

    private String houseView; // 用户意见

    private String houseSign; // 用户签名

    private String visitPerson; // 回访人

    private String visitPersonName; // 回访人

    private Date visitTime; // 回访时间

    private String visitContect; // 回访内容

    private String satisfy; // 满意程度

    private String satisfyName; // 满意程度

    private String notes; // 备注：情况说明

    private Date createDate; // 创建时间

    private String createUser; // 创建者

    private String createUserName; // 创建者

    private Date updateDate; // 更新时间

    private String updateUser; // 更新者

    private String updateUserName; // 更新者

    private String costType; // 费用类型：工程费、材料费、手续费等

    private String costTypeName; // 费用类型：工程费、材料费、手续费等

    private BigDecimal nums; // 数量

    private BigDecimal singleCost; // 单价

    private BigDecimal totalCost; // 总额

    private BigDecimal actCost; // 实收金额

    private String billno; // 发票号

    private String receiptno; // 收据号

    private String printFlag; // 打印标志

    private String printFlagName; // 打印标志

    private String payChannel; // 缴费方式：现金、刷卡、微信、支付宝等

    private String payChannelName; // 缴费方式：现金、刷卡、微信、支付宝等

    private String diameter; // 用户管径：取自用户信息表

    private String controlType; // 控制方式：取自用户信息表

    private String controlTypeName; // 控制方式：取自用户信息表

    private Date appointTime; // 用户预约时间
}

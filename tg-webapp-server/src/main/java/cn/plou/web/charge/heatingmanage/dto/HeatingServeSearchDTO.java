package cn.plou.web.charge.heatingmanage.dto;

import cn.plou.web.common.config.common.BasePageEntity;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @ClassName: HeatingServeSearchDTO
 * @Description: 供热服务-查询
 * @Author: youbc
 * @Date 2018-08-16 10:45
 */

public class HeatingServeSearchDTO extends BasePageEntity {

    @NotEmpty(message = "请选择范围（公司、站、小区等）")
    private String rangeId; // 右侧列表，支持小区、楼和单元

    private String consumerId; // 用户ＩＤ

    private Date apprTimeStart; // 申请时间（开始）

    private Date apprTimeEnd; // 申请时间（结束）

    private String taskType; // 任务类型：申请供暖、申请停暖

    private String emerge; // 紧急程度

    private String endState; // 完成情况

    private String satisfy; // 满意程度

    private String executePerson; //  执行人

    private String visitPerson; // 回访人

    private String  currentAnnual;//本采暖季（若传了这个参数，起止日期会是空）

    public String getCurrentAnnual() {
        return currentAnnual;
    }

    public void setCurrentAnnual(String currentAnnual) {
        this.currentAnnual = currentAnnual;
    }

    public String getRangeId() {
        return rangeId;
    }

    public void setRangeId(String rangeId) {
        this.rangeId = rangeId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public Date getApprTimeStart() {
        return apprTimeStart;
    }

    public void setApprTimeStart(Date apprTimeStart) {
        this.apprTimeStart = apprTimeStart;
    }

    public Date getApprTimeEnd() {
        return apprTimeEnd;
    }

    public void setApprTimeEnd(Date apprTimeEnd) {
        this.apprTimeEnd = apprTimeEnd;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getEmerge() {
        return emerge;
    }

    public void setEmerge(String emerge) {
        this.emerge = emerge;
    }

    public String getEndState() {
        return endState;
    }

    public void setEndState(String endState) {
        this.endState = endState;
    }

    public String getSatisfy() {
        return satisfy;
    }

    public void setSatisfy(String satisfy) {
        this.satisfy = satisfy;
    }

    public String getExecutePerson() {
        return executePerson;
    }

    public void setExecutePerson(String executePerson) {
        this.executePerson = executePerson;
    }

    public String getVisitPerson() {
        return visitPerson;
    }

    public void setVisitPerson(String visitPerson) {
        this.visitPerson = visitPerson;
    }
}

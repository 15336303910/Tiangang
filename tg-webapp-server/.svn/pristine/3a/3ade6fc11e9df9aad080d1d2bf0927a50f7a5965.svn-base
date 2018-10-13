package cn.plou.web.charge.heatingmanage.dto;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * @ClassName: ServiceFeedbackDTO
 * @Description: 供热服务-服务反馈
 * @Author: youbc
 * @Date 2018-08-16 10:45
 */
public class ServiceFeedbackDTO {

    private String primaryId; // 主键：用户ＩＤ＋申请时间

    private String executePerson; // 执行人

    private Date executeTime; // 执行日期

    @Length(max = 200, message = "反馈内容不能超过 200 字")
    private String feedbackContect; // 反馈内容

    private String endState; // 完成情况

    @Length(max = 200, message = "用户意见不能超过 200 字")
    private String houseView; // 用户意见

    private String visitPerson; // 回访人

    private Date visitTime; // 回访时间

    @Length(max = 200, message = "回访内容不能超过 200 字")
    private String visitContect; // 回访内容

    private String satisfy; // 满意程度

    @Length(max = 200, message = "备注不能超过 200 字")
    private String notes; // 备注：情况说明

    public String getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    public String getExecutePerson() {
        return executePerson;
    }

    public void setExecutePerson(String executePerson) {
        this.executePerson = executePerson;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public String getFeedbackContect() {
        return feedbackContect;
    }

    public void setFeedbackContect(String feedbackContect) {
        this.feedbackContect = feedbackContect;
    }

    public String getEndState() {
        return endState;
    }

    public void setEndState(String endState) {
        this.endState = endState;
    }

    public String getHouseView() {
        return houseView;
    }

    public void setHouseView(String houseView) {
        this.houseView = houseView;
    }

    public String getVisitPerson() {
        return visitPerson;
    }

    public void setVisitPerson(String visitPerson) {
        this.visitPerson = visitPerson;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getVisitContect() {
        return visitContect;
    }

    public void setVisitContect(String visitContect) {
        this.visitContect = visitContect;
    }

    public String getSatisfy() {
        return satisfy;
    }

    public void setSatisfy(String satisfy) {
        this.satisfy = satisfy;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

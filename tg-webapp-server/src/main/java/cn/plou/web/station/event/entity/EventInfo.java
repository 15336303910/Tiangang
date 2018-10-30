package cn.plou.web.station.event.entity;

import java.util.Date;

public class EventInfo {
    private String primaryId;

    private String companyId;

    private String sourceId;

    private String devId;

    private String type;

    private Date beginTime;

    private String des;

    private String result;

    private String resultPerson;

    private String currentVal;

    private String orgVal;

    private String ip;

    private String computer;

    public String getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId == null ? null : primaryId.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId == null ? null : devId.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getResultPerson() {
        return resultPerson;
    }

    public void setResultPerson(String resultPerson) {
        this.resultPerson = resultPerson == null ? null : resultPerson.trim();
    }

    public String getCurrentVal() {
        return currentVal;
    }

    public void setCurrentVal(String currentVal) {
        this.currentVal = currentVal == null ? null : currentVal.trim();
    }

    public String getOrgVal() {
        return orgVal;
    }

    public void setOrgVal(String orgVal) {
        this.orgVal = orgVal == null ? null : orgVal.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getComputer() {
        return computer;
    }

    public void setComputer(String computer) {
        this.computer = computer == null ? null : computer.trim();
    }
}
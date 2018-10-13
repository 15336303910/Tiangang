package cn.plou.web.system.baseMessage.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SystemInfo {
    private String systemId;

    private String systemName;

    private BigDecimal planArea;

    private BigDecimal secondPipeFarthest;

    private BigDecimal secondInTemperature;

    private BigDecimal secondOutTemperature;

    private BigDecimal outdoorSetTemperature;

    private BigDecimal roomSetTemperature;

    private BigDecimal heatingIndex;

    private String companyId;
    private String companyName;

    private String stationId;
    private String stationName;

    private String notes;

    private String memo1;

    private String memo2;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;

    private String businessType;

    private BigDecimal waterPlan;

    private BigDecimal waterAct;

    private BigDecimal waterDesign;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public BigDecimal getPlanArea() {
        return planArea;
    }

    public void setPlanArea(BigDecimal planArea) {
        this.planArea = planArea;
    }

    public BigDecimal getSecondPipeFarthest() {
        return secondPipeFarthest;
    }

    public void setSecondPipeFarthest(BigDecimal secondPipeFarthest) {
        this.secondPipeFarthest = secondPipeFarthest;
    }

    public BigDecimal getSecondInTemperature() {
        return secondInTemperature;
    }

    public void setSecondInTemperature(BigDecimal secondInTemperature) {
        this.secondInTemperature = secondInTemperature;
    }

    public BigDecimal getSecondOutTemperature() {
        return secondOutTemperature;
    }

    public void setSecondOutTemperature(BigDecimal secondOutTemperature) {
        this.secondOutTemperature = secondOutTemperature;
    }

    public BigDecimal getOutdoorSetTemperature() {
        return outdoorSetTemperature;
    }

    public void setOutdoorSetTemperature(BigDecimal outdoorSetTemperature) {
        this.outdoorSetTemperature = outdoorSetTemperature;
    }

    public BigDecimal getRoomSetTemperature() {
        return roomSetTemperature;
    }

    public void setRoomSetTemperature(BigDecimal roomSetTemperature) {
        this.roomSetTemperature = roomSetTemperature;
    }

    public BigDecimal getHeatingIndex() {
        return heatingIndex;
    }

    public void setHeatingIndex(BigDecimal heatingIndex) {
        this.heatingIndex = heatingIndex;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getMemo1() {
        return memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1;
    }

    public String getMemo2() {
        return memo2;
    }

    public void setMemo2(String memo2) {
        this.memo2 = memo2;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public BigDecimal getWaterPlan() {
        return waterPlan;
    }

    public void setWaterPlan(BigDecimal waterPlan) {
        this.waterPlan = waterPlan;
    }

    public BigDecimal getWaterAct() {
        return waterAct;
    }

    public void setWaterAct(BigDecimal waterAct) {
        this.waterAct = waterAct;
    }

    public BigDecimal getWaterDesign() {
        return waterDesign;
    }

    public void setWaterDesign(BigDecimal waterDesign) {
        this.waterDesign = waterDesign;
    }
}

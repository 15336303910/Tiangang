package cn.plou.web.system.baseMessage.system.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SystemVo {
    private List<String> systemIds;

    private String systemName;

    private BigDecimal planArea;

    private BigDecimal secondPipeFarthest;

    private BigDecimal secondInTemperature;

    private BigDecimal secondOutTemperature;

    private BigDecimal outdoorSetTemperature;

    private BigDecimal roomSetTemperature;

    private BigDecimal heatingIndex;

    private String companyId;

    private String stationId;

    private String notes;
    private Date updateDate;

    private String updateUser;

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

    public List<String> getSystemIds() {
        return systemIds;
    }

    public void setSystemIds(List<String> systemIds) {
        this.systemIds = systemIds;
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

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
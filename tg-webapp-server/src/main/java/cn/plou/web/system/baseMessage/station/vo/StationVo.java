package cn.plou.web.system.baseMessage.station.vo;

import cn.plou.web.system.baseMessage.station.entity.StationKey;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StationVo {

    private List<String> stationIds;

    private String companyId;

    private String stationName;

    private String producerId;

    private String pipeTypeId;

    private String stationTypeId;

    private Integer systemNum;

    private BigDecimal planArea;

    private String address;

    private String departmentId;

    private String chargePerson;

    private BigDecimal primaryInTemperature;

    private BigDecimal primaryOutTemperature;

    private BigDecimal outdoorSetTemperature;

    private BigDecimal roomSetTemperature;

    private BigDecimal heatingIndex;

    private BigDecimal producerDistance;

    private BigDecimal height;

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
    public List<String> getStationIds() {
        return stationIds;
    }

    public void setStationIds(List<String> stationIds) {
        this.stationIds = stationIds;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public String getStationTypeId() {
        return stationTypeId;
    }

    public void setStationTypeId(String stationTypeId) {
        this.stationTypeId = stationTypeId;
    }

    public Integer getSystemNum() {
        return systemNum;
    }

    public void setSystemNum(Integer systemNum) {
        this.systemNum = systemNum;
    }

    public BigDecimal getPlanArea() {
        return planArea;
    }

    public void setPlanArea(BigDecimal planArea) {
        this.planArea = planArea;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public BigDecimal getPrimaryInTemperature() {
        return primaryInTemperature;
    }

    public void setPrimaryInTemperature(BigDecimal primaryInTemperature) {
        this.primaryInTemperature = primaryInTemperature;
    }

    public BigDecimal getPrimaryOutTemperature() {
        return primaryOutTemperature;
    }

    public void setPrimaryOutTemperature(BigDecimal primaryOutTemperature) {
        this.primaryOutTemperature = primaryOutTemperature;
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

    public BigDecimal getProducerDistance() {
        return producerDistance;
    }

    public void setProducerDistance(BigDecimal producerDistance) {
        this.producerDistance = producerDistance;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPipeTypeId() {
        return pipeTypeId;
    }

    public void setPipeTypeId(String pipeTypeId) {
        this.pipeTypeId = pipeTypeId;
    }
}
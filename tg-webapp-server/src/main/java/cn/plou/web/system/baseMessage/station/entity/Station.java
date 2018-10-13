package cn.plou.web.system.baseMessage.station.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.math.BigDecimal;
import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Station extends StationKey {
    @ApiModelProperty(required = true)
    private String stationName;

    @ApiModelProperty(required = true)
    private String producerId;

    private String pipeTypeId;

    @ApiModelProperty(required = true)
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

    private String longitude;

    private String latitude;

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

    private String pipeInfo;

    private String city;

    private Integer index;

    private Integer hpumpNum;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName == null ? null : stationName.trim();
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId == null ? null : producerId.trim();
    }

    public String getPipeTypeId() {
        return pipeTypeId;
    }

    public void setPipeTypeId(String pipeTypeId) {
        this.pipeTypeId = pipeTypeId == null ? null : pipeTypeId.trim();
    }

    public String getStationTypeId() {
        return stationTypeId;
    }

    public void setStationTypeId(String stationTypeId) {
        this.stationTypeId = stationTypeId == null ? null : stationTypeId.trim();
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
        this.address = address == null ? null : address.trim();
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson == null ? null : chargePerson.trim();
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public String getMemo1() {
        return memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1 == null ? null : memo1.trim();
    }

    public String getMemo2() {
        return memo2;
    }

    public void setMemo2(String memo2) {
        this.memo2 = memo2 == null ? null : memo2.trim();
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
        this.createUser = createUser == null ? null : createUser.trim();
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
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
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

    public String getPipeInfo() {
        return pipeInfo;
    }

    public void setPipeInfo(String pipeInfo) {
        this.pipeInfo = pipeInfo == null ? null : pipeInfo.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getHpumpNum() {
        return hpumpNum;
    }

    public void setHpumpNum(Integer hpumpNum) {
        this.hpumpNum = hpumpNum;
    }
}
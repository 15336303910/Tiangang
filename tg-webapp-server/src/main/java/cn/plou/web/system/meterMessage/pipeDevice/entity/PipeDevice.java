package cn.plou.web.system.meterMessage.pipeDevice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PipeDevice implements Serializable {
    private String pipeDeviceId;

    @ApiModelProperty(required = true)
    private String pipeDeviceName;

    private String ascriptionId;

    private String fatherNo;

    private String pipeDeviceNo;

    private BigDecimal hight;

    private String equipmentNo;

    private BigDecimal outDiam;

    private BigDecimal pipeWall;

    private String techniqueParam;

    private String factory;

    private String deviceClassify;

    private String chargePerson;

    private BigDecimal lenth;

    private BigDecimal lengthAdjustFactor;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    private String longitude;

    private String latitude;

    @ApiModelProperty (required = true)
    private String companyId;

    private String notes;

    private String memo1;

    private String memo2;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;

    private BigDecimal inDiam;

    private String pipeTypeId;

    private String pipeId;

    public String getPipeDeviceId() {
        return pipeDeviceId;
    }

    public void setPipeDeviceId(String pipeDeviceId) {
        this.pipeDeviceId = pipeDeviceId == null ? null : pipeDeviceId.trim();
    }

    public String getPipeDeviceName() {
        return pipeDeviceName;
    }

    public void setPipeDeviceName(String pipeDeviceName) {
        this.pipeDeviceName = pipeDeviceName == null ? null : pipeDeviceName.trim();
    }

    public String getAscriptionId() {
        return ascriptionId;
    }

    public void setAscriptionId(String ascriptionId) {
        this.ascriptionId = ascriptionId == null ? null : ascriptionId.trim();
    }

    public String getFatherNo() {
        return fatherNo;
    }

    public void setFatherNo(String fatherNo) {
        this.fatherNo = fatherNo == null ? null : fatherNo.trim();
    }

    public String getPipeDeviceNo() {
        return pipeDeviceNo;
    }

    public void setPipeDeviceNo(String pipeDeviceNo) {
        this.pipeDeviceNo = pipeDeviceNo == null ? null : pipeDeviceNo.trim();
    }

    public BigDecimal getHight() {
        return hight;
    }

    public void setHight(BigDecimal hight) {
        this.hight = hight;
    }

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo == null ? null : equipmentNo.trim();
    }

    public BigDecimal getOutDiam() {
        return outDiam;
    }

    public void setOutDiam(BigDecimal outDiam) {
        this.outDiam = outDiam;
    }

    public BigDecimal getPipeWall() {
        return pipeWall;
    }

    public void setPipeWall(BigDecimal pipeWall) {
        this.pipeWall = pipeWall;
    }

    public String getTechniqueParam() {
        return techniqueParam;
    }

    public void setTechniqueParam(String techniqueParam) {
        this.techniqueParam = techniqueParam == null ? null : techniqueParam.trim();
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory == null ? null : factory.trim();
    }

    public String getDeviceClassify() {
        return deviceClassify;
    }

    public void setDeviceClassify(String deviceClassify) {
        this.deviceClassify = deviceClassify == null ? null : deviceClassify.trim();
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson == null ? null : chargePerson.trim();
    }

    public BigDecimal getLenth() {
        return lenth;
    }

    public void setLenth(BigDecimal lenth) {
        this.lenth = lenth;
    }

    public BigDecimal getLengthAdjustFactor() {
        return lengthAdjustFactor;
    }

    public void setLengthAdjustFactor(BigDecimal lengthAdjustFactor) {
        this.lengthAdjustFactor = lengthAdjustFactor;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
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

    public BigDecimal getInDiam() {
        return inDiam;
    }

    public void setInDiam(BigDecimal inDiam) {
        this.inDiam = inDiam;
    }

    public String getPipeId() {
        return pipeId;
    }

    public void setPipeId(String pipeId) {
        this.pipeId = pipeId == null ? null : pipeId.trim();
    }

    public String getPipeTypeId() {
        return pipeTypeId;
    }

    public void setPipeTypeId(String pipeTypeId) {
        this.pipeTypeId = pipeTypeId;
    }
}
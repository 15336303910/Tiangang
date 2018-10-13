package cn.plou.web.system.meterMessage.pipeDevice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PipeDeviceVo {

    private List<String>pipeDeviceIds;

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

    private Date startTime;

    private String companyId;

    private String notes;

    private BigDecimal inDiam;

    private String pipeId;
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

    public List<String> getPipeDeviceIds() {
        return pipeDeviceIds;
    }

    public void setPipeDeviceIds(List<String> pipeDeviceIds) {
        this.pipeDeviceIds = pipeDeviceIds;
    }

    public String getPipeDeviceName() {
        return pipeDeviceName;
    }

    public void setPipeDeviceName(String pipeDeviceName) {
        this.pipeDeviceName = pipeDeviceName;
    }

    public String getAscriptionId() {
        return ascriptionId;
    }

    public void setAscriptionId(String ascriptionId) {
        this.ascriptionId = ascriptionId;
    }

    public String getFatherNo() {
        return fatherNo;
    }

    public void setFatherNo(String fatherNo) {
        this.fatherNo = fatherNo;
    }

    public String getPipeDeviceNo() {
        return pipeDeviceNo;
    }

    public void setPipeDeviceNo(String pipeDeviceNo) {
        this.pipeDeviceNo = pipeDeviceNo;
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
        this.equipmentNo = equipmentNo;
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
        this.techniqueParam = techniqueParam;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getDeviceClassify() {
        return deviceClassify;
    }

    public void setDeviceClassify(String deviceClassify) {
        this.deviceClassify = deviceClassify;
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
        this.pipeId = pipeId;
    }
}
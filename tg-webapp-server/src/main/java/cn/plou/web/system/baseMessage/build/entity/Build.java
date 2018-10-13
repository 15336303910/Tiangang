package cn.plou.web.system.baseMessage.build.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Build extends BuildKey implements Serializable {
    private String buildingName;
    private String unitNum;
    private String floorNum;
    private BigDecimal floorHeight;
    private BigDecimal buildingArea;
    private String energySaving;
    private String preserveHeat;
    private String beginPipeId;
    private String pipeId;
    private String mainDiameter;
    private String pipePosition;
    private BigDecimal pipeLength;
    private BigDecimal heatingIndex;
    private String heatingForm;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String buildYear;
    private String builder;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date netInTime;
    private String netInContract;
    private String hasBuildMeter;
    private String hasBalanceValve;
    private String chargePerson;
    private String attributes;
    private String buildingRight;
    private String usingRight;
    private Integer systemNum;
    private String restTime;
    private String longitude;
    private String latitude;
    private String companyId;
    private String address;
    private String notes;
    private String memo1;
    private String memo2;
    private Date createDate;
    private String createUser;
    private Date updateDate;
    private String updateUser;
    private String controlType;
    private String waterPreserveHeat;
    private String waterPipeId;
    private Integer waterMainDiameter;
    private String waterPipePosition;
    private Integer waterPipeLength;
    private BigDecimal waterIndex;
    private String waterForm;
    private Date waterNetInTime;
    private String waterNetInContract;
    private String waterHasBuildMeter;
    private Integer index;
    private Integer isvalid;

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName == null ? null : buildingName.trim();
    }

    public String getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(String unitNum) {
        this.unitNum = unitNum == null ? null : unitNum.trim();
    }

    public String getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(String floorNum) {
        this.floorNum = floorNum == null ? null : floorNum.trim();
    }

    public BigDecimal getFloorHeight() {
        return floorHeight;
    }

    public void setFloorHeight(BigDecimal floorHeight) {
        this.floorHeight = floorHeight;
    }

    public BigDecimal getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(BigDecimal buildingArea) {
        this.buildingArea = buildingArea;
    }

    public String getEnergySaving() {
        return energySaving;
    }

    public void setEnergySaving(String energySaving) {
        this.energySaving = energySaving == null ? null : energySaving.trim();
    }

    public String getPreserveHeat() {
        return preserveHeat;
    }

    public void setPreserveHeat(String preserveHeat) {
        this.preserveHeat = preserveHeat == null ? null : preserveHeat.trim();
    }

    public String getBeginPipeId() {
        return beginPipeId;
    }

    public void setBeginPipeId(String beginPipeId) {
        this.beginPipeId = beginPipeId == null ? null : beginPipeId.trim();
    }

    public String getPipeId() {
        return pipeId;
    }

    public void setPipeId(String pipeId) {
        this.pipeId = pipeId == null ? null : pipeId.trim();
    }

    public String getMainDiameter() {
        return mainDiameter;
    }

    public void setMainDiameter(String mainDiameter) {
        this.mainDiameter = mainDiameter;
    }

    public String getPipePosition() {
        return pipePosition;
    }

    public void setPipePosition(String pipePosition) {
        this.pipePosition = pipePosition == null ? null : pipePosition.trim();
    }


    public BigDecimal getPipeLength() {
        return pipeLength;
    }

    public void setPipeLength(BigDecimal pipeLength) {
        this.pipeLength = pipeLength;
    }

    public BigDecimal getHeatingIndex() {
        return heatingIndex;
    }

    public void setHeatingIndex(BigDecimal heatingIndex) {
        this.heatingIndex = heatingIndex;
    }

    public String getHeatingForm() {
        return heatingForm;
    }

    public void setHeatingForm(String heatingForm) {
        this.heatingForm = heatingForm == null ? null : heatingForm.trim();
    }

    public String getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(String buildYear) {
        this.buildYear = buildYear == null ? null : buildYear.trim();
    }

    public String getBuilder() {
        return builder;
    }

    public void setBuilder(String builder) {
        this.builder = builder == null ? null : builder.trim();
    }

    public Date getNetInTime() {
        return netInTime;
    }

    public void setNetInTime(Date netInTime) {
        this.netInTime = netInTime;
    }

    public String getNetInContract() {
        return netInContract;
    }

    public void setNetInContract(String netInContract) {
        this.netInContract = netInContract == null ? null : netInContract.trim();
    }

    public String getHasBuildMeter() {
        return hasBuildMeter;
    }

    public void setHasBuildMeter(String hasBuildMeter) {
        this.hasBuildMeter = hasBuildMeter == null ? null : hasBuildMeter.trim();
    }

    public String getHasBalanceValve() {
        return hasBalanceValve;
    }

    public void setHasBalanceValve(String hasBalanceValve) {
        this.hasBalanceValve = hasBalanceValve == null ? null : hasBalanceValve.trim();
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson == null ? null : chargePerson.trim();
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes == null ? null : attributes.trim();
    }

    public String getBuildingRight() {
        return buildingRight;
    }

    public void setBuildingRight(String buildingRight) {
        this.buildingRight = buildingRight == null ? null : buildingRight.trim();
    }

    public String getUsingRight() {
        return usingRight;
    }

    public void setUsingRight(String usingRight) {
        this.usingRight = usingRight == null ? null : usingRight.trim();
    }

    public Integer getSystemNum() {
        return systemNum;
    }

    public void setSystemNum(Integer systemNum) {
        this.systemNum = systemNum;
    }

    public String getRestTime() {
        return restTime;
    }

    public void setRestTime(String restTime) {
        this.restTime = restTime == null ? null : restTime.trim();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType == null ? null : controlType.trim();
    }

    public String getWaterPreserveHeat() {
        return waterPreserveHeat;
    }

    public void setWaterPreserveHeat(String waterPreserveHeat) {
        this.waterPreserveHeat = waterPreserveHeat == null ? null : waterPreserveHeat.trim();
    }

    public String getWaterPipeId() {
        return waterPipeId;
    }

    public void setWaterPipeId(String waterPipeId) {
        this.waterPipeId = waterPipeId == null ? null : waterPipeId.trim();
    }

    public Integer getWaterMainDiameter() {
        return waterMainDiameter;
    }

    public void setWaterMainDiameter(Integer waterMainDiameter) {
        this.waterMainDiameter = waterMainDiameter;
    }

    public String getWaterPipePosition() {
        return waterPipePosition;
    }

    public void setWaterPipePosition(String waterPipePosition) {
        this.waterPipePosition = waterPipePosition == null ? null : waterPipePosition.trim();
    }

    public Integer getWaterPipeLength() {
        return waterPipeLength;
    }

    public void setWaterPipeLength(Integer waterPipeLength) {
        this.waterPipeLength = waterPipeLength;
    }

    public BigDecimal getWaterIndex() {
        return waterIndex;
    }

    public void setWaterIndex(BigDecimal waterIndex) {
        this.waterIndex = waterIndex;
    }

    public String getWaterForm() {
        return waterForm;
    }

    public void setWaterForm(String waterForm) {
        this.waterForm = waterForm == null ? null : waterForm.trim();
    }

    public Date getWaterNetInTime() {
        return waterNetInTime;
    }

    public void setWaterNetInTime(Date waterNetInTime) {
        this.waterNetInTime = waterNetInTime;
    }

    public String getWaterNetInContract() {
        return waterNetInContract;
    }

    public void setWaterNetInContract(String waterNetInContract) {
        this.waterNetInContract = waterNetInContract == null ? null : waterNetInContract.trim();
    }

    public String getWaterHasBuildMeter() {
        return waterHasBuildMeter;
    }

    public void setWaterHasBuildMeter(String waterHasBuildMeter) {
        this.waterHasBuildMeter = waterHasBuildMeter == null ? null : waterHasBuildMeter.trim();
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(Integer isvalid) {
        this.isvalid = isvalid;
    }
}
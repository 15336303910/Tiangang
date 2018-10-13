package cn.plou.web.system.baseMessage.producer.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Producer implements Serializable {
    private String producerId;

    @ApiModelProperty(required = true)
    private String producerName;

    private String superProducerId;

    @ApiModelProperty(required = true)
    private String companyId;

    private String totalLoad;

    private String deviceConf;

    private BigDecimal area;

    private BigDecimal length;

    private String longitude;

    private String latitude;

    private BigDecimal height;

    private String departmentId;

    private String chargePerson;

    private String notes;

    private String memo1;

    private String memo2;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;

    private String producerTypeId;

    private String businessType;

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId == null ? null : producerId.trim();
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName == null ? null : producerName.trim();
    }

    public String getSuperProducerId() {
        return superProducerId;
    }

    public void setSuperProducerId(String superProducerId) {
        this.superProducerId = superProducerId == null ? null : superProducerId.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getTotalLoad() {
        return totalLoad;
    }

    public void setTotalLoad(String totalLoad) {
        this.totalLoad = totalLoad;
    }

    public String getDeviceConf() {
        return deviceConf;
    }

    public void setDeviceConf(String deviceConf) {
        this.deviceConf = deviceConf;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
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

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
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

    public String getProducerTypeId() {
        return producerTypeId;
    }

    public void setProducerTypeId(String producerTypeId) {
        this.producerTypeId = producerTypeId == null ? null : producerTypeId.trim();
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        return Objects.equals(producerId, producer.producerId) &&
                Objects.equals(producerName, producer.producerName) &&
                Objects.equals(superProducerId, producer.superProducerId) &&
                Objects.equals(companyId, producer.companyId) &&
                Objects.equals(totalLoad, producer.totalLoad) &&
                Objects.equals(deviceConf, producer.deviceConf) &&
                Objects.equals(area, producer.area) &&
                Objects.equals(length, producer.length) &&
                Objects.equals(longitude, producer.longitude) &&
                Objects.equals(latitude, producer.latitude) &&
                Objects.equals(height, producer.height) &&
                Objects.equals(departmentId, producer.departmentId) &&
                Objects.equals(chargePerson, producer.chargePerson) &&
                Objects.equals(notes, producer.notes) &&
                Objects.equals(memo1, producer.memo1) &&
                Objects.equals(memo2, producer.memo2) &&
                Objects.equals(createDate, producer.createDate) &&
                Objects.equals(createUser, producer.createUser) &&
                Objects.equals(updateDate, producer.updateDate) &&
                Objects.equals(updateUser, producer.updateUser) &&
                Objects.equals(producerTypeId, producer.producerTypeId) &&
                Objects.equals(businessType, producer.businessType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(producerId, producerName, superProducerId, companyId, totalLoad, deviceConf, area, length, longitude, latitude, height, departmentId, chargePerson, notes, memo1, memo2, createDate, createUser, updateDate, updateUser, producerTypeId, businessType);
    }

    @Override
    public String toString() {
        return "Producer{" +
                "producerId='" + producerId + '\'' +
                ", producerName='" + producerName + '\'' +
                ", superProducerId='" + superProducerId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", totalLoad=" + totalLoad +
                ", producerInfo='" + deviceConf + '\'' +
                ", area=" + area +
                ", length=" + length +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", height=" + height +
                ", departmentId='" + departmentId + '\'' +
                ", chargePerson='" + chargePerson + '\'' +
                ", notes='" + notes + '\'' +
                ", memo1='" + memo1 + '\'' +
                ", memo2='" + memo2 + '\'' +
                ", createDate=" + createDate +
                ", createUser='" + createUser + '\'' +
                ", updateDate=" + updateDate +
                ", updateUser='" + updateUser + '\'' +
                ", producerTypeId='" + producerTypeId + '\'' +
                ", businessType='" + businessType + '\'' +
                '}';
    }
}
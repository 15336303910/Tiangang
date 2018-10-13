package cn.plou.web.system.baseMessage.producer.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProducerVo {
    private List<String> producerIds;

    private String producerName;

    private String superProducerId;

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

    private String producerTypeId;

    private String businessType;
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

    public List<String> getProducerIds() {
        return producerIds;
    }

    public void setProducerIds(List<String> producerIds) {
        this.producerIds = producerIds;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getSuperProducerId() {
        return superProducerId;
    }

    public void setSuperProducerId(String superProducerId) {
        this.superProducerId = superProducerId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
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
        this.departmentId = departmentId;
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getProducerTypeId() {
        return producerTypeId;
    }

    public void setProducerTypeId(String producerTypeId) {
        this.producerTypeId = producerTypeId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
}
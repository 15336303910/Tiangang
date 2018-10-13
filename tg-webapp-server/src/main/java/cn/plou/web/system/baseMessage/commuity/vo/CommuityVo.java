package cn.plou.web.system.baseMessage.commuity.vo;

import java.util.Date;
import java.util.List;

public class CommuityVo {
    private List<String> commuityIds;

    private String companyId;

    private String commuityName;

    private String property;

    private String tel;

    private String stationId;

    private String chargePerson;

    private String commuityArea;

    private Integer buildNum;

    private Integer unitNum;

    private String address;

    private String buildYear;

    private String longitude;

    private String latitude;

    private String notes;

    private String hasBuildMeter;

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

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public List<String> getCommuityIds() {
        return commuityIds;
    }

    public void setCommuityIds(List<String> commuityIds) {
        this.commuityIds = commuityIds;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCommuityName() {
        return commuityName;
    }

    public void setCommuityName(String commuityName) {
        this.commuityName = commuityName;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public String getCommuityArea() {
        return commuityArea;
    }

    public void setCommuityArea(String commuityArea) {
        this.commuityArea = commuityArea;
    }

    public Integer getBuildNum() {
        return buildNum;
    }

    public void setBuildNum(Integer buildNum) {
        this.buildNum = buildNum;
    }

    public Integer getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(Integer unitNum) {
        this.unitNum = unitNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(String buildYear) {
        this.buildYear = buildYear;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getHasBuildMeter() {
        return hasBuildMeter;
    }

    public void setHasBuildMeter(String hasBuildMeter) {
        this.hasBuildMeter = hasBuildMeter;
    }
}

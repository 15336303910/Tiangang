package cn.plou.web.system.baseMessage.commuity.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Commuity extends CommuityKey implements Serializable {

    private String rowno;

    private String companyId;

    private String commuityName;

    private String property;

    private String tel;

    private String chargePerson;

    private String commuityArea;

    private Integer buildNum;

    private Integer unitNum;

    private String address;

    private String buildYear;

    private String longitude;

    private String latitude;

    private String notes;

    private String memo1;

    private String memo2;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;

    private String hasBuildMeter;

    private String cbzq;

    private String xqzt;

    private Integer index;

    private String archivesId;
    public String getRowno() {
        return rowno;
    }

    public void setRowno(String rowno) {
        this.rowno = rowno;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCommuityName() {
        return commuityName;
    }

    public void setCommuityName(String commuityName) {
        this.commuityName = commuityName == null ? null : commuityName.trim();
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property == null ? null : property.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson == null ? null : chargePerson.trim();
    }

    public String getCommuityArea() {
        return commuityArea;
    }

    public void setCommuityArea(String commuityArea) {
        this.commuityArea = commuityArea == null ? null : commuityArea.trim();
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
        this.address = address == null ? null : address.trim();
    }

    public String getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(String buildYear) {
        this.buildYear = buildYear == null ? null : buildYear.trim();
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

    public String getHasBuildMeter() {
        return hasBuildMeter;
    }

    public void setHasBuildMeter(String hasBuildMeter) {
        this.hasBuildMeter = hasBuildMeter == null ? null : hasBuildMeter.trim();
    }

    public String getCbzq() {
        return cbzq;
    }

    public void setCbzq(String cbzq) {
        this.cbzq = cbzq;
    }

    public String getXqzt() {
        return xqzt;
    }

    public void setXqzt(String xqzt) {
        this.xqzt = xqzt == null ? null : xqzt.trim();
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }
}
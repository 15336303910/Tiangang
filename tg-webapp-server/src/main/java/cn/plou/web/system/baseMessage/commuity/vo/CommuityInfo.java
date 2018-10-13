package cn.plou.web.system.baseMessage.commuity.vo;

import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommuityInfo extends Commuity {
    private String rowno;

    private String companyId;
    private String companyName;

    private String commuityName;

    private String stationId;
    private String stationName;
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
    private String hasBuildMeterName;

    private String cbzq;
    private String cbzqName;

    private String xqzt;
    private String xqztName;

    private Integer index;

    @Override
    public String getStationId() {
        return stationId;
    }

    @Override
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getHasBuildMeterName() {
        return hasBuildMeterName;
    }

    public void setHasBuildMeterName(String hasBuildMeterName) {
        this.hasBuildMeterName = hasBuildMeterName;
    }

    public String getCbzqName() {
        return cbzqName;
    }

    public void setCbzqName(String cbzqName) {
        this.cbzqName = cbzqName;
    }

    public String getXqztName() {
        return xqztName;
    }

    public void setXqztName(String xqztName) {
        this.xqztName = xqztName;
    }

    @Override
    public String getRowno() {
        return rowno;
    }

    @Override
    public void setRowno(String rowno) {
        this.rowno = rowno;
    }

    @Override
    public String getCompanyId() {
        return companyId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String getCommuityName() {
        return commuityName;
    }

    @Override
    public void setCommuityName(String commuityName) {
        this.commuityName = commuityName;
    }

    @Override
    public String getProperty() {
        return property;
    }

    @Override
    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public String getTel() {
        return tel;
    }

    @Override
    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String getChargePerson() {
        return chargePerson;
    }

    @Override
    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    @Override
    public String getCommuityArea() {
        return commuityArea;
    }

    @Override
    public void setCommuityArea(String commuityArea) {
        this.commuityArea = commuityArea;
    }

    @Override
    public Integer getBuildNum() {
        return buildNum;
    }

    @Override
    public void setBuildNum(Integer buildNum) {
        this.buildNum = buildNum;
    }

    @Override
    public Integer getUnitNum() {
        return unitNum;
    }

    @Override
    public void setUnitNum(Integer unitNum) {
        this.unitNum = unitNum;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getBuildYear() {
        return buildYear;
    }

    @Override
    public void setBuildYear(String buildYear) {
        this.buildYear = buildYear;
    }

    @Override
    public String getLongitude() {
        return longitude;
    }

    @Override
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String getLatitude() {
        return latitude;
    }

    @Override
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    @Override
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String getMemo1() {
        return memo1;
    }

    @Override
    public void setMemo1(String memo1) {
        this.memo1 = memo1;
    }

    @Override
    public String getMemo2() {
        return memo2;
    }

    @Override
    public void setMemo2(String memo2) {
        this.memo2 = memo2;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String getCreateUser() {
        return createUser;
    }

    @Override
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public Date getUpdateDate() {
        return updateDate;
    }

    @Override
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String getUpdateUser() {
        return updateUser;
    }

    @Override
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public String getHasBuildMeter() {
        return hasBuildMeter;
    }

    @Override
    public void setHasBuildMeter(String hasBuildMeter) {
        this.hasBuildMeter = hasBuildMeter;
    }

    @Override
    public String getCbzq() {
        return cbzq;
    }

    @Override
    public void setCbzq(String cbzq) {
        this.cbzq = cbzq;
    }

    @Override
    public String getXqzt() {
        return xqzt;
    }

    @Override
    public void setXqzt(String xqzt) {
        this.xqzt = xqzt;
    }

    @Override
    public Integer getIndex() {
        return index;
    }

    @Override
    public void setIndex(Integer index) {
        this.index = index;
    }
}

package cn.plou.web.system.meterMessage.meterModifyData.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeterModifyData {
    private String rowno;

    private String meterId;

    private String meterType;

    private String consumerId;

    private String address2nd;

    private String flag;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date meterModifyTime;

    private String modifyReason;

    private String oldMetetBegin;

    private String oldMetetEnd;

    private String newMetetAddress2nd;

    private String newMetetBegin;

    private String companyId;

    private String notes;

    private String memo1;

    private String memo2;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;

    private String factory;

    public String getRowno() {
        return rowno;
    }

    public void setRowno(String rowno) {
        this.rowno = rowno == null ? null : rowno.trim();
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId == null ? null : meterId.trim();
    }

    public String getMeterType() {
        return meterType;
    }

    public void setMeterType(String meterType) {
        this.meterType = meterType == null ? null : meterType.trim();
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId == null ? null : consumerId.trim();
    }

    public String getAddress2nd() {
        return address2nd;
    }

    public void setAddress2nd(String address2nd) {
        this.address2nd = address2nd == null ? null : address2nd.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public Date getMeterModifyTime() {
        return meterModifyTime;
    }

    public void setMeterModifyTime(Date meterModifyTime) {
        this.meterModifyTime = meterModifyTime;
    }

    public String getModifyReason() {
        return modifyReason;
    }

    public void setModifyReason(String modifyReason) {
        this.modifyReason = modifyReason == null ? null : modifyReason.trim();
    }

    public String getOldMetetBegin() {
        return oldMetetBegin;
    }

    public void setOldMetetBegin(String oldMetetBegin) {
        this.oldMetetBegin = oldMetetBegin == null ? null : oldMetetBegin.trim();
    }

    public String getOldMetetEnd() {
        return oldMetetEnd;
    }

    public void setOldMetetEnd(String oldMetetEnd) {
        this.oldMetetEnd = oldMetetEnd == null ? null : oldMetetEnd.trim();
    }

    public String getNewMetetAddress2nd() {
        return newMetetAddress2nd;
    }

    public void setNewMetetAddress2nd(String newMetetAddress2nd) {
        this.newMetetAddress2nd = newMetetAddress2nd == null ? null : newMetetAddress2nd.trim();
    }

    public String getNewMetetBegin() {
        return newMetetBegin;
    }

    public void setNewMetetBegin(String newMetetBegin) {
        this.newMetetBegin = newMetetBegin == null ? null : newMetetBegin.trim();
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

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory == null ? null : factory.trim();
    }
}
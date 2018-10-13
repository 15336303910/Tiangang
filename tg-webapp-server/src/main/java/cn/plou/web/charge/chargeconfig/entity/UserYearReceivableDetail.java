package cn.plou.web.charge.chargeconfig.entity;

import java.math.BigDecimal;
import java.util.Date;

public class UserYearReceivableDetail {
    private String primaryId;

    private String companyId;

    private String consumerId;

    private String annual;

    private String chargingItem;

    private BigDecimal sum;

    private BigDecimal unitPrice;

    private BigDecimal discount;

    private BigDecimal total;

    private String beginDataFlag;

    private String certufucate;

    private String approveRes;

    private String notes;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;

    private String approveSerial;

    public String getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId == null ? null : primaryId.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId == null ? null : consumerId.trim();
    }

    public String getAnnual() {
        return annual;
    }

    public void setAnnual(String annual) {
        this.annual = annual == null ? null : annual.trim();
    }

    public String getChargingItem() {
        return chargingItem;
    }

    public void setChargingItem(String chargingItem) {
        this.chargingItem = chargingItem == null ? null : chargingItem.trim();
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getBeginDataFlag() {
        return beginDataFlag;
    }

    public void setBeginDataFlag(String beginDataFlag) {
        this.beginDataFlag = beginDataFlag == null ? null : beginDataFlag.trim();
    }

    public String getCertufucate() {
        return certufucate;
    }

    public void setCertufucate(String certufucate) {
        this.certufucate = certufucate == null ? null : certufucate.trim();
    }

    public String getApproveRes() {
        return approveRes;
    }

    public void setApproveRes(String approveRes) {
        this.approveRes = approveRes == null ? null : approveRes.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
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

    public String getApproveSerial() {
        return approveSerial;
    }

    public void setApproveSerial(String approveSerial) {
        this.approveSerial = approveSerial == null ? null : approveSerial.trim();
    }

}
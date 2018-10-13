package cn.plou.web.system.baseMessage.house.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PriceDefineUseInHouse {
    private String primaryId;

    private String priceName;

    private String annual;

    private String companyId;

    private String prePrice;

    private String areaPrice;

    private String heatPrice;

    private Integer yeatFlag;

    private String notes;

    private Date overdueBeginTime;

    private BigDecimal overdueRate;

    private String instruct;

    private String exchangerPrice;

    private String meteringChargingType;

    private String approveRes;

    private String approveSerial;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;

    private BigDecimal prePriceTmp;

    private BigDecimal areaPriceTmp;

    private BigDecimal heatPriceTmp;

    private BigDecimal exchangerPriceTmp;

    public String getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId == null ? null : primaryId.trim();
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName == null ? null : priceName.trim();
    }

    public String getAnnual() {
        return annual;
    }

    public void setAnnual(String annual) {
        this.annual = annual == null ? null : annual.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(String prePrice) {
        this.prePrice = prePrice == null ? null : prePrice.trim();
    }

    public String getAreaPrice() {
        return areaPrice;
    }

    public void setAreaPrice(String areaPrice) {
        this.areaPrice = areaPrice == null ? null : areaPrice.trim();
    }

    public String getHeatPrice() {
        return heatPrice;
    }

    public void setHeatPrice(String heatPrice) {
        this.heatPrice = heatPrice == null ? null : heatPrice.trim();
    }

    public Integer getYeatFlag() {
        return yeatFlag;
    }

    public void setYeatFlag(Integer yeatFlag) {
        this.yeatFlag = yeatFlag;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public Date getOverdueBeginTime() {
        return overdueBeginTime;
    }

    public void setOverdueBeginTime(Date overdueBeginTime) {
        this.overdueBeginTime = overdueBeginTime;
    }

    public BigDecimal getOverdueRate() {
        return overdueRate;
    }

    public void setOverdueRate(BigDecimal overdueRate) {
        this.overdueRate = overdueRate;
    }

    public String getInstruct() {
        return instruct;
    }

    public void setInstruct(String instruct) {
        this.instruct = instruct == null ? null : instruct.trim();
    }

    public String getExchangerPrice() {
        return exchangerPrice;
    }

    public void setExchangerPrice(String exchangerPrice) {
        this.exchangerPrice = exchangerPrice == null ? null : exchangerPrice.trim();
    }

    public String getMeteringChargingType() {
        return meteringChargingType;
    }

    public void setMeteringChargingType(String meteringChargingType) {
        this.meteringChargingType = meteringChargingType == null ? null : meteringChargingType.trim();
    }

    public String getApproveRes() {
        return approveRes;
    }

    public void setApproveRes(String approveRes) {
        this.approveRes = approveRes == null ? null : approveRes.trim();
    }

    public String getApproveSerial() {
        return approveSerial;
    }

    public void setApproveSerial(String approveSerial) {
        this.approveSerial = approveSerial == null ? null : approveSerial.trim();
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

    public BigDecimal getPrePriceTmp() {
        return prePriceTmp;
    }

    public void setPrePriceTmp(BigDecimal prePriceTmp) {
        this.prePriceTmp = prePriceTmp;
    }

    public BigDecimal getAreaPriceTmp() {
        return areaPriceTmp;
    }

    public void setAreaPriceTmp(BigDecimal areaPriceTmp) {
        this.areaPriceTmp = areaPriceTmp;
    }

    public BigDecimal getHeatPriceTmp() {
        return heatPriceTmp;
    }

    public void setHeatPriceTmp(BigDecimal heatPriceTmp) {
        this.heatPriceTmp = heatPriceTmp;
    }

    public BigDecimal getExchangerPriceTmp() {
        return exchangerPriceTmp;
    }

    public void setExchangerPriceTmp(BigDecimal exchangerPriceTmp) {
        this.exchangerPriceTmp = exchangerPriceTmp;
    }
}
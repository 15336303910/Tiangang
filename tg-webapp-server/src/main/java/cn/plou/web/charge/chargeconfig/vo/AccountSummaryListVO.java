package cn.plou.web.charge.chargeconfig.vo;

//import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

//@Data
public class AccountSummaryListVO {

    private String accountTimeRange;//缴费日期 范围
    private Date minAccountTime;//缴费日期 从
    private Date maxAccountTime;//缴费日期 到
    private String address;//统计对象
    private String id;//id 不需要在页面展示
    private BigDecimal advHeatCost;//预收总额
    private BigDecimal sumAccount;//实收总额
    private BigDecimal oweAccount;//欠费总额
    private BigDecimal accountRate;//收费率
    private BigDecimal sumReceivable;//费用总额
    private BigDecimal marginNow;//暖费余额
    private Integer accountTimes;//缴费次数
    private Integer billTimes;//开票次数
    private Integer notBillTimes;//未开票次数
    private BigDecimal billAccount;//开票金额
    private BigDecimal notBillAccount;//未开票金额


    public String getAccountTimeRange() {
        return accountTimeRange;
    }

    public void setAccountTimeRange(String accountTimeRange) {
        this.accountTimeRange = accountTimeRange;
    }

    public Date getMinAccountTime() {
        return minAccountTime;
    }

    public void setMinAccountTime(Date minAccountTime) {
        this.minAccountTime = minAccountTime;
    }

    public Date getMaxAccountTime() {
        return maxAccountTime;
    }

    public void setMaxAccountTime(Date maxAccountTime) {
        this.maxAccountTime = maxAccountTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAdvHeatCost() {
        return advHeatCost;
    }

    public void setAdvHeatCost(BigDecimal advHeatCost) {
        this.advHeatCost = advHeatCost;
    }

    public BigDecimal getSumAccount() {
        return sumAccount;
    }

    public void setSumAccount(BigDecimal sumAccount) {
        this.sumAccount = sumAccount;
    }

    public BigDecimal getOweAccount() {
        return oweAccount;
    }

    public void setOweAccount(BigDecimal oweAccount) {
        this.oweAccount = oweAccount;
    }

    public BigDecimal getAccountRate() {
        return accountRate;
    }

    public void setAccountRate(BigDecimal accountRate) {
        this.accountRate = accountRate;
    }

    public BigDecimal getSumReceivable() {
        return sumReceivable;
    }

    public void setSumReceivable(BigDecimal sumReceivable) {
        this.sumReceivable = sumReceivable;
    }

    public BigDecimal getMarginNow() {
        return marginNow;
    }

    public void setMarginNow(BigDecimal marginNow) {
        this.marginNow = marginNow;
    }

    public Integer getAccountTimes() {
        return accountTimes;
    }

    public void setAccountTimes(Integer accountTimes) {
        this.accountTimes = accountTimes;
    }

    public Integer getBillTimes() {
        return billTimes;
    }

    public void setBillTimes(Integer billTimes) {
        this.billTimes = billTimes;
    }

    public Integer getNotBillTimes() {
        return notBillTimes;
    }

    public void setNotBillTimes(Integer notBillTimes) {
        this.notBillTimes = notBillTimes;
    }

    public BigDecimal getBillAccount() {
        return billAccount;
    }

    public void setBillAccount(BigDecimal billAccount) {
        this.billAccount = billAccount;
    }

    public BigDecimal getNotBillAccount() {
        return notBillAccount;
    }

    public void setNotBillAccount(BigDecimal notBillAccount) {
        this.notBillAccount = notBillAccount;
    }

}

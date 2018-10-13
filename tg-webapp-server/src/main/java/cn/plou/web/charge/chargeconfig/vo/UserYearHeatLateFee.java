package cn.plou.web.charge.chargeconfig.vo;

import java.math.BigDecimal;
import java.util.Date;



public class UserYearHeatLateFee {

    private String consumerId;

    private String annual;

    private String companyId;

    //滞纳金
    private String lateFee;

    //滞纳金计算日期
    private Date overdueBeginTime;

    //滞纳金率
    private BigDecimal overdueRate;

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getAnnual() {
        return annual;
    }

    public void setAnnual(String annual) {
        this.annual = annual;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getLateFee() {
        return lateFee;
    }

    public void setLateFee(String lateFee) {
        this.lateFee = lateFee;
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

}
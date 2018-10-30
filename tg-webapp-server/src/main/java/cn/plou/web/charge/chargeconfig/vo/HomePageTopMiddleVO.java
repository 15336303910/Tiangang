package cn.plou.web.charge.chargeconfig.vo;

import java.math.BigDecimal;

public class HomePageTopMiddleVO {
    private BigDecimal sumAdvHeatCost;
    private BigDecimal sumAccount;
    private BigDecimal accountRate;
    public BigDecimal getSumAdvHeatCost() {
        return sumAdvHeatCost;
    }

    public void setSumAdvHeatCost(BigDecimal sumAdvHeatCost) {
        this.sumAdvHeatCost = sumAdvHeatCost;
    }

    public BigDecimal getSumAccount() {
        return sumAccount;
    }

    public void setSumAccount(BigDecimal sumAccount) {
        this.sumAccount = sumAccount;
    }

    public BigDecimal getAccountRate() {
        return accountRate;
    }

    public void setAccountRate(BigDecimal accountRate) {
        this.accountRate = accountRate;
    }
}

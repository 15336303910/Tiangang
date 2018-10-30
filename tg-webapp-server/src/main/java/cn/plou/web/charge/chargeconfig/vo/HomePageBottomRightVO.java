package cn.plou.web.charge.chargeconfig.vo;

import java.math.BigDecimal;

public class HomePageBottomRightVO {
    private String accountTime;
    private BigDecimal sumAccount;

    public String getAccountTime() {
        return accountTime;
    }

    public void setAccountTime(String accountTime) {
        this.accountTime = accountTime;
    }

    public BigDecimal getSumAccount() {
        return sumAccount;
    }

    public void setSumAccount(BigDecimal sumAccount) {
        this.sumAccount = sumAccount;
    }
}

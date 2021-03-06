package cn.plou.web.charge.chargeconfig.vo;

//import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

//@Data
public class AccountDetailListVO {
    private String accountChannel;//缴费通道
    private String accountType;//缴费方式
    private Integer accountTimesTotal;//收费笔数
    private BigDecimal accountCostTotal;//收费金额

    public String getAccountChannel() {
        return accountChannel;
    }

    public void setAccountChannel(String accountChannel) {
        this.accountChannel = accountChannel;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Integer getAccountTimesTotal() {
        return accountTimesTotal;
    }

    public void setAccountTimesTotal(Integer accountTimesTotal) {
        this.accountTimesTotal = accountTimesTotal;
    }

    public BigDecimal getAccountCostTotal() {
        return accountCostTotal;
    }

    public void setAccountCostTotal(BigDecimal accountCostTotal) {
        this.accountCostTotal = accountCostTotal;
    }


}

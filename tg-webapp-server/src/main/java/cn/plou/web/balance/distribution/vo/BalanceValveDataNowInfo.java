package cn.plou.web.balance.distribution.vo;

import cn.plou.web.balance.distribution.entity.BalanceValveDataNow;

import java.math.BigDecimal;

public class BalanceValveDataNowInfo extends BalanceValveDataNow {
    private BigDecimal flowingDeviation;

    public BigDecimal getFlowingDeviation() {
        return flowingDeviation;
    }

    public void setFlowingDeviation(BigDecimal flowingDeviation) {
        this.flowingDeviation = flowingDeviation;
    }
}

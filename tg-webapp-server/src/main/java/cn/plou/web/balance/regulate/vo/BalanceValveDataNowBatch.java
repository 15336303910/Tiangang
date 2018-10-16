package cn.plou.web.balance.regulate.vo;

import cn.plou.web.balance.distribution.entity.BalanceValveDataNow;

import java.util.List;

public class BalanceValveDataNowBatch extends BalanceValveDataNow {
    List<String> meterIds;
    
    public List<String> getMeterIds() {
        return meterIds;
    }

    public void setMeterIds(List<String> meterIds) {
        this.meterIds = meterIds;
    }
}

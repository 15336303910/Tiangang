package cn.plou.web.balance.dataAnalysis.vo;

import cn.plou.web.balance.dataAnalysis.entity.BalanceValveData;

public class BalanceValveDataInfo extends BalanceValveData {
    private String address2nd;
    private String address;

    public String getAddress2nd() {
        return address2nd;
    }

    public void setAddress2nd(String address2nd) {
        this.address2nd = address2nd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}

package cn.plou.web.charge.chargeconfig.vo;

import cn.plou.web.charge.chargeconfig.entity.UserYearReceivableDetail;

public class UserYearReceivableDetailInfo extends UserYearReceivableDetail {


    private String chargingItemName;
    private String approveResName;


    public String getChargingItemName() {
        return chargingItemName;
    }

    public void setChargingItemName(String chargingItemName) {
        this.chargingItemName = chargingItemName;
    }

    public String getApproveResName() {
        return approveResName;
    }

    public void setApproveResName(String approveResName) {
        this.approveResName = approveResName;
    }

}

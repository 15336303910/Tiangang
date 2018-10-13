package cn.plou.web.charge.chargeconfig.vo;

import cn.plou.web.charge.chargeconfig.entity.UserYearHeat;

public class UserYearHeatDetailInfo extends UserYearHeat {

    private String heatUserTypeName;
    private String payOverName;
    private String aroundHeatingName;
    private String heatingStatusName;

    public String getHeatUserTypeName() {
        return heatUserTypeName;
    }

    public void setHeatUserTypeName(String heatUserTypeName) {
        this.heatUserTypeName = heatUserTypeName;
    }

    public String getPayOverName() {
        return payOverName;
    }

    public void setPayOverName(String payOverName) {
        this.payOverName = payOverName;
    }

    public String getAroundHeatingName() {
        return aroundHeatingName;
    }

    public void setAroundHeatingName(String aroundHeatingName) {
        this.aroundHeatingName = aroundHeatingName;
    }

    public String getHeatingStatusName() {
        return heatingStatusName;
    }

    public void setHeatingStatusName(String heatingStatusName) {
        this.heatingStatusName = heatingStatusName;
    }




//    pay_over_name
//            heat_user_type_name
//    around_heating_name
//    heating_status_name
}

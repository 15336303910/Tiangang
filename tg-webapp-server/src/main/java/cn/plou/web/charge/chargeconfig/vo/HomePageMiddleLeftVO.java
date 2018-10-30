package cn.plou.web.charge.chargeconfig.vo;

import java.math.BigDecimal;

public class HomePageMiddleLeftVO {

    private String annual;
    private BigDecimal sumHeatingArea;
    private Integer houseCount;

    public String getAnnual() {
        return annual;
    }

    public void setAnnual(String annual) {
        this.annual = annual;
    }

    public BigDecimal getSumHeatingArea() {
        return sumHeatingArea;
    }

    public void setSumHeatingArea(BigDecimal sumHeatingArea) {
        this.sumHeatingArea = sumHeatingArea;
    }

    public Integer getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(Integer houseCount) {
        this.houseCount = houseCount;
    }
}

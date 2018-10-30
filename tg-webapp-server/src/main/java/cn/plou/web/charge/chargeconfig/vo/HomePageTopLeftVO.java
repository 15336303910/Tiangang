package cn.plou.web.charge.chargeconfig.vo;

import java.math.BigDecimal;
//最左上角那个图
public class HomePageTopLeftVO {
    private String heatingStatus;
    private Integer sumHouseCount;
    private BigDecimal sumHeatingArea;

    public String getHeatingStatus() {
        return heatingStatus;
    }

    public void setHeatingStatus(String heatingStatus) {
        this.heatingStatus = heatingStatus;
    }

    public Integer getSumHouseCount() {
        return sumHouseCount;
    }

    public void setSumHouseCount(Integer sumHouseCount) {
        this.sumHouseCount = sumHouseCount;
    }

    public BigDecimal getSumHeatingArea() {
        return sumHeatingArea;
    }

    public void setSumHeatingArea(BigDecimal sumHeatingArea) {
        this.sumHeatingArea = sumHeatingArea;
    }

}

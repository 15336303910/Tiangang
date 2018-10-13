package cn.plou.web.charge.chargeconfig.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description:
 * @Param:
 * @Return:
 * @Author:
 * @Date:
 */
@Data
public class ChargeSumVO {

    private String heatUserType; // 收费类型

    private String heatingArea; // 供热面积

    private String prePrice; // 预收单价

    private String priceName;

    private String advHeatCost; // 预收热费金额

    private String payArea; // 收费面积

    private String areaTotal; // 面积费用

    private String totalValue; // 供热流量

    private String heatTotal; // 热量费用

    private String sumReceivable; // 热费应收合计

    public String getHeatUserType() {
        return heatUserType;
    }

    public void setHeatUserType(String heatUserType) {
        this.heatUserType = heatUserType;
    }

    public String getHeatingArea() {
        return heatingArea;
    }

    public void setHeatingArea(String heatingArea) {
        this.heatingArea = heatingArea;
    }

    public String getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(String prePrice) {
        this.prePrice = prePrice;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public String getAdvHeatCost() {
        return advHeatCost;
    }

    public void setAdvHeatCost(String advHeatCost) {
        this.advHeatCost = advHeatCost;
    }

    public String getPayArea() {
        return payArea;
    }

    public void setPayArea(String payArea) {
        this.payArea = payArea;
    }

    public String getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(String areaTotal) {
        this.areaTotal = areaTotal;
    }

    public String getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(String totalValue) {
        this.totalValue = totalValue;
    }

    public String getHeatTotal() {
        return heatTotal;
    }

    public void setHeatTotal(String heatTotal) {
        this.heatTotal = heatTotal;
    }

    public String getSumReceivable() {
        return sumReceivable;
    }

    public void setSumReceivable(String sumReceivable) {
        this.sumReceivable = sumReceivable;
    }


}
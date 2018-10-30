package cn.plou.web.charge.chargeconfig.vo;

import java.util.Date;

public class BatchAdjustHeatInfoVO {

    private String commuityId;
    private String areaPriceType;
    private String heatUserType;

    private Date startDate;
    private Date endDate;


    public String getCommuityId() {
        return commuityId;
    }
    public void setCommuityId(String commuityId) {
        this.commuityId = commuityId;
    }

    public String getAreaPriceType() {
        return areaPriceType;
    }
    public void setAreaPriceType(String areaPriceType) {
        this.areaPriceType = areaPriceType;
    }
    public String getHeatUserType() {
        return heatUserType;
    }
    public void setHeatUserType(String heatUserType) {
        this.heatUserType = heatUserType;
    }

    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}

package cn.plou.web.heatManage.dataAnalysis.vo;

import java.math.BigDecimal;

public class HeatDistributionData {

    private String companyId;
    private String communityId;
    private String consumerId;


    // 瞬时流量
    private BigDecimal flowSpeed;

    // 累计热量
    private String heat;

    // 耗热指标
    private BigDecimal heatingIndex;

    // 流量指标指标
    private BigDecimal flowingIndex;

    // 室温指标
    private BigDecimal roomTemperatureRead;


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String primaryId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId == null ? null : communityId.trim();
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId ;
    }

    public BigDecimal getFlowSpeed() {
        return flowSpeed;
    }

    public void setFlowSpeed(BigDecimal flowSpeed) {
        this.flowSpeed = flowSpeed;
    }
    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    public BigDecimal getHeatingIndex() {
        return heatingIndex;
    }

    public void setHeatingIndex(BigDecimal heatingIndex) {
        this.heatingIndex = heatingIndex;
    }
    public BigDecimal getFlowingIndex() {
        return flowingIndex;
    }

    public void setFlowingIndexTotal(BigDecimal flowingIndex) {
        this.flowingIndex = flowingIndex;
    }

    public BigDecimal getRoomTemperatureRead() {
        return roomTemperatureRead;
    }

    public void setRoomTemperatureRead(BigDecimal roomTemperatureRead) {
        this.roomTemperatureRead = roomTemperatureRead;
    }
}

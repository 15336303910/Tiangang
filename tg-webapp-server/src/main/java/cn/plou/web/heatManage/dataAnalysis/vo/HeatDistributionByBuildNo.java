package cn.plou.web.heatManage.dataAnalysis.vo;

import java.math.BigDecimal;

public class HeatDistributionByBuildNo {

    private String companyId;

    // 换热站Id
    private String stationId;

    private String station;

    private String buildNo;
    private String longitude;

    private String latitude;
    // 瞬时流量
    private BigDecimal flowSpeedTotal;

    // 累计热量
    private String heatTotal;

    // 耗热指标
    private BigDecimal heatingIndexTotal;

    // 流量指标指标
    private BigDecimal flowingIndexTotal;

    // 室温指标
    private BigDecimal roomTemperatureReadTotal;

    // 设备故障
    private String equipmentIssue;

    // 通讯故障
    private String communicationIssue;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String primaryId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station ;
    }

    public String getBuildNo() {
        return buildNo;
    }

    public void setBuildNo(String buildNo) {
        this.buildNo = buildNo ;
    }

    public BigDecimal getFlowSpeedTotal() {
        return flowSpeedTotal;
    }

    public void setFlowSpeedTotal(BigDecimal flowSpeedTotal) {
        this.flowSpeedTotal = flowSpeedTotal;
    }
    public String getHeatTotal() {
        return heatTotal;
    }

    public void setHeat(String heatTotal) {
        this.heatTotal = heatTotal;
    }
    public BigDecimal getHeatingIndexTotal() {
        return heatingIndexTotal;
    }

    public void setHeatingIndexTotal(BigDecimal heatingIndexTotal) {
        this.heatingIndexTotal = heatingIndexTotal;
    }
    public BigDecimal getFlowingIndexTotal() {
        return flowingIndexTotal;
    }

    public void setFlowingIndexTotal(BigDecimal flowingIndexTotal) {
        this.flowingIndexTotal = flowingIndexTotal;
    }

    public BigDecimal getRoomTemperatureReadTotal() {
        return roomTemperatureReadTotal;
    }

    public void setRoomTemperatureReadTotal(BigDecimal roomTemperatureReadTotal) {
        this.roomTemperatureReadTotal = roomTemperatureReadTotal;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}

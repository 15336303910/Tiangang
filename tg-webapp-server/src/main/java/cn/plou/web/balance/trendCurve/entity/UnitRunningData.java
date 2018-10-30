package cn.plou.web.balance.trendCurve.entity;

import java.math.BigDecimal;
import java.util.Date;

public class UnitRunningData {
    private String primaryId;

    private String consumerId;

    private String systemId;

    private String heat;

    private BigDecimal power;

    private BigDecimal flowSpeed;

    private BigDecimal flowUpperLimit;

    private BigDecimal inTemperature;

    private BigDecimal outTemperature;

    private BigDecimal roomTemperature;

    private BigDecimal outdoorTemperature;

    private BigDecimal heatingArea;

    private BigDecimal heatingIndex;

    private BigDecimal flowingIndex;

    private Date systemReadTime;

    private String companyId;

    private BigDecimal adjHeatingIndex;

    private String sourceType;
    private BigDecimal inPressure;
    private BigDecimal outPressure;
    private BigDecimal pressureDiff;
    private BigDecimal setPressureDiff;
    private BigDecimal setTemperature;
    private BigDecimal limitFlow;
    private BigDecimal temperatureDiff;

    public String getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId == null ? null : primaryId.trim();
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId == null ? null : consumerId.trim();
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId == null ? null : systemId.trim();
    }

    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat == null ? null : heat.trim();
    }

    public BigDecimal getPower() {
        return power;
    }

    public void setPower(BigDecimal power) {
        this.power = power;
    }

    public BigDecimal getFlowSpeed() {
        return flowSpeed;
    }

    public void setFlowSpeed(BigDecimal flowSpeed) {
        this.flowSpeed = flowSpeed;
    }

    public BigDecimal getFlowUpperLimit() {
        return flowUpperLimit;
    }

    public void setFlowUpperLimit(BigDecimal flowUpperLimit) {
        this.flowUpperLimit = flowUpperLimit;
    }

    public BigDecimal getInTemperature() {
        return inTemperature;
    }

    public void setInTemperature(BigDecimal inTemperature) {
        this.inTemperature = inTemperature;
    }

    public BigDecimal getOutTemperature() {
        return outTemperature;
    }

    public void setOutTemperature(BigDecimal outTemperature) {
        this.outTemperature = outTemperature;
    }

    public BigDecimal getRoomTemperature() {
        return roomTemperature;
    }

    public void setRoomTemperature(BigDecimal roomTemperature) {
        this.roomTemperature = roomTemperature;
    }

    public BigDecimal getOutdoorTemperature() {
        return outdoorTemperature;
    }

    public void setOutdoorTemperature(BigDecimal outdoorTemperature) {
        this.outdoorTemperature = outdoorTemperature;
    }

    public BigDecimal getHeatingArea() {
        return heatingArea;
    }

    public void setHeatingArea(BigDecimal heatingArea) {
        this.heatingArea = heatingArea;
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

    public void setFlowingIndex(BigDecimal flowingIndex) {
        this.flowingIndex = flowingIndex;
    }

    public Date getSystemReadTime() {
        return systemReadTime;
    }

    public void setSystemReadTime(Date systemReadTime) {
        this.systemReadTime = systemReadTime;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public BigDecimal getAdjHeatingIndex() {
        return adjHeatingIndex;
    }

    public void setAdjHeatingIndex(BigDecimal adjHeatingIndex) {
        this.adjHeatingIndex = adjHeatingIndex;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType == null ? null : sourceType.trim();
    }

    public BigDecimal getInPressure() {
        return inPressure;
    }

    public void setInPressure(BigDecimal inPressure) {
        this.inPressure = inPressure;
    }

    public BigDecimal getOutPressure() {
        return outPressure;
    }

    public void setOutPressure(BigDecimal outPressure) {
        this.outPressure = outPressure;
    }

    public BigDecimal getPressureDiff() {
        return pressureDiff;
    }

    public void setPressureDiff(BigDecimal pressureDiff) {
        this.pressureDiff = pressureDiff;
    }

    public BigDecimal getSetPressureDiff() {
        return setPressureDiff;
    }

    public void setSetPressureDiff(BigDecimal setPressureDiff) {
        this.setPressureDiff = setPressureDiff;
    }

    public BigDecimal getSetTemperature() {
        return setTemperature;
    }

    public void setSetTemperature(BigDecimal setTemperature) {
        this.setTemperature = setTemperature;
    }

    public BigDecimal getLimitFlow() {
        return limitFlow;
    }

    public void setLimitFlow(BigDecimal limitFlow) {
        this.limitFlow = limitFlow;
    }

    public BigDecimal getTemperatureDiff() {
        return temperatureDiff;
    }

    public void setTemperatureDiff(BigDecimal temperatureDiff) {
        this.temperatureDiff = temperatureDiff;
    }
}
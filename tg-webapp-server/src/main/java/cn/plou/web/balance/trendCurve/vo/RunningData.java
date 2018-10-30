package cn.plou.web.balance.trendCurve.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
@ApiModel
public class RunningData {
    private String primaryId;

    private String consumerId;

    private String systemId;
    @ApiModelProperty(value="累计热量")
    private String heat;
    @ApiModelProperty(value="瞬时功率")
    private BigDecimal power;
    @ApiModelProperty(value="瞬时流量")
    private BigDecimal flowSpeed;
    @ApiModelProperty(value="进水温度")
    private BigDecimal inTemperature;
    @ApiModelProperty(value="回水温度")
    private BigDecimal outTemperature;
    @ApiModelProperty(value="室温测量值")
    private BigDecimal roomTemperature;
    @ApiModelProperty(value="室外温度值")
    private BigDecimal outdoorTemperature;
    @ApiModelProperty(value="供暖面积")
    private BigDecimal heatingArea;
    @ApiModelProperty(value="耗热指标")
    private BigDecimal heatingIndex;
    @ApiModelProperty(value="流量指标")
    private BigDecimal flowingIndex;
    @ApiModelProperty(value="时间")
    private Date systemReadTime;

    private String companyId;
    @ApiModelProperty(value="折算热指标")
    private BigDecimal adjHeatingIndex;
    @ApiModelProperty(value="数据来源：默认为１，１为单元　２位用户，３为楼")
    private String sourceType;
    private BigDecimal inPressure;
    private BigDecimal outPressure;
    private BigDecimal pressureDiff;
    private BigDecimal setPressureDiff;
    private BigDecimal setTemperature;
    private BigDecimal limitFlow;
    private BigDecimal temperatureDiff;
    private BigDecimal sampleFlowSpeed;

    public BigDecimal getSampleFlowSpeed() {
        return sampleFlowSpeed;
    }

    public void setSampleFlowSpeed(BigDecimal sampleFlowSpeed) {
        this.sampleFlowSpeed = sampleFlowSpeed;
    }

    public String getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat;
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
        this.companyId = companyId;
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
        this.sourceType = sourceType;
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

package cn.plou.web.balance.distribution.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
@ApiModel
public class BalanceValveDataNow {

    private String meterId;

    private String consumerId;
    @ApiModelProperty(value="控制模式：手动控制、压差控制、流量控制、温度控制、时间段控制等")
    private String controlMode;
    @ApiModelProperty(value="开度")
    private BigDecimal openness;
    @ApiModelProperty(value="进水压力")
    private BigDecimal inPressure;
    @ApiModelProperty(value="回水压力")
    private BigDecimal outPressure;
    @ApiModelProperty(value="供回压差")
    private BigDecimal pressureDiff;
    @ApiModelProperty(value="压差设定值")
    private BigDecimal setPressureDiff;
    @ApiModelProperty(value="压差阈值")
    private BigDecimal pressureDiffThreshold;
    @ApiModelProperty(value="进水温度")
    private BigDecimal inTemperature;
    @ApiModelProperty(value="回水温度")
    private BigDecimal outTemperature;
    @ApiModelProperty(value="设定温度")
    private BigDecimal setTemperature;
    @ApiModelProperty(value="温度阀值")
    private BigDecimal temperatureThreshold;
    @ApiModelProperty(value="样本瞬时流量")
    private BigDecimal sampleFlowSpeed;
    @ApiModelProperty(value="单元瞬时流量")
    private BigDecimal unitFlowSpeed;
    @ApiModelProperty(value="流量上限值（ｌ／ｈ）")
    private BigDecimal upperLimitFlow;
    @ApiModelProperty(value="流量阈值（ｌ／ｈ）")
    private BigDecimal maxFlowThreshold;
    @ApiModelProperty(value="读取时间")
    private Date sysReadTime;

    private String companyId;

    private String notes;

    private String memo1;

    private String memo2;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;
    @ApiModelProperty(value="环境温度")
    private BigDecimal envTime;
    @ApiModelProperty(value="设定开度")
    private BigDecimal openSet;
    @ApiModelProperty(value="开度阈值")
    private BigDecimal openLevel;
    @ApiModelProperty(value="基本开度最大值")
    private String baseOpenMax;
    @ApiModelProperty(value="基本开度最小值")
    private String baseOpenMin;
    @ApiModelProperty(value="最低温度")
    private BigDecimal lowTemp;
    @ApiModelProperty(value="温控间隔")
    private BigDecimal comInterval;
    @ApiModelProperty(value="温度测量换位")
    private String exchangeTemp;
    @ApiModelProperty(value="压力测量换位")
    private String exchangePress;
    @ApiModelProperty(value="锁定／解锁状态")
    private String locks;
    @ApiModelProperty(value="时间段参数１")
    private String timeParam1;
    @ApiModelProperty(value="时间段参数2")
    private String timeParam2;
    @ApiModelProperty(value="时间段参数4")
    private String timeParam4;
    @ApiModelProperty(value="温度曲线参数")
    private String tempParam;
    @ApiModelProperty(value="时间段１开度参数")
    private String timeOpenParam1;
    @ApiModelProperty(value="时间段2开度参数")
    private String timeOpenParam2;
    @ApiModelProperty(value="时间段3开度参数")
    private String timeOpenParam3;
    @ApiModelProperty(value="时间段１温度参数")
    private String timeTempParam1;
    @ApiModelProperty(value="时间段2温度参数")
    private String timeTempParam2;
    @ApiModelProperty(value="时间段3温度参数")
    private String timeTempParam3;
    @ApiModelProperty(value="时间段１温度曲线参数")
    private String timeTempCureParam1;
    @ApiModelProperty(value="时间段2温度曲线参数")
    private String timeTempCureParam2;
    @ApiModelProperty(value="时间段3温度曲线参数")
    private String timeTempCureParam3;
    @ApiModelProperty(value="出水压力修正值")
    private String pressAdjOut;
    @ApiModelProperty(value="进水压力修正值")
    private String pressAdjIn;
    @ApiModelProperty(value="阀当前时间")
    private Date currentTimes;
    @ApiModelProperty(value="数据返回时间")
    private Date readTime;
    @ApiModelProperty(value="仪表时间")
    private Date meterTime;
    @ApiModelProperty(value="")
    private Integer openSetAdd;

    private String primaryId;
    @ApiModelProperty(value="数据来源")
    private String flowSource;
    @ApiModelProperty(value="")
    private String flowSettingMode;
    @ApiModelProperty(value="")
    private String controlAddr;

    private String mbusId;

    private String address2nd;

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId == null ? null : meterId.trim();
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId == null ? null : consumerId.trim();
    }

    public String getControlMode() {
        return controlMode;
    }

    public void setControlMode(String controlMode) {
        this.controlMode = controlMode == null ? null : controlMode.trim();
    }

    public BigDecimal getOpenness() {
        return openness;
    }

    public void setOpenness(BigDecimal openness) {
        this.openness = openness;
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

    public BigDecimal getPressureDiffThreshold() {
        return pressureDiffThreshold;
    }

    public void setPressureDiffThreshold(BigDecimal pressureDiffThreshold) {
        this.pressureDiffThreshold = pressureDiffThreshold;
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

    public BigDecimal getSetTemperature() {
        return setTemperature;
    }

    public void setSetTemperature(BigDecimal setTemperature) {
        this.setTemperature = setTemperature;
    }

    public BigDecimal getTemperatureThreshold() {
        return temperatureThreshold;
    }

    public void setTemperatureThreshold(BigDecimal temperatureThreshold) {
        this.temperatureThreshold = temperatureThreshold;
    }

    public BigDecimal getSampleFlowSpeed() {
        return sampleFlowSpeed;
    }

    public void setSampleFlowSpeed(BigDecimal sampleFlowSpeed) {
        this.sampleFlowSpeed = sampleFlowSpeed;
    }

    public BigDecimal getUnitFlowSpeed() {
        return unitFlowSpeed;
    }

    public void setUnitFlowSpeed(BigDecimal unitFlowSpeed) {
        this.unitFlowSpeed = unitFlowSpeed;
    }

    public BigDecimal getUpperLimitFlow() {
        return upperLimitFlow;
    }

    public void setUpperLimitFlow(BigDecimal upperLimitFlow) {
        this.upperLimitFlow = upperLimitFlow;
    }

    public BigDecimal getMaxFlowThreshold() {
        return maxFlowThreshold;
    }

    public void setMaxFlowThreshold(BigDecimal maxFlowThreshold) {
        this.maxFlowThreshold = maxFlowThreshold;
    }

    public Date getSysReadTime() {
        return sysReadTime;
    }

    public void setSysReadTime(Date sysReadTime) {
        this.sysReadTime = sysReadTime;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public String getMemo1() {
        return memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1 == null ? null : memo1.trim();
    }

    public String getMemo2() {
        return memo2;
    }

    public void setMemo2(String memo2) {
        this.memo2 = memo2 == null ? null : memo2.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public BigDecimal getEnvTime() {
        return envTime;
    }

    public void setEnvTime(BigDecimal envTime) {
        this.envTime = envTime;
    }

    public BigDecimal getOpenSet() {
        return openSet;
    }

    public void setOpenSet(BigDecimal openSet) {
        this.openSet = openSet;
    }

    public BigDecimal getOpenLevel() {
        return openLevel;
    }

    public void setOpenLevel(BigDecimal openLevel) {
        this.openLevel = openLevel;
    }

    public String getBaseOpenMax() {
        return baseOpenMax;
    }

    public void setBaseOpenMax(String baseOpenMax) {
        this.baseOpenMax = baseOpenMax == null ? null : baseOpenMax.trim();
    }

    public String getBaseOpenMin() {
        return baseOpenMin;
    }

    public void setBaseOpenMin(String baseOpenMin) {
        this.baseOpenMin = baseOpenMin == null ? null : baseOpenMin.trim();
    }

    public BigDecimal getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(BigDecimal lowTemp) {
        this.lowTemp = lowTemp;
    }

    public BigDecimal getComInterval() {
        return comInterval;
    }

    public void setComInterval(BigDecimal comInterval) {
        this.comInterval = comInterval;
    }

    public String getExchangeTemp() {
        return exchangeTemp;
    }

    public void setExchangeTemp(String exchangeTemp) {
        this.exchangeTemp = exchangeTemp == null ? null : exchangeTemp.trim();
    }

    public String getExchangePress() {
        return exchangePress;
    }

    public void setExchangePress(String exchangePress) {
        this.exchangePress = exchangePress == null ? null : exchangePress.trim();
    }

    public String getLocks() {
        return locks;
    }

    public void setLocks(String locks) {
        this.locks = locks == null ? null : locks.trim();
    }

    public String getTimeParam1() {
        return timeParam1;
    }

    public void setTimeParam1(String timeParam1) {
        this.timeParam1 = timeParam1 == null ? null : timeParam1.trim();
    }

    public String getTimeParam2() {
        return timeParam2;
    }

    public void setTimeParam2(String timeParam2) {
        this.timeParam2 = timeParam2 == null ? null : timeParam2.trim();
    }

    public String getTimeParam4() {
        return timeParam4;
    }

    public void setTimeParam4(String timeParam4) {
        this.timeParam4 = timeParam4 == null ? null : timeParam4.trim();
    }

    public String getTempParam() {
        return tempParam;
    }

    public void setTempParam(String tempParam) {
        this.tempParam = tempParam == null ? null : tempParam.trim();
    }

    public String getTimeOpenParam1() {
        return timeOpenParam1;
    }

    public void setTimeOpenParam1(String timeOpenParam1) {
        this.timeOpenParam1 = timeOpenParam1 == null ? null : timeOpenParam1.trim();
    }

    public String getTimeOpenParam2() {
        return timeOpenParam2;
    }

    public void setTimeOpenParam2(String timeOpenParam2) {
        this.timeOpenParam2 = timeOpenParam2 == null ? null : timeOpenParam2.trim();
    }

    public String getTimeOpenParam3() {
        return timeOpenParam3;
    }

    public void setTimeOpenParam3(String timeOpenParam3) {
        this.timeOpenParam3 = timeOpenParam3 == null ? null : timeOpenParam3.trim();
    }

    public String getTimeTempParam1() {
        return timeTempParam1;
    }

    public void setTimeTempParam1(String timeTempParam1) {
        this.timeTempParam1 = timeTempParam1 == null ? null : timeTempParam1.trim();
    }

    public String getTimeTempParam2() {
        return timeTempParam2;
    }

    public void setTimeTempParam2(String timeTempParam2) {
        this.timeTempParam2 = timeTempParam2 == null ? null : timeTempParam2.trim();
    }

    public String getTimeTempParam3() {
        return timeTempParam3;
    }

    public void setTimeTempParam3(String timeTempParam3) {
        this.timeTempParam3 = timeTempParam3 == null ? null : timeTempParam3.trim();
    }

    public String getTimeTempCureParam1() {
        return timeTempCureParam1;
    }

    public void setTimeTempCureParam1(String timeTempCureParam1) {
        this.timeTempCureParam1 = timeTempCureParam1 == null ? null : timeTempCureParam1.trim();
    }

    public String getTimeTempCureParam2() {
        return timeTempCureParam2;
    }

    public void setTimeTempCureParam2(String timeTempCureParam2) {
        this.timeTempCureParam2 = timeTempCureParam2 == null ? null : timeTempCureParam2.trim();
    }

    public String getTimeTempCureParam3() {
        return timeTempCureParam3;
    }

    public void setTimeTempCureParam3(String timeTempCureParam3) {
        this.timeTempCureParam3 = timeTempCureParam3 == null ? null : timeTempCureParam3.trim();
    }

    public String getPressAdjOut() {
        return pressAdjOut;
    }

    public void setPressAdjOut(String pressAdjOut) {
        this.pressAdjOut = pressAdjOut == null ? null : pressAdjOut.trim();
    }

    public String getPressAdjIn() {
        return pressAdjIn;
    }

    public void setPressAdjIn(String pressAdjIn) {
        this.pressAdjIn = pressAdjIn == null ? null : pressAdjIn.trim();
    }

    public Date getCurrentTimes() {
        return currentTimes;
    }

    public void setCurrentTimes(Date currentTimes) {
        this.currentTimes = currentTimes;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public Date getMeterTime() {
        return meterTime;
    }

    public void setMeterTime(Date meterTime) {
        this.meterTime = meterTime;
    }

    public Integer getOpenSetAdd() {
        return openSetAdd;
    }

    public void setOpenSetAdd(Integer openSetAdd) {
        this.openSetAdd = openSetAdd;
    }

    public String getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId == null ? null : primaryId.trim();
    }

    public String getFlowSource() {
        return flowSource;
    }

    public void setFlowSource(String flowSource) {
        this.flowSource = flowSource == null ? null : flowSource.trim();
    }

    public String getFlowSettingMode() {
        return flowSettingMode;
    }

    public void setFlowSettingMode(String flowSettingMode) {
        this.flowSettingMode = flowSettingMode == null ? null : flowSettingMode.trim();
    }

    public String getControlAddr() {
        return controlAddr;
    }

    public void setControlAddr(String controlAddr) {
        this.controlAddr = controlAddr == null ? null : controlAddr.trim();
    }

    public String getMbusId() {
        return mbusId;
    }

    public void setMbusId(String mbusId) {
        this.mbusId = mbusId == null ? null : mbusId.trim();
    }

    public String getAddress2nd() {
        return address2nd;
    }

    public void setAddress2nd(String address2nd) {
        this.address2nd = address2nd == null ? null : address2nd.trim();
    }
}
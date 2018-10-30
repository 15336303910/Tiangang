package cn.plou.web.heatManage.dataAnalysis.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SummaryCountDataDO implements Serializable {
	private String systemReadTime;	//统计时间
	private String consumerId;	//户id或者楼、小区、站的id
	private String address;	//地址
	private BigDecimal heat;	//耗热量
	private BigDecimal heatingArea;	//供热面积
	private BigDecimal flowSpeed;//平均流量
	private BigDecimal outdoorTemperature;/* <RF> 平均室外温度 */
	private BigDecimal roomTemperature;/* <F> 平均室内温度 */
	private BigDecimal heatingIndex; /* <R> 实际耗热指标 */
	private BigDecimal adjHeatingIndex;/* <F> 折算标准耗热指标 */
	private BigDecimal adjPowerIndex;/* <F> 年度参考能耗指标 */
	private BigDecimal flowingIndex;/* <RF> 流量指标 */

	public String getSystemReadTime() {
		return systemReadTime;
	}

	public void setSystemReadTime(String systemReadTime) {
		this.systemReadTime = systemReadTime;
	}

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getHeat() {
		return heat;
	}

	public void setHeat(BigDecimal heat) {
		this.heat = heat;
	}

	public BigDecimal getHeatingArea() {
		return heatingArea;
	}

	public void setHeatingArea(BigDecimal heatingArea) {
		this.heatingArea = heatingArea;
	}

	public BigDecimal getFlowSpeed() {
		return flowSpeed;
	}

	public void setFlowSpeed(BigDecimal flowSpeed) {
		this.flowSpeed = flowSpeed;
	}

	public BigDecimal getOutdoorTemperature() {
		return outdoorTemperature;
	}

	public void setOutdoorTemperature(BigDecimal outdoorTemperature) {
		this.outdoorTemperature = outdoorTemperature;
	}

	public BigDecimal getRoomTemperature() {
		return roomTemperature;
	}

	public void setRoomTemperature(BigDecimal roomTemperature) {
		this.roomTemperature = roomTemperature;
	}

	public BigDecimal getHeatingIndex() {
		return heatingIndex;
	}

	public void setHeatingIndex(BigDecimal heatingIndex) {
		this.heatingIndex = heatingIndex;
	}

	public BigDecimal getAdjHeatingIndex() {
		return adjHeatingIndex;
	}

	public void setAdjHeatingIndex(BigDecimal adjHeatingIndex) {
		this.adjHeatingIndex = adjHeatingIndex;
	}

	public BigDecimal getAdjPowerIndex() {
		return adjPowerIndex;
	}

	public void setAdjPowerIndex(BigDecimal adjPowerIndex) {
		this.adjPowerIndex = adjPowerIndex;
	}

	public BigDecimal getFlowingIndex() {
		return flowingIndex;
	}

	public void setFlowingIndex(BigDecimal flowingIndex) {
		this.flowingIndex = flowingIndex;
	}


}

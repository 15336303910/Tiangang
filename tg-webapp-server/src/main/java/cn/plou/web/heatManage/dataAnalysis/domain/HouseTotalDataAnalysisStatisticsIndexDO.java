package cn.plou.web.heatManage.dataAnalysis.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author admin
 *
 */
public class HouseTotalDataAnalysisStatisticsIndexDO implements Serializable {
	private static final long serialVersionUID = 1L;



	private String consumerId;
	private String companyId;
	private String address;

	//统计时间
	private String systemReadTime;
	//耗热量
	private BigDecimal heat;
	//供暖面积
	private BigDecimal heatingArea;
	//平均流量
	private BigDecimal avgFlowing;
	//平均室外温度
	private BigDecimal avgOutdoorTemperature;
	//平均室内温度
	private BigDecimal avgRoomTemperature;
	//实际耗热指标
	private BigDecimal heatingIndex;
	//折算标准耗热指标
	private BigDecimal adjHeatingIndex;
	//年度参考能耗指标
	private BigDecimal heatTarge;
	//流量指标
	private BigDecimal flowingIndex;
	//平均进水温度
	private BigDecimal avgInTemperature;
	//平均回水温度
	private BigDecimal avgOutTemperature;
	//平均温差
	private BigDecimal avgRoomTemperatureDifference;


	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public String getSystemReadTime() {
		return systemReadTime;
	}

	public void setSystemReadTime(String systemReadTime) {
		this.systemReadTime = systemReadTime;
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

	public BigDecimal getAvgFlowing() {
		return avgFlowing;
	}

	public void setAvgFlowing(BigDecimal avgFlowing) {
		this.avgFlowing = avgFlowing;
	}

	public BigDecimal getAvgOutdoorTemperature() {
		return avgOutdoorTemperature;
	}

	public void setAvgOutdoorTemperature(BigDecimal avgOutdoorTemperature) {
		this.avgOutdoorTemperature = avgOutdoorTemperature;
	}

	public BigDecimal getAvgRoomTemperature() {
		return avgRoomTemperature;
	}

	public void setAvgRoomTemperature(BigDecimal avgRoomTemperature) {
		this.avgRoomTemperature = avgRoomTemperature;
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

	public BigDecimal getHeatTarge() {
		return heatTarge;
	}

	public void setHeatTarge(BigDecimal heatTarge) {
		this.heatTarge = heatTarge;
	}

	public BigDecimal getFlowingIndex() {
		return flowingIndex;
	}

	public void setFlowingIndex(BigDecimal flowingIndex) {
		this.flowingIndex = flowingIndex;
	}

	public BigDecimal getAvgInTemperature() {
		return avgInTemperature;
	}

	public void setAvgInTemperature(BigDecimal avgInTemperature) {
		this.avgInTemperature = avgInTemperature;
	}

	public BigDecimal getAvgOutTemperature() {
		return avgOutTemperature;
	}

	public void setAvgOutTemperature(BigDecimal avgOutTemperature) {
		this.avgOutTemperature = avgOutTemperature;
	}

	public BigDecimal getAvgRoomTemperatureDifference() {
		return avgRoomTemperatureDifference;
	}

	public void setAvgRoomTemperatureDifference(BigDecimal avgRoomTemperatureDifference) {
		this.avgRoomTemperatureDifference = avgRoomTemperatureDifference;
	}

}

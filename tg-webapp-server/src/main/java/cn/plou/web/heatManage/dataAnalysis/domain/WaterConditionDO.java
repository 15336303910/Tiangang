package cn.plou.web.heatManage.dataAnalysis.domain;

import lombok.Data;

@Data
public class WaterConditionDO {
	//用户地址名称
	private String address = "";
	private String consumerId = "";
	private String systemId = "";
	private Double flowDesign = 0d;
	//供热面积
	private Double heatingArea = 0d;
	//平均流量
	private Double flow =0d; 
	//实际耗热指标
	private Double heatingIndex = 0d;
	//流量指标
	private Double flowingIndex = 0d;
	//平均供水温度
	private Double inTemperature = 0d;
	//平均回水温度
	private Double outTemperature = 0d;
	//平均温差
	private Double tmpDiff = 0d;
	//平均室内温度
	private Double roomTemperatureRead = 0d;
	//平均室外温度
	private Double outdoorTemperature = 0d;
	//水力失调度（以设计流量为计算依据）
	private Double flowDiffRatio = 0d;
	//平衡离散度
	private Double flowDiscrete = 0d;

}

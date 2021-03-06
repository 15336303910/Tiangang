package cn.plou.web.balance.distribution.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SystemStaticData {
	String systemId;				//系统id
	String systemName;	//系统名
	Double primaryInTemperature = 0d;	//"进水温度",
	Double primaryOutTemperature = 0d;//回水温度",
	Double outdoorSetTemperature = 0d;//室外设定温度",
	Double roomSetTemperature = 0d;//室内设定温度",
	Double heatingIndex = 0d;//热指标",
	Double producerDistance = 0d;//距离",
	Double hpumpNum = 0d;//热泵数量",
	Double basPressupwat = 0d;//供水压力",
	Double basPrespumpf = 0d;//回水压力",
	Double basTemsupwat = 0d;//供水温度",	
	Double basTemretwat = 0d;//回水温度",
	Double basFlowpow = 0d;//瞬时功率",		
	Double basFlowinst = 0d;//瞬时流量"
	Double pressureDiff = 0d;
	Double temperatureDiff = 0d;
	Double basFlowpowSet = 0d;//瞬时功率设定",		
	Double basFlowinstSet = 0d;//瞬时流量设定"
	List<TimeValue> heats = new ArrayList<>();
}

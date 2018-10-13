package cn.plou.web.heatManage.diagnose.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MeterInfoVO {
	//用途ＩＤ
	private String consumerId;
	//热表总数
	private BigDecimal totalMeterCnts;
	//流量计总数
	private BigDecimal totalFlowMeterCnts;
	//温控阀总数
	private BigDecimal totalWkfCnts;
	//集中器总数
	private BigDecimal totalMbusCnts;
	//平衡阀总数
	private BigDecimal totalPhfCnts;
	//温度采集器总数
	private BigDecimal totalTmpCollectorCnts;

}

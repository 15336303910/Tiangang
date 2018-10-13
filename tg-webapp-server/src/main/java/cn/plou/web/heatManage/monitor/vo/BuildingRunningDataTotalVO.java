package cn.plou.web.heatManage.monitor.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 
 * @author admin
 *
 */
@Data
public class BuildingRunningDataTotalVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String communityId;
	
	private String communityName;
	// 用户ＩＤ
	private String buildingId;
	
	private String buildingName;
	
	private BigDecimal unitCnts;
	
	private BigDecimal houseCnts;
	
	private BigDecimal totalArea;
	
	private BigDecimal totalHeatArea;
	
	private BigDecimal houseTmp;

	private BigDecimal outsideTmp;
	
	private BigDecimal heatIndex;
	
	private BigDecimal flowIndex;
	
	private BigDecimal adjHeatingIndex;
	// 功率
	private BigDecimal power;
	// 瞬时流量
	private BigDecimal flow;
	// 总功率
	private BigDecimal totalPower;
	// 总流量
	private BigDecimal totalFlow;
	// 进水温度
	private BigDecimal inWaterTmp;
	// 回水温度
	private BigDecimal outWaterTmp;

}

package cn.plou.web.heatManage.history.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class HeatMeterDataDO  implements Serializable {
	private String primaryId;	
	private String meterId;	//表号
	private String rowno;	//表号
	private String sysReadTime;
	private BigDecimal heat;/* <H> 热量 */
	private BigDecimal cool;/* <C> 冷量 */
	private BigDecimal inFlow;/* <F> 正向流量 */
	private BigDecimal outFlow;/* <RF> 反向流量 */
	private BigDecimal flowSpeed; /* <R> 瞬时流量 */
	private BigDecimal inTemperature;/* <IT> 进水温度 */
	private BigDecimal outTemperature;/* <OT> 回水温度 */
	private BigDecimal power;/* <P> 功率 */
	private String allWorkTime;/* <WT> 工作时间h */
	private String allTime;/* */
	private String runningState;//运行状态
	private String meterTime;/* <T> 热表时间 */
	private String companyId;
	private String isfreeze;
	private String usingId;
	private String areas; // 供热面积
	private String source; // 地址信息
	private BigDecimal tmpDiff;//温差
	private String address2nd;	//表号
}

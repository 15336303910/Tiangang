package cn.plou.web.heatManage.history.domain;

import java.io.Serializable;
import java.util.Date;

import cn.plou.common.constant.CKey;
import cn.plou.common.utils.Tools;
import lombok.Data;
@Data
public class WkqDataDO  extends BaseHistoryData {
//	室温采集器ID	用途ID	室温	信号强度	电量	时间
//	指使用对象的ID				
	/** 仪表id */
//	private String meterId;

	/** 记录序号 */
	private String primaryId;

	/** 记录号 */
//	private String rowno;

	/** 用途ID */
//	private String consumerId;

	/** 室温 */
	private Double roomTemperature;

	/** 信号强度 */
	private Integer signal;

	/** 电量 */
	private Double elec;

	/** 读取时间 */
	private String sysReadTime;

	/** 公司ID */
	private String companyId;

	/** 仪表状态 */
//	private String runningState;

	/** 每个数据对应时间 */
	private Date readTime;

	/** 仪表时间 */
	private Date meterTime;
	/** 仪表时间 */
	private String inDoorTime;
	/** 地址全称*/
//	String source;
	/** 表号 */
	private String address2nd;
}

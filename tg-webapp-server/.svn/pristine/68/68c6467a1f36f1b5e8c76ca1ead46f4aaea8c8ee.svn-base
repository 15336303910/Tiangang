package cn.plou.web.heatManage.housecontrol.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class HouseControlInfoDO {
	// 仪表id
	private String meterId;
	// 总iD
	private String primaryId;
	//
	private String rowno;

	private String consumerId;
	// 地址
	private String consumeAddr;
	//阀门状态 全开、中间、全关
	private String runningState;

	//限制状态 正常动作、禁止开阀、禁止关阀、点动关阀、点动开阀
	private String limitStatus;

	//执行步长 固定步长*修正系数1*修正系数2
	private Integer executionStep;
	//固定步长
	private Integer fixedStep;
	//停电保护 阀门保持原位、阀门全开、阀门全关
	private String powerProtection;
	
	private BigDecimal setTemperature;

	private BigDecimal roomTemperature;

	private Date sysReadTime;

	private String companyId;
	
	private Integer stepTotal;

	private Integer baseStep;

	private Integer furStep;

	private Long openTime;

	private Integer intervals;

	private Integer pointStep;
	//有线/无线产品
	private String wireless;
	
	private Date currentTimes;
	//锁定/解锁状态
	private String locks;
	
	private String indoorColTime;

	private Date readTime;

	private BigDecimal heatingArea;

	private BigDecimal flowingIndex;

	private BigDecimal flowUpperRatio;

	private BigDecimal flowUpperValue;

	private BigDecimal maxFlowThreshold;
	//流量给定方式  自动计算、人工给定、优化整定
	private String flowGiveMode;
	//算法1、算法2
	private String calKind;
	//控制动作指令 自动控制、手动全开、手动全关、手动点开、手动点关、就地控制
	private String controlCommond;
	//调节方式 通断调节、动态调节
	private String adjustModel;

	private BigDecimal correctCoefficient;

	private BigDecimal flowRate;

	private BigDecimal flowLast;

	private BigDecimal flowNow;
	//本次开关动作 开阀、关阀、保持
	private String actionNow;

	private String controlAddr;

	private String mbusId;

	/** 二级地址 */
	private String address2nd;

	/** 协议 */
	private String protocol;

	/** 阀门采集状态 */
	private String valveReadStatus;

	/** 阀集中器ID */
	private String mbusId2;
	private String powerProtectionName;
	private String limitStatusName;	
	private String runningStateName;
	private String wirelessName;
	private String locksName;
	private String flowGiveModeName;
	private String calKindName;
	private String controlCommondName;
	private String adjustModelName;
	private String actionNowName;
	private String address;

}
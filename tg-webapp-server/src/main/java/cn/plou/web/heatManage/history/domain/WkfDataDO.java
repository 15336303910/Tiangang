package cn.plou.web.heatManage.history.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class WkfDataDO extends BaseHistoryData {
//	private String source; // 地址信息
	private String primaryId;  // 总iD
//	private String meterId;  // 仪表id                               
//	private String rowno;  // 仪表id2
//	private String consumerId;  // 用途ID
//	private String runningState;  // 阀门状态           
	private String limitStatus;  // 限制状态           
	private BigDecimal executionStep;  // 执行步长           
	private BigDecimal fixedStep;  // 固定步长           
	private String powerProtection;  // 停电保护           
	private BigDecimal setTemperature;  // 设定温度
	private BigDecimal roomTemperature;  // 室内温度
	private String sysReadTime;  // 读取时间
	private String companyId;  // 公司ID
	private BigDecimal stepTotal;  // 步长累计值
	private BigDecimal baseStep;  // 基本步长
	private BigDecimal furStep;  // 预开步数
	private String openTime;  // 开阀时长
	private BigDecimal intervals;  // 动作间隔：
	private String wireless;  // 有线/无线产品
	private String currentTimes;  // 阀当前时间
	private String locks;  // 锁定/解锁状态
	private String indoorColTime;  // 室内温度采集时间
	private String readTime;  // 数据返回时间
	private String heatingArea;  // 采暖面积（㎡）    
	private BigDecimal flowingIndex;  // 流量指标（L/㎡）  
	private BigDecimal flowUpperRatio;  // 流量上限比（%）   
	private BigDecimal flowUpperValue;  // 流量上限值（T/H） 
	private BigDecimal maxFlowThreshold;  // 流量阈值（T/H） 
	private String flowGiveMode;  // 流量给定方式 
	private String calKind;  // 算法选择
	private String controlCommond;  // 控制模式
	private BigDecimal correctCoefficient;  // 修正系数
	private BigDecimal flowRate;  // 流量偏差比
	private BigDecimal flowLast;  // 上次用户流量
	private BigDecimal flowNow;  // 本次用户流量
	private String actionNow;  // 温控阀本次动作
	private String controlAddr;  // 控制地址
	private String mbusId;  // 集中器
	private String notes;  // 备注
	private String address2nd;  //  二级地址
	private String protocol;  //  协议 
	private String valveReadStatus;  // 阀门采集状态
	private String mbusId2;  // 阀集中器ID
	private String actionSet;  // 动作指令
	private String actionStep;  // 执行步长           
	private BigDecimal maxTempSet;  // 最大室温设定
}

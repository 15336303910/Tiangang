package cn.plou.web.heatManage.diagnose.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author liuxiaodong
 * @email yigeyanse@hotmail.com
 * @date 2018-07-03 16:33:45
 */
public class MeterInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//仪表ＩＤ
	private String meterId;
	//仪表ＩＤ２
	private String rowno;
	//用途ＩＤ
	private String consumerId;
	//一级地址
	private String address1st;
	//二级地址
	private String address2nd;
	//协议
	private String protocol;
	//集中器　ＩＤ
	private String mbusId;
	//集中器封包协议
	private String mbusPro;
	//采集器　ＩＤ
	private String mbusReadmodelId;
	//通道号：采集器下的多通道采集使用
	private String repeaterId;
	//仪表类型：热表、户用温控阀、户用平衡温控阀、楼宇平衡阀、温控面板、管网监测终端、室温采集器
	private String meterType;
	//通讯卡号
	private String simCard;
	//运行状态
	private String runningState;
	//安装地址
	private String installAddress;
	//最近故障发生时间
	private Date meterErrorTime;
	//位置类型
	private String meterPosition;
	//上级表号
	private String superMeterId;
	//阀门对应表号
	private String valveMeterId;
	//分体对应主体ＩＤ
	private String mainMeterId;
	//启用日期
	private Date useStartDate;
	//下次校表时间
	private Date nextCheckTime;
	//海拔标高（安装高度
	private BigDecimal installHeight;
	//经度
	private String longitude;
	//纬度
	private String latitude;
	//公司ＩＤ
	private String companyId;
	//地址全称：小区名＋楼名＋单元名＋室名
	private String address;
	//备注
	private String notes;
	//保留１
	private String memo1;
	//保留２
	private String memo2;
	//保留３
	private String memo3;
	//保留４
	private String memo4;
	//创建时间
	private Date createDate;
	//创建者
	private String createUser;
	//更新时间
	private Date updateDate;
	//更新者
	private String updateUser;
	//新表底码
	private BigDecimal basecode;
	//仪表子类型
	private String meterSubtype;
	//水表钢印号
	private String steel;
	//水表地址
	private String waterAddress;
	//表状态设定：１是启用　０是删除　２是暂停
	private String meterState;
	//厂商
	private String factory;
	//口径
	private String diameter;
	//计量精度
	private BigDecimal precisiona;
	//软件版本
	private String softVer;
	//硬件版本
	private String hardVer;
	//常用流量
	private BigDecimal commonFlow;
	//最小流量
	private BigDecimal minimumFlow;
	//温差范围
	private BigDecimal temperatureDiffer;
	//温度范围
	private BigDecimal temperatureRange;
	//波特率
	private String baudRate;
	//量程
	private String maxBound;
	//设定流量
	private BigDecimal limtspeed;
	//终止标志
	private Integer ctrlflag;
	//有效标志，０表明删除
	private Integer isvalid;
	//数量
	private Integer cnts;
	
	private Integer name;
	/**
	 * 设置：仪表ＩＤ
	 */
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	/**
	 * 获取：仪表ＩＤ
	 */
	public String getMeterId() {
		return meterId;
	}
	/**
	 * 设置：仪表ＩＤ２
	 */
	public void setRowno(String rowno) {
		this.rowno = rowno;
	}
	/**
	 * 获取：仪表ＩＤ２
	 */
	public String getRowno() {
		return rowno;
	}
	/**
	 * 设置：用途ＩＤ
	 */
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	/**
	 * 获取：用途ＩＤ
	 */
	public String getConsumerId() {
		return consumerId;
	}
	/**
	 * 设置：一级地址
	 */
	public void setAddress1st(String address1st) {
		this.address1st = address1st;
	}
	/**
	 * 获取：一级地址
	 */
	public String getAddress1st() {
		return address1st;
	}
	/**
	 * 设置：二级地址
	 */
	public void setAddress2nd(String address2nd) {
		this.address2nd = address2nd;
	}
	/**
	 * 获取：二级地址
	 */
	public String getAddress2nd() {
		return address2nd;
	}
	/**
	 * 设置：协议
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	/**
	 * 获取：协议
	 */
	public String getProtocol() {
		return protocol;
	}
	/**
	 * 设置：集中器　ＩＤ
	 */
	public void setMbusId(String mbusId) {
		this.mbusId = mbusId;
	}
	/**
	 * 获取：集中器　ＩＤ
	 */
	public String getMbusId() {
		return mbusId;
	}
	/**
	 * 设置：集中器封包协议
	 */
	public void setMbusPro(String mbusPro) {
		this.mbusPro = mbusPro;
	}
	/**
	 * 获取：集中器封包协议
	 */
	public String getMbusPro() {
		return mbusPro;
	}
	/**
	 * 设置：采集器　ＩＤ
	 */
	public void setMbusReadmodelId(String mbusReadmodelId) {
		this.mbusReadmodelId = mbusReadmodelId;
	}
	/**
	 * 获取：采集器　ＩＤ
	 */
	public String getMbusReadmodelId() {
		return mbusReadmodelId;
	}
	/**
	 * 设置：通道号：采集器下的多通道采集使用
	 */
	public void setRepeaterId(String repeaterId) {
		this.repeaterId = repeaterId;
	}
	/**
	 * 获取：通道号：采集器下的多通道采集使用
	 */
	public String getRepeaterId() {
		return repeaterId;
	}
	/**
	 * 设置：仪表类型：热表、户用温控阀、户用平衡温控阀、楼宇平衡阀、温控面板、管网监测终端、室温采集器
	 */
	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}
	/**
	 * 获取：仪表类型：热表、户用温控阀、户用平衡温控阀、楼宇平衡阀、温控面板、管网监测终端、室温采集器
	 */
	public String getMeterType() {
		return meterType;
	}
	/**
	 * 设置：通讯卡号
	 */
	public void setSimCard(String simCard) {
		this.simCard = simCard;
	}
	/**
	 * 获取：通讯卡号
	 */
	public String getSimCard() {
		return simCard;
	}
	/**
	 * 设置：运行状态
	 */
	public void setRunningState(String runningState) {
		this.runningState = runningState;
	}
	/**
	 * 获取：运行状态
	 */
	public String getRunningState() {
		return runningState;
	}
	/**
	 * 设置：安装地址
	 */
	public void setInstallAddress(String installAddress) {
		this.installAddress = installAddress;
	}
	/**
	 * 获取：安装地址
	 */
	public String getInstallAddress() {
		return installAddress;
	}
	/**
	 * 设置：最近故障发生时间
	 */
	public void setMeterErrorTime(Date meterErrorTime) {
		this.meterErrorTime = meterErrorTime;
	}
	/**
	 * 获取：最近故障发生时间
	 */
	public Date getMeterErrorTime() {
		return meterErrorTime;
	}
	/**
	 * 设置：位置类型
	 */
	public void setMeterPosition(String meterPosition) {
		this.meterPosition = meterPosition;
	}
	/**
	 * 获取：位置类型
	 */
	public String getMeterPosition() {
		return meterPosition;
	}
	/**
	 * 设置：上级表号
	 */
	public void setSuperMeterId(String superMeterId) {
		this.superMeterId = superMeterId;
	}
	/**
	 * 获取：上级表号
	 */
	public String getSuperMeterId() {
		return superMeterId;
	}
	/**
	 * 设置：阀门对应表号
	 */
	public void setValveMeterId(String valveMeterId) {
		this.valveMeterId = valveMeterId;
	}
	/**
	 * 获取：阀门对应表号
	 */
	public String getValveMeterId() {
		return valveMeterId;
	}
	/**
	 * 设置：分体对应主体ＩＤ
	 */
	public void setMainMeterId(String mainMeterId) {
		this.mainMeterId = mainMeterId;
	}
	/**
	 * 获取：分体对应主体ＩＤ
	 */
	public String getMainMeterId() {
		return mainMeterId;
	}
	/**
	 * 设置：启用日期
	 */
	public void setUseStartDate(Date useStartDate) {
		this.useStartDate = useStartDate;
	}
	/**
	 * 获取：启用日期
	 */
	public Date getUseStartDate() {
		return useStartDate;
	}
	/**
	 * 设置：下次校表时间
	 */
	public void setNextCheckTime(Date nextCheckTime) {
		this.nextCheckTime = nextCheckTime;
	}
	/**
	 * 获取：下次校表时间
	 */
	public Date getNextCheckTime() {
		return nextCheckTime;
	}
	/**
	 * 设置：海拔标高（安装高度
	 */
	public void setInstallHeight(BigDecimal installHeight) {
		this.installHeight = installHeight;
	}
	/**
	 * 获取：海拔标高（安装高度
	 */
	public BigDecimal getInstallHeight() {
		return installHeight;
	}
	/**
	 * 设置：经度
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * 获取：经度
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * 设置：纬度
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * 获取：纬度
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * 设置：公司ＩＤ
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	/**
	 * 获取：公司ＩＤ
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * 设置：地址全称：小区名＋楼名＋单元名＋室名
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：地址全称：小区名＋楼名＋单元名＋室名
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：备注
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/**
	 * 获取：备注
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * 设置：保留１
	 */
	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}
	/**
	 * 获取：保留１
	 */
	public String getMemo1() {
		return memo1;
	}
	/**
	 * 设置：保留２
	 */
	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}
	/**
	 * 获取：保留２
	 */
	public String getMemo2() {
		return memo2;
	}
	/**
	 * 设置：保留３
	 */
	public void setMemo3(String memo3) {
		this.memo3 = memo3;
	}
	/**
	 * 获取：保留３
	 */
	public String getMemo3() {
		return memo3;
	}
	/**
	 * 设置：保留４
	 */
	public void setMemo4(String memo4) {
		this.memo4 = memo4;
	}
	/**
	 * 获取：保留４
	 */
	public String getMemo4() {
		return memo4;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：创建者
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取：创建者
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * 设置：更新者
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * 获取：更新者
	 */
	public String getUpdateUser() {
		return updateUser;
	}
	/**
	 * 设置：新表底码
	 */
	public void setBasecode(BigDecimal basecode) {
		this.basecode = basecode;
	}
	/**
	 * 获取：新表底码
	 */
	public BigDecimal getBasecode() {
		return basecode;
	}
	/**
	 * 设置：仪表子类型
	 */
	public void setMeterSubtype(String meterSubtype) {
		this.meterSubtype = meterSubtype;
	}
	/**
	 * 获取：仪表子类型
	 */
	public String getMeterSubtype() {
		return meterSubtype;
	}
	/**
	 * 设置：水表钢印号
	 */
	public void setSteel(String steel) {
		this.steel = steel;
	}
	/**
	 * 获取：水表钢印号
	 */
	public String getSteel() {
		return steel;
	}
	/**
	 * 设置：水表地址
	 */
	public void setWaterAddress(String waterAddress) {
		this.waterAddress = waterAddress;
	}
	/**
	 * 获取：水表地址
	 */
	public String getWaterAddress() {
		return waterAddress;
	}
	/**
	 * 设置：表状态设定：１是启用　０是删除　２是暂停
	 */
	public void setMeterState(String meterState) {
		this.meterState = meterState;
	}
	/**
	 * 获取：表状态设定：１是启用　０是删除　２是暂停
	 */
	public String getMeterState() {
		return meterState;
	}
	/**
	 * 设置：厂商
	 */
	public void setFactory(String factory) {
		this.factory = factory;
	}
	/**
	 * 获取：厂商
	 */
	public String getFactory() {
		return factory;
	}
	/**
	 * 设置：口径
	 */
	public void setDiameter(String diameter) {
		this.diameter = diameter;
	}
	/**
	 * 获取：口径
	 */
	public String getDiameter() {
		return diameter;
	}
	/**
	 * 设置：计量精度
	 */
	public void setPrecisiona(BigDecimal precisiona) {
		this.precisiona = precisiona;
	}
	/**
	 * 获取：计量精度
	 */
	public BigDecimal getPrecisiona() {
		return precisiona;
	}
	/**
	 * 设置：软件版本
	 */
	public void setSoftVer(String softVer) {
		this.softVer = softVer;
	}
	/**
	 * 获取：软件版本
	 */
	public String getSoftVer() {
		return softVer;
	}
	/**
	 * 设置：硬件版本
	 */
	public void setHardVer(String hardVer) {
		this.hardVer = hardVer;
	}
	/**
	 * 获取：硬件版本
	 */
	public String getHardVer() {
		return hardVer;
	}
	/**
	 * 设置：常用流量
	 */
	public void setCommonFlow(BigDecimal commonFlow) {
		this.commonFlow = commonFlow;
	}
	/**
	 * 获取：常用流量
	 */
	public BigDecimal getCommonFlow() {
		return commonFlow;
	}
	/**
	 * 设置：最小流量
	 */
	public void setMinimumFlow(BigDecimal minimumFlow) {
		this.minimumFlow = minimumFlow;
	}
	/**
	 * 获取：最小流量
	 */
	public BigDecimal getMinimumFlow() {
		return minimumFlow;
	}
	/**
	 * 设置：温差范围
	 */
	public void setTemperatureDiffer(BigDecimal temperatureDiffer) {
		this.temperatureDiffer = temperatureDiffer;
	}
	/**
	 * 获取：温差范围
	 */
	public BigDecimal getTemperatureDiffer() {
		return temperatureDiffer;
	}
	/**
	 * 设置：温度范围
	 */
	public void setTemperatureRange(BigDecimal temperatureRange) {
		this.temperatureRange = temperatureRange;
	}
	/**
	 * 获取：温度范围
	 */
	public BigDecimal getTemperatureRange() {
		return temperatureRange;
	}
	/**
	 * 设置：波特率
	 */
	public void setBaudRate(String baudRate) {
		this.baudRate = baudRate;
	}
	/**
	 * 获取：波特率
	 */
	public String getBaudRate() {
		return baudRate;
	}
	/**
	 * 设置：量程
	 */
	public void setMaxBound(String maxBound) {
		this.maxBound = maxBound;
	}
	/**
	 * 获取：量程
	 */
	public String getMaxBound() {
		return maxBound;
	}
	/**
	 * 设置：设定流量
	 */
	public void setLimtspeed(BigDecimal limtspeed) {
		this.limtspeed = limtspeed;
	}
	/**
	 * 获取：设定流量
	 */
	public BigDecimal getLimtspeed() {
		return limtspeed;
	}
	/**
	 * 设置：终止标志
	 */
	public void setCtrlflag(Integer ctrlflag) {
		this.ctrlflag = ctrlflag;
	}
	/**
	 * 获取：终止标志
	 */
	public Integer getCtrlflag() {
		return ctrlflag;
	}
	/**
	 * 设置：有效标志，０表明删除
	 */
	public void setIsvalid(Integer isvalid) {
		this.isvalid = isvalid;
	}
	/**
	 * 获取：有效标志，０表明删除
	 */
	public Integer getIsvalid() {
		return isvalid;
	}
	public Integer getCnts() {
		return cnts;
	}
	public void setCnts(Integer cnts) {
		this.cnts = cnts;
	}

	public Integer getName() {
		return name;
	}
	public void setName(Integer name) {
		this.name = name;
	}
}

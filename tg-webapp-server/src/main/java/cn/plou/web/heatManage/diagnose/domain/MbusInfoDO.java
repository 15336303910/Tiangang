package cn.plou.web.heatManage.diagnose.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author liuxiaodong
 * @email yigeyanse@hotmail.com
 * @date 2018-07-03 16:33:45
 */
public class MbusInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;

	// 通讯设备ＩＤ：采番
	private String mbusId;
	// 用途ＩＤ
	private String consumerId;
	// 安装时间
	private Date installTime;
	// 安装地址
	private String installAddress;
	// 集中器号
	private String mbusCode;
	// 厂商
	private String factory;
	// 型号：有心跳的用数字开头，没心跳的用字母开头
	private String equipmentNo;
	// 集中器位置类型
	private String mbusPosition;
	// 运行状态
	private String runningState;
	// 线路状态
	private String busyStatus;
	// 上传方式
	private String upCommMode;
	// 传输方式
	private String transMode;
	// 有无虚拟
	private String channlMode;
	// 上线时间
	private Date onlineTime;
	// 下线时间
	private Date offlineTime;
	// 下挂协议
	private String downPro;
	// 集中器自身封包协议
	private String mbusPro;
	// 使用协议
	private String protocol;
	// socket
	private String socket;
	// 通讯卡号
	private String simCard;
	// 通讯服务商ＩＤ
	private String simProvider;
	// 通讯服务器地址与端口
	private String serverPort;
	// 启用日期
	private Date useStartTime;
	// 上级设备地址
	private String supperDeviceAddress;
	// 经度
	private String longitude;
	// 纬度
	private String latitude;
	// 公司ＩＤ
	private String companyId;
	// 地址全称：小区名＋楼名＋单元名＋室名
	private String address;
	// 备注
	private String notes;
	// 保留１
	private String memo1;
	// 保留２
	private String memo2;
	// 创建时间
	private Date createDate;
	// 创建者
	private String createUser;
	// 更新时间
	private Date updateDate;
	// 更新者
	private String updateUser;
	// 是否是第一次搜索
	private String isFirst;
	// 卡实时流量
	private String cardFlow;
	// 指令发送间隔
	private Integer sendInterval;
	// 有效标志，０表明删除
	private Integer isvalid;

	// 数量
	private Integer cnts;
	
	private String typeName;

	/**
	 * 设置：通讯设备ＩＤ：采番
	 */
	public void setMbusId(String mbusId) {
		this.mbusId = mbusId;
	}

	/**
	 * 获取：通讯设备ＩＤ：采番
	 */
	public String getMbusId() {
		return mbusId;
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
	 * 设置：安装时间
	 */
	public void setInstallTime(Date installTime) {
		this.installTime = installTime;
	}

	/**
	 * 获取：安装时间
	 */
	public Date getInstallTime() {
		return installTime;
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
	 * 设置：集中器号
	 */
	public void setMbusCode(String mbusCode) {
		this.mbusCode = mbusCode;
	}

	/**
	 * 获取：集中器号
	 */
	public String getMbusCode() {
		return mbusCode;
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
	 * 设置：型号：有心跳的用数字开头，没心跳的用字母开头
	 */
	public void setEquipmentNo(String equipmentNo) {
		this.equipmentNo = equipmentNo;
	}

	/**
	 * 获取：型号：有心跳的用数字开头，没心跳的用字母开头
	 */
	public String getEquipmentNo() {
		return equipmentNo;
	}

	/**
	 * 设置：集中器位置类型
	 */
	public void setMbusPosition(String mbusPosition) {
		this.mbusPosition = mbusPosition;
	}

	/**
	 * 获取：集中器位置类型
	 */
	public String getMbusPosition() {
		return mbusPosition;
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
	 * 设置：线路状态
	 */
	public void setBusyStatus(String busyStatus) {
		this.busyStatus = busyStatus;
	}

	/**
	 * 获取：线路状态
	 */
	public String getBusyStatus() {
		return busyStatus;
	}

	/**
	 * 设置：上传方式
	 */
	public void setUpCommMode(String upCommMode) {
		this.upCommMode = upCommMode;
	}

	/**
	 * 获取：上传方式
	 */
	public String getUpCommMode() {
		return upCommMode;
	}

	/**
	 * 设置：传输方式
	 */
	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}

	/**
	 * 获取：传输方式
	 */
	public String getTransMode() {
		return transMode;
	}

	/**
	 * 设置：有无虚拟
	 */
	public void setChannlMode(String channlMode) {
		this.channlMode = channlMode;
	}

	/**
	 * 获取：有无虚拟
	 */
	public String getChannlMode() {
		return channlMode;
	}

	/**
	 * 设置：上线时间
	 */
	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}

	/**
	 * 获取：上线时间
	 */
	public Date getOnlineTime() {
		return onlineTime;
	}

	/**
	 * 设置：下线时间
	 */
	public void setOfflineTime(Date offlineTime) {
		this.offlineTime = offlineTime;
	}

	/**
	 * 获取：下线时间
	 */
	public Date getOfflineTime() {
		return offlineTime;
	}

	/**
	 * 设置：下挂协议
	 */
	public void setDownPro(String downPro) {
		this.downPro = downPro;
	}

	/**
	 * 获取：下挂协议
	 */
	public String getDownPro() {
		return downPro;
	}

	/**
	 * 设置：集中器自身封包协议
	 */
	public void setMbusPro(String mbusPro) {
		this.mbusPro = mbusPro;
	}

	/**
	 * 获取：集中器自身封包协议
	 */
	public String getMbusPro() {
		return mbusPro;
	}

	/**
	 * 设置：使用协议
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * 获取：使用协议
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * 设置：ＩＰ
	 */
	public void setSocket(String socket) {
		this.socket = socket;
	}

	/**
	 * 获取：ＩＰ
	 */
	public String getSocket() {
		return socket;
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
	 * 设置：通讯服务商ＩＤ
	 */
	public void setSimProvider(String simProvider) {
		this.simProvider = simProvider;
	}

	/**
	 * 获取：通讯服务商ＩＤ
	 */
	public String getSimProvider() {
		return simProvider;
	}

	/**
	 * 设置：通讯服务器地址与端口
	 */
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	/**
	 * 获取：通讯服务器地址与端口
	 */
	public String getServerPort() {
		return serverPort;
	}

	/**
	 * 设置：启用日期
	 */
	public void setUseStartTime(Date useStartTime) {
		this.useStartTime = useStartTime;
	}

	/**
	 * 获取：启用日期
	 */
	public Date getUseStartTime() {
		return useStartTime;
	}

	/**
	 * 设置：上级设备地址
	 */
	public void setSupperDeviceAddress(String supperDeviceAddress) {
		this.supperDeviceAddress = supperDeviceAddress;
	}

	/**
	 * 获取：上级设备地址
	 */
	public String getSupperDeviceAddress() {
		return supperDeviceAddress;
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
	 * 设置：是否是第一次搜索
	 */
	public void setIsFirst(String isFirst) {
		this.isFirst = isFirst;
	}

	/**
	 * 获取：是否是第一次搜索
	 */
	public String getIsFirst() {
		return isFirst;
	}

	/**
	 * 设置：卡实时流量
	 */
	public void setCardFlow(String cardFlow) {
		this.cardFlow = cardFlow;
	}

	/**
	 * 获取：卡实时流量
	 */
	public String getCardFlow() {
		return cardFlow;
	}

	/**
	 * 设置：指令发送间隔
	 */
	public void setSendInterval(Integer sendInterval) {
		this.sendInterval = sendInterval;
	}

	/**
	 * 获取：指令发送间隔
	 */
	public Integer getSendInterval() {
		return sendInterval;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}

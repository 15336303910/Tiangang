package cn.plou.web.system.meterMessage.mbus.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mbus implements Serializable {
    @ApiModelProperty(required = true)
    private String mbusId;

    @ApiModelProperty(required = true)
    private String consumerId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name="安装时间")
    private Date installTime;

    @Excel(name="安装地址")
    private String installAddress;

    @Excel(name="集中器号")
    private String mbusCode;

    @Excel(name="厂商")
    private String factory;

    @Excel(name="型号")
    private String equipmentNo;

    @Excel(name="集中器位置")
    private String mbusPosition;

    @Excel(name="运行状态")
    private String runningState;

    @Excel(name="线路状态")
    private String busyStatus;

    @Excel(name="上传方式")
    private String upCommMode;

    @Excel(name="传输方式")
    private String transMode;

    @Excel(name="有无虚拟")
    private String channlMode;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name="上线时间")
    private Date onlineTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name="下线时间")
    private Date offlineTime;

    @Excel(name="下挂协议")
    private String downPro;

    @Excel(name="集中器自身封包协议")
    private String mbusPro;

    @Excel(name="使用协议")
    private String protocol;

    @Excel(name="socket")
    private String socket;

    @Excel(name="通讯卡号")
    private String simCard;

    @Excel(name="通讯服务商")
    private String simProvider;

    @Excel(name="通讯服务器")
    private String serverPort;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name="启用日期")
    private Date useStartTime;

    @Excel(name="上级设备地址")
    private String supperDeviceAddress;

    @Excel(name="经度")
    private String longitude;

    @Excel(name="纬度")
    private String latitude;

    @ApiModelProperty(required = true)
    private String companyId;

    @Excel(name="地址全称")
    private String address;

    @Excel(name="备注")
    private String notes;

    @Excel(name="保留1")
    private String memo1;

    @Excel(name="保留2")
    private String memo2;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;

    private String isFirst;

    private String cardFlow;

    @Excel(name="指令发送间隔")
    private Integer sendInterval;

    private Integer isvalid;

    public String getMbusId() {
        return mbusId;
    }

    public void setMbusId(String mbusId) {
        this.mbusId = mbusId == null ? null : mbusId.trim();
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId == null ? null : consumerId.trim();
    }

    public Date getInstallTime() {
        return installTime;
    }

    public void setInstallTime(Date installTime) {
        this.installTime = installTime;
    }

    public String getInstallAddress() {
        return installAddress;
    }

    public void setInstallAddress(String installAddress) {
        this.installAddress = installAddress == null ? null : installAddress.trim();
    }

    public String getMbusCode() {
        return mbusCode;
    }

    public void setMbusCode(String mbusCode) {
        this.mbusCode = mbusCode == null ? null : mbusCode.trim();
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory == null ? null : factory.trim();
    }

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo == null ? null : equipmentNo.trim();
    }

    public String getMbusPosition() {
        return mbusPosition;
    }

    public void setMbusPosition(String mbusPosition) {
        this.mbusPosition = mbusPosition == null ? null : mbusPosition.trim();
    }

    public String getRunningState() {
        return runningState;
    }

    public void setRunningState(String runningState) {
        this.runningState = runningState == null ? null : runningState.trim();
    }

    public String getBusyStatus() {
        return busyStatus;
    }

    public void setBusyStatus(String busyStatus) {
        this.busyStatus = busyStatus == null ? null : busyStatus.trim();
    }

    public String getUpCommMode() {
        return upCommMode;
    }

    public void setUpCommMode(String upCommMode) {
        this.upCommMode = upCommMode == null ? null : upCommMode.trim();
    }

    public String getTransMode() {
        return transMode;
    }

    public void setTransMode(String transMode) {
        this.transMode = transMode == null ? null : transMode.trim();
    }

    public String getChannlMode() {
        return channlMode;
    }

    public void setChannlMode(String channlMode) {
        this.channlMode = channlMode == null ? null : channlMode.trim();
    }

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Date getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(Date offlineTime) {
        this.offlineTime = offlineTime;
    }

    public String getDownPro() {
        return downPro;
    }

    public void setDownPro(String downPro) {
        this.downPro = downPro == null ? null : downPro.trim();
    }

    public String getMbusPro() {
        return mbusPro;
    }

    public void setMbusPro(String mbusPro) {
        this.mbusPro = mbusPro == null ? null : mbusPro.trim();
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol == null ? null : protocol.trim();
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getSimCard() {
        return simCard;
    }

    public void setSimCard(String simCard) {
        this.simCard = simCard == null ? null : simCard.trim();
    }

    public String getSimProvider() {
        return simProvider;
    }

    public void setSimProvider(String simProvider) {
        this.simProvider = simProvider == null ? null : simProvider.trim();
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort == null ? null : serverPort.trim();
    }

    public Date getUseStartTime() {
        return useStartTime;
    }

    public void setUseStartTime(Date useStartTime) {
        this.useStartTime = useStartTime;
    }

    public String getSupperDeviceAddress() {
        return supperDeviceAddress;
    }

    public void setSupperDeviceAddress(String supperDeviceAddress) {
        this.supperDeviceAddress = supperDeviceAddress == null ? null : supperDeviceAddress.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public String getMemo1() {
        return memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1 == null ? null : memo1.trim();
    }

    public String getMemo2() {
        return memo2;
    }

    public void setMemo2(String memo2) {
        this.memo2 = memo2 == null ? null : memo2.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst == null ? null : isFirst.trim();
    }

    public String getCardFlow() {
        return cardFlow;
    }

    public void setCardFlow(String cardFlow) {
        this.cardFlow = cardFlow == null ? null : cardFlow.trim();
    }

    public Integer getSendInterval() {
        return sendInterval;
    }

    public void setSendInterval(Integer sendInterval) {
        this.sendInterval = sendInterval;
    }

    public Integer getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(Integer isvalid) {
        this.isvalid = isvalid;
    }
}
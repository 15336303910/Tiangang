package cn.plou.web.system.meterMessage.mbus.entity;

public class MeterTimingDefine {
    private String primaryId;

    private String mbusCode;

    private String upCommMode;

    private String orderType;

    private String execDay;

    private String timing;

    private Integer intervals;
    
    private String nextSendTime;

    private String reRead;

    private String companyId;

    private Integer sendTimes;

    private Integer timeIndex;

    private String equipmentNo;

    private Integer validFlg;

    private String successFlag;

    public String getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId == null ? null : primaryId.trim();
    }

    public String getMbusCode() {
        return mbusCode;
    }

    public void setMbusCode(String mbusCode) {
        this.mbusCode = mbusCode == null ? null : mbusCode.trim();
    }

    public String getUpCommMode() {
        return upCommMode;
    }

    public void setUpCommMode(String upCommMode) {
        this.upCommMode = upCommMode == null ? null : upCommMode.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public String getExecDay() {
        return execDay;
    }

    public void setExecDay(String execDay) {
        this.execDay = execDay == null ? null : execDay.trim();
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing == null ? null : timing.trim();
    }

    public Integer getIntervals() {
        return intervals;
    }

    public void setIntervals(Integer intervals) {
        this.intervals = intervals;
    }

    public String getNextSendTime() {
        return nextSendTime;
    }

    public void setNextSendTime(String nextSendTime) {
        this.nextSendTime = nextSendTime == null ? null : nextSendTime.trim();
    }

    public String getReRead() {
        return reRead;
    }

    public void setReRead(String reRead) {
        this.reRead = reRead == null ? null : reRead.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public Integer getSendTimes() {
        return sendTimes;
    }

    public void setSendTimes(Integer sendTimes) {
        this.sendTimes = sendTimes;
    }

    public Integer getTimeIndex() {
        return timeIndex;
    }

    public void setTimeIndex(Integer timeIndex) {
        this.timeIndex = timeIndex;
    }

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo == null ? null : equipmentNo.trim();
    }

    public Integer getValidFlg() {
        return validFlg;
    }

    public void setValidFlg(Integer validFlg) {
        this.validFlg = validFlg;
    }

    public String getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(String successFlag) {
        this.successFlag = successFlag == null ? null : successFlag.trim();
    }
}
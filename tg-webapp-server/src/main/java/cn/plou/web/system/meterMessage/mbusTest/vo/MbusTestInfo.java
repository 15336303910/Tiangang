package cn.plou.web.system.meterMessage.mbusTest.vo;

public class MbusTestInfo {
    private String id;

    private String mbusCode;
    private String consumerId;
    private String mbusPosition;

    private String sendData;

    private String recData;

    private String sendDate;

    private String recDate;

    private String recFlag;
    private String recFlagName;

    private String companyId;
    private String companyName;

    public String getMbusPosition() {
        return mbusPosition;
    }

    public void setMbusPosition(String mbusPosition) {
        this.mbusPosition = mbusPosition;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getRecFlagName() {
        return recFlagName;
    }

    public void setRecFlagName(String recFlagName) {
        this.recFlagName = recFlagName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMbusCode() {
        return mbusCode;
    }

    public void setMbusCode(String mbusCode) {
        this.mbusCode = mbusCode;
    }

    public String getSendData() {
        return sendData;
    }

    public void setSendData(String sendData) {
        this.sendData = sendData;
    }

    public String getRecData() {
        return recData;
    }

    public void setRecData(String recData) {
        this.recData = recData;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getRecDate() {
        return recDate;
    }

    public void setRecDate(String recDate) {
        this.recDate = recDate;
    }

    public String getRecFlag() {
        return recFlag;
    }

    public void setRecFlag(String recFlag) {
        this.recFlag = recFlag;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

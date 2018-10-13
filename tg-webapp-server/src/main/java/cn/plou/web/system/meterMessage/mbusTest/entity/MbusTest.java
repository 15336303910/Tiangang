package cn.plou.web.system.meterMessage.mbusTest.entity;

public class MbusTest {
    private String id;

    private String mbusCode;

    private String sendData;

    private String recData;

    private String sendDate;

    private String recDate;

    private String recFlag;

    private String companyId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMbusCode() {
        return mbusCode;
    }

    public void setMbusCode(String mbusCode) {
        this.mbusCode = mbusCode == null ? null : mbusCode.trim();
    }

    public String getSendData() {
        return sendData;
    }

    public void setSendData(String sendData) {
        this.sendData = sendData == null ? null : sendData.trim();
    }

    public String getRecData() {
        return recData;
    }

    public void setRecData(String recData) {
        this.recData = recData == null ? null : recData.trim();
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate == null ? null : sendDate.trim();
    }

    public String getRecDate() {
        return recDate;
    }

    public void setRecDate(String recDate) {
        this.recDate = recDate == null ? null : recDate.trim();
    }

    public String getRecFlag() {
        return recFlag;
    }

    public void setRecFlag(String recFlag) {
        this.recFlag = recFlag == null ? null : recFlag.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

}
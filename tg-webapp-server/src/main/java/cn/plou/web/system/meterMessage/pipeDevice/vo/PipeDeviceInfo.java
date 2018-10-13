package cn.plou.web.system.meterMessage.pipeDevice.vo;

import cn.plou.web.system.meterMessage.pipeDevice.entity.PipeDevice;

public class PipeDeviceInfo extends PipeDevice {
    private String companyName;
    private String staffName;
    private String pipeDevice;
    private String pipeTypeName;
    private String ascriptionName;
    private String factoryName;
    private String deviceClassifyName;
    private String caliber;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPipeDevice() {
        return pipeDevice;
    }

    public void setPipeDevice(String pipeDevice) {
        this.pipeDevice = pipeDevice;
    }

    public String getAscriptionName() {
        return ascriptionName;
    }

    public void setAscriptionName(String ascriptionName) {
        this.ascriptionName = ascriptionName;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getDeviceClassifyName() {
        return deviceClassifyName;
    }

    public void setDeviceClassifyName(String deviceClassifyName) {
        this.deviceClassifyName = deviceClassifyName;
    }

    public String getCaliber() {
        return caliber;
    }

    public void setCaliber(String caliber) {
        this.caliber = caliber;
    }

    public String getPipeTypeName() {
        return pipeTypeName;
    }

    public void setPipeTypeName(String pipeTypeName) {
        this.pipeTypeName = pipeTypeName;
    }
}

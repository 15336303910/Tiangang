package cn.plou.web.system.commonMessage.heatingTime.vo;

import cn.plou.web.system.commonMessage.heatingTime.entity.HeatingTime;

public class HeatingTimeInfo extends HeatingTime {
    private String companyName;

    private String stationName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}

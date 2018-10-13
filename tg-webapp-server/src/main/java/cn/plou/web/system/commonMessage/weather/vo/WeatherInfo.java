package cn.plou.web.system.commonMessage.weather.vo;


import cn.plou.web.system.commonMessage.weather.entity.Weather;

public class WeatherInfo extends Weather {
    private String cityName;
    private String areaName;
    private String provianceName;

    private String address;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getProvianceName() {
        return provianceName;
    }

    public void setProvianceName(String provianceName) {
        this.provianceName = provianceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

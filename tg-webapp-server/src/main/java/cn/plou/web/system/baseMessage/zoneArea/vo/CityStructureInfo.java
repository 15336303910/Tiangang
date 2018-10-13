package cn.plou.web.system.baseMessage.zoneArea.vo;

import cn.plou.web.system.baseMessage.zoneArea.entity.ZoneArea;
import cn.plou.web.system.baseMessage.zoneCity.entity.ZoneCity;
import cn.plou.web.system.baseMessage.zoneProvince.entity.ZoneProvince;

import java.util.List;

public class CityStructureInfo {
    private String zPProvinceid ;
    private String zCCityid;
    private String zAAreaid;
    private List<ZoneProvince> zoneProvinceList;
    private List<ZoneCity> zoneCityList;
    private List<ZoneArea> zoneAreaList;

    public String getzPProvinceid() {
        return zPProvinceid;
    }

    public void setzPProvinceid(String zPProvinceid) {
        this.zPProvinceid = zPProvinceid;
    }

    public String getzCCityid() {
        return zCCityid;
    }

    public void setzCCityid(String zCCityid) {
        this.zCCityid = zCCityid;
    }

    public String getzAAreaid() {
        return zAAreaid;
    }

    public void setzAAreaid(String zAAreaid) {
        this.zAAreaid = zAAreaid;
    }

    public List<ZoneProvince> getZoneProvinceList() {
        return zoneProvinceList;
    }

    public void setZoneProvinceList(List<ZoneProvince> zoneProvinceList) {
        this.zoneProvinceList = zoneProvinceList;
    }

    public List<ZoneCity> getZoneCityList() {
        return zoneCityList;
    }

    public void setZoneCityList(List<ZoneCity> zoneCityList) {
        this.zoneCityList = zoneCityList;
    }

    public List<ZoneArea> getZoneAreaList() {
        return zoneAreaList;
    }

    public void setZoneAreaList(List<ZoneArea> zoneAreaList) {
        this.zoneAreaList = zoneAreaList;
    }
}

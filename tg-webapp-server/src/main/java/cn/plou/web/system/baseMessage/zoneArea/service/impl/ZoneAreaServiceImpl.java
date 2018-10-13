package cn.plou.web.system.baseMessage.zoneArea.service.impl;

import cn.plou.web.system.baseMessage.zoneArea.dao.ZoneAreaMapper;
import cn.plou.web.system.baseMessage.zoneArea.entity.ZoneArea;
import cn.plou.web.system.baseMessage.zoneArea.service.ZoneAreaService;
import cn.plou.web.system.baseMessage.zoneArea.vo.CityStructureInfo;
import cn.plou.web.system.baseMessage.zoneCity.dao.ZoneCityMapper;
import cn.plou.web.system.baseMessage.zoneCity.entity.ZoneCity;
import cn.plou.web.system.baseMessage.zoneProvince.dao.ZoneProvinceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ZoneAreaServiceImpl implements ZoneAreaService {
    @Autowired
    ZoneAreaMapper zoneAreaMapper;
    @Autowired
    ZoneCityMapper zoneCityMapper;
    @Autowired
    ZoneProvinceMapper zoneProvinceMapper;

    @Override
    public List<ZoneArea> getZoneAreaByZACityid(String zCProvinceid) {
        return zoneAreaMapper.selectZoneAreaByZACityid(zCProvinceid);
    }

    @Override
    public CityStructureInfo getCityStructureInfoById(String idName, String id) {
        CityStructureInfo cityStructureInfo=new CityStructureInfo();
        cityStructureInfo.setZoneProvinceList(zoneProvinceMapper.selectAllZoneProvince());
        if(idName.equals("zCCityid")){
            String zPProvinceid =zoneProvinceMapper.selectZoneProvinceByZCCityid(id).getzPProvinceid();
            cityStructureInfo.setzPProvinceid(zPProvinceid);
            cityStructureInfo.setZoneCityList(zoneCityMapper.selectZoneCityByZCProvinceid(zPProvinceid));
        }
        if(idName.equals("zAAreaid")){
            String zCCityid = zoneCityMapper.selectZoneCityByZAAreaid(id).getzCCityid();
            cityStructureInfo.setzCCityid(zCCityid);
            String zPProvinceid =zoneProvinceMapper.selectZoneProvinceByZCCityid(zCCityid).getzPProvinceid();
            cityStructureInfo.setzPProvinceid(zPProvinceid);
            cityStructureInfo.setZoneCityList(zoneCityMapper.selectZoneCityByZCProvinceid(zPProvinceid));
            cityStructureInfo.setZoneAreaList(zoneAreaMapper.selectZoneAreaByZACityid(zCCityid));
        }
        return cityStructureInfo;
    }


}

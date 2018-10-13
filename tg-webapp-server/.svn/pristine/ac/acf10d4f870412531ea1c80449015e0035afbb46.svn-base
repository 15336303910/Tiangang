package cn.plou.web.system.baseMessage.zoneCity.service.impl;

import cn.plou.web.system.baseMessage.zoneCity.dao.ZoneCityMapper;
import cn.plou.web.system.baseMessage.zoneCity.entity.ZoneCity;
import cn.plou.web.system.baseMessage.zoneCity.service.ZoneCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ZoneCityServiceImpl implements ZoneCityService {
    @Autowired
    ZoneCityMapper zoneCityMapper;

    @Override
    public List<ZoneCity> getZoneCityByZCProvinceid(String zCProvinceid) {
        return zoneCityMapper.selectZoneCityByZCProvinceid(zCProvinceid);
    }

    @Override
    public ZoneCity getZoneCityByZAAreaid(String zAAreaid) {
        return zoneCityMapper.selectZoneCityByZAAreaid(zAAreaid);
    }
}

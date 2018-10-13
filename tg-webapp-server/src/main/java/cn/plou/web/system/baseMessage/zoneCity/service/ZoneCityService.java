package cn.plou.web.system.baseMessage.zoneCity.service;

import cn.plou.web.system.baseMessage.zoneCity.entity.ZoneCity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ZoneCityService {
    List<ZoneCity>getZoneCityByZCProvinceid(String zCProvinceid);
    ZoneCity getZoneCityByZAAreaid (String zAAreaid);
}

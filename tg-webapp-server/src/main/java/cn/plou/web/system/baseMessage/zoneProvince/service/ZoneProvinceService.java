package cn.plou.web.system.baseMessage.zoneProvince.service;

import cn.plou.web.system.baseMessage.zoneProvince.entity.ZoneProvince;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ZoneProvinceService {
    List<ZoneProvince>getAllZoneProvince();
    ZoneProvince getZoneProvinceByZCCityid( String zCCityid);
}

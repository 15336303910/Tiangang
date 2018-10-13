package cn.plou.web.system.baseMessage.zoneProvince.service.impl;

import cn.plou.web.system.baseMessage.zoneProvince.dao.ZoneProvinceMapper;
import cn.plou.web.system.baseMessage.zoneProvince.entity.ZoneProvince;
import cn.plou.web.system.baseMessage.zoneProvince.service.ZoneProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ZoneProvinceServiceImpl implements ZoneProvinceService {
    @Autowired
    ZoneProvinceMapper zoneProvinceMapper;

    @Override
    public List<ZoneProvince> getAllZoneProvince() {
        return zoneProvinceMapper.selectAllZoneProvince();
    }

    @Override
    public ZoneProvince getZoneProvinceByZCCityid(String zCCityid) {
        return zoneProvinceMapper.selectZoneProvinceByZCCityid(zCCityid);
    }
}

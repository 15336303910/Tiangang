package cn.plou.web.balance.trendCurve.service;

import cn.plou.web.balance.trendCurve.entity.UnitRunningData;
import cn.plou.web.common.config.common.Root;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface TrendCurveService {
    Root getTrendCurveDataService(String unitId, String buildingNo, Date startDate, Date endDate);
}

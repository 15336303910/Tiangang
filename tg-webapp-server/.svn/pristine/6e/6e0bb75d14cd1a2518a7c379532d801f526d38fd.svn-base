package cn.plou.web.balance.trendCurve.service.impl;

import cn.plou.web.balance.trendCurve.dao.BuildRunningDataMapper;
import cn.plou.web.balance.trendCurve.dao.UnitRunningDataMapper;
import cn.plou.web.balance.trendCurve.entity.UnitRunningData;
import cn.plou.web.balance.trendCurve.service.TrendCurveService;
import cn.plou.web.balance.trendCurve.vo.RunningData;
import cn.plou.web.common.config.common.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TrendCurveServiceImpl implements TrendCurveService {
    @Autowired
    private BuildRunningDataMapper buildRunningDataMapper;
    @Autowired
    private UnitRunningDataMapper unitRunningDataMapper;
    @Override
    public Root getTrendCurveDataService(String unitId, String buildingNo, Date startDate, Date endDate) {
        Root root = new Root();
        if(unitId!=null){
            List<RunningData> unitRunningDataList = unitRunningDataMapper.selectUnitRuuningDataList(unitId, startDate, endDate);
            root.setData(unitRunningDataList);
        }else if(buildingNo!=null){
            List<RunningData> buildRunningDataList = buildRunningDataMapper.selectBuildRuuningDataList(buildingNo,startDate,endDate);
            root.setData(buildRunningDataList);
        }else{
            List<String> nullList = new ArrayList<>();
            root.setData(nullList);
        }
        return root;
    }
}

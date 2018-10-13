package cn.plou.web.balance.dataAnalysis.service.impl;
import cn.plou.web.balance.dataAnalysis.entity.BalanceValveData;
import cn.plou.web.balance.dataAnalysis.service.BalanceValveDataService;
import cn.plou.web.balance.dataAnalysis.vo.BalanceValveDataInfo;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import java.util.Date;
import java.util.List;

@Component
public class BalanceValveDataServiceImpl implements BalanceValveDataService {
   /* @Autowired
    private BalanceValveDataMapper balanceValveDataMapper;*/
    @Override
    public List<BalanceValveDataInfo> getBalanceDataList(Date startDate, Date endDate, String consumerId) {
        return null;
    }

    @Override
    public List<BalanceValveDataInfo> getAvgHistoryData(Date startDate, Date endDate, String consumerId) {
        return null;
    }

    @Override
    public String exportExcelData(Date startDate, Date endDate, String consumerId, ServletRequest request) {
        return null;
    }

    @Override
    public String exportExcelAvgData(Date startDate, Date endDate, String consumerId, ServletRequest request) {
        return null;
    }

    @Override
    public String exportExcelPressureData(Date startDate, Date endDate, String consumerId, ServletRequest request) {
        return null;
    }

    @Override
    public String exportExcelTemperatureData(Date startDate, Date endDate, String consumerId, ServletRequest request) {
        return null;
    }

    @Override
    public String exportExcelFlowData(Date startDate, Date endDate, String consumerId, ServletRequest request) {
        return null;
    }

    @Override
    public List<BalanceValveData> getHistoryDataByDate(Date startDate,Date endDate,String consumerId,Date systemReadTime) {
        return null;
    }
}

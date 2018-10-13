package cn.plou.web.balance.dataAnalysis.service;

import cn.plou.web.balance.dataAnalysis.entity.BalanceValveData;
import cn.plou.web.balance.dataAnalysis.vo.BalanceValveDataInfo;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import java.util.Date;
import java.util.List;

@Service
public interface BalanceValveDataService {
    List<BalanceValveDataInfo> getBalanceDataList(Date startDate,Date endDate,String consumerId);

    List<BalanceValveDataInfo> getAvgHistoryData(Date startDate,Date endDate,String consumerId);

    String exportExcelData(Date startDate,Date endDate,String consumerId, ServletRequest request);
    String exportExcelAvgData(Date startDate,Date endDate,String consumerId, ServletRequest request);
    String exportExcelPressureData(Date startDate,Date endDate,String consumerId, ServletRequest request);
    String exportExcelTemperatureData(Date startDate,Date endDate,String consumerId, ServletRequest request);
    String exportExcelFlowData(Date startDate,Date endDate,String consumerId, ServletRequest request);
    List<BalanceValveData> getHistoryDataByDate(Date startDate,Date endDate,String consumerId,Date systemReadTime);
}

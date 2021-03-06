package cn.plou.web.balance.dataAnalysis.service;

import cn.plou.web.balance.dataAnalysis.entity.BalanceValveData;
import cn.plou.web.balance.dataAnalysis.vo.BalanceValveDataInfo;
import cn.plou.web.common.config.common.Root;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import java.util.Date;
import java.util.List;

@Service
public interface BalanceValveDataService {
    Root getBalanceDataList(Date startDate, Date endDate, String companyId, String stationId, String commuityId,
                            String building , String sortby, String order, Integer page, Integer pageSize,String controlMode);

    Root getAvgHistoryData(Date startDate,Date endDate,String companyId,String stationId,String commuityId,
                                                 String building ,String sortby, String order,Integer page,Integer pageSize);

    String exportExcelData(Date startDate,Date endDate,String companyId,String stationId,String commuityId,
                           String building, ServletRequest request);
    String exportExcelAvgData(Date startDate,Date endDate,String companyId,String stationId,String commuityId,
                              String building, ServletRequest request);
    String exportExcelPressureData(Date startDate,Date endDate,String companyId,String stationId,String commuityId,
                                   String building, String controlMode, ServletRequest request);
    String exportExcelTemperatureData(Date startDate,Date endDate,String companyId,String stationId,String commuityId,
                                      String building, String controlMode, ServletRequest request);
    String exportExcelFlowData(Date startDate,Date endDate,String companyId,String stationId,String commuityId,
                               String building, String controlMode, ServletRequest request);
    List<BalanceValveData> getHistoryDataByDate(Date startDate,Date endDate,String companyId,String stationId,String commuityId,
                                                String building,Date systemReadTime);
}

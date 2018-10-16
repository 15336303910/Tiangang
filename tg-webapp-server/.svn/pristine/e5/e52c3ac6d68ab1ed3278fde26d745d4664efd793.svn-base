package cn.plou.web.balance.regulate.service.impl;

import static cn.plou.web.common.config.common.Cond.getCond;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.plou.web.balance.distribution.dao.BalanceValveDataNowMapper;
import cn.plou.web.balance.distribution.vo.BalanceValveDataNowInfo;
import cn.plou.web.balance.distribution.vo.DeviationAndIndex;
import cn.plou.web.balance.distribution.vo.SearchCondition;
import cn.plou.web.balance.regulate.service.RegulateService;
import cn.plou.web.balance.regulate.vo.BalanceValveDataNowBatch;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.baseMessage.company.service.CompanyService;
import cn.plou.web.system.permission.role.service.DataRoleService;
import io.swagger.annotations.ApiModelProperty;

@Component
public class RegulateServiceImpl implements RegulateService {
    @Autowired
    private BalanceValveDataNowMapper balanceValveDataNowMapper;
    @ApiModelProperty
    private CompanyService companyService;
    @Autowired
    private DataRoleService dataRoleService;
    
    @Override
    public Root getBalanceDataList(SearchCondition searchCondition, String companyId,String stationId,String commuityId,String buildingId, String sortby, String order, Integer page, Integer pageSize) {
        /*searchCondition.setFlowingDeviation(flowingDeviation);
        searchCondition.setTemperatureDeviation(temperatureDeviation);
        searchCondition.setPressureDeviation(pressureDeviation);
        searchCondition.setHeatingIndex(heatingIndex);
        searchCondition.setFlowingIndex(flowingIndex);*/
        Integer start = null;
        if (page != null) {
            start = (page - 1) * pageSize;
        }
				DeviationAndIndex deviationAndIndex = null;
				if (searchCondition != null) {
					String flowingDeviation = searchCondition.getFlowingDeviation();
					String temperatureDeviation = searchCondition.getTemperatureDeviation();
					String pressureDeviation = searchCondition.getPressureDeviation();
					String heatingIndex = searchCondition.getHeatingIndex();
					String flowingIndex = searchCondition.getFlowingIndex();
					deviationAndIndex = new DeviationAndIndex();
					BigDecimal maxFlowingDeviation = getMaxRange(flowingDeviation);
					BigDecimal maxTemperatureDeviation = getMaxRange(temperatureDeviation);
					BigDecimal maxPressureDeviation = getMaxRange(pressureDeviation);
					BigDecimal maxHeatingIndex = getMaxRange(heatingIndex);
					BigDecimal maxFlowingIndex = getMaxRange(flowingIndex);
					BigDecimal minFlowingDeviation = getMinRange(flowingDeviation);
					BigDecimal minTemperatureDeviation = getMinRange(temperatureDeviation);
					BigDecimal minPressureDeviation = getMinRange(pressureDeviation);
					BigDecimal minHeatingIndex = getMinRange(heatingIndex);
					BigDecimal minFlowingIndex = getMinRange(flowingIndex);
					deviationAndIndex.setMaxFlowingDeviation(maxFlowingDeviation);
					deviationAndIndex.setMinFlowingDeviation(minFlowingDeviation);
					deviationAndIndex.setMaxFlowingIndex(maxFlowingIndex);
					deviationAndIndex.setMinFlowingIndex(minFlowingIndex);
					deviationAndIndex.setMaxHeatingIndex(maxHeatingIndex);
					deviationAndIndex.setMinHeatingIndex(minHeatingIndex);
					deviationAndIndex.setMaxTemperatureDeviation(maxTemperatureDeviation);
					deviationAndIndex.setMinTemperatureDeviation(minTemperatureDeviation);
					deviationAndIndex.setMaxPressureDeviation(maxPressureDeviation);
					deviationAndIndex.setMinPressureDeviation(minPressureDeviation);
				}
        List<String> companyIds = new ArrayList<>();
        List<String> stationIds = new ArrayList<String>();
        List<String> commuityIds = new ArrayList<String>();
        Boolean flag = dataRoleService.getDataRoleItems(companyIds, stationIds, commuityIds
            ,UserUtils.getUserId(),companyId, stationId,commuityId);
        
        int total = balanceValveDataNowMapper.selectDataNowListCount(deviationAndIndex,companyIds,null,commuityIds,buildingId);
        Root root = new Root();
        root.setCond(getCond(page,pageSize,total,order,sortby));
        if(total == 0){
        	List<BalanceValveDataNowInfo> list = new ArrayList<>();
        	root.setData(list);
        	return root;
        }
        List<BalanceValveDataNowInfo> list = balanceValveDataNowMapper.selectDataNowList(deviationAndIndex,companyIds,null,commuityIds,buildingId,sortby,order,start,pageSize);
        root.setData(list);
        return root;
    }

    @Override
    public int modifyBalanceValveParamBatch(BalanceValveDataNowBatch balanceValveDataNowBatch) {
        return balanceValveDataNowMapper.updateByPrimaryKeySelectiveBatch(balanceValveDataNowBatch);
    }

    public BigDecimal getMinRange(String str){
        if(str!=null && str.contains("|")){
            Integer index = str.indexOf("|");
            if(str.substring(0,index)!=null&&str.substring(0,index).length()>0){
                BigDecimal bigDecimal = new BigDecimal(Integer.parseInt(str.substring(0,index)));
                bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
                return bigDecimal;
            }
        }
        return null;
    }
    public BigDecimal getMaxRange(String str){
        if(str!=null && str.contains("|")){
            Integer index = str.indexOf("|");
            if(str.substring(index+1)!=null&&str.substring(index+1).length()>0){
                BigDecimal bigDecimal = new BigDecimal(Integer.parseInt(str.substring(index+1)));
                bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
                return bigDecimal;
            }
        }
        return null;
    }


}

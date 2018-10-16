package cn.plou.web.balance.regulate.service;

import cn.plou.web.balance.distribution.vo.BalanceValveDataNowInfo;
import cn.plou.web.balance.distribution.vo.SearchCondition;
import cn.plou.web.balance.regulate.vo.BalanceValveDataNowBatch;
import cn.plou.web.common.config.common.Root;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegulateService {
    Root getBalanceDataList(SearchCondition searchCondition,String companyId,String stationId,String commuityId,String buildingId,String sortby,String order,Integer page,Integer pageSize);
    int modifyBalanceValveParamBatch(BalanceValveDataNowBatch balanceValveDataNowBatch);
}

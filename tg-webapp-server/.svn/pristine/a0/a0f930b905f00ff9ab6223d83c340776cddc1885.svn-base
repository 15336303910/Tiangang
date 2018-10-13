package cn.plou.web.balance.regulate.service.impl;

import cn.plou.web.balance.distribution.dao.BalanceValveDataNowMapper;
import cn.plou.web.balance.distribution.entity.BalanceValveDataNow;
import cn.plou.web.balance.distribution.vo.SearchCondition;
import cn.plou.web.balance.regulate.service.DeviationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviationServiceImpl implements DeviationService {
    @Autowired
    private BalanceValveDataNowMapper balanceValveDataNowMapper;
    @Override
    public SearchCondition getDeviationsByBalanceValve(String meterId) {
        BalanceValveDataNow balanceValveDataNow = balanceValveDataNowMapper.selectByPrimaryKey(meterId);
        SearchCondition searchCondition = new SearchCondition();
        if(balanceValveDataNow!=null){
            //searchCondition.setTemperatureDeviation(balanceValveDataNow.getTemperatureThreshold()-balanceValveDataNow.getSetTemperature());
        }
        return null;
    }
}

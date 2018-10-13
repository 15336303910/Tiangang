package cn.plou.web.charge.chargeconfig.service.impl;

import cn.plou.web.charge.chargeconfig.dao.MoneyStateInfoMapper;
import cn.plou.web.charge.chargeconfig.dao.SmsInfoMapper;
import cn.plou.web.charge.chargeconfig.entity.MoneyStateInfo;
import cn.plou.web.charge.chargeconfig.entity.SmsInfo;
import cn.plou.web.charge.chargeconfig.service.MoneyStateInfoService;
import cn.plou.web.charge.chargeconfig.service.SmsInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author yinxiaochen
 * 2018/8/15 15:38
 */


@Service
@Transactional(rollbackFor = Exception.class)
public class MoneyStateInfoServiceImpl implements MoneyStateInfoService {


    @Resource
    private MoneyStateInfoMapper moneyStateInfoMapper;
    @Override
    public int deleteByPrimaryKey(String primaryId) {
        return moneyStateInfoMapper.deleteByPrimaryKey(primaryId);
    }

    @Override
    public int insert(MoneyStateInfo record) {
        return moneyStateInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(MoneyStateInfo record) {
        return moneyStateInfoMapper.insertSelective(record);
    }

    @Override
    public MoneyStateInfo selectByPrimaryKey(String primaryId) {
        return moneyStateInfoMapper.selectByPrimaryKey(primaryId);
    }



    @Override
    public int updateByPrimaryKeySelective(MoneyStateInfo record) {
        return moneyStateInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MoneyStateInfo record) {
        return moneyStateInfoMapper.updateByPrimaryKey(record);
    }


}

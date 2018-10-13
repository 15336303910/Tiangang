package cn.plou.web.charge.chargeconfig.service.impl;

import cn.plou.web.charge.chargeconfig.dao.PriceAccuracyInfoMapper;
import cn.plou.web.charge.chargeconfig.entity.PriceAccuracyInfo;
import cn.plou.web.charge.chargeconfig.service.PriceAccuracyInfoService;
import cn.plou.web.common.utils.IDWorker;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.permission.userLogin.dao.UserLoginMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class PriceAccuracyInfoServiceImpl  implements PriceAccuracyInfoService {


    @Resource
    private PriceAccuracyInfoMapper   priceAccuracyInfoMapper;
    @Resource
    private UserLoginMapper userLoginMapper;

    @Override
    public PriceAccuracyInfo findPriceAccuracyInfo(PriceAccuracyInfo record) {
        return priceAccuracyInfoMapper.findPriceAccuracyInfo(record);
    }

    @Override
    public int update(PriceAccuracyInfo record) {

        record.setUpdateDate(new Date());
        record.setUpdateUser(userLoginMapper.selectByPrimaryKey(UserUtils.getUserId()).getUsername());
        return priceAccuracyInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int add(PriceAccuracyInfo record) {
        record.setPrimaryId(IDWorker.uuid());
        record.setCreateDate(new Date());
        record.setCreateUser(userLoginMapper.selectByPrimaryKey(UserUtils.getUserId()).getUsername());
        return priceAccuracyInfoMapper.insertSelective(record);
    }
}

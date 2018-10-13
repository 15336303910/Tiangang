package cn.plou.web.charge.chargeconfig.service;

import cn.plou.web.charge.chargeconfig.entity.MoneyStateInfo;
import cn.plou.web.charge.chargeconfig.entity.SmsInfo;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @author yinxiaochen
 * 2018/8/15 15:38
 */
public interface MoneyStateInfoService {

    int deleteByPrimaryKey(String primaryId);

    int insert(MoneyStateInfo record);

    int insertSelective(MoneyStateInfo record);

    MoneyStateInfo selectByPrimaryKey(String primaryId);

    int updateByPrimaryKeySelective(MoneyStateInfo record);

    int updateByPrimaryKey(MoneyStateInfo record);

}

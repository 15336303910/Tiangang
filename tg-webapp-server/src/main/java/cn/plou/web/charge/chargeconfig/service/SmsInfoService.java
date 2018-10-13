package cn.plou.web.charge.chargeconfig.service;

import cn.plou.web.charge.chargeconfig.entity.SmsInfo;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @author yinxiaochen
 * 2018/8/15 15:38
 */
public interface SmsInfoService {


    int deleteByPrimaryKey(String primaryId);


    int insert(SmsInfo record);


    int insertSelective(SmsInfo record);

    SmsInfo selectByPrimaryKey(String primaryId);

    int updateByPrimaryKeySelective(SmsInfo record);


    int updateByPrimaryKey(SmsInfo record);

    PageInfo<SmsInfo> getSmsInfoList(Map<String, Object> map);





}

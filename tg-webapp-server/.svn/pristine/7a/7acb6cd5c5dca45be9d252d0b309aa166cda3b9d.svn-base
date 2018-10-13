package cn.plou.web.charge.chargeconfig.service;

import cn.plou.web.charge.chargeconfig.entity.SmsTemplate;

import java.util.List;

/**
 * @author yinxiaochen
 * 2018/8/15 15:01
 */
public interface SmsTemplateService {


    int deleteByPrimaryKey(String primaryId);


    int insert(SmsTemplate record);

    int insertSelective(SmsTemplate record);

    SmsTemplate selectByPrimaryKey(String primaryId);


    int updateByPrimaryKeySelective(SmsTemplate record);

    int updateByPrimaryKey(SmsTemplate record);


    List<SmsTemplate > findAllSmsTemplates(String  companyId);


    SmsTemplate findByTemplateCode(String templateCode);



}

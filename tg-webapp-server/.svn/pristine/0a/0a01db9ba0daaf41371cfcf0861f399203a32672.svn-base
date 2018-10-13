package cn.plou.web.charge.chargeconfig.service.impl;

import cn.plou.web.charge.chargeconfig.dao.SmsTemplateMapper;
import cn.plou.web.charge.chargeconfig.entity.SmsTemplate;
import cn.plou.web.charge.chargeconfig.service.SmsTemplateService;
import cn.plou.web.common.utils.IDWorker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yinxiaochen
 * 2018/8/15 15:01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmsTemplateServiceImpl  implements SmsTemplateService {


    @Resource
    private SmsTemplateMapper   smsTemplateMapper;

    @Override
    public int deleteByPrimaryKey(String primaryId) {
        return smsTemplateMapper.deleteByPrimaryKey(primaryId);
    }

    @Override
    public int insert(SmsTemplate record) {
        return smsTemplateMapper.insert(record);
    }

    @Override
    public int insertSelective(SmsTemplate record) {
        record.setPrimaryId(IDWorker.uuid());
        return smsTemplateMapper.insertSelective(record);
    }

    @Override
    public SmsTemplate selectByPrimaryKey(String primaryId) {
        return smsTemplateMapper.selectByPrimaryKey(primaryId);
    }

    @Override
    public int updateByPrimaryKeySelective(SmsTemplate record) {
        return smsTemplateMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SmsTemplate record) {
        return smsTemplateMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SmsTemplate> findAllSmsTemplates(String  companyId) {
        return smsTemplateMapper.findAllSmsTemplates(companyId);
    }

    @Override
    public SmsTemplate findByTemplateCode(String templateCode) {
        return smsTemplateMapper.findByTemplateCode(templateCode);
    }
}

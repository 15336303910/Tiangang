package cn.plou.web.charge.chargeconfig.dao;
import java.util.Date;

import cn.plou.web.charge.chargeconfig.entity.SmsTemplate;import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SmsTemplateMapper {

    int deleteByPrimaryKey(@Param("primaryId")String primaryId);


    int insert(SmsTemplate record);

    int insertSelective(SmsTemplate record);

    SmsTemplate selectByPrimaryKey(@Param("primaryId")String primaryId);


    int updateByPrimaryKeySelective(SmsTemplate record);

    int updateByPrimaryKey(SmsTemplate record);

    List <SmsTemplate >   findAllSmsTemplates(@Param("companyId")String  companyId);

    SmsTemplate findByTemplateCode(@Param("templateCode")String templateCode);


    SmsTemplate findByCompanyIdAndTitle(@Param("companyId")String companyId,@Param("title")String title);


}
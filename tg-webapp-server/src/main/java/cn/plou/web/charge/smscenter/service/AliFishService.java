package cn.plou.web.charge.smscenter.service;

import cn.plou.web.charge.chargeconfig.entity.SmsInfo;
import cn.plou.web.charge.heatingmanage.domain.MoneyClearTask;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;

import java.util.List;

/**
 */
public interface AliFishService {


    /**
     * 发送单条短信
     * @param mobile
     * @param param
     * @throws ClientException
     */
    void sendMessageByAliFish(String mobile,Object param,String templateId) throws ClientException;


    /**
     * @param templateId
     * @return
     * @throws ClientException
     * 清欠中心发送短信并记录发送结果
     */
    SmsInfo sendMessageByAliFishAndRecord(MoneyClearTask moneyClearTask, String templateId ,String companyId) throws ClientException;


    /**
     * 批量发送短信
     * @throws ClientException
     */
    void sendBatchMessageByAliFish(List<String> mobileList, List<? extends  Object> paramList, String templateId) throws ClientException;



}

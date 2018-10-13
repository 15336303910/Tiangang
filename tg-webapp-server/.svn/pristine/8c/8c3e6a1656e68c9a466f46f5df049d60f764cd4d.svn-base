package cn.plou.web.charge.smscenter.service.impl;
import java.util.Date;

import cn.plou.web.charge.chargeconfig.dao.SmsTemplateMapper;
import cn.plou.web.charge.chargeconfig.entity.SmsInfo;
import cn.plou.web.charge.chargeconfig.entity.SmsTemplate;
import cn.plou.web.charge.heatingmanage.domain.MoneyClearTask;
import cn.plou.web.charge.smscenter.service.AliFishService;
import cn.plou.web.common.utils.IDWorker;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.permission.userLogin.dao.UserLoginMapper;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * yinxiaochen   2018-8-22
 */
@Service
public class AliFishServiceImpl implements AliFishService {


    @Resource
    private SmsTemplateMapper  smsTemplateMapper;


    @Resource
    private
    UserLoginMapper userLoginMapper;
    //产品名称:云通信短信API产品,开发者无需替换
    private static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    private static final String domain = "dysmsapi.aliyuncs.com";




    /*需要配置项*/

    @Value("${alidayu.signature}")
    private    String    signature;


    @Value("${alidayu.accessKeyId}")
    private    String    accessKeyId;

    @Value("${alidayu.accessKeySecret}")
    private    String    accessKeySecret;



    @Override
    @Transactional
    //发送短息
    public void sendMessageByAliFish(String mobile, Object param,String templateId) throws ClientException {

        //验证参数
        if(mobile==null||mobile.equals("")||param==null){
            throw new RuntimeException("短信参数不完整");
        }
        //查找模板


        SmsTemplate   smsTemplate    =   smsTemplateMapper.selectByPrimaryKey(templateId);
        if(smsTemplate==null){
            throw new RuntimeException("未找到该主题对应的短信模版，请检查");
        }


        String paramJson = JSON.toJSONString(param);
        if(accessKeyId==null||accessKeySecret==null){
            throw new RuntimeException("短信模版配置不完善，请检查");
        }

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);

        //必填:待发送手机号
        request.setPhoneNumbers(mobile);
        //必填:短信签名-可在短信控制台中找到
        request .setSignName(signature);

        //必填:短信模板-可在短信控制台中找到



        request.setTemplateCode( smsTemplate.getTemplateCode());
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(paramJson);

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (sendSmsResponse.getCode() == null ||!"OK".equals(sendSmsResponse.getCode())) {
            throw new RuntimeException("发送失败");
        }

    }

    @Override
    public SmsInfo sendMessageByAliFishAndRecord(MoneyClearTask moneyClearTask, String templateId,String companyId) throws ClientException {




        //验证参数
        if(moneyClearTask.getUserTelephone()==null||moneyClearTask.getUserTelephone().equals("")){
            throw new RuntimeException("缺少手机号");
        }
        //查找模板
        SmsTemplate   smsTemplate    =   smsTemplateMapper.selectByPrimaryKey(templateId);
        if(smsTemplate==null){
            throw new RuntimeException("未找到该主题对应的短信模版，请检查");
        }


        String paramJson = JSON.toJSONString(moneyClearTask);
        if(accessKeyId==null||accessKeySecret==null){
            throw new RuntimeException("短信模版配置不完善，请检查");
        }

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);

        //必填:待发送手机号
        request.setPhoneNumbers(moneyClearTask.getUserTelephone());
        //必填:短信签名-可在短信控制台中找到
        request .setSignName(signature);

        //必填:短信模板-可在短信控制台中找到



        request.setTemplateCode( smsTemplate.getTemplateCode());
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(paramJson);

        //hint 此处可能会抛出异常，注意catch


        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);



        SmsInfo  smsInfo= new SmsInfo();
        smsInfo.setPrimaryId(IDWorker.uuid());

        smsInfo.setCompanyId(companyId);
        smsInfo.setTitle(smsTemplate.getTitle());
        smsInfo.setContents(smsTemplate.getContents());
        smsInfo.setReceiver(moneyClearTask.getUserName());
        smsInfo.setReceiverPhone(moneyClearTask.getUserTelephone());
        smsInfo.setConsumerId(moneyClearTask.getConsumerId());
        smsInfo.setStatus("成功");
        if (sendSmsResponse.getCode() == null ||!"OK".equals(sendSmsResponse.getCode())) {
            smsInfo.setStatus("失败");
        }
        smsInfo.setCreateDate(new Date());
        return smsInfo;
    }

    /**
     * 批量发送短信
     *
     */
    @Override
    public void sendBatchMessageByAliFish(List<String> mobileList, List<? extends  Object> paramList,String templateId) throws ClientException {


        //验证参数
        if(mobileList==null||mobileList.size()==0||paramList==null||paramList.size()==0){
            throw new RuntimeException("短信参数不完整");
        }
        //查找模板


        SmsTemplate   smsTemplate    =   smsTemplateMapper.selectByPrimaryKey(templateId);
        if(smsTemplate==null){
            throw new RuntimeException("未找到该主题对应的短信模版，请检查");
        }


        if(accessKeyId==null||accessKeySecret==null){
            throw new RuntimeException("短信模版配置不完善，请检查");
        }

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendBatchSmsRequest request = new SendBatchSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号
        request.setPhoneNumberJson(JSON.toJSONString(mobileList));
        //必填:短信签名-可在短信控制台中找到

        List<String> signatureList=new ArrayList<>();
        for (int i = 0; i < mobileList.size(); i++) {
            signatureList.add(signature);
        }
        request.setSignNameJson(JSON.toJSONString(signatureList));

        //必填:短信模板-可在短信控制台中找到



        request.setTemplateCode( smsTemplate.getTemplateCode());
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParamJson(JSON.toJSONString(paramList));

        //hint 此处可能会抛出异常，注意catch
        SendBatchSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (sendSmsResponse.getCode() == null ||!"OK".equals(sendSmsResponse.getCode())) {
            throw new RuntimeException("发送失败");
        }

    }


}

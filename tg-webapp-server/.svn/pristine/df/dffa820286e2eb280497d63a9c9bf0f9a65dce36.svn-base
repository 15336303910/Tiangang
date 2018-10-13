package cn.plou.web.charge.chargeconfig.controller;

import cn.plou.web.charge.chargeconfig.entity.SmsTemplate;
import cn.plou.web.charge.chargeconfig.service.SmsTemplateService;
import cn.plou.web.common.utils.IDWorker;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.permission.userLogin.service.UserLoginService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * @author yinxiaochen
 * 2018/8/15 15:45
 */

@RestController
@RequestMapping("${chargePath}/smstemplate")
@Validated
public class SmsTemplateController {

    @Resource
    private SmsTemplateService  smsTemplateService;
    @Resource
    private UserLoginService  userLoginService;

    /**
     * @return
     * 增加短信模版
     */

    @RequestMapping("/addSmsTemplate")
    public Boolean addSmsTemplate(SmsTemplate smsTemplate) {
        //主键

        //创建人
        smsTemplate.setCreateUser(userLoginService.getUserLoginById(UserUtils.getUserId()).getUsername());
        //创建时间
        smsTemplate.setCreateDate(new Date());
        return    smsTemplateService.insertSelective(smsTemplate)==1;

    }


    /**
     * @return
     *删除短信模版
     */
    @GetMapping("/delSmsTemplate")
    public Object delSmsTemplate(@NotEmpty(message = "短信模版id不能为空！") @RequestParam(value = "primaryId",defaultValue = "")  String  primaryId ) {
        return  smsTemplateService.deleteByPrimaryKey(primaryId)==1;
    }



    /**
     * @return 获取模版列表（考虑到模版的数量较少的特殊性 ，没有查询参数，暂时没有分页，后期有需要再加上）
     */
    @GetMapping("/getSmsTemplateList")
    public Object getSmsTemplateList(@RequestParam("companyId")String  companyId) {
        return    smsTemplateService.findAllSmsTemplates(companyId);

    }

}

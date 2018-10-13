package cn.plou.web.charge.chargeconfig.controller;

import cn.plou.web.charge.chargeconfig.service.SmsInfoService;
import cn.plou.web.common.config.common.Root;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

import static cn.plou.web.common.config.common.Cond.getCond;

/**
 * @author yinxiaochen
 * 2018/8/16 16:05
 */

@RestController
@RequestMapping("${chargePath}/smsinfo")
public class SmsInfoController {

    @Resource
    private SmsInfoService smsInfoService;


    /**
     * @param params 参数格式：
     *               createDate:'YYYY-MM-DD hh:mm:ss,YYYY-MM-DD hh:mm:ss'
     *               annual:'2017'
     *               receiver:'xxxxx'
     *               receiverPhone:'15555555555'
     *               status:'xxx'
     *               page:1   (必传)
     *               pageSize:10   (必传)
     *               sortby:  'receiver'
     *               order: 'desc'
     *               createUser:xxxx
     *               companyId:00001(必传)
     * @return
     */
    @GetMapping("/getSmsInfoList")
    public Object getSmsInfoList(@RequestParam Map<String, Object> params) throws Exception {

        Root root = new Root();
        String createDateString = (String) params.get("createDate");
        if (createDateString != null && !createDateString.equals("")) {
            String[] splitReadTime = new String[2];
            splitReadTime = createDateString.split(",");
            params.put("minCreateDate", splitReadTime[0]);
            params.put("maxCreateDate", splitReadTime[1]);
            if (params.get("createDate") == null || splitReadTime.length != 2) {
                throw new Exception("参数类型错误");
            }
        }
        String pageString = (String) params.get("page");
        String pageSizeString = (String) params.get("pageSize");
        if (pageString == null || pageSizeString == null) {
            throw new Exception("缺少分页参数");
        }


        PageInfo pageInfo = smsInfoService.getSmsInfoList(params);
        root.setData(pageInfo.getList());
        root.setCond(getCond(Integer.parseInt((String) params.get("page")), Integer.parseInt((String) params.get("pageSize")), (int) pageInfo.getTotal(),
                (String) params.get("sortby"), (String) params.get("order")));
        return root;
    }
}

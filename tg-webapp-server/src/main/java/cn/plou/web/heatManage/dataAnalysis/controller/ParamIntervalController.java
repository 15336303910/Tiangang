package cn.plou.web.heatManage.dataAnalysis.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.heatManage.dataAnalysis.service.ParamIntervalService;
import cn.plou.web.heatManage.dataAnalysis.vo.ParamInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: ParamIntervalController
 * @Description: 参数区间管理
 * @Author: youbc
 * @Date 2018-08-09 09:11
 */
@RestController
@RequestMapping("${heatManagePath}/dataAnalysis/paramInterval/")
public class ParamIntervalController  {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ParamIntervalService paramIntervalService;

    /**
     * @Description: 获取参数区间管理信息
     * @Param: []
     * @Return: cn.plou.web.heatManage.dataAnalysis.vo.ParamInterval
     * @Author: youbc
     * @Date: 2018/8/10 16下午47
     */
    @GetMapping("getParamInterval")
    public ParamInterval getParamInterval() {

        System.out.println("dssfdsfds");
        String userId = UserUtils.getUserId();
        logger.info("########## userId ##########:" + userId);
        return paramIntervalService.getParamInterval(userId);
    }

    /**
     * @Description: 更新参数区间管理信息
     * @Param: [id, data]
     * @Return: org.springframework.ui.ModelMap
     * @Author: youbc
     * @Date: 2018/8/13 15下午03
     */
    @PostMapping("updateParamInterval")
    public Root updateParamInterval(@RequestParam String id, @RequestParam String data) {
        String userId = UserUtils.getUserId();
        logger.info("########## userId ##########:" + userId);
        paramIntervalService.updateParamInterval(id, data, userId);
        return new Root();
    }
}

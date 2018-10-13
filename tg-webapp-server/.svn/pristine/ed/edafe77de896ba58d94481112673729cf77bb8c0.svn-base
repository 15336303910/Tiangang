package cn.plou.web.charge.heatingmanage.controller;

import cn.plou.web.charge.heatingmanage.domain.HouseHeatDetail;
import cn.plou.web.charge.heatingmanage.service.HouseHeatDetailService;
import cn.plou.web.common.config.common.Root;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.plou.web.common.config.common.Cond.getCond;

/**
 * @author yinxiaochen
 * 2018/8/21 15:35
 * 计量管理相关
 */

@RestController
@RequestMapping("${chargePath}/houseHeatingDetail")
public class HouseHeatDetailController {


    @Resource
    private HouseHeatDetailService houseHeatDetailService;

    /**
     * @param page
     * @param pageSize
     * @param id
     * @param keyWord
     * @param annual
     * @param sortby
     * @param order
     * @return  计量管理列表
     */
    @GetMapping("/getDetailList")
    public Object getHouseHeatingDetailList(@RequestParam("page") Integer page,
                                            @RequestParam("pageSize") Integer pageSize,
                                            @RequestParam("id") String id,
                                            @RequestParam(value = "keyWord", required = false) String keyWord,
                                            @RequestParam(value = "annual", required = false) String annual,
                                            @RequestParam(value = "sortby", required = false) String sortby,
                                            @RequestParam(value = "order", required = false) String order) {

        Root root = new Root();

        PageInfo pageInfo = houseHeatDetailService.getHouseHeatDetailList(id,keyWord, annual, sortby, order, page, pageSize);
        root.setData(pageInfo.getList());
        root.setCond(getCond(page, pageSize, (int) pageInfo.getTotal(),
                sortby, order));
        return root;
    }


    /**
     * @param id
     * @param annual
     * @param sortby
     * @param order
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/getHouseHeatingDetailListByConsumerId")
    public Object getHouseHeatingDetailListByConsumerId(
                                            @RequestParam("id") String id,
                                            @RequestParam(value = "annual", required = false) String annual,
                                            @RequestParam(value = "sortby", required = false) String sortby,
                                            @RequestParam(value = "order", required = false) String order,
                                            @RequestParam("page") Integer page,
                                            @RequestParam("pageSize") Integer pageSize) {

        Root root = new Root();

        PageInfo<HouseHeatDetail> pageInfo = houseHeatDetailService.findByUser(id,annual, sortby, order, page, pageSize);
        root.setData(pageInfo.getList());
        root.setCond(getCond(page, pageSize, (int) pageInfo.getTotal(),
                sortby, order));
        return root;
    }
}

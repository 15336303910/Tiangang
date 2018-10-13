package cn.plou.web.charge.heatingmanage.controller;

import cn.plou.web.charge.chargeconfig.entity.UserYearHeat;
import cn.plou.web.charge.chargeconfig.service.UserYearHeatService;
import cn.plou.web.charge.heatingmanage.service.HeatingServeService;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.charge.chargeconfig.vo.HeatInfoVO;
import cn.plou.web.system.baseMessage.house.entity.House;
import cn.plou.web.system.baseMessage.house.service.HouseService;
import cn.plou.web.system.baseMessage.house.vo.HouseInfo;
import cn.plou.web.system.commonMessage.typeMst.dao.TypeMstMapper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.plou.web.common.config.common.Cond.getCond;

import java.math.BigDecimal;
import java.util.List;

@RequestMapping("${chargePath}/heating-manage/heatinfo/")
@RestController
@Api(description = "供暖信息")
@Slf4j

public class HeatInfoController {
    @Autowired
    private UserYearHeatService userYearHeatService;
    @Resource
    private TypeMstMapper typeMstMapper;



    @ApiOperation(value = "供热信息查询")
    @RequestMapping("getHeatInfoList")
    public Root getHeatInfoList( @RequestParam(value = "companyId", required = false) String companyId,
                                 @RequestParam(value = "areaPriceType", required = false) String areaPriceType,
                                 @RequestParam(value = "chargeType", required = false) String chargeType,
                                 @RequestParam(value = "heatUserType", required = false) String heatUserType,
                                 @RequestParam(value = "heatingStatus", required = false) String heatingStatus,
                                 @RequestParam(value = "floorHigh", required = false) String floorHigh,
                                                @RequestParam(value = "order", required = false) String order,
                                                @RequestParam(value = "sortby", required = false) String sortby,
                                                @RequestParam Integer page,
                                                @RequestParam Integer pageSize) {
        System.out.println("供热信息查询api调用开始\n");
        Root root = new Root();
        if (companyId == null || companyId=="") {
            root.setCode(500);
            root.setMsg("参数companyId不能为空");
            return root;
        }
        PageInfo<HeatInfoVO> heatInfoList = userYearHeatService.getHeatInfoList(companyId,areaPriceType,chargeType,heatUserType,heatingStatus,floorHigh, order, sortby, page, pageSize);
        root.setData(heatInfoList.getList());
        root.setCond(getCond(page, pageSize, (int) heatInfoList.getTotal(),
                null, sortby));
        return root;
    }


}

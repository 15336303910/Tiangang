package cn.plou.web.system.meterMessage.meterModifyData.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Cond;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.PageInfo;
import cn.plou.web.system.meterMessage.meterModifyData.entity.MeterModifyData;
import cn.plou.web.system.meterMessage.meterModifyData.service.MeterModifyDataService;
import cn.plou.web.system.meterMessage.meterModifyData.vo.MeterModifyDataInfo;
import cn.plou.web.system.meterMessage.meterModifyData.vo.MeterModifyDataSelectInfo;
import cn.plou.web.system.meterMessage.meterModifyData.vo.MeterModifyDataVo;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@EnableSwagger2
@RequestMapping("${systemPath}/meterModifyData")
@Api(description = "设备维修记录")
public class MeterModifyDataController  {
    @Autowired
    MeterModifyDataService meterModifyDataService;

//    @ApiOperation(value = "根据id获取设备维修记录")
//    @GetMapping("/getMeterModifyDataById")
//    public Root getMeterModifyDataById(@RequestParam String meterId){
//        Root root = new Root();
//
//        return root;
//    }

    @ApiOperation(value = "增加一条设备维修记录")
    @PostMapping("/addMeterModifyData")
    @RequiresPermissions("20701")
    public Boolean addMeterModifyData(@RequestBody MeterModifyData meterModifyData){
        return meterModifyDataService.addMeterModifyData(meterModifyData)==1;
    }

    @ApiOperation(value = "根据查询条件获取全部设备维修记录")
    @PostMapping ("/getAllMeterModifyData")
    @RequiresPermissions("20704")
    public Root getAllMeterModifyData(@RequestBody MeterModifyDataSelectInfo meterModifyDataSelectInfo){
        Root root = new Root();
//        PageHelper.startPage(meterModifyDataSelectInfo.getPage(),meterModifyDataSelectInfo.getPageSize());
        com.github.pagehelper.PageInfo<MeterModifyDataInfo> pageInfo = meterModifyDataService.getAllMeterModifyData(meterModifyDataSelectInfo.getCompanyId(),
                meterModifyDataSelectInfo.getStationId(),meterModifyDataSelectInfo.getCommuityId(),meterModifyDataSelectInfo.getBuildingNo(),
                meterModifyDataSelectInfo.getUnitId(),meterModifyDataSelectInfo.getConsumerId(),meterModifyDataSelectInfo.getSearchCondition(),
                meterModifyDataSelectInfo.getOrder(), meterModifyDataSelectInfo.getSortby(),meterModifyDataSelectInfo.getPage(),meterModifyDataSelectInfo.getPageSize());
//        com.github.pagehelper.PageInfo<MeterModifyDataInfo> pageInfo=new com.github.pagehelper.PageInfo<>(meterModifyDataInfoList);
        root.setData(pageInfo.getList());
        root.setCond(Cond.getCond(meterModifyDataSelectInfo.getPage(),meterModifyDataSelectInfo.getPageSize(),(int)pageInfo.getTotal(),
                meterModifyDataSelectInfo.getOrder(),meterModifyDataSelectInfo.getSortby()));
        return root;
    }

    @ApiOperation(value = "批量修改设备维修记录")
    @PutMapping("/modifyMeterModifyDataBatch")
    @RequiresPermissions("20703")
    public Boolean modifyMeterModifyDataBatch(@RequestBody MeterModifyDataVo meterModifyDataVo){
        return meterModifyDataService.modifyMeterModifyDataBatch(meterModifyDataVo)==meterModifyDataVo.getRownos().size();
    }

    @ApiOperation(value = "修改设备维修记录")
    @PutMapping("/modifyMeterModifyData")
    @RequiresPermissions("20703")
    public Boolean modifyMeterModifyData(@RequestBody MeterModifyData meterModifyData){
        return meterModifyDataService.modifyMeterModifyData(meterModifyData)==1;
    }
}

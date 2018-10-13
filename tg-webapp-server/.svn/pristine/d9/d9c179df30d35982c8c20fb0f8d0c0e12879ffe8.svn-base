package cn.plou.web.system.meterMessage.mbusTest.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.meterMessage.mbus.dao.MbusMapper;
import cn.plou.web.system.meterMessage.mbus.entity.Mbus;
import cn.plou.web.system.meterMessage.mbus.service.MbusService;
import cn.plou.web.system.meterMessage.mbus.service.impl.MbusServiceImpl;
import cn.plou.web.system.meterMessage.mbus.vo.MbusSelectInfo;
import cn.plou.web.system.meterMessage.mbus.vo.MbusVo;
import cn.plou.web.system.meterMessage.mbusTest.entity.MbusTest;
import cn.plou.web.system.meterMessage.mbusTest.service.MbusTestService;
import cn.plou.web.system.meterMessage.mbusTest.vo.MbusTestInfo;
import cn.plou.web.system.meterMessage.mbusTest.vo.MbusTestListInfo;
import cn.plou.web.system.meterMessage.mbusTest.vo.MbusTestSelectInfo;
import cn.plou.web.system.meterMessage.mbusTest.vo.MbusTestVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static cn.plou.web.common.config.common.Cond.getCond;
import static cn.plou.web.common.utils.PageInfo.getPageInfo;

@RestController
@EnableSwagger2
@RequestMapping("${systemPath}/mbusWorkTest")
@Api(description = "通讯设备命令历史记录")
public class MbusTestController  {
    @Autowired
    private MbusTestService mbusTestService;
    @Autowired
    private MbusServiceImpl mbusService;
    @Autowired
    private MbusMapper mbusMapper;

    @ApiOperation("根据查询条件获取全部通讯设备命令历史记录")
    @PostMapping("getAllMbusTest")
    @RequiresPermissions("20104")
    public Root getAllMbusTest(@RequestBody MbusTestSelectInfo mbusTestSelectInfo) {
        Root root = new Root();
        if(mbusTestSelectInfo.getSortby()!=null){
            if(mbusTestSelectInfo.getSortby().equals("recFlagName")){
                mbusTestSelectInfo.setSortby("recFlag");
            }
        }
        //PageHelper.startPage(mbusTestSelectInfo.getPage(),mbusTestSelectInfo.getPageSize());
        MbusTestListInfo mbusTestListInfo = mbusTestService.getAllMbusTest(mbusTestSelectInfo.getPage(), mbusTestSelectInfo.getPageSize(),mbusTestSelectInfo.getCompanyId(), mbusTestSelectInfo.getStationId(), mbusTestSelectInfo.getCommuityId(), mbusTestSelectInfo.getBuildingNo(), mbusTestSelectInfo.getUnitId(), mbusTestSelectInfo.getHouseId(), mbusTestSelectInfo.getOrder(), mbusTestSelectInfo.getSortby(), mbusTestSelectInfo.getSearchCondition());
        root.setData(mbusTestListInfo.getMbusTestInfoList());
        //PageInfo pageInfo = new PageInfo(mbusTestVos);
//        root.setCond(getCond(pageInfo.getPageNum(), pageInfo.getPageSize(), (int)pageInfo.getTotal(), mbusTestSelectInfo.getOrder(), mbusTestSelectInfo.getSortby()));
        root.setCond(getCond(mbusTestSelectInfo.getPage(), mbusTestSelectInfo.getPageSize(), mbusTestListInfo.getCount(), mbusTestSelectInfo.getOrder(), mbusTestSelectInfo.getSortby()));
        return root;
    }

    /*@ApiOperation("根据id获取对应通讯设备命令历史记录")
    @GetMapping("getMbusTest")
    public Boolean getMbusTestById(@RequestParam String rowno){
        return null;
    }*/

    @ApiOperation("添加一个通讯设备命令历史信息记录")
    @PostMapping("/addMbusTest")
    @RequiresPermissions("20101")
    public Boolean addMbusTest(@RequestBody MbusTest mbusTest) {
        mbusTest.setId(mbusTestService.getNewId());
        mbusTest.setCompanyId(mbusMapper.selectByMbusCode(mbusTest.getMbusCode()).getCompanyId());
        return mbusTestService.addMbusTest(mbusTest) == 1;
    }

    @ApiOperation("批量修改通讯设备命令历史记录信息")
    @PutMapping("/modifyMbusTestBatch")
    @RequiresPermissions("20103")
    public Boolean modifyMbusTestBatch(@RequestBody MbusTestVo mbusTestVo) {
        if (mbusTestVo.getMbusCode() != null) {
            mbusTestVo.setCompanyId(mbusService.getMbusById(mbusTestVo.getMbusCode()).getCompanyId());
        }
        return mbusTestService.modifyBatch(mbusTestVo) == mbusTestVo.getIds().size();
    }

    @ApiOperation("修改一条通讯设备命令历史信息信息")
    @PutMapping("/modifyMbus")
    @RequiresPermissions("20103")
    public Boolean modifyMbus(@RequestBody MbusTest mbusTest) {
        if (mbusTest.getMbusCode() != null) {
            mbusTest.setCompanyId(mbusMapper.selectByMbusCode(mbusTest.getMbusCode()).getCompanyId());
        }
        return mbusTestService.modifyMbusById(mbusTest)==1;
    }
}

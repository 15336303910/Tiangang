package cn.plou.web.system.meterMessage.pipeDevice.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.config.common.Cond;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.common.utils.PageInfo;
import cn.plou.web.common.utils.RedisUtil;
import cn.plou.web.common.utils.SerializeUtil;
import cn.plou.web.system.baseMessage.build.vo.BuildInfo;
import cn.plou.web.system.meterMessage.pipeDevice.entity.PipeDevice;
import cn.plou.web.system.meterMessage.pipeDevice.service.PipeDeviceService;
import cn.plou.web.system.meterMessage.pipeDevice.vo.PipeDeviceInfo;
import cn.plou.web.system.meterMessage.pipeDevice.vo.PipeDeviceSelectInfo;
import cn.plou.web.system.meterMessage.pipeDevice.vo.PipeDeviceVo;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@EnableSwagger2
@RequestMapping("${systemPath}/pipeDevice")
@Api(description = "管线设备信息")
public class PipeDeviceController {

    @Autowired
    PipeDeviceService pipeDeviceService;

//    @ApiOperation(value = "根据id获取管线设备信息")
//    @GetMapping("/getPipeDeviceById")
//
//    public Root getPipeDeviceById(@RequestParam String pipeDeviceId){
//        Root root = new Root();
//
//        return root;
//    }

    @ApiOperation(value = "增加一条管线设备信息")
    @PostMapping("/addPipeDevice")
    @RequiresPermissions("20401")
    public Boolean addPipeDevice(@RequestBody PipeDevice pipeDevice) {
        return pipeDeviceService.addPipeDevice(pipeDevice) == 1;
    }

    @ApiOperation(value = "根据查询条件获取全部管线设备信息")
    @PostMapping("/getAllPipeDevice")
    @RequiresPermissions("20404")
    public Root getAllPipeDevice(@RequestBody PipeDeviceSelectInfo pipeDeviceSelectInfo) {
        Root root = new Root();
//        PageHelper.startPage(pipeDeviceSelectInfo.getPage(),pipeDeviceSelectInfo.getPageSize());
        com.github.pagehelper.PageInfo<PipeDeviceInfo> pageInfo = pipeDeviceService.getAllPipeDevice(pipeDeviceSelectInfo.getCompanyId(), pipeDeviceSelectInfo.getStationId(),
                pipeDeviceSelectInfo.getSearchCondition(), pipeDeviceSelectInfo.getOrder(), pipeDeviceSelectInfo.getSortby(), pipeDeviceSelectInfo.getPage(), pipeDeviceSelectInfo.getPageSize());
//        com.github.pagehelper.PageInfo<PipeDeviceInfo> pageInfo=new com.github.pagehelper.PageInfo<>(pipeDeviceList);
        root.setData(pageInfo.getList());
        root.setCond(Cond.getCond(pipeDeviceSelectInfo.getPage(), pipeDeviceSelectInfo.getPageSize(), (int) pageInfo.getTotal(),
                pipeDeviceSelectInfo.getOrder(), pipeDeviceSelectInfo.getSortby()));
        return root;
    }

    @ApiOperation(value = "批量修改管线设备信息")
    @PutMapping("/modifyPipeDeviceBatch")
    @RequiresPermissions("20403")
    public Boolean modifyPipeDeviceBatch(@RequestBody PipeDeviceVo pipeDeviceVo) {
        return pipeDeviceService.modifyPipeDeviceBatch(pipeDeviceVo) == pipeDeviceVo.getPipeDeviceIds().size();
    }

    @ApiOperation(value = "修改管线设备信息")
    @PutMapping("/modifyPipeDevice")
    @RequiresPermissions("20403")
    public Boolean modifyPipeDevice(@RequestBody PipeDevice pipeDevice) {
        return pipeDeviceService.modifyPipeDevice(pipeDevice) == 1;
    }

    @ApiOperation(value = "批量删除管线设备信息")
    @DeleteMapping("/deletePipeDeviceBatchByIds")
    @RequiresPermissions("20402")
    public Boolean deletePipeDeviceBatchByIds(@RequestBody List<String> pipeDeviceIds) {

        return pipeDeviceService.deletePipeDeviceBatchByIds(pipeDeviceIds) == pipeDeviceIds.size();
    }

    @ApiOperation(value = "下拉框")
    @GetMapping("/getPipeDeviceDownInfo")
    public Root getPipeDeviceDownInfo(@RequestParam String id) {
        Root root = new Root();
        root.setData(pipeDeviceService.getPipeDeviceByAscriptionId(id));
        return root;
    }

    @ApiOperation(value = "批量导入")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public ImportResult importExcel(@RequestParam("file") MultipartFile File, ServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        ImportResult importResult = new ImportResult();
        try {
            importResult = pipeDeviceService.importExcel(File, request);
        } catch (Exception e) {
        	if(e instanceof BusinessException){
        		throw e;
        	}
            modelAndView.addObject("msg", e.getMessage());
            return importResult;
        }
        if(importResult.getFailList().size()==0){
        	 modelAndView.addObject("msg", "数据导入成功");
        }
        return importResult;
    }

    @RequestMapping(value="/exportExcel",method = RequestMethod.POST)

    public Root exportExcel(@RequestBody PipeDeviceSelectInfo pipeDeviceSelectInfo,ServletRequest request) {
        Root root=new Root();
        root.setData(pipeDeviceService.exportExcel(pipeDeviceSelectInfo,request));
        return root;
    }
}

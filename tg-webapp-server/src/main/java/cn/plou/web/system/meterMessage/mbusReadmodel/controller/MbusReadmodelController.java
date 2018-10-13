package cn.plou.web.system.meterMessage.mbusReadmodel.controller;

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
import cn.plou.web.system.meterMessage.mbusReadmodel.entity.MbusReadmodel;
import cn.plou.web.system.meterMessage.mbusReadmodel.service.MbusReadmodelService;
import cn.plou.web.system.meterMessage.mbusReadmodel.vo.MbusReadmodelInfo;
import cn.plou.web.system.meterMessage.mbusReadmodel.vo.MbusReadmodelSelectInfo;
import cn.plou.web.system.meterMessage.mbusReadmodel.vo.MbusReadmodelVo;
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
@RequestMapping("${systemPath}/mbusReadmodel")
@Api(description = "采集器信息")
public class MbusReadmodelController  {

    @Autowired
    MbusReadmodelService mbusReadmodelService;

    @ApiOperation(value = "根据id获取采集器信息")
    @GetMapping("/getMbusReadmodelById")
    @RequiresPermissions("20204")
    public Root getMbusReadmodelById(@RequestParam String mbusReadmodelId) {
        Root root = new Root();
        root.setData(mbusReadmodelService.getMbusReadmodelById(mbusReadmodelId));
        return root;
    }

    @ApiOperation(value = "增加一条采集器信息")
    @PostMapping("/addMbusReadmodel")
    @RequiresPermissions("20201")
    public Boolean addMbusReadmodel(@RequestBody MbusReadmodel mbusReadmodel) {
        return mbusReadmodelService.addMbusReadmodel(mbusReadmodel) == 1;
    }

    @ApiOperation(value = "根据查询条件获取全部采集器信息")
    @PostMapping("/getAllMbusReadmodel")
    @RequiresPermissions("20204")
    public Root getAllMbusReadmodel(@RequestBody MbusReadmodelSelectInfo mbusReadmodelSelectInfo) {
        Root root = new Root();
//        PageHelper.startPage( mbusReadmodelSelectInfo.getPage(), mbusReadmodelSelectInfo.getPageSize());
        com.github.pagehelper.PageInfo<MbusReadmodelInfo> pageInfo = mbusReadmodelService.getAllMbusReadmodel(mbusReadmodelSelectInfo.getCompanyId(),
                mbusReadmodelSelectInfo.getStationId(), mbusReadmodelSelectInfo.getCommuityId(), mbusReadmodelSelectInfo.getBuildingNo(),
                mbusReadmodelSelectInfo.getUnitId(), mbusReadmodelSelectInfo.getConsumerId(),
                mbusReadmodelSelectInfo.getSearchCondition(), mbusReadmodelSelectInfo.getOrder(), mbusReadmodelSelectInfo.getSortby(),
                mbusReadmodelSelectInfo.getPage(), mbusReadmodelSelectInfo.getPageSize());
//        com.github.pagehelper.PageInfo<MbusReadmodelInfo> pageInfo=new com.github.pagehelper.PageInfo<>(mbusReadmodelList);
        root.setData(pageInfo.getList());
        root.setCond(Cond.getCond(mbusReadmodelSelectInfo.getPage(), mbusReadmodelSelectInfo.getPageSize(), (int)pageInfo.getTotal(),
                mbusReadmodelSelectInfo.getOrder(), mbusReadmodelSelectInfo.getSortby()));
        return root;
    }

    @ApiOperation(value = "批量修改采集器信息")
    @PutMapping("/modifyMbusReadmodelBatch")
    @RequiresPermissions("20203")
    public Boolean modifyMbusReadmodelBatch(@RequestBody MbusReadmodelVo mbusReadmodelVo) {
        return mbusReadmodelService.modifyMbusReadmodelBatch(mbusReadmodelVo) == mbusReadmodelVo.getMbusReadmodelIds().size();
    }

    @ApiOperation(value = "修改采集器信息")
    @PutMapping("/modifyMbusReadmodel")
    @RequiresPermissions("20203")
    public Boolean modifyMbusReadmodel(@RequestBody MbusReadmodel mbusReadmodel) {
        return mbusReadmodelService.modifyMbusReadmodel(mbusReadmodel) == 1;
    }

    @ApiOperation(value = "批量删除采集器信息（真）")
    @DeleteMapping("/deleteMbusReadmodelBatchByIds")
    @RequiresPermissions("20202")
    public Boolean deleteMbusReadmodelBatchByIds(@RequestBody List<String> mbusReadmodelIds) {
        return mbusReadmodelService.deleteMbusReadmodelBatchByIds(mbusReadmodelIds) == mbusReadmodelIds.size();
    }


    @ApiOperation(value = "批量删除采集器信息（假）")
    @PutMapping("/setDelMbusReadmodelBatch")
    @RequiresPermissions("20202")
    public Boolean setDelMbusReadmodelBatch(@RequestBody List<String> mbusReadmodelIds) {
        return mbusReadmodelService.setDelMbusReadmodelBatch(mbusReadmodelIds) == mbusReadmodelIds.size();
    }

    @ApiOperation(value = "根据公司获取采集器下拉框")
    @GetMapping("/getMbusReadmodelDownInfo")
    // @RequiresPermissions("20204")
    public Root getMbusReadmodelDownInfo(@RequestParam String companyId) {
        Root root = new Root();
        root.setData(mbusReadmodelService.getMbusReadmodelDownInfo(companyId));
        return root;
    }

    @ApiOperation(value = "根据集中器获取采集器下拉框")
    @GetMapping("/getMbusReadmodelByMbusId")
    public Root getMbusReadmodelByMbusId(@RequestParam String mbusId){
        Root root = new Root();
        root.setData(mbusReadmodelService.getMbusReadmodelByMbusId(mbusId));
        return root;
    }

    @ApiOperation(value = "批量导入")
    @RequestMapping(value="/importExcel",method=RequestMethod.POST)
    public ImportResult importExcel(@RequestParam("file") MultipartFile File, ServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        ImportResult importResult = new ImportResult();
        try {
            importResult = mbusReadmodelService.importExcel(File,request);
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

    public Root exportExcel(@RequestBody MbusReadmodelSelectInfo mbusReadmodelSelectInfo,ServletRequest request) {
        Root root=new Root();
        root.setData(mbusReadmodelService.exportExcel(mbusReadmodelSelectInfo,request));
        return root;
    }
}

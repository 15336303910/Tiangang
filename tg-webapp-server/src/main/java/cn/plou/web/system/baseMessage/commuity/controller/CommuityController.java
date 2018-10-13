package cn.plou.web.system.baseMessage.commuity.controller;
import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.common.utils.RedisUtil;
import cn.plou.web.common.utils.SerializeUtil;
import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import cn.plou.web.system.baseMessage.commuity.service.CommuityService;
import cn.plou.web.system.baseMessage.commuity.vo.*;
import cn.plou.web.system.baseMessage.company.entity.Company;
import cn.plou.web.system.baseMessage.company.service.CompanyService;
import cn.plou.web.system.baseMessage.station.entity.Station;
import cn.plou.web.system.baseMessage.station.service.StationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static cn.plou.web.common.config.common.Cond.getCond;

@RestController
@RequestMapping("${systemPath}/commuity")
@EnableSwagger2
@Api(description = "地址管理")
public class CommuityController{
    @Autowired
    private CommuityService commuityService;
    @Autowired
    private StationService stationService;

    @ApiOperation(value = "获取全部地址信息")
    @PostMapping("/getAllCommuity")
    @RequiresPermissions("10604")
    public Root getAllCommuity(@RequestBody CommuitySelectInfo commuitySelectInfo){
        Root root = new Root();
        if(commuitySelectInfo.getSortby()!=null){
            if(commuitySelectInfo.getSortby().equals("cbzqName")){
                commuitySelectInfo.setSortby("cbzq");
            }else if(commuitySelectInfo.getSortby().equals("hasBuildMeterName")){
                commuitySelectInfo.setSortby("hasBuildMeter");
            }else if(commuitySelectInfo.getSortby().equals("xqztName")){
                commuitySelectInfo.setSortby("xqzt");
            }
        }
        //PageHelper.startPage(commuitySelectInfo.getPage(),commuitySelectInfo.getPageSize());
        CommuityListInfo commuityListInfo = commuityService.getAllCommuity(commuitySelectInfo.getPage(),commuitySelectInfo.getPageSize(),commuitySelectInfo.getOrder(), commuitySelectInfo.getSortby(), commuitySelectInfo.getCompanyId(), commuitySelectInfo.getStationId(), commuitySelectInfo.getCommuityId(),commuitySelectInfo.getSearchCondition());
        //PageInfo pageInfo = new PageInfo<>(commuityList);
        //root.setCond(getCond(pageInfo.getPageNum(),pageInfo.getPageSize(),(int)pageInfo.getTotal(),commuitySelectInfo.getOrder(), commuitySelectInfo.getSortby()));
        root.setCond(getCond(commuitySelectInfo.getPage(),commuitySelectInfo.getPageSize(),commuityListInfo.getCount(),commuitySelectInfo.getOrder(), commuitySelectInfo.getSortby()));
        root.setData(commuityListInfo.getCommuityInfoList());
        return root;
    }

   /* @ApiOperation("根据id获取对应地址信息")
    @PostMapping("/getCommuityById")
    public Root getCommuityById(@RequestBody CommuityKey commuityKey){
        Root root = new Root();
        root.setData(commuityService.getCommuityById(commuityKey));
        return root;
    }*/

    @ApiOperation("根据id批量删除地址信息")
    @DeleteMapping("/deleteBatch")
    @RequiresPermissions("10602")
    public Boolean deleteCommuityBatchByIds(@RequestBody List<String> commuityIds){
        return commuityService.deleteBatchByIds(commuityIds)==commuityIds.size();
    }

    @ApiOperation("添加一个地址信息")
    @PostMapping("/addCommuity")
    @RequiresPermissions("10601")
    public Boolean addCommuity(@RequestBody Commuity commuity){

        return commuityService.addCommuity(commuity)==1;
    }

    @ApiOperation("批量修改地址信息")
    @PutMapping("/modifyCommuityBatch")
    @RequiresPermissions("10603")
    public Boolean modifyCommuityBatch(@RequestBody CommuityVo commuityVo){
        if(commuityVo.getStationId()!=null){
            commuityVo.setCompanyId(stationService.getStationById(commuityVo.getStationId()).getCompanyId());
    }
        return commuityService.modifyCommuityBatch(commuityVo)==commuityVo.getCommuityIds().size();
    }

    @ApiOperation("修改一条地址信息")
    @PutMapping("/modifyCommuity")
    @RequiresPermissions("10603")
    public Boolean modifyCommuity(@RequestBody Commuity commuity){
        if(commuity.getStationId()!=null){
            commuity.setCompanyId(stationService.getStationById(commuity.getStationId()).getCompanyId());
        }
        return commuityService.modifyCommuity(commuity)==1;
    }

    @ApiOperation("根据换热站id获取全部地址名")
    @GetMapping("/getCommuityNameByStationId")
    //@RequiresPermissions("10604")
    public List<Commuity> getCommuityNameByStationId(@RequestParam String stationId){
        List<Commuity> treeList = commuityService.getTreeList(stationId);
        return treeList;
    }

    @ApiOperation("根据树获取全部地址id")
    @GetMapping("/getCommuityByTree")
    // @RequiresPermissions("10604")
    public List<String> getCommuityByTree(@RequestParam(required = false) String companyId,
                                          @RequestParam(required = false) String stationId,
                                          @RequestParam(required = false) String commuityId){
        return commuityService.getCommuityIdsList(companyId,stationId,commuityId);
    }


    @ApiOperation("根据excel表格批量获取地址信息")
    @PostMapping(value="/importExcel")
    public ImportResult importCommuityExcel(@RequestParam("file") MultipartFile File, ServletRequest request) {
    	  ImportResult importResult = commuityService.importExcel(File,request);
        return importResult;
    }

    @ApiOperation("批量导出地址信息")
    @PostMapping("/exportExcel")
    public Root exportExcel(ServletRequest request,@RequestBody CommuitySelectInfo commuitySelectInfo) {
        Root root = new Root();
        root.setCode(0);
        root.setMsg("导出成功");
        root.setData(commuityService.exportExcel(request,commuitySelectInfo));
        return root;
    }
    /*
    @RequestMapping(value="/exportCommuityExcel",method=RequestMethod.GET)
    public void exportCommuityExcel(HttpServletResponse response) {
        try {
            commuityService.exportExcel(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}

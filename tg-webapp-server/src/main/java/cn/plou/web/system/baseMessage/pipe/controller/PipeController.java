package cn.plou.web.system.baseMessage.pipe.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.baseMessage.company.service.CompanyService;
import cn.plou.web.system.baseMessage.pipe.entity.PipeKey;
import cn.plou.web.system.baseMessage.pipe.service.PipeService;
import cn.plou.web.system.baseMessage.pipe.entity.Pipe;
import cn.plou.web.system.baseMessage.pipe.vo.PipeInfo;
import cn.plou.web.system.baseMessage.pipe.vo.PipeListInfo;
import cn.plou.web.system.baseMessage.pipe.vo.PipeSelectInfo;
import cn.plou.web.system.baseMessage.pipe.vo.PipeVo;
import cn.plou.web.system.baseMessage.producer.service.ProducerService;
import cn.plou.web.system.baseMessage.station.service.StationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static cn.plou.web.common.config.common.Cond.getCond;
import static cn.plou.web.common.utils.PageInfo.getPageInfo;

@RestController
@EnableSwagger2
@RequestMapping("${systemPath}/pipe")
@Api(description = "管线管理")
public class PipeController  {
    @Autowired
    private PipeService pipeService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ProducerService producerService;
    @Autowired
    private StationService stationService;

    @ApiOperation("根据条件获取全部管线信息")
    @PostMapping("/getAllPipe")
    @RequiresPermissions("10204")
    public Root getAllPipe(@RequestBody PipeSelectInfo pipeSelectInfo){
        Root root = new Root();
        if(pipeSelectInfo.getSortby()!=null){
            if(pipeSelectInfo.getSortby().equals("pipeSourceName")){
                pipeSelectInfo.setSortby("pipeSourceId");
            }else if(pipeSelectInfo.getSortby().equals("pipeLengthName")) {
                pipeSelectInfo.setSortby("pipeLength");
            }else if(pipeSelectInfo.getSortby().equals("pipeTypeNameName")) {
                pipeSelectInfo.setSortby("pipeTypeName");
            }else if(pipeSelectInfo.getSortby().equals("pipeFormatName")) {
                pipeSelectInfo.setSortby("pipeFormat");
            }
        }
        List<String> companyList = companyService.getCompanyIdsByCompanyId(pipeSelectInfo.getCompanyId());
        //PageHelper.startPage(pipeSelectInfo.getPage(),pipeSelectInfo.getPageSize());
        PipeListInfo pipeListInfo = pipeService.getAllPipe(pipeSelectInfo.getPage(),pipeSelectInfo.getPageSize(),pipeSelectInfo.getSortby(),pipeSelectInfo.getOrder(),pipeSelectInfo.getSearchCondition(),companyList,pipeSelectInfo.getStationId());
        //PageInfo pageInfo = new PageInfo(pipeList);
        //root.setCond(getCond(pageInfo.getPageNum(),pageInfo.getPageSize(),(int)pageInfo.getTotal(),pipeSelectInfo.getOrder(),pipeSelectInfo.getSortby()));
        root.setCond(getCond(pipeSelectInfo.getPage(),pipeSelectInfo.getPageSize(),pipeListInfo.getCount(),pipeSelectInfo.getOrder(),pipeSelectInfo.getSortby()));
        root.setData(pipeListInfo.getPipeInfoList());
        return root;
    }

    @ApiOperation("根据id获取对应管线信息")
    @PostMapping("/getPipeById")
    @RequiresPermissions("10204")
    public Root getPipeById(@RequestBody PipeKey pipeKey){
        Root root = new Root();
        root.setData(pipeService.getPipeById(pipeKey));
        return root;
    }

    @ApiOperation("根据id批量删除管线信息")
    @DeleteMapping("/deleteBatch")
    @RequiresPermissions("10202")
    public Boolean dropPipeBatchByIds(@RequestBody List<PipeKey> pipeKeyList){
        return pipeService.deleteBatchByIds(pipeKeyList)==pipeKeyList.size();
    }

    @ApiOperation("添加一个管线信息")
    @PostMapping("/addPipe")
    @RequiresPermissions("10201")
    public Boolean addPipe(@RequestBody Pipe pipe){
        pipe.setPipeTypeId(pipeService.getNewPipeId());
        if(pipe.getPipeSourceId().length()==5){
            pipe.setCompanyId(producerService.getProducerById(pipe.getPipeSourceId()).getCompanyId());
        }else if(pipe.getPipeSourceId().length()==6){
            pipe.setCompanyId(stationService.getStationById(pipe.getPipeSourceId()).getCompanyId());
        }
        return pipeService.addPipe(pipe)==1;
    }

    @ApiOperation("根据条件批量修改管线信息")
    @PutMapping("modifyPipeBatch")
    @RequiresPermissions("10203")
    public Boolean modifyPipeBatch(@RequestBody PipeVo pipeVo){
        if(pipeVo.getPipeSourceId()!=null){
            if(pipeVo.getPipeSourceId().length()==5){
                pipeVo.setCompanyId(producerService.getProducerById(pipeVo.getPipeSourceId()).getCompanyId());
            }else if(pipeVo.getPipeSourceId().length()==6){
                pipeVo.setCompanyId(stationService.getStationById(pipeVo.getPipeSourceId()).getCompanyId());
            }
        }
        return pipeService.modifyBatch(pipeVo)==pipeVo.getPipeKeyList().size();
    }

    @ApiOperation("修改一条管线信息")
    @PutMapping("modifyPipe")
    @RequiresPermissions("10203")
    public Boolean modifyPipe(@RequestBody Pipe pipe){
        if(pipe.getPipeSourceId()!=null){
            if(pipe.getPipeSourceId().length()==5){
                pipe.setCompanyId(producerService.getProducerById(pipe.getPipeSourceId()).getCompanyId());
            }else if(pipe.getPipeSourceId().length()==6){
                pipe.setCompanyId(stationService.getStationById(pipe.getPipeSourceId()).getCompanyId());
            }
        }
        return pipeService.modifyPipe(pipe)==1;
    }

    @ApiOperation("根据公司获取管线列表")
    @GetMapping("getPipeDownInfo")
    public List<Pipe> getPipeDownInfo(@RequestParam("ascriptionId") String ascriptionId){
        return pipeService.getPipeDownInfo(ascriptionId);
    }
}

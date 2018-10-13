package cn.plou.web.system.baseMessage.producer.controller;

import cn.plou.web.common.config.MyResponseBodyAdvice;
import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.baseMessage.producer.entity.Producer;
import cn.plou.web.system.baseMessage.producer.service.ProducerService;
import cn.plou.web.system.baseMessage.producer.vo.ProducerInfo;
import cn.plou.web.system.baseMessage.producer.vo.ProducerSelectInfo;
import cn.plou.web.system.baseMessage.producer.vo.ProducerVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cn.plou.web.common.config.common.Cond.getCond;
import static cn.plou.web.common.utils.PageInfo.getPageInfo;

@RestController
@RequestMapping("${systemPath}/producer")
@EnableSwagger2
@Api(description = "源信息")
public class ProducerController  extends MyResponseBodyAdvice {
    @Autowired
    ProducerService producerService;

    @ApiOperation(value = "根据id获取源信息")
    @GetMapping("/getProducerById")
    @RequiresPermissions("10104")
    public Root getProducerById(@RequestParam String producerId){
        Root root = new Root();
        root.setData(producerService.getProducerById(producerId));
        return root;
    }

    @ApiOperation(value = "增加一条源信息")
    @PostMapping("/addProducer")
    @RequiresPermissions("10101")
    public Boolean addProducer(@RequestBody Producer producer){
        return producerService.addProducer(producer)==1;
    }

    @ApiOperation(value = "根据查询条件获取全部源信息")
    @PostMapping ("/getAllProducer")
    @RequiresPermissions("10104")
    public Root getAllProducer(@RequestBody ProducerSelectInfo producerSelectInfo){
        Root root = new Root();
//        PageHelper.startPage(producerSelectInfo.getPage(),producerSelectInfo.getPageSize());
        PageInfo<ProducerInfo> pageInfo=producerService.getAllProducer(producerSelectInfo.getCompanyId(),producerSelectInfo.getProducerId(),
                    producerSelectInfo.getSearchCondition(), producerSelectInfo.getOrder(),producerSelectInfo.getSortby(),producerSelectInfo.getPage(),producerSelectInfo.getPageSize());
//        PageInfo<ProducerInfo> pageInfo=new PageInfo<>(producerList);
            if(pageInfo!=null){
                root.setData(pageInfo.getList());
                root.setCond(getCond(pageInfo.getPageNum(),pageInfo.getPageSize(),(int)pageInfo.getTotal(),producerSelectInfo.getOrder(),producerSelectInfo.getSortby()));
            }
        return root;
    }

    @ApiOperation(value = "批量修改源信息")
    @PutMapping("/modifyProducerBatch")
    @RequiresPermissions("10103")
    public Boolean modifyProducerBatch(@RequestBody ProducerVo producerVo){
        return producerService.modifyProducerBatch(producerVo)==producerVo.getProducerIds().size();
    }


    @ApiOperation(value = "修改源信息")
    @PutMapping("/modifyProducer")
    @RequiresPermissions("10103")
    public Boolean modifyProducer(@RequestBody Producer producer){
        return producerService.modifyProducer(producer)==1;
    }

    @ApiOperation(value = "批量删除源信息")
    @DeleteMapping("/deleteProducerBatchByIds")
    @RequiresPermissions("10102")
    public Boolean deleteProducerBatchByIds(@RequestBody List<String>producerIds){
        if(false){
            return false;
        }else {
         return   producerService.deleteProducerBatchByIds(producerIds)==producerIds.size();
        }
    }

    @ApiOperation(value = "下拉框")
    @GetMapping("/getProducerDownInfo")
    // @RequiresPermissions("10104")
    public Root getProducerDownInfo(@RequestParam String companyId){
        Root root = new Root();
        root.setData(producerService.getProducerByCompanyId(companyId));
        return root;
    }

    @ApiOperation(value = "根据源ID倒查")
    @GetMapping("/getStructureByProducerId")
    // @RequiresPermissions("10104")
    public Root getStructureByProducerId(@RequestParam String producerId){
        Root root = new Root();
        root.setData(producerService.getStructureByProducerId(producerId));
        return root;
    }
}

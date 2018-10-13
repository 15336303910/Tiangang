package cn.plou.web.system.meterMessage.meterKey.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Cond;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.common.utils.PageInfo;
import cn.plou.web.system.meterMessage.meterKey.entity.MeterKey;
import cn.plou.web.system.meterMessage.meterKey.service.MeterKeyService;
import cn.plou.web.system.meterMessage.meterKey.vo.MeterKeyInfo;
import cn.plou.web.system.meterMessage.meterKey.vo.MeterKeySelectInfo;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@EnableSwagger2
@RequestMapping("${systemPath}/meterKey")
@Api(description = "密钥信息")
public class MeterKeyController  {
    @Autowired
    MeterKeyService meterKeyService;

//    @ApiOperation(value = "根据id获取密钥信息")
//    @GetMapping("/getMeterKeyById")
//    @RequiresPermissions("20504")
//    public Root getMeterKeyById(@RequestParam String id){
//        Root root = new Root();
//
//        return root;
//    }

    @ApiOperation(value = "增加一条密钥信息")
    @PostMapping("/addMeterKey")
    @RequiresPermissions("20501")
    public Boolean addMeterKey(@RequestBody MeterKeyInfo meterKeyInfo){
        return meterKeyService.addMeterKey(meterKeyInfo)==1;
    }

    @ApiOperation(value = "根据查询条件获取全部密钥信息")
    @PostMapping ("/getAllMeterKey")
    @RequiresPermissions("20504")
    public Root getAllMeterKey(@RequestBody MeterKeySelectInfo meterKeySelectInfo){
        Root root = new Root();
//        PageHelper.startPage(meterKeySelectInfo.getPage(),meterKeySelectInfo.getPageSize());
        com.github.pagehelper.PageInfo<MeterKey> pageInfo = meterKeyService.getAllMeterKey(meterKeySelectInfo.getCompanyId(),meterKeySelectInfo.getStationId(),
                meterKeySelectInfo.getCommuityId(),meterKeySelectInfo.getBuildingNo(),meterKeySelectInfo.getUnitId(),
                meterKeySelectInfo.getConsumerId(),meterKeySelectInfo.getOrder(), meterKeySelectInfo.getSortby(),
                meterKeySelectInfo.getPage(),meterKeySelectInfo.getPageSize());
//        com.github.pagehelper.PageInfo<MeterKey> pageInfo=new com.github.pagehelper.PageInfo<>(meterKeyList);
        root.setData(pageInfo.getList());
        root.setCond(Cond.getCond(pageInfo.getPageNum(),pageInfo.getPageSize(),(int)pageInfo.getTotal(),meterKeySelectInfo.getOrder(),meterKeySelectInfo.getSortby()));
        return root;
    }

    @RequestMapping(value="/importCommuityExcel",method=RequestMethod.POST)
    public ImportResult importCommuityExcel(@RequestParam("file") MultipartFile File) {
        ModelAndView modelAndView = new ModelAndView();
        ImportResult importResult = new ImportResult();
        try {
            importResult = meterKeyService.importExcel(File);
        } catch (Exception e) {
            modelAndView.addObject("msg", e.getMessage());
            return importResult;
        }
        modelAndView.addObject("msg", "数据导入成功");
        return importResult;
    }
}

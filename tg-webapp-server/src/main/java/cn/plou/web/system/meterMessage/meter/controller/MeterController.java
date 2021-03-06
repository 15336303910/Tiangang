package cn.plou.web.system.meterMessage.meter.controller;

import static cn.plou.web.common.config.common.Cond.getCond;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.idgenerater.service.IDGenerater;
import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.common.utils.RedisUtil;
import cn.plou.web.common.utils.SerializeUtil;
import cn.plou.web.system.baseMessage.station.service.StationService;
import cn.plou.web.system.meterMessage.meter.entity.Meter;
import cn.plou.web.system.meterMessage.meter.service.MeterService;
import cn.plou.web.system.meterMessage.meter.vo.MeterListInfo;
import cn.plou.web.system.meterMessage.meter.vo.MeterSelectInfo;
import cn.plou.web.system.meterMessage.meter.vo.MeterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import redis.clients.jedis.Jedis;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@EnableSwagger2
@RequestMapping("${systemPath}/meter")
@Api(description = "仪表信息管理")
public class MeterController  {
    @Autowired
    private MeterService meterService;
    @Autowired
    private StationService stationService;
    @Autowired
    private IDGenerater idGenerater;
    
    @ApiOperation("根据查询条件获取全部仪表信息")
    @PostMapping("getAllMeter")
    @RequiresPermissions("20304")
    public Root getAllMeter(@RequestBody MeterSelectInfo meterSelectInfo) {
        Root root = new Root();
        if(meterSelectInfo.getSortby()!=null){
            if(meterSelectInfo.getSortby().equals("protocolName")){
                meterSelectInfo.setSortby("protocol");
            }else if(meterSelectInfo.getSortby().equals("mbusProName")){
                meterSelectInfo.setSortby("mbusPro");
            }else if(meterSelectInfo.getSortby().equals("meterTypeName")){
                meterSelectInfo.setSortby("meterType");
            }else if(meterSelectInfo.getSortby().equals("runningStateName")){
                meterSelectInfo.setSortby("runningState");
            }else if(meterSelectInfo.getSortby().equals("factoryName")){
                meterSelectInfo.setSortby("factory");
            }else if(meterSelectInfo.getSortby().equals("meterSubTypeName")){
                meterSelectInfo.setSortby("meterSubType");
            }else if(meterSelectInfo.getSortby().equals("meterStateName")){
                meterSelectInfo.setSortby("meterState");
            }
        }
        //PageHelper.startPage(meterSelectInfo.getPage(),meterSelectInfo.getPageSize());
        MeterListInfo meterListInfo = meterService.getAllMeter(meterSelectInfo.getPage(),meterSelectInfo.getPageSize(),meterSelectInfo.getOrder(), meterSelectInfo.getSortby(), meterSelectInfo.getCompanyId(), meterSelectInfo.getStationId(), meterSelectInfo.getCommuityId(), meterSelectInfo.getBuildingNo(), meterSelectInfo.getUnitId(), meterSelectInfo.getConsumerId(), meterSelectInfo.getSearchCondition());
        //PageInfo pageInfo = new PageInfo(meterInfoList);
        root.setData(meterListInfo.getMeterInfoList());
        //root.setCond(getCond(pageInfo.getPageNum(), pageInfo.getPageSize(), (int)pageInfo.getTotal(), meterSelectInfo.getOrder(), meterSelectInfo.getSortby()));
        root.setCond(getCond(meterSelectInfo.getPage(),meterSelectInfo.getPageSize(), meterListInfo.getCount(), meterSelectInfo.getOrder(), meterSelectInfo.getSortby()));
        return root;
    }

    @ApiOperation("根据id获取对应仪表信息")
    @GetMapping("getMeterInfoById")
    @RequiresPermissions("20304")
    public Root getMeterInfoById(@RequestParam String meterId) {
        Root root = new Root();
        root.setData(meterService.getMeterInfoById(meterId));
        return root;
    }

    @ApiOperation("根据上级地址id获取仪表下拉框")
    @GetMapping("getMeterDownInfo")
    public List<Meter> getMeterDownInfoByConsumerId(@RequestParam String consumerId) {
        return meterService.getMeterDownInforByConsumer(consumerId);
    }

    @ApiOperation("根据id批量删除仪表信息（真）")
    @DeleteMapping("deleteBatch")
    @RequiresPermissions("20302")
    public Boolean deletepMeterBatchByIds(@RequestBody List<String> meterIds) {
        return meterService.deleteMeterBatchByIds(meterIds) == meterIds.size();
    }

    @ApiOperation("根据id批量删除仪表信息（假）")
    @PutMapping("setDelMeterBatch")
    @RequiresPermissions("20302")
    public Boolean setDelMeterBatchByIds(@RequestBody List<String> meterIds) {
        return meterService.setDelMeterBatchByIds(meterIds) == meterIds.size();
    }

    @ApiOperation("添加一个仪表信息")
    @PostMapping("/addMeter")
    @RequiresPermissions("20301")
    public Boolean addMeter(@RequestBody Meter meter) {
    	
        meter.setMeterId(idGenerater.generateMeterId(meter.getMeterType(),meter.getConsumerId(), true));
        meter.setRowno(UUID.randomUUID().toString().replace("-",""));
        if(meter.getMeterPosition().equals("1")){
            meter.setCompanyId(stationService.getStationById(meter.getConsumerId()).getCompanyId());
        }else{
            meter.setCompanyId(meter.getConsumerId().substring(0, 5));
        }
        //meter.setIsvalid(1);
        return meterService.addMeter(meter) == 1;
    }

    @ApiOperation("批量修改仪表信息")
    @PutMapping("/modifyMeterBatch")
    @RequiresPermissions("20303")
    public Boolean modifyMeterBatch(@RequestBody MeterVo meterVo) {
        if (meterVo.getConsumerId() != null) {
            if(meterVo.getMeterPosition().equals("1")){
                meterVo.setCompanyId(stationService.getStationById(meterVo.getConsumerId()).getCompanyId());
            }else{
                meterVo.setCompanyId(meterVo.getConsumerId().substring(0, 5));
            }
        }
        return meterService.modifyBatchByIds(meterVo) == meterVo.getMeterIds().size();
    }

    @ApiOperation("修改一条仪表信息")
    @PutMapping("/modifyMeter")
    @RequiresPermissions("20303")
    public Boolean modifyMeter(@RequestBody Meter meter) {
        if(meter.getMeterPosition().equals("1")){
            if(stationService.getStationById(meter.getConsumerId())!=null){
                meter.setCompanyId(stationService.getStationById(meter.getConsumerId()).getCompanyId());
            }
        }else{
            meter.setCompanyId(meter.getConsumerId().substring(0, 5));
        }
        return meterService.modifyMeter(meter) == 1;
    }
    @ApiOperation("根据excel表格批量获取仪表信息")
    @PostMapping(value="/importExcel")
    public ImportResult excelImport(@RequestParam("file") MultipartFile file, ServletRequest request) {
        ImportResult importResult = meterService.importExcel(file,request);
        return importResult;
    }

    @ApiOperation("批量导出仪表信息")
    @PostMapping("/exportExcel")
    public Root exportExcel(ServletRequest request,@RequestBody MeterSelectInfo meterSelectInfo) {
        Root root = new Root();
        root.setCode(0);
        root.setMsg("导出成功");
        root.setData(meterService.exportExcel(request,meterSelectInfo));
        return root;
    }
}

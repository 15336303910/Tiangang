package cn.plou.web.system.meterMessage.mbus.controller;

import static cn.plou.web.common.config.common.Cond.getCond;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import cn.plou.web.system.meterMessage.mbus.dao.MbusMapper;
import cn.plou.web.system.meterMessage.mbus.vo.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.alibaba.fastjson.JSON;

import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.system.baseMessage.station.service.StationService;
import cn.plou.web.system.meterMessage.mbus.entity.Mbus;
import cn.plou.web.system.meterMessage.mbus.entity.MeterTimingDefine;
import cn.plou.web.system.meterMessage.mbus.service.MbusService;
import cn.plou.web.system.meterMessage.mbus.service.MeterTimingDefineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@RequestMapping("${systemPath}/mbus")
@EnableSwagger2
@Api(description = "通讯设备管理")
public class MbusController  {
    private static final Logger log = LoggerFactory.getLogger(MbusController.class);
    @Autowired
    private MbusService mbusService;
    @Autowired
    private StationService stationService;
    @Autowired
    private MeterTimingDefineService meterTimingDefineService;
    @Autowired
    private MbusMapper mbusMapper;

    @ApiOperation("根据查询条件获取全部通讯设备信息")
    @PostMapping("/getAllMbus")
    @RequiresPermissions("20004")
    public Root getAllMbus(@RequestBody MbusSelectInfo mbusSelectInfo) {
        Root root = new Root();
        if(mbusSelectInfo.getSortby()!=null){
            if(mbusSelectInfo.getSortby().equals("factoryName")){
                mbusSelectInfo.setSortby("factory");
            }else if(mbusSelectInfo.getSortby().equals("mbusPositionName")){
                mbusSelectInfo.setSortby("mbusPosition");
            }else if(mbusSelectInfo.getSortby().equals("upCommModeName")){
                mbusSelectInfo.setSortby("upCommMode");
            }else if(mbusSelectInfo.getSortby().equals("transModeName")){
                mbusSelectInfo.setSortby("transMode");
            }else if(mbusSelectInfo.getSortby().equals("channlModeName")){
                mbusSelectInfo.setSortby("channlMode");
            }else if(mbusSelectInfo.getSortby().equals("downProName")){
                mbusSelectInfo.setSortby("downPro");
            }else if(mbusSelectInfo.getSortby().equals("mbusProName")){
                mbusSelectInfo.setSortby("mbusPro");
            }else if(mbusSelectInfo.getSortby().equals("protocolName")){
                mbusSelectInfo.setSortby("protocol");
            }else if(mbusSelectInfo.getSortby().equals("simProviderName")){
                mbusSelectInfo.setSortby("simProvider");
            }else if(mbusSelectInfo.getSortby().equals("isFirstName")){
                mbusSelectInfo.setSortby("isFirst");
            }else if(mbusSelectInfo.getSortby().equals("runningStateName")){
                mbusSelectInfo.setSortby("runningState");
            }else if(mbusSelectInfo.getSortby().equals("busyStatusName")){
                mbusSelectInfo.setSortby("busyStatus");
            }else if(mbusSelectInfo.getSortby().equals("equipmentNoName")){
                mbusSelectInfo.setSortby("equipmentNo");
            }
        }
        //PageHelper.startPage(mbusSelectInfo.getPage(),mbusSelectInfo.getPageSize());
        MbusListInfo mbusListInfo = mbusService.getAllMbus(mbusSelectInfo.getPage(),mbusSelectInfo.getPageSize(),mbusSelectInfo.getCompanyId(), mbusSelectInfo.getStationId(), mbusSelectInfo.getCommuityId(), mbusSelectInfo.getBuildingNo(), mbusSelectInfo.getUnitId(), mbusSelectInfo.getHouseId(), mbusSelectInfo.getOrder(), mbusSelectInfo.getSortby(), mbusSelectInfo.getSearchCondition());
        //PageInfo pageInfo = new PageInfo(mbusList);
        root.setData(mbusListInfo.getMbusInfoList());
//        root.setCond(getCond(pageInfo.getPageNum(), pageInfo.getPageSize(), (int)pageInfo.getTotal(), mbusSelectInfo.getOrder(), mbusSelectInfo.getSortby()));
        root.setCond(getCond(mbusSelectInfo.getPage(), mbusSelectInfo.getPageSize(),mbusListInfo.getCount(), mbusSelectInfo.getOrder(), mbusSelectInfo.getSortby()));
        return root;
    }

    @ApiOperation("根据所属id获取通讯设备下拉框")
    @GetMapping("/getMbusDownInfo")
    //  @RequiresPermissions("20004")
    public List<Mbus> getMbusDownInfoByConsumerId(@RequestParam String consumerId) {
        return mbusService.geDownInfoByConsumerId(consumerId);
    }

    @ApiOperation("根据id获取对应通讯设备信息")
    @GetMapping("/getMbusById")
    @RequiresPermissions("20004")
    public Mbus getMbusById(@RequestParam String mbusId) {
        List<String> list = JSON.parseArray(mbusId,String.class);
        return mbusService.getMbusById(mbusId);
    }

    @ApiOperation("根据id批量删除通讯设备信息（真）")
    @DeleteMapping("/deleteMbusBatch")
    @RequiresPermissions("20002")
    public Boolean deleteMbusBatchByIds(@RequestBody List<String> mbusIds) {
        return mbusService.deleteMbusBatchByIds(mbusIds) == mbusIds.size();
    }

    @ApiOperation("根据id批量删除通讯设备信息（假）")
    @PutMapping("setDelMbusBatch")
    @RequiresPermissions("20002")
    public Boolean setDelMbusBatchByIds(@RequestBody List<String> mbusIds) {
        return mbusService.modifyDelMbusBatchById(mbusIds) == mbusIds.size();
    }

    @ApiOperation("添加一个通讯设备信息")
    @PostMapping("/addMbus")
    @RequiresPermissions("20001")
    public Boolean addMbus(@RequestBody Mbus mbus) {
        mbus.setIsvalid(1);
        mbus.setMbusId(mbus.getMbusCode() );
        if(mbus.getMbusPosition().contains("1")){
            mbus.setCompanyId(stationService.getStationById(mbus.getConsumerId()).getCompanyId());
        }else{
            mbus.setCompanyId(mbus.getConsumerId().substring(0, 5));
        }
        return mbusService.addMbus(mbus) == 1;
    }

    @ApiOperation("批量修改通讯设备信息")
    @PutMapping("/modifyMbusBatch")
    @RequiresPermissions("20003")
    public Boolean modifyMbusBatch(@RequestBody MbusVo mbusVo) {
        if (mbusVo.getConsumerId() != null && !mbusVo.getMbusPosition().contains("1")) {
            mbusVo.setCompanyId(mbusVo.getConsumerId().substring(0, 5));
        }else if(mbusVo.getConsumerId() != null && mbusVo.getMbusPosition().contains("1")){
            mbusVo.setCompanyId(stationService.getStationById(mbusVo.getConsumerId()).getCompanyId());
        }

        int num = mbusService.modifyBatch(mbusVo);
        return num >= mbusVo.getMbusIds().size();
    }

    @ApiOperation("修改一条通讯设备信息")
    @PutMapping("/modifyMbus")
    @RequiresPermissions("20003")
    public Boolean modifyMbus(@RequestBody Mbus mbus) {
        if (mbus.getConsumerId() != null && !mbus.getMbusPosition().contains("1")) {
            mbus.setCompanyId(mbus.getConsumerId().substring(0, 5));
        }else if(mbus.getConsumerId() != null && mbus.getMbusPosition().contains("1")){
            mbus.setCompanyId(stationService.getStationById(mbus.getConsumerId()).getCompanyId());
        }
        return mbusService.modifyMbus(mbus) == 1;
    }
    @ApiOperation("根据excel表格批量获取通讯设备信息")
    @PostMapping(value="/importExcel")
    public ImportResult excelImport(@RequestParam("file") MultipartFile file, ServletRequest request) {
        ImportResult importResult = mbusService.importExcel(file,request);
        return importResult;
    }

    @ApiOperation("批量导出通信设备信息")
    @PostMapping("/exportExcel")
    public Root exportExcel(ServletRequest request,@RequestBody MbusSelectInfo mbusSelectInfo) {
        Root root = new Root();
        root.setCode(0);
        root.setMsg("导出成功");
        root.setData(mbusService.exportExcel(request,mbusSelectInfo));
        return root;
    }

    @ApiOperation("集中器设定任务")
    @PostMapping("/setTask")
    public int modifyMbusTasks(@RequestBody MeterTimingDefineVo meterTimingDefineVo){
        return meterTimingDefineService.addOrDelTimingDefineBatch(meterTimingDefineVo);
    }

    @ApiOperation("指令测试")
    @PostMapping("addMbusControls")
    public List<MbusInfo> addMbusControls(@RequestBody MbusControls mbusControls){
        return mbusService.getMbusControls(mbusControls.getMbusCodes(),mbusControls.getConsumerId(),
                mbusControls.getMbusControl(),mbusControls.getMbusControlParam());
    }

    @ApiOperation("根据集中器id查询任务")
    @GetMapping("/getAllTaskByMbusCode")
    public Object getTasksByMbusCode(@RequestParam("mbusCode") String mbusCode){
        List<MeterTimingDefine> meterTimingDefineList = meterTimingDefineService.getTimingDefineByMbusCodes(mbusCode);
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        if(meterTimingDefineList != null && meterTimingDefineList.size() > 0) {
            if (meterTimingDefineList.size() == 1) {
                if (meterTimingDefineList.get(0).getUpCommMode().equals("up_comm_mode_1")) {
                    jsonObject.put("upCommMode", meterTimingDefineList.get(0).getUpCommMode());
                    jsonObject.put("intervals", meterTimingDefineList.get(0).getIntervals());
                } else if (meterTimingDefineList.get(0).getUpCommMode().equals("up_comm_mode_0")) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject.put("upCommMode", meterTimingDefineList.get(0).getUpCommMode());
                    jsonObject1.put("timing", meterTimingDefineList.get(0).getTiming());
                    jsonObject1.put("orderType", meterTimingDefineList.get(0).getOrderType());
                    array.add(jsonObject1);
                    jsonObject.put("timingTasks", array);
                }
            } else {
                for (int i = 0; i < meterTimingDefineList.size(); i++) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("timing", meterTimingDefineList.get(i).getTiming());
                    jsonObject1.put("orderType", meterTimingDefineList.get(i).getOrderType());
                    array.add(jsonObject1);
                }
                jsonObject.put("upCommMode", meterTimingDefineList.get(0).getUpCommMode());
                jsonObject.put("timingTasks", array);
            }
        }else{
            Mbus mbus = mbusMapper.selectByMbusCode(mbusCode);
            if(mbus.getEquipmentNo().equals("mbus_type_401")){
                for(int i=0;i<4;i++){
                    JSONObject jsonObject1 = new JSONObject();
                    String time = "08:00:00";
                    String hour = time.substring(0,2);
                    jsonObject1.put("timing",(Integer.parseInt(hour)+2)+""+time.substring(2));
                    jsonObject1.put("orderType","task_meter_0");
                    array.add(jsonObject1);
                }
                jsonObject.put("upCommMode", meterTimingDefineList.get(0).getUpCommMode());
                jsonObject.put("timingTasks", array);
            }else{
//            	for(int i=0;i<1;i++){
//                JSONObject jsonObject1 = new JSONObject();
//                String time = "08:00:00";
//                String hour = time.substring(0,2);
//                jsonObject1.put("timing",(Integer.parseInt(hour)+2)+""+time.substring(2));
//                jsonObject1.put("orderType","task_meter_0");
//                array.add(jsonObject1);
//	            }
//	            jsonObject.put("upCommMode", meterTimingDefineList.get(0).getUpCommMode());
//	            jsonObject.put("timingTasks", array);
	            }
        }
        return jsonObject;
    }
/*
    @ApiOperation("根据公司获取全部通讯设备信息")
    @GetMapping("/getMbusByCompany")
    public List<MbusInfo> getMbusByCompany(@RequestParam String companyId){
        return mbusService.getAllMbusByCompany(companyId);
    }*/
/*@PostMapping("excelImport")
public void excelImport(@RequestParam("file") MultipartFile file) {
    ImportParams importParams = new ImportParams();
    // 数据处理
    IExcelDataHandler<Mbus> handler = new MbusExcelHandler();
    handler.setNeedHandlerFields(new String[] { "mbus_id" });// 注意这里对应的是excel的列名。也就是对象上指定的列名。
    importParams.setDataHanlder(handler);

    // 需要验证
    importParams.setNeedVerfiy(true);

    try {
        ExcelImportResult<Mbus> result = ExcelImportUtil.importExcelMore(file.getInputStream(), Mbus.class,importParams);
        List<Mbus> successList = result.getList();
        List<Mbus> failList = result.getFailList();
        result.getFailWorkbook();

        log.info("是否存在验证未通过的数据:" + result.isVerfiyFail());
        log.info("验证通过的数量:" + successList.size());
        log.info("验证未通过的数量:" + failList.size());

        for (Mbus user : successList) {
            log.info("成功列表信息:ID="+user.getMbusId()+user.getMbusCode()+"-");
        }
        for (Mbus user : failList) {
            log.info("失败列表信息:" + user.getMbusId()+"-"+user.getMbusCode());
        }
    } catch (IOException e) {
        log.error(e.getMessage(), e);
    } catch (Exception e) {
        log.error(e.getMessage(), e);
    }
}*/
}
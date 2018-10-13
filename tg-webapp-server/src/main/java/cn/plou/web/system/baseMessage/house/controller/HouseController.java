package cn.plou.web.system.baseMessage.house.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.constraints.NotEmpty;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.plou.web.charge.chargeconfig.entity.PriceDefine;
import cn.plou.web.charge.chargeconfig.service.PriceDefineService;
import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.config.common.Cond;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.idgenerater.service.IDGenerater;
import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.common.utils.RedisUtil;
import cn.plou.web.common.utils.SerializeUtil;
import cn.plou.web.system.baseMessage.house.entity.House;
import cn.plou.web.system.baseMessage.house.service.HouseService;
import cn.plou.web.system.baseMessage.house.vo.HouseInfo;
import cn.plou.web.system.baseMessage.house.vo.HouseSelectInfo;
import cn.plou.web.system.baseMessage.house.vo.HouseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import redis.clients.jedis.Jedis;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@EnableSwagger2
@RequestMapping("${systemPath}/house")
@Api(description = "住户信息")
public class HouseController  {

    @Autowired
    HouseService houseService;
    @Autowired
    IDGenerater idGenerater;
    @Autowired
    PriceDefineService priceDefineService;
    
    @ApiOperation(value = "根据id获取住户信息")
    @GetMapping("/getHouseById")
    @RequiresPermissions("10904")
    public Root getHouseById(@RequestParam String consumerId){
        Root root = new Root();
        root.setData(houseService.getHouseById(consumerId));
        return root;
    }


    /**
     * @param consumerIds
     * @return 批量获取用户信息
     */
    @PostMapping("/getHouseByIds")
    @RequiresPermissions("10904")
    public Root getHouseByIds(@RequestBody List<String> consumerIds){
        Root root = new Root();
        root.setData(houseService.getHouseByIds(consumerIds));
        return root;
    }
    @ApiOperation(value = "增加一条住户信息")
    @PostMapping("/addHouse")
    @RequiresPermissions("10901")
    public Boolean addHouse(@RequestBody HouseInfo houseInfo){
        return houseService.addHouse(houseInfo)==1;
    }

    @ApiOperation(value = "根据查询条件获取全部住户信息")
    @PostMapping ("/getAllHouse")
    @RequiresPermissions("10904")
    public Root getAllHouse(@RequestBody HouseSelectInfo houseSelectInfo){
        Root root = new Root();
//        PageHelper.startPage(houseSelectInfo.getPage(),houseSelectInfo.getPageSize());
        com.github.pagehelper.PageInfo<HouseInfo> pageInfo = houseService.getAllHouse(houseSelectInfo.getCompanyId(),houseSelectInfo.getStationId(),houseSelectInfo.getCommuityId(),
                houseSelectInfo.getBuildingNo(),houseSelectInfo.getUnitId(),houseSelectInfo.getSearchCondition(),houseSelectInfo.getOrder(),houseSelectInfo.getSortby()
                ,houseSelectInfo.getPage(),houseSelectInfo.getPageSize());
//        com.github.pagehelper.PageInfo<HouseInfo> pageInfo=new com.github.pagehelper.PageInfo<>(houseList);
        root.setData(pageInfo.getList());
        root.setCond(Cond.getCond(pageInfo.getPageNum(),pageInfo.getPageSize(),(int)pageInfo.getTotal(),houseSelectInfo.getOrder(),houseSelectInfo.getSortby()));
        return root;
    }

    @ApiOperation(value = "批量修改住户信息")
    @PutMapping("/modifyHouseBatch")
    @RequiresPermissions("10903")
    public Boolean modifyHouseBatch(@RequestBody HouseVo houseVo){
        return houseService.modifyHouseBatch(houseVo)==houseVo.getConsumerIds().size();
    }

    @ApiOperation(value = "批量删除住户信息（真）")
    @DeleteMapping("/deleteHouseBatchByIds")
    @RequiresPermissions("10902")
    public Boolean deleteHouseBatchByIds(@RequestBody List<String> consumerIds){
        return houseService.deleteHouseBatchByIds(consumerIds)==consumerIds.size();
    }

    @ApiOperation(value = "批量删除住户信息（假）")
    @PutMapping("/setDelHouseBatch")
    @RequiresPermissions("10902")
    public Boolean setDelHouseBatch(@RequestBody List<String> consumerIds){
        return houseService.setDelHouseBatch(consumerIds)==consumerIds.size();
    }

    @ApiOperation(value = "修改住户信息")
    @PutMapping("/modifyHouse")
    @RequiresPermissions("10903")
    public Boolean modifyHouse(@RequestBody House house){
        return houseService.modifyHouse(house)==1;
    }

    @ApiOperation(value = "根据单元获取住户信息")
    @GetMapping("/getHouseByUnitId")
    // @RequiresPermissions("10904")
    public Root getHouseByUnitId(@RequestParam String unitId){
        Root root = new Root();
        root.setData(houseService.getHouseByUnitId(unitId));
        return root;
    }

    @ApiOperation(value = "根据id获取上层结构")
    @GetMapping("/getStructureById")
    //@RequiresPermissions("10904")
    public Root getStructureById(@RequestParam String id){
        Root root = new Root();
        root.setData(houseService.getStructureById(id));
        return root;
    }

    @ApiOperation(value = "批量导入")
    @RequestMapping(value="/importExcel",method=RequestMethod.POST)
    public ImportResult importExcel(@RequestParam("file") MultipartFile file, ServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        ImportResult importResult = new ImportResult();
        try {
            importResult = houseService.importExcel(file,request);
        } catch (Exception e) {
        	if(e instanceof BusinessException){
        		throw e;
        	}else{
            modelAndView.addObject("msg", e.getMessage());
            return importResult;
        	}
        }
        if(importResult.getFailList().size()==0){
        	modelAndView.addObject("msg", "数据导入成功");
        }
        return importResult;
    }

    @RequestMapping(value="/exportExcel",method = RequestMethod.POST)
    public Root exportExcel(@RequestBody HouseSelectInfo houseSelectInfo,ServletRequest request) {
        Root root = new Root();
        root.setData(houseService.exportExcel(houseSelectInfo,request));
        return root;
    }

    @ApiOperation(value = "获取供热单价下拉框")
    @GetMapping("/getPriceDownInfo")
    public Root getPriceDownInfo(@RequestParam String companyId){
        Root root = new Root();
        root.setData(houseService.getAllByYearAndCompany(companyId));
        return root;
    }

//    @RequestMapping(value="/test",method=RequestMethod.GET)
//    public void testImport() {
//        /**
//         * 注意:
//         * 如果通过@Excel的fixedIndex指定实体类属性与实际导入的EXCEL列的对应关系(fixedIndex从0开始),name可以随便写;
//         * 如果不指定Excel的fixedIndex,而是通过@Excel的name进行映射实体类属性与实际导入的EXCEL列标题的对应关系,
//         * 那么@Excel的name要与实际导入的EXCEL列标题相同;
//         *
//         * 如果是导入多个sheet页,且各个sheet页列标题存在不同,不要使用fixedIndex指定对应关系,直接使用name进行映射;
//         *
//         * 实体类与导入的Excel列标题没有全部一一对应也没关系(列标题可以多余实体类属性,实体类属性也可以多余列标题),
//         * 只有对应上的,才会进行映射;
//         */
//        ImportParams importParams = new ImportParams();
//        //导入文件不需要保存
//        importParams.setNeedSave(false);
//        //对于每个sheet页,从第0+1行开始,将表格内容映射成实体类
//        importParams.setStartRows(0);
//        importParams.setTitleRows(0);
//        //从哪个sheet页开始读,sheet页下标从0开始.默认就是0
//        importParams.setStartSheetIndex(0);
//        //读取几个sheet页,默认就是1
//        importParams.setSheetNum(1);
//        //对于每个sheet页,每次读取9+1行;启用标签校验功能后,即使校验不通过,也算读取了1行;默认是0,代表读取全部
//        importParams.setReadRows(9);
//        /**
//         * 对于每个sheet页,启用标签校验功能;
//         * 例如在实体类属性上使用标签@NotNull(message = "XX不能为空"),校验不通过的行,会在失败记录工作簿中记录
//         * 失败原因"XX不能为空"
//         */
//        importParams.setNeedVerfiy(true);
//        /**
//         * 注意:使用ExcelImportUtil导入时,至少要有一条记录与实体类对应,否则会抛异常java.util.NoSuchElementException;
//         * 当设置了读取多个sheet页的时候,例如设置读取2页sheet,从第10个记录开始读取,第1个sheet有10个记录行,
//         * 第2个sheet也有9个记录行,
//         * 导入时会因为第2个sheet页无法满足"至少一条记录与实体类对应",而导致importExcel方法直接
//         * 抛出java.util.NoSuchElementException异常;
//         *
//         * importExcel方法可以很好地支持一次调用读取多个sheet;
//         * importExcelMore不支持一次调用读取多个sheet,否则
//         * 抛org.apache.xmlbeans.impl.values.XmlValueDisconnectedException异常,
//         * 可以通过设置StartSheetIndex的值,每次进行读取一个sheet页
//         */
//
////        List<AddPriceExcelEntity> list= ExcelImportUtil.importExcel(new File("C:\\Users\\Admin\\Desktop\\test.xlsx"), AddPriceExcelEntity.class, importParams);
////
////        for (AddPriceExcelEntity addPriceExcelEntity : list) {
////            System.out.println(addPriceExcelEntity);
////        }
//
//        ExcelImportResult excelImportResult = ExcelImportUtil.importExcelMore(new File("C:\\Users\\lenovo\\Documents\\WeChat Files\\wxid_4npdgn3kraqt22\\Files\\导入模板\\用户信息模板.xlsx"), HouseInfo.class, importParams);
//        try (FileOutputStream successStream = new FileOutputStream("C:\\Users\\lenovo\\Desktop\\test_success.xlsx");
//             FileOutputStream failStream = new FileOutputStream("C:\\Users\\lenovo\\Desktop\\test_fail.xlsx")) {
//
//            /**
//             * 注意:成功失败列表只与当前实际读取过的行记录有关.
//             * 获取导入失败的行. 如果设置了只读前3行,且前3行中只有第1行能满足校验,那么成功列表只记录第2第3行对应的实体类;
//             * 获取导入成功的行. 如果设置了只读前3行,且前3行中只有第1行能满足校验,那么成功列表只记录第1行对应的实体类
//             */
//            List<HouseInfo> failList = excelImportResult.getFailList();
//            List<HouseInfo> successList = excelImportResult.getList();
//            for (HouseInfo addPriceExcelEntity : successList) {
//                System.out.println(addPriceExcelEntity);
//            }
//            /**
//             * 注意:
//             * 获取导入失败的工作簿,除了会把已经读取过的校验失败的行记录在工作簿中,还会把剩下的所有未读的行也记录在工作簿中.
//             * 不同的是,已经读取过的,如果设置了校验标签,且设置了校验失败后的提示,读取过的失败记录行后面会追加记录一列失败原因.
//             *
//             *获取导入成功的工作簿,除了会把已经读取过的校验成功的行记录在工作簿中,还会把剩下的所有未读的行也记录在工作簿中.
//             */
//            excelImportResult.getFailWorkbook().write(failStream);
//            excelImportResult.getWorkbook().write(successStream);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    
    @RequestMapping("/getPriceDefineCombo")
    public Object getPriceDefineCombo(
			@NotEmpty(message = "公司Id不能为空") @RequestParam(value = "companyId", defaultValue = "") String companyId) throws Exception {
      List<PriceDefine> priceDefineList = priceDefineService.findBycompanyId(companyId);
      Collections.sort(priceDefineList, new Comparator<PriceDefine>() {
        public int compare(PriceDefine o1, PriceDefine o2) {
          		return o2.getCreateDate().compareTo(o1.getCreateDate());
              }
        });
      return priceDefineList;
      }
}

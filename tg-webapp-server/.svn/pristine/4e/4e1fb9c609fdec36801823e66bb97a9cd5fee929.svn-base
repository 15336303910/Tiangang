package cn.plou.web.system.baseMessage.build.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.importDataBatch.ErrorInfo;
import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.common.utils.CamelCaseUtil;
import cn.plou.web.common.utils.ExcelTools;
import cn.plou.web.common.utils.RedisUtil;
import cn.plou.web.common.utils.SerializeUtil;
import cn.plou.web.common.utils.Tools;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.baseMessage.build.dao.BuildMapper;
import cn.plou.web.system.baseMessage.build.entity.Build;
import cn.plou.web.system.baseMessage.build.service.BuildService;
import cn.plou.web.system.baseMessage.build.vo.BuildInfo;
import cn.plou.web.system.baseMessage.build.vo.BuildSelectInfo;
import cn.plou.web.system.baseMessage.build.vo.BuildVo;
import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import cn.plou.web.system.baseMessage.commuity.service.impl.CommuityServiceImpl;
import cn.plou.web.system.baseMessage.commuity.vo.CommuityInfo;
import cn.plou.web.system.baseMessage.company.entity.Company;
import cn.plou.web.system.baseMessage.company.service.CompanyService;
import cn.plou.web.system.baseMessage.company.service.impl.CompanyServiceImpl;
import cn.plou.web.system.baseMessage.house.entity.Subord;
import cn.plou.web.system.baseMessage.house.service.HouseService;
import cn.plou.web.common.idgenerater.service.IDGenerater;
import cn.plou.web.system.baseMessage.unit.dao.UnitMapper;
import cn.plou.web.system.baseMessage.unit.entity.Unit;
import cn.plou.web.system.baseMessage.unit.service.UnitService;
import cn.plou.web.system.baseMessage.unit.vo.UnitInfo;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.impl.TypeMstServiceImpl;
import cn.plou.web.system.meterMessage.mbus.service.MbusService;
import cn.plou.web.system.meterMessage.mbus.service.impl.MbusServiceImpl;
import cn.plou.web.system.meterMessage.mbus.vo.MbusInfo;
import cn.plou.web.system.meterMessage.meter.service.MeterService;
import cn.plou.web.system.meterMessage.meter.service.impl.MeterServiceImpl;
import cn.plou.web.system.meterMessage.meter.vo.MeterInfo;
import cn.plou.web.system.permission.role.service.DataRoleService;
import cn.plou.web.system.permission.userPageHistory.entity.UserPageHistory;
import cn.plou.web.system.permission.userPageHistory.service.impl.UserPageHistoryServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.plou.web.common.importDataBatch.CleanOnTime.cleanTempFile;
import static cn.plou.web.common.utils.ExcelTools.getCellValue;

@Component
public class BuildServiceImpl implements BuildService {
    @Autowired
    BuildMapper buildMapper;
    @Autowired
    CompanyServiceImpl companyServiceImpl;
    
    @Autowired
    UnitMapper unitMapper;
    @Autowired
    private MbusServiceImpl mbusServiceImpl;
    @Autowired
    private MeterServiceImpl meterServiceImpl;
    @Autowired
    private TypeMstServiceImpl typeMstServiceImpl;
    @Autowired
    private CommuityServiceImpl commuityServiceImpl;
    @Autowired
    private UserPageHistoryServiceImpl userPageHistoryServiceImpl;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private IDGenerater idGenerater;
    @Autowired
    private HouseService houseService;
    @Autowired
    private DataRoleService dataRoleService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private BuildService buildService;
    @Autowired
    private MbusService mbusService;
    @Autowired
    private MeterService meterService;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addBuild(BuildInfo buildInfo) {
        String id = idGenerater.generateBuildId(buildInfo.getCommuityId(),1, true).get(0);    
        buildInfo.setBuildingNo(id);
        //buildInfo.setRowno(UUID.randomUUID().toString().replace("-", ""));
        buildInfo.setRowno(id);
        buildInfo.setCompanyId(buildInfo.getCommuityId().substring(0, 5));
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("build");
        userPageHistory.setAct("addBuild");
        buildInfo.setCreateUser(UserUtils.getUserId());
        buildInfo.setCreateDate(new Date());
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return buildMapper.insertSelective(buildInfo);
    }

    @Override
    public Build getBuildById(String buildingNo) {
        return buildMapper.selectByPrimaryKey(buildingNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyBuildBatch(BuildVo buildVo) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("build");
        userPageHistory.setAct("modifyBuildBatch");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        buildVo.setUpdateUser(UserUtils.getUserId());
        buildVo.setUpdateDate(new Date());
        if(buildVo.getBatchCheckbox()){
        	if(buildVo.getBatchModifyType().equals("station")){
        		List<String> comuitys = commuityServiceImpl.getCommuityIdsList(null, buildVo.getBatchModifyId(), null);
        		buildVo.setCommunitys(comuitys);
        		return buildMapper.updateBuildBatchAllStation(buildVo);
        	}else{
        		return buildMapper.updateBuildBatchAll(buildVo);
        	}
        }else{
        	return buildMapper.updateBuildBatch(buildVo);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyBuild(Build build) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("build");
        userPageHistory.setAct("modifyBuild");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        build.setUpdateUser(UserUtils.getUserId());
        build.setUpdateDate(new Date());
        Build buildOrg = buildMapper.selectByPrimaryKey(build.getBuildingNo());
        int num = buildMapper.updateByPrimaryKeySelective(build);
        if(!buildOrg.getBuildingName().equals(build.getBuildingName())){
        	unitService.modifyUnitName(build.getBuildingNo(), buildOrg.getBuildingName(), build.getBuildingName());
        	mbusService.modifyMbusAddressbyConsumer(build.getBuildingNo(),build.getAddress(), buildOrg.getAddress());
        	meterService.modifyMeterAddressbyConsumer(build.getBuildingNo(),build.getAddress(), buildOrg.getAddress());
        }
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBuildBatchByIds(List<String> buildingNos) {
        for (String buildingNo : buildingNos) {
            List<Unit> unitList = unitMapper.selectUnitTree(buildingNo);
            if (unitList.size() > 0) {
                throw new BusinessException("建筑物下有关联项，不能删除");
            }
            List<MbusInfo> mbusInfoList = mbusServiceImpl.getAllMbus(null, null, null, null, null, buildingNo, null, null, null, null, null).getMbusInfoList();
            if (mbusInfoList.size() > 0) {
                throw new BusinessException("建筑物下有关联项，不能删除");
            }
            List<MeterInfo> meterInfoList = meterServiceImpl.getAllMeter(null, null, null, null, null, null, null, buildingNo, null, null, null).getMeterInfoList();
            if (meterInfoList.size() > 0) {
                throw new BusinessException("建筑物下有关联项，不能删除");
            }
        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("build");
        userPageHistory.setAct("deleteBuildBatchByIds");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return buildMapper.deleteBuildBatchByIds(buildingNos);
    }

    @Override
    public List<Build> getBuildTree(String commuityId) {
        return buildMapper.selectBuildByCommuityId(commuityId);
    }

    @Override
    public PageInfo<BuildInfo> getAllBuild(String companyId, String stationId, String commuityId, BuildVo buildVo, String order, String sortby, Integer page, Integer pageSize) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("build");
        userPageHistory.setAct("getAllBuild");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);

        List<BuildInfo> buildInfoList = new ArrayList<BuildInfo>();
        sortby = CamelCaseUtil.toUnderscoreCase(sortby);
        if (sortby != null) {
            if (sortby.equals("energy_saving_name")) {
                sortby = "energy_saving";
            }
            if (sortby.equals("preserve_heat_name")) {
                sortby = "preserve_heat";
            }
            if (sortby.equals("pipe_length_name")) {
                sortby = "pipe_length";
            }
            if (sortby.equals("heating_form_name")) {
                sortby = "heating_form";
            }
            if (sortby.equals("has_build_meter_mame")) {
                sortby = "has_build_meter";
            }
            if (sortby.equals("has_balance_valve_name")) {
                sortby = "has_balance_valve";
            }
            if (sortby.equals("control_type_name")) {
                sortby = "control_type";
            }
            if (sortby.equals("attributes_name")) {
                sortby = "attributes";
            }
            if (sortby.equals("rest_time_name")) {
                sortby = "rest_time";
            }
        }
        List<String> companyIds = new ArrayList<String>();
        List<String> stationIds = new ArrayList<String>();
        List<String> commuityIds = new ArrayList<String>();
        
        Boolean flag = dataRoleService.getDataRoleItems(companyIds, stationIds, commuityIds
            ,UserUtils.getUserId(),companyId, stationId,commuityId);
					if (!flag) {
						return  new PageInfo<>();
					}
				if (companyIds.size() == 0 && stationIds.size() == 0 && commuityIds.size() == 0) {
					return new PageInfo<>();
				} else {
		        Integer start = null;
		        if (page != null) {
		            start = (page - 1) * pageSize;
		        }
					  buildInfoList = buildMapper.selectAllBuild(companyIds, stationIds, commuityIds, buildVo, order, sortby, start, pageSize);
					  if(buildInfoList.size() == 0){
					  	return new PageInfo<>();
					  }
            PageInfo<BuildInfo> pageInfo = new PageInfo<>(buildInfoList);
            int count = buildMapper.selectAllBuildCount(companyIds, stationIds, commuityIds, buildVo);
            pageInfo.setTotal(count);
            List<TypeMst> typeMstList = typeMstServiceImpl.getAllTypeMstByRedis();
            for (BuildInfo buildInfo : pageInfo.getList()) {
                buildInfo.setCommuityId(buildInfo.getBuildingNo().substring(0, 10));
                buildInfo.setEnergySavingName(typeMstServiceImpl.getTypeNameById(typeMstList, buildInfo.getEnergySaving()));
                buildInfo.setPreserveHeatName(typeMstServiceImpl.getTypeNameById(typeMstList, buildInfo.getPreserveHeat()));
                if(buildInfo.getPipeLength() != null){
                	buildInfo.setPipeLengthName(buildInfo.getPipeLength().toString());
                }
                buildInfo.setHeatingFormName(typeMstServiceImpl.getTypeNameById(typeMstList, buildInfo.getHeatingForm()));
                buildInfo.setHasBuildMeterName(typeMstServiceImpl.getTypeNameById(typeMstList, buildInfo.getHasBuildMeter()));
                buildInfo.setHasBalanceValveName(typeMstServiceImpl.getTypeNameById(typeMstList, buildInfo.getHasBalanceValve()));
                buildInfo.setControlTypeName(typeMstServiceImpl.getTypeNameById(typeMstList, buildInfo.getControlType()));
                buildInfo.setAttributesName(typeMstServiceImpl.getTypeNameById(typeMstList, buildInfo.getAttributes()));
                buildInfo.setRestTimeName(typeMstServiceImpl.getTypeNameById(typeMstList, buildInfo.getRestTime()));
            }
            return pageInfo;
        }
    }

    @Override
    public Build getBuildByName(String commuityName, String buildingName, String companyId) {
        String commuityId = commuityServiceImpl.getCommuityByName(commuityName, companyId).getCommuityId();
        return buildMapper.selectBuildByName(commuityId, buildingName);
    }

    public List<String> getBuildNosByCommuityId(String commuityId) {
        List<Build> buildList = getBuildTree(commuityId);
        List<String> buildingNos = new ArrayList<String>();
        for (Build build : buildList) {
            buildingNos.add(build.getBuildingNo());
        }
        return buildingNos;
    }

    @Override
    public ImportResult importExcel(MultipartFile multipartFile, ServletRequest request) {

        ImportResult importResult = new ImportResult();

        Map<String ,List<BuildInfo>> mapbuildInfoList = new HashMap<>();
        int successCount = 0;
        List<ErrorInfo> errorInfos = new ArrayList<ErrorInfo>();
       
        ExcelTools excel = new ExcelTools();
        Sheet sheet = excel.getFile(multipartFile, "建筑物信息");
        //一共有多少行
        int rows = excel.getRows();
        CellStyle style = excel.getStyle();
      	Workbook workbook = excel.getWorkbook();     	
        int totalData = 0;        
        Subord sub = new Subord();
        
        for (int i = 1; i <= rows + 1; i++) {
            //读取左上端单元格
            Row row = sheet.getRow(i);
            ErrorInfo errorInfo = new ErrorInfo();
            if (row != null) {
            	if (excel.isCellVolid(row,36, errorInfos,style)) {
                	totalData++;
                    BuildInfo buildInfo = new BuildInfo();
                    
                    String companyName = getCellValue(row.getCell(0));
                    String commuityName = getCellValue(row.getCell(1));
                    Boolean flag = houseService.getSubord(sub, companyName, commuityName, null, null, null, null, null);
                    if (!flag) {
                        sub.orderErrorInfo(errorInfos, i, row, style);
                        continue;
                    }
                    if (!sub.getCurrentInfo().equals("commuity")) {
                      sub.setErrorInfo("commuity");
                      sub.orderErrorInfo(errorInfos, i, row, style);
                      continue;
                    }
                    buildInfo.setCompanyId(sub.getCompany().getCompanyId());
                    buildInfo.setCommuityId(sub.getCommuity().getCommuityId());
  
                    buildInfo.setBuildingName(getCellValue(row.getCell(2)));
                    buildInfo.setUnitNum(getCellValue(row.getCell(3)));
                    buildInfo.setFloorNum(getCellValue(row.getCell(4)));
                    if (getCellValue(row.getCell(5)).length() != 0) {
                        try {
                            buildInfo.setFloorHeight(new BigDecimal(getCellValue(row.getCell(5))));
                        } catch (Exception e) {
                        	ExcelTools.addErrorInfo(errorInfos, i ,5,"字符串不能转换成数字", false, row, style);
                        }
                    }
                    if (getCellValue(row.getCell(6)).length() != 0) {
                        try {
                            buildInfo.setBuildingArea(new BigDecimal(getCellValue(row.getCell(6))));
                        } catch (Exception e) {
                        	ExcelTools.addErrorInfo(errorInfos, i ,6,"字符串不能转换成数字", false, row, style);
                        }
                    }
                    buildInfo.setEnergySaving((getCellValue(row.getCell(7))));
                    buildInfo.setPreserveHeat(getCellValue(row.getCell(8)));
                    buildInfo.setBeginPipeId(getCellValue(row.getCell(9)));
                    buildInfo.setPipeId(getCellValue(row.getCell(10)));
                    if (getCellValue(row.getCell(11)).length() != 0) {
                        try {
                            buildInfo.setMainDiameter(getCellValue(row.getCell(11)));
                        } catch (Exception e) {
                        	ExcelTools.addErrorInfo(errorInfos, i ,11,"字符串不能转换成数字", false, row, style);
                        }
                    }
                    buildInfo.setPipePosition(getCellValue(row.getCell(12)));
                    if(!getCellValue(row.getCell(13)).isEmpty()){
                    		buildInfo.setPipeLength(new BigDecimal(getCellValue(row.getCell(13))));
                    }
                    if (getCellValue(row.getCell(14)).length() != 0) {
                        try {
                            buildInfo.setHeatingIndex(new BigDecimal(getCellValue(row.getCell(14))));
                        } catch (Exception e) {
                        	ExcelTools.addErrorInfo(errorInfos, i ,14,"字符串不能转换成数字", false, row, style);
                        }
                    }
                    buildInfo.setHeatingForm((getCellValue(row.getCell(15))));
                    buildInfo.setBuildYear(getCellValue(row.getCell(16)));
                    buildInfo.setBuilder((getCellValue(row.getCell(17))));
                    if (getCellValue(row.getCell(18)).length() != 0) {
                        try {
                            buildInfo.setNetInTime(new SimpleDateFormat("yyyy-MM-dd").parse(getCellValue(row.getCell(18))));
                        } catch (ParseException e) {
                        	ExcelTools.addErrorInfo(errorInfos, i ,18,"日期格式错误", false, row, style);
                        }
                    }
                    buildInfo.setNetInContract(getCellValue(row.getCell(19)));
                    buildInfo.setHasBuildMeter(getCellValue(row.getCell(20)));
                    buildInfo.setHasBalanceValve(getCellValue(row.getCell(21)));
                    buildInfo.setChargePerson(getCellValue(row.getCell(22)));
                    buildInfo.setAttributes(getCellValue(row.getCell(23)));
                    buildInfo.setBuildingRight(getCellValue(row.getCell(24)));
                    buildInfo.setUsingRight(getCellValue(row.getCell(25)));
                    if (getCellValue(row.getCell(26)).length() != 0) {
                        try {
                            buildInfo.setSystemNum(Integer.parseInt(getCellValue(row.getCell(26))));
                        } catch (Exception e) {
                        	ExcelTools.addErrorInfo(errorInfos, i ,26,"字符串不能转换成整数", false, row, style);
                        }
                    }
                    buildInfo.setRestTime(getCellValue(row.getCell(27)));
                    buildInfo.setLongitude(getCellValue(row.getCell(28)));
                    buildInfo.setLatitude(getCellValue(row.getCell(29)));
                    if(getCellValue(row.getCell(30)).isEmpty()){
                    	String addr = getCellValue(row.getCell(1)) + Tools.nkey + buildInfo.getBuildingName();
                    	buildInfo.setAddress(addr);
                    }else{
                    	buildInfo.setAddress(getCellValue(row.getCell(30)));
                    }
                    buildInfo.setNotes(getCellValue(row.getCell(31)));
                    buildInfo.setMemo1(getCellValue(row.getCell(32)));
                    buildInfo.setMemo2(getCellValue(row.getCell(33)));
                    buildInfo.setControlType(getCellValue(row.getCell(34)));
                    if (getCellValue(row.getCell(35)).length() != 0) {
                        try {
                            buildInfo.setIndex(Integer.parseInt(getCellValue(row.getCell(35))));
                        } catch (Exception e) {
                        	ExcelTools.addErrorInfo(errorInfos, i ,35,"字符串不能转换成整数", false, row, style);
                        }
                    }
                    if (getCellValue(row.getCell(36)).length() != 0) {
                        try {
                            buildInfo.setIsvalid(Integer.parseInt(getCellValue(row.getCell(36))));
                        } catch (Exception e) {
                        	ExcelTools.addErrorInfo(errorInfos, i ,36,"字符串不能转换成整数", false, row, style);
                        }
                    }
                    String key = sub.getCommuity().getCommuityId();
                    Tools.addMap(key, mapbuildInfoList, buildInfo);        
                    if (errorInfo.getPosition() == null) {
                        successCount += 1;
                    }
                }
            }
        }
        if(errorInfos.size()>0) {
            String path = null;
            String path1 = null;
            try {
                path = request.getServletContext().getRealPath("") + "/errorFile/";
//            path = "C:/errorFile/";
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                path1 = "build-" + UserUtils.getUserId() + "-" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xlsx";
                path += path1;
                new File(path).createNewFile();
                FileOutputStream out = new FileOutputStream(path);
                workbook.write(out);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            cleanTempFile(path, 24);
            importResult.setErrorFilePath("/errorFile/" + path1);
        }
        importResult.setTotalCount(totalData);
        importResult.setFailList(errorInfos);
        importResult.setFailCount(totalData - successCount);
        importResult.setId(UserUtils.getUserId() + UUID.randomUUID().toString().replace("-", "") + "build");
        
        if(importResult.getFailList().size()==0){
        	for(String key: mapbuildInfoList.keySet()){
        		addBatch(key, mapbuildInfoList.get(key));
        	}
        }
        return importResult;
    }

    public int addBatch(String commuityId, List<BuildInfo> buildInfoList) {
        int count = 0;
        Boolean b = false;
        List<Build> buildList = getBuildTree(commuityId);
        for (BuildInfo buildInfo : buildInfoList) {
            for (Build build : buildList) {
                if (buildInfo.getBuildingName().equals(build.getBuildingName())) {
                    b = true;
                    break;
                }
            }
            if (b) {
                modifyBuild(buildInfo);
                count++;
            } else {
                addBuild(buildInfo);
                count++;
            }
        }
        return count;
    }

    @Override
    public String exportExcel(BuildSelectInfo buildSelectInfo,ServletRequest request) {
        String path=(UUID.randomUUID().toString().replace("-",""))+"build.xls";
        String filePath=request.getServletContext().getRealPath("/")+path;//文件路径
        HSSFWorkbook workbook = new HSSFWorkbook();//创建Excel文件(Workbook)
        HSSFSheet sheet = workbook.createSheet("建筑物信息");//创建工作表(Sheet)
        HSSFRow row = sheet.createRow(0);// 创建行,从0开始
        HSSFCell cell = row.createCell(0);// 创建行的单元格,也是从0开始
        /*cell.setCellValue("公司");// 设置单元格内容
        row.createCell(1).setCellValue("小区");// 设置单元格内容,重载
        row.createCell(2).setCellValue("楼号");
        row.createCell(3).setCellValue("单元数量");
        row.createCell(4).setCellValue("楼层数量");
        row.createCell(5).setCellValue("层高");
        row.createCell(6).setCellValue("建筑面积");
        row.createCell(7).setCellValue("节能建筑");
        row.createCell(8).setCellValue("保温情况");
        row.createCell(9).setCellValue("管线类别ID");
        row.createCell(10).setCellValue("管线号ID");
        row.createCell(11).setCellValue("总管管径");
        row.createCell(12).setCellValue("管网位置");
        row.createCell(13).setCellValue("管线长度");
        row.createCell(14).setCellValue("设计热指标");
        row.createCell(15).setCellValue("楼内系统形式");
        row.createCell(16).setCellValue("建成时间");
        row.createCell(17).setCellValue("建筑商");
        row.createCell(18).setCellValue("入网时间");
        row.createCell(19).setCellValue("入网合同号");
        row.createCell(20).setCellValue("栋表安装");
        row.createCell(21).setCellValue("平衡阀安装");
        row.createCell(22).setCellValue("负责人名");
        row.createCell(23).setCellValue("使用属性");
        row.createCell(24).setCellValue("房权单位");
        row.createCell(25).setCellValue("使用单位");
        row.createCell(26).setCellValue("系统数");
        row.createCell(27).setCellValue("供暖作息时间");
        row.createCell(28).setCellValue("经度");
        row.createCell(29).setCellValue("纬度");
        row.createCell(30).setCellValue("地址全称");
        row.createCell(31).setCellValue("备注");
        row.createCell(32).setCellValue("保留1");
        row.createCell(33).setCellValue("保留2");
        row.createCell(34).setCellValue("控制方式");
        row.createCell(35).setCellValue("显示排序");
        row.createCell(36).setCellValue("有效标志");*/
        cell.setCellValue("建筑房屋");
        row.createCell(1).setCellValue("公司名称");// 设置单元格内容,重载
        row.createCell(2).setCellValue("建筑属性");
        row.createCell(3).setCellValue("房权单位");
        row.createCell(4).setCellValue("使用单位");
        row.createCell(5).setCellValue("单元数量");
        row.createCell(6).setCellValue("楼层数量");
        row.createCell(7).setCellValue("层高");
        row.createCell(8).setCellValue("建筑面积");
        row.createCell(9).setCellValue("节能建筑");
        row.createCell(10).setCellValue("保温情况");
        row.createCell(11).setCellValue("管线号");
        row.createCell(12).setCellValue("总管管径");
        row.createCell(13).setCellValue("管网位置");
        row.createCell(14).setCellValue("管线长度");
        row.createCell(15).setCellValue("设计热指标");
        row.createCell(16).setCellValue("采暖形式");
        row.createCell(17).setCellValue("系统数");
        row.createCell(18).setCellValue("控制方式");
        row.createCell(19).setCellValue("栋表安装");
        row.createCell(20).setCellValue("平衡阀安装");
        row.createCell(21).setCellValue("建成时间");
        row.createCell(22).setCellValue("建筑商");
        row.createCell(23).setCellValue("入网时间");
        row.createCell(24).setCellValue("入网合同号");
        row.createCell(25).setCellValue("负责人");
        row.createCell(26).setCellValue("供暖作息时间");
        row.createCell(27).setCellValue("经度");
        row.createCell(28).setCellValue("纬度");
        row.createCell(29).setCellValue("地址全称");
        row.createCell(30).setCellValue("备注");
        List<BuildInfo> buildInfoList=new ArrayList<>();
        buildInfoList=getAllBuild(buildSelectInfo.getCompanyId(),buildSelectInfo.getStationId(),buildSelectInfo.getCommuityId(),
                buildSelectInfo.getSearchCondition(),buildSelectInfo.getOrder(),buildSelectInfo.getSortby(),null,null).getList();
        for(int i=1;i<buildInfoList.size();i++){
            row = sheet.createRow(i);// 创建行,从0开始
            cell = row.createCell(0);// 创建行的单元格,也是从0开始
            cell.setCellValue(buildInfoList.get(i-1).getBuildingName()==null?"":buildInfoList.get(i-1).getBuildingName());// 设置单元格内容
            row.createCell(1).setCellValue(buildInfoList.get(i-1).getCompanyName()==null?"":buildInfoList.get(i-1).getCompanyName());// 设置单元格内容,重载
            row.createCell(2).setCellValue(buildInfoList.get(i-1).getAttributesName()==null?"":buildInfoList.get(i-1).getAttributesName());
            row.createCell(3).setCellValue(buildInfoList.get(i-1).getBuildingRight()==null?"":buildInfoList.get(i-1).getBuildingRight());
            row.createCell(4).setCellValue(buildInfoList.get(i-1).getUsingRight()==null?"":buildInfoList.get(i-1).getUsingRight());
            if(buildInfoList.get(i-1).getUnitNum()==null){
                row.createCell(5).setCellValue("");
            }else {
                row.createCell(5).setCellValue(String.valueOf(buildInfoList.get(i - 1).getUnitNum()));
            }
            if(buildInfoList.get(i-1).getFloorNum()==null){
                row.createCell(6).setCellValue("");
            }else {
                row.createCell(6).setCellValue(String.valueOf(buildInfoList.get(i - 1).getFloorNum()));
            }
            if(buildInfoList.get(i-1).getFloorHeight()==null){
                row.createCell(7).setCellValue("");
            }else {
                row.createCell(7).setCellValue(String.valueOf(buildInfoList.get(i - 1).getFloorHeight()));
            }
            if(buildInfoList.get(i - 1).getBuildingArea()==null){
                row.createCell(8).setCellValue("");
            }else {
                row.createCell(8).setCellValue(String.valueOf(buildInfoList.get(i - 1).getBuildingArea()));
            }
            row.createCell(9).setCellValue(buildInfoList.get(i-1).getEnergySavingName()==null?"":buildInfoList.get(i-1).getEnergySavingName());
            row.createCell(10).setCellValue(buildInfoList.get(i-1).getPreserveHeatName()==null?"":buildInfoList.get(i-1).getPreserveHeatName());
            row.createCell(11).setCellValue(buildInfoList.get(i-1).getPipeId()==null?"":buildInfoList.get(i-1).getPipeId());
            if(buildInfoList.get(i-1).getMainDiameter()==null){
                row.createCell(12).setCellValue("");
            }else {
                row.createCell(12).setCellValue(buildInfoList.get(i - 1).getMainDiameter());
            }
            row.createCell(13).setCellValue(buildInfoList.get(i-1).getPipePosition()==null?"":buildInfoList.get(i-1).getPipePosition());
            row.createCell(14).setCellValue(buildInfoList.get(i-1).getPipeLengthName()==null?"":buildInfoList.get(i-1).getPipeLengthName());
            if(buildInfoList.get(i-1).getHeatingIndex()==null){
                row.createCell(15).setCellValue("");
            }else {
                row.createCell(15).setCellValue(String.valueOf(buildInfoList.get(i - 1).getHeatingIndex()));
            }
            row.createCell(16).setCellValue(buildInfoList.get(i-1).getHeatingFormName()==null?"":buildInfoList.get(i-1).getHeatingFormName());
            if(buildInfoList.get(i-1).getSystemNum()==null){
                row.createCell(17).setCellValue("");
            }else {
                row.createCell(17).setCellValue(buildInfoList.get(i - 1).getSystemNum());
            }
            row.createCell(18).setCellValue(buildInfoList.get(i-1).getControlTypeName()==null?"":buildInfoList.get(i-1).getControlTypeName());
            row.createCell(19).setCellValue(buildInfoList.get(i-1).getHasBuildMeterName()==null?"":buildInfoList.get(i-1).getHasBuildMeterName());
            row.createCell(20).setCellValue(buildInfoList.get(i-1).getHasBalanceValveName()==null?"":buildInfoList.get(i-1).getHasBalanceValveName());
            row.createCell(21).setCellValue(buildInfoList.get(i-1).getBuildYear()==null?"":buildInfoList.get(i-1).getBuildYear());
            row.createCell(22).setCellValue(buildInfoList.get(i-1).getBuilder()==null?"":buildInfoList.get(i-1).getBuilder());
            if(buildInfoList.get(i-1).getNetInTime()==null){
                row.createCell(23).setCellValue("");
            }else {
                row.createCell(23).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(buildInfoList.get(i - 1).getNetInTime()));
            }
            row.createCell(24).setCellValue(buildInfoList.get(i-1).getNetInContract()==null?"":buildInfoList.get(i-1).getNetInContract());
            row.createCell(25).setCellValue(buildInfoList.get(i-1).getChargePerson()==null?"":buildInfoList.get(i-1).getChargePerson());
            row.createCell(26).setCellValue(buildInfoList.get(i-1).getRestTimeName()==null?"":buildInfoList.get(i-1).getRestTimeName());
            row.createCell(27).setCellValue(buildInfoList.get(i-1).getLongitude()==null?"":buildInfoList.get(i-1).getLongitude());
            row.createCell(28).setCellValue(buildInfoList.get(i-1).getLatitude()==null?"":buildInfoList.get(i-1).getLatitude());
            row.createCell(29).setCellValue(buildInfoList.get(i-1).getAddress()==null?"":buildInfoList.get(i-1).getAddress());
            row.createCell(30).setCellValue(buildInfoList.get(i-1).getNotes()==null?"":buildInfoList.get(i-1).getNotes());
           /* cell = row.createCell(0);// 创建行的单元格,也是从0开始
            cell.setCellValue(buildInfoList.get(i-1).getCompanyName()==null?"":buildInfoList.get(i-1).getCompanyName());// 设置单元格内容
            row.createCell(1).setCellValue("");// 设置单元格内容,重载
            row.createCell(2).setCellValue(buildInfoList.get(i-1).getBuildingName()==null?"":buildInfoList.get(i-1).getBuildingName());
            row.createCell(3).setCellValue(buildInfoList.get(i-1).getUnitNum()==null?"":buildInfoList.get(i-1).getUnitNum());
            row.createCell(4).setCellValue(buildInfoList.get(i-1).getFloorNum()==null?"":buildInfoList.get(i-1).getFloorNum());
            if(buildInfoList.get(i-1).getFloorHeight()==null){
                row.createCell(5).setCellValue("");
            }else {
                row.createCell(5).setCellValue(String.valueOf(buildInfoList.get(i - 1).getFloorHeight()));
            }
            if(buildInfoList.get(i - 1).getBuildingArea()==null){
                row.createCell(6).setCellValue("");
            }else {
                row.createCell(6).setCellValue(String.valueOf(buildInfoList.get(i - 1).getBuildingArea()));
            }
            row.createCell(7).setCellValue(buildInfoList.get(i-1).getEnergySavingName()==null?"":buildInfoList.get(i-1).getEnergySavingName());
            row.createCell(8).setCellValue(buildInfoList.get(i-1).getPreserveHeatName()==null?"":buildInfoList.get(i-1).getPreserveHeatName());
            row.createCell(9).setCellValue("");
            row.createCell(10).setCellValue("");
            if(buildInfoList.get(i-1).getMainDiameter()==null){
                row.createCell(11).setCellValue("");
            }else {
                row.createCell(11).setCellValue(buildInfoList.get(i - 1).getMainDiameter());
            }
            row.createCell(12).setCellValue(buildInfoList.get(i-1).getPipePosition()==null?"":buildInfoList.get(i-1).getPipePosition());
            row.createCell(13).setCellValue(buildInfoList.get(i-1).getPipeLengthName()==null?"":buildInfoList.get(i-1).getPipeLengthName());
            if(buildInfoList.get(i-1).getHeatingIndex()==null){
                row.createCell(14).setCellValue("");
            }else {
                row.createCell(14).setCellValue(String.valueOf(buildInfoList.get(i - 1).getHeatingIndex()));
            }
            row.createCell(15).setCellValue(buildInfoList.get(i-1).getHeatingFormName()==null?"":buildInfoList.get(i-1).getHeatingFormName());
            row.createCell(16).setCellValue(buildInfoList.get(i-1).getBuildYear()==null?"":buildInfoList.get(i-1).getBuildYear());
            row.createCell(17).setCellValue(buildInfoList.get(i-1).getBuilder()==null?"":buildInfoList.get(i-1).getBuilder());
            if(buildInfoList.get(i-1).getNetInTime()==null){
                row.createCell(18).setCellValue("");
            }else {
                row.createCell(18).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(buildInfoList.get(i - 1).getNetInTime()));
            }
            row.createCell(19).setCellValue(buildInfoList.get(i-1).getNetInContract()==null?"":buildInfoList.get(i-1).getNetInContract());
            row.createCell(20).setCellValue(buildInfoList.get(i-1).getHasBuildMeterName()==null?"":buildInfoList.get(i-1).getHasBuildMeterName());
            row.createCell(21).setCellValue(buildInfoList.get(i-1).getHasBalanceValveName()==null?"":buildInfoList.get(i-1).getHasBalanceValveName());
            row.createCell(22).setCellValue(buildInfoList.get(i-1).getChargePerson()==null?"":buildInfoList.get(i-1).getChargePerson());
            row.createCell(23).setCellValue(buildInfoList.get(i-1).getAttributesName()==null?"":buildInfoList.get(i-1).getAttributesName());
            row.createCell(24).setCellValue(buildInfoList.get(i-1).getBuildingRight()==null?"":buildInfoList.get(i-1).getBuildingRight());
            row.createCell(25).setCellValue(buildInfoList.get(i-1).getUsingRight()==null?"":buildInfoList.get(i-1).getUsingRight());
            if(buildInfoList.get(i-1).getSystemNum()==null){
                row.createCell(26).setCellValue("");
            }else {
                row.createCell(26).setCellValue(buildInfoList.get(i - 1).getSystemNum());
            }
            row.createCell(27).setCellValue(buildInfoList.get(i-1).getRestTimeName()==null?"":buildInfoList.get(i-1).getRestTimeName());
            row.createCell(28).setCellValue(buildInfoList.get(i-1).getLongitude()==null?"":buildInfoList.get(i-1).getLongitude());
            row.createCell(29).setCellValue(buildInfoList.get(i-1).getLatitude()==null?"":buildInfoList.get(i-1).getLatitude());
            row.createCell(30).setCellValue(buildInfoList.get(i-1).getAddress()==null?"":buildInfoList.get(i-1).getAddress());
            row.createCell(31).setCellValue(buildInfoList.get(i-1).getNotes()==null?"":buildInfoList.get(i-1).getNotes());
            row.createCell(32).setCellValue(buildInfoList.get(i-1).getMemo1()==null?"":buildInfoList.get(i-1).getMemo1());
            row.createCell(33).setCellValue(buildInfoList.get(i-1).getMemo2()==null?"":buildInfoList.get(i-1).getMemo2());
            row.createCell(34).setCellValue(buildInfoList.get(i-1).getControlTypeName()==null?"":buildInfoList.get(i-1).getControlTypeName());
            if(buildInfoList.get(i-1).getIndex()==null){
                row.createCell(35).setCellValue("");
            }else {
                row.createCell(35).setCellValue(buildInfoList.get(i - 1).getIndex());
            }
            if(buildInfoList.get(i - 1).getIsvalid()==null){
                row.createCell(36).setCellValue("");
            }else {
                row.createCell(36).setCellValue(buildInfoList.get(i - 1).getIsvalid());
            }*/
        }
        try{
            FileOutputStream out = new FileOutputStream(filePath);
            workbook.write(out);//保存Excel文件
            out.close();//关闭文件流
            System.out.println("OK!");
        }catch (IOException e){
            e.printStackTrace();
        }

        return path;
    }

    public List<String> getBuildNosByCommuityIds(List<String> commuityIds) {
        return buildMapper.getBuildNosByCommuityIds(commuityIds);
    }
    
		public Map<String, Build> getBuildByIdAndToMap(Commuity commuity, Map<String, Map<String, Build>> mapBuild) {
			Map<String, Build> map = mapBuild.get(commuity.getCommuityId());
			if(map == null){
				map = new HashMap<String, Build>();
				List<Build> list = getBuildTree(commuity.getCommuityId());
				for(Build info:list){
					map.put(info.getBuildingName(), info);
				}
				mapBuild.put(commuity.getCommuityId(), map);
			}
			return map;
		}

		@Override
		public Integer getMaxBuildId(String commuityId) {
				Integer index = 0;
				String id = buildMapper.getMaxBuildId(commuityId);
				if (id != null && !id.isEmpty()) {
					index = Integer.valueOf(id.substring(10, 13));
				}
				return index;
		}
		
		@Override
		public	void modifyBuildName(String address, String addressOrg, String commuityId){
			List<Build> buildOrgs = buildMapper.selectBuildByCommuityId(commuityId);
			buildMapper.modifyBuildName(address, Tools.nkey, commuityId);
			for(Build build:buildOrgs){
				unitService.modifyUnitName(build.getBuildingNo(), null, address +  Tools.nkey + build.getBuildingName());
			}
		}
}
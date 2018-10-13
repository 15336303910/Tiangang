package cn.plou.web.system.baseMessage.commuity.service.impl;

import static cn.plou.web.common.importDataBatch.CleanOnTime.cleanTempFile;
import static cn.plou.web.common.utils.ExcelTools.getCellValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;

import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.idgenerater.service.IDGenerater;
import cn.plou.web.common.importDataBatch.ErrorInfo;
import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.common.utils.ExcelTools;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.baseMessage.build.entity.Build;
import cn.plou.web.system.baseMessage.build.service.BuildService;
import cn.plou.web.system.baseMessage.commuity.dao.CommuityMapper;
import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import cn.plou.web.system.baseMessage.commuity.entity.CommuityKey;
import cn.plou.web.system.baseMessage.commuity.service.CommuityService;
import cn.plou.web.system.baseMessage.commuity.vo.CommuityInfo;
import cn.plou.web.system.baseMessage.commuity.vo.CommuityListInfo;
import cn.plou.web.system.baseMessage.commuity.vo.CommuitySelectInfo;
import cn.plou.web.system.baseMessage.commuity.vo.CommuityUnique;
import cn.plou.web.system.baseMessage.commuity.vo.CommuityVo;
import cn.plou.web.system.baseMessage.company.entity.Company;
import cn.plou.web.system.baseMessage.company.service.impl.CompanyServiceImpl;
import cn.plou.web.system.baseMessage.house.entity.Subord;
import cn.plou.web.system.baseMessage.house.service.HouseService;
import cn.plou.web.system.baseMessage.station.entity.Station;
import cn.plou.web.system.baseMessage.station.service.StationService;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.TypeMstService;
import cn.plou.web.system.meterMessage.mbus.entity.Mbus;
import cn.plou.web.system.meterMessage.mbus.service.MbusService;
import cn.plou.web.system.meterMessage.meter.entity.Meter;
import cn.plou.web.system.meterMessage.meter.service.MeterService;
import cn.plou.web.system.permission.rlRoleData.entity.RlRoleData;
import cn.plou.web.system.permission.rlRoleData.service.RlRoleDataService;
import cn.plou.web.system.permission.rlUserRole.service.RlUserRoleService;
import cn.plou.web.system.permission.role.service.DataRoleService;
import cn.plou.web.system.permission.userPageHistory.entity.UserPageHistory;
import cn.plou.web.system.permission.userPageHistory.service.impl.UserPageHistoryServiceImpl;

@Component
public class CommuityServiceImpl implements CommuityService {
    @Autowired
    private CommuityMapper commuityMapper;
    @Autowired
    private CompanyServiceImpl companyService;
    @Autowired
    private RlUserRoleService userRoleService;
    @Autowired
    private UserPageHistoryServiceImpl userPageHistoryServiceImpl;
    @Autowired
    private MeterService meterService;
    @Autowired
    private MbusService mbusService;
    @Autowired
    private BuildService buildService;
    @Autowired
    private TypeMstService typeMstService;
    @Autowired
    private StationService stationService;
    @Autowired
    private RlRoleDataService rlRoleDataService;
    @Autowired
    private IDGenerater idGenerater;
    @Autowired
    private RlUserRoleService rlUserRoleService;
    @Autowired
    private HouseService houseService;
    @Autowired
    private DataRoleService dataRoleService;
    @Override
    public List<Commuity> getTreeList(String stationId) {
        List<String> commuityIds = new ArrayList<>();
        if(rlRoleDataService.getRlRoleDataByRoleId(rlUserRoleService.getRoleByUserId(UserUtils.getUserId())).size()>0){
            if(rlRoleDataService.getRlRoleDataByRoleId(rlUserRoleService.getRoleByUserId(UserUtils.getUserId())).get(0).getColumnType().equals("F")){
                commuityIds.addAll(companyService.getAllCommuityIdsByRole(rlUserRoleService.getRoleByUserId(UserUtils.getUserId())));
            }
        }
        return commuityMapper.selectTreeList(stationId,commuityIds);
    }

    public CommuityListInfo getAllCommuity(Integer page, Integer pageSize, String order, String sortby, String companyId, String stationId, String commuityId, CommuityVo commuityVo) {
        Integer start = null;
        if (page != null) {
            start = (page - 1) * pageSize;
        }
        CommuityListInfo commuityListInfo = new CommuityListInfo();
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("commuity");
        userPageHistory.setAct("getAllCommuity");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        //PageHelper.startPage(page,pageSize);
        
        List<String> companyIds = new ArrayList<String>();
        List<String> stationIds = new ArrayList<String>();
        List<String> commuityIds = new ArrayList<String>();
        List<CommuityInfo> commuityInfos = new ArrayList<CommuityInfo>();
        
        Boolean flag = dataRoleService.getDataRoleItems(companyIds, stationIds, commuityIds
            ,UserUtils.getUserId(),companyId, stationId,commuityId);
					if (!flag) {
						commuityListInfo.setCount(0);
						return  commuityListInfo;
					}
				if (companyIds.size() == 0 && stationIds.size() == 0 && commuityIds.size() == 0) {
					commuityListInfo.setCount(0);
				} else {
					commuityInfos.addAll(commuityMapper.selectAllCommuity(start, pageSize, order, sortby, companyIds, stationId, commuityId,
							null, commuityVo));
					if (commuityInfos.size() > 0) {
						commuityListInfo.setCount(commuityMapper.selectCommuityCount(companyIds, stationId, commuityId, null, commuityVo));
					} else {
						commuityListInfo.setCount(0);
					}
				}

        //PageInfo<CommuityInfo> pageInfo=new PageInfo<>(commuityInfos);
        //查找数据字典项字段名字
        if (commuityInfos.size() != 0) {
            List<TypeMst> typeMstList = typeMstService.getAllTypeMstByRedis();
            for (CommuityInfo commuityInfo : commuityInfos) {
                commuityInfo.setCbzqName(typeMstService.getTypeNameById(typeMstList, commuityInfo.getCbzq()));
                commuityInfo.setHasBuildMeterName(typeMstService.getTypeNameById(typeMstList, commuityInfo.getHasBuildMeter()));
                commuityInfo.setXqztName(typeMstService.getTypeNameById(typeMstList, commuityInfo.getXqzt()));
            }
        }
        commuityListInfo.setCommuityInfoList(commuityInfos);
        return commuityListInfo;
    }

    @Override
    public Commuity getCommuityById(CommuityKey commuityKey) {
        return commuityMapper.selectByPrimaryKey(commuityKey);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatchByIds(List<String> commuityIds) {
        for (String commuityId : commuityIds) {
            List<Meter> meters = meterService.getMeterDownInforByConsumer(commuityId);
            List<Mbus> mbuses = mbusService.geDownInfoByConsumerId(commuityId);
            List<Build> builds = buildService.getBuildTree(commuityId);
            if (meters.size() != 0 || mbuses.size() != 0 || builds.size() != 0) {
                throw new BusinessException(getCommuityById(commuityId).getCommuityName() + "地址下有关联项，不能删除");
            }
            if (rlRoleDataService.getRlRoleData().contains(commuityId)) {
                rlRoleDataService.deleteRlRoleDataByColumn(commuityId);
            }
        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("commuity");
        userPageHistory.setAct("deleteBatchByIds");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return commuityMapper.deleteBatchByIds(commuityIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addCommuity(Commuity commuity) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("commuity");
        userPageHistory.setAct("addCommuity");
        
        Station station = stationService.getStationById(commuity.getStationId());
        String companyId = station.getCompanyId();
        commuity.setCompanyId(companyId);
        commuity.setCommuityId(idGenerater.generateCommunityId(companyId,1).get(0));
        commuity.setRowno(commuity.getCommuityId());
        commuity.setCreateUser(UserUtils.getUserId());
        commuity.setCreateDate(new Date());
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        int num =  commuityMapper.insertSelective(commuity);

        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyCommuityBatch(CommuityVo commuityVo) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("commuity");
        userPageHistory.setAct("modifyCommuityBatch");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        commuityVo.setUpdateUser(UserUtils.getUserId());
        commuityVo.setUpdateDate(new Date());
        int num = commuityMapper.updateBatch(commuityVo);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyCommuity(Commuity commuity) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("commuity");
        userPageHistory.setAct("modifyCommuity");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        commuity.setUpdateUser(UserUtils.getUserId());
        commuity.setUpdateDate(new Date());
        Commuity commuityOrg = commuityMapper.selectById(commuity.getCommuityId());
        int num = commuityMapper.updateByPrimaryKeySelective(commuity);
        if(!commuityOrg.getCommuityName().equals(commuity.getCommuityName())){
        	buildService.modifyBuildName(commuity.getCommuityName(), commuityOrg.getCommuityName(), commuity.getCommuityId());
        	mbusService.modifyMbusAddressbyConsumer(commuity.getCommuityId(),commuity.getCommuityName(), commuityOrg.getCommuityName());
        	meterService.modifyMeterAddressbyConsumer(commuity.getCommuityId(),commuity.getCommuityName(), commuityOrg.getCommuityName());
        }
        return num;
    }

    @Override
    public Commuity getCommuityById(String commuityId) {
        return commuityMapper.selectById(commuityId);
    }

    public List<String> getCommuityIdsByCompanyId(String companyId) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("commuity");
        userPageHistory.setAct("getCommuityIdsByCompanyId");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return commuityMapper.selectCommuitysByCompanyId(companyId);
    }
    
    public List<CommuityInfo> getCommuityByCompanyId(String companyId) {
    	 List<String> ids = new ArrayList<String>();
    	 ids.add(companyId);
      return commuityMapper.selectAllCommuity(0,10000,null,null,ids,null,null,null,null);
  }

    public List<String> getCommuityIdsList(String companyId, String stationId, String commuityId) {
        List<CommuityInfo> allCommuityByCompany = getAllCommuity(null, null, null, null, companyId, stationId, commuityId, null).getCommuityInfoList();
        List<String> commuityIds = new ArrayList<>();
        String roleId = userRoleService.getRoleByUserId(UserUtils.getUserId());
        if (roleId.equals("1")) {
            if (companyId == null && stationId == null && commuityId == null) {
                commuityIds.addAll(commuityMapper.selectAllCommuityIds(null));
            } else {
                if (allCommuityByCompany.size() > 0) {
                    for (CommuityInfo commuityInfo : allCommuityByCompany) {
                        commuityIds.add(commuityInfo.getCommuityId());
                    }
                }
            }
        } else {
            if (companyId != null || stationId != null) {
            	List<String> lists =  companyService.getAllCommuityIdsByRole(roleId);
                for (CommuityInfo commuityInfo : allCommuityByCompany) {
                    if (lists.contains(commuityInfo.getCommuityId())) {
                        commuityIds.add(commuityInfo.getCommuityId());
                    }
                }
            } else if (commuityId != null) {
                commuityIds.add(commuityId);
            } else if (stationId == null && companyId == null && commuityId == null) {
                commuityIds.addAll(companyService.getAllCommuityIdsByRole(roleId));
            }
        }
        return commuityIds;
    }

    @SuppressWarnings("resource")
    public ImportResult importExcel(MultipartFile multipartFile, ServletRequest request) {
        ImportResult importResult = new ImportResult();
        int successCount = 0;
    	  List<Commuity> commuities = new ArrayList<>();
        List<ErrorInfo> errorInfos = new ArrayList<ErrorInfo>();
        
    	  ExcelTools excel = new ExcelTools();
        Sheet sheet = excel.getFile(multipartFile, "小区信息");
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
                if(row!=null){
                	if (excel.isCellVolid(row,19, errorInfos,style)) {
                    	totalData++;
                    	Commuity commuity = new Commuity();
                      String companyName = getCellValue(row.getCell(0));
                      String stationName = getCellValue(row.getCell(1));
                      Boolean flag = houseService.getSubord(sub, companyName, null, null, null, null, stationName, null);
                      if (!flag) {
                          sub.orderErrorInfo(errorInfos, i, row, style);
                          continue;
                      }
                      commuity.setCompanyId(sub.getCompany().getCompanyId());
                      if(sub.getStation() == null){
                      	ExcelTools.addErrorInfo(errorInfos, i ,2,"未找到对应的热力站", true, row, style);  
                      	continue;
                      }
                      commuity.setStationId(sub.getStation().getStationId());
                    
                    try {
                        commuity.setCommuityName(getCellValue(row.getCell(2)));
                    } catch (Exception e) {
                    		ExcelTools.addErrorInfo(errorInfos, i ,2,"小区名异常", true, row, style);    
                    }
                    commuity.setProperty(getCellValue(row.getCell(3)));
                    if (getCellValue(row.getCell(4)).length() > 13) {
                    	ExcelTools.addErrorInfo(errorInfos, i ,4,"电话号码格式不正确", false, row, style);
                    }
                    commuity.setChargePerson(getCellValue(row.getCell(5)));
                    commuity.setCommuityArea(getCellValue(row.getCell(6)));
                    if (getCellValue(row.getCell(7)).length() != 0) {
                        try {
                            commuity.setBuildNum(Integer.parseInt(getCellValue(row.getCell(7))));
                        } catch (Exception e) {
                        	ExcelTools.addErrorInfo(errorInfos, i ,7,"字符串不能转换成整数", false, row, style);
                        }
                    }

                    if (getCellValue(row.getCell(8)).length() != 0) {
                        try {
                            commuity.setUnitNum(Integer.parseInt(getCellValue(row.getCell(8))));
                        } catch (Exception e) {
                        	ExcelTools.addErrorInfo(errorInfos, i ,8,"字符串不能转换成整数", false, row, style);
                        }
                    }
      
                    commuity.setAddress(getCellValue(row.getCell(9)));
                    commuity.setBuildYear(getCellValue(row.getCell(10)));
                    commuity.setLongitude(getCellValue(row.getCell(11)));
                    commuity.setLatitude(getCellValue(row.getCell(12)));
                    commuity.setNotes(getCellValue(row.getCell(13)));
                    commuity.setMemo1(getCellValue(row.getCell(14)));
                    commuity.setMemo2(getCellValue(row.getCell(15)));
                    commuity.setHasBuildMeter(getCellValue(row.getCell(16)));
                    if (getCellValue(row.getCell(17)) != null && getCellValue(row.getCell(17)).length() > 0) {
                        commuity.setCbzq(getCellValue(row.getCell(17)));
                    }
                    if (getCellValue(row.getCell(18)) != null && getCellValue(row.getCell(18)).length() > 0) {
                        commuity.setXqzt(getCellValue(row.getCell(18)));
                    }
                    if (getCellValue(row.getCell(19)) != null && getCellValue(row.getCell(19)).length() > 0) {
                        try {
                            commuity.setIndex(Integer.parseInt(getCellValue(row.getCell(19))));
                        } catch (Exception e) {
                        	ExcelTools.addErrorInfo(errorInfos, i ,19,"字符串不能转换成整数", false, row, style);
                        }
                    }
                    commuities.add(commuity);
                    if (errorInfo.getPosition() == null) {
                        successCount += 1;
                    }
                    continue;
                }
         }
    }
    if(errorInfos.size()>0){
        String path = null;
        String path1 = null;
        try {
            path = request.getServletContext().getRealPath("") + "errorFile/";
            //path = "C:/errorFile/";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            path1 = "commuity-" + UserUtils.getUserId() + "-" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xlsx";
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
        cleanTempFile(path,24);
        importResult.setErrorFilePath("/errorFile/" + path1);
    }
        importResult.setTotalCount(totalData);
        importResult.setFailList(errorInfos);
        importResult.setFailCount(totalData - successCount);
        importResult.setId(UserUtils.getUserId() + UUID.randomUUID().toString().replace("-", "") + "commuity");  
        
        //小区导入ID在addBatch中生成，支持小区名重复
        if(importResult.getFailList().size()==0){
        		addBatch(commuities);
        }
        return importResult;
    }

    @Override
    public int addBatch(List<Commuity> commuities) {
        CommuityUnique commuityUnique = new CommuityUnique();
        List<CommuityUnique> commuityUniques = commuityMapper.selectAllCommuityUnique();
        for (Commuity commuity : commuities) {
            commuityUnique.setCommuityName(commuity.getCommuityName());
            commuityUnique.setCompanyId(commuity.getCompanyId());
            if (commuityUniques.contains(commuityUnique)) {
                //synchronized (this){
                    if(commuity.getStationId()!=null){
                        commuity.setCompanyId(stationService.getStationById(commuity.getStationId()).getCompanyId());
                    }
                    modifyCommuity(commuity);
                //}
            } else {
                //synchronized (this){
                    Station station = stationService.getStationById(commuity.getStationId());
                    String companyId = station.getCompanyId();
                    commuity.setCompanyId(companyId);
                    commuity.setCommuityId(idGenerater.generateCommunityId(commuity.getCompanyId(),1).get(0));
                    commuity.setRowno(UUID.randomUUID().toString().replace("-",""));
                    addCommuity(commuity);
                //}
            }
        }
        return commuities.size();
    }

    @Override
    public Commuity getCommuityByName(String commuityName, String companyId) {
        return commuityMapper.selectCommuityByName(commuityName, companyId);
    }

    @Override
    public String exportExcel(ServletRequest request,CommuitySelectInfo selectInfo) {
        String path1 = (UUID.randomUUID().toString().replace("-", "")) + "-commuity.xlsx";
        // 第一步，创建一个webbook，对应一个Excel文件
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = wb.createSheet("Sheet1");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        XSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        XSSFCell cell = row.createCell(0);
        cell.setCellValue("地址信息");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("公司");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("站");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("经度");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("坐落位置");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("楼宇数量");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("建设时间");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("备注");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("纬度");
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellValue("物业公司");
        cell.setCellStyle(style);
        cell = row.createCell(10);
        cell.setCellValue("占地面积");
        cell.setCellStyle(style);
        cell = row.createCell(11);
        cell.setCellValue("联系电话");
        cell.setCellStyle(style);
        cell = row.createCell(12);
        cell.setCellValue("单元数量");
        cell.setCellStyle(style);
        cell = row.createCell(13);
        cell.setCellValue("物业联系人");
        cell.setCellStyle(style);


        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
        List<CommuityInfo> list = getAllCommuity(null, null, null, null, selectInfo.getCompanyId(), selectInfo.getStationId(), selectInfo.getCommuityId(), selectInfo.getSearchCondition()).getCommuityInfoList();
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            CommuityInfo commuityInfo = list.get(i);
            if (commuityInfo.getCommuityName() != null) {
                row.createCell(0).setCellValue(commuityInfo.getCommuityName());
            }
            if (commuityInfo.getCompanyName() != null) {
                row.createCell(1).setCellValue(commuityInfo.getCompanyName());
            }
            if (commuityInfo.getStationName() != null) {
                row.createCell(2).setCellValue(commuityInfo.getStationName());
            }
            if (commuityInfo.getLongitude() != null) {
                row.createCell(3).setCellValue(commuityInfo.getLongitude());
            }
            if (commuityInfo.getAddress() != null) {
                row.createCell(4).setCellValue(commuityInfo.getAddress());
            }
            if (commuityInfo.getBuildNum() != null) {
                row.createCell(5).setCellValue(commuityInfo.getBuildNum());
            }
            if (commuityInfo.getBuildYear() != null) {
                row.createCell(6).setCellValue(commuityInfo.getBuildYear());
            }
            if (commuityInfo.getNotes() != null) {
                row.createCell(7).setCellValue(commuityInfo.getNotes());
            }
            if (commuityInfo.getLatitude() != null) {
                row.createCell(8).setCellValue(commuityInfo.getLatitude());
            }
            if (commuityInfo.getProperty() != null) {
                row.createCell(9).setCellValue(commuityInfo.getProperty());
            }
            if (commuityInfo.getCommuityArea() != null) {
                row.createCell(10).setCellValue(commuityInfo.getCommuityArea());
            }
            if (commuityInfo.getTel() != null) {
                row.createCell(11).setCellValue(commuityInfo.getTel());
            }
            if (commuityInfo.getUnitNum() != null) {
                row.createCell(12).setCellValue(commuityInfo.getUnitNum());
            }
            if (commuityInfo.getChargePerson() != null) {
                row.createCell(13).setCellValue(commuityInfo.getChargePerson());
            }
        }
            // 第四步，创建单元格，并设置值
            /*row.createCell(0).setCellValue(commuityInfo.getChargePerson());
            row.createCell(1).setCellValue(commuityInfo.getCommuityName());
            row.createCell(2).setCellValue(commuityInfo.getCompanyName());
            if (commuityInfo.getProperty() != null) {
                row.createCell(3).setCellValue(commuityInfo.getStationName());
            }
            if (commuityInfo.getTel() != null) {
                row.createCell(4).setCellValue(commuityInfo.getLongitude());
            }

            if (commuityInfo.getChargePerson() != null) {
                row.createCell(5).setCellValue(commuityInfo.getAddress());
            }
            if (commuityInfo.getCommuityArea() != null) {
                row.createCell(6).setCellValue(commuityInfo.getBuildNum());
            }
            if (commuityInfo.getBuildNum() != null) {
                row.createCell(7).setCellValue(commuityInfo.getBuildNum());
            }
            if (commuityInfo.getUnitNum() != null) {
                row.createCell(8).setCellValue(commuityInfo.getUnitNum());
            }
            if (commuityInfo.getAddress() != null) {
                row.createCell(9).setCellValue(commuityInfo.getAddress());
            }
            if (commuityInfo.getBuildYear() != null) {
                row.createCell(10).setCellValue(commuityInfo.getBuildYear());
            }
            if (commuityInfo.getLongitude() != null) {
                row.createCell(11).setCellValue(commuityInfo.getLongitude());
            }
            if (commuityInfo.getLatitude() != null) {
                row.createCell(12).setCellValue(commuityInfo.getLatitude());
            }
            if (commuityInfo.getNotes() != null) {
                row.createCell(13).setCellValue(commuityInfo.getNotes());
            }
            if (commuityInfo.getMemo1() != null) {
                row.createCell(14).setCellValue(commuityInfo.getMemo1());
            }
            if (commuityInfo.getMemo2() != null) {
                row.createCell(15).setCellValue(commuityInfo.getMemo2());
            }
            if (commuityInfo.getHasBuildMeter() != null) {
                row.createCell(16).setCellValue(commuityInfo.getHasBuildMeter());
            }
            if (commuityInfo.getCbzq() != null) {
                row.createCell(17).setCellValue(commuityInfo.getCbzq());
            }
            if (commuityInfo.getXqzt() != null) {
                row.createCell(18).setCellValue(commuityInfo.getXqzt());
            }
            if (commuityInfo.getIndex() != null) {
                row.createCell(19).setCellValue(commuityInfo.getIndex());
            }*/
            //第六步,输出Excel文件
            OutputStream output = null;
            String path = request.getServletContext().getRealPath("/");
            try {
                output = new FileOutputStream(path+path1);
                wb.write(output);
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return path1;
    }

		@Override
		public Commuity selectById(String commuityId) {
			return commuityMapper.selectById(commuityId);
		}

		@Override
		public Commuity selectCommuityByName(String commuityName, String companyId) {
			return commuityMapper.selectCommuityByName(commuityName, companyId);
		}

		public Map<String, Commuity> getCommuityByIdAndToMap(Company company, Map<String, Map<String, Commuity>> mapCommuity) {
			Map<String, Commuity> map = mapCommuity.get(company.getCompanyId());
			if(map == null){
				map = new HashMap<String, Commuity>();
				List<CommuityInfo> list = getCommuityByCompanyId(company.getCompanyId());
				for(CommuityInfo info:list){
					map.put(info.getCommuityName(), info);
				}
				mapCommuity.put(company.getCompanyId(), map);
			}
			return map;
		}

    /**
     * @Description: 根据换热站 id 获取其下所有的小区 id 列表
     * @Param: [stationId]
     * @Return: java.util.List<java.lang.String>
     * @Author: youbc
     * @Date: 2018/9/21 14下午18
     */
    @Override
    public List<String> getCommuityIdsByStationId(String stationId) {
        List<String> ids = new ArrayList<>();
        List<Commuity> list = commuityMapper.selectTreeList(stationId, null);
        if (list != null && list.size() > 0) {
            for (Commuity commuity : list) {
                ids.add(commuity.getCommuityId());
            }
        }
        return ids;
    }

    @Override
		public Integer getMaxCommuityId(String companyId) {
			Integer index = 0;
			String id = commuityMapper.getMaxCommuityId(companyId);
			if (id != null && !id.isEmpty()) {
				index = Integer.valueOf(id.substring(5, 10));
			}
			return index;
		}
		
		@Override
		public List<Commuity> selectCommuityByConsumerIdLike(String consumerId){
				return commuityMapper.selectCommuityByConsumerIdLike(consumerId);
		}
		
		@Override
		public List<Commuity> selectCommuityByStationIds(List<String> stationIds){
			return commuityMapper.selectCommuityByStationIds(stationIds);
		}
}

package cn.plou.web.system.meterMessage.mbusReadmodel.service.impl;

import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.importDataBatch.ErrorInfo;
import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.common.utils.CamelCaseUtil;
import cn.plou.web.common.utils.ExcelTools;
import cn.plou.web.common.utils.RedisUtil;
import cn.plou.web.common.utils.SerializeUtil;
import cn.plou.web.common.utils.Tools;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.baseMessage.build.dao.BuildMapper;
import cn.plou.web.system.baseMessage.build.service.BuildService;
import cn.plou.web.system.baseMessage.commuity.dao.CommuityMapper;
import cn.plou.web.system.baseMessage.commuity.service.CommuityService;
import cn.plou.web.system.baseMessage.company.service.CompanyService;
import cn.plou.web.system.baseMessage.company.service.impl.CompanyServiceImpl;
import cn.plou.web.system.baseMessage.house.dao.HouseMapper;
import cn.plou.web.system.baseMessage.house.entity.Subord;
import cn.plou.web.system.baseMessage.house.service.HouseService;
import cn.plou.web.system.baseMessage.station.dao.StationMapper;
import cn.plou.web.system.baseMessage.unit.dao.UnitMapper;
import cn.plou.web.system.baseMessage.unit.service.UnitService;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.TypeMstService;
import cn.plou.web.system.commonMessage.typeMst.service.impl.TypeMstServiceImpl;
import cn.plou.web.system.meterMessage.mbus.dao.MbusMapper;
import cn.plou.web.system.meterMessage.mbus.entity.Mbus;
import cn.plou.web.system.meterMessage.mbus.service.MbusService;
import cn.plou.web.system.meterMessage.mbus.service.impl.MbusServiceImpl;
import cn.plou.web.system.meterMessage.mbus.vo.MbusInfo;
import cn.plou.web.system.meterMessage.mbusReadmodel.dao.MbusReadmodelMapper;
import cn.plou.web.system.meterMessage.mbusReadmodel.entity.MbusReadmodel;
import cn.plou.web.system.meterMessage.mbusReadmodel.service.MbusReadmodelService;
import cn.plou.web.system.meterMessage.mbusReadmodel.vo.MbusReadmodelInfo;
import cn.plou.web.system.meterMessage.mbusReadmodel.vo.MbusReadmodelSelectInfo;
import cn.plou.web.system.meterMessage.mbusReadmodel.vo.MbusReadmodelVo;
import cn.plou.web.system.permission.rlRoleData.service.RlRoleDataService;
import cn.plou.web.system.permission.rlUserRole.service.RlUserRoleService;
import cn.plou.web.system.permission.role.service.DataRoleService;
import cn.plou.web.system.permission.userDataHistory.entity.UserDataHistory;
import cn.plou.web.system.permission.userDataHistory.service.impl.UserDataHistoryServiceImpl;
import cn.plou.web.system.permission.userPageHistory.entity.UserPageHistory;
import cn.plou.web.system.permission.userPageHistory.service.impl.UserPageHistoryServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.plou.web.common.importDataBatch.CleanOnTime.cleanTempFile;
import static cn.plou.web.common.utils.ExcelTools.getCellValue;

@Component
public class MbusReadmodelServiceImpl implements MbusReadmodelService {

    @Autowired
    MbusReadmodelMapper mbusReadmodelMapper;
    @Autowired
    CompanyService companyService;
    @Autowired
    MbusMapper mbusMapper;
    @Autowired
    MbusServiceImpl mbusServiceImpl;
    @Autowired
    StationMapper stationMapper;
//    @Autowired
//   CommuityMapper commuityMapper;
    @Autowired
    private CommuityService commuityMapper;
    @Autowired
    BuildMapper buildMapper;
    @Autowired
    UnitMapper unitMapper;
    @Autowired
    HouseMapper houseMapper;
    @Autowired
    HouseService houseService;
    @Autowired
    UnitService unitService;
    @Autowired
    BuildService buildService;
    @Autowired
    DataRoleService dataRoleService;
    @Autowired
    RlUserRoleService rlUserRoleService;
    @Autowired
    RlRoleDataService rlRoleDataService;
    @Autowired
    TypeMstService typeMstService;
    @Autowired
    private UserPageHistoryServiceImpl userPageHistoryServiceImpl;
    @Autowired
    private UserDataHistoryServiceImpl userDataHistoryServiceImpl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addMbusReadmodel(MbusReadmodel mbusReadmodel) {
//        List<Integer> ids=new ArrayList<Integer>();
//        List<MbusReadmodelInfo> mbusReadmodelInfoList = mbusReadmodelMapper.selectAllMbusReadmodel(null, null, null, null, null,null,null);
//        if(mbusReadmodelInfoList.size()==0){
//            mbusReadmodel.setMbusReadmodelId("1");
//        }else{
//            for(MbusReadmodelInfo mbusReadmodelInfo:mbusReadmodelInfoList){
//                ids.add(Integer.valueOf(mbusReadmodelInfo.getMbusReadmodelId()));
//            }
//            Collections.sort(ids);
//            Collections.reverse(ids);
//            mbusReadmodel.setMbusReadmodelId(""+(ids.get(0)+1));
//        }
        mbusReadmodel.setMbusReadmodelId(mbusReadmodel.getMbusReadmodelCode());
        mbusReadmodel.setIsvalid(Constant.ISVALID);
        mbusReadmodel.setCompanyId(mbusMapper.selectByMbusCode(mbusReadmodel.getMbusId()).getCompanyId());
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("mbusReadmodel");
        userPageHistory.setAct("addMbusReadmodel");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        mbusReadmodel.setCreateUser(UserUtils.getUserId());
        mbusReadmodel.setCreateDate(new Date());
        return mbusReadmodelMapper.insertSelective(mbusReadmodel);
    }

    @Override
    public MbusReadmodel getMbusReadmodelById(String mbusReadmodelId) {
        return mbusReadmodelMapper.selectByPrimaryKey(mbusReadmodelId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyMbusReadmodelBatch(MbusReadmodelVo mbusReadmodelVo) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("mbusReadmodel");
        userPageHistory.setAct("modifyMbusReadmodelBatch");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        mbusReadmodelVo.setUpdateUser(UserUtils.getUserId());
        mbusReadmodelVo.setUpdateDate(new Date());
        if(mbusReadmodelVo.getBatchCheckbox()){
		      	if(mbusReadmodelVo.getBatchModifyType().equals("station")){
		       		List<String> comuitys = commuityMapper.getCommuityIdsList(null, mbusReadmodelVo.getBatchModifyId(), null);
		       		mbusReadmodelVo.setCommunitys(comuitys);
		       		return mbusReadmodelMapper.updateMbusReadmodelBatchAllStation(mbusReadmodelVo);
		       	}else{
		       		return mbusReadmodelMapper.updateMbusReadmodelBatchAll(mbusReadmodelVo);
		       	}
       }else{
         for (String mbusReadmodelId : mbusReadmodelVo.getMbusReadmodelIds()) {
           UserDataHistory userDataHistory = new UserDataHistory();
           userDataHistory.setConsumerId(mbusMapper.selectByMbusCode(mbusReadmodelMapper.selectByPrimaryKey(mbusReadmodelId).getMbusId()).getConsumerId());
           userDataHistory.setPage("mbusReadmodel");
           userDataHistory.setInfo("修改采集器，采集器号:" + (mbusReadmodelMapper.selectByPrimaryKey(mbusReadmodelId).getMbusReadmodelCode()));
           userDataHistoryServiceImpl.addUserDataHistory(userDataHistory);
       }
       	return mbusReadmodelMapper.updateMbusReadmodelBatch(mbusReadmodelVo);
       }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyMbusReadmodel(MbusReadmodel mbusReadmodel) {
        mbusReadmodel.setCompanyId(mbusMapper.selectByMbusCode(mbusReadmodel.getMbusId()).getCompanyId());
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("mbusReadmodel");
        userPageHistory.setAct("modifyMbusReadmodel");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);

        UserDataHistory userDataHistory = new UserDataHistory();
        userDataHistory.setConsumerId(mbusMapper.selectByMbusCode(mbusReadmodel.getMbusId()).getConsumerId());
        userDataHistory.setPage("mbusReadmodel");
        userDataHistory.setInfo("修改采集器，采集器号:" + (mbusReadmodel.getMbusReadmodelCode()));
        userDataHistoryServiceImpl.addUserDataHistory(userDataHistory);
        mbusReadmodel.setUpdateUser(UserUtils.getUserId());
        mbusReadmodel.setUpdateDate(new Date());
        return mbusReadmodelMapper.updateByPrimaryKeySelective(mbusReadmodel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteMbusReadmodelBatchByIds(List<String> mbusReadmodelIds) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("mbusReadmodel");
        userPageHistory.setAct("deleteMbusReadmodelBatchByIds");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return mbusReadmodelMapper.deleteMbusReadmodelBatchByIds(mbusReadmodelIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int setDelMbusReadmodelBatch(List<String> mbusReadmodelIds) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("mbusReadmodel");
        userPageHistory.setAct("setDelMbusReadmodelBatch");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);

        for (String mbusReadmodelId : mbusReadmodelIds) {
            UserDataHistory userDataHistory = new UserDataHistory();
            userDataHistory.setConsumerId(mbusMapper.selectByMbusCode(mbusReadmodelMapper.selectByPrimaryKey(mbusReadmodelId).getMbusId()).getConsumerId());
            userDataHistory.setPage("mbusReadmodel");
            userDataHistory.setInfo("删除采集器，采集器号:" + (mbusReadmodelMapper.selectByPrimaryKey(mbusReadmodelId).getMbusReadmodelCode()));
            userDataHistoryServiceImpl.addUserDataHistory(userDataHistory);
        }

        return mbusReadmodelMapper.setDelMbusReadmodelBatch(mbusReadmodelIds,UserUtils.getUserId(),new Date());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageInfo<MbusReadmodelInfo> getAllMbusReadmodel(String companyId, String stationId, String commuityId, String buildingNo, String unitId,
                                                           String consumerId, MbusReadmodelVo mbusReadmodelVo, String order, String sortby, Integer page, Integer pageSize) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("mbusReadmodel");
        userPageHistory.setAct("getAllMbusReadmodel");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        //获得权限
        List<String> companyIds = new ArrayList<String>();
        List<String> stationIds = new ArrayList<String>();
        List<String> commuityIds = new ArrayList<String>();
        List<String> mbusIds = new ArrayList<String>();
        Boolean flag = dataRoleService.getDataRoleItems(companyIds, stationIds, commuityIds
            ,UserUtils.getUserId(),companyId, stationId,commuityId);
				if (!flag) {
 				 List<MbusReadmodelInfo> mbusReadmodelInfoList = new ArrayList<>();
         PageInfo<MbusReadmodelInfo> pageInfo = new PageInfo<>(mbusReadmodelInfoList);
         return pageInfo;
				}
      			if (companyIds.size() == 0 && stationIds.size() == 0 && commuityIds.size() == 0 && buildingNo== null && unitId == null && consumerId == null) {
      				 List<MbusReadmodelInfo> mbusReadmodelInfoList = new ArrayList<>();
               PageInfo<MbusReadmodelInfo> pageInfo = new PageInfo<>(mbusReadmodelInfoList);
               return pageInfo;
    				}
            if(companyIds.size() == 0){
                List<MbusInfo> mbusInfoList = mbusServiceImpl
                        .getAllMbus(null, null, companyId, stationId, commuityId, buildingNo, unitId, consumerId, null, null, null)
                        .getMbusInfoList();
                if (mbusInfoList.size() > 0) {
                    for (MbusInfo mbusInfo : mbusInfoList) {
                        mbusIds.add(mbusInfo.getMbusCode());
                    }
                }
            }
//        if(page!=null) {
//            page = (page - 1) * pageSize;
//        }
            sortby = CamelCaseUtil.toUnderscoreCase(sortby);
            if (sortby != null) {
                if (sortby.equals("factory_name")) {
                    sortby = "factory";
                }
                if (sortby.equals("isvirtual_name")) {
                    sortby = "isvirtual";
                }
                if (sortby.equals("channelstate_name")) {
                    sortby = "channelstate";
                }
            }
            if (page != null) {
                PageHelper.startPage(page, pageSize);
            }
            List<MbusReadmodelInfo> mbusReadmodelInfoList = mbusReadmodelMapper.selectAllMbusReadmodel(companyIds, mbusIds, mbusReadmodelVo, order, sortby, page, pageSize);
            PageInfo<MbusReadmodelInfo> pageInfo = new PageInfo<>(mbusReadmodelInfoList);
            List<TypeMst> typeMstList = typeMstService.getAllTypeMstByRedis();
            for (MbusReadmodelInfo mbusReadmodelInfo : pageInfo.getList()) {
                mbusReadmodelInfo.setFactoryName(typeMstService.getTypeNameById(typeMstList, mbusReadmodelInfo.getFactory()));
                mbusReadmodelInfo.setIsvirtualName(typeMstService.getTypeNameById(typeMstList, mbusReadmodelInfo.getIsvirtual()));
                mbusReadmodelInfo.setChannelstateName(typeMstService.getTypeNameById(typeMstList, mbusReadmodelInfo.getChannelstate()));
            }
            return pageInfo;
           
    }

    @Override
    public List<MbusReadmodel> getMbusReadmodelDownInfo(String companyId) {
        List<String> companyIds = companyService.getCompanyIdsByPid(companyId);
        return mbusReadmodelMapper.selectMbusReadmodelByCompanyId(companyIds);
    }

    @Override
    public List<MbusReadmodel> getMbusReadmodelByMbusId(String mbusId) {
        return mbusReadmodelMapper.selectByMbusId(mbusId);
    }

    @Override
    public ImportResult importExcel(MultipartFile multipartFile, ServletRequest request) {
        ImportResult importResult = new ImportResult();

        List<MbusReadmodel> mbusReadmodelList = new ArrayList<>();
        int successCount = 0;
        List<ErrorInfo> errorInfos = new ArrayList<ErrorInfo>();
        
        ExcelTools excel = new ExcelTools();
        Sheet sheet = excel.getFile(multipartFile, "采集器信息");
        //一共有多少行
        int rows = excel.getRows();
        CellStyle style = excel.getStyle();
      	Workbook workbook = excel.getWorkbook();
        int totalData = 0;        
        Subord sub = new Subord();
        List<TypeMst> typeMstList = typeMstService.getAllTypeMst();
        for (int i = 1; i <= rows + 1; i++) {
            //读取左上端单元格
            Row row = sheet.getRow(i);
            ErrorInfo errorInfo = new ErrorInfo();
            if (row != null) {
            	if (excel.isCellVolid(row,18, errorInfos,style)) {
                	totalData++;
                    MbusReadmodel mbusReadmodel = new MbusReadmodel();
                    
                    String companyName = getCellValue(row.getCell(0));
//                    String commuityName = getCellValue(row.getCell(1));
//                    String buidName = getCellValue(row.getCell(2));
//                    String unitName = getCellValue(row.getCell(3));
//                    String houseName = getCellValue(row.getCell(4));
//                    String stationName = getCellValue(row.getCell(5));
                    
                    Boolean flag = houseService.getSubord(sub,companyName,null, null,null,null,null,null);
                    if(!flag){
                   	  sub.orderErrorInfo(errorInfos, i, row, style);
                    	continue;
                    }
                    mbusReadmodel.setMbusId(getCellValue(row.getCell(6)));
                    mbusReadmodel.setCompanyId(sub.getCompany().getCompanyId());
                    
                    if (getCellValue(row.getCell(7)).length() != 0) {
                        	  mbusReadmodel.setMbusReadmodelCode(getCellValue(row.getCell(7)));
                    }else{
                    	ExcelTools.addErrorInfo(errorInfos, i ,7,"采集器号不能为空", false, row, style);
                    }
                    if (getCellValue(row.getCell(8)).length() != 0) {
                      try {
                      	mbusReadmodel.setInstallTime(new SimpleDateFormat("yyyy-MM-dd").parse(getCellValue(row.getCell(8))));
                      } catch (ParseException e) {
                      	ExcelTools.addErrorInfo(errorInfos, i ,8,"日期格式不正确", false, row, style);
                      }
                  }
                    
                    mbusReadmodel.setFactory(ExcelTools.getTypeMst(typeMstList, "factory" ,9 ,row, false));
                    mbusReadmodel.setEquipmentNo(ExcelTools.getTypeMst(typeMstList, "mbus_type" ,10,row, false));
                    mbusReadmodel.setPosition(ExcelTools.getTypeMst(typeMstList, "mbus_position" ,11,row, false));
                    mbusReadmodel.setNotes(getCellValue(row.getCell(12)));
                    mbusReadmodel.setMemo1(getCellValue(row.getCell(13)));
                    mbusReadmodel.setMemo2(getCellValue(row.getCell(14)));
                    mbusReadmodel.setIsvirtual(getCellValue(row.getCell(15)));
                    mbusReadmodel.setChannelstate(getCellValue(row.getCell(16)));
                    if (getCellValue(row.getCell(17)).length() != 0) {
                        try {
                            mbusReadmodel.setIsvalid(Integer.parseInt(getCellValue(row.getCell(17))));
                        } catch (Exception e) {
                        	ExcelTools.addErrorInfo(errorInfos, i ,17,"字符串不能转换成整数", false, row, style);
                        }
                    }
                    try {
                        mbusReadmodelList.add(mbusReadmodel);
//                    addMbusReadmodel(mbusReadmodel);
                        if (errorInfo.getPosition() == null) {
                            successCount += 1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    continue;
                } else {
                    rows -= 1;
                }
            }
        }
        if (errorInfos.size() > 0) {
            String path = null;
            String path1 = null;
            try {
                path = request.getServletContext().getRealPath("") + "/errorFile/";
//            path = "C:/errorFile/";
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                path1 = "mbusReadmodel-" + UserUtils.getUserId() + "-" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xlsx";
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
//        importResult.setSuccessCount(successCount);
        importResult.setFailCount(totalData - successCount);
        //importResult.setMultipartFile(multipartFile);
        importResult.setId(UserUtils.getUserId() + UUID.randomUUID().toString().replace("-", "") + "mbusReadmodel");
        if(importResult.getFailCount() == 0){
        	addBatch(mbusReadmodelList);
        }
        return importResult;
    }

    @Override
    public int addBatch(List<MbusReadmodel> mbusReadmodelList) {
        int count = 0;
        Boolean b = false;
        for (MbusReadmodel mbusReadmodel : mbusReadmodelList) {
            List<MbusReadmodel> mbusReadmodels = getMbusReadmodelDownInfo(mbusReadmodel.getCompanyId());
            for (MbusReadmodel mbusReadmodel1 : mbusReadmodels) {
                if (mbusReadmodel.getMbusReadmodelCode().equals(mbusReadmodel1.getMbusReadmodelCode())) {
                    b = true;
                    break;
                }
            }
            if (b) {
                modifyMbusReadmodel(mbusReadmodel);
                count++;
            } else {
                addMbusReadmodel(mbusReadmodel);
                count++;
            }
        }
        return count;
    }

    @Override
    public String exportExcel(MbusReadmodelSelectInfo mbusReadmodelSelectInfo, ServletRequest request) {
        String path=(UUID.randomUUID().toString().replace("-", "")) + "mbusReadmodel.xls";
        String filePath = request.getServletContext().getRealPath("/") + path;//文件路径
        HSSFWorkbook workbook = new HSSFWorkbook();//创建Excel文件(Workbook)
        HSSFSheet sheet = workbook.createSheet("采集器信息");//创建工作表(Sheet)
        HSSFRow row = sheet.createRow(0);// 创建行,从0开始
        HSSFCell cell = row.createCell(0);// 创建行的单元格,也是从0开始
//        row.createCell(1).setCellValue("站");// 设置单元格内容,重载
//        row.createCell(2).setCellValue("小区");
//        row.createCell(3).setCellValue("楼");
//        row.createCell(4).setCellValue("单元");
//        row.createCell(5).setCellValue("用户");
        /*cell.setCellValue("公司");// 设置单元格内容
        row.createCell(1).setCellValue("用途");
        row.createCell(2).setCellValue("集中器ID");
        row.createCell(3).setCellValue("采集器号");
        row.createCell(4).setCellValue("安装时间");
        row.createCell(5).setCellValue("厂商");
        row.createCell(6).setCellValue("型号");
        row.createCell(7).setCellValue("位置");
        row.createCell(8).setCellValue("备注");
        row.createCell(9).setCellValue("保留1");
        row.createCell(10).setCellValue("保留2");
        row.createCell(11).setCellValue("有无虚拟");
        row.createCell(12).setCellValue("通道状态");
        row.createCell(13).setCellValue("有效标志");*/
        cell.setCellValue("公司");// 设置单元格内容
        row.createCell(1).setCellValue("采集器号");
        row.createCell(2).setCellValue("集中器编号");
        row.createCell(3).setCellValue("安装时间");
        row.createCell(4).setCellValue("厂商");
        row.createCell(5).setCellValue("型号");
        row.createCell(6).setCellValue("位置");
        row.createCell(7).setCellValue("备注");
        row.createCell(8).setCellValue("有无虚拟");
        row.createCell(9).setCellValue("通道状态");
        List<MbusReadmodelInfo> mbusReadmodelInfoList = new ArrayList<>();
        mbusReadmodelInfoList = getAllMbusReadmodel(mbusReadmodelSelectInfo.getCompanyId(), mbusReadmodelSelectInfo.getStationId(), mbusReadmodelSelectInfo.getCommuityId(), mbusReadmodelSelectInfo.getBuildingNo(), mbusReadmodelSelectInfo.getUnitId(),
                mbusReadmodelSelectInfo.getConsumerId(), mbusReadmodelSelectInfo.getSearchCondition(), mbusReadmodelSelectInfo.getOrder(), mbusReadmodelSelectInfo.getSortby(), null, null).getList();
        for (int i = 1; i <= mbusReadmodelInfoList.size(); i++) {
            row = sheet.createRow(i);// 创建行,从0开始
            cell = row.createCell(0);// 创建行的单元格,也是从0开始
            cell.setCellValue(mbusReadmodelInfoList.get(i - 1).getMbusReadmodelCode() == null ? "" : mbusReadmodelInfoList.get(i - 1).getMbusReadmodelCode());// 设置单元格内容
            row.createCell(1).setCellValue(mbusReadmodelInfoList.get(i - 1).getMbusId() == null ? "" : mbusReadmodelInfoList.get(i - 1).getMbusId());
            if (mbusReadmodelInfoList.get(i - 1).getInstallTime() == null) {
                row.createCell(2).setCellValue("");
            } else {
                row.createCell(2).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(mbusReadmodelInfoList.get(i - 1).getInstallTime()));
            }
            row.createCell(3).setCellValue(mbusReadmodelInfoList.get(i - 1).getFactoryName() == null ? "" : mbusReadmodelInfoList.get(i - 1).getFactoryName());
            row.createCell(4).setCellValue(mbusReadmodelInfoList.get(i - 1).getEquipmentNo() == null ? "" : mbusReadmodelInfoList.get(i - 1).getEquipmentNo());
            row.createCell(5).setCellValue(mbusReadmodelInfoList.get(i - 1).getPosition() == null ? "" : mbusReadmodelInfoList.get(i - 1).getPosition());
            row.createCell(6).setCellValue(mbusReadmodelInfoList.get(i - 1).getCompanyName() == null ? "" : mbusReadmodelInfoList.get(i - 1).getCompanyName());
            row.createCell(7).setCellValue(mbusReadmodelInfoList.get(i - 1).getNotes() == null ? "" : mbusReadmodelInfoList.get(i - 1).getNotes());
            row.createCell(8).setCellValue(mbusReadmodelInfoList.get(i - 1).getIsvirtualName() == null ? "" : mbusReadmodelInfoList.get(i - 1).getIsvirtualName());
            row.createCell(9).setCellValue(mbusReadmodelInfoList.get(i - 1).getChannelstateName() == null ? "" : mbusReadmodelInfoList.get(i - 1).getChannelstateName());

            /*cell.setCellValue(mbusReadmodelInfoList.get(i - 1).getCompanyName() == null ? "" : mbusReadmodelInfoList.get(i - 1).getCompanyName());// 设置单元格内容
            row.createCell(1).setCellValue(mbusReadmodelInfoList.get(i - 1).getConsumerName() == null ? "" : mbusReadmodelInfoList.get(i - 1).getConsumerName());// 设置单元格内容,重载
            row.createCell(2).setCellValue(mbusReadmodelInfoList.get(i - 1).getMbusId() == null ? "" : mbusReadmodelInfoList.get(i - 1).getMbusId());
            row.createCell(3).setCellValue(mbusReadmodelInfoList.get(i - 1).getMbusReadmodelCode() == null ? "" : mbusReadmodelInfoList.get(i - 1).getMbusReadmodelCode());
            if (mbusReadmodelInfoList.get(i - 1).getInstallTime() == null) {
                row.createCell(4).setCellValue("");
            } else {
                row.createCell(4).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(mbusReadmodelInfoList.get(i - 1).getInstallTime()));
            }
            row.createCell(5).setCellValue(mbusReadmodelInfoList.get(i - 1).getFactoryName() == null ? "" : mbusReadmodelInfoList.get(i - 1).getFactoryName());
            row.createCell(6).setCellValue(mbusReadmodelInfoList.get(i - 1).getEquipmentNo() == null ? "" : mbusReadmodelInfoList.get(i - 1).getEquipmentNo());
            row.createCell(7).setCellValue(mbusReadmodelInfoList.get(i - 1).getPosition() == null ? "" : mbusReadmodelInfoList.get(i - 1).getPosition());
            row.createCell(8).setCellValue(mbusReadmodelInfoList.get(i - 1).getNotes() == null ? "" : mbusReadmodelInfoList.get(i - 1).getNotes());
            row.createCell(9).setCellValue(mbusReadmodelInfoList.get(i - 1).getMemo1() == null ? "" : mbusReadmodelInfoList.get(i - 1).getMemo1());
            row.createCell(10).setCellValue(mbusReadmodelInfoList.get(i - 1).getMemo2() == null ? "" : mbusReadmodelInfoList.get(i - 1).getMemo2());
            row.createCell(11).setCellValue(mbusReadmodelInfoList.get(i - 1).getIsvirtualName() == null ? "" : mbusReadmodelInfoList.get(i - 1).getIsvirtualName());
            row.createCell(11).setCellValue(mbusReadmodelInfoList.get(i - 1).getChannelstateName() == null ? "" : mbusReadmodelInfoList.get(i - 1).getChannelstateName());*/
            /*if (mbusReadmodelInfoList.get(i - 1).getIsvalid() == null) {
                row.createCell(13).setCellValue("");
            } else {
                row.createCell(13).setCellValue(mbusReadmodelInfoList.get(i - 1).getIsvalid());
            }*/
        }
        try {
            FileOutputStream out = new FileOutputStream(filePath);
            workbook.write(out);//保存Excel文件
            out.close();//关闭文件流
            System.out.println("OK!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

}

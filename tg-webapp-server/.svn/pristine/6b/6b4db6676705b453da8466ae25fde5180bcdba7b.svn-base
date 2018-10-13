package cn.plou.web.system.meterMessage.meter.service.impl;

import static cn.plou.web.common.importDataBatch.CleanOnTime.cleanTempFile;
import static cn.plou.web.common.utils.ExcelTools.getCellValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
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

import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.idgenerater.service.IDGenerater;
import cn.plou.web.common.importDataBatch.ErrorInfo;
import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.common.utils.ExcelTools;
import cn.plou.web.common.utils.Tools;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.baseMessage.build.service.BuildService;
import cn.plou.web.system.baseMessage.commuity.service.CommuityService;
import cn.plou.web.system.baseMessage.company.service.CompanyService;
import cn.plou.web.system.baseMessage.house.entity.Subord;
import cn.plou.web.system.baseMessage.house.service.HouseService;
import cn.plou.web.system.baseMessage.station.service.StationService;
import cn.plou.web.system.baseMessage.unit.service.UnitService;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.TypeMstService;
import cn.plou.web.system.meterMessage.mbus.entity.Mbus;
import cn.plou.web.system.meterMessage.mbus.service.MbusService;
import cn.plou.web.system.meterMessage.meter.dao.MeterMapper;
import cn.plou.web.system.meterMessage.meter.entity.Meter;
import cn.plou.web.system.meterMessage.meter.service.MeterService;
import cn.plou.web.system.meterMessage.meter.vo.MeterInfo;
import cn.plou.web.system.meterMessage.meter.vo.MeterListInfo;
import cn.plou.web.system.meterMessage.meter.vo.MeterSelectInfo;
import cn.plou.web.system.meterMessage.meter.vo.MeterVo;
import cn.plou.web.system.permission.rlRoleData.entity.RlRoleData;
import cn.plou.web.system.permission.rlRoleData.service.RlRoleDataService;
import cn.plou.web.system.permission.rlUserRole.service.RlUserRoleService;
import cn.plou.web.system.permission.role.service.DataRoleService;
import cn.plou.web.system.permission.userDataHistory.entity.UserDataHistory;
import cn.plou.web.system.permission.userDataHistory.service.impl.UserDataHistoryServiceImpl;
import cn.plou.web.system.permission.userPageHistory.entity.UserPageHistory;
import cn.plou.web.system.permission.userPageHistory.service.impl.UserPageHistoryServiceImpl;

@Component
public class MeterServiceImpl implements MeterService {

	@Autowired
    private MeterMapper meterMapper;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private StationService stationService;
    @Autowired
    private CommuityService commuityService;
    @Autowired
    private BuildService buildService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private HouseService houseService;
    @Autowired
    private MbusService mbusService;
    @Autowired
    private UserPageHistoryServiceImpl userPageHistoryServiceImpl;
    @Autowired
    private UserDataHistoryServiceImpl userDataHistoryServiceImpl;
    @Autowired
    private TypeMstService typeMstService;
    @Autowired
    private IDGenerater idGenerater;
    @Autowired
    private RlRoleDataService rlRoleDataService;
    @Autowired
    private RlUserRoleService rlUserRoleService;
    @Autowired
    private DataRoleService dataRoleService;
    @Override
    public List<MeterInfo> getMeterInfoById(String meterId) {
        List<MeterInfo> list = new ArrayList<MeterInfo>();
        MeterInfo meterInfo = meterMapper.selectByPrimaryKey(meterId);
        list.add(meterInfo);
        return list;
    }

    @Override
    public MeterInfo getMeterById(String meterId) {
        return meterMapper.selectByPrimaryKey(meterId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteMeterBatchByIds(List<String> meterIds) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("meter");
        userPageHistory.setAct("deleteMeterBatchByIds");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return meterMapper.deleteBatchByIds(meterIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int setDelMeterBatchByIds(List<String> meterIds) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("meter");
        userPageHistory.setAct("setDelMeterBatchByIds");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);

        for (String meterId : meterIds) {
            UserDataHistory userDataHistory = new UserDataHistory();
            MeterInfo info = meterMapper.selectByPrimaryKey(meterId);
            if(info != null){
            	userDataHistory.setConsumerId(meterMapper.selectByPrimaryKey(meterId).getConsumerId());
            }else{
            	userDataHistory.setConsumerId(meterId.substring(2, meterId.length()-2));
            }
            userDataHistory.setPage("meter");
            userDataHistory.setInfo("删除仪表，仪表号:" + (meterMapper.selectByPrimaryKey(meterId).getAddress2nd()));
            userDataHistoryServiceImpl.addUserDataHistory(userDataHistory);
        }
        return meterMapper.updateDelBatchByIds(meterIds,UserUtils.getUserId(),new Date());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addMeter(Meter meter) {
        meter.setIsvalid(1);
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("meter");
        userPageHistory.setAct("addMeter");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        meter.setCreateUser(UserUtils.getUserId());
        meter.setCreateDate(new Date());
        int num = meterMapper.insertSelective(meter);
        List<Meter> meters = new ArrayList<>();
        meters.add(meter);
      	Map<String, String> map = mbusService.parseDownProtByMeter(meters);
      	mbusService.updateDownProt(map);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyBatchByIds(MeterVo meterVo) {
			int num = 0;
			List<Meter> meters = null;
			meterVo.setUpdateUser(UserUtils.getUserId());
			meterVo.setUpdateDate(new Date());
			if (meterVo.getBatchCheckbox()) {
				UserDataHistory userDataHistory = new UserDataHistory();
				userDataHistory.setConsumerId(meterVo.getBatchModifyId());
				userDataHistory.setPage("house");
				userDataHistory.setInfo("批量修改住户，ID:" + (meterVo.getBatchModifyId()));
				userDataHistoryServiceImpl.addUserDataHistory(userDataHistory);

				if (meterVo.getBatchModifyType().equals("station")) {
					List<String> comuitys = commuityService.getCommuityIdsList(null, meterVo.getBatchModifyId(), null);
					meterVo.setCommunitys(comuitys);
					num = meterMapper.updateMeterBatchAllStation(meterVo);
					meters = meterMapper.selectMeterBycomuitysIds(comuitys);
				} else {
					num = meterMapper.updateMeterBatchAll(meterVo);
					meters = meterMapper.selectMeterByotherId(meterVo.getBatchModifyId());
				}
				meters = meterMapper.selectMeterByIds(meterVo.getMeterIds());
        Map<String, String> map = mbusService.parseDownProtByMeter(meters);
        mbusService.updateDownProt(map);
        return num;
			}
    	
    	
        if (meterVo.getMeterPosition() != null) {
            if (!meterVo.getMeterPosition().equals("1") && meterVo.getMeterPosition().length() == 6 ||
                    !meterVo.getMeterPosition().equals("2") && meterVo.getMeterPosition().length() == 10 ||
                    !meterVo.getMeterPosition().equals("3") && meterVo.getMeterPosition().length() == 13 ||
                    !meterVo.getMeterPosition().equals("4") && meterVo.getMeterPosition().length() == 15 ||
                    !meterVo.getMeterPosition().equals("5") && meterVo.getMeterPosition().length() == 19) {
                throw new BusinessException("位置类型与所属id不匹配,请重新修改");
            }
        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("meter");
        userPageHistory.setAct("modifyBatchByIds");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);

        for (String meterId : meterVo.getMeterIds()) {
            UserDataHistory userDataHistory = new UserDataHistory();
            MeterInfo meter = meterMapper.selectByPrimaryKey(meterId);
            userDataHistory.setConsumerId(meter.getConsumerId());
            userDataHistory.setPage("meter");
            userDataHistory.setInfo("修改仪表，仪表号:" + (meterMapper.selectByPrimaryKey(meterId).getAddress2nd()));
            userDataHistoryServiceImpl.addUserDataHistory(userDataHistory);
        }
        num = meterMapper.updateBatchByIds(meterVo);
				meters = meterMapper.selectMeterByIds(meterVo.getMeterIds());
        Map<String, String> map = mbusService.parseDownProtByMeter(meters);
        mbusService.updateDownProt(map);
        return num;
    }

    @Override
    public List<String> getMeterIdsByRoleId() {
        List<String> consumerIds = new ArrayList<>();
        List<RlRoleData> rlRoleDataList = rlRoleDataService.getRlRoleDataByRoleId(rlUserRoleService.getRoleByUserId(UserUtils.getUserId()));
        if(!UserUtils.getUserId().equals("1")){
            for(RlRoleData rlRoleData : rlRoleDataList){
                if(rlRoleData.getColumnType().equals("A")){
                    consumerIds.addAll(stationService.getStationIdList(rlRoleData.getColumnValue(),null));
                }
                if(rlRoleData.getColumnType().equals("E")){
                    consumerIds.addAll(stationService.getStationIdList(null,rlRoleData.getColumnValue()));
                }
                if(rlRoleData.getColumnType().equals("F")){
                    consumerIds.add(rlRoleData.getColumnValue());
                }
            }
        }
        return consumerIds;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeterListInfo getAllMeter(Integer page, Integer pageSize, String order, String sortby, String companyId, String stationId, String commuityId, String buildingId, String unitId, String houseId, MeterVo meterVo) {
        MeterListInfo meterListInfo = new MeterListInfo();
        Integer start = null;
        if (page != null) {
            start = (page - 1) * pageSize;
        }
        
        List<String> companyIds = new ArrayList<String>();
        List<String> stationIds = new ArrayList<String>();
        List<String> commuityIds = new ArrayList<String>();
        Boolean flag = dataRoleService.getDataRoleItems(companyIds, stationIds, commuityIds
                    ,UserUtils.getUserId(),companyId, stationId,commuityId);
				if (!flag) {
					return meterListInfo;
				}
				if (companyIds.size() == 0 && stationIds.size() == 0 && commuityIds.size() == 0 && buildingId == null && unitId == null
						&& houseId == null) {
					meterListInfo.setCount(0);
					return meterListInfo;
				}
				List<MeterInfo> meterInfoList = meterMapper.selectAllMeter2(start, pageSize, companyIds, stationIds, commuityIds,
						buildingId, unitId, houseId, order, sortby, meterVo, null);
				Integer count = meterMapper.selectMeterListCount2(companyIds, stationIds, commuityIds, buildingId, unitId, houseId, meterVo,
						null);
				UserPageHistory userPageHistory = new UserPageHistory();
				userPageHistory.setCol("meter");
				userPageHistory.setAct("getAllMeter");
				userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
				List<TypeMst> typeMstList = typeMstService.getAllTypeMst();
				
				for (MeterInfo meterInfo : meterInfoList) {				
					meterInfo.setProtocolName(typeMstService.getTypeNameById(typeMstList, meterInfo.getProtocol()));
					meterInfo.setMbusProName(typeMstService.getTypeNameById(typeMstList, meterInfo.getMbusPro()));
					meterInfo.setMeterTypeName(typeMstService.getTypeNameById(typeMstList, meterInfo.getMeterType()));
					meterInfo.setRunningStateName(typeMstService.getTypeNameById(typeMstList, meterInfo.getRunningState()));
					meterInfo.setFactoryName(typeMstService.getTypeNameById(typeMstList, meterInfo.getFactory()));
					meterInfo.setMeterSubtypeName(typeMstService.getTypeNameById(typeMstList, meterInfo.getMeterSubtype()));
					meterInfo.setMeterStateName(typeMstService.getTypeNameById(typeMstList, meterInfo.getMeterState()));
				}
				meterListInfo.setMeterInfoList(meterInfoList);
				meterListInfo.setCount(count);
        return meterListInfo;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyMeter(Meter meter) {
        if (meter.getMeterPosition() != null) {
            if (!meter.getMeterPosition().contains("1") && meter.getConsumerId().length() == 6 ||
                    !meter.getMeterPosition().contains("2") && meter.getConsumerId().length() == 10 ||
                    !meter.getMeterPosition().contains("3") && meter.getConsumerId().length() == 13 ||
                    !meter.getMeterPosition().contains("4") && meter.getConsumerId().length() == 15 ||
                    !meter.getMeterPosition().contains("5") && meter.getConsumerId().length() == 19) {
                throw new BusinessException("位置类型与所属id不匹配,请重新修改");
            }

        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("meter");
        userPageHistory.setAct("modifyMeter");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);

        UserDataHistory userDataHistory = new UserDataHistory();
        userDataHistory.setConsumerId(meter.getConsumerId());
        userDataHistory.setPage("meter");
        userDataHistory.setInfo("修改仪表，仪表号:" + (meter.getAddress2nd()));
        userDataHistoryServiceImpl.addUserDataHistory(userDataHistory);
        meter.setUpdateUser(UserUtils.getUserId());
        meter.setUpdateDate(new Date());
        int num =  meterMapper.updateByPrimaryKeySelective(meter);
        List<Meter> meters = new ArrayList<>();
        meters.add(meter);
        Map<String, String> map = mbusService.parseDownProtByMeter(meters);
        mbusService.updateDownProt(map);
        return  num;
    }

    @Override
    public List<Meter> getMeterDownInforByConsumer(String consumerId) {
        return meterMapper.selectMeterDownInforByConsumer(consumerId);
    }

    @Override
    public List<String> getAllIds(String meterType, String consumerId) {
        return meterMapper.selectAllIds(meterType, consumerId);
    }

    @Override
    public ImportResult importExcel(MultipartFile multipartFile, ServletRequest request) {

        ImportResult importResult = new ImportResult();
        ExcelTools excel = new ExcelTools();
        Sheet sheet = excel.getFile(multipartFile, "设备信息");
        String path = request.getServletContext().getRealPath("") + "/errorFile/"; 
        importExcelSpeed(sheet,excel,importResult, path, excel.getIsSpeed());
       	return importResult;
    }

    private void importExcelSpeed(Sheet sheet, ExcelTools excel, ImportResult importResult, String path, Boolean isUpdate) {

      int successCount = 0;
      List<ErrorInfo> errorInfos = new ArrayList<ErrorInfo>();
      Map<String, List<Meter>> meterMap = new HashMap<>();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
          	if (excel.isCellVolid(row,36, errorInfos,style)) {
              	 totalData++;
                  Meter meter = new Meter();
                  String companyName = getCellValue(row.getCell(0));
                  String commuityName = getCellValue(row.getCell(1));
                  String buidName = getCellValue(row.getCell(2));
                  String unitName = getCellValue(row.getCell(3));
                  String houseName = getCellValue(row.getCell(4));
                  String systemName = getCellValue(row.getCell(49));
                  Boolean flag = houseService.getSubordAndUpdate(sub,companyName,commuityName, buidName,unitName,houseName,null,systemName,isUpdate);
                  if(!flag){
                  	sub.orderErrorInfo(errorInfos, i, row, style);
                  	continue;
                  }
                  meter.setMeterPosition(ExcelTools.getTypeMst(typeMstList, "meter_position" ,17 ,row, false));
                  String key = sub.orderMeterConsumerInfo(meter.getMeterPosition(), errorInfos,  i,17,  row, style);
                  if(!key.isEmpty()){
                  	meter.setConsumerId(key);
                  }else{
                  	continue;
                  }

                  meter.setCompanyId(sub.getCompany().getCompanyId());   
									if (sub.getSystem() != null) {
										meter.setSystemId(sub.getSystem().getSystemId());
									}
                  meter.setAddress1st(getCellValue(row.getCell(5)));
                  if (getCellValue(row.getCell(6)).length() == 0) {
                  	ExcelTools.addErrorInfo(errorInfos, i ,6,"二级地址不能为空", true, row, style);   
                      continue;
                  } else {
                      meter.setAddress2nd(getCellValue(row.getCell(6)));
                  }
                  if (getCellValue(row.getCell(7)).length() == 0) {
                  	ExcelTools.addErrorInfo(errorInfos, i ,7,"协议不能为空", true, row, style);   
                      continue;
                  } else {
                      meter.setProtocol(ExcelTools.getTypeMst(typeMstList, "protocol" ,7 ,row, false));
                  }
                  String mbusId = getCellValue(row.getCell(8));
                  meter.setMbusId(mbusId);
                  meter.setMbusPro(ExcelTools.getTypeMst(typeMstList, "protocoltype" ,9 ,row, false));
                  meter.setMbusReadmodelId(getCellValue(row.getCell(10)));
                  meter.setRepeaterId(getCellValue(row.getCell(11)));
                  if (getCellValue(row.getCell(12)).length() == 0) {
                  	ExcelTools.addErrorInfo(errorInfos, i ,12,"仪表类型不能为空", true, row, style);
                      continue;
                  } else {
                      meter.setMeterType(ExcelTools.getTypeMst(typeMstList, "meter_type" ,12 ,row, false));
                  }
                  meter.setSimCard(getCellValue(row.getCell(13)));
                  meter.setRunningState(getCellValue(row.getCell(14)));
                  meter.setInstallAddress(getCellValue(row.getCell(15)));
                  if (getCellValue(row.getCell(16)).length() != 0) {
                      try {
                          meter.setMeterErrorTime(sdf.parse(getCellValue(row.getCell(16))));
                      } catch (ParseException e) {
                      	ExcelTools.addErrorInfo(errorInfos, i ,16,"日期格式错误", false, row, style);                 
                      }
                  }
                  
                  meter.setSuperMeterId(getCellValue(row.getCell(18)));
                  meter.setValveMeterId(getCellValue(row.getCell(19)));
                  meter.setMainMeterId(getCellValue(row.getCell(20)));
                  if (getCellValue(row.getCell(21)).length() != 0) {
                      try {
                          meter.setUseStartDate(sdf.parse(getCellValue(row.getCell(21))));
                      } catch (ParseException e) {
                      	ExcelTools.addErrorInfo(errorInfos, i ,21,"日期格式错误", false, row, style);
                      }
                  }

                  if (getCellValue(row.getCell(22)).length() != 0) {
                      try {
                          meter.setNextCheckTime(sdf.parse(getCellValue(row.getCell(22))));
                      } catch (ParseException e) {
                      	ExcelTools.addErrorInfo(errorInfos, i ,22,"日期格式错误", false, row, style);
                      }
                  }
                  if (getCellValue(row.getCell(23)).length() != 0) {
                      try {
                          meter.setInstallHeight(new BigDecimal(getCellValue(row.getCell(23))));
                      } catch (Exception e) {
                      	ExcelTools.addErrorInfo(errorInfos, i ,23,"字符串类型转换错误", false, row, style);
                      }
                  }
                  meter.setLongitude(getCellValue(row.getCell(24)));
                  meter.setLatitude(getCellValue(row.getCell(25)));
                  meter.setAddress(getCellValue(row.getCell(26)));
                  meter.setNotes(getCellValue(row.getCell(27)));
                  meter.setMemo1(getCellValue(row.getCell(28)));
                  meter.setMemo2(getCellValue(row.getCell(29)));
                  meter.setMemo3(getCellValue(row.getCell(30)));
                  meter.setMemo4(getCellValue(row.getCell(31)));
                  if (getCellValue(row.getCell(32)).length() != 0) {
                      try {
                          meter.setBasecode(new BigDecimal(getCellValue(row.getCell(32))));
                      } catch (Exception e) {
                      	ExcelTools.addErrorInfo(errorInfos, i ,32,"字符串类型转换错误", false, row, style);
                      }
                  }
                  meter.setMeterSubtype(getCellValue(row.getCell(33)));
                  meter.setMeterState(getCellValue(row.getCell(34)));
                  meter.setFactory(getCellValue(row.getCell(35)));
                  meter.setDiameter(getCellValue(row.getCell(36)));
                  if (getCellValue(row.getCell(37)).length() != 0) {
                      try {
                          meter.setPrecisiona(new BigDecimal(getCellValue(row.getCell(37))));
                      } catch (Exception e) {
                      	ExcelTools.addErrorInfo(errorInfos, i ,37,"字符串类型转换错误", false, row, style);
                      }
                  }
                  meter.setSoftVer(getCellValue(row.getCell(38)));
                  meter.setHardVer(getCellValue(row.getCell(39)));
                  if (getCellValue(row.getCell(40)).length() != 0) {
                      try {
                          meter.setCommonFlow(new BigDecimal(getCellValue(row.getCell(40))));
                      } catch (Exception e) {
                      	ExcelTools.addErrorInfo(errorInfos, i ,40,"字符串类型转换错误", false, row, style);
                      }
                  }
                  if (getCellValue(row.getCell(41)).length() != 0) {
                      try {
                          meter.setMinimumFlow(new BigDecimal(getCellValue(row.getCell(41))));
                      } catch (Exception e) {
                      	ExcelTools.addErrorInfo(errorInfos, i ,41,"字符串类型转换错误", false, row, style);
                      }
                  }
                  if (getCellValue(row.getCell(42)).length() != 0) {
                      try {
                          meter.setTemperatureDiffer(new BigDecimal(getCellValue(row.getCell(42))));
                      } catch (Exception e) {
                      	ExcelTools.addErrorInfo(errorInfos, i ,42,"字符串类型转换错误", false, row, style);
                      }
                  }
                  if (getCellValue(row.getCell(43)).length() != 0) {
                      try {
                          meter.setTemperatureRange(new BigDecimal(getCellValue(row.getCell(43))));
                      } catch (Exception e) {
                      	ExcelTools.addErrorInfo(errorInfos, i ,43,"字符串类型转换错误", false, row, style);
                      }
                  }
                  meter.setBaudRate(getCellValue(row.getCell(44)));
                  meter.setMaxBound(getCellValue(row.getCell(45)));
                  if (getCellValue(row.getCell(46)).length() != 0) {
                      try {
                          meter.setLimtspeed(new BigDecimal(getCellValue(row.getCell(46))));
                      } catch (Exception e) {
                      	ExcelTools.addErrorInfo(errorInfos, i ,46,"字符串类型转换错误", false, row, style);
                      }
                  }
                  if (getCellValue(row.getCell(47)).length() != 0) {
                      try {
                          meter.setCtrlflag(Integer.parseInt(getCellValue(row.getCell(47))));
                      } catch (Exception e) {
                      	ExcelTools.addErrorInfo(errorInfos, i ,47,"数据不是整数", false, row, style);
                      }
                  }
                  
                  meter.setIsvalid(1);
                  //meter.setRowno(UUID.randomUUID().toString().replace("-", ""));
                  key = sub.getCommuity().getCommuityId();
                  Tools.addMap(key, meterMap, meter);
                  if (errorInfo.getPosition() == null) {
                      successCount += 1;
                  }
                  if(isUpdate){
                  	Mbus mbus = new Mbus();
                  	mbus.setMbusId(mbusId);
                  	mbus.setMbusCode(mbusId);
                  	if(sub.getCommuity() != null){
                  		mbus.setConsumerId(sub.getCommuity().getCommuityId());
                  	}
                  	mbus.setCompanyId(meter.getCompanyId());
                  	mbus.setMbusPosition("mbus_position_2");
                  	mbus.setEquipmentNo(ExcelTools.getTypeMst(typeMstList, "mbus_type" ,48,row, false));						//集中器类型
                    mbus.setMbusPro(ExcelTools.getTypeMst(typeMstList, "mbus_pro" ,49 ,row, false));										//封包协议
                    mbus.setTransMode(ExcelTools.getTypeMst(typeMstList, "trans_mode" ,50 ,row, false));							//传输方式
                    mbus.setProtocol(ExcelTools.getTypeMst(typeMstList, "protocoltype" ,51 ,row, false));									//使用协议
                    mbus.setUpCommMode(ExcelTools.getTypeMst(typeMstList, "up_comm_mode" ,52 ,row, false));					//上传方式   
                    mbus.setChannlMode(ExcelTools.getTypeMst(typeMstList, "channl_mode" ,53 ,row, false));					//有无虚拟
                    mbus.setFactory(ExcelTools.getTypeMst(typeMstList, "factory" ,54 ,row, false));
                    mbus.setDownPro(ExcelTools.getTypeMst(typeMstList, "protocol" ,55 ,row, false));
                  	mbusService.addMbus(mbus);
                  }
              }
          }
   }
   if(errorInfos.size()>0){
       String path1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "meter-" + UserUtils.getUserId() + "-" + ".xlsx";
       try {
           //path = "C:/errorFile/";
           File file = new File(path);
           if (!file.exists()) {
               file.mkdirs();
           }
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
      importResult.setId(UserUtils.getUserId() + UUID.randomUUID().toString().replace("-", "") + "meter");
     
      if(importResult.getFailList().size()==0){
      	for(String key: meterMap.keySet()){
      	idGenerater.generateMeterIdsbyCommuity(key, meterMap.get(key));
      	addBatch(meterMap.get(key));
      	}
      }
		}

		@Override
    public int addBatch(List<Meter> meters) {
    	List<Meter> meters2 = new ArrayList<>();
    	int sum = meters.size()/100;
    	int index = 0;
    	for(index = 0; index < sum-1; index++){
    		meters2.clear();
	    	for(int i = 0; i < 100; i++){
	    		meters2.add(meters.get(index*100+i));
	    	}
	    	meterMapper.addBatch(meters2);
    	}
    	meters2.clear();
    	for(int i = index*100; i < meters.size(); i++){
    		meters2.add(meters.get(i));
    	}
    	meterMapper.addBatch(meters);
    	Map<String, String> map = mbusService.parseDownProtByMeter(meters);
    	mbusService.updateDownProt(map);
        return meters.size();
    }

//    @Override
//    public String getMeterByConsumer(String companyName, String commmuityName, String buildingName, String unitName, String houseName) {
//        String consumerId = null;
//        String companyId = companyService.getCompanyByName(companyName).getCompanyId();
//        /*if(stationName != null&&commmuityName==null&&buildingName==null&&unitName==null&&houseName==null){
//            consumerId = stationService.getStationByName(stationName).getStationId();
//        }else*/
//        if (commmuityName != null && buildingName == null && unitName == null && houseName == null) {
//            consumerId = commuityService.getCommuityByName(commmuityName, commmuityName).getCommuityId();
//        } else if (buildingName != null && unitName == null && houseName == null) {
//            consumerId = buildService.getBuildByName(commmuityName, buildingName, companyId).getBuildingNo();
//        } else if (unitName != null && houseName == null) {
//            consumerId = unitService.getUnitByName(companyId, commmuityName, buildingName, unitName).getUnitId();
//        } else if (houseName != null) {
//            //String commuityId = commuityService.getCommuityByName(commmuityName,companyId).getCommuityId();
//            String commuityId = commuityService.getCommuityByName(commmuityName, companyId).getCommuityId();
//            consumerId = houseService.getHouseByName(houseName, commuityId).getConsumerId();
//        }
//        return consumerId;
//    }
//    

    @Override
    public String exportExcel(ServletRequest request,MeterSelectInfo meterSelectInfo) {
        String path1 = (UUID.randomUUID().toString().replace("-",""))+"-meter.xlsx";
        // 第一步，创建一个webbook，对应一个Excel文件
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = wb.createSheet("Sheet1");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        XSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        /*XSSFCell cell = row.createCell(0);
        cell.setCellValue("仪表Id");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("仪表Id2");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("用途Id");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("一级地址");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("二级地址");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("协议");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("集中器Id");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("集中器封包协议");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("采集器Id");
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellValue("通道号");
        cell.setCellStyle(style);
        cell = row.createCell(10);
        cell.setCellValue("仪表类型");
        cell.setCellStyle(style);
        cell = row.createCell(11);
        cell.setCellValue("通讯卡号");
        cell.setCellStyle(style);
        cell = row.createCell(12);
        cell.setCellValue("运行状态");
        cell.setCellStyle(style);
        cell = row.createCell(13);
        cell.setCellValue("安装地址");
        cell.setCellStyle(style);
        cell = row.createCell(14);
        cell.setCellValue("最近故障发生时间");
        cell.setCellStyle(style);
        cell = row.createCell(15);
        cell.setCellValue("位置类型");
        cell.setCellStyle(style);
        cell = row.createCell(16);
        cell.setCellValue("上级表号");
        cell.setCellStyle(style);
        cell = row.createCell(17);
        cell.setCellValue("阀门对应表号");
        cell.setCellStyle(style);
        cell = row.createCell(18);
        cell.setCellValue("分体对应主体ＩＤ");
        cell.setCellStyle(style);
        cell = row.createCell(19);
        cell.setCellValue("启用日期");
        cell.setCellStyle(style);
        cell = row.createCell(20);
        cell.setCellValue("下次校表时间");
        cell.setCellStyle(style);
        cell = row.createCell(21);
        cell.setCellValue("海拔标高");
        cell.setCellStyle(style);
        cell = row.createCell(22);
        cell.setCellValue("经度");
        cell.setCellStyle(style);
        cell = row.createCell(23);
        cell.setCellValue("纬度");
        cell.setCellStyle(style);
        cell = row.createCell(24);
        cell.setCellValue("公司Id");
        cell.setCellStyle(style);
        cell = row.createCell(25);
        cell.setCellValue("地址全称");
        cell.setCellStyle(style);
        cell = row.createCell(26);
        cell.setCellValue("备注");
        cell.setCellStyle(style);
        cell = row.createCell(27);
        cell.setCellValue("保留1");
        cell.setCellStyle(style);
        cell = row.createCell(28);
        cell.setCellValue("保留2");
        cell.setCellStyle(style);
        cell = row.createCell(29);
        cell.setCellValue("保留3");
        cell.setCellStyle(style);
        cell = row.createCell(30);
        cell.setCellValue("保留4");
        cell.setCellStyle(style);
        cell = row.createCell(31);
        cell.setCellValue("新表底码");
        cell.setCellStyle(style);
        cell = row.createCell(32);
        cell.setCellValue("仪表子类型");
        cell.setCellStyle(style);
        cell = row.createCell(33);
        cell.setCellValue("水表钢印号");
        cell.setCellStyle(style);
        cell = row.createCell(34);
        cell.setCellValue("水表地址");
        cell.setCellStyle(style);
        cell = row.createCell(35);
        cell.setCellValue("表状态设定");
        cell.setCellStyle(style);
        cell = row.createCell(36);
        cell.setCellValue("厂商");
        cell.setCellStyle(style);
        cell = row.createCell(37);
        cell.setCellValue("口径");
        cell.setCellStyle(style);
        cell = row.createCell(38);
        cell.setCellValue("计量精度");
        cell.setCellStyle(style);
        cell = row.createCell(39);
        cell.setCellValue("软件版本");
        cell.setCellStyle(style);
        cell = row.createCell(40);
        cell.setCellValue("硬件版本");
        cell.setCellStyle(style);
        cell = row.createCell(41);
        cell.setCellValue("常用流量");
        cell.setCellStyle(style);
        cell = row.createCell(42);
        cell.setCellValue("最小流量");
        cell.setCellStyle(style);
        cell = row.createCell(43);
        cell.setCellValue("温差范围");
        cell.setCellStyle(style);
        cell = row.createCell(44);
        cell.setCellValue("温度范围");
        cell.setCellStyle(style);
        cell = row.createCell(45);
        cell.setCellValue("波特率");
        cell.setCellStyle(style);
        cell = row.createCell(46);
        cell.setCellValue("量程");
        cell.setCellStyle(style);
        cell = row.createCell(47);
        cell.setCellValue("设定流量");
        cell.setCellStyle(style);
        cell = row.createCell(48);
        cell.setCellValue("终止标志");
        cell.setCellStyle(style);
        cell = row.createCell(49);
        cell.setCellValue("有效标志");
        cell.setCellStyle(style);*/
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("仪表类型");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("二级地址");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("协议");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("运行状态");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("上级表号");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("阀门对应表号");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("启用日期");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("下次校表时间");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("安装高度");
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellValue("公司名称");
        cell.setCellStyle(style);
        cell = row.createCell(10);
        cell.setCellValue("厂商");
        cell.setCellStyle(style);
        cell = row.createCell(11);
        cell.setCellValue("新表底码");
        cell.setCellStyle(style);
        cell = row.createCell(12);
        cell.setCellValue("表状态设定");
        cell.setCellStyle(style);
        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
        List<MeterInfo> list = getAllMeter(null,null,null,null,meterSelectInfo.getCompanyId(),meterSelectInfo.getStationId(),meterSelectInfo.getCommuityId(),meterSelectInfo.getBuildingNo(),meterSelectInfo.getUnitId(),meterSelectInfo.getConsumerId(),meterSelectInfo.getSearchCondition()).getMeterInfoList();
        for (int i = 0; i < list.size(); i++){
            row = sheet.createRow(i + 1);
            MeterInfo meterInfo = list.get(i);
            // 第四步，创建单元格，并设置值
            if(meterInfo.getMeterTypeName()!=null){
                row.createCell(0).setCellValue(meterInfo.getMeterTypeName());
            }
            row.createCell(1).setCellValue(meterInfo.getAddress2nd());
            if(meterInfo.getProtocolName()!=null){
                row.createCell(2).setCellValue(meterInfo.getProtocolName());
            }
            if(meterInfo.getRunningStateName()!=null){
                row.createCell(3).setCellValue(meterInfo.getRunningStateName());
            }
            if(meterInfo.getSuperMeterId()!=null){
                row.createCell(4).setCellValue(meterInfo.getSuperMeterId());
            }
            if(meterInfo.getValveMeterId()!=null){
                row.createCell(5).setCellValue(meterInfo.getValveMeterId());
            }
            if(meterInfo.getUseStartDate()!=null){
                row.createCell(6).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(meterInfo.getUseStartDate()));
            }
            if(meterInfo.getNextCheckTime()!=null){
                row.createCell(7).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(meterInfo.getNextCheckTime()));
            }
            if(meterInfo.getInstallHeight()!=null){
                row.createCell(8).setCellValue(meterInfo.getInstallHeight()+"");
            }if(meterInfo.getCompanyName()!=null){
                row.createCell(9).setCellValue(meterInfo.getCompanyName());
            }
            if(meterInfo.getFactoryName()!=null){
                row.createCell(10).setCellValue(meterInfo.getFactoryName());
            }
            if(meterInfo.getBasecode()!=null){
                row.createCell(11).setCellValue(meterInfo.getBasecode()+"");
            }
            if(meterInfo.getMeterStateName()!=null){
                row.createCell(12).setCellValue(meterInfo.getMeterStateName());
            }
            /*row.createCell(0).setCellValue(meterInfo.getMeterId());
            row.createCell(1).setCellValue(meterInfo.getRowno());
            row.createCell(2).setCellValue(meterInfo.getConsumerId());
            if(meterInfo.getAddress1st()!=null){
                row.createCell(3).setCellValue(meterInfo.getAddress1st());
            }
            row.createCell(4).setCellValue(meterInfo.getAddress2nd());
            if(meterInfo.getProtocol()!=null){
                row.createCell(5).setCellValue(meterInfo.getProtocol());
            }
            if(meterInfo.getMbusId()!=null){
                row.createCell(6).setCellValue(meterInfo.getMbusId());
            }
            if(meterInfo.getMbusPro()!=null){
                row.createCell(7).setCellValue(meterInfo.getMbusPro());
            }
            if(meterInfo.getMbusReadmodelId()!=null){
                row.createCell(8).setCellValue(meterInfo.getMbusReadmodelId());
            }
            if(meterInfo.getRepeaterId()!=null){
                row.createCell(9).setCellValue(meterInfo.getRepeaterId());
            }
            if(meterInfo.getMeterType()!=null){
                row.createCell(10).setCellValue(meterInfo.getMeterType());
            }
            if(meterInfo.getSimCard()!=null){
                row.createCell(11).setCellValue(meterInfo.getSimCard());
            }
            if(meterInfo.getRunningState()!=null){
                row.createCell(12).setCellValue(meterInfo.getRunningState());
            }
            if(meterInfo.getInstallAddress()!=null){
                row.createCell(13).setCellValue(meterInfo.getInstallAddress());
            }
            if(meterInfo.getMeterErrorTime()!=null){
                row.createCell(14).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(meterInfo.getMeterErrorTime()));
            }
            if(meterInfo.getMeterPosition()!=null){
                row.createCell(15).setCellValue(meterInfo.getMeterPosition());
            }
            if(meterInfo.getSuperMeterId()!=null){
                row.createCell(16).setCellValue(meterInfo.getSuperMeterId());
            }
            if(meterInfo.getValveMeterId()!=null){
                row.createCell(17).setCellValue(meterInfo.getValveMeterId());
            }
            if(meterInfo.getMainMeterId()!=null){
                row.createCell(18).setCellValue(meterInfo.getMainMeterId());
            }
            if(meterInfo.getUseStartDate()!=null){
                row.createCell(19).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(meterInfo.getUseStartDate()));
            }
            if(meterInfo.getNextCheckTime()!=null){
                row.createCell(20).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(meterInfo.getNextCheckTime()));
            }
            if(meterInfo.getInstallHeight()!=null){
                row.createCell(21).setCellValue(meterInfo.getInstallHeight()+"");
            }
            if(meterInfo.getLongitude()!=null){
                row.createCell(22).setCellValue(meterInfo.getLongitude());
            }
            if(meterInfo.getLatitude()!=null){
                row.createCell(23).setCellValue(meterInfo.getLatitude());
            }
            if(meterInfo.getCompanyId()!=null){
                row.createCell(24).setCellValue(meterInfo.getCompanyId());
            }
            if(meterInfo.getAddress()!=null){
                row.createCell(25).setCellValue(meterInfo.getAddress());
            }
            if(meterInfo.getNotes()!=null){
                row.createCell(26).setCellValue(meterInfo.getNotes());
            }
            if(meterInfo.getMemo1()!=null){
                row.createCell(27).setCellValue(meterInfo.getMemo1());
            }
            if(meterInfo.getMemo2()!=null){
                row.createCell(28).setCellValue(meterInfo.getMemo2());
            }
            if(meterInfo.getMemo3()!=null){
                row.createCell(29).setCellValue(meterInfo.getMemo3());
            }
            if(meterInfo.getMemo4()!=null){
                row.createCell(30).setCellValue(meterInfo.getMemo4());
            }
            if(meterInfo.getBasecode()!=null){
                row.createCell(31).setCellValue(meterInfo.getBasecode()+"");
            }
            if(meterInfo.getMeterSubtype()!=null){
                row.createCell(32).setCellValue(meterInfo.getMeterSubtype());
            }
            if(meterInfo.getSteel()!=null){
                row.createCell(33).setCellValue(meterInfo.getSteel());
            }
            if(meterInfo.getWaterAddress()!=null){
                row.createCell(34).setCellValue(meterInfo.getWaterAddress());
            }
            if(meterInfo.getMeterState()!=null){
                row.createCell(35).setCellValue(meterInfo.getMeterState());
            }
            if(meterInfo.getFactory()!=null){
                row.createCell(36).setCellValue(meterInfo.getFactory());
            }
            if(meterInfo.getDiameter()!=null){
                row.createCell(37).setCellValue(meterInfo.getDiameter());
            }
            if(meterInfo.getPrecisiona()!=null){
                row.createCell(38).setCellValue(meterInfo.getPrecisiona()+"");
            }
            if(meterInfo.getSoftVer()!=null){
                row.createCell(39).setCellValue(meterInfo.getSoftVer());
            }
            if(meterInfo.getHardVer()!=null){
                row.createCell(40).setCellValue(meterInfo.getHardVer());
            }
            if(meterInfo.getCommonFlow()!=null){
                row.createCell(41).setCellValue(meterInfo.getCommonFlow()+"");
            }
            if(meterInfo.getMinimumFlow()!=null){
                row.createCell(42).setCellValue(meterInfo.getMinimumFlow()+"");
            }
            if(meterInfo.getTemperatureDiffer()!=null){
                row.createCell(43).setCellValue(meterInfo.getTemperatureDiffer()+"");
            }
            if(meterInfo.getTemperatureRange()!=null){
                row.createCell(44).setCellValue(meterInfo.getTemperatureRange()+"");
            }
            if(meterInfo.getBaudRate()!=null){
                row.createCell(45).setCellValue(meterInfo.getBaudRate());
            }
            if(meterInfo.getMaxBound()!=null){
                row.createCell(46).setCellValue(meterInfo.getMaxBound());
            }
            if(meterInfo.getLimtspeed()!=null){
                row.createCell(47).setCellValue(meterInfo.getLimtspeed()+"");
            }
            if(meterInfo.getCtrlflag()!=null){
                row.createCell(48).setCellValue(meterInfo.getCtrlflag());
            }
            if(meterInfo.getIsvalid()!=null){
                row.createCell(49).setCellValue(meterInfo.getIsvalid());
            }*/
        }
        //第六步,输出Excel文件
        OutputStream output= null;
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
		public Integer getMaxmeterId(String typeAndConsumerId) {
			Integer index = 0;
			String id = meterMapper.getMaxMeterId(typeAndConsumerId);
			if (id != null && id.isEmpty()) {
				index = Integer.valueOf(id.substring(id.length()-2, id.length()));
			}
			return index;
		}

		@Override
		public Integer getMeterCountbyCommuityId(String commuityId) {
			return meterMapper.getMeterCountbyCommuityId(commuityId);
		}

		@Override
		public List<Meter> selectMeterByotherId(String consumerId) {
				return meterMapper.selectMeterByotherId(consumerId);
		}

		@Override
		public void modifyMeterAddressbyConsumer(String commuityId, String changedAdress, String orgAddress) {
			meterMapper.modifyMeterAddressbyConsumer(commuityId, Tools.nkey, changedAdress, orgAddress);
		}

		@Override
		public void modifyMeterAddress(String consumerId, String address) {
			meterMapper.modifyMeterAddress(consumerId, address);
		}
		
}

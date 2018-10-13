package cn.plou.web.system.meterMessage.mbus.service.impl;

import static cn.plou.web.common.importDataBatch.CleanOnTime.cleanTempFile;
import static cn.plou.web.common.utils.ExcelTools.getCellValue;
import static cn.plou.web.common.utils.Tools.getMaxIdNoSize;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
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

import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.importDataBatch.ErrorInfo;
import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.common.utils.ExcelTools;
import cn.plou.web.common.utils.Tools;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.heatManage.housecontrol.service.HouseControlService;
import cn.plou.web.system.baseMessage.build.service.BuildService;
import cn.plou.web.system.baseMessage.commuity.service.CommuityService;
import cn.plou.web.system.baseMessage.company.service.CompanyService;
import cn.plou.web.system.baseMessage.company.vo.CompanyInfo;
import cn.plou.web.system.baseMessage.company.vo.CompanyListInfo;
import cn.plou.web.system.baseMessage.house.entity.Subord;
import cn.plou.web.system.baseMessage.house.service.HouseService;
import cn.plou.web.system.baseMessage.station.service.StationService;
import cn.plou.web.system.baseMessage.unit.service.UnitService;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.TypeMstService;
import cn.plou.web.system.meterMessage.mbus.dao.MbusMapper;
import cn.plou.web.system.meterMessage.mbus.entity.Mbus;
import cn.plou.web.system.meterMessage.mbus.service.MbusService;
import cn.plou.web.system.meterMessage.mbus.vo.MbusInfo;
import cn.plou.web.system.meterMessage.mbus.vo.MbusListInfo;
import cn.plou.web.system.meterMessage.mbus.vo.MbusSelectInfo;
import cn.plou.web.system.meterMessage.mbus.vo.MbusVo;
import cn.plou.web.system.meterMessage.mbusReadmodel.entity.MbusReadmodel;
import cn.plou.web.system.meterMessage.mbusReadmodel.service.MbusReadmodelService;
import cn.plou.web.system.meterMessage.meter.entity.Meter;
import cn.plou.web.system.meterMessage.meter.service.MeterService;
import cn.plou.web.system.meterMessage.meter.vo.MeterInfo;
import cn.plou.web.system.permission.rlRoleData.entity.RlRoleData;
import cn.plou.web.system.permission.rlRoleData.service.RlRoleDataService;
import cn.plou.web.system.permission.rlUserRole.service.RlUserRoleService;
import cn.plou.web.system.permission.role.entity.DataRole;
import cn.plou.web.system.permission.role.service.DataRoleService;
import cn.plou.web.system.permission.userDataHistory.entity.UserDataHistory;
import cn.plou.web.system.permission.userDataHistory.service.impl.UserDataHistoryServiceImpl;
import cn.plou.web.system.permission.userPageHistory.entity.UserPageHistory;
import cn.plou.web.system.permission.userPageHistory.service.impl.UserPageHistoryServiceImpl;

@Component
public class MbusServiceImpl implements MbusService {
    @Autowired
    private MbusMapper mbusMapper;
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
    private UserPageHistoryServiceImpl userPageHistoryServiceImpl;
    @Autowired
    private UserDataHistoryServiceImpl userDataHistoryServiceImpl;
    @Autowired
    private MbusReadmodelService mbusReadmodelService;
    @Autowired
    private TypeMstService typeMstService;
    @Autowired
    RlRoleDataService rlRoleDataService;
    @Autowired
    RlUserRoleService rlUserRoleService;
    @Autowired
    private MeterService meterService;
    @Autowired
    private DataRoleService dataRoleService;
    @Autowired
    private HouseControlService houseControlService;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addMbus(Mbus mbus) {
        if (getAllCodes().contains(mbus.getMbusCode())) {
            throw new BusinessException("您所添加的通讯设备编号已存在");
        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("mbus");
        userPageHistory.setAct("addMbus");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        mbus.setCreateUser(UserUtils.getUserId());
        mbus.setCreateDate(new Date());
        if(!mbus.getMbusPosition().contains("mbus_position")){
        	mbus.setMbusPosition("mbus_position" + mbus.getMbusPosition());
        }
        return mbusMapper.insertSelective(mbus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteMbusBatchByIds(List<String> mbusIds) {
        for (String mbusId : mbusIds) {
            List<MbusReadmodel> mbusReadmodels = mbusReadmodelService.getMbusReadmodelByMbusId(mbusId);
            if (mbusReadmodels.size() != 0) {
                throw new BusinessException(getMbusById(mbusId).getMbusCode() + "通讯设备下有关联项，不能删除");
            }
        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("mbus");
        userPageHistory.setAct("deleteMbusBatchByIds");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return mbusMapper.deleteMbusBatchByIds(mbusIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyDelMbusBatchById(List<String> mbusIds) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("mbus");
        userPageHistory.setAct("modifyDelMbusBatchById");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        for (String mbusId : mbusIds) {
            List<MbusReadmodel> mbusReadmodels = mbusReadmodelService.getMbusReadmodelByMbusId(mbusId);
            if (mbusReadmodels.size() != 0) {
                throw new BusinessException(getMbusById(mbusId).getMbusCode() + "通讯设备下有关联项，不能删除");
            }
        }
        for (String mbusId : mbusIds) {
            UserDataHistory userDataHistory = new UserDataHistory();
            userDataHistory.setConsumerId(mbusMapper.selectByPrimaryKey(mbusId).getConsumerId());
            userDataHistory.setPage("mbus");
            userDataHistory.setInfo("删除集中器，集中器号:" + (mbusMapper.selectByPrimaryKey(mbusId).getMbusCode()));
            userDataHistoryServiceImpl.addUserDataHistory(userDataHistory);
        }
        return mbusMapper.updateDelBatchByIds(mbusIds,UserUtils.getUserId(),new Date());
    }

    @Override
    public Mbus getMbusById(String mbusId) {
        return mbusMapper.selectByPrimaryKey(mbusId);
    }

		@Override
		@Transactional(rollbackFor = Exception.class)
		public MbusListInfo getAllMbus(Integer page, Integer pageSize, String companyId, String stationId, String commuityId,
				String buildingId, String unitId, String houseId, String order, String sortby, MbusVo mbusVo) {
	
			MbusListInfo mbusListInfo = new MbusListInfo();
			Integer start = null;
			if (page != null) {
				start = (page - 1) * pageSize;
			}
			// 获得权限
			List<String> companyIds = new ArrayList<String>();
			List<String> stationIds = new ArrayList<String>();
			List<String> commuityIds = new ArrayList<String>();
      Boolean flag = dataRoleService.getDataRoleItems(companyIds, stationIds, commuityIds
          ,UserUtils.getUserId(),companyId, stationId,commuityId);
				if (!flag) {
					mbusListInfo.setCount(0);
					return mbusListInfo;
				}
			if (companyIds.size() == 0 && stationIds.size() == 0 && commuityIds.size() == 0 && buildingId == null && unitId == null
					&& houseId == null) {
				mbusListInfo.setCount(0);
				return mbusListInfo;
			}
			// 查找数据
			Integer count = mbusMapper.selectMbusListCount2(companyIds, stationIds, commuityIds, buildingId, unitId, houseId, mbusVo,
					null);
			List<MbusInfo> mbusList = mbusMapper.selectAllMbus2(start, pageSize, companyIds, stationIds, commuityIds, buildingId,
					unitId, houseId, order, sortby, mbusVo, null);
			UserPageHistory userPageHistory = new UserPageHistory();
			userPageHistory.setCol("mbus");
			userPageHistory.setAct("getAllMbus");
			userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
			// 根据id获取字典项名称
			List<TypeMst> typeMstList = typeMstService.getAllTypeMst();
			for (MbusInfo mbusInfo : mbusList) {
				mbusInfo.setFactoryName(typeMstService.getTypeNameById(typeMstList, mbusInfo.getFactory()));
				mbusInfo.setUpCommModeName(typeMstService.getTypeNameById(typeMstList, mbusInfo.getUpCommMode()));
				mbusInfo.setTransModeName(typeMstService.getTypeNameById(typeMstList, mbusInfo.getTransMode()));
				mbusInfo.setChannlModeName(typeMstService.getTypeNameById(typeMstList, mbusInfo.getChannlMode()));
				mbusInfo.setMbusProName(typeMstService.getTypeNameById(typeMstList, mbusInfo.getMbusPro()));
				mbusInfo.setProtocolName(typeMstService.getTypeNameById(typeMstList, mbusInfo.getProtocol()));
				mbusInfo.setSimProviderName(typeMstService.getTypeNameById(typeMstList, mbusInfo.getSimProvider()));
				mbusInfo.setIsFirstName(typeMstService.getTypeNameById(typeMstList, mbusInfo.getIsFirst()));
				mbusInfo.setRunningStateName(typeMstService.getTypeNameById(typeMstList, mbusInfo.getRunningState()));
				mbusInfo.setBusyStatusName(typeMstService.getTypeNameById(typeMstList, mbusInfo.getBusyStatus()));
				mbusInfo.setEquipmentNoName(typeMstService.getTypeNameById(typeMstList, mbusInfo.getEquipmentNo()));
				if (mbusInfo.getDownPro() != null) {
					if (mbusInfo.getDownPro().contains(",")) {
						String downProName = null;
						String downPro[] = mbusInfo.getDownPro().split(",");
						for (int i = 0; i < downPro.length; i++) {
							if (typeMstService.getTypeMstById(downPro[i]) == null) {
								mbusInfo.setDownProName(null);
							} else {
								if (downProName == null) {
									downProName = typeMstService.getTypeMstById(downPro[i]).getTypeName();
								} else {
									downProName += "，" + typeMstService.getTypeMstById(downPro[i]).getTypeName();
								}
							}
						}
						mbusInfo.setDownProName(downProName);
					} else {
						if (typeMstService.getTypeMstById(mbusInfo.getDownPro()) == null) {
							mbusInfo.setDownProName(null);
						} else {
							mbusInfo.setDownProName(typeMstService.getTypeMstById(mbusInfo.getDownPro()).getTypeName());
						}
					}
				}
			}
			mbusListInfo.setMbusInfoList(mbusList);
			mbusListInfo.setCount(count);
			return mbusListInfo;
		}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyBatch(MbusVo mbusVo) {
        if (mbusVo.getConsumerId() != null && mbusVo.getMbusPosition() != null) {
            if (!mbusVo.getMbusPosition().contains("1") && mbusVo.getConsumerId().length() == 6 ||
                    !mbusVo.getMbusPosition().contains("2") && mbusVo.getConsumerId().length() == 10 ||
                    !mbusVo.getMbusPosition().contains("3") && mbusVo.getConsumerId().length() == 13 ||
                    !mbusVo.getMbusPosition().contains("4") && mbusVo.getConsumerId().length() == 15 ||
                    !mbusVo.getMbusPosition().contains("5") && mbusVo.getConsumerId().length() == 19) {
                throw new BusinessException("位置类型与所属id不匹配,请重新修改");
            }
        }

        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("mbus");
        userPageHistory.setAct("modifyBatch");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        mbusVo.setUpdateUser(UserUtils.getUserId());
        mbusVo.setUpdateDate(new Date());
        if(mbusVo.getBatchCheckbox()){ 	
          UserDataHistory userDataHistory = new UserDataHistory();
          userDataHistory.setConsumerId(mbusVo.getBatchModifyId());
          userDataHistory.setPage("mbus");
          userDataHistory.setInfo("批量修改集中器，修改位置:" + (mbusVo.getBatchModifyId()));      
          userDataHistoryServiceImpl.addUserDataHistory(userDataHistory);
          
       	if(mbusVo.getBatchModifyType().equals("station")){
       		List<String> comuitys = commuityService.getCommuityIdsList(null, mbusVo.getBatchModifyId(), null);
       		mbusVo.setCommunitys(comuitys);
       		return mbusMapper.updateBatchAllStation(mbusVo);
       	}else{
       		//下挂协议更新
  			String companyId = mbusVo.getBatchModifyId().substring(0,5);
  			List<Meter> meters = meterService.selectMeterByotherId(companyId);
	    	Map<String, String> map = parseDownProtByMeter(meters);
        updateDownProt(map);
       	return mbusMapper.updateBatchAll(mbusVo);
       	}
       }else{
         for (String mbusId : mbusVo.getMbusIds()) {
           UserDataHistory userDataHistory = new UserDataHistory();
           userDataHistory.setConsumerId(mbusMapper.selectByPrimaryKey(mbusId).getConsumerId());
           userDataHistory.setPage("mbus");
           userDataHistory.setInfo("修改集中器，集中器号:" + (mbusMapper.selectByPrimaryKey(mbusId).getMbusCode()));
           userDataHistoryServiceImpl.addUserDataHistory(userDataHistory);
       }
       	return mbusMapper.updateBatch(mbusVo);
       }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyMbus(Mbus mbus) {
//        if (!mbus.getMbusPosition().equals("1") && mbus.getConsumerId().length() == 6 ||
//                !mbus.getMbusPosition().equals("2") && mbus.getConsumerId().length() == 10 ||
//                !mbus.getMbusPosition().equals("3") && mbus.getConsumerId().length() == 13 ||
//                !mbus.getMbusPosition().equals("4") && mbus.getConsumerId().length() == 15 ||
//                !mbus.getMbusPosition().equals("5") && mbus.getConsumerId().length() == 19) {
//            throw new BusinessException("位置类型与所属id不匹配,请重新修改");
//        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("mbus");
        userPageHistory.setAct("modifyMbus");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);

        UserDataHistory userDataHistory = new UserDataHistory();
        userDataHistory.setConsumerId(mbus.getConsumerId());
        userDataHistory.setPage("mbus");
        userDataHistory.setInfo("修改集中器，集中器号:" + (mbus.getMbusCode()));
        userDataHistoryServiceImpl.addUserDataHistory(userDataHistory);
        mbus.setUpdateUser(UserUtils.getUserId());
        mbus.setUpdateDate(new Date());
        return mbusMapper.updateByPrimaryKeySelective(mbus);
    }

   /* @Override
    public List<MbusInfo> getAllMbusByCompany(String companyId) {
        *//*List<String> companyIds = new ArrayList<>();
        if(companyId==null){
            companyIds.addAll(companyService.getCompanyIdsByPid(Constant.companyId));
        }else{
            companyIds.addAll(companyService.getCompanyIdsByPid(companyId));
        }
        List<MbusInfo> mbusList =  mbusMapper.selectAllMbusByCompany(companyIds);*//*
        //List<MbusInfo> mbusList =  mbusMapper.selectAllMbusByCompany(companyService.getCompanyIdsByCompanyId(companyId));
        List<MbusInfo> mbusList = mbusMapper.selectAllMbus(companyService.getCompanyIdsByCompanyId(companyId),null,null,null,null,null,null,null,null);
        for(MbusInfo mbusInfo:mbusList){
            if(mbusInfo.getMbusPosition().equals("1")){
                mbusInfo.setConsumerName(stationService.getStationById(mbusInfo.getConsumerId()).getStationName());
            }else if(mbusInfo.getMbusPosition().equals("2")){
                mbusInfo.setConsumerName(commuityService.getCommuityById(mbusInfo.getConsumerId()).getCommuityName());
            }else if(mbusInfo.getMbusPosition().equals("3")){
                mbusInfo.setConsumerName(buildService.getBuildById(mbusInfo.getConsumerId()).getBuildingName());
            }else if(mbusInfo.getMbusPosition().equals("4")){
                mbusInfo.setConsumerName(unitService.getUnitById(mbusInfo.getConsumerId()).getUnitName());
            }else if(mbusInfo.getMbusPosition().equals("5")){
                mbusInfo.setConsumerName(houseService.getHouseById(mbusInfo.getConsumerId()).getName());
            }
        }
        return mbusList;
    }*/

    @Override
    public List<Mbus> geDownInfoByConsumerId(String consumerId) {
        return mbusMapper.selectDownInfo(consumerId);
    }

    @Override
    public List<String> getAllIds() {
        return mbusMapper.selectAllIds();
    }

    @Override
    public String getNewId() {
        if (mbusMapper.selectAllIds().size() == 0) {
            return "1";
        } else {
            return getMaxIdNoSize(mbusMapper.selectAllIds());
        }
    }

    @Override
    public List<String> getAllCodes() {
        return mbusMapper.selectAllCodes();
    }

    @Override
    public List<String> getAllMbusCodesByConsumerIds(String consumer) {
        return null;
    }

    @Override
    public ImportResult importExcel(MultipartFile multipartFile, ServletRequest request) {

        ImportResult importResult = new ImportResult();
        List<Mbus> mbuses = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int successCount = 0;
        List<ErrorInfo> errorInfos = new ArrayList<ErrorInfo>();
        ExcelTools excel = new ExcelTools();
        Sheet sheet = excel.getFile(multipartFile, "集中器信息");
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
                    Mbus mbus = new Mbus();
                    
                    String companyName = getCellValue(row.getCell(0));
                    String commuityName = getCellValue(row.getCell(1));
                    String buidName = getCellValue(row.getCell(2));
                    String unitName = getCellValue(row.getCell(3));
                    String houseName = getCellValue(row.getCell(4));
                    String stationName = getCellValue(row.getCell(5));
                    
                    if (getCellValue(row.getCell(11)).length() == 0) {
                      ExcelTools.addErrorInfo(errorInfos, i ,11,"位置类型不能为空", true, row, style);   
                      continue;
                  } else {
                      mbus.setMbusPosition(ExcelTools.getTypeMst(typeMstList, "mbus_position" ,11 ,row, false));
                  }
                    
                    Boolean flag = houseService.getSubord(sub,companyName,commuityName, buidName,unitName,houseName,stationName,null);
                    if(!flag){
                    	sub.orderErrorInfo(errorInfos, i, row, style);
                    	continue;
                    }
                    String key = sub.orderMbusConsumerInfo(mbus.getMbusPosition(), errorInfos,  i, 11,  row, style);
                    if(!key.isEmpty()){
                    	mbus.setConsumerId(key);
                    }else{
                    	continue;
                    }
                    mbus.setCompanyId(sub.getCompany().getCompanyId());
                    
                    if (getCellValue(row.getCell(6)).length() != 0) {
                        try {
                            mbus.setInstallTime(sdf.parse(getCellValue(row.getCell(6))));
                        } catch (ParseException e) {
                        	ExcelTools.addErrorInfo(errorInfos, i ,6,"日期格式不正确", false, row, style);   
                        }
                    }
                    mbus.setInstallAddress(getCellValue(row.getCell(7)));
                    //mbus.setMbusCode(getCellValue(row.getCell(7)));
                    if (getCellValue(row.getCell(8)).length() >= 0) {
                        try {
                        	mbus.setMbusCode(getCellValue(row.getCell(8)));
                        } catch (Exception e) {
                        	ExcelTools.addErrorInfo(errorInfos, i ,8,"集中器号不能为空", true, row, style);
                            continue;
                        }
                    } else {
                    	ExcelTools.addErrorInfo(errorInfos, i ,8,"集中器号不能为空", true, row, style);
                        continue;
                    }
                    mbus.setMbusId(getCellValue(row.getCell(8)));
                    mbus.setFactory(ExcelTools.getTypeMst(typeMstList, "factory" ,9 ,row, false));
                    //mbus.setFactory(getCellValue(row.getCell(9)));
                    mbus.setEquipmentNo(ExcelTools.getTypeMst(typeMstList, "mbus_type" ,10,row, false));
                    mbus.setRunningState(getCellValue(row.getCell(12)));
                    mbus.setBusyStatus(getCellValue(row.getCell(13)));
                    mbus.setUpCommMode(ExcelTools.getTypeMst(typeMstList, "up_comm_mode" ,14 ,row, false));					//上传方式
                    mbus.setTransMode(ExcelTools.getTypeMst(typeMstList, "trans_mode" ,15 ,row, false));							//传输方式
                    mbus.setChannlMode(ExcelTools.getTypeMst(typeMstList, "channl_mode" ,16 ,row, false));					//有无虚拟
                    if (getCellValue(row.getCell(17)).length() != 0) {
                        try {
                            mbus.setOnlineTime(sdf.parse(getCellValue(row.getCell(17))));
                        } catch (ParseException e) {
                        	ExcelTools.addErrorInfo(errorInfos, i ,17,"日期格式不正确", false, row, style);
                        }
                    }
                    if (getCellValue(row.getCell(18)).length() != 0) {
                        try {
                            mbus.setOfflineTime(sdf.parse(getCellValue(row.getCell(18))));
                        } catch (ParseException e) {
                        	ExcelTools.addErrorInfo(errorInfos, i ,17,"日期格式不正确", false, row, style);
                        }
                    }
                    mbus.setDownPro(ExcelTools.getTypeMst(typeMstList, "protocol" ,19 ,row, false));
                    mbus.setMbusPro(ExcelTools.getTypeMst(typeMstList, "mbus_pro" ,20 ,row, false));										//封包协议
                    mbus.setProtocol(ExcelTools.getTypeMst(typeMstList, "protocoltype" ,21 ,row, false));									//使用协议
                    mbus.setSocket(getCellValue(row.getCell(22)));
                    mbus.setSimCard(getCellValue(row.getCell(23)));
                    mbus.setSimProvider(getCellValue(row.getCell(24)));
                    if (getCellValue(row.getCell(25)).length() != 0) {
                        try {
                            mbus.setUseStartTime(sdf.parse(getCellValue(row.getCell(25))));
                        } catch (ParseException e) {
                        	ExcelTools.addErrorInfo(errorInfos, i ,25,"日期格式不正确", false, row, style);
                        }
                    }
                    mbus.setSupperDeviceAddress(getCellValue(row.getCell(26)));
                    mbus.setLongitude(getCellValue(row.getCell(27)));
                    mbus.setLatitude(getCellValue(row.getCell(28)));
                    if(getCellValue(row.getCell(29)) != null){
                    	mbus.setAddress(getCellValue(row.getCell(29)));
                    }else{
                    	if(sub.getCommuity() != null){
                    		mbus.setAddress(sub.getCommuity().getCommuityName());
                    	}
                    }
                    mbus.setNotes(getCellValue(row.getCell(30)));
                    mbus.setMemo1(getCellValue(row.getCell(31)));
                    mbus.setMemo2(getCellValue(row.getCell(32)));
                    if (getCellValue(row.getCell(33)).length() != 0) {
                        try {
                            mbus.setSendInterval(Integer.parseInt(getCellValue(row.getCell(33))));
                        } catch (Exception e) {
                        	ExcelTools.addErrorInfo(errorInfos, i ,33,"字符串类型不能转换为整数", false, row, style);
                        }
                    }
                    mbus.setIsvalid(1);
                    mbuses.add(mbus);
                    if (errorInfo.getPosition() == null) {
                        successCount += 1;
                    }
                    //continue;
                }
            }
    }
    if(errorInfos.size()>0){
        String path = null;
        String path1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "mbus-" + UserUtils.getUserId() + "-" + ".xlsx";
        try {
            path = request.getServletContext().getRealPath("") + "/errorFile/";
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
        importResult.setId(UserUtils.getUserId() + UUID.randomUUID().toString().replace("-", "") + "mbus");
        
        if(importResult.getFailList().size()==0){
        	addBatch(mbuses);
        }
        return importResult;
    }

    @Override
    public int addBatch(List<Mbus> mbuses) {
        List<String> mbusCodes = mbusMapper.selectAllCodes();
        Map<String, String> stationCompanyMap = new HashMap<>();
        
        for (Mbus mbus : mbuses) {
            if (mbusCodes.contains(mbus.getMbusCode())) {
                if (mbus.getConsumerId() != null && !mbus.getMbusPosition().contains("1")) {
                    mbus.setCompanyId(mbus.getConsumerId().substring(0, 5));
                }else if(mbus.getConsumerId() != null && mbus.getMbusPosition().contains("1")){
                	mbus.setCompanyId(stationService.getCompanyIdbyMap(stationCompanyMap, mbus.getConsumerId()));
                }
                modifyMbus(mbus);
            } else {
                mbus.setIsvalid(1);
                mbus.setMbusId(mbus.getMbusCode());
                if(mbus.getMbusPosition().contains("1")){
                	mbus.setCompanyId(stationService.getCompanyIdbyMap(stationCompanyMap, mbus.getConsumerId()));
                }else{
                    mbus.setCompanyId(mbus.getConsumerId().substring(0, 5));
                }
                addMbus(mbus);
            }
        }
        return mbuses.size();
    }

    @Override
    public String getMbusByConsumer(String companyName, String commmuityName, String buildingName, String unitName, String houseName) {
        String consumerId = null;
        String companyId = companyService.getCompanyByName(companyName).getCompanyId();
        /*if(stationName != null&&commmuityName==null&&buildingName==null&&unitName==null&&houseName==null){
            consumerId = stationService.getStationByName(stationName).getStationId();
        }else*/
        if (commmuityName != null && buildingName == null && unitName == null && houseName == null) {
            consumerId = commuityService.getCommuityByName(commmuityName, commmuityName).getCommuityId();
        } else if (buildingName != null && unitName == null && houseName == null) {
            consumerId = buildService.getBuildByName(commmuityName, buildingName, companyId).getBuildingNo();
        } else if (unitName != null && houseName == null) {
            consumerId = unitService.getUnitByName(companyId, commmuityName, buildingName, unitName).getUnitId();
        } else if (houseName != null) {
            String commuityId = commuityService.getCommuityByName(commmuityName, companyId).getCommuityId();
            consumerId = houseService.getHouseByName(houseName, commuityId).getConsumerId();
        }
        return consumerId;
    }

    @Override
    public String exportExcel(ServletRequest request,MbusSelectInfo mbusSelectInfo) {
        String path1 = (UUID.randomUUID().toString().replace("-",""))+"-mbus.xlsx";
        // 第一步，创建一个webbook，对应一个Excel文件
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = wb.createSheet("Sheet1");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        XSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

       /* XSSFCell cell = row.createCell(0);
        cell.setCellValue("通讯设备Id");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("用途Id");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("安装时间");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("安装地址");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("集中器号");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("厂商");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("型号");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("集中器位置类型");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("运行状态");
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellValue("线路状态");
        cell.setCellStyle(style);
        cell = row.createCell(10);
        cell.setCellValue("上传方式");
        cell.setCellStyle(style);
        cell = row.createCell(11);
        cell.setCellValue("传输方式");
        cell.setCellStyle(style);
        cell = row.createCell(12);
        cell.setCellValue("有无虚拟");
        cell.setCellStyle(style);
        cell = row.createCell(13);
        cell.setCellValue("上线时间");
        cell.setCellStyle(style);
        cell = row.createCell(14);
        cell.setCellValue("下线时间");
        cell.setCellStyle(style);
        cell = row.createCell(15);
        cell.setCellValue("下挂协议");
        cell.setCellStyle(style);
        cell = row.createCell(16);
        cell.setCellValue("集中器自身封包协议");
        cell.setCellStyle(style);
        cell = row.createCell(17);
        cell.setCellValue("使用协议");
        cell.setCellStyle(style);
        cell = row.createCell(18);
        cell.setCellValue("IP");
        cell.setCellStyle(style);
        cell = row.createCell(19);
        cell.setCellValue("通讯卡号");
        cell.setCellStyle(style);
        cell = row.createCell(20);
        cell.setCellValue("通讯服务商Id");
        cell.setCellStyle(style);
        cell = row.createCell(21);
        cell.setCellValue("通讯服务器地址与端口");
        cell.setCellStyle(style);
        cell = row.createCell(22);
        cell.setCellValue("启用日期");
        cell.setCellStyle(style);
        cell = row.createCell(23);
        cell.setCellValue("上级设备地址");
        cell.setCellStyle(style);
        cell = row.createCell(24);
        cell.setCellValue("经度");
        cell.setCellStyle(style);
        cell = row.createCell(25);
        cell.setCellValue("纬度");
        cell.setCellStyle(style);
        cell = row.createCell(26);
        cell.setCellValue("公司Id");
        cell.setCellStyle(style);
        cell = row.createCell(27);
        cell.setCellValue("地址全称");
        cell.setCellStyle(style);
        cell = row.createCell(28);
        cell.setCellValue("备注");
        cell.setCellStyle(style);
        cell = row.createCell(29);
        cell.setCellValue("保留1");
        cell.setCellStyle(style);
        cell = row.createCell(30);
        cell.setCellValue("保留2");
        cell.setCellStyle(style);
        cell = row.createCell(31);
        cell.setCellValue("是否是第一次搜索");
        cell.setCellStyle(style);
        cell = row.createCell(32);
        cell.setCellValue("卡实时流量");
        cell.setCellStyle(style);
        cell = row.createCell(33);
        cell.setCellValue("指令发送间隔");
        cell.setCellStyle(style);
        cell = row.createCell(34);
        cell.setCellValue("有效标志");
        cell.setCellStyle(style);
        cell = row.createCell(35);
        cell.setCellValue("crsq");
        cell.setCellStyle(style);*/
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("集中器号");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("安装位置");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("运行状态");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("型号");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("厂商");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("位置类型");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("有无虚拟");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("下挂协议");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("安装时间");
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellValue("备注");
        cell.setCellStyle(style);
        cell = row.createCell(10);
        cell.setCellValue("线路状态");
        cell.setCellStyle(style);
        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
        List<MbusInfo> list = getAllMbus(null,null,mbusSelectInfo.getCompanyId(),mbusSelectInfo.getStationId(),mbusSelectInfo.getCommuityId(),mbusSelectInfo.getBuildingNo(),mbusSelectInfo.getUnitId(),mbusSelectInfo.getHouseId(),null,null,mbusSelectInfo.getSearchCondition()).getMbusInfoList();
        for (int i = 0; i < list.size(); i++){
            row = sheet.createRow(i + 1);
            MbusInfo mbusInfo = list.get(i);
            // 第四步，创建单元格，并设置值
            row.createCell(0).setCellValue(mbusInfo.getMbusCode());
            if(mbusInfo.getInstallAddress()!=null){
                row.createCell(1).setCellValue(mbusInfo.getInstallAddress());
            }
            if(mbusInfo.getRunningStateName()!=null){
                row.createCell(2).setCellValue(mbusInfo.getRunningStateName());
            }
            if(mbusInfo.getEquipmentNoName()!=null){
                row.createCell(3).setCellValue(mbusInfo.getEquipmentNoName());
            }
            if(mbusInfo.getFactoryName()!=null){
                row.createCell(4).setCellValue(mbusInfo.getFactoryName());
            }
            if(mbusInfo.getMbusPosition()!=null){
                if(mbusInfo.getMbusPosition().equals("1")){
                    row.createCell(5).setCellValue("站级");
                }
                if(mbusInfo.getMbusPosition().equals("2")){
                    row.createCell(5).setCellValue("小区级");
                }
                if(mbusInfo.getMbusPosition().equals("3")){
                    row.createCell(5).setCellValue("建筑物级");
                }
                if(mbusInfo.getMbusPosition().equals("4")){
                    row.createCell(5).setCellValue("单元级");
                }
                if(mbusInfo.getMbusPosition().equals("5")){
                    row.createCell(5).setCellValue("用户级");
                }
            }
            if(mbusInfo.getChannlModeName()!=null){
                row.createCell(6).setCellValue(mbusInfo.getChannlModeName());
            }
            if(mbusInfo.getDownProName()!=null){
                row.createCell(7).setCellValue(mbusInfo.getDownProName());
            }
            if(mbusInfo.getInstallTime()!=null){
                row.createCell(8).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(mbusInfo.getInstallTime()));
            }
            if(mbusInfo.getNotes()!=null){
                row.createCell(9).setCellValue(mbusInfo.getNotes());
            }
            if(mbusInfo.getBusyStatusName()!=null){
                row.createCell(10).setCellValue(mbusInfo.getBusyStatusName());
            }
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
		public void updateDownProt(Map<String, String> map) {
			
			List<Mbus> list = new ArrayList<>();
			for(String key:map.keySet()){
				Mbus mbus = new Mbus();
				mbus.setMbusCode(key);
				mbus.setDownPro(map.get(key));
				list.add(mbus);
			}
			if(list.size() == 0){
				return;
			}
			//getDownProtBy(map, list);
			
			for(Mbus bus:list){
				mbusMapper.updateDownProt(bus);
			}
		}

		private void getDownProtBy(Map<String, String> map, List<Mbus> list) {
			if(list.size() == 0){
				return;
			}
			List<Mbus> list2 = mbusMapper.selectByMbusCodes(list);
			for (Mbus mbus2:list2) {
				for (Mbus mbus1:list) {
					if(mbus2.getMbusCode().equals(mbus1.getMbusCode())){
						String prot1 = mbus1.getDownPro();
						String prot2 = mbus2.getDownPro();
						if(prot2 == null){
							break;
						}
						String[] prots2 = prot2.split(",");
						for(int i = 0 ; i<prots2.length; i++){
							if(!prot1.contains(prots2[i])){
								prot1 = prot1 + "," +prots2[i];
		    			}
						}
    				map.put(mbus1.getMbusId(), prot1);
    				break;
					}
				}
			}
		}

		@Override
		public Map<String, String> parseDownProtByMeter(List<Meter> meters) {
			Map<String, String> map = new HashMap<>();
    		for (Meter meter:meters) {
	    		String mbusId = meter.getMbusId();
	    		String meterprot = meter.getProtocol();
	    		if(map.containsKey(mbusId)){
	    			String prots = map.get(mbusId);
	    			if(!prots.contains(meterprot)){
	    				prots = prots + "," +meterprot;
	    				map.put(mbusId, prots);
	    			}
	    		}else{
	    			map.put(mbusId, meterprot);
	    		}
			}
	    	return map;
		}

    @Override
    public List<String> getMbusIdsByRoleId() {
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
		public void modifyMbusAddressbyConsumer(String commuityId, String changedAdress, String orgAddress) {
			mbusMapper.modifyMbusAddressbyConsumer(commuityId, Tools.nkey, changedAdress, orgAddress);
		}

		@Override
		public void modifyMbusAddress(String consumerId, String address) {
			mbusMapper.modifyMbusAddress(consumerId, address);
		}

    @Override
    public List<MbusInfo> getMbusControls(List<String> mbusCodes, String consumerId, String mbusControl, String mbusControlParam) {
        List<MbusInfo> mbusInfoList = new ArrayList<>();
        String type = "house";
        if(consumerId!=null){
            if(consumerId.length()==5){
            	 type = "company";
                mbusInfoList.addAll(getAllMbus(null,null,consumerId,null,null,null,null,null,null,null,null).getMbusInfoList());
            }
            if(consumerId.length()==6){
            	 type = "station";
                mbusInfoList.addAll(getAllMbus(null,null,null,consumerId,null,null,null,null,null,null,null).getMbusInfoList());
            }
            if(consumerId.length()==10){
            	 type = "commuity";
                mbusInfoList.addAll(getAllMbus(null,null,null,null,consumerId,null,null,null,null,null,null).getMbusInfoList());
            }
            if(consumerId.length()==13){
            	type = "building";
                mbusInfoList.addAll(getAllMbus(null,null,null,null,null,consumerId,null,null,null,null,null).getMbusInfoList());
            }
            if(consumerId.length()==15){
            	type = "unit";
                mbusInfoList.addAll(getAllMbus(null,null,null,null,null,null,consumerId,null,null,null,null).getMbusInfoList());
            }
            if(consumerId.length()==18){
            	type = "house";
                mbusInfoList.addAll(getAllMbus(null,null,null,null,null,null,null,consumerId,null,null,null).getMbusInfoList());
            }
        }
        //使用户用阀门的接口，id，location，type（company，station，commuity，building，unit，house），
     	 // valveControl，meterControl，para1，companyId
        Map<String, Object> maps = new HashMap<>();
        maps.put("id", mbusCodes);
        maps.put("location", consumerId);
        maps.put("valveControl", mbusControl);
        maps.put("para1", mbusControlParam);
        maps.put("type", type);
        houseControlService.houseControl(maps);
        return mbusInfoList;
    }
}
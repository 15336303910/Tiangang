package cn.plou.web.system.baseMessage.house.service.impl;

import static cn.plou.web.common.importDataBatch.CleanOnTime.cleanTempFile;
import static cn.plou.web.common.utils.ExcelTools.getCellValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

import cn.plou.web.common.utils.*;
import cn.plou.web.system.baseMessage.commuity.dao.CommuityMapper;
import cn.plou.web.system.baseMessage.house.dao.PriceDefineUseByHouseMapper;
import cn.plou.web.system.baseMessage.house.entity.PriceDefineUseInHouse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.plou.web.charge.chargeconfig.entity.PriceDefine;
import cn.plou.web.charge.chargeconfig.service.PriceDefineService;

/*import cn.plou.web.charge.heatingmanage.dto.HeatingServeUserSearchDTO;
import cn.plou.web.charge.heatingmanage.vo.HeatingServeUserListVO;*/

import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.idgenerater.service.IDGenerater;
import cn.plou.web.common.importDataBatch.ErrorInfo;
import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.system.baseMessage.build.entity.Build;
import cn.plou.web.system.baseMessage.build.service.BuildService;
import cn.plou.web.system.baseMessage.build.vo.BuildInfo;
import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import cn.plou.web.system.baseMessage.commuity.service.CommuityService;
import cn.plou.web.system.baseMessage.company.dao.CompanyMapper;
import cn.plou.web.system.baseMessage.company.entity.Company;
import cn.plou.web.system.baseMessage.company.service.CompanyService;
import cn.plou.web.system.baseMessage.company.service.impl.CompanyServiceImpl;
import cn.plou.web.system.baseMessage.house.dao.HouseMapper;
import cn.plou.web.system.baseMessage.house.entity.House;
import cn.plou.web.system.baseMessage.house.entity.Subord;
import cn.plou.web.system.baseMessage.house.service.HouseService;
import cn.plou.web.system.baseMessage.house.vo.HouseInfo;
import cn.plou.web.system.baseMessage.house.vo.HouseSelectInfo;
import cn.plou.web.system.baseMessage.house.vo.HouseVo;
import cn.plou.web.system.baseMessage.house.vo.StructureInfo;
import cn.plou.web.system.baseMessage.station.entity.Station;
import cn.plou.web.system.baseMessage.station.service.StationService;
import cn.plou.web.system.baseMessage.system.service.SystemService;
import cn.plou.web.system.baseMessage.system.vo.SystemInfo;
import cn.plou.web.system.baseMessage.unit.entity.Unit;
import cn.plou.web.system.baseMessage.unit.service.UnitService;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.impl.TypeMstServiceImpl;
import cn.plou.web.system.meterMessage.mbus.service.MbusService;
import cn.plou.web.system.meterMessage.meter.service.MeterService;
import cn.plou.web.system.meterMessage.meter.service.impl.MeterServiceImpl;
import cn.plou.web.system.meterMessage.meter.vo.MeterInfo;
import cn.plou.web.system.permission.role.service.DataRoleService;
import cn.plou.web.system.permission.userDataHistory.entity.UserDataHistory;
import cn.plou.web.system.permission.userDataHistory.service.impl.UserDataHistoryServiceImpl;
import cn.plou.web.system.permission.userPageHistory.entity.UserPageHistory;
import cn.plou.web.system.permission.userPageHistory.service.impl.UserPageHistoryServiceImpl;

@Component
public class HouseServiceImpl implements HouseService {

    private Map<String, Integer> unitIdMap = new ConcurrentHashMap<>();    //单元ID，户ID最大值
    @Autowired
    private HouseMapper houseMapper;
    @Autowired
    private MeterServiceImpl meterServiceImpl;
    @Autowired
    private CommuityService commuityService;
    @Autowired
    private CompanyServiceImpl companyServiceImpl;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private StationService stationService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private BuildService buildService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private TypeMstServiceImpl typeMstServiceImpl;
    @Autowired
    private UserPageHistoryServiceImpl userPageHistoryServiceImpl;
    @Autowired
    private UserDataHistoryServiceImpl userDataHistoryServiceImpl;
    @Autowired
    private IDGenerater idGenerater;
    @Autowired
    private DataRoleService dataRoleService;
    @Autowired
    private PriceDefineService priceDefineService;
    @Autowired
    private PriceDefineUseByHouseMapper priceDefineUseByHouseMapper;
    @Autowired
    private MeterService meterService;
    @Autowired
    private MbusService mbusService;
    @Autowired
    private CommonServiceImp commonServiceImp;
    
    @Resource
    CommuityMapper commuityMapper;

    @Override
    public Integer getMaxHouseId(String unitId) {
        Integer index = 0;
        String id = houseMapper.selectMaxHouseId(unitId);
        if (id != null && !id.isEmpty()) {
            index = Integer.valueOf(id.substring(15, 19));
        }
        return index;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addHouse(HouseInfo houseInfo) {
        String id = idGenerater.generateHouseId(houseInfo.getUnitId(), 1, true).get(0);
        houseInfo.setIsvalid(Constant.ISVALID);
        houseInfo.setConsumerId(id);
        houseInfo.setRowno(id);
        //houseInfo.setRowno(UUID.randomUUID().toString().replace("-", ""));
        houseInfo.setCompanyId(houseInfo.getUnitId().substring(0, 5));
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("house");
        userPageHistory.setAct("addHouse");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        houseInfo.setCreateUser(UserUtils.getUserId());
        houseInfo.setCreateDate(new Date());
        return houseMapper.insertSelective(houseInfo);
    }

    @Override
    public House getHouseById(String consumerId) {
        return houseMapper.selectByPrimaryKey(consumerId);
    }

    @Override
    public List<House> getHouseByIds(List<String> consumerIds) {
        return houseMapper.getHouseByIds(consumerIds);
    }

    @Override
    public List<House> getHouseByChargeIdOrTelOrIdcardOrCompanyId(String chargeId, String tel, String idcard, String companyId) {
        return houseMapper.getHouseByChargeIdOrTelOrIdcardOrCompanyId(chargeId, tel, idcard, companyId);
    }

    @Override
    public PageInfo<House> getHouseBySearchTextOrId(String searchText, String id, String order, String sortby, Integer page, Integer pageSize) {
        List<House> houseList = new ArrayList<>();
        PageHelper.startPage(page, pageSize);
        if (id.length() == 6) {//说明是站的id
            //通过站id查出所有的小区
            List<Commuity> commuityList = commuityMapper.selectTreeList(id,null);
            if (commuityList != null && commuityList.size() > 0) {
                List<String> ids = new ArrayList<>();
                for (Commuity commuity : commuityList) {
                    ids.add(commuity.getCommuityId());
                }
                houseList = houseMapper.findHouseListByCommuitys(searchText, ids, order, sortby);
            }
        } else {
            houseList = houseMapper.getHouseBySearchTextOrId(searchText, id, order, sortby, page, pageSize);
        }

        return new PageInfo<>(houseList);
    }

    @Override
    public HouseInfo selectHouseInfoById(String consumerId) {
        return houseMapper.selectHouseInfoById(consumerId);
    }

    @Override
    public int addBatch(List<HouseInfo> houseInfoList) {
        int count = houseInfoList.size();
        //Boolean b = false;
        for(HouseInfo houseInfo:houseInfoList){
            houseInfo.setCreateUser(UserUtils.getUserId());
            houseInfo.setCreateDate(new Date());
        }
        houseMapper.batchInsertOrUpdate(houseInfoList);
//        for (HouseInfo houseInfo : houseInfoList) {
//            List<House> houseList = getHouseByUnitId(houseInfo.getUnitId());
//            for (House house : houseList) {
//                if (houseInfo.getRoomName().equals(house.getRoomName())) {
//                    b = true;
//                    break;
//                }
//            }
//            if (b) {
//                modifyHouse(houseInfo);
//                count++;
//            } else {
//                addHouse(houseInfo);
//                count++;
//            }
//        }
        return count;
    }

    @Override
    public String exportExcel(HouseSelectInfo houseSelectInfo, ServletRequest request) {
        /*String path = (UUID.randomUUID().toString().replace("-", "")) + "house.xlsx";
        String filePath = request.getServletContext().getRealPath("/") + path;//文件路径
        java.lang.System.out.println("路径：" + filePath);
        XSSFWorkbook workbook = new XSSFWorkbook();//创建Excel文件(Workbook)
        XSSFSheet sheet = workbook.createSheet("用户信息");//创建工作表(Sheet)
        XSSFRow row = sheet.createRow(0);// 创建行,从0开始
        XSSFCell cell = row.createCell(0);// 创建行的单元格,也是从0开始
        *//*cell.setCellValue("公司");// 设置单元格内容
        row.createCell(1).setCellValue("小区");// 设置单元格内容,重载
        row.createCell(2).setCellValue("楼");
        row.createCell(3).setCellValue("单元");
        row.createCell(4).setCellValue("热力站分系统");
        row.createCell(5).setCellValue("室名");
        row.createCell(6).setCellValue("建筑面积");
        row.createCell(7).setCellValue("套内建筑面积");
        row.createCell(8).setCellValue("实用面积");
        row.createCell(9).setCellValue("供热面积");
        row.createCell(10).setCellValue("供热收费类型");
        row.createCell(11).setCellValue("热费单价类型");
        row.createCell(12).setCellValue("供热用户类型");
        row.createCell(13).setCellValue("收费面积");
        row.createCell(14).setCellValue("用户姓名");
        row.createCell(15).setCellValue("邮箱");
        row.createCell(16).setCellValue("单位名称");
        row.createCell(17).setCellValue("联系电话");
        row.createCell(18).setCellValue("室内暖气");
        row.createCell(19).setCellValue("入网状态");
        row.createCell(20).setCellValue("换热器数量");
        row.createCell(21).setCellValue("流量样本");
        row.createCell(22).setCellValue("采暖户型");
        row.createCell(23).setCellValue("用户管径");
        row.createCell(24).setCellValue("热量表安装");
        row.createCell(25).setCellValue("温控阀安装");
        row.createCell(26).setCellValue("房屋结构");
        row.createCell(27).setCellValue("收费ID");
        row.createCell(28).setCellValue("二次管线ID");
        row.createCell(29).setCellValue("地址全称");
        row.createCell(30).setCellValue("设计热指标");
        row.createCell(31).setCellValue("备注");
        row.createCell(32).setCellValue("保留1");
        row.createCell(33).setCellValue("保留2");
        row.createCell(34).setCellValue("保留3");
        row.createCell(35).setCellValue("保留4");
        row.createCell(36).setCellValue("保留5");
        row.createCell(37).setCellValue("层高");
        row.createCell(38).setCellValue("身份证信息");
        row.createCell(39).setCellValue("登陆密码");
        row.createCell(40).setCellValue("人口数");
        row.createCell(41).setCellValue("客户类型");
        row.createCell(42).setCellValue("集收号");
        row.createCell(43).setCellValue("统一社会信用代码");
        row.createCell(44).setCellValue("低保姓名");
        row.createCell(45).setCellValue("低保证号");
        row.createCell(46).setCellValue("房屋方向");
        row.createCell(47).setCellValue("用户密码");
        row.createCell(48).setCellValue("维修负责人");
        row.createCell(49).setCellValue("有效标志");
        row.createCell(50).setCellValue("供暖状态");
        row.createCell(51).setCellValue("控制方式");
        row.createCell(52).setCellValue("显示级别");*//*
        cell.setCellValue("地址全称");// 设置单元格内容
        row.createCell(1).setCellValue("单元名称");
        row.createCell(2).setCellValue("室名");
        row.createCell(3).setCellValue("用户姓名");
        row.createCell(4).setCellValue("系统名称");
        row.createCell(5).setCellValue("室内暖气");
        row.createCell(6).setCellValue("户位置");
        row.createCell(7).setCellValue("热力站名称");
        row.createCell(8).setCellValue("采暖户型");
        row.createCell(9).setCellValue("用户管径");
        row.createCell(10).setCellValue("房屋结构");
        row.createCell(11).setCellValue("身份证号");
        row.createCell(12).setCellValue("热量表安装");
        row.createCell(13).setCellValue("层高");
        row.createCell(14).setCellValue("温控阀安装");
        row.createCell(15).setCellValue("联系电话");
        row.createCell(16).setCellValue("电子邮箱");
        row.createCell(17).setCellValue("实用面积");
        row.createCell(18).setCellValue("公司名称");
        row.createCell(19).setCellValue("入网状态");
        row.createCell(20).setCellValue("工作单位");
        row.createCell(21).setCellValue("套内建筑面积");
        row.createCell(22).setCellValue("备注");*/
        List<HouseInfo> houseInfoList = getAllHouse(houseSelectInfo.getCompanyId(), houseSelectInfo.getStationId(), houseSelectInfo.getCommuityId(), houseSelectInfo.getBuildingNo(), houseSelectInfo.getUnitId(),
                houseSelectInfo.getSearchCondition(), houseSelectInfo.getOrder(), houseSelectInfo.getSortby(), null, null).getList();
        return commonServiceImp.getHisDataToExcel(request,"house","用户信息",houseInfoList);
        /*for (int i = 1; i <= houseInfoList.size(); i++) {
            row = sheet.createRow(i);// 创建行,从0开始
            cell = row.createCell(0);// 创建行的单元格,也是从0开始
            cell.setCellValue(houseInfoList.get(i - 1).getAddress() == null ? "" : houseInfoList.get(i - 1).getAddress());// 设置单元格内容
            row.createCell(1).setCellValue(houseInfoList.get(i - 1).getUnitName() == null ? "" : houseInfoList.get(i - 1).getUnitName());
            row.createCell(2).setCellValue(houseInfoList.get(i - 1).getRoomName() == null ? "" : houseInfoList.get(i - 1).getRoomName());
            row.createCell(3).setCellValue(houseInfoList.get(i - 1).getName() == null ? "" : houseInfoList.get(i - 1).getName());
            row.createCell(4).setCellValue(houseInfoList.get(i - 1).getSystemName() == null ? "" : houseInfoList.get(i - 1).getSystemName());
            row.createCell(5).setCellValue(houseInfoList.get(i - 1).getHeatingFormName() == null ? "" : houseInfoList.get(i - 1).getHeatingFormName());
            row.createCell(6).setCellValue(houseInfoList.get(i - 1).getHouseLocaltypeName() == null ? "" : houseInfoList.get(i - 1).getHouseLocaltypeName());
            row.createCell(7).setCellValue(houseInfoList.get(i - 1).getStationName() == null ? "" : houseInfoList.get(i - 1).getSystemName());
            row.createCell(8).setCellValue(houseInfoList.get(i - 1).getHouseTypeName() == null ? "" : houseInfoList.get(i - 1).getHouseTypeName());
            row.createCell(9).setCellValue(houseInfoList.get(i - 1).getDiameterName() == null ? "" : houseInfoList.get(i - 1).getDiameterName());
            row.createCell(10).setCellValue(houseInfoList.get(i - 1).getHouseStructureId() == null ? "" : houseInfoList.get(i - 1).getHouseStructureId());
            row.createCell(11).setCellValue(houseInfoList.get(i - 1).getIdcard() == null ? "" : houseInfoList.get(i - 1).getIdcard());
            row.createCell(12).setCellValue(houseInfoList.get(i - 1).getHasHeatMeterName() == null ? "" : houseInfoList.get(i - 1).getHasHeatMeterName());
            if (houseInfoList.get(i - 1).getFloorHigh() == null) {
                row.createCell(13).setCellValue("");
            } else {
                row.createCell(13).setCellValue(String.valueOf(houseInfoList.get(i - 1).getFloorHigh()));
            }
            row.createCell(14).setCellValue(houseInfoList.get(i - 1).getHasValveName() == null ? "" : houseInfoList.get(i - 1).getHasValveName());
            row.createCell(15).setCellValue(houseInfoList.get(i - 1).getTel() == null ? "" : houseInfoList.get(i - 1).getTel());
            row.createCell(16).setCellValue(houseInfoList.get(i - 1).getEmail() == null ? "" : houseInfoList.get(i - 1).getEmail());
            if (houseInfoList.get(i - 1).getUserBuildArea() == null) {
                row.createCell(17).setCellValue("");
            } else {
                row.createCell(17).setCellValue(String.valueOf(houseInfoList.get(i - 1).getUserBuildArea()));
            }
            row.createCell(18).setCellValue(houseInfoList.get(i - 1).getUserCompany() == null ? "" : houseInfoList.get(i - 1).getUserCompany());
            row.createCell(19).setCellValue(houseInfoList.get(i - 1).getNetStatusName() == null ? "" : houseInfoList.get(i - 1).getNetStatusName());
            row.createCell(20).setCellValue(houseInfoList.get(i - 1).getUserCompany() == null ? "" : houseInfoList.get(i - 1).getUserCompany());
            if (houseInfoList.get(i - 1).getInBuildArea() == null) {
                row.createCell(21).setCellValue("");
            } else {
                row.createCell(21).setCellValue(String.valueOf(houseInfoList.get(i - 1).getInBuildArea()));
            }
            row.createCell(22).setCellValue(houseInfoList.get(i - 1).getNotes() == null ? "" : houseInfoList.get(i - 1).getNotes());
            *//*cell.setCellValue(houseInfoList.get(i - 1).getCompanyName() == null ? "" : houseInfoList.get(i - 1).getCompanyName());// 设置单元格内容
            row.createCell(1).setCellValue("");// 设置单元格内容,重载
            row.createCell(2).setCellValue(houseInfoList.get(i - 1).getBuildingName() == null ? "" : houseInfoList.get(i - 1).getBuildingName());
            row.createCell(3).setCellValue(houseInfoList.get(i - 1).getUnitName() == null ? "" : houseInfoList.get(i - 1).getUnitName());
            row.createCell(4).setCellValue(houseInfoList.get(i - 1).getSystemName() == null ? "" : houseInfoList.get(i - 1).getSystemName());
            row.createCell(5).setCellValue(houseInfoList.get(i - 1).getRoomName() == null ? "" : houseInfoList.get(i - 1).getRoomName());
            if (houseInfoList.get(i - 1).getBuildingArea() == null) {
                row.createCell(6).setCellValue("");
            } else {
                //row.createCell(6).setCellValue(String.valueOf(houseInfoList.get(i - 1).getBuildingArea()));
                row.createCell(6).setCellValue(String.valueOf(houseInfoList.get(i-1).getBuildingArea()));
            }
            if (houseInfoList.get(i - 1).getInBuildArea() == null) {
                row.createCell(7).setCellValue("");
            } else {
                row.createCell(7).setCellValue(String.valueOf(houseInfoList.get(i - 1).getInBuildArea()));
            }
            if (houseInfoList.get(i - 1).getUserBuildArea() == null) {
                row.createCell(8).setCellValue("");
            } else {
                row.createCell(8).setCellValue(String.valueOf(houseInfoList.get(i - 1).getUserBuildArea()));
            }
            if (houseInfoList.get(i - 1).getHeatingArea() == null) {
                row.createCell(9).setCellValue("");
            } else {
                row.createCell(9).setCellValue(String.valueOf(houseInfoList.get(i - 1).getHeatingArea()));
            }
            row.createCell(10).setCellValue(houseInfoList.get(i - 1).getChargeTypeName() == null ? "" : houseInfoList.get(i - 1).getChargeTypeName());
            row.createCell(11).setCellValue(houseInfoList.get(i - 1).getUnitPriceType() == null ? "" : houseInfoList.get(i - 1).getUnitPriceType());
            row.createCell(12).setCellValue(houseInfoList.get(i - 1).getHeatUserTypeName() == null ? "" : houseInfoList.get(i - 1).getHeatUserTypeName());
            if (houseInfoList.get(i - 1).getChargeArea() == null) {
                row.createCell(13).setCellValue("");
            } else {
                row.createCell(13).setCellValue(String.valueOf(houseInfoList.get(i - 1).getChargeArea()));
            }
            row.createCell(14).setCellValue(houseInfoList.get(i - 1).getName() == null ? "" : houseInfoList.get(i - 1).getName());
            row.createCell(15).setCellValue(houseInfoList.get(i - 1).getEmail() == null ? "" : houseInfoList.get(i - 1).getEmail());
            row.createCell(16).setCellValue(houseInfoList.get(i - 1).getUserCompany() == null ? "" : houseInfoList.get(i - 1).getUserCompany());
            row.createCell(17).setCellValue(houseInfoList.get(i - 1).getTel() == null ? "" : houseInfoList.get(i - 1).getTel());
            row.createCell(18).setCellValue(houseInfoList.get(i - 1).getHeatingFormName() == null ? "" : houseInfoList.get(i - 1).getHeatingFormName());
            row.createCell(19).setCellValue(houseInfoList.get(i - 1).getNetStatusName() == null ? "" : houseInfoList.get(i - 1).getNetStatusName());
            row.createCell(20).setCellValue(houseInfoList.get(i - 1).getHeatExchangeStatusName() == null ? "" : houseInfoList.get(i - 1).getHeatExchangeStatusName());
            row.createCell(21).setCellValue(houseInfoList.get(i - 1).getIssample() == null ? "" : houseInfoList.get(i - 1).getIssample());
            row.createCell(22).setCellValue(houseInfoList.get(i - 1).getHouseTypeName() == null ? "" : houseInfoList.get(i - 1).getHouseTypeName());
            row.createCell(23).setCellValue(houseInfoList.get(i - 1).getDiameter() == null ? "" : houseInfoList.get(i - 1).getDiameter());
            row.createCell(24).setCellValue(houseInfoList.get(i - 1).getHasHeatMeterName() == null ? "" : houseInfoList.get(i - 1).getHasHeatMeterName());
            row.createCell(25).setCellValue(houseInfoList.get(i - 1).getHasValveName() == null ? "" : houseInfoList.get(i - 1).getHasValveName());
            row.createCell(26).setCellValue(houseInfoList.get(i - 1).getHouseStructureId() == null ? "" : houseInfoList.get(i - 1).getHouseStructureId());
            row.createCell(27).setCellValue(houseInfoList.get(i - 1).getChargeId() == null ? "" : houseInfoList.get(i - 1).getChargeId());
            row.createCell(28).setCellValue(houseInfoList.get(i - 1).getSecPipeId() == null ? "" : houseInfoList.get(i - 1).getSecPipeId());
            row.createCell(29).setCellValue(houseInfoList.get(i - 1).getAddress() == null ? "" : houseInfoList.get(i - 1).getAddress());
            row.createCell(30).setCellValue(houseInfoList.get(i - 1).getDesignHeatTarget() == null ? "" : houseInfoList.get(i - 1).getDesignHeatTarget().toString());
            row.createCell(31).setCellValue(houseInfoList.get(i - 1).getNotes() == null ? "" : houseInfoList.get(i - 1).getNotes());
            row.createCell(32).setCellValue(houseInfoList.get(i - 1).getMemo1() == null ? "" : houseInfoList.get(i - 1).getMemo1());
            row.createCell(33).setCellValue(houseInfoList.get(i - 1).getMemo2() == null ? "" : houseInfoList.get(i - 1).getMemo2());
            row.createCell(34).setCellValue(houseInfoList.get(i - 1).getMemo3() == null ? "" : houseInfoList.get(i - 1).getMemo3());
            row.createCell(35).setCellValue(houseInfoList.get(i - 1).getMemo4() == null ? "" : houseInfoList.get(i - 1).getMemo4());
            row.createCell(36).setCellValue(houseInfoList.get(i - 1).getMemo5() == null ? "" : houseInfoList.get(i - 1).getMemo5());
            row.createCell(37).setCellValue(houseInfoList.get(i - 1).getFloorHigh() == null ? "" : houseInfoList.get(i - 1).getFloorHigh());
            row.createCell(38).setCellValue(houseInfoList.get(i - 1).getIdcard() == null ? "" : houseInfoList.get(i - 1).getIdcard());
            row.createCell(39).setCellValue(houseInfoList.get(i - 1).getPasswod() == null ? "" : houseInfoList.get(i - 1).getPasswod());
            if (houseInfoList.get(i - 1).getPersonSum() == null) {
                row.createCell(40).setCellValue("");
            } else {
                row.createCell(40).setCellValue(String.valueOf(houseInfoList.get(i - 1).getPersonSum()));
            }
            row.createCell(41).setCellValue(houseInfoList.get(i - 1).getPersonType() == null ? "" : houseInfoList.get(i - 1).getPersonType());
            row.createCell(42).setCellValue(houseInfoList.get(i - 1).getColorNo() == null ? "" : houseInfoList.get(i - 1).getColorNo());
            row.createCell(43).setCellValue(houseInfoList.get(i - 1).getPersoncardId() == null ? "" : houseInfoList.get(i - 1).getPersoncardId());
            row.createCell(44).setCellValue(houseInfoList.get(i - 1).getLowinsureName() == null ? "" : houseInfoList.get(i - 1).getLowinsureName());
            row.createCell(45).setCellValue(houseInfoList.get(i - 1).getLowinsureNo() == null ? "" : houseInfoList.get(i - 1).getLowinsureNo());
            row.createCell(46).setCellValue(houseInfoList.get(i - 1).getHouseDirectName() == null ? "" : houseInfoList.get(i - 1).getHouseDirectName());
            row.createCell(47).setCellValue(houseInfoList.get(i - 1).getHouseKey() == null ? "" : houseInfoList.get(i - 1).getHouseKey());
            row.createCell(48).setCellValue(houseInfoList.get(i - 1).getServiceman() == null ? "" : houseInfoList.get(i - 1).getServiceman());
            if (houseInfoList.get(i - 1).getIsvalid() == null) {
                row.createCell(49).setCellValue("");
            } else {
                row.createCell(49).setCellValue(houseInfoList.get(i - 1).getIsvalid());
            }
            row.createCell(50).setCellValue(houseInfoList.get(i - 1).getHeatingStatusName() == null ? "" : houseInfoList.get(i - 1).getHeatingStatusName());
            row.createCell(51).setCellValue(houseInfoList.get(i - 1).getControlTypeName() == null ? "" : houseInfoList.get(i - 1).getControlTypeName());
            row.createCell(52).setCellValue(houseInfoList.get(i - 1).getHouseLocaltypeName() == null ? "" : houseInfoList.get(i - 1).getHouseLocaltypeName());*//*
        }
        try {
            FileOutputStream out = new FileOutputStream(filePath);
            workbook.write(out);//保存Excel文件
            out.close();//关闭文件流
            java.lang.System.out.println("OK!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;*/
    }

    @Override
    public Map<String, House> getHouseByUnitIds(List<Unit> units) {

        String housesIds = "";
        for (Unit unit : units) {
            housesIds += "'" + unit.getUnitId() + "',";
        }
        housesIds = housesIds.substring(0, housesIds.length() - 1);
        List<House> lHouse = houseMapper.selectHouseByUnitIds(housesIds);
        Map<String, House> maph = new HashMap<>();
        for (House hous : lHouse) {
            maph.put(hous.getConsumerId(), hous);
        }
        return maph;
    }

    @Override
    public List<House> getHouseByUnitId(String unitId) {
        return houseMapper.selectHouseByUnitId(unitId);
    }

    /*  @Override
      public List<HeatingServeUserListVO> selectByRangeId(HeatingServeUserSearchDTO heatingServeUserSearchDTO) {
          return houseMapper.selectByRangeId(heatingServeUserSearchDTO);
      }

      @Override
      public Integer selectCountByRangeId(HeatingServeUserSearchDTO heatingServeUserSearchDTO) {
          return houseMapper.selectCountByRangeId(heatingServeUserSearchDTO);
      }*/
    @Override
    public Integer selectCountByNetStatus(String companyId, String netStatus) {
        return houseMapper.selectCountByNetStatus(companyId, netStatus);
    }


    @Override
    public Integer selectCountNetStatusNull(String companyId) {
        return houseMapper.selectCountNetStatusNull(companyId);
    }




    @Override
    public Integer selectCountByNetStatusForCommunityIds(List<String> communityIds, String netStatus) {
        return houseMapper.selectCountByNetStatusForCommunityIds(communityIds, netStatus);
    }


    @Override
    public Integer selectCountNetStatusNullForCommunityIds(List<String> communityIds) {
        return houseMapper.selectCountNetStatusNullForCommunityIds(communityIds);
    }

    @Override
    public Integer selectCountByNetStatusForConsumerIds(List<String> consumerIds, String netStatus) {
        return houseMapper.selectCountByNetStatusForConsumerIds(consumerIds, netStatus);
    }

    @Override
    public Integer selectCountNetStatusNullForConsumerIds(List<String> consumerIds) {
        return houseMapper.selectCountNetStatusNullForConsumerIds(consumerIds);
    }

    @Override
    public Integer selectCountByNetStatusForStationIds(List<String> stationIds, String netStatus) {
        return houseMapper.selectCountByNetStatusForStationIds(stationIds, netStatus);
    }

    @Override
    public Integer selectCountNetStatusNullForStationIds(List<String> stationIds) {
        return houseMapper.selectCountNetStatusNullForStationIds(stationIds);
    }



    @Override
    public Integer selectCountHeatingStatusNull(String companyId) {
        return houseMapper.selectCountHeatingStatusNull(companyId);
    }


    @Override
    public Integer selectCountHeatingStatusNullForCommunityIds(List<String> communityIds) {
        return houseMapper.selectCountHeatingStatusNullForCommunityIds(communityIds);
    }




    @Override
    public Integer selectCountHeatingStatusNullForConsumerIds(List<String> consumerIds) {
        return houseMapper.selectCountHeatingStatusNullForConsumerIds(consumerIds);
    }



    @Override
    public Integer selectCountHeatingStatusNullForStationIds(List<String> stationIds) {
        return houseMapper.selectCountHeatingStatusNullForStationIds(stationIds);
    }


    @Override
    public Integer selectCountPriceDefineNull(String companyId) {
        return houseMapper.selectCountPriceDefineNull(companyId);
    }


    @Override
    public Integer selectCountPriceDefineNullForCommunityIds(List<String> communityIds) {
        return houseMapper.selectCountPriceDefineNullForCommunityIds(communityIds);
    }




    @Override
    public Integer selectCountPriceDefineNullForConsumerIds(List<String> consumerIds) {
        return houseMapper.selectCountPriceDefineNullForConsumerIds(consumerIds);
    }



    @Override
    public Integer selectCountPriceDefineNullForStationIds(List<String> stationIds) {
        return houseMapper.selectCountPriceDefineNullForStationIds(stationIds);
    }



    @Override
    public Integer selectCountChargeTypeNull(String companyId) {
        return houseMapper.selectCountChargeTypeNull(companyId);
    }


    @Override
    public Integer selectCountChargeTypeNullForCommunityIds(List<String> communityIds) {
        return houseMapper.selectCountChargeTypeNullForCommunityIds(communityIds);
    }




    @Override
    public Integer selectCountChargeTypeNullForConsumerIds(List<String> consumerIds) {
        return houseMapper.selectCountChargeTypeNullForConsumerIds(consumerIds);
    }



    @Override
    public Integer selectCountChargeTypeNullForStationIds(List<String> stationIds) {
        return houseMapper.selectCountChargeTypeNullForStationIds(stationIds);
    }





    @Override
    public Integer selectCountByHeatingStatus(String companyId, String heatingStatus) {
        return houseMapper.selectCountByHeatingStatus(companyId, heatingStatus);
    }



    @Override
    public Integer selectCountNotHeating(String companyId) {
        return houseMapper.selectCountNotHeating(companyId);
    }


    @Override
    public Integer selectCountByChargeType(String companyId, String chargeType) {
        return houseMapper.selectCountByChargeType(companyId, chargeType);
    }

    @Override
    public Integer getAllCount(String companyId) {
        return houseMapper.getAllCount(companyId);
    }


    @Override
    public Integer getCountByCommunityIds(List<String> communityIds) {
        return houseMapper.getCountByCommunityIds(communityIds);
    }


    @Override
    public Integer getCountByStationIds(List<String> stationIdls) {
        return houseMapper.getCountByStationIds(stationIdls);
    }

    @Override
    public Integer getCountByConsumerIds(List<String> consumerIds) {
        return houseMapper.getCountByConsumerIds(consumerIds);
    }



    @Override
    public List<PriceDefine> selectPriceDefineByCompanyId(String companyId) {
        return houseMapper.selectPriceDefineByCompanyId(companyId);
    }



    @Override
    public List<PriceDefine> selectPriceDefineByCommunityIds(List<String> communityIds) {
        return houseMapper.selectPriceDefineByCommunityIds(communityIds);
    }

    @Override
    public List<PriceDefine> selectPriceDefineByConsumerIds(List<String> consumerIds) {
        return houseMapper.selectPriceDefineByConsumerIds(consumerIds);
    }

    @Override
    public List<PriceDefine> selectPriceDefineByStationIds(List<String> stationIds) {
        return houseMapper.selectPriceDefineByStationIds(stationIds);
    }

    @Override
    public List<PriceDefineUseInHouse> getAllByYearAndCompany(String companyId) {
        return priceDefineUseByHouseMapper.selectByYearAndCompany(companyId);
    }


    @Override
    public StructureInfo getStructureById(String id) {
        StructureInfo structureInfo = new StructureInfo();
        if (id.length() >= 5) {
            String companyId = "";
            if (id.length() == 6) {
                companyId = stationService.getStationById(id).getCompanyId();
            } else {
                companyId = id.substring(0, 5);
            }
            structureInfo.setCompanyId(companyId);
            List<Company> companyList = companyMapper.selectChildrenCompany(companyId);
            companyList.add(companyServiceImpl.get(companyId));
            structureInfo.setCompanyList(companyList);
            if (id.length() >= 6) {
                String stationId;
                if (id.length() == 6) {
                    stationId = id;
                } else {
                    stationId = commuityService.getCommuityById(id.substring(0, 10)).getStationId();
                }
                structureInfo.setStationId(stationId);
                structureInfo.setStationList(stationService.getStationDownInfo(companyId));
                if (id.length() >= 10) {
                    String commuityId = id.substring(0, 10);
                    structureInfo.setCommuityId(commuityId);
                    structureInfo.setCommuityList(commuityService.getTreeList(stationId));
                    if (id.length() >= 13) {
                        String buildingNo = id.substring(0, 13);
                        structureInfo.setBuildingNo(buildingNo);
                        structureInfo.setBuildList(buildService.getBuildTree(commuityId));
                        if (id.length() >= 15) {
                            String unitId = id.substring(0, 15);
                            structureInfo.setUnitId(unitId);
                            structureInfo.setUnitList(unitService.getUnitTree(buildingNo));
                            if (id.length() == 19) {
                                structureInfo.setHouseList(getHouseByUnitId(unitId));
                            }
                        }
                    }
                }
            }
        }
        return structureInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyHouseBatch(HouseVo houseVo) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("house");
        userPageHistory.setAct("modifyHouseBatch");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        houseVo.setUpdateUser(UserUtils.getUserId());
        houseVo.setUpdateDate(new Date());
        if (houseVo.getBatchCheckbox()) {
            UserDataHistory userDataHistory = new UserDataHistory();
            userDataHistory.setConsumerId(houseVo.getBatchModifyId());
            userDataHistory.setPage("house");
            userDataHistory.setInfo("批量修改住户，ID:" + (houseVo.getBatchModifyId()));
            userDataHistoryServiceImpl.addUserDataHistory(userDataHistory);

            if (houseVo.getBatchModifyType().equals("station")) {
                List<String> comuitys = commuityService.getCommuityIdsList(null, houseVo.getBatchModifyId(), null);
                houseVo.setCommunitys(comuitys);
                return houseMapper.updateHouseBatchAllStation(houseVo);
            } else {
                return houseMapper.updateHouseBatchAll(houseVo);
            }
        } else {
            int count = 0;
            if (houseVo.getNetStatus() != null || houseVo.getBuildingArea() != null || houseVo.getInBuildArea() != null || houseVo.getUserBuildArea() != null || houseVo.getHeatingForm() != null
                    || houseVo.getHouseType() != null || houseVo.getHasHeatMeter() != null || houseVo.getHasValve() != null || houseVo.getHouseStructureId() != null
                    || houseVo.getAddress() != null || houseVo.getNotes() != null || houseVo.getHouseLocaltype() != null || houseVo.getControlType() != null) {
                count = houseMapper.updateHouseBatch(houseVo);
            } else {
                count = houseVo.getConsumerIds().size();
            }
            for (String consumerId : houseVo.getConsumerIds()) {
                UserDataHistory userDataHistory = new UserDataHistory();
                userDataHistory.setConsumerId(consumerId);
                userDataHistory.setPage("house");
                userDataHistory.setInfo("修改住户，ID:" + (consumerId));
                userDataHistoryServiceImpl.addUserDataHistory(userDataHistory);
            }
            if (houseVo.getHeatingAreaFormula() != null) {
                House house = new House();
                String[] split = houseVo.getHeatingAreaFormula().split("\\|");
                for (String id : houseVo.getConsumerIds()) {
                    house.setConsumerId(id);
                    switch (split[0]) {
                        case "buildingArea":
                            BigDecimal buildingArea = getHouseById(id).getBuildingArea();
                            if (buildingArea != null) {
                                house.setHeatingArea(buildingArea.multiply(new BigDecimal(split[1])).add(new BigDecimal(split[2])));
                            }
                            break;
                        case "inBuildArea":
                            BigDecimal inBuildArea = getHouseById(id).getInBuildArea();
                            if (inBuildArea != null) {
                                house.setHeatingArea(inBuildArea.multiply(new BigDecimal(split[1])).add(new BigDecimal(split[2])));
                            }
                            break;
                        case "userBuildArea":
                            BigDecimal userBuildArea = getHouseById(id).getUserBuildArea();
                            if (userBuildArea != null) {
                                house.setHeatingArea(userBuildArea.multiply(new BigDecimal(split[1])).add(new BigDecimal(split[2])));
                            }
                            break;
                        default:
                            break;
                    }
                    if (house.getHeatingArea() != null) {
                        modifyHouse(house);
                    }
                }
            }
            if (houseVo.getChargeAreaFormula() != null) {
                House house = new House();
                String[] split = houseVo.getChargeAreaFormula().split("\\|");
                for (String id : houseVo.getConsumerIds()) {
                    house.setConsumerId(id);
                    switch (split[0]) {
                        case "buildingArea":
                            house.setChargeArea(getHouseById(id).getBuildingArea().multiply(new BigDecimal(split[1])).add(new BigDecimal(split[2])));
                            break;
                        case "inBuildArea":
                            house.setChargeArea(getHouseById(id).getInBuildArea().multiply(new BigDecimal(split[1])).add(new BigDecimal(split[2])));
                            break;
                        case "userBuildArea":
                            house.setChargeArea(getHouseById(id).getUserBuildArea().multiply(new BigDecimal(split[1])).add(new BigDecimal(split[2])));
                            break;
                        default:
                            break;
                    }
                    if (house.getChargeArea() != null) {
                        modifyHouse(house);
                    }
                }
            }
            return count;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteHouseBatchByIds(List<String> consumerIds) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("house");
        userPageHistory.setAct("deleteHouseBatchByIds");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return houseMapper.deleteHouseBatchByIds(consumerIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int setDelHouseBatch(List<String> consumerIds) {
        for (String consumerId : consumerIds) {
            List<MeterInfo> list = meterServiceImpl.getAllMeter(null, null, null, null, null, null, null, null, null, consumerId, null).getMeterInfoList();
            if (list.size() > 0) {
                throw new BusinessException("用户下有关联项，不能删除");
            }
        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("house");
        userPageHistory.setAct("setDelHouseBatch");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);

        for (String consumerId : consumerIds) {
            UserDataHistory userDataHistory = new UserDataHistory();
            userDataHistory.setConsumerId(consumerId);
            userDataHistory.setPage("house");
            userDataHistory.setInfo("删除住户，ID:" + (consumerId));
            userDataHistoryServiceImpl.addUserDataHistory(userDataHistory);
        }
        return houseMapper.setDelHouseBatch(consumerIds,UserUtils.getUserId(),new Date());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyHouse(House house) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("house");
        userPageHistory.setAct("modifyHouse");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);

        UserDataHistory userDataHistory = new UserDataHistory();
        userDataHistory.setConsumerId(house.getConsumerId());
        userDataHistory.setPage("house");
        userDataHistory.setInfo("修改住户，ID:" + (house.getConsumerId()));
        userDataHistoryServiceImpl.addUserDataHistory(userDataHistory);
        house.setUpdateUser(UserUtils.getUserId());
        house.setUpdateDate(new Date());
        int num = houseMapper.updateByPrimaryKeySelective(house);
      	mbusService.modifyMbusAddress(house.getConsumerId(),house.getAddress());
      	meterService.modifyMeterAddress(house.getConsumerId(),house.getAddress());
      	return num;
    }


    @Override
    public List<String> getAllCompanyId() {
        return houseMapper.getAllCompanyId();
    }

    @Override
    public PageInfo<HouseInfo> getAllHouse(String companyId, String stationId, String commuityId, String buildingNo, String unitId, HouseVo houseVo, String order, String sortby, Integer page, Integer pageSize) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("house");
        userPageHistory.setAct("getAllHouse");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        
        List<String> companyIds = new ArrayList<String>();
        List<String> stationIds = new ArrayList<String>();
        List<String> commuityIds = new ArrayList<String>();
        Boolean flag = dataRoleService.getDataRoleItems(companyIds, stationIds, commuityIds
            ,UserUtils.getUserId(),companyId, stationId,commuityId);
					if (!flag) {
						return  new PageInfo<HouseInfo>();
					}
        if(companyIds.size() == 0 && commuityIds.size() == 0 && buildingNo == null && unitId == null){
        	return new PageInfo<HouseInfo>();
        }
        List<HouseInfo> houseInfoList = new ArrayList<HouseInfo>();
        sortby = CamelCaseUtil.toUnderscoreCase(sortby);
        if (sortby != null) {
            if (sortby.equals("charge_type_name")) {
                sortby = "charge_type";
            }
            if (sortby.equals("heat_user_type_name")) {
                sortby = "heat_user_type";
            }
            if (sortby.equals("heating_form_name")) {
                sortby = "heating_form";
            }
            if (sortby.equals("net_status_name")) {
                sortby = "net_status";
            }
            if (sortby.equals("has_heat_meter_mame")) {
                sortby = "has_heat_meter";
            }
            if (sortby.equals("has_valve_name")) {
                sortby = "has_valve";
            }
            if (sortby.equals("heat_exchange_status_name")) {
                sortby = "heat_exchange_status";
            }
            if (sortby.equals("house_type_name")) {
                sortby = "house_type";
            }
            if (sortby.equals("house_direct_name")) {
                sortby = "house_direct";
            }
            if (sortby.equals("heating_status_name")) {
                sortby = "heating_status";
            }
            if (sortby.equals("house_localtype_name")) {
                sortby = "house_localtype";
            }
            if (sortby.equals("control_type_name")) {
                sortby = "control_type";
            }
            if(sortby.equals("diameter_name")){
                sortby = "diameter";
            }
        }
        if (page != null) {
          PageHelper.startPage(page, pageSize);
      }
      houseInfoList = houseMapper.selectAllHouse(companyIds, null, commuityIds, buildingNo, unitId, houseVo, order, sortby, page, pageSize);
      PageInfo<HouseInfo> pageInfo = new PageInfo<>(houseInfoList);
      List<TypeMst> typeMstList = typeMstServiceImpl.getAllTypeMst();
      for (HouseInfo houseInfo : pageInfo.getList()) {
          houseInfo.setChargeTypeName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getChargeType()));
          houseInfo.setHeatUserTypeName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHeatUserType()));
          houseInfo.setHeatingFormName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHeatingForm()));
          houseInfo.setNetStatusName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getNetStatus()));
          houseInfo.setHasHeatMeterName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHasHeatMeter()));
          houseInfo.setHasValveName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHasValve()));
          houseInfo.setHeatExchangeStatusName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHeatExchangeStatus()));
          houseInfo.setHouseTypeName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHouseType()));
          houseInfo.setHouseDirectName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHouseDirect()));
          houseInfo.setHeatingStatusName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHeatingStatus()));
          houseInfo.setHouseLocaltypeName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHouseLocaltype()));
          houseInfo.setControlTypeName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getControlType()));
          houseInfo.setDiameterName(typeMstServiceImpl.getTypeNameById(typeMstList,houseInfo.getDiameter()));
      }
      return pageInfo;
    }


    @Override
    public PageInfo<HouseInfo> getAllHouse(String commuityIds, HouseVo houseVo, String order, String sortby, Integer page, Integer pageSize) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("house");
        userPageHistory.setAct("getAllHouse");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);

        List<HouseInfo> houseInfoList = new ArrayList<HouseInfo>();
        List<String> companyIds = new ArrayList<String>();
        sortby = CamelCaseUtil.toUnderscoreCase(sortby);
        if (sortby != null) {
            if (sortby.equals("charge_type_name")) {
                sortby = "charge_type";
            }
            if (sortby.equals("heat_user_type_name")) {
                sortby = "heat_user_type";
            }
            if (sortby.equals("heating_form_name")) {
                sortby = "heating_form";
            }
            if (sortby.equals("net_status_name")) {
                sortby = "net_status";
            }
            if (sortby.equals("has_heat_meter_mame")) {
                sortby = "has_heat_meter";
            }
            if (sortby.equals("has_valve_name")) {
                sortby = "has_valve";
            }
            if (sortby.equals("heat_exchange_status_name")) {
                sortby = "heat_exchange_status";
            }
            if (sortby.equals("house_type_name")) {
                sortby = "house_type";
            }
            if (sortby.equals("house_direct_name")) {
                sortby = "house_direct";
            }
            if (sortby.equals("heating_status_name")) {
                sortby = "heating_status";
            }
            if (sortby.equals("house_localtype_name")) {
                sortby = "house_localtype";
            }
            if (sortby.equals("control_type_name")) {
                sortby = "control_type";
            }
        }
        List<String> commuityIdlst = new ArrayList<String>();
        if (commuityIds == null) {
            commuityIdlst = null;
        } else {
            String[] split = commuityIds.split(",");
            for (String s : split) {
                commuityIdlst.add(s);
            }
        }

        if (commuityIdlst.size() > 0) {
            if (page != null) {
                PageHelper.startPage(page, pageSize);
            }
            houseInfoList = houseMapper.selectAllHouse(companyIds, null, commuityIdlst, null, null, houseVo, order, sortby, page, pageSize);
            PageInfo<HouseInfo> pageInfo = new PageInfo<>(houseInfoList);
            List<TypeMst> typeMstList = typeMstServiceImpl.getAllTypeMstByRedis();
            for (HouseInfo houseInfo : pageInfo.getList()) {
                houseInfo.setChargeTypeName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getChargeType()));
                houseInfo.setHeatUserTypeName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHeatUserType()));
                houseInfo.setHeatingFormName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHeatingForm()));
                houseInfo.setNetStatusName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getNetStatus()));
                houseInfo.setHasHeatMeterName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHasHeatMeter()));
                houseInfo.setHasValveName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHasValve()));
                houseInfo.setHeatExchangeStatusName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHeatExchangeStatus()));
                houseInfo.setHouseTypeName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHouseType()));
                houseInfo.setHouseDirectName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHouseDirect()));
                houseInfo.setHeatingStatusName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHeatingStatus()));
                houseInfo.setHouseLocaltypeName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHouseLocaltype()));
                houseInfo.setControlTypeName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getControlType()));
            }
            return pageInfo;
        } else {
            return new PageInfo<>();
        }
    }


    @Override
    public PageInfo<HouseInfo> getAllHouseByIds(String consumerIds, HouseVo houseVo, String order, String sortby, Integer page, Integer pageSize) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("house");
        userPageHistory.setAct("getAllHouse");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);

        List<HouseInfo> houseInfoList = new ArrayList<HouseInfo>();
        List<String> companyIds = new ArrayList<String>();
        sortby = CamelCaseUtil.toUnderscoreCase(sortby);
        if (sortby != null) {
            if (sortby.equals("charge_type_name")) {
                sortby = "charge_type";
            }
            if (sortby.equals("heat_user_type_name")) {
                sortby = "heat_user_type";
            }
            if (sortby.equals("heating_form_name")) {
                sortby = "heating_form";
            }
            if (sortby.equals("net_status_name")) {
                sortby = "net_status";
            }
            if (sortby.equals("has_heat_meter_mame")) {
                sortby = "has_heat_meter";
            }
            if (sortby.equals("has_valve_name")) {
                sortby = "has_valve";
            }
            if (sortby.equals("heat_exchange_status_name")) {
                sortby = "heat_exchange_status";
            }
            if (sortby.equals("house_type_name")) {
                sortby = "house_type";
            }
            if (sortby.equals("house_direct_name")) {
                sortby = "house_direct";
            }
            if (sortby.equals("heating_status_name")) {
                sortby = "heating_status";
            }
            if (sortby.equals("house_localtype_name")) {
                sortby = "house_localtype";
            }
            if (sortby.equals("control_type_name")) {
                sortby = "control_type";
            }
        }
        List<String> consumerIdlst = new ArrayList<String>();
        if (consumerIds == null) {
            consumerIdlst = null;
        } else {
            String[] split = consumerIds.split(",");
            for (String s : split) {
                consumerIdlst.add(s);
            }
        }

        if (consumerIdlst.size() > 0) {
            if (page != null) {
                PageHelper.startPage(page, pageSize);
            }
            houseInfoList = houseMapper.selectAllHouseByIds(companyIds, null, consumerIdlst, null, null, houseVo, order, sortby, page, pageSize);
            PageInfo<HouseInfo> pageInfo = new PageInfo<>(houseInfoList);
            List<TypeMst> typeMstList = typeMstServiceImpl.getAllTypeMstByRedis();
            for (HouseInfo houseInfo : pageInfo.getList()) {
                houseInfo.setChargeTypeName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getChargeType()));
                houseInfo.setHeatUserTypeName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHeatUserType()));
                houseInfo.setHeatingFormName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHeatingForm()));
                houseInfo.setNetStatusName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getNetStatus()));
                houseInfo.setHasHeatMeterName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHasHeatMeter()));
                houseInfo.setHasValveName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHasValve()));
                houseInfo.setHeatExchangeStatusName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHeatExchangeStatus()));
                houseInfo.setHouseTypeName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHouseType()));
                houseInfo.setHouseDirectName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHouseDirect()));
                houseInfo.setHeatingStatusName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHeatingStatus()));
                houseInfo.setHouseLocaltypeName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getHouseLocaltype()));
                houseInfo.setControlTypeName(typeMstServiceImpl.getTypeNameById(typeMstList, houseInfo.getControlType()));
            }

            return pageInfo;
        } else {
            return new PageInfo<>();
        }
    }


    @Override
    public House getHouseByName(String houseName, String unitId) {
        return houseMapper.selectByName(houseName, unitId);
    }

    @Override
    public ImportResult importExcel(MultipartFile multipartFile, ServletRequest request) {
        ImportResult importResult = new ImportResult();
        ExcelTools excel = new ExcelTools();
        Sheet sheet = excel.getFile(multipartFile, "用户信息");
        String path = request.getServletContext().getRealPath("") + "/errorFile/"; 
        importExcelSpeed(sheet,excel,importResult, path, excel.getIsSpeed());
        return importResult;
    }
    
    
    private void importExcelSpeed(Sheet sheet, ExcelTools excel, ImportResult importResult, String path, Boolean isUpdate) {
    	int successCount = 0;
      List<HouseInfo> houseInfoList = new ArrayList<HouseInfo>();
      List<ErrorInfo> errorInfos = new ArrayList<ErrorInfo>();
      //一共有多少行
      int rows = excel.getRows();
      CellStyle style = excel.getStyle();
    	Workbook workbook = excel.getWorkbook();
    	FormulaEvaluator formulaEvaluator = excel.getFormulaEvaluator();
      int totalData = 0;        
      Subord sub = new Subord();
      List<PriceDefine> pricelist = new ArrayList<PriceDefine>();
      
      Map<String,List<String>> houseReCheck = new HashMap<>();
      int checkReNum = 0;
      for (int i = 1; i <= rows + 1; i++) {
          //读取左上端单元格
          Row row = sheet.getRow(i);
          ErrorInfo errorInfo = new ErrorInfo();
          
          if (row != null) {
              if (excel.isCellVolid(row, 53, errorInfos, style)) {
                  totalData++;
                  HouseInfo houseInfo = new HouseInfo();
                  String companyName = getCellValue(row.getCell(0));
                  String commuityName = getCellValue(row.getCell(1));
                  String buidName = getCellValue(row.getCell(2));
                  String unitName = getCellValue(row.getCell(3));
                  String systemName = getCellValue(row.getCell(5));

                  Boolean flag = getSubordAndUpdate(sub, companyName, commuityName, buidName, unitName, null, null, systemName, isUpdate);
                  if (!flag) {
                      sub.orderErrorInfo(errorInfos, i, row, style);
                      continue;
                  }
                  if (!sub.getCurrentInfo().equals("unit")) {
                      sub.setErrorInfo("unit");
                      sub.orderErrorInfo(errorInfos, i, row, style);
                      continue;
                  }
                  houseInfo.setCompanyId(sub.getCompany().getCompanyId());
                  houseInfo.setUnitId(sub.getUnit().getUnitId());

                  houseInfo.setRoomName(getCellValue(row.getCell(4)));
                  Boolean Reflag = ExcelTools.checkRep(houseReCheck, houseInfo.getUnitId(), houseInfo.getRoomName());
                  if(Reflag){
                  	checkReNum++;
                  	continue;
                  }
                  if (sub.getSystem() != null) {
                      houseInfo.setSystemId(sub.getSystem().getSystemId());
                  }

                  if (getCellValue(row.getCell(6)).length() != 0) {
                      try {
                          houseInfo.setBuildingArea(new BigDecimal(ExcelTools.getCellValueFormula(row.getCell(6), formulaEvaluator)));
                      } catch (Exception e) {
                      	ExcelTools.addErrorInfo(errorInfos, i ,6,"字符串不能转换成数字", false, row, style);
                      }
                  }
                  if (getCellValue(row.getCell(7)).length() != 0) {
                      try {
                          houseInfo.setInBuildArea(new BigDecimal(ExcelTools.getCellValueFormula(row.getCell(7), formulaEvaluator)));
                      } catch (Exception e) {
                          ExcelTools.addErrorInfo(errorInfos, i ,7,"字符串不能转换成数字", false, row, style);
                      }
                  }
                  if (getCellValue(row.getCell(8)).length() != 0) {
                      try {
                          houseInfo.setUserBuildArea(new BigDecimal(ExcelTools.getCellValueFormula(row.getCell(8), formulaEvaluator)));
                      } catch (Exception e) {
                          ExcelTools.addErrorInfo(errorInfos, i ,8,"字符串不能转换成数字", false, row, style);
                      }
                  }
                  houseInfo.setContractHeatId(getCellValue(row.getCell(9)));
                  if (getCellValue(row.getCell(10)).length() != 0) {
                      try {
                          houseInfo.setHeatingArea(new BigDecimal(ExcelTools.getCellValueFormula(row.getCell(10), formulaEvaluator)));
                      } catch (Exception e) {
                      	ExcelTools.addErrorInfo(errorInfos, i ,10,"字符串不能转换成数字", false, row, style);
                      }
//                  } else {
//                  	ExcelTools.addErrorInfo(errorInfos, i ,10,"供热面积不能为空", false, row, style);
//                  	continue;
                 }
                  
                  String unitPriceId = getUnitPriceTypeIdByName(pricelist, getCellValue(row.getCell(12)),houseInfo.getCompanyId());
                  houseInfo.setChargeType(getCellValue(row.getCell(11)));
                  houseInfo.setUnitPriceType(unitPriceId);
                  houseInfo.setHeatUserType(getCellValue(row.getCell(13)));
                  if (getCellValue(row.getCell(14)).length() != 0) {
                      try {
                          houseInfo.setChargeArea(new BigDecimal(ExcelTools.getCellValueFormula(row.getCell(14), formulaEvaluator)));
                      } catch (Exception e) {
                      	ExcelTools.addErrorInfo(errorInfos, i ,14,"字符串不能转换成数字", false, row, style);
                      }
                  }
                  houseInfo.setName(getCellValue(row.getCell(15)));
                  houseInfo.setEmail(getCellValue(row.getCell(16)));
                  houseInfo.setUserCompany(getCellValue(row.getCell(17)));
                  houseInfo.setTel(getCellValue(row.getCell(18)));
                  houseInfo.setHeatingForm(getCellValue(row.getCell(19)));
                  houseInfo.setNetStatus(getCellValue(row.getCell(20)));
                  if(getCellValue(row.getCell(21)).length()!= 0){
                  	houseInfo.setHeatExchangeStatus(getCellValue(row.getCell(21)));
                  }else{
                  	houseInfo.setHeatExchangeStatus("0");
                  }
                  houseInfo.setIssample(getCellValue(row.getCell(22)));
                  houseInfo.setHouseType(getCellValue(row.getCell(23)));
                  houseInfo.setDiameter(getCellValue(row.getCell(24)));
                  houseInfo.setHasHeatMeter(getCellValue(row.getCell(25)));
                  houseInfo.setHasValve(getCellValue(row.getCell(26)));
                  houseInfo.setHouseStructureId(getCellValue(row.getCell(27)));
                  houseInfo.setChargeId(getCellValue(row.getCell(28)));
                  houseInfo.setSecPipeId(getCellValue(row.getCell(29)));
//                  String addr = getCellValue(row.getCell(30));
//
//                  if (addr.isEmpty()) {
                  String addr = getCellValue(row.getCell(1)) + Tools.nkey  + getCellValue(row.getCell(2)) + Tools.nkey  + getCellValue(row.getCell(3)) + Tools.nkey  + houseInfo.getRoomName();
//                  }

                  houseInfo.setAddress(addr);
                  if (getCellValue(row.getCell(31)).length() != 0) {
                      houseInfo.setDesignHeatTarget(new BigDecimal(ExcelTools.getCellValueFormula(row.getCell(31), formulaEvaluator)));
                  } else {
                      houseInfo.setDesignHeatTarget(new BigDecimal(0));
                  }
                  houseInfo.setNotes(getCellValue(row.getCell(32)));
                  houseInfo.setMemo1(getCellValue(row.getCell(33)));
                  houseInfo.setMemo2(getCellValue(row.getCell(34)));
                  houseInfo.setMemo3(getCellValue(row.getCell(35)));
                  houseInfo.setMemo4(getCellValue(row.getCell(36)));
                  houseInfo.setMemo5(getCellValue(row.getCell(37)));
                  houseInfo.setFloorHigh(getCellValue(row.getCell(38)));
                  houseInfo.setIdcard(getCellValue(row.getCell(39)));
                  houseInfo.setPasswod(getCellValue(row.getCell(40)));
                  if (getCellValue(row.getCell(41)).length() != 0) {
                      try {
                          houseInfo.setPersonSum(new BigDecimal(ExcelTools.getCellValueFormula(row.getCell(41), formulaEvaluator)));
                      } catch (Exception e) {
                      	ExcelTools.addErrorInfo(errorInfos, i ,41,"字符串不能转换成数字", false, row, style);
                      }
                  }
                  houseInfo.setPersonType(getCellValue(row.getCell(42)));
                  houseInfo.setColorNo(getCellValue(row.getCell(43)));
                  houseInfo.setPersoncardId(getCellValue(row.getCell(44)));
                  houseInfo.setLowinsureName(getCellValue(row.getCell(45)));
                  houseInfo.setLowinsureNo(getCellValue(row.getCell(46)));
                  houseInfo.setHouseDirect(getCellValue(row.getCell(47)));
                  houseInfo.setHouseKey(getCellValue(row.getCell(48)));
                  houseInfo.setServiceman(getCellValue(row.getCell(49)));
                  houseInfo.setHeatingStatus(getCellValue(row.getCell(50)));
                  houseInfo.setHouseLocaltype(getCellValue(row.getCell(51)));
                  houseInfo.setControlType(getCellValue(row.getCell(52)));

                  //houseInfo.setConsumerId(idGenerater.generateHouseId(unit.getUnitId(),1,true).get(0));
                  houseInfo.setRowno(houseInfo.getConsumerId());
                  houseInfo.setIsvalid(1);
//              addHouse(houseInfo);
                  if (errorInfo.getPosition() == null) {
                      successCount += 1;
                  }
                  houseInfoList.add(houseInfo);
                  continue;
              }
          }
      }
      if (errorInfos.size() > 0) {
          String path1 = null;
          try {
//          path = "C:/errorFile/";
              File file = new File(path);
              if (!file.exists()) {
                  file.mkdirs();
              }
              path1 = "house-" + UserUtils.getUserId() + "-" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xlsx";
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
      importResult.setFailCount(totalData - successCount - checkReNum);
      //importResult.setMultipartFile(multipartFile);
      importResult.setId(UserUtils.getUserId() + UUID.randomUUID().toString().replace("-", "") + "house");
      //导入数据
      if (importResult.getFailList().size() == 0) {
          Map<String, List<HouseInfo>> infos = idGenerater.getHousebyCommunity(houseInfoList);
          for (String comid : infos.keySet()) {
              idGenerater.generateHouseIdsbyCommuityWithUpdat(comid, infos.get(comid));
              addBatch(infos.get(comid));
          }
      }
		}

		private String getUnitPriceTypeIdByName(List<PriceDefine> list, String priceName,String companyId) {
    	if(priceName == null || priceName.isEmpty()){
    		return "";
    	}
			for(PriceDefine def:list){
				if(def.getPriceName().equals(priceName)){
					return def.getPrimaryId();
				}
			}
			List<PriceDefine> list2 = priceDefineService.findBycompanyId(companyId);
			list.addAll(list2);
			//
			for(PriceDefine def:list2){
				if(def.getPriceName().equals(priceName)){
					return def.getPrimaryId();
				}
			}
			return priceName;
		}

		@Override
    public List<House> findByChargeType(String chargeType) {
        return houseMapper.findByChargeType(chargeType);
    }

    @Override
    public List<House> findByConsumerIdlike(String likeConsumerId) {
        return houseMapper.findByConsumerIdlike(likeConsumerId);
    }

  @Override
  public int findCountByConsumerIdlike(String likeConsumerId) {
    return houseMapper.findCountByConsumerIdlike(likeConsumerId);
  }

    @Override
    public Map<String, House> getHouseByIdAndToMap(Commuity commuity, Map<String, Map<String, House>> mapHouse) {
        Map<String, House> map = mapHouse.get(commuity.getCommuityId());
        if (map == null) {
            map = new HashMap<String, House>();
            List<House> list = findByConsumerIdlike(commuity.getCommuityId());
            for (House info : list) {
                map.put(info.getConsumerId().substring(0, 15) + info.getRoomName(), info);
            }
            mapHouse.put(commuity.getCommuityId(), map);
        }
        return map;
    }
    @Override
    public Boolean getSubord(Subord sub, String companyName, String commmuityName, String buildingName,
        String unitName, String houseName, String stationName, String systemName){
    	return getSubordAndUpdate(sub,companyName, commmuityName, buildingName, unitName, houseName,stationName, systemName, false);
    }
    @Override
    public Boolean getSubordAndUpdate(Subord sub, String companyName, String commmuityName, String buildingName,
                             String unitName, String houseName, String stationName, String systemName, Boolean isUpdate) {
        sub.setErrorInfo("");
        sub.setCurrentInfo("");
        sub.setCurrnetId("");
        sub.setCompany(null);
        sub.setCommuity(null);
        sub.setBuilding(null);
        sub.setUnit(null);
        sub.setHouse(null);
        sub.setStation(null);
        sub.setSystem(null);
        if (companyName == null || companyName.isEmpty()) {
            sub.setErrorInfo("company");
            return false;
        }
        Company company = companyService.getCompanyByNameAndToMap(companyName, sub.getMapCompany());
        if (company == null) {
            sub.setErrorInfo("company");
            return false;
        }
        sub.setCompany(company);
        sub.setCurrentInfo("company");
        sub.setCurrnetId(company.getCompanyId());
        Station station = null;
        if (stationName != null && !stationName.isEmpty()) {
            
            Map<String, Station> stations = stationService.getStationByIdAndToMap(company, sub.getMapStation());
            station = stations.get(stationName);
            if (station == null) {
                sub.setErrorInfo("station");
                return false;
            }
            sub.setStation(station);
        }
        SystemInfo system = null;
        if (systemName != null && !systemName.isEmpty()) {
            
            Map<String, SystemInfo> systems = systemService.getSystemByIdAndToMap(company, sub.getMapSystem());
            system = systems.get(systemName);
            if (system == null) {
                sub.setErrorInfo("system");
                return false;
            }

            sub.setSystem(system);
        }
        if (commmuityName == null || commmuityName.isEmpty()) {
            return true;
        }
        Commuity commuity = null;
        Map<String, Commuity> commuitys = commuityService.getCommuityByIdAndToMap(company, sub.getMapCommuity());
        commuity = commuitys.get(commmuityName);
        if (commuity == null) {
        	if(isUpdate){
            if(station == null){
            	station = stationService.getStationById(system.getStationId());
            }
        		commuity = new Commuity();
        		commuity.setCompanyId(company.getCompanyId());
        		if(station != null){
        			commuity.setStationId(station.getStationId());
        		}
        		String id = idGenerater.generateCommunityId(company.getCompanyId(), 1).get(0);
        		commuity.setCommuityId(id);
        		commuity.setCommuityName(commmuityName);
        		commuityService.addCommuity(commuity);	
        		commuitys.put(commmuityName, commuity);
        	}else{
            sub.setErrorInfo("commuity");
            return false;
        	}
        }
        sub.setCommuity(commuity);
        sub.setCurrentInfo("commuity");
        sub.setCurrnetId(commuity.getCommuityId());
        if (buildingName == null || buildingName.isEmpty()) {
            return true;
        }
        Build build = null;
        Map<String, Build> builds = buildService.getBuildByIdAndToMap(commuity, sub.getMapBuild());
        build = builds.get(buildingName);
        if (build == null) {
        	if(isUpdate){
        		BuildInfo buildInfo = new BuildInfo();
        		buildInfo.setCompanyId(company.getCompanyId());
        		if(commuity != null){
        			//id由内部生成
//        			String id = idGenerater.generateBuildId(commuity.getCommuityId(), 1, true).get(0);
//        			buildInfo.setBuildingNo(id);
        			buildInfo.setCommuityId(commuity.getCommuityId());
        			buildInfo.setBuildingName(buildingName);
        			buildInfo.setAddress(commuity.getCommuityName() + "-" + buildingName);
        			buildService.addBuild(buildInfo);
        			build = buildInfo;
        			builds.put(buildingName, build);
        		}	
        	}else{
            sub.setErrorInfo("building");
            return false;
        	}
        }
        sub.setBuilding(build);
        sub.setCurrentInfo("building");
        sub.setCurrnetId(build.getBuildingNo());
        if (unitName == null || unitName.isEmpty()) {
            return true;
        }
        Unit unit = null;
        Map<String, Unit> units = unitService.getUnitByIdAndToMap(build, sub.getMapUnit());
//		java.lang.System.out.println("-----------------" + buildingName+ "--------"+unitName+"----------");
        unit = units.get(unitName);
        if (unit == null) {
        	if(isUpdate){
        		unit = new Unit();
        		unit.setCompanyId(company.getCompanyId());
        		if(build != null){
        			//String id = idGenerater.generateUnitId(build.getBuildingNo(), 1, true).get(0);
        			unit.setBuildingId(build.getBuildingNo());
        			//unit.setUnitId(id);
        			unit.setUnitName(unitName);
        			unit.setAddress(commuity.getCommuityName() + "-" + build.getBuildingName() + "-" + unitName);
        			unitService.addUnit(unit);
        			units.put(unitName, unit);
        		}	
        	}else{
            sub.setErrorInfo("unit");
            return false;
        	}
        }
        sub.setUnit(unit);
        sub.setCurrentInfo("unit");
        sub.setCurrnetId(unit.getUnitId());
        if (houseName == null || houseName.isEmpty()) {
            return true;
        }
        House house = null;
        Map<String, House> houses = getHouseByIdAndToMap(commuity, sub.getMapHouse());
        house = houses.get(unit.getUnitId() + houseName);
        if (house == null) {
            sub.setErrorInfo("house");
            return false;
        }
        sub.setHouse(house);
        sub.setCurrentInfo("house");
        sub.setCurrnetId(house.getConsumerId());
        return true;
    }
    
    @Override
  	public void modifyHouseName(String changedAdress, String consumerId){
    		houseMapper.modifyHouseName(changedAdress, Tools.nkey, consumerId); 	
    }
    
    @Override
  	public void modifyHouseNameByAddress(String changedAdress, String orgAdress, String consumerId){
    		houseMapper.modifyHouseNameByAddress(orgAdress,changedAdress, Tools.nkey, consumerId); 	
    }
    @Override
    public List<House> getHouseListById(String id) {
        List<House> houseList = new ArrayList<>();
        if (id.length() == 6) {//说明是站的id
            //通过站id查出所有的小区
            List<Commuity> commuityList = commuityMapper.selectTreeList(id,null);
            if (commuityList != null && commuityList.size() > 0) {
                List<String> ids = new ArrayList<>();
                for (Commuity commuity : commuityList) {
                    ids.add(commuity.getCommuityId());
                }
                houseList = houseMapper.findHouseListByCommuitys(null, ids, null, null);
            }
        } else {
            houseList = houseMapper.getHouseBySearchTextOrId(null, id, null, null, null, null);
        }
        return  houseList;
    }

}

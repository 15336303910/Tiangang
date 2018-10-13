package cn.plou.web.system.meterMessage.meterKey.service.impl;

import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.importDataBatch.ErrorInfo;
import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.common.utils.ExcelTools;
import cn.plou.web.system.baseMessage.company.service.impl.CompanyServiceImpl;
import cn.plou.web.system.meterMessage.meter.dao.MeterMapper;
import cn.plou.web.system.meterMessage.meter.service.MeterService;
import cn.plou.web.system.meterMessage.meter.vo.MeterInfo;
import cn.plou.web.system.meterMessage.meterKey.dao.MeterKeyMapper;
import cn.plou.web.system.meterMessage.meterKey.entity.MeterKey;
import cn.plou.web.system.meterMessage.meterKey.service.MeterKeyService;
import cn.plou.web.system.meterMessage.meterKey.vo.MeterKeyInfo;
import cn.plou.web.system.permission.userPageHistory.entity.UserPageHistory;
import cn.plou.web.system.permission.userPageHistory.service.impl.UserPageHistoryServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cn.plou.web.common.utils.ExcelTools.getCellValue;

@Component
public class MeterKeyServiceImpl implements MeterKeyService {

    @Autowired
    MeterKeyMapper meterKeyMapper;
    @Autowired
    MeterMapper meterMapper;
    @Autowired
    CompanyServiceImpl companyServiceImpl;
    @Autowired
    MeterService meterService;
    @Autowired
    private UserPageHistoryServiceImpl userPageHistoryServiceImpl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addMeterKey(MeterKeyInfo meterKeyInfo) {
//        String address2nd = meterService.getMeterById(meterKeyInfo.getMeterId()).getAddress2nd();
        List<MeterKey> meterKeyList = meterKeyMapper.selectAllMeterKey(null, null, null, null, null);
        List<Integer> list = new ArrayList<Integer>();
        if (meterKeyList.size() == 0) {
            meterKeyInfo.setId("1");
        } else {
            for (MeterKey meterKey : meterKeyList) {
                list.add(Integer.valueOf(meterKey.getId()));
                if(meterKey.getAddress2nd().equals(meterKeyInfo.getAddress2nd())){
                    throw new BusinessException("该设备已添加密钥");
                }
            }
            Collections.sort(list);
            Collections.reverse(list);
            meterKeyInfo.setId("" + (list.get(0) + 1));
        }
//        meterKeyInfo.setAddress2nd(address2nd);
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("meterKey");
        userPageHistory.setAct("addMeterKey");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return meterKeyMapper.insertSelective(meterKeyInfo);
    }

    @Override
    public ImportResult importExcel(MultipartFile multipartFile) {

        ImportResult importResult = new ImportResult();
        int successcount = 0;
        List<ErrorInfo> errorInfos = new ArrayList<ErrorInfo>();
        ExcelTools excel = new ExcelTools();
        Sheet sheet = excel.getFile(multipartFile, "秘钥");
        //一共有多少行
        int rows = excel.getRows();
        int totalData = 0;        
        
		for (int i = 1; i <= rows + 1; i++) {
			// 读取左上端单元格
			Row row = sheet.getRow(i);
			if (row != null) {
				if (getCellValue(row.getCell(0)).trim().length() != 0) {
					totalData++;
					MeterKeyInfo meterKeyInfo = new MeterKeyInfo();

					try {
						addMeterKey(meterKeyInfo);
						successcount += 1;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
        importResult.setTotalCount(totalData);
        importResult.setFailList(errorInfos);
//        importResult.setSuccessCount(successcount);
        importResult.setFailCount(totalData-successcount);
        return importResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageInfo<MeterKey> getAllMeterKey(String companyId, String stationId, String commuityId, String buildingNo, String unitId,
                                             String consumerId, String order, String sortby, Integer page, Integer pageSize) {
        List<String> meterIds = new ArrayList<String>();
        List<MeterInfo> meterInfoList = meterService.getAllMeter(null,null,null, null, companyId, stationId, commuityId, buildingNo, unitId, consumerId, null).getMeterInfoList();
        for (MeterInfo meterInfo : meterInfoList) {
            meterIds.add(meterInfo.getMeterId());
        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("meterKey");
        userPageHistory.setAct("getAllMeterKey");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);

        List<String> address2nds = meterMapper.selectALLAddress2nd(meterIds);
//        if(page!=null) {
//            page = (page - 1) * pageSize;
//        }
        PageHelper.startPage(page,pageSize);
        //List<MeterKey> meterKeyList = meterKeyMapper.selectAllMeterKey(meterIds, order, sortby, page, pageSize);
        List<MeterKey> meterKeyList = meterKeyMapper.selectAllMeterKeyByAddress2nds(address2nds,order,sortby,page,pageSize);
        PageInfo<MeterKey> pageInfo=new PageInfo<>(meterKeyList);
        return pageInfo;
    }
}

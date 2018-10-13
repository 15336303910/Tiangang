package cn.plou.web.system.baseMessage.unit.service;

import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.system.baseMessage.build.entity.Build;
import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import cn.plou.web.system.baseMessage.unit.entity.Unit;
import cn.plou.web.system.baseMessage.unit.vo.UnitInfo;
import cn.plou.web.system.baseMessage.unit.vo.UnitListInfo;
import cn.plou.web.system.baseMessage.unit.vo.UnitSelectInfo;
import cn.plou.web.system.baseMessage.unit.vo.UnitVo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

@Service
public interface UnitService {
    List<Unit> getUnitTree(String buildingId);
    Unit getUnitById(String unitId);
    Unit getUnitByName(String companyId,String commuityName,String buildingName,String unitName);
    int addUnit(Unit unit);
    UnitListInfo getAllUnit(Integer page, Integer pageSize, String order, String sortby, String companyId, String stationId, String commuityId, String buildingId, String unitId,UnitVo unitVo);
    int deleteBatchByIds(List<String> unitIds);
    int modifyBatch(UnitVo unitVo);
    int modifyUnit(Unit unit);
    ImportResult importExcel(MultipartFile multipartFile,ServletRequest request);
    String exportExcel(ServletRequest request,UnitSelectInfo unitSelectInfo);
		Integer getMaxUnitId(String buildId);
		List<Unit> getAllUnitByCommuity(String commuity);
		Map<String, Unit> getUnitByIdAndToMap(Build build, Map<String, Map<String, Unit>> mapUnit);
		void modifyUnitName(String buildingNo, String buildingAddressOrg, String buildingAddress);
}
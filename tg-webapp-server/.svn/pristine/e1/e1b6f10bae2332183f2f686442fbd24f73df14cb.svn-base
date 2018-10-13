package cn.plou.web.system.baseMessage.commuity.service;

import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import cn.plou.web.system.baseMessage.commuity.entity.CommuityKey;
import cn.plou.web.system.baseMessage.commuity.vo.CommuityListInfo;
import cn.plou.web.system.baseMessage.commuity.vo.CommuitySelectInfo;
import cn.plou.web.system.baseMessage.commuity.vo.CommuityVo;
import cn.plou.web.system.baseMessage.company.entity.Company;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
@Service
public interface CommuityService {
    List<Commuity> getTreeList(String stationId);
    CommuityListInfo getAllCommuity(Integer page, Integer pageSize, String order, String sortby, String companyId, String stationId, String commuityId, CommuityVo commuityVo);
    Commuity getCommuityById(CommuityKey commuityKey);
    int deleteBatchByIds(List<String> commuityIds);
    int addCommuity(Commuity commuity);
    int modifyCommuityBatch(CommuityVo commuityVo);
    int modifyCommuity(Commuity commuity);
    Commuity getCommuityById(String commuityId);
    List<String> getCommuityIdsList(String companyId,String stationId,String commuityId);
    ImportResult importExcel(MultipartFile multipartFile,ServletRequest request);
    int addBatch(List<Commuity> commuities);
    Commuity getCommuityByName(String commuityName,String companyId);
    String exportExcel(ServletRequest request,CommuitySelectInfo selectInfo);
    Commuity selectById(String commuityId);
    Commuity selectCommuityByName(@Param("commuityName")String commuityName,@Param("companyId")String companyId);
		Integer getMaxCommuityId(String companyId);
		Map<String, Commuity> getCommuityByIdAndToMap(Company company, Map<String, Map<String, Commuity>> mapCommuity);
    List<String> getCommuityIdsByStationId(String stationId);
		List<Commuity> selectCommuityByConsumerIdLike(String consumerId);
		List<Commuity> selectCommuityByStationIds(List<String> stationIds);
}

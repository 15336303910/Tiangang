package cn.plou.web.system.baseMessage.system.service;

import cn.plou.web.system.baseMessage.company.entity.Company;
import cn.plou.web.system.baseMessage.system.entity.System;
import cn.plou.web.system.baseMessage.system.vo.SystemInfo;
import cn.plou.web.system.baseMessage.system.vo.SystemListInfo;
import cn.plou.web.system.baseMessage.system.vo.SystemVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface SystemService {
    /*List<SystemInfo> getAllSystem(Integer page,Integer pageSize,String order, String sortby, String companyId, String stationId, SystemVo systemVo);*/

    SystemListInfo getAllSystem(Integer page, Integer pageSize, String order, String sortby, String companyIds, String stationId, SystemVo systemVo);
    System getSystemById(String systemId);

    int deleteSystemBatch(List<String> systemIds);

    int addSystem(System system);

    int modifySystemBatch(SystemVo systemVo);

    String getNewSystemId();

    int modifySystem(System system);

    List<String> getAllSystemIds();
	List<SystemInfo> getSystemByIds(List<String> systemIdAndName);
	Integer getMaxSystemId();
	Map<String, SystemInfo> getSystemByIdAndToMap(Company company,
			Map<String, Map<String, SystemInfo>> mapSystem);
}

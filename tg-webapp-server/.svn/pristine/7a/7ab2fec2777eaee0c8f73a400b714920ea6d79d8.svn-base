package cn.plou.web.system.baseMessage.sewageStation.service;

import cn.plou.web.system.baseMessage.sewageStation.entity.SewageStation;
import cn.plou.web.system.baseMessage.sewageStation.vo.SewageStationInfo;
import cn.plou.web.system.baseMessage.sewageStation.vo.SewageStationVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SewageStationService {
    int addSewageStation(SewageStation sewageStation);
    SewageStation getSewageStationById(String sewageStationId);
    int modifySewageStation(SewageStation sewageStation);
    int modifySewageStationBatch(SewageStationVo sewageStationVo);
    int deleteSewageStationBatchByIds(List<String> sewageStationIds);
    List<SewageStationInfo> getAllSewageStation(String companyId, SewageStationVo sewageStationVo, String order, String sortby);
}

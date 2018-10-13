package cn.plou.web.system.baseMessage.station.service;

import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import cn.plou.web.system.baseMessage.company.entity.Company;
import cn.plou.web.system.baseMessage.station.entity.Station;
import cn.plou.web.system.baseMessage.station.entity.StationKey;
import cn.plou.web.system.baseMessage.station.vo.StationInfo;
import cn.plou.web.system.baseMessage.station.vo.StationVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface StationService {
    int addStation(Station station);
    int deleteStationBatchByIds(List<String> stationIds);
    Station getStationById(String stationId);
    int modifyStation(Station station);
    int modifyStationBatch(StationVo stationVo);
    PageInfo<StationInfo> getAllStation(String companyId, String producerId, StationVo stationVo, String order, String sortby, Integer page, Integer pageSize);
    List<Station> getStationDownInfo(String companyId);
    List<String> getStationIdList(String companyId,String stationId);
    Station getStationByName(String stationName);
		Integer getMaxStationId();
		String getCompanyIdbyMap(Map<String, String> stationCompanyMap, String consumerId);
		Map<String, Station> getStationByIdAndToMap(Company company, Map<String, Map<String, Station>> mapStation);
		List<Station> selectStationByCompanyId(String companyId);
		List<Station> selectStationByCompanyIds(List<String> companyId);
}
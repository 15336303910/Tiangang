package cn.plou.web.system.baseMessage.station.service.impl;
import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.idgenerater.service.IDGenerater;
import cn.plou.web.common.utils.CamelCaseUtil;
import cn.plou.web.common.utils.RedisUtil;
import cn.plou.web.common.utils.SerializeUtil;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.baseMessage.build.entity.Build;
import cn.plou.web.system.baseMessage.commuity.dao.CommuityMapper;
import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import cn.plou.web.system.baseMessage.company.entity.Company;
import cn.plou.web.system.baseMessage.company.service.impl.CompanyServiceImpl;
import cn.plou.web.system.baseMessage.pipe.dao.PipeMapper;
import cn.plou.web.system.baseMessage.pipe.vo.PipeInfo;
import cn.plou.web.system.baseMessage.producer.dao.ProducerMapper;
import cn.plou.web.system.baseMessage.station.dao.StationMapper;
import cn.plou.web.system.baseMessage.station.entity.Station;
import cn.plou.web.system.baseMessage.station.service.StationService;
import cn.plou.web.system.baseMessage.station.vo.StationInfo;
import cn.plou.web.system.baseMessage.station.vo.StationVo;
import cn.plou.web.system.baseMessage.system.dao.SystemMapper;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.impl.TypeMstServiceImpl;
import cn.plou.web.system.meterMessage.mbus.service.impl.MbusServiceImpl;
import cn.plou.web.system.meterMessage.mbus.vo.MbusInfo;
import cn.plou.web.system.meterMessage.meter.service.impl.MeterServiceImpl;
import cn.plou.web.system.meterMessage.meter.vo.MeterInfo;
import cn.plou.web.system.meterMessage.pipeDevice.dao.PipeDeviceMapper;
import cn.plou.web.system.meterMessage.pipeDevice.vo.PipeDeviceInfo;
import cn.plou.web.system.permission.rlRoleData.dao.RlRoleDataMapper;
import cn.plou.web.system.permission.rlRoleData.entity.RlRoleData;
import cn.plou.web.system.permission.rlUserRole.service.RlUserRoleService;
import cn.plou.web.system.permission.userPageHistory.entity.UserPageHistory;
import cn.plou.web.system.permission.userPageHistory.service.impl.UserPageHistoryServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.*;

@Component
public class StationServiceImpl implements StationService {

    @Autowired
    StationMapper stationMapper;
    @Autowired
    private CompanyServiceImpl companyServiceImpl;
    @Autowired
    private CommuityMapper commuityMapper;
    @Autowired
    private SystemMapper systemMapper;
    @Autowired
    private PipeMapper pipeMapper;
    @Autowired
    private PipeDeviceMapper pipeDeviceMapper;
    @Autowired
    private RlRoleDataMapper rlRoleDataMapper;
    @Autowired
    private TypeMstServiceImpl typeMstServiceImpl;
    @Autowired
    private MbusServiceImpl mbusServiceImpl;
    @Autowired
    private MeterServiceImpl meterServiceImpl;
    @Autowired
    private UserPageHistoryServiceImpl userPageHistoryServiceImpl;
    @Autowired
    private RlUserRoleService userRoleService;
    @Autowired
    private ProducerMapper producerMapper;
    @Autowired
    private IDGenerater idGenerater;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addStation(Station station) {
//        String companyId = producerMapper.selectByPrimaryKey(station.getProducerId()).getCompanyId();

        String id = idGenerater.generateStationId();
        station.setStationId(id);
//        station.setCompanyId(companyId);
        station.setBusinessType("business_type_1");
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("station");
        userPageHistory.setAct("addStation");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        station.setCreateUser(UserUtils.getUserId());
        station.setCreateDate(new Date());
        return stationMapper.insertSelective(station);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteStationBatchByIds(List<String> stationIds) {
        List<PipeDeviceInfo> pipeDeviceInfoList = pipeDeviceMapper.selectAllPipeDevice(null, stationIds, null, null, null, null, null);
        if (pipeDeviceInfoList.size() > 0) {
            throw new BusinessException("站下有关联项，不能删除");
        }
        for (String stationId : stationIds) {
            List<Commuity> commuityList = commuityMapper.selectTreeList(stationId,null);
            if (commuityList.size() > 0) {
                throw new BusinessException("站下有关联项，不能删除");
            }
            List<PipeInfo> pipeInfoList = pipeMapper.selectAllPipe(null, null, null, null, null, null, stationId);
            if(pipeInfoList!=null){
                if (pipeInfoList.size() > 0) {
                    throw new BusinessException("站下有关联项，不能删除");
                }
            }
            List<MbusInfo> mbusInfoList = mbusServiceImpl.getAllMbus(null, null, null, stationId, null, null, null, null, null, null, null).getMbusInfoList();
            if (mbusInfoList.size() > 0) {
                throw new BusinessException("站下有关联项，不能删除");
            }
            List<MeterInfo> meterInfoList = meterServiceImpl.getAllMeter(null, null, null, null, null, stationId, null, null, null, null, null).getMeterInfoList();
            if (meterInfoList.size() > 0) {
                throw new BusinessException("站下有关联项，不能删除");
            }
            if(rlRoleDataMapper.selectAllRlRoleData().contains(stationId)){
                rlRoleDataMapper.deleteRlRoleDataByColumn(stationId);
            }
        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("station");
        userPageHistory.setAct("deleteStationBatchByIds");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return stationMapper.deleteStationBatchByIds(stationIds);
    }

    @Override
    public Station getStationById(String stationId) {
        return stationMapper.selectByPrimaryKey(stationId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyStation(Station station) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("station");
        userPageHistory.setAct("modifyStation");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        station.setUpdateUser(UserUtils.getUserId());
        station.setUpdateDate(new Date());
        return stationMapper.updateByPrimaryKeySelective(station);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyStationBatch(StationVo stationVo) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("station");
        userPageHistory.setAct("modifyStationBatch");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        stationVo.setUpdateUser(UserUtils.getUserId());
        stationVo.setUpdateDate(new Date());
        return stationMapper.updateStationBatch(stationVo);
    }

    @Override
    public PageInfo<StationInfo> getAllStation(String companyId, String producerId, StationVo stationVo, String order, String sortby, Integer page, Integer pageSize) {
        List<StationInfo> stationInfoList = new ArrayList<StationInfo>();
        PageInfo<StationInfo> pageInfo = new PageInfo<>();
        sortby = CamelCaseUtil.toUnderscoreCase(sortby);
        if (sortby != null) {
            if (sortby.equals("station_type_name")) {
                sortby = "station_type";
            }
            if (sortby.equals("business_type_name")) {
                sortby = "business_type";
            }
            if(sortby.equals("station_type")){
            	sortby = "station_type_id";
            }
        }
//        if(page!=null) {
//            page = (page - 1) * pageSize;
//        }
        if (companyId != null) {
            List<String> stationIds = getStationIdList(companyId, null);
            if (stationIds.size() > 0) {
                PageHelper.startPage(page, pageSize);
                stationInfoList = stationMapper.selectStationByStationIds(stationIds, producerId, stationVo, order, sortby, page, pageSize);
                pageInfo = new PageInfo<>(stationInfoList);
            }
        } else {
            List<String> stationIds = getStationIdList(null, null);
            if(stationIds.size()>0){
                PageHelper.startPage(page, pageSize);
                stationInfoList = stationMapper.selectStationByStationIds(stationIds, producerId, stationVo, order, sortby, page, pageSize);
                pageInfo = new PageInfo<>(stationInfoList);
            }
        }
        if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
            List<TypeMst> typeMstList = typeMstServiceImpl.getAllTypeMstByRedis();
            for (StationInfo stationInfo : pageInfo.getList()) {
                stationInfo.setStationTypeName(typeMstServiceImpl.getTypeNameById(typeMstList, stationInfo.getStationTypeId()));
                stationInfo.setBusinessTypeName(typeMstServiceImpl.getTypeNameById(typeMstList, stationInfo.getBusinessType()));
//                if (stationInfo.getStationTypeId() != null) {
//                    if (typeMstServiceImpl.getTypeMstById(stationInfo.getStationTypeId()) != null) {
//                        stationInfo.setStationTypeName(typeMstServiceImpl.getTypeMstById(stationInfo.getStationTypeId()).getTypeName());
//                    }
//                }
//                if (stationInfo.getBusinessType() != null) {
//                    if (typeMstServiceImpl.getTypeMstById(stationInfo.getBusinessType()) != null) {
//                        stationInfo.setBusinessTypeName(typeMstServiceImpl.getTypeMstById(stationInfo.getBusinessType()).getTypeName());
//                    }
//                }
            }
        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("station");
        userPageHistory.setAct("getAllStation");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return pageInfo;
    }

    @Override
    public List<Station> getStationDownInfo(String companyId) {
        List<Station> stationList = new ArrayList<>();
        String roleId = userRoleService.getRoleByUserId(UserUtils.getUserId());
        if("1".equals(roleId)){
        	return stationMapper.selectStationByCompanyId(companyId);
        }
        if(rlRoleDataMapper.selectRlRoleDataByRoleId(userRoleService.getRoleByUserId(UserUtils.getUserId())).size()>0){
            if(rlRoleDataMapper.selectRlRoleDataByRoleId(userRoleService.getRoleByUserId(UserUtils.getUserId())).get(0).getColumnType().equals("A")){
                return stationMapper.selectStationByCompanyId(companyId);
            }else{
                for(RlRoleData rlRoleData:rlRoleDataMapper.selectRlRoleDataByRoleId(userRoleService.getRoleByUserId(UserUtils.getUserId()))){
                    if(rlRoleData.getColumnType().equals("E")){
                        stationList.add(stationMapper.selectByPrimaryKey(rlRoleData.getColumnValue()));
                    }
                    if(rlRoleData.getColumnType().equals("F")){
                        stationList.add(stationMapper.selectByPrimaryKey(commuityMapper.selectById(rlRoleData.getColumnValue()).getStationId()));
                    }
                }
            }
        }
        return stationList;
    }

    public List<String> getStationIdList(String companyId, String stationId) {
        List<String> stationIds = new ArrayList<>();
        if (companyId != null) {
            List<String> companyIds = companyServiceImpl.getCompanyIdsByCompanyId(companyId);

            List<Station> allStationByCompany = new ArrayList<Station>();
            for (String id : companyIds) {
                if (getStationDownInfo(id).size() > 0) {
                    allStationByCompany.addAll(getStationDownInfo(id));
                }
            }
            if (allStationByCompany.size() > 0) {
                for (Station station : allStationByCompany) {
                    if (companyServiceImpl.getAllStationIdsByRole(userRoleService.getRoleByUserId(UserUtils.getUserId())).contains(station.getStationId())) {
                        stationIds.add(station.getStationId());
                    }
                }
            }
        } else if (stationId != null) {
            stationIds.add(stationId);
        } else if (stationId == null && companyId == null) {
            stationIds.addAll(companyServiceImpl.getAllStationIdsByRole(userRoleService.getRoleByUserId(UserUtils.getUserId())));
        }
        return stationIds;
    }

    @Override
    public Station getStationByName(String stationName) {
        return stationMapper.selectByName(stationName);
    }

		@Override
		public Integer getMaxStationId() {
			Integer index = 0;
			String id = stationMapper.getMaxStationId();
			if (id != null && !id.isEmpty()) {
				index = Integer.valueOf(id);
			}
			return index;
		}

		@Override
		public String getCompanyIdbyMap(Map<String, String> stationCompanyMap, String consumerId) {
			String id = stationCompanyMap.get(consumerId);
			if (id == null) {
				Station stat = getStationById(consumerId);
				if (stat != null) {
					id = stat.getCompanyId();
					stationCompanyMap.put(consumerId, id);
				}
			}
			return id;
		}

		@Override
		public Map<String, Station> getStationByIdAndToMap(Company company, Map<String, Map<String, Station>> mapStation) {
			
			Map<String, Station> map = mapStation.get(company.getCompanyId());
			if(map == null){
				map = new HashMap<String, Station>();
				List<Station> list = getStationDownInfo(company.getCompanyId());
				for(Station info:list){
					map.put(info.getStationName(), info);
				}
				mapStation.put(company.getCompanyId(), map);
			}
			return map;
		}
		
		@Override
		public List<Station> selectStationByCompanyId(String companyId){
			return stationMapper.selectStationByCompanyId(companyId);
		}
		
		@Override
		public List<Station> selectStationByCompanyIds(List<String> companyIds){
			return stationMapper.selectStationByCompanyIds(companyIds);
		}
}

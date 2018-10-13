package cn.plou.web.system.baseMessage.producer.service.impl;

import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.idgenerater.service.IDGenerater;
import cn.plou.web.common.utils.CamelCaseUtil;
import cn.plou.web.common.utils.RedisUtil;
import cn.plou.web.common.utils.SerializeUtil;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.baseMessage.commuity.dao.CommuityMapper;
import cn.plou.web.system.baseMessage.company.dao.CompanyMapper;
import cn.plou.web.system.baseMessage.company.entity.Company;
import cn.plou.web.system.baseMessage.company.service.impl.CompanyServiceImpl;
import cn.plou.web.system.baseMessage.pipe.dao.PipeMapper;
import cn.plou.web.system.baseMessage.pipe.vo.PipeInfo;
import cn.plou.web.system.baseMessage.producer.dao.ProducerMapper;
import cn.plou.web.system.baseMessage.producer.entity.Producer;
import cn.plou.web.system.baseMessage.producer.service.ProducerService;
import cn.plou.web.system.baseMessage.producer.vo.ProducerInfo;
import cn.plou.web.system.baseMessage.producer.vo.ProducerVo;
import cn.plou.web.system.baseMessage.producer.vo.StructureInfo;
import cn.plou.web.system.baseMessage.station.dao.StationMapper;
import cn.plou.web.system.baseMessage.station.service.impl.StationServiceImpl;
import cn.plou.web.system.baseMessage.station.vo.StationInfo;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.impl.TypeMstServiceImpl;
import cn.plou.web.system.meterMessage.pipeDevice.dao.PipeDeviceMapper;
import cn.plou.web.system.meterMessage.pipeDevice.vo.PipeDeviceInfo;
import cn.plou.web.system.permission.rlRoleData.dao.RlRoleDataMapper;
import cn.plou.web.system.permission.rlRoleData.entity.RlRoleData;
import cn.plou.web.system.permission.rlUserRole.dao.RlUserRoleMapper;
import cn.plou.web.system.permission.userPageHistory.entity.UserPageHistory;
import cn.plou.web.system.permission.userPageHistory.service.impl.UserPageHistoryServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class ProduceServiceImpl implements ProducerService {

    @Autowired
    private ProducerMapper producerMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private CompanyServiceImpl companyServiceImpl;
    @Autowired
    private StationMapper stationMapper;
    @Autowired
    private StationServiceImpl stationServiceImpl;
    @Autowired
    CommuityMapper commuityMapper;
    @Autowired
    private TypeMstServiceImpl typeMstServiceImpl;
    @Autowired
    private RlRoleDataMapper rlRoleDataMapper;
    @Autowired
    private RlUserRoleMapper rlUserRoleMapper;
    @Autowired
    private PipeMapper pipeMapper;
    @Autowired
    private PipeDeviceMapper pipeDeviceMapper;
    @Autowired
    private UserPageHistoryServiceImpl userPageHistoryServiceImpl;
    @Autowired
    private IDGenerater idGenerater;
    @Override
    public PageInfo<ProducerInfo> getAllProducer(String companyId, String producerId, ProducerVo producerVo, String order, String sortby, Integer page, Integer pageSize) {
        List<ProducerInfo> producerInfoList = new ArrayList<ProducerInfo>();
        List<String> companyIds = new ArrayList<String>();
        PageInfo<ProducerInfo> pageInfo = null;
        sortby = CamelCaseUtil.toUnderscoreCase(sortby);
        if (sortby != null) {
            if (sortby.equals("producer_type_name")) {
                sortby = "producer_type";
            }
            if (sortby.equals("business_type_name")) {
                sortby = "business_type";
            }
        }
//        if(page!=null) {
//            page = (page - 1) * pageSize;
//        }

        if (rlUserRoleMapper.selectRoleByUserId(UserUtils.getUserId()).equals("1")) {
            companyIds = companyServiceImpl.getAllCompanyIds(companyId);
            PageHelper.startPage(page, pageSize);
            producerInfoList = producerMapper.selectAllProducer(companyIds, producerId, producerVo, order, sortby, page, pageSize);
            pageInfo = new PageInfo<>(producerInfoList);
        } else {
            List<RlRoleData> roleDataList = rlRoleDataMapper.selectRlRoleDataByUserId(UserUtils.getUserId());
            if (roleDataList.size() != 0) {
                if (roleDataList.get(0).getColumnType().equals("A")) {
                    if (companyId == null) {
                        companyIds = companyServiceImpl.getAllCompanyIds(null);
                        PageHelper.startPage(page, pageSize);
                        producerInfoList = producerMapper.selectAllProducer(companyIds, producerId, producerVo, order, sortby, page, pageSize);
                        pageInfo = new PageInfo<>(producerInfoList);
                    } else {
                        companyIds = companyServiceImpl.getAllCompanyIds(companyId);
                        PageHelper.startPage(page, pageSize);
                        producerInfoList = producerMapper.selectAllProducer(companyIds, producerId, producerVo, order, sortby, page, pageSize);
                        pageInfo = new PageInfo<>(producerInfoList);
                    }
                }
                if (roleDataList.get(0).getColumnType().equals("E") || roleDataList.get(0).getColumnType().equals("F")) {
                    if (companyId == null) {
                        List<String> stationIdList = stationServiceImpl.getStationIdList(null, null);
                        List<String> producerIds = new ArrayList<String>();
                        for (String stationId : stationIdList) {
                            producerIds.add(stationMapper.selectByPrimaryKey(stationId).getProducerId());
                        }
                        PageHelper.startPage(page, pageSize);
                        producerInfoList = producerMapper.selectProducerByProducerIds(producerIds, producerId, producerVo, order, sortby, page, pageSize);
                        pageInfo = new PageInfo<>(producerInfoList);
                    } else {
                        List<String> stationIdList = stationServiceImpl.getStationIdList(companyId, null);
                        List<String> producerIds = new ArrayList<String>();
                        for (String stationId : stationIdList) {
                            producerIds.add(stationMapper.selectByPrimaryKey(stationId).getProducerId());
                        }
                        PageHelper.startPage(page, pageSize);
                        producerInfoList = producerMapper.selectProducerByProducerIds(producerIds, producerId, producerVo, order, sortby, page, pageSize);
                        pageInfo = new PageInfo<>(producerInfoList);
                    }
                }
            }
        }
        if(pageInfo!=null){
            if (pageInfo.getList().size() > 0) {
                List<TypeMst> typeMstList = typeMstServiceImpl.getAllTypeMstByRedis();
                for (ProducerInfo producerInfo : pageInfo.getList()) {
                    producerInfo.setProducerTypeName(typeMstServiceImpl.getTypeNameById(typeMstList, producerInfo.getProducerTypeId()));
                    producerInfo.setBusinessTypeName(typeMstServiceImpl.getTypeNameById(typeMstList, producerInfo.getBusinessType()));
//                if (producerInfo.getProducerTypeId() != null) {
//                    if (typeMstServiceImpl.getTypeMstById(producerInfo.getProducerTypeId()) != null) {
//                        producerInfo.setProducerTypeName(typeMstServiceImpl.getTypeMstById(producerInfo.getProducerTypeId()).getTypeName());
//                    }
//                }
//                if (producerInfo.getBusinessType() != null) {
//                    if (typeMstServiceImpl.getTypeMstById(producerInfo.getBusinessType()) != null) {
//                        producerInfo.setBusinessTypeName(typeMstServiceImpl.getTypeMstById(producerInfo.getBusinessType()).getTypeName());
//                    }
//                }
                }
            }
        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("producer");
        userPageHistory.setAct("getAllProducer");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return pageInfo;
    }

    @Override
    public Producer getProducerById(String producerId) {
        return producerMapper.selectByPrimaryKey(producerId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteProducerBatchByIds(List<String> producerIds) {
        for (String producerId : producerIds) {
            List<StationInfo> stationInfoList = stationMapper.selectAllStation(null, producerId, null, null, null);
            if (stationInfoList.size() > 0) {
                throw new BusinessException("源下有关联项，不能删除");
            }
            List<ProducerInfo> producerInfoList = producerMapper.selectAllProducer(null, producerId, null, null, null, null, null);
            if (producerInfoList.size() > 0) {
                throw new BusinessException("源下有关联项，不能删除");
            }
            List<PipeInfo> pipeInfoList = pipeMapper.selectAllPipe(null, null, null, null, null, null, producerId);
            if (pipeInfoList.size() > 0) {
                throw new BusinessException("源下有关联项，不能删除");
            }
        }
        List<PipeDeviceInfo> pipeDeviceInfoList = pipeDeviceMapper.selectAllPipeDevice(null, producerIds, null, null, null, null, null);
        if (pipeDeviceInfoList.size() > 0) {
            throw new BusinessException("源下有关联项，不能删除");
        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("producer");
        userPageHistory.setAct("deleteProducerBatchByIds");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return producerMapper.deleteProducerBatchByIds(producerIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addProducer(Producer producer) {
    	 	String id = idGenerater.generateProducerId();
        producer.setProducerId(id);
        producer.setBusinessType("business_type_1");

        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("producer");
        userPageHistory.setAct("addProducer");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        producer.setCreateUser(UserUtils.getUserId());
        producer.setCreateDate(new Date());
        return producerMapper.insertSelective(producer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyProducerBatch(ProducerVo producerVo) {
        if (producerVo.getSuperProducerId() != null) {
            if (producerMapper.selectByPrimaryKey(producerVo.getProducerIds().get(0)).getCompanyId().equals(producerVo.getCompanyId())) {
                for (String producerId : producerVo.getProducerIds()) {
                    List<ProducerInfo> producerInfoList = producerMapper.selectAllProducer(null, producerId, null, null, null, null, null);
                    if (producerInfoList.size() > 0) {
                        for (ProducerInfo producerInfo : producerInfoList) {
                            if (producerVo.getSuperProducerId().equals(producerInfo.getProducerId()) || producerVo.getSuperProducerId().equals(producerId)) {
                                throw new BusinessException("不能把隶属热源改为自身或其下级源");
                            }
                        }
                    }
                }
            } else {
                for (String producerId : producerVo.getProducerIds()) {
                    List<StationInfo> stationInfoList = stationMapper.selectAllStation(null, producerId, null, null, null);
                    if (stationInfoList.size() > 0) {
                        throw new BusinessException("源下有关联项，不能修改公司");
                    }
                    List<ProducerInfo> producerInfoList = producerMapper.selectAllProducer(null, producerId, null, null, null, null, null);
                    if (producerInfoList.size() > 0) {
                        throw new BusinessException("源下有关联项，不能修改公司");
                    }
                    List<PipeInfo> pipeInfoList = pipeMapper.selectAllPipe(null, null, null, null, null, null, producerId);
                    if (pipeInfoList.size() > 0) {
                        throw new BusinessException("源下有关联项，不能修改公司");
                    }
                }
                List<PipeDeviceInfo> pipeDeviceInfoList = pipeDeviceMapper.selectAllPipeDevice(null, producerVo.getProducerIds(), null, null, null, null, null);
                if (pipeDeviceInfoList.size() > 0) {
                    throw new BusinessException("源下有关联项，不能修改公司");
                }
            }
        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("producer");
        userPageHistory.setAct("modifyProducerBatch");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        producerVo.setUpdateUser(UserUtils.getUserId());
        producerVo.setUpdateDate(new Date());
        return producerMapper.updateProducerBatch(producerVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyProducer(Producer producer) {
        if (producer.getSuperProducerId() != null) {
            if (producerMapper.selectByPrimaryKey(producer.getProducerId()).getCompanyId().equals(producer.getCompanyId())) {
                List<ProducerInfo> producerInfoList = producerMapper.selectAllProducer(null, producer.getProducerId(), null, null, null, null, null);
                if (producerInfoList.size() > 0) {
                    for (ProducerInfo producerInfo : producerInfoList) {
                        if (producer.getSuperProducerId().equals(producerInfo.getProducerId()) || producer.getSuperProducerId().equals(producer.getProducerId())) {
                            throw new BusinessException("不能把隶属热源改为自身或其下级源");
                        }
                    }
                }
            } else {
                List<StationInfo> stationInfoList = stationMapper.selectAllStation(null, producer.getProducerId(), null, null, null);
                if (stationInfoList.size() > 0) {
                    throw new BusinessException("源下有关联项，不能修改公司");
                }
                List<ProducerInfo> producerInfoList = producerMapper.selectAllProducer(null, producer.getProducerId(), null, null, null, null, null);
                if (producerInfoList.size() > 0) {
                    throw new BusinessException("源下有关联项，不能修改公司");
                }
                List<PipeInfo> pipeInfoList = pipeMapper.selectAllPipe(null, null, null, null, null, null, producer.getProducerId());
                if (pipeInfoList.size() > 0) {
                    throw new BusinessException("源下有关联项，不能修改公司");
                }
            }
        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("producer");
        userPageHistory.setAct("modifyProducer");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        producer.setUpdateUser(UserUtils.getUserId());
        producer.setUpdateDate(new Date());
        return producerMapper.updateByPrimaryKeySelective(producer);
    }

    @Override
    public List<Producer> getProducerByCompanyId(String companyId) {
        return producerMapper.selectProducerByCompanyId(companyId);
    }

    @Override
    public StructureInfo getStructureByProducerId(String producerId) {
        StructureInfo structureInfo = new StructureInfo();
        String companyId = getProducerById(producerId).getCompanyId();
        structureInfo.setCompanyId(companyId);
        List<Company> companyList = companyMapper.selectChildrenCompany(companyId);
        companyList.add(companyServiceImpl.get(companyId));
        structureInfo.setCompanyList(companyList);
        structureInfo.setProducerId(producerId);
        structureInfo.setProducerList(getProducerByCompanyId(companyId));
        return structureInfo;
    }

    @Override
    public Producer getProducerByName(String producerName) {
        return null;
    }

		@Override
		public Integer getMaxProducerId() {
			Integer index = 0;
			String id = producerMapper.getMaxProducerId();
			if (id != null && !id.isEmpty()) {
				index = Integer.valueOf(id);
			}
			return index;
		}
}

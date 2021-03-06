package cn.plou.web.system.meterMessage.meterModifyData.service.impl;

import cn.plou.web.common.utils.CamelCaseUtil;
import cn.plou.web.common.utils.RedisUtil;
import cn.plou.web.common.utils.SerializeUtil;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.baseMessage.build.dao.BuildMapper;
import cn.plou.web.system.baseMessage.commuity.dao.CommuityMapper;
import cn.plou.web.system.baseMessage.company.service.impl.CompanyServiceImpl;
import cn.plou.web.system.baseMessage.house.dao.HouseMapper;
import cn.plou.web.system.baseMessage.station.dao.StationMapper;
import cn.plou.web.system.baseMessage.unit.dao.UnitMapper;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.impl.TypeMstServiceImpl;
import cn.plou.web.system.meterMessage.meter.service.impl.MeterServiceImpl;
import cn.plou.web.system.meterMessage.meter.vo.MeterInfo;
import cn.plou.web.system.meterMessage.meterModifyData.dao.MeterModifyDataMapper;
import cn.plou.web.system.meterMessage.meterModifyData.entity.MeterModifyData;
import cn.plou.web.system.meterMessage.meterModifyData.service.MeterModifyDataService;
import cn.plou.web.system.meterMessage.meterModifyData.vo.MeterModifyDataInfo;
import cn.plou.web.system.meterMessage.meterModifyData.vo.MeterModifyDataVo;
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
public class MeterModifyDataServiceImpl implements MeterModifyDataService {

    @Autowired
    MeterModifyDataMapper meterModifyDataMapper;
    @Autowired
    StationMapper stationMapper;
    @Autowired
    CommuityMapper commuityMapper;
    @Autowired
    BuildMapper buildMapper;
    @Autowired
    UnitMapper unitMapper;
    @Autowired
    HouseMapper houseMapper;
    @Autowired
    TypeMstServiceImpl typeMstServiceImpl;
    @Autowired
    CompanyServiceImpl companyServiceImpl;
    @Autowired
    MeterServiceImpl meterServiceImpl;
    @Autowired
    private UserPageHistoryServiceImpl userPageHistoryServiceImpl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addMeterModifyData(MeterModifyData meterModifyData) {
        List<MeterModifyDataInfo> meterModifyDataInfoList = meterModifyDataMapper.selectAllMeterModifyData(null, null, null, null,null,null);
        if(meterModifyDataInfoList.size()==0){
            meterModifyData.setRowno("1");
        }else{
            List<Integer> ids=new ArrayList<Integer>();
            for(MeterModifyDataInfo meterModifyDataInfo:meterModifyDataInfoList){
                ids.add(Integer.valueOf(meterModifyDataInfo.getRowno()));
            }
            Collections.sort(ids);
            Collections.reverse(ids);
            meterModifyData.setRowno(""+(ids.get(0)+1));
        }
        meterModifyData.setAddress2nd(meterServiceImpl.getMeterById(meterModifyData.getMeterId()).getAddress2nd());
        if(meterModifyData.getConsumerId().length()==6){
        meterModifyData.setCompanyId(stationMapper.selectByPrimaryKey(meterModifyData.getConsumerId()).getCompanyId());
        }if(meterModifyData.getConsumerId().length()>6){
            meterModifyData.setCompanyId(meterModifyData.getConsumerId().substring(0,5));
        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("meterModify");
        userPageHistory.setAct("addMeterModifyData");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        meterModifyData.setCreateUser(UserUtils.getUserId());
        meterModifyData.setCreateDate(new Date());
        return meterModifyDataMapper.insertSelective(meterModifyData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageInfo<MeterModifyDataInfo> getAllMeterModifyData(String companyId, String stationId, String commuityId, String buildingNo, String unitId, String cosumerId,
                                                               MeterModifyDataVo searchCondition, String order, String sortby, Integer page, Integer pageSize) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("meterModify");
        userPageHistory.setAct("getAllMeterModifyData");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);

        List<String> meterIds = new ArrayList<String>();
        List<MeterInfo> meterInfoList = meterServiceImpl.getAllMeter(null,null,null, null, companyId, stationId, commuityId, buildingNo, unitId, cosumerId, null).getMeterInfoList();
        if(meterInfoList.size()>0) {
            for (MeterInfo meterInfo : meterInfoList) {
                meterIds.add(meterInfo.getMeterId());
            }
//        if(page!=null) {
//            page = (page - 1) * pageSize;
//        }
            sortby = CamelCaseUtil.toUnderscoreCase(sortby);
            if (sortby != null) {
                if (sortby.equals("meter_type_name")) {
                    sortby = "meter_type";
                }
                if (sortby.equals("flag_name")) {
                    sortby = "flag";
                }
                if (sortby.equals("factory_name")) {
                    sortby = "factory";
                }
            }
            PageHelper.startPage(page, pageSize);
            List<MeterModifyDataInfo> meterModifyDataInfoList = meterModifyDataMapper.selectAllMeterModifyData(meterIds, searchCondition, order, sortby, page, pageSize);
            PageInfo<MeterModifyDataInfo> pageInfo = new PageInfo<>(meterModifyDataInfoList);
            List<TypeMst> typeMstList = typeMstServiceImpl.getAllTypeMst();
            for (MeterModifyDataInfo meterModifyDataInfo : pageInfo.getList()) {
                meterModifyDataInfo.setMeterTypeName(typeMstServiceImpl.getTypeNameById(typeMstList,meterModifyDataInfo.getMeterType()));
                meterModifyDataInfo.setFlagName(typeMstServiceImpl.getTypeNameById(typeMstList,meterModifyDataInfo.getFlag()));
                meterModifyDataInfo.setFactoryName(typeMstServiceImpl.getTypeNameById(typeMstList,meterModifyDataInfo.getFactory()));
//                if (meterModifyDataInfo.getMeterType() != null) {
//                    if (typeMstServiceImpl.getTypeMstById(meterModifyDataInfo.getMeterType()) != null) {
//                        meterModifyDataInfo.setMeterTypeName(typeMstServiceImpl.getTypeMstById(meterModifyDataInfo.getMeterType()).getTypeName());
//                    }
//                }
//                if (meterModifyDataInfo.getFlag() != null) {
//                    if (typeMstServiceImpl.getTypeMstById(meterModifyDataInfo.getFlag()) != null) {
//                        meterModifyDataInfo.setFlagName(typeMstServiceImpl.getTypeMstById(meterModifyDataInfo.getFlag()).getTypeName());
//                    }
//                }
//                if (meterModifyDataInfo.getFactory() != null) {
//                    if (typeMstServiceImpl.getTypeMstById(meterModifyDataInfo.getFactory()) != null) {
//                        meterModifyDataInfo.setFactoryName(typeMstServiceImpl.getTypeMstById(meterModifyDataInfo.getFactory()).getTypeName());
//                    }
//                }
                String id = meterModifyDataInfo.getConsumerId();
                if (id.length() == 6) {
                    meterModifyDataInfo.setConsumerName(stationMapper.selectByPrimaryKey(id).getStationName());
                }
                if (id.length() == 10) {
                    meterModifyDataInfo.setConsumerName(commuityMapper.selectById(id).getCommuityName());
                }
                if (id.length() == 13) {
                    meterModifyDataInfo.setConsumerName(buildMapper.selectByPrimaryKey(id).getBuildingName());
                }
                if (id.length() == 15) {
                    meterModifyDataInfo.setConsumerName(unitMapper.selectByPrimaryKey(id).getUnitName());
                }
                if (id.length() == 19) {
                    meterModifyDataInfo.setConsumerName(houseMapper.selectByPrimaryKey(id).getRoomName());
                }
            }
        return pageInfo;
        } else {
            return new PageInfo<>();
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyMeterModifyDataBatch(MeterModifyDataVo meterModifyDataVo) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("meterModify");
        userPageHistory.setAct("modifyMeterModifyDataBatch");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        meterModifyDataVo.setUpdateUser(UserUtils.getUserId());
        meterModifyDataVo.setUpdateDate(new Date());
        return meterModifyDataMapper.updateMeterModifyDataBatch(meterModifyDataVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyMeterModifyData(MeterModifyData meterModifyData) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("meterModify");
        userPageHistory.setAct("modifyMeterModifyData");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        meterModifyData.setUpdateUser(UserUtils.getUserId());
        meterModifyData.setUpdateDate(new Date());
        return meterModifyDataMapper.updateByPrimaryKeySelective(meterModifyData);
    }
}

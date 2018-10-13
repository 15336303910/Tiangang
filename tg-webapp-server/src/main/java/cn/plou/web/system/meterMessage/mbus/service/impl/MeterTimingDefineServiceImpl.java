package cn.plou.web.system.meterMessage.mbus.service.impl;

import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.meterMessage.mbus.dao.MbusMapper;
import cn.plou.web.system.meterMessage.mbus.dao.MeterTimingDefineMapper;
import cn.plou.web.system.meterMessage.mbus.entity.Mbus;
import cn.plou.web.system.meterMessage.mbus.entity.MeterTimingDefine;
import cn.plou.web.system.meterMessage.mbus.service.MbusService;
import cn.plou.web.system.meterMessage.mbus.service.MeterTimingDefineService;
import cn.plou.web.system.meterMessage.mbus.vo.MbusInfo;
import cn.plou.web.system.meterMessage.mbus.vo.MbusListInfo;
import cn.plou.web.system.meterMessage.mbus.vo.MeterTimingDefineVo;
import cn.plou.web.system.meterMessage.mbus.vo.TimingTask;
import cn.plou.web.system.permission.rlRoleData.service.RlRoleDataService;
import cn.plou.web.system.permission.role.service.DataRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class MeterTimingDefineServiceImpl implements MeterTimingDefineService {
    @Autowired
    private MeterTimingDefineMapper meterTimingDefineMapper;
    @Autowired
    private MbusMapper mbusMapper;
    @Autowired
    private RlRoleDataService rlRoleDataService;
    @Autowired
    private DataRoleService dataRoleService;
    @Autowired
    private MbusService mbusService;

    @Override
    public int deleteTimingDefineBatch(List<String> mbusCodes) {
        return 0;
    }

    @Override
    public int addOrDelTimingDefineBatch(MeterTimingDefineVo meterTimingDefineVo) {
        List<String> mbusCodes = new ArrayList<>();
        if(meterTimingDefineVo.getMbusCodes()!=null){
            mbusCodes.addAll(meterTimingDefineVo.getMbusCodes());
        }
        List<MeterTimingDefine> meterTimingDefineListNow = null;
        if(mbusCodes.size() != 0){
        	//meterTimingDefineListNow = meterTimingDefineMapper.selectAllByDefineVo(meterTimingDefineVo.getMbusCodes(),meterTimingDefineVo.getTimingTasks(),meterTimingDefineVo.getUpCommMode(),meterTimingDefineVo.getIntervals());
            meterTimingDefineListNow = meterTimingDefineMapper.selectAllByDefineVo(meterTimingDefineVo.getMbusCodes());
        }
        List<MeterTimingDefine> meterTimingDefineListNew = new ArrayList<>();
        List<String> needDeletePrimaryIdList = new ArrayList<>();
        List<MeterTimingDefine> needInsertList = new ArrayList<>();
        MbusListInfo mbusListInfo = new MbusListInfo();
        if(meterTimingDefineVo.getConsumerId()!=null){
            String consumerId = meterTimingDefineVo.getConsumerId();
            if(consumerId.length()==5){
                mbusListInfo = mbusService.getAllMbus(null,null,consumerId,null,null,null,null,null,null,null,null);
            }else if(consumerId.length()==6){
                mbusListInfo = mbusService.getAllMbus(null,null,null,consumerId,null,null,null,null,null,null,null);
            }else if(consumerId.length()==10){
                mbusListInfo = mbusService.getAllMbus(null,null,null,null,consumerId,null,null,null,null,null,null);
            }else if(consumerId.length()==13){
                mbusListInfo = mbusService.getAllMbus(null,null,null,null,null,consumerId,null,null,null,null,null);
            }else if(consumerId.length()==15){
                mbusListInfo = mbusService.getAllMbus(null,null,null,null,null,null,consumerId,null,null,null,null);
            }else if(consumerId.length()==19){
                mbusListInfo = mbusService.getAllMbus(null,null,null,null,null,null,null,consumerId,null,null,null);
            }
        }
        for(MbusInfo mbusInfo:mbusListInfo.getMbusInfoList()){
            mbusCodes.add(mbusInfo.getMbusCode());
        }
        //根据传过来的参数获取全部任务列表
        for(String mbusCode:mbusCodes){
            Mbus mbus = mbusMapper.selectByMbusCode(mbusCode);
            if(meterTimingDefineVo.getTimingTasks()==null){
                MeterTimingDefine meterTimingDefine = new MeterTimingDefine();
                BeanUtils.copyProperties(meterTimingDefineVo,meterTimingDefine);
                meterTimingDefine.setMbusCode(mbusCode);
                meterTimingDefine.setCompanyId(mbus.getCompanyId());
                meterTimingDefine.setEquipmentNo(mbus.getEquipmentNo());
                meterTimingDefine.setPrimaryId(UUID.randomUUID().toString().replace("-",""));
                meterTimingDefine.setSendTimes(0);
                meterTimingDefineListNew.add(meterTimingDefine);
            }else{
                for(TimingTask timingTask:meterTimingDefineVo.getTimingTasks()){
                    MeterTimingDefine meterTimingDefine1 = new MeterTimingDefine();
                    BeanUtils.copyProperties(meterTimingDefineVo,meterTimingDefine1);
                    meterTimingDefine1.setTiming(timingTask.getTiming());
                    meterTimingDefine1.setOrderType(timingTask.getOrderType());
                    meterTimingDefine1.setMbusCode(mbusCode);
                    meterTimingDefine1.setCompanyId(mbus.getCompanyId());
                    meterTimingDefine1.setEquipmentNo(mbus.getEquipmentNo());
                    if(timingTask.getOrderType().equals("task_meter_0")){
                        meterTimingDefine1.setValidFlg(0);
                    }
                    meterTimingDefine1.setSendTimes(0);
                    //meterTimingDefine1.setPrimaryId(UUID.randomUUID().toString().replace("-",""));
                    meterTimingDefineListNew.add(meterTimingDefine1);
                }
            }
        }
        /*List<MeterTimingDefine> meterTimingDefineNewNoIdList = new ArrayList<>();
        for(MeterTimingDefine meterTimingDefineNewNoId:meterTimingDefineListNew){
            String primaryId = meterTimingDefineNewNoId.getPrimaryId();
            meterTimingDefineNewNoId.setPrimaryId(null);
            meterTimingDefineNewNoIdList.add(meterTimingDefineNewNoId);
            meterTimingDefineNewNoId.setPrimaryId(primaryId);
        }*/
        if(meterTimingDefineListNow!=null) {
            for (MeterTimingDefine meterTimingDefine : meterTimingDefineListNow) {
                String primaryId = meterTimingDefine.getPrimaryId();
                meterTimingDefine.setPrimaryId(null);
                if (!meterTimingDefineListNew.contains(meterTimingDefine)) {
                    needDeletePrimaryIdList.add(primaryId);
                }
            }
            for(MeterTimingDefine meterTimingDefine:meterTimingDefineListNew){
            /*String primaryId = meterTimingDefine.getPrimaryId();
            meterTimingDefine.setPrimaryId(null);*/
                if(!meterTimingDefineListNow.contains(meterTimingDefine)){
                    meterTimingDefine.setPrimaryId(UUID.randomUUID().toString().replace("-",""));
                    needInsertList.add(meterTimingDefine);
                }
            }
        }else{
            for(MeterTimingDefine meterTimingDefine:meterTimingDefineListNew){
                meterTimingDefine.setPrimaryId(UUID.randomUUID().toString().replace("-",""));
                needInsertList.add(meterTimingDefine);
            }
        }

        meterTimingDefineMapper.deleteTimingDefineBatch(needDeletePrimaryIdList);
        //meterTimingDefineMapper.insertSelectiveBatch2(needInsertList);
        meterTimingDefineMapper.insertSelectiveBatch3(needInsertList);
        return needDeletePrimaryIdList.size()+needInsertList.size();
    }

    @Override
    public List<MeterTimingDefine> getTimingDefineByMbusCodes(String mbusCode) {
        return meterTimingDefineMapper.selectAllTimigByMbusCode(mbusCode);
    }
}
package cn.plou.web.system.baseMessage.sewageStation.service.impl;

import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.utils.CamelCaseUtil;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.baseMessage.company.service.impl.CompanyServiceImpl;
import cn.plou.web.system.baseMessage.sewageStation.dao.SewageStationMapper;
import cn.plou.web.system.baseMessage.sewageStation.entity.SewageStation;
import cn.plou.web.system.baseMessage.sewageStation.service.SewageStationService;
import cn.plou.web.system.baseMessage.sewageStation.vo.SewageStationInfo;
import cn.plou.web.system.baseMessage.sewageStation.vo.SewageStationVo;
import cn.plou.web.system.permission.rlRoleData.dao.RlRoleDataMapper;
import cn.plou.web.system.permission.rlRoleData.entity.RlRoleData;
import cn.plou.web.system.permission.userPageHistory.entity.UserPageHistory;
import cn.plou.web.system.permission.userPageHistory.service.impl.UserPageHistoryServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class SewageStationServiceImpl implements SewageStationService {

    @Autowired
    SewageStationMapper sewageStationMapper;
    @Autowired
    private CompanyServiceImpl companyServiceImpl;
    @Autowired
    private RlRoleDataMapper rlRoleDataMapper;
    @Autowired
    private UserPageHistoryServiceImpl userPageHistoryServiceImpl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addSewageStation(SewageStation sewageStation) {
        List<SewageStationInfo> sewageStationInfoList = sewageStationMapper.selectAllSewageStation(null,null,null,null);
        List<Integer>sewageStationIds=new ArrayList<Integer>();
        String id="";
        if(sewageStationInfoList.size()==0){
            id="1";
        }else {
            for (SewageStationInfo sewageStationInfo : sewageStationInfoList) {
                sewageStationIds.add(Integer.valueOf(sewageStationInfo.getSewageStationId()));
            }
            Collections.sort(sewageStationIds);
            Collections.reverse(sewageStationIds);
            id=(sewageStationIds.get(0) + 1)+"";
        }
        sewageStation.setSewageStationId(id);
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("sewageStation");
        userPageHistory.setAct("addSewageStation");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        sewageStation.setCreateUser(UserUtils.getUserId());
        sewageStation.setCreateDate(new Date());
        return sewageStationMapper.insertSelective(sewageStation);
    }

    @Override
    public SewageStation getSewageStationById(String sewageStationId) {
        return sewageStationMapper.selectByPrimaryKey(sewageStationId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifySewageStation(SewageStation sewageStation) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("sewageStation");
        userPageHistory.setAct("modifySewageStation");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        sewageStation.setUpdateUser(UserUtils.getUserId());
        sewageStation.setUpdateDate(new Date());
        return sewageStationMapper.updateByPrimaryKeySelective(sewageStation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifySewageStationBatch(SewageStationVo sewageStationVo) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("sewageStation");
        userPageHistory.setAct("modifySewageStationBatch");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        sewageStationVo.setUpdateUser(UserUtils.getUserId());
        sewageStationVo.setUpdateDate(new Date());
        return sewageStationMapper.updateSewageStationBatch(sewageStationVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteSewageStationBatchByIds(List<String> sewageStationIds) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("sewageStation");
        userPageHistory.setAct("deleteSewageStationBatchByIds");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return sewageStationMapper.deleteSewageStationBatchByIds(sewageStationIds);
    }

    @Override
    public List<SewageStationInfo> getAllSewageStation(String companyId, SewageStationVo sewageStationVo, String order, String sortby) {
        List<SewageStationInfo> sewageStationInfoList=new ArrayList<SewageStationInfo>();
        List<String> companyIds = new ArrayList<String>();
        sortby = CamelCaseUtil.toUnderscoreCase(sortby);
        List<RlRoleData> roleDataList = rlRoleDataMapper.selectRlRoleDataByUserId(UserUtils.getUserId());
        if(roleDataList.size()!=0) {
                if (companyId == null) {
                    companyIds = companyServiceImpl.getAllCompanyIds(null);
                    sewageStationInfoList = sewageStationMapper.selectAllSewageStation(companyIds, sewageStationVo, order, sortby);
                }
             else {
                companyIds = companyServiceImpl.getAllCompanyIds(companyId);
                sewageStationInfoList = sewageStationMapper.selectAllSewageStation(companyIds, sewageStationVo, order, sortby);
            }
        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("sewageStation");
        userPageHistory.setAct("getAllSewageStation");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return sewageStationInfoList;
    }
}

package cn.plou.web.system.commonMessage.heatingTime.service.impl;

import cn.plou.web.common.utils.CamelCaseUtil;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.baseMessage.company.dao.CompanyMapper;
import cn.plou.web.system.baseMessage.company.entity.Company;
import cn.plou.web.system.baseMessage.company.service.impl.CompanyServiceImpl;
import cn.plou.web.system.baseMessage.company.vo.CompanyInfo;
import cn.plou.web.system.baseMessage.station.entity.Station;
import cn.plou.web.system.baseMessage.station.service.StationService;
import cn.plou.web.system.commonMessage.heatingTime.dao.HeatingTimeMapper;
import cn.plou.web.system.commonMessage.heatingTime.entity.HeatingTime;
import cn.plou.web.system.commonMessage.heatingTime.entity.HeatingTimeKey;
import cn.plou.web.system.commonMessage.heatingTime.service.HeatingTimeService;
import cn.plou.web.system.commonMessage.heatingTime.vo.HeatingInfo;
import cn.plou.web.system.commonMessage.heatingTime.vo.HeatingTimeInfo;
import cn.plou.web.system.commonMessage.heatingTime.vo.HeatingTimeListInfo;
import cn.plou.web.system.commonMessage.heatingTime.vo.HeatingTimeVo;
import cn.plou.web.system.permission.rlUserRole.service.RlUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static cn.plou.web.common.utils.Tools.getMaxIdNoSize;


@Component
public class HeatingTimeServiceImpl implements HeatingTimeService {
    @Autowired
    private HeatingTimeMapper heatingTimeMapper;
    @Autowired
    private CompanyServiceImpl companyServiceImpl;
    @Autowired
    private StationService stationService;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private RlUserRoleService userRoleService;
    @Autowired
    private RlUserRoleService rlUserRoleService;

    @Override
    public List<String> getAnnualByCompanyId(String companyId) {
        return heatingTimeMapper.getAnnualByCompanyId(companyId);
    }

    //添加供暖时间信息
    @Override
    public int addHeatingTime(HeatingTime heatingTime){
        if(heatingTime.getStationId().length()==5){
            heatingTime.setCompanyId(heatingTime.getStationId());
        }else {
            heatingTime.setCompanyId(stationService.getStationById(heatingTime.getStationId()).getCompanyId());
        }
        heatingTime.setCreateUser(UserUtils.getUserId());
        heatingTime.setCreateDate(new Date());
        return heatingTimeMapper.insertHeatingTime(heatingTime);
    }

    @Override
    public int deleteBatchByIds(List<String> primaryIds) {
        return heatingTimeMapper.deleteBatchByIds(primaryIds);
    }

    @Override
    public int modifyHeatingById(HeatingTime heatingTime) {
        heatingTime.setUpdateUser(UserUtils.getUserId());
        heatingTime.setUpdateDate(new Date());
        return heatingTimeMapper.updateByPrimaryKeySelective(heatingTime);
    }

    @Override
    public int modifyHeatingTimeBatch(HeatingTimeVo heatingTimeVo) {
        heatingTimeVo.setUpdateUser(UserUtils.getUserId());
        heatingTimeVo.setUpdateDate(new Date());
        return heatingTimeMapper.updateHeatingTimeBatch(heatingTimeVo);
    }

    @Override
    public String getNewId() {
        if(heatingTimeMapper.selectAllIds().size() == 0){
            return "1";
        }else{
            return getMaxIdNoSize(heatingTimeMapper.selectAllIds());
        }
    }

    //按ID查
    @Override
    public HeatingTime getHeatingTimeById(String primaryId){
        return heatingTimeMapper.selectHeatingTimeById(primaryId);
    }

    //查全部
    @Override
    public HeatingTimeListInfo getAllHeatingTime(String companyId, String stationId, HeatingTimeVo heatingTimeVo, String order, String sortby, Integer page, Integer pageSize){
        HeatingTimeListInfo heatingTimeListInfo = new HeatingTimeListInfo();
        if(page!=null){
            page = (page-1) * pageSize;
        }
        List<String> companyIds = new ArrayList<String>();
        if (companyId != null){
            companyIds = companyServiceImpl.getCompanyIdsByPid(companyId);
        }else{
            if(userRoleService.getRoleByUserId(UserUtils.getUserId()).equals("1")){
                companyIds.addAll(companyMapper.selectAllCompanyIds());
            }else{
                for(Company company:companyServiceImpl.getCompanyByRole(userRoleService.getRoleByUserId(UserUtils.getUserId()))){
                    companyIds.add(company.getCompanyId());
                }
            }
        }
//        sortby = CamelCaseUtil.toUnderscoreCase(sortby);
        List<HeatingTimeInfo> heatingTimeInfoList = heatingTimeMapper.selectAllHeatingTime(companyIds, stationId, heatingTimeVo, order, sortby, page, pageSize);
        for(HeatingTimeInfo heatingTimeInfo:heatingTimeInfoList){
            if(heatingTimeInfo.getStationId().length()==5){
                heatingTimeInfo.setStationName(heatingTimeInfo.getCompanyName());
            }else{
                heatingTimeInfo.setStationName(stationService.getStationById(heatingTimeInfo.getStationId()).getStationName());
            }
        }
        heatingTimeListInfo.setHeatingTimeInfoList(heatingTimeInfoList);
        heatingTimeListInfo.setCount(heatingTimeMapper.selectHeatingTimeCount(companyIds,stationId,heatingTimeVo));
        return heatingTimeListInfo;
    }

    @Override
    public List<HeatingTimeInfo> selectHeatingTimes(String companyIds, String stationIds,String year){
        List<String> companyIdlst = new ArrayList<>();
        List<String> stationIdlst = new ArrayList<>();
        if(companyIds != null){
            String[] split = companyIds.split(",");
            for (String s : split) {
                companyIdlst.add(s);
            }
        }
        else{
            companyIdlst =  null;
        }
        if(stationIds != null){
            String[] split2 = stationIds.split(",");
            for (String s : split2) {
                stationIdlst.add(s);
            }
        }
        else{
            stationIdlst =  null;
        }


        return heatingTimeMapper.selectHeatingTimes(companyIdlst,stationIdlst,year);
    }

    @Override
    public HeatingInfo getAllCompanyAndAllStationDownInfo() {
        HeatingInfo heatingInfo=new HeatingInfo();
        String userId = UserUtils.getUserId();
        String roleId = rlUserRoleService.getRoleByUserId(userId);
        List<Company> companyList = companyServiceImpl.getDownInfo(roleId);
        heatingInfo.setCompanyList(companyList);
        List<Station> stationList=new ArrayList<>();
        for(Company company:companyList){
            List<Station> stations = stationService.getStationDownInfo(company.getCompanyId());
            if(stations.size()>0){
                stationList.addAll(stations);
            }
        }
        heatingInfo.setStationList(stationList);
        return heatingInfo;
    }

}

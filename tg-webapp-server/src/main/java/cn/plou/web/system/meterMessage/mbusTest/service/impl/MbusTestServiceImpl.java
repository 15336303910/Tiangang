package cn.plou.web.system.meterMessage.mbusTest.service.impl;

import cn.plou.web.common.utils.RedisUtil;
import cn.plou.web.common.utils.SerializeUtil;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.TypeMstService;
import cn.plou.web.system.meterMessage.mbus.service.MbusService;
import cn.plou.web.system.meterMessage.mbus.vo.MbusInfo;
import cn.plou.web.system.meterMessage.mbusTest.dao.MbusTestMapper;
import cn.plou.web.system.meterMessage.mbusTest.entity.MbusTest;
import cn.plou.web.system.meterMessage.mbusTest.service.MbusTestService;
import cn.plou.web.system.meterMessage.mbusTest.vo.MbusTestInfo;
import cn.plou.web.system.meterMessage.mbusTest.vo.MbusTestListInfo;
import cn.plou.web.system.meterMessage.mbusTest.vo.MbusTestVo;
import cn.plou.web.system.permission.userPageHistory.entity.UserPageHistory;
import cn.plou.web.system.permission.userPageHistory.service.impl.UserPageHistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

import static cn.plou.web.common.utils.Tools.getMaxIdNoSize;

@Component
public class MbusTestServiceImpl implements MbusTestService {
    @Autowired
    private MbusTestMapper mbusTestMapper;
    @Autowired
    private MbusService mbusService;
    @Autowired
    private UserPageHistoryServiceImpl userPageHistoryServiceImpl;
    @Autowired
    private TypeMstService typeMstService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addMbusTest(MbusTest mbusTest) {
        mbusTest.setId(getNewId());
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("mbusWorkHistory");
        userPageHistory.setAct("addMbusTest");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return mbusTestMapper.insertSelective(mbusTest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyBatch(MbusTestVo mbusTestVo) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("mbusWorkHistory");
        userPageHistory.setAct("modifyBatch");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return mbusTestMapper.updateBatch(mbusTestVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyMbusById(MbusTest mbusTest) {
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("mbusWorkHistory");
        userPageHistory.setAct("modifyMbusById");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        return mbusTestMapper.updateByPrimaryKeySelective(mbusTest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MbusTestListInfo getAllMbusTest(Integer page, Integer pageSize, String companyId, String stationId, String commuityId, String buildingId, String unitId, String houseId, String order, String sortby, MbusTestVo mbusTestVo) {
        Integer start = null;
        if (page != null) {
            start = (page - 1) * pageSize;
        }
        MbusTestListInfo mbusTestListInfo = new MbusTestListInfo();
        List<MbusInfo> mbusInfoList = mbusService.getAllMbus(null, null, companyId, stationId, commuityId, buildingId, unitId, houseId, null, null, null).getMbusInfoList();
        List<String> mbusCodes = new ArrayList<>();
        for (MbusInfo mbusInfo : mbusInfoList) {
            mbusCodes.add(mbusInfo.getMbusCode());
        }
        UserPageHistory userPageHistory = new UserPageHistory();
        userPageHistory.setCol("mbusWorkHistory");
        userPageHistory.setAct("getAllMbusTest");
        userPageHistoryServiceImpl.addUserPageHistory(userPageHistory);
        List<MbusTestInfo> mbusTestInfos = mbusTestMapper.selectAllMbusTest(start, pageSize, order, sortby, mbusCodes, mbusTestVo);
        List<TypeMst> typeMstList = typeMstService.getAllTypeMstByRedis();
        for (MbusTestInfo mbusTestInfo : mbusTestInfos) {
            mbusTestInfo.setRecFlagName(typeMstService.getTypeNameById(typeMstList, mbusTestInfo.getRecFlag()));
//            if(mbusTestInfo.getRecFlag()!=null){
//                if(typeMstService.getTypeMstById(mbusTestInfo.getRecFlag())==null){
//                    mbusTestInfo.setRecFlagName(null);
//                }else{
//                    mbusTestInfo.setRecFlagName(typeMstService.getTypeMstById(mbusTestInfo.getRecFlag()).getTypeName());
//                }
//            }
        }
        mbusTestListInfo.setMbusTestInfoList(mbusTestInfos);
        mbusTestListInfo.setCount(mbusTestMapper.selectMbusTestCount(mbusCodes, mbusTestVo));
        return mbusTestListInfo;
    }

    @Override
    public String getNewId() {
        if (mbusTestMapper.selectAllIds().size() == 0) {
            return "1";
        } else {
            return getMaxIdNoSize(mbusTestMapper.selectAllIds());
        }
    }
}

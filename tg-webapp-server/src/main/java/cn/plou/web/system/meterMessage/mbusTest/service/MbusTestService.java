package cn.plou.web.system.meterMessage.mbusTest.service;

import cn.plou.web.system.meterMessage.mbusTest.entity.MbusTest;
import cn.plou.web.system.meterMessage.mbusTest.vo.MbusTestInfo;
import cn.plou.web.system.meterMessage.mbusTest.vo.MbusTestListInfo;
import cn.plou.web.system.meterMessage.mbusTest.vo.MbusTestVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MbusTestService {
    int addMbusTest(MbusTest mbusTest);
    int modifyBatch(MbusTestVo mbusTestVo);
    int modifyMbusById(MbusTest mbusTest);
    MbusTestListInfo getAllMbusTest(Integer page,
                                    Integer pageSize,
                                    String companyId,
                                    String stationId,
                                    String commuityId,
                                    String buildingId,
                                    String unitId,
                                    String houseId,
                                    String order,
                                    String sortby,
                                    MbusTestVo mbusTestVo);
    String getNewId();
}

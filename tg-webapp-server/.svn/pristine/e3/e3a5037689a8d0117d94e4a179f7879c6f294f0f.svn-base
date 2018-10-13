package cn.plou.web.system.meterMessage.meterModifyData.service;

import cn.plou.web.system.meterMessage.meterModifyData.entity.MeterModifyData;
import cn.plou.web.system.meterMessage.meterModifyData.vo.MeterModifyDataInfo;
import cn.plou.web.system.meterMessage.meterModifyData.vo.MeterModifyDataVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MeterModifyDataService {
    int addMeterModifyData(MeterModifyData meterModifyData);
    PageInfo<MeterModifyDataInfo> getAllMeterModifyData(String companyId, String stationId, String commuityId, String buildingNo, String unitId, String cosumerId,
                                                        MeterModifyDataVo searchCondition, String order, String sortby, Integer page, Integer pageSize);
    int modifyMeterModifyDataBatch(MeterModifyDataVo meterModifyDataVo);
    int modifyMeterModifyData(MeterModifyData meterModifyData);
}

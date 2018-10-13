package cn.plou.web.system.meterMessage.meterKey.service;

import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.system.meterMessage.meterKey.entity.MeterKey;
import cn.plou.web.system.meterMessage.meterKey.vo.MeterKeyInfo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface MeterKeyService {
    int addMeterKey( MeterKeyInfo meterKeyInfo);
    ImportResult importExcel(MultipartFile multipartFile);
    PageInfo<MeterKey> getAllMeterKey(String companyId, String stationId, String commuityId, String buildingNo, String unitId,
                                      String consumerId, String order, String sortby, Integer page, Integer pageSize);
}

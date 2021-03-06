package cn.plou.web.system.meterMessage.meter.service;

import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.system.meterMessage.mbus.entity.Mbus;
import cn.plou.web.system.meterMessage.mbus.vo.MbusInfo;
import cn.plou.web.system.meterMessage.meter.entity.Meter;
import cn.plou.web.system.meterMessage.meter.vo.MeterInfo;
import cn.plou.web.system.meterMessage.meter.vo.MeterListInfo;
import cn.plou.web.system.meterMessage.meter.vo.MeterSelectInfo;
import cn.plou.web.system.meterMessage.meter.vo.MeterVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import java.util.List;

@Service
public interface MeterService {
    MeterInfo getMeterById(String meterId);
    List<MeterInfo> getMeterInfoById(String meterId);
    int deleteMeterBatchByIds(List<String> meterIds);
    int setDelMeterBatchByIds(List<String> meterIds);
    int addMeter(Meter meter);
    int modifyBatchByIds(MeterVo meterVo);
    MeterListInfo getAllMeter(Integer page, Integer pageSize, String order, String sortby, String companyId, String stationId,
                              String commuityId, String buildingId, String unitId, String consumerId,
                              MeterVo meterVo);
    int modifyMeter(Meter meter);
    List<Meter> getMeterDownInforByConsumer(String consumerId);
    List<String> getAllIds(String meterType,String consumerId);
    List<String> getAllIdsByConsumerIds(List<String> consumerIds);
    ImportResult importExcel(MultipartFile multipartFile, ServletRequest request);
    int addBatch(List<Meter> meters);
    
    String exportExcel(ServletRequest request,MeterSelectInfo meterSelectInfo);
		Integer getMaxmeterId(String typeAndConsumerId);
		Integer getMeterCountbyCommuityId(String commuityId);
    List<String> getMeterIdsByRoleId() ;
		List<Meter> selectMeterByotherId(String consumerId);
		void modifyMeterAddressbyConsumer(String commuityId, String address, String address2);
		void modifyMeterAddress(String consumerId, String address);
}

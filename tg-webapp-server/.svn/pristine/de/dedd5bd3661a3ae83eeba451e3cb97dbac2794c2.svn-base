package cn.plou.web.system.baseMessage.producer.service;

import cn.plou.web.system.baseMessage.producer.entity.Producer;
import cn.plou.web.system.baseMessage.producer.vo.ProducerInfo;
import cn.plou.web.system.baseMessage.producer.vo.ProducerVo;
import cn.plou.web.system.baseMessage.producer.vo.StructureInfo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProducerService {
   PageInfo<ProducerInfo> getAllProducer(String companyId, String producerId, ProducerVo producerVo, String order, String sortby, Integer page, Integer pageSize);
   Producer getProducerById(String producerId);
   int deleteProducerBatchByIds(List<String> producerIds);
   int addProducer(Producer producer);
   int modifyProducerBatch(ProducerVo producerVo);
   int modifyProducer(Producer producer);
   List<Producer> getProducerByCompanyId(String companyId);
   StructureInfo getStructureByProducerId(String producerId);
   Producer getProducerByName(String producerName);
	Integer getMaxProducerId();
}

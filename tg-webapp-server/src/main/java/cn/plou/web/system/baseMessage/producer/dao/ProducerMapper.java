package cn.plou.web.system.baseMessage.producer.dao;


import cn.plou.web.system.baseMessage.producer.entity.Producer;
import cn.plou.web.system.baseMessage.producer.vo.ProducerInfo;
import cn.plou.web.system.baseMessage.producer.vo.ProducerVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProducerMapper {
    int deleteByPrimaryKey(String producerId);

    int insert(Producer record);

    int insertSelective(Producer record);

    Producer selectByPrimaryKey(String producerId);

    int updateByPrimaryKeySelective(Producer record);

    int updateByPrimaryKey(Producer record);

    int deleteProducerBatchByIds(List<String> producerIds);

    int updateProducerBatch(ProducerVo producerVo);

    List<Producer>selectProducerByCompanyId(String companyId);

    Producer selectByName(String producerName);

    List<ProducerInfo> selectProducerByProducerIds(@Param("producerIds") List<String> producerIds,@Param("producerId") String producerId,
                                                   @Param("producerVo") ProducerVo producerVo,
                                                   @Param("order") String order, @Param("sortby") String sortby,
                                                   @Param("page") Integer page,@Param("pageSize") Integer pageSize);

    List<ProducerInfo> selectAllProducer(@Param("companyIds") List<String> companyIds, @Param("producerId") String producerId,
                                         @Param("producerVo") ProducerVo producerVo,
                                         @Param("order") String order, @Param("sortby") String sortby,
                                         @Param("page") Integer page,@Param("pageSize") Integer pageSize);

		String getMaxProducerId();
}
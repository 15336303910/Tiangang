package cn.plou.web.common.config;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import com.google.common.collect.Maps;
import cn.plou.web.common.config.condition.KafkaCondition;

/**
 * @Project : tg-micro
 * @File : KafkaConfig.java
 * @Author : WangJiWei
 * @Date : 2018年5月8日上午8:44:27
 *
 * @Comments :
 * 
 */
@Conditional(KafkaCondition.class)
@Configuration
public class KafkaConfig {

    /** kafka */
    @Value("${bootstraps.servers}")
    private String servers;

    /** producer */
    @Value("${producer.retries}")
    private int retries;
    @Value("${producer.batch.size}")
    private int batchSize;
    @Value("${producer.linger}")
    private long linger;
    @Value("${producer.buffer.memory}")
    private int bufferMemory;
    @Value("${key.serializer}")
    private String keySerializer;
    @Value("${value.serializer}")
    private String valueSerializer;
    @Value("${producer.max.block.ms}")
    private long maxBlockMs;

    @Bean(name = "prodcuerConfig")
    public Map<String, Object> producerConfigs() {
	Map<String, Object> props = Maps.newHashMap();
	props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
	props.put(ProducerConfig.RETRIES_CONFIG, retries);
	props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
	props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
	props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
	//请求的最长等待时间
	props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, maxBlockMs);
	//Kafka消息的序列化方式
	props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
	props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
	return props;
    }

    public DefaultKafkaProducerFactory<String, String> producerFactory() {
	return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<?, ?> kafkaTemplate() {
	return new KafkaTemplate<String, String>(producerFactory());
    }

}

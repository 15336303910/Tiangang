package cn.plou.web.common.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

import cn.plou.common.utils.Tools;

/**
 * @Author : WangJiWei
 * @Date : 2017年12月22日上午11:06:19
 * @Comments : 工具类,读取配置文件
 */
@Component
public class EnvUtils {
    private static final Logger log = LoggerFactory.getLogger(EnvUtils.class);

    public static StandardEnvironment instance;
    private static Map<String, Object> kafkaConsumerConfig;

    @Autowired
    private void registerInstance(Environment env) {
	instance = (StandardEnvironment) env;
	log.info("register environment instance: " + env);
    }

    @PostConstruct
    public void init() {
	kafkaConsumerConfig = new HashMap<String, Object>() {
	    private static final long serialVersionUID = 1L;
	    {
		put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
		put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitIntervalMs);
		put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
		put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializerClass);
		put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializerClass);
		put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
		put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
	    }
	};
    }

    @Value("${bootstraps.servers}")
    private String bootstrapServers;
    @Value("${consumer.enable.auto.commit}")
    private String enableAutoCommit;
    @Value("${consumer.auto.commit.interval}")
    private String autoCommitIntervalMs;
    @Value("${consumer.session.timeout}")
    private String sessionTimeoutMs;
    @Value("${key.deserializer}")
    private String keyDeserializerClass;
    @Value("${value.deserializer}")
    private String valueDeserializerClass;
    @Value("${consumer.group.id}")
    private String groupId;
    @Value("${consumer.auto.offset.reset}")
    private String autoOffsetReset;
    @Value("${consumer.max.poll.records}")
    private String maxPollRecords;

    /**
     * 默认的consumer配置
     */
    public static Map<String, Object> kafkaConsumerConfig() {
	return Collections.unmodifiableMap(kafkaConsumerConfig);
    }

    public static String get(String key) {
	return instance.getProperty(key);
    }

    public static int getInt(String key) {
	return Integer.parseInt(instance.getProperty(key));
    }

    public static boolean getBoolean(String key) {
	String v = instance.getProperty(key);
	try {
	    return Tools.isNull(v) ? false : Boolean.parseBoolean(v);
	} catch (Exception e) {
	    return false;
	}
    }

    public static long getLong(String key) {
	return Long.parseLong(instance.getProperty(key));
    }

    /**
     * 是否启用MQEngine
     */
    public static String getMQEnable() {
	return get("mq.enable");
    }

    public static String getMQConsumerIds() {
	return get("mq.consumer.ids");
    }

    public static String getEsAddr() {
	return get("elasticsearch.addr");
    }

    public static String getEsClusterName() {
	return get("elasticsearch.cluster.name");
    }

    public static Boolean getEsClientLimit() {
	return getBoolean("elasticsearch.client.concurrent.limit");
    }

    public static Integer getEsClientQueueSize() {
	return getInt("elasticsearch.client.queue.size");
    }

    public static Integer getEsClientQueueMaxSize() {
	return getInt("elasticsearch.client.queue.max.size");
    }

    public static Boolean getESisEnable() {
	return getBoolean("elasticsearch.enable");
    }

    /**
     * redis
     */
    public static String getRedisHosts() {
	return get("redis.hosts");
    }

    public static Integer getRedisPort() {
	return getInt("redis.port");
    }

    public static Boolean getRedisEnable() {
	return getBoolean("redis.enable");
    }

    public static String getMQServers() {
	return get("bootstraps.servers");
    }

    public static String getCommunicateProtocolPorts() {
	return get("communicator.protocol-ports");
    }

    public static Boolean getCommunicateEnable() {
	return getBoolean("communicator.enable");
    }

    public static Boolean getRedisIsEnable() {
	return getBoolean("redis.enable");
    }

    public static String getRedisAddr() {
	return get("redis.addr");
    }

}

package cn.plou.web.common.config;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.plou.component.es.ESTool;
import cn.plou.component.es.ESToolConfig;
import cn.plou.web.common.utils.EnvUtils;

/**
 * @Project : tg-micro
 * @File : ESConfig.java
 * @Author : WangJiWei
 * @Date : 2018年6月11日上午9:54:15
 *
 * @Comments :
 * 
 */
@Configuration
public class ESConfig {

    @Bean
    public ESTool esTool() throws UnknownHostException {

	Map<String, Object> esconfig = new HashMap<>();
	esconfig.put(ESToolConfig.ES_ADDR, EnvUtils.getEsAddr());
	esconfig.put(ESToolConfig.ES_CLUSTER_NAME, EnvUtils.getEsClusterName());
	esconfig.put(ESToolConfig.ES_CLIENT_QUEUE_SIZE, EnvUtils.getEsClientQueueSize());
	esconfig.put(ESToolConfig.ES_CLIENT_CONCURRENT_LIMIT, EnvUtils.getEsClientLimit());
	esconfig.put(ESToolConfig.ES_CLIENT_QUEUE_MAX_SIZE, EnvUtils.getEsClientQueueMaxSize());
	ESTool es = new ESTool(esconfig);
	//es.start();
	return es;
    }

}

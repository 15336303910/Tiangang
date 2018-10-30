package cn.plou.web.common.config;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import cn.plou.component.es.ESTool;
import cn.plou.component.es.ESToolConfig;

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

	@Autowired
	Environment  env;
    @Bean 
    public ESTool esTool() throws UnknownHostException {

	Map<String, Object> esconfig = new HashMap<>();
	esconfig.put(ESToolConfig.ES_ADDR, env.getProperty("elasticsearch.addr"));
	esconfig.put(ESToolConfig.ES_CLUSTER_NAME, env.getProperty("elasticsearch.cluster.name"));
	esconfig.put(ESToolConfig.ES_CLIENT_QUEUE_SIZE,Integer.parseInt(env.getProperty("elasticsearch.client.queue.size")));
	esconfig.put(ESToolConfig.ES_CLIENT_CONCURRENT_LIMIT, Boolean.parseBoolean(env.getProperty("elasticsearch.client.concurrent.limit")));
	esconfig.put(ESToolConfig.ES_CLIENT_QUEUE_MAX_SIZE,  Integer.parseInt(env.getProperty("elasticsearch.client.queue.max.size")));
	ESTool es = new ESTool(esconfig);
	es.start();
	return es;
    }

}

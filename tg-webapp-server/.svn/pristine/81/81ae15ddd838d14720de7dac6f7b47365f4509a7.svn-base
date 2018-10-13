package cn.plou.web.common.utils;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.ElasticSearchDruidDataSourceFactory;

import cn.plou.component.es.ESTool;
import cn.plou.web.common.config.ApplicationContextRegister;

@Component
public class Initloader {
  @PostConstruct
	public void dataSource() {
		try {
			Properties properties = new Properties();
			// jdbc:elasticsearch://192.168.1.62:9300/
			properties.put(DruidDataSourceFactory.PROP_URL, "jdbc:elasticsearch://" + EnvUtils.get("elasticsearch.sql.url"));
			// "jdbc:elasticsearch://" + EnvUtils.get("elasticsearch.sql.url"));
			properties.put(DruidDataSourceFactory.PROP_CONNECTIONPROPERTIES, "client.transport.ignore_cluster_name=true");
			DruidDataSource dataSource = (DruidDataSource) ElasticSearchDruidDataSourceFactory.createDataSource(properties);
			dataSource.setMaxActive(50);
			dataSource.setMinIdle(5);
			dataSource.setInitialSize(5);
			dataSource.setMaxWait(10000);
			dataSource.setTimeBetweenConnectErrorMillis(1000);
			dataSource.setMinEvictableIdleTimeMillis(30000);
			dataSource.setTestOnBorrow(true);
			dataSource.setTestOnReturn(false);
			dataSource.setMaxOpenPreparedStatements(200000);
			dataSource.setRemoveAbandoned(true);
			dataSource.setRemoveAbandonedTimeout(2000000);
			ESTool.esSource = dataSource;
		} catch (Exception ex) {

		}
	}
	
  /**
	 * @Comments : 全局工具,在各组件实例化后执行
	 */
	@SuppressWarnings("unchecked")
  @PostConstruct
	public void prepareCommTool() {
		ApplicationContext ctx = ApplicationContextRegister.getApplicationContext();
		JdbcTemplate jt = ctx.getBean(JdbcTemplate.class);
		Support.init(jt);
		DataSource dataSource = ctx.getBean(DataSource.class);
		Support.init(dataSource);
//		KafkaTemplate<String, String> kp = ctx.getBean(KafkaTemplate.class);
//		Support.init(kp);
		ESTool es = ctx.getBean(ESTool.class);
		Support.init(es);

		RedisTemplate<String, Object> redisTemplate = ctx.getBean("redisTemplate", RedisTemplate.class);
		Support.init(redisTemplate);
	}

}

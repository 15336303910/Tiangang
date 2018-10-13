package cn.plou.web.common.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;

import com.alibaba.druid.pool.DruidDataSource;

import cn.plou.common.utils.Tools.JDBCUtils;
import cn.plou.component.es.ESTool;
import cn.plou.web.TGWebApplication;

/**
 * @Project : tg-micro
 * @File : CommonTool.java
 * @Author : WangJiWei
 * @Date : 2018年5月29日上午11:09:22
 *
 * @Comments :
 * 
 */
public class Support {

	public static KafkaTemplate<String, String> kafkaProducer;
	public static JdbcTemplate jdbcTemplate;
	public static DataSource dataSource;
	public static ESTool es;
	public static RedisTemplate jredis;
 
    
	public static void init(KafkaTemplate<String, String> kp) {
		if (null == Support.kafkaProducer) {
			Support.kafkaProducer = kp;
		}

	}

	public static void init(JdbcTemplate jt) {
		if (null == Support.jdbcTemplate) {
			Support.jdbcTemplate = jt;
		}
	}

	public static void init(DataSource ds) {
		if (null == Support.dataSource) {
			Support.dataSource = ds;
		}
	}

	public static void init(ESTool est) {
		if (null == Support.es) {
			Support.es = est;
		}
	}

	public static void init(RedisTemplate redsdao) {
		if (null == Support.jredis) {
			Support.jredis = redsdao;
		}
	}
    
	public static ResultSet  querryFromEs(String sql){
		DruidDataSource dds = ESTool.esSource;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			connection = dds.getConnection();
			ps = connection.prepareStatement(sql);
			resultSet = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.releaseResource(connection, ps, resultSet);
		}
	return resultSet;
}

}

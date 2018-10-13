package cn.plou.web.common.utils;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public final class RedisUtil {

    private final Logger log = Logger.getLogger(RedisUtil.class);
    private static RedisUtil INSTANCE = null;

    // Redis 服务器 IP
    //private String address = "192.168.1.61";
    private String address = "192.168.1.136";
    //private String address = "127.0.0.1";
    //private String address = "58.59.67.182";

    // Redis的端口号
    private int port = 6379;
    //private int port = 63790;

    // 访问密码
    //private String password = "123456";
    private String password = null;
    //private String password ="86a1b907d54bf7010394bf316e183e67";

    // 连接 redis 等待时间
    private int timeOut = 10000;

    // 可用连接实例的最大数目，默认值为8；
    // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
    private int maxTotal = 1024;

    // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8
    private int maxIdle = 200;

    // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
    private int maxWait = 10000;
  	//库
    private int database = 0;
    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
    private boolean testOnBorrow = true;

    // 连接池
    private JedisPool jedisPool = null;

    // 构造函数
    public RedisUtil() {
				try {
					address = EnvUtils.get("redis.host");
					port = EnvUtils.getInt("redis.port");
					String passwordt = EnvUtils.get("redis.password");
					password = passwordt.isEmpty()?null:passwordt;
					maxTotal = EnvUtils.getInt("redis.pool.max-total");
					maxIdle = EnvUtils.getInt("redis.pool.max-idle");
					maxWait = EnvUtils.getInt("redis.pool.max-wait");
					database = EnvUtils.getInt("redis.database");
				} catch (Exception ex) {
					log.error(ex.getMessage());
				}
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(maxTotal);
            config.setMaxIdle(maxIdle);
            config.setMaxWaitMillis(maxWait);
            config.setTestOnBorrow(testOnBorrow);
            jedisPool = new JedisPool(config, address, port, timeOut, password,database);
        } catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    public static RedisUtil getSingleton() {
        if (null == INSTANCE) {
            synchronized (RedisUtil.class) {
                if (null == INSTANCE) {
                    INSTANCE = new RedisUtil();
                }
            }
        }
        return INSTANCE;
    }

    public static void returnSource(Jedis jedis) {
        if (null != jedis) {
            jedis.close();
        }
    }

    // 获取 Jedis 实例
    public Jedis getJedis() {
        if (jedisPool != null) {
            return jedisPool.getResource();
        }
        return null;
    }
}
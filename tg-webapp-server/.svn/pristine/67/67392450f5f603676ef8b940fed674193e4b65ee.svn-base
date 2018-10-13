package cn.plou.web.common.utils;

import cn.plou.common.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.Random;
import java.util.Set;

/**
 * @ClassName: FlowIdTool
 * @Description: 任务流水号 flow_id 生成类：内存保存流水号增长标记，按天重置标识位
 * @Author: youbc
 * @Date 2018-08-20 15:01
 */
public class FlowIdTool {

    private static final String FIRST_FLOW_ID = "1";

    private static final String FLOW_ID_KEYS = "flow_id_keys";

    private static final String FLOW_ID_DATE = "flow_id_date";

    private static final String DATE_FORMAT = "yyyyMMdd";

    private static FlowIdTool flowIdTool = null;

    private static final int PLACE = 6; // 流水号位数

    private static final String RECEIPT_NO = "05"; // 收据号

    private FlowIdTool() {
        init();
    }

    /**
     * @Description: 判断日期是否改变
     * @Param: []
     * @Return: void
     * @Author: youbc
     * @Date: 2018/8/20 15下午48
     */
    private String checkChangeDay() {
        return init();
    }

    /**
     * @Description: 初始化日期和自增序列数
     * @Param: []
     * @Return: void
     * @Author: youbc
     * @Date: 2018/8/20 15下午50
     */
    private String init() {
        RedisUtil redisUtil = RedisUtil.getSingleton();
        Jedis jedis = null;
        String redisDate;
        try {
            jedis = redisUtil.getJedis();
            redisDate = jedis.get(FLOW_ID_DATE);
            if (redisDate == null) {
                redisDate = DateUtil.toDateTimeString(new Date(), DATE_FORMAT);
                jedis.set(FLOW_ID_DATE, redisDate);
            } else {
                String nowDate = DateUtil.toDateTimeString(new Date(), DATE_FORMAT);
                if (!StringUtils.equalsIgnoreCase(redisDate, nowDate)) {
                    Set<String> smembers = jedis.smembers(FLOW_ID_KEYS);
                    for (String it : smembers) {
                        jedis.set(it, FIRST_FLOW_ID);
                    }
                    redisDate = nowDate;
                    jedis.set(FLOW_ID_DATE, redisDate);
                }
            }
        } finally {
            RedisUtil.returnSource(jedis);
        }
        return redisDate;
    }

    /**
     * @Description: 取得 FlowIdTool 的单例实现
     * @Param: []
     * @Return: cn.plou.web.common.utils.FlowIdTool
     * @Author: youbc
     * @Date: 2018/8/20 15下午45
     */
    private static FlowIdTool getInstance() {
        if (flowIdTool == null) {
            synchronized (FlowIdTool.class) {
                if (flowIdTool == null) {
                    flowIdTool = new FlowIdTool();
                }
            }
        }
        return flowIdTool;
    }

    /**
     * @Description: 生成下一个编号, 前缀自动补全 0
     * @Param: []
     * @Return: java.lang.String
     * @Author: youbc
     * @Date: 2018/8/20 15下午50
     */
    private synchronized String getNextNumber(String companyId, String taskType) {
        StringBuffer stringBuffer = new StringBuffer(companyId);
        String day = checkChangeDay();
        stringBuffer.append(day);
        stringBuffer.append(taskType);
        String key = companyId + "@" + taskType;

        RedisUtil redisUtil = RedisUtil.getSingleton();
        Jedis jedis = null;
        String numRds;
        try {
            jedis = redisUtil.getJedis();
            numRds = jedis.get(key);
            if (!jedis.sismember(FLOW_ID_KEYS, key)) {
                jedis.sadd(FLOW_ID_KEYS, key);
            }
        } finally {
            RedisUtil.returnSource(jedis);
        }

        if (numRds == null) {
            numRds = FIRST_FLOW_ID;
        }
        int numPlaces = numRds.length();
        // 数字位数小于需要补全的总位数，需要补全0
        if (numPlaces < PLACE) {
            for (int i = 0; i < PLACE - numPlaces; i++) {
                stringBuffer.append("0");
            }
            stringBuffer.append(numRds);
        } else {
            stringBuffer.append(numRds);
        }

        try {
            jedis = redisUtil.getJedis();
            jedis.set(key, Integer.parseInt(numRds) + 1 + "");
        } finally {
            RedisUtil.returnSource(jedis);
        }
        return stringBuffer.toString();
    }

    /**
     * @Description: 获取流水号
     * @Param: [taskType]
     * @Return: java.lang.String
     * @Author: youbc
     * @Date: 2018/8/24 15下午13
     */
    public static String GetFlowId(String companyId, String taskType) {
        return FlowIdTool.getInstance().getNextNumber(companyId, taskType);
    }

    /**
     * @Description: 获取收据号
     * @Param: [companyId]
     * @Return: java.lang.String
     * @Author: youbc
     * @Date: 2018/9/20 14下午31
     */
    public static String GetReceiptNo(String companyId) {
        return GetFlowId(companyId, RECEIPT_NO);
    }

    public static void main(String[] args) {
        new YourJunit().start();
    }
}


class YourJunit extends Thread {
    public void run() {
        //开10个线程，模拟10个用户
        for (int i = 0; i < 10; i++) {
            Thread thread = new YourThreadTest();
            thread.setName(" user NO." + (i));
            thread.start();
        }
    }
}

class YourThreadTest extends Thread {
    public void run() {
        for (int i = 0; i < 10; i++) {
//            System.out.println(this.getName() + ":" + FlowIdTool.getInstance().getNextNumber("$$"));
            System.out.println(this.getName() + ":" + FlowIdTool.GetFlowId(new Random().nextInt(2) + "", "@" + new Random().nextInt(3) + "@"));
//            System.out.println(this.getName() + ":" + FlowIdTool.GetFlowId("00101#", "@" + new Random().nextInt(3) + "@"));
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


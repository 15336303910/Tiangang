package cn.plou.web.common.constant;

import cn.plou.web.common.utils.EnvUtils;

/**
 * @Project : tg-services
 * @File : MQContant.java
 * @Author : WangJiWei
 * @Date : 2017年11月27日上午8:45:41
 *
 * @Comments : MQ 常量
 * 
 */
public interface MQConstant {

    /** 是否启用MQ */
    public static final String MQ_ENABLE = EnvUtils.getMQEnable();

    public static final String MQ_SERVERS = EnvUtils.getMQServers();
    
    public static final String MQ_CONSUMER_ID = EnvUtils.getMQConsumerIds();
}

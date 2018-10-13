package cn.plou.web.common.constant;

import cn.plou.web.common.utils.EnvUtils;

/** 
 * @Project : tg-services 
 * @File : ESContant.java
 * @Author : WangJiWei
 * @Date : 2017年11月28日上午10:39:16 
 *
 * @Comments : ES配置
 * 
 */
public interface ESConstant {

    /** 是否启用ES*/
    public static final Boolean ES_ENABLE = EnvUtils.getESisEnable();
    /** ES的index的type*/
    public static final String INDEX_TYPE = "data";
    /** ES集群的addr*/
    public static final String ES_ADDR = EnvUtils.getEsAddr();
    public static final String ES_CLUSTER_NAME = EnvUtils.getEsClusterName();
    /** 是否开启ES客户端的阻塞配置*/
    public static final Boolean ES_CLIENT_CONCURRENT_LIMIT = EnvUtils.getEsClientLimit();
    /** ES客户端阻塞队列的大小*/
    public static final Integer ES_CLIENT_QUEUE_SIZE = EnvUtils.getEsClientQueueSize();
    
    public static final Integer ES_CLIENT_QUEUE_MAX_SIZE = EnvUtils.getEsClientQueueMaxSize();
    
    /** -------------ES 配置 -------------------- */
    public static final String ES_CLUSTER_NAME_CONFIG = "cluster.name";
}

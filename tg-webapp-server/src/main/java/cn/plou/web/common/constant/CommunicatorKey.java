package cn.plou.web.common.constant;

import cn.plou.web.common.utils.EnvUtils;

/**
 * @Project : tg-services
 * @File : CommunicatorKey.java
 * @Author : WangJiWei
 * @Date : 2017年12月1日下午2:05:14
 *
 * @Comments :
 * 
 */
public interface CommunicatorKey {

    /** 协议端口 */
    public static final String PROTOCOL_AND_PORTS = EnvUtils.getCommunicateProtocolPorts();
    public static final Boolean COMMUNICATOR_ENABLE = EnvUtils.getCommunicateEnable();
    
    enum Protocol {
	/***/
	tcp("tcp"), udp("udp");

	private String name;

	Protocol(String n) {
	    this.name = n;
	}

	@Override
	public String toString() {
	    return this.name;
	}
    }

}

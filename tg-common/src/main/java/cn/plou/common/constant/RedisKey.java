package cn.plou.common.constant;

public interface RedisKey {

	//用户基本信息
	public static final String HOUSE_INFO = "houseinfo";
		
	//集中器基本信息
	public static final String MBUS_INFO = "mbusinfobase";
	//仪表基础信息
	public static final String MERER_INFO = "meterinfo";
	//阀对应的表信息加载完成
	public static final String MERER_VALVE_KEY = "v_h";
	public static final String MERER_HEAT_KEY = "h_v";
	//集中器读取时间
	public static final String MBUS_TIMING = "mbusinfotiming";
	
	//集中器读取时间
	public static final String MBUS_CONTROL= "mbusControlInfo";
	
//	//集中器读取时间
//	public static final String REDIS_PARSE_NOTICE_TOPIC= "redisparsenoticetopic";
	
	//发送接收flg sendRecFlg+MBUSCODE_METERCODE
	public static final String SEND_REC_FLG = "sendRecFlg";
	//接收端立即插入++MBUSCODE_METERCODE
	public static final String OPT_FLG = "optFlg";
	
  //接收端反馈数据立即插入++MBUSCODE_METERCODE
	public static final String READ_RESULT = "readResult";
//	
//  //集中器上次定时间隔发送初始时间
	public static final String MBUS_LAST_TIMEING_READ_TIME = "mbusLastReadtime";

	//	集中器socket  key：socket value:集中器号
 public static final String GET_MBUS_FROM_SOCKET="SOCKET";
 
 //通过集中器号取得socket  key:集中器号 value:socket 
 public static final String GET_SOCKET_FROM_MBUS="MBUSSOCKET";
 //保存集中器上线最新时间
 public static final String MBUS_ONLINE_TIME="mbusOnlineTime";

 //自动搜表号
 public static final String AUTO_SEARCH_METERCODE_TIME="autoSearchMetercode";
 
}

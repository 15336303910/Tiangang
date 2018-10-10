package cn.plou.common.constant;

public interface Constant {

	//指令类型  读表
	public static final String ORDER_TYPE_READ = "1";
	
	//指令类型  自动搜表号
	public static final String ORDER_TYPE_MBUS_AUTOSEARCH = "99";
	
	//指令类型  读状态
	public static final String ORDER_TYPE_MBUS_READ_STATUS= "80";
	
	//指令类型  开通道
	public static final String ORDER_TYPE_MBUS_CHANNEL_OPEN = "81";
	//指令类型  开虚拟
	public static final String ORDER_TYPE_MBUS_INVENTED_OPEN = "82";
	//指令类型  开通道
	public static final String ORDER_TYPE_MBUS_CHANNEL_CLOSE = "83";
	//指令类型  开虚拟
	public static final String ORDER_TYPE_MBUS_INVENTED_CLOSE = "84";
}

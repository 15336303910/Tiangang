package cn.plou.web.mobile.common;

/**
 * @Project : tg-webapp-server
 * @File : AppConstant.java
 * @Author : WangJiWei
 * @Date : 2018年10月15日下午3:59:34
 *
 * @Comments :
 * 
 */
public interface AppConstant {

    /** 热泵devType */
    public static final String RB = "rb";

    /** 小时数据 */
    public static final String PARAM_SHOW_TYPE_HOUR = "hour";
    /** 全部数据 */
    public static final String PARAM_SHOW_TYPE_ALL = "all";

    /** 报警已确认 */
    public static final Integer ALARM_RESULT_1 = 1;
    /** 报警未确认 */
    public static final Integer ALARM_RESULT_0 = 0;

    /** 有效点位 */
    public static final Integer POINT_VALIDED = 1;
    /** 无效点位 */
    public static final Integer POINT_NO_VALIDED = 0;

    /** 关注页 */
    public static final Integer SHOW_LEVEL_1 = 1;
    /** 全部页 */
    public static final Integer SHOW_LEVEL_2 = 2;

    public static final String LIMIT_1 = "limit 0,1";

    /** 报警类型字典表 kbn */
    public static final String ALARM_TYPE_MST = "station_alarm_type";
    
    /** COP点位名 */
    public static final String COP_POINT_NAME = "COP"; 

}

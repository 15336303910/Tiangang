package cn.plou.web.mobile.station.query;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @Project : tg-webapp-server
 * @File : AlarmsQuery.java
 * @Author : WangJiWei
 * @Date : 2018年10月8日下午4:40:43
 *
 * @Comments :
 * 
 */
@Data
public class AlarmsQuery {

    private List<String> stationIds;
    private String type; // 类型
    private String level; // 级别
    private Integer result;// 结果
    private String alarmTime;// 报警时间
    private String resultTime;
    private Date startTime;
    private Date endTime;
    private String sortBy; // end_time(报警日志时间)/level()/type()/result_time()
    private String order;

}

package cn.plou.web.mobile.station.query;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @Project : tg-webapp-server
 * @File : AlarmsQuery.java
 * @Author : WangJiWei
 * @Date : 2018年10月8日下午4:40:43
 *
 * @Comments : 报警页查询
 * 
 */
@Data
public class AlarmsQuery {

//    @NotNull(message="非空")
    private List<String> systemIds;
    private List<String> stationIds;
    private String type; // 类型
    private String level; // 级别
    private Integer result;// 结果
    private String alarmTime;// 报警时间
    private String resultTime;
    private Date startTime;
    private Date endTime;
    private String sortBy = "end_time"; // end_time(报警日志时间)
    private String order = "desc";
    private Integer page = 1;
    private Integer pageSize = 20;
    private Integer offset;

}

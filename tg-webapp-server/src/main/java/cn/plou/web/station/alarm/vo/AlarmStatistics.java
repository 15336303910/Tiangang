package cn.plou.web.station.alarm.vo;

import lombok.Data;

/**
 * @Project : tg-webapp-server
 * @File : AlarmStatistics.java
 * @Author : WangJiWei
 * @Date : 2018年9月28日下午3:16:47
 *
 * @Comments :
 * 
 */
@Data
public class AlarmStatistics {

    private String type;
    private Integer total;
    private String typeName;

    public AlarmStatistics() {

    }

    public AlarmStatistics(String type, Integer total, String typeName) {
	super();
	this.type = type;
	this.total = total;
	this.typeName = typeName;
    }
}

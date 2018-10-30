package cn.plou.web.station.alarm.vo;

import java.io.Serializable;

import cn.plou.web.station.alarm.entity.AlarmInfo;
import lombok.Data;

/**
 * @Project : tg-webapp-server
 * @File : AlarmInfoVo.java
 * @Author : WangJiWei
 * @Date : 2018年9月27日下午3:46:14
 *
 * @Comments :
 * 
 */
public class AlarmInfoVo extends AlarmInfo {
    private static final long serialVersionUID = 1L;
    private String typeName;
    private String stationName;
    private String sysName;

    public String getTypeName() {
	return typeName;
    }

    public void setTypeName(String typeName) {
	this.typeName = typeName;
    }

    public String getStationName() {
	return stationName;
    }

    public void setStationName(String stationName) {
	this.stationName = stationName;
    }

    public String getSysName() {
	return sysName;
    }

    public void setSysName(String sysName) {
	this.sysName = sysName;
    }

}

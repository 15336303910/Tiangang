package cn.plou.web.station.alarm.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class AlarmInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String primaryId;

    private String companyId;

    private String commuityId;

    private String stationId;

    private String sysId;

    private String devId;

    private String type;

    private Date beginTime;

    private Date endTime;

    private String des;

    private Integer result;

    private Date resultTime;

    private String resultDes;

    private String resultPerson;

    private String level;

    private String val;

    private String valset;

}
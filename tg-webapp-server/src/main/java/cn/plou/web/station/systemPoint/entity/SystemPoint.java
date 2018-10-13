package cn.plou.web.station.systemPoint.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SystemPoint {
    private String primaryId;

    private String pointid;

    private String sourceid;

    private String showdevtype;

    private String tablename;

    private String unit;

    private String kind;

    private Integer warnkind;

    private BigDecimal lwarn;

    private BigDecimal llwarn;

    private BigDecimal hwarn;

    private BigDecimal hhwarn;

    private BigDecimal swarn;

    private String warndes;

    private Integer iswarn;

    private Integer valided;

    private String pointName;
    
    private String showlevel;
}
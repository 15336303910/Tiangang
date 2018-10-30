package cn.plou.web.mobile.station.vo;

import java.io.Serializable;

/**
 * @Project : tg-webapp-server
 * @File : LineData.java
 * @Author : WangJiWei
 * @Date : 2018年10月15日上午11:54:31
 *
 * @Comments :
 * 
 */

public class LineData implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long time;
    private Double val;

    public LineData() {
    }

    public LineData(Long time, Double val) {
	this.time = time;
	this.val = val;
    }

    public Long getTime() {
	return time;
    }

    public void setTime(Long time) {
	this.time = time;
    }

    public Double getVal() {
	return val;
    }

    public void setVal(Double val) {
	this.val = val;
    }

}

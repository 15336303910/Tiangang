package cn.plou.web.heatManage.dataAnalysis.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author admin
 *
 */
public class HeatingStartAndEndTimeDO implements Serializable {
	private static final long serialVersionUID = 1L;


	//供暖起始时间
	private String startTime;

	//供暖结束时间
	private String endTime;


	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}



}

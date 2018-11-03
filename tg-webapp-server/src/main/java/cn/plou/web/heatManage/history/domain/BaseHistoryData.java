package cn.plou.web.heatManage.history.domain;

import lombok.Data;

@Data
public class BaseHistoryData {
	public String meterId;	//表号
	public String rowno;	//表号
	public String consumerId;	//
	public String source; // 地址信息
	public String runningState;//运行状态
		
}

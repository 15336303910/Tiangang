package cn.plou.web.balance.regulate.vo;

import cn.plou.web.balance.distribution.entity.BalanceValveDataNow;
import lombok.Data;

import java.util.List;
@Data
public class BalanceValveDataNowBatch extends BalanceValveDataNow {
    List<String> meterIds;
  	private Boolean batchCheckbox = false;
  	private String batchModifyId;
  	private String batchModifyType;	//
  	private String balanceValveControl;		//平衡阀控制

	public List<String> getMeterIds() {
		return meterIds;
	}

	public void setMeterIds(List<String> meterIds) {
		this.meterIds = meterIds;
	}

	public Boolean getBatchCheckbox() {
		return batchCheckbox;
	}

	public void setBatchCheckbox(Boolean batchCheckbox) {
		this.batchCheckbox = batchCheckbox;
	}

	public String getBatchModifyId() {
		return batchModifyId;
	}

	public void setBatchModifyId(String batchModifyId) {
		this.batchModifyId = batchModifyId;
	}

	public String getBatchModifyType() {
		return batchModifyType;
	}

	public void setBatchModifyType(String batchModifyType) {
		this.batchModifyType = batchModifyType;
	}

	public String getBalanceValveControl() {
		return balanceValveControl;
	}

	public void setBalanceValveControl(String balanceValveControl) {
		this.balanceValveControl = balanceValveControl;
	}
}

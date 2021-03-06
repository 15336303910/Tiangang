package cn.plou.web.balance.distribution.vo;

import cn.plou.web.balance.distribution.entity.BalanceValveDataNow;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class BalanceValveDataNowInfo extends BalanceValveDataNow {
    private BigDecimal flowingDeviation;
    private String controlRoleName;
    private String flowSourceName;
	private String exchangePressName;
	private String exchangeTempName;
	private String companyName;
	private String systemId;
	private String systemName;
	
}

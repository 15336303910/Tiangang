package cn.plou.web.balance.dataAnalysis.vo;

import cn.plou.web.balance.dataAnalysis.entity.BalanceValveData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class BalanceValveDataInfo extends BalanceValveData {
    private String address2nd;
    private String address;
    @ApiModelProperty(value="供水温差")
    private BigDecimal tempuratureDiff;
    @ApiModelProperty(value = "用户平均回温")
    private BigDecimal houseAvgOutTemp;

    public String getAddress2nd() {
        return address2nd;
    }

    public void setAddress2nd(String address2nd) {
        this.address2nd = address2nd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getTempuratureDiff() {
        return tempuratureDiff;
    }

    public void setTempuratureDiff(BigDecimal tempuratureDiff) {
        this.tempuratureDiff = tempuratureDiff;
    }

    public BigDecimal getHouseAvgOutTemp() {
        return houseAvgOutTemp;
    }

    public void setHouseAvgOutTemp(BigDecimal houseAvgOutTemp) {
        this.houseAvgOutTemp = houseAvgOutTemp;
    }
}

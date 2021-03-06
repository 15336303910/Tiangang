package cn.plou.web.system.baseMessage.house.vo;

import cn.plou.web.system.baseMessage.house.entity.House;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude (JsonInclude.Include.NON_NULL)
public class HouseInfo extends House {
    private String systemName;
    private String companyName;
    @ApiModelProperty(required = true)
    private String unitId;
    private String unitName;
    private String chargeTypeName;
    private String heatUserTypeName;
    private String heatingFormName;
    private String netStatusName;
    private String hasHeatMeterName;
    private String hasValveName  ;
    private String heatExchangeStatusName;
    private String houseTypeName;
    private String houseDirectName;
    private String heatingStatusName;
    private String buildingName;
    private String buildingNo;
    private String stationId;
    private String stationName;
    private String commuityId;
    private String commuityName;
    private String houseLocaltypeName;
    private String controlTypeName;
    private String prePrice;
    private String areaPrice;
    private String heatPrice;
    private String diameterName;

    public String getDiameterName() {
        return diameterName;
    }

    public void setDiameterName(String diameterName) {
        this.diameterName = diameterName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }


    public String getCommuityId() {
        return commuityId;
    }

    public void setCommuityId(String commuityId) {
        this.commuityId = commuityId;
    }

    public String getCommuityName() {
        return commuityName;
    }

    public void setCommuityName(String commuityName) {
        this.commuityName = commuityName;
    }

    public String getChargeTypeName() {
        return chargeTypeName;
    }

    public void setChargeTypeName(String chargeTypeName) {
        this.chargeTypeName = chargeTypeName;
    }

    public String getHeatUserTypeName() {
        return heatUserTypeName;
    }

    public void setHeatUserTypeName(String heatUserTypeName) {
        this.heatUserTypeName = heatUserTypeName;
    }

    public String getHeatingFormName() {
        return heatingFormName;
    }

    public void setHeatingFormName(String heatingFormName) {
        this.heatingFormName = heatingFormName;
    }

    public String getNetStatusName() {
        return netStatusName;
    }

    public void setNetStatusName(String netStatusName) {
        this.netStatusName = netStatusName;
    }

    public String getHeatExchangeStatusName() {
        return heatExchangeStatusName;
    }

    public void setHeatExchangeStatusName(String heatExchangeStatusName) {
        this.heatExchangeStatusName = heatExchangeStatusName;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getHouseTypeName() {
        return houseTypeName;
    }

    public void setHouseTypeName(String houseTypeName) {
        this.houseTypeName = houseTypeName;
    }

    public String getHouseDirectName() {
        return houseDirectName;
    }

    public void setHouseDirectName(String houseDirectName) {
        this.houseDirectName = houseDirectName;
    }

    public String getHasHeatMeterName() {
        return hasHeatMeterName;
    }

    public void setHasHeatMeterName(String hasHeatMeterName) {
        this.hasHeatMeterName = hasHeatMeterName;
    }

    public String getHasValveName() {
        return hasValveName;
    }

    public void setHasValveName(String hasValveName) {
        this.hasValveName = hasValveName;
    }

    public String getHeatingStatusName() {
        return heatingStatusName;
    }

    public void setHeatingStatusName(String heatingStatusName) {
        this.heatingStatusName = heatingStatusName;
    }

    public String getHouseLocaltypeName() {
        return houseLocaltypeName;
    }

    public void setHouseLocaltypeName(String houseLocaltypeName) {
        this.houseLocaltypeName = houseLocaltypeName;
    }

    public String getControlTypeName() {
        return controlTypeName;
    }

    public void setControlTypeName(String controlTypeName) {
        this.controlTypeName = controlTypeName;
    }


    public String getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(String prePrice) {
        this.prePrice = prePrice;
    }

    public String getAreaPrice() {
        return areaPrice;
    }

    public void setAreaPrice(String areaPrice) {
        this.areaPrice = areaPrice;
    }

    public String getHeatPrice() {
        return heatPrice;
    }

    public void setHeatPrice(String heatPrice) {
        this.heatPrice = heatPrice;
    }
}

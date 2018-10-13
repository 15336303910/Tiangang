package cn.plou.web.charge.chargeconfig.vo;

import lombok.Data;

/**
 * @Description:
 * @Param:
 * @Return:
 * @Author:
 * @Date:
 */
@Data
public class ChargeHouseDataVO {


    private String consumerId; // id

    private String address; // 地址

    private String name; // 姓名

    private String chargeArea; // 供热面积

    private String chargeType; // 收费类型

    private String heatUserType; // 用户类型

    private String houseType; // 户型

    private String contryStat; // 状态

    private String hasGenerated; // 是否生成了数据

    private String netStatus; // 入网状态

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChargeArea() {
        return chargeArea;
    }

    public void setChargeArea(String chargeArea) {
        this.chargeArea = chargeArea;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getHeatUserType() {
        return heatUserType;
    }

    public void setHeatUserType(String heatUserType) {
        this.heatUserType = heatUserType;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getContryStat() {
        return contryStat;
    }

    public void setContryStat(String contryStat) {
        this.contryStat = contryStat;
    }

    public String getHasGenerated() {
        return hasGenerated;
    }

    public void setHasGenerated(String hasGenerated) {
        this.hasGenerated = hasGenerated;
    }

    public String getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(String netStatus) {
        this.netStatus = netStatus;
    }

}
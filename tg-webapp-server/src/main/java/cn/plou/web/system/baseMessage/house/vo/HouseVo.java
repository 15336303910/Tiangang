package cn.plou.web.system.baseMessage.house.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HouseVo {

    private List<String> consumerIds;
    private List<String> rownos;
    //
    private String roomName;

    private String systemId;

    //建筑面积
    private BigDecimal buildingArea;

    private BigDecimal inBuildArea;

    private BigDecimal userBuildArea;

    //
    private String buildingNo;

    //
    private String unitId;

    //
    private String stationId;

    private String name;

    private String email;

    private BigDecimal designHeatTarget;

    private String userCompany;

    private String tel;

    //室内暖气
    private String heatingForm;

    //入网状态
    private String netStatus;

    private String heatExchangeStatus;

    private String issample;

    private String houseType;

    private String heatingArea;

    private String chargeArea;

    private String diameter;

    //热量表安装
    private String hasHeatMeter;

    //温控阀安装
    private String hasValve;

    //房屋结构
    private String houseStructureId;

    //
    private String companyId;

    //private String secPipeId;

    private String address;

    private String notes;

    private String floorHigh;
    
    private String heatingStatus;

    private String housePosition;

    private String houseLocaltype;

    private String controlType;

    private String heatingAreaFormula;

    private String chargeAreaFormula;

    private Date updateDate;

    private String updateUser;

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getUnitPriceType() {
        return unitPriceType;
    }

    public void setUnitPriceType(String unitPriceType) {
        this.unitPriceType = unitPriceType;
    }

    public String getHeatUserType() {
        return heatUserType;
    }

    public void setHeatUserType(String heatUserType) {
        this.heatUserType = heatUserType;
    }

    private String chargeType;

    private String unitPriceType;

    private String heatUserType;


    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public List<String> getConsumerIds() {
        return consumerIds;
    }

    public void setConsumerIds(List<String> consumerIds) {
        this.consumerIds = consumerIds;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public BigDecimal getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(BigDecimal buildingArea) {
        this.buildingArea = buildingArea;
    }

    public BigDecimal getInBuildArea() {
        return inBuildArea;
    }

    public void setInBuildArea(BigDecimal inBuildArea) {
        this.inBuildArea = inBuildArea;
    }

    public BigDecimal getUserBuildArea() {
        return userBuildArea;
    }

    public void setUserBuildArea(BigDecimal userBuildArea) {
        this.userBuildArea = userBuildArea;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getHeatingForm() {
        return heatingForm;
    }

    public void setHeatingForm(String heatingForm) {
        this.heatingForm = heatingForm;
    }

    public String getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(String netStatus) {
        this.netStatus = netStatus;
    }

    public String getHeatExchangeStatus() {
        return heatExchangeStatus;
    }

    public void setHeatExchangeStatus(String heatExchangeStatus) {
        this.heatExchangeStatus = heatExchangeStatus;
    }

    public String getIssample() {
        return issample;
    }

    public void setIssample(String issample) {
        this.issample = issample;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getHasHeatMeter() {
        return hasHeatMeter;
    }

    public void setHasHeatMeter(String hasHeatMeter) {
        this.hasHeatMeter = hasHeatMeter;
    }

    public String getHasValve() {
        return hasValve;
    }

    public void setHasValve(String hasValve) {
        this.hasValve = hasValve;
    }

    public String getHouseStructureId() {
        return houseStructureId;
    }

    public void setHouseStructureId(String houseStructureId) {
        this.houseStructureId = houseStructureId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFloorHigh() {
        return floorHigh;
    }

    public void setFloorHigh(String floorHigh) {
        this.floorHigh = floorHigh;
    }

    public String getHeatingArea() {
        return heatingArea;
    }

    public void setHeatingArea(String heatingArea) {
        this.heatingArea = heatingArea;
    }

    public String getChargeArea() {
        return chargeArea;
    }

	public void setChargeArea(String chargeArea) {
		this.chargeArea = chargeArea;
	}

	public void setHeatingStatus(String heatingStatus) {
		this.heatingStatus = heatingStatus;
	}

	public String getHeatingStatus() {
		return heatingStatus;
	}

    public BigDecimal getDesignHeatTarget() {
        return designHeatTarget;
    }

    public void setDesignHeatTarget(BigDecimal designHeatTarget) {
        this.designHeatTarget = designHeatTarget;
    }

    public String getHousePosition() {
        return housePosition;
    }

    public void setHousePosition(String housePosition) {
        this.housePosition = housePosition;
    }

    public String getHouseLocaltype() {
        return houseLocaltype;
    }

    public void setHouseLocaltype(String houseLocaltype) {
        this.houseLocaltype = houseLocaltype;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getHeatingAreaFormula() {
        return heatingAreaFormula;
    }

    public void setHeatingAreaFormula(String heatingAreaFormula) {
        this.heatingAreaFormula = heatingAreaFormula;
    }

    public String getChargeAreaFormula() {
        return chargeAreaFormula;
    }

    public void setChargeAreaFormula(String chargeAreaFormula) {
        this.chargeAreaFormula = chargeAreaFormula;
    }

    private Boolean batchCheckbox = false;
    private String batchModifyId;
    private String batchModifyType;
    private List<String> communitys;

    public List<String> getCommunitys() {
        return communitys;
    }

    public void setCommunitys(List<String> communitys) {
        this.communitys = communitys;
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

    public Boolean getBatchCheckbox() {
        return batchCheckbox;
    }

    public void setBatchCheckbox(Boolean batchCheckbox) {
        this.batchCheckbox = batchCheckbox;
    }
    
    public List<String> getRownos() {
      return rownos;
    }

	  public void setRownos(List<String> rownos) {
	      this.rownos = rownos;
	  }
}

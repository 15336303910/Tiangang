package cn.plou.web.system.baseMessage.unit.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class UnitVo {
    List<String> unitIds;

    private String buildingId;

    private String unitName;

    private BigDecimal buildingArea;

    private String diameter;

    private String controlType;

    private String floorNum;

    private BigDecimal floorHouse;

    private String heatingForm;

    private String companyId;

    private String address;

    private String notes;

    private String hasBalanceValve;
    private Date updateDate;

    private String updateUser;

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

    public String getHasBalanceValve() {
        return hasBalanceValve;
    }

    public void setHasBalanceValve(String hasBalanceValve) {
        this.hasBalanceValve = hasBalanceValve;
    }

    public List<String> getUnitIds() {
        return unitIds;
    }

    public void setUnitIds(List<String> unitIds) {
        this.unitIds = unitIds;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public BigDecimal getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(BigDecimal buildingArea) {
        this.buildingArea = buildingArea;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(String floorNum) {
        this.floorNum = floorNum;
    }

    public BigDecimal getFloorHouse() {
        return floorHouse;
    }

    public void setFloorHouse(BigDecimal floorHouse) {
        this.floorHouse = floorHouse;
    }

    public String getHeatingForm() {
        return heatingForm;
    }

    public void setHeatingForm(String heatingForm) {
        this.heatingForm = heatingForm;
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
}
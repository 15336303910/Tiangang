package cn.plou.web.system.baseMessage.build.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuildVo {
	
    private List<String> buildingNos;

    private String buildingName;

    private String unitNum;

    private String floorNum;

    private Integer floorHeight;

    private BigDecimal buildingArea;

    //节能建筑
    private String energySaving;

    private String preserveHeat;

   // private String beginPipeId;

    private String pipeId;

    private Integer mainDiameter;

    private String pipePosition;

    private Integer pipeLength;

    //设计热指标
    private BigDecimal heatingIndex;

    //采暖形式
    private String heatingForm;

    private String buildYear;

    private String builder;

    private Date netInTime;

    //入网合同号
    private String netInContract;

    //栋表安装
    private String hasBuildMeter;

    //平衡阀安装
    private String hasBalanceValve;

    private String chargePerson;

    //建筑属性
    private String attributes;

    //房权单位
    private String buildingRight;

    private String usingRight;

    private Integer systemNum;

    //供暖作息时间
    private String restTime;

    private String longitude;

    private String latitude;

    private String companyId;

    private String address;

    private String notes;

    private String controlType;

    private Date updateDate;
    private String updateUser;

    private String sourceType;

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {

        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<String> getBuildingNos() {
        return buildingNos;
    }

    public void setBuildingNos(List<String> buildingNos) {
        this.buildingNos = buildingNos;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(String unitNum) {
        this.unitNum = unitNum;
    }

    public String getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(String floorNum) {
        this.floorNum = floorNum;
    }

    public Integer getFloorHeight() {
        return floorHeight;
    }

    public void setFloorHeight(Integer floorHeight) {
        this.floorHeight = floorHeight;
    }

    public BigDecimal getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(BigDecimal buildingArea) {
        this.buildingArea = buildingArea;
    }

    public String getEnergySaving() {
        return energySaving;
    }

    public void setEnergySaving(String energySaving) {
        this.energySaving = energySaving;
    }

    public String getPreserveHeat() {
        return preserveHeat;
    }

    public void setPreserveHeat(String preserveHeat) {
        this.preserveHeat = preserveHeat;
    }

    public String getPipeId() {
        return pipeId;
    }

    public void setPipeId(String pipeId) {
        this.pipeId = pipeId;
    }

    public Integer getMainDiameter() {
        return mainDiameter;
    }

    public void setMainDiameter(Integer mainDiameter) {
        this.mainDiameter = mainDiameter;
    }

    public String getPipePosition() {
        return pipePosition;
    }

    public void setPipePosition(String pipePosition) {
        this.pipePosition = pipePosition;
    }

    public Integer getPipeLength() {
        return pipeLength;
    }

    public void setPipeLength(Integer pipeLength) {
        this.pipeLength = pipeLength;
    }

    public BigDecimal getHeatingIndex() {
        return heatingIndex;
    }

    public void setHeatingIndex(BigDecimal heatingIndex) {
        this.heatingIndex = heatingIndex;
    }

    public String getHeatingForm() {
        return heatingForm;
    }

    public void setHeatingForm(String heatingForm) {
        this.heatingForm = heatingForm;
    }

    public String getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(String buildYear) {
        this.buildYear = buildYear;
    }

    public String getBuilder() {
        return builder;
    }

    public void setBuilder(String builder) {
        this.builder = builder;
    }

    public Date getNetInTime() {
        return netInTime;
    }

    public void setNetInTime(Date netInTime) {
        this.netInTime = netInTime;
    }

    public String getNetInContract() {
        return netInContract;
    }

    public void setNetInContract(String netInContract) {
        this.netInContract = netInContract;
    }

    public String getHasBuildMeter() {
        return hasBuildMeter;
    }

    public void setHasBuildMeter(String hasBuildMeter) {
        this.hasBuildMeter = hasBuildMeter;
    }

    public String getHasBalanceValve() {
        return hasBalanceValve;
    }

    public void setHasBalanceValve(String hasBalanceValve) {
        this.hasBalanceValve = hasBalanceValve;
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getBuildingRight() {
        return buildingRight;
    }

    public void setBuildingRight(String buildingRight) {
        this.buildingRight = buildingRight;
    }

    public String getUsingRight() {
        return usingRight;
    }

    public void setUsingRight(String usingRight) {
        this.usingRight = usingRight;
    }

    public Integer getSystemNum() {
        return systemNum;
    }

    public void setSystemNum(Integer systemNum) {
        this.systemNum = systemNum;
    }

    public String getRestTime() {
        return restTime;
    }

    public void setRestTime(String restTime) {
        this.restTime = restTime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
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

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
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

package cn.plou.web.system.baseMessage.unit.vo;

import io.swagger.annotations.ApiModelProperty;

public class UnitSelectInfo {
    @ApiModelProperty(required = true)
    Integer page;
    @ApiModelProperty(required = true)
    Integer pageSize;
    String order;
    String sortby;
    String companyId;
    String stationId;
    String commuityId;
    String buildingNo;
    String unitId;
    UnitVo searchCondition;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSortby() {
        return sortby;
    }

    public void setSortby(String sortby) {
        this.sortby = sortby;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getCommuityId() {
        return commuityId;
    }

    public void setCommuityId(String commuityId) {
        this.commuityId = commuityId;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public UnitVo getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(UnitVo searchCondition) {
        this.searchCondition = searchCondition;
    }
    
    public void setUnitId(String unitId) {
      this.unitId = unitId;
  }

  public String getUnitId() {
      return unitId;
  }
}

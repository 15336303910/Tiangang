package cn.plou.web.balance.dataAnalysis.vo;
import cn.plou.web.balance.distribution.vo.SearchCondition;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class BalanceValveDataSelectInfo {
   @JsonFormat(pattern = "yyyy-MM-dd")
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   Date startDate;
   @JsonFormat(pattern = "yyyy-MM-dd")
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   Date endDate;
   String companyId;
   String stationId;
   String commuityId;
   String buildingNo;
   String unitId;
   Date systemReadTime;
   SearchCondition searchCondition;
   String controlMode;
   String sortby;
   String order;
   Integer page;
   Integer pageSize;

   public Date getStartDate() {
      return startDate;
   }

   public void setStartDate(Date startDate) {
      this.startDate = startDate;
   }

   public Date getEndDate() {
      return endDate;
   }

   public void setEndDate(Date endDate) {
      this.endDate = endDate;
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

   public String getUnitId() {
      return unitId;
   }

   public void setUnitId(String unitId) {
      this.unitId = unitId;
   }

   public Date getSystemReadTime() {
      return systemReadTime;
   }

   public void setSystemReadTime(Date systemReadTime) {
      this.systemReadTime = systemReadTime;
   }

   public SearchCondition getSearchCondition() {
      return searchCondition;
   }

   public void setSearchCondition(SearchCondition searchCondition) {
      this.searchCondition = searchCondition;
   }

   public String getControlMode() {
      return controlMode;
   }

   public void setControlMode(String controlMode) {
      this.controlMode = controlMode;
   }

   public String getSortby() {
      return sortby;
   }

   public void setSortby(String sortby) {
      this.sortby = sortby;
   }

   public String getOrder() {
      return order;
   }

   public void setOrder(String order) {
      this.order = order;
   }

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
}

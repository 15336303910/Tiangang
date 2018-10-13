package cn.plou.web.system.organizationMessage.staff.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StaffSelectInfo {
    private String companyId;

    private StaffVo searchCondition;

    private Integer page;

    private Integer pageSize;

    private String order;

    private String sortby;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public StaffVo getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(StaffVo searchCondition) {
        this.searchCondition = searchCondition;
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
}

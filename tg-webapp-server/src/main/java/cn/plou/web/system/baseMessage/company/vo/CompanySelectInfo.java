package cn.plou.web.system.baseMessage.company.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CompanySelectInfo {
    @ApiModelProperty(required = true)
    Integer page;
    @ApiModelProperty(required = true)
    Integer pageSize;
    String order;
    String sortby;
    String companyId;
    CompanyVo searchCondition;

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

    public CompanyVo getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(CompanyVo searchCondition) {
        this.searchCondition = searchCondition;
    }
}

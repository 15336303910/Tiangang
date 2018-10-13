package cn.plou.web.system.permission.rlRoleData.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RlRoleDataSelectInfo {
    private RlRoleDataVo searchCondition;
    private Integer page;
    private Integer pageSize;
    private String order;
    private String sortby;

    public RlRoleDataVo getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(RlRoleDataVo searchCondition) {
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

package cn.plou.web.system.permission.rlRoleValve.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RlRoleValveSelectInfo {
    private RlRoleValveVo searchCondition;
    private String order;
    private String sortby;

    public RlRoleValveVo getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(RlRoleValveVo searchCondition) {
        this.searchCondition = searchCondition;
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

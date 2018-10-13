package cn.plou.web.system.permission.userLogin.vo;

public class UserLoginSelectInfo {
    private String roleId;
    private UserLoginVo searchCondition;
    private Integer page;
    private Integer pageSize;
    private String order;
    private String sortby;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public UserLoginVo getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(UserLoginVo searchCondition) {
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

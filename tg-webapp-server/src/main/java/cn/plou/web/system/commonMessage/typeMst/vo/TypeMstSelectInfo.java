package cn.plou.web.system.commonMessage.typeMst.vo;

public class TypeMstSelectInfo {
    private Integer page;
    private Integer pageSize;
    private String typeKbn;
    private String order;
    private String sortby;
    private TypeMstVo searchCondition;

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

    public String getTypeKbn() {
        return typeKbn;
    }

    public void setTypeKbn(String typeKbn) {
        this.typeKbn = typeKbn;
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

    public TypeMstVo getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(TypeMstVo searchCondition) {
        this.searchCondition = searchCondition;
    }
}

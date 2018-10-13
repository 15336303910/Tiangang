package cn.plou.web.system.commonMessage.pageGrid.vo;

import cn.plou.web.system.commonMessage.pageGrid.entity.PageGrid;
import cn.plou.web.system.commonMessage.pageGrid.entity.PageGridKey;

import java.util.List;

public class PageGridVo extends PageGrid {
    private Integer page;
    private Integer pageSize;
    List<PageGridKey> pageGridKeys;
    PageGridSelectInfo searchCondition;
    private String pageNo;
    private String userId;
    private String order;
    private String sortby;

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

    public List<PageGridKey> getPageGridKeys() {
        return pageGridKeys;
    }

    public void setPageGridKeys(List<PageGridKey> pageGridKeys) {
        this.pageGridKeys = pageGridKeys;
    }

    public PageGridSelectInfo getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(PageGridSelectInfo searchCondition) {
        this.searchCondition = searchCondition;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

package cn.plou.web.common.config.common;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: BasePageEntity
 * @Description: 分页数据
 * @Author: youbc
 * @Date 2018-08-22 15:21
 */
public class BasePageEntity {

    private Integer page;

    private Integer pageSize;

    private String order;

    private String sortby;

    private Integer start;

    public Integer getPage() {
        if (page == null) {
            return 1;
        }
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
        if (this.page != null && this.pageSize != null) {
            this.start = (this.page - 1) * this.pageSize;
        }
    }

    public Integer getPageSize() {
        if (pageSize == null) {
            return 50000;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        if (this.page != null && this.pageSize != null) {
            this.start = (this.page - 1) * this.pageSize;
        }
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

    public Integer getStart() {
        if (start == null) {
            return 0;
        }
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }


    public void setPageParams(HttpServletRequest request) {
        String page = request.getParameter("page");
        String pageSize = request.getParameter("pageSize");
        String order = request.getParameter("order");
        String sortby = request.getParameter("sortby");
        if (page == null || page.equals("") || pageSize == null || pageSize.equals("")) {
            throw new RuntimeException("缺少分页参数");
        }
        this.page = Integer.parseInt(page);
        this.pageSize = Integer.parseInt(pageSize);
        this.order = order;
        this.sortby = sortby;

    }


}

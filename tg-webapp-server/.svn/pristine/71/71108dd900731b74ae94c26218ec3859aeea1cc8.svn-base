package cn.plou.web.common.config.common;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cond {
    private Integer page;
    private Integer perPage;
    private Integer total;
    private String sortby;
    private String order;

    public Cond(){
    }

    public Cond(Integer page, Integer perPage, Integer total, String sortby, String order) {
        this.page = page;
        this.perPage=perPage;
        this.total = total;
        this.sortby = sortby;
        this.order = order;
    }


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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

    public static Cond getCond(Integer page,Integer pageSize,Integer total,String order,String sortby){
        Cond cond = new Cond();
        cond.setOrder(order);
        cond.setSortby(sortby);
        cond.setTotal(total);
        cond.setPerPage(pageSize);
        cond.setPage(page);
        return cond;
    }
}

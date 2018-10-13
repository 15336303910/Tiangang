package cn.plou.web.system.commonMessage.weather.vo;

public class WeatherSelectInfo {
    private WeatherVo searchCondition;
    private Integer page;
    private Integer pageSize;
    private String order;
    private String sortby;
    private String zCCityid;
    private String zAAreaid;
    private String zPProvinceid;

    public String getzCCityid() {
        return zCCityid;
    }

    public void setzCCityid(String zCCityid) {
        this.zCCityid = zCCityid;
    }

    public String getzAAreaid() {
        return zAAreaid;
    }

    public void setzAAreaid(String zAAreaid) {
        this.zAAreaid = zAAreaid;
    }

    public String getzPProvinceid() {
        return zPProvinceid;
    }

    public void setzPProvinceid(String zPProvinceid) {
        this.zPProvinceid = zPProvinceid;
    }

    public WeatherVo getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(WeatherVo searchCondition) {
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

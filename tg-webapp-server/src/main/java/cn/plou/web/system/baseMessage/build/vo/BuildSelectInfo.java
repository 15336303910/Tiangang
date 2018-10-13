package cn.plou.web.system.baseMessage.build.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuildSelectInfo {
    private String companyId;
    private String stationId;
    private String commuityId;
    private BuildVo searchCondition ;
    @ApiModelProperty(required = true)
    private Integer page;
    @ApiModelProperty(required = true)
    private Integer pageSize;
    private String order;
    private String sortby;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getCommuityId() {
        return commuityId;
    }

    public void setCommuityId(String commuityId) {
        this.commuityId = commuityId;
    }

    public BuildVo getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(BuildVo searchCondition) {
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

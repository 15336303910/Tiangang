package cn.plou.web.system.baseMessage.house.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HouseKey {
    private String consumerId;

    private String rowno;

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId == null ? null : consumerId.trim();
    }

    public String getRowno() {
        return rowno;
    }

    public void setRowno(String rowno) {
        this.rowno = rowno == null ? null : rowno.trim();
    }
}
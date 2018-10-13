package cn.plou.web.system.meterMessage.meterKey.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeterKey {
    private String id;

    private String address2nd;

    private String meterKey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAddress2nd() {
        return address2nd;
    }

    public void setAddress2nd(String address2nd) {
        this.address2nd = address2nd == null ? null : address2nd.trim();
    }

    public String getMeterKey() {
        return meterKey;
    }

    public void setMeterKey(String meterKey) {
        this.meterKey = meterKey == null ? null : meterKey.trim();
    }
}
package cn.plou.web.system.baseMessage.sewageStation.vo;

import cn.plou.web.system.baseMessage.sewageStation.entity.SewageStation;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SewageStationInfo extends SewageStation {
    private String companyName;

    private String sewageStationSizeName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSewageStationSizeName() {
        return sewageStationSizeName;
    }

    public void setSewageStationSizeName(String sewageStationSizeName) {
        this.sewageStationSizeName = sewageStationSizeName;
    }
}

package cn.plou.web.system.baseMessage.company.vo;

import cn.plou.web.system.baseMessage.company.entity.Company;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyInfo extends Company {
    private String superCompanyName;
    private String companyNatureName;
    private String companyTypeName;
    private String industryName;
    private String provianceName;
    private String city;
    private String area;
    private String cityName;
    private String areaName;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getSuperCompanyName() {
        return superCompanyName;
    }

    public void setSuperCompanyName(String superCompanyName) {
        this.superCompanyName = superCompanyName;
    }

    public String getCompanyNatureName() {
        return companyNatureName;
    }

    public void setCompanyNatureName(String companyNatureName) {
        this.companyNatureName = companyNatureName;
    }

    public String getCompanyTypeName() {
        return companyTypeName;
    }

    public void setCompanyTypeName(String companyTypeName) {
        this.companyTypeName = companyTypeName;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getProvianceName() {
        return provianceName;
    }

    public void setProvianceName(String provianceName) {
        this.provianceName = provianceName;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getArea() {
        return area;
    }

    @Override
    public void setArea(String area) {
        this.area = area;
    }
}

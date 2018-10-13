package cn.plou.web.system.baseMessage.company.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.Objects;

@Api("公司")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Company extends CompanyKey {
    private String companyId;
    @ApiModelProperty(required = true)
    private String companyName;

    private String superCompanyId;

    private String address;

    private String companyNature;

    private String companySummary;

    private String tel;

    private String serviceTel;

    private String email;

    private String url;

    private String companyType;

    private String longitude;

    private String latitude;

    private String notes;

    @ApiModelProperty(required=false)
    private String memo1;

    @ApiModelProperty(required=false)
    private String memo2;

    @ApiModelProperty(required=false)
    private Date createDate;

    @ApiModelProperty(required=false)
    private String createUser;

    @ApiModelProperty(required=false)
    private Date updateDate;

    @ApiModelProperty(required=false)
    private String updateUser;

    @ApiModelProperty(required=false)
    private String industry;

    @ApiModelProperty(required=false)
    private String proviance;

    @ApiModelProperty(required=false)
    private String city;

    @ApiModelProperty(required=false)
    private String area;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getSuperCompanyId() {
        return superCompanyId;
    }

    public void setSuperCompanyId(String superCompanyId) {
        this.superCompanyId = superCompanyId == null ? null : superCompanyId.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getCompanyNature() {
        return companyNature;
    }

    public void setCompanyNature(String companyNature) {
        this.companyNature = companyNature == null ? null : companyNature.trim();
    }

    public String getCompanySummary() {
        return companySummary;
    }

    public void setCompanySummary(String companySummary) {
        this.companySummary = companySummary == null ? null : companySummary.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getServiceTel() {
        return serviceTel;
    }

    public void setServiceTel(String serviceTel) {
        this.serviceTel = serviceTel == null ? null : serviceTel.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType == null ? null : companyType.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public String getMemo1() {
        return memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1 == null ? null : memo1.trim();
    }

    public String getMemo2() {
        return memo2;
    }

    public void setMemo2(String memo2) {
        this.memo2 = memo2 == null ? null : memo2.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public String getProviance() {
        return proviance;
    }

    public void setProviance(String proviance) {
        this.proviance = proviance == null ? null : proviance.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(companyId, company.companyId) &&
                Objects.equals(companyName, company.companyName) &&
                Objects.equals(superCompanyId, company.superCompanyId) &&
                Objects.equals(address, company.address) &&
                Objects.equals(companyNature, company.companyNature) &&
                Objects.equals(companySummary, company.companySummary) &&
                Objects.equals(tel, company.tel) &&
                Objects.equals(serviceTel, company.serviceTel) &&
                Objects.equals(email, company.email) &&
                Objects.equals(url, company.url) &&
                Objects.equals(companyType, company.companyType) &&
                Objects.equals(longitude, company.longitude) &&
                Objects.equals(latitude, company.latitude) &&
                Objects.equals(notes, company.notes) &&
                Objects.equals(memo1, company.memo1) &&
                Objects.equals(memo2, company.memo2) &&
                Objects.equals(createDate, company.createDate) &&
                Objects.equals(createUser, company.createUser) &&
                Objects.equals(updateDate, company.updateDate) &&
                Objects.equals(updateUser, company.updateUser) &&
                Objects.equals(industry, company.industry) &&
                Objects.equals(proviance, company.proviance) &&
                Objects.equals(city, company.city) &&
                Objects.equals(area, company.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, companyName, superCompanyId, address, companyNature, companySummary, tel, serviceTel, email, url, companyType, longitude, latitude, notes, memo1, memo2, createDate, createUser, updateDate, updateUser, industry, proviance, city, area);
    }
}
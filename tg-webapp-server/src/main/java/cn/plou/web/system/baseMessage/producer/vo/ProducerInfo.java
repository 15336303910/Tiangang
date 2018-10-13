package cn.plou.web.system.baseMessage.producer.vo;

import cn.plou.web.system.baseMessage.producer.entity.Producer;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProducerInfo extends Producer {
    private String superProducerName;
    private String companyName;
    private String departmentName;
    private String staffName;
    private String producerTypeName;
    private String businessTypeName;

    public String getSuperProducerName() {
        return superProducerName;
    }

    public void setSuperProducerName(String superProducerName) {
        this.superProducerName = superProducerName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getProducerTypeName() {
        return producerTypeName;
    }

    public void setProducerTypeName(String producerTypeName) {
        this.producerTypeName = producerTypeName;
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }
}

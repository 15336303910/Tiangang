package cn.plou.web.system.baseMessage.producer.vo;

import cn.plou.web.system.baseMessage.company.entity.Company;
import cn.plou.web.system.baseMessage.producer.entity.Producer;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StructureInfo {
    private List<Company> companyList;
    private List<Producer> producerList;
    private String companyId;
    private String producerId;

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    public List<Producer> getProducerList() {
        return producerList;
    }

    public void setProducerList(List<Producer> producerList) {
        this.producerList = producerList;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }
}

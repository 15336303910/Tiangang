package cn.plou.web.charge.heatingmanage.dto;

import cn.plou.web.common.config.common.BasePageEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @Description: 入网管理-查询
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018-8-27 17:02:44
 */
public class NetInManageSearchDTO extends BasePageEntity {

    private String contractCode; // 合同编号

    @NotEmpty(message = "请选择公司")
    @Length(min = 5, max = 5, message = "只能按公司查询")
    private String companyId; // 公司ｉｄ

    private Date signDateStart; // 签订日期（起始入网日期）

    private Date signDateEnd; // 签订日期（截止入网日期）

    private String aName; // 甲方名称（入网单位）

    private String arrearsFlag; // 入网欠费（0-未勾选，1-勾选）

    private String priceAdjFlag; // 费用调整（0-未勾选，1-勾选）

    private String approveFlag; // 未审批（0-未勾选，1-勾选）

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Date getSignDateStart() {
        return signDateStart;
    }

    public void setSignDateStart(Date signDateStart) {
        this.signDateStart = signDateStart;
    }

    public Date getSignDateEnd() {
        return signDateEnd;
    }

    public void setSignDateEnd(Date signDateEnd) {
        this.signDateEnd = signDateEnd;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getArrearsFlag() {
        return arrearsFlag;
    }

    public void setArrearsFlag(String arrearsFlag) {
        this.arrearsFlag = arrearsFlag;
    }

    public String getPriceAdjFlag() {
        return priceAdjFlag;
    }

    public void setPriceAdjFlag(String priceAdjFlag) {
        this.priceAdjFlag = priceAdjFlag;
    }

    public String getApproveFlag() {
        return approveFlag;
    }

    public void setApproveFlag(String approveFlag) {
        this.approveFlag = approveFlag;
    }
}
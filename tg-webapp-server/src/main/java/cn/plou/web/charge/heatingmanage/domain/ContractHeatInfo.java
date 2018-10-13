package cn.plou.web.charge.heatingmanage.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 入网合同信息表
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018/8/27 9上午28
 */
public class ContractHeatInfo {

    private String primaryId; // 合同记录号 uuid

    private String companyId; // 公司ｉｄ

    private String contractCode; // 合同编号

    private String contractName; // 合同名称

    private String aName; // 甲方名称

    private String bName; // 乙方名称

    private String scope; // 入网范围

    private BigDecimal area; // 入网面积

    private BigDecimal unitPrice; // 入网单价

    private BigDecimal total; // 入网总价（入网面积*入网单价）

    private BigDecimal priceAdj; // 费用调整：正数，增加；负数，减免

    private BigDecimal accountAll; // 缴费总额

    private BigDecimal arrearsCost; // 欠费总额=入网总价+费用调整-缴费总额

    private String contactName; // 联系人

    private String contactPhone; // 联系电话

    private String filePath; // 文件名（最多 4 个）

    private String notes; // 备注

    private Date signDate; // 签订日期

    private String memo1; // 保留１（用作删除说明）

    private String memo2; // 保留２

    private Date createDate; // 创建时间

    private String createUser; // 创建者

    private Date updateDate; // 更新时间

    private String updateUser; // 更新者

    private String approveSerial; // 审批流水号2

    public String getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getPriceAdj() {
        return priceAdj;
    }

    public void setPriceAdj(BigDecimal priceAdj) {
        this.priceAdj = priceAdj;
    }

    public BigDecimal getAccountAll() {
        return accountAll;
    }

    public void setAccountAll(BigDecimal accountAll) {
        this.accountAll = accountAll;
    }

    public BigDecimal getArrearsCost() {
        return arrearsCost;
    }

    public void setArrearsCost(BigDecimal arrearsCost) {
        this.arrearsCost = arrearsCost;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getMemo1() {
        return memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1;
    }

    public String getMemo2() {
        return memo2;
    }

    public void setMemo2(String memo2) {
        this.memo2 = memo2;
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
        this.createUser = createUser;
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
        this.updateUser = updateUser;
    }

    public String getApproveSerial() {
        return approveSerial;
    }

    public void setApproveSerial(String approveSerial) {
        this.approveSerial = approveSerial;
    }
}
package cn.plou.web.charge.heatingmanage.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: HeatingServeVO
 * @Description: 入网管理-入网缴费弹框
 * @Author: youbc
 * @Date 2018-8-27 14:38:18
 */
public class NetInPayVO {

    private String primaryId; // 合同记录号 uuid

    private String contractCode; // 合同编号

    private String contractName; // 合同名称

    private String aName; // 甲方名称

    private String bName; // 乙方名称

    private BigDecimal total; // 入网总价

    private BigDecimal priceAdj; // 费用调整：正数，增加；负数，减免

    private BigDecimal accountAll; // 缴费总额（已缴费用）

    private BigDecimal arrearsCost; // 欠费总额（应缴金额/预收合计）

    private BigDecimal arrearsCostCash; // 欠费总额（现金：应缴金额/预收合计）

    private BigDecimal arrearsCostNotCash; // 欠费总额（非现金：应缴金额/预收合计）

    private String receiptno; // 收据号

    private List<PayHistoryVO> payHistory;

    public String getReceiptno() {
        return receiptno;
    }

    public void setReceiptno(String receiptno) {
        this.receiptno = receiptno;
    }

    public String getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
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

    public List<PayHistoryVO> getPayHistory() {
        return payHistory;
    }

    public void setPayHistory(List<PayHistoryVO> payHistory) {
        this.payHistory = payHistory;
    }

    public BigDecimal getArrearsCostCash() {
        return arrearsCostCash;
    }

    public void setArrearsCostCash(BigDecimal arrearsCostCash) {
        this.arrearsCostCash = arrearsCostCash;
    }

    public BigDecimal getArrearsCostNotCash() {
        return arrearsCostNotCash;
    }

    public void setArrearsCostNotCash(BigDecimal arrearsCostNotCash) {
        this.arrearsCostNotCash = arrearsCostNotCash;
    }
}

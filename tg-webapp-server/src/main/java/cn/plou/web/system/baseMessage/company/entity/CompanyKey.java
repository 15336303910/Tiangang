package cn.plou.web.system.baseMessage.company.entity;

public class CompanyKey {
    private String companyId;

    private String rowno;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getRowno() {
        return rowno;
    }

    public void setRowno(String rowno) {
        this.rowno = rowno == null ? null : rowno.trim();
    }
}
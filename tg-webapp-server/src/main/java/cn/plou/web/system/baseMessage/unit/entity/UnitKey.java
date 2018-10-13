package cn.plou.web.system.baseMessage.unit.entity;

import java.io.Serializable;

public class UnitKey implements Serializable {
    private String unitId;

    private String rowno;

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    public String getRowno() {
        return rowno;
    }

    public void setRowno(String rowno) {
        this.rowno = rowno == null ? null : rowno.trim();
    }
}
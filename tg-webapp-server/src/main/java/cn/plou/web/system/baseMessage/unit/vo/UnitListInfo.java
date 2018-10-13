package cn.plou.web.system.baseMessage.unit.vo;

import java.util.List;

public class UnitListInfo {
    List<UnitInfo> unitInfoList;
    Integer count;

    public List<UnitInfo> getUnitInfoList() {
        return unitInfoList;
    }

    public void setUnitInfoList(List<UnitInfo> unitInfoList) {
        this.unitInfoList = unitInfoList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

package cn.plou.web.system.meterMessage.mbusTest.vo;

import java.util.List;

public class MbusTestListInfo {
    List<MbusTestInfo> mbusTestInfoList;
    Integer count;

    public List<MbusTestInfo> getMbusTestInfoList() {
        return mbusTestInfoList;
    }

    public void setMbusTestInfoList(List<MbusTestInfo> mbusTestInfoList) {
        this.mbusTestInfoList = mbusTestInfoList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

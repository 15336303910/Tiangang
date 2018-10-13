package cn.plou.web.system.meterMessage.mbus.vo;

import java.util.List;

public class MbusListInfo {
    List<MbusInfo> mbusInfoList;
    Integer count;

    public List<MbusInfo> getMbusInfoList() {
        return mbusInfoList;
    }

    public void setMbusInfoList(List<MbusInfo> mbusInfoList) {
        this.mbusInfoList = mbusInfoList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

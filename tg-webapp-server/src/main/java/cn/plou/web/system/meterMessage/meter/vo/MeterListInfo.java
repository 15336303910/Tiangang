package cn.plou.web.system.meterMessage.meter.vo;

import java.util.List;

public class MeterListInfo {
    List<MeterInfo> meterInfoList;
    Integer count;

    public List<MeterInfo> getMeterInfoList() {
        return meterInfoList;
    }

    public void setMeterInfoList(List<MeterInfo> meterInfoList) {
        this.meterInfoList = meterInfoList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

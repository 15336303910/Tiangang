package cn.plou.web.system.meterMessage.mbus.vo;

import java.util.List;

public class MeterTimingDefineVo {
    List<String> mbusCodes;
    List<TimingTask> timingTasks;
    String upCommMode;
    Integer intervals;
    String consumerId;

    public List<String> getMbusCodes() {
        return mbusCodes;
    }

    public void setMbusCodes(List<String> mbusCodes) {
        this.mbusCodes = mbusCodes;
    }

    public List<TimingTask> getTimingTasks() {
        return timingTasks;
    }

    public void setTimingTasks(List<TimingTask> timingTasks) {
        this.timingTasks = timingTasks;
    }

    public String getUpCommMode() {
        return upCommMode;
    }

    public void setUpCommMode(String upCommMode) {
        this.upCommMode = upCommMode;
    }

    public Integer getIntervals() {
        return intervals;
    }

    public void setIntervals(Integer intervals) {
        this.intervals = intervals;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }
}

package cn.plou.web.system.baseMessage.system.vo;

import java.util.List;

public class SystemListInfo {
    List<SystemInfo> systemInfoList;
    Integer count;

    public List<SystemInfo> getSystemInfoList() {
        return systemInfoList;
    }

    public void setSystemInfoList(List<SystemInfo> systemInfoList) {
        this.systemInfoList = systemInfoList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

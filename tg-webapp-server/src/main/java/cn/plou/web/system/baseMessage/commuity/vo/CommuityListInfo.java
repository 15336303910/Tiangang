package cn.plou.web.system.baseMessage.commuity.vo;

import java.util.List;

public class CommuityListInfo {
    List<CommuityInfo> commuityInfoList;
    Integer count;

    public List<CommuityInfo> getCommuityInfoList() {
        return commuityInfoList;
    }

    public void setCommuityInfoList(List<CommuityInfo> commuityInfoList) {
        this.commuityInfoList = commuityInfoList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

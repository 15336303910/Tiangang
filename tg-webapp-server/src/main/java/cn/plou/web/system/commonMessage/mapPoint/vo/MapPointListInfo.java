package cn.plou.web.system.commonMessage.mapPoint.vo;

import java.util.List;

public class MapPointListInfo {
    List<MapPointInfo> mapPointInfoList;
    Integer count;

    public List<MapPointInfo> getMapPointInfoList() {
        return mapPointInfoList;
    }

    public void setMapPointInfoList(List<MapPointInfo> mapPointInfoList) {
        this.mapPointInfoList = mapPointInfoList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

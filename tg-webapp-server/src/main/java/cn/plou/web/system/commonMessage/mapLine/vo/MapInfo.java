package cn.plou.web.system.commonMessage.mapLine.vo;

import cn.plou.web.system.commonMessage.mapPoint.vo.MapPointInfo;

import java.util.List;

public class MapInfo {
    private List<MapLineInfo> mapLineInfoList;
    private List<MapPointInfo> mapPointInfoList;

    public List<MapLineInfo> getMapLineInfoList() {
        return mapLineInfoList;
    }

    public void setMapLineInfoList(List<MapLineInfo> mapLineInfoList) {
        this.mapLineInfoList = mapLineInfoList;
    }

    public List<MapPointInfo> getMapPointInfoList() {
        return mapPointInfoList;
    }

    public void setMapPointInfoList(List<MapPointInfo> mapPointInfoList) {
        this.mapPointInfoList = mapPointInfoList;
    }
}

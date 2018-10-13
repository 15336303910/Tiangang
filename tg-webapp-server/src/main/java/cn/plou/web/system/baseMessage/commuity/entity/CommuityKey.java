package cn.plou.web.system.baseMessage.commuity.entity;

import java.io.Serializable;

public class CommuityKey implements Serializable {
    private String commuityId;

    private String stationId;

    public String getCommuityId() {
        return commuityId;
    }

    public void setCommuityId(String commuityId) {
        this.commuityId = commuityId == null ? null : commuityId.trim();
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
    }
}
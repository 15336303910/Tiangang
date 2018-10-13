package cn.plou.web.system.commonMessage.weather.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherVo {

    private List<String> weatherIds;

    private String notes;
    private Date updateDate;

    private String updateUser;

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<String> getWeatherIds() {
        return weatherIds;
    }

    public void setWeatherIds(List<String> weatherIds) {
        this.weatherIds = weatherIds;
    }

}

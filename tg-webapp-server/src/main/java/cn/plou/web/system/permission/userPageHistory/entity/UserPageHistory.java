package cn.plou.web.system.permission.userPageHistory.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPageHistory {
    private String primaryId;

    private String userCode;

    private String username;

    private Date intime;

    private String col;

    private String act;

    public String getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Date getIntime() {
        return intime;
    }

    public void setIntime(Date intime) {
        this.intime = intime;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col == null ? null : col.trim();
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act == null ? null : act.trim();
    }
}
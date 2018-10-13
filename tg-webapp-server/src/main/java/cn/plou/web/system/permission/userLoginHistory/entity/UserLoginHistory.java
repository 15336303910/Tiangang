package cn.plou.web.system.permission.userLoginHistory.entity;

import java.util.Date;

public class UserLoginHistory {
    private String primaryId;
    private String userCode;

    private String username;

    private Date intime;

    private String ip;

    private String ipcity;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getIpcity() {
        return ipcity;
    }

    public void setIpcity(String ipcity) {
        this.ipcity = ipcity == null ? null : ipcity.trim();
    }
}
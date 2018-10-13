package cn.plou.web.system.permission.userLogin.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLoginVo {
    private List<String> userCodes;

    private String userPsw;

    private String username;

    private String identityCard;

    private String email;

    private String phone;

    private String tel;

    private String notes;

    private Date lasttime;

    private String devideId;

    private String status;

    private String roleId;

    public List<String> getUserCodes() {
        return userCodes;
    }

    public void setUserCodes(List<String> userCodes) {
        this.userCodes = userCodes;
    }

    public String getUserPsw() {
        return userPsw;
    }

    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getLasttime() {
        return lasttime;
    }

    public void setLasttime(Date lasttime) {
        this.lasttime = lasttime;
    }

    public String getDevideId() {
        return devideId;
    }

    public void setDevideId(String devideId) {
        this.devideId = devideId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}

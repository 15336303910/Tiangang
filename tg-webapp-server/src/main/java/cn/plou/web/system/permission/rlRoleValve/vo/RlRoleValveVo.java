package cn.plou.web.system.permission.rlRoleValve.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RlRoleValveVo {

    private List<RoleValve> roleValveList;

    private String roleId;

    public List<RoleValve> getRoleValveList() {
        return roleValveList;
    }

    public void setRoleValveList(List<RoleValve> roleValveList) {
        this.roleValveList = roleValveList;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}

package cn.plou.web.system.permission.rlRoleValve.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RlRoleValve {
    private String id;

    private String roleId;

    private String valveId;

    private String valveType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getValveId() {
        return valveId;
    }

    public void setValveId(String valveId) {
        this.valveId = valveId == null ? null : valveId.trim();
    }

    public String getValveType() {
        return valveType;
    }

    public void setValveType(String valveType) {
        this.valveType = valveType == null ? null : valveType.trim();
    }
}
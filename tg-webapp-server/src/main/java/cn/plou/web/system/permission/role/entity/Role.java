package cn.plou.web.system.permission.role.entity;

public class Role {
    private String pRoleId;

    private String roleId;

    private String roleName;

    private String roleState;

    private String roleDesc;

    public String getpRoleId() {
        return pRoleId;
    }

    public void setpRoleId(String pRoleId) {
        this.pRoleId = pRoleId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleState() {
        return roleState;
    }

    public void setRoleState(String roleState) {
        this.roleState = roleState;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
    }
}
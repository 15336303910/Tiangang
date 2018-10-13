package cn.plou.web.system.permission.rlRoleValve.vo;

import cn.plou.web.system.permission.rlRoleValve.entity.RlRoleValve;

public class RlRoleValveInfo extends RlRoleValve {

    private String roleName;

    private String valveName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getValveName() {
        return valveName;
    }

    public void setValveName(String valveName) {
        this.valveName = valveName;
    }
}

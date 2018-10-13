package cn.plou.web.system.permission.rlRoleData.vo;

import cn.plou.web.system.permission.rlRoleData.entity.RlRoleData;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RlRoleDataInfo extends RlRoleData {
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}

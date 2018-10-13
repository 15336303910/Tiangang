package cn.plou.web.system.permission.role.vo;

import cn.plou.web.system.permission.role.entity.Role;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleInfo extends Role {
    private String roleStateName;

    public String getRoleStateName() {
        return roleStateName;
    }

    public void setRoleStateName(String roleStateName) {
        this.roleStateName = roleStateName;
    }
}

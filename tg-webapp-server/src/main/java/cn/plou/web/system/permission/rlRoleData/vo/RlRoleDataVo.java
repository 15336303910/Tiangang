package cn.plou.web.system.permission.rlRoleData.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RlRoleDataVo {

    private String roleId;

    private String columnType;

    private List<RoleData> roleDataList;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<RoleData> getRoleDataList() {
        return roleDataList;
    }

    public void setRoleDataList(List<RoleData> roleDataList) {
        this.roleDataList = roleDataList;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
}

package cn.plou.web.system.commonMessage.typeMst.entity;

import io.swagger.annotations.ApiModelProperty;

public class TypeMstKey {
    @ApiModelProperty(required = true)
    private String typeKbn;
    @ApiModelProperty(required = true)
    private String typeId;

    public String getTypeKbn() {
        return typeKbn;
    }

    public void setTypeKbn(String typeKbn) {
        this.typeKbn = typeKbn;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}

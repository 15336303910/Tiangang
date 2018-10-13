package cn.plou.web.system.baseMessage.pipe.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

public class PipeKey {

    private String pipeTypeId;

    @ApiModelProperty(required = true)
    private String pipeTypeName;

    public String getPipeTypeId() {
        return pipeTypeId;
    }

    public void setPipeTypeId(String pipeTypeId) {
        this.pipeTypeId = pipeTypeId == null ? null : pipeTypeId.trim();
    }

    public String getPipeTypeName() {
        return pipeTypeName;
    }

    public void setPipeTypeName(String pipeTypeName) {
        this.pipeTypeName = pipeTypeName == null ? null : pipeTypeName.trim();
    }
}
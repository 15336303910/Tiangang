package cn.plou.web.charge.heatingmanage.dto;

import cn.plou.web.common.config.common.BasePageEntity;

import javax.validation.constraints.NotEmpty;

/**
 * @ClassName: HeatingServeSearchDTO
 * @Description: 供热服务-选择右侧树进行用户查询
 * @Author: youbc
 * @Date 2018-08-16 10:45
 */
public class HeatingServeUserSearchDTO extends BasePageEntity {

    @NotEmpty(message = "请选择范围（公司、站、小区等）")
    private String rangeId; // 右侧列表，支持小区、楼和单元

    public String getRangeId() {
        return rangeId;
    }

    public void setRangeId(String rangeId) {
        this.rangeId = rangeId;
    }
}

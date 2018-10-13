package cn.plou.web.common.config.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.github.pagehelper.PageInfo;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 定义接口返回形式
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "root")
@Component(value = "root")// 用Spring标记该组件root
@Scope("prototype")
public class Root implements Serializable{

    /**
     * 本次操作状态(-1/0/其他),默认值为{code:0,msg:""} <br>
     */
    private Integer code = Constant.SUCCESS;
    private String msg = "操作成功";

    private Object data;

    private Cond cond;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @JsonProperty(value = "cond")
    public Cond getCond() {
        return cond;
    }

    public void setCond(Cond cond) {
        this.cond = cond;
    }
}

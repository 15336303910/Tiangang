package cn.plou.web.common.config.common;

import static cn.plou.web.common.config.common.Constant.SUCCESS_STATUS_CODE;

public class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException(String msg) {
        super(msg);
        this.code = Constant.DEFAULT_ERROR_STATUS_CODE;
        //this.code = SUCCESS_STATUS_CODE;
    }

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public BusinessException(Integer code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

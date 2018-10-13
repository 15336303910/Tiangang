package cn.plou.web.common.eventhandle;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * @author yinxiaochen
 * 2018/8/22 9:21
 * 根据需求可以再扩展
 */
public class TGEvent   extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    private String msg;

    public TGEvent(Object source, String msg) {
        super(source);
        this.setMsg(msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
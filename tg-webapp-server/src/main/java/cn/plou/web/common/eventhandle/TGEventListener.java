package cn.plou.web.common.eventhandle;

import org.springframework.context.ApplicationListener;

/**
 * @author yinxiaochen
 * 2018/8/22 9:24
 */
public interface TGEventListener<E extends TGEvent> extends ApplicationListener<E> {


}

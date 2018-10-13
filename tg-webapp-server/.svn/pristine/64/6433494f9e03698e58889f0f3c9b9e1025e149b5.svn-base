package cn.plou.web.common.eventhandle;

/**
 * @author yinxiaochen
 * 2018/8/22 9:09
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 事件管理
 */

@Component
public class EventManager {

    @Autowired
    ApplicationContext appContext;
    /**
     * 发布事件
     * @param event
     */
    public  void publish(TGEvent event) {
        appContext.publishEvent(event);
    }
}


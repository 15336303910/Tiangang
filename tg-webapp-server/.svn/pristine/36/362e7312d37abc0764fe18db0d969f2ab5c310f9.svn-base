package cn.plou.web.common.config.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import cn.plou.web.common.utils.Tools;

/**
 * @Project : tg-micro
 * @File : MQCondition.java
 * @Author : WangJiWei
 * @Date : 2018年5月14日上午10:59:36
 *
 * @Comments :
 * 
 */
public class MQCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata meta) {
	String mq = context.getEnvironment().getProperty("mq.enable");
	return !Tools.isNull(mq);
    }

}


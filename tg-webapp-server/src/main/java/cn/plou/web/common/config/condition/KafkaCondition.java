package cn.plou.web.common.config.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import cn.plou.web.common.utils.Tools;

/**
 * @Project : tg-micro
 * @File : KafkaCondition.java
 * @Author : WangJiWei
 * @Date : 2018年5月10日下午1:34:14
 *
 * @Comments :
 * 
 */
public class KafkaCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata meta) {
	String mq = context.getEnvironment().getProperty("mq.enable");
	return Tools.isNull(mq) ? false : "kafka".equals(mq.toLowerCase().trim());
    }

}

package cn.plou.web.common.config.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Project : tg-micro
 * @File : WindowsCondition.java
 * @Author : WangJiWei
 * @Date : 2018年5月10日下午2:09:18
 *
 * @Comments :
 * 
 */
public class WindowsCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
	String env = context.getEnvironment().getProperty("os.system");
	return env.contains("window");
    }

}

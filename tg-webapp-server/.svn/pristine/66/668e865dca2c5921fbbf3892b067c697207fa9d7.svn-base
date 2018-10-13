package cn.plou.web.common.config.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Project : tg-micro
 * @File : ESCondition.java
 * @Author : WangJiWei
 * @Date : 2018年5月14日下午1:39:20
 *
 * @Comments :
 * 
 */
public class ESCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
	boolean es = Boolean.valueOf(context.getEnvironment().getProperty("elasticsearch.enable"));
	return es;
    }

}

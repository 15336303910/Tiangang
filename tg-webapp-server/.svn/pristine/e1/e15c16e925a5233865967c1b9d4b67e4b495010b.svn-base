package cn.plou.web.common.annotation;

import java.lang.annotation.*;

/**
 * @author yinxiaochen
 * 2018/9/28 14:07
 * 访问接口记录注解
 */

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VisitRecord {

    /** 要执行的具体操作**/
    public String operationName() default "";


}

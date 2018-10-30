package cn.plou.web.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: Excel 导出
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 18/10/23 17下午21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExportExcel {

    int index();

    String name();
}

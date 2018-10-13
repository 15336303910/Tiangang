package cn.plou.web.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import org.springframework.cglib.beans.BeanMap;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

/**
 * @Project : tg-services
 * @File : BeanUtils.java
 * @Author : WangJiWei
 * @Date : 2018年2月10日上午7:39:05
 *
 * @Comments :
 * 
 */
public class JBeanUtils {

    /**
     * json -> bean
     */
    public static <T> T toBean(String json, Class<T> clzz) {
	return parseObject(json, clzz);
    }

    /**
     * map -> bean
     */
    public static <T> T toBean(Map<?, ?> map, Class<T> clzz) {
	return parseObject(JSON.toJSONString(map), clzz);
    }

    private static <T> T parseObject(String text, Class<T> clazz) {
	return JSON.parseObject(text, clazz, new Feature[0]);
    }

    public static <T> T mapToBean(Map<String, Object> map, T bean) throws Exception {
	BeanMap beanMap = BeanMap.create(bean);
	beanMap.putAll(map);
	return bean;
    }

    //
    public static Map<?, ?> objectToMap(Object obj) {
	return obj == null ? null : BeanMap.create(obj);
    }

    public class TypeR<T> {
	private final Type type;

	protected TypeR() {
	    Type superClass = getClass().getGenericSuperclass();
	    type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
	}

	public Type getType() {
	    return this.type;
	}
    }
}

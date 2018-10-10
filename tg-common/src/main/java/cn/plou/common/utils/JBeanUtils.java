package cn.plou.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.google.common.collect.Lists;

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

    /**
     * cglib BeanCopier 15ms
     * 
     * Spring BeanUtil 4031ms
     * 
     * apache BeanUtils 18514ms.
     *
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) throws Exception {
	BeanMap beanMap = BeanMap.create(bean);
	beanMap.putAll(map);
	return bean;
    }

    //
    public static Map<String, Object> objectToMap(Object obj) {
	return obj == null ? null : BeanMap.create(obj);
	// JSONObject json = (JSONObject) JSON.toJSON(h);
	// Map<String, Object> mm = json;
    }

    public static <T> List<T> parseArray(List<Map<String, Object>> list, Class<T> clazz) {
	List<T> ssi = JSON.parseArray(JSON.toJSONString(list), clazz);
	return ssi;
    }

    public static <T> List<T> parseArray(String json, Class<T> clazz) {
	List<T> ssi = JSON.parseArray(json, clazz);
	return ssi;
    }

    public static <T> List<T> listToBean(List<Map<String, Object>> maps, Class<T> clazz)
	    throws Exception {
	List<T> list = null;
	if (maps != null && maps.size() != 0) {
	    list = new ArrayList<>();
	    T bean;
	    for (Map<String, Object> m : maps) {
		T o = clazz.newInstance();
		bean = mapToBean(m, o);
		list.add(bean);
	    }
	}
	return list;
    }

    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
	List<Map<String, Object>> list = Lists.newArrayList();
	if (objList != null && objList.size() > 0) {
	    Map<String, Object> map;
	    for (T b : objList) {
		map = objectToMap(b);
		list.add(map);
	    }
	}
	return list;
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

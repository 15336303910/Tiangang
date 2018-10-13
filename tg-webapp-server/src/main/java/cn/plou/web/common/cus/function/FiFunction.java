package cn.plou.web.common.cus.function;

/** 
 * @Project : tg-micro 
 * @File : FiFunction.java
 * @Author : WangJiWei
 * @Date : 2018年5月13日上午8:46:18 
 *
 * @Comments : 
 * 
 */
@FunctionalInterface
public interface FiFunction<T, E, O, F> {
	public F getValue(T t, E e, O o);
}

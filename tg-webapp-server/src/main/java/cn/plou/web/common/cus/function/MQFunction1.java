package cn.plou.web.common.cus.function;

/** 
 * @Project : tg-micro 
 * @File : MQFunction1.java
 * @Author : WangJiWei
 * @Date : 2018年5月13日上午8:47:10 
 *
 * @Comments : 
 * 
 */
@FunctionalInterface
public interface MQFunction1<T, E> {
	public E handle(T t);
}

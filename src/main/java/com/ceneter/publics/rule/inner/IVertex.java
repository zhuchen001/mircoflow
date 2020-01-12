package com.ceneter.publics.rule.inner;

import java.io.Serializable;

/**
 * 算子
 * 
 * @author ZHUZHU
 *
 * @param <T>
 */
public interface IVertex<T extends Serializable> {

	/**
	 * 条件判断
	 * 
	 * @param t
	 */
	boolean when(T t);

	/**
	 * 获取执行顺序
	 * 
	 * @return
	 */
	int order();

	/**
	 * 描述
	 * 
	 * @return
	 */
	default String desc() {
		return "desc";
	}

	/**
	 * 所属分组
	 * 
	 * @return
	 */
	default GroupEumn group() {
		return GroupEumn.Default.DEFAULT;
	}

	/**
	 * 执行规则(如果执行失败通过Runtime异常抛出)
	 * 
	 * @param t
	 */
	void then(T t);

	/**
	 * 设置执行终止
	 * 
	 * @param message
	 */
	default void setBreak(String message) {
		RuleThreadLocal.set(message);
	}

	/**
	 * 算子类型
	 * @return
	 */
	default VertexType type() {
		return VertexType.SYN;
	}
	
	/**
	 * 唯一标识算子(默认包名+类名)
	 * @return
	 */
	default String id() {
		return getClass().getName();
	}
}

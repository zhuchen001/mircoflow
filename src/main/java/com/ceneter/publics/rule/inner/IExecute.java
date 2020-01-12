/**
 * 
 */
package com.ceneter.publics.rule.inner;

import java.io.Serializable;

/**
 * @author zhuzhu
 *
 */
public interface IExecute<T extends Serializable> extends BaseDefined<T> {

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
}

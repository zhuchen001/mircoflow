/**
 * 
 */
package com.ceneter.publics.rule.inner;

import java.io.Serializable;

/**
 * @author zhuzhu
 *
 */
public interface ICondition<T extends Serializable> extends BaseDefined<T> {
	/**
	 * 条件判断
	 * 
	 * @param t
	 */
	boolean when(T t);
}

/**
 * 
 */
package com.center.microflow.api;

import java.io.Serializable;

/**
 * 算子执行条件
 * 
 * @author Administrator
 *
 */
public interface ICondition<T extends Serializable> {
	
	/**
	 * 算子执行条件
	 */
	boolean when(T t);
}

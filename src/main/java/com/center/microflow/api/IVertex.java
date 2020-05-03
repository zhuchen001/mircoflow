/**
 * 
 */
package com.center.microflow.api;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 执行算子
 * 
 * @author Administrator
 *
 */
public interface IVertex <T extends Serializable> extends IExecute<T>, ICondition<T>, BaseDefined<T> {
	
	/**
	 * 执行顺序
	 */
	BigDecimal order();

}

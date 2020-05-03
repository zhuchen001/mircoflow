/**
 * 
 */
package com.center.microflow.api;

/**
 * @author Administrator
 *
 */
public interface DeepCopy {
	
	/**
	 * 深度拷贝
	 * 
	 * @param src
	 * @return
	 */
	<T> T copy(T src);

	/**
	 * 加载顺序(降序，即越大越优先执行)
	 */
	int order();
}

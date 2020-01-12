/**
 * 
 */
package com.ceneter.publics.rule.inner;

import java.io.Serializable;

/**
 * 基础方法定义
 * 
 * @author zhuzhu
 *
 */
public interface BaseDefined<T extends Serializable> {
	/**
	 * 参数
	 * 
	 * @return
	 */
	default Parameter[] parameters() {
		return null;
	}

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
}

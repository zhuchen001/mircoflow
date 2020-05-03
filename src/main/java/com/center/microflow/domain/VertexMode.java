/**
 * 
 */
package com.center.microflow.domain;

/**
 * 算子执行模式
 * 
 * @author Administrator
 *
 */
public enum VertexMode {
	/**
	 * 默认按顺序执行
	 */
	DEFAULT,
	/**
	 * finally执行，即只要进入流程，只要流程不异常退出，那么即便前面的算子break，此算子也会执行
	 */
	FINALLY
}

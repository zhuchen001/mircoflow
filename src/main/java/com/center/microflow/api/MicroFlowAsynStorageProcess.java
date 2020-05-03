/**
 * 
 */
package com.center.microflow.api;

/**
 * 异步算子执行（持久化）
 * 
 * @author Administrator
 *
 */
public interface MicroFlowAsynStorageProcess extends MicroFlowAsynProcess {
	
	/**
	 * 异步任务类型
	 */
	String TYPE_NAME = " MicroFlowAsynStorageJOB";
}

/**
 * 
 */
package com.center.microflow.api;

import com.center.microflow.domain.ExecuteResult;

/**
 * 异常转FailInfo定义
 * 
 * @author Administrator
 *
 */
public interface MicroFlowExceptionChange {
	
	ExecuteResult.FailInfo exceptionToFailInfo(Throwable exception);
	
	Class<?> exceptionClass();
	
}

/**
 * 
 */
package com.center.microflow.domain;

import java.io.Serializable;

/**
 * 重试策略
 * 
 * @author Administrator
 *
 */
public class RetryPolicy implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public static final RetryPolicy NONE = new RetryPolicy(0);
	public static final RetryPolicy COMMON = new RetryPolicy(3);
	public static final RetryPolicy ALWAYS = new RetryPolicy(Integer.MAX_VALUE);
	
	/**
	 * 已重试次数
	 */
	private int retryTimes;
	
	/**
	 * 重试最大次数
	 */
	private int maxRetryTimes;
	
	/**
	 * 重试间隔（默认30S）
	 */
	private int retryInterval = 30;

	public RetryPolicy() {
	}

	public RetryPolicy(int maxRetryTimes) {
		this.maxRetryTimes = maxRetryTimes;
	}

	public RetryPolicy(int maxRetryTimes, int retryInterval) {
		this.maxRetryTimes = maxRetryTimes;
		this.retryInterval = retryInterval;
	}

	public RetryPolicy(RetryPolicy policy) {
		this.maxRetryTimes = policy.maxRetryTimes;
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	public int getMaxRetryTimes() {
		return maxRetryTimes;
	}

	public void setMaxRetryTimes(int maxRetryTimes) {
		this.maxRetryTimes = maxRetryTimes;
	}
	
	public int increaseRetryTimes()
	{
		return ++this.retryTimes;
	}

	public int getRetryInterval() {
		return retryInterval;
	}

	public void setRetryInterval(int retryInterval) {
		this.retryInterval = retryInterval;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RetryPolicy [retryTimes=");
		builder.append(retryTimes);
		builder.append(", maxRetryTimes=");
		builder.append(maxRetryTimes);
		builder.append(", retryInterval=");
		builder.append(retryInterval);
		builder.append("]");
		return builder.toString();
	}


}

/**
 * 
 */
package com.ceneter.publics.rule.inner;

/**
 * @author zhuzhu
 *
 */
public class RuleThreadLocal {
	public static ThreadLocal<BreakBean> threadLocal = new ThreadLocal<BreakBean>();

	public static void set(BreakBean value) {
		threadLocal.set(value);
	}
	
	public static void set() {
		BreakBean breakBean = new BreakBean();
		threadLocal.set(breakBean);
	}

	public static void set(String message) {
		BreakBean breakBean = threadLocal.get();
		breakBean.setBreak(message);
		threadLocal.set(breakBean);
	}

	public static BreakBean get() {
		return threadLocal.get();
	}

	public static void remove() {
		threadLocal.remove();
	}

}

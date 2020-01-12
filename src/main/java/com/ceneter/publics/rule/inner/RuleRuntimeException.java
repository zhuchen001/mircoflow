/**
 * 
 */
package com.ceneter.publics.rule.inner;

/**
 * 规则引擎内部抛出的运行异常
 * 
 * @author zhuzhu
 *
 */
public class RuleRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;

	public RuleRuntimeException(String code, String message) {
		super(message);
		this.code = code;
	}

	public RuleRuntimeException(String message) {
		super(message);
	}

	public RuleRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public RuleRuntimeException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}

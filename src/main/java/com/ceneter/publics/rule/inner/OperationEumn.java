package com.ceneter.publics.rule.inner;

/**
 * 操作类型
 * @author zhuzhu
 *
 */
public enum OperationEumn {
	ADD("+"), EQUALS("=");//and more

	private final String operation;

	public String getOperation() {
		return operation;
	}

	private OperationEumn(String operation) {
		this.operation = operation;
	}

}

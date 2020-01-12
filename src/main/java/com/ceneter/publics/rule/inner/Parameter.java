/**
 * 
 */
package com.ceneter.publics.rule.inner;

import java.io.Serializable;

/**
 * 参数定义(映射规则引擎的参数表)
 * 
 * @author zhuzhu
 *
 */
public class Parameter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 参数唯一标识
	 */
	private String key;

	/**
	 * 参数展示的名称
	 */
	private String displayName;

	/**
	 * 参数描述
	 */
	private String desc;

	/**
	 * 参数具体的值
	 */
	private String value;

	/**
	 * 参数支持的操作类型
	 */
	private OperationEumn[] supportoOperations = new OperationEumn[] {
			OperationEumn.ADD, OperationEumn.EQUALS };

	/**
	 * 参数的实际操作类型
	 */
	private OperationEumn operation;

	/**
	 * 是否必须
	 */
	private boolean required;

	/**
	 * 正在表达式
	 */
	private String regEx;

	/**
	 * 0:String 1:int 2:BigDecimal(double) 3:枚举
	 */
	private int type;

	public Parameter() {
	}

	public Parameter(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public OperationEumn[] getSupportoOperations() {
		return supportoOperations;
	}

	public void setSupportoOperations(OperationEumn[] supportoOperations) {
		this.supportoOperations = supportoOperations;
	}

	public OperationEumn getOperation() {
		return operation;
	}

	public void setOperation(OperationEumn operation) {
		this.operation = operation;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRegEx() {
		return regEx;
	}

	public void setRegEx(String regEx) {
		this.regEx = regEx;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}

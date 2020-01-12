/**
 * 
 */
package com.ceneter.publics.rule.inner;

/**
 * 具体的分支
 * @author zhuzhu
 *
 */
@SuppressWarnings("rawtypes")
public class BranchElement {
	private String name;
	private BranchEnum type;
	private ICondition condition;
	private Object rule;
	
	
	public BranchElement(String name, BranchEnum type, ICondition condition, Object rule) {
		this.name = name;
		this.condition = condition;
		this.type = type;
		this.rule = rule;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ICondition getCondition() {
		return condition;
	}
	public void setCondition(ICondition condition) {
		this.condition = condition;
	}
	public BranchEnum getType() {
		return type;
	}
	public void setType(BranchEnum type) {
		this.type = type;
	}
	public Object getRule() {
		return rule;
	}
	public void setRule(Object rule) {
		this.rule = rule;
	}
	
	
}

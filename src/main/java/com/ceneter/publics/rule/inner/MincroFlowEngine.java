/**
 * 
 */
package com.ceneter.publics.rule.inner;

import java.io.Serializable;

/**
 * 规则流引擎
 * 
 * @author zhuzhu
 *
 */
public interface MincroFlowEngine<T extends Serializable> {
	/**
	 * 新增stage(规则组)
	 * 
	 * @param group
	 */
	MincroFlowEngine<T> addStage(String stage, GroupEumn group);

	/**
	 * 新增stage(单规则)
	 * 
	 * @param stage
	 * @param execute
	 */
	MincroFlowEngine<T> addStage(String stage, IVertex<T> rule);

	/**
	 * 新增stage(子流程)
	 * 
	 * @param stage
	 * @param subFlow
	 * @return
	 */
	MincroFlowEngine<T> addStage(String stage, MincroFlowEngine<T> subFlow);

	/**
	 * 新增stage(分支处理)
	 * 
	 * @param stage
	 * @param branch
	 * @return
	 */
	MincroFlowEngine<T> addStage(String stage, BranchRule<T> branch);

	/**
	 * 执行
	 * 
	 * @param t
	 * @return
	 */
	MincroFlowEngine<T> execute(T t);
	
	/**
	 * 微流程标识(唯一区分)
	 * @param name
	 * @return
	 */
	MincroFlowEngine<T> setName(String name);
}

/**
 * 
 */
package com.ceneter.publics.rule.inner;

import java.io.Serializable;

/**
 * 分支规则（支持多个分支）
 * 
 * @author zhuzhu
 *
 */
public interface BranchRule<T extends Serializable> {

	/**
	 * 增加一个分支条件下的分组处理
	 * 
	 * @param con
	 * @param group
	 * @return
	 */
	BranchRule<T> addBranch(String name, ICondition<T> con, GroupEumn group);

	/**
	 * 增加一个分支条件下的子流程处理
	 * 
	 * @param con
	 * @param group
	 * @return
	 */
	BranchRule<T> addBranch(String name, ICondition<T> con, MincroFlowEngine<T> subFlow);
	
	/**
	 * 增加一个分支条件下的分支条件(组成分支树)
	 * @param name
	 * @param con
	 * @param branch
	 * @return
	 */
	BranchRule<T> addBranch(String name, ICondition<T> con, BranchRule<T> branch);

	/**
	 * 执行
	 * 
	 * @param t
	 * @return
	 */
	BranchRule<T> execute(T t);
}

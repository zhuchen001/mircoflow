/**
 * 
 */
package com.ceneter.publics.rule.inner.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ceneter.publics.rule.inner.BranchRule;
import com.ceneter.publics.rule.inner.GroupEumn;
import com.ceneter.publics.rule.inner.IVertex;
import com.ceneter.publics.rule.inner.MincroFlowEngine;
import com.ceneter.publics.rule.inner.RuleRuntimeException;
import com.ceneter.publics.rule.inner.RuleThreadLocal;
import com.ceneter.publics.rule.inner.Stage;
import com.ceneter.publics.rule.inner.StageEnum;
import com.ceneter.publics.rule.inner.VertexType;

/**
 * @author zhuzhu
 *
 */
public class MincroFlowEngineImpl<T extends Serializable> implements
		MincroFlowEngine<T> {

	private List<Stage> stageList = new ArrayList<Stage>();

	private Set<String> stageName = new HashSet<String>();

	private int order;

	private String name;

	public MincroFlowEngine<T> addStage(String name, GroupEumn group) {

		if (!stageName.add(name)) {
			throw new RuleRuntimeException("duplicate stage:" + name);
		}

		List<IVertex<T>> ruleListByGroup = getRuleListByGroup(group);

		Stage stage = new Stage(name, this.order++, StageEnum.RULE_GROUP,
				ruleListByGroup);

		stageList.add(stage);

		MincroFlowEngineCallback.getInstance().addStageCallback(this.name,
				stage);

		return this;
	}

	public MincroFlowEngine<T> addStage(String stage, IVertex<T> rule) {
		stageList.add(new Stage(stage, this.order++, StageEnum.RULE, rule));

		return this;
	}

	public MincroFlowEngine<T> addStage(String stage,
			MincroFlowEngine<T> subFlow) {

		if (!stageName.add(stage)) {
			throw new RuleRuntimeException("duplicate stage");
		}

		stageList.add(new Stage(stage, this.order++, StageEnum.SUB_FLOW,
				subFlow));

		return this;
	}

	public MincroFlowEngine<T> addStage(String stage, BranchRule<T> branch) {

		if (!stageName.add(stage)) {
			throw new RuleRuntimeException("duplicate stage");
		}

		stageList.add(new Stage(stage, this.order++, StageEnum.BRANCH, branch));

		return this;
	}

	@SuppressWarnings("unchecked")
	public MincroFlowEngine<T> execute(T t) {
		// 加入中断的上下文
		RuleThreadLocal.set();

		for (Stage stage : stageList) {
			switch (stage.getType()) {

			case RULE_GROUP:

				List<IVertex<T>> ruleList = (List<IVertex<T>>) (stage.getRule());

				for (IVertex<T> iRule : ruleList) {
					if (isContinue() && iRule.when(t)) {
						iRule.then(t);
					}
				}

				break;

			case RULE:
				IVertex<T> rule = (IVertex<T>) stage.getRule();
				if (isContinue() && rule.when(t)) {
					executeVertex(rule, t);
				}
				break;

			case SUB_FLOW:
				if (isContinue()) {
					MincroFlowEngine<T> subFlow = (MincroFlowEngine<T>) stage
							.getRule();
					subFlow.execute(t);
				}
				break;

			case BRANCH:
				if (isContinue()) {
					BranchRule<T> branch = (BranchRule<T>) stage.getRule();
					branch.execute(t);
				}
				break;

			default:
				break;
			}
		}

		// 清理上下文
		RuleThreadLocal.remove();

		return this;
	}

	private void executeVertex(IVertex<T> vertex, T t) {
		if (vertex.type() == VertexType.SYN)
			vertex.then(t);
		if (vertex.type() == VertexType.ASYN_MEM) 
		{
			// 加入队列
		}
		
		if (vertex.type() == VertexType.ASYN_STORGE) 
		{
			//加入任务框架
		}
	}

	private List<IVertex<T>> getRuleListByGroup(GroupEumn group) {
		return null;
	}

	private boolean isContinue() {
		return !RuleThreadLocal.get().isBreakLogic();
	}

	public MincroFlowEngine<T> setName(String name) {
		this.name = name;

		return this;
	}

}

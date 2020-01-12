/**
 * 
 */
package com.ceneter.publics.rule.inner.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ceneter.publics.rule.inner.BranchElement;
import com.ceneter.publics.rule.inner.BranchEnum;
import com.ceneter.publics.rule.inner.BranchRule;
import com.ceneter.publics.rule.inner.GroupEumn;
import com.ceneter.publics.rule.inner.ICondition;
import com.ceneter.publics.rule.inner.IVertex;
import com.ceneter.publics.rule.inner.MincroFlowEngine;
import com.ceneter.publics.rule.inner.RuleThreadLocal;

/**
 * 分支Rule的实现
 * 
 * @author zhuzhu
 *
 */
public class BranchRuleImpl<T extends Serializable> implements BranchRule<T> {

	private List<BranchElement> branchElementList = new ArrayList<BranchElement>();

	public BranchRule<T> addBranch(String name, ICondition<T> con,
			GroupEumn group) {
		List<IVertex<T>> ruleListByGroup = getRuleListByGroup(group);
		branchElementList.add(new BranchElement(name, BranchEnum.RULE_GROUP,
				con, ruleListByGroup));
		return this;
	}

	public BranchRule<T> addBranch(String name, ICondition<T> con,
			MincroFlowEngine<T> subFlow) {
		branchElementList.add(new BranchElement(name, BranchEnum.SUB_FLOW, con,
				subFlow));
		return this;
	}

	public BranchRule<T> addBranch(String name, ICondition<T> con,
			BranchRule<T> branch) {
		branchElementList.add(new BranchElement(name, BranchEnum.BRANCH, con,
				branch));
		return this;
	}

	@SuppressWarnings("unchecked")
	public BranchRule<T> execute(T t) {
		for (BranchElement branchElement : branchElementList) {

			switch (branchElement.getType()) {
			case RULE_GROUP:
				if (isContinue() && branchElement.getCondition().when(t)) {
					List<IVertex<T>> ruleList = (List<IVertex<T>>) (branchElement
							.getRule());

					for (IVertex<T> iRule : ruleList) {
						if (isContinue() && iRule.when(t)) {
							iRule.then(t);
						}
					}
				}
				break;

			case SUB_FLOW:
				if (isContinue() && branchElement.getCondition().when(t)) {
					MincroFlowEngine<T> subFlow = (MincroFlowEngine<T>) branchElement
							.getRule();
					subFlow.execute(t);
				}
				break;

			case BRANCH:
				if (isContinue() && branchElement.getCondition().when(t)) {
					BranchRule<T> branch = (BranchRule<T>) branchElement
							.getRule();
					branch.execute(t);
				}
				break;

			default:
				break;
			}

		}
		return this;
	}

	private List<IVertex<T>> getRuleListByGroup(GroupEumn group) {
		return null;
	}

	private boolean isContinue() {
		return !RuleThreadLocal.get().isBreakLogic();
	}

}

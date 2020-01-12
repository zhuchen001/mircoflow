/**
 * 
 */
package com.ceneter.publics.rule.inner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuzhu
 *
 */
public class Stage {
	private String name;
	private int order;
	private StageEnum type;
	private Object rule;

	public Stage() {
	}

	public Stage(String name, int order, StageEnum type, Object rule) {
		this.name = name;
		this.order = order;
		this.type = type;
		this.rule = rule;
	}

	/**
	 * 分析是否存在异步流程
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, IVertex<?>> analysisAsyn() {
		Map<String, IVertex<?>> result = new HashMap<String, IVertex<?>>();
		if (this.type == StageEnum.RULE) {
			IVertex<?> vertex = (IVertex<?>) this.rule;

			if (vertex.type() != VertexType.SYN) {
				result.put(this.name + "-" + vertex.id(), vertex);
				result.put(vertex.id(), vertex);
			}
		}

		if (this.type == StageEnum.RULE_GROUP) {
			List<IVertex<?>> ruleList = (List<IVertex<?>>) (this.rule);

			for (IVertex<?> vertex : ruleList) {
				if (vertex.type() != VertexType.SYN) {
					result.put(this.name + "-" + vertex.id(), vertex);
					result.put( vertex.id(), vertex);
				}
			}
		}

		return result;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Object getRule() {
		return rule;
	}

	public void setRule(Object rule) {
		this.rule = rule;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StageEnum getType() {
		return type;
	}

	public void setType(StageEnum type) {
		this.type = type;
	}

}

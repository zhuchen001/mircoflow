/**
 * 
 */
package com.ceneter.publics.rule.inner.impl;

import java.util.HashMap;
import java.util.Map;

import com.ceneter.publics.rule.inner.IVertex;
import com.ceneter.publics.rule.inner.Stage;

/**
 * 异步回调（全局）
 * 
 * @author zhuzhu
 *
 */
public class MincroFlowEngineCallback {
	private Map<String, IVertex<?>> cahce = new HashMap<String, IVertex<?>>();

	/**
	 * 懒加载
	 * 
	 * @author zhuzhu
	 *
	 */
	private static class SingletonClassInstance {
		private static final MincroFlowEngineCallback instance = new MincroFlowEngineCallback();
	}

	private MincroFlowEngineCallback() {
	}

	public synchronized void addStageCallback(String mincroFlowEngineName,
			Stage stage) {

		stage.analysisAsyn().forEach(
				(k, v) -> this.cahce.put(mincroFlowEngineName + "-" + k, v));
	}

	/**
	 * 获取异步任务的执行对象(先严格匹配，然后允许去除阶段名再匹配)
	 * 
	 * @param mincroFlowEngineName
	 *            微流程名
	 * @param stageName
	 *            阶段名
	 * @param id
	 *            算子ID
	 * @return
	 */
	public IVertex<?> getCallbackIVertex(String mincroFlowEngineName,
			String stageName, String vid) {
		IVertex<?> iVertex = this.cahce.get(mincroFlowEngineName + "-"
				+ stageName + "-" + vid);

		if (iVertex == null) {
			iVertex = this.cahce.get(mincroFlowEngineName + "-" + vid);
		}

		return iVertex;
	}

	public static MincroFlowEngineCallback getInstance() {
		return SingletonClassInstance.instance;
	}
}

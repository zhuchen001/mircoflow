/**
 * 
 */
package com.center.microflow.factory;

import com.center.microflow.api.BranchDecide;
import com.center.microflow.api.BranchFlow;
import com.center.microflow.api.MicroFlow;
import com.center.microflow.impl.BranchFlowImpl;
import com.center.microflow.impl.MicroFlowEngineImpl;

import java.io.Serializable;

/**
 * 微流程的入口工厂类
 * 
 * @author Administrator
 *
 */
public abstract class MicroFlowEngineFactory {
	
	/**
	 * 创建新的微流程定义
	 */
	public static <T extends Serializable> MicroFlow<T> createMicroFlow(String name, Class<T> claz)
	{
		return createMicroFlow(name, claz, false);
	}

	/**
	 * 创建新的微流程定义
	 */
	public static <T extends Serializable> MicroFlow<T> createMicroFlow(String name, Class<T> claz, boolean debug)
	{
		MicroFlowEngineImpl<T> engine = new MicroFlowEngineImpl<T>(name, debug);

		return engine;
	}

	/**
	 * 构建新的分支处理
	 */
	public static <T extends Serializable> BranchFlow<T> createBranchFlow(String name, BranchDecide<T> decide, Class<T> claz)
	{
		BranchFlowImpl<T> branch = new BranchFlowImpl<T>(name);
		
		branch.setDecide(decide);
		
		return branch;
	}

}

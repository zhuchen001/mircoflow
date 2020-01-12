/**
 * 
 */
package com.ceneter.publics.rule.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhuzhu
 *
 */
public class MyInvocationHandler implements InvocationHandler {
	// 目标对象
	private Object target;

	/**
	 * 构造方法
	 * 
	 * @param target
	 *            目标对象
	 */
	public MyInvocationHandler(Object target) {
		super();
		this.target = target;
	}

	/**
	 * 执行目标对象的方法
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		// 在目标对象的方法执行之前简单的打印一下
		System.out.println("------------------before------------------");

		// 执行目标对象的方法
		Object result = method.invoke(target, args);

		// 在目标对象的方法执行之后简单的打印一下
		System.out.println("-------------------after------------------");

		return result;
	}

	/**
	 * 获取目标对象的代理对象
	 * 
	 * @return 代理对象
	 */
	public Object getProxy() {
		return Proxy.newProxyInstance(Thread.currentThread()
				.getContextClassLoader(), getAllInterfaces().toArray(new Class[0]),
				this);
	}
	
	public List<Class<?>> getAllInterfaces()
	{
		List<Class<?>> result = new ArrayList<Class<?>>();
		
		result.addAll(Arrays.asList(target.getClass().getInterfaces()));
		
		Class<?> sup =  target.getClass().getSuperclass();
		
		while (sup != Object.class)
		{
			result.addAll(Arrays.asList(sup.getInterfaces()));
			
			sup = sup.getSuperclass();
		}
		
		return result;
	}
}

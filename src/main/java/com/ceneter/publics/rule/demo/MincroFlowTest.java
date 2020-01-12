/**
 * 
 */
package com.ceneter.publics.rule.demo;

import com.ceneter.publics.rule.inner.IVertex;

/**
 * @author zhuzhu
 *
 */
public class MincroFlowTest {

	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		VertexImpl1 ver1 = new VertexImpl1();
		VertexImpl2 ver2 = new VertexImpl2();
		
		MyInvocationHandler invocationHandler = new MyInvocationHandler(ver2);  
		
		System.out.println(ver1.id());
		System.out.println(ver2.id());
		
		IVertex<String> proxy = (IVertex<String>)invocationHandler.getProxy();
		
		System.out.println(proxy.id());

	}

}

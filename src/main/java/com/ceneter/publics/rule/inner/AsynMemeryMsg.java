/**
 * 
 */
package com.ceneter.publics.rule.inner;

import java.io.Serializable;

/**
 * @author zhuzhu
 *
 */
@SuppressWarnings("rawtypes")
public class AsynMemeryMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Object bo;
	
	private IVertex vertex;

	public Object getBo() {
		return bo;
	}

	public void setBo(Object bo) {
		this.bo = bo;
	}

	public IVertex getVertex() {
		return vertex;
	}

	public void setVertex(IVertex vertex) {
		this.vertex = vertex;
	}
	
	

}

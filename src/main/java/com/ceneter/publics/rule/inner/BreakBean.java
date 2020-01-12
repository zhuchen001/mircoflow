/**
 * 
 */
package com.ceneter.publics.rule.inner;

import java.io.Serializable;

/**
 * 中断
 * 
 * @author zhuzhu
 *
 */
public class BreakBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean breakLogic = false;
	
	private String message;

	public BreakBean() {
	}

	public BreakBean(boolean breakLogic, String message) {
		this.breakLogic = breakLogic;
		this.message = message;
	}
	
	public void setBreak(String message)
	{
		this.breakLogic = true;
		this.message = message;
	}
	
	public boolean isBreakLogic() {
		return breakLogic;
	}

	public void setBreakLogic(boolean breakLogic) {
		this.breakLogic = breakLogic;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

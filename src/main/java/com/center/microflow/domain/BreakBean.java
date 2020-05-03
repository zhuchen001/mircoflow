/**
 *
 */
package com.center.microflow.domain;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class BreakBean implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 是否中断
     */
    private boolean breakLogic = false;

    /**
     * 中断信息
     */
    private String message;

	/**
	 * 流程名称
	 */
	private String flowName;

    public BreakBean() {
    }

    public BreakBean(boolean breakLogic, String message) {
        this.breakLogic = breakLogic;
        this.message = message;
    }

    public BreakBean(String message) {
        this.breakLogic = true;
        this.message = message;
    }

    public boolean isBreak() {
        return isBreakLogic();
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

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BreakBean{");
        sb.append("breakLogic=").append(breakLogic);
        sb.append(", message='").append(message).append('\'');
        sb.append(", flowName='").append(flowName).append('\'');
        sb.append('}');
        return sb.toString();
    }

}

/**
 *
 */
package com.center.microflow.domain;

/**
 * 微流程引擎内部抛出的运行异常
 *
 * @author Administrator
 *
 */
public class MicroFlowRuntimeException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private String code;


    public MicroFlowRuntimeException(String code, String message) {
        super(message);
        this.code = code;
    }

    public MicroFlowRuntimeException() {
    }


    public MicroFlowRuntimeException(String message) {
        super(message);
    }

    public MicroFlowRuntimeException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}

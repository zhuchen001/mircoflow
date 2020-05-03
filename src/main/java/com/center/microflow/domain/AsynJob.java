/**
 *
 */
package com.center.microflow.domain;

import java.io.Serializable;

/**
 * 异步job
 *
 * @author Administrator
 *
 */
public class AsynJob implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private String type;
    private String boClassName;
    private Object bo;
    private RetryPolicy retryPolicy;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBoClassName() {
        return boClassName;
    }

    public void setBoClassName(String boClassName) {
        this.boClassName = boClassName;
    }

    public Object getBo() {
        return bo;
    }

    public void setBo(Object bo) {
        this.bo = bo;
    }

    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AsynJob [type=");
        builder.append(type);
        builder.append(", boClassName=");
        builder.append(boClassName);
        builder.append(", bo=");
        builder.append(bo);
        builder.append(", retryPolicy=");
        builder.append(retryPolicy);
        builder.append("]");
        return builder.toString();
    }

}

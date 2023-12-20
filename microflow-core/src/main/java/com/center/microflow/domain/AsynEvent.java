/**
 *
 */
package com.center.microflow.domain;

import com.center.microflow.api.IVertex;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class AsynEvent<T extends Serializable> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private String name;
    private IVertex<T> vertex;
    private T bo;
    private RetryPolicy retryPolicy = RetryPolicy.NONE;

    public AsynEvent(String name, IVertex<T> vertex, T bo) {
        this.name = name;
        this.vertex = vertex;
        this.bo = bo;
        confRetryPolicy();
    }

    public boolean increaseFail() {
        this.retryPolicy.increaseRetryTimes();

        return this.retryPolicy.getMaxRetryTimes() >= this.retryPolicy.getRetryTimes();
    }

    public String getType() {
        return this.vertex.id();
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public IVertex<T> getVertex() {
        return vertex;
    }


    public void setVertex(IVertex<T> vertex) {
        this.vertex = vertex;
    }


    public T getBo() {
        return bo;
    }


    public void setBo(T bo) {
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
        builder.append("AsynEvent [name=");
        builder.append(name);
        builder.append(", vertex=");
        builder.append(vertex);
        builder.append(", bo=");
        builder.append(bo);
        builder.append(", retryPolicy=");
        builder.append(retryPolicy);
        builder.append("]");
        return builder.toString();
    }


    private void confRetryPolicy() {
        RetryPolicy po = getVertex().retryPolicy();

        RetryPolicy eventPoliocy = new RetryPolicy(po);

        setRetryPolicy(eventPoliocy);

    }


}

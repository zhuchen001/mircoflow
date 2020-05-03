/**
 *
 */
package com.center.microflow.domain;

import java.io.Serializable;

/**
 * 微流程执行轨迹
 *
 * @author Administrator
 *
 */
public class MicroFlowTrack implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 耗时
     */
    private long cost = 0;

    private String vertex;

    /**
     * 执行结束的输出
     */
    private String context;

    public MicroFlowTrack(String vertex) {
        this.vertex = vertex;
    }

    public MicroFlowTrack(long times, String vertex) {
        this.cost = System.currentTimeMillis() - times;
        this.vertex = vertex;
    }

    /**
     * 这里只有debug才能出现context
     * @param times
     * @param vertex
     * @param context
     */
    public MicroFlowTrack(long times, String vertex, String context) {
        this.cost = System.currentTimeMillis() - times;
        this.vertex = vertex;
        this.context = context;
    }


    public MicroFlowTrack(String vertex, String context) {
        this.vertex = vertex;
        this.context = context;
    }


    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public String getVertex() {
        return vertex;
    }

    public void setVertex(String vertex) {
        this.vertex = vertex;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MicroFlowTrack [cost=");
        builder.append(cost);
        builder.append(", vertex=");
        builder.append(vertex);
        builder.append(", context=");
        builder.append(context);
        builder.append("]");
        return builder.toString();
    }

}

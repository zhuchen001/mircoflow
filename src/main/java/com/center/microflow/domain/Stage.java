/**
 *
 */
package com.center.microflow.domain;

import com.center.microflow.api.*;
import com.center.microflow.utils.AssertUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 执行阶段
 *
 * @author Administrator
 *
 */
public class Stage {
    private String name;

    private int order;

    private VertexEnum type;

    /**
     * 不同类型的Stage，vertex值类型也是不同的
     */
    private Object vertex;

    /**
     * 事务管理
     */
    private ITransactionManager transactionManager;

    public Stage(String name, int order, VertexEnum type, Object vertex) {
        this(name, order, type, vertex, null);
    }

    public Stage(String name, int order, VertexEnum type, Object vertex, ITransactionManager transactionManager) {
        this.name = name;
        this.order = order;
        this.type = type;
        this.vertex = vertex;

        // 需要进行排序,这样就会升序执行
        if (type == VertexEnum.VERTEX_LIST) {
            getVertexList().sort((r1, r2) -> r1.order().compareTo(r2.order()));
        }
        this.transactionManager = transactionManager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public VertexEnum getType() {
        return type;
    }

    public void setType(VertexEnum type) {
        this.type = type;
    }

    public Object getVertex() {
        return vertex;
    }

    public void setVertex(Object vertex) {
        this.vertex = vertex;
    }

    public GroupEnum getVertexGroupEnum() {
        AssertUtils.assertEquals(VertexEnum.VERTEX_GROUP, this.type);
        return (GroupEnum) this.vertex;
    }

    @SuppressWarnings("unchecked")
    public <T extends Serializable> List<IVertex<T>> getVertexList() {
        AssertUtils.assertEquals(VertexEnum.VERTEX_LIST, this.type);
        return (List<IVertex<T>>) this.vertex;
    }

    @SuppressWarnings("unchecked")
    public <T extends Serializable> MicroFlow<T> getVertexMicroFlow() {
        AssertUtils.assertEquals(VertexEnum.SUB_FLOW, this.type);
        return (MicroFlow<T>) this.vertex;
    }

    @SuppressWarnings("unchecked")
    public <T extends Serializable> BranchFlow<T> getVertexBranch() {
        AssertUtils.assertEquals(VertexEnum.BRANCH, this.type);
        return (BranchFlow<T>) this.vertex;
    }

    public ITransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(ITransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Stage [name=");
        builder.append(name);
        builder.append(", order=");
        builder.append(order);
        builder.append(", type=");
        builder.append(type);
        builder.append(", vertex=");
        builder.append(vertex);
        builder.append("]");
        return builder.toString();
    }

}

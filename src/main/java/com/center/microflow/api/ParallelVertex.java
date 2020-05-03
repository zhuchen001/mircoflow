/**
 *
 */
package com.center.microflow.api;

import com.center.microflow.domain.VertexType;

import java.io.Serializable;

/**
 * 并行算子(并行算子要求在同一个stage中且order必须一致)
 *
 * @author Administrator
 *
 */
public interface ParallelVertex<T extends Serializable> extends IVertex<T> {
    long DEFAULT_TIMEOUT = 300L * 1000;

    /**
     * 把futureBo合并到context中
     */
    void merge(T futureBo, T context);

    default long timeout() {
        return DEFAULT_TIMEOUT;
    }

    default void setBreak(String message) {
        throw new UnsupportedOperationException("Can not support setBreak");
    }

    default VertexType type() {
        return VertexType.PARALLEL;
    }

}

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
    /**
     * Parallel默认超时时间
     */
    long DEFAULT_TIMEOUT = 300L * 1000;

    /**
     * 把futureBo合并到context中
     * @param futureBo 并行算子中的拷贝上下文
     * @param context 主流程原始的上下文
     */
    void merge(T futureBo, T context);

    /**
     * 设置超时时间（MILLISECONDS）
     * @return 默认DEFAULT_TIMEOUT
     */
    default long timeout() {
        return DEFAULT_TIMEOUT;
    }

    /**
     * ParallelVertex 中不支持setBreak
     * @param message
     */
    @Override
    default void setBreak(String message) {
        throw new UnsupportedOperationException("ParallelVertex can not support setBreak");
    }

    /**
     * 默认返回PARALLEL
     * @return PARALLEL
     */
    @Override
    default VertexType type() {
        return VertexType.PARALLEL;
    }

}

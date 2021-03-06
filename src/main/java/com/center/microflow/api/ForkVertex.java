/**
 *
 */
package com.center.microflow.api;

import com.center.microflow.domain.VertexType;

import java.io.Serializable;

/**
 * Fork算子(fork算子在执行成功的或者break的时候兜底join到主线程中)
 *
 * @author Administrator
 *
 */
public interface ForkVertex<T extends Serializable> extends ParallelVertex<T> {
    /**
     * 返回默认FORK类型
     *
     * @return 默认FORK类型
     */
    @Override
    default VertexType type() {
        return VertexType.FORK;
    }
}

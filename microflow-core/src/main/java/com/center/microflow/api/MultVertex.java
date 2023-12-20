package com.center.microflow.api;

import java.io.Serializable;

/**
 * 复用的算子(即一个算子被多个微流程使用)
 *
 * @param <T> 流程BO
 * @param <R> 抽象后可复用的BO
 */
public interface MultVertex<T extends Serializable, R extends Serializable> extends IVertex<T> {

    @Override
    default boolean when(T t) {
        return getVertex().when(transferBo(t));
    }

    @Override
    default void then(T t) throws Exception {
        R r = transferBo(t);
        getVertex().then(r);
        writeBackBo(t, r);
    }

    /**
     * 返回实际算子
     */
    IVertex<R> getVertex();

    /**
     * 业务算子BO转换为复用算子BO
     */
    R transferBo(T bo);

    /**
     * 复用算子BO回写到业务算子BO
     */
    void writeBackBo(T bo, R r);
}

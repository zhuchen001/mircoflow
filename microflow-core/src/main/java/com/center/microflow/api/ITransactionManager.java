package com.center.microflow.api;

import com.center.microflow.domain.MicroFlowRuntimeException;

/**
 * 通用事务定义
 */
public interface ITransactionManager<T> {
    T begin() throws MicroFlowRuntimeException;

    void commit(T t) throws MicroFlowRuntimeException;

    void rollback(T t) throws MicroFlowRuntimeException;
}

/**
 *
 */
package com.center.microflow.api;

import com.center.microflow.domain.ExecuteResult;

import java.io.Serializable;
import java.util.List;

/**
 * 微流程
 *
 * @author Administrator
 *
 */
public interface MicroFlow<T extends Serializable> {

    /**
     * 增加Stage（分组算子）
     */
    MicroFlow<T> addStage(String stage, GroupEnum group);

    /**
     * 增加Stage（分组算子，支持事务）
     */
    MicroFlow<T> addStage(String stage, GroupEnum group, ITransactionManager transactionManager);

    /**
     * 增加Stage（算子列表）
     */
    MicroFlow<T> addStage(String stage, List<IVertex<T>> vertexList);

    /**
     * 增加Stage（算子列表，支持事务）
     */
    MicroFlow<T> addStage(String stage, List<IVertex<T>> vertexList, ITransactionManager transactionManager);

    /**
     * 增加Stage（单算子）
     */
    MicroFlow<T> addStage(String stage, IVertex<T> vertex);

    /**
     * 增加Stage（单算子，支持事务）
     */
    MicroFlow<T> addStage(String stage, IVertex<T> vertex, ITransactionManager transactionManager);

    /**
     * 增加Stage（子流程）
     */
    MicroFlow<T> addStage(String stage, MicroFlow<T> subFlow);

    /**
     * 增加Stage（分支处理）
     */
    MicroFlow<T> addStage(String stage, BranchFlow<T> branch);

    /**
     * 增加Stage（分支处理，支持事务）
     */
    MicroFlow<T> addStage(String stage, BranchFlow<T> branch, ITransactionManager transactionManager);

    /**
     * 设置异常处理类
     */
    MicroFlow<T> setExceptionHandler(ExceptionHandler<T> handler);

    /**
     * 流程执行
     */
    ExecuteResult execute(T t);
}

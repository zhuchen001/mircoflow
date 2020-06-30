/**
 *
 */
package com.center.microflow.api;

import com.center.microflow.domain.ExecuteResult;

import java.io.Serializable;
import java.util.List;

/**
 * 分支处理（支持多个分支）
 *
 * @author Administrator
 *
 */
public interface BranchFlow<T extends Serializable> {

    /**
     * 增加分支判断条件
     */
    BranchFlow<T> setDecide(BranchDecide<T> decide);

    /**
     * 增加一个分支条件下的处理
     *
     * @param decide
     * @param group
     * @return
     */
    <V extends Enum & IBranch>BranchFlow<T> addBranch(V decide, GroupEnum group);

    /**
     * 增加分支判断条件(支持事务)
     */
    <V extends Enum & IBranch>BranchFlow<T> addBranch(V decide, GroupEnum group, ITransactionManager transactionManager);

    /**
     * 增加一个分支条件下的处理
     *
     * @param decide
     * @param vertexList
     * @return
     */
    <V extends Enum & IBranch>BranchFlow<T> addBranch(V decide, List<IVertex<T>> vertexList);

    <V extends Enum & IBranch>BranchFlow<T> addBranch(V decide, List<IVertex<T>> vertexList, ITransactionManager transactionManager);

    <V extends Enum & IBranch>BranchFlow<T> addBranch(V decide, IVertex<T> vertex);

    <V extends Enum & IBranch>BranchFlow<T> addBranch(V decide, IVertex<T> vertex, ITransactionManager transactionManager);


    /**
     * 增加一个分支条件下的处理(分支树)
     * @param decide
     * @param branch
     * @return
     */
    <V extends Enum & IBranch>BranchFlow<T> addBranch(V decide, BranchFlow<T> branch);

    <V extends Enum & IBranch>BranchFlow<T> addBranch(V decide, BranchFlow<T> branch, ITransactionManager transactionManager);


    /**
     * 分支执行
     * @param t
     * @return
     * @throws Exception
     */
    ExecuteResult execute(T t) throws Throwable;

}

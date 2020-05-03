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
    BranchFlow<T> addBranch(IBranch decide, GroupEnum group);

    /**
     * 增加分支判断条件(支持事务)
     */
    BranchFlow<T> addBranch(IBranch decide, GroupEnum group, ITransactionManager transactionManager);

    /**
     * 增加一个分支条件下的处理
     *
     * @param decide
     * @param vertexList
     * @return
     */
    BranchFlow<T> addBranch(IBranch decide, List<IVertex<T>> vertexList);

    BranchFlow<T> addBranch(IBranch decide, List<IVertex<T>> vertexList, ITransactionManager transactionManager);

    BranchFlow<T> addBranch(IBranch decide, IVertex<T> vertex);

    BranchFlow<T> addBranch(IBranch decide, IVertex<T> vertex, ITransactionManager transactionManager);


    /**
     * 增加一个分支条件下的处理(分支树)
     * @param decide
     * @param branch
     * @return
     */
    BranchFlow<T> addBranch(IBranch decide, BranchFlow<T> branch);

    BranchFlow<T> addBranch(IBranch decide, BranchFlow<T> branch, ITransactionManager transactionManager);


    /**
     * 分支执行
     * @param t
     * @return
     * @throws Exception
     */
    ExecuteResult execute(T t) throws Throwable;

}

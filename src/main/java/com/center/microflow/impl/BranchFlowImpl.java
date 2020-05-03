/**
 *
 */
package com.center.microflow.impl;

import com.center.microflow.api.*;
import com.center.microflow.domain.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class BranchFlowImpl<T extends Serializable> extends BaseEngineImpl<T> implements BranchFlow<T> {

    private Map<IBranch, BranchElement> branchElementMap = new HashMap<>();

    private BranchDecide<T> decide;

    protected String name;

    public BranchFlowImpl(String name) {
        this.name = name;
    }

    @Override
    public BranchFlowImpl<T> setDecide(BranchDecide<T> decide) {
        this.decide = decide;
        return this;
    }

    @Override
    public BranchFlow<T> addBranch(IBranch decide, GroupEnum group) {

        return addBranchElementInner(new BranchElement(getBranchElementName(decide), VertexEnum.VERTEX_GROUP, decide, group));

    }

    @Override
    public BranchFlow<T> addBranch(IBranch decide, GroupEnum group, ITransactionManager transactionManager) {
        return addBranchElementInner(new BranchElement(getBranchElementName(decide), VertexEnum.VERTEX_GROUP, decide, group, transactionManager));
    }

    @Override
    public BranchFlow<T> addBranch(IBranch decide, List<IVertex<T>> vertexList) {

        return addBranchElementInner(new BranchElement(getBranchElementName(decide), VertexEnum.VERTEX_LIST, decide, vertexList));
    }

    @Override
    public BranchFlow<T> addBranch(IBranch decide, List<IVertex<T>> iVertices, ITransactionManager transactionManager) {
        return addBranchElementInner(new BranchElement(getBranchElementName(decide), VertexEnum.VERTEX_LIST, decide, iVertices, transactionManager));
    }

    @Override
    public BranchFlow<T> addBranch(IBranch decide, IVertex<T> vertex) {
        return addBranchElementInner(new BranchElement(getBranchElementName(decide), VertexEnum.VERTEX_LIST, decide, Arrays.asList(vertex)));
    }

    @Override
    public BranchFlow<T> addBranch(IBranch decide, IVertex<T> vertex, ITransactionManager transactionManager) {
        return addBranchElementInner(new BranchElement(getBranchElementName(decide), VertexEnum.VERTEX_LIST, decide, Arrays.asList(vertex), transactionManager));
    }

    @Override
    public BranchFlow<T> addBranch(IBranch decide, BranchFlow<T> branch) {
        return addBranchElementInner(new BranchElement(getBranchElementName(decide), VertexEnum.BRANCH, decide, branch));
    }

    @Override
    public BranchFlow<T> addBranch(IBranch decide, BranchFlow<T> branch, ITransactionManager transactionManager) {
        return addBranchElementInner(new BranchElement(getBranchElementName(decide), VertexEnum.BRANCH, decide, branch, transactionManager));
    }

    private String getBranchElementName(IBranch decide)
    {
        return this.name + '-' + decide;
    }

    @Override
    public ExecuteResult execute(T t) throws Throwable {
        log.info("BranchFlowImpl begin execute...");

        IBranch decideValue = this.decide.decide(t);
        ExecuteResult result = new ExecuteResult();

        BranchElement branchElement = this.branchElementMap.get(decideValue);

        if (branchElement == null) {
            throw new MicroFlowRuntimeException("decide[" + decideValue + "] not found in BranchFlowImpl");
        }

        log.info("begin execute branch:{}", decideValue);

        // 这里如果执行遇到异常，会直接抛到上层框架
        executeStage(t, result, branchElement);

        result.setBreakBean(MicroflowThreadLocal.get());

        log.info("BranchFlowImpl execute result:{}", result);

        return result;
    }

    @Override
    public String getName() {
        return this.name;
    }

    private BranchFlowImpl<T> addBranchElementInner(BranchElement element) {
        this.branchElementMap.put(element.getDecide(), element);
        return this;
    }

}

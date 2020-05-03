/**
 *
 */
package com.center.microflow.impl;

import com.center.microflow.api.*;
import com.center.microflow.domain.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 微流程引擎核心实现
 * @author Administrator
 *
 */
public class MicroFlowEngineImpl<T extends Serializable> extends BaseEngineImpl<T> implements MicroFlow<T> {

    private List<Stage> stageList = new ArrayList<>();

    private int order;

    protected String name;

    protected ExceptionHandler<T> exceptionHandler;

    public MicroFlowEngineImpl(String name) {
        this.name = name;
    }

    public MicroFlowEngineImpl(String name, boolean debug) {
        this.name = name;
        this.debug = debug;
    }

    @Override
    public MicroFlow<T> addStage(String stageName, GroupEnum group) {
        return addStageInner(new Stage(stageName, this.order++, VertexEnum.VERTEX_GROUP, group));
    }

    @Override
    public MicroFlow<T> addStage(String stageName, GroupEnum group, ITransactionManager transactionManager) {
        return addStageInner(new Stage(stageName, this.order++, VertexEnum.VERTEX_GROUP, group, transactionManager));
    }

    @Override
    public MicroFlow<T> addStage(String stageName, List<IVertex<T>> vertexList) {
        return addStageInner(new Stage(stageName, this.order++, VertexEnum.VERTEX_LIST, vertexList));
    }

    @Override
    public MicroFlow<T> addStage(String stageName, List<IVertex<T>> iVertices, ITransactionManager transactionManager) {
        return addStageInner(new Stage(stageName, this.order++, VertexEnum.VERTEX_LIST, iVertices, transactionManager));
    }

    @Override
    public MicroFlow<T> addStage(String stageName, IVertex<T> vertex) {
        return addStageInner(new Stage(stageName, this.order++, VertexEnum.VERTEX_LIST, Arrays.asList(vertex)));
    }

    @Override
    public MicroFlow<T> addStage(String stageName, IVertex<T> vertex, ITransactionManager transactionManager) {
        return addStageInner(new Stage(stageName, this.order++, VertexEnum.VERTEX_LIST, Arrays.asList(vertex), transactionManager));
    }

    @Override
    public MicroFlow<T> addStage(String stageName, MicroFlow<T> subFlow) {
        return addStageInner(new Stage(stageName, this.order++, VertexEnum.SUB_FLOW, subFlow));
    }

    @Override
    public MicroFlow<T> addStage(String stageName, BranchFlow<T> branch) {
        return addStageInner(new Stage(stageName, this.order++, VertexEnum.BRANCH, branch));
    }

    @Override
    public MicroFlow<T> addStage(String stageName, BranchFlow<T> branch, ITransactionManager transactionManager) {
        return addStageInner(new Stage(stageName, this.order++, VertexEnum.BRANCH, branch, transactionManager));
    }

    @Override
    public MicroFlow<T> setExceptionHandler(ExceptionHandler<T> handler) {
        this.exceptionHandler = handler;
        return this;
    }

    @Override
    public ExecuteResult execute(T t) {
        log.info("MicroFlowEngineImpl begin execute...");

        ExecuteResult result = new ExecuteResult();

        BreakBean breakBean = null;

        try {
            // 加入中断上下文(子流程这里返回null)
            breakBean = MicroflowThreadLocal.init();

            // 如果是子流程执行，那么不需要再次init
            if (breakBean != null) {

                // 设置流程名称
                MicroflowThreadLocal.setFlowName(getName());

                if (this.debug) {
                    result.initVertexTrack(t.toString());
                } else {
                    result.initVertexTrack(null);
                }
            }

            for (Stage stage : this.stageList) {
                log.info("begin execute stage:{}", stage.getName());
                executeStage(t, result, stage);
            }

            // 这里需要兜底执行并行算子（防止最后一个stage的最后算子是并行的）
            result.getParallelExecute().merge(t);
            // 这里兜底执行fork算子的merger
            result.getForkExecute().merge(t);

        } catch (Throwable e) {
            log.warn(e.toString(), e);

            // 执行异常的handler
            if (this.exceptionHandler != null) {
                try {
                    this.exceptionHandler.handler(e, t);
                } catch (Throwable e1) {
                    log.error(e1.toString(), e1);
                }
            }
            //捕获所有异常，直接退出算子执行
            result.setException(e);
        } finally {
            result.setBreakBean(MicroflowThreadLocal.get());

            // 删除中断上下文(如果存在子流程，这里不能remove,核心由remove方法实现)
            MicroflowThreadLocal.remove(breakBean);
        }

        if (this.debug) {
            log.info("MicroFlowEngineImpl execute result:{}", result.toString2());
        } else {
            log.info("MicroFlowEngineImpl execute result:{}", result.toString());
        }

        return result;
    }

    @Override
    public String getName() {
        return this.name;
    }

    private MicroFlowEngineImpl<T> addStageInner(Stage stage) {
        this.stageList.add(stage);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

}

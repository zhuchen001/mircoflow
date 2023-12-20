/**
 *
 */
package com.center.microflow.impl;

import com.center.microflow.api.*;
import com.center.microflow.boot.MicroFlowEntrustBootstrap;
import com.center.microflow.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Administrator
 *
 */
public abstract class BaseEngineImpl<T extends Serializable> {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected boolean debug;

    /**
     * stage执行
     *
     * @param t
     * @param result
     * @param stage
     * @throws Throwable
     */
    protected void executeStage(T t, ExecuteResult result, Stage stage) throws Throwable {
        ITransactionManager transactionManager = stage.getTransactionManager();

        if (transactionManager == null) {
            executeStageInner(t, result, stage);
        } else {
            Object status = transactionManager.begin();

            try {
                executeStageInner(t, result, stage);
                //事务提交
                transactionManager.commit(status);
            } catch (Exception e) {
                // 事务回滚
                transactionManager.rollback(status);
                throw e;
            }
        }

    }

    private void executeStageInner(T t, ExecuteResult result, Stage stage) throws Throwable {
        switch (stage.getType()) {
            case VERTEX_GROUP:
                // 执行时候动态获取算子列表
                List<IVertex<T>> gvertexList = getMicroFlowVertexListByGroup(stage.getVertexGroupEnum());

                for (IVertex<T> vertex : gvertexList) {

                    executeVertex(vertex, t, result, stage);
                }

                break;

            case VERTEX_LIST:
                // 获取算子列表
                List<IVertex<T>> vertexList = stage.getVertexList();

                for (IVertex<T> vertex : vertexList) {

                    executeVertex(vertex, t, result, stage);
                }

                break;


            case SUB_FLOW:
                MicroFlow<T> subFlow = stage.getVertexMicroFlow();

                ExecuteResult execute = subFlow.execute(t);

                result.addVertexTrack(execute);

                Throwable exception = execute.getException();

                // 子流程的异常继续向上抛出，根流程统一处理
                if (exception != null) {
                    throw exception;
                }

                break;

            case BRANCH:
                BranchFlow<T> branch = stage.getVertexBranch();

                ExecuteResult bra = branch.execute(t);

                result.addVertexTrack(bra);

                break;

            default:
                break;
        }
    }

    protected void executeVertex(IVertex<T> vertex, T t, ExecuteResult result, Stage stage) throws Throwable {

        if (when(vertex, t)) {
            log.info("execute vertex:{}", vertex.id());

            long begin = System.currentTimeMillis();

            executeVertexInner(vertex, t, result, stage);

            // debug模式下支持输出大量的跟踪日志
            if (this.debug) {
                result.addVertexTrack(vertex, begin, t.toString());
            } else {
                result.addVertexTrack(vertex, begin);
            }
        }
    }

    /**
     * 算子是否执行
     *
     * @param vertex
     * @param t
     * @return
     */
    protected boolean when(IVertex<T> vertex, T t) {

        boolean when = vertex.when(t);

        // FINALLY类型算子必须执行（如果其他算子出现异常则不一定执行）
        if (when && vertex.mode() == VertexMode.FINALLY) {
            return true;
        }

        return isContinue() && when;
    }

    protected boolean isContinue() {
        return !MicroflowThreadLocal.get().isBreak();
    }

    protected void executeVertexInner(IVertex<T> vertex, T bo, ExecuteResult result, Stage stage) throws Throwable {
        // 获取执行点标识
        String parallelPoint = getParallelPoint(stage, vertex);

        ParallelExecute parallelExecute = result.getParallelExecute();

        // 如果执行点变化了，则merge并行执行结果(fork不在内)
        if (!parallelExecute.isSameParallel(parallelPoint) && parallelExecute.canMerge()) {
            parallelExecute.merge(bo);

            // 如果merge异常，可能是执行超时或者执行异常
            Exception mergeException = parallelExecute.getMergeException();

            if (mergeException != null) {
                throw mergeException;
            }

            // 重置并行执行结果
            parallelExecute.clear();
        }

        // 通过卫语句实现多执行策略
        if (vertex.type() == VertexType.SYN) {
            vertex.then(bo);
        }

        if (vertex.type() == VertexType.ASYN_MEM) {
            MicroFlowEntrustBootstrap.getMicroFlowAsynMemeryProcess()
                    .addAsynEvent(new AsynEvent<T>(getName(), vertex, deepCopy(bo)));
        }

        if (vertex.type() == VertexType.ASYN_STORAGE) {
            MicroFlowEntrustBootstrap.getMicroFlowAsynStorageProcess()
                    .addAsynEvent(new AsynEvent<T>(getName(), vertex, deepCopy(bo)));
        }

        if (vertex.type() == VertexType.PARALLEL) {
            AsynEvent<T> event = new AsynEvent<>(getName(), vertex, deepCopy(bo));
            Future<T> parallel = MicroFlowEntrustBootstrap.getMicroFlowParallelProcess()
                    .parallel(event);

            // 设置执行点标识
            parallelExecute.setPoint(parallelPoint);
            parallelExecute.addParallelInfo(event, (ParallelVertex) vertex, parallel);
        }

        if (vertex.type() == VertexType.FORK) {
            AsynEvent<T> event = new AsynEvent<>(getName(), vertex, deepCopy(bo));
            Future<T> parallel = MicroFlowEntrustBootstrap.getMicroFlowParallelProcess()
                    .parallel(event);

            result.getForkExecute().addParallelInfo(event, (ParallelVertex) vertex, parallel);
        }

    }

    private String getParallelPoint(Stage stage, IVertex vertex) {
        return stage.getName() + '-' + vertex.order().toString();
    }

    private T deepCopy(T bo) {
        // 如果类实现了Clone接口使用类本身的Clone方法
        if (bo instanceof Clone) {
            return (T) ((Clone) bo).clone();
        }

        return MicroFlowEntrustBootstrap.getDeepCopy().copy(bo);
    }

    protected List<IVertex<T>> getMicroFlowVertexListByGroup(GroupEnum group) {
        return MicroFlowEntrustBootstrap.getMicroFlowFindVertex().getMicroFlowFlowVertexListByGroup(group);
    }

    public abstract String getName();

}

/**
 *
 */
package com.center.microflow.boot;

import com.center.microflow.api.*;
import com.center.microflow.impl.DeepCopyDefaultImpl;
import com.center.microflow.utils.AssertUtils;
import com.center.microflow.utils.MicroFlowExceptionUtils;
import com.center.microflow.utils.ServiceLoaderUtils;

import java.util.List;

/**
 * 微流程注入外部核心实现类，用于初始化委托实现类(建议在程序启动中实现注入，避免多线程不安全设置)
 *
 * @author Administrator
 *
 */
public abstract class MicroFlowEntrustBootstrap {

    /**
     * 算子的查询
     */
    private static volatile MicroFlowFindVertex microFlowFindVertex;

    /**
     * 内存异步算子的处理
     */
    private static volatile MicroFlowAsynMemeryProcess microFlowAsynMemeryProcess;

    /**
     * 并行算子执行引擎
     */
    private static volatile MicroFlowParallelProcess microFlowParallelProcess;

    /**
     * 持久化异步算子的执行（需要自主实现，无默认实现）
     */
    private static volatile MicroFlowAsynStorageProcess microFlowAsynStorageProcess;

    private static volatile DeepCopy deepCopy = new DeepCopyDefaultImpl();

    static {
        // SPI加载DeepCopy
        List<DeepCopy> deepCopyList = ServiceLoaderUtils.getServiceList(DeepCopy.class);

        // 降序排列
        deepCopyList.sort((x, y) -> y.order() - x.order());

        if (!deepCopyList.isEmpty()) {
            setDeepCopy(deepCopyList.get(0));
        }
    }

    public static List<MicroFlowExceptionChange> getMicroFlowExceptionChangeList() {
        return MicroFlowExceptionUtils.getMicroFlowExceptionChangeList();
    }

    public static void addMicroFlowExceptionChange(List<MicroFlowExceptionChange> serviceList) {
        MicroFlowExceptionUtils.addMicroflowexceptionChange(serviceList);
    }

    public static void addMicroFlowExceptionChange(MicroFlowExceptionChange process) {
        MicroFlowExceptionUtils.addMicroflowexceptionChange(process);
    }

    public static DeepCopy getDeepCopy() {
        return deepCopy;
    }

    public static MicroFlowFindVertex getMicroFlowFindVertex() {
        AssertUtils.assertNotNull(microFlowFindVertex, "microFlowFindVertex is null, must init microFlowFindVertex");
        return microFlowFindVertex;
    }

    public static void setMicroFlowFindVertex(MicroFlowFindVertex microFlowFindVertex) {
        MicroFlowEntrustBootstrap.microFlowFindVertex = microFlowFindVertex;
    }

    public static MicroFlowAsynMemeryProcess getMicroFlowAsynMemeryProcess() {
        AssertUtils.assertNotNull(microFlowAsynMemeryProcess, "microFlowAsynMemeryProcess is null, must init microFlowAsynMemeryProcess");
        return microFlowAsynMemeryProcess;
    }

    public static void setMicroFlowAsynMemeryProcess(MicroFlowAsynMemeryProcess microFlowAsynMemeryProcess) {
        MicroFlowEntrustBootstrap.microFlowAsynMemeryProcess = microFlowAsynMemeryProcess;
    }

    public static MicroFlowAsynStorageProcess getMicroFlowAsynStorageProcess() {
        AssertUtils.assertNotNull(microFlowAsynStorageProcess, "microFlowAsynStorageProcess is null, must init microFlowAsynStorageProcess");
        return microFlowAsynStorageProcess;
    }

    public static void setMicroFlowAsynStorageProcess(MicroFlowAsynStorageProcess microFlowAsynStorageProcess) {
        MicroFlowEntrustBootstrap.microFlowAsynStorageProcess = microFlowAsynStorageProcess;
    }

    public static MicroFlowParallelProcess getMicroFlowParallelProcess() {
        AssertUtils.assertNotNull(microFlowParallelProcess, "microFlowParallelProcess is null, must init microFlowParallelProcess");
        return microFlowParallelProcess;
    }

    public static void setMicroFlowParallelProcess(MicroFlowParallelProcess microFlowParallelProcess) {
        MicroFlowEntrustBootstrap.microFlowParallelProcess = microFlowParallelProcess;
    }

    public static void setDeepCopy(DeepCopy deepCopy) {
        MicroFlowEntrustBootstrap.deepCopy = deepCopy;
    }

}

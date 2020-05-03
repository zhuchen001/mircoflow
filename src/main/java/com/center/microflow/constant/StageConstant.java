package com.center.microflow.constant;

/**
 * 常见的阶段名称定义(4P)
 */
public interface StageConstant {
    // 预处理
    String PREPARE = "Prepare-Stage";
    // 逻辑处理
    String PROCESS = "Process-Stage";
    // 持久化
    String PERSISTENT = "Persistent-Stage";
    // 后置处理
    String POSTPROCESS = "Post-Process-Stage";
}

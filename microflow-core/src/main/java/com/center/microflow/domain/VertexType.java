/**
 *
 */
package com.center.microflow.domain;

/**
 * 算子执行类型
 *
 * @author Administrator
 *
 */
public enum VertexType {
    /**同步算子（默认）*/
    SYN,

    /**异步算子（内存）*/
    ASYN_MEM,
    /**
     * 异步算子（持久化）
     */
    ASYN_STORAGE,
    /**
     * 并行算子（同一个stage中order相同的并行算子会并行执行）
     */
    PARALLEL,
    /**
     * fork算子（执行中fork一个线程执行，等流程执行结束的时候join到流程中）
     */
    FORK
}

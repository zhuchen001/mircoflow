/**
 *
 */
package com.center.microflow.api;

import com.center.microflow.domain.MicroflowThreadLocal;
import com.center.microflow.domain.RetryPolicy;
import com.center.microflow.domain.VertexMode;
import com.center.microflow.domain.VertexType;

import java.io.Serializable;

/**
 *
 * @author Administrator
 *
 */
public interface BaseDefined<T extends Serializable> {

    /**
     * 算子描述
     */
    default String desc() {
        return "";
    }

    /**
     * 分组
     *
     */
    default GroupEnum group() {
        return GroupEnum.Default.DEFAULT;
    }

    /**
     * 设置执行终止
     *
     */
    default void setBreak(String message) {
        MicroflowThreadLocal.set(message);
    }

    /**
     * 重试策略
     *
     */
    default RetryPolicy retryPolicy() {
        return RetryPolicy.NONE;
    }

    /**
     * 算子执行类型
     *
     */
    default VertexType type() {
        return VertexType.SYN;
    }

    /**
     * 算子执行模式
     *
     */
    default VertexMode mode() {
        return VertexMode.DEFAULT;
    }

    /**
     * 算子唯一标识（默认包名+类名）
     */
    default String id() {
        return getClass().getName();
    }

}

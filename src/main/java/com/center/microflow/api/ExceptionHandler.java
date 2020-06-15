/**
 *
 */
package com.center.microflow.api;

import java.io.Serializable;

/**
 * 出现异常的时候允许自定义handle处理
 *
 * @author Administrator
 *
 */
@FunctionalInterface
public interface ExceptionHandler<T extends Serializable> {

    /**
     * 失败处理
     * @param exception 业务抛出的异常，也有可能是执行中底层抛出的异常
     * @param bo 业务上下文
     */
    void handler(Throwable exception, T bo);
}

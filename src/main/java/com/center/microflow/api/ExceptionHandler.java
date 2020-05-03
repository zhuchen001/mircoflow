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

    void handler(Throwable exception, T bo);
}

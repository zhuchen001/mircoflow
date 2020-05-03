/**
 *
 */
package com.center.microflow.api;

import java.io.Serializable;

/**
 * 算子执行
 *
 * @author Administrator
 *
 */
public interface IExecute<T extends Serializable> {

    /**
     * 算子执行（如果失败通过异常抛出）
     * @param t 上下文
     */
    void then(T t) throws Exception;

}

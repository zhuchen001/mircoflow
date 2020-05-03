/**
 *
 */
package com.center.microflow.api;

import com.center.microflow.domain.AsynEvent;

import java.io.Serializable;
import java.util.concurrent.Future;

/**
 * 并行算子处理
 *
 * @author Administrator
 *
 */
public interface MicroFlowParallelProcess {
    <T extends Serializable> Future<T> parallel(AsynEvent<T> event);

    /**
     * 加载顺序(降序，即越大越优先执行)
     */
    default int order() {
        return 0;
    }
}

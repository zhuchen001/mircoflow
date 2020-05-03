/**
 *
 */
package com.center.microflow.api;

import com.center.microflow.domain.AsynEvent;

import java.io.Serializable;

/**
 * 异步算子处理
 *
 * @author Administrator
 *
 */
public interface MicroFlowAsynProcess {
    <T extends Serializable> void addAsynEvent(AsynEvent<T> event);

    /**
     * 加载顺序(降序，即越大越优先执行)
     */
    default int order() {
        return 0;
    }

}

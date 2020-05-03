/**
 *
 */
package com.center.microflow.api;

import java.io.Serializable;

/**
 * 复用并行算子
 *
 * @author Administrator
 *
 */
public interface MultParalleVertex<T extends Serializable, R extends Serializable> extends MultVertex<T, R>, ParallelVertex<T> {

}

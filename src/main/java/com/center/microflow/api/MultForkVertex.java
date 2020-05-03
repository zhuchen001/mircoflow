/**
 *
 */
package com.center.microflow.api;

import java.io.Serializable;

/**
 * 复用Fork算子
 *
 * @author Administrator
 *
 */
public interface MultForkVertex<T extends Serializable, R extends Serializable> extends MultVertex<T, R>, ForkVertex<T> {

}

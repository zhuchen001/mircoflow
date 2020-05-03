/**
 *
 */
package com.center.microflow.api;

import java.io.Serializable;

/**
 * 分支判断
 *
 * @author Administrator
 *
 */
public interface BranchDecide<T extends Serializable> {

    /**
     * 分支判断
     *
     * @param t
     *            上下文
     * @return 判断结果
     */
    IBranch decide(T t);

}

/**
 *
 */
package com.center.microflow.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程上下文
 *
 * @author Administrator
 *
 */
public class MicroflowThreadLocal {

    private static final Logger log = LoggerFactory.getLogger(MicroflowThreadLocal.class);

    private static final ThreadLocal<BreakBean> threadLocal = new ThreadLocal<>();

    public static void set(String message) {
        BreakBean value = get();
        value.setBreakLogic(true);
        value.setMessage(message);
        threadLocal.set(value);

        log.warn("MicroflowThreadLocal set break info:{}", value);
    }

    public static BreakBean init() {
        // 一个微流程当前设计只允许存在一个中断上下文，包括子流程
        if (get() == null) {
            BreakBean value = new BreakBean();
            threadLocal.set(value);
            return value;
        } else {
            // 如果已经初始化返回null
            return null;
        }
    }

    public static void setFlowName(String flowName) {
        BreakBean breakBean = get();
        if (breakBean != null) {
            breakBean.setFlowName(flowName);
        }
    }

    public static String getFlowName() {
        BreakBean breakBean = get();
        if (breakBean != null) {
            return breakBean.getFlowName();
        }

        return null;
    }

    public static BreakBean get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }

    public static void remove(BreakBean value) {
        // 防止子流程remove
        if (get() == value) {
            threadLocal.remove();
        }
    }

}

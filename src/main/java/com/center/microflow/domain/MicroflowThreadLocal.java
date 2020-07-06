/**
 *
 */
package com.center.microflow.domain;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程上下文（允许嵌套多个流程，每个流程一个独立的break，子流程的break不影响主流程的）
 *
 * @author Administrator
 *
 */
public class MicroflowThreadLocal {

    private static final Logger log = LoggerFactory.getLogger(MicroflowThreadLocal.class);

    private static final ThreadLocal<Stack<BreakBean>> threadLocal = new ThreadLocal<>();

    public static void set(String message) {
        BreakBean value = get();
        value.setBreakLogic(true);
        value.setMessage(message);

        log.info("MicroflowThreadLocal set break info:{}", value);
    }

    private static void setInner(BreakBean value) {
        Stack<BreakBean> breakStack = threadLocal.get();

        if (breakStack == null) {
            breakStack = new Stack<>();
            threadLocal.set(breakStack);
        }

        breakStack.push(value);
    }

    public static BreakBean init() {

        BreakBean value = new BreakBean();
        setInner(value);
        return value;

    }

    public static BreakBean init(String flowName) {
        BreakBean init = init();
        setFlowName(flowName);
        return init;
    }

    public static void setFlowName(String flowName) {
        get().setFlowName(flowName);
    }

    public static String getFlowName() {
        return get().getFlowName();
    }

    public static BreakBean get() {
        return threadLocal.get().peek();
    }

    public static boolean isEmpty() {
        return threadLocal.get() == null;
    }

    public static BreakBean remove() {
        Stack<BreakBean> breakBeans = threadLocal.get();
        BreakBean pop = breakBeans.pop();
        if (breakBeans.isEmpty()) {
            threadLocal.remove();
        }
        return pop;
    }

}

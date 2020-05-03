/**
 *
 */
package com.center.microflow.utils;

import com.center.microflow.domain.MicroFlowRuntimeException;

/**
 * 断言
 *
 * @author Administrator
 *
 */
public abstract class AssertUtils {

    /**
     * 断言
     *
     * @param expected
     * @param actual
     */
    public static <T> void assertEquals(T expected, T actual) {
        if (expected == null && actual == null)
            return;
        if (expected != null && expected.equals(actual))
            return;

        throw new MicroFlowRuntimeException("Assert Exception, expected:" + expected + ",and actual:" + actual);
    }

    public static void assertNotNull(Object actual, String message) {
        if (actual == null) {
            throw new MicroFlowRuntimeException(message);
        }
    }
}

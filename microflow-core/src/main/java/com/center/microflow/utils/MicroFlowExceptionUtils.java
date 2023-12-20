package com.center.microflow.utils;

import com.center.microflow.api.MicroFlowExceptionChange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class MicroFlowExceptionUtils {
    private static final List<MicroFlowExceptionChange> microFlowExceptionChangeList = new ArrayList<>();

    static {
        // 通过ServiceLoader加载MicroFlowExceptionProcess和DeepCopy
        List<MicroFlowExceptionChange> serviceList = ServiceLoaderUtils
                .getServiceList(MicroFlowExceptionChange.class);

        microFlowExceptionChangeList.addAll(serviceList);
    }

    public static List<MicroFlowExceptionChange> getMicroFlowExceptionChangeList() {
        return Collections.unmodifiableList(microFlowExceptionChangeList);
    }

    public static void addMicroflowexceptionChange(List<MicroFlowExceptionChange> serviceList) {
        MicroFlowExceptionUtils.microFlowExceptionChangeList.addAll(serviceList);
    }

    public static void addMicroflowexceptionChange(MicroFlowExceptionChange process) {
        MicroFlowExceptionUtils.microFlowExceptionChangeList.add(process);
    }
}

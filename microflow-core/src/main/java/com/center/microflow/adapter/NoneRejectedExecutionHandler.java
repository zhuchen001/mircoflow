package com.center.microflow.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class NoneRejectedExecutionHandler implements RejectedExecutionHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.error("Task {} rejected from {}", r, executor);
    }
}

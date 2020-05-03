package com.center.microflow.adapter;

import java.util.concurrent.ThreadFactory;

public class DefaultThreadFactory implements ThreadFactory {

    private String pfix;

    public DefaultThreadFactory(String pfix) {
        this.pfix = pfix;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        //设置为守护线程
        thread.setDaemon(true);
        thread.setName(this.pfix);
        return thread;
    }
}

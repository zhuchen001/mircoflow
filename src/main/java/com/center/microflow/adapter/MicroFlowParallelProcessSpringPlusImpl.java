/**
 *
 */
package com.center.microflow.adapter;

import com.center.microflow.api.MicroFlowParallelProcess;
import com.center.microflow.boot.MicroFlowEntrustBootstrap;
import com.center.microflow.domain.AsynEvent;
import com.center.microflow.domain.MicroflowThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 并行算子执行引擎（支持注入多个线程池，不同线程池绑定不同的流程，保证核心流程不收其他流程影响）
 *
 * @author Administrator
 *
 */
public class MicroFlowParallelProcessSpringPlusImpl implements MicroFlowParallelProcess, ApplicationContextAware {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 默认线程池
     */
    private ThreadPoolTaskExecutor defaultThreadPoolTaskExecutor = null;

    /**
     * 允许给指定的微流程自定义线程池
     */
    private Map<String, ThreadPoolTaskExecutor> threadPoolTaskExecutorMap = new HashMap<>();

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.context.ApplicationContextAware#setApplicationContext(org
     * .springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        MicroFlowEntrustBootstrap.setMicroFlowParallelProcess(this);
    }

    @Override
    public <T extends Serializable> Future<T> parallel(AsynEvent<T> event) {
        // 如果并行执行线程满了，这里会抛出异常(由引擎框架捕获，最终执行异常)
        return getThreadPoolTaskExecutor().submit(new ParallelTask(event));
    }

    public ThreadPoolTaskExecutor getDefaultThreadPoolTaskExecutor() {
        return defaultThreadPoolTaskExecutor;
    }

    public void setDefaultThreadPoolTaskExecutor(ThreadPoolTaskExecutor defaultThreadPoolTaskExecutor) {
        this.defaultThreadPoolTaskExecutor = defaultThreadPoolTaskExecutor;
    }

    public Map<String, ThreadPoolTaskExecutor> getThreadPoolTaskExecutorMap() {
        return threadPoolTaskExecutorMap;
    }

    public void setThreadPoolTaskExecutorMap(Map<String, ThreadPoolTaskExecutor> threadPoolTaskExecutorMap) {
        this.threadPoolTaskExecutorMap = threadPoolTaskExecutorMap;
    }


    /**
     * 获取执行线程池
     */
    private ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        String flowName = MicroflowThreadLocal.getFlowName();

        if (flowName == null) {
            return getDefaultThreadPoolTaskExecutor();
        }

        return this.threadPoolTaskExecutorMap.getOrDefault(flowName, getDefaultThreadPoolTaskExecutor());
    }

    class ParallelTask<T extends Serializable> implements Callable<T> {
        private AsynEvent<T> event;

        public ParallelTask(AsynEvent<T> event) {
            this.event = event;
        }

        @Override
        public T call() throws Exception {
            // 算子执行
            event.getVertex().then(event.getBo());

            return event.getBo();
        }
    }
}



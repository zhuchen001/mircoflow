/**
 *
 */
package com.center.microflow.adapter;

import com.center.microflow.api.MicroFlowParallelProcess;
import com.center.microflow.boot.MicroFlowEntrustBootstrap;
import com.center.microflow.domain.AsynEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 并行算子执行引擎(默认实现)
 *
 * @author Administrator
 *
 */
@Component("microFlowParallelProcessSpringImpl")
public class MicroFlowParallelProcessSpringImpl implements MicroFlowParallelProcess, ApplicationContextAware {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 并行算子线程池
     */
    private ThreadPoolTaskExecutor threadPoolTaskExecutor = null;

    public MicroFlowParallelProcessSpringImpl() {
    }

    @PostConstruct
    public void init() {
        this.threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        this.threadPoolTaskExecutor.setCorePoolSize(20);
        this.threadPoolTaskExecutor.setMaxPoolSize(200);
        this.threadPoolTaskExecutor.setQueueCapacity(10240);
        this.threadPoolTaskExecutor.setThreadNamePrefix("MicroFlowParallel-");
        this.threadPoolTaskExecutor.setRejectedExecutionHandler((new ThreadPoolExecutor.AbortPolicy()));

        this.threadPoolTaskExecutor.initialize();

    }

    /**
     * 关闭线程池
     */
    @PreDestroy
    public void destory() {
        threadPoolTaskExecutor.shutdown();
    }

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

    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        return threadPoolTaskExecutor;
    }

    public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Override
    public <T extends Serializable> Future<T> parallel(AsynEvent<T> event) {
        // 如果并行执行线程满了，这里会抛出异常(由引擎框架捕获，最终执行异常)
        return threadPoolTaskExecutor.submit(new ParallelTask(event));
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



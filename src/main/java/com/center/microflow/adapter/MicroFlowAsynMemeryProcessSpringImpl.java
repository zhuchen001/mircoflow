/**
 *
 */
package com.center.microflow.adapter;

import com.center.microflow.api.MicroFlowAsynMemeryProcess;
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
import java.util.concurrent.*;

/**
 * 异步算子执行引擎(如果多次重试失败则丢弃)
 *
 * @author Administrator
 *
 */
@Component("microFlowAsynMemeryProcessSpringImpl")
public class MicroFlowAsynMemeryProcessSpringImpl implements MicroFlowAsynMemeryProcess, ApplicationContextAware {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 重试队列(最大上限10240)
     */
    private BlockingQueue<AsynEvent<?>> retryQueue = new LinkedBlockingQueue<>(10240);

    /**
     * 重试线程池
     */
    private ScheduledExecutorService scheduledThreadPool = new ScheduledThreadPoolExecutor(10, new DefaultThreadFactory("MicroFlowAsynMemery-Scheduled-"), new NoneRejectedExecutionHandler());

    /**
     * 立即执行线程池
     */
    private ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

    /**
     * 重试线程运行标识
     */
    private volatile boolean running = true;

    public MicroFlowAsynMemeryProcessSpringImpl() {

    }

    @PostConstruct
    public void init() {
        this.threadPoolTaskExecutor.setCorePoolSize(10);
        this.threadPoolTaskExecutor.setMaxPoolSize(20);
        this.threadPoolTaskExecutor.setQueueCapacity(10240);
        this.threadPoolTaskExecutor.setThreadNamePrefix("MicroFlowAsynMemery");
        this.threadPoolTaskExecutor.setRejectedExecutionHandler((new NoneRejectedExecutionHandler()));

        this.threadPoolTaskExecutor.initialize();


        // 启动失败重试调度线程
        Thread thread = new Thread(() -> executeScheduled());
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * 关闭线程池
     */
    @PreDestroy
    public void destroy() {
        this.running = false;
        scheduledThreadPool.shutdown();
        threadPoolTaskExecutor.shutdown();
        // 打印丢失的任务
        this.retryQueue.forEach(x -> {
            log.info("jvm has shutdown,retry task lost:{}", x);
        });
    }

    /*
     * (non-Javadoc)
     *
     * @see com.center.microflow.api.api.MicroFlowAsynProcess#addAsynEvent(com.center.
     * microflow.api.AsynEvent)
     */
    @Override
    public <T extends Serializable> void addAsynEvent(AsynEvent<T> event) {
        // 这里如果执行等待较多，新的任务直接丢弃，且不抛出异常
        threadPoolTaskExecutor.execute(() -> {
            executeEvent(event);
        });
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
        MicroFlowEntrustBootstrap.setMicroFlowAsynMemeryProcess(this);
    }

    @SuppressWarnings({"rawtypes"})
    private void executeScheduled() {
        while (this.running) {
            try {
                AsynEvent event = retryQueue.poll(3, TimeUnit.SECONDS);

                if (event == null) {
                    continue;
                }

                // 间隔重试
                scheduledThreadPool.schedule(() -> {
                    executeEvent(event);
                }, event.getRetryPolicy().getRetryInterval(), TimeUnit.SECONDS);
            } catch (Throwable e) {
                log.error(e.toString(), e);
            }
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void executeEvent(AsynEvent event) {
        try {
            // 算子异步执行
            event.getVertex().then(event.getBo());
        } catch (Throwable e) {
            log.warn(e.toString(), e);

            if (event.increaseFail()) {
                addEventToRetryQueue(event);
            } else {
                log.warn("execute AsynEvent fail and drop it:{}", event);
            }
        }
    }

    private void addEventToRetryQueue(AsynEvent event) {
        try {
            boolean offer = retryQueue.offer(event, 3, TimeUnit.SECONDS);

            if (!offer) {
                log.warn("addEventToRetryQueue fail and drop it:{}", event);
            }
        } catch (InterruptedException e) {
            log.error(e.toString(), e);

            log.warn("addEventToRetryQueue fail and drop it:{}", event);
        }
    }

}

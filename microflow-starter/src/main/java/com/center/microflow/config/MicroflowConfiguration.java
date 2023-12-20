package com.center.microflow.config;

import com.center.microflow.adapter.*;
import com.center.microflow.api.ITransactionManager;
import com.center.microflow.api.MicroFlowAsynMemeryProcess;
import com.center.microflow.api.MicroFlowFindVertex;
import com.center.microflow.api.MicroFlowParallelProcess;
import com.center.microflow.boot.MicroFlowEntrustBootstrap;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * microflow的初始化启动
 */
@Configuration
@ConditionalOnClass(MicroFlowEntrustBootstrap.class)
public class MicroflowConfiguration {

    @Bean
    @ConditionalOnMissingBean(MicroFlowFindVertex.class)
    public MicroFlowFindVertex microFlowFindVertexSpringImpl() {
        return new MicroFlowFindVertexSpringImpl();
    }

    @Bean
    @ConditionalOnMissingBean(MicroFlowAsynMemeryProcess.class)
    public MicroFlowAsynMemeryProcess microFlowAsynMemeryProcessSpringImpl() {
        return new MicroFlowAsynMemeryProcessSpringImpl();
    }

    /**
     * microflow.parallelType=simple（默认加载simple）
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(MicroFlowParallelProcess.class)
    @ConditionalOnProperty(prefix = "microflow", name = "parallelType", havingValue = "simple", matchIfMissing = true)
    public MicroFlowParallelProcess microFlowParallelProcessSpringImpl() {
        return new MicroFlowParallelProcessSpringImpl();
    }

    /**
     * microflow.parallelType=plus(只有配置是plus的时候才加载)
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(MicroFlowParallelProcess.class)
    @ConditionalOnProperty(prefix = "microflow", name = "parallelType", havingValue = "plus")
    public MicroFlowParallelProcess microFlowParallelProcessSpringPlusImpl() {
        return new MicroFlowParallelProcessSpringPlusImpl();
    }

    /**
     * 只有PlatformTransactionManager这个bean存在的时候才加载
     *
     * @return
     */
    @Bean
    @ConditionalOnBean(PlatformTransactionManager.class)
    @ConditionalOnMissingBean(ITransactionManager.class)
    public ITransactionManager transactionManagerSpringImpl(PlatformTransactionManager transactionManager) {
        return new TransactionManagerSpringImpl(transactionManager);
    }

}

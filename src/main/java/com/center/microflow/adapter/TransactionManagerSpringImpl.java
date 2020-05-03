package com.center.microflow.adapter;

import com.center.microflow.api.ITransactionManager;
import com.center.microflow.domain.MicroFlowRuntimeException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionManagerSpringImpl implements ITransactionManager<TransactionStatus> {
    private PlatformTransactionManager transactionManager;

    private int transactionDefinition = DefaultTransactionDefinition.PROPAGATION_REQUIRED;

    public TransactionManagerSpringImpl() {
    }

    public TransactionManagerSpringImpl(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public TransactionManagerSpringImpl(PlatformTransactionManager transactionManager, int transactionDefinition) {
        this.transactionManager = transactionManager;
        this.transactionDefinition = transactionDefinition;
    }

    @Override
    public TransactionStatus begin() throws MicroFlowRuntimeException {
        DefaultTransactionDefinition transDef = new DefaultTransactionDefinition();// 定义事务属性
        transDef.setPropagationBehavior(this.transactionDefinition);// 设置传播行为属性
        TransactionStatus status = this.transactionManager.getTransaction(transDef);// 获得事务状态,即开始事务
        return status;
    }

    @Override
    public void commit(TransactionStatus status) throws MicroFlowRuntimeException {
        this.transactionManager.commit(status);
    }

    @Override
    public void rollback(TransactionStatus status) throws MicroFlowRuntimeException {
        this.transactionManager.rollback(status);
    }

    public PlatformTransactionManager getTransactionManager() {
        return this.transactionManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public int getTransactionDefinition() {
        return this.transactionDefinition;
    }

    public void setTransactionDefinition(int transactionDefinition) {
        this.transactionDefinition = transactionDefinition;
    }
}

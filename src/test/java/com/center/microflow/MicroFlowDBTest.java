package com.center.microflow;

import com.center.microflow.api.MicroFlow;
import com.center.microflow.constant.StageConstant;
import com.center.microflow.domain.ExecuteResult;
import com.center.microflow.factory.MicroFlowEngineFactory;
import com.center.microflow.adapter.TransactionManagerSpringImpl;
import com.center.microflow.test.OrderBo;
import com.center.microflow.test.jdbc.OrderJdbcGroup;
import com.center.microflow.test.jdbc.PrepareNoneVertex;
import com.center.microflow.test.jdbc.TestBean;
import com.center.microflow.test.jdbc.TestDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;


/**
 * 依赖数据库
 */
public class MicroFlowDBTest {
    private ApplicationContext context;

    @Before
    public void before() {
        this.context = new ClassPathXmlApplicationContext("classpath:applicationContext-jdbc.xml");
        initDB();
    }

    @Test
    public void testFlowWithTransaction() {

        TestDao dao = (TestDao) context.getBean("testDao");

        PlatformTransactionManager transactionManager = (PlatformTransactionManager) context.getBean("transactionManager");

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        microFlow.addStage(StageConstant.PREPARE, new PrepareNoneVertex())
                .addStage(StageConstant.PERSISTENT, OrderJdbcGroup.PRISTEND, new TransactionManagerSpringImpl(transactionManager));

        OrderBo bo = new OrderBo();

        bo.setId("123");
        bo.setName("zhuzhu");

        ExecuteResult execute = microFlow.execute(bo);

        TestBean testBean = dao.find(123);

        TestBean testBean2 = dao.find(99);

        Assert.assertEquals(true, execute.isSuccess());
        Assert.assertEquals(true, testBean2 != null);
        Assert.assertEquals(123, testBean.getId());
        Assert.assertEquals("zhuzhu-ver", testBean.getName());
    }

    @Test
    public void testFlowWithTransactionException() {

        TestDao dao = (TestDao) context.getBean("testDao");

        PlatformTransactionManager transactionManager = (PlatformTransactionManager) context.getBean("transactionManager");

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        microFlow.addStage(StageConstant.PREPARE, new PrepareNoneVertex())
                .addStage(StageConstant.PERSISTENT, OrderJdbcGroup.PRISTEND, new TransactionManagerSpringImpl(transactionManager));

        OrderBo bo = new OrderBo();

        // 模拟主键冲突
        bo.setId("1");
        bo.setName("error");

        ExecuteResult execute = microFlow.execute(bo);

        TestBean testBean = dao.find(1);

        // 回滚未插入
        TestBean testBean2 = dao.find(99);

        Assert.assertEquals(true, execute.isFail());
        Assert.assertEquals(null, testBean2);
        Assert.assertEquals("zhuzhu", testBean.getName());
    }

    @Test
    public void testFlowWithoutTransaction() {

        TestDao dao = (TestDao) context.getBean("testDao");

        PlatformTransactionManager transactionManager = (PlatformTransactionManager) context.getBean("transactionManager");

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        // 没有事务，不会提交的
        microFlow.addStage(StageConstant.PREPARE, new PrepareNoneVertex())
                .addStage(StageConstant.PERSISTENT, OrderJdbcGroup.PRISTEND);

        OrderBo bo = new OrderBo();

        bo.setId("123");
        bo.setName("zhuzhu");

        ExecuteResult execute = microFlow.execute(bo);

        TestBean testBean = dao.find(123);

        TestBean testBean2 = dao.find(99);

        Assert.assertEquals(true, execute.isSuccess());
        Assert.assertEquals(true, testBean2 == null);
        Assert.assertEquals(true, testBean == null);
    }

    /**
     * 外层事务
     */
    @Test
    public void testFlowWithTransaction2() {

        TestDao dao = (TestDao) context.getBean("testDao");

        TransactionManagerSpringImpl transactionManager = new TransactionManagerSpringImpl((PlatformTransactionManager) context.getBean("transactionManager"));

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        microFlow.addStage(StageConstant.PREPARE, new PrepareNoneVertex())
                .addStage(StageConstant.PERSISTENT, OrderJdbcGroup.PRISTEND);

        OrderBo bo = new OrderBo();

        bo.setId("123");
        bo.setName("zhuzhu");

        // 编程式事务
        TransactionStatus status = transactionManager.begin();
        ExecuteResult execute = microFlow.execute(bo);
        transactionManager.commit(status);

        TestBean testBean = dao.find(123);

        TestBean testBean2 = dao.find(99);

        Assert.assertEquals(true, execute.isSuccess());
        Assert.assertEquals(true, testBean2 != null);
        Assert.assertEquals(123, testBean.getId());
        Assert.assertEquals("zhuzhu-ver", testBean.getName());
    }

    private void initDB() {
        TestDao dao = (TestDao) context.getBean("testDao");

        TransactionManagerSpringImpl transactionManager = new TransactionManagerSpringImpl((PlatformTransactionManager) context.getBean("transactionManager"));

        TransactionStatus status = transactionManager.begin();

        int i = dao.deleteAll();

        System.out.println("delete count:" + i);

        TestBean bean = new TestBean();
        bean.setId(1);
        bean.setName("zhuzhu");

        dao.addTest(bean);

        transactionManager.commit(status);
    }


}

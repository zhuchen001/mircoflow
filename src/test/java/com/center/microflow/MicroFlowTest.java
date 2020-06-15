package com.center.microflow;

import com.center.microflow.api.BranchFlow;
import com.center.microflow.api.IVertex;
import com.center.microflow.api.MicroFlow;
import com.center.microflow.constant.StageConstant;
import com.center.microflow.domain.ExecuteResult;
import com.center.microflow.factory.MicroFlowEngineFactory;
import com.center.microflow.test.*;
import com.center.microflow.test.jdbc.PrepareNoneVertex;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;


/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml"})
public class MicroFlowTest {
    @Autowired
    private ApplicationContext context;

    @Before
    public void before() {
        //this.context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }

    @After
    public void after() {
        //this.context.close();
    }

    /**
     * 正常流程
     */
    @Test
    public void testFlow() {
        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        microFlow.addStage(StageConstant.PREPARE, OrderGroup.PREPARE)
                .addStage(StageConstant.PROCESS, OrderGroup.PROCESS)
                .addStage(StageConstant.PERSISTENT, OrderGroup.PRISTEND);

        OrderBo bo = new OrderBo();

        bo.setId("123");
        bo.setName("zhuzhu");

        ExecuteResult execute = microFlow.execute(bo);

        Assert.assertEquals(true, execute.isSuccess());
        Assert.assertEquals("456", bo.getId());
        Assert.assertEquals(4, execute.getVertexTrackList().size());
        Assert.assertEquals(true, execute.getVertexTrackList().get(0).getContext() != null);
    }

    @Test
    public void testFlowNoDebug() {

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class);

        microFlow.addStage(StageConstant.PREPARE, OrderGroup.PREPARE)
                .addStage(StageConstant.PROCESS, OrderGroup.PROCESS)
                .addStage(StageConstant.PERSISTENT, OrderGroup.PRISTEND);


        OrderBo bo = new OrderBo();

        bo.setId("123");
        bo.setName("zhuzhu");

        ExecuteResult execute = microFlow.execute(bo);

        Assert.assertEquals(true, execute.isSuccess());
        Assert.assertEquals("456", bo.getId());
        Assert.assertEquals(4, execute.getVertexTrackList().size());
        Assert.assertEquals(true, execute.getVertexTrackList().get(0).getContext() == null);
    }

    @Test
    public void testFlowBreak() {

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        microFlow.addStage(StageConstant.PREPARE, OrderGroup.PREPARE)
                .addStage(StageConstant.PROCESS, new BreakVertex())
                .addStage(StageConstant.PERSISTENT, OrderGroup.PRISTEND);

        OrderBo bo = new OrderBo();

        bo.setId("123");
        bo.setName("zhuzhu");
        bo.setDesc("desc");

        ExecuteResult execute = microFlow.execute(bo);

        Assert.assertEquals(true, execute.isBreak());
        Assert.assertEquals("456", bo.getId());
        Assert.assertEquals(3, execute.getVertexTrackList().size());
        Assert.assertEquals("desc", bo.getDesc());
    }

    @Test
    public void testFlowFinally() {

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        microFlow.addStage(StageConstant.PREPARE, OrderGroup.PREPARE)
                .addStage(StageConstant.PROCESS, new BreakVertex())
                .addStage(StageConstant.PERSISTENT, new FinallyVertex());

        OrderBo bo = new OrderBo();

        bo.setId("123");
        bo.setName("zhuzhu");
        bo.setDesc("desc");

        ExecuteResult execute = microFlow.execute(bo);

        Assert.assertEquals(true, execute.isBreak());
        Assert.assertEquals(4, execute.getVertexTrackList().size());
        Assert.assertEquals("finally", bo.getDesc());
    }

    @Test
    public void testFlowException() {

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        microFlow.addStage(StageConstant.PREPARE, OrderGroup.PREPARE)
                .addStage(StageConstant.PROCESS, new ExceptionVertex())
                .addStage(StageConstant.PERSISTENT, OrderGroup.PRISTEND);

        OrderBo bo = new OrderBo();

        bo.setId("123");
        bo.setName("zhuzhu");
        bo.setDesc("desc");

        ExecuteResult execute = microFlow.execute(bo);

        Assert.assertEquals(true, execute.isFail());
        Assert.assertEquals(2, execute.getVertexTrackList().size());
        Assert.assertEquals("F001", execute.getFailInfo().getCode());
    }

    /**
     * 测试异常handler
     */
    @Test
    public void testFlowException2() {

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        microFlow.addStage(StageConstant.PREPARE, OrderGroup.PREPARE)
                .addStage(StageConstant.PROCESS, new ExceptionVertex())
                .addStage(StageConstant.PERSISTENT, OrderGroup.PRISTEND)
                .setExceptionHandler((e, t) -> {
                    t.setDesc("exception");
                });

        OrderBo bo = new OrderBo();

        bo.setId("123");
        bo.setName("zhuzhu");
        bo.setDesc("desc");

        ExecuteResult execute = microFlow.execute(bo);

        Assert.assertEquals(true, execute.isFail());
        Assert.assertEquals(2, execute.getVertexTrackList().size());
        Assert.assertEquals("F001", execute.getFailInfo().getCode());
        Assert.assertEquals("exception", bo.getDesc());
    }

    @Test
    public void testFlowBranch() {

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        BranchFlow<OrderBo> branch = MicroFlowEngineFactory.createBranchFlow("pay", new OrderBranchDecide(), OrderBo.class);

        branch.addBranch(OrderBranch.PAY, new BranchPayVertex())
                .addBranch(OrderBranch.NOPAY, new BranchNoPayVertex())
                .addBranch(OrderBranch.FREE, OrderGroup.FREE);

        microFlow.addStage(StageConstant.PREPARE, OrderGroup.PREPARE)
                .addStage(StageConstant.PROCESS, branch)
                .addStage(StageConstant.PERSISTENT, OrderGroup.PRISTEND);

        OrderBo bo = null;

        ExecuteResult execute = null;

        bo = new OrderBo();
        bo.setId("123");
        bo.setName("zhuzhu");
        bo.setDesc("desc");
        bo.setType(0);

        execute = microFlow.execute(bo);

        System.out.println(execute);

        Assert.assertEquals(true, execute.isSuccess());
        Assert.assertEquals("store", bo.getDesc());
        Assert.assertEquals(4, execute.getVertexTrackList().size());
        Assert.assertEquals("pay", bo.getPayInfo());

        bo = new OrderBo();
        bo.setId("123");
        bo.setName("zhuzhu");
        bo.setDesc("desc");
        bo.setType(1);

        execute = microFlow.execute(bo);

        Assert.assertEquals(true, execute.isSuccess());
        Assert.assertEquals("store", bo.getDesc());
        Assert.assertEquals(4, execute.getVertexTrackList().size());
        Assert.assertEquals("nopay", bo.getPayInfo());

        bo = new OrderBo();
        bo.setId("123");
        bo.setName("zhuzhu");
        bo.setDesc("desc");
        bo.setType(2);

        execute = microFlow.execute(bo);

        Assert.assertEquals(true, execute.isSuccess());
        Assert.assertEquals("store", bo.getDesc());
        Assert.assertEquals(4, execute.getVertexTrackList().size());
        Assert.assertEquals("free", bo.getPayInfo());
    }

    @Test
    public void testFlowSubFlow() {

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        MicroFlow<OrderBo> subMicroFlow = MicroFlowEngineFactory.createMicroFlow("order-sub-flow", OrderBo.class, true);

        subMicroFlow.addStage(StageConstant.PREPARE, new BranchPayVertex())
                .addStage(StageConstant.PREPARE, OrderGroup.FREE);

        microFlow.addStage(StageConstant.PREPARE, OrderGroup.PREPARE)
                .addStage(StageConstant.PROCESS, subMicroFlow)
                .addStage(StageConstant.PERSISTENT, OrderGroup.PRISTEND);

        OrderBo bo = null;

        ExecuteResult execute = null;

        bo = new OrderBo();
        bo.setId("123");
        bo.setName("zhuzhu");
        bo.setDesc("desc");
        bo.setType(0);

        execute = microFlow.execute(bo);

        //System.out.println(execute.toString2());

        Assert.assertEquals(true, execute.isSuccess());
        Assert.assertEquals("store", bo.getDesc());
        Assert.assertEquals(5, execute.getVertexTrackList().size());
        Assert.assertEquals("free", bo.getPayInfo());

    }

    /**
     * 子流程异常会抛出到顶层
     */
    @Test
    public void testFlowSubFlowException() {

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        MicroFlow<OrderBo> subMicroFlow = MicroFlowEngineFactory.createMicroFlow("order-sub-flow", OrderBo.class, true);

        subMicroFlow.addStage(StageConstant.PREPARE, new BranchPayVertex())
                .addStage(StageConstant.PREPARE, new ExceptionVertex());

        microFlow.addStage(StageConstant.PREPARE, OrderGroup.PREPARE)
                .addStage(StageConstant.PROCESS, subMicroFlow)
                .addStage(StageConstant.PERSISTENT, OrderGroup.PRISTEND);

        OrderBo bo = null;

        ExecuteResult execute = null;

        bo = new OrderBo();
        bo.setId("123");
        bo.setName("zhuzhu");
        bo.setDesc("desc");
        bo.setType(0);

        execute = microFlow.execute(bo);

        //System.out.println(execute.toString2());

        Assert.assertEquals(true, execute.isFail());
        Assert.assertEquals("F001", execute.getFailInfo().getCode());

    }

    @Test
    public void testFlowAsyn() {

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        microFlow.addStage(StageConstant.PREPARE, OrderGroup.PREPARE)
                .addStage(StageConstant.PROCESS, OrderGroup.PROCESS)
                .addStage(StageConstant.POSTPROCESS, new AsynVertex());

        OrderBo bo = new OrderBo();

        bo.setId("123");
        bo.setName("zhuzhu");
        bo.setDesc("desc");

        ExecuteResult execute = microFlow.execute(bo);

        Assert.assertEquals(true, execute.isSuccess());
        Assert.assertEquals(4, execute.getVertexTrackList().size());
        Assert.assertEquals("desc", bo.getDesc());
    }

    @Test
    public void testFlowParallel() {

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        microFlow.addStage(StageConstant.PREPARE, OrderGroup.PREPARE)
                .addStage("PARALLEL", OrderGroup.PARALLEL)
                .addStage(StageConstant.PROCESS, new PrepareNoneVertex());

        OrderBo bo = new OrderBo();

        bo.setId("123");
        bo.setName("zhuzhu");
        bo.setDesc("desc");

        ExecuteResult execute = microFlow.execute(bo);

        Assert.assertEquals(true, execute.isSuccess());
        Assert.assertEquals("parallel", bo.getName());
        Assert.assertEquals("789", bo.getId());
        Assert.assertEquals("parallel-pay", bo.getPayInfo());
    }

    @Test
    public void testFlowParallelLast() {

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        microFlow.addStage(StageConstant.PREPARE, OrderGroup.PREPARE)
                .addStage("PARALLEL", OrderGroup.PARALLEL);

        OrderBo bo = new OrderBo();

        bo.setId("123");
        bo.setName("zhuzhu");
        bo.setDesc("desc");

        ExecuteResult execute = microFlow.execute(bo);

        Assert.assertEquals(true, execute.isSuccess());
        Assert.assertEquals("parallel", bo.getName());
        Assert.assertEquals("789", bo.getId());
        Assert.assertEquals("parallel-pay", bo.getPayInfo());
    }

    @Test
    public void testFlowParallelException() {

        List verList = new ArrayList();

        verList.add(new PrepareParaVertex());
        verList.add(new ParallelVerter1());
        verList.add(new ParallelVerter2());
        verList.add(new ParallelVerter3());

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        microFlow.addStage(StageConstant.PREPARE, OrderGroup.PREPARE)
                .addStage("PARALLEL", verList)
                .addStage(StageConstant.PROCESS, new PrepareNoneVertex());

        OrderBo bo = new OrderBo();

        bo.setId("123");
        bo.setName("zhuzhu");
        bo.setDesc("desc");

        ExecuteResult execute = microFlow.execute(bo);

        Assert.assertEquals(true, execute.isFail());
    }

    @Test
    public void testFlowFork() {

        List verList = new ArrayList();

        verList.add(new PrepareParaVertex());
        // fork算子
        verList.add(new ForkVerter1());

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        microFlow.addStage(StageConstant.PREPARE, OrderGroup.PREPARE)
                .addStage("fork", verList)
                .addStage(StageConstant.PROCESS, new PrepareNoneVertex());

        OrderBo bo = new OrderBo();

        bo.setId("123");
        bo.setName("zhuzhu");
        bo.setDesc("desc");

        ExecuteResult execute = microFlow.execute(bo);

        Assert.assertEquals(true, execute.isSuccess());
        Assert.assertEquals("fork", bo.getName());
    }

    /**
     * fork算子复用
     */
    @Test
    public void testFlowForkMult() {
        MultForkVertexImpl multForkVertexImpl = this.context.getBean("multForkVertexImpl", MultForkVertexImpl.class);

        List<IVertex<OrderBo>> verList = new ArrayList();

        verList.add(new PrepareParaVertex());
        // fork算子
        verList.add(multForkVertexImpl);

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        microFlow.addStage(StageConstant.PREPARE, OrderGroup.PREPARE)
                .addStage("fork", verList)
                .addStage(StageConstant.PROCESS, new PrepareNoneVertex());

        OrderBo bo = new OrderBo();

        bo.setId("123");
        bo.setName("zhuzhu");
        bo.setDesc("desc");

        ExecuteResult execute = microFlow.execute(bo);

        Assert.assertEquals(true, execute.isSuccess());
        Assert.assertEquals("1001", bo.getId());
    }

    @Test
    public void testFlowMult() {

        List verList = new ArrayList();

        verList.add(new PrepareParaVertex());
        // mult算子
        verList.add(new MultVertex1());

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        microFlow.addStage(StageConstant.PREPARE, OrderGroup.PREPARE)
                .addStage("mult", verList)
                .addStage(StageConstant.PROCESS, new PrepareNoneVertex());

        OrderBo bo = new OrderBo();

        bo.setId("123");
        bo.setName("zhuzhu");
        bo.setDesc("desc");

        ExecuteResult execute = microFlow.execute(bo);

        Assert.assertEquals(true, execute.isSuccess());
        Assert.assertEquals("998", bo.getId());
    }

    @Test
    public void testFlowMult2() {

        List verList = new ArrayList();

        verList.add(new PrepareParaVertex());
        // mult算子
        verList.add(new MultVertex1());

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        microFlow.addStage(StageConstant.PREPARE, OrderGroup.PREPARE)
                .addStage("mult", OrderGroup.MULT)
                .addStage(StageConstant.PROCESS, new PrepareNoneVertex());

        OrderBo bo = new OrderBo();

        bo.setId("123");
        bo.setName("zhuzhu");
        bo.setDesc("desc");

        ExecuteResult execute = microFlow.execute(bo);

        Assert.assertEquals(true, execute.isSuccess());
        Assert.assertEquals("998", bo.getId());
    }

    /**
     * 并行复用算子
     */
    @Test
    public void testFlowParalleMult() {

        List verList = new ArrayList();

        verList.add(new PrepareParaVertex());
        // mult算子
        verList.add(new MultVertex1());

        MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        microFlow.addStage(StageConstant.PREPARE, OrderGroup.PREPARE)
                .addStage("mult", OrderGroup.PARMULT)
                .addStage(StageConstant.PROCESS, new PrepareNoneVertex());

        OrderBo bo = new OrderBo();

        bo.setId("123");
        bo.setName("zhuzhu");
        bo.setDesc("desc");

        ExecuteResult execute = microFlow.execute(bo);

        Assert.assertEquals(true, execute.isSuccess());
        Assert.assertEquals("1001", bo.getId());
        Assert.assertEquals("mult2", bo.getName());
    }
}

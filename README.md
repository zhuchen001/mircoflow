# mircoflow
微流程引擎

# code
```java
MicroFlow<OrderBo> microFlow = MicroFlowEngineFactory.createMicroFlow("order-flow", OrderBo.class, true);

        microFlow.addStage(StageConstant.PREPARE, OrderGroup.PREPARE)
                .addStage(StageConstant.PROCESS, OrderGroup.PROCESS)
                .addStage(StageConstant.PERSISTENT, OrderGroup.PRISTEND);

        OrderBo bo = new OrderBo();

        bo.setId("123");
        bo.setName("zhuzhu");

        ExecuteResult execute = microFlow.execute(bo);
   ```


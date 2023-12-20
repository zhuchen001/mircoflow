package com.center.microflow.test;

import com.center.microflow.api.GroupEnum;
import com.center.microflow.api.ParallelVertex;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class ParallelVerter3 implements ParallelVertex<OrderBo> {
    @Override
    public void merge(OrderBo futureBo, OrderBo context) {
        context.setPayInfo(futureBo.getPayInfo());
    }

    @Override
    public BigDecimal order() {
        return new BigDecimal(2.5);
    }

    @Override
    public boolean when(OrderBo orderBo) {
        return true;
    }

    @Override
    public void then(OrderBo orderBo) throws Exception {
        TimeUnit.SECONDS.sleep(5);
    }

    @Override
    public long timeout() {
        return 3000L;
    }

    @Override
    public GroupEnum group() {
        return OrderGroup.PARALLEL;
    }
}

package com.center.microflow.test;

import com.center.microflow.api.GroupEnum;
import com.center.microflow.api.ParallelVertex;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ParallelVerter2 implements ParallelVertex<OrderBo> {
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
        orderBo.setPayInfo("parallel-pay");
    }

    @Override
    public GroupEnum group() {
        return OrderGroup.PARALLEL;
    }
}

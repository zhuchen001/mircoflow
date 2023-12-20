package com.center.microflow.test;

import com.center.microflow.api.ForkVertex;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Component
public class ForkVerter1 implements ForkVertex<OrderBo> {
    @Override
    public void merge(OrderBo futureBo, OrderBo context) {
        context.setName(futureBo.getName());
    }

    @Override
    public BigDecimal order() {
        return new BigDecimal(0);
    }

    @Override
    public boolean when(OrderBo orderBo) {
        return true;
    }

    @Override
    public void then(OrderBo orderBo) throws Exception {
        TimeUnit.SECONDS.sleep(1);
        orderBo.setName("fork");
    }

}

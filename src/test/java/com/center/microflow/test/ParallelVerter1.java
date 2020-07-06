package com.center.microflow.test;

import com.center.microflow.api.Desc;
import com.center.microflow.api.GroupEnum;
import com.center.microflow.api.ParallelVertex;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Desc(value = "Test")
@Component
public class ParallelVerter1 implements ParallelVertex<OrderBo> {
    @Override
    public void merge(OrderBo futureBo, OrderBo context) {
        context.setName(futureBo.getName());
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
        orderBo.setName("parallel");
    }

    @Override
    public GroupEnum group() {
        return OrderGroup.PARALLEL;
    }
}

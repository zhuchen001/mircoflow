package com.center.microflow.test;

import com.center.microflow.api.GroupEnum;
import com.center.microflow.api.IVertex;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MultParalleNoneVertex implements IVertex<OrderBo> {
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
        orderBo.setId("101");

    }

    @Override
    public GroupEnum group() {
        return OrderGroup.PARMULT;
    }
}

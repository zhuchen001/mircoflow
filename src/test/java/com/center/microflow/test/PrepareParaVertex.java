package com.center.microflow.test;

import com.center.microflow.api.GroupEnum;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PrepareParaVertex implements OrderVertex {
    @Override
    public BigDecimal order() {
        return new BigDecimal(0);
    }

    @Override
    public boolean when(OrderBo orderBo) {
        return true;
    }

    @Override
    public void then(OrderBo orderBo) {
        orderBo.setId("789");
    }

    @Override
    public GroupEnum group() {
        return OrderGroup.PARALLEL;
    }
}

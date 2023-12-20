package com.center.microflow.test;

import com.center.microflow.api.GroupEnum;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StoreVertex implements OrderVertex {
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
        orderBo.setDesc("store");
    }

    @Override
    public GroupEnum group() {
        return OrderGroup.PRISTEND;
    }
}

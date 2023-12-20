package com.center.microflow.test;

import com.center.microflow.api.GroupEnum;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 条件不满足
 */
@Component
public class PrepareVertex2 implements OrderVertex {
    @Override
    public BigDecimal order() {
        return new BigDecimal(-1);
    }

    @Override
    public boolean when(OrderBo orderBo) {
        return false;
    }

    @Override
    public void then(OrderBo orderBo) {
        orderBo.setId("4567");
    }

    @Override
    public GroupEnum group() {
        return OrderGroup.PREPARE;
    }
}

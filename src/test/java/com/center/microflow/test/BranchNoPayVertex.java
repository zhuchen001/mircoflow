package com.center.microflow.test;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BranchNoPayVertex implements OrderVertex {
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
        orderBo.setPayInfo("nopay");
    }

}

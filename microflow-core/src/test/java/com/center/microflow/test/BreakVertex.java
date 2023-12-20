package com.center.microflow.test;

import com.center.microflow.api.GroupEnum;

import java.math.BigDecimal;


public class BreakVertex implements OrderVertex {
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
        setBreak("break in vertex");
    }

    @Override
    public GroupEnum group() {
        return OrderGroup.PROCESS;
    }
}

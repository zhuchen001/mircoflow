package com.center.microflow.test;

import com.center.microflow.api.GroupEnum;
import com.center.microflow.domain.MicroFlowRuntimeException;

import java.math.BigDecimal;


public class ExceptionVertex implements OrderVertex {
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
        throw new MicroFlowRuntimeException("F001", "exception");
    }

    @Override
    public GroupEnum group() {
        return OrderGroup.PROCESS;
    }
}

package com.center.microflow.test.jdbc;

import com.center.microflow.test.OrderBo;
import com.center.microflow.test.OrderVertex;

import java.math.BigDecimal;


public class PrepareNoneVertex implements OrderVertex {
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
    }


}

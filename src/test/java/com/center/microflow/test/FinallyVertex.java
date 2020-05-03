package com.center.microflow.test;

import com.center.microflow.domain.VertexMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FinallyVertex implements OrderVertex {
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
        orderBo.setDesc("finally");
    }

    @Override
    public VertexMode mode() {
        return VertexMode.FINALLY;
    }
}

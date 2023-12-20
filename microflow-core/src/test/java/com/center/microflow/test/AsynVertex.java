package com.center.microflow.test;

import com.center.microflow.domain.VertexType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AsynVertex implements OrderVertex {
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
        System.out.println("AsynVertex:" + orderBo);
    }


    @Override
    public VertexType type() {
        return VertexType.ASYN_MEM;
    }
}

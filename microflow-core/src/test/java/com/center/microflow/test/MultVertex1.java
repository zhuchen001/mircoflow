package com.center.microflow.test;

import com.center.microflow.api.IVertex;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("multVertex1")
public class MultVertex1 implements IVertex<IName> {
    @Override
    public BigDecimal order() {
        return new BigDecimal(0);
    }

    @Override
    public boolean when(IName orderBo) {
        return true;
    }

    @Override
    public void then(IName orderBo) throws Exception {
        System.out.println("read data from:" + orderBo.getName());
        orderBo.setId("998");
    }

}

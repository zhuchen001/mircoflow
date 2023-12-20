package com.center.microflow.test;

import com.center.microflow.api.GroupEnum;
import com.center.microflow.api.IVertex;
import com.center.microflow.api.MultVertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MultVertex2 implements MultVertex<OrderBo, IName> {

    @Autowired
    @Qualifier("multVertex1")
    private IVertex<IName> vertex;

    @Override
    public IName transferBo(OrderBo bo) {
        Name name = new Name();
        name.setName(bo.getName());
        return name;
    }

    @Override
    public void writeBackBo(OrderBo bo, IName iName) {
        bo.setId(iName.getId());
    }

    @Override
    public BigDecimal order() {
        return new BigDecimal(0);
    }

    @Override
    public IVertex<IName> getVertex() {
        return this.vertex;
    }

    @Override
    public GroupEnum group() {
        return OrderGroup.MULT;
    }
}

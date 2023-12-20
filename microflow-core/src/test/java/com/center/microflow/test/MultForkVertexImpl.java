package com.center.microflow.test;

import com.center.microflow.api.GroupEnum;
import com.center.microflow.api.IVertex;
import com.center.microflow.api.MultForkVertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("multForkVertexImpl")
public class MultForkVertexImpl implements MultForkVertex<OrderBo, IName> {

    @Autowired
    @Qualifier("multVertex1")
    private IVertex<IName> vertex;

    @Override
    public IVertex<IName> getVertex() {
        return this.vertex;
    }

    @Override
    public IName transferBo(OrderBo bo) {
        Name name = new Name();
        name.setName(bo.getName());
        return name;
    }

    @Override
    public void writeBackBo(OrderBo bo, IName name) {
        bo.setId(name.getId());
    }

    @Override
    public void merge(OrderBo futureBo, OrderBo context) {
        context.setId("1001");
    }

    @Override
    public BigDecimal order() {
        return new BigDecimal(1);
    }

    @Override
    public GroupEnum group() {
        return OrderGroup.PARMULT;
    }
}

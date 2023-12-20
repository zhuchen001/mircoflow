package com.center.microflow.test.jdbc;

import com.center.microflow.api.GroupEnum;
import com.center.microflow.test.OrderBo;
import com.center.microflow.test.OrderVertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PersistentAdd0Vertex implements OrderVertex {

    @Autowired
    private TestDao testDao;

    @Override
    public BigDecimal order() {
        return new BigDecimal(-1);
    }

    @Override
    public boolean when(OrderBo orderBo) {
        return true;
    }

    @Override
    public void then(OrderBo orderBo) {
        TestBean test = new TestBean();
        test.setId(99);
        test.setName(orderBo.getName());
        testDao.addTest(test);
    }

    @Override
    public GroupEnum group() {
        return OrderJdbcGroup.PRISTEND;
    }
}

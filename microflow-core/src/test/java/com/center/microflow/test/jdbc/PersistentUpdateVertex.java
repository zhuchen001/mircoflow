package com.center.microflow.test.jdbc;

import com.center.microflow.api.GroupEnum;
import com.center.microflow.test.OrderBo;
import com.center.microflow.test.OrderVertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PersistentUpdateVertex implements OrderVertex {

    @Autowired
    private TestDao testDao;

    @Override
    public BigDecimal order() {
        return new BigDecimal(1);
    }

    @Override
    public boolean when(OrderBo orderBo) {
        return true;
    }

    @Override
    public void then(OrderBo orderBo) {
        TestBean test = new TestBean();
        test.setId(Integer.parseInt(orderBo.getId()));
        test.setName(orderBo.getName() + "-ver");
        testDao.updateTest(test);
    }

    @Override
    public GroupEnum group() {
        return OrderJdbcGroup.PRISTEND;
    }
}

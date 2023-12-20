package com.center.microflow.test;

import com.center.microflow.api.GroupEnum;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Component
public class ProcessVertex implements OrderVertex {
    @Override
    public BigDecimal order() {
        return new BigDecimal(0);
    }

    @Override
    public boolean when(OrderBo orderBo) {
        return true;
    }

    @Override
    public void then(OrderBo orderBo) throws Exception {
        orderBo.setName("csd");
        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Override
    public GroupEnum group() {
        return OrderGroup.PROCESS;
    }
}

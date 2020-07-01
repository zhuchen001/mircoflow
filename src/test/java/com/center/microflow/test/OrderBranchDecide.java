package com.center.microflow.test;

import com.center.microflow.api.BranchDecide;
import com.center.microflow.api.IBranch;

public class OrderBranchDecide implements BranchDecide<OrderBo, OrderBranch> {

    @Override
    public OrderBranch decide(OrderBo bo) {
        if (bo.getType() == 0) return OrderBranch.PAY;
        if (bo.getType() == 1) return OrderBranch.NOPAY;

        return OrderBranch.FREE;
    }
}

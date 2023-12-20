package com.center.microflow.test;

import com.center.microflow.api.Desc;
import com.center.microflow.api.IBranch;

public enum OrderBranch implements IBranch {
    PAY(""),
    NOPAY(""),
    FREE("");

    private String name;

    private OrderBranch(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}

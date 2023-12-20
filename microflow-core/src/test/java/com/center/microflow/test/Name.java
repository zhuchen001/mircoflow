package com.center.microflow.test;

public class Name implements IName {
    private String id;
    private String name;

    @Override
    public String getName() {
        return this.name;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}

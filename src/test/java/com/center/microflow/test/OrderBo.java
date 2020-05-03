package com.center.microflow.test;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderBo implements IName, Serializable {
    private String id;
    private String name;
    private String desc;
    private String payInfo;
    private int type;
}

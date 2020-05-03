package com.center.microflow.api;

import java.io.Serializable;

/**
 * 克隆接口
 */
public interface Clone extends Cloneable, Serializable {
    Object clone();
}

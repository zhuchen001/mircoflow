/**
 *
 */
package com.center.microflow.domain;

/**
 * 算子类型
 *
 * @author Administrator
 *
 */
public enum VertexEnum {
    /** 分组算子（推荐） */
    VERTEX_GROUP,
    /** 算子列表 */
    VERTEX_LIST,
    /** 子流程 */
    SUB_FLOW,
    /** 分支 */
    BRANCH
}

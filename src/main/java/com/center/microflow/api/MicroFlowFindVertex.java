/**
 *
 */
package com.center.microflow.api;

import java.io.Serializable;
import java.util.List;

/**
 * 查询具体的算子定义
 *
 * @author Administrator
 *
 */
public interface MicroFlowFindVertex {

    /**
     * 根据分组获取微流程的算子
     * @param group
     * @return
     */
    <T extends Serializable> List<IVertex<T>> getMicroFlowFlowVertexListByGroup(GroupEnum group);

    /**
     * 根据ID查询算子实现类
     * @param id
     * @return
     */
    <T extends Serializable> IVertex<T> getMicroFlowFlowVertexById(String id);

}

/**
 *
 */
package com.center.microflow.adapter;

import com.center.microflow.api.GroupEnum;
import com.center.microflow.api.IVertex;
import com.center.microflow.api.MicroFlowFindVertex;
import com.center.microflow.boot.MicroFlowEntrustBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 *
 */
@Component
public class MicroFlowFindVertexSpringImpl implements MicroFlowFindVertex, ApplicationContextAware {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private Map<GroupEnum, List<IVertex>> beanMap = new HashMap<>();

    private Map<String, IVertex> vertexMap = new HashMap<>();

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.context.ApplicationContextAware#setApplicationContext(org
     * .springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {

        MicroFlowEntrustBootstrap.setMicroFlowFindVertex(this);

        Collection<IVertex> values = context.getBeansOfType(IVertex.class).values();

        // 加载所有算子到缓存中
        values.forEach(x -> this.vertexMap.put(x.id(), x));

        Map<GroupEnum, List<IVertex>> collect = values.stream().collect(Collectors.groupingBy(x -> x.group()));

        // 排序执行
        collect.forEach((k, v) -> v.sort((v1, v2) -> v1.order().compareTo(v2.order())));

        this.beanMap.putAll(collect);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.center.microflow.api.api.MicroFlowFindVertex#
     * getMicroFlowFlowVertexListByGroup(com.center.microflow.api.api.GroupEnum)
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public <T extends Serializable> List<IVertex<T>> getMicroFlowFlowVertexListByGroup(GroupEnum group) {
        Object cache = beanMap.get(group);

        if (cache != null) {
            return (List<IVertex<T>>) cache;
        } else {
            return new ArrayList<>();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.center.microflow.api.api.MicroFlowFindVertex#getMicroFlowFlowVertexById(java.
     * lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> IVertex<T> getMicroFlowFlowVertexById(String id) {
        return this.vertexMap.get(id);
    }

}

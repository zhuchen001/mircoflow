package com.center.microflow.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 算子等描述,用于自动生成流程图
 * @author zhuzhu
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Desc {
    /**
     * 描述的title
     */
    String value();

    /**
     * 描述详情
     * @return
     */
    String note() default "";
}

package com.touchkiss.catalogueoflife.es.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created on 2020/04/03 18:07
 *
 * @author Touchkiss
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface EsDocument {
    /**
     * es对应索引
     */
    String index();

    /**
     * 唯一标识，必填
     */
    String id();

    /**
     * 分片
     */
    short shards() default 5;

    /**
     * 副本
     */
    short replicas() default 1;
}

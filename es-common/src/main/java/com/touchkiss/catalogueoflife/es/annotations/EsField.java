package com.touchkiss.catalogueoflife.es.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created on 2020/04/03 18:07
 *
 * @author Touchkiss
 */
@Retention(RUNTIME)
@Target({FIELD, ANNOTATION_TYPE})
public @interface EsField {

    /**
     * 字段类型，默认自动，同字段类型，可以指定进行修改
     */
    FieldType type() default FieldType.Auto;

    /**
     * 是否允许被搜索
     */
    boolean index() default true;

    /**
     * 字段文本分词器（ik_max_word，ik_smart）
     */
    String analyzer() default "";

    /**
     * 搜索词的分词器
     */
    String search_analyzer() default "";

    enum FieldType {
        Text,
        Byte,
        Short,
        Integer,
        Long,
        Date,
        Half_Float,
        Float,
        Double,
        Boolean,
        Object,
        Auto,
        Nested,
        Ip,
        Attachment,
        Keyword
    }
}

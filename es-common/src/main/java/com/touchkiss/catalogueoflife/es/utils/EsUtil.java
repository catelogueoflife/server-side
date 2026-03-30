package com.touchkiss.catalogueoflife.es.utils;

import com.touchkiss.catalogueoflife.es.annotations.EsDocument;
import com.touchkiss.catalogueoflife.es.annotations.EsField;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EsUtil {
    private final static Map<String, String> FIELD_TYPE_MAP = new HashMap() {{
        put("int", EsField.FieldType.Integer.toString().toLowerCase());
        put("integer", EsField.FieldType.Integer.toString().toLowerCase());
        put("double", EsField.FieldType.Double.toString().toLowerCase());
        put("float", EsField.FieldType.Float.toString().toLowerCase());
        put("byte", EsField.FieldType.Byte.toString().toLowerCase());
        put("short", EsField.FieldType.Short.toString().toLowerCase());
        put("long", EsField.FieldType.Long.toString().toLowerCase());
        put("date", EsField.FieldType.Date.toString().toLowerCase());
        put("boolean", EsField.FieldType.Boolean.toString().toLowerCase());
        put("object", EsField.FieldType.Object.toString().toLowerCase());
        put("string", EsField.FieldType.Text.toString().toLowerCase());
    }};

    /**
     * 获取对象的es注解
     *
     * @param clazz
     * @return
     */
    public static EsDocument getEsTableAnnotation(Class clazz) {
        EsDocument esDocument = null;
        if (clazz.isAnnotationPresent(EsDocument.class)) {
            esDocument = (EsDocument) clazz.getAnnotation(EsDocument.class);
        }
        return esDocument;
    }

    public static String fitFieldType(Field field) {
        if (field.isAnnotationPresent(EsField.class)) {
            EsField esField = field.getAnnotation(EsField.class);
            if (EsField.FieldType.Auto.equals(esField.type())) {
                String fieldTypeName = field.getType().getTypeName().toLowerCase();
                fieldTypeName = fieldTypeName.substring(fieldTypeName.lastIndexOf(".") + 1);
                return FIELD_TYPE_MAP.containsKey(fieldTypeName) ? FIELD_TYPE_MAP.get(fieldTypeName) : FIELD_TYPE_MAP.get("string");
            } else {
                return esField.type().toString().toLowerCase();
            }
        }
        return FIELD_TYPE_MAP.get("string");
    }
}

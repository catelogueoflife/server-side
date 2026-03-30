package com.touchkiss.catalogueoflife.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 2020/03/19 9:51
 *
 * @author Touchkiss
 */
@Slf4j
public class MapUtil {
    public static final Map<String, String> EMPTY_MAP = Collections.unmodifiableMap(new HashMap<>());

    private MapUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static Map<String, Object> toMap(final Object obj, final boolean keepNull) {
        if (obj == null) {
            return Collections.emptyMap();
        }
        return Arrays.stream(obj.getClass().getDeclaredFields()).filter(field -> {
            field.setAccessible(true);
            try {
                return keepNull || field.get(obj) != null;
            } catch (Exception e) {
                log.warn("反射读取属性过滤失败, field: {}", field.getName(), e);
            }
            return false;
        }).collect(Collectors.toMap(Field::getName, field -> {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                return value == null ? "" : value;
            } catch (Exception e) {
                log.warn("反射读取属性值失败, field: {}", field.getName(), e);
            }
            return "";
        }));
    }

    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) {
        if (map == null || clazz == null) {
            return null;
        }
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();
            Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> map.containsKey(field.getName()))
                    .forEach(field -> {
                        String fieldName = field.getName();
                        field.setAccessible(true);
                        try {
                            Object value = map.get(fieldName);
                            if (value != null) {
                                field.set(obj, value);
                            }
                        } catch (IllegalAccessException e) {
                            log.warn("反射设置属性失败, field: {}", fieldName, e);
                        }
                    });
            return obj;
        } catch (Exception e) {
            log.error("Map转换为对象失败, clazz: {}", clazz.getName(), e);
        }
        return null;
    }
}

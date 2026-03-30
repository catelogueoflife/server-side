package com.touchkiss.catalogueoflife.utils;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;

/**
 * Created on 2020/03/18 17:27
 *
 * @author Touchkiss
 */
@Slf4j
public class GsonUtil {
    private final static Gson GSON = new Gson();

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return GSON.fromJson(json, clazz);
        } catch (Exception e) {
            log.error("待转换的json：{}", json, e);
            return null;
        }
    }

    public static <T> T fromJson(String json, Type type) {
        try {
            return GSON.fromJson(json, type);
        } catch (Exception e) {
            log.error("待转换的json：{}", json, e);
            return null;
        }
    }

    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }
}

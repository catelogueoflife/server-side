package com.touchkiss.catalogueoflife.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created on 2020/04/03 18:07
 *
 * @author Touchkiss
 */
public class StringUtil {
    public static String substringAtIndexOf(String sourceStr, String... searchStr) {
        String temp = sourceStr;
        for (String search : searchStr) {
            if (temp.contains(search)) {
                temp = temp.substring(0, temp.indexOf(search));
            }
        }
        return temp;
    }

    public static boolean isNullOrBlank(Object obj) {
        return obj == null || StringUtils.isBlank(obj.toString());
    }

    public static boolean notNullOrBlank(Object obj) {
        return !isNullOrBlank(obj);
    }

    public static String[] toStringArray(Collection collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return null;
        }
        String[] array = new String[collection.size()];
        AtomicInteger i = new AtomicInteger(0);
        collection.forEach(item -> array[i.getAndIncrement()] = item.toString());
        return array;
    }
}

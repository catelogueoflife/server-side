package com.touchkiss.catelogueoflife.controller;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2020/03/19 9:45
 *
 * @author Touchkiss
 */
public interface BaseController {
    default HashMap<String, String> parameterMap(HttpServletRequest request) {

        Map<String, String[]> prmap = request.getParameterMap();
        HashMap<String, String> parameterMap = new HashMap<>();
        String key;
        String[] value;


        for (Map.Entry<String, String[]> entry : prmap.entrySet()) {
            key = entry.getKey();
            if (!StringUtils.equalsAny(key, "p", "pagesize", "ordering")) {
                value = entry.getValue();
                parameterMap.put(key, value == null ? null : String.join(",", value));
            }
        }
        String[] orderings = prmap.get("ordering");
        if (orderings != null && orderings.length > 0 && StringUtils.isNotBlank(orderings[0])) {
            String ordering = orderings[0];
            if (ordering.startsWith("-")) {
                parameterMap.put("datatableorderby", "order by " + ordering.substring(1) + " desc");
            } else {
                parameterMap.put("datatableorderby", "order by " + ordering);
            }
        }
        return parameterMap;
    }

    default HashMap<String, String> parameterMapJQD(HttpServletRequest request) {
        Map<String, String[]> prmap = request.getParameterMap();
        HashMap<String, String> parameterMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : prmap.entrySet()) {
            if (ArrayUtils.isNotEmpty(entry.getValue())) {
                parameterMap.put(entry.getKey(), String.join(",", entry.getValue()));
            }
        }
        //处理排序字段
        String[] orderings = prmap.get("order[0][column]");
        if (orderings != null && orderings.length > 0 && StringUtils.isNotBlank(orderings[0])) {

            String ordering = prmap.get("columns[" + orderings[0] + "][data]")[0];
            ordering = formatColumnName(ordering);
            if ("desc".equals(parameterMap.getOrDefault("order[0][dir]","asc").trim().toLowerCase())) {
                parameterMap.put("datatableorderby", "order by " + ordering + " desc");
            } else {
                parameterMap.put("datatableorderby", "order by " + ordering);
            }
        }
        return parameterMap;
    }

    default String formatColumnName(String name) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = name.toCharArray();
        char[] lowerChars = name.toLowerCase().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 'A' && chars[i] <= 'Z') {
                stringBuilder.append('_');
            }
            stringBuilder.append(lowerChars[i]);
        }
        return stringBuilder.toString();
    }
}

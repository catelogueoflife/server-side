package com.touchkiss.catalogueoflife.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Created on 2020/03/18 17:15
 *
 * @author Touchkiss
 */
@Slf4j
public class NumberUtil {
    private NumberUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static int stringToInt(String numberStr) {
        if (StringUtils.isNotBlank(numberStr)) {
            try {
                return Integer.parseInt(numberStr.replaceAll("[,?]", ""));
            } catch (Exception e) {
                log.warn("无法将字符串转换为数字: {}", numberStr);
            }
        }
        return 0;
    }
}

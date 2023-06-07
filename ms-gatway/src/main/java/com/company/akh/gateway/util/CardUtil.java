package com.company.akh.gateway.util;

import org.apache.commons.lang3.StringUtils;

public final class CardUtil {

    private CardUtil() {
    }

    public static String maskPan(String pan) {
        if (StringUtils.isNotEmpty(pan)) {
            return pan.replaceAll("(\\b\\d{6})(\\d*)(\\d{4})", "$1******$3");
        }
        return pan;
    }

}

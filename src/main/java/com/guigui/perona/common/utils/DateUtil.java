package com.guigui.perona.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description: 日期处理工具
 * @Author: guigui
 * @Date: 2019-11-14 20:36
 */
public final class DateUtil {

    // LocalDateTime默认时间格式
    public static final String LOCAL_DEFAULT_PATTERN = "HH:mm:ss MMM dd E uuuu";

    // 年月格式
    public static final String LOCAL_YEAR_MONTH = "yyMM";
    // 年月日格式
    public static final String LOCAL_YEAR_MONTH_DAY = "yyMMdd";

    public static String dateFormat(LocalDateTime dateTime, String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return dateTime.format(dtf);
    }

    public static String dateFormat(LocalDateTime dateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(LOCAL_DEFAULT_PATTERN);
        return dateTime.format(dtf);
    }
}

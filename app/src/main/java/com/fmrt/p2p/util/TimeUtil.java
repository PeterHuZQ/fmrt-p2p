package com.fmrt.p2p.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *时间日期工具类
 */
public class TimeUtil
{
    public final static String DATE_FORMAT_DATE = "yyyy-MM-dd";
    /**
     * 方法1
     * @param time:1534176000000
     * @return yyyy-MM-dd : 2018-08-14
     */
    public static final String longTime2Str(long time) {
        return formatDate(new Date(time), DATE_FORMAT_DATE);
    }







    /**
     * 将日期类型数据转化为字符串形式
     * @param date
     * @param pattern 转化后字符串的格式
     * @return
     */
    public static final String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = getFormat(pattern);
        return sdf.format(date);
    }

    public static SimpleDateFormat getFormat(String format) {
        return new SimpleDateFormat(format);
    }
}

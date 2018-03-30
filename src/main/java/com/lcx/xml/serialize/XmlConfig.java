package com.lcx.xml.serialize;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XmlConfig {

    private static boolean showNull = false;

    private static String timeFormat = "yyyy-MM-dd HH:mm:ss";

    public static boolean isShowNull() {
        return showNull;
    }

    public static void setShowNull(boolean showNull) {
        XmlConfig.showNull = showNull;
    }

    public static String getTimeFormat() {
        return timeFormat;
    }

    public static void setTimeFormat(String timeFormat) {
        XmlConfig.timeFormat = timeFormat;
    }

    protected static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        return format.format(date);
    }

    protected static Date parseDate(String source) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        try {
            return format.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}

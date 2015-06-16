package com.fang.chinaindex.questionnaire.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 丽双 on 2015/5/4.
 */
public class DateUtils {

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_ONE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getCurrentDate() {
        return SIMPLE_DATE_FORMAT_ONE.format(new Date());
    }

    /**
     * @param endTime
     * @return
     */
    public static String getFormattedTime(String endTime){
        Date date = null;
        try {
            date = SIMPLE_DATE_FORMAT_ONE.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return SIMPLE_DATE_FORMAT_ONE.format(date);
    }
}

package com.fang.chinaindex.questionnaire.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 丽双 on 2015/5/4.
 */
public class DateUtils {
    public static String getCurrentDate(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String getStringFromTimeStamp(String endTime) {
        return null;
    }
}

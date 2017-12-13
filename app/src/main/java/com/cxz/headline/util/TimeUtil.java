package com.cxz.headline.util;

/**
 * Created by chenxz on 2017/12/12.
 */

public class TimeUtil {

    public static String getCurrentTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

}

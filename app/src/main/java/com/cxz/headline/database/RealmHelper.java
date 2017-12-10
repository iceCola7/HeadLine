package com.cxz.headline.database;

/**
 * Created by chenxz on 2017/12/10.
 */

public class RealmHelper {

    public static String DB_NAME = "HeadLine";

    private static RealmHelper instance;

    private RealmHelper() {
    }

    public static RealmHelper getInstance() {
        if (instance == null) {
            synchronized (RealmHelper.class) {
                if (instance == null)
                    instance = new RealmHelper();
            }
        }
        return instance;
    }

}
